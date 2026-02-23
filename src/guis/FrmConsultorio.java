package guis;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import clases.*;
import arrays.*; 
import java.awt.event.*;

public class FrmConsultorio extends JFrame implements ActionListener, MouseListener {
    private static final long serialVersionUID = 1L;
    private JTextField txtNom, txtPis;
    private JButton btnAdicionar, btnModificar, btnEliminar, btnNuevo;
    private JTable tabla;
    private DefaultTableModel modelo;

    private arrayConsultorios acon = new arrayConsultorios();
    private arrayCitas acit = new arrayCitas();

    public FrmConsultorio() {
        setTitle("Mantenimiento de Consultorios");
        setSize(550, 450); 
        setLayout(null);
        setLocationRelativeTo(null);
        setResizable(false);

        add(new JLabel("Nombre:")).setBounds(10, 10, 80, 20);
        txtNom = new JTextField(); txtNom.setBounds(100, 10, 150, 20); add(txtNom);
        
        add(new JLabel("Piso:")).setBounds(10, 40, 80, 20);
        txtPis = new JTextField(); txtPis.setBounds(100, 40, 50, 20); add(txtPis);
        
        btnNuevo = new JButton("Nuevo");
        btnNuevo.setBounds(300, 10, 120, 23);
        btnNuevo.addActionListener(this);
        add(btnNuevo);

        btnAdicionar = new JButton("Grabar"); 
        btnAdicionar.setBounds(300, 40, 120, 23);
        btnAdicionar.addActionListener(this);
        add(btnAdicionar);

        btnModificar = new JButton("Modificar");
        btnModificar.setBounds(300, 70, 120, 23);
        btnModificar.addActionListener(this);
        add(btnModificar);

        btnEliminar = new JButton("Eliminar");
        btnEliminar.setBounds(300, 100, 120, 23);
        btnEliminar.addActionListener(this);
        add(btnEliminar);

        modelo = new DefaultTableModel();
        modelo.addColumn("Código");
        modelo.addColumn("Nombre");
        modelo.addColumn("Piso");
        modelo.addColumn("Estado");

        tabla = new JTable(modelo);
        tabla.addMouseListener(this);
        JScrollPane sp = new JScrollPane(tabla);
        sp.setBounds(10, 140, 510, 250);
        add(sp);

        listar();
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnNuevo) limpiar();
        if (e.getSource() == btnAdicionar) adicionar();
        if (e.getSource() == btnModificar) modificar();
        if (e.getSource() == btnEliminar) eliminar();
    }

    private void adicionar() {
        clsConsultorio c = new clsConsultorio(acon.correlativo(), txtNom.getText(), Integer.parseInt(txtPis.getText()), 0, 1);
        acon.adicionar(c);
        listar();
        limpiar();
        JOptionPane.showMessageDialog(this, "Consultorio grabado.");
    }

    private void modificar() {
        int fila = tabla.getSelectedRow();
        if (fila != -1) {
            int cod = (int) modelo.getValueAt(fila, 0);
            clsConsultorio c = acon.obtenerPorCodigo(cod);
            c.nombre = txtNom.getText();
            c.piso = Integer.parseInt(txtPis.getText());
            acon.guardar();
            listar();
            limpiar();
            JOptionPane.showMessageDialog(this, "Consultorio actualizado.");
        }
    }

    private void eliminar() {
        int fila = tabla.getSelectedRow();
        if (fila != -1) {
            int cod = (int) modelo.getValueAt(fila, 0);
            if (acit.tieneCitasPendientes(cod, 3)) { 
                JOptionPane.showMessageDialog(this, "ERROR: El consultorio tiene citas pendientes.");
            } else {
                clsConsultorio c = acon.obtenerPorCodigo(cod);
                c.estado = 0; 
                acon.guardar();
                listar();
                limpiar();
                JOptionPane.showMessageDialog(this, "Consultorio desactivado.");
            }
        }
    }

    public void mouseClicked(MouseEvent e) {
        int fila = tabla.getSelectedRow();
        if (fila != -1) {
            txtNom.setText(modelo.getValueAt(fila, 1).toString());
            txtPis.setText(modelo.getValueAt(fila, 2).toString());
        }
    }

    void listar() {
        modelo.setRowCount(0);
        for(int i=0; i<acon.tamaño(); i++) {
            clsConsultorio c = acon.obtener(i);
            modelo.addRow(new Object[]{c.codConsultorio, c.nombre, c.piso, (c.estado == 1 ? "Activo" : "Inactivo")});
        }
    }

    private void limpiar() {
        txtNom.setText(""); txtPis.setText("");
        txtNom.requestFocus();
        tabla.clearSelection();
    }

    public void mousePressed(MouseEvent e) {}
    public void mouseReleased(MouseEvent e) {}
    public void mouseEntered(MouseEvent e) {}
    public void mouseExited(MouseEvent e) {}
}