/*
* Proyecto: swing_c_p02_CsibiSebastian
* Paquete: swing_c_p02_CsibiSebastian
* Archivo: VentanaPrincipal.java
* Autor/a: Sebastian Csibi
* Fecha: 21 nov 2025 8:32:46
*
* Descripción:
* [Ventana principal de la aplicación con menús y botones.]
*
* Licencia:
* [Todos los derechos reservados.]
*/
package principal;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.KeyStroke;

import paneles.PanelJDialog;

public class VentanaPrincipal extends JFrame {
	
    // ======================================================= //
    //    Declaración de los componentes de la ventana         //
    // ======================================================= //

    private JButton pisoAlta;
    private JButton pisoBaja;
    private JMenuBar barra;
    private JMenu menuArchivo, menuRegistro, menuAyuda;
    private JMenuItem itemAlta, itemBaja, itemSalir, itemAcerca;

    
    // Constructor //
    public VentanaPrincipal() {
    	// Nombre de la ventana //
        super("Gestión Apartamentos Turísticos Bolivianos");

        
        // Cerrar aplicación al salir //
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        
        // Icono de la ventana //
        setIconImage(Toolkit.getDefaultToolkit().getImage(
                getClass().getResource("/recursos/icono.png")));

        
        
        // ======================================================= //
        // BARRA DE MENÚS										   //
        // ======================================================= //
        
        barra = new JMenuBar();
        menuArchivo = new JMenu("Archivo");
        menuRegistro = new JMenu("Registro");
        menuAyuda = new JMenu("Ayuda");
        
        // Configuración de Mnemonic para el menú //
        menuArchivo.setMnemonic(KeyEvent.VK_A); // Alt + A
        menuRegistro.setMnemonic(KeyEvent.VK_R); // Alt + R
        menuAyuda.setMnemonic(KeyEvent.VK_H); // Alt + H

        barra.add(menuArchivo);
        barra.add(menuRegistro);
        barra.add(menuAyuda);

        // Items de menú //
        itemAlta = new JMenuItem("Alta Pisos");
        itemBaja = new JMenuItem("Baja Pisos");
        itemSalir = new JMenuItem("Salir");
        itemAcerca = new JMenuItem("Acerca de...");
        
        // Configuración de Mnemonic para los items //
        itemAlta.setMnemonic(KeyEvent.VK_A); // A
        itemBaja.setMnemonic(KeyEvent.VK_B); // B
        itemSalir.setMnemonic(KeyEvent.VK_S); // S
        itemAcerca.setMnemonic(KeyEvent.VK_C); // C 

        // Atajos de teclado //
        itemAlta.setAccelerator(KeyStroke.getKeyStroke("ctrl N"));
        itemBaja.setAccelerator(KeyStroke.getKeyStroke("ctrl B"));
        itemSalir.setAccelerator(KeyStroke.getKeyStroke("ctrl Q"));
        itemAcerca.setAccelerator(KeyStroke.getKeyStroke("F1"));

        // Añadir items a sus menús //
        menuRegistro.add(itemAlta);
        menuRegistro.add(itemBaja);
        menuArchivo.add(itemSalir);
        menuAyuda.add(itemAcerca);

        // Poner la barra //
        this.setJMenuBar(barra);
        
        

        // ======================================================= //
        // BOTONES PRINCIPALES DE LA VENTANA					   //
        // ======================================================= //
        
        // Icono NUEVO //
        ImageIcon iconoNuevo = new ImageIcon(getClass().getResource("/recursos/nuevo.png"));
        Image imgNuevo = iconoNuevo.getImage().getScaledInstance(16, 16, Image.SCALE_SMOOTH);
        ImageIcon iconoNuevoEscalado = new ImageIcon(imgNuevo);
        
        // Icono ELIMINAR //
        ImageIcon iconoEliminar = new ImageIcon(getClass().getResource("/recursos/eliminar.png"));
        Image imgEliminar = iconoEliminar.getImage().getScaledInstance(16, 16, Image.SCALE_SMOOTH);
        ImageIcon iconoEliminarEscalado = new ImageIcon(imgEliminar);
        pisoAlta = new JButton("Alta Pisos", iconoNuevoEscalado);
        pisoBaja = new JButton("Baja Pisos", iconoEliminarEscalado);
        
        
        // Configuracion Mnemonic para los botones //
        pisoAlta.setMnemonic(KeyEvent.VK_A); // Alt + A
        pisoBaja.setMnemonic(KeyEvent.VK_B); // Alt + B

        // Tooltips para los botones //
        pisoAlta.setToolTipText("Alta de nuevos pisos (Ctrl+N)");
        pisoBaja.setToolTipText("Baja de pisos existentes (Ctrl+B)");
        
        
        // Tamaño más pequeño para los botones //
        pisoAlta.setPreferredSize(new Dimension(150, 40));
        pisoBaja.setPreferredSize(new Dimension(150, 40));

        // Panel para los botones //
        JPanel panelBotones = new JPanel();
        panelBotones.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 20));
        panelBotones.add(pisoAlta);
        panelBotones.add(pisoBaja);
        add(panelBotones, BorderLayout.CENTER);

        // BOTÓN POR DEFECTO -> ENTER ejecuta "Alta Pisos" //
        getRootPane().setDefaultButton(pisoAlta);

        // ======================================================= //
        // ASIGNAR ACCIONES A MENÚS Y BOTONES					   //
        // ======================================================= //

        // Abrir JDialog Alta Pisos //
        itemAlta.addActionListener(e -> new PanelJDialog(this));
        pisoAlta.addActionListener(e -> new PanelJDialog(this));

        // Baja Pisos //
        itemBaja.addActionListener(e ->
                JOptionPane.showMessageDialog(this,
                        "La opción 'Baja Pisos' aún no está implementada.",
                        "Información",
                        JOptionPane.INFORMATION_MESSAGE));

        pisoBaja.addActionListener(e ->
                JOptionPane.showMessageDialog(this,
                        "La opción 'Baja Pisos' aún no está implementada.",
                        "Información",
                        JOptionPane.INFORMATION_MESSAGE));

        // Salir de la aplicación //
        itemSalir.addActionListener(e -> 
        		System.exit(0));
        

        // Acerca de //
        itemAcerca.addActionListener(e ->
                JOptionPane.showMessageDialog(this, "Gestión Apartamentos Turísticos Bolivianos\n Versión: 1.0\n Autor: Sebastian Csibi", "Acerca de", JOptionPane.INFORMATION_MESSAGE));

        // ======================================================= //
        // TAMAÑO DE LA VENTANA									   //
        // ======================================================= //
        Dimension pantalla = Toolkit.getDefaultToolkit().getScreenSize();
        int ancho = pantalla.width/2;
        int alto = pantalla.height/2;

        this.setSize(ancho, alto);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }
}
