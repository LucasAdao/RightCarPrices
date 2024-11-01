package dev.lucas.rightcarprices.utils;

import dev.lucas.rightcarprices.models.Dados;
import dev.lucas.rightcarprices.models.DadosAnos;
import dev.lucas.rightcarprices.models.Modelos;
import dev.lucas.rightcarprices.models.Veiculo;
import dev.lucas.rightcarprices.service.ConsumoApi;
import dev.lucas.rightcarprices.service.ConversorDeDados;


import java.awt.*;
import java.net.URI;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Funcionalidades {
    private final String URL = "https://parallelum.com.br/fipe/api/v1/carros/marcas";
    private ConversorDeDados conversorDeDados = new ConversorDeDados();
    private ConsumoApi consumo = new ConsumoApi();
    private Scanner scanner = new Scanner(System.in);

    public void abrirLinkDeMusica(String url){
        try {
            // Verifica se o Desktop está disponível
            if (Desktop.isDesktopSupported()) {
                Desktop desktop = Desktop.getDesktop();

                // Verifica se é possível abrir o navegador
                if (desktop.isSupported(Desktop.Action.BROWSE)) {
                    URI uri = new URI(url);
                    desktop.browse(uri);  // Abre o link no navegador
                }
            } else {
                System.out.println("Ação não suportada no seu sistema.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void esperarEnter(){
        Scanner sc = new Scanner(System.in);
        System.out.println("Digite ENTER após pular o anuncio no youtube!! hehe");
        sc.nextLine();
    }

    public void limparConsole()  {
        System.out.println("\n" + "\n" +"\n" +"\n" +"\n" +"\n" +"\n" +"\n" +"\n" +"\n" +"\n"+"\n"+"\n"+"\n" );
    }

    public void mostrarLogo() throws InterruptedException {
        System.out.println("\\______________________/");
        System.out.println("__/__|_____________|__\\__");
        System.out.println("/⭕⭕____________⭕⭕\\");
        System.out.println("|_____/__Alura-01__\\_____|");
        System.out.println("\\\uD83D\uDCA5\uD83D\uDCA5__|_|_|__|_|__\uD83D\uDCA5\uD83D\uDCA5/");
        Thread.sleep(10000);
        System.out.println();
        System.out.println("Você fez a escolha certa, veio ao melhor lugar para verificar os preços dos carros!");
        Thread.sleep(6000);
        System.out.println("Aqui na Right Car traremos os preços dos carros de acordo com a FIPE");
        Thread.sleep(6000);
        System.out.println("Boas consultas!!");
        Thread.sleep(3500);

    }
    public void exibeMenu() throws InterruptedException {
        System.out.println("Esse são os carros que temos disponiveis no momento:");
        Thread.sleep(4000);
        var json = consumo.obterDados(URL);
        var marcas = conversorDeDados.converterJsonParaListaDeClasses(json, Dados.class);
        marcas.stream()
                .sorted(Comparator.comparing(Dados::codigo))
                .forEach(System.out::println);
        System.out.println("Informe o código da marca que você quer consultar o preço:");

        String codigoMarca = scanner.nextLine();
        String endereco = URL + "/" + codigoMarca + "/modelos";
        json = consumo.obterDados(endereco);
        var modelosLista = conversorDeDados.converterJsonParaClasse(json, Modelos.class);
        System.out.println("\nModelos dessa marca:");
        modelosLista
                  .modelos()
                  .stream()
                  .sorted(Comparator.comparing(Dados::codigo))
                  .forEach(System.out::println);

        System.out.println("Digite o nome do modelo que você quer consultar:");
        var modeloDoVeiculo = scanner.nextLine();

        List<Dados> modelosFiltrados = modelosLista.modelos().stream()
                .filter(m -> m.nome().toLowerCase().contains(modeloDoVeiculo.toLowerCase()))
                .collect(Collectors.toList());
        System.out.println("\nModelos filtrados:");
        modelosFiltrados.forEach(System.out::println);

        System.out.println("Digite o valor do código do modelo para buscar os valores de avaliação:");
        var codigoModelo = scanner.nextLine();
        endereco = endereco + "/" + codigoModelo + "/anos";
        json = consumo.obterDados(endereco);

        List<DadosAnos> anos = conversorDeDados.converterJsonParaListaDeClasses(json, DadosAnos.class);

        List<Veiculo> veiculos = new ArrayList<>();

        for (int i = 0; i < anos.size(); i++) {
            var enderecoAnos = endereco + "/" + anos.get(i).codigo();
            json = consumo.obterDados(enderecoAnos);
            Veiculo veiculo = conversorDeDados.converterJsonParaClasse(json, Veiculo.class);
            veiculos.add(veiculo);
        }

        System.out.println("\nTodos os veiculos filtrados com avaliações por ano: ");
        veiculos.forEach(System.out::println);
    }
    public void play() throws InterruptedException {
        Funcionalidades func = new Funcionalidades();
        //para melhor experiência volte para o intelijj depois que rodar o aplicativo!
        func.abrirLinkDeMusica("https://www.youtube.com/watch?v=ubWL8VAPoYw&list=PLK_-XzZlq5v733AgeXOXiiMv9hUrvdocy");
        func.esperarEnter();
        func.mostrarLogo();
        func.limparConsole();
        boolean repete = true;
        while (repete) {
            func.exibeMenu();
            System.out.println("Se quiser sair digite 0, se não, Enter");
            String escolha = scanner.nextLine();
            if (escolha.equals("0")) {
                repete = false;
            }
        }
    }
}