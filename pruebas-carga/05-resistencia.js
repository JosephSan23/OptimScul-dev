// 05 · RESISTENCIA (soak) — carga MEDIA sostenida mucho tiempo, con think-time real.
//      Busca fugas de memoria y degradación lenta. Para un soak real, sube el hold a '2h'.
import { loginPool, viajeEstudiante } from './lib/common.js';

const CARGA = Number(__ENV.VUS || 300); // carga media, bien por debajo del techo

export const options = {
  scenarios: {
    resistencia: {
      executor: 'ramping-vus',
      startVUs: 0,
      stages: [
        { duration: '2m',  target: CARGA },  // sube
        { duration: '30m', target: CARGA },  // AGUANTA (súbelo a '2h' para un soak real)
        { duration: '2m',  target: 0 },      // baja
      ],
      gracefulStop: '30s',
    },
  },
  thresholds: {
    http_req_failed:                      ['rate<0.01'],
    'http_req_duration{name:notas}':      ['p(95)<2000'],
    'http_req_duration{name:horario}':    ['p(95)<2000'],
    'http_req_duration{name:asistencia}': ['p(95)<2000'],
  },
};

let token = null;

export default function () {
  if (token === null) token = loginPool();
  viajeEstudiante(token, { think: true }); // think-time realista de 8-25s
}
