import {Injectable} from '@angular/core';
import {NavigationService} from "./navigation.service";
import {environment} from "../../environments/environment";
import {Observable} from "rxjs/Observable";
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {HttpData} from "./http-data.interface";

@Injectable()
export class UserService {

    private readonly LOGIN_URL: string = '/api/v1/security/login';

    constructor(private httpService: NavigationService, private httpClient: HttpClient) {
    }

    public doLogin(authentication: HttpData): Observable<Object> {
        return this.httpClient.post(this.LOGIN_URL, authentication.toPayload(), {headers: new HttpHeaders().set('Content-Type', 'application/x-www-form-urlencoded')});
    }

    public doLogout() {
        localStorage.removeItem(environment.authTokenKey);
        this.httpService.doNavigate(['/home']);
    }

    public isLoggedIn(): boolean {
        return !!localStorage.getItem(environment.authTokenKey);
    }

    public getUserToken(): string {
        return localStorage.getItem(environment.authTokenKey);
    }
}
