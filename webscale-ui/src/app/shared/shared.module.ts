import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {NavigationService} from "./navigation.service";
import {UserService} from "./user.service";
import {JwtService} from "./jwt.service";
import {CategoryService} from "./category.service";
import {MessageService} from "./message.service";
import {MatSnackBarModule} from "@angular/material";
import {ImagesService} from "./images.service";
import {FileService} from "./file.service";

@NgModule({
    imports: [
        CommonModule,
        MatSnackBarModule
    ],
    providers: [
        NavigationService,
        UserService,
        JwtService,
        CategoryService,
        MessageService,
        ImagesService,
        FileService
    ]
})
export class SharedModule {
}
