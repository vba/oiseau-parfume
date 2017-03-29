import tools.CacheableHttpClient
import tools.CurrencyClientImpl
import tools.DatesProviderImpl

// Place your Spring DSL code here
beans = {
    currencyClient(CurrencyClientImpl) {
//        someProperty = 42
//        otherProperty = "blue"
//        bookService = ref("bookService")
    }
    datesProvider(DatesProviderImpl) {}
    cacheableHttpClient(CacheableHttpClient) {}
}
