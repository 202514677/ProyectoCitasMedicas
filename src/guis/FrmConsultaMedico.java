package guis;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import clases.*;
import arrays.*;
import java.awt.event.*;
import java.util.Calendar;

public class FrmConsultaMedico extends JFrame implements ActionListener {
    private JComboBox<String> cboMedico;
    private JTextField txtFecha;
    private JButton btnConsultar, btnMesActual, btnProximoMes;
    private JTable tabla;
    private DefaultTableModel modelo;
    private arrayCitas ac = new arrayCitas();
    private arrayMedicos am = new arrayMedicos();
    private arrayPacientes ap = new arrayPacientes();

    public FrmConsultaMedico() {
        setTitle("Agenda del Médico");
        setSize(800, 500); 
        setLayout(null); 
        setLocationRelativeTo(null);

        add(new JLabel("Médico:")).setBounds(10, 20, 60, 20);
        cboMedico = new JComboBox<>();
        for(int i=0; i<am.tamaño(); i++) {
            clsMedico m = am.obtener(i);
            if(m.estado == 1)
                cboMedico.addItem(m.codMedico + " - " + m.apellidos);
        }
        cboMedico.setBounds(70, 20, 180, 20); add(cboMedico);

        add(new JLabel("Fecha (DD/MM/AAAA):")).setBounds(270, 20, 140, 20);
        txtFecha = new JTextField();
        txtFecha.setBounds(410, 20, 100, 20); add(txtFecha);

        btnConsultar = new JButton("Consultar"); 
        btnConsultar.setBounds(530, 18, 120, 25);
        btnConsultar.addActionListener(this); add(btnConsultar);

        btnMesActual = new JButton("Mes Actual");
        btnMesActual.setBounds(70, 50, 120, 25);
        btnMesActual.addActionListener(this); add(btnMesActual);

        btnProximoMes = new JButton("Próximo Mes");
        btnProximoMes.setBounds(200, 50, 120, 25);
        btnProximoMes.addActionListener(this); add(btnProximoMes);

        modelo = new DefaultTableModel(new String[]{"Cita", "Paciente", "Fecha", "Hora", "Estado", "Consultorio"}, 0);
        tabla = new JTable(modelo);
        add(new JScrollPane(tabla)).setBounds(10, 90, 760, 350);
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
        String item = (String)cboMedico.getSelectedItem();
        if (item == null) return;
        int codMed = Integer.parseInt(item.split(" - ")[0]);
        String fecha = txtFecha.getText().trim();
        
        ac.cargar();
        modelo.setRowCount(0);
        for(clsCita c : ac.getLista()) {
            if(c.codMedico == codMed && c.fecha.equals(fecha)) {
                agregarFila(c);
            }
        }
    }

    private void consultarPorMes(int incremento) {
        String item = (String)cboMedico.getSelectedItem();
        if (item == null) return;
        int codMed = Integer.parseInt(item.split(" - ")[0]);

        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MONTH, incremento);
        int mes = cal.get(Calendar.MONTH) + 1;
        int anio = cal.get(Calendar.YEAR);
        String filtroMes = String.format("/%02d/%d", mes, anio);

        ac.cargar();
        modelo.setRowCount(0);
        for(clsCita c : ac.getLista()) {
            if(c.codMedico == codMed && c.fecha.endsWith(filtroMes)) {
                agregarFila(c);
            }
        }
    }

    private void agregarFila(clsCita c) {
        clsPaciente p = ap.buscar(c.codPaciente);
        String nomP = (p != null) ? p.apellidos + ", " + p.nombres : "ID: " + c.codPaciente;
        String estado = (c.estado==0?"Pendiente":(c.estado==1?"Atendida":"Cancelada"));
        modelo.addRow(new Object[]{c.numCita, nomP, c.fecha, c.hora, estado, c.codConsultorio});
    }
}