import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {LoginComponent} from './login/login.component';
import {TranslateModule} from "@ngx-translate/core";
import {AuthGuard} from "../core/guards/auth.guard";
import {FormsModule} from "@angular/forms";

@NgModule({
    imports: [
        CommonModule,
        TranslateModule,
        FormsModule
    ],
    declarations: [
        LoginComponent
    ],
    exports: [
        LoginComponent
    ],
    providers: [
        AuthGuard
    ]
})
export class LoginModule {
}
