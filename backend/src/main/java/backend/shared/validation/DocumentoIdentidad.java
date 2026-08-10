package backend.shared.validation;

import java.util.Map;
import java.util.regex.Pattern;

/**
 * Reglas de formato del número de documento SEGÚN el tipo. Es la contraparte
 * en el servidor de REGLAS_DOCUMENTO del frontend; mantener ambas sincronizadas.
 *
 * - RC (Registro Civil):     8–11 dígitos
 * - TI (Tarjeta Identidad):  10–11 dígitos
 * - CC (Cédula Ciudadanía):  6–10 dígitos
 * - CE (Cédula Extranjería): 6–15 alfanumérico (admite letras)
 * - PASAPORTE:               6–12 alfanumérico (admite letras)
 */
public final class DocumentoIdentidad {

    private DocumentoIdentidad() {
    }

    public record Regla(Pattern patron, String mensaje) {
    }

    private static final Map<String, Regla> REGLAS = Map.of(
            "RC", new Regla(Pattern.compile("[0-9]{8,11}"),
                    "El registro civil debe tener entre 8 y 11 dígitos."),
            "TI", new Regla(Pattern.compile("[0-9]{10,11}"),
                    "La tarjeta de identidad debe tener entre 10 y 11 dígitos."),
            "CC", new Regla(Pattern.compile("[0-9]{6,10}"),
                    "La cédula debe tener entre 6 y 10 dígitos (solo números)."),
            "CE", new Regla(Pattern.compile("[A-Za-z0-9]{6,15}"),
                    "La cédula de extranjería debe tener entre 6 y 15 caracteres."),
            "PASAPORTE", new Regla(Pattern.compile("[A-Za-z0-9]{6,12}"),
                    "El pasaporte debe tener entre 6 y 12 caracteres alfanuméricos."));

    /**
     * true si el número es válido para el tipo dado. Devuelve true (no bloquea)
     * cuando falta el tipo o el número (otras reglas @NotBlank los cubren) o
     * cuando el tipo no está mapeado.
     */
    public static boolean esValido(String tipo, String numero) {
        if (tipo == null || tipo.isBlank()) return true;
        if (numero == null || numero.isBlank()) return true;
        Regla regla = REGLAS.get(tipo);
        if (regla == null) return true;
        return regla.patron().matcher(numero.trim()).matches();
    }

    /** Mensaje de error específico para el tipo (o uno genérico si no se mapea). */
    public static String mensaje(String tipo) {
        Regla regla = tipo == null ? null : REGLAS.get(tipo);
        return regla != null
                ? regla.mensaje()
                : "Número de documento inválido para el tipo seleccionado.";
    }
}
