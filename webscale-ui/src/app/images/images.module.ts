import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {ImagesDisplayComponent} from "./images-display/images-display.component";
import {ImagesService} from "../shared/images.service";
import {ImageThumbnailComponent} from "./image-thumbnail/image-thumbnail.component";
import {InfiniteScrollModule} from "ngx-infinite-scroll";
import {TranslateModule} from "@ngx-translate/core";
import {FormsModule} from "@angular/forms";
import {MatChipsModule, MatSelectModule} from "@angular/material";
import {BrowserAnimationsModule} from "@angular/platform-browser/animations";
import {ImagePreviewComponent} from "./image-preview/image-preview.component";

@NgModule({
    imports: [
        CommonModule,
        InfiniteScrollModule,
        TranslateModule,
        FormsModule,
        BrowserAnimationsModule,
        MatSelectModule,
        MatChipsModule
    ],
    declarations: [
        ImagesDisplayComponent,
        ImageThumbnailComponent,
        ImagePreviewComponent
    ]
})
export class ImagesModule {
}
