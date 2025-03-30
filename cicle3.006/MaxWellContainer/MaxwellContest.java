package MaxWellContainer;
import java.util.Arrays;

/**
 * Clase que resuelve el problema de Maxwell's Demon para un contenedor de partículas.
 */
public class MaxwellContest {
    static final double EPS = 1e-9; // Épsilon para comparaciones de punto flotante

    /**
     * Resuelve el problema de encontrar el tiempo mínimo para que todas las partículas rojas
     * estén en el lado izquierdo y las azules en el derecho del contenedor.
     * 
     * @param w Ancho del contenedor
     * @param h Altura del contenedor
     * @param d Posición vertical de la división (pared divisoria)
     * @param r Número de partículas rojas
     * @param b Número de partículas azules
     * @param particles Matriz de partículas donde cada fila contiene [px, py, vx, vy]
     * @return El tiempo mínimo requerido, o -1.0 si es imposible
     */
    public static double solve(int w, int h, int d, int r, int b, int[][] particles) {
        double candidateT = 0.0;
        boolean possible = true;

        // Verificar si todas las partículas ya están en sus lados correctos
        boolean allCorrect = true;
        for (int i = 0; i < particles.length; i++) {
            boolean isRed = i < r;
            double px = particles[i][0];
            if ((isRed && px >= 0) || (!isRed && px <= 0)) {
                allCorrect = false;
                break;
            }
        }
        if (allCorrect) {
            return 0.0; // Ya están todas ordenadas
        }
        
        // Procesar cada partícula
        for (int i = 0; i < particles.length; i++) {
            int[] p = particles[i];
            double px = p[0];
            double py = p[1];
            double vx = p[2];
            double vy = p[3];
            boolean isRed = i < r;

            // Verificar si la partícula está en el lado correcto inicialmente
            boolean isCorrectInitially = (isRed && px < 0) || (!isRed && px > 0);
            
            if (!isCorrectInitially) {
                // Calcular tiempo para cruzar al lado correcto
                double flipTime = findFlipTime(w, h, d, px, py, vx, vy, isRed);
                if (flipTime == Double.POSITIVE_INFINITY) {
                    possible = false;
                    break;
                }
                candidateT = Math.max(candidateT, flipTime);
            }
        }

        if (!possible) {
            return -1.0; // "impossible"
        }

        // Verificar partículas correctas en candidateT
        for (int i = 0; i < particles.length; i++) {
            int[] p = particles[i];
            double px = p[0];
            double py = p[1];
            double vx = p[2];
            double vy = p[3];
            boolean isRed = i < r;

            boolean isCorrectInitially = (isRed && px < 0) || (!isRed && px > 0);
            if (isCorrectInitially) {
                double x = calculateX(px, vx, candidateT, w);
                boolean isCorrectAtT = (isRed && x < 0) || (!isRed && x > 0);
                if (!isCorrectAtT) {
                    double nextT = findNextCorrectTime(px, vx, w, candidateT, isRed);
                    if (nextT == Double.POSITIVE_INFINITY) {
                        possible = false;
                        break;
                    }
                    candidateT = Math.max(candidateT, nextT);
                }
            }
        }

        return possible ? candidateT : -1.0;
    }

    /**
     * Encuentra el tiempo para que una partícula cruce al lado correcto.
     * 
     * @param w Ancho del contenedor
     * @param h Altura del contenedor
     * @param d Posición vertical de la división
     * @param px Posición x inicial de la partícula
     * @param py Posición y inicial de la partícula
     * @param vx Velocidad x de la partícula
     * @param vy Velocidad y de la partícula
     * @param isRed Indica si la partícula es roja
     * @return Tiempo mínimo para cruzar o infinito si es imposible
     */
    private static double findFlipTime(int w, int h, int d, double px, double py, double vx, double vy, boolean isRed) {
        if (vx == 0) return Double.POSITIVE_INFINITY;

        double bestTime = Double.POSITIVE_INFINITY;

        for (int k = 0; k <= 100000; k++) {
            double timeX = timeToReachX(px, vx, 0, w, k);
            if (timeX == Double.POSITIVE_INFINITY) continue;

            double y = calculateY(py, vy, timeX, h);
            if (Math.abs(y - d) > EPS) continue;

            // Calcular dirección efectiva
            double distance = vx * timeX;
            double effectiveX = px + distance;
            double normalizedX = effectiveX + w;
            int reflections = (int) (normalizedX / (2 * w));
            if (normalizedX % (2 * w) == 0) reflections--;

            double effectiveVx = vx * Math.pow(-1, reflections);
            if ((isRed && effectiveVx < 0) || (!isRed && effectiveVx > 0)) {
                bestTime = Math.min(bestTime, timeX);
            }
        }

        return bestTime;
    }

    /**
     * Calcula el tiempo para alcanzar una posición x considerando reflejos.
     * 
     * @param px Posición x inicial
     * @param vx Velocidad x
     * @param targetX Posición x objetivo
     * @param w Ancho del contenedor
     * @param k Número de reflejos a considerar
     * @return Tiempo requerido o infinito si es imposible
     */
    private static double timeToReachX(double px, double vx, double targetX, int w, int k) {
        if (vx == 0) return Double.POSITIVE_INFINITY;

        double period = 2 * w / Math.abs(vx);
        double firstTime = (targetX - px) / vx;
        double time = firstTime + k * period;

        return time < 0 ? Double.POSITIVE_INFINITY : time;
    }

    /**
     * Calcula la posición x después de un tiempo t considerando reflejos.
     * 
     * @param x0 Posición x inicial
     * @param vx Velocidad x
     * @param t Tiempo transcurrido
     * @param w Ancho del contenedor
     * @return Posición x después del tiempo t
     */
    private static double calculateX(double x0, double vx, double t, double w) {
        if (vx == 0) return x0;

        double normalizedX0 = x0 + w;
        double dx = vx * t;
        double reflectedXN = (normalizedX0 + dx) % (2 * w);
        if (reflectedXN < 0) reflectedXN += 2 * w;
        if (reflectedXN > w) reflectedXN = 2 * w - reflectedXN;
        return reflectedXN - w;
    }

    /**
     * Calcula la posición y después de un tiempo t considerando reflejos.
     * 
     * @param y0 Posición y inicial
     * @param vy Velocidad y
     * @param t Tiempo transcurrido
     * @param h Altura del contenedor
     * @return Posición y después del tiempo t
     */
    private static double calculateY(double y0, double vy, double t, int h) {
        if (vy == 0) return y0;

        double dy = vy * t;
        double reflectedYN = (y0 + dy) % (2 * h);
        if (reflectedYN < 0) reflectedYN += 2 * h;
        if (reflectedYN > h) reflectedYN = 2 * h - reflectedYN;
        return reflectedYN;
    }

    /**
     * Encuentra el próximo tiempo en que la partícula está en el lado correcto.
     * 
     * @param x0 Posición x inicial
     * @param vx Velocidad x
     * @param w Ancho del contenedor
     * @param currentT Tiempo actual
     * @param isRed Indica si la partícula es roja
     * @return Próximo tiempo válido o infinito si es imposible
     */
    private static double findNextCorrectTime(double x0, double vx, double w, double currentT, boolean isRed) {
        if (vx == 0) {
            boolean correct = (isRed && x0 < 0) || (!isRed && x0 > 0);
            return correct ? currentT : Double.POSITIVE_INFINITY;
        }

        double low = currentT;
        double high = currentT + 1e18;
        double bestTime = Double.POSITIVE_INFINITY;

        for (int iter = 0; iter < 100; iter++) {
            double mid = (low + high) / 2;
            double x = calculateX(x0, vx, mid, w);
            boolean correct = (isRed && x < 0) || (!isRed && x > 0);

            if (correct) {
                bestTime = mid;
                high = mid;
            } else {
                low = mid;
            }

            if (high - low < 1e-8) break;
        }

        double xHigh = calculateX(x0, vx, high, w);
        boolean correctHigh = (isRed && xHigh < 0) || (!isRed && xHigh > 0);
        return correctHigh ? high : bestTime;
    }

    /**
     * Simula el comportamiento de las partículas en el contenedor y muestra la solución.
     * 
     * @param h Altura del contenedor
     * @param w Ancho del contenedor
     * @param d Posición vertical de la división
     * @param b Número de partículas azules
     * @param r Número de partículas rojas
     * @param particles Matriz de partículas donde cada fila contiene [px, py, vx, vy]
     */
    public static void simulate(int h, int w, int d, int b, int r, int[][] particles) {
        double solutionTime = solve(h, w, d, r, b, particles);

        if (solutionTime != -1.0) {
            // Factor de escala para visualización
            final int SCALE_FACTOR = 10;
            
            // Escalar dimensiones del contenedor
            int scaledW = w * SCALE_FACTOR;
            int scaledH = h * SCALE_FACTOR;
            int scaledD = d * SCALE_FACTOR;
            
            // Escalar posiciones y velocidades de las partículas
            int[][] scaledParticles = new int[particles.length][4];
            for (int i = 0; i < particles.length; i++) {
                // Escalar posiciones (px, py)
                scaledParticles[i][0] = particles[i][0] * SCALE_FACTOR;
                scaledParticles[i][1] = particles[i][1] * SCALE_FACTOR;
                
                // Escalar velocidades (vx, vy) para mantener proporción temporal
                scaledParticles[i][2] = particles[i][2] * SCALE_FACTOR;
                scaledParticles[i][3] = particles[i][3] * SCALE_FACTOR;
            }
            
            // Crear contenedor con parámetros escalados
            MaxwellContainer container = new MaxwellContainer(h*10,w*10,d,b,r,scaledParticles);
            container.start(1000);
        } else {
            System.out.println("impossible");
        }
    }
}