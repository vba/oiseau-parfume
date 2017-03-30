import fr.oiseau.parfume.cache.PagesCacheManager
import fr.oiseau.parfume.clients.CurrencyClientImpl

import fr.oiseau.parfume.providers.DatesProviderImpl
import fr.oiseau.parfume.crawlers.HtmlCrawlerImpl
import fr.oiseau.parfume.transformers.PageGettingTransformer

// Place your Spring DSL code here
beans = {
    currencyClient(CurrencyClientImpl) {
//        someProperty = 42
//        otherProperty = "blue"
//        bookService = ref("bookService")
    }
    datesProvider(DatesProviderImpl) {}
    htmlParser(HtmlCrawlerImpl) {}
    pagesCacheManager(PagesCacheManager) {}
    pageGettingTransformer(PageGettingTransformer) {}
}
