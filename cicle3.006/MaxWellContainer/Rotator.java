package MaxWellContainer;
import Shapes.*;
/**
 * Clase que representa una partícula que intercambia sus velocidades Vx y Vy en cada colisión.
 * Hereda de la clase Particle.
 */
public class Rotator extends Particle {
    
    /**
     * Constructor para crear una partícula rotatoria.
     * @param x         Posición inicial en X (coordenada interna)
     * @param y         Posición inicial en Y
     * @param color     Color de la partícula
     * @param isRed     Indica si es roja
     * @param diameter  Diámetro de la partícula
     * @param speedX    Velocidad en eje X
     * @param speedY    Velocidad en eje Y
     * @param originalX Coordenada X original (puede ser negativa)
     */
    public Rotator(int x, int y, String color, boolean isRed, int diameter, int speedX, int speedY, int originalX) {
        
        super(x, y, color, isRed, diameter, speedX, speedY, originalX);
    }

    /**
     * Mueve la partícula intercambiando velocidades al colisionar.
     * @param containerWidth  Ancho del contenedor
     * @param containerHeight Altura del contenedor
     * @param lineX           Posición de la línea divisoria
     */
    @Override
    public void move(int containerWidth, int containerHeight, int lineX) {
        // Detectar colisión antes de mover
        boolean willCollide = checkCollision(containerWidth, containerHeight, lineX);
        
        if (willCollide) {
            rotateVelocities();
        }
        
        // Ejecutar movimiento normal de la partícula
        super.move(containerWidth, containerHeight, lineX);
    }

    /**
     * Verifica si ocurrirá una colisión en el próximo movimiento.
     * @return true si hay colisión inminente
     */
    private boolean checkCollision(int containerWidth, int containerHeight, int lineX) {
        int nextX = getXPosition() + getSpeedX();
        int nextY = getYPosition() + getSpeedY();
        
        // Colisión con bordes verticales
        boolean verticalCollision = (nextX <= 0) || (nextX + getDiameter() >= containerWidth);
        
        // Colisión con bordes horizontales
        boolean horizontalCollision = (nextY <= 0) || (nextY + getDiameter() >= containerHeight);
        
        // Colisión con línea divisoria
        boolean lineCollision = (getXPosition() <= lineX && nextX >= lineX) ||
                                (getXPosition() >= lineX && nextX <= lineX);
        
        return verticalCollision || horizontalCollision || lineCollision;
    }

    /**
     * Intercambia las velocidades en X e Y
     */
    private void rotateVelocities() {
        int tempSpeed = getSpeedX();
        setSpeedX(getSpeedY());
        setSpeedY(tempSpeed);
    }
}
