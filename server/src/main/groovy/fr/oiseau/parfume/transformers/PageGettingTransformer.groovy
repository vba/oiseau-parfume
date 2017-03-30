package fr.oiseau.parfume.transformers

import com.google.gson.internal.$Gson$Preconditions
import fr.oiseau.parfume.ArgumentPreconditions
import fr.oiseau.parfume.cache.PagesCacheManager
import fr.oiseau.parfume.clients.CurrencyClient
import rx.Observable
import rx.schedulers.Schedulers

import java.time.LocalDate

import static fr.oiseau.parfume.ArgumentPreconditions.notNull

class PageGettingTransformer implements Observable.Transformer<LocalDate, Tuple2<LocalDate, String>> {

    private final PagesCacheManager pagesCacheManager
    private final CurrencyClient currencyClient

    PageGettingTransformer(CurrencyClient currencyClient,
                           PagesCacheManager pagesCacheManager) {
        this.currencyClient = notNull(currencyClient)
        this.pagesCacheManager = notNull(pagesCacheManager)
    }

    @Override
    Observable<Tuple2<LocalDate, String>> call(Observable<LocalDate> completable) {
        completable
            .observeOn(Schedulers.io())
            .flatMap({ mapFromCache(it) }, { x, y ->
                tuple(x, pagesCacheManager.putIfNotPresent(x.toString(), y.toString()))
            })
            .observeOn(Schedulers.computation())
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
