package edu.ifma.lbd.consultas.repository;

import edu.ifma.lbd.consultas.domain.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.junit.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.Assert.*;

public class ProntuarioRepositoryTest {

    private static EntityManagerFactory factory;
    private EntityManager manager;
    private ProntuarioRepository prontuarioRepository;
    private ConsultaRepository consultaRepository;
    private PacienteRepository pacienteRepository;
    private MedicoRepository medicoRepository;
    private MedicamentoRepository medicamentoRepository;
    private ExameRepository exameRepository;

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
        prontuarioRepository = new ProntuarioRepository(manager);
        consultaRepository = new ConsultaRepository(manager);
        pacienteRepository = new PacienteRepository(manager);
        medicoRepository = new MedicoRepository(manager);
        medicamentoRepository = new MedicamentoRepository(manager);
        exameRepository = new ExameRepository(manager);
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
    public void deveRegistrarProntuarioCompletoComPrescricoesEExames() {
        // preparacao do cenario do caso de uso
        Medico medico = new Medico("Dr. Ricardo Vilegas", "1234-MA");
        medicoRepository.salvaOuAtualiza(medico);

        Endereco endereco = new Endereco("Rua das Flores", "123", "Renascença", "São Luís", "MA", "65075-000");
        Paciente paciente = new Paciente("Lucas Andrade", "Fernanda", LocalDate.of(2022, 6, 10), "M", endereco);
        pacienteRepository.salvaOuAtualiza(paciente);

        Consulta consulta = new Consulta(paciente, medico, LocalDateTime.of(2026, 11, 10, 9, 30), true, true);
        consultaRepository.salvaOuAtualiza(consulta);

        Medicamento paracetamol = new Medicamento("Paracetamol Gotas");
        Medicamento amoxicilina = new Medicamento("Amoxicilina Suspensão");
        medicamentoRepository.salvaOuAtualiza(paracetamol);
        medicamentoRepository.salvaOuAtualiza(amoxicilina);

        Exame hemograma = new Exame("Hemograma Completo");
        Exame urina = new Exame("Exame de Urina (EAS)");
        exameRepository.salvaOuAtualiza(hemograma);
        exameRepository.salvaOuAtualiza(urina);

        // medico registra o prontuario com peso, altura, sintomas, exames e prescricoes
        Prontuario prontuario = new Prontuario(
                consulta,
                new BigDecimal("8.50"),
                new BigDecimal("68.00"),
                "Febre baixa e tosse leve",
                "Ausculta pulmonar limpa. Hidratacao preservada."
        );

        prontuario.adicionaExame(hemograma);
        prontuario.adicionaExame(urina);

        Prescricao prescricao1 = new Prescricao(paracetamol, "10 gotas", "oral, a cada 6h se febre", "3 dias");
        Prescricao prescricao2 = new Prescricao(amoxicilina, "5 ml", "oral, a cada 8h", "7 dias");
        prontuario.adicionaPrescricao(prescricao1);
        prontuario.adicionaPrescricao(prescricao2);

        prontuarioRepository.salvaOuAtualiza(prontuario);
        manager.flush();
        manager.clear();

        assertNotNull(prontuario.getId());

        Prontuario doBanco = prontuarioRepository.buscaPor(prontuario.getId());
        assertNotNull(doBanco);
        assertEquals(new BigDecimal("8.50"), doBanco.getPeso());
        assertEquals(2, doBanco.getExames().size());
        assertEquals(2, doBanco.getPrescricoes().size());
        assertEquals("Lucas Andrade", doBanco.getConsulta().getPaciente().getNomeCrianca());
    }

    @Test
    public void deveBuscarProntuarioPelaConsulta() {
        Medico medico = new Medico("Dr. Ricardo Vilegas", "1234-MA");
        medicoRepository.salvaOuAtualiza(medico);

        Endereco endereco = new Endereco("Rua 2", "20", "Centro", "São Luís", "MA", "65000-000");
        Paciente paciente = new Paciente("Sofia Ramos", "Patrícia", LocalDate.of(2023, 1, 15), "F", endereco);
        pacienteRepository.salvaOuAtualiza(paciente);

        Consulta consulta = new Consulta(paciente, medico, LocalDateTime.now(), false, true);
        consultaRepository.salvaOuAtualiza(consulta);

        Prontuario prontuario = new Prontuario(
                consulta,
                new BigDecimal("12.00"),
                new BigDecimal("85.00"),
                "Rotina",
                "Sem queixas"
        );
        prontuarioRepository.salvaOuAtualiza(prontuario);
        manager.flush();
        manager.clear();

        Prontuario buscado = prontuarioRepository.buscaPorConsulta(consulta);
        assertNotNull(buscado);
        assertEquals(new BigDecimal("12.00"), buscado.getPeso());
    }

    @Test
    public void deveBuscarHistoricoDeProntuariosDoPaciente() {
        Medico medico = new Medico("Dr. Ricardo Vilegas", "1234-MA");
        medicoRepository.salvaOuAtualiza(medico);

        Endereco endereco = new Endereco("Rua 3", "30", "Cohama", "São Luís", "MA", "65000-000");
        Paciente paciente = new Paciente("Davi Oliveira", "Larissa", LocalDate.of(2022, 2, 18), "M", endereco);
        pacienteRepository.salvaOuAtualiza(paciente);

        Consulta c1 = new Consulta(paciente, medico, LocalDateTime.of(2025, 1, 10, 9, 0), true, true);
        Consulta c2 = new Consulta(paciente, medico, LocalDateTime.of(2026, 6, 15, 10, 0), false, true);
        consultaRepository.salvaOuAtualiza(c1);
        consultaRepository.salvaOuAtualiza(c2);

        Prontuario p1 = new Prontuario(c1, new BigDecimal("10.00"), new BigDecimal("75.00"), "Consulta 1", "Ok");
        Prontuario p2 = new Prontuario(c2, new BigDecimal("13.00"), new BigDecimal("88.00"), "Consulta 2", "Ok");
        prontuarioRepository.salvaOuAtualiza(p1);
        prontuarioRepository.salvaOuAtualiza(p2);

        manager.flush();
        manager.clear();

        List<Prontuario> historico = prontuarioRepository.buscaHistoricoPorPaciente(paciente);
        assertEquals(2, historico.size());
    }
}
