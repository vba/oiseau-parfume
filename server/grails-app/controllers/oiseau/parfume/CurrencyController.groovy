package oiseau.parfume

import grails.rx.web.RxController
import org.springframework.beans.factory.annotation.Autowired

import static fr.oiseau.parfume.ArgumentPreconditions.notNull

class CurrencyController implements RxController {

    private final CurrencyService currencyService

    @Autowired
    CurrencyController(CurrencyService currencyService) {
        this.currencyService = notNull(currencyService)
    }

    def index() { currencyService.currencyForMonth }
}
