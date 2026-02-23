package forms;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JOptionPane;

import classes.CitaDAO;
import classes.clsCita;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class frmCita extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JLabel lblNumCita;
	private JLabel lblPaciente;
	private JLabel lblMedico;
	private JLabel lblConsultorio;
	private JLabel lblFecha;
	private JLabel lblHora;
	private JLabel lblMotivo;
	private JLabel lblEstado;
	private JTextField txtNumCita;
	private JTextField txtFecha;
	private JTextField txtHora;
	private JTextField txtMotivo;
	private JTextField txtEstado;
	private JComboBox cboPaciente;
	private JComboBox cboMedico;
	private JComboBox cboConsultorio;
	private JButton btnNuevo;
	private JButton btnRegistrar;
	private JButton btnModificar;
	private JButton btnCancelar;
	private JButton btnBuscar;
	// DAO
	private CitaDAO dao = new CitaDAO();
	private JScrollPane scrollPane;
	private JTable tbCita;
	private JButton btnAtender;


	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					frmCita frame = new frmCita();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public frmCita() {
		setTitle("REGISTRO DE CITAS MEDICAS");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 599, 580);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		lblNumCita = new JLabel("N° Cita:");
		lblNumCita.setFont(new Font("Arial", Font.PLAIN, 10));
		lblNumCita.setBounds(32, 27, 83, 25);
		contentPane.add(lblNumCita);
		
		lblPaciente = new JLabel("Paciente:");
		lblPaciente.setFont(new Font("Arial", Font.PLAIN, 10));
		lblPaciente.setBounds(32, 51, 83, 25);
		contentPane.add(lblPaciente);
		
		lblMedico = new JLabel("Medico:");
		lblMedico.setFont(new Font("Arial", Font.PLAIN, 10));
		lblMedico.setBounds(32, 86, 83, 25);
		contentPane.add(lblMedico);
		
		lblConsultorio = new JLabel("Consultorio:");
		lblConsultorio.setFont(new Font("Arial", Font.PLAIN, 10));
		lblConsultorio.setBounds(32, 114, 83, 25);
		contentPane.add(lblConsultorio);
		
		lblFecha = new JLabel("Fecha:");
		lblFecha.setFont(new Font("Arial", Font.PLAIN, 10));
		lblFecha.setBounds(32, 142, 83, 25);
		contentPane.add(lblFecha);
		
		lblHora = new JLabel("Hora:");
		lblHora.setFont(new Font("Arial", Font.PLAIN, 10));
		lblHora.setBounds(32, 177, 83, 25);
		contentPane.add(lblHora);
		
		lblMotivo = new JLabel("Motivo:");
		lblMotivo.setFont(new Font("Arial", Font.PLAIN, 10));
		lblMotivo.setBounds(32, 212, 83, 25);
		contentPane.add(lblMotivo);
		
		lblEstado = new JLabel("Estado:");
		lblEstado.setFont(new Font("Arial", Font.PLAIN, 10));
		lblEstado.setBounds(32, 247, 83, 25);
		contentPane.add(lblEstado);
		
		txtNumCita = new JTextField();
		txtNumCita.setBounds(125, 30, 155, 18);
		contentPane.add(txtNumCita);
		txtNumCita.setColumns(10);
		
		txtFecha = new JTextField();
		txtFecha.setColumns(10);
		txtFecha.setBounds(125, 145, 155, 18);
		contentPane.add(txtFecha);
		
		txtHora = new JTextField();
		txtHora.setColumns(10);
		txtHora.setBounds(125, 180, 155, 18);
		contentPane.add(txtHora);
		
		txtMotivo = new JTextField();
		txtMotivo.setColumns(10);
		txtMotivo.setBounds(125, 215, 155, 18);
		contentPane.add(txtMotivo);
		
		txtEstado = new JTextField();
		txtEstado.setEditable(false);
		txtEstado.setColumns(10);
		txtEstado.setBounds(125, 250, 155, 18);
		contentPane.add(txtEstado);
		
		cboPaciente = new JComboBox();
		cboPaciente.setBounds(125, 53, 155, 20);
		contentPane.add(cboPaciente);
		
		cboMedico = new JComboBox();
		cboMedico.setBounds(125, 88, 155, 20);
		contentPane.add(cboMedico);
		
		cboConsultorio = new JComboBox();
		cboConsultorio.setBounds(125, 116, 155, 20);
		contentPane.add(cboConsultorio);
		
		btnNuevo = new JButton("Nuevo");
		btnNuevo.addActionListener(this);
		btnNuevo.setBounds(459, 29, 96, 20);
		contentPane.add(btnNuevo);
		
		btnRegistrar = new JButton("Registrar");
		btnRegistrar.addActionListener(this);
		btnRegistrar.setBounds(459, 73, 96, 20);
		contentPane.add(btnRegistrar);
		
		btnModificar = new JButton("Modificar");
		btnModificar.addActionListener(this);
		btnModificar.setBounds(459, 163, 96, 20);
		contentPane.add(btnModificar);
		
		btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(this);
		btnCancelar.setHorizontalAlignment(SwingConstants.LEFT);
		btnCancelar.setBounds(459, 207, 96, 20);
		contentPane.add(btnCancelar);
		
		btnBuscar = new JButton("Buscar");
		btnBuscar.addActionListener(this);
		btnBuscar.setBounds(459, 249, 96, 20);
		contentPane.add(btnBuscar);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 284, 544, 236);
		contentPane.add(scrollPane);
		
		tbCita = new JTable();
		tbCita.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"N\u00B0 Cita", "Paciente", "Medico", "Consultorio", "Fecha", "Hora", "Motivo", "Estado"
			}
		));
		scrollPane.setViewportView(tbCita);
		
		btnAtender = new JButton("Atender");
		btnAtender.addActionListener(this);
		btnAtender.setBounds(459, 116, 96, 20);
		contentPane.add(btnAtender);
		
		// Inicializar campos
		limpiarCampos();
		listarCitasEnTabla();


	}
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnAtender) {
			atenderCita();
		}
		if (e.getSource() == btnBuscar) {
			buscarCita();
		}
		if (e.getSource() == btnCancelar) {
			cancelarCita();
		}
		if (e.getSource() == btnModificar) {
			modificarCita();
		}
		if (e.getSource() == btnRegistrar) {
			registrarCita();
		}
		if (e.getSource() == btnNuevo) {
			limpiarCampos();
		}
		
	}
	
	// MÉTODO PARA REGISTRAR CITA
		private void registrarCita() {
			try {
				int num = Integer.parseInt(txtNumCita.getText());
				int codPaciente = cboPaciente.getSelectedIndex();
				int codMedico = cboMedico.getSelectedIndex();
				int codConsultorio = cboConsultorio.getSelectedIndex();
				String fecha = txtFecha.getText();
				String hora = txtHora.getText();
				String motivo = txtMotivo.getText();
				int estado = 0;

				if (fecha.isEmpty() || hora.isEmpty()) {
					JOptionPane.showMessageDialog(this, "Complete todos los campos.");
					return;
				}

				clsCita c = new clsCita(num, codPaciente, codMedico, codConsultorio,
						fecha, hora, estado, motivo);

				String mensaje = dao.agregar(c);
				JOptionPane.showMessageDialog(this, mensaje);
				listarCitasEnTabla();
				limpiarCampos();

			} catch (Exception ex) {
				JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
			}
		}

		// MÉTODO PARA LIMPIAR CAMPOS 
		private void limpiarCampos() {
			txtNumCita.setText("" + dao.generarCorrelativo());
			cboPaciente.setModel(new DefaultComboBoxModel(new String[] {"Juan Perez", "Ana Torres", "Luis Garcia"}));
			cboPaciente.setSelectedIndex(0);
			cboMedico.setModel(new DefaultComboBoxModel(new String[] {"Dr. Gaspar", "Dra. Espinoza", "Dr. Navarro"}));
			cboMedico.setSelectedIndex(0);
			cboConsultorio.setModel(new DefaultComboBoxModel(new String[] {"301", "302", "303"}));
			cboConsultorio.setSelectedIndex(0);
			txtFecha.setText("");
			txtHora.setText("");
			txtMotivo.setText("");
			txtEstado.setText("0");
		}
		
		

	
		// MÉTODO PARA MODIFICAR CITA
		private void modificarCita() {
		    try {
		        // 1. Leer número de cita
		        int num = Integer.parseInt(txtNumCita.getText());

		        // 2. Buscar cita existente
		        clsCita actual = dao.buscar(num);

		        if (actual == null) {
		            JOptionPane.showMessageDialog(this, "La cita no existe.");
		            return;
		        }

		        // 3. Leer nuevos datos desde la GUI
		        int codPaciente = cboPaciente.getSelectedIndex();
		        int codMedico = cboMedico.getSelectedIndex();
		        int codConsultorio = cboConsultorio.getSelectedIndex();
		        String fecha = txtFecha.getText();
		        String hora = txtHora.getText();
		        String motivo = txtMotivo.getText();
		        int estado = Integer.parseInt(txtEstado.getText());

		        // 4. Validar campos vacíos
		        if (fecha.isEmpty() || hora.isEmpty()) {
		            JOptionPane.showMessageDialog(this, "Complete todos los campos.");
		            return;
		        }

		        // 5. Crear un nuevo objeto con los datos modificados
		        clsCita nueva = new clsCita(num, codPaciente, codMedico, codConsultorio,
		                                    fecha, hora, estado, motivo);

		        // 6. Llamar al DAO para modificar
		        String mensaje = dao.modificar(nueva);

		        // 7. Mostrar mensaje
		        JOptionPane.showMessageDialog(this, mensaje);
		        listarCitasEnTabla();

		        // 8. Limpiar campos
		        limpiarCampos();

		    } catch (Exception ex) {
		        JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
		    }
		}
		// MÉTODO PARA CANCELAR CITA
		private void cancelarCita() {
		    try {
		        // 1. Leer número de cita
		        int num = Integer.parseInt(txtNumCita.getText());

		        // 2. Buscar cita existente
		        clsCita c = dao.buscar(num);

		        if (c == null) {
		            JOptionPane.showMessageDialog(this, "La cita no existe.");
		            return;
		        }

		        // 3. Cambiar estado a 2 (cancelada)
		        String mensaje = dao.cancelar(num);

		        // 4. Mostrar mensaje
		        JOptionPane.showMessageDialog(this, mensaje);
		        listarCitasEnTabla();

		        // 5. Limpiar campos
		        limpiarCampos();

		    } catch (Exception ex) {
		        JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
		    }
		}
		// MÉTODO PARA BUSCAR CITA
		private void buscarCita() {
		    try {
		        int num = Integer.parseInt(txtNumCita.getText());

		        clsCita c = dao.buscar(num);

		        if (c == null) {
		            JOptionPane.showMessageDialog(this, "La cita no existe.");
		            return;
		        }

		        // Como por ahora usas índices, cargamos por índice
		        cboPaciente.setSelectedIndex(c.getCodPaciente());
		        cboMedico.setSelectedIndex(c.getCodMedico());
		        cboConsultorio.setSelectedIndex(c.getCodConsultorio());
		        txtFecha.setText(c.getFecha());
		        txtHora.setText(c.getHora());
		        txtMotivo.setText(c.getMotivo());
		        txtEstado.setText(String.valueOf(c.getEstado()));

		        JOptionPane.showMessageDialog(this, "Cita encontrada.");

		    } catch (Exception ex) {
		        JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
		    }
		}
		
		
		// MÉTODO PARA LISTAR CITAS EN LA TABLA
		private void listarCitasEnTabla() {
		    DefaultTableModel modelo = (DefaultTableModel) tbCita.getModel();
		    modelo.setRowCount(0); // Limpiar tabla

		    for (clsCita c : dao.listar()) {

		        String paciente = cboPaciente.getItemAt(c.getCodPaciente()).toString();
		        String medico = cboMedico.getItemAt(c.getCodMedico()).toString();
		        String consultorio = cboConsultorio.getItemAt(c.getCodConsultorio()).toString();

		        // Convertir estado numérico a texto
		        String estadoTexto = "";
		        switch (c.getEstado()) {
		            case 0: estadoTexto = "Pendiente"; break;
		            case 1: estadoTexto = "Atendida"; break;
		            case 2: estadoTexto = "Cancelada"; break;
		        }

		        Object[] fila = {
		            c.getNumCita(),
		            paciente,
		            medico,
		            consultorio,
		            c.getFecha(),
		            c.getHora(),
		            c.getMotivo(),
		            estadoTexto
		        };

		        modelo.addRow(fila);
		    }
		}
		
		private void atenderCita() {
		    try {
		        int num = Integer.parseInt(txtNumCita.getText());

		        clsCita c = dao.buscar(num);

		        if (c == null) {
		            JOptionPane.showMessageDialog(this, "La cita no existe.");
		            return;
		        }

		        String mensaje = dao.atender(num);
		        JOptionPane.showMessageDialog(this, mensaje);

		        listarCitasEnTabla();
		        limpiarCampos();

		    } catch (Exception ex) {
		        JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
		    }
		}
	
}
















