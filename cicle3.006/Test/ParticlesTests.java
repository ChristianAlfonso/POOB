package Test;
import MaxWellContainer.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Clase de pruebas para la clase MaxwellContainer.
 * Esta clase prueba el comportamiento de los diferentes tipos de partículas.
 */
public class ParticlesTests {
    private MaxwellContainer container;
    private final int WIDTH = 400;
    private final int HEIGHT = 400;

    /**
     * Configura el entorno antes de cada prueba.
     * Inicializa el contenedor y lo hace invisible.
     */
    @BeforeEach
    public void setUp() {
        container = new MaxwellContainer(HEIGHT, WIDTH);
        container.makeInvisible();
        container.finish();
    }

    /**
     * Limpia el entorno después de cada prueba.
     * Finaliza la simulación y asegura que el contenedor esté invisible.
     */
    @AfterEach
    public void tearDown() {
        if (container != null) {
            container.finish();
            container.makeInvisible();
        }
    }
    
    //Normal//
    
    @Test
    public void shouldBounceVerticallyWhenHittingTopWall() {
        container.addParticle("normal", "blue", true, 50, 5, 0, -2);
        int initialSpeedY = container.particles()[0][3];
        container.start(10);
        assertTrue(container.particles()[0][3] > 0, "Debería invertir velocidad Y al chocar");
        assertNotEquals(initialSpeedY, container.particles()[0][3], "La velocidad Y debería cambiar");
    }
    
    @Test
    public void shouldNotChangeSpeedWithoutCollisions() {
        container.addParticle("normal", "green", false, -100, 200, 1, 1);
        int[] initialVelocities = container.particles()[0].clone();
        container.start(5);
        assertEquals(initialVelocities[2], container.particles()[0][2], "Vx no debería cambiar sin colisiones");
        assertEquals(initialVelocities[3], container.particles()[0][3], "Vy no debería cambiar sin colisiones");
    }
    
    //Flying//
    @Test
    public void shouldIgnoreHolesWhenFlying() {
        container.addHole("normal", 50, 100, 1);
        container.addParticle("flying", "green", true, 40, 100, 3, 0);
        int initialCount = container.particles().length;
        
        container.start(50);
        
        assertEquals(initialCount, container.particles().length, "No debería ser atrapada por agujeros");
    }
    
    @Test
    public void shouldNotChangeSpeedWhenFlyingOverHoles() {
        container.addHole("normal", 100, 100, 1);
        container.addParticle("flying", "blue", true, 90, 100, 2, 2);
        int[] initialVelocities = container.particles()[0].clone();
        container.start(3);
        assertEquals(initialVelocities[2], container.particles()[0][2], "Vx no debería cambiar");
        assertEquals(initialVelocities[3], container.particles()[0][3], "Vy no debería cambiar");
    }

    
    //Rotator//
    @Test
    public void shouldSwapVelocitiesAfterCollision() {
        container.addParticle("rotator", "blue", true, 50, 10, -3, 6);
        int initialVx = container.particles()[0][2];
        int initialVy = container.particles()[0][3];
        container.start(25);
        int[][] particles = container.particles();
        assertEquals(initialVy, particles[0][2], "Vx debería tomar el valor inicial de Vy");
        assertEquals(initialVx, particles[0][3], "Vy debería tomar el valor inicial de Vx");
    }
    
    @Test
    public void shouldNotMaintainOriginalVelocitiesAfterMultipleCollisions() {
        container.addParticle("rotator", "red", false, -50, 100, 2, 2);
        int[] initialVelocities = container.particles()[0].clone();
        container.start(30);
        assertFalse(initialVelocities[2] == container.particles()[0][2] && 
                   initialVelocities[3] == container.particles()[0][3],"Las velocidades no deberían mantenerse igual después de colisiones");
    }
    
    @Test
    public void shouldNotSwapVelocitiesWithoutCollisions() {
        container.addParticle("rotator", "green", false, -100, 200, 1, 1);
        int[] initialVelocities = container.particles()[0].clone();
        container.start(2);
        assertEquals(initialVelocities[2], container.particles()[0][2], "Vx no debería cambiar sin colisiones");
        assertEquals(initialVelocities[3], container.particles()[0][3], "Vy no debería cambiar sin colisiones");
    }
    //Ephymeral//
    @Test
    public void shouldDisappearWhenSpeedReachesZero() {
        container.addParticle("ephemeral", "orange", false, 50, 100, 1, 1);
        
        for (int i = 0; i < 4; i++) {
            container.start(50);
            if (container.particles().length == 0) break;
        }
        
        assertEquals(0, container.particles().length, "Debería desaparecer al llegar a velocidad 0");
    }

    @Test
    public void shouldLoseOneUnitOfSpeedPerCollision() {
        container.addParticle("ephemeral", "purple", false, -50, 10, 3, 3);
        int initialVx = container.particles()[0][2];
        int initialVy = container.particles()[0][3];
        container.start(50);
        int[][] particles = container.particles();
        assertEquals(Math.abs(initialVx) - 1, Math.abs(particles[0][2]), "Debería perder 1 unidad en X");
        assertEquals(Math.abs(initialVy) - 1, Math.abs(particles[0][3]), "Debería perder 1 unidad en Y");
    }
    
    @Test
    public void shouldNotDisappearWithoutCollisions() {
        container.addParticle("ephemeral", "cyan", false, -100, 200, 0, 0);
        container.start(5);
        assertEquals(1, container.particles().length, "No debería desaparecer sin colisiones");
    }

}