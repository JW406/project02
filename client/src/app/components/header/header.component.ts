import { Component, Input, OnInit } from '@angular/core';
import { CartService } from 'src/app/services/cart/cart.service';
import { NotificationService } from 'src/app/services/notification/notification.service';
import { UserManagementService } from 'src/app/services/user-management/user-management.service';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.scss'],
})
export class HeaderComponent implements OnInit {
  @Input() openDialog!: () => void;

  constructor(
    public um: UserManagementService,
    public notificationService: NotificationService,
    public cs: CartService
  ) {}

  ngOnInit(): void {}

  checkout(e: Event) {
    e.stopPropagation()
  }
}
