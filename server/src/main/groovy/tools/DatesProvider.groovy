package tools

import groovy.transform.PackageScope
import org.codehaus.groovy.runtime.InvokerHelper

import java.time.LocalDate

interface DatesProvider {
    List<LocalDate> provide()
}

@Singleton
@PackageScope
class LocalDateProvider {
    Closure<LocalDate> provide = { LocalDate.now() }
}

@Singleton
class DatesProviderImpl implements DatesProvider {

    @PackageScope
    Tuple2<Integer, Integer> "get begin and end dates for month" () {
        final now = LocalDateProvider.instance.provide()
        new Tuple2<Integer, Integer> (
            now.withDayOfMonth(1).dayOfMonth,
            now.plusMonths(1).withDayOfMonth(1).minusDays(1).dayOfMonth
        )
    }

    @PackageScope
    List<LocalDate> "generate dates interval"(Tuple2<Integer, Integer> days) {
        final now = LocalDateProvider.instance.provide()
        (days.first .. days.second).collect{ LocalDate.of(now.year, now.month, it) }
    }

    @Override
    List<LocalDate> provide() {
        (this.&"get begin and end dates for month" >> this.&"generate dates interval") ()
    }
}
