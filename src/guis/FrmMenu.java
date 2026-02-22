package guis;
import javax.swing.*;
import java.awt.event.*;

public class FrmMenu extends JFrame implements ActionListener {
    private JMenuBar mb;
    private JMenu mMan, mReg, mCon, mRep, mAyu;    
   
    private JMenuItem iPac, iMed, iCon;
    private JMenuItem iCita;
    private JMenuItem iConPac, iConMed, iConCon, iConFec;
    private JMenuItem iRepPen, iRepEst, iRepDia;
    private JMenuItem iIntegrantes;

    public FrmMenu() {
        setTitle("SISTEMA DE RESERVA DE CITAS MÉDICAS");
        setExtendedState(MAXIMIZED_BOTH);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        
        mb = new JMenuBar();              
      
        JLabel fondo = new JLabel(new ImageIcon("hospital.jpg")); 
        fondo.setBounds(0, 0, 1920, 1080);
        add(fondo);       
               
        mMan = new JMenu("Mantenimiento");
        iPac = new JMenuItem("Pacientes");
        iMed = new JMenuItem("Médicos");
        iCon = new JMenuItem("Consultorios");
        mMan.add(iPac); mMan.add(iMed); mMan.add(iCon);  
      
        mReg = new JMenu("Registro");
        iCita = new JMenuItem("Reservar / Gestionar Citas");
        mReg.add(iCita);     
                     
        mCon = new JMenu("Consulta");
        iConPac = new JMenuItem("Por Paciente (DNI)");
        iConMed = new JMenuItem("Por Médico (Agenda)");
        iConCon = new JMenuItem("Por Consultorio (Ocupación)");
        iConFec = new JMenuItem("Por Fecha (Listado General)");
        mCon.add(iConPac); mCon.add(iConMed); mCon.add(iConCon); mCon.add(iConFec);
    
        mRep = new JMenu("Reportes");
        iRepPen = new JMenuItem("Pacientes con Citas Pendientes");
        iRepEst = new JMenuItem("Estadísticas por Médico");
        iRepDia = new JMenuItem("Agenda del Día");
        mRep.add(iRepPen); mRep.add(iRepEst); mRep.add(iRepDia);     
   
        mAyu = new JMenu("Ayuda");
        iIntegrantes = new JMenuItem("Integrantes del Grupo");
        mAyu.add(iIntegrantes);   
      
        iPac.addActionListener(this); 
        iMed.addActionListener(this); 
        iCon.addActionListener(this);
        iCita.addActionListener(this); 
        iConPac.addActionListener(this); 
        iConMed.addActionListener(this);
        iConCon.addActionListener(this);
        iConFec.addActionListener(this);
        iRepPen.addActionListener(this);
        iRepEst.addActionListener(this);
        iRepDia.addActionListener(this);
        iIntegrantes.addActionListener(this);
      
        mb.add(mMan); mb.add(mReg); mb.add(mCon); mb.add(mRep); mb.add(mAyu);
        setJMenuBar(mb);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        
       if(e.getSource() == iPac) new FrmPaciente().setVisible(true);
        if(e.getSource() == iMed) new FrmMedico().setVisible(true);
        if(e.getSource() == iCon) new FrmConsultorio().setVisible(true);    
       
        if(e.getSource() == iCita) new FrmCita().setVisible(true);        
       
        if(e.getSource() == iConPac) new FrmConsultas(null).setVisible(true);
        /*   if(e.getSource() == iConMed) new FrmConsultaMedico().setVisible(true);
        if(e.getSource() == iConCon) new FrmConsultaConsultorio().setVisible(true); 
        if(e.getSource() == iConFec) new FrmConsultaFecha().setVisible(true);
       
        if(e.getSource() == iRepPen) new FrmReportePendientes().setVisible(true);
        if(e.getSource() == iRepEst) new FrmReporteEstadistico().setVisible(true);
        if(e.getSource() == iRepDia) new FrmConsultaFecha().setVisible(true);   */    
       
        if(e.getSource() == iIntegrantes) new FrmAyuda().setVisible(true); 
    }
}