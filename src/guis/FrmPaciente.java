package guis;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import clases.*;
import arrays.*;
import java.awt.event.*;

public class FrmPaciente extends JFrame implements ActionListener, MouseListener {
    private static final long serialVersionUID = 1L;
    private JTextField txtNom, txtApe, txtDni, txtEdad;
    private JButton btnGrabar, btnModificar, btnEliminar, btnNuevo;
    private JTable tabla;
    private DefaultTableModel modelo;    
   
    private arrayPacientes ap = new arrayPacientes();
    private arrayCitas ac = new arrayCitas(); 

    public FrmPaciente() {
        setTitle("Mantenimiento de Pacientes");
        setSize(750, 550); 
        setLayout(null);
        setLocationRelativeTo(null);
        setResizable(false);
       
        add(new JLabel("Nombres:")).setBounds(10, 10, 100, 20);
        txtNom = new JTextField(); txtNom.setBounds(110, 10, 150, 20); add(txtNom);
        
        add(new JLabel("Apellidos:")).setBounds(280, 10, 100, 20);
        txtApe = new JTextField(); txtApe.setBounds(380, 10, 150, 20); add(txtApe);

        add(new JLabel("DNI:")).setBounds(10, 40, 100, 20);
        txtDni = new JTextField(); txtDni.setBounds(110, 40, 100, 20); add(txtDni);

        add(new JLabel("Edad:")).setBounds(280, 40, 100, 20);
        txtEdad = new JTextField(); txtEdad.setBounds(380, 40, 50, 20); add(txtEdad);

        btnNuevo = new JButton("Nuevo");
        btnNuevo.setBounds(580, 10, 120, 23);
        btnNuevo.addActionListener(this);
        add(btnNuevo);

        btnGrabar = new JButton("Grabar");
        btnGrabar.setBounds(580, 40, 120, 23);
        btnGrabar.addActionListener(this);
        add(btnGrabar);

        btnModificar = new JButton("Modificar");
        btnModificar.setBounds(580, 70, 120, 23);
        btnModificar.addActionListener(this);
        add(btnModificar);

        btnEliminar = new JButton("Eliminar");
        btnEliminar.setBounds(580, 100, 120, 23);
        btnEliminar.addActionListener(this);
        add(btnEliminar);

        modelo = new DefaultTableModel();
        modelo.addColumn("Código");
        modelo.addColumn("DNI");
        modelo.addColumn("Apellidos");
        modelo.addColumn("Nombres");
        modelo.addColumn("Edad");
        modelo.addColumn("Estado");

        tabla = new JTable(modelo);
        tabla.addMouseListener(this);
        JScrollPane sp = new JScrollPane(tabla);
        sp.setBounds(10, 150, 710, 340);
        add(sp);

        listar();
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnNuevo) {
            limpiar();
        }
        if (e.getSource() == btnGrabar) {
            grabar();
        }
        if (e.getSource() == btnModificar) {
            modificar();
        }
        if (e.getSource() == btnEliminar) {
            eliminar();
        }
    }

    private void grabar() {
        try {
            String nom = txtNom.getText().trim();
            String ape = txtApe.getText().trim();
            String dni = txtDni.getText().trim();
            int edad = Integer.parseInt(txtEdad.getText().trim());

            clsPaciente nuevo = new clsPaciente(ap.correlativo(), nom, ape, dni, edad, "", "", 1);
            ap.adicionar(nuevo);
            listar();
            limpiar();
            JOptionPane.showMessageDialog(this, "Paciente registrado.");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error en los datos.");
        }
    }

    private void modificar() {
        int fila = tabla.getSelectedRow();
        if (fila == -1) return;
        int cod = (int) modelo.getValueAt(fila, 0);
        clsPaciente p = ap.obtenerPorCodigo(cod);
        if (p != null) {
            p.nombres = txtNom.getText();
            p.apellidos = txtApe.getText();
            p.dni = txtDni.getText();
            p.edad = Integer.parseInt(txtEdad.getText());
            ap.guardar();
            listar();
            limpiar();
            JOptionPane.showMessageDialog(this, "Paciente actualizado.");
        }
    }

    private void eliminar() {
        int fila = tabla.getSelectedRow();
        if (fila == -1) return;
        int cod = (int) modelo.getValueAt(fila, 0);
        if (ac.tieneCitasPendientes(cod, 1)) {
            JOptionPane.showMessageDialog(this, "ERROR: El paciente tiene citas pendientes.");
        } else {
            clsPaciente p = ap.obtenerPorCodigo(cod);
            if (p != null) {
                p.estado = 0;
                ap.guardar();
                listar();
                limpiar();
                JOptionPane.showMessageDialog(this, "Paciente desactivado.");
            }
        }
    }
    
    public void mouseClicked(MouseEvent e) {
        int fila = tabla.getSelectedRow();
        if (fila != -1) {
            txtDni.setText(modelo.getValueAt(fila, 1).toString());
            txtApe.setText(modelo.getValueAt(fila, 2).toString());
            txtNom.setText(modelo.getValueAt(fila, 3).toString());
            txtEdad.setText(modelo.getValueAt(fila, 4).toString());
        }
    }

    void listar() {
        modelo.setRowCount(0);
        for(int i=0; i<ap.tamaño(); i++) {
            clsPaciente p = ap.obtener(i);
            modelo.addRow(new Object[]{p.codPaciente, p.dni, p.apellidos, p.nombres, p.edad, (p.estado==1?"Activo":"Inactivo")});
        }
    }

    void limpiar() {
        txtNom.setText("");
        txtApe.setText("");
        txtDni.setText("");
        txtEdad.setText("");
        txtNom.requestFocus();
        tabla.clearSelection();
    }

    public void mousePressed(MouseEvent e) {}
    public void mouseReleased(MouseEvent e) {}
    public void mouseEntered(MouseEvent e) {}
    public void mouseExited(MouseEvent e) {}
}