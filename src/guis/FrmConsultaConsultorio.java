package guis;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import clases.*;
import arrays.*;
import java.awt.event.*;
import java.util.Calendar;

public class FrmConsultaConsultorio extends JFrame implements ActionListener {
    private JComboBox<String> cboConsultorio;
    private JTextField txtFecha;
    private JButton btnConsultar, btnMesActual, btnProximoMes;
    private JTable tabla;
    private DefaultTableModel modelo;    
   
    private arrayCitas ac = new arrayCitas();
    private arrayConsultorios acon = new arrayConsultorios();
    private arrayMedicos am = new arrayMedicos();
    private arrayPacientes ap = new arrayPacientes();  

    public FrmConsultaConsultorio() {
        setTitle("Ocupación de Consultorio");
        setSize(800, 500);
        setLayout(null);
        setLocationRelativeTo(null);
       
        add(new JLabel("Consultorio:")).setBounds(10, 20, 80, 20);
        cboConsultorio = new JComboBox<>();
        cargarConsultorios();
        cboConsultorio.setBounds(90, 20, 180, 20);
        add(cboConsultorio);

        add(new JLabel("Fecha (DD/MM/AAAA):")).setBounds(290, 20, 140, 20);
        txtFecha = new JTextField();
        txtFecha.setBounds(430, 20, 100, 20);
        add(txtFecha);

        btnConsultar = new JButton("Consultar");
        btnConsultar.setBounds(550, 18, 120, 25);
        btnConsultar.addActionListener(this);
        add(btnConsultar);

        btnMesActual = new JButton("Mes Actual");
        btnMesActual.setBounds(90, 50, 120, 25);
        btnMesActual.addActionListener(this);
        add(btnMesActual);

        btnProximoMes = new JButton("Próximo Mes");
        btnProximoMes.setBounds(220, 50, 120, 25);
        btnProximoMes.addActionListener(this);
        add(btnProximoMes);

        modelo = new DefaultTableModel(new String[]{"Cita", "Fecha", "Hora", "Médico", "Paciente", "Estado"}, 0);
        tabla = new JTable(modelo);
        add(new JScrollPane(tabla)).setBounds(10, 90, 760, 350);
    }

    private void cargarConsultorios() {
        for (int i = 0; i < acon.tamaño(); i++) {
            clsConsultorio c = acon.obtener(i);
            if (c.estado == 1) {
                cboConsultorio.addItem(c.codConsultorio + " - " + c.nombre);
            }
        }
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnConsultar) {
            consultarPorFecha();
        } else if (e.getSource() == btnMesActual) {
            consultarPorMes(0);
        } else if (e.getSource() == btnProximoMes) {
            consultarPorMes(1);
        }
    }

    private void consultarPorFecha() {
        String item = (String) cboConsultorio.getSelectedItem();
        if (item == null) return;
        int codCon = Integer.parseInt(item.split(" - ")[0]);
        String fecha = txtFecha.getText().trim();

        ac.cargar();
        modelo.setRowCount(0);
        for (clsCita c : ac.getLista()) {
            if (c.codConsultorio == codCon && c.fecha.equals(fecha)) {
                agregarFila(c);
            }
        }
    }

    private void consultarPorMes(int incremento) {
        String item = (String) cboConsultorio.getSelectedItem();
        if (item == null) return;
        int codCon = Integer.parseInt(item.split(" - ")[0]);

        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MONTH, incremento);
        int mes = cal.get(Calendar.MONTH) + 1;
        int anio = cal.get(Calendar.YEAR);
        String filtroMes = String.format("/%02d/%d", mes, anio);

        ac.cargar();
        modelo.setRowCount(0);
        for (clsCita c : ac.getLista()) {
            if (c.codConsultorio == codCon && c.fecha.endsWith(filtroMes)) {
                agregarFila(c);
            }
        }
    }

    private void agregarFila(clsCita c) {
        clsMedico med = am.buscar(c.codMedico);
        String nomMed = (med != null) ? med.apellidos : "ID: " + c.codMedico;
        clsPaciente pac = ap.buscar(c.codPaciente);
        String nomPac = (pac != null) ? pac.apellidos + ", " + pac.nombres : "ID: " + c.codPaciente;
        String estado = (c.estado == 0) ? "Pendiente" : (c.estado == 1) ? "Atendida" : "Cancelada";

        modelo.addRow(new Object[]{c.numCita, c.fecha, c.hora, nomMed, nomPac, estado});
    }
}