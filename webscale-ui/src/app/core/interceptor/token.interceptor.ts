
import {tap} from 'rxjs/operators';
import {
    HttpEvent,
    HttpHandler,
    HttpHeaderResponse,
    HttpInterceptor,
    HttpProgressEvent,
    HttpRequest,
    HttpResponse,
    HttpSentEvent,
    HttpUserEvent,
} from "@angular/common/http";
import {Observable} from "rxjs";

import {environment} from "../../../environments/environment";

export class TokenInterceptor implements HttpInterceptor {

    intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpSentEvent | HttpHeaderResponse | HttpProgressEvent | HttpResponse<any> | HttpUserEvent<any>> {

        let requestCopy = req;
        let authToken = localStorage.getItem(environment.authTokenKey);
        if (null != authToken) {
            requestCopy = req.clone({
                setHeaders: {
                    'X-Auth-Token': authToken
                }
            });
        }

        return next.handle(requestCopy).pipe(tap((event: HttpEvent<any>) => {
            if (event instanceof HttpResponse) {
                let authToken = event.headers.get(environment.authTokenKey);
                if (null != authToken) {
                    localStorage.setItem(environment.authTokenKey, authToken);
                }
            }
        }));
    }
}