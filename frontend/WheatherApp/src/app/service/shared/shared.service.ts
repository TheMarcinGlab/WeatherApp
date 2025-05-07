import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';

@Injectable({ providedIn: 'root' })
export class SharedService {
  private showAddFormSubject = new BehaviorSubject<boolean>(false);
  showAddForm$ = this.showAddFormSubject.asObservable();

  showForm(): void {
    this.showAddFormSubject.next(true);
  }

  hideForm(): void {
    this.showAddFormSubject.next(false);
  }
}
