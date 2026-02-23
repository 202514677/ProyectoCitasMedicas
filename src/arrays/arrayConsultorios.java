package arrays;
import clases.clsConsultorio;
import java.util.*;
import java.io.*;

public class arrayConsultorios {
    private ArrayList<clsConsultorio> lista;

    public arrayConsultorios() {
        lista = new ArrayList<>();
        cargar();
    }

    public clsConsultorio obtenerPorCodigo(int cod) {
        for (int i = 0; i < lista.size(); i++) {
            if (lista.get(i).codConsultorio == cod) {
                return lista.get(i);
            }
        }
        return null;
    }

    public clsConsultorio buscar(int cod) {
        return obtenerPorCodigo(cod);
    }

    public void adicionar(clsConsultorio x) { 
        lista.add(x); 
        guardar(); 
    }

    public int tamaño() { return lista.size(); }
    
    public clsConsultorio obtener(int i) { return lista.get(i); }

    public int correlativo() { 
        return (lista.isEmpty()) ? 501 : lista.get(lista.size()-1).codConsultorio + 1; 
    }

    public boolean existeNombre(String nombre) {
        for (clsConsultorio c : lista) {
            if (c.nombre.equalsIgnoreCase(nombre)) return true;
        }
        return false;
    }

    public void guardar() {
        try (PrintWriter pw = new PrintWriter(new FileWriter("consultorios.txt"))) {
            for (clsConsultorio c : lista) {
                pw.println(c.codConsultorio + ";" + c.nombre + ";" + c.piso + ";" + 
                           c.capacidad + ";" + c.estado);
            }
        } catch (Exception e) {
            System.out.println("Error al guardar consultorios: " + e.getMessage());
        }
    }

    private void cargar() {
        try (BufferedReader br = new BufferedReader(new FileReader("consultorios.txt"))) {
            String s;
            while ((s = br.readLine()) != null) {
                String[] d = s.split(";");
                lista.add(new clsConsultorio(
                    Integer.parseInt(d[0]), 
                    d[1], 
                    Integer.parseInt(d[2]), 
                    Integer.parseInt(d[3]), 
                    Integer.parseInt(d[4])
                ));
            }
        } catch (Exception e) {
        }
    }
}