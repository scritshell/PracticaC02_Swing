/*
* Proyecto: swing_c_p02_CsibiSebastian
* Paquete: paneles
* Archivo: PanelJDialog.java
* Autor/a: Sebastián Csibi
* Fecha: 21 nov 2025 8:37:14
*
* Descripción:
* [Esste es el diálogo (MODAL) para el alta de nuevos pisos. Contiene formularios solicitando datos del arrendador y del inmueble.]
*
* Licencia:
* [Todos los derechos reservados.]
*/
package paneles;

import javax.swing.*;
import javax.swing.border.Border;

import java.awt.*;
import java.util.Calendar;
import java.util.Date;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentListener;


public class PanelJDialog extends JDialog {

    // ======================================================= //
    // Declaración de todos los componentes del formulario     //
	// Diferenciados por tipos de panel para tener limpieza    //
	// del código.							   //
    // ======================================================= //
	
	
	// -> PANEL 1: CABECERA <- //
	private JPanel panelCabecera;
	
	
	// -> PANEL 2: DATOS DEL ARRENDADOR <- //
	private JPanel panelArrendador;
	private JTextField nombre, apellidos, dni, telefono;
	
	
	// -> PANEL 3: DATOS DEL INMUEBLE <- //
	private JPanel panelInmueble;
	
    // Campos de texto //
    private JTextField direccion;

    private JComboBox<String> provincia;

    private JSpinner fechaAlta;
    private JSpinner fechaFin;
    private JSpinner huespedes;
    private JSpinner dormitorios;
    private JSpinner banios;
    private JSpinner camas;

    private JPanel panelTiposCamas;
    private JComboBox<String>[] tiposCama;
    private JCheckBox ninios;
    private JPanel panelExtrasNinios;
    private JSpinner edadNinio;
    private JTextArea extras;

    private JPanel panelImagenes;
    private JLabel imagen1, imagen2, imagen3;
    
    private JTextField precioMinimo;
    
	
    // -> PANEL 4: RESUMEN <- //
    private JTabbedPane panelResumen;
    private JPanel panelResumenArrendador, panelResumenInmueble;
    private JTextArea resumenArrendador, resumenInmueble;
    
    private JButton imprimir, nuevo, guardar;
    private JSlider valoracion;
    
    // Bordes //
    private final Border BORDE_DEFECTO = UIManager.getBorder("TextField.border");
    private final Border BORDE_ROJO = BorderFactory.createLineBorder(Color.RED, 2);


    // ======================================================= //
    //                       CONSTRUCTOR                       //
    // ======================================================= //

    public PanelJDialog(Frame owner) {
    	// ======================================================== //
    	// super(Frame owner, String title, boolean modal)		//
        // Llamamos al constructor con parametro "Frame owner"		//
        // owner -> la ventana que abre este diálogo			//
        // "Alta Pisos" -> título del diálogo				//
        // true -> modal (sirve para bloquear la ventana principal) //
		// ======================================================== //

        super(owner, "Alta Pisos", true);

        // ======================================================= //
        // 1. Configuración inicial de la ventana de diálogo       //
        // ======================================================= //

        // Obtenemos el tamaño de la pantalla del usuario //
        Dimension tamanio = Toolkit.getDefaultToolkit().getScreenSize();
        setIconImage(Toolkit.getDefaultToolkit().getImage(
                getClass().getResource("/recursos/icono.png")));
        // El diálogo ocupará toda la pantalla //
        setSize(tamanio);
        setResizable(false);


        // Centrar ventana //
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // ======================================================= //
        // 2. Inicialización de componentes y eventos			   //
        // ======================================================= //
        
        // Contenedor general para el formulario. //
        // usamos BoxLayout vertical para apilar las secciones //
        
        
        JPanel panelContenedor = new JPanel();
        panelContenedor.setMaximumSize(new Dimension(1200, Integer.MAX_VALUE));
        panelContenedor.setLayout(new BoxLayout(panelContenedor, BoxLayout.Y_AXIS));
        panelContenedor.setBorder(BorderFactory.createEmptyBorder(15, 15, 20, 20));

        // ======================================================= //
        // 3. Panel de cabecera						        //
        // ======================================================= //
        
        panelCabecera = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panelCabecera.setBackground(new Color(200, 220, 235));
        panelCabecera.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
        JLabel titulo = new JLabel("Formulario de Alta Pisos - Gestión Apartamentos Turísticos Bolivianos");
        titulo.setFont(new Font("SansSerif", Font.BOLD, 24));
        panelCabecera.add(titulo);
        panelContenedor.add(panelCabecera);
        
        
        // ======================================================= //
        // 4. Panel de formulario para el arrendador		       //
        // ======================================================= //
        
        panelArrendador = new JPanel(new GridLayout(4, 2, 10, 10));
        panelArrendador.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY), "Datos del Arrendador"));
        panelArrendador.setMaximumSize(new Dimension(800, 300));
        panelArrendador.setAlignmentX(Component.CENTER_ALIGNMENT);
        nombre = new JTextField(15);
        apellidos = new JTextField(15);
        dni = new JTextField(10);
        telefono = new JTextField(9);

        
        // Se añaden los componentes al panel del arrendados //
        panelArrendador.add(new JLabel("Nombre:"));
        panelArrendador.add(nombre);
        panelArrendador.add(new JLabel("Apellidos:"));
        panelArrendador.add(apellidos);
        panelArrendador.add(new JLabel("DNI:"));
        panelArrendador.add(dni);
        panelArrendador.add(new JLabel("Teléfono:"));
        panelArrendador.add(telefono);
        //\\						//\\
        panelContenedor.add(panelArrendador);
        
        
        // ======================================================= //
        // 5. Inicialización de los componentes del inmueble       //
        // ======================================================= //
        
        // *** APLICADO: mismo enfoque visual que en 'Datos del Arrendador' ***
        // Reducimos la altura visual de los campos y limitamos su máximo para
        // que el panel tenga una altura similar y no parezca descompensado.
        
        direccion = new JTextField(15);
        direccion.setMaximumSize(new Dimension(Integer.MAX_VALUE, 25));
        
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
        
        // Limitar altura del combo para mantener uniformidad
        provincia.setMaximumSize(new Dimension(300, 25));
        provincia.setPreferredSize(new Dimension(300, 25));


        // Las fechas se manejan como JSpinner simples //
        fechaAlta = new JSpinner(new SpinnerDateModel());
        fechaFin = new JSpinner(new SpinnerDateModel());
        // Reducir la altura visual de los spinners
        fechaAlta.setPreferredSize(new Dimension(200, 25));
        fechaAlta.setMaximumSize(new Dimension(200, 25));
        fechaFin.setPreferredSize(new Dimension(200, 25));
        fechaFin.setMaximumSize(new Dimension(200, 25));

        //\\ Spinners de números //\\
        // (Cada spinner permite seleccionar un valor dentro de un rango definido) //

        //\\ Spinner para el número de huéspedes //\\
        // new SpinnerNumberModel(valorInicial, valorMinimo, valorMaximo, incremento) //
        huespedes = new JSpinner(new SpinnerNumberModel(1, 1, 8, 1));
        huespedes.setPreferredSize(new Dimension(80, 25));
        huespedes.setMaximumSize(new Dimension(80, 25));
     	// -> Empieza en 1
     	// -> No puede bajar de 1
     	// -> No puede subir de 8
     	// -> Se incrementa/decrementa de 1 en 1

        //\\ Spinner para el número de dormitorios //\\
     	dormitorios = new JSpinner(new SpinnerNumberModel(1, 1, 4, 1));
     	dormitorios.setPreferredSize(new Dimension(80, 25));
     	dormitorios.setMaximumSize(new Dimension(80, 25));
     	// -> Valor inicial: 1
     	// -> Entre 1 y 4 dormitorios
     	// -> Paso de 1

     	//\\ Spinner para número de baños //\\
     	banios = new JSpinner(new SpinnerNumberModel(1, 1, 3, 1));
     	banios.setPreferredSize(new Dimension(80, 25));
     	banios.setMaximumSize(new Dimension(80, 25));
     	// -> Valor inicial: 1
     	// -> Entre 1 y 3 baños
     	// -> Incremento de 1

     	//\\ Spinner para número de camas //\\
     	camas = new JSpinner(new SpinnerNumberModel(1, 1, 4, 1));
     	camas.setPreferredSize(new Dimension(80, 25));
     	camas.setMaximumSize(new Dimension(80, 25));
     	// -> Valor inicial: 1
     	// -> Mínimo 1 cama, máximo 4
     	// -> Incremento de 1

     	
     	// ======================================================= //
     	// 6. Panel para TIPOS DE CAMA					   //
     	// ======================================================= //
     	
     	panelTiposCamas = new JPanel(new GridLayout(4, 2, 10, 10));

        // Eliminamos el borde (setBorder) para no restar espacio y que no se vean aplastados //
        // Al no tener ese borde, se aprovecha toda la altura disponible para los combos y etiquetas //
        panelTiposCamas.setBorder(null);

        // Establecemos una dimensión suficiente para alojar las 4 filas sin problemas (más compacta)
        panelTiposCamas.setPreferredSize(new Dimension(350, 120));
        panelTiposCamas.setMaximumSize(new Dimension(350, 120)); // Limitar la altura para GridBagLayout

        // Array de 4 JComboBox (uno por cada posible cama) //
        tiposCama = new JComboBox[4];

        // Hago un bucle para crear las 4 etiquetas y ComboBoxes de camas //
        for (int i = 0; i < 4; i++) {
        	JLabel etiquetaCama = new JLabel("Cama " + (i + 1) + ":");
        	panelTiposCamas.add(etiquetaCama);
        	tiposCama[i] = new JComboBox<>(new String[]{"Simple", "Doble", "Sofá cama"});
        	// Limitar altura de cada combo para consistencia visual
        	tiposCama[i].setPreferredSize(new Dimension(150, 25));
        	tiposCama[i].setMaximumSize(new Dimension(150, 25));
        	panelTiposCamas.add(tiposCama[i]);
        };
        // ======================================================= //
        // 7. Panel de niños						   //
        // ======================================================= //
        
        ninios = new JCheckBox("¿Niños?");
        panelExtrasNinios = new JPanel(new GridLayout(2, 2, 5, 5));
        panelExtrasNinios.setEnabled(false); // Por defecto, se deshabilitan las opciones debido a que la casilla por defecto está desactivada //
        panelExtrasNinios.setBorder(BorderFactory.createTitledBorder("Extras niños"));
        panelExtrasNinios.setMaximumSize(new Dimension(400, 120));

        edadNinio = new JSpinner(new SpinnerNumberModel(0, 0, 10, 1));
    	edadNinio.setEnabled(false); // Lo mismo para la edad de los niños //
        extras = new JTextArea(3, 15);
    	extras.setEnabled(false); // Lo mismo para los extras //


        panelExtrasNinios.add(new JLabel("Edad niño:"));
        panelExtrasNinios.add(edadNinio);

        panelExtrasNinios.add(new JLabel("Extras:"));
        panelExtrasNinios.add(new JScrollPane(extras));
        // Lógica para activar/desactivar el panel "Extras" //
        ninios.addActionListener(e -> {
        	boolean elegido = ninios.isSelected(); // creamos boolean que cambia true o false dependiendo de si está elegido o no la casilla de "Niños?" //
        	panelExtrasNinios.setEnabled(elegido); // Pasamos como parámetro el booleano creado al setEnable() para que el panel se active o desactive 
        	edadNinio.setEnabled(elegido); // Lo mismo para la edad de los niños //
        	extras.setEnabled(elegido); // Lo mismo para "Extras" //
        });

        // ======================================================= //
        // 8. Panel de IMÁGENES					   //
        // ======================================================= //

        panelImagenes = new JPanel(new GridLayout(1, 3, 10, 10));
        panelImagenes.setBorder(BorderFactory.createTitledBorder("Imágenes del piso"));
        panelImagenes.setMaximumSize(new Dimension(600, 120));

        imagen1 = new JLabel("Imagen 1", SwingConstants.CENTER);
        imagen2 = new JLabel("Imagen 2", SwingConstants.CENTER);
        imagen3 = new JLabel("Imagen 3", SwingConstants.CENTER);

        // Usamos try-catch por si no cargan las imagenes, una buena práctica para manejo de errores //
        try {
            imagen1.setIcon(new ImageIcon(getClass().getResource("/recursos/img1.png")));
            imagen2.setIcon(new ImageIcon(getClass().getResource("/recursos/img2.png")));
            imagen3.setIcon(new ImageIcon(getClass().getResource("/recursos/img3.png")));
        } catch (Exception m) {
            System.err.println("No se pudieron cargar los recursos de imágenes. " + m.getMessage());
        }
        
        // Ajustar etiquetas para mostrar texto e imagen //
        imagen1.setHorizontalTextPosition(SwingConstants.CENTER);
        imagen1.setVerticalTextPosition(SwingConstants.BOTTOM);
        imagen2.setHorizontalTextPosition(SwingConstants.CENTER);
        imagen2.setVerticalTextPosition(SwingConstants.BOTTOM);
        imagen3.setHorizontalTextPosition(SwingConstants.CENTER);
        imagen3.setVerticalTextPosition(SwingConstants.BOTTOM);


        panelImagenes.add(imagen1);
        panelImagenes.add(imagen2);
        panelImagenes.add(imagen3);

        // ======================================================= //
        // 9. Precio mínimo (sin lógica)				   //
        // ======================================================= //

        precioMinimo = new JTextField("0 €");
        precioMinimo.setEditable(false);
        precioMinimo.setMaximumSize(new Dimension(300, 25));

        // ======================================================= //
        // 10. Añadir todos los componentes al panel inmueble	   //
        // ======================================================= //
        
        // MODIFICACIÓN A GridBagLayout para evitar el estiramiento vertical //
        panelInmueble = new JPanel(new GridBagLayout()); 
        panelInmueble.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY), "Datos del inmueble"));
        // Aplicado: limitar altura máxima como en panelArrendador para homogeneidad
        panelInmueble.setMaximumSize(new Dimension(800, 600)); // Aumentado ligeramente para acomodar todos los campos
        panelInmueble.setAlignmentX(Component.CENTER_ALIGNMENT);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5); // Márgenes entre componentes
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        int fila = 0;

        // Fila 1: Dirección
        gbc.gridx = 0; gbc.gridy = fila; gbc.weightx = 0.0; gbc.anchor = GridBagConstraints.WEST;
        panelInmueble.add(new JLabel("Dirección:"), gbc);
        gbc.gridx = 1; gbc.weightx = 1.0; 
        panelInmueble.add(direccion, gbc);
        fila++;

        // Fila 2: Provincia
        gbc.gridx = 0; gbc.gridy = fila; gbc.weightx = 0.0; gbc.anchor = GridBagConstraints.WEST;
        panelInmueble.add(new JLabel("Provincia:"), gbc);
        gbc.gridx = 1; gbc.weightx = 1.0; 
        panelInmueble.add(provincia, gbc);
        fila++;

        // Fila 3: Fecha alta
        gbc.gridx = 0; gbc.gridy = fila; gbc.weightx = 0.0; gbc.anchor = GridBagConstraints.WEST;
        panelInmueble.add(new JLabel("Fecha alta:"), gbc);
        gbc.gridx = 1; gbc.weightx = 1.0; 
        panelInmueble.add(fechaAlta, gbc);
        fila++;

        // Fila 4: Fecha fin
        gbc.gridx = 0; gbc.gridy = fila; gbc.weightx = 0.0; gbc.anchor = GridBagConstraints.WEST;
        panelInmueble.add(new JLabel("Fecha fin:"), gbc);
        gbc.gridx = 1; gbc.weightx = 1.0; 
        panelInmueble.add(fechaFin, gbc);
        fila++;
        
        // Fila 5: Nº huéspedes
        gbc.gridx = 0; gbc.gridy = fila; gbc.weightx = 0.0; gbc.anchor = GridBagConstraints.WEST;
        panelInmueble.add(new JLabel("Nº huéspedes:"), gbc);
        gbc.gridx = 1; gbc.weightx = 1.0; 
        panelInmueble.add(huespedes, gbc);
        fila++;
        
        // Fila 6: Nº dormitorios
        gbc.gridx = 0; gbc.gridy = fila; gbc.weightx = 0.0; gbc.anchor = GridBagConstraints.WEST;
        panelInmueble.add(new JLabel("Nº dormitorios:"), gbc);
        gbc.gridx = 1; gbc.weightx = 1.0; 
        panelInmueble.add(dormitorios, gbc);
        fila++;
        
        // Fila 7: Nº baños
        gbc.gridx = 0; gbc.gridy = fila; gbc.weightx = 0.0; gbc.anchor = GridBagConstraints.WEST;
        panelInmueble.add(new JLabel("Nº baños:"), gbc);
        gbc.gridx = 1; gbc.weightx = 1.0; 
        panelInmueble.add(banios, gbc);
        fila++;
        
        // Fila 8: Nº camas
        gbc.gridx = 0; gbc.gridy = fila; gbc.weightx = 0.0; gbc.anchor = GridBagConstraints.WEST;
        panelInmueble.add(new JLabel("Nº camas:"), gbc);
        gbc.gridx = 1; gbc.weightx = 1.0; 
        panelInmueble.add(camas, gbc);
        fila++;
        
        // Fila 9: Tipos de cama (ocupará dos columnas)
        gbc.gridx = 0; gbc.gridy = fila; gbc.weightx = 0.0; gbc.anchor = GridBagConstraints.NORTHWEST;
        panelInmueble.add(new JLabel("Tipos de cama: "), gbc);
        gbc.gridx = 1; gbc.weightx = 1.0; gbc.fill = GridBagConstraints.BOTH;
        panelInmueble.add(panelTiposCamas, gbc);
        fila++;

        // Fila 10: ¿Niños? y Panel de Extras
        gbc.gridx = 0; gbc.gridy = fila; gbc.weightx = 0.0; gbc.anchor = GridBagConstraints.NORTHWEST;
        gbc.fill = GridBagConstraints.NONE;
        panelInmueble.add(ninios, gbc);
        gbc.gridx = 1; gbc.weightx = 1.0; gbc.fill = GridBagConstraints.HORIZONTAL;
        panelInmueble.add(panelExtrasNinios, gbc);
        fila++;
        
        // Fila 11: Panel de Imágenes (ocupará dos columnas)
        gbc.gridx = 0; gbc.gridy = fila; gbc.gridwidth = 2; gbc.weightx = 1.0; gbc.fill = GridBagConstraints.HORIZONTAL;
        panelInmueble.add(panelImagenes, gbc);
        gbc.gridwidth = 1; // Resetear gridwidth
        fila++;
        
        // Fila 12: Precio Mínimo
        gbc.gridx = 0; gbc.gridy = fila; gbc.weightx = 0.0; gbc.anchor = GridBagConstraints.WEST;
        panelInmueble.add(new JLabel("Precio mínimo:"), gbc);
        gbc.gridx = 1; gbc.weightx = 1.0; gbc.fill = GridBagConstraints.HORIZONTAL;
        panelInmueble.add(precioMinimo, gbc);
        fila++;
        
        // Espacio vacío para empujar los elementos hacia arriba y evitar estiramiento total (si fuera necesario, aquí no lo es tanto)
        // gbc.gridx = 0; gbc.gridy = fila; gbc.weighty = 1.0; panelInmueble.add(new JLabel(""), gbc);
        
        panelContenedor.add(panelInmueble);

        // ======================================================= //
        // 11. Panel de resumen y sus acciones			   //
        // ======================================================= //
        
     // Tabbed Pane para el Resumen (Arrendador e Inmueble)
        panelResumen = new JTabbedPane();
        panelResumen.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelResumen.setPreferredSize(new Dimension(400, 200));
        
        // Resumen del arrendador //
        panelResumenArrendador = new JPanel(new BorderLayout());
        resumenArrendador = new JTextArea("");
        resumenArrendador.setEditable(false);
        panelResumenArrendador.add(new JScrollPane(resumenArrendador), BorderLayout.CENTER);
        panelResumen.addTab("Arrendador", panelResumenArrendador);

        // Resumen del inmueble //
        panelResumenInmueble = new JPanel(new BorderLayout()); // Inicialización del panel de resumen de inmueble // 
        resumenInmueble = new JTextArea(""); // Inicialización del área de texto // 
        resumenInmueble.setEditable(false);
        panelResumenInmueble.add(new JScrollPane(resumenInmueble), BorderLayout.CENTER);
        panelResumen.addTab("Inmueble", panelResumenInmueble); // Se añade la pestaña // 

        // Valoracion //
        valoracion = new JSlider(0, 5);
        valoracion.setMajorTickSpacing(1);
        valoracion.setPaintTicks(true);
        valoracion.setPaintLabels(true);

        JPanel panelValoracion = new JPanel(new BorderLayout());
        panelValoracion.add(new JLabel("Valoración del Piso (0-5):", SwingConstants.CENTER), BorderLayout.NORTH);
        panelValoracion.add(valoracion, BorderLayout.CENTER);

        // Panel que combina el TabbedPane y el Slider //
        JPanel panelResumenYValoracion = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        panelResumenYValoracion.add(panelResumen);
        panelResumenYValoracion.add(panelValoracion);
        panelContenedor.add(panelResumenYValoracion);
        
        // ============================= //
        // Botones de acción del diálogo //
        // ============================= //
        
        imprimir = new JButton("Imprimir");
        nuevo = new JButton("Nuevo");
        guardar = new JButton("Guardar");

        // El botón guardar es el botón por defecto del diálogo //
        getRootPane().setDefaultButton(guardar);

        JPanel panelAcciones = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
        panelAcciones.add(imprimir);
        panelAcciones.add(nuevo);
        panelAcciones.add(guardar);


        // ========================== //
        // Botones Aceptar / Cancelar //
        // ========================== //
        JButton btnAceptar = new JButton("Aceptar"); // Mantener para completar la funcionalidad del constructor //
        JButton btnCancelar = new JButton("Cancelar");

        JPanel panelBotonesControl = new JPanel();
        panelBotonesControl.add(btnAceptar);
        panelBotonesControl.add(btnCancelar);

        // Botón Cancelar -> cierra el diálogo //
        btnCancelar.addActionListener(e -> dispose());


        // Panel inferior que une los botones de acción con los de control //
        JPanel panelInferiorTotal = new JPanel(new BorderLayout());
        panelInferiorTotal.add(panelAcciones, BorderLayout.NORTH);
        panelInferiorTotal.add(panelBotonesControl, BorderLayout.SOUTH);

        // ======================================================= //
        // 12. Inicialización de la lógica del formulario          //
        // ======================================================= //
        
        // Inicializar el precio y la visibilidad de combos de cama //
        calcularPrecio();
        // Ajustar la visibilidad inicial de los tipos de cama //
        int initialCamas = (int) camas.getValue();
        for (int i = 0; i < tiposCama.length; i++) {
            boolean enabled = i < initialCamas;
            tiposCama[i].setEnabled(enabled);
            // La etiqueta es el componente justo antes del ComboBox en el GridLayout del panelTiposCamas //
            if (i * 2 < panelTiposCamas.getComponentCount()) {
                panelTiposCamas.getComponent(i * 2).setEnabled(enabled);
            }
        }
        
        // ======================================================= //
        // 13. Asignación de Eventos y Lógica Funcional		   //
        // ======================================================= //
        
        // Listener para calcular el precio //
        ChangeListener priceChangeListener = e -> calcularPrecio();
        
        // Eventos que recalculan el precio //
        banios.addChangeListener(priceChangeListener);
        
        // Listener especial para camas: recalcula precio y ajusta la visibilidad de los combos de tipo de cama //
        camas.addChangeListener(e -> {
            calcularPrecio();
            int numCamas = (int) camas.getValue();
            for (int i = 0; i < tiposCama.length; i++) {
                boolean enabled = i < numCamas;
                tiposCama[i].setEnabled(enabled);
                // Habilitar/deshabilitar la etiqueta correspondiente //
                if (i * 2 < panelTiposCamas.getComponentCount()) {
                    panelTiposCamas.getComponent(i * 2).setEnabled(enabled);
                }
            }
        });
        
        // Eventos que recalculan el precio para los tipos de cama seleccionados //
        for (JComboBox<String> combo : tiposCama) {
            combo.addActionListener(e -> calcularPrecio());
        }

        // Añadir lógica de relleno automático de 'Extras' y recalcular precio al activar/desactivar 'Niños' //
        ninios.addActionListener(e -> {
            boolean elegido = ninios.isSelected();
            // Lógica existente:
            panelExtrasNinios.setEnabled(elegido);
            edadNinio.setEnabled(elegido);
            extras.setEnabled(elegido);
            
            if (elegido) {
                // Relleno automático de "Cuna" si se selecciona "Niños" //
                extras.setText("Cuna");
            } else {
                extras.setText("");
            }
            
            calcularPrecio();
        });
        
        // DocumentListener para recalcular precio cuando se modifica el campo "Extras"
        extras.getDocument().addDocumentListener(new DocumentListener() {
            public void insertUpdate(javax.swing.event.DocumentEvent e) { calcularPrecio(); }
            public void removeUpdate(javax.swing.event.DocumentEvent e) { calcularPrecio(); }
            public void changedUpdate(javax.swing.event.DocumentEvent e) {}
        });
        
        // Lógica de botones de acción //
        guardar.addActionListener(e -> validaciones(true)); // Guardar -> validar y mostrar éxito si esta bien //
        nuevo.addActionListener(e -> limpiar()); // Nuevo -> limpiar formulario //

        // Imprimir //
        imprimir.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Funcionalidad de Imprimir a Documento no implementada.", "Información", JOptionPane.INFORMATION_MESSAGE);
        });
        
        // Botón Aceptar -> Validar, actualizar resumen y cerrar //
        btnAceptar.addActionListener(e -> {
            validaciones(false); // Validar y actualizar resumen si es ok //
            // Comprobamos si las validaciones pasaron para cerrar el diálogo //
            // Obtenemos el campo de la fecha de alta para comprobar su borde. //
            JTextField fechaAltaFieldTemp = ((JSpinner.DefaultEditor)fechaAlta.getEditor()).getTextField();
            if (nombre.getBorder() == BORDE_DEFECTO && apellidos.getBorder() == BORDE_DEFECTO &&
                dni.getBorder() == BORDE_DEFECTO && telefono.getBorder() == BORDE_DEFECTO &&
                fechaAltaFieldTemp.getBorder() == BORDE_DEFECTO) {
                dispose();
            }
        });



        // ======================================================= //
        // 14. Añadir todo al JDialog				   //
        // ======================================================= //
        // El contenedor de todas las secciones se envuelve en un solo JScrollPane //
        add(new JScrollPane(panelContenedor), BorderLayout.CENTER);
        add(panelInferiorTotal, BorderLayout.SOUTH);

        // La lógica de activación inicial: deshabilitar extras niños y poner foco //
        for (Component componente : panelExtrasNinios.getComponents()) {
            componente.setEnabled(false);
        }

        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent e) {
                nombre.requestFocusInWindow();
            }
        });

        
        
        //\\ Mostrar la ventana //\\
        setVisible(true);
    }
    
    // ======================================================= //
    // MÉTODOS DE LÓGICA 						   //
    // ======================================================= //
    
    private void calcularPrecio() {
        double precio = 0.0;
        
        // Sumar por baños (25€ cada uno) //
        int numBanios = (int) banios.getValue();
        precio += numBanios * 25;

        // Sumar por camas (Cama doble = 20€, Simple/Sofá = 15€) //
        int numCamas = (int) camas.getValue();
        for (int i = 0; i < numCamas; i++) {
            String tipo = (String) tiposCama[i].getSelectedItem();
            if ("Doble".equals(tipo)) precio += 20;
            else precio += 15; // Simple o Sofá cama //
        }
 
        // sumar extras niños (Cuna/Supletoria = 12€)  //
        if (ninios.isSelected()) {
            String textoExtra = extras.getText();
            if (textoExtra.contains("Cuna") || textoExtra.contains("Cama supletoria")) {
                precio += 12;
            }
        }
        
        // Actualizar el campo de texto //
        precioMinimo.setText(String.format("%.2f €", precio));
    }
    
    
    
    private void validaciones(boolean isGuardar) {
        boolean valido = true;
        StringBuilder errores = new StringBuilder("Errores encontrados:\n");

        // 1. Validar Nombre/Apellidos //
        if (nombre.getText().trim().isEmpty()) {
            nombre.setBorder(BORDE_ROJO); valido = false; errores.append("- Nombre obligatorio\n");
        } else nombre.setBorder(BORDE_DEFECTO);

        if (apellidos.getText().trim().isEmpty()) {
            apellidos.setBorder(BORDE_ROJO); valido = false; errores.append("- Apellidos obligatorios\n");
        } else apellidos.setBorder(BORDE_DEFECTO);

        // 2. Validar DNI //
        if (!dni.getText().matches("\\d{8}[A-Za-z]")) {
            dni.setBorder(BORDE_ROJO); valido = false; errores.append("- DNI incorrecto (8 números + Letra)\n");
        } else dni.setBorder(BORDE_DEFECTO);

        // 3. Validar Teléfono //
        if (!telefono.getText().matches("\\d{9}")) {
            telefono.setBorder(BORDE_ROJO); valido = false; errores.append("- Teléfono incorrecto (9 dígitos)\n");
        } else telefono.setBorder(BORDE_DEFECTO);

        // 4. Validar Fecha Alta //
        Date fechaElegida = (Date) fechaAlta.getValue();
        Calendar hoy = Calendar.getInstance();
        hoy.set(Calendar.HOUR_OF_DAY, 0); hoy.set(Calendar.MINUTE, 0); 
        hoy.set(Calendar.SECOND, 0); hoy.set(Calendar.MILLISECOND, 0);
        
        JTextField fechaAltaField = ((JSpinner.DefaultEditor)fechaAlta.getEditor()).getTextField();
        
        if (fechaElegida.before(hoy.getTime())) {
            fechaAltaField.setBorder(BORDE_ROJO);
            valido = false; errores.append("- Fecha de alta no puede ser anterior a hoy\n");
        } else {
            fechaAltaField.setBorder(BORDE_DEFECTO);
        }

        if (!valido) {
            JOptionPane.showMessageDialog(this, errores.toString(), "Error de Validación", JOptionPane.ERROR_MESSAGE);
        } else {
            // Acción final tras pasar la validación //
            if (isGuardar) {
                JOptionPane.showMessageDialog(this, "Registro Guardado Correctamente", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            } 
            // Si pasa la validación, siempre actualizamos la pestaña de resumen (útil para el botón Aceptar o para el FocusLost)
            actualizar();
        }
        
    }
    
    
    
    private void actualizar() {
        // Actualización de la pestaña de Resumen del Arrendador
        resumenArrendador.setText(String.format(
        "Nombre: %s\nApellidos: %s\nDNI: %s\nTeléfono: %s",
        nombre.getText(), apellidos.getText(), dni.getText(), telefono.getText()));
        
        // Actualización de la pestaña de Resumen del Inmueble //
        resumenInmueble.setText(String.format(
        "Dirección: %s\nProvincia: %s\nNº Huéspedes: %d\nNº Dormitorios: %d\nNº Baños: %d\n\nPrecio Mínimo/Día: %s",
        direccion.getText(), provincia.getSelectedItem(), huespedes.getValue(), 
        dormitorios.getValue(), banios.getValue(), precioMinimo.getText()));
        
        panelResumen.setSelectedIndex(0); // Muestra la primera pestaña de resumen
    }
    
    
    
    private void limpiar() {
        // Campos de texto
        nombre.setText(""); apellidos.setText(""); dni.setText(""); telefono.setText(""); direccion.setText("");
        
        // Spinners
        fechaAlta.setValue(new Date()); fechaFin.setValue(new Date());
        huespedes.setValue(1); dormitorios.setValue(1); banios.setValue(1); camas.setValue(1);
        edadNinio.setValue(0);
        
        // Componentes especiales
        provincia.setSelectedIndex(0);
        ninios.setSelected(false);
        extras.setText("");
        
        // Limpiar el resumen //
        resumenArrendador.setText("");
        resumenInmueble.setText("");
        
        // Resetear bordes //
        nombre.setBorder(BORDE_DEFECTO); 
        apellidos.setBorder(BORDE_DEFECTO);
        dni.setBorder(BORDE_DEFECTO); 
        telefono.setBorder(BORDE_DEFECTO);
        ((JSpinner.DefaultEditor)fechaAlta.getEditor()).getTextField().setBorder(BORDE_DEFECTO);
        
        // Recálculo y foco //
        calcularPrecio();
        nombre.requestFocus(); 
    }
}