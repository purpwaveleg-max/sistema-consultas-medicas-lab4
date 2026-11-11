package edu.ifma.lbd.consultas.repository;

import edu.ifma.lbd.consultas.domain.Consulta;
import edu.ifma.lbd.consultas.domain.Medico;
import edu.ifma.lbd.consultas.domain.Paciente;
import jakarta.persistence.EntityManager;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

public class ConsultaRepository {

    private final EntityManager manager;
    private final DAOGenerico<Consulta> daoGenerico;

    public ConsultaRepository(EntityManager manager) {
        this.manager = manager;
        this.daoGenerico = new DAOGenerico<>(manager);
    }

    public Consulta buscaPor(Integer id) {
        return daoGenerico.buscaPorId(Consulta.class, id);
    }

    public List<Consulta> todas() {
        return daoGenerico.todos(Consulta.class);
    }

    // consultas agendadas para o dia especificado
    public List<Consulta> buscaConsultasDoDia(LocalDate data) {
        LocalDateTime inicio = data.atStartOfDay();
        LocalDateTime fim = data.atTime(LocalTime.MAX);

        return manager.createQuery(
                "from Consulta c where c.dataHora between :inicio and :fim and c.agendada = true order by c.dataHora",
                Consulta.class)
                .setParameter("inicio", inicio)
                .setParameter("fim", fim)
                .getResultList();
    }

    // consultas de um medico em determinado dia
    public List<Consulta> buscaAgendadasPorMedicoEData(Medico medico, LocalDate data) {
        LocalDateTime inicio = data.atStartOfDay();
        LocalDateTime fim = data.atTime(LocalTime.MAX);

        return manager.createQuery(
                "from Consulta c where c.medico = :medico and c.dataHora between :inicio and :fim and c.agendada = true order by c.dataHora",
                Consulta.class)
                .setParameter("medico", medico)
                .setParameter("inicio", inicio)
                .setParameter("fim", fim)
                .getResultList();
    }

    // historico completo de consultas de uma crianca
    public List<Consulta> buscaHistoricoPorPaciente(Paciente paciente) {
        return manager.createQuery(
                "from Consulta c where c.paciente = :paciente order by c.dataHora desc",
                Consulta.class)
                .setParameter("paciente", paciente)
                .getResultList();
    }

    public Consulta salvaOuAtualiza(Consulta consulta) {
        return daoGenerico.salvaOuAtualiza(consulta);
    }

    public void remove(Consulta consulta) {
        daoGenerico.remove(consulta);
    }
}
