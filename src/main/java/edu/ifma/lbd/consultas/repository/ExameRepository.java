package edu.ifma.lbd.consultas.repository;

import edu.ifma.lbd.consultas.domain.Exame;
import jakarta.persistence.EntityManager;
import java.util.List;

public class ExameRepository {

    private final EntityManager manager;
    private final DAOGenerico<Exame> daoGenerico;

    public ExameRepository(EntityManager manager) {
        this.manager = manager;
        this.daoGenerico = new DAOGenerico<>(manager);
    }

    public Exame buscaPor(Integer id) {
        return daoGenerico.buscaPorId(Exame.class, id);
    }

    // lista todos os exames disponiveis para solicitacao
    public List<Exame> todos() {
        return manager.createQuery("from Exame e order by e.nome", Exame.class)
                .getResultList();
    }

    public List<Exame> buscaPorNome(String nome) {
        return manager.createQuery(
                "from Exame e where lower(e.nome) like :nome order by e.nome", Exame.class)
                .setParameter("nome", "%" + nome.toLowerCase() + "%")
                .getResultList();
    }

    public Exame salvaOuAtualiza(Exame exame) {
        return daoGenerico.salvaOuAtualiza(exame);
    }

    public void remove(Exame exame) {
        daoGenerico.remove(exame);
    }
}
