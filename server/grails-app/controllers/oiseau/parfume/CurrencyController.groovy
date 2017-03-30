package oiseau.parfume

import grails.rx.web.RxController
import org.springframework.beans.factory.annotation.Autowired
import rx.Observable
import rx.schedulers.Schedulers
import fr.oiseau.parfume.clients.CurrencyClient
import fr.oiseau.parfume.providers.DatesProvider
import fr.oiseau.parfume.crawlers.HtmlCrawler
import fr.oiseau.parfume.cache.PagesCacheManager

import java.time.LocalDate

class CurrencyController implements RxController {

    private final CurrencyClient currencyClient
    private final DatesProvider datesProvider
    private final HtmlCrawler htmlParser
    private final PagesCacheManager pagesCacheManager

    @Autowired
    CurrencyController(DatesProvider datesProvider,
                       CurrencyClient currencyClient,
                       HtmlCrawler htmlParser,
                       PagesCacheManager pagesCacheManager) {

        this.pagesCacheManager = pagesCacheManager
        this.htmlParser = htmlParser
        this.datesProvider = datesProvider
        this.currencyClient = currencyClient

        assert currencyClient != null : 'currencyClient cannot be a null'
        assert datesProvider != null : 'datesProvider cannot be a null'
        assert htmlParser != null : 'htmlParser cannot be a null'
        assert pagesCacheManager != null : 'pagesCacheManager cannot be a null'
    }

    def index() {
        Observable
            .from(datesProvider.provide())
            .observeOn(Schedulers.io())
            .flatMap({ mapFromCache(it) }, { x, y ->
                tuple(x, pagesCacheManager.putIfNotPresent(x.toString(), y.toString()))
            })
            .observeOn(Schedulers.computation())
            .map({ htmlParser.findCurrencies(it.first, it.second as String) })
            .map({ [usd: it.first, eur: it.second] })
            .reduce([].asImmutable(), { agg, x -> agg + x })
    }

    private static <TFirst, TSecond> Tuple2<TFirst, TSecond> tuple(TFirst first, TSecond second) {
        new Tuple2<TFirst, TSecond>(first, second)
    }

    private Observable<String> mapFromCache(LocalDate date) {
        pagesCacheManager
            .get(date.toString())
            .map({ Observable.just(it) })
            .orElse(currencyClient.getEntirePageByDate(date))
    }
}
