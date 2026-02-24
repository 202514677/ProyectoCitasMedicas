package arrays;
import clases.clsPaciente;
import java.util.*;
import java.io.*;

public class arrayPacientes {
    private ArrayList<clsPaciente> lista;
    public arrayPacientes() {
        lista = new ArrayList<>();
        cargar();
    }
    public boolean adicionar(clsPaciente x) {
        if (existeDni(x.dni)) {
            return false; 
        }
        lista.add(x); 
        guardar(); 
        return true; 
    }
    public int tamaño() { return lista.size(); }
    public clsPaciente obtener(int i) { return lista.get(i); }
    public clsPaciente buscar(int cod) {
        for (clsPaciente p : lista) if (p.codPaciente == cod) return p;
        return null;
    }
    public clsPaciente buscarDni(String dni) {
        for (clsPaciente p : lista) if (p.dni.equals(dni)) return p;
        return null;
    }
    public int correlativo() { return (lista.isEmpty()) ? 1001 : lista.get(lista.size()-1).codPaciente + 1; }
    public void guardar() {
        try (PrintWriter pw = new PrintWriter(new FileWriter("pacientes.txt"))) {
            for (clsPaciente p : lista) pw.println(p.codPaciente+";"+p.nombres+";"+p.apellidos+";"+p.dni+";"+p.edad+";"+p.celular+";"+p.correo+";"+p.estado);
        } catch (Exception e) {}
    }
    private void cargar() {
        try (BufferedReader br = new BufferedReader(new FileReader("pacientes.txt"))) {
            String s;
            while ((s = br.readLine()) != null) {
                String[] d = s.split(";");
                lista.add(new clsPaciente(Integer.parseInt(d[0]), d[1], d[2], d[3], Integer.parseInt(d[4]), d[5], d[6], Integer.parseInt(d[7])));
            }
        } catch (Exception e) {}
    }
    public boolean existeDni(String dni) {
        for (clsPaciente p : lista) {
            if (p.dni.equals(dni)) return true;
        }
        return false;
    }    
    public clsPaciente obtenerPorCodigo(int cod) {
        for (int i = 0; i < lista.size(); i++) {
            if (lista.get(i).codPaciente == cod) {
                return lista.get(i);
            }
        }
        return null; 
    }
}