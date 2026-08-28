// 04 · PICOS (spike) — un salto brusco, como cuando se abren las matrículas.
//      Lo clave: ¿se RECUPERA el sistema después del pico?
import { sleep } from 'k6';
import { loginPool, viajeEstudiante } from './lib/common.js';

export const options = {
  scenarios: {
    pico: {
      executor: 'ramping-vus',
      startVUs: 0,
      stages: [
        { duration: '30s', target: 50 },    // día normal tranquilo
        { duration: '15s', target: 1200 },  // ¡PUM! avalancha repentina
        { duration: '1m',  target: 1200 },  // aguanta el pico
        { duration: '15s', target: 50 },    // baja de golpe
        { duration: '1m',  target: 50 },    // ¿se recupera?
        { duration: '10s', target: 0 },
      ],
      gracefulStop: '20s',
    },
  },
};

let token = null;

export default function () {
  if (token === null) token = loginPool();
  viajeEstudiante(token, { think: false });
  sleep(Math.random() * 2 + 1); // 1-3s: refrescos rápidos, típico de un pico
}
