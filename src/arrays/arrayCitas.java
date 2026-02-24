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

    public int correlativo() {
        if (lista.isEmpty()) {
            return 1; 
        } else {
            return lista.get(lista.size() - 1).numCita + 1;
        }
    }

    public String adicionar(clsCita nueva) {
        for (clsCita c : lista) {
            if (c.estado == 0 || c.estado == 1) {
                if (c.fecha.equals(nueva.fecha) && c.hora.equals(nueva.hora)) {
                    
                    if (c.codConsultorio == nueva.codConsultorio) 
                        return "EL CONSULTORIO YA ESTÁ OCUPADO.";
                    
                    if (c.codMedico == nueva.codMedico) 
                        return "EL MÉDICO YA TIENE UNA CITA A ESA HORA.";
                    
                    if (c.codPaciente == nueva.codPaciente) 
                        return "EL PACIENTE YA TIENE UNA CITA A ESA HORA.";
                }
            }
        }        

        lista.add(nueva);
        guardar(); 
        return null;
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

    public void cargar() {
        try (BufferedReader br = new BufferedReader(new FileReader("citas.txt"))) {
            lista.clear(); 
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
            if (c.codPaciente == codPaciente) {
                resultado.add(c);
            }
        }
        return resultado;
    }

    public ArrayList<clsCita> buscarPorMedico(int codMedico) {
        ArrayList<clsCita> resultado = new ArrayList<>();
        for (clsCita c : lista) {
            if (c.codMedico == codMedico) {
                resultado.add(c);
            }
        }
        return resultado;
    }
    
    public ArrayList<clsCita> buscarPorConsultorio(int codConsultorio) {
        ArrayList<clsCita> resultado = new ArrayList<>();
        for (clsCita c : lista) {
            if (c.codConsultorio == codConsultorio) {
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