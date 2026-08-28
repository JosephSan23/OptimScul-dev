// 07 · MULTIINSTITUCIÓN — varias instituciones trabajando A LA VEZ.
import http from 'k6/http';
import { check, sleep, group } from 'k6';
import { SharedArray } from 'k6/data';

const BASE     = __ENV.BASE_URL || 'http://localhost:8080';
const VUS      = Number(__ENV.VUS || 100);   // usuarios simultáneos POR institución
const DURACION = __ENV.DURACION || '3m';

// Config de instituciones (cada una con su año/periodo y su pool de usuarios).
const INSTITUCIONES = JSON.parse(open('./lib/instituciones.json'));

// Un pool de usuarios (SharedArray) por institución.
const pools = {};
INSTITUCIONES.forEach((inst) => {
  pools[inst.nombre] = new SharedArray('users-' + inst.nombre, () => {
    const arr = [];
    for (let i = inst.usuarios.desde; i <= inst.usuarios.hasta; i++) {
      arr.push({ u: inst.usuarios.prefijo + String(i).padStart(5, '0'), p: inst.usuarios.pass });
    }
    return arr;
  });
});

// Acceso rápido a la config por nombre.
const CFG = {};
INSTITUCIONES.forEach((i) => { CFG[i.nombre] = i; });

// Un escenario por institución (todos arrancan juntos) + umbrales por institución.
const scenarios = {};
const thresholds = { http_req_failed: ['rate<0.05'] };
INSTITUCIONES.forEach((inst) => {
  scenarios['inst_' + inst.nombre] = {
    executor: 'constant-vus',
    vus: VUS,
    duration: DURACION,
    exec: 'viaje',
    tags: { institucion: inst.nombre },   // etiqueta TODAS las métricas de este escenario
    env: { INST: inst.nombre },
  };
  thresholds[`http_req_duration{institucion:${inst.nombre}}`] = ['p(95)<2000'];
  thresholds[`http_req_failed{institucion:${inst.nombre}}`]   = ['rate<0.02'];
});

export const options = { scenarios, thresholds };

// Token cacheado por cada VU (inicia sesión una sola vez, como una persona real).
let token = null;

function login(inst) {
  const pool = pools[inst.nombre];
  const cred = pool[(__VU - 1) % pool.length];
  const res = http.post(`${BASE}/api/auth/login`,
    JSON.stringify({ usernameOrEmail: cred.u, password: cred.p }),
    { headers: { 'Content-Type': 'application/json' }, tags: { name: 'login' } });
  const ok = check(res, { 'login 200': (r) => r.status === 200 });
  return ok ? res.json('token') : null;
}

// Punto de entrada de cada escenario/institución.
export function viaje() {
  const inst = CFG[__ENV.INST];
  if (token === null) token = login(inst);
  const auth = { headers: { Authorization: `Bearer ${token}`, 'Content-Type': 'application/json' } };

  group('notas', () => {
    const r = http.get(
      `${BASE}/api/estudiante/mis-notas?anioId=${inst.anioId}&periodoId=${inst.periodoId}`,
      Object.assign({}, auth, { tags: { name: 'notas' } }));
    check(r, { 'notas 200': (x) => x.status === 200 });
  });

  group('horario', () => {
    const r = http.get(`${BASE}/api/estudiante/mi-horario?anioId=${inst.anioId}`,
      Object.assign({}, auth, { tags: { name: 'horario' } }));
    check(r, { 'horario 200': (x) => x.status === 200 });
  });

  group('asistencia', () => {
    const r = http.get(`${BASE}/api/estudiante/mi-asistencia?anioId=${inst.anioId}`,
      Object.assign({}, auth, { tags: { name: 'asistencia' } }));
    check(r, { 'asistencia 200': (x) => x.status === 200 });
  });

  sleep(Math.random() * 2 + 1); // 1-3s de pausa entre ciclos
}
