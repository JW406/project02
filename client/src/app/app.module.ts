import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { MatBadgeModule } from '@angular/material/badge';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MatDialogModule } from '@angular/material/dialog';
import { MatDividerModule } from '@angular/material/divider';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatGridListModule } from '@angular/material/grid-list';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { MatListModule } from '@angular/material/list';
import { MatMenuModule } from '@angular/material/menu';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { MatSidenavModule } from '@angular/material/sidenav';
import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { JWTTokenHeaderInjector } from './apiInjectors/JWTTokenHeaderInjector';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { CardItemComponent } from './components/card-item/card-item.component';
import { CartItemComponent } from './components/cart-item/cart-item.component';
import { HeaderComponent } from './components/header/header.component';
import { HomeComponent } from './components/home/home.component';
import { LoginDialogComponent } from './components/login-dialog/login-dialog.component';
import { MessageBoxComponent } from './components/message-box/message-box.component';
import { ShopComponent } from './components/shop/shop.component';
import { UserDashboardComponent } from './components/user-dashboard/user-dashboard.component';
import { UserProfileComponent } from './components/user-dashboard/user-profile/user-profile.component';
import { PaymentModule } from './modules/payment/payment.module';
import { FourOFourComponent } from './pages/four-ofour/four-ofour.component';
import { MessageBoxService } from './services/message-box/message-box.service';
import { NotificationService } from './services/notification/notification.service';
import { OAuth2Service } from './services/oauth2/oauth2.service';
import { UserManagementService } from './services/user-management/user-management.service';



@NgModule({
  declarations: [
    AppComponent,
    UserDashboardComponent,
    UserProfileComponent,
    FourOFourComponent,
    HomeComponent,
    LoginDialogComponent,
    HeaderComponent,
    ShopComponent,
    MessageBoxComponent,
    CardItemComponent,
    CartItemComponent,
  ],
  imports: [
    HttpClientModule,
    FormsModule,
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    PaymentModule,
    MatButtonModule,
    MatListModule,
    MatIconModule,
    MatMenuModule,
    MatCardModule,
    MatDialogModule,
    MatFormFieldModule,
    MatInputModule,
    MatSidenavModule,
    MatGridListModule,
    MatBadgeModule,
    MatDividerModule,
    MatProgressSpinnerModule,
  ],
  providers: [
    { provide: HTTP_INTERCEPTORS, useClass: JWTTokenHeaderInjector, multi: true },
    UserManagementService,
    OAuth2Service,
    MessageBoxService,
    NotificationService,
  ],
  bootstrap: [AppComponent],
})
export class AppModule {}
