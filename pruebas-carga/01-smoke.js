// 01 · SMOKE — verificación mínima: ¿responde el login y es rápido? (1 usuario)
import http from 'k6/http';
import { check, sleep } from 'k6';
import { BASE, SINGLE_USER, SINGLE_PASS } from './lib/common.js';

export const options = {
  vus: 1,
  duration: '30s',
};

export default function () {
  const res = http.post(`${BASE}/api/auth/login`,
    JSON.stringify({ usernameOrEmail: SINGLE_USER, password: SINGLE_PASS }),
    { headers: { 'Content-Type': 'application/json' } });

  check(res, {
    'status 200': (r) => r.status === 200,
    'responde en < 500ms': (r) => r.timings.duration < 500,
  });

  sleep(1);
}
