import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedReader;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import java.net.URL;
import java.util.LinkedList;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 * Tarea 3: Atrapando objetos
 *
 * Juego donde un OBJETO cae de la parte superior de la pantalla del applet y el
 * JUGADOR debe de atraparlos para evitar perder vidas y ganar puntos: cada vez
 * que caigan 10 OBJETOS al suelo el jugador perderá una vida y 20 puntos menos,
 * cada vez que el JUGADOR atrape un OBJETO ganará 100 puntos; el JUGADOR se 
 * mueve haciendo click sobre él y haciendo "drag" a donde se vaya a dirigir.
 *
 * @author Guillermo A. Mendoza Soni - A01550742
 * @version 1.0
 * @date 03/Febrero/2016
 */
public class Juego5 extends JFrame implements Runnable, MouseListener, 
        MouseMotionListener {

    private static final int iWIDTH = 800;      //Ancho del JFrame
    private static final int iHEIGHT = 600;     //Alto del JFrame
    
    private Base basJugador;        // Pertenece al JUGADOR
    private Base basObjeto;         // Será el OBJETOS que cae
    private Base basFallido;        // Aparece cuando un OBJETO cae hasta el fondo
    private Base basVida;           // Objeto que representa la vida del jugador
    
    private LinkedList <Base> lklObjetos; // Coleccion de OBJETOS que caen
    private LinkedList <Base> lklFallidos;// Coleccion de imagenes que aparecen
                                          // cuando una imagen se cae al fondo
    
    private Image imaImagenFondo;   // Imagen de fondo en el juego
    private Image imaImagenJugador; // Imagen del JUGADOR
    private Image imaImagenJugador2;// Imagen del JUGADOR con la mitad de vidas    
    private Image imaImagenJugador3;// Imagen del JUGADOR con pocas vidas
    private Image imaImagenObjeto;  // Imagen del OBJETO que cae y ofrece puntos
    private Image imaImagenFallido; // Pertenece al objeto FALLIDO
    
    private SoundClip sonFondo;     // Sonido de background
    private SoundClip sonAtrapa;    // Sonido cuando el JUGADOR atrapa un punto
    private SoundClip sonFallido;   // Sonido cuando un OBJETO llega al fondo
    
    
    private int iMouseX;            // Posición en el eje 'X' del click
    private int iMouseY;            // Posición en el eje 'X' del click
    private int iOffsetX;           // Variable de ajuste de coordenada X
    private int iOffsetY;           // Variable de ajuste de coordenada Y
    private int iVelocidad; // Indica la velocidad de movimiento de los OBJETOS
    private int iVidas;     // Indica la cantidad de vidas que tiene el JUGADOR
    private int iPuntos;            // Indica los puntos acumulados del JUGADOR
    private int iRandomObj;         // Tamaño random de la lista de OBJETOS
    private int iFallidos;  // Contador para verificar cuantas veces el JUGADOR
                            // dejó caer un OBJETO hasta el límite inferior.
    
    private boolean bPressed;       // Determina si hay un click con el mouse
    private boolean bColision;  // Determina si hubo una colisión con el JUGADOR
    private boolean bColVentana;// Determina si el OBJETO ya llegó al límite 
                                // inferior de la pantalla del applet
    
    /* objetos para manejar el buffer del Applet y 
       que la imagen no parpadee */
    private Image    imaImagenApplet;   // Imagen a proyectar en Applet	
    private Graphics graGraficaApplet;  // Objeto grafico de la Imagen
          	
    /** 
     * init
     * 
     * Metodo sobrescrito de la clase <code>Applet</code>.<P>
     * En este metodo se inizializan las variables o se crean los objetos
     * a usarse en el <code>Applet</code> y se definen funcionalidades.
     * 
     */
    public Juego5() {
        // Creación del applet con un tamaño de 800, 500
        setSize(800,500);
             
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
        
        // Sonido que se reproducirá cada vez que destruya un ENEMIGO
        sonAtrapa = new SoundClip("KeyTyping.wav");
        // Sonido que se reproducirá cada vez que el JUGADOR colisione con el
        // ENEMIGO
        sonFallido = new SoundClip("XPerror.wav");
        // Se asgina el sonido que se reproducirá de fondo en el juego
        sonFondo = new SoundClip("Starwars_PhantomMenace.wav");      
        
        // Se crea el objeto de jugador
	basJugador = new Base(0, 0,
                Toolkit.getDefaultToolkit().getImage(urlImagenPrincipal));  
        
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
        
        // Se crea el objeto de vidas y se le asigna una imagen
        URL urlImagenIcon = this.getClass().getResource("vida.gif");
        basVida = new Base(0, 0,
                Toolkit.getDefaultToolkit().getImage(urlImagenIcon));

        // Inicializa las coordenadas de los objetos dentro de los límites del 
        //  applet
        reposicionaJugador(basJugador);
        for(Base basObjeto : lklObjetos){
            reposicionaObjeto(basObjeto);
        }
        
        basVida.setX(getWidth() - basVida.getAncho());
        basVida.setY(5);
        
        
        // La siguiente sección de código inicializa las variables globales que
        // se utilizarán en el juego :
        iMouseX = 0;        
        iMouseY = 0;
        iOffsetX = 0;
        iOffsetY = 0;
        iVelocidad = 1;         // Velocidad de jugador por default (1 unidad)
        iVidas = 5;             // El jugador inicia con 5 vidas
        iPuntos = 0;            // Puntaje inicial del jugador
        iFallidos = 0;          // El JUGADOR aún no ha dejado caer un OBJETO
        bPressed = false;       // NO se está haciendo click al iniciar
        bColision = false;      // NO hay colisión al inicio del juego
        
        // La applet escuchará las siguientes interrupciones:
        addMouseListener(this); 
        addMouseMotionListener(this);
        start();
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
            actualiza();
            checaColision();
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
        // Detecta si no se ha hecho un click en el mouse, si se hizo
        // un click y dragged sobre el jugador, se actualizan sus posiciones
        if(bPressed){
            basJugador.setX(iMouseX - iOffsetX);
            basJugador.setY(iMouseY - iOffsetY);
        }
           
        // Movimiento del OBJETO que "cae" al fondo del applet
        for(Base basObjeto : lklObjetos){
            basObjeto.setY((int) (basObjeto.getY() + (1 * iVelocidad) + 
                    (int)(Math.random() * 3)));
        }
        
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
        
        // Se actualiza la posición del OBJETO en caso de que el JUGADOR lo haya
        // atrapado
        for(Base basObjeto : lklObjetos){
            if(basJugador.colisionaAbajo(basObjeto)){
                // Se reposiciona el objeto hasta arriba
                reposicionaObjeto(basObjeto);
                // Se aumentan los puntos por atraparlo 
                iPuntos += 100;
                // Se reproduce un sonido de que el JUGADOR atrapó el OBJETO
                sonAtrapa.play();
            }
        }
  
        // Desición para cuando el JUGADOR haya superado el límite de OBJETOS 
        // que se pueden "caer" (OBJETOS que alcanzan el límite inferior de la 
        // pantalla del applet)
        if(iFallidos >= 10){
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
	
    /**
     * checaColision
     * 
     * Metodo usado para checar la colision entre objetos
     * 
     */
    public void checaColision(){
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
        
        // Desición que detecta cuando el jugador ha colsionado con el enemigo
        for(Base basObjeto : lklObjetos){
            if (basJugador.colisionaAbajo(basObjeto)) {
                bColision = true;
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
        if (basJugador != null && lklObjetos != null && basVida != null &&
                imaImagenFondo != null) {
        	// ...Dibujar la imagen de fondo
        	graDibujo.drawImage(imaImagenFondo, 0, 0, getWidth(),
                        getHeight(), this);
                
                // Desicion para cambiar la imagen si el jugador está en cierto
                // rango de vidas
                if(4 > iVidas && iVidas > 1){
                    basJugador.setImagen(imaImagenJugador2);
                }
                if(1 >= iVidas){
                    basJugador.setImagen(imaImagenJugador3);
                }
                
                // Dibuja la imagen de cada enemigo
                for (Base basObjeto : lklObjetos){
                    basObjeto.paint(graDibujo, this);
                }
                
                // Dibuja la imagen del jugador en el applet
                basJugador.paint(graDibujo, this);
                
                // Dibuja las vidas del jugador
                for (int iK = 0; iK < iVidas; iK++) {
                    basVida.paint(graDibujo, this);
                    basVida.setX(basVida.getX() - basVida.getAncho() - 1);
                }
                basVida.setX(getWidth() - basVida.getAncho());
        } // En caso de que no haya cargado la imagen...
        else {
                graDibujo.drawString("No se cargo la imagen..", 20, 20);
        }
         
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
        Juego5 juego = new Juego5();
        juego.setSize(iWIDTH, iHEIGHT);
        juego.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        juego.setVisible(true);
    }

    @Override
    public void mouseClicked(MouseEvent mouEvent) {
    }
    // Cuando el usuario haga clicl con el mouse, se capturará las coordenadas
    // donde se encuentre ubicado el mouse dentro del applet
    @Override
    public void mousePressed(MouseEvent mouEvent) {
    }

    @Override
    public void mouseReleased(MouseEvent mouEvent) {
        // Se apaga la booleana del dragged para indicar que ya no se está 
        // moviendo
        bPressed = false;
    }

    @Override
    public void mouseEntered(MouseEvent mouEvent) {  
    }

    @Override
    public void mouseExited(MouseEvent mouEvent) { 
    }
    
    // Si el usuario mueve el mouse mientras se ha hecho un click, entonces se
    // hirán actualizando las coordenadas del objeto del jugador con las del
    // movimiento del cursor, es decir, el usuario podrá hacer un drag de su 
    // jugador dentro de la pantalla del applet
    @Override
    public void mouseDragged(MouseEvent mouEvent) {
        // Activamos el booleano (TRUE) para indicar que el usuario ha hecho 
        // click.
        
        // Si no estoy moviendome aún y le dieron click al jugador entonces
        // se actualiza el offset
        
        if(!bPressed && basJugador.colisiona(mouEvent.getX(), mouEvent.getY())){
            iOffsetX = mouEvent.getX() - basJugador.getX();
            iOffsetX = mouEvent.getY() - basJugador.getY();
            bPressed = true;
        }
        // Se actualizan las posiciones del mouse en sus respectivas variables 
        // de coordenadas 'X' y 'Y'.
        iMouseX = mouEvent.getX();
        iMouseY = mouEvent.getY();
    }

    @Override
    public void mouseMoved(MouseEvent mouEvent) {
    }
}

