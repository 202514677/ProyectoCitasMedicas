package arrays;
import clases.*;
import java.util.*;
import java.io.*;

public class arrayCitas {
    private ArrayList<clsCita> lista;

    public arrayCitas() {
        lista = new ArrayList<>();
        cargar();
    }
    
    public ArrayList<clsCita> getLista() { 
        return lista; 
    }

    public void adicionar(clsCita x) { 
        lista.add(x); 
        guardar(); 
    }

    public int correlativo() { 
        return (lista.isEmpty()) ? 10001 : lista.get(lista.size()-1).numCita + 1; 
    }
    
    public String validarReservaEspecial(clsPaciente p, clsMedico m, clsConsultorio c, String fec, String hor) {
        if (p.estado != 1) return "Error: El paciente está INACTIVO.";
        if (m.estado != 1) return "Error: El médico está INACTIVO.";
        if (c.estado != 1) return "Error: El consultorio está INACTIVO.";
      
        for (clsCita cita : lista) {
            if (cita.estado == 0) { 
                if (cita.fecha.equals(fec) && cita.hora.equals(hor)) {
                    if (cita.codMedico == m.codMedico) return "Médico ocupado en esa fecha/hora.";
                    if (cita.codConsultorio == c.codConsultorio) return "Consultorio ocupado en esa fecha/hora.";
                    if (cita.codPaciente == p.codPaciente) return "El paciente ya tiene otra cita en esa fecha/hora.";
                }
            }
        }
        return null; 
    }
   
    public boolean tieneCitasPendientes(int codigo, int tipo) {
        for (clsCita c : lista) {
            if (c.estado == 0) { 
                if (tipo == 1 && c.codPaciente == codigo) return true;
                if (tipo == 2 && c.codMedico == codigo) return true;
                if (tipo == 3 && c.codConsultorio == codigo) return true;
            }
        }
        return false;
    }

    public void guardar() {
        try (PrintWriter pw = new PrintWriter(new FileWriter("citas.txt"))) {
            for(clsCita c : lista) {
                pw.println(c.numCita+";"+c.codPaciente+";"+c.codMedico+";"+
                           c.codConsultorio+";"+c.fecha+";"+c.hora+";"+
                           c.estado+";"+c.motivo);
            }
        } catch (Exception e) {
            System.out.println("Error al guardar: " + e.getMessage());
        }
    }

    private void cargar() {
        try (BufferedReader br = new BufferedReader(new FileReader("citas.txt"))) {
            String s;
            while((s = br.readLine()) != null) {
                String[] d = s.split(";");
                lista.add(new clsCita(
                    Integer.parseInt(d[0]), 
                    Integer.parseInt(d[1]), 
                    Integer.parseInt(d[2]), 
                    Integer.parseInt(d[3]), 
                    d[4], d[5], 
                    Integer.parseInt(d[6]), 
                    d[7]
                ));
            }
        } catch (Exception e) {
        }
    }

    public ArrayList<clsCita> buscarPorPaciente(int codPaciente) {

        ArrayList<clsCita> resultado = new ArrayList<>();

        for (clsCita c : lista) {
            if (c.codPaciente == codPaciente && c.estado == 0) {
                resultado.add(c);
            }
        }

        return resultado;
    }

    public ArrayList<clsCita> buscarPorFecha(String fecha) {

        ArrayList<clsCita> resultado = new ArrayList<>();

        for (clsCita c : lista) {
            if (c.fecha.equals(fecha)) {
                resultado.add(c);
            }
        }

        return resultado;
    }

}