package edu.ifma.lbd.consultas.repository;

import edu.ifma.lbd.consultas.domain.Exame;
import edu.ifma.lbd.consultas.domain.Medicamento;
import edu.ifma.lbd.consultas.domain.PlanoSaude;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.junit.*;

import java.util.List;

import static org.junit.Assert.*;

public class CatalogoClinicoRepositoryTest {

    private static EntityManagerFactory factory;
    private EntityManager manager;
    private MedicamentoRepository medicamentoRepository;
    private ExameRepository exameRepository;
    private PlanoSaudeRepository planoRepository;

    @BeforeClass
    public static void setUpClass() {
        factory = Persistence.createEntityManagerFactory("consultas_medicas_test");
    }

    @AfterClass
    public static void tearDownClass() {
        if (factory != null && factory.isOpen()) {
            factory.close();
        }
    }

    @Before
    public void setUp() {
        manager = factory.createEntityManager();
        medicamentoRepository = new MedicamentoRepository(manager);
        exameRepository = new ExameRepository(manager);
        planoRepository = new PlanoSaudeRepository(manager);
        manager.getTransaction().begin();
    }

    @After
    public void tearDown() {
        if (manager.getTransaction().isActive()) {
            manager.getTransaction().rollback();
        }
        manager.close();
    }

    @Test
    public void deveListarMedicamentosDisponiveisParaPrescricao() {
        medicamentoRepository.salvaOuAtualiza(new Medicamento("Paracetamol Gotas"));
        medicamentoRepository.salvaOuAtualiza(new Medicamento("Amoxicilina Suspensão"));
        medicamentoRepository.salvaOuAtualiza(new Medicamento("Ibuprofeno Gotas"));
        manager.flush();
        manager.clear();

        List<Medicamento> lista = medicamentoRepository.todos();
        assertEquals(3, lista.size());
        assertEquals("Amoxicilina Suspensão", lista.get(0).getNome());
    }

    @Test
    public void deveBuscarMedicamentoPorNome() {
        medicamentoRepository.salvaOuAtualiza(new Medicamento("Dipirona Gotas"));
        manager.flush();
        manager.clear();

        List<Medicamento> encontrados = medicamentoRepository.buscaPorNome("dipirona");
        assertEquals(1, encontrados.size());
        assertEquals("Dipirona Gotas", encontrados.get(0).getNome());
    }

    @Test
    public void deveListarExamesDisponiveisParaSolicitacao() {
        exameRepository.salvaOuAtualiza(new Exame("Hemograma Completo"));
        exameRepository.salvaOuAtualiza(new Exame("Exame de Urina (EAS)"));
        manager.flush();
        manager.clear();

        List<Exame> lista = exameRepository.todos();
        assertEquals(2, lista.size());
    }

    @Test
    public void deveBuscarPlanoDeSaudePorNome() {
        planoRepository.salvaOuAtualiza(new PlanoSaude("Bradesco Saúde"));
        manager.flush();
        manager.clear();

        List<PlanoSaude> planos = planoRepository.buscaPorNome("bradesco");
        assertEquals(1, planos.size());
        assertEquals("Bradesco Saúde", planos.get(0).getNomePlano());
    }
}
