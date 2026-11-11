package edu.ifma.lbd.consultas.domain;

import jakarta.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "prescricao")
public class Prescricao implements EntidadeBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_prescricao")
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_prontuario", nullable = false)
    private Prontuario prontuario;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_medicamento", nullable = false)
    private Medicamento medicamento;

    @Column(nullable = false, length = 60)
    private String dosagem;

    // via de administracao (oral, topico, etc)
    @Column(nullable = false, length = 60)
    private String administracao;

    // duracao ou tempo de uso prescrito
    @Column(name = "tempo_de_uso", nullable = false, length = 60)
    private String tempoDeUso;

    public Prescricao() {
    }

    public Prescricao(Medicamento medicamento, String dosagem, String administracao, String tempoDeUso) {
        this.medicamento = medicamento;
        this.dosagem = dosagem;
        this.administracao = administracao;
        this.tempoDeUso = tempoDeUso;
    }

    public Prescricao(Prontuario prontuario, Medicamento medicamento, String dosagem, String administracao, String tempoDeUso) {
        this.prontuario = prontuario;
        this.medicamento = medicamento;
        this.dosagem = dosagem;
        this.administracao = administracao;
        this.tempoDeUso = tempoDeUso;
    }

    @Override
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Prontuario getProntuario() {
        return prontuario;
    }

    public void setProntuario(Prontuario prontuario) {
        this.prontuario = prontuario;
    }

    public Medicamento getMedicamento() {
        return medicamento;
    }

    public void setMedicamento(Medicamento medicamento) {
        this.medicamento = medicamento;
    }

    public String getDosagem() {
        return dosagem;
    }

    public void setDosagem(String dosagem) {
        this.dosagem = dosagem;
    }

    public String getAdministracao() {
        return administracao;
    }

    public void setAdministracao(String administracao) {
        this.administracao = administracao;
    }

    public String getTempoDeUso() {
        return tempoDeUso;
    }

    public void setTempoDeUso(String tempoDeUso) {
        this.tempoDeUso = tempoDeUso;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Prescricao that = (Prescricao) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
