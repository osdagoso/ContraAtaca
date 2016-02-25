/**
 * Bala
 *
 * Clase hija que hereda de <code>Base</code> y modela la definición de los
 * disparos o balas en el juego.
 *
 * @author Oscar González (A00816447) y Guillermo Mendoza (A01550742)
 * @version 1.0
 * @date 24/Febrero/2016
 */

import java.awt.Image;

public class Bala extends Base {
    
    private char cTipo;   // Determina el tipo de movimiento de la bala
    
    /**
     * Bala
     * 
     * Método constructor utilizado para crear un nuevo objeto bala;
     * hereda los atributos inicizalizados por el constructor de la
     * clase <code>Base</code>.
     * 
     * @param iX es la <code>posicion en x</code> del objeto.
     * @param iY es la <code>posicion en y</code> del objeto.
     * @param iAncho es el <code>ancho</code> del objeto.
     * @param iAlto es el <code>largo</code> del objeto.
     * @param imaImagen es la <code>imagen</code> del objeto.
     * @param cType es el <code>tipo</code> de bala creada.
     */
    public Bala (int iX, int iY, Image imaImagen, char cType) {
        super(iX, iY, imaImagen);
        cTipo = cType;
    }
    
    /**
     * getTipo
     * 
     * Metodo de acceso que regresa la clave del tipo de la bala.
     * 
     * @return cTipo es la <code>clave de tipo</code> de la bala.
     */
    public char getTipo() {
        return cTipo;
    }
    
    /**
     * setTipo
     * 
     * Metodo modificador usado para cambiar el tipo de la bala en cuestión.
     * 
     * @param cType es la <code>clave de tipo</code> de la bala.
     * 
     */
    public void setTipo(char cType) {
        cTipo = cType;
    }
    
    /**
     * avanza
     * 
     * Método que se encarga del movimiento de la bala, el cual difiere
     * dependiendo de la clave de tipo establecida.
     * 
     */
    public void avanza() {
        switch (cTipo) {
            case 'C':
                this.setY(this.getY() - 3);
                break;
            case 'I':
                this.setX(this.getX() - (int)(Math.random() * 2) - 1);
                this.setY(this.getY() - (int)(Math.random() * 2) - 1);
                break;
            case 'D':
                this.setX(this.getX() + (int)(Math.random() * 2) + 1);
                this.setY(this.getY() - (int)(Math.random() * 2) - 1);
                break;
        }
    }
    
    /**
     * colisionaBorde
     * 
     * Método que verifica si la bala está o ha colisionado con un borde del
     * JFrame.
     * 
     * @param iWidth es el <code>ancho</code> del JFrame.
     * @param iHeight es el <code>alto</code> del JFrame.
     * @return bColisiona indica si la bala colisionó con algún borde.
     */
    public boolean colisionaBorde(int iWidth, int iHeight) {
        return (this.getX() < 0 || this.getX() + this.getAncho() >= iWidth ||
                this.getY() < 0);
    }
}
