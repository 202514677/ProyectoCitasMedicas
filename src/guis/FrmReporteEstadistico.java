package guis;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import arrays.*;
import clases.*;
import java.awt.*;

public class FrmReporteEstadistico extends JFrame {
    private JTable tabla;
    private DefaultTableModel modelo;
    private arrayCitas ac = new arrayCitas();
    private arrayMedicos am = new arrayMedicos();

    public FrmReporteEstadistico() {
        setTitle("Reporte: Estadísticas y Totales por Médico");
        setSize(700, 400);
        setLayout(new BorderLayout());
        setLocationRelativeTo(null);

        JLabel lbl = new JLabel("RESUMEN DE CITAS TOTALES POR MÉDICO", SwingConstants.CENTER);
        lbl.setFont(new Font("Tahoma", Font.BOLD, 14));
        add(lbl, BorderLayout.NORTH);

        modelo = new DefaultTableModel(new String[]{"Médico", "Pendientes", "Atendidas", "Canceladas", "TOTAL"}, 0);
        tabla = new JTable(modelo);
        add(new JScrollPane(tabla), BorderLayout.CENTER);

        generarEstadisticas();
    }

    private void generarEstadisticas() {
        modelo.setRowCount(0);
        for (int i = 0; i < am.tamaño(); i++) {
            clsMedico m = am.obtener(i);
            int p = 0, a = 0, can = 0;
            for (clsCita cita : ac.getLista()) {
                if (cita.codMedico == m.codMedico) {
                    if (cita.estado == 0) p++;
                    else if (cita.estado == 1) a++;
                    else if (cita.estado == 2) can++;
                }
            }
            modelo.addRow(new Object[]{m.apellidos + " " + m.nombres, p, a, can, (p + a + can)});
        }
    }
}