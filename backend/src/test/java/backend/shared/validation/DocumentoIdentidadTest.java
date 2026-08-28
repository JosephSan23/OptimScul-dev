package backend.shared.validation;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Anatomía de una prueba:
 *   - @Test           -> marca un método como una prueba ejecutable.
 *   - @DisplayName     -> nombre legible que sale en el reporte.
 *   - assertThat(...)  -> "afirmación" (AssertJ): comprueba que algo se cumple.
 *                         Si no se cumple, la prueba falla (rojo).
 */
class DocumentoIdentidadTest {

    @Test
    @DisplayName("CC válida: entre 6 y 10 dígitos numéricos")
    void cedulaValida() {
        assertThat(DocumentoIdentidad.esValido("CC", "123456")).isTrue();      // 6 dígitos (mínimo)
        assertThat(DocumentoIdentidad.esValido("CC", "1234567890")).isTrue();  // 10 dígitos (máximo)
    }

    @Test
    @DisplayName("CC inválida: muy corta, muy larga o con letras")
    void cedulaInvalida() {
        assertThat(DocumentoIdentidad.esValido("CC", "12345")).isFalse();       // 5 dígitos
        assertThat(DocumentoIdentidad.esValido("CC", "12345678901")).isFalse(); // 11 dígitos
        assertThat(DocumentoIdentidad.esValido("CC", "12345A")).isFalse();      // CC no admite letras
    }

    @Test
    @DisplayName("CE sí admite letras (6 a 15 caracteres)")
    void cedulaExtranjeriaAlfanumerica() {
        assertThat(DocumentoIdentidad.esValido("CE", "ABC123")).isTrue();
        assertThat(DocumentoIdentidad.esValido("CE", "AB12")).isFalse(); // solo 4 caracteres
    }

    @Test
    @DisplayName("Tipo o número vacío NO bloquea (otras validaciones lo cubren)")
    void vaciosNoBloquean() {
        assertThat(DocumentoIdentidad.esValido(null, "123456")).isTrue();
        assertThat(DocumentoIdentidad.esValido("CC", null)).isTrue();
        assertThat(DocumentoIdentidad.esValido("CC", "   ")).isTrue();
    }

    @Test
    @DisplayName("Un tipo no reconocido no bloquea")
    void tipoDesconocido() {
        assertThat(DocumentoIdentidad.esValido("XX", "123")).isTrue();
    }

    @ParameterizedTest
    @CsvSource({
            "TI, 1234567890, true",
            "TI, 123456789,  false",
            "RC, 12345678,   true",
            "PASAPORTE, AB1234, true"
    })
    @DisplayName("Varios tipos y números en una sola prueba")
    void casosVarios(String tipo, String numero, boolean esperado) {
        assertThat(DocumentoIdentidad.esValido(tipo, numero)).isEqualTo(esperado);
    }

    @Test
    @DisplayName("El mensaje de error corresponde al tipo de documento")
    void mensajePorTipo() {
        assertThat(DocumentoIdentidad.mensaje("CC")).contains("cédula");
        assertThat(DocumentoIdentidad.mensaje("XX")).contains("inválido"); // mensaje genérico
    }
}