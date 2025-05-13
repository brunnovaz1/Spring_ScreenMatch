package br.com.alura.screenmatch.principal;

import br.com.alura.screenmatch.model.DadosEpisodio;
import br.com.alura.screenmatch.model.DadosSerie;
import br.com.alura.screenmatch.model.DadosTemporada;
import br.com.alura.screenmatch.model.Episodio;
import br.com.alura.screenmatch.service.ConsumoApi;
import br.com.alura.screenmatch.service.ConverteDados;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Principal {
    private Scanner scan = new Scanner(System.in);
    private ConsumoApi consumo = new ConsumoApi();
    private ConverteDados conversor = new ConverteDados();


    private final String ENDERECO = "http://www.omdbapi.com/?t=";
    private final String API_KEY = "&apikey=3531d047";

    public void exibeMenu(){
        System.out.println(" - - - - - - - - - - - - - - - - - - - - - - - - - - ");
        System.out.println("                 PESQUISA DE SÉRIES");
        System.out.println(" - - - - - - - - - - - - - - - - - - - - - - - - - - ");
        System.out.print("Nome da Série (inglês): ");
        var nomeSerie = scan.nextLine();

        var json = consumo.obterDados(ENDERECO + nomeSerie.replace(" ", "+") + API_KEY);

        DadosSerie dados = conversor.obterDados(json, DadosSerie.class);          // <T> T obterDados(String json, Class<T> classe)
        System.out.println(dados);

		List<DadosTemporada> temporadas = new ArrayList<>();
		for(int i = 1; i<=dados.totalTemporadas(); i++) {
			json = consumo.obterDados(ENDERECO + nomeSerie.replace(" ","+") + "&Season=" + i + API_KEY);
			DadosTemporada dadosTemporada = conversor.obterDados(json, DadosTemporada.class);
			temporadas.add(dadosTemporada);
		}
		temporadas.forEach(System.out::println);

//        for(int i = 0;  i < dados.totalTemporadas(); i++){
//            List<DadosEpisodio> episodiosTemporada = temporadas.get(i).episodios();
//            for (int j = 0; j<episodiosTemporada.size(); j++ ){
//                System.out.println(episodiosTemporada.get(j).titulo());
//            }
//        }
        temporadas.forEach(t -> t.episodios().forEach(e -> System.out.println(e.titulo())));

        List<DadosEpisodio> dadosEpisodios = temporadas.stream()
                .flatMap(t -> t.episodios().stream())
                .collect(Collectors.toList());
        //tenho uma lista dentro de outra e quero puxar todos dados juntos
        //.toList(); cria uma lista imutável

        System.out.println("\nTOP-5 Episódios");
        dadosEpisodios.stream()
                .filter(e -> !e.avaliacao().equals("N/A"))
                .sorted(Comparator.comparing(DadosEpisodio::avaliacao).reversed())
                .limit(5)
                .forEach(System.out::println);

        List<Episodio> episodios = temporadas.stream()
                .flatMap(t -> t.episodios().stream()
                .map(dadosEpisodio -> new Episodio(t.numero(), dadosEpisodio))
                ).collect(Collectors.toList());

        episodios.forEach(System.out::println);
    }
}
