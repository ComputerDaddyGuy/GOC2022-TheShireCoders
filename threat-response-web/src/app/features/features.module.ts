import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {DashboardComponent} from './components/dashboard/dashboard.component';
import {StatsComponent} from './components/stats/stats.component';
import {NgxEchartsModule} from "ngx-echarts";
import {RealtimeComponent} from './components/realtime/realtime.component';
import {CardModule} from "primeng/card";
import {StatsDetailComponent} from './components/stats/stats-detail/stats-detail.component';
import {TableModule} from "primeng/table";
import {ButtonModule} from "primeng/button";
import { ChartsComponent } from './components/stats/charts/charts.component';
import {ChartModule} from "primeng/chart";
import {TimelineModule} from "primeng/timeline";
import {NgxGaugeModule} from "ngx-gauge";


@NgModule({
  declarations: [
    DashboardComponent,
    StatsComponent,
    RealtimeComponent,
    StatsDetailComponent,
    ChartsComponent
  ],
  imports: [
    CommonModule,
    NgxEchartsModule.forRoot({
      echarts: () => import('echarts')
    }),
    CardModule,
    TableModule,
    ButtonModule,
    ChartModule,
    TimelineModule,
    NgxGaugeModule
  ]
})
export class FeaturesModule { }
