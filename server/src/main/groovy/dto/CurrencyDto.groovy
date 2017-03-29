package dto

import groovy.transform.TupleConstructor
import jdk.nashorn.internal.ir.annotations.Immutable

@Immutable
@TupleConstructor
class CurrencyDto {
    String date
    Integer units
    String label
    BigDecimal value
}
