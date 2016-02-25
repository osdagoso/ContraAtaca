/**
 * Malo
 *
 * Clase hija que hereda de <code>Base</code> y representa a los enemigos del
 * juego
 *
 * @author Oscar González (A00816447) y Guillermo Mendoza (A01550742)
 * @version 1.0
 * @date 24/Febrero/2016
 */

import java.awt.Image;
        
public class Malo extends Base {
    
    private int iVelocidad;     // Entero que indica la velocidad de movimiento
    private int iTipoMalo;      // Entero que indica el tipo de MALO
    
    /**
     * Malo
     * 
     * Metodo constructor usado para crear el objeto malo 
     * con una imagen propia, ubicación y tipo de malo en el juego
     * 
     * @param iX es la <code>posicion en x</code> del objeto.
     * @param iY es la <code>posicion en y</code> del objeto.
     * @param imaImagen es la <code>imagen</code> del objeto.
     * @param iVel es la <code>velocidad</code> del objeto.
     * @param iTipo es la <code>tipo</code> de enemigo.
     * 
     */
    public Malo(int iX, int iY, Image imaImagen, int iVel, int iTipo) {
        // Construye los demás atributos con el constructor Base() de la clase
        // padre
        super(iX, iY, imaImagen);
        iVelocidad = iVel;
        iTipoMalo = iTipo;
    }
   
    /**
     * avanza
     * 
     * Método que sirve para hacer avanzar a los malos de tipo 1
     * 
     */
    public void avanza() {
    // MALO de tipo 1: solo avanza en una dirección, en este caso de arriba 
    // a abajo
        iY += 2 * iVelocidad;
    }
    
    /**
     * avanza
     * 
     * sobrecarga del método avanza que sirve para mover a los malos de tipo 2
     * 
     * @param basPerseguida la <code>base</code> que el objeto malo perseguirá.
     * 
     */
    public void avanza(Base basPerseguida) {
    // Malo de tipo 2: avanza siempre de arriba hacia abajo pero sigue al 
    // jugador en el eje X
        iY +=  2 * iVelocidad;
        
        // Si el objeto perseguido esta a la izquierda del actual: RESTAR X
        if((iX + iAncho / 2) >= 
                    (basPerseguida.getX() + basPerseguida.getAncho() / 2)) {
            iX -= 2 * iVelocidad;
            
            // Si el objeto perseguido esta a la derecha del actual: SUMAR X
        } else if((iX + iAncho / 2) < 
                    (basPerseguida.getX() + basPerseguida.getAncho() / 2)) {
                iX += 2 * iVelocidad;
        }       
    }
    
    /**
     * setVelocidad
     * 
     * Metodo modificador usado para la velocidad del objeto
     * 
     * @param iVel es la <code>velocidad</code> a la que se mueve el objeto malo.
     * 
     */
    public void setVelocidad(int iVel) {
        this.iVelocidad  = iVel;
    }
    
    /**
     * getVelocidad
     * 
     * Metodo de acceso usado para obtener la velocidad del objeto
     * 
     */
    public int getVelocidad() {
        return iVelocidad;
    }
    
    /**
     * setTipo
     * 
     * Metodo modificador usado para cambiar el tipo de malo
     * 
     * @param iTipo es el <code>tipo</code> de objeto malo.
     * 
     */
    public void setTipo(int iTipo) {
        this.iTipoMalo = iTipo;
    }
    
    /**
     * getTipo
     * 
     * Metodo de acceso usado para obtener el tipo de malo que es actualmente
     * el objeto
     * 
     */
    public int getTipo() {
        return iTipoMalo;
    }
}
