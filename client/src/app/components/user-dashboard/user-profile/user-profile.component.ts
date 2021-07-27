import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { MessageBoxService } from 'src/app/services/message-box/message-box.service';
import { UserManagementService } from 'src/app/services/user-management/user-management.service';

@Component({
  selector: 'app-user-profile',
  templateUrl: './user-profile.component.html',
  styleUrls: ['./user-profile.component.scss'],
})
export class UserProfileComponent implements OnInit {
  constructor(
    private http: HttpClient,
    private mb: MessageBoxService,
    private um: UserManagementService
  ) {}

  name = '';
  zipCode = '';

  async ngOnInit() {
    const res = await this.um.getUserInfo();
    this.name = res.name;
    this.zipCode = res.zipCode;
  }

  reset() {
    this.name = '';
    this.zipCode = '';
  }

  submit(e: Event) {
    e.preventDefault();
    this.http
      .patch('/api/update-user-info', {
        name: this.name,
        zipCode: this.zipCode,
      })
      .subscribe((d: any) => {
        if (d['isSuccess']) {
          this.um.initSync();
          this.mb.show('User Information update successful');
        }
      });
  }
}
