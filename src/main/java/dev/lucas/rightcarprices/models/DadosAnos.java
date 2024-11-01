package dev.lucas.rightcarprices.models;

public record DadosAnos(String codigo,
                        String nome) {
    @Override
    public String toString() {
        return  codigo + " - " + nome;
    }
}
