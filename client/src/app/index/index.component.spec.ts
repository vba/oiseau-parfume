/* tslint:disable:no-unused-variable */

import {TestBed, async} from '@angular/core/testing';
import {IndexComponent} from './index.component';
import {Observable, Observer} from 'rxjs';
import {IndexService} from './index.service';
import {APP_BASE_HREF} from '@angular/common';
import {RouterTestingModule} from '@angular/router/testing';
import {Routes} from '@angular/router';
import {FormsModule} from '@angular/forms';

describe('Component: Index', () => {

    const rootRouterConfig: Routes = [
        {path: '', redirectTo: 'index', pathMatch: 'full'},
        {path: 'index', component: IndexComponent},
    ];

    let component: IndexComponent;

    const indexService = {
        getCurrencies: (mta: any = 0) => {
            return Observable.create((observer: Observer<any>) => {
                observer.next({"date":"20170401","eur":{"value":"59.8107"},"usd":{"value":"55.9606"}});
                observer.next({"date":"20170402","eur":{"value":"59.8108"},"usd":{"value":"55.9607"}});
                observer.next({"date":"20170403","eur":{"value":"59.8109"},"usd":{"value":"55.9608"}});
                observer.next({"date":"20170404","eur":{"value":"59.8110"},"usd":{"value":"55.9609"}});
                observer.complete();
            });
        }
    };

    beforeEach(() => {
        TestBed.configureTestingModule({
            declarations: [
                IndexComponent
            ],
            imports: [
                RouterTestingModule.withRoutes(rootRouterConfig)
            ],
            providers: [
                {provide: IndexService, useValue: indexService},
                {provide: APP_BASE_HREF, useValue: '/'},
            ],
        });

        let fixture = TestBed.createComponent(IndexComponent);
        component = fixture.debugElement.componentInstance;
    });

    it('should create the component', async(() => {
        expect(component).toBeTruthy();
    }));

    it('should initialize controllers with currencies', () => {
        // arrange
        // act
        component.ngOnInit();
        // assert
        expect(component._currencies.length).toBe(4);
        expect(component._currencies[0].get('date')).toBe('20170401');
        expect(component._currencies[1].get('date')).toBe('20170402');
        expect(component._currencies[2].get('date')).toBe('20170403');
        expect(component._currencies[3].get('date')).toBe('20170404');
    });

    it('should initialize max min map with coherent values', () => {
        // arrange
        // act
        component.ngOnInit();
        // assert
        expect(component._maxMinMap.get('eurMax')).toBe(59.8110);
        expect(component._maxMinMap.get('eurMin')).toBe(59.8107);
        expect(component._maxMinMap.get('usdMax')).toBe(55.9609);
        expect(component._maxMinMap.get('usdMin')).toBe(55.9606);
    });

});
