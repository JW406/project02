import { Injectable } from '@angular/core';

export interface Cart {
  items: CartItem[]
}

export interface Item {
  name: string;
  price: number;
  photo: string;
}

export interface CartItem extends Item {
  quantities: number;
}

@Injectable({
  providedIn: 'root',
})
export class CartService {
  public static CARTKEY = 'POKECART';
  private _cart: Cart;

  constructor() {
    try {
      this._cart = JSON.parse(localStorage.getItem(CartService.CARTKEY) || '');
    } catch (error) {
      this._cart = {
        items: [],
      };
    }
  }

  get cart() {
    return this._cart;
  }
  set cart(newCart: Cart) {
    localStorage.setItem(CartService.CARTKEY, JSON.stringify(newCart));
    this._cart = newCart;
  }

  addItemToCart(item: Item) {
    const existingCartItem = this._cart.items.find((i) => i.name === item.name)
    if (existingCartItem) {
      const items = this._cart.items.map((i) => {
        return i.name === item.name
          ? { ...i, quantities: i.quantities + 1 }
          : i;
      });
      this.cart = { ...this.cart, items };
    } else {
      const cartItem: CartItem = { ...item, quantities: 1 };
      this.cart = { ...this.cart, items: [...this.cart.items, cartItem] };
    }
  }

  removeItemFromCart(item: Item) {
    const items = this._cart.items.filter((i) => {
      if (i.name !== item.name) {
        return true;
      } else if (i.quantities > 1) {
        --i.quantities;
        return true;
      } else {
        return false;
      }
    });
    this.cart = { ...this.cart, items };
  }

  removeAllQuantitiesOfItemFromCart(item: Item) {
    const items = this._cart.items.filter((i) => {
      return i.name !== item.name;
    });
    this.cart = { ...this.cart, items };
  }

  get total() {
    return this._cart.items.reduce((prev, curr) => {
      return prev + curr.price * curr.quantities;
    }, 0);
  }
}
