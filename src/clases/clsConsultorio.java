
package clases;

public class clsConsultorio {
    private int codigo;
    private String nombre;
    private int piso;
    private int estado;

    public clsConsultorio(int c,String n,int p,int e){
        codigo=c; nombre=n; piso=p; estado=e;
    }

    public String getNombre(){ return nombre; }
}
