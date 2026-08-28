// 03 · ESTRÉS — subimos en escalones hasta que algo ceda. SIN thresholds que corten:
//      aquí QUEREMOS ver hasta dónde aguanta y cómo se degrada.
import { sleep } from 'k6';
import { loginPool, viajeEstudiante } from './lib/common.js';

export const options = {
  stages: [
    { duration: '2m', target: 100 },  // 100 usuarios
    { duration: '2m', target: 200 },  // 200
    { duration: '2m', target: 400 },  // 400
    { duration: '2m', target: 800 },  // 800
    { duration: '1m', target: 0 },    // recuperación (baja a 0)
  ],
};

let token = null;

export default function () {
  if (token === null) token = loginPool();
  viajeEstudiante(token, { think: false }); // sin pausas: máxima presión
  sleep(1);
}
