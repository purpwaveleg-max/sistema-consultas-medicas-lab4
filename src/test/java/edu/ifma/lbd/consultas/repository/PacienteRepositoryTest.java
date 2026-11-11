package edu.ifma.lbd.consultas.repository;

import edu.ifma.lbd.consultas.domain.Endereco;
import edu.ifma.lbd.consultas.domain.Paciente;
import edu.ifma.lbd.consultas.domain.PlanoSaude;
import edu.ifma.lbd.consultas.domain.Telefone;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.junit.*;

import java.time.LocalDate;
import java.util.List;

import static org.junit.Assert.*;

public class PacienteRepositoryTest {

    private static EntityManagerFactory factory;
    private EntityManager manager;
    private PacienteRepository repository;
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
        repository = new PacienteRepository(manager);
        planoRepository = new PlanoSaudeRepository(manager);
        manager.getTransaction().begin();
    }

    @After
    public void tearDown() {
        // rollback garante isolamento entre testes sem poluir o banco
        if (manager.getTransaction().isActive()) {
            manager.getTransaction().rollback();
        }
        manager.close();
    }

    @Test
    public void deveSalvarPacienteComEnderecoETelefones() {
        Endereco endereco = new Endereco("Rua das Flores", "123", "Renascença", "São Luís", "MA", "65075-000");
        PlanoSaude plano = new PlanoSaude("Unimed");
        planoRepository.salvaOuAtualiza(plano);

        Paciente paciente = new Paciente(
                "Lucas Andrade Silva",
                "Fernanda Andrade",
                LocalDate.of(2022, 6, 10),
                "M",
                endereco
        );
        paciente.setPlanoSaude(plano);

        Telefone cel = new Telefone("(98) 99111-2222", "celular");
        Telefone fixo = new Telefone("(98) 3235-1111", "residencial");
        paciente.adicionaTelefone(cel);
        paciente.adicionaTelefone(fixo);

        repository.salvaOuAtualiza(paciente);
        manager.flush();
        manager.clear();

        assertNotNull(paciente.getId());

        Paciente buscado = repository.buscaPor(paciente.getId());
        assertNotNull(buscado);
        assertEquals("Lucas Andrade Silva", buscado.getNomeCrianca());
        assertEquals("Fernanda Andrade", buscado.getNomeResponsavel());
        assertEquals(2, buscado.getTelefones().size());
        assertEquals("Renascença", buscado.getEndereco().getBairro());
        assertEquals("Unimed", buscado.getPlanoSaude().getNomePlano());
    }

    @Test
    public void deveBuscarPacientePorNomeCrianca() {
        Endereco endereco = new Endereco("Av. Litorânea", "450", "Ponta d'Areia", "São Luís", "MA", "65077-000");
        Paciente paciente = new Paciente(
                "Maria Eduarda Silva",
                "Fernanda Andrade",
                LocalDate.of(2021, 7, 22),
                "F",
                endereco
        );

        repository.salvaOuAtualiza(paciente);
        manager.flush();
        manager.clear();

        List<Paciente> resultado = repository.buscaPorNomeCrianca("eduarda");
        assertFalse(resultado.isEmpty());
        assertEquals("Maria Eduarda Silva", resultado.get(0).getNomeCrianca());
    }

    @Test
    public void deveBuscarPacientePorNomeResponsavel() {
        Endereco endereco = new Endereco("Rua São Pantaleão", "88", "Centro", "São Luís", "MA", "65010-000");
        Paciente paciente = new Paciente(
                "Sofia Ramos Costa",
                "Patrícia Ramos",
                LocalDate.of(2023, 1, 15),
                "F",
                endereco
        );

        repository.salvaOuAtualiza(paciente);
        manager.flush();
        manager.clear();

        List<Paciente> resultado = repository.buscaPorNomeResponsavel("Patrícia");
        assertEquals(1, resultado.size());
        assertEquals("Sofia Ramos Costa", resultado.get(0).getNomeCrianca());
    }

    @Test
    public void deveAtualizarDadosDoPaciente() {
        Endereco endereco = new Endereco("Travessa Boa Vista", "22", "Cohama", "São Luís", "MA", "65076-000");
        Paciente paciente = new Paciente(
                "Gabriel Lima Ferreira",
                "Renata Lima",
                LocalDate.of(2020, 5, 30),
                "M",
                endereco
        );

        repository.salvaOuAtualiza(paciente);
        manager.flush();

        paciente.setNomeResponsavel("Renata Lima Ferreira");
        repository.salvaOuAtualiza(paciente);
        manager.flush();
        manager.clear();

        Paciente atualizado = repository.buscaPor(paciente.getId());
        assertEquals("Renata Lima Ferreira", atualizado.getNomeResponsavel());
    }

    @Test
    public void deveExcluirPacienteEEndercoEmCascata() {
        Endereco endereco = new Endereco("Rua 1", "10", "Bairro", "Cidade", "MA", "65000-000");
        Paciente paciente = new Paciente(
                "Davi Oliveira",
                "Larissa Oliveira",
                LocalDate.of(2022, 2, 18),
                "M",
                endereco
        );

        repository.salvaOuAtualiza(paciente);
        manager.flush();

        Integer idPaciente = paciente.getId();
        Integer idEndereco = paciente.getEndereco().getId();

        repository.remove(paciente);
        manager.clear();

        assertNull(repository.buscaPor(idPaciente));
        assertNull(manager.find(Endereco.class, idEndereco));
    }
}
