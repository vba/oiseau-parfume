package fr.oiseau.parfume

import fr.oiseau.parfume.cache.PagesCacheManager
import fr.oiseau.parfume.clients.CbrClient
import fr.oiseau.parfume.clients.CurrencyClient
import fr.oiseau.parfume.clients.CurrencyClientImpl
import fr.oiseau.parfume.crawlers.HtmlCrawler
import fr.oiseau.parfume.crawlers.HtmlCrawlerImpl
import fr.oiseau.parfume.providers.DatesProvider
import fr.oiseau.parfume.providers.DatesProviderImpl
import fr.oiseau.parfume.transformers.PageGettingTransformer
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

@Configuration
class AppConfig {

    @Bean PageGettingTransformer pageGettingTransformer(CurrencyClient currencyClient,
                                                        PagesCacheManager pagesCacheManager) {
        new PageGettingTransformer(currencyClient, pagesCacheManager)
    }

    @Bean PagesCacheManager pagesCacheManager() {
        new PagesCacheManager()
    }

    @Bean HtmlCrawler htmlCrawler() {
        new HtmlCrawlerImpl()
    }

    @Bean DatesProvider datesProvider() {
        new DatesProviderImpl()
    }

    @Bean CurrencyClient currencyClient(CbrClient cbrClient) {
        new CurrencyClientImpl(cbrClient)
    }

    @Bean CbrClient cbrClient(Retrofit.Builder retrofitBuilder) {
        retrofitBuilder.build().create(CbrClient)
    }

    @Bean Retrofit.Builder retrofitBuilder(@Value('${app.url.cbr}') String baseUrl) {
        new Retrofit.Builder()
            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
            .addConverterFactory(ScalarsConverterFactory.create())
            .baseUrl(baseUrl)
    }
}
