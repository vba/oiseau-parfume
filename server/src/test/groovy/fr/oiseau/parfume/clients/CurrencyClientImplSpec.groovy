package fr.oiseau.parfume.clients

import rx.Observable
import spock.lang.Specification

import java.time.LocalDate
import java.time.format.DateTimeFormatter

class CurrencyClientImplSpec extends Specification {

    def "getEntirePageByDate should return nothing when date in future is received" () {
        given:
        final actualDate = LocalDate.now().plusWeeks(1)
        final cbrClient = Mock(CbrClient)
        final sut = new CurrencyClientImpl(cbrClient)
        final expected

        when:
        expected = sut.getEntirePageByDate(actualDate)

        then:
        expected.isEmpty()
    }

    def "getEntirePageByDate should call the client when valid date is received" () {
        given:
        final actualContent = UUID.randomUUID().toString()
        final actualDate = LocalDate.now().plusWeeks(-1)
        final actualFormattedDate = actualDate.format(DateTimeFormatter.ofPattern(CurrencyClientImpl.DateFormat))
        final cbrClient = Mock(CbrClient)
        cbrClient.getEntirePageByDate(actualFormattedDate) >> Observable.just(actualContent)
        final sut = new CurrencyClientImpl(cbrClient)
        final expected

        when:
        expected = sut.getEntirePageByDate(actualDate)

        then:
        expected.toBlocking().first() == actualContent
    }
}
