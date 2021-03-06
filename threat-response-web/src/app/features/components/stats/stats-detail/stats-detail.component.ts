import { Component, OnInit } from '@angular/core';
import {HttpService} from "../../../../shared/services/http.service";
import {Table} from "primeng/table";

@Component({
  selector: 'app-stats-detail',
  templateUrl: './stats-detail.component.html',
  styleUrls: ['./stats-detail.component.scss']
})
export class StatsDetailComponent implements OnInit {

  detectionList: DetectionEvent[];
  events2: any[];
  thresholdConfig: {};

  constructor(
    private httpService: HttpService
  ) {
    this.detectionList= [];
    this.httpService.get('detection-list').subscribe( result => {
      this.detectionList = result;
    });

    this.events2 = [
      "Receive email", "Rule id #829 detected on email #39876", "Block DNS on Corp. firewall", "Remove phishing link from email"
    ];

    this.thresholdConfig = {
      '0': {color: 'green'},
      '5': {color: 'orange'},
      '9': {color: 'red'}
    };
  }

  ngOnInit(): void {
  }

  clear(table: Table) {
    table.clear();
  }

}

interface DetectionEvent {
  id: string;
  to: string;
  from: string;
  level: string;
  category: string;
  status: string;
  detectionDate: Date;
}
