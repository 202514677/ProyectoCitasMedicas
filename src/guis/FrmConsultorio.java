package guis;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import clases.*;
import arrays.*; 
import java.awt.event.*;

public class FrmConsultorio extends JFrame implements ActionListener {
    private JTextField txtNom, txtPis;
    private JButton btnAdicionar, btnModificar, btnEliminar;
    private JTable tabla;
    private DefaultTableModel modelo;
    

    private arrayConsultorios acon = new arrayConsultorios();
    private arrayCitas acit = new arrayCitas();

    public FrmConsultorio() {
        setTitle("Mantenimiento de Consultorios");
        setSize(550, 450); setLayout(null);
        setLocationRelativeTo(null);

        add(new JLabel("Nombre:")).setBounds(10, 10, 80, 20);
        txtNom = new JTextField(); txtNom.setBounds(100, 10, 150, 20); add(txtNom);
        
        add(new JLabel("Piso:")).setBounds(10, 40, 80, 20);
        txtPis = new JTextField(); txtPis.setBounds(100, 40, 50, 20); add(txtPis);
        
        btnAdicionar = new JButton("Grabar"); 
        btnAdicionar.setBounds(300, 10, 120, 25);
        btnAdicionar.addActionListener(this); add(btnAdicionar);

        btnModificar = new JButton("Modificar"); 
        btnModificar.setBounds(300, 40, 120, 25);
        btnModificar.addActionListener(this); add(btnModificar);

        btnEliminar = new JButton("Eliminar"); 
        btnEliminar.setBounds(300, 70, 120, 25);
        btnEliminar.addActionListener(this); add(btnEliminar);

        modelo = new DefaultTableModel(new String[]{"ID", "Nombre", "Piso", "Estado"}, 0);
        tabla = new JTable(modelo);
        JScrollPane sp = new JScrollPane(tabla); sp.setBounds(10, 110, 510, 280); add(sp);
        
        listar();
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnAdicionar) registrarConsultorio();
        if (e.getSource() == btnModificar) modificarConsultorio();
        if (e.getSource() == btnEliminar) eliminarConsultorio();
    }

    private void registrarConsultorio() {
        try {
            String nombre = txtNom.getText().trim();            
           
            if (acon.existeNombre(nombre)) {
                JOptionPane.showMessageDialog(this, "ERROR: El nombre del consultorio ya existe.");
                return;
            }

            clsConsultorio c = new clsConsultorio(acon.correlativo(), nombre, Integer.parseInt(txtPis.getText()), 10, 1);
            acon.adicionar(c);
            listar();
            JOptionPane.showMessageDialog(this, "Consultorio registrado.");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error en los datos.");
        }
    }

    private void modificarConsultorio() {
        int fila = tabla.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione una fila.");
            return;
        }
        int cod = (int) modelo.getValueAt(fila, 0);
        clsConsultorio c = acon.obtenerPorCodigo(cod);
        
        if (c != null) {
            c.nombre = txtNom.getText();
            c.piso = Integer.parseInt(txtPis.getText());
            acon.guardar(); 
            listar();
            JOptionPane.showMessageDialog(this, "Actualizado.");
        }
    }

    private void eliminarConsultorio() {
        int fila = tabla.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione una fila.");
            return;
        }
        int cod = (int) modelo.getValueAt(fila, 0);

       
        if (acit.tieneCitasPendientes(cod, 3)) { 
            JOptionPane.showMessageDialog(this, "ERROR: No se puede eliminar. El consultorio tiene citas futuras.");
        } else {
            clsConsultorio c = acon.obtenerPorCodigo(cod);
            c.estado = 0; 
            acon.guardar();
            listar();
            JOptionPane.showMessageDialog(this, "Consultorio desactivado.");
        }
    }

    void listar() {
        modelo.setRowCount(0);
        for(int i=0; i<acon.tamaño(); i++) {
            clsConsultorio c = acon.obtener(i);
            modelo.addRow(new Object[]{
                c.codConsultorio, c.nombre, c.piso, (c.estado == 1 ? "Activo" : "Inactivo")
            });
        }
    }
}