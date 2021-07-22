import { Injectable } from '@angular/core';
import { environment as env } from 'src/environments/environment';
import { UserManagementService } from '../user-management/user-management.service';

@Injectable({
  providedIn: 'root',
})
export class OAuth2Service {
  constructor(private um: UserManagementService) {}

  googleOAuth = (cb: () => void) => {
    window.open(
      `${env.googleAuthEndPoint}&redirect_uri=${window.location.origin}${env.googleRedirectUri}&client_id=${env.googleClientID}&${env.googleScope}`,
      '_blank'
    );
    let flag: string | null = '';
    const intervalID = setInterval(() => {
      if ((flag = window.localStorage.getItem('__flag'))) {
        clearInterval(intervalID);
        window.localStorage.removeItem('__flag');
        if (flag === 'true') {
          cb();
          const userInfo = window.localStorage.getItem('userInfo');
          const userObj: any = JSON.parse(userInfo || '{}');
          this.um.setUsername(userObj['name']);
        } else {
          // TODO: use dialog
          console.error('Google Error');
        }
      }
    }, 500);
  };

  githubOAuth = (cb: () => void) => {
    window.open(
      `${env.githubAuthEndPoint}&redirect_uri=${window.location.origin}${env.githubRedirectUri}&client_id=${env.githubClientID}&${env.githubScope}&state="foobar"`,
      '_blank'
    );
    let flag: string | null = '';
    const intervalID = setInterval(() => {
      if ((flag = window.localStorage.getItem('__flag'))) {
        clearInterval(intervalID);
        window.localStorage.removeItem('__flag');
        if (flag === 'true') {
          cb();
          const userInfo = window.localStorage.getItem('userInfo');
          const userObj: any = JSON.parse(userInfo || '{}');
          this.um.setUsername(userObj['login']);
        } else {
          // TODO: use dialog
          console.error('Github Error');
        }
      }
    }, 500);
  };
}
