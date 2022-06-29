import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {DashboardComponent} from "./features/components/dashboard/dashboard.component";
import {LoginComponent} from "./login/login.component";
import {LoginGuard} from "./shared/guards/login.guard";
import {StatsDetailComponent} from "./features/components/stats/stats-detail/stats-detail.component";
import {NotFoundComponent} from "./shared/components/not-found/not-found.component";
import {ContainerComponent} from "./shared/components/container/container.component";

const routes: Routes = [
  {
    path: 'login',
    pathMatch: 'full',
    component: LoginComponent
  },
  {
    path: 'dashboard',
    component: ContainerComponent,
    canActivate: [LoginGuard],
    runGuardsAndResolvers: 'always',
    children: [
      {
        path: '',
        component: DashboardComponent
      },
      {
        path:'view/:type',
        component: StatsDetailComponent,
        canActivate: [LoginGuard]
      },
    ]
  },
  {
    path: '**',
    pathMatch:'full',
    component: NotFoundComponent,
    canActivate: [LoginGuard]
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
