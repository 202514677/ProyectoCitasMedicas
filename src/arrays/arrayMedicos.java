package arrays;
import clases.clsMedico;
import java.util.*;
import java.io.*;

public class arrayMedicos {
    private ArrayList<clsMedico> lista;
    public arrayMedicos() {
        lista = new ArrayList<>();
        cargar();
    }
    public void adicionar(clsMedico x) { lista.add(x); guardar(); }
    public int tamaño() { return lista.size(); }
    public clsMedico obtener(int i) { return lista.get(i); }
    public clsMedico buscar(int cod) {
        for (int i = 0; i < lista.size(); i++) {
            if (lista.get(i).codMedico == cod) {
                return lista.get(i);
            }
        }
        return null;
    }
    public int correlativo() { return (lista.isEmpty()) ? 2001 : lista.get(lista.size()-1).codMedico + 1; }
    public void guardar() {
        try (PrintWriter pw = new PrintWriter(new FileWriter("medicos.txt"))) {
            for (clsMedico m : lista) pw.println(m.codMedico+";"+m.nombres+";"+m.apellidos+";"+m.especialidad+";"+m.cmp+";"+m.estado);
        } catch (Exception e) {}
    }
    private void cargar() {
        try (BufferedReader br = new BufferedReader(new FileReader("medicos.txt"))) {
            String s;
            while ((s = br.readLine()) != null) {
                String[] d = s.split(";");
                lista.add(new clsMedico(Integer.parseInt(d[0]), d[1], d[2], d[3], d[4], Integer.parseInt(d[5])));
            }
        } catch (Exception e) {}
    }
     
    public boolean existeCmp(String cmp) {
        if (cmp.equals("")) return false; 
        for (clsMedico m : lista) {
            if (m.cmp.equals(cmp)) return true;
        }
        return false;
    }
 
    public clsMedico obtenerPorCodigo(int cod) {
        for (clsMedico m : lista) {
            if (m.codMedico == cod) return m;
        }
        return null;
    }
    
   
       
}