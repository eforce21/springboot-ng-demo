import {NgModule} from '@angular/core';
import {RouterModule, Routes} from "@angular/router";
import {AdmininstrationMainComponent} from "./admininstration-main/admininstration-main.component";

const administrationRoutes: Routes = [
    {
        path: 'administration',
        component: AdmininstrationMainComponent
    }
];

@NgModule({
    imports: [
        RouterModule.forChild(administrationRoutes)
    ],
})
export class AdministrationRoutingModule {
}
