package edu.ifma.lbd.consultas.repository;

import edu.ifma.lbd.consultas.domain.Consulta;
import edu.ifma.lbd.consultas.domain.Paciente;
import edu.ifma.lbd.consultas.domain.Prontuario;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import java.util.List;

public class ProntuarioRepository {

    private final EntityManager manager;
    private final DAOGenerico<Prontuario> daoGenerico;

    public ProntuarioRepository(EntityManager manager) {
        this.manager = manager;
        this.daoGenerico = new DAOGenerico<>(manager);
    }

    public Prontuario buscaPor(Integer id) {
        return daoGenerico.buscaPorId(Prontuario.class, id);
    }

    public List<Prontuario> todos() {
        return daoGenerico.todos(Prontuario.class);
    }

    // busca o prontuario da consulta
    public Prontuario buscaPorConsulta(Consulta consulta) {
        try {
            return manager.createQuery(
                    "from Prontuario p where p.consulta = :consulta", Prontuario.class)
                    .setParameter("consulta", consulta)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    // recupera historico de prontuarios do paciente com prescricoes e exames carregados
    public List<Prontuario> buscaHistoricoPorPaciente(Paciente paciente) {
        return manager.createQuery(
                "select distinct p from Prontuario p join p.consulta c where c.paciente = :paciente order by c.dataHora desc",
                Prontuario.class)
                .setParameter("paciente", paciente)
                .getResultList();
    }

    public Prontuario salvaOuAtualiza(Prontuario prontuario) {
        return daoGenerico.salvaOuAtualiza(prontuario);
    }

    public void remove(Prontuario prontuario) {
        daoGenerico.remove(prontuario);
    }
}
