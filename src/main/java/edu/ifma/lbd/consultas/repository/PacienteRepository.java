package edu.ifma.lbd.consultas.repository;

import edu.ifma.lbd.consultas.domain.Paciente;
import jakarta.persistence.EntityManager;
import java.util.List;

public class PacienteRepository {

    private final EntityManager manager;
    private final DAOGenerico<Paciente> daoGenerico;

    public PacienteRepository(EntityManager manager) {
        this.manager = manager;
        this.daoGenerico = new DAOGenerico<>(manager);
    }

    public Paciente buscaPor(Integer id) {
        return daoGenerico.buscaPorId(Paciente.class, id);
    }

    public List<Paciente> todos() {
        return daoGenerico.todos(Paciente.class);
    }

    // busca por aproximacao de nome da crianca
    public List<Paciente> buscaPorNomeCrianca(String nome) {
        return manager.createQuery(
                "from Paciente p where lower(p.nomeCrianca) like :nome", Paciente.class)
                .setParameter("nome", "%" + nome.toLowerCase() + "%")
                .getResultList();
    }

    // busca por aproximacao do responsavel
    public List<Paciente> buscaPorNomeResponsavel(String nomeResponsavel) {
        return manager.createQuery(
                "from Paciente p where lower(p.nomeResponsavel) like :nome", Paciente.class)
                .setParameter("nome", "%" + nomeResponsavel.toLowerCase() + "%")
                .getResultList();
    }

    public Paciente salvaOuAtualiza(Paciente paciente) {
        return daoGenerico.salvaOuAtualiza(paciente);
    }

    public void remove(Paciente paciente) {
        daoGenerico.remove(paciente);
    }
}
