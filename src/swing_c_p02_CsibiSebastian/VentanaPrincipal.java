/*
* Proyecto: swing_c_p02_CsibiSebastian
* Paquete: swing_c_p02_CsibiSebastian
* Archivo: VentanaPrincipal.java
* Autor/a: Tu Nombre y Apellidos
* Fecha: 21 nov 2025 8:32:46
*
* Descripción:
* [Resumen del propósito del archivo/clase.]
*
* Licencia:
* [Condiciones de uso/licencia.]
*/
package swing_c_p02_CsibiSebastian;

import java.awt.Toolkit;

import javax.swing.JFrame;

/**
 * 
 */
public class VentanaPrincipal extends JFrame {
	
	
	public VentanaPrincipal() {
        super("Swing Práctica02 - C - Sebastian Csibi");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setIconImage(Toolkit.getDefaultToolkit().getImage(
                getClass().getResource("/recursos/icono.png")));
        
        
        this.setSize(900, 600);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
	}
}
