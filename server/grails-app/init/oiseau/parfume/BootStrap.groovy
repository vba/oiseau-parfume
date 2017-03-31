package oiseau.parfume

class BootStrap {

    def currencyService

    def init = { servletContext ->
        currencyService
            .currencyForMonth
            .subscribe({}, {}, {
                println("Currencies were warmed up, it should work faster now")
            })
    }

    def destroy = {
    }
}
