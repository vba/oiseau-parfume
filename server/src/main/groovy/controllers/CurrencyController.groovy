package controllers

import tools.CurrencyClient
import tools.CurrencyClientImpl
import grails.rx.web.RxController

class CurrencyController implements RxController {

    private final CurrencyClient currencyClient

    CurrencyController(CurrencyClient currencyClient = CurrencyClientImpl.instance) {
        this.currencyClient = currencyClient
    }

    def all() {
    }
}
