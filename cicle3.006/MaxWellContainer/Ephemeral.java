package MaxWellContainer;
import Shapes.*;
/**
 * Clase que representa una partícula que pierde velocidad en cada colisión
 * y desaparece cuando su velocidad llega a 0,0.
 * Hereda de la clase Particle.
 */
public class Ephemeral extends Particle {
    private boolean isActive; // Indica si la particula no ha desaparecido

    /**
     * Constructor para crear una partícula con colisiones especiales.
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
    public Ephemeral(int x, int y, String color, boolean isRed, int diameter, int speedX, int speedY, int originalX) {
        super(x, y, color, isRed, diameter, speedX, speedY, originalX);
        this.isActive = true;
    }

    /**
     * Mueve la partícula y maneja las colisiones con pérdida de velocidad.
     *
     * @param containerWidth  El ancho del contenedor.
     * @param containerHeight La altura del contenedor.
     * @param lineX           La posición en X de la línea divisoria.
     */
    @Override
    public void move(int containerWidth, int containerHeight, int lineX) {
        if (!isActive) return; // Si la partícula está inactiva, no hacer nada

        erase(); // Borra la partícula de su posición actual.

        // Actualiza la posición de la partícula según su velocidad.
        int newX = getXPosition() + getSpeedX();
        int newY = getYPosition() + getSpeedY();

        boolean collided = false;

        // Verificar colisiones con los bordes
        if (newX <= 0 || newX + getDiameter() >= containerWidth) {
            collided = true;
        }
        if (newY <= 0 || newY + getDiameter() >= containerHeight) {
            collided = true;
        }

        // Verificar colisión con la línea divisoria
        if ((getXPosition() <= lineX && newX >= lineX) ||
            (getXPosition() >= lineX && newX <= lineX)) {
            collided = true;
        }

        // Si hubo colisión, reducir velocidad
        if (collided) {
            reduceSpeed();
        }

        // Mover la partícula (el padre maneja el rebote)
        super.move(containerWidth, containerHeight, lineX);

        // Verificar si la partícula debe desaparecer
        checkIfShouldDisappear();
    }

    /**
     * Reduce la velocidad de la partícula en 1 unidad en cada eje.
     * Si la velocidad es positiva se reduce, si es negativa se aumenta.
     */
    private void reduceSpeed() {
        int newSpeedX = getSpeedX();
        int newSpeedY = getSpeedY();

        if (newSpeedX > 0) {
            newSpeedX--;
        } else {
            newSpeedX++;
        }

        if (newSpeedY > 0) {
            newSpeedY--;
        } else {
            newSpeedY++;
        }

        // Actualizar las velocidades (necesitaríamos setters en la clase padre)
        // Como la clase Particle no tiene setters para speedX y speedY,
        // necesitamos acceder directamente a los campos o modificar la clase padre
        // Para este ejemplo, asumiremos que hay métodos para establecer la velocidad
        setSpeedX(newSpeedX);
        setSpeedY(newSpeedY);
    }

    /**
     * Verifica si la partícula debe desaparecer (velocidad 0,0).
     * Si es así, la marca como inactiva y la borra de la pantalla.
     */
    private void checkIfShouldDisappear() {
        if (getSpeedX() == 0 && getSpeedY() == 0) {
            erase();
            isActive = false;
        }
    }

    /**
     * Indica si la partícula está activa (no ha desaparecido).
     *
     * @return true si la partícula está activa, false si ha desaparecido.
     */
    public boolean isActive() {
        return isActive;
    }

    // Métodos para establecer la velocidad (necesarios porque la clase padre no los tiene)
    // Estos deberían estar en la clase Particle, pero los añadimos aquí
    // En una implementación real, sería mejor añadirlos a la clase padre
    
    /**
     * Establece la velocidad en el eje X.
     * @param speedX La nueva velocidad en el eje X.
     */
    public void setSpeedX(int speedX) {
        // Esto asume que speedX es accesible o hay un método para cambiarlo
        // En una implementación real, la clase Particle debería tener este setter
        // Usamos reflexión para acceder al campo privado de la clase padre
        try {
            java.lang.reflect.Field field = Particle.class.getDeclaredField("speedX");
            field.setAccessible(true);
            field.set(this, speedX);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Establece la velocidad en el eje Y.
     * @param speedY La nueva velocidad en el eje Y.
     */
    public void setSpeedY(int speedY) {
        // Similar al método setSpeedX
        try {
            java.lang.reflect.Field field = Particle.class.getDeclaredField("speedY");
            field.setAccessible(true);
            field.set(this, speedY);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
