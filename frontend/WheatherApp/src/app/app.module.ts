import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { FormsModule } from '@angular/forms';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { AppComponent } from './app.component';
import { WeatherComponent } from './components/weather/weather.component';
import { ToolbarComponent } from './components/toolbar/toolbar.component';
import { AuthInterceptor } from './auth.interceptor';
import { AuthModule } from '@auth0/auth0-angular';
import { MenuAddToDatabaseComponent } from './components/menu-add-to-database/menu-add-to-database.component';

@NgModule({
  declarations: [
    AppComponent,
    WeatherComponent,
    ToolbarComponent,
    MenuAddToDatabaseComponent
  ],
  imports: [
    BrowserModule,
    FormsModule,
    HttpClientModule,
    AuthModule.forRoot({
      domain: 'dev-5fzx8o0336ybfbaq.us.auth0.com', // ← ZMIEŃ NA SWOJE
      clientId: 'UJDuHyg7fLKwDdinqhclvzFu3pO5jITW',
      authorizationParams: {
        redirect_uri: window.location.origin,
        audience: 'https://weather-api.theglab',  // <-- dokładnie to co w Auth0 API!
      }
    
    })
  ],
  providers: [
    { provide: HTTP_INTERCEPTORS, useClass: AuthInterceptor, multi: true }
  ],
  bootstrap: [AppComponent]
})
export class AppModule {}
