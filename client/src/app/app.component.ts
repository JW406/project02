import { OnInit } from '@angular/core';
import { Component } from '@angular/core';
import { MatDialog, MatDialogRef } from '@angular/material/dialog';
import { LoginDialogComponent } from './components/login-dialog/login-dialog.component';
import { UserManagementService } from './services/user-management/user-management.service';

export interface MatDialogData {
  closeDialog: () => void;
}

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss'],
})
export class AppComponent implements OnInit, MatDialogData {
  matDialogref: MatDialogRef<LoginDialogComponent> | null = null;

  constructor(private dialog: MatDialog, public um: UserManagementService) {}

  ngOnInit(): void {
    this.um.initSync();
  }

  openDialog = () => {
    this.matDialogref = this.dialog.open<LoginDialogComponent, MatDialogData>(
      LoginDialogComponent,
      {
        autoFocus: false,
        data: {
          closeDialog: this.closeDialog,
        },
      }
    );
  };

  closeDialog = () => {
    this.matDialogref?.close();
  };
}
