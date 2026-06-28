import { Component, OnInit, HostListener, ViewEncapsulation } from '@angular/core';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.scss'],
  encapsulation: ViewEncapsulation.None
})

export class NavbarComponent implements OnInit {

  isScrolled = false;
  heroHeight = 0;

  ngOnInit(): void {
    if (typeof document !== 'undefined') {
      const hero = document.querySelector('.hero') as HTMLElement;
      if (hero) {
        this.heroHeight = hero.offsetHeight;
      }
    }
  }

  @HostListener('window:scroll', [])
  onScroll(): void {
    if (typeof document !== 'undefined') {
      const hero = document.querySelector('.hero') as HTMLElement;
      if (hero) {
        this.heroHeight = hero.offsetHeight;
      }
      this.isScrolled = window.scrollY > (this.heroHeight - 80);
    }
  }
}
