package br.com.alura.screenmatch.service;

public interface IConverteDados {

   <T> T obterDados(String json, Class<T> classe);
}


//recebe um json que é um String, vai receber uma classe e la no conversor de dados vai tentar transformar o json
// na classe que foi indicada.