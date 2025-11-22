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

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

import paneles.PanelJDialog;

/**
 * 
 */
public class VentanaPrincipal extends JFrame {
    // ======================================================= //
    //   Declaración de todos los componentes de la ventana    //
    // ======================================================= //
	private PanelJDialog panelJDialog;
	JMenuBar barra = new JMenuBar();
	
	private JButton pisoAlta;
	private JButton pisoBaja;
	private JButton salir;
	private JButton acercaDe;
	
	public VentanaPrincipal() {
        super("Gestión Apartamentos Turísticos Bolivianos");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setIconImage(Toolkit.getDefaultToolkit().getImage(
                getClass().getResource("/recursos/icono.png")));
        
        Dimension pantalla = Toolkit.getDefaultToolkit().getScreenSize();

        
        JMenu menuArchivo = new JMenu("Archivo");
        JMenu menuRegistro = new JMenu("Registro");
        JMenu menuAyuda = new JMenu("Ayuda");
        
        barra.add(menuArchivo);
        barra.add(menuRegistro);
        barra.add(menuAyuda);
        
        JMenuItem itemAlta = new JMenuItem("Alta Pisos");
        JMenuItem itemBaja = new JMenuItem("Baja Pisos");
        JMenuItem itemSalir = new JMenuItem("Salir");
        JMenuItem itemAcerca = new JMenuItem("Acerca de...");
        
        itemAlta.setAccelerator(KeyStroke.getKeyStroke("ctrl N"));
        itemBaja.setAccelerator(KeyStroke.getKeyStroke("ctrl B"));
        itemSalir.setAccelerator(KeyStroke.getKeyStroke("ctrl Q"));
        itemAcerca.setAccelerator(KeyStroke.getKeyStroke("F1"));
        
        pisoAlta = new JButton("Alta Pisos",
                new ImageIcon(getClass().getResource("/recursos/nuevo.png")));

        pisoBaja = new JButton("Baja Pisos",
                new ImageIcon(getClass().getResource("/recursos/eliminar.png")));

        getRootPane().setDefaultButton(pisoAlta);

        this.setJMenuBar(barra);


        
        
        
        
        
        panelJDialog = new PanelJDialog(null);
        
        
        // Calcular la mitad
        int ancho = pantalla.width / 2;
     	int alto = pantalla.height / 2;

     	// Asignar tamaño y centrar
     	this.setSize(ancho, alto);
     	this.setLocationRelativeTo(null);
     	this.setVisible(true);
	}
}
