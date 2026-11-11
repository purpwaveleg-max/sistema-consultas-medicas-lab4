package edu.ifma.lbd.consultas.repository;

import edu.ifma.lbd.consultas.domain.Consulta;
import edu.ifma.lbd.consultas.domain.Endereco;
import edu.ifma.lbd.consultas.domain.Medico;
import edu.ifma.lbd.consultas.domain.Paciente;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.junit.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.Assert.*;

public class ConsultaRepositoryTest {

    private static EntityManagerFactory factory;
    private EntityManager manager;
    private ConsultaRepository consultaRepository;
    private PacienteRepository pacienteRepository;
    private MedicoRepository medicoRepository;

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
        consultaRepository = new ConsultaRepository(manager);
        pacienteRepository = new PacienteRepository(manager);
        medicoRepository = new MedicoRepository(manager);
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
    public void deveBuscarConsultasDoDia() {
        Medico medico = new Medico("Dr. Ricardo Vilegas", "1234-MA");
        medicoRepository.salvaOuAtualiza(medico);

        Endereco end1 = new Endereco("Rua 1", "10", "Bairro", "São Luís", "MA", "65000-000");
        Paciente p1 = new Paciente("Lucas Andrade", "Fernanda", LocalDate.of(2022, 6, 10), "M", end1);
        pacienteRepository.salvaOuAtualiza(p1);

        LocalDate hoje = LocalDate.of(2026, 11, 10);
        Consulta c1 = new Consulta(p1, medico, LocalDateTime.of(2026, 11, 10, 9, 30), true, true);
        Consulta c2 = new Consulta(p1, medico, LocalDateTime.of(2026, 11, 10, 14, 0), false, true);
        Consulta cOutroDia = new Consulta(p1, medico, LocalDateTime.of(2026, 11, 11, 10, 0), false, true);

        consultaRepository.salvaOuAtualiza(c1);
        consultaRepository.salvaOuAtualiza(c2);
        consultaRepository.salvaOuAtualiza(cOutroDia);

        manager.flush();
        manager.clear();

        List<Consulta> consultas = consultaRepository.buscaConsultasDoDia(hoje);
        assertEquals(2, consultas.size());
        assertEquals(LocalDateTime.of(2026, 11, 10, 9, 30), consultas.get(0).getDataHora());
        assertEquals(LocalDateTime.of(2026, 11, 10, 14, 0), consultas.get(1).getDataHora());
    }

    @Test
    public void deveBuscarHistoricoDeConsultasDoPaciente() {
        Medico medico = new Medico("Dr. Ricardo Vilegas", "1234-MA");
        medicoRepository.salvaOuAtualiza(medico);

        Endereco end = new Endereco("Rua A", "1", "Centro", "São Luís", "MA", "65000-000");
        Paciente paciente = new Paciente("Enzo Carvalho", "Débora", LocalDate.of(2021, 12, 1), "M", end);
        pacienteRepository.salvaOuAtualiza(paciente);

        Consulta cAntiga = new Consulta(paciente, medico, LocalDateTime.of(2024, 1, 15, 9, 0), true, true);
        Consulta cRecente = new Consulta(paciente, medico, LocalDateTime.of(2026, 5, 20, 10, 0), false, true);

        consultaRepository.salvaOuAtualiza(cAntiga);
        consultaRepository.salvaOuAtualiza(cRecente);

        manager.flush();
        manager.clear();

        List<Consulta> historico = consultaRepository.buscaHistoricoPorPaciente(paciente);
        assertEquals(2, historico.size());
        // ordenado do mais recente para o mais antigo
        assertTrue(historico.get(0).getDataHora().isAfter(historico.get(1).getDataHora()));
    }
}
