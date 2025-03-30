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
public class MaxwellContainerCC1Test {
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
    
    /**
     * No deberia permitir agregar una particula con el color base en la arena del rival
     */
    @Test
    public void accordingGRshouldNotAddParticleInOtherPlayersArena(){
        container.addParticle("blue", false, 700, 300, 1, -1); 
        container.addParticle("red", true, -100, 300, 1, -1);
        assertFalse(container.ok());
    }
}
