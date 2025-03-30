package MaxWellContainer;
import Shapes.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Random;
import java.awt.Color;

/**
 * Clase que representa un contenedor de Maxwell, que simula partículas, demonios y agujeros
 * en un entorno dividido en dos rectángulos (izquierdo y derecho).
 */
public class MaxwellContainer {
    private int width;
    private int height;
    private int posDemon;
    private int blueParticles;
    private int redParticles;
    private boolean isVisible;
    private List<Particle> particles;
    private List<Demon> demons;
    private List<Hole> holes;
    private Rectangle leftContainer;
    private Rectangle rightContainer;
    private boolean lastActionSuccess;

    /**
     * Constructor para inicializar el contenedor con dimensiones específicas.
     *
     * @param h La altura del contenedor.
     * @param w El ancho del contenedor.
     */
    public MaxwellContainer(int h, int w) {
        this.width = w;
        this.height = h;
        this.isVisible = false;
        this.particles = new ArrayList<>();
        this.demons = new ArrayList<>();
        this.holes = new ArrayList<>();

        // Crear los dos rectángulos que simulan el contenedor dividido
        this.leftContainer = new Rectangle(0, 0, "pink", h, w);
        this.rightContainer = new Rectangle(w, 0,"lightBlue" , h, w);
    }

    /**
     * Constructor para inicializar el contenedor con dimensiones, posición del demonio,
     * cantidad de partículas azules y rojas, y una matriz de datos de partículas.
     *
     * @param w         El ancho del contenedor.
     * @param h         La altura del contenedor.
     * @param d         La posición inicial del demonio.
     * @param r         La cantidad de partículas rojas.
     * @param b         La cantidad de partículas azules.
     * @param particles Una matriz que contiene los datos de las partículas.
     */
    public MaxwellContainer(int w, int h, int d, int r, int b, int[][] particles) {
        this.width = w;
        this.height = h;
        this.posDemon = d;
        this.blueParticles = b;
        this.redParticles = r;
        this.isVisible = false;
        this.particles = new ArrayList<>();
        this.demons = new ArrayList<>();
        this.holes = new ArrayList<>();

        this.leftContainer = new Rectangle(0, 0,"pink" , h, w);
        this.rightContainer = new Rectangle(w, 0, "lightBlue", h, w);

        addDemon(d);
        addParticlesFromData(r, b, particles);
    }

    /**
     * Agrega partículas al contenedor a partir de una matriz de datos.
     *
     * @param r Número de partículas rojas.
     * @param b Número de partículas azules.
     * @param particlesData Matriz con datos de partículas.
     */
    private void addParticlesFromData(int r, int b, int[][] particlesData) {
        for (int i = 0; i < particlesData.length; i++) {
            int px = particlesData[i][0];
            int py = particlesData[i][1];
            int vx = particlesData[i][2];
            int vy = particlesData[i][3];

            boolean isRed = r > 0;
            if (isRed) r--;
            
            String color = getColorFromCanvas(i);
            addParticle(color, isRed, px, py, vx, vy);
        }
    }

    /**
     * Obtiene un color del diccionario de colores de Canvas basado en un índice.
     *
     * @param index El índice del color a obtener.
     * @return El nombre del color correspondiente al índice.
     */
    private String getColorFromCanvas(int index) {
        List<String> colorKeys = new ArrayList<>(Canvas.getCanvas().getColorMap().keySet());
        return colorKeys.get(index % colorKeys.size());
    }

    /**
     * Elimina una partícula del contenedor por su color.
     *
     * @param color El color de la partícula a eliminar.
     */
    public void delParticle(String color) {
        Particle particleToRemove = null;
        for (Particle particle : particles) {
            if (particle.getColor().equals(color)) {
                particleToRemove = particle;
                break;
            }
        }

        if (particleToRemove != null) {
            particles.remove(particleToRemove);
            particleToRemove.erase();
            lastActionSuccess = true;
        } else {
            lastActionSuccess = false;
        }
    }

    /**
     * Agrega una partícula al contenedor.
     *
     * @param color El color de la partícula.
     * @param isRed Indica si la partícula es roja.
     * @param px    La posición en x de la partícula.
     * @param py    La posición en y de la partícula.
     * @param vx    La velocidad en x de la partícula.
     * @param vy    La velocidad en y de la partícula.
     */
    public void addParticle(String color, boolean isRed, int px, int py, int vx, int vy) {
        int internalX = px < 0 ? (width) + px : (width) + px;
    
        if (py < 0 || py >= height) {
            lastActionSuccess = false;
            return;
        }
        
        if (px < -width || px >= width) {
            lastActionSuccess = false;
            return;
        }

        boolean isInLeftContainer = px < 0;

        if ((isRed && isInLeftContainer) || (!isRed && !isInLeftContainer)) {
            lastActionSuccess = false;
            return;
        }

        for (Particle particle : particles) {
            if (particle.getColor().equals(color)) {
                lastActionSuccess = false;
                return;
            }
        }

        particles.add(new Particle(internalX, py, color, isRed, 5, vx, vy, px));
        lastActionSuccess = true;
    }

    /**
     * Agrega un demonio al contenedor en una posición específica.
     *
     * @param d La posición en y del demonio.
     */
    public void addDemon(int d) {
        if (d > 0 && d < height) {
            demons.add(new Demon(width - 5, d, "black", 10, 10, this));
            lastActionSuccess = true;
        } else {
            lastActionSuccess = false;
        }
    }

    /**
     * Elimina un demonio en la posición vertical dada.
     *
     * @param d La posición vertical (y) del demonio que se desea eliminar.
     */
    public void delDemon(int d) {
        Demon demonToRemove = null;
        for (Demon demon : demons) {
            if (demon.getYPosition() == d) {
                demonToRemove = demon;
                break;
            }
        }
    
        if (demonToRemove != null) {
            demons.remove(demonToRemove);
            lastActionSuccess = true;
        } else {
            lastActionSuccess = false;
        }
    }

    /**
     * Agrega un agujero al contenedor en una posición específica con una capacidad dada.
     *
     * @param px       La posición en x del agujero.
     * @param py       La posición en y del agujero.
     * @param capacity La capacidad del agujero.
     */
    public void addHole(int px, int py, int capacity) {
        int internalX = px < 0 ? (width) + px : (width) + px;

        if (py < 0 || py >= height) {
            lastActionSuccess = false;
            return;
        }
        
        if (px < -width || px >= width) {
            lastActionSuccess = false;
            return;
        }

        if (capacity > 0) {
            holes.add(new Hole(internalX, py, "black", 10, capacity, px));
            lastActionSuccess = true;
        } else {
            lastActionSuccess = false;
        }
    }

    /**
     * Mueve todos los elementos del contenedor (partículas, demonios y agujeros móviles).
     *
     * @param containerWidth  El ancho total del contenedor.
     * @param containerHeight La altura total del contenedor.
     * @param lineX           La posición de la línea divisoria.
     */
    private void moveAll(int containerWidth, int containerHeight, int lineX) {
        // Mover partículas
        for (Particle particle : particles) {
            particle.move(containerWidth, containerHeight, lineX);
            System.out.printf("Posición - X: %d, Y: %d%n", particle.getXPosition(), particle.getYPosition());
        }
        
        // Mover demonios
        for (Demon demon : demons) {
            demon.move(containerHeight);
        }
        
        // Mover agujeros móviles
        for (Hole hole : holes) {
            if (hole instanceof Movil) {
                ((Movil) hole).move(containerWidth, containerHeight);
            }
        }
    }

    /**
     * Inicia la simulación del contenedor durante un número específico de ticks.
     *
     * @param ticks El número de ticks para ejecutar la simulación.
     */
    public void start(int ticks) {
        if (ticks <= 0) {
            lastActionSuccess = false;
            return;
        }
        
        makeVisible();
        lastActionSuccess = true;
        
        for (int i = 0; i < ticks; i++) {
            moveAll(2 * width, height, width);
            checkCollisions();
            pause(100);
        }
    }

    /**
     * Verifica colisiones entre todos los elementos del contenedor.
     */
    private void checkCollisions() {
        // Demonios con partículas
        for (Demon demon : demons) {
            for (Particle particle : particles) {
                if (demon.collidesWith(particle)) {
                    demon.transferParticle(particle);
                }
            }
        }
        
        // Agujeros con partículas
        for (Hole hole : holes) {
            for (Particle particle : particles) {
                hole.trapParticle(particle);
            }
        }
    }

    /**
     * Pausa la ejecución por un tiempo determinado.
     *
     * @param milliseconds Tiempo de pausa en milisegundos.
     */
    private void pause(int milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    /**
     * Verifica si se ha alcanzado la condición de victoria.
     *
     * @return true si todas las partículas están en sus contenedores correspondientes.
     */
    public boolean isGoal() {
        for (Particle particle : particles) {
            if (particle.isRed() && particle.getXPosition() >= width) {
                return false;
            }
            if (!particle.isRed() && particle.getXPosition() < width) {
                return false;
            }
        }
        lastActionSuccess = true;
        return true;
    }

    /**
     * Obtiene las posiciones de los demonios en el contenedor.
     *
     * @return Un arreglo con las posiciones en y de los demonios, ordenadas.
     */
    public int[] demons() {
        List<Integer> demonPositions = new ArrayList<>();
        
        System.out.print("Posiciones Y de demonios: ");
        
        for (Demon demon : demons) {
            demonPositions.add(demon.getYPosition());
            System.out.print(demon.getYPosition() + " ");
        }
        
        Collections.sort(demonPositions);
        System.out.println("\n(Ordenadas: " + demonPositions + ")");
        
        lastActionSuccess = true;
        return demonPositions.stream().mapToInt(i -> i).toArray();
    }

    /**
     * Obtiene los datos de las partículas en el contenedor.
     *
     * @return Una matriz con los datos de las partículas, ordenados.
     */
    public int[][] particles() {
        List<int[]> particleData = new ArrayList<>();
        
        // Recopilar datos
        for (Particle particle : particles) {
            particleData.add(new int[]{
                particle.getOriginalX(),
                particle.getYPosition(),
                particle.getSpeedX(),
                particle.getSpeedY()
            });
        }
        
        // Ordenar los datos (manteniendo tu lógica original)
        Collections.sort(particleData, (a, b) -> {
            for (int i = 0; i < a.length; i++) {
                if (a[i] != b[i]) return Integer.compare(a[i], b[i]);
            }
            return 0;
        });
        
        // Construir el string de salida en formato [[],[],[]]
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < particleData.size(); i++) {
            if (i > 0) sb.append(", ");
            sb.append(Arrays.toString(particleData.get(i)));
        }
        sb.append("]");
        
        // Imprimir el resultado
        System.out.println("Datos de partículas:");
        System.out.println(sb.toString());
        
        lastActionSuccess = true;
        return particleData.toArray(new int[0][]);
    }

    /**
     * Obtiene los datos de los agujeros en el contenedor.
     *
     * @return Una matriz con los datos de los agujeros, ordenados.
     */
    public int[][] holes() {
        List<int[]> holeData = new ArrayList<>();
        
        // Recopilar datos de los agujeros
        for (Hole hole : holes) {
            holeData.add(new int[]{
                hole.getOriginalX(),
                hole.getYPosition(),
                hole.getRemainingCapacity()
            });
        }
        
        // Ordenar los datos (manteniendo tu lógica original)
        Collections.sort(holeData, (a, b) -> {
            for (int i = 0; i < a.length; i++) {
                if (a[i] != b[i]) return Integer.compare(a[i], b[i]);
            }
            return 0;
        });
        
        // Construir el string de salida en formato [[],[],...]
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < holeData.size(); i++) {
            if (i > 0) sb.append(", ");
            sb.append(Arrays.toString(holeData.get(i)));
        }
        sb.append("]");
        
        // Imprimir el resultado
        System.out.println("Datos de agujeros:");
        System.out.println(sb.toString());
        lastActionSuccess = true;
        return holeData.toArray(new int[0][]);
    }

    /**
     * Hace visible el contenedor y todos sus elementos.
     */
    public void makeVisible() {
        this.isVisible = true;
        Canvas canvas = Canvas.getCanvas();
        canvas.setVisible(true);

        leftContainer.makeVisible();
        rightContainer.makeVisible();
        
        for (Particle particle : particles) {
            particle.makeVisible();
        }
        for (Demon demon : demons) {
            demon.makeVisible();
        }
        for (Hole hole : holes) {
            hole.makeVisible();
        }
        
        lastActionSuccess = true;
    }

    /**
     * Hace invisible el contenedor y todos sus elementos.
     */
    public void makeInvisible() {
        this.isVisible = false;
        leftContainer.makeInvisible();
        rightContainer.makeInvisible();
        
        for (Particle particle : particles) {
            particle.makeInvisible();
        }
        for (Demon demon : demons) {
            demon.makeInvisible();
        }
        for (Hole hole : holes) {
            hole.makeInvisible();
        }

        Canvas canvas = Canvas.getCanvas();
        canvas.redraw();
        lastActionSuccess = true;
    }

    /**
     * Finaliza la simulación, limpiando el contenedor.
     */
    public void finish() {
        makeInvisible();
        clearContainers();
        Canvas canvas = Canvas.getCanvas();
        canvas.redraw();
        lastActionSuccess = true;
    }

    /**
     * Limpia las listas de partículas, demonios y agujeros.
     */
    private void clearContainers() {
        particles.clear();
        demons.clear();
        holes.clear();
    }

    /**
     * Cuenta las partículas en el contenedor izquierdo.
     *
     * @return El número de partículas en el contenedor izquierdo.
     */
    private int particlesInLeftContainer() {
        return (int) particles.stream()
            .filter(p -> p.getXPosition() < width)
            .count();
    }

    /**
     * Cuenta las partículas en el contenedor derecho.
     *
     * @return El número de partículas en el contenedor derecho.
     */
    private int particlesInRightContainer() {
        return (int) particles.stream()
            .filter(p -> p.getXPosition() >= width)
            .count();
    }
    
    /**
     * Obtiene el ancho del contenedor.
     *
     * @return El ancho del contenedor en píxeles.
     */
    public int getWidth() {
        lastActionSuccess = true;
        return this.width;
    }

    /**
     * Verifica si la última acción realizada fue exitosa.
     *
     * @return true si la última acción fue exitosa.
     */
    public boolean ok() {
        return lastActionSuccess;
    }
    
    /**
     * Obtiene información de los contenedores.
     *
     * @return String con la información de cada contenedor.
     */
    public String getContainerInfo() {
        StringBuilder info = new StringBuilder();
        
        info.append("Contenedor Izquierdo: ");
        info.append("  Ancho: ").append(leftContainer.getWidth());
        info.append("  Alto: ").append(leftContainer.getHeight());
        info.append("  Partículas: ").append(particlesInLeftContainer());
        info.append("  Demonios: ").append(Arrays.toString(demons()));
        
        info.append("  Contenedor Derecho: ");
        info.append("  Ancho: ").append(rightContainer.getWidth());
        info.append("  Alto: ").append(rightContainer.getHeight());
        info.append("  Partículas: ").append(particlesInRightContainer());
        info.append("  Demonios: ").append(Arrays.toString(demons()));
        
        lastActionSuccess = true;
        return info.toString();
    }
    
    /**
     * Agrega un demonio de tipo específico al contenedor.
     * @param type El tipo de demonio ("normal", "blue", "weak").
     * @param d La posición en y del demonio.
     */
    public void addDemon(String type, int d) {
        if (d < 0 || d >= height) {
            lastActionSuccess = false;
            return;
        }
    
        String typeLower = type.toLowerCase();
        if ("blue".equals(typeLower)) {
            demons.add(new Blue(width - 5, d, "blue", 10, 10, this));
        } 
        else if ("weak".equals(typeLower)) {
            demons.add(new Weak(width - 5, d, "black", 10, 10, this));
        } 
        else {
            demons.add(new Demon(width - 5, d, "black", 10, 10, this));
        }
        lastActionSuccess = true;
    }
    
    /**
     * Agrega una partícula de tipo específico al contenedor.
     * @param type El tipo de partícula ("normal", "flying", "rotator", "ephemeral").
     * @param color El color de la partícula.
     * @param isRed Indica si la partícula es roja.
     * @param px La posición en x de la partícula.
     * @param py La posición en y de la partícula.
     * @param vx La velocidad en x de la partícula.
     * @param vy La velocidad en y de la partícula.
     */
    public void addParticle(String type, String color, boolean isRed, int px, int py, int vx, int vy) {
        if (py < 0 || py >= height || px < -width || px >= width) {
            lastActionSuccess = false;
            return;
        }
    
        for (Particle p : particles) {
            if (p.getColor().equals(color)) {
                lastActionSuccess = false;
                return;
            }
        }
    
        int internalX = px < 0 ? (width) + px : (width) + px;
        boolean isInLeftContainer = px < 0;
    
        if ((isRed && isInLeftContainer) || (!isRed && !isInLeftContainer)) {
            lastActionSuccess = false;
            return;
        }
    
        Particle particle;
        String typeLower = type.toLowerCase();
        
        if ("flying".equals(typeLower)) {
            particle = new Flying(internalX, py, color, isRed, 5, vx, vy, px);
        } 
        else if ("rotator".equals(typeLower)) {
            particle = new Rotator(internalX, py, color, isRed, 5, vx, vy, px);
        } 
        else if ("ephemeral".equals(typeLower)) {
            particle = new Ephemeral(internalX, py, color, isRed, 5, vx, vy, px);
        } 
        else {
            particle = new Particle(internalX, py, color, isRed, 5, vx, vy, px);
        }
        
        particles.add(particle);
        lastActionSuccess = true;
    }
        
    /**
     * Agrega un agujero de tipo específico al contenedor.
     * @param type El tipo de agujero ("normal", "movil").
     * @param px La posición en x del agujero.
     * @param py La posición en y del agujero.
     * @param capacity La capacidad del agujero.
     */
    public void addHole(String type , int px, int py, int capacity) {
        if (py < 0 || py >= height || px < -width || px >= width || capacity <= 0) {
            lastActionSuccess = false;
            return;
        }
    
        int internalX = px < 0 ? (width) + px : (width) + px;
        String typeLower = type.toLowerCase();
        
        if ("movil".equals(typeLower)) {
            holes.add(new Movil(internalX, py, "black", 10, capacity, px));
        } 
        else {
            holes.add(new Hole(internalX, py, "black", 10, capacity, px));
        }
        
        lastActionSuccess = true;
    }
    
    /**
     * Obtiene las posiciones de los demonios de un tipo específico.
     * @param type El tipo de demonio ("normal", "blue", "weak").
     * @return Arreglo con las posiciones Y de los demonios del tipo especificado.
     */
    public int[] demons(String type) {
        List<Integer> positions = new ArrayList<>();
        String typeLower = type.toLowerCase();
        
        for (Demon demon : demons) {
            if ("blue".equals(typeLower) && demon instanceof Blue) {
                positions.add(demon.getYPosition());
            } 
            else if ("weak".equals(typeLower) && demon instanceof Weak) {
                positions.add(demon.getYPosition());
            } 
            else if ("normal".equals(typeLower) && !(demon instanceof Blue) && !(demon instanceof Weak)) {
                positions.add(demon.getYPosition());
            } 
            else if (!"blue".equals(typeLower) && !"weak".equals(typeLower) && !"normal".equals(typeLower)) {
                positions.add(demon.getYPosition());
            }
        }
        
        Collections.sort(positions);
        lastActionSuccess = true;
        return positions.stream().mapToInt(i -> i).toArray();
    }
    
    /**
     * Obtiene los datos de las partículas de un tipo específico.
     * @param type El tipo de partícula ("normal", "flying", "rotator", "ephemeral").
     * @return Matriz con los datos de las partículas del tipo especificado.
     */
    public int[][] particles(String type) {
        List<int[]> particleData = new ArrayList<>();
        String typeLower = type.toLowerCase();
    
        for (Particle particle : particles) {
            boolean match = false;
            
            if ("flying".equals(typeLower)) {
                match = particle instanceof Flying;
            } 
            else if ("rotator".equals(typeLower)) {
                match = particle instanceof Rotator;
            } 
            else if ("ephemeral".equals(typeLower)) {
                match = particle instanceof Ephemeral;
            } 
            else if ("normal".equals(typeLower)) {
                match = !(particle instanceof Flying) && 
                       !(particle instanceof Rotator) && 
                       !(particle instanceof Ephemeral);
            } 
            else {
                match = true;
            }
            
            if (match) {
                particleData.add(new int[]{
                    particle.getOriginalX(),
                    particle.getYPosition(),
                    particle.getSpeedX(),
                    particle.getSpeedY()
                });
            }
        }
                  
        Collections.sort(particleData, (a, b) -> {
            for (int i = 0; i < a.length; i++) {
                if (a[i] != b[i]) return Integer.compare(a[i], b[i]);
            }
            return 0;
        });
        
        lastActionSuccess = true;
        return particleData.toArray(new int[0][]);
    }
    
    /**
     * Obtiene los datos de los agujeros de un tipo específico.
     * @param type El tipo de agujero ("normal", "movil").
     * @return Matriz con los datos de los agujeros del tipo especificado.
     */
    public int[][] holes(String type) {
        List<int[]> holeData = new ArrayList<>();
        String typeLower = type.toLowerCase();
        
        for (Hole hole : holes) {
            boolean match = false;
            
            if ("movil".equals(typeLower)) {
                match = hole instanceof Movil;
            } 
            else if ("normal".equals(typeLower)) {
                match = !(hole instanceof Movil);
            } 
            else {
                match = true;
            }
            
            if (match) {
                holeData.add(new int[]{
                    hole.getOriginalX(),
                    hole.getYPosition(),
                    hole.getRemainingCapacity()
                });
            }
        }
        
        Collections.sort(holeData, (a, b) -> {
            for (int i = 0; i < a.length; i++) {
                if (a[i] != b[i]) return Integer.compare(a[i], b[i]);
            }
            return 0;
        });
        
        lastActionSuccess = true;
        return holeData.toArray(new int[0][]);
    }

    /**
     * Método principal que demuestra el uso básico de la clase MaxwellContainer.
     * 
     * Este método muestra un ejemplo completo de cómo:
     *   Crear un contenedor de Maxwell
     *   Agregar diferentes tipos de partículas
     *   Agregar diferentes tipos de demonios
     *   Agregar diferentes tipos de agujeros
     *   Ejecutar una simulación
     *   Mostrar resultados estadísticos
     * 
     * @param args Argumentos de línea de comandos (no utilizados en este ejemplo)
     * 
     * @example
     * // Ejemplo de salida:
     * // ¿Objetivo cumplido? false
     * // Partículas en izquierda: 2
     * // Partículas en derecha: 1
     * // Estado de huecos: [[-50, 50, 3], [50, 50, 4]]
     */
    public static void main(String[] args) {
        // 1. Creación del contenedor con dimensiones 400x400 píxeles
        MaxwellContainer container = new MaxwellContainer(400, 400);
        
        // 2. Adición de partículas de diferentes tipos:
        // - Partícula normal roja en posición (50,100) con velocidad (2,1)
        container.addParticle("normal", "red", true, 50, 100, 2, 1);
        // - Partícula efímera verde en posición (50,200) con velocidad (3,-2)
        container.addParticle("ephemeral", "green", true, 50, 200, 3, -2);
        // - Partícula voladora negra en posición (50,300) con velocidad (1,1)
        container.addParticle("flying", "black", true, 50, 300, 1, 1);
        // - Partícula rotatoria azul en posición (-50,100) con velocidad (-2,3)
        container.addParticle("rotator", "blue", false, -50, 100, -2, 3);
        
        // 3. Adición de demonios de diferentes tipos:
        // - Demonio normal en posición Y=200
        container.addDemon("normal", 200);
        // - Demonio azul (especial) en posición Y=100
        container.addDemon("blue", 100);
        // - Demonio débil (especial) en posición Y=300
        container.addDemon("weak", 300);
        
        // 4. Adición de agujeros:
        // - Agujero normal en posición (-50,50) con capacidad para 3 partículas
        container.addHole("normal", -50, 50, 3);
        // - Agujero móvil en posición (50,50) con capacidad para 5 partículas
        container.addHole("movil", 50, 50, 5);
        
        // 5. Ejecución de la simulación:
        // - Hacer visible el contenedor
        container.makeVisible();
        // - Ejecutar 900 ciclos (ticks) de simulación
        container.start(900);
        
        // 6. Mostrar resultados:
        // - Verificar si se cumplió el objetivo de separación
        System.out.println("¿Objetivo cumplido? " + container.isGoal());
        // - Contar partículas en el lado izquierdo
        System.out.println("Partículas en izquierda: " + container.particlesInLeftContainer());
        // - Contar partículas en el lado derecho
        System.out.println("Partículas en derecha: " + container.particlesInRightContainer());
        // - Mostrar estado de los agujeros [posiciónX, posiciónY, capacidad restante]
        System.out.println("Estado de huecos: " + Arrays.deepToString(container.holes()));
    }
}