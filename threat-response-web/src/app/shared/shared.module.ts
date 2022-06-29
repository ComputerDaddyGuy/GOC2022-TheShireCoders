import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {NotFoundComponent} from './components/not-found/not-found.component';
import {ContainerComponent} from './components/container/container.component';
import {RouterModule} from "@angular/router";
import {SelectButtonModule} from "primeng/selectbutton";
import {MenuModule} from "primeng/menu";


@NgModule({
  declarations: [
    NotFoundComponent,
    ContainerComponent
  ],
  exports: [
    NotFoundComponent
  ],
  imports: [
    CommonModule,
    RouterModule,
    SelectButtonModule,
    MenuModule
  ]
})
export class SharedModule {
}
