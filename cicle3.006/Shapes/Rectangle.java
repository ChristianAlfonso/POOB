package Shapes;

/**
 * Clase que representa un rectángulo en un lienzo.
 * Hereda de la clase `Figure` y añade funcionalidades para dibujar y borrar un rectángulo,
 * así como para obtener sus dimensiones.
 */
public class Rectangle extends Figure {
    protected int height; // Altura del rectángulo.
    protected int width;  // Ancho del rectángulo.

    /**
     * Constructor para crear un rectángulo con posición, color, altura y ancho específicos.
     *
     * @param x      La posición inicial en el eje X.
     * @param y      La posición inicial en el eje Y.
     * @param color  El color del rectángulo.
     * @param height La altura del rectángulo.
     * @param width  El ancho del rectángulo.
     */
    public Rectangle(int x, int y, String color, int height, int width) {
        super(x, y, color); // Llama al constructor de la clase base (Figure).
        this.height = height; // Inicializa la altura del rectángulo.
        this.width = width;   // Inicializa el ancho del rectángulo.
    }

    /**
     * Dibuja el rectángulo en el lienzo si está visible.
     * Utiliza la clase `Canvas` para realizar el dibujo.
     */
    @Override
    protected void draw() {
        if (isVisible) {
            Canvas canvas = Canvas.getCanvas(); // Obtiene la instancia del lienzo.
            // Dibuja el rectángulo en la posición (xPosition, yPosition) con el ancho y alto especificados.
            canvas.draw(this, color, new java.awt.Rectangle(xPosition, yPosition, width, height));
        }
    }

    /**
     * Borra el rectángulo del lienzo si está visible.
     * Utiliza la clase `Canvas` para realizar el borrado.
     */
    @Override
    protected void erase() {
        if (isVisible) {
            Canvas canvas = Canvas.getCanvas(); // Obtiene la instancia del lienzo.
            canvas.erase(this); // Borra el rectángulo del lienzo.
        }
    }

    /**
     * Obtiene la altura del rectángulo.
     *
     * @return La altura del rectángulo.
     */
    public int getHeight() {
        return height;
    }

    /**
     * Obtiene el ancho del rectángulo.
     *
     * @return El ancho del rectángulo.
     */
    public int getWidth() {
        return width;
    }
}
