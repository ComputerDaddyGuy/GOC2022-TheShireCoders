import { Component, OnInit } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {ActivatedRoute, Router} from "@angular/router";
import {HttpService} from "../../../shared/services/http.service";

@Component({
  selector: 'app-stats',
  templateUrl: './stats.component.html',
  styleUrls: ['./stats.component.scss']
})
export class StatsComponent implements OnInit {

  single: any[] | undefined;

  constructor(
    private httpService: HttpService,
    private router: Router
  ) { }

  ngOnInit(): void {
    this.httpService.get('threat-datas').subscribe(result => {
      this.single = result as any[];

      for (let data of this.single){
        data.computedValue = data.value - Math.floor(Math.random() * 101);
        let currentIntervalStop = setInterval(() => {
          data.computedValue++;
          if (data.computedValue === data.value) {
            clearInterval(currentIntervalStop);
          }
        }, 10);
      }
    });
  }

  openView(type: string) {
    this.router.navigate(['dashboard/view/' + type]);
  }
}
