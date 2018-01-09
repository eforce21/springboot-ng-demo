import {NgModule} from "@angular/core";
import {HTTP_INTERCEPTORS} from "@angular/common/http";
import {TokenInterceptor} from "./interceptor/token.interceptor";
import {AuthGuard} from "./guards/auth.guard";
import {CommonModule} from "@angular/common";

@NgModule({
    imports: [
        CommonModule
    ],
    providers: [
        AuthGuard,
        {
            provide: HTTP_INTERCEPTORS,
            useClass: TokenInterceptor,
            multi: true
        }
    ]
})

export class CoreModule {

}