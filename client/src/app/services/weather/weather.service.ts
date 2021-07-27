import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment as env } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class WeatherService {
  constructor(private http: HttpClient) { }
  getCurrentWeatherByZipCode(zipCode: number | string) {
    return this.http.get(env.weatherApiEndpoint + zipCode).toPromise() as Promise<any>
  }
}
