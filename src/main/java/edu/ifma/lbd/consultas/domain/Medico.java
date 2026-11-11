package edu.ifma.lbd.consultas.domain;

import jakarta.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "medico")
public class Medico implements EntidadeBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_medico")
    private Integer id;

    @Column(nullable = false, length = 120)
    private String nome;

    // crm unico do medico
    @Column(nullable = false, unique = true, length = 20)
    private String crm;

    public Medico() {
    }

    public Medico(String nome, String crm) {
        this.nome = nome;
        this.crm = crm;
    }

    @Override
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCrm() {
        return crm;
    }

    public void setCrm(String crm) {
        this.crm = crm;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Medico medico = (Medico) o;
        return Objects.equals(id, medico.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
