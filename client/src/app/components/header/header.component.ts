import { HttpClient } from '@angular/common/http';
import { Component, Input, OnInit } from '@angular/core';
import { CartService } from 'src/app/services/cart/cart.service';
import { LoadingSpinnerService } from 'src/app/services/loading-spinner/loading-spinner.service';
import { MessageBoxService } from 'src/app/services/message-box/message-box.service';
import { NotificationService } from 'src/app/services/notification/notification.service';
import { PokemonService } from 'src/app/services/pokemon/pokemon.service';
import { UserManagementService } from 'src/app/services/user-management/user-management.service';
import { WeatherService } from 'src/app/services/weather/weather.service';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.scss'],
})
export class HeaderComponent implements OnInit {
  @Input() openDialog!: () => void;

  currentCoins = 0;
  constructor(
    public um: UserManagementService,
    public notificationService: NotificationService,
    private http: HttpClient,
    public cs: CartService,
    private mb: MessageBoxService,
    private ps: PokemonService,
    private ls: LoadingSpinnerService
  ) {}

  ngOnInit() {}

  async refreshToken() {
    const res = await this.ps.getCurrentPokeToken();
    this.currentCoins = res['pokeToken'];
  }

  onCartBtnClick() {
    this.refreshToken();
  }

  checkout(e: Event) {
    e.stopPropagation();
    this.ls.isWait = true;
    this.http
      .post(
        '/api/shop/make-transaction',
        {
          txAmnt: this.cs.total,
        },
        {
          headers: {
            'Content-Type': 'application/json',
          },
        }
      )
      .subscribe((d: any) => {
        this.ls.isWait = false;
        if (d['isSuccess']) {
          this.refreshToken();
          this.notificationService.listOfNotifications = [
            {
              body: [
                'Congratulations, you have purchased',
                ` some Pokémon for ${this.cs.total} Poké Token`,
              ],
              title: 'Success',
              date: Date.now(),
              isRead: false,
            },
            ...this.notificationService.listOfNotifications,
          ];
          this.cs.emptyCart();
          this.mb.show(
            'Trancaction success, you may download the Pokémon now.'
          );
        } else {
          this.mb.show('Trancaction failure, please try again.');
        }
      });
  }
}
