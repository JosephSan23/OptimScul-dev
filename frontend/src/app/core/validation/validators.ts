/* ============================================================
   validators.ts · OptimScul — VALIDADORES REUTILIZABLES
   Funciones puras, sin dependencia de Angular. Cada validador
   devuelve `null` si el valor es válido, o un `string` con el
   mensaje de error si no lo es.

   Se combinan por campo en un esquema (ver form-validator.ts):
     numeroDocumento: [requerido(), soloDigitos(), longitud(3, 15)]

   Convención: los validadores de FORMATO consideran válido el
   valor vacío (retornan null). La obligatoriedad la impone
   siempre `requerido()`. Así un campo opcional solo valida
   formato cuando el usuario escribe algo.
   ============================================================ */

export type Validador = (valor: any) => string | null;

/** Normaliza a string y recorta espacios. */
const txt = (v: any): string => (v ?? '').toString().trim();

const esVacio = (v: any): boolean => txt(v) === '';

// ── Obligatoriedad ─────────────────────────────────────────
export const requerido =
  (mensaje = 'Este campo es obligatorio'): Validador =>
  (valor) =>
    esVacio(valor) ? mensaje : null;

/** Para checkboxes / selección: exige un valor "verdadero". */
export const requeridoBool =
  (mensaje = 'Debes seleccionar esta opción'): Validador =>
  (valor) =>
    valor === true ? null : mensaje;

// ── Longitud ───────────────────────────────────────────────
export const longitudMin =
  (min: number, mensaje?: string): Validador =>
  (valor) =>
    esVacio(valor) || txt(valor).length >= min
      ? null
      : mensaje ?? `Debe tener al menos ${min} caracteres`;

export const longitudMax =
  (max: number, mensaje?: string): Validador =>
  (valor) =>
    esVacio(valor) || txt(valor).length <= max
      ? null
      : mensaje ?? `No puede superar los ${max} caracteres`;

export const longitud =
  (min: number, max: number, mensaje?: string): Validador =>
  (valor) => {
    if (esVacio(valor)) return null;
    const n = txt(valor).length;
    return n >= min && n <= max
      ? null
      : mensaje ?? `Debe tener entre ${min} y ${max} caracteres`;
  };

// ── Formato de texto ───────────────────────────────────────
export const soloDigitos =
  (mensaje = 'Solo se permiten números'): Validador =>
  (valor) =>
    esVacio(valor) || /^[0-9]+$/.test(txt(valor)) ? null : mensaje;

/** Letras (incluye acentos y ñ), espacios, apóstrofo y guion. */
export const soloLetras =
  (mensaje = 'Solo se permiten letras'): Validador =>
  (valor) =>
    esVacio(valor) || /^[A-Za-zÁÉÍÓÚáéíóúÑñÜü' -]+$/.test(txt(valor))
      ? null
      : mensaje;

export const patron =
  (regex: RegExp, mensaje = 'El formato no es válido'): Validador =>
  (valor) =>
    esVacio(valor) || regex.test(txt(valor)) ? null : mensaje;

// ── Correo ─────────────────────────────────────────────────
const CORREO_RE = /^[^\s@]+@[^\s@]+\.[^\s@]{2,}$/;
export const correo =
  (mensaje = 'Escribe un correo válido (ej: nombre@dominio.com)'): Validador =>
  (valor) =>
    esVacio(valor) || CORREO_RE.test(txt(valor)) ? null : mensaje;

// ── Teléfono (Colombia) ────────────────────────────────────
/**
 * Acepta 7 dígitos (fijo) o 10 dígitos (celular). Ignora espacios,
 * guiones y paréntesis al validar.
 */
export const telefonoCo =
  (mensaje = 'Teléfono inválido (7 dígitos fijo o 10 dígitos celular)'): Validador =>
  (valor) => {
    if (esVacio(valor)) return null;
    const soloNum = txt(valor).replace(/[\s()-]/g, '');
    return /^[0-9]{7}$/.test(soloNum) || /^[0-9]{10}$/.test(soloNum)
      ? null
      : mensaje;
  };

/**
 * Documento de identidad: solo dígitos, entre `min` y `max`.
 * Por defecto 3–15, que cubre RC, TI, CC y CE colombianos.
 */
export const documento =
  (min = 3, max = 15, mensaje?: string): Validador =>
  (valor) => {
    if (esVacio(valor)) return null;
    const s = txt(valor);
    return /^[0-9]+$/.test(s) && s.length >= min && s.length <= max
      ? null
      : mensaje ?? `Documento inválido (${min} a ${max} dígitos, solo números)`;
  };

// ── Fechas ─────────────────────────────────────────────────
const hoyISO = (): string => new Date().toISOString().slice(0, 10);

export const fechaNoFutura =
  (mensaje = 'La fecha no puede ser futura'): Validador =>
  (valor) =>
    esVacio(valor) || txt(valor) <= hoyISO() ? null : mensaje;

export const fechaNoPasada =
  (mensaje = 'La fecha no puede ser anterior a hoy'): Validador =>
  (valor) =>
    esVacio(valor) || txt(valor) >= hoyISO() ? null : mensaje;

// ── Numéricos ──────────────────────────────────────────────
export const rangoNumerico =
  (min: number, max: number, mensaje?: string): Validador =>
  (valor) => {
    if (esVacio(valor)) return null;
    const n = Number(valor);
    return !isNaN(n) && n >= min && n <= max
      ? null
      : mensaje ?? `Debe ser un número entre ${min} y ${max}`;
  };

// ── Comparación entre campos ───────────────────────────────
/**
 * Valida que este campo coincida con otro del mismo formulario.
 * Se resuelve con el objeto completo (ver validarEsquema, que pasa
 * el `todos` como segundo argumento a los validadores cruzados).
 */
export const coincideCon =
  (otroCampo: string, mensaje = 'Los valores no coinciden'): ValidadorCruzado =>
  (valor, todos) =>
    txt(valor) === txt(todos?.[otroCampo]) ? null : mensaje;

/** Validador que además recibe el objeto completo del formulario. */
export type ValidadorCruzado = (valor: any, todos: Record<string, any>) => string | null;
