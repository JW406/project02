import { Injectable } from '@angular/core';

export interface Notification {
  title: string;
  body: string[];
  date: number;
  isRead: boolean;
}

@Injectable()
export class NotificationService {
  private NOTIFICATIONTAG = 'notificaitonList'
  private _listOfNotifications: Notification[] = JSON.parse(localStorage.getItem(this.NOTIFICATIONTAG) || '[]');
  private _currentNotificationNumber = this._listOfNotifications.reduce((prev, curr) => +(!curr.isRead) + prev, 0);

  constructor() {}

  get currentNotificationNumber() {
    return this._currentNotificationNumber;
  }

  get listOfNotifications() {
    return this._listOfNotifications;
  }

  private setCurrentNotificationNumber(newCurrentNotificationNumber: number) {
    if (newCurrentNotificationNumber >= 0) {
      this._currentNotificationNumber = newCurrentNotificationNumber;
    }
  }

  set listOfNotifications(newListOfNotifications: Notification[]) {
    this.setCurrentNotificationNumber(
      newListOfNotifications.reduce((prev, curr) => {
        return prev + +!curr.isRead;
      }, 0)
    );
    localStorage.setItem(this.NOTIFICATIONTAG, JSON.stringify(newListOfNotifications))
    this._listOfNotifications = newListOfNotifications;
  }

  readAll = () => {
    this.listOfNotifications = this._listOfNotifications.map((item) => {
      item.isRead = true;
      return item;
    });
    this._currentNotificationNumber = 0;
  }
}
