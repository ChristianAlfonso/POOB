package Shapes;

/**
 * Clase que representa un círculo en un lienzo.
 * Hereda de la clase abstracta `Figure` y proporciona implementaciones
 * para los métodos `draw()` y `erase()`.
 */
public class Circle extends Figure {
    protected int diameter; // Diámetro del círculo.

    /**
     * Constructor para crear un círculo con posición, color y diámetro específicos.
     *
     * @param x        La posición inicial en el eje X.
     * @param y        La posición inicial en el eje Y.
     * @param color    El color del círculo.
     * @param diameter El diámetro del círculo.
     */
    public Circle(int x, int y, String color, int diameter) {
        super(x, y, color); // Llama al constructor de la clase base (Figure).
        this.diameter = diameter; // Inicializa el diámetro del círculo.
    }

    /**
     * Dibuja el círculo en el lienzo si está visible.
     * Utiliza la clase `Canvas` para realizar el dibujo.
     */
    @Override
    public void draw() {
        if (isVisible) {
            Canvas canvas = Canvas.getCanvas(); // Obtiene la instancia del lienzo.
            // Dibuja el círculo en la posición (xPosition, yPosition) con el diámetro especificado.
            canvas.draw(this, color, new java.awt.geom.Ellipse2D.Double(xPosition, yPosition, diameter, diameter));
        }
    }

    /**
     * Borra el círculo del lienzo si está visible.
     * Utiliza la clase `Canvas` para realizar el borrado.
     */
    @Override
    public void erase() {
        if (isVisible) {
            Canvas canvas = Canvas.getCanvas(); // Obtiene la instancia del lienzo.
            canvas.erase(this); // Borra el círculo del lienzo.
        }
    }

    /**
     * Obtiene el diámetro del círculo.
     *
     * @return El diámetro del círculo.
     */
    public int getDiameter() {
        return diameter;
    }
}
