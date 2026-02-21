
package arrays;

import clases.clsPaciente;
import java.util.ArrayList;

public class arrayPaciente {

    private ArrayList<clsPaciente> lista;

    public arrayPaciente(){
        lista=new ArrayList<>();
    }

    public void adicionar(clsPaciente p){
        lista.add(p);
    }

    public clsPaciente buscarDni(String dni){
        for(clsPaciente p:lista){
            if(p.getDni().equals(dni))
                return p;
        }
        return null;
    }

    public ArrayList<clsPaciente> getLista(){
        return lista;
    }
}
