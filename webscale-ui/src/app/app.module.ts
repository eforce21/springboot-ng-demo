import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';
import {FormsModule} from '@angular/forms';

import {AppComponent} from './app.component';
import {RouterModule} from '@angular/router';
import {AppRoutingModule} from './app.routing.module';
import {LoginModule} from './login/login.module';
import {TranslateLoader, TranslateModule} from '@ngx-translate/core';
import {TranslateHttpLoader} from '@ngx-translate/http-loader';
import {HttpClient, HttpClientModule} from '@angular/common/http';
import {ImagesRoutingModule} from "./images/images.routing.module";
import {ImagesModule} from "./images/images.module";
import {SharedModule} from "./shared/shared.module";
import {HomeModule} from "./home/home.module";
import {AdministrationModule} from "./administration/administration.module";
import {AdministrationRoutingModule} from "./administration/administration.routing.module";
import {HashLocationStrategy, LocationStrategy} from "@angular/common";
import {CoreModule} from "./core/core.module";

@NgModule({
    declarations: [
        AppComponent
    ],
    imports: [
        BrowserModule,
        FormsModule,
        RouterModule,
        CoreModule,
        HttpClientModule,
        TranslateModule.forRoot({
            loader: {
                provide: TranslateLoader,
                useFactory: translateStaticLoaderFactory,
                deps: [HttpClient]
            }
        }),
        AppRoutingModule,
        LoginModule,
        SharedModule,
        HomeModule,
        ImagesModule,
        ImagesRoutingModule,
        AdministrationModule,
        AdministrationRoutingModule
    ],
    providers: [
        {
            provide: LocationStrategy,
            useClass: HashLocationStrategy
        }
    ],
    bootstrap: [AppComponent]
})
export class AppModule {
}

export function translateStaticLoaderFactory(http: HttpClient) {
    return new TranslateHttpLoader(http, './assets/i18n/', '.json');
}