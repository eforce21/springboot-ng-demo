import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {AdmininstrationMainComponent} from "./admininstration-main/admininstration-main.component";
import {CategoriesComponent} from "./categories/categories.component";
import {MatTableModule} from "@angular/material";
import {TranslateModule} from "@ngx-translate/core";
import {FormsModule} from "@angular/forms";
import {SharedModule} from "../shared/shared.module";
import {ImageUploadComponent} from "./image-upload/image-upload.component";

@NgModule({
    imports: [
        CommonModule,
        SharedModule,
        MatTableModule,
        TranslateModule,
        FormsModule
    ],
    declarations: [
        AdmininstrationMainComponent,
        CategoriesComponent,
        ImageUploadComponent
    ],
    providers: [
    ]
})
export class AdministrationModule {
}
