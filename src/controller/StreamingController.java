
package controller;

import java.util.ArrayList;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;

import model.Midia;
import model.Filme;
import model.Serie;

public class StreamingController {

    private ArrayList<Midia> midias;

    public StreamingController() {
        midias = new ArrayList<Midia>();
    }

    public void cadastrarMidia(Midia midia) {
        midias.add(midia);
    }

    public ArrayList<Midia> listarMidias() {
        return midias;
    }

    public Midia buscarMidia(String titulo) {
        for (Midia midia : midias) {
            if (midia.getTitulo().equalsIgnoreCase(titulo)) {
                return midia;
            }
        }
        return null;
    }

    public void salvarArquivo() throws IOException {

        FileWriter writer = new FileWriter("streaming.txt");

        try {
            for (Midia midia : midias) {

                if (midia instanceof Filme) {
                    Filme filme = (Filme) midia;

                    writer.write("FILME," + filme.getTitulo() + "," +
                    filme.getGenero() + "," +
                    filme.getAnoLancamento() + "," +
                    filme.getDuracaoMinutos() + "\n");

                } else if (midia instanceof Serie) {
                    Serie serie = (Serie) midia;

                    writer.write("SERIE," + serie.getTitulo() + "," +
                    serie.getGenero() + "," +
                    serie.getAnoLancamento() + "," +
                    serie.getNumeroTemporadas() + "," +
                    serie.getNumeroEpisodios() + "," +
                    serie.getDuracaoEpisodio() + "\n");
                }
            }
        } finally {
            writer.close();
        }
    }

    public void carregarArquivo() throws IOException {

        BufferedReader reader = new BufferedReader(
            new FileReader("streaming.txt")
        );

        ArrayList<Midia> lista = new ArrayList<Midia>();

        try {
            String linha = reader.readLine();

            while (linha != null) {

                String[] dados = linha.split(",");

                if (dados[0].equals("FILME")) {

                    Filme filme = new Filme(
                        dados[1],
                        dados[2],
                        Integer.parseInt(dados[3]),
                        Integer.parseInt(dados[4])
                    );

                    lista.add(filme);

                } else if (dados[0].equals("SERIE")) {

                    Serie serie = new Serie(
                        dados[1],
                        dados[2],
                        Integer.parseInt(dados[3]),
                        Integer.parseInt(dados[4]),
                        Integer.parseInt(dados[5]),
                        Integer.parseInt(dados[6])
                    );

                    lista.add(serie);
                }

                linha = reader.readLine();
            }

            midias = lista;

        } finally {
            reader.close();
        }
    }
}
