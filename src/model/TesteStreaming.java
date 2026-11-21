package model;

import model.Filme;
import model.Serie;

public class TesteStreaming {

    public static void main(String[] args) {

        Filme filme = new Filme(
            "Moana", "Animacao", 2016, 107
        );

        Serie serie = new Serie(
            "Wandinha", "Misterio", 2022, 1, 8, 45
        );

        System.out.println("Filme: " + filme.getTitulo());
        System.out.println("Duracao: " + filme.duracao());

        System.out.println("Serie: " + serie.getTitulo());
        System.out.println("Temporadas: " + serie.getNumeroTemporadas());
        System.out.println("Duracao total: " + serie.duracao());
    }
}