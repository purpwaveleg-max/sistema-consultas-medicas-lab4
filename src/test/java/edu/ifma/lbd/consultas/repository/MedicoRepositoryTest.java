package edu.ifma.lbd.consultas.repository;

import edu.ifma.lbd.consultas.domain.Medico;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.junit.*;

import java.util.List;

import static org.junit.Assert.*;

public class MedicoRepositoryTest {

    private static EntityManagerFactory factory;
    private EntityManager manager;
    private MedicoRepository repository;

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
        repository = new MedicoRepository(manager);
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
    public void deveSalvarEBuscarMedicoPorCrm() {
        Medico medico = new Medico("Dr. Ricardo Vilegas", "1234-MA");
        repository.salvaOuAtualiza(medico);
        manager.flush();
        manager.clear();

        Medico buscado = repository.buscaPorCrm("1234-MA");
        assertNotNull(buscado);
        assertEquals("Dr. Ricardo Vilegas", buscado.getNome());
    }

    @Test
    public void deveBuscarMedicosPorParteDoNome() {
        Medico m1 = new Medico("Dra. Ana Beatriz Costa", "5678-MA");
        Medico m2 = new Medico("Dr. Carlos Alberto", "9999-MA");
        repository.salvaOuAtualiza(m1);
        repository.salvaOuAtualiza(m2);
        manager.flush();
        manager.clear();

        List<Medico> medicos = repository.buscaPorNome("beatriz");
        assertEquals(1, medicos.size());
        assertEquals("5678-MA", medicos.get(0).getCrm());
    }

    @Test
    public void deveRetornarNullQuandoCrmNaoExiste() {
        Medico inexistente = repository.buscaPorCrm("0000-XX");
        assertNull(inexistente);
    }
}
