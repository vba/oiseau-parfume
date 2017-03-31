package fr.oiseau.parfume.crawlers

import spock.lang.Specification

import java.time.LocalDate

class HtmlCrawlerSpec extends Specification {
    final String markup = '''
<table class="data">
    <tbody>
        <tr>
            <th>Цифр. код</th>
            <th>Букв. код</th>
            <th>Единиц</th>
            <th>Валюта</th>
            <th>Курс</th>
        </tr>
        <tr>
            <td class="">933</td>
            <td class="">BYN</td>
            <td class="">1</td>
            <td class="">Белорусский рубль</td>
            <td class="">30,8095</td>
        </tr>
        <tr>
            <td class="">840</td>
            <td class="">USD</td>
            <td class="">1</td>
            <td class="">Доллар США</td>
            <td class="">58,9540</td>
        </tr>
        <tr>
            <td class="">978</td>
            <td class="">EUR</td>
            <td class="">1</td>
            <td class="">Евро</td>
            <td class="">62,7447</td>
        </tr>
        <tr>
            <td class="">356</td>
            <td class="">INR</td>
            <td class="">100</td>
            <td class="">Индийских рупий</td>
            <td class="">89,1082</td>
        </tr>
    </tbody>
</table>
    '''

    def "It should find currencies in expected html stream" () {
        given:
        final sut = new HtmlCrawlerImpl()
        final expected

        when:
        expected = sut.findCurrencies(LocalDate.now(), markup)

        then:
        expected?.eur != null
        expected?.usd != null
        expected.eur.units == 1
        expected.usd.value == '58.9540'
        expected.usd.units == 1
        expected.eur.value == '62.7447'
    }
}
