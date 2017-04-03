package oiseau.parfume

class BootStrap {

    def currencyService

    def init = { servletContext ->
        currencyService
            .getCurrencyForMonth(0)
            .mergeWith(currencyService.getCurrencyForMonth(-1))
            .subscribe({}, {}, {
                println("Currencies were warmed up, it should work faster now")
            })
    }

    def destroy = {
    }
}
