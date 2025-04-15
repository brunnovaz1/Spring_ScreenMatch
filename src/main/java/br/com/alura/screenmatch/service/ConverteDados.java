package br.com.alura.screenmatch.service;

import br.com.alura.screenmatch.model.DadosSerie;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ConverteDados implements IConverteDados{   //necessário para usar o metodo obterDados
    private ObjectMapper mapper = new ObjectMapper();   //ObjectMapper converte Json em objeto Java, vice-versa.


    @Override
    public <T> T obterDados(String json, Class<T> classe) {
        try {
            return mapper.readValue(json, classe);    // mapper, leia o json e transforme na classe, apos isso mande para o record
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
