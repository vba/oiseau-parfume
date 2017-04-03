# oiseau-parfumé

## Initial tasks

- Make a page to display USD/EUR currency rates to RUB for the last month
- Page should have 2 tabs, first with a table and second with a chart
- Prices must be obtained from the page `https://www.cbr.ru/currеncy_bаse/dаily.аspx?dаte_rеq=XXX`
- The cells with max/min rates should be highlighted with colors
- Application should have as less connections to source as possible in every day usage

## Requirements

- angular-cli, can be installed through `npm -g install angular-js`
- jdk8

## How to run

- You can run both server and client parts with `./gradlew bootRun --parallel` from the root
- Client is available from `http://localhost:4200/`
- Server is reachable from `http://localhost:8080/currency?monthsToAdd=-1`

### Realisation details

As requested I've used Grails 3 and Bootstrap to accomplish this exercise. As you can notice the controller part is made in asynchronous way, because it matters. Since Servlet API v3 we can do standard Java web application in unblocking way. Honestly speaking it's not the best manner to do non blocking apllication, more robust solutions can be done with `Vert.x` ou `Play 2 / Netty`. 

##### Reactive Extensions
Non blocking part is covered with `rxGroovy`, I didn't add any error handling because it CBR usage seems to be pretty straightforward, if needed it can be easily done with Reactive Extensions.

##### Caching
All CBR requests are cached, I did not use any standard Grails/Spring component because it does not seem to work quite well with `rxGroovy` and honestly speaking I was looking for more flexible solution rather than configurable composition. That's why I have used `Guava` memory cache manager.

##### HTML Crawling
This exercise requires to work with plain HTML page rather than web service. In true live I would strongly recommend you to use some 3rd party service like Yahoo or Google currency instead of crawling HTML. 

##### Tests
Tests are important ... Mostly all server parts are covered with unit tests. I did't write any functional/integration because I prefer to use external frameworks such cucumber for purposes of integration testing. Client part doesn't have a stong test coverage, lack of time.

##### Client
Client part is covered with Angular2, TypeScript, RxJS and Bootstrap.

