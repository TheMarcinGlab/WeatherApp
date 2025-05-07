import { Component } from '@angular/core';
import { City } from 'src/app/models/city';

import { WeatherComponent } from '../weather/weather.component';
import { WeatherService } from 'src/app/service/weather/weather.service';

import { StatusLoginService } from 'src/app/service/statusLogin/status-login.service';
@Component({
  selector: 'app-menu-add-to-database',
  templateUrl: './menu-add-to-database.component.html',
  styleUrls: ['./menu-add-to-database.component.css']
})
export class MenuAddToDatabaseComponent {

    cities: City[] = [];
    weather: any = {};
  
    constructor(
      private svc: WeatherService,
      public statusLogin: StatusLoginService
    ) {}
  
    ngOnInit(): void {
      this.svc.getCities().subscribe({
        next: data => this.cities = data,
        error: () => alert('Nie udało się pobrać listy miast')
      });
    }
  
    submitForm(): void {
      this.svc.addWeatherSnapshot(this.weather).subscribe({
        next: () => {
          alert('Dodano wpis pogodowy!');
          this.weather = {}; // wyczyść formularz
        },
        error: err => {
          console.error(err);
          alert('Błąd przy dodawaniu wpisu');
        }
      });
    }
  

}
