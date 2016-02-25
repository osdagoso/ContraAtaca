
import java.awt.Image;

/**
 * Base
 *
 * Modela la definición de todos los objetos de tipo
 * <code>Bala</code>
 *
 * @author Oscar Daniel González Sosa - A00816447
 * @version 1.0
 * @date 24/Febrero/2016
 */

public class Bala extends Base {
    
    private char cTipo;
    
    public Bala (int iX, int iY, Image imaImagen, char cType) {
        super(iX, iY, imaImagen);
        cTipo = cType;
    }
    
    public char getTipo() {
        return cTipo;
    }
    
    public void setTipo(char cType) {
        cTipo = cType;
    }
    
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
    
    public boolean colisionaBorde(int iWidth, int iHeight) {
        return (this.getX() < 0 || this.getX() + this.getAncho() >= iWidth ||
                this.getY() < 0);
    }
}
