package controller;

import controller.StreamingController;
import model.Midia;
import model.Filme;
import model.Serie;

import java.io.IOException;

public class TesteController {

    public static void main(String[] args) {

        StreamingController sistema = new StreamingController();

        try {
            Filme filme = new Filme("Moana", "Animacao", 2016, 107);
            Serie serie = new Serie("Wandinha", "Misterio", 2022, 1, 8, 45);

            sistema.cadastrarMidia(filme);
            sistema.cadastrarMidia(serie);

            for (Midia midia : sistema.listarMidias()) {
                System.out.println(midia.getTitulo());
            }

            Midia encontrada = sistema.buscarMidia("Moana");

            if (encontrada != null) {
                System.out.println("Encontrado: " + encontrada.getTitulo());
            }

            sistema.salvarArquivo();
            System.out.println("Arquivo salvo com sucesso!");

            sistema.carregarArquivo();
            System.out.println("Arquivo carregado com sucesso!");

        } catch (IllegalArgumentException e) {
            System.out.println("Erro: " + e.getMessage());

        } catch (IOException e) {
            System.out.println("Erro no arquivo: " + e.getMessage());
        }
    }
}
