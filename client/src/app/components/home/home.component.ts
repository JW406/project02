import { Component, OnInit } from '@angular/core';
import { DomSanitizer, SafeResourceUrl } from '@angular/platform-browser';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss'],
})
export class HomeComponent implements OnInit {
  wallPaper = 'poke_wallpaper.png';
  _wallPaper?: SafeResourceUrl;
  constructor(private domSanitizer: DomSanitizer) {}

  ngOnInit() {
    this._wallPaper = this.domSanitizer.bypassSecurityTrustResourceUrl(
      `assets/${this.wallPaper}`
    );
  }
}
