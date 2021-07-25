import {
  HttpEvent,
  HttpInterceptor,
  HttpHandler,
  HttpRequest,
} from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { tap } from 'rxjs/operators';
import { UserManagementService } from '../services/user-management/user-management.service';

@Injectable()
export class JWTTokenHeaderInjector implements HttpInterceptor {
  private interceptedUrls = ['/api', '/pay'];

  constructor(private um: UserManagementService) {}

  intercept(
    req: HttpRequest<any>,
    next: HttpHandler
  ): Observable<HttpEvent<any>> {
    if (this.interceptedUrls.some((url) => req.url.startsWith(url))) {
      const jwtToken = localStorage.getItem('jwtToken');
      if (jwtToken != null) {
        const resetReq = req.clone({
          setHeaders: {
            Authorization: `Bearer ${localStorage.getItem('jwtToken')}`,
          },
        });
        return next.handle(resetReq).pipe(
          tap(
            (e: any) => {
              if (e.body?.isSuccess === 'false') {
                this.um.logout();
              }
            },
            () => {}
          )
        );
      }
    }
    return next.handle(req);
  }
}
