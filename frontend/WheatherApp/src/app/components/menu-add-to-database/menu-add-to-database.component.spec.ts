import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MenuAddToDatabaseComponent } from './menu-add-to-database.component';

describe('MenuAddToDatabaseComponent', () => {
  let component: MenuAddToDatabaseComponent;
  let fixture: ComponentFixture<MenuAddToDatabaseComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ MenuAddToDatabaseComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(MenuAddToDatabaseComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
