package MaxWellContainer;
import Shapes.*;

/**
 * Clase que implementa el demonio de Maxwell, responsable de separar partículas
 * entre los dos lados del contenedor según su color (rojo/azul).
 * 
 * <p>El demonio se representa visualmente como un rectángulo que puede moverse
 * verticalmente y detectar colisiones con partículas para transferirlas al lado
 * correspondiente.</p>
 */
public class Demon extends Rectangle {
    protected int speedY; // Velocidad vertical (píxeles por movimiento)
    protected MaxwellContainer container; // Referencia al contenedor padre

    /**
     * Constructor para crear un demonio en posición específica.
     * 
     * @param x Posición horizontal inicial (coordenada X)
     * @param y Posición vertical inicial (coordenada Y)
     * @param color Color del demonio (usado para representación visual)
     * @param width Ancho del rectángulo del demonio
     * @param height Alto del rectángulo del demonio
     * @param container Referencia al contenedor que contiene este demonio
     */
    public Demon(int x, int y, String color, int width, int height, MaxwellContainer container) {
        super(x, y, color, height, width);
        this.speedY = 0; // Inicialmente estático
        this.container = container;
    }

    /**
     * Mueve el demonio verticalmente dentro del contenedor con efecto rebote.
     * 
     * @param containerHeight Altura total del contenedor para límite de rebote
     */
    public void move(int containerHeight) {
        erase(); // Limpia la posición anterior
        yPosition += speedY; // Actualiza posición
        
        // Lógica de rebote en bordes superior/inferior
        if (yPosition <= 0 || yPosition + height >= containerHeight) {
            speedY = -speedY; // Invierte dirección
        }

        draw(); // Dibuja en nueva posición
    }

    /**
     * Detecta colisión entre el demonio y una partícula.
     * 
     * @param particle Partícula a verificar colisión
     * @return true si hay superposición entre sus áreas, false en caso contrario
     */
    public boolean collidesWith(Particle particle) {
        // Área del demonio (considerando centro como referencia)
        int demonLeft = xPosition - width;
        int demonRight = xPosition + width;
        int demonTop = yPosition - height;
        int demonBottom = yPosition + height;
    
        // Posición y radio de la partícula
        int particleCenterX = particle.getXPosition();
        int particleCenterY = particle.getYPosition();
        int radius = particle.getDiameter() / 2;
    
        // Verifica superposición en ambos ejes
        boolean collisionX = (particleCenterX + radius >= demonLeft) && 
                           (particleCenterX - radius <= demonRight);
        boolean collisionY = (particleCenterY + radius >= demonTop) && 
                           (particleCenterY - radius <= demonBottom);
    
        return collisionX && collisionY;
    }

    /**
     * Transfiere partículas al lado correspondiente según su color.
     * 
     * <p>Las partículas rojas van a la izquierda y las azules a la derecha.
     * Una vez transferidas, no pueden volver al lado original.</p>
     * 
     * @param particle Partícula a transferir
     */
    public void transferParticle(Particle particle) {
        int containerWidth = this.container.getWidth();
        int midPoint = containerWidth; // Punto medio del contenedor
    
        // Evita transferencias múltiples
        if (particle.isTransferred()) {
            return;
        }
    
        // Verifica lado incorrecto según color
        if ((particle.isRed() && particle.getXPosition() >= midPoint) || 
            (!particle.isRed() && particle.getXPosition() <= midPoint)) {
            
            // Mueve 15px hacia el lado correcto
            int newX = particle.isRed() ? 
                particle.getXPosition() - 15 : 
                particle.getXPosition() + 15;
                
            particle.moveTo(newX, particle.getYPosition());
    
            // Invierte dirección horizontal
            particle.invertDirectionX();
    
            // Marca como transferida
            particle.setTransferred();
        }
    }

    /**
     * Obtiene la posición vertical actual del demonio.
     * 
     * @return Coordenada Y en píxeles
     */
    public int getYPosition() {
        return this.yPosition;
    }
    
    /**
     * Obtiene el contenedor al que pertenece este demonio.
     * 
     * @return Referencia al MaxwellContainer padre
     */
    public MaxwellContainer getContainer() {
        return this.container;
    }
}
