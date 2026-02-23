package guis;

import arrays.arrayCitas;
import clases.clsCita;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.util.ArrayList;
import java.awt.*;

public class FrmConsultas extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private arrayCitas objCitas; 
    private JTextArea txtArea;
    private JTextField txtPaciente, txtFecha, txtMedico, txtConsultorio;

    public FrmConsultas(arrayCitas arrayCitas) {
        this.objCitas = arrayCitas;

        setTitle("Formulario de Consulta General de Citas");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 750, 600);
        setMinimumSize(new Dimension(700, 550));
        setLocationRelativeTo(null);

        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel lblTitulo = new JLabel("Criterios de Búsqueda en Archivos");
        lblTitulo.setFont(new Font("Tahoma", Font.BOLD, 14));
        lblTitulo.setBounds(20, 15, 300, 20);
        contentPane.add(lblTitulo);

        addLabel("Cod. Paciente:", 20, 50);
        txtPaciente = addTextField(140, 50, 120);
        addBtn("Buscar Paciente", 280, 48, e -> buscarPorPaciente());

        addLabel("Cod. Médico:", 20, 85);
        txtMedico = addTextField(140, 85, 120);
        addBtn("Buscar Médico", 280, 83, e -> buscarPorMedico());

        addLabel("Cod. Consultorio:", 20, 120);
        txtConsultorio = addTextField(140, 120, 120);
        addBtn("Buscar Consultorio", 280, 118, e -> buscarPorConsultorio());

        addLabel("Fecha (dd/mm/aaaa):", 20, 155);
        txtFecha = addTextField(140, 155, 120);
        addBtn("Buscar Fecha", 280, 153, e -> buscarPorFecha());

        JButton btnPendientes = new JButton("Ver Pendientes");
        btnPendientes.setBounds(460, 48, 150, 30);
        btnPendientes.addActionListener(e -> mostrarPendientes());
        contentPane.add(btnPendientes);

        JButton btnLimpiar = new JButton("Limpiar Filtros");
        btnLimpiar.setBounds(460, 88, 150, 30);
        btnLimpiar.addActionListener(e -> limpiar());
        contentPane.add(btnLimpiar);

        JScrollPane scroll = new JScrollPane();
        scroll.setBounds(20, 200, 690, 340);
        contentPane.add(scroll);

        txtArea = new JTextArea();
        txtArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        txtArea.setEditable(false);
        scroll.setViewportView(txtArea);
    }

    private void actualizarDatos() {
        objCitas.cargar(); 
    }

    private void buscarPorPaciente() {
        try {
            actualizarDatos();
            int cod = Integer.parseInt(txtPaciente.getText().trim());
            imprimirResultados(objCitas.buscarPorPaciente(cod), "Paciente: " + cod);
        } catch (Exception e) { 
            JOptionPane.showMessageDialog(this, "Ingrese un código de paciente válido."); 
        }
    }

    private void buscarPorMedico() {
        try {
            actualizarDatos();
            int cod = Integer.parseInt(txtMedico.getText().trim());
            imprimirResultados(objCitas.buscarPorMedico(cod), "Médico: " + cod);
        } catch (Exception e) { 
            JOptionPane.showMessageDialog(this, "Ingrese un código de médico válido."); 
        }
    }

    private void buscarPorConsultorio() {
        try {
            actualizarDatos();
            int cod = Integer.parseInt(txtConsultorio.getText().trim());
            imprimirResultados(objCitas.buscarPorConsultorio(cod), "Consultorio: " + cod);
        } catch (Exception e) { 
            JOptionPane.showMessageDialog(this, "Ingrese un código de consultorio válido."); 
        }
    }

    private void buscarPorFecha() {
        actualizarDatos();
        String fec = txtFecha.getText().trim();
        if (!fec.isEmpty()) {
            imprimirResultados(objCitas.buscarPorFecha(fec), "Fecha: " + fec);
        } else {
            JOptionPane.showMessageDialog(this, "Ingrese una fecha para buscar.");
        }
    }

    private void mostrarPendientes() {
        actualizarDatos();
        ArrayList<clsCita> temp = new ArrayList<>();
        for (clsCita c : objCitas.getLista()) {
            if (c.getEstado() == 0) temp.add(c);
        }
        imprimirResultados(temp, "Citas con Estado: Pendiente");
    }

    private void imprimirResultados(ArrayList<clsCita> lista, String filtro) {
        txtArea.setText("");
        txtArea.append(" REPORTE DE CITAS - FILTRO: [" + filtro.toUpperCase() + "]\n");
        txtArea.append(" =====================================================================\n");
        txtArea.append(String.format(" %-10s %-12s %-10s %-10s %-10s %-10s\n", 
                       "N.CITA", "PACIENTE", "MEDICO", "FECHA", "HORA", "ESTADO"));
        txtArea.append(" ---------------------------------------------------------------------\n");

        if (lista.isEmpty()) {
            txtArea.append("\n >>> No se encontraron registros en el archivo citas.txt");
        } else {
            for (clsCita c : lista) {
                String est = (c.getEstado() == 0 ? "Pendiente" : (c.getEstado() == 1 ? "Atendida" : "Cancelada"));
                txtArea.append(String.format(" %-10d %-12d %-10d %-10s %-10s %-10s\n",
                               c.getNumCita(), c.getCodPaciente(), c.getCodMedico(), 
                               c.getFecha(), c.getHora(), est));
            }
            txtArea.append("\n ---------------------------------------------------------------------\n");
            txtArea.append(" Total de registros: " + lista.size());
        }
    }

    private void addLabel(String t, int x, int y) {
        JLabel l = new JLabel(t); l.setBounds(x, y, 120, 20); contentPane.add(l);
    }
    private JTextField addTextField(int x, int y, int w) {
        JTextField t = new JTextField(); t.setBounds(x, y, w, 20); contentPane.add(t); return t;
    }
    private void addBtn(String t, int x, int y, java.awt.event.ActionListener a) {
        JButton b = new JButton(t); b.setBounds(x, y, 160, 23); b.addActionListener(a); contentPane.add(b);
    }
    private void limpiar() {
        txtPaciente.setText(""); txtMedico.setText(""); txtConsultorio.setText("");
        txtFecha.setText(""); txtArea.setText("");
    }
}