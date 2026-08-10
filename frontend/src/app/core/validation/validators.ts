/* ============================================================
   validators.ts · OptimScul — VALIDADORES REUTILIZABLES
   Funciones puras, sin dependencia de Angular. Cada validador
   devuelve `null` si el valor es válido, o un `string` con el
   mensaje de error si no lo es.

   Se combinan por campo en un esquema (ver form-validator.ts):
     numeroDocumento: [requerido(), documentoPorTipo()]

   Convención: los validadores de FORMATO consideran válido el
   valor vacío (retornan null). La obligatoriedad la impone
   siempre `requerido()`. Así un campo opcional solo valida
   formato cuando el usuario escribe algo.
   ============================================================ */

export type Validador = (valor: any) => string | null;
/** Validador que además recibe el objeto completo del formulario. */
export type ValidadorCruzado = (valor: any, todos: Record<string, any>) => string | null;

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

// ── Documento de identidad ─────────────────────────────────
/**
 * Reglas por TIPO de documento. Cada tipo trae su patrón y su mensaje.
 * Ajusta libremente estos rangos; son el único lugar donde viven.
 *
 * - RC (Registro Civil):     8–11 dígitos (NUIP de menores)
 * - TI (Tarjeta Identidad):  10–11 dígitos (NUIP)
 * - CC (Cédula Ciudadanía):  6–10 dígitos
 * - CE (Cédula Extranjería): 6–15 alfanumérico (puede llevar letras)
 * - PASAPORTE:               6–12 alfanumérico (lleva letras)
 */
export interface ReglaDocumento {
  regex: RegExp;
  mensaje: string;
}
export const REGLAS_DOCUMENTO: Record<string, ReglaDocumento> = {
  RC: {
    regex: /^[0-9]{8,11}$/,
    mensaje: 'El registro civil debe tener entre 8 y 11 dígitos.',
  },
  TI: {
    regex: /^[0-9]{10,11}$/,
    mensaje: 'La tarjeta de identidad debe tener entre 10 y 11 dígitos.',
  },
  CC: {
    regex: /^[0-9]{6,10}$/,
    mensaje: 'La cédula debe tener entre 6 y 10 dígitos (solo números).',
  },
  CE: {
    regex: /^[A-Za-z0-9]{6,15}$/,
    mensaje: 'La cédula de extranjería debe tener entre 6 y 15 caracteres.',
  },
  PASAPORTE: {
    regex: /^[A-Za-z0-9]{6,12}$/,
    mensaje: 'El pasaporte debe tener entre 6 y 12 caracteres alfanuméricos.',
  },
};

/**
 * Valida el número de documento SEGÚN el tipo elegido en el formulario.
 * Lee el tipo de `tipoCampo` (por defecto 'tipoDocumento'). Si aún no hay
 * tipo, o el tipo no está mapeado, no bloquea (otra regla cubre el tipo).
 */
export const documentoPorTipo =
  (
    tipoCampo = 'tipoDocumento',
    reglas: Record<string, ReglaDocumento> = REGLAS_DOCUMENTO,
  ): ValidadorCruzado =>
  (valor, todos) => {
    if (esVacio(valor)) return null; // requerido() cubre el vacío
    const tipo = txt(todos?.[tipoCampo]);
    if (!tipo) return null; // sin tipo aún → no validamos formato
    const regla = reglas[tipo];
    if (!regla) return null; // tipo no mapeado → no bloquea
    return regla.regex.test(txt(valor)) ? null : regla.mensaje;
  };

// ── Fechas ─────────────────────────────────────────────────
/** Fecha de hoy en formato ISO 'YYYY-MM-DD' (comparable como string). */
export const hoy = (): string => new Date().toISOString().slice(0, 10);

/** ISO de hace `n` años desde hoy (útil para acotar nacimientos). */
export const restarAnios = (n: number): string => {
  const d = new Date();
  d.setFullYear(d.getFullYear() - n);
  return d.toISOString().slice(0, 10);
};

export const fechaNoFutura =
  (mensaje = 'La fecha no puede ser futura'): Validador =>
  (valor) =>
    esVacio(valor) || txt(valor) <= hoy() ? null : mensaje;

export const fechaNoPasada =
  (mensaje = 'La fecha no puede ser anterior a hoy'): Validador =>
  (valor) =>
    esVacio(valor) || txt(valor) >= hoy() ? null : mensaje;

/** La fecha debe estar dentro del rango [minISO, maxISO] (inclusive). */
export const fechaEntre =
  (minISO: string, maxISO: string, mensaje?: string): Validador =>
  (valor) => {
    if (esVacio(valor)) return null;
    const v = txt(valor);
    return v >= minISO && v <= maxISO
      ? null
      : mensaje ?? `La fecha debe estar entre ${minISO} y ${maxISO}.`;
  };

/**
 * Fecha de nacimiento realista: no futura y con una edad máxima (por
 * defecto 100 años). Nadie de más de 100 años está matriculado.
 */
export const fechaNacimiento =
  (
    edadMaxima = 100,
    mensaje = 'Fecha de nacimiento inválida (no puede ser futura ni de hace más de 100 años).',
  ): Validador =>
    fechaEntre(restarAnios(edadMaxima), hoy(), mensaje);

// ── Fechas cruzadas (relación entre dos campos) ────────────
/**
 * Esta fecha no puede ser ANTERIOR a la de `otroCampo`.
 * Ej: la fecha límite de una actividad no puede ir antes de su publicación.
 */
export const fechaNoAnteriorA =
  (
    otroCampo: string,
    mensaje = 'La fecha no puede ser anterior a la fecha de referencia.',
  ): ValidadorCruzado =>
  (valor, todos) => {
    const ref = txt(todos?.[otroCampo]);
    const v = txt(valor);
    if (!ref || !v) return null;
    return v >= ref ? null : mensaje;
  };

/** Esta fecha no puede ser POSTERIOR a la de `otroCampo`. */
export const fechaNoPosteriorA =
  (
    otroCampo: string,
    mensaje = 'La fecha no puede ser posterior a la fecha de referencia.',
  ): ValidadorCruzado =>
  (valor, todos) => {
    const ref = txt(todos?.[otroCampo]);
    const v = txt(valor);
    if (!ref || !v) return null;
    return v <= ref ? null : mensaje;
  };

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
 * Valida que este campo coincida con otro del mismo formulario
 * (ej: confirmar contraseña).
 */
export const coincideCon =
  (otroCampo: string, mensaje = 'Los valores no coinciden'): ValidadorCruzado =>
  (valor, todos) =>
    txt(valor) === txt(todos?.[otroCampo]) ? null : mensaje;
