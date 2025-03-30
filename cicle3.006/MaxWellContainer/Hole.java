package MaxWellContainer;
import Shapes.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase que representa un agujero en el contenedor de Maxwell.
 * Hereda de la clase Circle y añade funcionalidades para atrapar partículas
 * y gestionar su capacidad de almacenamiento.
 */
public class Hole extends Circle {
    private int capacity; // Capacidad máxima de partículas que el agujero puede atrapar.
    private int remainingCapacity; // Capacidad restante del agujero.
    private List<Particle> trappedParticles; // Lista de partículas atrapadas en el agujero.
    private int originalX; // Almacena la coordenada X original (puede ser negativa)

    /**
     * Constructor para crear un agujero con posición, color, diámetro y capacidad específicos.
     *
     * @param x La posición inicial en el eje X (coordenada interna ajustada para visualización)
     * @param y La posición inicial en el eje Y
     * @param color El color del agujero
     * @param diameter El diámetro del agujero
     * @param capacity La capacidad máxima de partículas que puede contener
     * @param originalX La coordenada X original (puede ser negativa, representa la posición física real)
     */
    public Hole(int x, int y, String color, int diameter, int capacity, int originalX) {
        super(x, y, color, diameter);
        this.capacity = capacity;
        this.remainingCapacity = capacity;
        this.trappedParticles = new ArrayList<>();
        this.originalX = originalX;
    }

    /**
     * Obtiene la coordenada X original (puede ser negativa).
     * Esta coordenada representa la posición física real en el contenedor.
     *
     * @return La coordenada X original (puede ser negativa)
     */
    public int getOriginalX() {
        return originalX;
    }

    /**
     * Intenta atrapar una partícula si el agujero tiene capacidad disponible
     * y la partícula está dentro de su área de influencia.
     * No atrapa partículas que implementen la interfaz Flying.
     *
     * @param particle La partícula que se intenta atrapar
     */
    public void trapParticle(Particle particle) {
        // No atrapar partículas voladoras
        if (particle instanceof Flying) return;
        
        if (trappedParticles.size() < capacity) {
            double distance = Math.sqrt(Math.pow(particle.getXPosition() - xPosition, 2) 
                                + Math.pow(particle.getYPosition() - yPosition, 2));
                
            if (distance <= diameter / 2) {
                trappedParticles.add(particle);
                remainingCapacity--;
                particle.makeInvisible();
            }
        }
    }

    /**
     * Obtiene la capacidad restante del agujero.
     *
     * @return La cantidad de partículas que aún puede atrapar
     */
    public int getRemainingCapacity() {
        return remainingCapacity;
    }
    
    /**
     * Obtiene la posición X actual del agujero (coordenada ajustada para visualización)
     *
     * @return La coordenada X actual
     */
    public int getXPosition() {
        return this.xPosition;
    }
    
    /**
     * Obtiene la posición Y actual del agujero
     *
     * @return La coordenada Y actual
     */
    public int getYPosition() {
        return this.yPosition;
    }
    
    /**
     * Establece una nueva posición X para el agujero
     *
     * @param x La nueva coordenada X
     */
    public void setXPosition(int x) {
        this.xPosition = x;
    }
    
    /**
     * Establece una nueva posición Y para el agujero
     *
     * @param y La nueva coordenada Y
     */
    public void setYPosition(int y) {
        this.yPosition = y;
    }
}
