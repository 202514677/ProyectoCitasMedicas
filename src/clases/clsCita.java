
package clases;

public class clsCita {
    private int numero;
    private int codPaciente;
    private int codMedico;
    private int codConsultorio;
    private String fecha;
    private String hora;
    private int estado;

    public clsCita(int n,int p,int m,int c,String f,String h,int e){
        numero=n; codPaciente=p; codMedico=m;
        codConsultorio=c; fecha=f; hora=h; estado=e;
    }
}
