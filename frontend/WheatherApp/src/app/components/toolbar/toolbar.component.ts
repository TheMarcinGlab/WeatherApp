import { Component } from '@angular/core';
import { StatusLoginService } from 'src/app/service/statusLogin/status-login.service';
@Component({
  selector: 'app-toolbar',
  templateUrl: './toolbar.component.html',
  styleUrls: ['./toolbar.component.css']
})
export class ToolbarComponent {

  constructor(public statusLogin: StatusLoginService){
    
  }


  addData(){

  }

  toggleLogin(){
    
  }

}
