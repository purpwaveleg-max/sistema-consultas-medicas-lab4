package edu.ifma.lbd.consultas.repository;

import edu.ifma.lbd.consultas.domain.Medico;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import java.util.List;

public class MedicoRepository {

    private final EntityManager manager;
    private final DAOGenerico<Medico> daoGenerico;

    public MedicoRepository(EntityManager manager) {
        this.manager = manager;
        this.daoGenerico = new DAOGenerico<>(manager);
    }

    public Medico buscaPor(Integer id) {
        return daoGenerico.buscaPorId(Medico.class, id);
    }

    public List<Medico> todos() {
        return daoGenerico.todos(Medico.class);
    }

    public Medico buscaPorCrm(String crm) {
        try {
            return manager.createQuery(
                    "from Medico m where m.crm = :crm", Medico.class)
                    .setParameter("crm", crm)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    public List<Medico> buscaPorNome(String nome) {
        return manager.createQuery(
                "from Medico m where lower(m.nome) like :nome", Medico.class)
                .setParameter("nome", "%" + nome.toLowerCase() + "%")
                .getResultList();
    }

    public Medico salvaOuAtualiza(Medico medico) {
        return daoGenerico.salvaOuAtualiza(medico);
    }

    public void remove(Medico medico) {
        daoGenerico.remove(medico);
    }
}
