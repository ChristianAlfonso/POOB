package Shapes;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.*;
import java.util.HashMap; 
import java.util.Map; 

/**
 * Clase que representa un lienzo gráfico para dibujar formas y partículas.
 * Utiliza un patrón Singleton para garantizar una única instancia del lienzo.
 */
public class Canvas {
    private static Canvas canvasSingleton;

    /**
     * Obtiene la instancia única del lienzo (Singleton).
     * Si no existe, crea una nueva instancia con un tamaño y color de fondo predeterminados.
     *
     * @return La instancia única del lienzo.
     */
    public static Canvas getCanvas() {
        if (canvasSingleton == null) {
            canvasSingleton = new Canvas("BlueJ Shapes Demo", 1200, 900, Color.white);
        }
        canvasSingleton.setVisible(true);
        return canvasSingleton;
    }

    private JFrame frame;
    private CanvasPane canvas;
    private Graphics2D graphic;
    private Color backgroundColour;
    private Image canvasImage;
    private List<Object> objects;
    private HashMap<Object, ShapeDescription> shapes;
    private HashMap<String, Color> colorMap; // Diccionario para mapear nombres de colores a objetos Color

    /**
     * Constructor privado para inicializar el lienzo.
     *
     * @param title   El título de la ventana del lienzo.
     * @param width   El ancho del lienzo.
     * @param height  La altura del lienzo.
     * @param bgColour El color de fondo del lienzo.
     */
    private Canvas(String title, int width, int height, Color bgColour) {
        frame = new JFrame();
        canvas = new CanvasPane();
        frame.setContentPane(canvas);
        frame.setTitle(title);
        canvas.setPreferredSize(new Dimension(width, height));
        backgroundColour = bgColour;
        frame.pack();
        objects = new ArrayList<Object>();
        shapes = new HashMap<Object, ShapeDescription>();
        initializeColorMap(); // Inicializar el diccionario de colores
    }

    /**
     * Inicializa el diccionario de colores con colores predefinidos y personalizados.
     */

        private void initializeColorMap() {
        colorMap = new HashMap<>();
    
        // Colores predefinidos de Java
        colorMap.put("red", Color.RED);
        colorMap.put("black", Color.BLACK);
        colorMap.put("blue", Color.BLUE);
        colorMap.put("yellow", Color.YELLOW);
        colorMap.put("green", Color.GREEN);
        colorMap.put("magenta", Color.MAGENTA);
        colorMap.put("white", Color.WHITE);
        colorMap.put("cyan", Color.CYAN);
        colorMap.put("orange", Color.ORANGE);
        colorMap.put("pink", Color.PINK); // Rosa
        colorMap.put("gray", Color.GRAY);
        colorMap.put("darkGray", Color.DARK_GRAY);
        colorMap.put("lightGray", Color.LIGHT_GRAY);
    
        // Colores personalizados (usando RGB)
        colorMap.put("lightBlue", new Color(173, 216, 230)); // Azul claro
        colorMap.put("skyBlue", new Color(135, 206, 235));   // Azul cielo
        colorMap.put("aqua", new Color(0, 255, 255));        // Aqua (azul verdoso)
        colorMap.put("powderBlue", new Color(176, 224, 230)); // Azul polvo
        colorMap.put("cornflowerBlue", new Color(100, 149, 237)); // Azul aciano
    
        // Otros colores personalizados (opcionales)
        colorMap.put("purple", new Color(128, 0, 128));       // Morado
        colorMap.put("teal", new Color(0, 128, 128));         // Verde azulado
        colorMap.put("navy", new Color(0, 0, 128));           // Azul marino
        colorMap.put("olive", new Color(128, 128, 0));        // Oliva
        colorMap.put("maroon", new Color(128, 0, 0));         // Granate
        colorMap.put("coral", new Color(255, 127, 80));       // Coral
        colorMap.put("gold", new Color(255, 215, 0));         // Oro
        colorMap.put("silver", new Color(192, 192, 192));     // Plata
        colorMap.put("indigo", new Color(75, 0, 130));        // Índigo
        colorMap.put("lime", new Color(0, 255, 0));           // Lima
        colorMap.put("turquoise", new Color(64, 224, 208));   // Turquesa
        colorMap.put("violet", new Color(238, 130, 238));     // Violeta
        colorMap.put("salmon", new Color(250, 128, 114));     // Salmón
        colorMap.put("tan", new Color(210, 180, 140));        // Bronceado
        colorMap.put("khaki", new Color(240, 230, 140));      // Caqui
        colorMap.put("lavender", new Color(230, 230, 250));   // Lavanda
        colorMap.put("plum", new Color(221, 160, 221));       // Ciruela
        colorMap.put("beige", new Color(245, 245, 220));      // Beige
        colorMap.put("mint", new Color(189, 252, 201));       // Menta
        colorMap.put("peach", new Color(255, 218, 185));      // Melocotón
        colorMap.put("mustard", new Color(255, 219, 88));     // Mostaza
        colorMap.put("rose", new Color(255, 0, 127));         // Rosa
        colorMap.put("crimson", new Color(220, 20, 60));      // Carmesí
        colorMap.put("sienna", new Color(160, 82, 45));       // Siena
        colorMap.put("chocolate", new Color(210, 105, 30));   // Chocolate
        colorMap.put("peru", new Color(205, 133, 63));        // Perú
        colorMap.put("sandyBrown", new Color(244, 164, 96));  // Marrón arena
        colorMap.put("goldenrod", new Color(218, 165, 32));   // Vara de oro
        colorMap.put("darkGreen", new Color(0, 100, 0));      // Verde oscuro
        colorMap.put("forestGreen", new Color(34, 139, 34));  // Verde bosque
        colorMap.put("seaGreen", new Color(46, 139, 87));     // Verde mar
        colorMap.put("springGreen", new Color(0, 255, 127));  // Verde primavera
        colorMap.put("aquamarine", new Color(127, 255, 212)); // Aguamarina
        colorMap.put("steelBlue", new Color(70, 130, 180));   // Azul acero
        colorMap.put("royalBlue", new Color(65, 105, 225));   // Azul real
        colorMap.put("midnightBlue", new Color(25, 25, 112)); // Azul medianoche
        colorMap.put("orchid", new Color(218, 112, 214));     // Orquídea
        colorMap.put("thistle", new Color(216, 191, 216));    // Cardo
        colorMap.put("wheat", new Color(245, 222, 179));      // Trigo
        colorMap.put("burlywood", new Color(222, 184, 135));  // Madera clara
        colorMap.put("firebrick", new Color(178, 34, 34));    // Ladrillo
        colorMap.put("tomato", new Color(255, 99, 71));       // Tomate
        colorMap.put("coralPink", new Color(248, 131, 121));  // Coral rosado
        colorMap.put("hotPink", new Color(255, 105, 180));    // Rosa fuerte
        colorMap.put("deepPink", new Color(255, 20, 147));    // Rosa profundo
        colorMap.put("paleVioletRed", new Color(219, 112, 147)); // Violeta rojo pálido
    }

    /**
     * Hace visible o invisible el lienzo.
     *
     * @param visible true para hacer visible el lienzo, false para ocultarlo.
     */
    public void setVisible(boolean visible) {
        if (graphic == null) {
            Dimension size = canvas.getSize();
            canvasImage = canvas.createImage(size.width, size.height);
            graphic = (Graphics2D) canvasImage.getGraphics();
            graphic.setColor(backgroundColour);
            graphic.fillRect(0, 0, size.width, size.height);
            graphic.setColor(Color.black);
        }
        frame.setVisible(visible);
    }

    /**
     * Dibuja una forma en el lienzo asociada a un objeto de referencia.
     *
     * @param referenceObject El objeto de referencia asociado a la forma.
     * @param color           El color de la forma.
     * @param shape           La forma a dibujar.
     */
    public void draw(Object referenceObject, String color, Shape shape) {
        objects.remove(referenceObject);
        objects.add(referenceObject);
        shapes.put(referenceObject, new ShapeDescription(shape, color));
        redraw();
    }

    /**
     * Borra una forma del lienzo asociada a un objeto de referencia.
     *
     * @param referenceObject El objeto de referencia asociado a la forma.
     */
    public void erase(Object referenceObject) {
        objects.remove(referenceObject);
        shapes.remove(referenceObject);
        redraw();
    }

    /**
     * Establece el color de primer plano usando el diccionario de colores.
     *
     * @param colorString El nombre del color a usar.
     */
    public void setForegroundColor(String colorString) {
        Color color = colorMap.getOrDefault(colorString, Color.BLACK); // Usar BLACK como color por defecto
        graphic.setColor(color);
    }

    /**
     * Pausa la ejecución del programa durante un número específico de milisegundos.
     *
     * @param milliseconds El número de milisegundos para pausar.
     */
    public void wait(int milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (Exception e) {
            // Ignorar excepción por el momento
        }
    }

    /**
     * Redibuja todas las formas en el lienzo.
     */
    public void redraw() {
        erase();
        for (Iterator i = objects.iterator(); i.hasNext(); ) {
            shapes.get(i.next()).draw(graphic);
        }
        canvas.repaint();
    }

    /**
     * Borra todo el contenido del lienzo, dejándolo con el color de fondo.
     */
    private void erase() {
        Color original = graphic.getColor();
        graphic.setColor(backgroundColour);
        Dimension size = canvas.getSize();
        graphic.fill(new java.awt.Rectangle(0, 0, size.width, size.height));
        graphic.setColor(original);
    }

    /**
     * Clase interna que representa el panel donde se dibuja el lienzo.
     */
    private class CanvasPane extends JPanel {
        /**
         * Dibuja la imagen del lienzo en el panel.
         *
         * @param g El contexto gráfico en el que se dibuja.
         */
        public void paint(Graphics g) {
            g.drawImage(canvasImage, 0, 0, null);
        }
    }

    /**
     * Clase interna que describe una forma y su color.
     */
    private class ShapeDescription {
        private Shape shape;
        private String colorString;

        /**
         * Constructor para crear una descripción de forma.
         *
         * @param shape      La forma a dibujar.
         * @param colorString El color de la forma.
         */
        public ShapeDescription(Shape shape, String color) {
            this.shape = shape;
            this.colorString = color;
        }

        /**
         * Dibuja la forma en el contexto gráfico proporcionado.
         *
         * @param graphic El contexto gráfico en el que se dibuja la forma.
         */
        public void draw(Graphics2D graphic) {
            setForegroundColor(colorString);
            graphic.draw(shape);
            graphic.fill(shape);
        }
    }

    /**
     * Obtiene el diccionario de colores del lienzo.
     *
     * @return Un mapa que asocia nombres de colores con objetos Color.
     */
    public Map<String, Color> getColorMap() {
        return colorMap;
    }
}
