import { Component } from '@angular/core';
import { StatusLoginService } from 'src/app/service/statusLogin/status-login.service';
import { SharedService } from 'src/app/service/shared/shared.service';
@Component({
  selector: 'app-toolbar',
  templateUrl: './toolbar.component.html',
  styleUrls: ['./toolbar.component.css']
})
export class ToolbarComponent {

 
  

  constructor(public statusLogin: StatusLoginService, private shared: SharedService){
    
  }


  addData(){
    this.shared.showForm(); // ← to przekaże sygnał do WeatherComponent

  }

  toggleLogin() {
    if (this.statusLogin.isLoginIn) {
      this.statusLogin.logout();
    } else {
      this.statusLogin.login();
    }
  }
  
}
