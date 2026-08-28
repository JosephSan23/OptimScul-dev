// ─────────────────────────────────────────────────────────────────────────────
// lib/common.js — Configuración y lógica compartida de TODAS las pruebas de carga.
//
// Todo se puede sobreescribir con variables de entorno al ejecutar, por ejemplo:
//   k6 run -e BASE_URL=http://localhost:8080 -e K6_USER=usuario -e K6_PASS=clave 01-smoke.js
//
// Así no hay credenciales ni UUIDs quemados repetidos en cada archivo.
// ─────────────────────────────────────────────────────────────────────────────
import http from 'k6/http';
import { check, sleep, group } from 'k6';
import { SharedArray } from 'k6/data';

// ── URL base y datos del año/periodo (cámbialos por -e o edítalos aquí) ──────
export const BASE       = __ENV.BASE_URL   || 'http://localhost:8080';
export const ANIO_ID    = __ENV.ANIO_ID    || 'f67e1bc7-e636-4d82-968a-c19f65176498';
export const PERIODO_ID = __ENV.PERIODO_ID || '05057c65-1815-4aed-8092-c0562168943a';

// ── Usuario único (smoke y escenarios con un solo token) ─────────────────────
export const SINGLE_USER = __ENV.K6_USER || 'carlos-martinez20';
export const SINGLE_PASS = __ENV.K6_PASS || '202020Jos.';

// ── Pool de cuentas de carga: loadtest-00001 .. loadtest-000NN ───────────────
//    Generadas por crear-estudiantes-carga.sql. Password por defecto: Load1234.
const TOTAL_CUENTAS = Number(__ENV.TOTAL_CUENTAS || 500);
const LOAD_PASS     = __ENV.LOAD_PASS || 'Load1234';
export const USERS = new SharedArray('users', () => {
  const arr = [];
  for (let i = 1; i <= TOTAL_CUENTAS; i++) {
    arr.push({ u: 'loadtest-' + String(i).padStart(5, '0'), p: LOAD_PASS });
  }
  return arr;
});

const JSON_HEADERS = { 'Content-Type': 'application/json' };

// ── Login con credenciales dadas → devuelve el token (o null si falla) ───────
export function loginCon(usernameOrEmail, password) {
  const res = http.post(`${BASE}/api/auth/login`,
    JSON.stringify({ usernameOrEmail, password }),
    { headers: JSON_HEADERS, tags: { name: 'login' } });
  const ok = check(res, { 'login 200': (r) => r.status === 200 });
  return ok ? res.json('token') : null;
}

// ── Login de la cuenta del pool que le toca a este VU ────────────────────────
export function loginPool() {
  const cred = USERS[(__VU - 1) % USERS.length];
  return loginCon(cred.u, cred.p);
}

// ── Cabeceras autenticadas a partir de un token ──────────────────────────────
export function auth(token) {
  return { headers: Object.assign({}, JSON_HEADERS, { Authorization: `Bearer ${token}` }) };
}

// ── Tiempo de lectura realista entre pantallas: 8-25s (configurable) ─────────
export function pensar() {
  const min = Number(__ENV.THINK_MIN || 8);
  const max = Number(__ENV.THINK_MAX || 25);
  sleep(Math.random() * (max - min) + min);
}

// ─────────────────────────────────────────────────────────────────────────────
// EL VIAJE DEL ESTUDIANTE — la lógica DEFINITIVA reutilizada por todas las pruebas.
// Simula a un estudiante entrando a ver sus notas, horario y asistencia.
//
//   opts.think        true  → pausa realista de 8-25s entre pantallas (soak/realista)
//                     false → sin pausa (para pruebas de throughput/estrés)
//   opts.includePerfil true → a veces (30%) también revisa su perfil
// ─────────────────────────────────────────────────────────────────────────────
export function viajeEstudiante(token, opts) {
  opts = opts || {};
  const think = opts.think !== false;      // por defecto: pausa realista
  const includePerfil = opts.includePerfil === true;
  const a = auth(token);

  group('ver notas', () => {
    const r = http.get(`${BASE}/api/estudiante/mis-notas?anioId=${ANIO_ID}&periodoId=${PERIODO_ID}`,
      Object.assign({}, a, { tags: { name: 'notas' } }));
    check(r, { 'notas 200': (x) => x.status === 200 });
  });
  if (think) pensar();

  group('ver horario', () => {
    const r = http.get(`${BASE}/api/estudiante/mi-horario?anioId=${ANIO_ID}`,
      Object.assign({}, a, { tags: { name: 'horario' } }));
    check(r, { 'horario 200': (x) => x.status === 200 });
  });
  if (think) pensar();

  group('ver asistencia', () => {
    const r = http.get(`${BASE}/api/estudiante/mi-asistencia?anioId=${ANIO_ID}`,
      Object.assign({}, a, { tags: { name: 'asistencia' } }));
    check(r, { 'asistencia 200': (x) => x.status === 200 });
  });
  if (think) pensar();

  if (includePerfil && Math.random() < 0.3) {
    const r = http.get(`${BASE}/api/perfil`,
      Object.assign({}, a, { tags: { name: 'perfil' } }));
    check(r, { 'perfil 200': (x) => x.status === 200 });
    if (think) pensar();
  }
}
