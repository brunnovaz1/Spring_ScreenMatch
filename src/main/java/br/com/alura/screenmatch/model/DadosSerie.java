package br.com.alura.screenmatch.model;             //os dados nao serao trabalhados, apenas para transição

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DadosSerie( @JsonAlias("Title") String titulo,
                          @JsonAlias("totalSeasons") int totalTemporadas,
                          @JsonAlias("imdbRating") String avaliacao) {

    @Override
    public String toString() {
        return "Dados Serie" +
                "\nTítulo: " + titulo +
                "\nTemporadas: " + totalTemporadas +
                "\nNota: " + avaliacao;
    }
}
