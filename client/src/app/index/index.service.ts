import 'rxjs/add/operator/map';
import 'rxjs/add/operator/publishReplay';
import {Observable} from 'rxjs';

import { Map } from "immutable";
import {Injectable} from '@angular/core';
import {Http, Response} from '@angular/http';

type Currencies = Map<string, any>[]

@Injectable()
export class IndexService {

    constructor(private http: Http) {
    }

    getCurrencies(monthsToAdd: string = '0'): Observable<Currencies> {
        return this.http.get('http://localhost:8080/currency?monthsToAdd='+monthsToAdd)
            .map((res: Response) => {
                return (<any[]>res.json()).map(x => Map(x))
            })
            .publishReplay(1)
            .refCount();
    }
}