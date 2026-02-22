package guis;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import clases.clsMedico;
import arrays.*; 
import java.awt.event.*;

public class FrmMedico extends JFrame implements ActionListener {
    private JTextField txtNom, txtApe, txtEsp, txtCmp;
    private JButton btnGrabar, btnEliminar, btnModificar;
    private JTable tabla;
    private DefaultTableModel modelo;
    
    private arrayMedicos am = new arrayMedicos();
    private arrayCitas ac = new arrayCitas(); 

    public FrmMedico() {
        setTitle("Mantenimiento de Médicos");
        setSize(720, 500); setLayout(null);
        setLocationRelativeTo(null);

        add(new JLabel("Nombres:")).setBounds(10, 10, 80, 20);
        txtNom = new JTextField(); txtNom.setBounds(100, 10, 150, 20); add(txtNom);
        
        add(new JLabel("Apellidos:")).setBounds(270, 10, 80, 20);
        txtApe = new JTextField(); txtApe.setBounds(350, 10, 150, 20); add(txtApe);

        add(new JLabel("Especialidad:")).setBounds(10, 40, 80, 20);
        txtEsp = new JTextField(); txtEsp.setBounds(100, 40, 150, 20); add(txtEsp);

        add(new JLabel("CMP:")).setBounds(270, 40, 50, 20);
        txtCmp = new JTextField(); txtCmp.setBounds(350, 40, 100, 20); add(txtCmp);
       
        btnGrabar = new JButton("Adicionar"); 
        btnGrabar.setBounds(550, 10, 130, 25);
        btnGrabar.addActionListener(this); add(btnGrabar);
        
        btnModificar = new JButton("Modificar"); 
        btnModificar.setBounds(550, 40, 130, 25);
        btnModificar.addActionListener(this); add(btnModificar);
        
        btnEliminar = new JButton("Eliminar"); 
        btnEliminar.setBounds(550, 70, 130, 25);
        btnEliminar.addActionListener(this); add(btnEliminar);

        modelo = new DefaultTableModel(new String[]{"ID", "Apellidos", "Nombres", "Especialidad", "CMP", "Estado"}, 0);
        tabla = new JTable(modelo);
        JScrollPane sp = new JScrollPane(tabla); sp.setBounds(10, 110, 680, 320); add(sp);
        listar();
    }

    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == btnGrabar) adicionar();
        if(e.getSource() == btnModificar) modificar();
        if(e.getSource() == btnEliminar) eliminar();
    }

    private void adicionar() {
        try {
            String cmp = txtCmp.getText().trim();            
            if (am.existeCmp(cmp)) {
                JOptionPane.showMessageDialog(this, "El CMP ya está registrado en el sistema.");
                return;
            }
            
            clsMedico m = new clsMedico(am.correlativo(), txtNom.getText(), txtApe.getText(), txtEsp.getText(), cmp, 1);
            am.adicionar(m);
            listar();
            JOptionPane.showMessageDialog(this, "Médico registrado.");
        } catch(Exception ex) {
            JOptionPane.showMessageDialog(this, "Error en los datos.");
        }
    }

    private void modificar() {
        int fila = tabla.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione un médico de la tabla");
            return;
        }
        int cod = (int) modelo.getValueAt(fila, 0);
        clsMedico m = am.obtenerPorCodigo(cod);
        
        if (m != null) {
            m.nombres = txtNom.getText();
            m.apellidos = txtApe.getText();
            m.especialidad = txtEsp.getText();
            m.cmp = txtCmp.getText();
            am.guardar(); 
            listar();
            JOptionPane.showMessageDialog(this, "Médico actualizado.");
        }
    }

    private void eliminar() {
        int fila = tabla.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione un médico");
            return;
        }
        int cod = (int) modelo.getValueAt(fila, 0);

        if (ac.tieneCitasPendientes(cod, 2)) {
            JOptionPane.showMessageDialog(this, "NO SE PUEDE ELIMINAR: El médico tiene citas pendientes programadas.");
        } else {
            clsMedico m = am.obtenerPorCodigo(cod);
            m.estado = 0; 
            am.guardar();
            listar();
            JOptionPane.showMessageDialog(this, "Médico desactivado.");
        }
    }

    void listar() {
        modelo.setRowCount(0);
        for(int i=0; i<am.tamaño(); i++) {
            clsMedico m = am.obtener(i);
            modelo.addRow(new Object[]{
                m.codMedico, m.apellidos, m.nombres, m.especialidad, m.cmp, (m.estado == 1 ? "Activo" : "Inactivo")
            });
        }
    }
}