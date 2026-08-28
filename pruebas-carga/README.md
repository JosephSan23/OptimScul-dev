# Pruebas de carga (k6) — OptimScul

Suite de rendimiento del backend con [k6](https://k6.io). Toda la lógica común
(URL, credenciales, UUIDs, login y el "viaje del estudiante") vive en
`lib/common.js`; cada archivo solo declara su **patrón de carga**.

## Requisitos previos

- Backend corriendo (por defecto `http://localhost:8080`).
- Cuentas de carga sembradas (`loadtest-00001…`) con `crear-estudiantes-carga.sql`.

## Variables de entorno (opcionales)

Nada está quemado: se puede sobreescribir al ejecutar con `-e CLAVE=valor`.

| Variable       | Por defecto                     | Para qué |
|----------------|---------------------------------|----------|
| `BASE_URL`     | `http://localhost:8080`         | URL del backend |
| `K6_USER`      | `carlos-martinez20`             | Usuario único (smoke) |
| `K6_PASS`      | `202020Jos.`                    | Contraseña del usuario único |
| `LOAD_PASS`    | `Load1234`                      | Contraseña del pool `loadtest-*` |
| `TOTAL_CUENTAS`| `500`                           | Tamaño del pool de cuentas |
| `ANIO_ID`      | (UUID del año lectivo)          | Año lectivo a consultar |
| `PERIODO_ID`   | (UUID del periodo)              | Periodo a consultar |
| `THINK_MIN` / `THINK_MAX` | `8` / `25`           | Rango del think-time realista (s) |
| `VUS`          | —                               | Fija usuarios constantes (afinado / soak) |
| `PICO`         | `1300`                          | Objetivo de la rampa en la prueba realista |

> Recomendado: para ejecuciones reales pasa las credenciales por `-e` en lugar
> de dejar los valores por defecto.

## Los archivos

| Archivo | Tipo | Qué responde |
|---------|------|--------------|
| `01-smoke.js`          | Smoke       | ¿El login responde y es rápido? |
| `02-carga.js`          | Carga       | ¿Cumple p(95)<800ms con 50 usuarios sostenidos? |
| `03-estres.js`         | Estrés      | ¿Dónde se rompe? (escalones 100→800) |
| `04-picos.js`          | Picos       | ¿Se recupera tras una avalancha repentina? |
| `05-resistencia.js`    | Soak        | ¿Hay fugas/degradación en carga larga? |
| `06-carga-realista.js` | **Definitiva** | Viaje completo con usuarios reales; sirve para hallar el techo (rampa) y para afinar (`-e VUS=n`) |

## Cómo ejecutar

```bash
k6 run 01-smoke.js
k6 run -e K6_USER=carlos-martinez20 -e K6_PASS='202020Jos.' 02-carga.js
k6 run 03-estres.js
k6 run 04-picos.js
k6 run 05-resistencia.js

# Prueba definitiva — modo rampa (hallar el techo):
k6 run -e PICO=1300 06-carga-realista.js
# Prueba definitiva — modo afinado (carga constante, itera el número):
k6 run -e VUS=100 06-carga-realista.js
```
