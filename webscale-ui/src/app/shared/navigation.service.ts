import {Injectable} from '@angular/core';
import {NavigationExtras, Router} from "@angular/router";


@Injectable()
export class NavigationService {

    constructor(private router: Router) {
    }

    doNavigate(route: string[], navigationExtras?: NavigationExtras) {
        this.router.navigate(route, navigationExtras);
    }
}
