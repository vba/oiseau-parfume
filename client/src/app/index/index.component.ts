import {ChangeDetectorRef, Component, NgZone, OnInit, ViewChild} from '@angular/core';
import {IndexService} from './index.service';
import { Map } from "immutable";
import {ChartModule} from "angular2-highcharts";

type DtoMap = Map<string, any>

ChartModule.forRoot(require('highcharts'));

@Component({
    selector: 'app-index',
    templateUrl: './index.component.html',
    styleUrls: ['./index.component.css']
})
export class IndexComponent implements OnInit {

    private readonly _initialMaxMinMap: DtoMap = Map({
        eurMax: 0,
        usdMax: 0,
        eurMin: Number.MAX_SAFE_INTEGER,
        usdMin: Number.MAX_SAFE_INTEGER
    });

    public readonly _sources = [
        { value: '-1', display: 'Previous month' },
        { value: '0', display: 'Current month' }
    ];
    public _sourceMonthsToAdd: string = '-1';

    _maxMinMap: DtoMap = Map({});

    public _currencies: DtoMap[] = [];

    public _chartOptions: any = {};

    constructor(private indexService: IndexService) {
    }

    ngOnInit(): void {
        this.refresh()
    }

    refresh(): void {
        this.indexService.getCurrencies(this._sourceMonthsToAdd)
            .subscribe(currencies => {
                this._currencies = currencies;
                this._maxMinMap = currencies.reduce((agg, x) => {
                    const eur = x.get('eur').value * 1;
                    const usd = x.get('usd').value * 1;
                    return agg
                        .set('eurMax', agg.get('eurMax') < eur ? eur : agg.get('eurMax'))
                        .set('eurMin', agg.get('eurMin') > eur ? eur : agg.get('eurMin'))
                        .set('usdMax', agg.get('usdMax') < usd ? usd : agg.get('usdMax'))
                        .set('usdMin', agg.get('usdMin') > usd ? usd : agg.get('usdMin'));
                }, this._initialMaxMinMap);
                this._chartOptions = {
                    title : { text : 'The chart' },
                    series: [
                        {data: currencies.map(x => this.getPoint('usd', x)), name: 'USD'},
                        {data: currencies.map(x => this.getPoint('eur', x)), name: 'EUR'}
                    ]
                };
            });
    }

    private getPoint(currency: string, x: DtoMap): number[] {
        return [
            x.get('date').toString().substring(6) * 1,
            x.get(currency).value * 1
        ];
    }

    public getSpecialStyle(value :any, type: string) {
        const price = value * 1;
        if (this._maxMinMap.get(type+'Max') == price) return 'max-price';
        if (this._maxMinMap.get(type+'Min') == price) {
            return 'min-price';
        }
        return '';
    }

    public chartClicked(e:any):void {
        console.log(e);
    }

    public chartHovered(e:any):void {
        console.log(e);
    }
}
