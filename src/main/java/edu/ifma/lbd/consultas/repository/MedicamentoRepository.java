package edu.ifma.lbd.consultas.repository;

import edu.ifma.lbd.consultas.domain.Medicamento;
import jakarta.persistence.EntityManager;
import java.util.List;

public class MedicamentoRepository {

    private final EntityManager manager;
    private final DAOGenerico<Medicamento> daoGenerico;

    public MedicamentoRepository(EntityManager manager) {
        this.manager = manager;
        this.daoGenerico = new DAOGenerico<>(manager);
    }

    public Medicamento buscaPor(Integer id) {
        return daoGenerico.buscaPorId(Medicamento.class, id);
    }

    // lista todos os medicamentos para prescricao
    public List<Medicamento> todos() {
        return manager.createQuery("from Medicamento m order by m.nome", Medicamento.class)
                .getResultList();
    }

    public List<Medicamento> buscaPorNome(String nome) {
        return manager.createQuery(
                "from Medicamento m where lower(m.nome) like :nome order by m.nome", Medicamento.class)
                .setParameter("nome", "%" + nome.toLowerCase() + "%")
                .getResultList();
    }

    public Medicamento salvaOuAtualiza(Medicamento medicamento) {
        return daoGenerico.salvaOuAtualiza(medicamento);
    }

    public void remove(Medicamento medicamento) {
        daoGenerico.remove(medicamento);
    }
}
