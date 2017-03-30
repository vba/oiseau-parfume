import {Injectable} from '@angular/core';
import {Http, Response} from '@angular/http';

import 'rxjs/add/operator/map';
import 'rxjs/add/operator/publishReplay';
import {Observable} from 'rxjs';

@Injectable()
export class NavService {

    _navData: Observable<any>;

    constructor(private http: Http) {
    }

    getNavData(): Observable<any> {
        if (!this._navData) {
            this._navData = this.http.get('http://localhost:8080/currency')
                .map((res: Response) => res.json())
                .publishReplay(1)
                .refCount();
        }
        return this._navData;
    }
}
