package clases;
public class clsConsultorio {
    public int codConsultorio, piso, capacidad, estado;
    public String nombre, ubicacion;
    public clsConsultorio(int cc, String nom, int pis, int cap, int est) {
        this.codConsultorio = cc; this.nombre = nom; this.piso = pis; this.capacidad = cap; this.estado = est;
    }
}

