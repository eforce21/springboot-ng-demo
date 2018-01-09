import {Injectable} from '@angular/core';
import {ActivatedRouteSnapshot, CanActivate, RouterStateSnapshot} from '@angular/router';
import {Observable} from 'rxjs/Observable';
import {UserService} from "../../shared/user.service";
import {JwtService} from "../../shared/jwt.service";
import {NavigationService} from "../../shared/navigation.service";

@Injectable()
export class AuthGuard implements CanActivate {

    private readonly HOME_URL: string = '/home';

    constructor(private userService: UserService,
                private jwtService: JwtService,
                private navigationService: NavigationService) {
    }

    canActivate(next: ActivatedRouteSnapshot,
                state: RouterStateSnapshot): Observable<boolean> | Promise<boolean> | boolean {
        if (!this.userService.isLoggedIn() || this.jwtService.isTokenExpired(this.userService.getUserToken())) {
            this.navigationService.doNavigate([this.HOME_URL]);
            return false;
        }
        return true;
    }
}
