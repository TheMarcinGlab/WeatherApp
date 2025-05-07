import { Component, OnInit } from '@angular/core';
import { WeatherService } from '../../service/weather/weather.service';
import { City } from '../../models/city';
import { WeatherSnapshot } from '../../models/weather-snapshot';

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

  constructor(private svc: WeatherService) {}

  ngOnInit(): void {
    this.svc.getCities().subscribe(
      data => this.cities = data,
      () => this.error = 'Nie udało się pobrać listy miast'
    );
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
        data => { this.history = data; this.loading = false; },
        err => {
          console.error('[showLongTerm] Błąd:', err);
          this.error = 'Błąd podczas pobierania długoterminowej historii';
          this.loading = false;
        }
      );
  }
}
