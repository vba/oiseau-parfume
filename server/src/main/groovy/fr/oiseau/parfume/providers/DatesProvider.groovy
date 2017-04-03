package fr.oiseau.parfume.providers

import groovy.transform.PackageScope

import java.time.LocalDate

interface DatesProvider {
    List<LocalDate> provide()
    List<LocalDate> provide(Integer monthsToAdd)
}

@Singleton
@PackageScope
class LocalDateProvider {
    Closure<LocalDate> provide = { Integer x = 0 -> LocalDate.now().plusMonths(x) }
}

class DatesProviderImpl implements DatesProvider {

    @PackageScope
        getFirstAndLastDays = { Integer x ->
            final now = LocalDateProvider.instance.provide(x)
            new Tuple2<LocalDate, LocalDate>(
                now.withDayOfMonth(1),
                now.plusMonths(1)
                    .withDayOfMonth(1)
                    .minusDays(1)
            )
        }

    @PackageScope
        fillDatesInterval = { Tuple2<LocalDate, LocalDate> days ->
            (days.first.dayOfMonth..days.second.dayOfMonth)
                .collect { LocalDate.of(days.first.year, days.first.month, it as Integer) }
        }

    @Override
    List<LocalDate> provide(Integer monthsToAdd = 0) {
        (getFirstAndLastDays.curry(monthsToAdd) >> fillDatesInterval) ()
    }
}
