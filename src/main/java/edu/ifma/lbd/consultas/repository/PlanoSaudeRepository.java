package edu.ifma.lbd.consultas.repository;

import edu.ifma.lbd.consultas.domain.PlanoSaude;
import jakarta.persistence.EntityManager;
import java.util.List;

public class PlanoSaudeRepository {

    private final EntityManager manager;
    private final DAOGenerico<PlanoSaude> daoGenerico;

    public PlanoSaudeRepository(EntityManager manager) {
        this.manager = manager;
        this.daoGenerico = new DAOGenerico<>(manager);
    }

    public PlanoSaude buscaPor(Integer id) {
        return daoGenerico.buscaPorId(PlanoSaude.class, id);
    }

    public List<PlanoSaude> todos() {
        return manager.createQuery("from PlanoSaude p order by p.nomePlano", PlanoSaude.class)
                .getResultList();
    }

    public List<PlanoSaude> buscaPorNome(String nome) {
        return manager.createQuery(
                "from PlanoSaude p where lower(p.nomePlano) like :nome order by p.nomePlano", PlanoSaude.class)
                .setParameter("nome", "%" + nome.toLowerCase() + "%")
                .getResultList();
    }

    public PlanoSaude salvaOuAtualiza(PlanoSaude plano) {
        return daoGenerico.salvaOuAtualiza(plano);
    }

    public void remove(PlanoSaude plano) {
        daoGenerico.remove(plano);
    }
}
