import { Component, OnInit } from '@angular/core';
import { MatIconRegistry } from '@angular/material/icon';
import { DomSanitizer } from '@angular/platform-browser';

@Component({
  selector: 'app-user-dashboard',
  templateUrl: './user-dashboard.component.html',
  styleUrls: ['./user-dashboard.component.scss'],
})
export class UserDashboardComponent implements OnInit {
  constructor(iconRegistry: MatIconRegistry, domSanitizer: DomSanitizer) {
    for (const tab of this.tabs) {
      iconRegistry.addSvgIcon(
        tab['icon'],
        domSanitizer.bypassSecurityTrustResourceUrl(`assets/${tab['icon']}.svg`)
      );
    }
  }
  tabs: any[] = [
    {
      link: '/user/profiles',
      name: 'Profile',
      icon: 'user_profile_icon',
    },
  ];
  ngOnInit() {}
}
