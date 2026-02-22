package clases;
public class clsPaciente {
    public int codPaciente, edad, estado;
    public String nombres, apellidos, dni, celular, correo;
    public clsPaciente(int cp, String nom, String ape, String dni, int ed, String cel, String cor, int est) {
        this.codPaciente = cp; this.nombres = nom; this.apellidos = ape;
        this.dni = dni; this.edad = ed; this.celular = cel; this.correo = cor; this.estado = est;
    }
}