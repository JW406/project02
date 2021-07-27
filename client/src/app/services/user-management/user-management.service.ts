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

  parseJWT(jwt?: string | null) {
    const seg = jwt?.split('.')[1];
    if (seg) {
      return JSON.parse(
        decodeURIComponent(
          escape(window.atob(seg.replace(/-/g, '+').replace(/_/g, '/')))
        )
      );
    } else {
      return {};
    }
  }

  initSync = async () => {
    if (localStorage.getItem('jwtToken')) {
      const userInfo = await this.getUserInfo();
      this.setUsername(userInfo['name']);
      const res = await this.ws.getCurrentWeatherByZipCode(userInfo['zipCode']);
      this.userLocation.temperatureF = res.current.temp_f;
      this.userLocation.name = res.location.name;
      this.userLocation.region = res.location.region;
    }
  };

  getUserInfo() {
    return this.http.get('/api/get-user-info').toPromise() as Promise<{
      email: string;
      name: string;
      zipCode: string;
      pokeToken: number;
    }>;
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
