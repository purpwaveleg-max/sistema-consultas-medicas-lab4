package edu.ifma.lbd.consultas.repository;

import edu.ifma.lbd.consultas.domain.EntidadeBase;
import jakarta.persistence.EntityManager;
import java.util.List;
import java.util.Objects;

// operacoes genericas de crud compartilhadas entre repositorios
class DAOGenerico<T extends EntidadeBase> {

    private final EntityManager manager;

    DAOGenerico(EntityManager manager) {
        this.manager = manager;
    }

    T buscaPorId(Class<T> clazz, Integer id) {
        return manager.find(clazz, id);
    }

    List<T> todos(Class<T> clazz) {
        return manager.createQuery("from " + clazz.getSimpleName(), clazz)
                .getResultList();
    }

    T salvaOuAtualiza(T t) {
        if (Objects.isNull(t.getId())) {
            manager.persist(t);
            return t;
        } else {
            return manager.merge(t);
        }
    }

    void remove(T t) {
        manager.remove(t);
        manager.flush();
    }
}
