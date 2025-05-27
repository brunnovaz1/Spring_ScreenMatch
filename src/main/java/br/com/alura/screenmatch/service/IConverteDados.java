package br.com.alura.screenmatch.service;

public interface IConverteDados {
    <T> T obterDados(String json, Class <T> classe);
}



// vai receber um json(str), uma classe e la no conversor será transformado o json na classe indicada.