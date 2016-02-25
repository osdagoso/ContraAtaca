/**
 * @author Oscar González (A00816447) y Guillermo Mendoza (A01550742)
 * @version 1.0
 * @date 24/Febrero/2016
 */

import java.awt.Image;
        
public class Malo extends Base {
    
    private int iVelocidad;
    private int iTipoMalo;
    
    public Malo(int iX, int iY, Image imaImagen, int iVel, int iTipo) {
        super(iX, iY, imaImagen);
        iVelocidad = iVel;
        iTipoMalo = iTipo;
    }
   
    public void avanza() {
    // MALO de tipo 1: solo avanza en una dirección, en este caso de arriba 
    // a abajo
        iY += 2 * iVelocidad;
    }
    
    public void avanza(Base basPerseguida) {
    // Malo de tipo 2: avanza siempre de arriba hacia abajo pero sigue al 
    // jugador en el eje X
        iY +=  2 * iVelocidad;
        
        // Si el objeto perseguido esta a la izquierda del actual: RESTAR X
        if((iX + iAncho / 2) > 
                    (basPerseguida.getX() + basPerseguida.getAncho() / 2)) {
            iX -= 2 * iVelocidad;
            
            // Si el objeto perseguido esta a la derecha del actual: SUMAR X
        } else if((iX + iAncho / 2) < 
                    (basPerseguida.getX() + basPerseguida.getAncho() / 2)) {
                iX += 2 * iVelocidad;
        }       
    }
    
    public void setVelocidad(int iVel) {
        this.iVelocidad  = iVel;
    }
    
    public int getVelocidad() {
        return iVelocidad;
    }
    
    public void setTipo(int iTipo) {
        this.iTipoMalo = iTipo;
    }
    
    public int getTipo() {
        return iTipoMalo;
    }
}
