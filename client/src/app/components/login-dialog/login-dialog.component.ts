import { Component, Inject, OnInit } from '@angular/core';
import { MAT_DIALOG_DATA } from '@angular/material/dialog';
import { MatIconRegistry } from '@angular/material/icon';
import { DomSanitizer } from '@angular/platform-browser';
import { MatDialogData } from 'src/app/app.component';
import { OAuth2Service } from 'src/app/services/oauth2/oauth2.service';
import { UserManagementService } from 'src/app/services/user-management/user-management.service';

const googleLogoURL =
  'https://raw.githubusercontent.com/fireflysemantics/logo/master/Google.svg';

const gitHubLogoURL = 'assets/github.svg';

@Component({
  selector: 'app-login-dialog',
  templateUrl: './login-dialog.component.html',
  styleUrls: ['./login-dialog.component.scss'],
})
export class LoginDialogComponent implements OnInit {
  constructor(
    private matIconRegistry: MatIconRegistry,
    private domSanitizer: DomSanitizer,
    @Inject(MAT_DIALOG_DATA)
    private parentData: MatDialogData,
    private OAuth2: OAuth2Service,
    private um: UserManagementService
  ) {
    this.matIconRegistry.addSvgIcon(
      'google-logo',
      this.domSanitizer.bypassSecurityTrustResourceUrl(googleLogoURL)
    );
    this.matIconRegistry.addSvgIcon(
      'github-logo',
      this.domSanitizer.bypassSecurityTrustResourceUrl(gitHubLogoURL)
    );
  }

  ngOnInit(): void {}

  login(type: 'google' | 'github') {
    if (type === 'google') {
      this.OAuth2.googleOAuth(() => {
        this.um.initSync();
        this.parentData.closeDialog();
      });
    } else if (type === 'github') {
      this.OAuth2.githubOAuth(() => {
        this.um.initSync();
        this.parentData.closeDialog();
      });
    }
  }
}
