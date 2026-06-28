import { Component } from '@angular/core';

@Component({
  selector: 'app-footer',
  templateUrl: './footer.component.html',
  styleUrls: ['./footer.component.scss']
})
export class FooterComponent {
  anioActual = new Date().getFullYear();

   columnaPlataforma = [
    { texto: 'El Desafío', enlace: '/', fragmento: 'problem' },
    { texto: 'Nuestras Soluciones', enlace: '/', fragmento: 'solutions' },
    { texto: 'Funcionalidades', enlace: '/', fragmento: 'features' },
    { texto: 'Infraestructura', enlace: '/', fragmento: 'capabilities' },
  ];

  columnaRecursos = [
    { texto: '¿Cómo funciona?', enlace: '/primeros-pasos' },
    { texto: 'Centro de Ayuda', enlace: '/' },
    { texto: 'Política de Privacidad', enlace: '/' },
    { texto: 'Términos y Condiciones', enlace: '/' },
  ];

  redesSociales = [
    { icono: 'fab fa-facebook-f', enlace: '#' },
    { icono: 'fab fa-x-twitter', enlace: '#' },
    { icono: 'fab fa-linkedin-in', enlace: '#' },
  ];
}
