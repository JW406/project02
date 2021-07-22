import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { MatDialog, MatDialogRef } from '@angular/material/dialog';
import { MessageBoxService } from 'src/app/services/message-box/message-box.service';

export interface MatDialogData {
  closeDialog: () => void;
  quantities?: number | string;
}

@Component({
  selector: 'app-payment',
  templateUrl: './payment.component.html',
  styleUrls: ['./payment.component.scss'],
})
export class PaymentComponent implements OnInit {
  model: Record<any, string | number | undefined> = {
    amount: 100, // $1 each
    quantities: undefined,
    itemName: 'Poke Coins',
  }
  isWait = false;

  constructor(private dialog: MatDialog, private http: HttpClient, private mb: MessageBoxService) {}

  ngOnInit(): void {
  }

  checkout = async (url: string) => {
    const temp = document.createElement('form');
    temp.action = url;
    temp.method = 'post';
    temp.target = '_blank';
    temp.style.display = 'none';
    for (const p in this.model) {
      const opt = document.createElement('input');
      opt.type = 'text';
      opt.name = p;
      opt.value = this.model[p] as any;
      temp.appendChild(opt);
    }
    document.body.appendChild(temp);
    temp.submit();
    this.isWait = true;
    setInterval(() => {
      const flag = window.localStorage.getItem('__flag');
      if (flag) {
        window.localStorage.removeItem('__flag');
        if (flag === 'true') {
          this.mb.show('Success')
        } else if (flag === 'false') {
          this.mb.show('Failed')
        }
        this.isWait = false;
      }
    }, 500);
  };
}
