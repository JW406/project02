import { Component, Input, OnInit } from '@angular/core';
import { DomSanitizer, SafeStyle } from '@angular/platform-browser';

@Component({
  selector: 'app-card-item',
  templateUrl: './card-item.component.html',
  styleUrls: ['./card-item.component.scss'],
})
export class CardItemComponent implements OnInit {
  @Input() title = '';
  @Input() price = 0;
  @Input() img: string | SafeStyle = '';

  constructor(private sanitizer: DomSanitizer) {}

  ngOnInit(): void {
    this.img = this.sanitizer.bypassSecurityTrustStyle(`url(${this.img})`);
  }

  bottomClasses = ['bottom'];

  buy() {
    this.bottomClasses.push('clicked');
  }

  remove() {
    this.bottomClasses.splice(this.bottomClasses.indexOf('clicked'), 1);
  }
}
