package MaxWellContainer;
import Shapes.*;
/**
 * Clase que representa un agujero móvil que se desplaza hasta llegar a un borde.
 * Hereda de la clase Hole y añade funcionalidad de movimiento.
 */
public class Movil extends Hole {
    private int speedX; // Velocidad en el eje X (siempre 1)
    private int speedY; // Velocidad en el eje Y (siempre 1)
    private boolean hasStopped; // Indica si el agujero ha llegado a un borde

    /**
     * Constructor para crear un agujero móvil.
     *
     * @param x         La posición inicial en el eje X (coordenada interna).
     * @param y         La posición inicial en el eje Y.
     * @param color     El color del agujero.
     * @param diameter  El diámetro del agujero.
     * @param capacity  La capacidad máxima de partículas que el agujero puede atrapar.
     * @param originalX La coordenada X original (puede ser negativa).
     */
    public Movil(int x, int y, String color, int diameter, int capacity, int originalX) {
        super(x, y, color, diameter, capacity, originalX);
        this.speedX = 1; // Velocidad fija en X
        this.speedY = 1; // Velocidad fija en Y
        this.hasStopped = false;
    }

    /**
     * Mueve el agujero móvil dentro del contenedor.
     * Se detiene cuando llega a cualquier borde del contenedor.
     *
     * @param containerWidth  El ancho del contenedor.
     * @param containerHeight La altura del contenedor.
     */
    public void move(int containerWidth, int containerHeight) {
        if (hasStopped) return;
    
        int newX = getXPosition() + speedX;
        int newY = getYPosition() + speedY;

        // Verificar bordes con tamaño del agujero
        boolean hitBorder = 
        newX <= 0 || 
        newX + getDiameter() >= containerWidth || 
        newY <= 0 || 
        newY + getDiameter() >= containerHeight;

        if (hitBorder) {
            hasStopped = true;
            newX = Math.max(0, Math.min(newX, containerWidth - getDiameter()));
            newY = Math.max(0, Math.min(newY, containerHeight - getDiameter()));
        }

        setXPosition(newX);
        setYPosition(newY);
    
        draw(); // Dibuja el agujero en su nueva posición
    }

    /**
     * Indica si el agujero móvil ha dejado de moverse por llegar a un borde.
     *
     * @return true si ha llegado a un borde y se ha detenido, false en caso contrario.
     */
    public boolean hasStopped() {
        return hasStopped;
    }

    /**
     * Sobrescribe el método para atrapar partículas, considerando el movimiento.
     *
     * @param particle La partícula que se intenta atrapar.
     */
    @Override
    public void trapParticle(Particle particle) {
        if (!hasStopped) {
            // Opcional: Podríamos decidir no atrapar partículas mientras se mueve
            // return;
        }
        super.trapParticle(particle); // Llama al método de la clase padre
    }
}
