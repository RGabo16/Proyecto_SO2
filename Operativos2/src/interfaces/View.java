package interfaces;

import Clases.Archivo;
import Clases.Cola;
import Clases.Directorio;
import Clases.Nodo;
import Clases.Proceso;
import java.awt.Color;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.JTree;
import javax.swing.SpinnerNumberModel;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

/**
 *
 * @author Luri
 */
public class View extends javax.swing.JFrame {

    boolean isAdmin = true;
    private Controlador controlador;
    private DefaultTreeModel treeModel;
    private DefaultMutableTreeNode rootNode;
    private JPanel panel;
    private JTextField nombreField;
    private JRadioButton directorioButton;
    private JRadioButton archivoButton;
    private JSpinner bloquesSpinner;
    private JComboBox<String> privacidadCombo;
    private java.util.Map<String, java.awt.Color> bloqueColors = new java.util.HashMap<>();

    public View(Controlador controlador) {
        this.controlador = controlador;

        initComponents();

        actualizarTablaProcesos();
        actualizarTablaDiscos();
        actualizarListasProcesos();

        rootNode = new DefaultMutableTreeNode("/");
        treeModel = new DefaultTreeModel(rootNode);

        jTree.setModel(treeModel);

        // Configurar el renderer para mostrar solo el nombre
        // Configurar el renderer personalizado para mostrar iconos adecuados
        jTree.setCellRenderer(new DefaultTreeCellRenderer() {
            @Override
            public java.awt.Component getTreeCellRendererComponent(JTree tree, Object value, boolean sel, boolean expanded, boolean leaf, int row, boolean hasFocus) {
                super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);

                if (value instanceof DefaultMutableTreeNode) {
                    DefaultMutableTreeNode node = (DefaultMutableTreeNode) value;
                    Object userObject = node.getUserObject();

                    // Establecer el texto (nombre)
                    if (userObject instanceof Archivo) {
                        setText(((Archivo) userObject).getNombre());
                        // Icono de archivo para archivos
                        setIcon(UIManager.getIcon("FileView.fileIcon"));
                    } else if (userObject instanceof Directorio) {
                        setText(((Directorio) userObject).getNombre());
                        // Icono de carpeta para directorios
                        if (expanded) {
                            setIcon(UIManager.getIcon("FileView.directoryIcon"));
                        } else {
                            setIcon(UIManager.getIcon("FileView.directoryIcon"));
                        }
                    } else if (userObject instanceof String) {
                        setText((String) userObject);
                        // Para el nodo raíz (string "/")
                        setIcon(UIManager.getIcon("FileView.directoryIcon"));
                    }
                }
                return this;
            }
        });
        jTree.setShowsRootHandles(true);
        jTree.setRootVisible(true);

        Directorio dirPrincipal = new Directorio("/", "Modo Administrador");

        //Nombre
        nombreField = new JTextField(15);

        // Tipo
        directorioButton = new JRadioButton("Directorio", true); // Directorio seleccionado por defecto
        archivoButton = new JRadioButton("Archivo", false);
        ButtonGroup tipoGroup = new ButtonGroup();
        tipoGroup.add(directorioButton);
        tipoGroup.add(archivoButton);

        //#Bloques
        SpinnerNumberModel spinnerModel = new SpinnerNumberModel(1, 1, 100, 1); // Valor inicial 1, Mínimo 1, Máximo 100, Paso 1
        bloquesSpinner = new JSpinner(spinnerModel);
        bloquesSpinner.setEnabled(false); // Deshabilitado por defecto si Directorio está seleccionado

        // Privacidad (JComboBox)
        String[] opcionesPrivacidad = {"Público", "Privado"};
        privacidadCombo = new JComboBox<>(opcionesPrivacidad);

        ActionListener tipoListener = e -> {
            // Habilita el JSpinner solo si se selecciona 'Archivo'
            bloquesSpinner.setEnabled(archivoButton.isSelected());
        };

        directorioButton.addActionListener(tipoListener);
        archivoButton.addActionListener(tipoListener);

        panel = new JPanel(new GridLayout(4, 2, 5, 5));

        // Fila 1: Nombre
        panel.add(new JLabel("Nombre:"));
        panel.add(nombreField);

        // Fila 2: Tipo (Radio Buttons en un sub-panel para mantenerlos juntos)
        JPanel radioPanel = new JPanel();
        radioPanel.add(directorioButton);
        radioPanel.add(archivoButton);
        panel.add(new JLabel("Tipo:"));
        panel.add(radioPanel);

        // Fila 3: # Bloques
        panel.add(new JLabel("# Bloques:"));
        panel.add(bloquesSpinner);

        // Fila 4: Privacidad
        panel.add(new JLabel("Privacidad:"));
        panel.add(privacidadCombo);

    }

    // Método para actualizar las listas de procesos
    public void actualizarListasProcesos() {
        // Ejecutar en el hilo de EDT (Event Dispatch Thread)
        javax.swing.SwingUtilities.invokeLater(() -> {
            actualizarColaSolicitudes();
            actualizarColaTerminadas();
        });
    }
    
    private void actualizarColaSolicitudes() {
        if (controlador != null && controlador.getGp() != null) {
            Cola colaProcesos = controlador.getGp().getCola_procesos();
            java.util.List<String> procesos = new java.util.ArrayList<>();
            
            // Recorrer la cola de procesos y agregar a la lista
            Nodo actual = colaProcesos.getCabeza();
            while (actual != null) {
                Proceso proceso = actual.getProceso();
                if (proceso != null) {
                    String infoProceso = String.format("%s - %s (%d bloques)",
                            proceso.getTipo_solicitud(),
                            proceso.getArchivo() != null ? proceso.getArchivo().getNombre() : "Sin archivo",
                            proceso.getArchivo() != null ? proceso.getArchivo().getCantidad_bloq() : 0);
                    procesos.add(infoProceso);
                }
                actual = actual.getSiguiente();
            }
            
            // Actualizar la JList
            colaSolicitudes.setListData(procesos.toArray(new String[0]));
        }
    }
    
    private void actualizarColaTerminadas() {
        if (controlador != null && controlador.getGp() != null) {
            Cola colaTerminados = controlador.getGp().getCola_terminados();
            java.util.List<String> terminados = new java.util.ArrayList<>();
            
            // Recorrer la cola de terminados y agregar a la lista
            Nodo actual = colaTerminados.getCabeza();
            while (actual != null) {
                Proceso proceso = actual.getProceso();
                if (proceso != null) {
                    String infoProceso = String.format("%s - %s (%d bloques) - TERMINADO",
                            proceso.getTipo_solicitud(),
                            proceso.getArchivo() != null ? proceso.getArchivo().getNombre() : "Sin archivo",
                            proceso.getArchivo() != null ? proceso.getArchivo().getCantidad_bloq() : 0);
                    terminados.add(infoProceso);
                }
                actual = actual.getSiguiente();
            }
            
            // Actualizar la JList
            ColaSolicitudesTerminadas.setListData(terminados.toArray(new String[0]));
        }
    }
   

    
    private void actualizarTablaProcesos() {
        String[] columnNames = {"Archivo", "Proceso creador", "Tamaño (bloques)", "Primer bloque", "Color"};
        Object[][] data = {};

        javax.swing.table.DefaultTableModel model = new javax.swing.table.DefaultTableModel(data, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Hacer la tabla no editable
            }

            @Override
            public Class<?> getColumnClass(int columnIndex) {
                // Especificar el tipo de dato para cada columna
                if (columnIndex == 4) { // Columna "Color"
                    return Color.class;
                }
                return Object.class;
            }
        };
        jTable1.setModel(model);

        // Configurar el renderizador para la columna de color
        jTable1.getColumnModel().getColumn(4).setCellRenderer(new ColorCellRenderer());
    }

    // Clase para renderizar la columna de color en jTable1
    class ColorCellRenderer extends DefaultTableCellRenderer {

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

            if (value instanceof Color) {
                Color color = (Color) value;
                c.setBackground(color);
                c.setForeground(getContrastColor(color)); // Texto con contraste
                setText(""); // No mostrar texto, solo el color
                setHorizontalAlignment(CENTER);
            } else {
                c.setBackground(Color.WHITE);
                c.setForeground(Color.BLACK);
                setText(value != null ? value.toString() : "");
            }

            // Resaltar fila seleccionada
            if (isSelected) {
                c.setBackground(c.getBackground().darker());
            }

            return c;
        }

        private Color getContrastColor(Color color) {
            // Calcular el brillo del color de fondo
            double brightness = (color.getRed() * 0.299 + color.getGreen() * 0.587 + color.getBlue() * 0.114) / 255;
            // Usar texto blanco para fondos oscuros, negro para fondos claros
            return brightness > 0.5 ? Color.BLACK : Color.WHITE;
        }
    }

    private void actualizarTablaDiscos() {
        // Configurar la tabla de discos con 10 columnas
        String[] columnNames = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10"};
        Object[][] data = new Object[10][10]; // 100 bloquesss

        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                data[i][j] = "";
            }
        }

        javax.swing.table.DefaultTableModel model = new javax.swing.table.DefaultTableModel(data, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Hacer la tabla no editable
            }
        };
        jTable2.setModel(model);

        // MEJORAR el renderizador de celdas
        jTable2.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

                // Restablecer color por defecto
                c.setBackground(Color.WHITE);
                c.setForeground(Color.BLACK);
                setBorder(javax.swing.BorderFactory.createLineBorder(Color.LIGHT_GRAY));

                // Si la celda tiene contenido, aplicar el color almacenado
                if (value != null && !value.toString().isEmpty()) {
                    Color color = obtenerColorDelBloque(row, column);
                    if (color != null) {
                        c.setBackground(color);
                        c.setForeground(getContrastColor(color));
                        setBorder(javax.swing.BorderFactory.createLineBorder(Color.DARK_GRAY));
                    } else {
                        // DEBUG: Si no encuentra el color, mostrar en rojo para identificar el problema
                        c.setBackground(Color.RED);
                        c.setForeground(Color.WHITE);
                    }
                }

                // Resaltar selección
                if (isSelected) {
                    c.setBackground(c.getBackground().darker());
                }

                setHorizontalAlignment(CENTER);
                return c;
            }

            private Color getContrastColor(Color color) {
                double brightness = (color.getRed() * 0.299 + color.getGreen() * 0.587 + color.getBlue() * 0.114) / 255;
                return brightness > 0.5 ? Color.BLACK : Color.WHITE;
            }
        });
    }

    private void debugColores() {
        System.out.println("=== DEBUG COLORES ===");
        System.out.println("Total de colores almacenados: " + bloqueColors.size());
        for (String clave : bloqueColors.keySet()) {
            Color color = bloqueColors.get(clave);
            System.out.println("Bloque " + clave + " -> Color: " + color);
        }
        System.out.println("=====================");
    }

    private Color obtenerColorDelBloque(int fila, int columna) {
        String claveBloque = fila + "," + columna;
        return bloqueColors.get(claveBloque);
    }

    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(View.class.getName());

    /**
     * Creates new form View
     */
    public View() {
        initComponents();
        this.setLocationRelativeTo(null);

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        editView = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        modeButton = new javax.swing.JToggleButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTree = new javax.swing.JTree();
        detailsLabel = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        editarButton = new javax.swing.JButton();
        eliminarButton = new javax.swing.JButton();
        jScrollPane4 = new javax.swing.JScrollPane();
        colaSolicitudes = new javax.swing.JList<>();
        jScrollPane5 = new javax.swing.JScrollPane();
        ColaSolicitudesTerminadas = new javax.swing.JList<>();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        planPolicy = new javax.swing.JComboBox<>();
        jLabel3 = new javax.swing.JLabel();
        clockLabel = new javax.swing.JLabel();
        crearButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        modeButton.setText("Modo Administrador");
        modeButton.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                modeButtonItemStateChanged(evt);
            }
        });
        modeButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                modeButtonActionPerformed(evt);
            }
        });

        jTree.addTreeSelectionListener(new javax.swing.event.TreeSelectionListener() {
            public void valueChanged(javax.swing.event.TreeSelectionEvent evt) {
                jTreeValueChanged(evt);
            }
        });
        jScrollPane1.setViewportView(jTree);

        detailsLabel.setText("Detalles directorio/archivo");
        detailsLabel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 0, 0)));

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Archivo", "Proceso creador", "Tamaño (bloques)", "Primer bloque", "Color"
            }
        ));
        jScrollPane2.setViewportView(jTable1);

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "1", "2", "3", "4", "5", "6", "7", "8", "9", "10"
            }
        ));
        jScrollPane3.setViewportView(jTable2);

        editarButton.setText("Editar");
        editarButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editarButtonActionPerformed(evt);
            }
        });

        eliminarButton.setText("Eliminar");
        eliminarButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                eliminarButtonActionPerformed(evt);
            }
        });

        colaSolicitudes.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jScrollPane4.setViewportView(colaSolicitudes);

        ColaSolicitudesTerminadas.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jScrollPane5.setViewportView(ColaSolicitudesTerminadas);

        jLabel1.setText("Cola de solicitudes (E/S)");

        jLabel2.setText("Cola de solicitudes terminadas");

        planPolicy.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "FIFO", "LIFO", "LOOK", "C-LOOK" }));

        jLabel3.setText("Política de planificación:");

        clockLabel.setText("jLabel4");

        crearButton.setText("Crear");
        crearButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                crearButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(modeButton)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(crearButton)
                                .addGap(18, 18, 18))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(planPolicy, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(92, 92, 92)))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(editarButton)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(eliminarButton))
                            .addComponent(clockLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addComponent(detailsLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 215, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane2)
                    .addComponent(jScrollPane3)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel1))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 221, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(25, 25, 25))
        );

        jPanel1Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {editarButton, eliminarButton});

        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(25, 25, 25)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(detailsLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(modeButton)
                                    .addComponent(eliminarButton)
                                    .addComponent(editarButton)
                                    .addComponent(crearButton))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(planPolicy, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel3)
                                    .addComponent(clockLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(jLabel2))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 125, Short.MAX_VALUE)
                            .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 185, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap(22, Short.MAX_VALUE)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 600, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );

        editView.addTab("Simulación", jPanel1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(editView, javax.swing.GroupLayout.PREFERRED_SIZE, 1000, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(editView, javax.swing.GroupLayout.PREFERRED_SIZE, 650, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void crearButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_crearButtonActionPerformed
        // TODO add your handling code here:
        int resultado = JOptionPane.showConfirmDialog(
            null,
            panel,
            "Crear nuevo Archivo/Directorio",
            JOptionPane.OK_CANCEL_OPTION,
            JOptionPane.PLAIN_MESSAGE
        );

        // Si el usuario cancela, no hacer nada
        if (resultado != JOptionPane.OK_OPTION) {
            return;
        }

        String nombre = nombreField.getText();
        String tipo = directorioButton.isSelected() ? "Directorio" : "Archivo";
        int bloques = (int) bloquesSpinner.getValue();
        String privacidad = (String) privacidadCombo.getSelectedItem();

        String creador = isAdmin ? "Modo Administrador" : "Modo Usuario";

        // Validar que el nombre no esté vacío
        if (nombre.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "El nombre no puede estar vacío.", "Error de Entrada", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Obtener el nodo seleccionado en el árbol (será el padre)
        DefaultMutableTreeNode nodoPadre = getSelectedNodeOrRoot();

        // Verificar que si el padre es un archivo, no se puedan agregar hijos
        if (nodoPadre.getUserObject() instanceof Archivo) {
            JOptionPane.showMessageDialog(null, "No se pueden agregar elementos dentro de un archivo.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int bloquesRequeridos = tipo.equals("Archivo") ? bloques : 1;
        int primerBloque = encontrarPrimerBloqueDisponible(bloquesRequeridos);

        if (primerBloque == -1) {
            JOptionPane.showMessageDialog(this,
                "No hay espacio suficiente en el disco para crear '" + nombre + "'.",
                "Espacio insuficiente",
                JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Crear el objeto y el nodo del árbol
        Object nuevoObjeto;
        DefaultMutableTreeNode nuevoNodo;
        Proceso nuevoProceso;

        java.awt.Color color = generarColorUnico();

        if (tipo.equals("Archivo")) {
            // Crear archivo - los archivos son hojas (no pueden tener hijos)
            Archivo nuevoArchivo = new Archivo(nombre, bloques, privacidad, creador);
            nuevoObjeto = nuevoArchivo;
            nuevoNodo = new DefaultMutableTreeNode(nuevoArchivo);
            // Los archivos son hojas por definición

            // Agregar a Proceso
            nuevoProceso = new Proceso("Crear", nuevoArchivo);
            this.getControlador().getGp().getCola_procesos().add_nodo(nuevoProceso);

            // Agregar a la tabla de procesos
            agregarProcesoATabla(nuevoArchivo, nuevoProceso, bloques, primerBloque, color, creador);

            // Mostrar en la tabla de discos
            mostrarEnTablaDiscos(nuevoArchivo, bloques, primerBloque, color);

            actualizarListasProcesos();

        } else {
            // Crear directorio - los directorios pueden tener hijos
            Directorio nuevoDirectorio = new Directorio(nombre, creador);
            nuevoObjeto = nuevoDirectorio;
            nuevoNodo = new DefaultMutableTreeNode(nuevoDirectorio);
            // Los directorios permiten hijos por defecto

            nuevoProceso = new Proceso("Crear", nuevoDirectorio);
            this.getControlador().getGp().getCola_procesos().add_nodo(nuevoProceso);

            // Agregar a la tabla de procesos
            agregarProcesoATabla(nuevoDirectorio, nuevoProceso, 1, primerBloque, color, creador);

            // Mostrar en la tabla de discos
            mostrarEnTablaDiscos(nuevoDirectorio, 1, primerBloque, color);

            actualizarListasProcesos();

        }

        // Agregar el nodo al árbol
        treeModel.insertNodeInto(nuevoNodo, nodoPadre, nodoPadre.getChildCount());

        jTree.expandPath(new TreePath(nodoPadre.getPath()));

        // Limpiar campos después de crear
        nombreField.setText("");
        bloquesSpinner.setValue(1);
        privacidadCombo.setSelectedIndex(0);

        // Forzar actualización del árbol
        treeModel.reload(nodoPadre);
    }//GEN-LAST:event_crearButtonActionPerformed

    private void eliminarButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_eliminarButtonActionPerformed
        DefaultMutableTreeNode nodoSeleccionado = (DefaultMutableTreeNode) jTree.getLastSelectedPathComponent();

        if (nodoSeleccionado == null) {
            JOptionPane.showMessageDialog(this, "Por favor, seleccione un elemento para eliminar.", "Sin selección", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // No permitir eliminar la raíz
        if (nodoSeleccionado == rootNode) {
            JOptionPane.showMessageDialog(this, "No se puede eliminar el directorio raíz.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Object objeto = nodoSeleccionado.getUserObject();

        // VERIFICAR PERMISOS EN MODO USUARIO
        if (!isAdmin) {
            String creador = obtenerCreadorDeObjeto(objeto);
            if ("Modo Administrador".equals(creador)) {
                JOptionPane.showMessageDialog(this,
                    "No tiene permisos para eliminar elementos creados por el administrador.",
                    "Permiso denegado",
                    JOptionPane.WARNING_MESSAGE);
                return;
            }
        }

        String nombre = obtenerNombreDeObjeto(objeto);

        int confirmacion = JOptionPane.showConfirmDialog(
            this,
            "¿Está seguro de que desea eliminar '" + nombre + "'?",
            "Confirmar eliminación",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE
        );

        if (confirmacion == JOptionPane.YES_OPTION) {
            // Eliminar del árbol
            treeModel.removeNodeFromParent(nodoSeleccionado);

            eliminarDeTablas(nombre);

            // Agregar proceso de eliminación a la cola
            if (objeto instanceof Archivo) {
                Proceso procesoEliminar = new Proceso("Eliminar", (Archivo) objeto);
                
                this.getControlador().getGp().getCola_procesos().add_nodo(procesoEliminar);
                        actualizarListasProcesos();

            } else if (objeto instanceof Directorio) {
                Proceso procesoEliminar = new Proceso("Eliminar", (Directorio) objeto);
                this.getControlador().getGp().getCola_procesos().add_nodo(procesoEliminar);
                    actualizarListasProcesos();

            }
        }
    }//GEN-LAST:event_eliminarButtonActionPerformed

    private void editarButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editarButtonActionPerformed
        DefaultMutableTreeNode nodoSeleccionado = (DefaultMutableTreeNode) jTree.getLastSelectedPathComponent();

        if (nodoSeleccionado == null) {
            JOptionPane.showMessageDialog(this, "Por favor, seleccione un elemento para editar.", "Sin selección", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Object objeto = nodoSeleccionado.getUserObject();

        // VERIFICAR PERMISOS EN MODO USUARIO
        if (!isAdmin) {
            String creador = obtenerCreadorDeObjeto(objeto);
            if ("admin".equals(creador)) {
                JOptionPane.showMessageDialog(this,
                    "No tiene permisos para editar elementos creados por el administrador.",
                    "Permiso denegado",
                    JOptionPane.WARNING_MESSAGE);
                return;
            }
        }

        String nombreActual = obtenerNombreDeObjeto(objeto);

        // Crear panel de edición
        JPanel panelEdicion = new JPanel(new GridLayout(2, 2, 5, 5));
        JTextField nombreFieldEditar = new JTextField(nombreActual, 15);

        panelEdicion.add(new JLabel("Nombre:"));
        panelEdicion.add(nombreFieldEditar);

        // Variables para archivos
        JSpinner bloquesSpinnerEditar = null;
        JComboBox<String> privacidadComboEditar = null;

        // Si es archivo, mostrar bloques y privacidad
        if (objeto instanceof Archivo) {
            Archivo archivo = (Archivo) objeto;
            bloquesSpinnerEditar = new JSpinner(new SpinnerNumberModel(archivo.getCantidad_bloq(), 1, 100, 1));
            privacidadComboEditar = new JComboBox<>(new String[]{"Público", "Privado"});
            privacidadComboEditar.setSelectedItem(archivo.getPrivacidad());

            panelEdicion.add(new JLabel("# Bloques:"));
            panelEdicion.add(bloquesSpinnerEditar);
            panelEdicion.add(new JLabel("Privacidad:"));
            panelEdicion.add(privacidadComboEditar);
        }

        int resultado = JOptionPane.showConfirmDialog(
            null,
            panelEdicion,
            "Editar Elemento",
            JOptionPane.OK_CANCEL_OPTION,
            JOptionPane.PLAIN_MESSAGE
        );

        if (resultado == JOptionPane.OK_OPTION) {
            String nuevoNombre = nombreFieldEditar.getText().trim();
            if (nuevoNombre.isEmpty()) {
                JOptionPane.showMessageDialog(this, "El nombre no puede estar vacío.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Guardar el nombre antiguo para buscar en la tabla
            String nombreAntiguo = obtenerNombreDeObjeto(objeto);

            // Actualizar el objeto
            if (objeto instanceof Archivo) {
                Archivo archivo = (Archivo) objeto;
                archivo.setNombre(nuevoNombre);
                if (bloquesSpinnerEditar != null) {
                    int nuevosBloques = (int) bloquesSpinnerEditar.getValue();
                    archivo.setCantidad_bloq(nuevosBloques);
                    actualizarListasProcesos();

                }
                if (privacidadComboEditar != null) {
                    archivo.setPrivacidad((String) privacidadComboEditar.getSelectedItem());
                }
            } else if (objeto instanceof Directorio) {
                Directorio directorio = (Directorio) objeto;
                directorio.setNombre(nuevoNombre);
                actualizarListasProcesos();

            }

            // ACTUALIZAR LA TABLA DE PROCESOS
            actualizarProcesoEnTabla(nombreAntiguo, nuevoNombre, objeto);

            // Notificar al tree model que el nodo ha cambiado
            treeModel.nodeChanged(nodoSeleccionado);

            // Agregar proceso de edición a la cola
            if (objeto instanceof Archivo) {
                Proceso procesoEditar = new Proceso("Editar", (Archivo) objeto);
                this.getControlador().getGp().getCola_procesos().add_nodo(procesoEditar);
            } else if (objeto instanceof Directorio) {
                Proceso procesoEditar = new Proceso("Editar", (Directorio) objeto);
                this.getControlador().getGp().getCola_procesos().add_nodo(procesoEditar);
            }
        }
    }//GEN-LAST:event_editarButtonActionPerformed

    private void jTreeValueChanged(javax.swing.event.TreeSelectionEvent evt) {//GEN-FIRST:event_jTreeValueChanged
        // TODO add your handling code here:
        DefaultMutableTreeNode nodoSeleccionado = (DefaultMutableTreeNode) jTree.getLastSelectedPathComponent();

        if (nodoSeleccionado != null) {
            Object datosNodo = nodoSeleccionado.getUserObject();
            String detalles = "";

            // EN MODO USUARIO: Verificar permisos de visualización
            if (!isAdmin) {
                String creador = obtenerCreadorDeObjeto(datosNodo);
                String privacidad = "";

                if (datosNodo instanceof Archivo) {
                    privacidad = ((Archivo) datosNodo).getPrivacidad();
                }

                // Si es privado y no fue creado por el usuario, no mostrar detalles
                if ("admin".equals(creador) && "Privado".equals(privacidad)) {
                    detalles = "Acceso denegado: Archivo privado del administrador";
                    detailsLabel.setText("<html>" + detalles + "</html>");
                    return;
                }
            }

            if (datosNodo instanceof Archivo) {
                Archivo archivo = (Archivo) datosNodo;
                detalles = String.format("Archivo: %s\nTamaño: %d bloques\nPrivacidad: %s\nCreador: %s",
                    archivo.getNombre(), archivo.getCantidad_bloq(), archivo.getPrivacidad(), archivo.getCreador());
            } else if (datosNodo instanceof Directorio) {
                Directorio directorio = (Directorio) datosNodo;
                detalles = String.format("Directorio: %s\nElementos: %d\nCreador: %s",
                    directorio.getNombre(), nodoSeleccionado.getChildCount(), directorio.getCreador());
            } else if (datosNodo instanceof String) {
                detalles = "Directorio Raíz: " + datosNodo;
            }

            detailsLabel.setText("<html>" + detalles.replace("\n", "<br>") + "</html>");
            System.out.println("Nodo seleccionado: " + datosNodo);
        }
    }//GEN-LAST:event_jTreeValueChanged

    private void modeButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_modeButtonActionPerformed
        // TODO add your handling code here:
        if (modeButton.isSelected()) {
            modeButton.setText("Modo Usuario");
            isAdmin = false;
        } else {
            modeButton.setText("Modo Administrador");
            isAdmin = true;
        }
    }//GEN-LAST:event_modeButtonActionPerformed

    private void modeButtonItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_modeButtonItemStateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_modeButtonItemStateChanged

    private void actualizarProcesoEnTabla(String nombreAntiguo, String nuevoNombre, Object objeto) {
        javax.swing.table.DefaultTableModel model = (javax.swing.table.DefaultTableModel) jTable1.getModel();

        // Buscar la fila que corresponde al elemento editado
        for (int i = 0; i < model.getRowCount(); i++) {
            String nombreEnTabla = (String) model.getValueAt(i, 0);
            String procesoCreador = (String) model.getValueAt(i, 1);

            // Comparar con el nombre antiguo (considerando que los directorios tienen "(Dir)")
            boolean coincide = false;
            if (objeto instanceof Archivo) {
                coincide = nombreEnTabla.equals(nombreAntiguo);
            } else if (objeto instanceof Directorio) {
                coincide = nombreEnTabla.equals(nombreAntiguo + " (Dir)")
                        || nombreEnTabla.equals(nombreAntiguo);
            }

            if (coincide) {
                // Actualizar el nombre en la tabla
                if (objeto instanceof Archivo) {
                    model.setValueAt(nuevoNombre, i, 0);
                } else if (objeto instanceof Directorio) {
                    model.setValueAt(nuevoNombre + " (Dir)", i, 0);
                }

                // Si es archivo, actualizar también el tamaño de bloques
                if (objeto instanceof Archivo) {
                    Archivo archivo = (Archivo) objeto;
                    model.setValueAt(archivo.getCantidad_bloq(), i, 2);
                }

                break;
            }
        }

        // Actualizar también la tabla de discos si es un archivo y cambió el tamaño
        if (objeto instanceof Archivo) {
            actualizarTablaDiscosDespuesEdicion((Archivo) objeto, nombreAntiguo);
        }

    }

    private void actualizarTablaDiscosDespuesEdicion(Archivo archivo, String nombreAntiguo) {
        javax.swing.table.DefaultTableModel model = (javax.swing.table.DefaultTableModel) jTable2.getModel();
        String nuevoNombre = archivo.getNombre();

        // Buscar en la tabla de procesos el primer bloque de este archivo
        int primerBloque = -1;
        int tamañoActual = -1;
        javax.swing.table.DefaultTableModel modelProcesos = (javax.swing.table.DefaultTableModel) jTable1.getModel();

        for (int i = 0; i < modelProcesos.getRowCount(); i++) {
            String nombreEnTabla = (String) modelProcesos.getValueAt(i, 0);
            if (nombreEnTabla.equals(nuevoNombre)) {
                primerBloque = (Integer) modelProcesos.getValueAt(i, 3);
                tamañoActual = (Integer) modelProcesos.getValueAt(i, 2);
                break;
            }
        }

        if (primerBloque != -1 && tamañoActual != -1) {
            // Actualizar el nombre en los bloques ocupados
            for (int i = 0; i < tamañoActual; i++) {
                int bloqueGlobal = primerBloque + i;
                if (bloqueGlobal < 100) {
                    int fila = bloqueGlobal / 10;
                    int columna = bloqueGlobal % 10;

                    // Actualizar la abreviatura del nombre
                    String abreviatura = nuevoNombre.substring(0, Math.min(nuevoNombre.length(), 3));
                    model.setValueAt(abreviatura, fila, columna);
                }
            }

            jTable2.repaint();
        }
    }

    private String obtenerCreadorDeObjeto(Object objeto) {
        if (objeto instanceof Archivo) {
            return ((Archivo) objeto).getCreador();
        } else if (objeto instanceof Directorio) {
            return ((Directorio) objeto).getCreador();
        }
        return "Modo Administrador"; // Por defecto, asumir admin para nodos del sistema
    }

    private void eliminarDeTablas(String nombre) {
        // Eliminar de la tabla de procesos
        javax.swing.table.DefaultTableModel modelProcesos = (javax.swing.table.DefaultTableModel) jTable1.getModel();
        for (int i = modelProcesos.getRowCount() - 1; i >= 0; i--) {
            String nombreEnTabla = (String) modelProcesos.getValueAt(i, 0);

            // Buscar coincidencia exacta o con "(Dir)"
            boolean coincide = nombreEnTabla.equals(nombre)
                    || nombreEnTabla.equals(nombre + " (Dir)")
                    || (nombreEnTabla.endsWith(" (Dir)")
                    && nombreEnTabla.substring(0, nombreEnTabla.length() - 6).equals(nombre));

            if (coincide) {
                // Antes de eliminar, liberar los bloques en la tabla de discos
                Integer primerBloque = (Integer) modelProcesos.getValueAt(i, 3);
                Integer tamaño = (Integer) modelProcesos.getValueAt(i, 2);
                if (primerBloque != null && tamaño != null) {
                    liberarBloques(primerBloque, tamaño);
                }
                modelProcesos.removeRow(i);
                break;
            }
        }

        jTable2.repaint();
    }

    private void liberarBloques(int primerBloque, int tamaño) {
        javax.swing.table.DefaultTableModel model = (javax.swing.table.DefaultTableModel) jTable2.getModel();

        for (int i = 0; i < tamaño; i++) {
            int bloqueGlobal = primerBloque + i;
            if (bloqueGlobal < 100) {
                int fila = bloqueGlobal / 10;
                int columna = bloqueGlobal % 10;

                // Limpiar la celda
                model.setValueAt("", fila, columna);

                // Eliminar el color del mapa
                String claveBloque = fila + "," + columna;
                bloqueColors.remove(claveBloque);
            }
        }
    }

    private String obtenerNombreDeObjeto(Object objeto) {
        if (objeto instanceof Archivo) {
            return ((Archivo) objeto).getNombre();
        } else if (objeto instanceof Directorio) {
            return ((Directorio) objeto).getNombre();
        } else if (objeto instanceof String) {
            return (String) objeto;
        }
        return "Desconocido";
    }

    private void agregarProcesoATabla(Object objeto, Proceso proceso, int bloques, int primerBloque, java.awt.Color color, String creador) {
        javax.swing.table.DefaultTableModel model = (javax.swing.table.DefaultTableModel) jTable1.getModel();

        String nombreArchivo = "";
        String procesoCreador = proceso.getTipo() + " (" + creador + ")"; // Incluir info del creador        int tamañoBloques = bloques;
        int tamañoBloques = bloques;

        if (objeto instanceof Archivo) {
            nombreArchivo = ((Archivo) objeto).getNombre();
        } else if (objeto instanceof Directorio) {
            nombreArchivo = ((Directorio) objeto).getNombre() + " (Dir)";
        }

        // Agregar fila a la tabla
        model.addRow(new Object[]{
            nombreArchivo,
            procesoCreador,
            tamañoBloques,
            primerBloque,
            color // Usar el mismo color que se usará en los bloques
        });
    }

    private void mostrarEnTablaDiscos(Object objeto, int bloques, int primerBloque, java.awt.Color color) {
        javax.swing.table.DefaultTableModel model = (javax.swing.table.DefaultTableModel) jTable2.getModel();

        String nombre = "";
        if (objeto instanceof Archivo) {
            nombre = ((Archivo) objeto).getNombre();
        } else if (objeto instanceof Directorio) {
            nombre = ((Directorio) objeto).getNombre();
        }

        // Ocupar los bloques consecutivos en la tabla de discos
        for (int i = 0; i < bloques; i++) {
            int bloqueGlobal = primerBloque + i;
            if (bloqueGlobal < 100) { // Dentro del rango de 100 bloques
                int fila = bloqueGlobal / 10;     // Fila: división entera
                int columna = bloqueGlobal % 10;  // Columna: resto

                // Usar abreviatura del nombre
                String abreviatura = nombre.substring(0, Math.min(nombre.length(), 3));
                model.setValueAt(abreviatura, fila, columna);

                // Almacenar el color para este bloque (USAR EL MISMO COLOR)
                String claveBloque = fila + "," + columna;
                bloqueColors.put(claveBloque, color);
            }
        }

        // DEBUG: Verificar colores
        debugColores();

        // Actualizar la tabla
        jTable2.repaint();
    }

    private int encontrarPrimerBloqueDisponible(int bloquesNecesarios) {
        // Si no se necesitan bloques (directorio), retornar -1 o manejar diferente
        if (bloquesNecesarios <= 0) {
            return 0;
        }

        // Buscar bloques consecutivos disponibles
        for (int inicio = 0; inicio <= 100 - bloquesNecesarios; inicio++) {
            boolean disponible = true;

            // Verificar si los bloques consecutivos están disponibles
            for (int i = 0; i < bloquesNecesarios; i++) {
                if (!estaBloqueDisponible(inicio + i)) {
                    disponible = false;
                    break;
                }
            }

            if (disponible) {
                return inicio;
            }
        }

        // Si no hay espacio consecutivo
        return -1;
    }

    private boolean estaBloqueDisponible(int bloqueGlobal) {
        if (bloqueGlobal < 0 || bloqueGlobal >= 100) {
            return false; // Fuera de rango
        }

        javax.swing.table.DefaultTableModel model = (javax.swing.table.DefaultTableModel) jTable2.getModel();
        int fila = bloqueGlobal / 10;
        int columna = bloqueGlobal % 10;

        Object valor = model.getValueAt(fila, columna);
        return valor == null || valor.toString().isEmpty();
    }

    private int encontrarFilaDisponible() {
        javax.swing.table.DefaultTableModel model = (javax.swing.table.DefaultTableModel) jTable2.getModel();
        for (int fila = 0; fila < model.getRowCount(); fila++) {
            boolean filaVacia = true;
            for (int col = 0; col < model.getColumnCount(); col++) {
                Object valor = model.getValueAt(fila, col);
                if (valor != null && !valor.toString().isEmpty()) {
                    filaVacia = false;
                    break;
                }
            }
            if (filaVacia) {
                return fila;
            }
        }
        return 0; // Fallback a la primera fila
    }

    private java.awt.Color generarColorUnico() {
        java.util.Random rand = new java.util.Random();

        // Generar colores más distintos entre sí
        float hue = rand.nextFloat(); // Tono (0-1)
        float saturation = 0.7f + rand.nextFloat() * 0.3f; // Saturación (0.7-1.0)
        float brightness = 0.8f + rand.nextFloat() * 0.2f; // Brillo (0.8-1.0)

        return Color.getHSBColor(hue, saturation, brightness);
    }

    private boolean permiteHijos(DefaultMutableTreeNode node) {
        if (node == null) {
            return false;
        }

        Object userObject = node.getUserObject();
        // Los directorios permiten hijos, los archivos no
        return userObject instanceof Directorio || userObject instanceof String;
    }

    private DefaultMutableTreeNode getSelectedNodeOrRoot() {
        DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) jTree.getLastSelectedPathComponent();
        if (selectedNode == null) {
            // Si no hay nodo seleccionado, usar la raíz
            return rootNode;
        }
        return selectedNode;
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ReflectiveOperationException | javax.swing.UnsupportedLookAndFeelException ex) {
            logger.log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> new View().setVisible(true));
    }

    public boolean isIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(boolean isAdmin) {
        this.isAdmin = isAdmin;
    }

    public Controlador getControlador() {
        return controlador;
    }

    public void setControlador(Controlador controlador) {
        this.controlador = controlador;
    }

    public JLabel getDetailsLabel() {
        return detailsLabel;
    }

    public void setDetailsLabel(JLabel detailsLabel) {
        this.detailsLabel = detailsLabel;
    }

    public JButton getEditarButton() {
        return editarButton;
    }

    public void setEditarButton(JButton editarButton) {
        this.editarButton = editarButton;
    }

    public JButton getEliminarButton() {
        return eliminarButton;
    }

    public void setEliminarButton(JButton eliminarButton) {
        this.eliminarButton = eliminarButton;
    }

    public JPanel getjPanel1() {
        return jPanel1;
    }

    public void setjPanel1(JPanel jPanel1) {
        this.jPanel1 = jPanel1;
    }

    public JScrollPane getjScrollPane1() {
        return jScrollPane1;
    }

    public void setjScrollPane1(JScrollPane jScrollPane1) {
        this.jScrollPane1 = jScrollPane1;
    }

    public JScrollPane getjScrollPane2() {
        return jScrollPane2;
    }

    public void setjScrollPane2(JScrollPane jScrollPane2) {
        this.jScrollPane2 = jScrollPane2;
    }

    public JScrollPane getjScrollPane3() {
        return jScrollPane3;
    }

    public void setjScrollPane3(JScrollPane jScrollPane3) {
        this.jScrollPane3 = jScrollPane3;
    }

    public JScrollPane getjScrollPane4() {
        return jScrollPane4;
    }

    public void setjScrollPane4(JScrollPane jScrollPane4) {
        this.jScrollPane4 = jScrollPane4;
    }

    public JScrollPane getjScrollPane5() {
        return jScrollPane5;
    }

    public void setjScrollPane5(JScrollPane jScrollPane5) {
        this.jScrollPane5 = jScrollPane5;
    }

    public JTabbedPane getjTabbedPane1() {
        return editView;
    }

    public void setjTabbedPane1(JTabbedPane jTabbedPane1) {
        this.editView = jTabbedPane1;
    }

    public JTable getjTable1() {
        return jTable1;
    }

    public void setjTable1(JTable jTable1) {
        this.jTable1 = jTable1;
    }

    public JTable getjTable2() {
        return jTable2;
    }

    public void setjTable2(JTable jTable2) {
        this.jTable2 = jTable2;
    }

    public JTree getjTree() {
        return jTree;
    }

    public void setjTree(JTree jTree) {
        this.jTree = jTree;
    }

    public JToggleButton getModeButton() {
        return modeButton;
    }

    public void setModeButton(JToggleButton modeButton) {
        this.modeButton = modeButton;
    }

    public JComboBox<String> getPlanPolicy() {
        return planPolicy;
    }

    public void setPlanPolicy(JComboBox<String> planPolicy) {
        this.planPolicy = planPolicy;
    }

    public JLabel getClockLabel() {
        return clockLabel;
    }

    public void setClockLabel(JLabel clockLabel) {
        this.clockLabel = clockLabel;
    }

    public JList<String> getColaSolicitudesTerminadas() {
        return ColaSolicitudesTerminadas;
    }

    public void setColaSolicitudesTerminadas(JList<String> ColaSolicitudesTerminadas) {
        this.ColaSolicitudesTerminadas = ColaSolicitudesTerminadas;
    }

    public JList<String> getColaSolicitudes() {
        return colaSolicitudes;
    }

    public void setColaSolicitudes(JList<String> colaSolicitudes) {
        this.colaSolicitudes = colaSolicitudes;
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JList<String> ColaSolicitudesTerminadas;
    private javax.swing.JLabel clockLabel;
    private javax.swing.JList<String> colaSolicitudes;
    private javax.swing.JButton crearButton;
    private javax.swing.JLabel detailsLabel;
    private javax.swing.JTabbedPane editView;
    private javax.swing.JButton editarButton;
    private javax.swing.JButton eliminarButton;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable2;
    private javax.swing.JTree jTree;
    private javax.swing.JToggleButton modeButton;
    private javax.swing.JComboBox<String> planPolicy;
    // End of variables declaration//GEN-END:variables
}
