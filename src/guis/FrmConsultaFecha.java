package guis;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import clases.*;
import arrays.*;
import java.awt.event.*;
import java.util.Calendar;

public class FrmConsultaFecha extends JFrame implements ActionListener {
    
    private JTextField txtFecha;
    private JButton btnConsultar, btnHoy, btnMesActual, btnProximoMes, btnAnio;
    private JTable tabla;
    private DefaultTableModel modelo;    
   
    private arrayCitas ac = new arrayCitas();
    private arrayPacientes ap = new arrayPacientes();
    private arrayMedicos am = new arrayMedicos();
    private arrayConsultorios acon = new arrayConsultorios();

    public FrmConsultaFecha() {
        setTitle("Consulta: Agenda por Fecha y Periodos");
        setSize(850, 500);
        setLayout(null);
        setLocationRelativeTo(null);

        add(new JLabel("Fecha (DD/MM/AAAA):")).setBounds(20, 20, 140, 20);
        txtFecha = new JTextField(); 
        txtFecha.setBounds(150, 20, 100, 20); 
        add(txtFecha);

        btnConsultar = new JButton("Consultar Fecha");
        btnConsultar.setBounds(260, 18, 130, 25);
        btnConsultar.addActionListener(this); 
        add(btnConsultar);

        btnHoy = new JButton("Agenda de Hoy");
        btnHoy.setBounds(400, 18, 130, 25);
        btnHoy.addActionListener(this); 
        add(btnHoy);

        btnMesActual = new JButton("Ver Mes Actual");
        btnMesActual.setBounds(20, 50, 150, 25);
        btnMesActual.addActionListener(this);
        add(btnMesActual);

        btnProximoMes = new JButton("Ver Próximo Mes");
        btnProximoMes.setBounds(180, 50, 150, 25);
        btnProximoMes.addActionListener(this);
        add(btnProximoMes);

        btnAnio = new JButton("Todo el año");
        btnAnio.setBounds(340, 50, 150, 25);
        btnAnio.addActionListener(this);
        add(btnAnio);
      
        modelo = new DefaultTableModel(new String[]{
            "Nro Cita", "Fecha", "Hora", "Paciente", "Médico", "Consultorio", "Estado"
        }, 0);
        tabla = new JTable(modelo);
        JScrollPane sp = new JScrollPane(tabla);
        sp.setBounds(10, 90, 810, 350);
        add(sp);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnHoy) {
            txtFecha.setText(getFechaSistema());
            consultarPorFecha();
        } else if (e.getSource() == btnConsultar) {
            consultarPorFecha();
        } else if (e.getSource() == btnMesActual) {
            consultarPorMes(0); 
        } else if (e.getSource() == btnProximoMes) {
            consultarPorMes(1); 
        } else if (e.getSource() == btnAnio) {
            consultarAnioActual();
        }
    }

    private String getFechaSistema() {
        Calendar c = Calendar.getInstance();
        return String.format("%02d/%02d/%d", c.get(Calendar.DATE), (c.get(Calendar.MONTH) + 1), c.get(Calendar.YEAR));
    }

    private void consultarPorFecha() {
        String fec = txtFecha.getText().trim();
        if (fec.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ingrese una fecha.");
            return;
        }
        filtrar(fec, true);
    }

    private void consultarPorMes(int incremento) {
        Calendar c = Calendar.getInstance();
        c.add(Calendar.MONTH, incremento);        
        String mesBusqueda = String.format("/%02d/%d", (c.get(Calendar.MONTH) + 1), c.get(Calendar.YEAR));
        filtrar(mesBusqueda, false);
    }

    private void consultarAnioActual() {
        Calendar c = Calendar.getInstance();
        String anioBusqueda = String.format("/%d", c.get(Calendar.YEAR));
        filtrar(anioBusqueda, false);
    }

    private void filtrar(String criterio, boolean esFechaExacta) {
        ac.cargar(); // Recargamos para ver cambios en el archivo citas.txt
        modelo.setRowCount(0);
        for (clsCita c : ac.getLista()) {
            boolean coincide = esFechaExacta ? c.fecha.equals(criterio) : c.fecha.contains(criterio);
            
            if (coincide) {             
                clsPaciente p = ap.buscar(c.codPaciente);
                clsMedico m = am.buscar(c.codMedico);
                clsConsultorio con = acon.buscar(c.codConsultorio);
                
                String nomP = (p != null) ? p.apellidos + ", " + p.nombres : "ID: " + c.codPaciente;
                String nomM = (m != null) ? m.apellidos : "ID: " + c.codMedico;
                String nomC = (con != null) ? con.nombre : "Sala " + c.codConsultorio;
                String est = (c.estado == 0) ? "Pendiente" : (c.estado == 1) ? "Atendida" : "Cancelada";

                modelo.addRow(new Object[]{
                    c.numCita, c.fecha, c.hora, nomP, nomM, nomC, est
                });
            }
        }
        
        if (modelo.getRowCount() == 0) {
            JOptionPane.showMessageDialog(this, "No se encontraron citas para este criterio.");
        }
    }
}