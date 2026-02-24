package clases;

import java.util.ArrayList;

public class CitaDAO {

    private ArrayList<clsCita> citas;

    public CitaDAO() {
        citas = new ArrayList<>();
    }
   
    public int generarCorrelativo() {
        if (citas.isEmpty())
            return 001;
        return citas.get(citas.size() - 1).getNumCita() + 1;
    }


    public String agregar(clsCita c) {

        if (!validarDisponibilidad(c))
            return "Conflicto: Médico, consultorio o paciente ya tiene cita en esa fecha/hora.";

        citas.add(c);
        return "Cita registrada correctamente.";
    }


    public clsCita buscar(int num) {
        for (clsCita c : citas) {
            if (c.getNumCita() == num)
                return c;
        }
        return null;
    }


    public String modificar(clsCita nueva) {
        clsCita actual = buscar(nueva.getNumCita());
        if (actual == null)
            return "Cita no encontrada.";


        if (!validarDisponibilidad(nueva))
            return "Conflicto: No se puede reprogramar por choque de horario.";


        actual.setFecha(nueva.getFecha());
        actual.setHora(nueva.getHora());
        actual.setCodConsultorio(nueva.getCodConsultorio());
        actual.setMotivo(nueva.getMotivo());

        return "Cita modificada correctamente.";
    }


    public String cancelar(int num) {
        clsCita c = buscar(num);
        if (c == null)
            return "Cita no encontrada.";

        c.setEstado(2); 
        return "Cita cancelada correctamente.";
    }
    
    public String atender(int num) {
        for (clsCita c : citas) {
            if (c.getNumCita() == num) {
                c.setEstado(1);
                return "Cita marcada como atendida.";
            }
        }
        return "No se encontró la cita.";
    }

 
    public ArrayList<clsCita> listar() {
        return citas;
    }


    private boolean validarDisponibilidad(clsCita nueva) {
        for (clsCita c : citas) {

            if (c.getEstado() == 2)
                continue;

            boolean mismoMomento = 
                c.getFecha().equals(nueva.getFecha()) &&
                c.getHora().equals(nueva.getHora());

            if (mismoMomento) {

                if (c.getCodMedico() == nueva.getCodMedico())
                    return false;

                if (c.getCodConsultorio() == nueva.getCodConsultorio())
                    return false;

                if (c.getCodPaciente() == nueva.getCodPaciente())
                    return false;
            }
        }
        return true;
    }
}