
package interfaces;

import Clases.Archivo;
import Clases.Directorio;
import Clases.Proceso;
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

    public View(Controlador controlador) {
        this.controlador = controlador;
        
        initComponents();
        
        rootNode = new DefaultMutableTreeNode("/");
        treeModel = new DefaultTreeModel(rootNode);
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
        jTree.setModel(treeModel);
        
        Directorio dirPrincipal = new Directorio("/");
        
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
        jPanel2 = new javax.swing.JPanel();

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

        planPolicy.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "FIFO", "SSTF", "SCAN", "C-SCAN" }));

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

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 922, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 650, Short.MAX_VALUE)
        );

        editView.addTab("Métricas", jPanel2);

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

    private void modeButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_modeButtonActionPerformed
        // TODO add your handling code here:
        if (modeButton.isSelected()){
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

    private void jTreeValueChanged(javax.swing.event.TreeSelectionEvent evt) {//GEN-FIRST:event_jTreeValueChanged
        // TODO add your handling code here:
            DefaultMutableTreeNode nodoSeleccionado = (DefaultMutableTreeNode) jTree.getLastSelectedPathComponent();

            if (nodoSeleccionado != null) {
                Object datosNodo = nodoSeleccionado.getUserObject();
                String detalles = "";

                if (datosNodo instanceof Archivo) {
                    Archivo archivo = (Archivo) datosNodo;
                    detalles = String.format("Archivo: %s\nTamaño: %d bloques\nPrivacidad: %s", 
                        archivo.getNombre(), archivo.getCantidad_bloq(), archivo.getPrivacidad());
                } else if (datosNodo instanceof Directorio) {
                    Directorio directorio = (Directorio) datosNodo;
                    detalles = String.format("Directorio: %s\nElementos: %d", 
                        directorio.getNombre(), nodoSeleccionado.getChildCount());
                } else if (datosNodo instanceof String) {
                    detalles = "Directorio Raíz: " + datosNodo;
                }

                detailsLabel.setText("<html>" + detalles.replace("\n", "<br>") + "</html>");
                System.out.println("Nodo seleccionado: " + datosNodo);
            }  
    }//GEN-LAST:event_jTreeValueChanged

    private void editarButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editarButtonActionPerformed
        // TODO add your handling code here:
        DefaultMutableTreeNode nodoSeleccionado = (DefaultMutableTreeNode) jTree.getLastSelectedPathComponent();
    
        if (nodoSeleccionado == null) {
            JOptionPane.showMessageDialog(this, "Por favor, seleccione un elemento para editar.", "Sin selección", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Object objeto = nodoSeleccionado.getUserObject();
        String nombreActual = obtenerNombreDeObjeto(objeto);

        // Crear panel de edición
        JPanel panelEdicion = new JPanel(new GridLayout(2, 2, 5, 5));
        JTextField nombreFieldEditar = new JTextField(nombreActual, 15);

        panelEdicion.add(new JLabel("Nombre:"));
        panelEdicion.add(nombreFieldEditar);

        // Si es archivo, mostrar bloques
        if (objeto instanceof Archivo) {
            Archivo archivo = (Archivo) objeto;
            JSpinner bloquesSpinnerEditar = new JSpinner(new SpinnerNumberModel(archivo.getCantidad_bloq(), 1, 100, 1));
            JComboBox<String> privacidadComboEditar = new JComboBox<>(new String[]{"Público", "Privado"});
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

            // Actualizar el objeto
            if (objeto instanceof Archivo) {
                Archivo archivo = (Archivo) objeto;
                archivo.setNombre(nuevoNombre);
                // Actualizar otros atributos si es necesario
            } else if (objeto instanceof Directorio) {
                Directorio directorio = (Directorio) objeto;
                directorio.setNombre(nuevoNombre);
            }

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

    private void eliminarButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_eliminarButtonActionPerformed
        // TODO add your handling code here:
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

        // Confirmar eliminación
        Object objeto = nodoSeleccionado.getUserObject();
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

            // Aquí también deberías eliminar el objeto de tu modelo de datos
            // y agregar un proceso de eliminación a la cola
            if (objeto instanceof Archivo) {
                Proceso procesoEliminar = new Proceso("Eliminar", (Archivo) objeto);
                this.getControlador().getGp().getCola_procesos().add_nodo(procesoEliminar);
            } else if (objeto instanceof Directorio) {
                Proceso procesoEliminar = new Proceso("Eliminar", (Directorio) objeto);
                this.getControlador().getGp().getCola_procesos().add_nodo(procesoEliminar);
            }
        }
    }//GEN-LAST:event_eliminarButtonActionPerformed

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

            // Crear el objeto y el nodo del árbol
            Object nuevoObjeto;
            DefaultMutableTreeNode nuevoNodo;

            if (tipo.equals("Archivo")) {
                // Crear archivo - los archivos son hojas (no pueden tener hijos)
                Archivo nuevoArchivo = new Archivo(nombre, bloques, privacidad);
                nuevoObjeto = nuevoArchivo;
                nuevoNodo = new DefaultMutableTreeNode(nuevoArchivo);
                // Los archivos son hojas por definición

                // Agregar a Proceso
                Proceso nuevoProceso = new Proceso("Crear", nuevoArchivo);
                this.getControlador().getGp().getCola_procesos().add_nodo(nuevoProceso);
            } else {
                // Crear directorio - los directorios pueden tener hijos
                Directorio nuevoDirectorio = new Directorio(nombre);
                nuevoObjeto = nuevoDirectorio;
                nuevoNodo = new DefaultMutableTreeNode(nuevoDirectorio);
                // Los directorios permiten hijos por defecto

                Proceso nuevoProceso = new Proceso("Crear", nuevoDirectorio);
                this.getControlador().getGp().getCola_procesos().add_nodo(nuevoProceso);
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
    
    private boolean permiteHijos(DefaultMutableTreeNode node) {
        if (node == null) return false;

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

    public JPanel getjPanel2() {
        return jPanel2;
    }

    public void setjPanel2(JPanel jPanel2) {
        this.jPanel2 = jPanel2;
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
    private javax.swing.JPanel jPanel2;
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
