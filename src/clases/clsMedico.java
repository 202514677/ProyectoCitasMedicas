package clases;
public class clsMedico {
    public int codMedico, estado;
    public String nombres, apellidos, especialidad, cmp;
    public clsMedico(int cm, String nom, String ape, String esp, String cmp, int est) {
        this.codMedico = cm; this.nombres = nom; this.apellidos = ape;
        this.especialidad = esp; this.cmp = cmp; this.estado = est;
    }
}

