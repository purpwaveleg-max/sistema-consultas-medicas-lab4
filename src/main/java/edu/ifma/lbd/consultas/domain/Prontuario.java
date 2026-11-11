package edu.ifma.lbd.consultas.domain;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "prontuario")
public class Prontuario implements EntidadeBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_prontuario")
    private Integer id;

    // relacionamento 1:1 com consulta
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_consulta", nullable = false, unique = true)
    private Consulta consulta;

    @Column(nullable = false, precision = 5, scale = 2)
    private BigDecimal peso;

    @Column(nullable = false, precision = 5, scale = 2)
    private BigDecimal altura;

    @Column(name = "descricao_sintomas", columnDefinition = "TEXT")
    private String descricaoSintomas;

    @Column(name = "observacao_clinica", columnDefinition = "TEXT")
    private String observacaoClinica;

    // relacionamento n:n com exames via prontuario_exame
    @ManyToMany
    @JoinTable(
        name = "prontuario_exame",
        joinColumns = @JoinColumn(name = "id_prontuario"),
        inverseJoinColumns = @JoinColumn(name = "id_exame")
    )
    private List<Exame> exames = new ArrayList<>();

    // relacionamento 1:n com prescricao de medicamentos
    @OneToMany(mappedBy = "prontuario", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Prescricao> prescricoes = new ArrayList<>();

    public Prontuario() {
    }

    public Prontuario(Consulta consulta, BigDecimal peso, BigDecimal altura, String descricaoSintomas, String observacaoClinica) {
        this.consulta = consulta;
        this.peso = peso;
        this.altura = altura;
        this.descricaoSintomas = descricaoSintomas;
        this.observacaoClinica = observacaoClinica;
    }

    @Override
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Consulta getConsulta() {
        return consulta;
    }

    public void setConsulta(Consulta consulta) {
        this.consulta = consulta;
    }

    public BigDecimal getPeso() {
        return peso;
    }

    public void setPeso(BigDecimal peso) {
        this.peso = peso;
    }

    public BigDecimal getAltura() {
        return altura;
    }

    public void setAltura(BigDecimal altura) {
        this.altura = altura;
    }

    public String getDescricaoSintomas() {
        return descricaoSintomas;
    }

    public void setDescricaoSintomas(String descricaoSintomas) {
        this.descricaoSintomas = descricaoSintomas;
    }

    public String getObservacaoClinica() {
        return observacaoClinica;
    }

    public void setObservacaoClinica(String observacaoClinica) {
        this.observacaoClinica = observacaoClinica;
    }

    public List<Exame> getExames() {
        return exames;
    }

    public void setExames(List<Exame> exames) {
        this.exames = exames;
    }

    public List<Prescricao> getPrescricoes() {
        return prescricoes;
    }

    public void setPrescricoes(List<Prescricao> prescricoes) {
        this.prescricoes = prescricoes;
    }

    public void adicionaExame(Exame exame) {
        exames.add(exame);
    }

    public void removeExame(Exame exame) {
        exames.remove(exame);
    }

    public void adicionaPrescricao(Prescricao prescricao) {
        prescricoes.add(prescricao);
        prescricao.setProntuario(this);
    }

    public void removePrescricao(Prescricao prescricao) {
        prescricoes.remove(prescricao);
        prescricao.setProntuario(null);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Prontuario that = (Prontuario) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
