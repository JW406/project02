import { Injectable } from '@angular/core';
import { MatDialog, MatDialogRef } from '@angular/material/dialog';
import { MessageBoxComponent } from 'src/app/components/message-box/message-box.component';

export interface MatDialogData {
  text: string;
}

@Injectable({
  providedIn: 'root',
})
export class MessageBoxService {
  matDialogref: MatDialogRef<MessageBoxComponent> | null = null;
  constructor(private dialog: MatDialog) {}

  show = (text: string) => {
    this.matDialogref = this.dialog.open<MessageBoxComponent, MatDialogData>(
      MessageBoxComponent,
      {
        autoFocus: false,
        data: {
          text,
        },
      }
    );
  };

  close = () => {
    this.matDialogref?.close();
  };
}
