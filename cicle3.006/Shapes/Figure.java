package Shapes;

/**
 * Clase abstracta que representa una figura genérica en un lienzo.
 * Proporciona funcionalidades básicas como movimiento, cambio de color y visibilidad.
 * Las subclases deben implementar los métodos abstractos `draw()` y `erase()`.
 */
public abstract class Figure {
    protected int xPosition; // Posición en el eje X de la figura.
    protected int yPosition; // Posición en el eje Y de la figura.
    protected String color;  // Color de la figura.
    protected boolean isVisible; // Estado de visibilidad de la figura.

    /**
     * Constructor para inicializar una figura con una posición y color específicos.
     *
     * @param x     La posición inicial en el eje X.
     * @param y     La posición inicial en el eje Y.
     * @param color El color inicial de la figura.
     */
    public Figure(int x, int y, String color) {
        this.xPosition = x;
        this.yPosition = y;
        this.color = color;
        this.isVisible = false; // Por defecto, la figura no es visible.
    }

    /**
     * Obtiene la posición actual en el eje X.
     *
     * @return La posición en el eje X.
     */
    public int getXPosition() {
        return xPosition;
    }

    /**
     * Obtiene la posición actual en el eje Y.
     *
     * @return La posición en el eje Y.
     */
    public int getYPosition() {
        return yPosition;
    }
    
    /**
     * Hace visible la figura en el lienzo.
     */
    public void makeVisible() {
        isVisible = true;
        draw(); // Este método es abstracto y será implementado por las subclases
    }
    
     /**
     * Hace invisible la figura en el lienzo.
     * Si la figura estaba visible, se borra.
     */
    public void makeInvisible() {
        erase(); // Este método es abstracto y será implementado por las subclases
        isVisible = false;
    }
    

    /**
     * Mueve la figura a una nueva posición en el lienzo.
     *
     * @param x La nueva posición en el eje X.
     * @param y La nueva posición en el eje Y.
     */
    public void moveTo(int x, int y) {
        erase(); // Borra la figura de su posición actual.
        xPosition = x; // Actualiza la posición en X.
        yPosition = y; // Actualiza la posición en Y.
        draw(); // Dibuja la figura en la nueva posición.
    }
    
    

    /**
     * Cambia el color de la figura.
     *
     * @param newColor El nuevo color de la figura.
     */
    public void changeColor(String newColor) {
        color = newColor; // Actualiza el color.
        draw(); // Redibuja la figura con el nuevo color.
    }

    /**
     * Obtiene el color actual de la figura.
     *
     * @return El color de la figura.
     */
    public String getColor() {
        return color;
    }

    /**
     * Método abstracto para dibujar la figura en el lienzo.
     * Debe ser implementado por las subclases.
     */
    protected abstract void draw();

    /**
     * Método abstracto para borrar la figura del lienzo.
     * Debe ser implementado por las subclases.
     */
    protected abstract void erase();
    
    
}
