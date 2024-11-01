package dev.lucas.rightcarprices.models;

public record Dados(Integer codigo,
                    String nome) {
    @Override
    public String toString() {
        return  codigo + " - " + nome;
    }
}
