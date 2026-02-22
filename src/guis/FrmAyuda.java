package guis;
import javax.swing.*;
import java.awt.*;

public class FrmAyuda extends JFrame {
    
    public FrmAyuda() {
        setTitle("Acerca del Sistema - Integrantes");
        setSize(400, 320);
        setLayout(null);
        setLocationRelativeTo(null); 
        setResizable(false);        
        getContentPane().setBackground(new Color(245, 245, 245));

        JLabel lblTitulo = new JLabel("Integrantes Grupo 7", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Tahoma", Font.BOLD, 16));
        lblTitulo.setBounds(50, 20, 300, 30);
        add(lblTitulo);

        JTextArea txtNombres = new JTextArea();
        txtNombres.setText("1. Gustavo Hinostroza \n" +                           
                           "2. Luis Gonzales \n" +
                           "3. Franklin Gaspar\n" +
                           "4. Martin Aguayo");
        txtNombres.setFont(new Font("Monospaced", Font.BOLD, 14));
        txtNombres.setEditable(false);
        txtNombres.setOpaque(false); 
        txtNombres.setFocusable(false);
        
        txtNombres.setBounds(100, 70, 200, 150);
        add(txtNombres);        
       
        JLabel lblCurso = new JLabel("Curso: Algoritmos y Estructura de Datos", SwingConstants.CENTER);
        lblCurso.setFont(new Font("Tahoma", Font.ITALIC, 11));
        lblCurso.setBounds(10, 240, 360, 20);
        add(lblCurso);

        validate(); 
        repaint();
    }
}