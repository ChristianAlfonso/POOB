package Test;
import MaxWellContainer.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Clase de pruebas para la clase MaxwellContainer.
 * Esta clase prueba el comportamiento de los métodos de MaxwellContainer.
 */
public class MaxwellContainerTest {
    private MaxwellContainer container;

    /**
     * Configura el entorno antes de cada prueba.
     * Inicializa el contenedor y lo hace invisible.
     */
    @BeforeEach
    public void setUp() {
        // Inicializa el contenedor y asegura que esté invisible.
        container = new MaxwellContainer(900, 1200, 500, 10, 10, new int[][]{});
        container.makeInvisible(); // Oculta el contenedor.
        container.finish(); // Detiene el simulador (si es necesario).
    }

    /**
     * Limpia el entorno después de cada prueba.
     * Finaliza la simulación y asegura que el contenedor esté invisible.
     */
    @AfterEach
    public void tearDown() {
        // Limpia el contenedor después de cada prueba.
        if (container != null) {
            container.finish(); // Finaliza la simulación y libera recursos.
            container.makeInvisible(); // Asegura que el contenedor esté invisible.
            container.finish(); // Detiene el simulador (si es necesario).
        }
    }

    // Pruebas para el método addParticle

    /**
     * Prueba que se pueda agregar una partícula válida.
     * Verifica que la última acción fue exitosa.
     */
    @Test
    public void shouldAddParticleWhenValid() {
        // Debería agregar una partícula válida (roja en el lado derecho).
        container.addParticle("red", true, 700, 300, 1, -1);
        assertTrue(container.ok()); // Verifica que la última acción fue exitosa.
    }

    /**
     * Prueba que no se pueda agregar una partícula en una posición inválida.
     * Verifica que la última acción falló.
     */
    @Test
    public void shouldNotAddParticleWhenInvalidContainer() {
        // No debería agregar una partícula roja en el lado izquierdo (inválido).
        container.addParticle("red", true, -100, 300, 1, -1);
        assertFalse(container.ok()); // Verifica que la última acción falló.
    }

    // Pruebas para el método delParticle

    /**
     * Prueba que se pueda eliminar una partícula existente.
     * Verifica que la última acción fue exitosa.
     */
    @Test
    public void shouldDeleteParticleWhenExists() {
        // Agrega una partícula y luego la elimina.
        container.addParticle("blue", false, -100, 300, 1, -1);
        container.delParticle("blue");
        assertTrue(container.ok()); // Verifica que la última acción fue exitosa.
    }

    /**
     * Prueba que no se pueda eliminar una partícula que no existe.
     * Verifica que la última acción falló.
     */
    @Test
    public void shouldNotDeleteParticleWhenNotExists() {
        // Intenta eliminar una partícula que no existe.
        container.delParticle("green");
        assertFalse(container.ok()); // Verifica que la última acción falló.
    }

    // Pruebas para el método addDemon

    /**
     * Prueba que se pueda agregar un demonio en una posición válida.
     * Verifica que la última acción fue exitosa.
     */
    @Test
    public void shouldAddDemonWhenValid() {
        // Debería agregar un demonio en una posición válida.
        container.addDemon(400);
        assertTrue(container.ok()); // Verifica que la última acción fue exitosa.
    }

    /**
     * Prueba que no se pueda agregar un demonio en una posición inválida.
     * Verifica que la última acción falló.
     */
    @Test
    public void shouldNotAddDemonWhenInvalidPosition() {
        // No debería agregar un demonio en una posición inválida (fuera del contenedor).
        container.addDemon(-100);
        assertFalse(container.ok()); // Verifica que la última acción falló.
    }

    // Pruebas para el método addHole

    /**
     * Prueba que se pueda agregar un agujero en una posición válida.
     * Verifica que la última acción fue exitosa.
     */
    @Test
    public void shouldAddHoleWhenValid() {
        // Debería agregar un agujero en una posición válida.
        container.addHole(200, 200, 5);
        assertTrue(container.ok()); // Verifica que la última acción fue exitosa.
    }

    /**
     * Prueba que no se pueda agregar un agujero con capacidad inválida.
     * Verifica que la última acción falló.
     */
    @Test
    public void shouldNotAddHoleWhenInvalidCapacity() {
        // No debería agregar un agujero con capacidad inválida (menor o igual a 0).
        container.addHole(200, 200, 0);
        assertFalse(container.ok()); // Verifica que la última acción falló.
    }

    // Pruebas para el método start

    /**
     * Prueba que se pueda iniciar la simulación con un número válido de ticks.
     * Verifica que la última acción fue exitosa.
     */
    @Test
    public void shouldStartSimulationWhenValid() {
        // Debería iniciar la simulación sin errores.
        container.start(10); // Ejecuta 10 ticks de simulación.
        assertTrue(container.ok()); // Verifica que la última acción fue exitosa.
    }

    /**
     * Prueba que no se pueda iniciar la simulación con un número inválido de ticks.
     * Verifica que la última acción falló.
     */
    @Test
    public void shouldNotStartSimulationWhenInvalidTicks() {
        // No debería iniciar la simulación con un número inválido de ticks (negativo).
        container.start(-10);
        assertFalse(container.ok()); // Verifica que la última acción falló.
    }

    // Pruebas para el método isGoal

    /**
     * Prueba que se cumpla la condición de victoria.
     * Verifica que el método isGoal() retorne true cuando se cumple la condición.
     */

    @Test
    public void shouldReturnTrueWhenGoalAchieved() {
        // Configura el contenedor para que se cumpla la condición de victoria.
        container.addParticle("blue", false, 100, 300, 1, -1); // Partícula azul en el lado derecho.
        container.addParticle("red", true, -500, 300, 1, -1);   // Partícula roja en el lado izquierdo.
        assertTrue(container.isGoal()); // Verifica que se cumple la condición de victoria.
    }

    /**
     * Prueba que no se cumpla la condición de victoria.
     * Verifica que el método isGoal() retorne false cuando no se cumple la condición.
     */
    @Test
    public void shouldNotReturnTrueWhenGoalNotAchieved() {
        // Configura el contenedor para que no se cumpla la condición de victoria.
        container.addParticle("blue", false, 100, 300, 1, -1); // Partícula azul en el lado izquierdo.
        container.addParticle("red", true, 700, 300, 1, -1);   // Partícula roja en el lado derecho.
        assertFalse(container.isGoal()); // Verifica que no se cumple la condición de victoria.
    }

    // Pruebas para el método demons

    /**
     * Prueba que se devuelvan las posiciones de los demonios cuando existen.
     * Verifica que la última acción fue exitosa.
     */
    @Test
    public void shouldReturnDemonPositionsWhenDemonsExist() {
        // Agrega demonios y verifica que se devuelven sus posiciones correctamente.
        container.addDemon(400);
        container.addDemon(500);
        int[] positions = container.demons();
        assertEquals(2, positions.length); // Verifica que hay 2 demonios.
        assertTrue(container.ok()); // Verifica que la última acción fue exitosa.
    }

    /**
     * Prueba que no se devuelvan posiciones si no hay demonios.
     * Verifica que la última acción fue exitosa.
     */
    @Test
    public void shouldNotReturnDemonPositionsWhenNoDemons() {
        // Verifica que no se devuelven posiciones si no hay demonios.
        int[] positions = container.demons();
        assertEquals(0, positions.length); // Verifica que no hay demonios.
        assertTrue(container.ok()); // Verifica que la última acción fue exitosa.
    }

    // Pruebas para el método particles

    /**
     * Prueba que se devuelvan los datos de las partículas cuando existen.
     * Verifica que la última acción fue exitosa.
     */
    @Test
    public void shouldReturnParticleDataWhenParticlesExist() {
        // Agrega partículas y verifica que se devuelven sus datos correctamente.
        container.addParticle("blue", false, -100, 300, 1, -1);
        container.addParticle("red", true, 100, 300, 1, -1);
        int[][] particlesData = container.particles();
        assertEquals(2, particlesData.length); // Verifica que hay 2 partículas.
        assertTrue(container.ok()); // Verifica que la última acción fue exitosa.
    }

    /**
     * Prueba que no se devuelvan datos si no hay partículas.
     * Verifica que la última acción fue exitosa.
     */
    @Test
    public void shouldNotReturnParticleDataWhenNoParticles() {
        // Verifica que no se devuelven datos si no hay partículas.
        int[][] particlesData = container.particles();
        assertEquals(0, particlesData.length); // Verifica que no hay partículas.
        assertTrue(container.ok()); // Verifica que la última acción fue exitosa.
    }

    // Pruebas para el método holes

    /**
     * Prueba que se devuelvan los datos de los agujeros cuando existen.
     * Verifica que la última acción fue exitosa.
     */
    @Test
    public void shouldReturnHoleDataWhenHolesExist() {
        // Agrega agujeros y verifica que se devuelven sus datos correctamente.
        container.addHole(200, 200, 5);
        container.addHole(300, 300, 3);
        int[][] holesData = container.holes();
        assertEquals(2, holesData.length); // Verifica que hay 2 agujeros.
        assertTrue(container.ok()); // Verifica que la última acción fue exitosa.
    }

    /**
     * Prueba que no se devuelvan datos si no hay agujeros.
     * Verifica que la última acción fue exitosa.
     */
    @Test
    public void shouldNotReturnHoleDataWhenNoHoles() {
        // Verifica que no se devuelven datos si no hay agujeros.
        int[][] holesData = container.holes();
        assertEquals(0, holesData.length); // Verifica que no hay agujeros.
        assertTrue(container.ok()); // Verifica que la última acción fue exitosa.
    }

    // Pruebas para el método makeVisible

    /**
     * Prueba que se pueda hacer visible el contenedor.
     * Verifica que la última acción fue exitosa.
     */
    @Test
    public void shouldMakeContainerVisible() {
        // Debería hacer visible el contenedor.
        container.makeVisible();
        assertTrue(container.ok()); // Verifica que la última acción fue exitosa.
    }

    // Pruebas para el método makeInvisible

    /**
     * Prueba que se pueda hacer invisible el contenedor.
     * Verifica que la última acción fue exitosa.
     */
    @Test
    public void shouldMakeContainerInvisible() {
        // Debería hacer invisible el contenedor.
        container.makeInvisible();
        assertTrue(container.ok()); // Verifica que la última acción fue exitosa.
    }

    // Pruebas para el método finish

    /**
     * Prueba que se pueda finalizar la simulación.
     * Verifica que la última acción fue exitosa.
     */
    @Test
    public void shouldFinishSimulation() {
        // Debería finalizar la simulación y limpiar el contenedor.
        container.finish();
        assertTrue(container.ok()); // Verifica que la última acción fue exitosa.
    }

    // Pruebas para el método ok

    /**
     * Prueba que se retorne true si la última acción fue exitosa.
     */
    @Test
    public void shouldReturnTrueWhenLastActionSuccessful() {
        // Debería retornar true si la última acción fue exitosa.
        container.addParticle("blue", false, -100, 300, 1, -1);
        assertTrue(container.ok()); // Verifica que la última acción fue exitosa.
    }

    /**
     * Prueba que no se retorne true si la última acción falló.
     */
    @Test
    public void shouldNotReturnTrueWhenLastActionFailed() {
        // No debería retornar true si la última acción falló.
        container.addParticle("red", true, -100, 300, 1, -1); // Intenta agregar una partícula inválida.
        assertFalse(container.ok()); // Verifica que la última acción falló.
    }

    /**
     * Prueba que no se puedan agregar partículas en el área del jugador contrario.
     */
    @Test
    public void shouldNotAddParticleInOtherPlayersArena() {
        // Intenta agregar una partícula azul en el lado derecho (inválido).
        container.addParticle("blue", false, 700, 300, 1, -1);
        assertFalse(container.ok()); // Verifica que la última acción falló.

        // Intenta agregar una partícula roja en el lado izquierdo (inválido).
        container.addParticle("red", true, -100, 300, 1, -1);
        assertFalse(container.ok()); // Verifica que la última acción falló.
    }
}
