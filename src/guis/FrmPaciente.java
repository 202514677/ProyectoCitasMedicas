package guis;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import clases.*;
import arrays.*;
import java.awt.event.*;

public class FrmPaciente extends JFrame implements ActionListener {
    private JTextField txtNom, txtApe, txtDni, txtEdad, txtCel, txtCor;
    private JButton btnGrabar, btnModificar, btnEliminar, btnLimpiar;
    private JTable tabla;
    private DefaultTableModel modelo;    
   
    private arrayPacientes ap = new arrayPacientes();
    private arrayCitas ac = new arrayCitas(); 

    public FrmPaciente() {
        setTitle("Mantenimiento de Pacientes");
        setSize(750, 550); setLayout(null);
        setLocationRelativeTo(null);
       
        add(new JLabel("Nombres:")).setBounds(10, 10, 100, 20);
        txtNom = new JTextField(); txtNom.setBounds(110, 10, 150, 20); add(txtNom);
        
        add(new JLabel("Apellidos:")).setBounds(280, 10, 100, 20);
        txtApe = new JTextField(); txtApe.setBounds(380, 10, 150, 20); add(txtApe);

        add(new JLabel("DNI:")).setBounds(10, 40, 100, 20);
        txtDni = new JTextField(); txtDni.setBounds(110, 40, 100, 20); add(txtDni);

        add(new JLabel("Edad:")).setBounds(280, 40, 100, 20);
        txtEdad = new JTextField(); txtEdad.setBounds(380, 40, 50, 20); add(txtEdad);
      
        btnGrabar = new JButton("Adicionar"); 
        btnGrabar.setBounds(550, 10, 150, 25);
        btnGrabar.addActionListener(this); add(btnGrabar);

        btnModificar = new JButton("Modificar"); 
        btnModificar.setBounds(550, 40, 150, 25);
        btnModificar.addActionListener(this); add(btnModificar);

        btnEliminar = new JButton("Eliminar/Desactivar"); 
        btnEliminar.setBounds(550, 70, 150, 25);
        btnEliminar.addActionListener(this); add(btnEliminar);
       
        modelo = new DefaultTableModel(new String[]{"ID", "DNI", "Apellidos", "Nombres", "Edad", "Estado"}, 0);
        tabla = new JTable(modelo);
        JScrollPane sp = new JScrollPane(tabla); sp.setBounds(10, 110, 710, 350); add(sp);
        
        listar();
    }

    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == btnGrabar) adicionar();
        if(e.getSource() == btnModificar) modificar();
        if(e.getSource() == btnEliminar) eliminar();
    }

    private void adicionar() {
        try {
            String dni = txtDni.getText().trim();            
           
            if(ap.existeDni(dni)) {
                JOptionPane.showMessageDialog(this, "El DNI ya está registrado.");
                return;
            }

            clsPaciente p = new clsPaciente(ap.correlativo(), txtNom.getText(), txtApe.getText(), 
                             dni, Integer.parseInt(txtEdad.getText()), "", "", 1);
            ap.adicionar(p);
            listar();
            JOptionPane.showMessageDialog(this, "Paciente registrado correctamente");
        } catch(Exception ex) { 
            JOptionPane.showMessageDialog(this, "Error en los datos ingresados"); 
        }
    }

    private void modificar() {
        int fila = tabla.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione un paciente de la tabla");
            return;
        }
        int cod = (int) modelo.getValueAt(fila, 0);
        clsPaciente p = ap.obtenerPorCodigo(cod); 
        
        if (p != null) {           
            p.nombres = txtNom.getText();
            p.apellidos = txtApe.getText();
            p.edad = Integer.parseInt(txtEdad.getText());
            ap.guardar(); 
            listar();
            JOptionPane.showMessageDialog(this, "Datos actualizados");
        }
    }

    private void eliminar() {
        int fila = tabla.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione un paciente para eliminar");
            return;
        }
        int cod = (int) modelo.getValueAt(fila, 0);
      
        if (ac.tieneCitasPendientes(cod, 1)) {
            JOptionPane.showMessageDialog(this, "ERROR: No se puede eliminar. El paciente tiene citas pendientes.");
        } else {
            clsPaciente p = ap.obtenerPorCodigo(cod);
            p.estado = 0;
            ap.guardar();
            listar();
            JOptionPane.showMessageDialog(this, "Paciente desactivado del sistema.");
        }
    }

    void listar() {
        modelo.setRowCount(0);
        for(int i=0; i<ap.tamaño(); i++) {
            clsPaciente p = ap.obtener(i);
            modelo.addRow(new Object[]{
                p.codPaciente, p.dni, p.apellidos, p.nombres, p.edad, (p.estado==1?"Activo":"Inactivo")
            });
        }
    }
}