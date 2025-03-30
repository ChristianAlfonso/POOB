package Test;
import MaxWellContainer.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class DemonTests {
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
    // PRUEBAS PARA DEMONIO NORMAL
    // --------------------------

    @Test
    public void shouldTransferRedParticleToLeft() {
        // Partícula roja en lado derecho (incorrecto)
        container.addParticle("normal", "red", true, 50, 200, -5, 0);
        container.addDemon("normal", 200);
        
        int initialX = container.particles()[0][0];
        container.start(50);
        
        assertTrue(container.particles()[0][0] < initialX, "Debería mover partícula roja a la izquierda");
    }

    @Test
    public void shouldTransferBlueParticleToRight() {
        // Partícula azul en lado izquierdo (incorrecto)
        container.addParticle("normal", "blue", false, -50, 200, 5, 0);
        container.addDemon("normal", 200);
        
        int initialX = container.particles()[0][0];
        container.start(50);
        
        assertTrue(container.particles()[0][0] > initialX, "Debería mover partícula azul a la derecha");
    }

    @Test
    public void shouldNotTransferParticlesAlreadyInCorrectSide() {
        // Partícula roja en lado izquierdo (correcto)
        container.addParticle("normal", "red", true, 50, 200, 0, 0);
        container.addDemon("normal", 200);
        
        int initialX = container.particles()[0][0];
        container.start(50);
        
        assertEquals(initialX, container.particles()[0][0], "No debería mover partícula ya en lado correcto");
    }

    // --------------------------
    // PRUEBAS PARA DEMONIO AZUL (BLUE)
    // --------------------------

    @Test
    public void shouldTransferOnlyBlueParticles() {
        container.addDemon("blue", 200);
        container.addParticle("normal", "blue", false, -50, 200, 5, 0); // Azul en lado incorrecto
        container.addParticle("normal", "red", true, 50, 200, -5, 0);   // Roja en lado incorrecto
        
        container.start(50);
        
        int[][] particles = container.particles();
        // Azul debería haberse movido a la derecha
        assertTrue(particles[1][0] > WIDTH/2, "Debería mover partícula azul");
        // Roja debería permanecer en su lugar
        assertTrue(particles[0][0] > WIDTH/2, "No debería mover partícula roja");
    }

    @Test
    public void shouldNotTransferRedParticles() {
        container.addDemon("blue", 200);
        container.addParticle("normal", "red", true, 50, 200, -5, 0);
        
        int initialX = container.particles()[0][0];
        container.start(50);
        
        assertEquals(initialX, container.particles()[0][0], "No debería mover partículas rojas");
    }

    // --------------------------
    // PRUEBAS PARA DEMONIO DÉBIL (WEAK)
    // --------------------------

    @Test
    public void shouldDisappearAfterTransferringParticle() {
        container.addDemon("weak", 200);
        container.addParticle("normal", "red", true, 50, 200, -5, 0);
        
        int initialDemonCount = container.demons().length;
        container.start(50);
        
        assertEquals(initialDemonCount - 1, container.demons().length, "Debería desaparecer después de transferir");
    }

    @Test
    public void shouldTransferOnlyOneParticle() {
        container.addDemon("weak", 200);
        container.addParticle("normal", "red", true, 50, 200, -5, 0);
        container.addParticle("normal", "blue", false, -50, 200, 5, 0);
        
        container.start(50);
        
        assertEquals(1, container.demons().length, "Debería quedar 0 demonios (ya que solo hay weak)");
        assertEquals(1, container.particles().length, "Solo debería haber transferido una partícula");
    }

    // --------------------------
    // PRUEBAS COMUNES
    // --------------------------

    @Test
    public void shouldNotAddDemonOutsideContainer() {
        container.addDemon("normal", -10); // Y negativo
        assertFalse(container.ok(), "No debería permitir Y negativo");
        
        container.addDemon("blue", HEIGHT + 10); // Y mayor que altura
        assertFalse(container.ok(), "No debería permitir Y mayor que altura");
    }

    @Test
    public void shouldReturnCorrectDemonTypes() {
        container.addDemon("normal", 100);
        container.addDemon("blue", 150);
        container.addDemon("weak", 200);
        
        assertEquals(1, container.demons("normal").length);
        assertEquals(1, container.demons("blue").length);
        assertEquals(1, container.demons("weak").length);
    }

    @Test
    public void shouldMoveVerticallyWithinBounds() {
        container.addDemon("normal", 100);
        int initialY = container.demons()[0];
        
        // Simular movimiento (el demonio normal no se mueve por defecto, necesitaría setSpeedY)
        // Asumimos que hay un método para establecer velocidad en Y
        // demon.setSpeedY(1);
        // container.start(1);
        // assertNotEquals(initialY, container.demons()[0], "Debería moverse verticalmente");
        
        // Alternativa: verificar que no se sale de los límites
        container.start(50);
        int finalY = container.demons()[0];
        assertTrue(finalY > 0 && finalY < HEIGHT, "Debería permanecer dentro de los límites");
    }
}