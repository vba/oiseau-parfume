package fr.oiseau.parfume.clients

import groovy.transform.PackageScope
import retrofit2.http.GET
import retrofit2.http.Query
import rx.Observable

import java.time.LocalDate
import java.time.format.DateTimeFormatter

import static fr.oiseau.parfume.ArgumentPreconditions.notNull

interface CbrClient {
    @GET("daily.aspx?")
    Observable<String> getEntirePageByDate(@Query("date_req") String date)
}

interface CurrencyClient {
    Observable<String> getEntirePageByDate(LocalDate date)
}

class CurrencyClientImpl implements CurrencyClient {

    @PackageScope
    static final String DateFormat = "dd.MM.yyyy"
    private final CbrClient cbrClient

    CurrencyClientImpl(CbrClient cbrClient) {
        this.cbrClient = notNull(cbrClient)
    }

    Observable<String> getEntirePageByDate(LocalDate date) {
        if (date.isAfter(LocalDate.now())) {
            return Observable.empty()
        }
        final formattedDate = date.format(DateTimeFormatter.ofPattern(DateFormat))
        cbrClient.getEntirePageByDate(formattedDate)
    }
}
