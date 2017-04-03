import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';
import {FormsModule} from '@angular/forms';
import {HttpModule} from '@angular/http';
import {LocationStrategy, HashLocationStrategy} from '@angular/common';
import {NgbModule} from "@ng-bootstrap/ng-bootstrap";
import {IndexComponent} from './index/index.component';
import {AppComponent} from './app.component';
import {AppRoutingModule} from "./app-routing.module";
import {IndexService} from "./index/index.service";
import {ChartModule} from "angular2-highcharts";

import { HighchartsStatic } from 'angular2-highcharts/dist/HighchartsService';

export function highchartsFactory() {
    return require('highcharts');
}

@NgModule({
    declarations: [
        AppComponent,
        IndexComponent
    ],
    imports: [
        ChartModule,
        BrowserModule,
        FormsModule,
        HttpModule,
        AppRoutingModule,
        NgbModule.forRoot()
    ],
    providers: [
        {provide: LocationStrategy, useClass: HashLocationStrategy},
        { provide: HighchartsStatic, useFactory: highchartsFactory},
        IndexService
    ],
    bootstrap: [AppComponent]
})
export class AppModule {
}
