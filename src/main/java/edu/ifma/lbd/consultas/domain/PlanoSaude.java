package edu.ifma.lbd.consultas.domain;

import jakarta.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "plano_saude")
public class PlanoSaude implements EntidadeBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_plano")
    private Integer id;

    // nome do convenio ou plano
    @Column(name = "nome_plano", nullable = false, length = 100)
    private String nomePlano;

    public PlanoSaude() {
    }

    public PlanoSaude(String nomePlano) {
        this.nomePlano = nomePlano;
    }

    @Override
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNomePlano() {
        return nomePlano;
    }

    public void setNomePlano(String nomePlano) {
        this.nomePlano = nomePlano;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PlanoSaude that = (PlanoSaude) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
