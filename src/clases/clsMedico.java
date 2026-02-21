
package clases;

public class clsMedico {
    private int codigo;
    private String nombres;
    private String especialidad;
    private String cmp;
    private int estado;

    public clsMedico(int c,String n,String e,String cmp,int es){
        codigo=c; nombres=n; especialidad=e;
        this.cmp=cmp; estado=es;
    }

    public int getCodigo(){ return codigo; }
}
