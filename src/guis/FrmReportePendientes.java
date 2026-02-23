package guis;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import arrays.*;
import clases.*;
import java.awt.*;

public class FrmReportePendientes extends JFrame {
    private JTable tabla;
    private DefaultTableModel modelo;
    private arrayCitas ac = new arrayCitas();
    private arrayPacientes ap = new arrayPacientes();
    private arrayMedicos am = new arrayMedicos();

    public FrmReportePendientes() {
        setTitle("Reporte: Pacientes con Citas Pendientes");
        setSize(800, 450);
        setLayout(new BorderLayout());
        setLocationRelativeTo(null);

        JLabel lbl = new JLabel("Reporte: Pacientes con Citas Pendientes", SwingConstants.CENTER);
        lbl.setFont(new Font("Tahoma", Font.BOLD, 14));
        add(lbl, BorderLayout.NORTH);

        modelo = new DefaultTableModel(new String[]{"Nro Cita", "DNI", "Paciente", "Médico", "Fecha", "Hora"}, 0);
        tabla = new JTable(modelo);
        add(new JScrollPane(tabla), BorderLayout.CENTER);

        generarReporte();
    }

    private void generarReporte() {
        modelo.setRowCount(0);
        for (clsCita c : ac.getLista()) {
            if (c.estado == 0) { 
                clsPaciente p = ap.buscar(c.codPaciente);
                clsMedico m = am.buscar(c.codMedico);
                String nomP = (p != null) ? p.apellidos + ", " + p.nombres : "---";
                String dniP = (p != null) ? p.dni : "---";
                String nomM = (m != null) ? m.apellidos : "---";
                modelo.addRow(new Object[]{c.numCita, dniP, nomP, nomM, c.fecha, c.hora});
            }
        }
    }
}