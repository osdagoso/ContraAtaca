
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
}
