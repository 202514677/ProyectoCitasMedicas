package main;
import guis.FrmMenu;
import javax.swing.UIManager;

public class Main {
    public static void main(String[] args) {
        try { UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); } catch(Exception e){}
        new FrmMenu().setVisible(true);
    }
}