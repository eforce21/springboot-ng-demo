import {HttpData} from "../../shared/http-data.interface";

export class Authentication implements HttpData {

    private _username: string;
    private _password: string;

    get username(): string {
        return this._username;
    }

    set username(value: string) {
        this._username = value;
    }

    get password(): string {
        return this._password;
    }

    set password(value: string) {
        this._password = value;
    }

    toPayload(): string {
        return 'username=' + this._username + '&password=' + this._password;
    }
}