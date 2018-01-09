import {NgModule} from '@angular/core';
import {RouterModule, Routes} from "@angular/router";
import {ImagesDisplayComponent} from "./images-display/images-display.component";
import {AuthGuard} from "../core/guards/auth.guard";

const imagesRoutes: Routes = [
    {
        path: 'images',
        component: ImagesDisplayComponent,
        canActivate: [
            AuthGuard
        ]
    }
];

@NgModule({
    imports: [
        RouterModule.forChild(imagesRoutes)
    ],
})
export class ImagesRoutingModule {
}
