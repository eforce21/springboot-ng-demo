import {Injectable} from '@angular/core';
import {MatSnackBar} from "@angular/material";
import {TranslateService} from "@ngx-translate/core";

@Injectable()
export class MessageService {

    private readonly BASE_MESSAGE_KEY: string = 'messages';

    constructor(private snackBar: MatSnackBar,
                private translateService: TranslateService) {
    }

    displayMessage(messageKey: string) {
        this.translateService.get(this.BASE_MESSAGE_KEY + '.' + messageKey)
            .subscribe(message => this.snackBar.open(message, null, {duration: 2000}));
    }
}
