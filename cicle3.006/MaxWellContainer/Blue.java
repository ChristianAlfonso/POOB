package MaxWellContainer;
import Shapes.*;
/**
 * Clase que representa un demonio azul que solo permite el paso de partículas azules.
 * Hereda de la clase Demon y modifica el comportamiento de transferencia.
 */
public class Blue extends Demon {
    /**
     * Constructor para crear un demonio azul.
     *
     * @param x         La posición inicial en el eje X.
     * @param y         La posición inicial en el eje Y.
     * @param color     El color del demonio (debería ser azul o similar).
     * @param width     El ancho del demonio.
     * @param height    La altura del demonio.
     * @param container El contenedor donde se simula la arena.
     */
    public Blue(int x, int y, String color, int width, int height, MaxwellContainer container) {
        super(x, y, color, width, height, container);
    }

    /**
     * Transfiere una partícula solo si es azul (isRed = false).
     * Sobrescribe el método de la clase padre para agregar esta restricción.
     *
     * @param particle La partícula que se intenta transferir.
     */
    @Override
    public void transferParticle(Particle particle) {
        // Verificar si la partícula ya ha sido transferida
        if (particle.isTransferred()) {
            return;
        }

        // Solo transferir partículas azules 
         if (!particle.isRed() && !particle.isTransferred()) {
            MaxwellContainer container = getContainer();
            int containerWidth = container.getWidth();
            int midPoint = containerWidth; 

            if (particle.getXPosition() < midPoint) {
                // Mover la partícula al lado derecho
                particle.setXPosition(particle.getXPosition() + 20); 
                particle.setSpeedX(Math.abs(particle.getSpeedX())); 
                particle.setTransferred(); 
            }
        }
    }

    /**
     * Verifica colisión con partículas (mismo comportamiento que el demonio base).
     * Se mantiene igual ya que la detección de colisiones no cambia.
     */
    @Override
    public boolean collidesWith(Particle particle) {
        return super.collidesWith(particle);
    }

}
