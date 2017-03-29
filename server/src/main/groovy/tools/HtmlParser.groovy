package tools

import dto.CurrencyDto
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.select.Elements

import java.math.MathContext
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.time.LocalDate
import java.time.format.DateTimeFormatter

import static java.math.MathContext.DECIMAL32

interface HtmlParser {
    Tuple2<CurrencyDto, CurrencyDto> findCurrencies(LocalDate date, String html)
}

class HtmlParserImpl implements HtmlParser {

    @Lazy static DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyyMMdd")

    @Lazy static DecimalFormat decimalFormat = {
        DecimalFormatSymbols formatSymbols = new DecimalFormatSymbols(Locale.default);
        formatSymbols.setDecimalSeparator(',' as char);
        formatSymbols.setGroupingSeparator('.' as char);
        final decimalFormat = new DecimalFormat('##.####', formatSymbols);
        decimalFormat.parseBigDecimal = true
        decimalFormat
    } ()

    @Override
    Tuple2<CurrencyDto, CurrencyDto> findCurrencies(LocalDate date, String html) {
        final document = Jsoup.parse(html)

        return new Tuple2<CurrencyDto, CurrencyDto>(
            extractCurrency(document, 'usd', date),
            extractCurrency(document, 'eur', date)
        );
    }

    private static CurrencyDto extractCurrency(Document document, String label, LocalDate date) {
        final selected = document.select("table.data tr:contains($label) > td")

        new CurrencyDto(label: label,
                        value: decimalFormat.parse(selected.last().text()) as BigDecimal,
                        units: new Integer(selected[2].text()),
                        date: date.format(dateTimeFormatter))
    }
}
