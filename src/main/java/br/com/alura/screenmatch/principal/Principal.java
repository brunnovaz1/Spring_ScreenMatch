package br.com.alura.screenmatch.principal;

import br.com.alura.screenmatch.model.DadosSerie;
import br.com.alura.screenmatch.model.DadosTemporada;
import br.com.alura.screenmatch.service.ConsumoAPI;
import br.com.alura.screenmatch.service.ConverteDados;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Principal {
    Scanner scan = new Scanner(System.in);

    private ConsumoAPI consumoAPI = new ConsumoAPI();
    private ConverteDados conversor = new ConverteDados();


    private final String ENDERECO = "http://www.omdbapi.com/?t=";
    private final String API_KEY = "&apikey=3531d047";

    public void exibeMenu(){
        System.out.println("\n========---------PESQUISA SÉRIES---------========");
        System.out.print("Nome: ");
        var nomeSerie = scan.nextLine();
        nomeSerie = URLEncoder.encode(nomeSerie, StandardCharsets.UTF_8);
        String json = consumoAPI.obterDados(ENDERECO + nomeSerie + API_KEY);

        DadosSerie dados = conversor.obterDados(json, DadosSerie.class);
    //    System.out.println(dados);

        List<DadosTemporada> temporadas = new ArrayList<>();

		for(int i = 1; i <= dados.totalTemporadas(); i++) {
			json = consumoAPI.obterDados(ENDERECO + nomeSerie + "&season=" + i + API_KEY);
			DadosTemporada dadosTemporada = conversor.obterDados(json, DadosTemporada.class);
			temporadas.add(dadosTemporada);		}
		temporadas.forEach(System.out::println);
    }
}
