import { Injectable } from '@angular/core';
import { HttpClient, HttpParams, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { City } from '../../models/city';
import { WeatherSnapshot } from '../../models/weather-snapshot';
import { AuthService } from '@auth0/auth0-angular';
import { from, switchMap } from 'rxjs';
import { AuthConfig } from '@auth0/auth0-angular';

@Injectable({ providedIn: 'root' })
export class WeatherService {
  private api = 'http://localhost:8080/api'; // Upewnij się, że port i host się zgadzają

  constructor(private http: HttpClient, private auth: AuthService) {}

  getCities(): Observable<City[]> {
    // 🔧 ZMIANA: poprawiona ścieżka do endpointu miast
    return this.http.get<City[]>(`${this.api}/public/cities`);
  }

  getCurrentWeather(cityId: number): Observable<WeatherSnapshot> {
    console.log("CurrentWeather", `${this.api}/public/weather/current/byId`);
      
    return this.http.get<WeatherSnapshot>(`${this.api}/public/weather/current/byId`, {
      params: new HttpParams().set('cityId', cityId.toString())
    });
  }

  getHistoryRange(cityId: number, startDate: string, endDate: string): Observable<WeatherSnapshot[]> {
    const params = new HttpParams()
      .set('cityId', cityId.toString())
      .set('startDate', startDate)
      .set('endDate', endDate);

    return this.http.get<WeatherSnapshot[]>(`${this.api}/public/weather/history/range`, { params });
  }

  getHistoryLastDays(cityId: number, days: number): Observable<WeatherSnapshot[]> {
    const params = new HttpParams()
      .set('cityId', cityId.toString())
      .set('days', days.toString());

    return this.http.get<WeatherSnapshot[]>(`${this.api}/public/weather/history/lastDays`, { params });
  }

  getFutureWeather(cityId: number, days: number): Observable<WeatherSnapshot[]> {
    const params = new HttpParams()
      .set('cityId', cityId.toString())
      .set('days', days.toString());

    return this.http.get<WeatherSnapshot[]>(`${this.api}/public/weather/futureWeather`, { params });
  }

  addWeatherSnapshot(data: any): Observable<any> {
    return this.auth.getAccessTokenSilently().pipe(
      switchMap(token => {
        console.log('🛡️ Token wysyłany:', token); // <-- to dodaj, żeby zobaczyć token w konsoli

        const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);
        return this.http.post('http://localhost:8080/api/admin/weather/add', data, { headers });
      })
    );
  }
  
}
