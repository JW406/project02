import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { LoadingSpinnerService } from 'src/app/services/loading-spinner/loading-spinner.service';
import { MessageBoxService } from 'src/app/services/message-box/message-box.service';
import { NotificationService } from 'src/app/services/notification/notification.service';

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
  model: Record<string, any> = {
    amount: 100, // $1 each
    quantities: undefined,
    itemName: 'Poke Coins',
  };
  currentCoins?: number = undefined;

  constructor(
    private http: HttpClient,
    private mb: MessageBoxService,
    private notificationService: NotificationService,
    private ls: LoadingSpinnerService
  ) {}

  ngOnInit(): void {
    this.http.get('/api/user/tokens').subscribe((d: any) => {
      this.currentCoins = d['pokeToken'];
    });
  }

  checkout = async () => {
    const temp = document.createElement('form');
    temp.action = '/pay/create-checkout-session';
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
    this.ls.isWait = true;
    // Polling for payment to be finished, or cancelled
    setInterval(() => {
      const flag = window.localStorage.getItem('__flag');
      if (flag) {
        document.body.removeChild(temp);
        window.localStorage.removeItem('__flag');
        if (flag === 'true') {
          this.mb.show('Success');
          const amount = this.model['amount'] as number;
          const quantities = this.model['quantities'] as number;
          this.notificationService.listOfNotifications = this.notificationService.listOfNotifications.concat(
            {
              title: 'Transaction complete',
              body: [
                `You have purchased ${this.model['quantities']} Poké Tokens`,
                `for \$${(amount * quantities) / 100}`,
              ],
              date: new Date().getTime(),
              isRead: false,
            }
          );
          this.currentCoins! += this.model.quantities as number;
          this.reset();
        } else if (flag === 'false') {
          this.mb.show('Failed');
        }
        this.ls.isWait = false;
      }
    }, 500);
  };

  reset = () => {
    this.model.quantities = undefined;
  };
}
