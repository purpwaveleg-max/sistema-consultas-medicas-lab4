package edu.ifma.lbd.consultas.domain;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "paciente")
public class Paciente implements EntidadeBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_paciente")
    private Integer id;

    @Column(name = "nome_crianca", nullable = false, length = 120)
    private String nomeCrianca;

    @Column(name = "nome_responsavel", length = 120)
    private String nomeResponsavel;

    @Column(name = "data_nascimento", nullable = false)
    private LocalDate dataNascimento;

    @Column(nullable = false, length = 1)
    private String sexo;

    // relacionamento 1:1 com endereco
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "id_endereco", nullable = false)
    private Endereco endereco;

    // relacionamento n:1 com plano de saude, pode ser particular (null)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_plano")
    private PlanoSaude planoSaude;

    @OneToMany(mappedBy = "paciente", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Telefone> telefones = new ArrayList<>();

    @OneToMany(mappedBy = "paciente")
    private List<Consulta> consultas = new ArrayList<>();

    public Paciente() {
    }

    public Paciente(String nomeCrianca, String nomeResponsavel, LocalDate dataNascimento, String sexo, Endereco endereco) {
        this.nomeCrianca = nomeCrianca;
        this.nomeResponsavel = nomeResponsavel;
        this.dataNascimento = dataNascimento;
        this.sexo = sexo;
        this.endereco = endereco;
    }

    @Override
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNomeCrianca() {
        return nomeCrianca;
    }

    public void setNomeCrianca(String nomeCrianca) {
        this.nomeCrianca = nomeCrianca;
    }

    public String getNomeResponsavel() {
        return nomeResponsavel;
    }

    public void setNomeResponsavel(String nomeResponsavel) {
        this.nomeResponsavel = nomeResponsavel;
    }

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

    public PlanoSaude getPlanoSaude() {
        return planoSaude;
    }

    public void setPlanoSaude(PlanoSaude planoSaude) {
        this.planoSaude = planoSaude;
    }

    public List<Telefone> getTelefones() {
        return telefones;
    }

    public void setTelefones(List<Telefone> telefones) {
        this.telefones = telefones;
    }

    public List<Consulta> getConsultas() {
        return consultas;
    }

    public void setConsultas(List<Consulta> consultas) {
        this.consultas = consultas;
    }

    public void adicionaTelefone(Telefone telefone) {
        telefones.add(telefone);
        telefone.setPaciente(this);
    }

    public void removeTelefone(Telefone telefone) {
        telefones.remove(telefone);
        telefone.setPaciente(null);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Paciente paciente = (Paciente) o;
        return Objects.equals(id, paciente.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
