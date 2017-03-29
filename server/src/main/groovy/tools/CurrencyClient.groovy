package tools

import groovy.transform.PackageScope
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.jackson.JacksonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import rx.Observable

import java.time.LocalDate
import java.time.format.DateTimeFormatter

@PackageScope
interface CbrClient {
    @GET("daily.aspx?")
    Observable<String> getEntirePageByDate(@Query("date_req") String date)
}

interface CurrencyClient {
    Observable<String> getEntirePageByDate(LocalDate date)
}

class CurrencyClientImpl implements CurrencyClient {

    private final CacheableHttpClient cacheableHttpClient

    CurrencyClientImpl(CacheableHttpClient cacheableHttpClient) {
        this.cacheableHttpClient = cacheableHttpClient
        assert cacheableHttpClient != null : "cacheableHttpClient cannot be a null"
    }

    private @Lazy CbrClient cbrClient = {
        Retrofit retrofit = new Retrofit.Builder()
            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
            .addConverterFactory(ScalarsConverterFactory.create())
            .baseUrl("https://www.cbr.ru/currency_base/")
            .client(cacheableHttpClient.get())
            .build();

        retrofit.create(CbrClient)
    } ()

    Observable<String> getEntirePageByDate(LocalDate date) {
        final formattedDate = date.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"))
        cbrClient.getEntirePageByDate(formattedDate)
    }
}
