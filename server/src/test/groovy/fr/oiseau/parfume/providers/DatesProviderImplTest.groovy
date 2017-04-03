package fr.oiseau.parfume.providers

import spock.lang.Specification

import java.time.LocalDate

class DatesProviderImplTest extends Specification {

    def "It should get begin and end dates for entire month" () {
        given:
        LocalDateProvider.instance.provide = {LocalDate.of(2017, 02, 28)}
        final sut = new DatesProviderImpl()
        final expected

        when:
        expected = sut.getFirstAndLastDays(0)

        then:
        expected.first.dayOfMonth == 1
        expected.second.dayOfMonth == 28
    }

    def "It should generate a complete month for an interval" () {
        given:
        LocalDateProvider.instance.provide = {LocalDate.of(2017, 02, 15)}
        final sut = new DatesProviderImpl()
        final List<LocalDate> expected

        when:
        expected = sut.provide()

        then:
        expected != null
        expected.size() == 28
        0 .. 27 .each {
            assert expected[it].dayOfMonth == it + 1
            assert expected[it].monthValue == 2
            assert expected[it].year == 2017
        }
    }
}
