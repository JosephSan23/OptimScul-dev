import { Component, OnInit, HostListener, Input, ViewEncapsulation } from '@angular/core';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.scss'],
  encapsulation: ViewEncapsulation.None
})
export class NavbarComponent implements OnInit {

  // @Input permite que el componente padre le pase valores
  // al navbar desde el HTML donde se usa
  // Por defecto el navbar es dinámico (cambia con scroll)
  // pero si el padre pasa [estatico]="true" se queda blanco fijo
  @Input() estatico = false;

  isScrolled = false;
  heroHeight = 0;

  ngOnInit(): void {
    if (typeof document === 'undefined') return;
    // Si es estático no necesitamos calcular nada
    if (this.estatico) return;
    const hero = document.querySelector('.hero') as HTMLElement;
    if (hero) this.heroHeight = hero.offsetHeight;
  }

  @HostListener('window:scroll', [])
  onScroll(): void {
    // Si el navbar es estático ignoramos el scroll completamente
    if (this.estatico) return;
    if (typeof document === 'undefined') return;
    const hero = document.querySelector('.hero') as HTMLElement;
    if (hero) this.heroHeight = hero.offsetHeight;
    this.isScrolled = window.scrollY > (this.heroHeight - 80);
  }
}
