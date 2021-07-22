import { Component, Input, OnInit } from '@angular/core';
import { NotificationService } from 'src/app/services/notification/notification.service';
import { UserManagementService } from 'src/app/services/user-management/user-management.service';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.scss']
})
export class HeaderComponent implements OnInit {

  @Input() openDialog!: () => void

  constructor(public um: UserManagementService, public notificationService: NotificationService) { }

  ngOnInit(): void {
  }
}
