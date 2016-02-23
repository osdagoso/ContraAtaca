/**
 * Base
 *
 * Modela la definición de todos los objetos de tipo
 * <code>Base</code>
 *
 * @author Guillermo A. Mendoza Soni - A01550742
 * @version 1.0
 * @date 27/Enero/2016
 */
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.ImageObserver;
import javax.swing.ImageIcon;

public class Base {

    private int iX;                 //posicion en x.       
    private int iY;                 //posicion en y.
    private int iAncho;             //ancho del objeto
    private int iAlto;              //largo del objeto
    private Image imaImagen;        //imagen.
    private ImageIcon imiImagen;    // imagen con medidas

    /**
     * Base
     * 
     * Metodo constructor usado para crear el objeto animal
     * creando el icono a partir de una imagen
     * 
     * @param iX es la <code>posicion en x</code> del objeto.
     * @param iY es la <code>posicion en y</code> del objeto.
     * @param iAncho es el <code>ancho</code> del objeto.
     * @param iAlto es el <code>Largo</code> del objeto.
     * @param imaImagen es la <code>imagen</code> del objeto.
     * 
     */
    public Base(int iX, int iY ,  Image imaImagen) {
        this.iX = iX;
        this.iY = iY;
        this.imaImagen = imaImagen;
        this.imiImagen = new ImageIcon(imaImagen);
        this.iAncho = this.imiImagen.getIconWidth();
        this.iAlto = this.imiImagen.getIconHeight();
    }

    
    /**
     * setX
     * 
     * Metodo modificador usado para cambiar la posicion en x del objeto
     * 
     * @param iX es la <code>posicion en x</code> del objeto.
     * 
     */
    public void setX(int iX) {
        this.iX = iX;
    }

    /**
     * getX
     * 
     * Metodo de acceso que regresa la posicion en x del objeto 
     * 
     * @return iX es la <code>posicion en x</code> del objeto.
     * 
     */
    public int getX() {
            return iX;
    }

    /**
     * setY
     * 
     * Metodo modificador usado para cambiar la posicion en y del objeto 
     * 
     * @param iY es la <code>posicion en y</code> del objeto.
     * 
     */
    public void setY(int iY) {
            this.iY = iY;
    }

    /**
     * getY
     * 
     * Metodo de acceso que regresa la posicion en y del objeto 
     * 
     * @return posY es la <code>posicion en y</code> del objeto.
     * 
     */
    public int getY() {
        return iY;
    }

    /**
     * setImagen
     * 
     * Metodo modificador usado para cambiar el icono de imagen del objeto
     * tomandolo de un objeto imagen
     * 
     * @param imaImagen es la <code>imagen</code> del objeto.
     * 
     */
    public void setImagen(Image imaImagen) {
        this.imaImagen = imaImagen;
        this.imiImagen = new ImageIcon(imaImagen);
        this.iAncho = this.imiImagen.getIconWidth();
        this.iAlto = this.imiImagen.getIconHeight();
    }

    /**
     * getImagen
     * 
     * Metodo de acceso que regresa la imagen que representa el icono del objeto
     * 
     * @return la imagen a partide del <code>icono</code> del objeto.
     * 
     */
    public Image getImagen() {
        return imaImagen;
    }

    /**
     * getAncho
     * 
     * Metodo de acceso que regresa el ancho del icono 
     * 
     * @return un <code>entero</code> que es el ancho de la imagen.
     * 
     */
    public int getAncho() {
        return iAncho;
    }

    /**
     * getAlto
     * 
     * Metodo que  da el alto del icono 
     * 
     * @return un <code>entero</code> que es el alto de la imagen.
     * 
     */
    public int getAlto() {
        return iAlto;
    }
    
    /**
     * colisiona
     * 
     * Método que checa si el objeto colisiona con otro objeto Base
     * 
     * @param obj objeto de la clase <code>Object</code>
     * @return boolean <code> true </code> si colisiona <code> false
     * </code> si no colisiona
     * 
     */
    public boolean colisiona(Object obj) {
        // Verificar que el objeto sea de la clase Base
        if (obj instanceof Base) {
            Rectangle recEste = new Rectangle(getX(), getY(), getAncho(),
                    getAlto());
            Rectangle recEse = new Rectangle(((Base) obj).getX(),
                    ((Base) obj).getY(), ((Base) obj).getAncho(),
                    ((Base) obj).getAlto());
            return recEste.intersects(recEse);
        }
        return false;
    }
    
        /**
     * colisionaAbajo
     * 
     * Método que checa si el objeto colisionó con otro objeto Base en la parte
     * de abajo
     * 
     * @param obj objeto de la clase <code>Object</code>
     * @return boolean <code> true </code> si colisiona <code> false
     * </code> si no colisiona
     * 
     */
    
    public boolean colisionaAbajo(Object obj) {
        if(obj instanceof Base) {
            // Condición que verifica que el objeto
            //  - La primera comparación verifica que la esquina izquierda esté
            //    dentro del margen izquierdo del objeto actual
            //  - La segunda comparación verifica que la esquina derecha esté 
            //    dentro del margen derecho del objeto actual
            //  - La tercera comparación es un grupo de dos comparaciones. Se
            //    hace un área imaginaria del mismo largo que el objeto actual y
            //    con un ancho de 10 pixeles. La comparación sirve para checar 
            //    que el mero borde de el objeto exterior se encuentre dentro de
            //    esta área imaginaria (el área imaginaria se encuentra en la 
            //    parte de arriba de el objeto actual, como si fuera una tapa).
            if( (((Base) obj).iX > iX) && ((((Base) obj).iX + ((Base) obj).iAncho) 
                    < (iX + iAncho)) && ((((Base) obj).iY + ((Base) obj).iAlto 
                    >= iY) && (((Base) obj).iY + ((Base) obj).iAlto 
                    <= iY + 20)) ){
                return true;
            }
        }
        return false;
    }
    
    /**
     * colisiona
     * 
     * Método que checa si el objeto colisiona con otro objeto Base
     * 
     * @param iX valor  <code>integer</code> que representa la x a revisar
     * @param iY valor  <code>integer</code> que representa la y a revisar
     * @return boolean <code> true </code> si colisiona <code> false
     * </code> si no colisiona
     * 
     */
    public boolean colisiona(int iX, int iY) {
        // Creo un retangulo del presente personaje
        Rectangle recEste = new Rectangle(getX(), getY(), getAncho(), getAlto());
        
        // REgreso el valor de la intersección entre este personaje y (x, y)
        return recEste.contains(iX, iY);
    }
    /**
     * colisiona
     * 
     * Método que checa si el objeto llega al fondo inferior de la pantalla
     * 
     * @param iHeightApplet valor  <code>integer</code> representa el alto del applet
     * @return boolean <code> true </code> si llegó al fondo o se pasa <code> 
     * false </code> si no ha llegado
     * 
     */    
    public boolean colisiona(int iHeightApplet){
        if(iY > iHeightApplet)
            return true;
        else
            return false;
    }
    
    /**
     * paint
     * 
     * Metodo para pintar el animal
     * 
     * @param graGrafico    objeto de la clase <code>Graphics</code> para graficar
     * @param imoObserver  objeto de la clase <code>ImageObserver</code> es el 
     *    Applet donde se pintara
     * 
     */
    public void paint(Graphics graGrafico, ImageObserver imoObserver) {
        graGrafico.drawImage(getImagen(), getX(), getY(), getAncho(), getAlto(), imoObserver);
    }

    /**
     * equals
     * 
     * Metodo para checar igualdad con otro objeto
     * 
     * @param objObjeto    objeto de la clase <code>Object</code> para comparar
     * @return un valor <code>boleano</code> que sera verdadero si el objeto
     *   que invoca es igual al objeto recibido como parámetro
     * 
     */
    public boolean equals(Object objObjeto) {
        // Desición que identifica si el objeto parametro es una instancia de 
        // la clase Base.
        if (objObjeto instanceof Base) {
            // Se regresa la comparación entre este objeto que invoca y el
            // objeto recibido como parametro.
            Base basParam = (Base) objObjeto;
            return this.getX() ==  basParam.getX() && 
                    this.getY() == basParam.getY() &&
                    this.getAncho() == basParam.getAncho() &&
                    this.getAlto() == basParam.getAlto() &&
                    this.getImagen() == basParam.getImagen();
        }
        else {
            // Regresa FALSE ya que el objeto recibido no es de tipo Base
            return false;
        }
    }

    /**
     * toString
     * 
     * Metodo para obtener la interfaz del objeto
     * 
      * @return un valor <code>String</code> que representa al objeto
     * 
     */
    public String toString() {
        return " x: " + this.getX() + " y: "+ this.getY() +
                " ancho: " + this.getAncho() + " alto: " + this.getAlto();
    }
}