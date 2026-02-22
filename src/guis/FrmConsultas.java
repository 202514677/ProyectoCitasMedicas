package guis;

import arrays.arrayCitas;
import clases.clsCita;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.util.ArrayList;

public class FrmConsultas extends JFrame {

    private static final long serialVersionUID = 1L;

    private JPanel contentPane;
    private arrayCitas arrayCitas;
    private JTextArea txtArea;
    private JTextField txtPaciente;
    private JTextField txtFecha;

    // =========================
    // CONSTRUCTOR
    // =========================
    public FrmConsultas(arrayCitas arrayCitas) {

        this.arrayCitas = arrayCitas;

        setTitle("Consultas de Citas");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 600, 400);
        setLocationRelativeTo(null);

        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        // -------------------------
        // FILA 1 - BUSCAR PACIENTE
        // -------------------------
        JLabel lblPaciente = new JLabel("Cod Paciente:");
        lblPaciente.setBounds(20, 20, 100, 25);
        contentPane.add(lblPaciente);

        txtPaciente = new JTextField();
        txtPaciente.setBounds(120, 20, 120, 25);
        contentPane.add(txtPaciente);

        JButton btnBuscar = new JButton("Buscar");
        btnBuscar.setBounds(260, 20, 100, 25);
        contentPane.add(btnBuscar);

        // -------------------------
        // FILA 2 - BUSCAR FECHA
        // -------------------------
        JLabel lblFecha = new JLabel("Fecha:");
        lblFecha.setBounds(20, 50, 80, 25);
        contentPane.add(lblFecha);

        txtFecha = new JTextField();
        txtFecha.setBounds(120, 50, 120, 25);
        contentPane.add(txtFecha);

        JButton btnBuscarFecha = new JButton("Buscar Fecha");
        btnBuscarFecha.setBounds(260, 50, 120, 25);
        contentPane.add(btnBuscarFecha);

        // -------------------------
        // FILA 3 - PENDIENTES
        // -------------------------
        JButton btnPendientes = new JButton("Ver Pendientes");
        btnPendientes.setBounds(400, 20, 150, 25);
        contentPane.add(btnPendientes);

        // -------------------------
        // ÁREA DE RESULTADOS
        // -------------------------
        txtArea = new JTextArea();
        txtArea.setEditable(false);

        JScrollPane scroll = new JScrollPane(txtArea);
        scroll.setBounds(20, 100, 540, 240);
        contentPane.add(scroll);

        // -------------------------
        // EVENTOS
        // -------------------------
        btnBuscar.addActionListener(e -> buscarPorPaciente());
        btnBuscarFecha.addActionListener(e -> buscarPorFecha());

        btnPendientes.addActionListener(e -> {

            txtArea.setText("");

            ArrayList<clsCita> lista = arrayCitas.getLista();
            int contador = 0;

            for (clsCita c : lista) {

                if (c.getEstado() == 0) {

                    contador++;

                    txtArea.append(
                        "N°: " + c.getNumCita() +
                        " | Paciente: " + c.getCodPaciente() +
                        " | Médico: " + c.getCodMedico() +
                        " | Fecha: " + c.getFecha() +
                        " | Hora: " + c.getHora()
                        + "\n"
                    );
                }
            }

            txtArea.append("\nTotal de citas pendientes: " + contador);
        });
    }

    // =========================
    // MÉTODOS
    // =========================

    private void mostrarTodas() {

        txtArea.setText("");

        ArrayList<clsCita> lista = arrayCitas.getLista();

        if (lista.isEmpty()) {
            txtArea.append("No hay citas registradas.\n");
            return;
        }

        for (clsCita c : lista) {
            txtArea.append(
                "N°: " + c.getNumCita() +
                " | Paciente: " + c.getCodPaciente() +
                " | Médico: " + c.getCodMedico() +
                " | Fecha: " + c.getFecha() +
                " | Hora: " + c.getHora() +
                " | Estado: " + c.getEstado()
                + "\n"
            );
        }
    }

    private void buscarPorPaciente() {

        txtArea.setText("");

        try {
            int codPaciente = Integer.parseInt(txtPaciente.getText());

            ArrayList<clsCita> resultado =
                    arrayCitas.buscarPorPaciente(codPaciente);

            if (resultado.isEmpty()) {
                txtArea.append("No se encontraron citas para el paciente "
                        + codPaciente + "\n");
            } else {
                for (clsCita c : resultado) {
                    txtArea.append(
                        "N°: " + c.getNumCita() +
                        " | Paciente: " + c.getCodPaciente() +
                        " | Médico: " + c.getCodMedico() +
                        " | Fecha: " + c.getFecha() +
                        " | Hora: " + c.getHora() +
                        " | Estado: " + c.getEstado()
                        + "\n"
                    );
                }
            }

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(
                this,
                "Ingrese un código de paciente válido",
                "Error",
                JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private void buscarPorFecha() {

        txtArea.setText("");

        String fecha = txtFecha.getText();

        ArrayList<clsCita> resultado =
                arrayCitas.buscarPorFecha(fecha);

        if (resultado.isEmpty()) {
            txtArea.append("No se encontraron citas en esa fecha\n");
        } else {
            for (clsCita c : resultado) {
                txtArea.append(
                    "N°: " + c.getNumCita() +
                    " | Paciente: " + c.getCodPaciente() +
                    " | Médico: " + c.getCodMedico() +
                    " | Fecha: " + c.getFecha() +
                    " | Hora: " + c.getHora() +
                    " | Estado: " + c.getEstado()
                    + "\n"
                );
            }
        }
    }
}