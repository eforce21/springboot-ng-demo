import {Component, OnInit} from '@angular/core';
import {TranslateService} from "@ngx-translate/core";
import {UserService} from "./shared/user.service";
import {JwtService} from "./shared/jwt.service";

@Component({
    selector: 'app-root',
    templateUrl: './app.component.html',
    styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {

    public login: boolean;

    constructor(translate: TranslateService,
                private userService: UserService,
                private jwtService: JwtService) {
        translate.setDefaultLang('de');
        translate.use('de');
    }

    public ngOnInit(): void {
        this.login = false;
    }

    public toggleLogin() {
        this.login = !this.login;
    }

    public userLogout() {
        this.userService.doLogout();
    }

    public loggedInUser() {
        return this.userService.isLoggedIn() && !this.jwtService.isTokenExpired(this.userService.getUserToken());
    }
}
