package edu.ifma.lbd.consultas.domain;

import jakarta.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "telefone")
public class Telefone implements EntidadeBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_telefone")
    private Integer id;

    @Column(nullable = false, length = 20)
    private String numero;

    // celular ou residencial
    @Column(nullable = false, length = 20)
    private String tipo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_paciente", nullable = false)
    private Paciente paciente;

    public Telefone() {
    }

    public Telefone(String numero, String tipo) {
        this.numero = numero;
        this.tipo = tipo;
    }

    public Telefone(String numero, String tipo, Paciente paciente) {
        this.numero = numero;
        this.tipo = tipo;
        this.paciente = paciente;
    }

    @Override
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Telefone telefone = (Telefone) o;
        return Objects.equals(id, telefone.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
