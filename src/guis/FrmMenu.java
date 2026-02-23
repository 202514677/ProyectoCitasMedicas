package guis;

import javax.swing.*;
import java.awt.event.*;
import arrays.*;
import java.awt.*;

public class FrmMenu extends JFrame implements ActionListener {
    
    private static final long serialVersionUID = 1L;
    private JMenuBar mb;
    private JMenu mMan, mReg, mCon, mRep, mAyu;    
   
    private JMenuItem iPac, iMed, iCon;
    private JMenuItem iCita;
    private JMenuItem iConPac, iConFec;
    private JMenuItem iRepPen, iRepEst; 
    private JMenuItem iIntegrantes;    

    private arrayCitas ac = new arrayCitas();

    public FrmMenu() {
        setTitle("SISTEMA DE RESERVA DE CITAS MÉDICAS ");
        
        // 1. Establecer un tamaño inicial para cuando se restaure
        setSize(1024, 768);
        // 2. Establecer un tamaño mínimo para que no se pierda el foco
        setMinimumSize(new Dimension(800, 600));
        // 3. Maximizar por defecto
        setExtendedState(MAXIMIZED_BOTH);
        
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
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
        iConPac = new JMenuItem("Consulta General (Multicriterio)");
        iConFec = new JMenuItem("Por Fecha (Listado General)");
        mCon.add(iConPac); mCon.add(iConFec);
    
        mRep = new JMenu("Reportes");
        iRepPen = new JMenuItem("Pacientes con Citas Pendientes");
        iRepEst = new JMenuItem("Estadísticas por Médico");

        mRep.add(iRepPen); mRep.add(iRepEst);     
   
        mAyu = new JMenu("Ayuda");
        iIntegrantes = new JMenuItem("Integrantes del Grupo");
        mAyu.add(iIntegrantes);   
      
        iPac.addActionListener(this); 
        iMed.addActionListener(this); 
        iCon.addActionListener(this);
        iCita.addActionListener(this); 
        iConPac.addActionListener(this); 
        iConFec.addActionListener(this);
        iRepPen.addActionListener(this);
        iRepEst.addActionListener(this);
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
       
        if (e.getSource() == iConPac) {
            new FrmConsultas(ac).setVisible(true); 
        }
        if(e.getSource() == iConFec) new FrmConsultaFecha().setVisible(true);
       
        if(e.getSource() == iRepPen) new FrmReportePendientes().setVisible(true);
        if(e.getSource() == iRepEst) new FrmReporteEstadistico().setVisible(true);
       
        if(e.getSource() == iIntegrantes) new FrmAyuda().setVisible(true); 
    }
}