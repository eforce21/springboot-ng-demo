import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {HomeComponent} from "./home/home.component";
import {TranslateModule} from "@ngx-translate/core";

@NgModule({
    imports: [
        CommonModule,
        TranslateModule
    ],
    declarations: [
        HomeComponent
    ]
})
export class HomeModule {
}
