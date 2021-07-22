import { Component, Inject, OnInit } from '@angular/core';
import { MAT_DIALOG_DATA } from '@angular/material/dialog';
import { MatDialogData } from 'src/app/services/message-box/message-box.service';

@Component({
  selector: 'app-message-box',
  templateUrl: './message-box.component.html',
  styleUrls: ['./message-box.component.scss'],
})
export class MessageBoxComponent implements OnInit {
  constructor(
    @Inject(MAT_DIALOG_DATA)
    public parentData: MatDialogData
  ) {}

  ngOnInit(): void {}
}
