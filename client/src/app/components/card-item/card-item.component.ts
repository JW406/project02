import { Component, Input, OnInit } from '@angular/core';
import { DomSanitizer, SafeStyle } from '@angular/platform-browser';
import { CartService } from 'src/app/services/cart/cart.service';

@Component({
  selector: 'app-card-item',
  templateUrl: './card-item.component.html',
  styleUrls: ['./card-item.component.scss'],
})
export class CardItemComponent implements OnInit {
  @Input() title = '';
  @Input() price = 0;
  @Input() img: string = '';
  _img?: SafeStyle;

  constructor(private sanitizer: DomSanitizer, public cs: CartService) {}

  ngOnInit(): void {
    this._img = this.sanitizer.bypassSecurityTrustStyle(`url(${this.img})`);
  }

  bottomClasses = ['bottom'];

  buy() {
    this.bottomClasses.push('clicked');
    this.cs.addItemToCart({
      name: this.title,
      price: this.price,
      photo: this.img,
    });
  }

  remove() {
    this.bottomClasses.splice(this.bottomClasses.indexOf('clicked'), 1);
    this.cs.removeAllQuantitiesOfItemFromCart({
      name: this.title,
      price: this.price,
      photo: this.img,
    });
  }
}
