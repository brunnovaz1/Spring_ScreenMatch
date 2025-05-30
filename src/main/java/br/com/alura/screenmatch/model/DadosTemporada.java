package br.com.alura.screenmatch.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DadosTemporada(@JsonAlias("Season") int numero,
                             @JsonAlias("Episodes") List<DadosEpisodios> episodios
                             ) {

    @Override
    public String toString() {
        return "DadosTemporada" +
                "\n                             TEMPORADA " + numero +
                "\nepisodios=" + episodios;
    }
}
