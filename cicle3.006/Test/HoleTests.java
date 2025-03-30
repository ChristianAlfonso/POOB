package Test;
import MaxWellContainer.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class HoleTests {
    private MaxwellContainer container;
    private final int WIDTH = 400;
    private final int HEIGHT = 400;

    @BeforeEach
    public void setUp() {
        container = new MaxwellContainer(HEIGHT, WIDTH);
        container.makeInvisible();
        container.finish();
    }

    @AfterEach
    public void tearDown() {
        if (container != null) {
            container.finish();
            container.makeInvisible();
        }
    }

    // --------------------------
    // PRUEBAS PARA AGUJERO NORMAL
    // --------------------------

    @Test
    public void shouldNotTrapFlyingParticles() {
        container.addHole("normal", 100, 100, 1);
        container.addParticle("flying", "blue", false, 50, 100, 3, 0);
        
        int initialCount = container.particles().length;
        container.start(5);
        
        assertEquals(initialCount, container.particles().length, "No debería atrapar partículas flying");
        assertEquals(1, container.holes("normal")[0][2], "La capacidad debería permanecer igual");
    }

    @Test
    public void shouldNotExceedCapacity() {
        container.addHole("normal", 100, 100, 1);
        container.addParticle("normal", "black", true, 80, 100, 3, 0);
        //container.addParticle("normal", "green", true, 70, 100, 3, 0); // Otra partícula
        
        container.start(50);
        System.out.println(container.particles().length);
        assertEquals(1, container.particles().length, "Solo debería atrapar una partícula");
    }

    // --------------------------
    // PRUEBAS PARA AGUJERO MÓVIL
    // --------------------------


    @Test
    public void shouldMaintainCapacityAfterStopping() {
        container.addHole("movil", WIDTH-20, HEIGHT-20, 2); // Cerca del borde
        container.addParticle("normal", "red", false, -50, -HEIGHT+20, 3, 0);
        container.addParticle("normal", "blue", true, 50, -HEIGHT+20, -3, 0);
        
        container.start(5); // Llega al borde y atrapa
        assertEquals(2, container.holes("movil")[0][2], "Debería mantener capacidad correcta");
    }

    // --------------------------
    // PRUEBAS COMUNES
    // --------------------------

    @Test
    public void shouldNotAddHoleWithInvalidPosition() {
        container.addHole("normal", -WIDTH-10, 100, 1); // X fuera de límites
        assertFalse(container.ok(), "No debería permitir posición X inválida");
        
        container.addHole("normal", 100, -10, 1); // Y fuera de límites
        assertFalse(container.ok(), "No debería permitir posición Y inválida");
    }

    @Test
    public void shouldNotAddHoleWithZeroOrNegativeCapacity() {
        container.addHole("normal", 100, 100, 0);
        assertFalse(container.ok(), "No debería permitir capacidad 0");
        
        container.addHole("movil", 100, 100, -1);
        assertFalse(container.ok(), "No debería permitir capacidad negativa");
    }

    @Test
    public void shouldReturnCorrectHoleData() {
        container.addHole("normal", 100, 150, 2);
        container.addHole("movil", 200, 250, 1);
        
        int[][] normalHoles = container.holes("normal");
        int[][] movilHoles = container.holes("movil");
        
        assertEquals(1, normalHoles.length, "Debería haber 1 agujero normal");
        assertEquals(100, normalHoles[0][0], "Posición X incorrecta");
        assertEquals(150, normalHoles[0][1], "Posición Y incorrecta");
        assertEquals(2, normalHoles[0][2], "Capacidad incorrecta");
        
        assertEquals(1, movilHoles.length, "Debería haber 1 agujero móvil");
    }
}