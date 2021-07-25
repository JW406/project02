import { Injectable } from '@angular/core';
import { CanActivate, Router } from '@angular/router';

@Injectable()
export class LoginGuard implements CanActivate {
  constructor(private router: Router) {}

  canActivate() {
    const allowed = localStorage.getItem('jwtToken') != null;
    if (allowed) {
      return true;
    } else {
      return this.router.parseUrl('/')
    }
  }
}
