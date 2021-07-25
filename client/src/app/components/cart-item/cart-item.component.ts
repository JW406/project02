import { Component, Input, OnInit } from '@angular/core';
import { DomSanitizer, SafeStyle } from '@angular/platform-browser';
import { CartService } from 'src/app/services/cart/cart.service';

@Component({
  selector: 'app-cart-item',
  templateUrl: './cart-item.component.html',
  styleUrls: ['./cart-item.component.scss'],
})
export class CartItemComponent implements OnInit {
  @Input() title = '';
  @Input() price = 0;
  @Input() quantities = 0;
  @Input() img: string = '';
  _img?: SafeStyle;

  constructor(private sanitizer: DomSanitizer, private cs: CartService) {}

  ngOnInit(): void {
    this._img = this.sanitizer.bypassSecurityTrustStyle(`url(${this.img})`);
  }

  quantityChange(e: Event, amt: number) {
    e.stopPropagation()
    if (amt > 0) {
      this.cs.addItemToCart({
        name: this.title,
        photo: this.img,
        price: this.price,
      })
    } else if (amt < 0) {
      this.cs.removeItemFromCart({
        name: this.title,
        photo: this.img,
        price: this.price,
      })
    }
  }
}
