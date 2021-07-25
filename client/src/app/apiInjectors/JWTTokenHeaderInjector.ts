import {
  HttpEvent,
  HttpInterceptor,
  HttpHandler,
  HttpRequest,
} from '@angular/common/http';
import { Observable } from 'rxjs';

export class JWTTokenHeaderInjector implements HttpInterceptor {
  private interceptedUrls = ['/api', '/pay'];

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
        return next.handle(resetReq);
      }
    }
    return next.handle(req);
  }
}
