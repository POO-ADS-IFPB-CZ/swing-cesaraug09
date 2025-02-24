package model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

public class Produto implements Serializable {

    private final long serialVersionUID;
    private int id;
    private String descricao;
    private float preco;
    private LocalDate validade;

    public Produto(int id, String descricao, float preco, LocalDate validade) {
        this.serialVersionUID = 1L;
        this.id = id;
        this.descricao = descricao;
        this.preco = preco;
        this.validade = validade;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public float getPreco() {
        return preco;
    }

    public void setPreco(float preco) {
        this.preco = preco;
    }

    public LocalDate getValidade() {
        return validade;
    }

    public void setValidade(LocalDate validade) {
        this.validade = validade;
    }

    @Override
    public String toString() {
        return "Produto{" +
                "id=" + id +
                ", descricao='" + descricao + '\'' +
                ", preco=" + preco +
                ", validade=" + validade +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Produto produto = (Produto) o;
        return id == produto.id;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}