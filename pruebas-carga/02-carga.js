// 02 · CARGA — carga sostenida moderada (50 usuarios) con el viaje del estudiante.
//      Criterio del plan: p(95) < 800ms y menos del 1% de errores.
import { sleep } from 'k6';
import { loginPool, viajeEstudiante } from './lib/common.js';

export const options = {
  stages: [
    { duration: '1m', target: 50 },  // sube a 50 usuarios en 1 min
    { duration: '3m', target: 50 },  // los mantiene 3 min (carga sostenida)
    { duration: '1m', target: 0 },   // baja a 0 suavemente
  ],
  thresholds: {
    http_req_failed:                      ['rate<0.01'],  // < 1% de errores
    'http_req_duration{name:notas}':      ['p(95)<800'],
    'http_req_duration{name:horario}':    ['p(95)<800'],
    'http_req_duration{name:asistencia}': ['p(95)<800'],
  },
};

// Cada VU inicia sesión una sola vez (como una persona real) y cachea su token.
let token = null;

export default function () {
  if (token === null) token = loginPool();
  viajeEstudiante(token, { think: false }); // sin pausas: buscamos throughput sostenido
  sleep(1);
}
