package br.com.alura.screenmatch.model;

import br.com.alura.screenmatch.service.ConsumoAPI;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DadosEpisodios( @JsonAlias("Title") String titulo,
                              @JsonAlias("Episode") int numero,
                              @JsonAlias("imdbRating") String avaliacao,
                              @JsonAlias("Released") String dataLancamento
                            ) {

    @Override
    public String toString() {
        return  "\nE:" + numero +
                " - Titulo: " + titulo +
                " - Avaliacao: " + avaliacao +
                " - DataLancamento: " + dataLancamento;
    }
}
