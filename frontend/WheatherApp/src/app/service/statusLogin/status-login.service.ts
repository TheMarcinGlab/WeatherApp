import { Injectable } from '@angular/core';
import { AuthService } from '@auth0/auth0-angular';

@Injectable({
  providedIn: 'root'
})
export class StatusLoginService {
  isLoginIn: boolean = false;

  constructor(public authService: AuthService) {
    this.authService.isAuthenticated$.subscribe((status: boolean) =>{
      this.isLoginIn = status
    }
  );
    
   }

   login(): void{
    this.authService.loginWithRedirect();
   }

   logout(): void{
    this.authService.logout({ logoutParams: { returnTo: window.location.origin } });
  }


}
