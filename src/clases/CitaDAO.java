package clases;

import java.util.ArrayList;

public class CitaDAO {

    private ArrayList<clsCita> citas;

    public CitaDAO() {
        citas = new ArrayList<>();
    }

    // Generar número correlativo
    public int generarCorrelativo() {
        if (citas.isEmpty())
            return 1;
        return citas.get(citas.size() - 1).getNumCita() + 1;
    }

    // Agregar cita
    public String agregar(clsCita c) {
        // Validaciones
        if (!validarDisponibilidad(c))
            return "Conflicto: Médico, consultorio o paciente ya tiene cita en esa fecha/hora.";

        citas.add(c);
        return "Cita registrada correctamente.";
    }

    // Buscar cita por número
    public clsCita buscar(int num) {
        for (clsCita c : citas) {
            if (c.getNumCita() == num)
                return c;
        }
        return null;
    }

    // Modificar cita
    public String modificar(clsCita nueva) {
        clsCita actual = buscar(nueva.getNumCita());
        if (actual == null)
            return "Cita no encontrada.";

        // Validar disponibilidad
        if (!validarDisponibilidad(nueva))
            return "Conflicto: No se puede reprogramar por choque de horario.";

        // Actualizar datos
        actual.setFecha(nueva.getFecha());
        actual.setHora(nueva.getHora());
        actual.setCodConsultorio(nueva.getCodConsultorio());
        actual.setMotivo(nueva.getMotivo());

        return "Cita modificada correctamente.";
    }

    // Cancelar cita
    public String cancelar(int num) {
        clsCita c = buscar(num);
        if (c == null)
            return "Cita no encontrada.";

        c.setEstado(2); // 2 = cancelada
        return "Cita cancelada correctamente.";
    }

    // Listar todas las citas
    public ArrayList<clsCita> listar() {
        return citas;
    }

    // Validar disponibilidad
    private boolean validarDisponibilidad(clsCita nueva) {
        for (clsCita c : citas) {

            // Ignorar citas canceladas
            if (c.getEstado() == 2)
                continue;

            // Mismo día y misma hora
            boolean mismoMomento = 
                c.getFecha().equals(nueva.getFecha()) &&
                c.getHora().equals(nueva.getHora());

            if (mismoMomento) {

                // Mismo médico
                if (c.getCodMedico() == nueva.getCodMedico())
                    return false;

                // Mismo consultorio
                if (c.getCodConsultorio() == nueva.getCodConsultorio())
                    return false;

                // Mismo paciente
                if (c.getCodPaciente() == nueva.getCodPaciente())
                    return false;
            }
        }
        return true;
    }
}