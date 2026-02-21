
package guis;

import javax.swing.*;

public class frmPrincipal extends JFrame {

    public frmPrincipal(){

        setTitle("Sistema de Citas Médicas");
        setSize(600,450);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JMenuBar barra=new JMenuBar();

        JMenu mMante=new JMenu("Mantenimiento");
        JMenu mCitas=new JMenu("Citas");
        JMenu mConsulta=new JMenu("Consultas");

        JMenuItem itPaciente=new JMenuItem("Pacientes");
        JMenuItem itCita=new JMenuItem("Registrar Cita");

        mMante.add(itPaciente);
        mCitas.add(itCita);

        barra.add(mMante);
        barra.add(mCitas);
        barra.add(mConsulta);

        setJMenuBar(barra);
    }

    public static void main(String[] args){
        new frmPrincipal().setVisible(true);
    }
}
