
package arrays;

import clases.clsCita;
import java.util.ArrayList;

public class arrayCita {

    private ArrayList<clsCita> lista;

    public arrayCita(){
        lista=new ArrayList<>();
    }

    public void adicionar(clsCita c){
        lista.add(c);
    }

    public ArrayList<clsCita> getLista(){
        return lista;
    }
}
