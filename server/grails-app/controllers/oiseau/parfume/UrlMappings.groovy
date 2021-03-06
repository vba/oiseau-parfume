package oiseau.parfume

class UrlMappings {

    static mappings = {
        get "/currency"(controller: 'currency', action: 'index')
        delete "/$controller/$id(.$format)?"(action:"delete")
        get "/$controller(.$format)?"(action:"index")
        get "/$controller/$id(.$format)?"(action:"show")
        post "/$controller(.$format)?"(action:"save")
        put "/$controller/$id(.$format)?"(action:"update")
        patch "/$controller/$id(.$format)?"(action:"patch")

        "/"(controller: 'currency', action:'index')
        "500"(view: '/error')
        "404"(view: '/notFound')
    }
}
