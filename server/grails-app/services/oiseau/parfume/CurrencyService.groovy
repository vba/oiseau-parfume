package oiseau.parfume

import fr.oiseau.parfume.crawlers.HtmlCrawler
import fr.oiseau.parfume.providers.DatesProvider
import fr.oiseau.parfume.transformers.PageGettingTransformer
import org.springframework.beans.factory.annotation.Autowired
import rx.Observable

import static fr.oiseau.parfume.ArgumentPreconditions.notNull

class CurrencyService {
    private final DatesProvider datesProvider
    private final HtmlCrawler htmlParser
    private final PageGettingTransformer pageGettingTransformer

    @Autowired
    CurrencyService(DatesProvider datesProvider,
                    HtmlCrawler htmlParser,
                    PageGettingTransformer pageGettingTransformer) {

        this.pageGettingTransformer = notNull(pageGettingTransformer)
        this.htmlParser = notNull(htmlParser)
        this.datesProvider = notNull(datesProvider)
    }

    def getCurrencyForMonth (Integer monthsToAdd = 0) {
        Observable
            .from(datesProvider.provide(monthsToAdd))
            .compose(pageGettingTransformer)
            .map({ htmlParser.findCurrencies(it.first, it.second as String) })
            .map({ it.toMap() })
            .reduce([].asImmutable(), { agg, x -> agg + x })
    }
}