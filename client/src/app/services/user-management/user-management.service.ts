import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { CartService } from '../cart/cart.service';
import { WeatherService } from '../weather/weather.service';

@Injectable()
export class UserManagementService {
  loggedInUser = {
    username: '',
  };

  userLocation = {
    name: '',
    region: '',
    temperatureF: 0,
  };

  constructor(private http: HttpClient, private ws: WeatherService) {}

  initSync = async () => {
    let OAuthUserInfo: string | null = '';
    if ((OAuthUserInfo = localStorage.getItem('userInfo'))) {
      const userObj = JSON.parse(OAuthUserInfo);
      const authType = localStorage.getItem('authType');
      if (authType === 'google') {
        this.setUsername(userObj.name);
      } else if (authType === 'github') {
        this.setUsername(userObj.login);
      }
      const userInfo = await this.getUserInfo();
      const res = await this.ws.getCurrentWeatherByZipCode(userInfo['zipCode']);
      this.userLocation.temperatureF = res.current.temp_f;
      this.userLocation.name = res.location.name;
      this.userLocation.region = res.location.region;
    }
  };

  getUserInfo() {
    return this.http.get('/api/get-user-info').toPromise() as Promise<any>;
  }

  setUsername = (name: string) => {
    this.loggedInUser.username = name;
  };

  logout = () => {
    localStorage.removeItem('userInfo');
    localStorage.removeItem('access_token');
    localStorage.removeItem('authType');
    localStorage.removeItem('jwtToken');
    localStorage.removeItem('notificaitonList');
    localStorage.removeItem(CartService.CARTKEY);
    this.setUsername('');
    window.location.href = '/';
  };
}
