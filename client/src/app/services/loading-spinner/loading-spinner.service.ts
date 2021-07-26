import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root',
})
export class LoadingSpinnerService {
  isWait = false;
  constructor() {}
}
