package fr.oiseau.parfume.dto

import groovy.transform.Immutable
import groovy.transform.TupleConstructor

@Immutable(knownImmutableClasses = [CurrencyDto])
@TupleConstructor
class CurrenciesDto {
    String date
    CurrencyDto eur
    CurrencyDto usd

    def toMap() {
        ['date': date, 'eur': eur, 'usd': usd]
    }
}
