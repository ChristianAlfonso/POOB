package MaxWellContainer;
import Shapes.*;
import java.util.List;

/**
 * Clase que representa una partícula que puede volar por encima de los agujeros.
 * Hereda de la clase Particle e ignora las colisiones con los agujeros.
 */
public class Flying extends Particle {
    private boolean isFlying; // Indica si la partícula está actualmente volando (sobre un agujero)
    private double flightAltitude; // Altura a la que vuela sobre los agujeros

    /**
     * Constructor para crear una partícula voladora.
     *
     * @param x           La posición inicial en el eje X (coordenada interna).
     * @param y           La posición inicial en el eje Y.
     * @param color       El color de la partícula.
     * @param isRed       Indica si la partícula es de color rojo.
     * @param diameter    El diámetro de la partícula.
     * @param speedX      La velocidad en el eje X.
     * @param speedY      La velocidad en el eje Y.
     * @param originalX   La coordenada X original (puede ser negativa).
     */
    public Flying(int x, int y, String color, boolean isRed, int diameter, int speedX, int speedY, int originalX) {
        super(x, y, color, isRed, diameter, speedX, speedY, originalX);
        this.isFlying = false;
        this.flightAltitude = diameter * 1.5; // Vuela a 1.5 veces su diámetro por encima
    }

    /**
     * Mueve la partícula, verificando si necesita volar sobre agujeros.
     *
     * @param containerWidth  El ancho del contenedor.
     * @param containerHeight La altura del contenedor.
     * @param lineX           La posición en X de la línea divisoria.
     * @param holes           Lista de agujeros en el contenedor.
     */
    public void move(int containerWidth, int containerHeight, int lineX, List<Hole> holes) {
        erase(); // Borra la partícula de su posición actual

        // Verificar si está sobre un agujero
        checkHoleCollision(holes);

        // Mover según si está volando o no
        if (isFlying) {
            // Movimiento normal pero ignorando agujeros
            super.move(containerWidth, containerHeight, lineX);
            
            // Aterrizar si ya no está sobre un agujero
            if (!isOverHole(holes)) {
                land();
            }
        } else {
            // Movimiento normal
            super.move(containerWidth, containerHeight, lineX);
        }
    }

    /**
     * Verifica si la partícula está sobre un agujero y activa el modo vuelo si es necesario.
     *
     * @param holes Lista de agujeros en el contenedor.
     */
    private void checkHoleCollision(List<Hole> holes) {
        for (Hole hole : holes) {
            if (isOverHole(hole)) {
                takeOff();
                break;
            }
        }
    }

    /**
     * Verifica si la partícula está sobre un agujero específico.
     *
     * @param hole El agujero a verificar.
     * @return true si está sobre el agujero, false en caso contrario.
     */
    private boolean isOverHole(Hole hole) {
        double distance = Math.sqrt(Math.pow(getXPosition() - hole.getXPosition(), 2) + 
                          Math.pow(getYPosition() - hole.getYPosition(), 2));
        return distance <= hole.getDiameter() / 2;
    }

    /**
     * Verifica si la partícula está sobre cualquier agujero de la lista.
     *
     * @param holes Lista de agujeros.
     * @return true si está sobre algún agujero, false en caso contrario.
     */
    private boolean isOverHole(List<Hole> holes) {
        for (Hole hole : holes) {
            if (isOverHole(hole)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Activa el modo vuelo de la partícula.
     */
    private void takeOff() {
        if (!isFlying) {
            isFlying = true;
            // Guardamos la posición Y original antes de volar
            setOriginalYPosition(getYPosition());
            // Elevamos la partícula
            setYPosition((int)(getYPosition() - flightAltitude));
        }
    }

    /**
     * Desactiva el modo vuelo de la partícula.
     */
    private void land() {
        if (isFlying) {
            isFlying = false;
            // Restauramos la posición Y original
            setYPosition(getOriginalYPosition());
        }
    }

    /**
     * Obtiene si la partícula está volando.
     *
     * @return true si está volando, false en caso contrario.
     */
    public boolean isFlying() {
        return isFlying;
    }

    // Métodos adicionales necesarios (asumiendo que los añadimos a la clase Particle)
    // En una implementación real, estos deberían estar en la clase padre
    
    private int originalYPosition; // Almacena la coordenada Y original antes de volar

    /**
     * Establece la posición Y original.
     * @param y La coordenada Y original.
     */
    public void setOriginalYPosition(int y) {
        this.originalYPosition = y;
    }

    /**
     * Obtiene la posición Y original.
     * @return La coordenada Y original.
     */
    public int getOriginalYPosition() {
        return originalYPosition;
    }
}
