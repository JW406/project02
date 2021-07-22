import { Injectable } from '@angular/core';

export interface Notification {
  title: string;
  body: string[];
  date: number;
}

@Injectable()
export class NotificationService {
  private _currentNotificationNumber = 0;
  private _listOfNotifications: Notification[] = [];

  constructor() {}

  get currentNotificationNumber() {
    return this._currentNotificationNumber;
  }

  get listOfNotifications() {
    return this._listOfNotifications;
  }

  set currentNotificationNumber(newCurrentNotificationNumber: number) {
    if (newCurrentNotificationNumber >= 0) {
      this._currentNotificationNumber = newCurrentNotificationNumber;
    }
  }

  set listOfNotifications(newListOfNotifications: Notification[]) {
    this._listOfNotifications = newListOfNotifications;
  }
}
