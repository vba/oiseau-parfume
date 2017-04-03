package oiseau.parfume

import fr.oiseau.parfume.crawlers.HtmlCrawler
import fr.oiseau.parfume.dto.CurrenciesDto
import fr.oiseau.parfume.providers.DatesProvider
import fr.oiseau.parfume.transformers.PageGettingTransformer
import rx.Observable
import spock.lang.Specification

import java.time.LocalDate

class CurrencyServiceSpec extends Specification {

    def "getCurrencyForMonth should activate entire chain when a valid monthToAdd is provided"() {
        given:
        final monthToAdd = 0;
        final actualDates = [LocalDate.now()]
        final datesProvider = Mock(DatesProvider)
        final htmlCrawler = Mock(HtmlCrawler)
        final currenciesDto = new CurrenciesDto([:])
        final pageGettingTransformer = Mock(PageGettingTransformer)
        final sut = new CurrencyService(datesProvider, htmlCrawler, pageGettingTransformer)
        datesProvider.provide(monthToAdd) >> actualDates
        pageGettingTransformer.call(_) >> Observable.just(new Tuple2(actualDates.first(), ""))
        htmlCrawler.findCurrencies(actualDates[0], "") >> currenciesDto
        currenciesDto.toMap() >> [:]

        final expected

        when:
        expected = sut.getCurrencyForMonth(monthToAdd)

        then:
        expected.toBlocking().first() == [[date:null, eur:null, usd:null]]
    }
}
