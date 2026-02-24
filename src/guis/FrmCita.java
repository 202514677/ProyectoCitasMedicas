package guis;

import java.awt.EventQueue;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.event.*;
import javax.swing.table.DefaultTableModel;

import clases.*;
import arrays.*;

public class FrmCita extends JFrame implements ActionListener, MouseListener {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField txtNumCita, txtFecha, txtHora, txtMotivo, txtEstado, txtDniPaciente;
    private JComboBox<String> cboMedico, cboConsultorio;
    private JButton btnNuevo, btnRegistrar, btnModificar, btnCancelar, btnBuscar, btnAtender, btnValidarDni;
    private JTable tbCita;
    private DefaultTableModel modelo;
    private JScrollPane scrollPane;

    private arrayCitas ac = new arrayCitas();
    private arrayPacientes ap = new arrayPacientes();
    private arrayMedicos am = new arrayMedicos();
    private arrayConsultorios acon = new arrayConsultorios();
    
    private clsPaciente pacienteSeleccionado = null;

    public FrmCita() {
        setTitle("GESTIÓN DE CITAS");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 620, 580);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel lblNumCita = new JLabel("N° Cita:"); lblNumCita.setBounds(32, 27, 83, 25); contentPane.add(lblNumCita);
        txtNumCita = new JTextField(); txtNumCita.setEditable(false); txtNumCita.setBounds(125, 30, 155, 18); contentPane.add(txtNumCita);

        JLabel lblDni = new JLabel("DNI Paciente:"); lblDni.setBounds(32, 55, 83, 25); contentPane.add(lblDni);
        txtDniPaciente = new JTextField(); txtDniPaciente.setBounds(125, 58, 100, 18); contentPane.add(txtDniPaciente);

        btnValidarDni = new JButton("Validar"); btnValidarDni.setBounds(230, 57, 80, 20); btnValidarDni.addActionListener(this); contentPane.add(btnValidarDni);

        JLabel lblMedico = new JLabel("Medico:"); lblMedico.setBounds(32, 86, 83, 25); contentPane.add(lblMedico);
        cboMedico = new JComboBox<>(); cboMedico.setBounds(125, 88, 250, 20); contentPane.add(cboMedico);

        JLabel lblConsultorio = new JLabel("Consultorio:"); lblConsultorio.setBounds(32, 114, 83, 25); contentPane.add(lblConsultorio);
        cboConsultorio = new JComboBox<>(); cboConsultorio.setBounds(125, 116, 250, 20); contentPane.add(cboConsultorio);

        JLabel lblFecha = new JLabel("Fecha:"); lblFecha.setBounds(32, 142, 83, 25); contentPane.add(lblFecha);
        txtFecha = new JTextField(); txtFecha.setBounds(125, 145, 155, 18); contentPane.add(txtFecha);

        JLabel lblHora = new JLabel("Hora:"); lblHora.setBounds(32, 177, 83, 25); contentPane.add(lblHora);
        txtHora = new JTextField(); txtHora.setBounds(125, 180, 155, 18); contentPane.add(txtHora);

        JLabel lblMotivo = new JLabel("Motivo:"); lblMotivo.setBounds(32, 212, 83, 25); contentPane.add(lblMotivo);
        txtMotivo = new JTextField(); txtMotivo.setBounds(125, 215, 250, 18); contentPane.add(txtMotivo);

        JLabel lblEstado = new JLabel("Estado:"); lblEstado.setBounds(32, 247, 83, 25); contentPane.add(lblEstado);
        txtEstado = new JTextField(); txtEstado.setEditable(false); txtEstado.setBounds(125, 250, 155, 18); contentPane.add(txtEstado);
      
        btnNuevo = crearBoton("Nuevo", 459, 29);
        btnRegistrar = crearBoton("Guardar", 459, 65);
        btnAtender = crearBoton("Atender", 459, 101);
        btnModificar = crearBoton("Modificar", 459, 137);
        btnCancelar = crearBoton("Cancelar", 459, 173);
        btnBuscar = crearBoton("Buscar Cita", 459, 209);
       
        scrollPane = new JScrollPane(); scrollPane.setBounds(10, 284, 584, 236); contentPane.add(scrollPane);
        modelo = new DefaultTableModel(null, new String[] { "N° Cita", "DNI Paciente", "Cod Med", "Cod Con", "Fecha", "Hora", "Estado" });
        tbCita = new JTable(modelo);
        tbCita.addMouseListener(this);
        scrollPane.setViewportView(tbCita);

        cargarCombos();
        listarCitas();
        limpiarCampos();
    }

    private JButton crearBoton(String t, int x, int y) {
        JButton b = new JButton(t); b.setBounds(x, y, 110, 25); b.addActionListener(this);
        contentPane.add(b); return b;
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnValidarDni) buscarPaciente();
        if (e.getSource() == btnRegistrar) registrarCita();
        if (e.getSource() == btnAtender) cambiarEstado(1);
        if (e.getSource() == btnCancelar) cambiarEstado(2);
        if (e.getSource() == btnNuevo) limpiarCampos();
        if (e.getSource() == btnBuscar) buscarCitaPorNumero();
        if (e.getSource() == btnModificar) modificarCita();
    }

   
    private void cargarDatosCita(clsCita c) {
        if (c != null) {
            txtNumCita.setText("" + c.numCita);
            txtFecha.setText(c.fecha);
            txtHora.setText(c.hora);
            txtMotivo.setText(c.motivo);
            txtEstado.setText(c.estado == 0 ? "Pendiente" : c.estado == 1 ? "Atendida" : "Cancelada");
            
            pacienteSeleccionado = ap.obtenerPorCodigo(c.codPaciente);
            txtDniPaciente.setText(pacienteSeleccionado != null ? pacienteSeleccionado.dni : "N/A");
            
            setSelectedIndexByCode(cboMedico, c.codMedico);
            setSelectedIndexByCode(cboConsultorio, c.codConsultorio);            
         
            seleccionarFilaEnTabla(c.numCita);
        }
    }

    private void seleccionarFilaEnTabla(int numCita) {
        for (int i = 0; i < tbCita.getRowCount(); i++) {
            if ((int)modelo.getValueAt(i, 0) == numCita) {
                tbCita.setRowSelectionInterval(i, i);
                tbCita.scrollRectToVisible(tbCita.getCellRect(i, 0, true));
                break;
            }
        }
    }

    private void buscarCitaPorNumero() {
        try {
            String input = JOptionPane.showInputDialog(this, "Ingrese el N° de Cita a buscar:");
            if (input == null || input.isEmpty()) return;
            
            int num = Integer.parseInt(input);
            clsCita c = localizarCita(num);
            
            if (c != null) {
                cargarDatosCita(c); 
            } else {
                JOptionPane.showMessageDialog(this, "La cita N° " + num + " no existe.");
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Ingrese un número válido.");
        }
    }

    public void mouseClicked(MouseEvent e) {
        int fila = tbCita.getSelectedRow();
        if (fila != -1) {
            int numCita = (int) modelo.getValueAt(fila, 0);
            clsCita c = localizarCita(numCita);
            cargarDatosCita(c);
        }
    }

    
    private void setSelectedIndexByCode(JComboBox<String> cb, int code) {
        for (int i = 0; i < cb.getItemCount(); i++) {
            if (cb.getItemAt(i).startsWith(code + " - ")) {
                cb.setSelectedIndex(i);
                break;
            }
        }
    }

    private int getCodCbo(JComboBox<String> cb) {
        return Integer.parseInt(cb.getSelectedItem().toString().split(" - ")[0]);
    }

    private clsCita localizarCita(int num) {
        for (clsCita c : ac.getLista()) if (c.numCita == num) return c;
        return null;
    }

    private void listarCitas() {
        modelo.setRowCount(0);
        for (clsCita c : ac.getLista()) {
            clsPaciente p = ap.obtenerPorCodigo(c.codPaciente);
            String dni = (p != null) ? p.dni : "Desconocido";
            String sEst = (c.estado == 0) ? "Pendiente" : (c.estado == 1) ? "Atendida" : "Cancelada";
            modelo.addRow(new Object[]{ c.numCita, dni, c.codMedico, c.codConsultorio, c.fecha, c.hora, sEst });
        }
    }

    private void buscarPaciente() {
        pacienteSeleccionado = ap.buscarDni(txtDniPaciente.getText().trim());
        if (pacienteSeleccionado != null && pacienteSeleccionado.estado == 1) {
            JOptionPane.showMessageDialog(this, "Validado: " + pacienteSeleccionado.apellidos + ", " + pacienteSeleccionado.nombres);
        } else {
            JOptionPane.showMessageDialog(this, "Paciente no encontrado.");
            pacienteSeleccionado = null;
        }
    }

    private void registrarCita() {
        if (pacienteSeleccionado == null) {
            JOptionPane.showMessageDialog(this, "Valide un paciente primero.");
            return;
        }
        try {
            int codMed = getCodCbo(cboMedico);
            int codCon = getCodCbo(cboConsultorio);            
           
            clsCita nueva = new clsCita(ac.correlativo(), pacienteSeleccionado.codPaciente, codMed, codCon, 
                                        txtFecha.getText(), txtHora.getText(), 0, txtMotivo.getText());            
          
            String mensajeError = ac.adicionar(nueva);

            if (mensajeError == null) {
                listarCitas();
                limpiarCampos();
                JOptionPane.showMessageDialog(this, "Reserva exitosa.");
            } else {
                
                JOptionPane.showMessageDialog(this, "No se puede registrar:\n" + mensajeError, 
                                              "Cruce de Horario", JOptionPane.WARNING_MESSAGE);
            }
            
        } catch (Exception ex) { 
            JOptionPane.showMessageDialog(this, "Error al registrar: " + ex.getMessage()); 
        }
    }

    private void modificarCita() {
        if (pacienteSeleccionado == null) {
            JOptionPane.showMessageDialog(this, "Seleccione una cita de la tabla.");
            return;
        }
        int num = Integer.parseInt(txtNumCita.getText());
        clsCita c = localizarCita(num);

        if (c != null && c.estado == 0) {          
            int nuevoMed = getCodCbo(cboMedico);
            int nuevoCon = getCodCbo(cboConsultorio);
            String nuevaFec = txtFecha.getText();
            String nuevaHor = txtHora.getText();
          
            clsCita temporal = new clsCita(num, c.codPaciente, nuevoMed, nuevoCon, nuevaFec, nuevaHor, 0, "");            
         
            ac.getLista().remove(c);
            String error = ac.adicionar(temporal); 
            
            if (error == null) {
                listarCitas();
                JOptionPane.showMessageDialog(this, "Cita reprogramada con éxito.");
            } else {
                ac.getLista().add(c); 
                JOptionPane.showMessageDialog(this, "Error al reprogramar: " + error);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Solo se pueden modificar citas en estado Pendiente.");
        }
    }

    private void cambiarEstado(int nuevoEstado) {
        try {
            int num = Integer.parseInt(txtNumCita.getText());
            clsCita c = localizarCita(num);
            if (c != null) {
                c.estado = nuevoEstado;
                ac.guardar();
                listarCitas();
                cargarDatosCita(c); 
            }
        } catch (Exception e) {}
    }

    private void cargarCombos() {
        cboMedico.removeAllItems();
        for (int i = 0; i < am.tamaño(); i++) {
            clsMedico m = am.obtener(i);
            if (m.estado == 1) cboMedico.addItem(m.codMedico + " - " + m.apellidos);
        }
        cboConsultorio.removeAllItems();
        for (int i = 0; i < acon.tamaño(); i++) {
            clsConsultorio c = acon.obtener(i);
            if (c.estado == 1) cboConsultorio.addItem(c.codConsultorio + " - " + c.nombre);
        }
    }

    private void limpiarCampos() {
        txtNumCita.setText("" + ac.correlativo());
        txtDniPaciente.setText("");
        txtFecha.setText("");
        txtHora.setText("");
        txtMotivo.setText("");
        txtEstado.setText("Pendiente");
        pacienteSeleccionado = null;
        tbCita.clearSelection();
    }

    public void mousePressed(MouseEvent e) {}
    public void mouseReleased(MouseEvent e) {}
    public void mouseEntered(MouseEvent e) {}
    public void mouseExited(MouseEvent e) {}
}