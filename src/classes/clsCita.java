package classes;

public class clsCita {
	// Atributos
    private int numCita;
    private int codPaciente;
    private int codMedico;
    private int codConsultorio;
    private String fecha;
    private String hora;
    private int estado;     // 0 = pendiente, 1 = atendida, 2 = cancelada
    private String motivo;

    /* Constructor:
    Este método se ejecuta cuando creas una cita nueva.
	Recibe todos los datos necesarios.

     */
    public clsCita(int numCita, int codPaciente, int codMedico, int codConsultorio,
                String fecha, String hora, int estado, String motivo) {

        this.numCita = numCita;
        this.codPaciente = codPaciente;
        this.codMedico = codMedico;
        this.codConsultorio = codConsultorio;
        this.fecha = fecha;
        this.hora = hora;
        this.estado = estado;
        this.motivo = motivo;
    }

    // Getters y Setters
    public int getNumCita() {
        return numCita;
    }

    public void setNumCita(int numCita) {
        this.numCita = numCita;
    }

    public int getCodPaciente() {
        return codPaciente;
    }

    public void setCodPaciente(int codPaciente) {
        this.codPaciente = codPaciente;
    }

    public int getCodMedico() {
        return codMedico;
    }

    public void setCodMedico(int codMedico) {
        this.codMedico = codMedico;
    }

    public int getCodConsultorio() {
        return codConsultorio;
    }

    public void setCodConsultorio(int codConsultorio) {
        this.codConsultorio = codConsultorio;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }


}
