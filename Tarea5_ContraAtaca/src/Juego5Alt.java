import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedReader;
import java.net.URL;
import java.util.LinkedList;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 * Tarea 5: Contra Ataca
 *
 * Juego donde un ENEMIGO avanza desde la parte superior de la pantalla, el 
 * JUGADOR se defiende de los ENEMIGOS disparando balas que al momento de 
 * colisinoar on el ENEMIGO este desaparece, si el ENEMIGO colisiona con el 
 * JUGADOR se perderá un punto, y si colisiona 5 veces el JUGADOR pierde una vida
 *
 * @author Oscar González (A00816447) y Guillermo Mendoza (A01550742)
 * @version 1.0
 * @date 24/Febrero/2016
 */
public class Juego5Alt extends JFrame implements Runnable, KeyListener {

    private static final int iWIDTH = 800;      //Ancho del JFrame
    private static final int iHEIGHT = 600;     //Alto del JFrame
    
    private Base basJugador;        // Pertenece al JUGADOR
    private Base basObjeto;         // Será el OBJETOS que cae
    private Base basVida;           // Objeto que representa la vida del jugador
    
    private LinkedList <Base> lklObjetos; // Coleccion de OBJETOS que caen
    
    private Image imaImagenFondo;   // Imagen de fondo en el juego
    private Image imaImagenJugador; // Imagen del JUGADOR
    private Image imaImagenJugador2;// Imagen del JUGADOR con la mitad de vidas    
    private Image imaImagenJugador3;// Imagen del JUGADOR con pocas vidas
    private Image imaImagenObjeto;  // Imagen del OBJETO que cae y ofrece puntos
    private Image imaImagenFallido; // Pertenece al objeto FALLIDO
    
    private Image imaImagenGameOver;// Imagen de Game Over
    private Image imaImagenPausa;   // Imagen de pausa en el juego
    
    private SoundClip sonFondo;     // Sonido de background
    private SoundClip sonAtrapa;    // Sonido cuando el JUGADOR atrapa un punto
    private SoundClip sonFallido;   // Sonido cuando un OBJETO llega al fondo
    
    private int iDireccion;         // Indica la tecla de direccion de movimiento
    private int iVelocidad; // Indica la velocidad de movimiento de los OBJETOS
    private int iVidas;     // Indica la cantidad de vidas que tiene el JUGADOR
    private int iPuntos;            // Indica los puntos acumulados del JUGADOR
    private int iRandomObj;         // Tamaño random de la lista de OBJETOS
    private int iFallidos;  // Contador para verificar cuantas veces el JUGADOR
                            // dejó caer un OBJETO hasta el límite inferior.
    
    private boolean bPressed;       // Determina si se presionó una tecla
    private boolean bColision;  // Determina si hubo una colisión con el JUGADOR
    private boolean bColVentana;// Determina si el OBJETO ya llegó al límite 
                                // inferior de la pantalla del applet
    private boolean bPause;    // Determina si el juego está en pausa
    
    /* objetos para manejar el buffer del Applet y 
       que la imagen no parpadee */
    private Image    imaImagenApplet;   // Imagen a proyectar en Applet	
    private Graphics graGraficaApplet;  // Objeto grafico de la Imagen
          	
    /** 
     * Juego5()
     * 
     * Constructor de la clase <code>Juego5</code>.<P>
     * En este metodo se inizializan las variables o se crean los objetos
     * a usarse en el <code>JFrame</code> y se definen funcionalidades.
     * 
     */
    public Juego5Alt() {
        // Creación del applet con un tamaño de 800, 600
        setSize(iWIDTH,iHEIGHT);
        inicializaImagenes();    
        inicializaSonidos();
        inicializaJugador();
        inicializaEnemigos();
        inicializaVidas();       
        
        // La siguiente sección de código inicializa las variables globales que
        // se utilizarán en el juego :
        iVelocidad = 1;         // Velocidad de jugador por default (1 unidad)
        iVidas = 5;             // El jugador inicia con 5 vidas
        iPuntos = 0;            // Puntaje inicial del jugador
        iFallidos = 0;          // El JUGADOR aún no ha dejado caer un OBJETO
        bPressed = false;       // NO se está haciendo click al iniciar
        bColision = false;      // NO hay colisión al inicio del juego
        bPause = false;         // El juego NO inicia en pausa
        
        // La applet escuchará las siguientes interrupciones:
        addKeyListener(this);
        
        start();
    }
    
    public void inicializaImagenes() {
        // Se asigna la imagen del jugador contenida en la carpeta /src
	URL urlImagenPrincipal = this.getClass().getResource("Programador1.gif");
        imaImagenJugador = Toolkit.getDefaultToolkit().getImage(urlImagenPrincipal);
        
        // Se asigna la imagen del jugador cuando tiene 3 o 2 vidas restantes,
        // la imagen está en la carpeta /src
        URL urlImagenPricipAlt1 = this.getClass().getResource("Programador2.gif");
        imaImagenJugador2 = Toolkit.getDefaultToolkit().getImage(urlImagenPricipAlt1);

        // Se asigna la imagen del jugador cuando tiene 1 vida restantes, la 
        // imagen está en la carpeta /src
        URL urlImagenPricipAlt2 = this.getClass().getResource("Programador3.gif");
        imaImagenJugador3 = Toolkit.getDefaultToolkit().getImage(urlImagenPricipAlt2);
        
        // Se asigna la imagen del objeto que cae, contenida en la carpeta /src
        URL urlImagenAnt = this.getClass().getResource("Binario.gif");
        imaImagenObjeto = Toolkit.getDefaultToolkit().getImage(urlImagenAnt);

        URL urlImagenFallo = this.getClass().getResource("ErrorWindow.gif");
        imaImagenFallido = Toolkit.getDefaultToolkit().getImage(urlImagenFallo);
        
        // Se asigna la imagen de fondo para el juego
        URL urlImagenFondo = this.getClass().getResource("Background.gif");
        imaImagenFondo = Toolkit.getDefaultToolkit().getImage(urlImagenFondo);
        
        // Se asigna la imagen a desplegar cuando las vidas del jugador sean 0
        URL urlImagenGameOver = this.getClass().getResource("game_over.png");
        imaImagenGameOver = Toolkit.getDefaultToolkit().getImage(urlImagenGameOver);
        
        // Se asigna la imagen a desplegar cuando se pause el juego
        URL urlImagenPausa = this.getClass().getResource("Pausa.png");
        imaImagenPausa = Toolkit.getDefaultToolkit().getImage(urlImagenPausa);

    }
    
    public void inicializaSonidos() {
        // Sonido que se reproducirá cada vez que destruya un ENEMIGO
        sonAtrapa = new SoundClip("KeyTyping.wav");
        // Sonido que se reproducirá cada vez que el JUGADOR colisione con el
        // ENEMIGO
        sonFallido = new SoundClip("XPerror.wav");
        // Se asgina el sonido que se reproducirá de fondo en el juego
        sonFondo = new SoundClip("Starwars_PhantomMenace.wav"); 
    }
    
    public void inicializaJugador() {
        // Se crea el objeto de jugador
        URL urlImagenPrincipal = this.getClass().getResource("Programador1.gif");
	basJugador = new Base(0, 0,
                Toolkit.getDefaultToolkit().getImage(urlImagenPrincipal)); 
        
        // Inicializa las coordenadas del JUGADOR dentro de los límites del 
        // applet
        reposicionaJugador(basJugador);
    }
    
    public void inicializaEnemigos() {
        
        URL urlImagenAnt = this.getClass().getResource("Binario.gif");
        lklObjetos = new LinkedList(); // Se crean la lista de enemigos
        // Se genera un número al azar de enemigos entre 7 y 10
        // Math random * 4 genera un número al hazar entre 0 y 4
        iRandomObj = (int)(Math.random() * 9) + 7;
        
        // Se crean los enemigos
        for (int iI = 0; iI < iRandomObj; iI++){
            Base basObjeto = new Base(0, 0,
                Toolkit.getDefaultToolkit().getImage(urlImagenAnt));
            lklObjetos.add(basObjeto);
        }
        
        // Inicializa las posiciones de los ENEMIGOS que caerán desde la parte
        // superior de la ventana
        for(Base basObjeto : lklObjetos){
            reposicionaObjeto(basObjeto);
        }
        
    }
    
    public void inicializaVidas() {
        // Se crea el objeto de vidas y se le asigna una imagen
        URL urlImagenIcon = this.getClass().getResource("vida.gif");
        basVida = new Base(0, 0,
                Toolkit.getDefaultToolkit().getImage(urlImagenIcon));
        
        // Se indica la posición de las vidas en la pantalla del JFrame
        basVida.setX(getWidth() - basVida.getAncho());
        basVida.setY(5);
        
    }
    
    /**
     * reposicionaJugador
     * 
     * Metodo que reposiciona solamente el jugador en la parte inferior del 
     * applet
     * 
     */
    public void reposicionaJugador(Base basEjemplo) {
        // Posicionamiento del JUGADOR en la parte central inferior del applet
        basEjemplo.setX(getWidth() / 2);
        basEjemplo.setY(getHeight());
    }

    /**
     * reposicionaObjeto
     * 
     * Metodo que reposiciona el objeto de manera aleatoria en la parte superior
     * del applet
     * 
     */    
    public void reposicionaObjeto(Base basEjemplo){
        // El objeto aparece de manera aleatoria a lo largo de la pantalla 
        basEjemplo.setX((int)((Math.random() * getWidth()) + 0));
        // El objeto aparece fuera del área de la pantalla en las coordenadas
        // en Y, para que de el efecto de que estan cayendo al azar
        basEjemplo.setY((int)((Math.random() * (-300)) - basEjemplo.getAlto()));
        // En caso de que el OBJETO se salga del borde derecho de la pantalla 
        // del applet se corregirá su ubicación
        if(basEjemplo.getX() + basEjemplo.getAncho() > getWidth()){
            basEjemplo.setX(getWidth() - basEjemplo.getAncho());
        }
        // En caso de que el OBJETO se salga del borde izquierdo de la pantalla 
        // del applet se corregirá su ubicación
        if(basEjemplo.getX() < 0)
            basEjemplo.setX(0);
    }
	
    /** 
     * start
     * 
     * Metodo sobrescrito de la clase <code>Applet</code>.<P>
     * En este metodo se crea e inicializa el hilo
     * para la animacion este metodo es llamado despues del init o 
     * cuando el usuario visita otra pagina y luego regresa a la pagina
     * en donde esta este <code>Applet</code>
     * 
     */
    public void start () {
        // Se crea un Thread para el juego
        Thread th = new Thread (this);
        th.start ();
    }
	
    /** 
     * run
     * 
     * Metodo sobrescrito de la clase <code>Thread</code>.<P>
     * En este metodo se ejecuta el hilo, que contendrÃ¡ las instrucciones
     * de nuestro juego.
     * 
     */
    public void run () {
        /* mientras el jugador tenga vias, se actualizan posiciones de jugadores
           se checa si hubo colisiones para desaparecer jugadores o corregir
           movimientos y se vuelve a pintar todo
        */ 
        
        while (iVidas > 0) {
            if (!bPause) {
                actualiza();
                checaColision();
            }
            repaint();
            //  Se manda a dormir (pausar) al thread del juego
            try	{
                Thread.sleep (20);
            }
            catch (InterruptedException iexError) {
                System.out.println("Hubo un error en el juego " + 
                        iexError.toString());
            }
	}
    }
	
    /** 
     * actualiza
     * 
     * Metodo que actualiza el estado de los objetos
     * 
     */
    public void actualiza(){
        actualizaJugador();
        actualizaVidas();
        actualizaEnemigo();
    }
    
    public void actualizaJugador() {
        // Detecta si se ha oprimido una tecla, si se opimió entonces se
        // ve si la tecla pertence a una de las que mueve al personaje y se 
        // actualiza su posición
        if(bPressed) {
            // El jugador se mueve a la izquierda
            if (iDireccion == 1) {
                basJugador.setX(basJugador.getX() - 3);
            }
            // El jugador se mueve a la derecha
            else if (iDireccion == 2) {
                basJugador.setX(basJugador.getX() + 3);
            }
        }
    }
    
    public void actualizaVidas() {
        // Desición para cuando el JUGADOR haya superado el límite de OBJETOS 
        // que se pueden "caer" (OBJETOS que alcanzan el límite inferior de la 
        // pantalla del applet)
        if(iFallidos >= 5){
            // Se resta una vida
            iVidas--;
            // Aumenta la velocidad en la que caen los OBJETOS debido a que se 
            // perdió una vida
            iVelocidad++;
            // Se reinicia el número de veces que se ha fallado en atrapar un 
            // OBJETO
            iFallidos = 0;
        }
    }
    
    public void actualizaEnemigo() {
        // Movimiento del OBJETO que "cae" al fondo del applet
        for(Base basObjeto : lklObjetos){
            basObjeto.setY((int) (basObjeto.getY() + (1 * iVelocidad) + 
                    (int)(Math.random() * 3)));
        }
    }
    
    /**
     * checaColision
     * 
     * Metodo usado para checar la colision entre objetos
     * 
     */
    public void checaColision(){
        colisionPantallaJugador();
        colisionPantallaEnemigo();
        colisionEnemigo();
    }
    
    public void colisionPantallaJugador() {
        // Detecta la colisión del jugador con alguno de los bordes de la 
        // pantalla del applet, cuando sucede una colisión, el objeto del jugador
        // se mantendrá al margen del applet.
        if (basJugador.getX() < 0) {
            basJugador.setX(0);
        }
        else if (basJugador.getX() + basJugador.getAncho() >= getWidth()) {
            basJugador.setX(getWidth() - basJugador.getAncho());
        }
        else if (basJugador.getY() < 0) {
            basJugador.setY(0);
        }
        else if (basJugador.getY() + basJugador.getAlto() >= getHeight()) {
            basJugador.setY(getHeight() - basJugador.getAlto());
        }
    }
    
    public void colisionPantallaEnemigo() {
        // Se actualiza la posición del OBJETO en caso de que se halla salido de
        // el límite inferior de la pantalla del applet
        for(Base basObjeto : lklObjetos){
            if(basObjeto.getY() >= getHeight()){
                // Se reposiciona el objeto hasta arriba
                reposicionaObjeto(basObjeto);
                // Se aumenta el número de veces que el JUGADOR dejar caer un 
                // objeto antes de perder una vida
                iFallidos++;
                // Se restan 20 puntos al jugador como penalización
                iPuntos -= 20;
                // Se reproduce un sonido de que el objeto se cayó
                sonFallido.play();
            }
        }
    }
    
    public void colisionEnemigo() {
        // Se actualiza la posición del OBJETO en caso de que el JUGADOR lo haya
        // atrapado
        for(Base basObjeto : lklObjetos){
            if(basJugador.colisionaAbajo(basObjeto)){
                // Se reposiciona el objeto hasta arriba
                reposicionaObjeto(basObjeto);
                // Se aumentan los puntos por atraparlo 
                iPuntos += 10;
                // Se reproduce un sonido de que el JUGADOR atrapó el OBJETO
                sonAtrapa.play();
            }
        }
    }
    
     /**
     * paint
     * 
     * Metodo sobrescrito de la clase <code>JFrame</code>,
     * heredado de la clase Container.<P>
     * En este metodo lo que hace es actualizar el contenedor y 
     * define cuando usar ahora el paint
     * 
     * @param graGrafico es el <code>objeto grafico</code> usado para dibujar.
     * 
     */
    public void paint (Graphics graGrafico){
        // Inicializan el DoubleBuffer
        if (imaImagenApplet == null) {
                imaImagenApplet = createImage (this.getSize().width, 
                        this.getSize().height);
                graGraficaApplet = imaImagenApplet.getGraphics ();
        }

        // Actualiza la imagen de fondo.
        URL urlImagenFondo = this.getClass().getResource("Background.gif");
        Image imaImagenFondo = Toolkit.getDefaultToolkit().getImage(urlImagenFondo);
        graGraficaApplet.drawImage(imaImagenFondo, 0, 0, getWidth(),
                getHeight(), this);
        
        // Actualiza el Foreground.
        graGraficaApplet.setColor (getForeground());
        paint1(graGraficaApplet);

        // Dibuja la imagen actualizada
        graGrafico.drawImage(imaImagenApplet, 0, 0, this);
    }
    
    /**
     * paint1
     * 
     * Metodo sobrescrito de la clase <code>Applet</code>,
     * heredado de la clase Container.<P>
     * En este metodo se dibuja la imagen con la posicion actualizada,
     * ademas que cuando la imagen es cargada te despliega una advertencia.
     * 
     * @param graDibujo es el objeto de <code>Graphics</code> usado para dibujar.
     * 
     */
    public void paint1(Graphics graDibujo) {
        // Desición que detecta si la imagen se cargo...
        if (basJugador != null && lklObjetos != null && basVida != null
                && imaImagenFondo != null) {
            if (iVidas > 0) {
                if (!bPause) {
                    // ...Dibujar la imagen de fondo
                    graDibujo.drawImage(imaImagenFondo, 0, 0, getWidth(),
                            getHeight(), this);

                    paintPrincipal(graDibujo);

                    // Dibuja la imagen de cada enemigo
                    for (Base basObjeto : lklObjetos) {
                        basObjeto.paint(graDibujo, this);
                    }

                    paintLetreros(graDibujo);
                } else {
                    graDibujo.drawImage(imaImagenPausa, 0, 0, getWidth(),
                            getHeight(), this);
                }
            } else {
                graDibujo.drawImage(imaImagenGameOver, 0, 0, getWidth(),
                        getHeight(), this);
            }
        } // En caso de que no haya cargado la imagen...
        else {
            graDibujo.drawString("No se cargo la imagen..", 20, 20);
        }
    }
    
    /**
     * paintPrincipal
     * 
     * En este método se dibuja el personaje principal.
     * 
     * @param graDibujo es el objeto de <code>Graphics</code> usado para dibujar.
     * 
     */
    public void paintPrincipal(Graphics graDibujo) {
        // Cambiar la imagen si el jugador está en cierto rango de vidas
        if (iVidas < 4 && iVidas > 1) {
            basJugador.setImagen(imaImagenJugador2);
        }
        if (iVidas <= 1) {
            basJugador.setImagen(imaImagenJugador3);
        }

        // Dibuja la imagen del jugador en el applet
        basJugador.paint(graDibujo, this);
    }
    
    /**
     * paintLetreros
     * 
     * En este metodo se dibujan los letreros de score y vidas.
     * 
     * @param graDibujo es el objeto de <code>Graphics</code> usado para dibujar.
     * 
     */
    public void paintLetreros(Graphics graDibujo) {
        // Se crea un string que contendrá los puntos
        String sPuntaje = Integer.toString(iPuntos);
        // Se declara un font específico, tipo y tamaño de letra
        Font fPuntaje = new Font("Consolas", Font.BOLD, 90);
        // Se establece las características del font al string que se pintará
        graDibujo.setFont(fPuntaje);
        // Las letras serán de color blanco
        graDibujo.setColor(Color.white);
        // Se pinta el string con los valores antes mencionados
        graDibujo.drawString(sPuntaje, 25, 75);

        // Dibuja las vidas del jugador
        for (int iK = 0; iK < iVidas; iK++) {
            basVida.paint(graDibujo, this);
            basVida.setX(basVida.getX() - basVida.getAncho() - 1);
        }
        basVida.setX(getWidth() - basVida.getAncho());
    }

    /** 
     * getWidth
     * 
     * Función que regresa el tamaño del ancho del JFrame.
     * 
     */
    public int getWidth() {
        return iWIDTH;
    }
    
    /** 
     * getHeight
     * 
     * Función que regresa el tamaño de la altura del JFrame.
     * 
     */
    public int getHeight() {
        return iHEIGHT;
    }

    /** 
     * main
     * 
     * Función principal del juego.
     * 
     * @param args
     */
    public static void main(String[] args) {
        Juego5Alt juego = new Juego5Alt();
        juego.setSize(iWIDTH, iHEIGHT);
        juego.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        juego.setVisible(true);
    }

   
    @Override
    public void keyTyped(KeyEvent ke) {
    }

    @Override
    public void keyPressed(KeyEvent keyEvent) {
        if (iVidas > 0) {
            // Si ninguna tecla usada en el juego está presionada entonces...
            if (!bPressed) {
                // Se habilita el booleano como verdadero ya que se está presionando
                // una tecla
                bPressed = true;
                // Se captura la tecla de "flecha a la izquierda" como una variable
                // de valor 1 en "iDireccion"
                if (keyEvent.getKeyCode() == keyEvent.VK_LEFT) {
                    iDireccion = 1;
                } // Se captura la tecla de "flecha a la derecha" como una variable
                // de valor 2 en "iDireccion"
                else if (keyEvent.getKeyCode() == keyEvent.VK_RIGHT) {
                    iDireccion = 2;
                }
            }
            // Al presionar la tecla P se alterna entre pausa y no pausa
            if (keyEvent.getKeyCode() == keyEvent.VK_P) {
                if (bPause) {
                    bPause = false;
                } else {
                    bPause = true;
                }
            }
        }
        else {
            // Si se desea continuar, destruir el JFrame actual y volver a
            // llamar el método main
            if (keyEvent.getKeyCode() == keyEvent.VK_S) {
                this.dispose();
                main(new String[]{""});
            }
            // Si no se desea continuar, simplemente destruir el JFrame
            else if (keyEvent.getKeyCode() == keyEvent.VK_N) {
                this.dispose();
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent ke) {
        bPressed = false;
    }
}

