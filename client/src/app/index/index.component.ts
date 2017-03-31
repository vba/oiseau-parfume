import {Component, OnInit} from '@angular/core';
import {IndexService} from './index.service';
import {Route, Router} from '@angular/router';
import { Map } from "immutable";

type DtoMap = Map<string, any>

@Component({
    selector: 'app-index',
    templateUrl: './index.component.html',
    styleUrls: ['./index.component.css']
})
export class IndexComponent implements OnInit {

    private readonly _initialMaxMinMap: DtoMap = Map({maxEur: 0, maxUsd: 0, minEur: 0, minUsd: 0});

    maxMinMap: DtoMap = Map({});

    private currencies: DtoMap[] = [];

    constructor(private indexService: IndexService, private router: Router) {
    }

    ngOnInit(): void {
        this.indexService.getCurrencies().subscribe(currencies  => {
            this.currencies = currencies;
            this.maxMinMap = currencies.reduce((agg, x) => {
                const eur = this.parseCurrencyValue(x.get('eur'));
                const usd = this.parseCurrencyValue(x.get('usd'));
                return agg
                    .set('maxEur', agg.get('maxEur') < eur ? eur : agg.get('maxEur'))
                    .set('minEur', agg.get('minEur') > eur ? eur : agg.get('minEur'))
                    .set('maxUsd', agg.get('maxUsd') < usd ? usd : agg.get('maxUsd'))
                    .set('minUsd', agg.get('minUsd') > usd ? usd : agg.get('minUsd'));
            }, this._initialMaxMinMap);

            /*
            currencies.controllers.sort((a: any, b: any) => {
                if (a.name < b.name) {
                    return -1;
                } else if (a.name > b.name) {
                    return 1;
                } else {
                    return 0;
                }
            });
            */
        });
    }

    private parseCurrencyValue(curr: any): number {
        return curr.value * 1;
    }

    hasRoute(controllerName: string): boolean {
        return this.router.config.some((route: Route) => {
            if (route.path === controllerName) {
                return true;
            }
        });
    }
}
