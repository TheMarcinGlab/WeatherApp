import { Component, OnInit } from '@angular/core';
import { WeatherService } from '../../service/weather/weather.service';
import { City } from '../../models/city';
import { WeatherSnapshot } from '../../models/weather-snapshot';
import { AuthConfig } from '@auth0/auth0-angular';
import { StatusLoginService} from 'src/app/service/statusLogin/status-login.service';
import { AuthService } from '@auth0/auth0-angular';
@Component({
  selector: 'app-weather',
  templateUrl: './weather.component.html',
  styleUrls: ['./weather.component.css']
})
export class WeatherComponent implements OnInit {
  cities: City[] = [];
  selectedCityId: number | null = 1;
  current: WeatherSnapshot | null = null;
  history: WeatherSnapshot[] = [];
  rangeStart = '';
  rangeEnd = '';
  longTermDays: number = 1;
  loading = false;
  error = '';
  historyTitle: string = '';
  maxDays: number = 7;
  visibleTable:boolean = false;
  

  constructor(
    private svc: WeatherService,
    private auth: AuthService,
    public statusLogin: StatusLoginService
  ) {}


  ngOnInit(): void {
    this.svc.getCities().subscribe(
      data => this.cities = data,
      () => this.error = 'Nie udało się pobrać listy miast'
    );

  

    this.auth.isAuthenticated$.subscribe(auth => {
      this.statusLogin.isLoginIn = auth;
      this.maxDays = auth ? 14 : 7;
    });
    
  }

  onCityChange(): void {
    console.log('[onCityChange] selectedCityId:', this.selectedCityId, typeof this.selectedCityId);

    if (this.selectedCityId === null || isNaN(this.selectedCityId)) {
      console.warn('[onCityChange] Brak ID miasta po wyborze');
      return;
    }

    this.loading = true;
    this.error = '';
    this.history = [];

    this.svc.getCurrentWeather(this.selectedCityId).subscribe(
      data => {
        console.log('[getCurrentWeather] Dane:', data);
        this.current = data;
        this.loading = false;
      },
      err => {
        console.error('[getCurrentWeather] Błąd:', err);
        this.error = 'Błąd podczas pobierania aktualnej pogody';
        this.loading = false;
      }
    );
  }

  showRangeHistory(): void {
    console.log('Wchodzimy do funkcji showRangeHistory');
    console.log(this.rangeStart, this.rangeEnd);

    if (this.selectedCityId === null || !this.rangeStart || !this.rangeEnd) {
      console.warn('Brak wymaganych danych do wysłania requesta');
      return;
    }

    this.historyTitle = "Historia pogody"
    this.loading = true;
    this.error = '';

    console.log(`[HTTP] Wysyłam zapytanie: cityId=${this.selectedCityId}, start=${this.rangeStart}, end=${this.rangeEnd}`);

    this.svc.getHistoryRange(this.selectedCityId, this.rangeStart, this.rangeEnd)
      .subscribe({
        next: data => {
          this.history = data;
          console.log('[HTTP] Historia odebrana:', data);
          this.loading = false;
          this.generateJSON(this.history);
        },
        error: err => {
          console.error('[HTTP] Błąd:', err);
          this.error = 'Błąd podczas pobierania historii';
          this.loading = false;
        }
      });
  }

  showLongTerm(): void {
    if (this.selectedCityId === null) {
      console.warn('[showLongTerm] Brak ID miasta');
      return;
    }

    this.loading = true;
    this.error = '';
    this.historyTitle = "Prognoza pogody na " + this.longTermDays + " dni";

    this.svc.getFutureWeather(this.selectedCityId, this.longTermDays)
      .subscribe(
        data => { 
          this.history = data; 
          this.loading = false;
          this.generateJSON(this.history);
        },
        err => {
          console.error('[showLongTerm] Błąd:', err);
          this.error = 'Błąd podczas pobierania długoterminowej historii';
          this.loading = false;
        }
      );
  }


  generateJSON(data: WeatherSnapshot[]): void {
    if (!data || data.length === 0) {
      console.warn('Brak danych do zapisania jako JSON');
      return;
    }
  
    const filename = `weather-history-${new Date().toISOString()}.json`;
    const json = JSON.stringify(data, null, 2);
    const blob = new Blob([json], { type: 'application/json' });
    const url = URL.createObjectURL(blob);
  
    const a = document.createElement('a');
    a.href = url;
    a.download = filename;
    a.click();
    URL.revokeObjectURL(url);
  }
  

}
