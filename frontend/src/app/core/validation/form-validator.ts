/* ============================================================
   form-validator.ts · OptimScul — MOTOR DE VALIDACIÓN POR ESQUEMA
   Recibe el objeto del formulario y un esquema { campo: [reglas] }
   y devuelve un mapa { campo: mensaje } con SOLO los campos que
   fallaron (se queda con el primer error de cada campo).

   Uso típico en un componente:

     import { validarEsquema, hayErrores, Esquema } from '...';
     import { requerido, documento, correo } from '...';

     esquema: Esquema = {
       tipoDocumento:   [requerido('Selecciona el tipo de documento')],
       numeroDocumento: [requerido(), documento()],
       correo:          [correo()],
     };

     guardar() {
       this.errores = validarEsquema(this.form, this.esquema);
       if (hayErrores(this.errores)) return;   // no envía
       // ... llamada al servicio
     }
   ============================================================ */

import { Validador, ValidadorCruzado } from './validators';

/** Un campo puede tener validadores simples o cruzados (que ven todo el form). */
export type ReglaCampo = Validador | ValidadorCruzado;

export type Esquema = Record<string, ReglaCampo[]>;

export type ErroresForm = Record<string, string>;

/**
 * Valida `valores` contra `esquema`. Devuelve el mapa de errores.
 * Para cada campo se aplican las reglas en orden y se retiene el
 * PRIMER mensaje de error (el más relevante para el usuario).
 */
export function validarEsquema(
  valores: Record<string, any>,
  esquema: Esquema,
): ErroresForm {
  const errores: ErroresForm = {};

  for (const campo of Object.keys(esquema)) {
    const reglas = esquema[campo] ?? [];
    const valor = valores?.[campo];

    for (const regla of reglas) {
      // Los validadores cruzados aceptan (valor, todos); los simples ignoran el 2º arg.
      const mensaje = (regla as ValidadorCruzado)(valor, valores);
      if (mensaje) {
        errores[campo] = mensaje;
        break; // primer error de este campo → siguiente campo
      }
    }
  }

  return errores;
}

/** true si el mapa de errores tiene al menos un campo con error. */
export function hayErrores(errores: ErroresForm): boolean {
  return Object.keys(errores).length > 0;
}

/**
 * Valida un solo campo (útil para validar "al salir" / on blur).
 * Devuelve el mensaje o null.
 */
export function validarCampo(
  campo: string,
  valores: Record<string, any>,
  esquema: Esquema,
): string | null {
  const reglas = esquema[campo] ?? [];
  const valor = valores?.[campo];
  for (const regla of reglas) {
    const mensaje = (regla as ValidadorCruzado)(valor, valores);
    if (mensaje) return mensaje;
  }
  return null;
}
