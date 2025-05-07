import { Injectable, NgZone } from '@angular/core';
import { AuthService } from '@auth0/auth0-angular';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class StatusLoginService {
  isLoginIn: boolean = false;

  constructor(
    private authService: AuthService,
    private ngZone: NgZone,
    private http: HttpClient
  ) {
    this.authService.isAuthenticated$.subscribe((isAuthenticated: boolean) => {
      this.ngZone.run(() => {
        this.isLoginIn = isAuthenticated;

        if (isAuthenticated) {
          this.handleAuthenticatedUser();
        } else {
          this.clearUserData();
        }
      });
    });
  }

  private handleAuthenticatedUser(): void {
    this.authService.idTokenClaims$.subscribe({
      next: (claims: any) => {
        console.log('🧾 Pełne dane z id_token:', claims);

        const email = claims?.email;

        if (!email) {
          console.error('❌ Email not found in id_token claims!');
          return;
        }

        localStorage.setItem('user_email', email);
        this.sendUserToDatabase(email);
      },
      error: (err) => {
        console.error('❌ Błąd podczas pobierania id_token claims', err);
      }
    });
  }

  private sendUserToDatabase(email: string): void {
    const body = { email, role: 'ROLE_USER' };
  
    const token = localStorage.getItem('access_token');
  
    const options = token
      ? { headers: { Authorization: `Bearer ${token}` } }
      : {}; // nie przekazuj nagłówków, jeśli brak tokena
  
    this.http.post('http://localhost:8080/api/public/createNewUser', body, options)
      .subscribe({
        next: () => console.log('✅ Użytkownik dodany do bazy.'),
        error: (err) => {
          if (err.status === 409) {
            console.warn('ℹ️ Użytkownik już istnieje.');
          } else if (err.status === 401) {
            console.error('❌ Brak autoryzacji – nieprawidłowy lub brakujący token JWT.');
          } else {
            console.error('❌ Błąd przy dodawaniu użytkownika', err);
          }
        }
      });
  }
  
  
  

  login(): void {
    this.authService.loginWithRedirect({
      authorizationParams: {
        scope: 'openid profile email'
      }
    });
  }

  logout(): void {
    this.authService.logout({ logoutParams: { returnTo: window.location.origin } });
    this.clearUserData();
  }

  private clearUserData(): void {
    localStorage.removeItem('user_email');
  }
}
