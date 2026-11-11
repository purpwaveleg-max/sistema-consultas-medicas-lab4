package edu.ifma.lbd.consultas.domain;

import jakarta.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "exame")
public class Exame implements EntidadeBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_exame")
    private Integer id;

    @Column(nullable = false, length = 120)
    private String nome;

    public Exame() {
    }

    public Exame(String nome) {
        this.nome = nome;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Exame exame = (Exame) o;
        return Objects.equals(id, exame.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
