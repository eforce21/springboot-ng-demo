import {Component, EventEmitter, HostListener, Input, OnInit, Output} from '@angular/core';
import {UserService} from "../../shared/user.service";
import {NavigationService} from "../../shared/navigation.service";
import {HttpData} from "../../shared/http-data.interface";
import {Authentication} from "../model/authentication";

@Component({
    selector: 'wu-login',
    templateUrl: './login.component.html',
    styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

    @Input()
    public showLogin: boolean;

    @Output()
    public onCloseLogin: EventEmitter<null> = new EventEmitter();

    public authentication: HttpData;

    private readonly LANDING_PAGE: string = '/images';

    constructor(private userService: UserService,
                private httpService: NavigationService) {
    }

    public ngOnInit() {
        this.authentication = new Authentication();
    }

    public closeLogin() {
        this.onCloseLogin.emit();
    }

    public login() {
        this.userService.doLogin(this.authentication).subscribe(
            _ => {
                this.showLogin = false;
                this.httpService.doNavigate([this.LANDING_PAGE]);
            },
            error => console.log(error)
        );
    }

    @HostListener('document:keyup', ['$event'])
    private closeOnEscape(event: any) {
        if (event.key === 'Escape' && this.showLogin) {
            this.onCloseLogin.emit();
        }
    }

    @HostListener('document:keyup', ['$event'])
    private loginOnEnter(event: any) {
        if (event.key === 'Enter' && this.showLogin) {
            this.login();
        }
    }
}
