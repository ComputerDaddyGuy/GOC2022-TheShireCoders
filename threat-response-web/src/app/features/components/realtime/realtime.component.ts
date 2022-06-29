import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-realtime',
  templateUrl: './realtime.component.html',
  styleUrls: ['./realtime.component.scss']
})
export class RealtimeComponent implements OnInit {

  options: any;
  updateOptions: any;

  private oneMinute =  60 * 1000;
  private now = new Date();
  private beginDate = new Date();
  private value = Math.random() * 1000;
  private data: any[] = [];
  private timer: any;

  constructor() {
    this.beginDate.setHours(this.now.getHours() - 12);
  }

  ngOnInit(): void {

    for (let i = 0; i < (12 * 60); i++) {
      this.data.push(this.randomData());
    }
    console.log(this.data);

    // initialize chart options:
    this.options = {
      title: {
        text: 'RealTime Phishing Detection',
      },
      tooltip: {
        trigger: 'axis',
        formatter: (params: any) => {
          params = params[0];
          const date = new Date(params.name);
          return date.getHours() + ':' + this.getformattedMinutes(date.getMinutes()) + ' [ ' + params.value[1] + ' ] ';
        },
        axisPointer: {
          animation: false
        }
      },
      xAxis: {
        formatter: (params: any) => {
          params = params[0];
          const date = new Date(params.name);
          return date.getDate() + '/' + (date.getMonth() + 1) + '/' + date.getFullYear() + ' : ' + params.value[1];
        },
        type: 'time',
        splitLine: {
          show: true
        }
      },
      yAxis: {
        type: 'value',
        boundaryGap: [0, '100%'],
        splitLine: {
          show: false
        }
      },
      series: [{
        name: 'Mocking Data',
        type: 'line',
        showSymbol: true,
        hoverAnimation: true,
        data: this.data
      }]
    };

    // Mock dynamic data:
    this.timer = setInterval(() => {
      this.data.shift();
      this.data.push(this.randomData());

      // update series data:
      this.updateOptions = {
        series: [{
          data: this.data
        }]
      };
    }, this.oneMinute);
  }

  ngOnDestroy() {
    clearInterval(this.timer);
  }

  randomData() {
    this.beginDate = new Date(this.beginDate.getTime() + this.oneMinute);
    this.value = this.value + Math.random() * 21 - 10;
    return {
      name: this.beginDate.toString(),
      value: [
        [this.beginDate.getFullYear(), this.beginDate.getMonth(), this.beginDate.getDate()].join('/') + ' ' + [this.beginDate.getHours(), this.getformattedMinutes(this.beginDate.getMinutes())].join(':'),
        Math.round(this.value)
      ]
    };
  }

  getformattedMinutes(minutes: number): string {
    return (minutes<10?'0':'') + minutes;
  }


}
