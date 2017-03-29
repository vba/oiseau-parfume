package oiseau.parfume

import grails.rest.RestfulController
import grails.rx.web.RxController
import org.springframework.beans.factory.annotation.Autowired
import rx.Observable
import rx.schedulers.Schedulers
import tools.CurrencyClient
import tools.DatesProvider

class CurrencyController implements RxController {

    private final CurrencyClient currencyClient
    private final DatesProvider datesProvider

    @Autowired
    CurrencyController(DatesProvider datesProvider,
                       CurrencyClient currencyClient) {

        this.datesProvider = datesProvider
        this.currencyClient = currencyClient

        assert currencyClient != null : 'currencyClient cannot be a null'
        assert datesProvider != null : 'dates provider cannot be a null'
    }

    def index() {
        Observable
            .from(datesProvider.provide())
            .observeOn(Schedulers.io())
            .flatMap({currencyClient.getEntirePageByDate(it)})
            .observeOn(Schedulers.computation())
            .map({println(it); it})
        //Observable.just([field: "DDDD"])
    }
}
