
package clases;

public class clsPaciente {
    private int codigo;
    private String nombres;
    private String apellidos;
    private String dni;
    private int estado;

    public clsPaciente(int c,String n,String a,String d,int e){
        codigo=c; nombres=n; apellidos=a; dni=d; estado=e;
    }

    public String getDni(){ return dni; }
    public int getCodigo(){ return codigo; }
}
