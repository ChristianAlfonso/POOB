package Test;
import MaxWellContainer.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Clase de pruebas para la clase MaxwellContest.
 * Esta prueba verifica el comportamiento del método `solve` cuando las partículas ya están ordenadas.
 */
public class MaxwellContestCTest {

    /**
     * Prueba que verifica si el método `solve` maneja correctamente el caso en el que las partículas ya están ordenadas.
     * En este caso, el tiempo necesario para ordenar las partículas debería ser cero.
     */
    @Test
    public void testAlreadySortedParticles() {
        // Datos de entrada
        int w = 5; // Ancho del contenedor
        int h = 5; // Alto del contenedor
        int d = 2; // Posición de la división en el eje Y
        int r = 2; // Número de partículas rojas
        int b = 2; // Número de partículas azules

        // Partículas ya ordenadas:
        // - Partículas rojas (isRed = true) en el lado izquierdo (px < 0)
        // - Partículas azules (isRed = false) en el lado derecho (px > 0)
        int[][] particlesData = {
            {-1, 1, 1, 1},   // Partícula roja en el lado izquierdo
            {-2, 2, -1, 1},  // Partícula roja en el lado izquierdo
            {3, 3, 2, -1},   // Partícula azul en el lado derecho
            {4, 1, 1, 1}     // Partícula azul en el lado derecho
        };

        // Llamar al método solve
        double result = MaxwellContest.solve(w, h, d, r, b, particlesData);

        // Verificar que el tiempo necesario es 0.0 (partículas ya ordenadas)
        assertEquals(0.0f, result, 1e-6); // Tolerancia de 1e-6 para comparar floats
    }
}
