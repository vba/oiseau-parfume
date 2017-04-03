package oiseau.parfume

import grails.rx.web.RxController
import org.springframework.beans.factory.annotation.Autowired
import rx.Observable

import static fr.oiseau.parfume.ArgumentPreconditions.notNull

class CurrencyController implements RxController {

    private final CurrencyService currencyService

    @Autowired
    CurrencyController(CurrencyService currencyService) {
        this.currencyService = notNull(currencyService)
    }

    def index() {
        if (params.monthsToAdd && !params.monthsToAdd.matches('^(?:0|-1)$')) {
            response.sendError(400, "Wrong monthsToAdd param value: ${params.monthsToAdd}")
            return Observable.empty()
        }
        final monthsToAdd = params.monthsToAdd as Integer ?: 0
        currencyService.getCurrencyForMonth(monthsToAdd)
    }
}
