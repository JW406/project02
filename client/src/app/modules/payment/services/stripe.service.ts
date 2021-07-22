import { Injectable } from '@angular/core';
import { loadStripe, Stripe } from '@stripe/stripe-js';
import { environment as env } from 'src/environments/environment';

@Injectable({
  providedIn: 'root',
})
export class StripeService {
  private _instance: Promise<Stripe>;
  constructor() {
    this._instance = loadStripe(env.stripeClientID) as Promise<Stripe>;
  }
  get instance() {
    return this._instance;
  }
}
