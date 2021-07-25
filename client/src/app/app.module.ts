import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';

import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MatDialogModule } from '@angular/material/dialog';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatGridListModule } from '@angular/material/grid-list';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { MatListModule } from '@angular/material/list';
import { MatMenuModule } from '@angular/material/menu';
import { MatSidenavModule } from '@angular/material/sidenav';
import { MatTabsModule } from '@angular/material/tabs';
import { MatBadgeModule } from '@angular/material/badge';
import { MatDividerModule } from '@angular/material/divider';

import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HeaderComponent } from './components/header/header.component';
import { HomeComponent } from './components/home/home.component';
import { LoginDialogComponent } from './components/login-dialog/login-dialog.component';
import { ShopComponent } from './components/shop/shop.component';
import { UserDashboardComponent } from './components/user-dashboard/user-dashboard.component';
import { UserDefaultComponent } from './components/user-dashboard/user-default/user-default.component';
import { UserProfileComponent } from './components/user-dashboard/user-profile/user-profile.component';
import { UserSettingsComponent } from './components/user-dashboard/user-settings/user-settings.component';
import { FourOFourComponent } from './pages/four-ofour/four-ofour.component';
import { PaymentModule } from './modules/payment/payment.module';
import { UserManagementService } from './services/user-management/user-management.service';
import { OAuth2Service } from './services/oauth2/oauth2.service';
import { MessageBoxService } from './services/message-box/message-box.service';
import { MessageBoxComponent } from './components/message-box/message-box.component';
import { NotificationService } from './services/notification/notification.service';
import { JWTTokenHeaderInjector } from './apiInjectors/JWTTokenHeaderInjector';

@NgModule({
  declarations: [
    AppComponent,
    UserDashboardComponent,
    UserProfileComponent,
    UserSettingsComponent,
    UserDefaultComponent,
    FourOFourComponent,
    HomeComponent,
    LoginDialogComponent,
    HeaderComponent,
    ShopComponent,
    MessageBoxComponent,
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
    MatTabsModule,
    MatMenuModule,
    MatCardModule,
    MatDialogModule,
    MatFormFieldModule,
    MatInputModule,
    MatSidenavModule,
    MatGridListModule,
    MatBadgeModule,
    MatDividerModule,
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
