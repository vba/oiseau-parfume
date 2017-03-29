import tools.PagesCacheManager
import tools.CurrencyClientImpl

import tools.DatesProviderImpl
import tools.HtmlParserImpl

// Place your Spring DSL code here
beans = {
    currencyClient(CurrencyClientImpl) {
//        someProperty = 42
//        otherProperty = "blue"
//        bookService = ref("bookService")
    }
    datesProvider(DatesProviderImpl) {}
    htmlParser(HtmlParserImpl) {}
    pagesCacheManager(PagesCacheManager) {}
}
