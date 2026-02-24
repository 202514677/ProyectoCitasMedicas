package guis;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import clases.clsMedico;
import arrays.*; 
import java.awt.event.*;

public class FrmMedico extends JFrame implements ActionListener, MouseListener {
    private static final long serialVersionUID = 1L;
    private JTextField txtNom, txtApe, txtEsp, txtCmp;
    private JButton btnGrabar, btnEliminar, btnModificar, btnNuevo;
    private JTable tabla;
    private DefaultTableModel modelo;
    
    private arrayMedicos am = new arrayMedicos();
    private arrayCitas ac = new arrayCitas(); 

    public FrmMedico() {
        setTitle("Mantenimiento de Médicos");
        setSize(720, 500); 
        setLayout(null);
        setLocationRelativeTo(null);
        setResizable(false);

        add(new JLabel("Nombres:")).setBounds(10, 10, 80, 20);
        txtNom = new JTextField(); txtNom.setBounds(100, 10, 150, 20); add(txtNom);
        
        add(new JLabel("Apellidos:")).setBounds(270, 10, 80, 20);
        txtApe = new JTextField(); txtApe.setBounds(350, 10, 150, 20); add(txtApe);

        add(new JLabel("Especialidad:")).setBounds(10, 40, 80, 20);
        txtEsp = new JTextField(); txtEsp.setBounds(100, 40, 150, 20); add(txtEsp);

        add(new JLabel("CMP:")).setBounds(270, 40, 80, 20);
        txtCmp = new JTextField(); txtCmp.setBounds(350, 40, 150, 20); add(txtCmp);  

        btnNuevo = new JButton("Nuevo");
        btnNuevo.setBounds(550, 10, 120, 23);
        btnNuevo.addActionListener(this);
        add(btnNuevo);

        btnGrabar = new JButton("Grabar");
        btnGrabar.setBounds(550, 40, 120, 23);
        btnGrabar.addActionListener(this);
        add(btnGrabar);

        btnModificar = new JButton("Modificar");
        btnModificar.setBounds(550, 70, 120, 23);
        btnModificar.addActionListener(this);
        add(btnModificar);

        btnEliminar = new JButton("Eliminar");
        btnEliminar.setBounds(550, 100, 120, 23);
        btnEliminar.addActionListener(this);
        add(btnEliminar);

        modelo = new DefaultTableModel();
        modelo.addColumn("Código");
        modelo.addColumn("Apellidos");
        modelo.addColumn("Nombres");
        modelo.addColumn("Especialidad");
        modelo.addColumn("CMP");
        modelo.addColumn("Estado");

        tabla = new JTable(modelo);
        tabla.addMouseListener(this);
        JScrollPane sp = new JScrollPane(tabla);
        sp.setBounds(10, 140, 680, 300);
        add(sp);

        listar();
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnNuevo) limpiar();
        if (e.getSource() == btnGrabar) grabar();
        if (e.getSource() == btnModificar) modificar();
        if (e.getSource() == btnEliminar) eliminar();
    }

    private void grabar() {
        if (validarCampos()) {
            String cmp = txtCmp.getText().trim();            
            
            if (am.existeCmp(cmp)) {
                JOptionPane.showMessageDialog(this, "ERROR: El CMP ya está registrado.");
                return;
            }
            clsMedico m = new clsMedico(am.correlativo(), txtNom.getText().trim(), 
                                        txtApe.getText().trim(), txtEsp.getText().trim(), 
                                        cmp, 1);
            am.adicionar(m);
            listar();
            limpiar();
            JOptionPane.showMessageDialog(this, "Médico registrado.");
        }
    }

    private void modificar() {
        int fila = tabla.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione un médico.");
            return;
        }

        if (validarCampos()) {
            int cod = (int) modelo.getValueAt(fila, 0);
            String cmpNuevo = txtCmp.getText().trim();

            boolean duplicado = false;
            for (int i = 0; i < am.tamaño(); i++) {
                clsMedico aux = am.obtener(i);
                if (aux.cmp.equals(cmpNuevo) && aux.codMedico != cod) {
                    duplicado = true;
                    break;
                }
            }

            if (duplicado) {
                JOptionPane.showMessageDialog(this, "ERROR: El CMP ya pertenece a otro médico.");
                return;
            }

            clsMedico m = am.obtenerPorCodigo(cod);
            if (m != null) {
                m.nombres = txtNom.getText().trim();
                m.apellidos = txtApe.getText().trim();
                m.especialidad = txtEsp.getText().trim();
                m.cmp = cmpNuevo;
                am.guardar();
                listar();
                limpiar();
                JOptionPane.showMessageDialog(this, "Médico actualizado.");
            }
        }
    }

    private void eliminar() {
        int fila = tabla.getSelectedRow();
        if (fila != -1) {
            int cod = (int) modelo.getValueAt(fila, 0);
            if (ac.tieneCitasPendientes(cod, 2)) {
                JOptionPane.showMessageDialog(this, "ERROR: Médico tiene citas pendientes.");
            } else {
                clsMedico m = am.obtenerPorCodigo(cod);
                m.estado = 0;
                am.guardar();
                listar();
                limpiar();
                JOptionPane.showMessageDialog(this, "Médico desactivado.");
            }
        }
    }

    public void mouseClicked(MouseEvent e) {
        int fila = tabla.getSelectedRow();
        if (fila != -1) {
            txtApe.setText(modelo.getValueAt(fila, 1).toString());
            txtNom.setText(modelo.getValueAt(fila, 2).toString());
            txtEsp.setText(modelo.getValueAt(fila, 3).toString());
            txtCmp.setText(modelo.getValueAt(fila, 4).toString());
        }
    }

    private boolean validarCampos() {
        if (txtNom.getText().trim().isEmpty() || 
            txtApe.getText().trim().isEmpty() || 
            txtCmp.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Todos los campos principales son obligatorios.");
            return false;
        }
        return true;
    }

    void listar() {
        modelo.setRowCount(0);
        for(int i=0; i<am.tamaño(); i++) {
            clsMedico m = am.obtener(i);
            modelo.addRow(new Object[]{m.codMedico, m.apellidos, m.nombres, m.especialidad, m.cmp, (m.estado == 1 ? "Activo" : "Inactivo")});
        }
    }

    private void limpiar() {
        txtNom.setText(""); txtApe.setText(""); txtEsp.setText(""); txtCmp.setText("");
        txtNom.requestFocus();
        tabla.clearSelection();
    }

    public void mousePressed(MouseEvent e) {}
    public void mouseReleased(MouseEvent e) {}
    public void mouseEntered(MouseEvent e) {}
    public void mouseExited(MouseEvent e) {}
}