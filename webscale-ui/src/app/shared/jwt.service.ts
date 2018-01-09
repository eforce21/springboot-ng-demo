import {Injectable} from '@angular/core';

declare let escape: any;

@Injectable()
export class JwtService {

    public urlBase64Decode(str: string) {
        let output = str.replace(/-/g, '+').replace(/_/g, '/');
        switch (output.length % 4) {
            case 0: {
                break;
            }
            case 2: {
                output += '==';
                break;
            }
            case 3: {
                output += '=';
                break;
            }
            default: {
                throw 'Illegal base64url string!';
            }
        }

        // Polyfill https://github.com/davidchambers/Base64.js
        return decodeURIComponent(escape(typeof window === 'undefined' ? atob(output) : window.atob(output)));
    }

    public decodeToken(token: string) {
        let parts = token.split('.');

        if (parts.length !== 3) {
            throw new Error('JWT must have 3 parts');
        }

        let decoded = this.urlBase64Decode(parts[1]);
        if (!decoded) {
            throw new Error('Cannot decode the token');
        }

        return JSON.parse(decoded);
    }

    public getTokenExpirationDate(token: string) {
        let decoded: any;
        decoded = this.decodeToken(token);

        if (typeof decoded.exp === 'undefined') {
            return null;
        }

        let date = new Date(0);
        date.setUTCSeconds(decoded.exp);

        return date;
    }

    public isTokenExpired(token: string, offsetSeconds?: number) {
        let date = this.getTokenExpirationDate(token);
        offsetSeconds = offsetSeconds || 0;
        if (date === null) {
            return false;
        }
        return !(date.valueOf() > new Date().setUTCSeconds(offsetSeconds).valueOf());
    }
}
