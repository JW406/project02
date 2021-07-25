import { CommonModule } from '@angular/common';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MatDialogModule } from '@angular/material/dialog';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { JWTTokenHeaderInjector } from 'src/app/apiInjectors/JWTTokenHeaderInjector';
import { PaymentRoutingModule } from './payment-routing.module';
import { PaymentComponent } from './payment.component';
import { StripeService } from './services/stripe.service';



@NgModule({
  declarations: [PaymentComponent],
  imports: [
    CommonModule,
    HttpClientModule,
    FormsModule,
    PaymentRoutingModule,
    MatFormFieldModule,
    MatIconModule,
    MatInputModule,
    MatButtonModule,
    MatDialogModule,
    MatProgressSpinnerModule,
  ],
  providers: [
    {
      provide: HTTP_INTERCEPTORS,
      useClass: JWTTokenHeaderInjector,
      multi: true,
    },
    StripeService,
  ],
  exports: [PaymentComponent],
})
export class PaymentModule {}
