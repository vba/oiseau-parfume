package oiseau.parfume

class BootStrap {

    def currencyService

    def init = { servletContext ->
        currencyService
            .currencyForMonth
            .subscribe({}, {}, {
                println("Currencies warmed, it must work faster now")
            })
    }

    def destroy = {
    }
}
