
// Techo real
import { loginPool, viajeEstudiante } from './lib/common.js';

const PICO      = Number(__ENV.PICO || 800);
const CONSTANTE = __ENV.VUS ? Number(__ENV.VUS) : null;
const DURACION  = __ENV.DURACION || '2m';

const thresholds = {
  http_req_failed:                      ['rate<0.01'],   // < 1% de errores en total
  'http_req_duration{name:notas}':      ['p(95)<2000'],
  'http_req_duration{name:horario}':    ['p(95)<2000'],
  'http_req_duration{name:asistencia}': ['p(95)<2000'],
};

export const options = CONSTANTE !== null
  // ── Modo AFINADO: carga constante para iterar el número de usuarios ──
  ? { vus: CONSTANTE, duration: DURACION, thresholds }
  // ── Modo RAMPA: sube a PICO y aguanta, como una jornada real de acceso ──
  : {
      scenarios: {
        usuarios_reales: {
          executor: 'ramping-vus',
          startVUs: 0,
          stages: [
            { duration: '1m',  target: PICO },
            { duration: '4m',  target: PICO },
            { duration: '30s', target: 0 },
          ],
          gracefulStop: '30s',
        },
      },
      thresholds,
    };

// Cada VU inicia sesión UNA vez (como una persona real) y cachea su token.
let token = null;

export default function () {
  if (token === null) token = loginPool();
  viajeEstudiante(token, { think: true, includePerfil: true });
}
