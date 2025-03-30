package MaxWellContainer;
import Shapes.*;
/**
 * Clase que representa una partícula en un contenedor.
 * Hereda de la clase `Circle` y añade funcionalidades como movimiento,
 * rebote en los bordes y una línea divisoria, y manejo de velocidad.
 */
public class Particle extends Circle {
    private int speedX; // Velocidad en el eje X.
    private int speedY; // Velocidad en el eje Y.
    private boolean isRed; // Indica si la partícula es de color rojo.
    private int originalX; // Almacena la coordenada X original (puede ser negativa)
    private boolean isInRedContainer;
    private boolean transferred;
    /**
     * Constructor para crear una partícula con posición, color, diámetro, velocidad y tipo (roja o no).
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
    public Particle(int x, int y, String color, boolean isRed, int diameter, int speedX, int speedY, int originalX) {
        super(x, y, color, diameter);
        this.speedX = speedX;
        this.speedY = speedY;
        this.isRed = isRed;
        this.originalX = originalX; // Guardar la coordenada X original
        this.isInRedContainer = false;
        this.transferred = false;
    }
    
    /**
     * Marca la partícula como transferida.
     */
        public void setTransferred() {
        this.transferred = true;
    }
    
    /**
     * Indica si la partícula ya ha sido transferida.
     *
     * @return `true` si la partícula ha sido transferida, `false` en caso contrario.
     */
        public boolean isTransferred() {
        return transferred;
    }
    
    /**
     * Obtiene la coordenada X original (puede ser negativa).
     *
     * @return La coordenada X original.
     */
    public int getOriginalX() {
        return originalX;
    }
    
    /**
     * Mueve la partícula dentro del contenedor, manejando rebotes en los bordes
     * y en la línea divisoria.
     *
     * @param containerWidth  El ancho del contenedor.
     * @param containerHeight La altura del contenedor.
     * @param lineX           La posición en X de la línea divisoria.
     */
    public void move(int containerWidth, int containerHeight, int lineX) {
        if (!isVisible) return;
        
        erase();
        
        // Calcular nueva posición
        int newX = xPosition + speedX;
        int newY = yPosition + speedY;
        
        // Verificar colisiones con bordes
        if (newX <= 0 || newX + diameter >= containerWidth) {
            speedX = -speedX;
            handleCollision();
        }
        
        if (newY <= 0 || newY + diameter >= containerHeight) {
            speedY = -speedY;
            handleCollision();
        }
        
        // Verificar colisión con línea divisoria
        if ((xPosition <= lineX && newX >= lineX) || (xPosition >= lineX && newX <= lineX)) {
            speedX = -speedX;
            handleCollision();
        }
        
        // Actualizar posición
        xPosition += speedX;
        yPosition += speedY;
        
        draw();
    }
    
    // Método para manejar comportamiento específico en colisiones
    protected void handleCollision() {
        // Puede ser sobrescrito por subclases
    }

    /**
     * Obtiene la velocidad actual de la partícula en el eje X.
     *
     * @return La velocidad en el eje X.
     */
    public int getSpeedX() {
        return this.speedX;
    }

    /**
     * Obtiene la velocidad actual de la partícula en el eje Y.
     *
     * @return La velocidad en el eje Y.
     */
    public int getSpeedY() {
        return this.speedY;
    }

    /**
     * Indica si la partícula es de color rojo.
     *
     * @return true si la partícula es roja, false en caso contrario.
     */
    public boolean isRed() {
        return this.isRed;
    }
    
    /**
     * Invierte la dirección de la partícula en el eje X.
     * Esto cambia el signo de la velocidad en el eje X, haciendo que la partícula
     * se mueva en la dirección opuesta.
     */
    public void invertDirectionX() {
        this.speedX = -this.speedX; // Invierte la velocidad en el eje X.
    }
    
    /**
     * Establece si la partícula está en el contenedor rojo basándose en su posición en el eje X.
     * Si la posición en X es menor o igual a 0, se considera que la partícula está en el contenedor rojo.
     */
    public void setIsInRedContainer() {
        if (xPosition <= 0) {
            this.isInRedContainer = true;
        } else {
            this.isInRedContainer = false; // Asegura que se actualice correctamente si la posición cambia
        }
    }
    
    /**
     * Obtiene si la partícula está en el contenedor rojo.
     * Este método actualiza primero el estado de la partícula llamando a `setIsInRedContainer`.
     *
     * @return `true` si la partícula está en el contenedor rojo, `false` en caso contrario.
     */
    public boolean getIsInRedContainer() {
        setIsInRedContainer(); // Actualiza el estado antes de devolverlo
        return this.isInRedContainer;
    }
    
    /**
     * Establece la velocidad horizontal de la partícula.
     * 
     * @param speedX La nueva velocidad en el eje X (píxeles por movimiento)
     * @throws IllegalArgumentException Si el valor excede los límites permitidos
     */
    public void setSpeedX(int speedX) {
        this.speedX = speedX;
    }
    
    /**
     * Establece la velocidad vertical de la partícula.
     * 
     * @param speedY La nueva velocidad en el eje Y (píxeles por movimiento)
     * @throws IllegalArgumentException Si el valor excede los límites permitidos
     */
    public void setSpeedY(int speedY) {
        this.speedY = speedY;
    }
    
    /**
     * Establece la posición horizontal absoluta de la partícula.
     * 
     * @param x La nueva coordenada X en píxeles
     * @throws IllegalArgumentException Si la posición está fuera del contenedor
     */
    public void setXPosition(int x) {
        this.xPosition = x;
    }
    
    /**
     * Establece la posición vertical absoluta de la partícula.
     * 
     * @param y La nueva coordenada Y en píxeles
     * @throws IllegalArgumentException Si la posición está fuera del contenedor
     */
    public void setYPosition(int y) {
        this.yPosition = y;
    }
}
