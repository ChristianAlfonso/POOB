package MaxWellContainer;

/**
 * Clase que representa un demonio débil que desaparece después de transferir una partícula.
 * Hereda de la clase Demon.
 */
public class Weak extends Demon {
    private boolean hasTransferred; // Indica si ya ha transferido una partícula
    private boolean isActive; // Indica si el demonio sigue activo

    /**
     * Constructor para crear un demonio débil.
     *
     * @param x         La posición inicial en el eje X.
     * @param y         La posición inicial en el eje Y.
     * @param color     El color del demonio.
     * @param width     El ancho del demonio.
     * @param height    La altura del demonio.
     * @param container El contenedor donde se simula la arena.
     */
    public Weak(int x, int y, String color, int width, int height, MaxwellContainer container) {
        super(x, y, color, width, height, container);
        this.hasTransferred = false;
        this.isActive = true;
    }

    /**
     * Transfiere una partícula y luego desaparece.
     * Sobrescribe el método de la clase padre.
     *
     * @param particle La partícula que se intenta transferir.
     */
    @Override
    public void transferParticle(Particle particle) {
        if (!isActive || hasTransferred) {
            return;
        }

        // Transferir la partícula usando la lógica del demonio base
        super.transferParticle(particle);
        
        // Marcar que ya ha transferido una partícula
        this.hasTransferred = true;
        
        // Desaparecer
        disappear();
        
    }

  
    /**
     * Verifica colisión solo si está activo.
     * Sobrescribe el método de la clase padre.
     *
     * @param particle La partícula con la que se verifica la colisión.
     * @return true si hay colisión y el demonio está activo, false en caso contrario.
     */
    @Override
    public boolean collidesWith(Particle particle) {
        return isActive && super.collidesWith(particle);
    }

    /**
     * Hace que el demonio desaparezca.
     */
    private void disappear() {
        makeInvisible();
        erase(); 
        isActive = false;
    }

    /**
     * Indica si el demonio está activo.
     *
     * @return true si el demonio está activo, false si ha desaparecido.
     */
    public boolean isActive() {
        return isActive;
    }
}
