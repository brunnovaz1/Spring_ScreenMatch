package br.com.alura.screenmatch.principal;

import br.com.alura.screenmatch.model.DadosEpisodios;
import br.com.alura.screenmatch.model.DadosSerie;
import br.com.alura.screenmatch.model.DadosTemporada;
import br.com.alura.screenmatch.model.Episodio;
import br.com.alura.screenmatch.service.ConsumoAPI;
import br.com.alura.screenmatch.service.ConverteDados;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class Principal {
    Scanner scan = new Scanner(System.in);

    private ConsumoAPI consumoAPI = new ConsumoAPI();
    private ConverteDados conversor = new ConverteDados();


    private final String ENDERECO = "http://www.omdbapi.com/?t=";
    private final String API_KEY = "&apikey=3531d047";

    public void exibeMenu() {
        System.out.println("\n========---------PESQUISA SÉRIES---------========");
        System.out.print("Nome: ");
        var nomeSerie = scan.nextLine();
        nomeSerie = URLEncoder.encode(nomeSerie, StandardCharsets.UTF_8);
        String json = consumoAPI.obterDados(ENDERECO + nomeSerie + API_KEY);

        DadosSerie dados = conversor.obterDados(json, DadosSerie.class);
        //    System.out.println(dados);

        List<DadosTemporada> temporadas = new ArrayList<>();

        for (int i = 1; i <= dados.totalTemporadas(); i++) {
            json = consumoAPI.obterDados(ENDERECO + nomeSerie + "&season=" + i + API_KEY);
            DadosTemporada dadosTemporada = conversor.obterDados(json, DadosTemporada.class);
            temporadas.add(dadosTemporada);
        }
    //    temporadas.forEach(System.out::println);

   //    temporadas.forEach(t -> t.episodios().forEach(e -> System.out.println(e.titulo())));    //percorre a coleção, em cada temporada "t" percorre todos seus episodios, agora dentro deles, imprime o titulo

        List<DadosEpisodios> dadosEpisodios = temporadas.stream()
                .flatMap(t -> t.episodios().stream())
                .collect(Collectors.toList());                                                   //diferentemente do .toList() permite alterar a lista

        List<Episodio> episodios = temporadas.stream()
                .flatMap(t -> t.episodios().stream()
                    .map(d -> new Episodio(t.numero(), d ))
                ).collect(Collectors.toList());
    //    episodios.forEach(System.out::println);

        List<Episodio> top5 = episodios.stream()
                .sorted(Comparator.comparing(Episodio::getAvaliacao).reversed())
                .limit(5)
                .collect(Collectors.toList());
        //System.out.println("\n                                  +++++ TOP-5 +++++");
        //top5.forEach(System.out::println);

    /*    System.out.println("--==-- Busca Episódio por data --==--");
        System.out.print("Ano: ");
        int ano = scan.nextInt();
        scan.nextLine();

        LocalDate dataBusca = LocalDate.of(ano, 1, 1);

        DateTimeFormatter formatador = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        episodios.stream()
                .filter(e -> e.getDataLancamento() != null && e.getDataLancamento().isAfter(dataBusca))
                .forEach(e -> {
                    String linha = String.format(
                            "Temporada: %d - Episódio: %2d - Data: %s",
                            e.getTemporada(),
                            e.getNumero(),
                            e.getDataLancamento().format(formatador)
                    );
                    System.out.println(linha);
                });


        System.out.println("--==-- Busca Episódio por nome/termo --==--");
        System.out.print("Nome: ");
        String trechoTitulo = scan.nextLine();
        Optional<Episodio> episodioBuscado = episodios.stream()
                .filter(e -> e.getTitulo().toUpperCase().contains(trechoTitulo.toUpperCase()))
                .findFirst();
        if(episodioBuscado.isPresent()) {
            System.out.println(episodioBuscado);
        } else{
            System.out.println("Espisódio não encontrado!");
        }

        System.out.println("--== Média Temporadas ==--");
        Map<Integer, Double> avaliacaoPorTemporada = episodios.stream()
                .filter(e -> e.getAvaliacao() > 0)
                .collect(Collectors.groupingBy(Episodio::getTemporada,           //agrupa por temporada
                        Collectors.averagingDouble(Episodio::getAvaliacao)));    //media de avaliacoes
        avaliacaoPorTemporada.forEach(
                (temporada, media) -> {
                    String resp = String.format("Temporada:%d - Média:%.1f%n", temporada, media);
                    System.out.print(resp);
                }
        );

        */
        DoubleSummaryStatistics est = episodios.stream()
                .filter(e -> e.getAvaliacao() > 0)
                .collect(Collectors.summarizingDouble(Episodio::getAvaliacao));
        System.out.println(
                "Nº Episódios: "+ est.getCount() +
                "\nMaior nota: "+ est.getMax()   +
                "\nPior nota: " + est.getMin()
        );
    }
}
