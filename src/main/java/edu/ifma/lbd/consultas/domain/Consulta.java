package edu.ifma.lbd.consultas.domain;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "consulta")
public class Consulta implements EntidadeBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_consulta")
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_paciente", nullable = false)
    private Paciente paciente;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_medico", nullable = false)
    private Medico medico;

    @Column(name = "data_hora", nullable = false)
    private LocalDateTime dataHora;

    // indica se e a primeira consulta do paciente
    @Column(name = "novo_paciente", nullable = false)
    private Boolean novoPaciente = false;

    // flag para agendamento
    @Column(nullable = false)
    private Boolean agendada = true;

    // prontuario vinculado a consulta (1:1)
    @OneToOne(mappedBy = "consulta", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Prontuario prontuario;

    public Consulta() {
    }

    public Consulta(Paciente paciente, Medico medico, LocalDateTime dataHora, Boolean novoPaciente, Boolean agendada) {
        this.paciente = paciente;
        this.medico = medico;
        this.dataHora = dataHora;
        this.novoPaciente = novoPaciente;
        this.agendada = agendada;
    }

    @Override
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }

    public Medico getMedico() {
        return medico;
    }

    public void setMedico(Medico medico) {
        this.medico = medico;
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }

    public void setDataHora(LocalDateTime dataHora) {
        this.dataHora = dataHora;
    }

    public Boolean getNovoPaciente() {
        return novoPaciente;
    }

    public void setNovoPaciente(Boolean novoPaciente) {
        this.novoPaciente = novoPaciente;
    }

    public Boolean getAgendada() {
        return agendada;
    }

    public void setAgendada(Boolean agendada) {
        this.agendada = agendada;
    }

    public Prontuario getProntuario() {
        return prontuario;
    }

    public void setProntuario(Prontuario prontuario) {
        this.prontuario = prontuario;
        if (prontuario != null) {
            prontuario.setConsulta(this);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Consulta consulta = (Consulta) o;
        return Objects.equals(id, consulta.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
