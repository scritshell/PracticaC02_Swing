/*
* Proyecto: swing_c_p02_CsibiSebastian
* Paquete: paneles
* Archivo: PanelJDialog.java
* Autor/a: Tu Nombre y Apellidos
* Fecha: 21 nov 2025 8:37:14
*
* Descripción:
* [Resumen del propósito del archivo/clase.]
*
* Licencia:
* [Condiciones de uso/licencia.]
*/
package paneles;

import javax.swing.*;
import java.awt.*;

public class PanelJDialog extends JDialog {

    // ======================================================= //
    //   Declaración de todos los componentes del formulario   //
    // ======================================================= //

    // Campos de texto //
    private JTextField direccion;

    // Provincia con JComboBox //
    private JComboBox<String> provincia;

    // Fechas con JSpinner //
    private JSpinner fechaAlta;
    private JSpinner fechaFin;

    // Spinners de cantidades //
    private JSpinner huespedes;
    private JSpinner dormitorios;
    private JSpinner banios;
    private JSpinner camas;

    // Panel + array de JComboBox para tipos de camas //
    private JPanel panelTiposCamas;
    private JComboBox<String>[] tiposCama;

    // Checkbox de niños + panel adicional //
    private JCheckBox ninios;
    private JPanel panelExtrasNinios;
    private JSpinner edadNinio;
    private JTextArea extras;

    // Panel de imágenes del piso //
    private JPanel panelImagenes;
    private JLabel imagen1, imagen2, imagen3;

    // Precio mínimo //
    private JTextField precioMinimo;


    // ======================================================= //
    //                       CONSTRUCTOR                       //
    // ======================================================= //

    public PanelJDialog(Frame owner) {
    		// ========================================================= //
    		// super(Frame owner, String title, boolean modal)			//
        // Llamamos al constructor con parametro "Frame owner"		//
        // owner -> la ventana que abre este diálogo					//
        // "Alta Pisos" -> título del diálogo						//
        // true -> modal (sirve para bloquear la ventana principal)  //
		// ========================================================= //

        super(owner, "Alta Pisos", true);

        // ======================================================= //
        // 1. Configuración INICIAL de la ventana de diálogo       //
        // ======================================================= //

        // Obtenemos el tamaño de la pantalla del usuario //
        Dimension tamanio = Toolkit.getDefaultToolkit().getScreenSize();

        // El diálogo ocupará toda la pantalla //
        setSize(tamanio);

        // Centrar ventana //
        setLocationRelativeTo(null);

        // ======================================================= //
        // 2. Panel principal (donde van todos los componentes)	  //
        // ======================================================= //

        JPanel panelPrincipal = new JPanel();

        // GridLayout con 2 columnas -> etiqueta / componente //
        // "0" filas significa que Swing calculará las filas que hagan falta //
        panelPrincipal.setLayout(new GridLayout(0, 2, 10, 10));

        // Bordes alrededor del formulario //
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // ======================================================= //
        // 3. Inicialización de los componentes					  //
        // ======================================================= //

        // Campo de texto para Dirección //
        direccion = new JTextField(20);

        // JComboBox con todas las provincias de España (50 provincias) //
        provincia = new JComboBox<>(new String[]{
                "Álava","Albacete","Alicante","Almería","Asturias","Ávila","Badajoz",
                "Barcelona","Burgos","Cáceres","Cádiz","Cantabria","Castellón",
                "Ciudad Real","Córdoba","A Coruña","Cuenca","Girona","Granada",
                "Guadalajara","Guipúzcoa","Huelva","Huesca","Islas Baleares","Jaén",
                "León","Lleida","Lugo","Madrid","Málaga","Murcia","Navarra","Ourense",
                "Palencia","Las Palmas","Pontevedra","La Rioja","Salamanca","Segovia",
                "Sevilla","Soria","Tarragona","Teruel","Toledo","Valencia","Valladolid",
                "Vizcaya","Zamora","Zaragoza"
        });

        // Las fechas se manejan como JSpinner simples //
        fechaAlta = new JSpinner(new SpinnerDateModel());
        fechaFin = new JSpinner(new SpinnerDateModel());

        //\\ Spinners de números //\\
        // (Cada spinner permite seleccionar un valor dentro de un rango definido) //

        //\\ Spinner para el número de huéspedes //\\
        // new SpinnerNumberModel(valorInicial, valorMinimo, valorMaximo, incremento) //
        huespedes = new JSpinner(new SpinnerNumberModel(1, 1, 8, 1));
     	// -> Empieza en 1
     	// -> No puede bajar de 1
     	// -> No puede subir de 8
     	// -> Se incrementa/decrementa de 1 en 1

        //\\ Spinner para el número de dormitorios //\\
     	dormitorios = new JSpinner(new SpinnerNumberModel(1, 1, 4, 1));
     	// -> Valor inicial: 1
     	// -> Entre 1 y 4 dormitorios
     	// -> Paso de 1

     	//\\ Spinner para número de baños //\\
     	banios = new JSpinner(new SpinnerNumberModel(1, 1, 3, 1));
     	// -> Valor inicial: 1
     	// -> Entre 1 y 3 baños
     	// -> Incremento de 1

     	//\\ Spinner para número de camas //\\
     	camas = new JSpinner(new SpinnerNumberModel(1, 1, 4, 1));
     	// -> Valor inicial: 1
     	// -> Mínimo 1 cama, máximo 4
     	// -> Incremento de 1

        // ======================================================= //
        // 4. Panel para TIPOS DE CAMA							  //
        // ======================================================= //

        panelTiposCamas = new JPanel(new GridLayout(4, 2, 5, 5));
        panelTiposCamas.setBorder(BorderFactory.createTitledBorder("Tipos de cama"));

        // Array de 4 JComboBox (uno por cada posible cama) //
        tiposCama = new JComboBox[4];

        // ======================================================= //
        // Creamos este bucle para ejecutarlo 4 veces.			   //
        // Crea una etiqueta JLabel asignando automaticamente el   //
        // número de la cama (Cama 1, Cama 2...).				   //
        // Crea un JComboBox que permitirá al usuario seleccionar  //
        // el tipo de cama que desea.							   //
        // Añade ambos componentes al panel.					   //
        // De esta forma se evita repetir código y queda mucho	   //
        // más limpio.											   //
        // ======================================================= //
        for (int i = 0; i < 4; i++) {
            panelTiposCamas.add(new JLabel("Cama " + (i + 1) + ":"));
            tiposCama[i] = new JComboBox<>(new String[]{"Simple", "Doble", "Sofá cama"});
            panelTiposCamas.add(tiposCama[i]);
        }

        // ======================================================= //
        // 5. Panel de NIÑOS									   //
        // ======================================================= //

        ninios = new JCheckBox("¿Niños?");
        panelExtrasNinios = new JPanel(new GridLayout(2, 2, 5, 5));
        panelExtrasNinios.setBorder(BorderFactory.createTitledBorder("Extras niños"));

        edadNinio = new JSpinner(new SpinnerNumberModel(0, 0, 10, 1));
        extras = new JTextArea(3, 15);

        panelExtrasNinios.add(new JLabel("Edad niño:"));
        panelExtrasNinios.add(edadNinio);

        panelExtrasNinios.add(new JLabel("Extras:"));
        panelExtrasNinios.add(new JScrollPane(extras));

        // ======================================================= //
        // 6. Panel de IMÁGENES									   //
        // ======================================================= //

        panelImagenes = new JPanel(new GridLayout(1, 3, 10, 10));
        panelImagenes.setBorder(BorderFactory.createTitledBorder("Imágenes del piso"));

        imagen1 = new JLabel("Imagen 1", SwingConstants.CENTER);
        imagen2 = new JLabel("Imagen 2", SwingConstants.CENTER);
        imagen3 = new JLabel("Imagen 3", SwingConstants.CENTER);

        panelImagenes.add(imagen1);
        panelImagenes.add(imagen2);
        panelImagenes.add(imagen3);

        // ======================================================= //
        // 7. Precio mínimo (sin lógica)						   //
        // ======================================================= //

        precioMinimo = new JTextField("0 €");
        precioMinimo.setEditable(false);

        // ======================================================= //
        // 8. Añadir todos los componentes al panel principal	   //
        // ======================================================= //

        panelPrincipal.add(new JLabel("Dirección:"));
        panelPrincipal.add(direccion);

        panelPrincipal.add(new JLabel("Provincia:"));
        panelPrincipal.add(provincia);

        panelPrincipal.add(new JLabel("Fecha alta:"));
        panelPrincipal.add(fechaAlta);

        panelPrincipal.add(new JLabel("Fecha fin:"));
        panelPrincipal.add(fechaFin);

        panelPrincipal.add(new JLabel("Nº huéspedes:"));
        panelPrincipal.add(huespedes);

        panelPrincipal.add(new JLabel("Nº dormitorios:"));
        panelPrincipal.add(dormitorios);

        panelPrincipal.add(new JLabel("Nº baños:"));
        panelPrincipal.add(banios);

        panelPrincipal.add(new JLabel("Nº camas:"));
        panelPrincipal.add(camas);

        panelPrincipal.add(panelTiposCamas);
        panelPrincipal.add(new JLabel("")); // espacio vacío

        panelPrincipal.add(ninios);
        panelPrincipal.add(panelExtrasNinios);

        panelPrincipal.add(panelImagenes);
        panelPrincipal.add(new JLabel("")); // espacio vacío

        panelPrincipal.add(new JLabel("Precio mínimo:"));
        panelPrincipal.add(precioMinimo);

        // ======================================================= //
        // 9. Botones Aceptar / Cancelar						   //
        // ======================================================= //

        JButton btnAceptar = new JButton("Aceptar");
        JButton btnCancelar = new JButton("Cancelar");

        JPanel panelBotones = new JPanel();
        panelBotones.add(btnAceptar);
        panelBotones.add(btnCancelar);

        // Botón Cancelar → simplemente cierra el diálogo
        btnCancelar.addActionListener(e -> dispose());

        // ======================================================= //
        // 10. Añadir todo al propio JDialog					   //
        // ======================================================= //

        add(new JScrollPane(panelPrincipal), BorderLayout.CENTER);
        add(panelBotones, BorderLayout.SOUTH);

        // Mostrar la ventana //
        setVisible(false);
    }
}