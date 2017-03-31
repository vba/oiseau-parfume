import 'rxjs/add/operator/map';
import 'rxjs/add/operator/publishReplay';
import {Observable} from 'rxjs';

import { Map } from "immutable";
import {Injectable} from '@angular/core';
import {Http, Response} from '@angular/http';

type Currencies = Map<string, any>[]

@Injectable()
export class IndexService {

    private currencies: Observable<Currencies>;

    constructor(private http: Http) {
    }

    getCurrencies(): Observable<Currencies> {
        if (!this.currencies) {
            this.currencies = this.http.get('http://localhost:8080/currency')
                .map((res: Response) => {
                    return <Currencies>res.json()
                })
                .publishReplay(1)
                .refCount();
        }
        return this.currencies;
    }
}