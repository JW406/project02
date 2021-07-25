import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-user-profile',
  templateUrl: './user-profile.component.html',
  styleUrls: ['./user-profile.component.scss'],
})
export class UserProfileComponent implements OnInit {
  constructor(private http: HttpClient) {}

  data = '';

  ngOnInit(): void {
    this.http.get('/api/foo').subscribe((d: any) => {
      this.data = d;
    });
  }
}
