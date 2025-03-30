package Test;
import MaxWellContainer.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Clase de pruebas para la clase MaxwellContest.
 * Esta clase contiene pruebas unitarias que verifican el comportamiento del método `solve`
 * en diferentes escenarios.
 */
public class MaxwellContestTest {

    /**
     * Prueba que verifica si el método `solve` devuelve 0.0 cuando las partículas ya están ordenadas.
     */
    @Test
    @DisplayName("Should return 0.0 when particles are already sorted")
    public void shouldReturnZeroWhenParticlesAreAlreadySorted() {
        int w = 5, h = 5, d = 2, r = 2, b = 2;
        int[][] particlesData = {
            {-1, 1, 1, 1},   // Partícula roja en el lado izquierdo
            {-2, 2, -1, 1},  // Partícula roja en el lado izquierdo
            {3, 3, 2, -1},   // Partícula azul en el lado derecho
            {4, 1, 1, 1}     // Partícula azul en el lado derecho
        };

        double result = MaxwellContest.solve(w, h, d, r, b, particlesData);
        assertEquals(0.0f, result, 1e-6); // Tolerancia de 1e-6 para comparar floats
    }

    /**
     * Prueba que verifica si el método `solve` devuelve -1.0 cuando una partícula tiene velocidad X igual a 0.
     */
    @Test
    @DisplayName("Should return -1.0 when a particle has zero X velocity")
    public void shouldReturnImpossibleWhenParticleHasZeroXVelocity() {
        int w = 5, h = 5, d = 2, r = 1, b = 0;
        int[][] particlesData = {
            {1, 1, 0, 1} // Partícula roja con vx = 0
        };

        double result = MaxwellContest.solve(w, h, d, r, b, particlesData);
        assertEquals(-1.0f, result, 1e-6); // Debe devolver -1.0f si es imposible
    }

    /**
     * Prueba que verifica si el método `solve` devuelve -1.0 cuando las partículas están fuera del contenedor.
     */
    @Test
    @DisplayName("Should return -1.0 when particles are outside the container")
    public void shouldReturnImpossibleWhenParticlesAreOutsideContainer() {
        int w = 5, h = 5, d = 2, r = 1, b = 1;
        int[][] particlesData = {
            {10, 1, -1, 0},  // Partícula roja fuera del contenedor (px > w)
            {-10, 2, 1, 0}    // Partícula azul fuera del contenedor (px < -w)
        };

        double result = MaxwellContest.solve(w, h, d, r, b, particlesData);
        assertEquals(-1.0f, result, 1e-6); // Debe devolver -1.0f si es imposible
    }

    /**
     * Prueba que verifica si el método `solve` devuelve un tiempo positivo cuando las partículas tienen velocidad Y igual a 0.
     */
    @Test
    @DisplayName("Should return positive time when particles have zero Y velocity")
    public void shouldReturnPositiveTimeWhenParticlesHaveZeroYVelocity() {
        int w = 5, h = 5, d = 2, r = 1, b = 1;
        int[][] particlesData = {
            {-1, 1, -1, 0},   // Partícula roja en el lado izquierdo con vy = 0
            {3, 2, 1, 0}      // Partícula azul en el lado derecho con vy = 0
        };

        double result = MaxwellContest.solve(w, h, d, r, b, particlesData);
        assertNotEquals(-1.0f, result); // No debe ser imposible
        assertTrue(result >= 0.0f); // El tiempo debe ser positivo
    }

    /**
     * Prueba que verifica si el método `solve` devuelve -1.0 cuando las partículas tienen velocidad X negativa y no pueden ser ordenadas.
     */
    @Test
    @DisplayName("Should return -1.0 when particles have negative X velocity and cannot be sorted")
    public void shouldReturnImpossibleWhenParticlesHaveNegativeXVelocityAndCannotBeSorted() {
        int w = 5, h = 5, d = 2, r = 1, b = 1;
        int[][] particlesData = {
            {1, 1, -1, 0},   // Partícula roja en el lado derecho con vx = -1 (debe moverse a la izquierda)
            {3, 2, -1, 0}    // Partícula azul en el lado derecho con vx = -1 (debe moverse a la izquierda, lo cual es incorrecto)
        };

        double result = MaxwellContest.solve(w, h, d, r, b, particlesData);
        assertEquals(-1.0f, result, 1e-6); // Debe ser imposible
    }
}
