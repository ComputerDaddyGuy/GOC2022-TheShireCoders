import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-charts',
  templateUrl: './charts.component.html',
  styleUrls: ['./charts.component.scss']
})
export class ChartsComponent implements OnInit {

  data: any;
  dataDomain:any;
  chartOptions: any;

  basicData: any;
  basicOptions: any;

  constructor() { }

  ngOnInit(): void {

    this.data = {
      datasets: [{
        data: [
          89,
          12,
          56,
          23
        ],
        backgroundColor: [
          "#42A5F5",
          "#66BB6A",
          "#FFA726",
          "#26C6DA",
          "#7E57C2"
        ],
        label: 'My dataset'
      }],
      labels: [
        "@phis.com",
        "@2312312.lu",
        "@yopmail.com",
        "@gmail.com"
      ]
    };

    this.dataDomain = {
      datasets: [{
        data: [
          21,
          16,
          7,
          3,
          14
        ],
        backgroundColor: [
          "#42A5F5",
          "#66BB6A",
          "#FFA726",
          "#26C6DA",
          "#7E57C2"
        ],
        label: 'My dataset'
      }],
      labels: [
        "Finance",
        "Telecom",
        "Administration",
        "Gaming",
        "Others"
      ]
    };

    this.basicData = {
      labels: ['2022/01', '2022/02', '2022/03', '2022/04', '2022/05', '2022/06'],
      datasets: [
        {
          label: 'Phishing Detection',
          data: [33, 76, 98, 34, 24, 65, 40],
          fill: false,
          borderColor: '#c02a2a',
          tension: .4
        },
        {
          label: 'Deleted Mails',
          data: [65, 59, 80, 81, 56, 55, 40],
          fill: false,
          borderColor: '#42A5F5',
          tension: .4
        },
        {
          label: 'Email Live Notification',
          data: [98, 54, 123, 56, 43, 87, 23],
          fill: false,
          borderColor: '#42f596',
          tension: .4
        },
        {
          label: 'New Rules',
          data: [10, 21, 7, 19, 12, 8, 30],
          fill: false,
          borderColor: '#FFA726',
          tension: .4
        }
      ]
    };
  }

}
