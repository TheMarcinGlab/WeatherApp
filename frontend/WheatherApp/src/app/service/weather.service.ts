import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { City } from '../models/city';
import { WeatherSnapshot } from '../models/weather-snapshot';
import { environment } from '../../../environment';

@Injectable({ providedIn: 'root' })
export class WeatherService {
  //private api = environment.apiUrl;
  private api = 'http://localhost:8080/api'
  constructor(private http: HttpClient) {}

  getCities(): Observable<City[]> {
    return this.http.get<City[]>(`${this.api}/cities`);
  }

  getCurrentWeather(cityId: number): Observable<WeatherSnapshot> {
    return this.http.get<WeatherSnapshot>(`${this.api}/weather/current/byId`, {
      params: new HttpParams().set('cityId', cityId.toString())
    });
  }

  getHistoryRange(cityId: number, startDate: string, endDate: string): Observable<WeatherSnapshot[]> {
    let params = new HttpParams()
      .set('cityId', cityId.toString())
      .set('startDate', startDate)
      .set('endDate', endDate);
    return this.http.get<WeatherSnapshot[]>(`${this.api}/weather/history/range`, { params });
  }

  getHistoryLastDays(cityId: number, days: number): Observable<WeatherSnapshot[]> {
    return this.http.get<WeatherSnapshot[]>(`${this.api}/weather/history/lastDays`, {
      params: new HttpParams()
        .set('cityId', cityId.toString())
        .set('days', days.toString())
    });
  }
}
