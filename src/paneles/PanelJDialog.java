/*
* Proyecto: swing_c_p02_CsibiSebastian
* Paquete: paneles
* Archivo: PanelJDialog.java
* Autor/a: Sebastián Csibi
* Fecha: 21 nov 2025 8:37:14
*
* Descripción:
* [Este es el diálogo (MODAL) para el alta de nuevos pisos. Contiene formularios solicitando datos del arrendador y del inmueble.]
*
* Licencia:
* [Todos los derechos reservados.]
*/
package paneles;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.*;
import java.util.Calendar;
import java.util.Date;

public class PanelJDialog extends JDialog {

    // ======================================================= //
    // Declaración de todos los componentes del formulario     //
	// Diferenciados por tipos de panel para limpieza de	      //
	// código.												  //
    // ======================================================= //
	
	// -> PANEL 1: CABECERA <- //
	private JPanel panelCabecera;
	private JLabel tituloCabecera;
	
	// -> PANEL 2: DATOS DEL ARRENDADOR <- //
	private JPanel panelArrendador;
	private JTextField nombre, apellidos, dni, telefono;
	private JLabel lblNombre, lblApellidos, lblDni, lblTelefono;
	
	// -> PANEL 3: DATOS DEL INMUEBLE <- //
	private JPanel panelInmueble;
	
    // Campos de texto //
    private JTextField direccion;
    private JLabel lblDireccion;
    // Provincia con JComboBox //
    private JComboBox<String> provincia;
    private JLabel lblProvincia;
    // Fechas con JSpinner //
    private JSpinner fechaAlta;
    private JSpinner fechaFin;
    private JLabel lblFechaAlta, lblFechaFin;
    // Spinners de cantidades //
    private JSpinner huespedes;
    private JSpinner dormitorios;
    private JSpinner banios;
    private JSpinner camas;
    private JLabel lblHuespedes, lblDormitorios, lblBanios, lblCamas;
    // Panel + array de JComboBox para tipos de camas //
    private JPanel panelTiposCamas;
    private JComboBox<String>[] tiposCama;
    // Checkbox de niños + panel adicional //
    private JCheckBox ninios;
    private JPanel panelExtrasNinios;
    private JSpinner edadNinio;
    private JTextArea extras;
    private JLabel lblEdadNinio, lblExtras;
    // Panel de imágenes del piso //
    private JPanel panelImagenes;
    private JLabel imagen1, imagen2, imagen3;
    // Precio mínimo //
    private JTextField precioMinimo;
    private JLabel lblPrecioMinimo;
	
    // -> PANEL 4: RESUMEN <- //
    private JTabbedPane paneResumen;
    private JPanel panelResumenArrendador, panelResumenInmueble;
    private JTextArea resumenArrendador, resumenInmueble;
    // Botones de acción //
    private JButton imprimir, nuevo, guardar, aceptar, cancelar;
    // El componente adicional (un JSlider en este caso) //
    private JSlider valoracion;
    private JLabel lblValoracion;

    
    // Bordes simplificados
    private final Border BORDE_DEFECTO = UIManager.getBorder("TextField.border");
    private final Border BORDE_ROJO = BorderFactory.createLineBorder(Color.RED, 2);

    // ======================================================= //
    //                       CONSTRUCTOR                       //
    // ======================================================= //

    public PanelJDialog(Frame owner) {
    		// ========================================================= //
    		// super(Frame owner, String title, boolean modal)			//
        // Llamamos al constructor con parametro "Frame owner"		//
        // owner -> la ventana que abre este diálogo				    //
        // "Alta Pisos" -> título del diálogo						//
        // true -> modal (sirve para bloquear la ventana principal)  //
		// ========================================================= //

        super(owner, "Alta Pisos", true);

        // ======================================================= //
        // 1. Configuración INICIAL de la ventana de diálogo       //
        // ======================================================= //

        // Obtenemos el tamaño de la pantalla del usuario //
        Dimension tamanio = Toolkit.getDefaultToolkit().getScreenSize();
        // NOTA: Se ha reducido el tamaño inicial para que no ocupe toda la pantalla
        // y se ajuste mejor al contenido del formulario.
        setSize((int)(tamanio.width * 0.9), (int)(tamanio.height * 0.85));

        // Centrar ventana //
        setLocationRelativeTo(null);

        // ======================================================= //
        // 2. Inicialización de componentes					   	   //
        // ======================================================= //
        
        inicializarComponentes();
        configurarLayout();
        configurarEventos();
        registrarAccelerators();
        actualizarResumen();
        attachResumenListener(nombre, apellidos, dni, telefono, direccion);
        attachResumenListener(extras);
        attachResumenListener(huespedes, dormitorios, banios, camas, edadNinio);
        attachResumenListener(valoracion); 
        attachResumenListener(ninios);

        // BOTON POR DEFECTO
        getRootPane().setDefaultButton(aceptar);

        // Mostrar la ventana //
        setVisible(true);
    }

    // ======================================================= //
    //                 INICIALIZAR COMPONENTES                 //
    // ======================================================= //

    private void inicializarComponentes() {
        // Panel Cabecera //
        panelCabecera = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panelCabecera.setBackground(new Color(200, 220, 255));
        panelCabecera.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
        ImageIcon icono = new ImageIcon(getClass().getResource("/recursos/icono.png"));

        
        tituloCabecera = new JLabel("Formulario de Alta Pisos - Gestión Apartamentos Turísticos Bolivianos", icono, JLabel.LEFT);
        tituloCabecera.setFont(new Font("SansSerif", Font.BOLD, 24));
        tituloCabecera.setForeground(Color.BLUE);
        panelCabecera.add(tituloCabecera);

        // Panel Arrendador //
        panelArrendador = new JPanel(new GridLayout(4, 2, 10, 10));
        panelArrendador.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(Color.LIGHT_GRAY), 
            "Datos del Arrendador"));
        
        // Inicializar componentes del arrendador //
        nombre = new JTextField(15);
        apellidos = new JTextField(15);
        dni = new JTextField(10);
        telefono = new JTextField(9);
        
        lblNombre = new JLabel("Nombre:");
        lblApellidos = new JLabel("Apellidos:");
        lblDni = new JLabel("DNI:");
        lblTelefono = new JLabel("Teléfono:");
        
        // Configurar mnemonics para labels //
        // Los mnemonics mejoran la accesibilidad (Alt + Letra) //
        lblNombre.setDisplayedMnemonic(KeyEvent.VK_N);
        lblNombre.setLabelFor(nombre);
        lblApellidos.setDisplayedMnemonic(KeyEvent.VK_S); // De surname, VK_A se usa en el boton aceptar //
        lblApellidos.setLabelFor(apellidos);
        lblDni.setDisplayedMnemonic(KeyEvent.VK_D); 
        lblDni.setLabelFor(dni);
        lblTelefono.setDisplayedMnemonic(KeyEvent.VK_T);
        lblTelefono.setLabelFor(telefono);
        
        // Tooltips para ayudar al usuario //s
        nombre.setToolTipText("Introduce el nombre del arrendador (obligatorio)");
        apellidos.setToolTipText("Introduce los apellidos del arrendador (obligatorio)");
        dni.setToolTipText("Introduce el DNI (8 dígitos y letra), por ejemplo: 12345678A");
        telefono.setToolTipText("Introduce el teléfono (mínimo 9 dígitos)");


        // Guardar propiedades originales en cada componente //
        storeOriginalProperties(nombre);
        storeOriginalProperties(apellidos);
        storeOriginalProperties(dni);
        storeOriginalProperties(telefono);

        // Añadir componentes al panel arrendador //
        panelArrendador.add(lblNombre);
        panelArrendador.add(nombre);
        panelArrendador.add(lblApellidos);
        panelArrendador.add(apellidos);
        panelArrendador.add(lblDni);
        panelArrendador.add(dni);
        panelArrendador.add(lblTelefono);
        panelArrendador.add(telefono);

        // Panel Inmueble //
        panelInmueble = new JPanel();
        panelInmueble.setLayout(new BoxLayout(panelInmueble, BoxLayout.Y_AXIS));
        panelInmueble.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(Color.LIGHT_GRAY), 
            "Datos del Inmueble"));
        
        // Inicializar componentes del inmueble //
        direccion = new JTextField(20);
        lblDireccion = new JLabel("Dirección:");
        lblDireccion.setDisplayedMnemonic(KeyEvent.VK_Q);
        lblDireccion.setLabelFor(direccion);
        direccion.setToolTipText("Introduce la dirección del inmueble (obligatorio) (usar ALT + Q para la escritura)");
        storeOriginalProperties(direccion);
        
        // Provincia //
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
        lblProvincia = new JLabel("Provincia:");
        lblProvincia.setDisplayedMnemonic(KeyEvent.VK_P);
        lblProvincia.setLabelFor(provincia);
        provincia.setToolTipText("Selecciona la provincia donde se ubica el inmueble");

        // Fechas //
        Calendar calendario = Calendar.getInstance();
        Date fechaActual = calendario.getTime();
        
        calendario.add(Calendar.YEAR, 1);
        Date fechaUnAnio = calendario.getTime();
        
        fechaAlta = new JSpinner(new SpinnerDateModel(fechaActual, null, null, Calendar.DAY_OF_MONTH));
        fechaFin = new JSpinner(new SpinnerDateModel(fechaUnAnio, null, null, Calendar.DAY_OF_MONTH));
        
        // Formatear spinners de fecha para mostrar solo fecha (sin hora) //
        JSpinner.DateEditor editorFechaAlta = new JSpinner.DateEditor(fechaAlta, "dd/MM/yyyy");
        JSpinner.DateEditor editorFechaFin = new JSpinner.DateEditor(fechaFin, "dd/MM/yyyy");
        fechaAlta.setEditor(editorFechaAlta);
        fechaFin.setEditor(editorFechaFin);
        
        lblFechaAlta = new JLabel("Fecha de alta:");
        lblFechaFin = new JLabel("Fecha final:");

        // Spinners numéricos //
        huespedes = new JSpinner(new SpinnerNumberModel(1, 1, 8, 1));
        dormitorios = new JSpinner(new SpinnerNumberModel(1, 1, 4, 1));
        banios = new JSpinner(new SpinnerNumberModel(1, 1, 3, 1));
        camas = new JSpinner(new SpinnerNumberModel(1, 1, 4, 1));
        
        lblHuespedes = new JLabel("Nº huéspedes:");
        lblDormitorios = new JLabel("Nº dormitorios:");
        lblBanios = new JLabel("Nº baños:");
        lblCamas = new JLabel("Nº camas:");

        // Panel tipos de camas //
        panelTiposCamas = new JPanel(new GridLayout(4, 2, 10, 10));
        panelTiposCamas.setBorder(BorderFactory.createTitledBorder("Tipos de cama"));
        tiposCama = new JComboBox[4];
        
        for (int i = 0; i < 4; i++) {
            JLabel etiquetaCama = new JLabel("Cama " + (i + 1) + ":");
            tiposCama[i] = new JComboBox<>(new String[]{"Cama simple", "Cama doble", "Sofá cama"});
            tiposCama[i].setEnabled(i == 0); // Solo la primera habilitada inicialmente
            panelTiposCamas.add(etiquetaCama);
            panelTiposCamas.add(tiposCama[i]);
        }

        // Panel niños //
        ninios = new JCheckBox("¿Niños?");
        panelExtrasNinios = new JPanel(new GridLayout(2, 2, 5, 5));
        panelExtrasNinios.setBorder(BorderFactory.createTitledBorder("Extras niños"));
        // El estado de los componentes internos debe ser manejado por el evento del JCheckBox
        
        edadNinio = new JSpinner(new SpinnerNumberModel(0, 0, 10, 1));
        extras = new JTextArea(3, 15);
        extras.setLineWrap(true);
        extras.setEditable(false); // Bloquear edición por el usuario

        
        lblEdadNinio = new JLabel("Edad niño:");
        lblExtras = new JLabel("Extras:");
        
        panelExtrasNinios.add(lblEdadNinio);
        panelExtrasNinios.add(edadNinio);
        panelExtrasNinios.add(lblExtras);
        panelExtrasNinios.add(new JScrollPane(extras));
        
        // Inicialmente deshabilitado //
        setPanelExtrasNiniosEnabled(false);
        
        // Más tooltips //
        fechaAlta.setToolTipText("Selecciona la fecha de alta (por defecto, hoy)");
        fechaFin.setToolTipText("Selecciona la fecha final del contrato");
        storeOriginalProperties(fechaAlta);
        storeOriginalProperties(fechaFin);
        huespedes.setToolTipText("Número máximo de huéspedes permitidos");
        dormitorios.setToolTipText("Número de dormitorios del inmueble");
        banios.setToolTipText("Número de baños (influye en el precio mínimo)");
        camas.setToolTipText("Número total de camas disponibles");
        storeOriginalProperties(huespedes);
        storeOriginalProperties(dormitorios);
        storeOriginalProperties(banios);
        storeOriginalProperties(camas);
        for (int i = 0; i < tiposCama.length; i++) {
            tiposCama[i].setToolTipText("Selecciona el tipo de la cama " + (i + 1));
            storeOriginalProperties(tiposCama[i]);
        }
        ninios.setToolTipText("Marca si hay niños (activará opciones adicionales)");
        storeOriginalProperties(ninios);
        edadNinio.setToolTipText("Edad del niño (0 a 10 años). Controla el tipo de cama: cuna o supletoria");
        extras.setToolTipText("Se rellenará automáticamente según la edad (cuna o cama supletoria)");
        storeOriginalProperties(edadNinio);
        storeOriginalProperties(extras);

        // Panel imágenes //
        panelImagenes = new JPanel(new GridLayout(1, 3, 10, 10));
        panelImagenes.setBorder(BorderFactory.createTitledBorder("Imágenes del piso"));
        
 
        imagen1 = new JLabel(new ImageIcon(getClass().getResource("/recursos/img1.png")), SwingConstants.CENTER);
        imagen2 = new JLabel(new ImageIcon(getClass().getResource("/recursos/img2.png")), SwingConstants.CENTER);
        imagen3 = new JLabel(new ImageIcon(getClass().getResource("/recursos/img3.png")), SwingConstants.CENTER);
        
        imagen1.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        imagen2.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        imagen3.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        
        panelImagenes.add(imagen1);
        panelImagenes.add(imagen2);
        panelImagenes.add(imagen3);

        // Precio mínimo //
        precioMinimo = new JTextField("0 €");
        precioMinimo.setEditable(false);
        lblPrecioMinimo = new JLabel("Precio Mínimo:");
        precioMinimo.setToolTipText("Precio mínimo calculado automáticamente");
        
        imagen1.setToolTipText("Vista previa 1 del inmueble");
        imagen2.setToolTipText("Vista previa 2 del inmueble");
        imagen3.setToolTipText("Vista previa 3 del inmueble");
        precioMinimo.setToolTipText("Precio mínimo del inmueble según camas, baños y extras");
        storeOriginalProperties(precioMinimo);

        // Panel Resumen (JTabbedPane) //
        paneResumen = new JTabbedPane();
        paneResumen.setPreferredSize(new Dimension(400, 150));
        
        panelResumenArrendador = new JPanel(new BorderLayout());
        panelResumenInmueble = new JPanel(new BorderLayout());
        
        resumenArrendador = new JTextArea(8, 30);
        resumenInmueble = new JTextArea(8, 30);
        resumenArrendador.setEditable(false);
        resumenInmueble.setEditable(false);
        
        panelResumenArrendador.add(new JScrollPane(resumenArrendador), BorderLayout.CENTER);
        panelResumenInmueble.add(new JScrollPane(resumenInmueble), BorderLayout.CENTER);
        
        paneResumen.addTab("Arrendador", panelResumenArrendador);
        paneResumen.addTab("Inmueble", panelResumenInmueble);
        
        resumenArrendador.setToolTipText("Resumen automático de los datos del arrendador");
        resumenInmueble.setToolTipText("Resumen automático de los datos del inmueble");
        storeOriginalProperties(resumenArrendador);
        storeOriginalProperties(resumenInmueble);

        // Botones de acción //
        imprimir = new JButton("Imprimir");
        nuevo = new JButton("Nuevo");
        guardar = new JButton("Guardar");
        aceptar = new JButton("Aceptar");
        cancelar = new JButton("Cancelar");
        
        // Configurar mnemonics para botones
        imprimir.setMnemonic(KeyEvent.VK_I);
        nuevo.setMnemonic(KeyEvent.VK_N);
        guardar.setMnemonic(KeyEvent.VK_G);
        aceptar.setMnemonic(KeyEvent.VK_A);
        cancelar.setMnemonic(KeyEvent.VK_C);

        // Tooltips para botones (ayuda rápida) //
        imprimir.setToolTipText("Imprimir formulario (Ctrl+I)");
        nuevo.setToolTipText("Limpiar formulario para un nuevo registro (Ctrl+N)");
        guardar.setToolTipText("Guardar los datos (Ctrl+G)");
        aceptar.setToolTipText("Aceptar y cerrar (Ctrl+A)");
        cancelar.setToolTipText("Cancelar y cerrar (Ctrl+C)");
        
        // Componente adicional: JSlider //
        valoracion = new JSlider(0, 10, 5);
        valoracion.setMajorTickSpacing(2);
        valoracion.setMinorTickSpacing(1);
        valoracion.setPaintTicks(true);
        valoracion.setPaintLabels(true);
        valoracion.setToolTipText("Valoración general del inmueble (0-10)");
        lblValoracion = new JLabel("Valoración:");
    }
    
    // Método auxiliar para habilitar/deshabilitar el panelExtrasNinios //
    private void setPanelExtrasNiniosEnabled(boolean activo) {
        panelExtrasNinios.setEnabled(activo);
        for (Component componente : panelExtrasNinios.getComponents()) {
            componente.setEnabled(activo);
        }
    }

    // Guarda propiedades originales del componente sin usar colecciones externas (IA)
    private void storeOriginalProperties(JComponent comp) {
        comp.putClientProperty("origBorder", comp.getBorder());
        comp.putClientProperty("origTooltip", comp.getToolTipText());
    }

    // Pone borde rojo y tooltip de error //
    private void mostrarError(JComponent componente, String mensaje) {
        componente.setBorder(BORDE_ROJO);
        componente.setToolTipText(mensaje);
    }

    // Restaura borde y tooltip originales (si se guardaron), o BORDE_DEFECTO sino (Generado con IA) //
    private void manejorErrorBordes(JComponent comp) {
        Object b = comp.getClientProperty("origBorder");
        if (b instanceof Border) comp.setBorder((Border) b);
        else comp.setBorder(BORDE_DEFECTO);

        Object t = comp.getClientProperty("origTooltip");
        if (t instanceof String) comp.setToolTipText((String) t);
        else comp.setToolTipText(null);
    }

    // ======================================================= //
    //                 CONFIGURAR LAYOUT                       //
    // ======================================================= //

    private void configurarLayout() {
        // Panel principal con BorderLayout //
        JPanel panelPrincipal = new JPanel(new BorderLayout(10, 10));
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Margen general
        
        // Panel norte: Cabecera //
        panelPrincipal.add(panelCabecera, BorderLayout.NORTH);
        
        // Panel centro: Formularios (Arrendador e Inmueble) //
        JPanel panelFormularios = new JPanel();
        // Usamos un layout manager que permita una buena distribución horizontal/vertical //
        // Para simplificar y mantener la estructura original, usamos BoxLayout vertical para el contenido principal //
        panelFormularios.setLayout(new BoxLayout(panelFormularios, BoxLayout.Y_AXIS)); 
        panelFormularios.add(panelArrendador);
        panelFormularios.add(Box.createVerticalStrut(10));
        
        // Panel de Inmueble. se usa un panel intermedio para el contenido principal //
        JPanel panelInmuebleContenido = new JPanel(new GridLayout(0, 2, 10, 10));
        panelInmuebleContenido.add(lblDireccion);
        panelInmuebleContenido.add(direccion);
        panelInmuebleContenido.add(lblProvincia);
        panelInmuebleContenido.add(provincia);
        panelInmuebleContenido.add(lblFechaAlta);
        panelInmuebleContenido.add(fechaAlta);
        panelInmuebleContenido.add(lblFechaFin);
        panelInmuebleContenido.add(fechaFin);
        panelInmuebleContenido.add(lblHuespedes);
        panelInmuebleContenido.add(huespedes);
        panelInmuebleContenido.add(lblDormitorios);
        panelInmuebleContenido.add(dormitorios);
        panelInmuebleContenido.add(lblBanios);
        panelInmuebleContenido.add(banios);
        panelInmuebleContenido.add(lblCamas);
        panelInmuebleContenido.add(camas);
        
        // El panel Inmueble es el contenedor vertical de todos los subpaneles del inmueble //
        panelInmueble.add(panelInmuebleContenido);
        panelInmueble.add(Box.createVerticalStrut(10));
        panelInmueble.add(panelTiposCamas);
        panelInmueble.add(Box.createVerticalStrut(10));
        
        JPanel panelNinios = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelNinios.add(ninios);
        panelInmueble.add(panelNinios);
        panelInmueble.add(panelExtrasNinios);
        panelInmueble.add(Box.createVerticalStrut(10));
        panelInmueble.add(panelImagenes);
        panelInmueble.add(Box.createVerticalStrut(10));
        
        JPanel panelPrecio = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelPrecio.add(lblPrecioMinimo);
        panelPrecio.add(precioMinimo);
        panelInmueble.add(panelPrecio);
        
        panelFormularios.add(panelInmueble);
        
        // Hacemos el panel principal de formularios desplazable, ya que el contenido es grande //
        panelPrincipal.add(new JScrollPane(panelFormularios), BorderLayout.CENTER); 
        
        // Panel sur: Resumen y Botones //
        JPanel panelSur = new JPanel(new BorderLayout(10, 10));

        // Panel Resumen
        // Se añade directamente el JTabbedPane, ya configurado con las pestañas "Arrendador" e "Inmueble" //
        
        // Panel de valoración //
        JPanel panelValoracion = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.0;
        panelValoracion.add(lblValoracion, gbc);
        
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        panelValoracion.add(valoracion, gbc);

        // Contenedor para Resumen y Valoración //
        JPanel panelResumenYValoracion = new JPanel(new GridLayout(1, 2, 10, 0));
        panelResumenYValoracion.add(paneResumen);
        panelResumenYValoracion.add(panelValoracion);
        
        panelSur.add(panelResumenYValoracion, BorderLayout.CENTER);
        
        // Botones //
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panelBotones.add(imprimir);
        panelBotones.add(nuevo);
        panelBotones.add(guardar);
        panelBotones.add(aceptar);
        panelBotones.add(cancelar);
        
        panelSur.add(panelBotones, BorderLayout.SOUTH);
        
        panelPrincipal.add(panelSur, BorderLayout.SOUTH);
        
        // Añadir panel principal al diálogo //
        add(panelPrincipal);
    }

    // ======================================================= //
    // CONFIGURAR EVENTOS									   //
    // ======================================================= //
    private void configurarEventos() {
        // Checkbox niños habilita/deshabilita panel de extras
        ninios.addItemListener(e -> {
            boolean seleccionado = (e.getStateChange() == ItemEvent.SELECTED);
            setPanelExtrasNiniosEnabled(seleccionado);
        });
        
     // Al activar/desactivar la casilla de niños: habilitar panel y limpiar si se desactiva //
        ninios.addItemListener(e -> {
            boolean seleccionado = (e.getStateChange() == ItemEvent.SELECTED);
            setPanelExtrasNiniosEnabled(seleccionado);
            if (!seleccionado) {
                // si se desactiva, borramos el texto de extras //
                extras.setText("");
                // opcional: recalcular precio si dependiera //
                recalcularPrecio();
                actualizarResumen();

            }
        });

        // Cuando el spinner de edad cambia, si el campo extras tiene foco o está vacío actualizar (Generado con IA)
        edadNinio.addChangeListener(e -> {
            // Si el campo extras tiene focus, o si está vacío/contiene texto automático, actualizarlo
            if (extras.isFocusOwner() || extras.getText().trim().isEmpty()
                    || extras.getText().trim().equalsIgnoreCase("cuna")
                    || extras.getText().trim().equalsIgnoreCase("cama supletoria pequeña")) {
                rellenarExtrasPorEdad();
            }
            
            recalcularPrecio();
            actualizarResumen();
        });

        // Cuando el campo "extras" recibe el foco, rellenarl automáticamente //
        extras.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                rellenarExtrasPorEdad();
            }
        });


        // DocumentListeners para borar borde rojo cuando el usuario escribe //
        addClearErrorOnEdit(nombre);
        addClearErrorOnEdit(apellidos);
        addClearErrorOnEdit(dni);
        addClearErrorOnEdit(telefono);
        addClearErrorOnEdit(direccion);

        // Botones: acciones mínimas //
        imprimir.addActionListener(e -> {
            // Acción mínima: confirmar impresión //
            JOptionPane.showMessageDialog(this, "Enviar a imprimir.", "Imprimir", JOptionPane.INFORMATION_MESSAGE);
        });

        nuevo.addActionListener(e -> {
            limpiarFormulario();
        });

        guardar.addActionListener(e -> {
            if (validarFormulario()) {
                JOptionPane.showMessageDialog(this, "Datos validados. Guardando...", "Guardar", JOptionPane.INFORMATION_MESSAGE);
            }
        });
        
        
        
        // Recalcular cuando cambien los baños //
        banios.addChangeListener(e -> {
        recalcularPrecio();
        actualizarResumen();
        });

        // Recalcular cuando cambie el número de camas //
        camas.addChangeListener(e -> {
            recalcularPrecio();
            actualizarResumen();
            });
        

        // Recalcular cuando cambie el tipo de cada cama //
        for (int i = 0; i < tiposCama.length; i++) {
            tiposCama[i].addActionListener(e -> {
                recalcularPrecio();
                actualizarResumen();
                });
        }

        // Recalcular cuando se marque/desmarque la opción de niños //
        ninios.addActionListener(e -> {
            recalcularPrecio();
            actualizarResumen();
            });

        aceptar.addActionListener(e -> {
            if (validarFormulario()) {
                // Aceptar: validar y cerrar //
                JOptionPane.showMessageDialog(this, "Datos validados. Aceptando y cerrando.", "Aceptar", JOptionPane.INFORMATION_MESSAGE);
                dispose();
            }
        });

        cancelar.addActionListener(e -> {
            // Confirmación antes de cerrar //
            int resp = JOptionPane.showConfirmDialog(this, "¿Cancelar y cerrar sin guardar?", "Cancelar", JOptionPane.YES_NO_OPTION);
            if (resp == JOptionPane.YES_OPTION) {
                dispose();
            }
        });
        
        camas.addChangeListener(e -> {
            int num = (int) camas.getValue();

            for (int i = 0; i < tiposCama.length; i++) {
                if (i < num) {
                    tiposCama[i].setEnabled(true);
                } else {
                    tiposCama[i].setEnabled(false);
                    tiposCama[i].setSelectedIndex(0);
                }
            }
        });

        // Restaurar borde cuando los spinners cambian//
        fechaAlta.addChangeListener(e -> manejorErrorBordes(fechaAlta));
        fechaFin.addChangeListener(e -> manejorErrorBordes(fechaFin));
    }

    // Añade un DocumentListener que limpia la marca de error al editar (IA) //
    private void addClearErrorOnEdit(JTextField field) {
        field.getDocument().addDocumentListener(new DocumentListener() {
            @Override public void insertUpdate(DocumentEvent e) { manejorErrorBordes(field); }
            @Override public void removeUpdate(DocumentEvent e) { manejorErrorBordes(field); }
            @Override public void changedUpdate(DocumentEvent e) { manejorErrorBordes(field); }
        });
    }
    
    private void attachResumenListener(JTextField... campos) {
        for (JTextField field : campos) {
            field.getDocument().addDocumentListener(new DocumentListener() {
                @Override public void insertUpdate(DocumentEvent e) { actualizarResumen(); }
                @Override public void removeUpdate(DocumentEvent e) { actualizarResumen(); }
                @Override public void changedUpdate(DocumentEvent e) { actualizarResumen(); }
            });
        }
    }
    
    private void attachResumenListener(JTextArea... areas) {
        for (JTextArea area : areas) {
            area.getDocument().addDocumentListener(new DocumentListener() {
                @Override public void insertUpdate(DocumentEvent e) { actualizarResumen(); }
                @Override public void removeUpdate(DocumentEvent e) { actualizarResumen(); }
                @Override public void changedUpdate(DocumentEvent e) { actualizarResumen(); }
            });
        }
    }

    private void attachResumenListener(JSlider... sliders) {
        for (JSlider slider : sliders) {
            slider.addChangeListener(e -> actualizarResumen());
        }
    }

    private void attachResumenListener(JSpinner... spinners) {
        for (JSpinner spinner : spinners) {
            spinner.addChangeListener(e -> actualizarResumen());
        }
    }

    private void attachResumenListener(JCheckBox... checkboxes) {
        for (JCheckBox cb : checkboxes) {
            cb.addActionListener(e -> actualizarResumen());
        }
    }
    
    private void actualizarResumen() {
        // ============================= //
        // Resumen Arrendador			 //
        // ============================= //
        StringBuilder sbArr = new StringBuilder();
        sbArr.append("Nombre: ").append(nombre.getText().trim()).append("\n");
        sbArr.append("Apellidos: ").append(apellidos.getText().trim()).append("\n");
        sbArr.append("DNI: ").append(dni.getText().trim()).append("\n");
        sbArr.append("Teléfono: ").append(telefono.getText().trim()).append("\n");
        sbArr.append("Dirección: ").append(direccion.getText().trim()).append("\n");
        sbArr.append("Número de huéspedes: ").append(huespedes.getValue()).append("\n");
        resumenArrendador.setText(sbArr.toString());

        // ============================= //
        // Resumen Inmueble 			 //
        // ============================= //
        
        StringBuilder inmueble = new StringBuilder();
        inmueble.append("Dormitorios: ").append(dormitorios.getValue()).append("\n");
        inmueble.append("Baños: ").append(banios.getValue()).append("\n");
        inmueble.append("Camas: ").append(camas.getValue()).append("\n");

        for (int i = 0; i < (int)camas.getValue(); i++) {
            inmueble.append("Cama ").append(i + 1).append(": ").append(tiposCama[i].getSelectedItem()).append("\n");
        }

        inmueble.append("Niños: ").append(ninios.isSelected() ? "Sí" : "No").append("\n");
        if (ninios.isSelected()) {
            inmueble.append("Extras: ").append(extras.getText().trim()).append("\n");
        }

        inmueble.append("Precio mínimo: ").append(precioMinimo.getText()).append("\n");
        inmueble.append("Valoración: ").append(valoracion.getValue()).append(" / 10\n");

        resumenInmueble.setText(inmueble.toString());
    }
    
    private void recalcularPrecio() {
        int precio = 0;

        // 1. Precio por baños
        int numBanios = (int) banios.getValue();
        precio += numBanios * 25;

        // 2. Precio por camas
        int numCamas = (int) camas.getValue();
        for (int i = 0; i < numCamas; i++) {
            String tipo = (String) tiposCama[i].getSelectedItem();

            if ("Cama doble".equals(tipo)) {
                precio += 20;
            } else if ("Cama simple".equals(tipo)) {
                precio += 15;
            } else if ("Sofá cama".equals(tipo)) {
                precio += 15;
            }
        }

        // 3. Cuna / cama supletoria si hay niños
        if (ninios.isSelected()) {
            precio += 12;
        }

        // 4. Mostrar precio final
        precioMinimo.setText(precio + " €");
    }
    
    private void rellenarExtrasPorEdad() {
        if (!ninios.isSelected()) {
            extras.setText("");
            return;
        }

        int edad = (int) edadNinio.getValue();
        if (edad >= 0 && edad <= 3) {
            extras.setText("cuna");
        } else if (edad >= 4 && edad <= 10) {
            extras.setText("cama supletoria pequeña");
        } else {
            extras.setText("");
        }

        // Asegurar que precio y resumen reflejen el cambio
        recalcularPrecio();
        actualizarResumen();
    }


    // Limpia todos los campos del formulario y restaura bordes //
    private void limpiarFormulario() {
        nombre.setText("");
        apellidos.setText("");
        dni.setText("");
        telefono.setText("");
        direccion.setText("");
        huespedes.setValue(1);
        dormitorios.setValue(1);
        banios.setValue(1);
        camas.setValue(1);
        precioMinimo.setText("40 €"); //La cama simple + el baño //
        valoracion.setValue(5);
        ninios.setSelected(false);
        extras.setText("");
        // restaurar propiedades originales en los componentes que registramos //
        manejorErrorBordes(nombre);
        manejorErrorBordes(apellidos);
        manejorErrorBordes(dni);
        manejorErrorBordes(telefono);
        manejorErrorBordes(direccion);
    }

    // ======================================================= //
    // MÉTODOS DE VALIDACIÓN                                   //
    // ======================================================= //

    // Valida el formulario de manera básica (puedes ampliar reglas)
    private boolean validarFormulario() {
        boolean valido = true;

        // Limpiar errores previos en campos principales
        manejorErrorBordes(nombre);
        manejorErrorBordes(apellidos);
        manejorErrorBordes(dni);
        manejorErrorBordes(telefono);
        manejorErrorBordes(direccion);

        // Nombre
        if (nombre.getText().trim().isEmpty()) {
            mostrarError(nombre, "El nombre es obligatorio.");
            valido = false;
        }

        // Apellidos
        if (apellidos.getText().trim().isEmpty()) {
            mostrarError(apellidos, "Los apellidos son obligatorios.");
            valido = false;
        }

        // DNI (Uso expresiones regulares porque ya los he manejado el curso anterior) //
        String dniText = dni.getText().trim();
        if (dniText.isEmpty() || !dniText.matches("\\d{8}[A-Za-z]")) {
            mostrarError(dni, "DNI inválido. Formato: 8 dígitos y letra, por ejemplo 12345678A");
            valido = false;
        }

        // Teléfono de 9 digitos //
        String tel = telefono.getText().trim();
        if (!tel.matches("\\d{9}")) {  // debe ser exactamente 9 dígitos, nada más
            mostrarError(telefono, "Teléfono inválido. Introduce exactamente 9 dígitos numéricos.");
            valido = false;
        }

        // Dirección
        if (direccion.getText().trim().isEmpty()) {
            mostrarError(direccion, "La dirección es obligatoria.");
            valido = false;
        }

        // Si hay error: mostrar mensaje resumen //
        if (!valido) {
            JOptionPane.showMessageDialog(this, "Hay errores en el formulario. Revisa los campos marcados en rojo.", "Errores de validación", JOptionPane.WARNING_MESSAGE);
        }

        return valido;
    }

    // ======================================================= //
    // ACCELERATORS (ATAJOS)                                   //
    // ======================================================= //

    // Registra atajos de teclado (Ctrl+G, Ctrl+N, Ctrl + P, Ctrl+A, Ctrl+C) //
    private void registrarAccelerators() {
        JRootPane root = getRootPane();
        InputMap im = root.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        ActionMap am = root.getActionMap();

        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_G, InputEvent.CTRL_DOWN_MASK), "guardar");
        am.put("guardar", new AbstractAction() {
            @Override public void actionPerformed(ActionEvent e) { guardar.doClick(); }
        });

        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_N, InputEvent.CTRL_DOWN_MASK), "nuevo");
        am.put("nuevo", new AbstractAction() {
            @Override public void actionPerformed(ActionEvent e) { nuevo.doClick(); }
        });

        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_I, InputEvent.CTRL_DOWN_MASK), "imprimir");
        am.put("imprimir", new AbstractAction() {
            @Override public void actionPerformed(ActionEvent e) { imprimir.doClick(); }
        });

        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_A, InputEvent.CTRL_DOWN_MASK), "aceptar");
        am.put("aceptar", new AbstractAction() {
            @Override public void actionPerformed(ActionEvent e) { aceptar.doClick(); }
        });

        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_C, InputEvent.CTRL_DOWN_MASK), "cancelar");
        am.put("cancelar", new AbstractAction() {
            @Override public void actionPerformed(ActionEvent e) { cancelar.doClick(); }
        });
    }
}