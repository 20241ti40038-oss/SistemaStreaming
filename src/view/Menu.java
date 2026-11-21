
package view;

import java.util.Scanner;
import java.io.IOException;
import java.util.InputMismatchException;

import controller.StreamingController;
import model.Midia;
import model.Filme;
import model.Serie;

public class Menu {

    public static void main(String[] args) {

        Scanner entrada = new Scanner(System.in);
        StreamingController sistema = new StreamingController();

        int opcao = -1;

        while (opcao != 0) {

            System.out.println();
            System.out.println("===== SISTEMA STREAMING =====");
            System.out.println("1 - Cadastrar filme");
            System.out.println("2 - Cadastrar serie");
            System.out.println("3 - Listar midias");
            System.out.println("4 - Buscar midia");
            System.out.println("5 - Salvar arquivo");
            System.out.println("6 - Carregar arquivo");
            System.out.println("0 - Sair");
            System.out.print("Escolha uma opcao: ");

            try {

                opcao = entrada.nextInt();
                entrada.nextLine();

                if (opcao == 1) {

                    System.out.print("Digite o titulo do filme: ");
                    String titulo = entrada.nextLine();

                    System.out.print("Digite o genero do filme: ");
                    String genero = entrada.nextLine();

                    System.out.print("Digite o ano de lancamento: ");
                    int ano = entrada.nextInt();
                    entrada.nextLine();

                    System.out.print("Digite a duracao em minutos: ");
                    int duracao = entrada.nextInt();
                    entrada.nextLine();

                    Filme filme = new Filme(titulo, genero, ano, duracao);

                    sistema.cadastrarMidia(filme);

                    System.out.println("Filme cadastrado com sucesso!");

                } else if (opcao == 2) {

                    System.out.print("Digite o titulo da serie: ");
                    String titulo = entrada.nextLine();

                    System.out.print("Digite o genero da serie: ");
                    String genero = entrada.nextLine();

                    System.out.print("Digite o ano de lancamento: ");
                    int ano = entrada.nextInt();
                    entrada.nextLine();

                    System.out.print("Digite o numero de temporadas: ");
                    int temporadas = entrada.nextInt();
                    entrada.nextLine();

                    System.out.print("Digite o numero de episodios por temporada: ");
                    int episodios = entrada.nextInt();
                    entrada.nextLine();

                    System.out.print("Digite a duracao de cada episodio: ");
                    int duracao = entrada.nextInt();
                    entrada.nextLine();

                    Serie serie = new Serie(
                        titulo,
                        genero,
                        ano,
                        temporadas,
                        episodios,
                        duracao
                    );

                    sistema.cadastrarMidia(serie);

                    System.out.println("Serie cadastrada com sucesso!");

                } else if (opcao == 3) {

                    System.out.println();
                    System.out.println("===== LISTA DE MIDIAS =====");

                    if (sistema.listarMidias().isEmpty()) {

                        System.out.println("Nenhuma midia cadastrada!");

                    } else {

                        for (Midia midia : sistema.listarMidias()) {

                            if (midia instanceof Filme) {
                                System.out.println("Tipo: Filme");
                            } else if (midia instanceof Serie) {
                                System.out.println("Tipo: Serie");
                            }

                            System.out.println("Titulo: " + midia.getTitulo());
                            System.out.println("Genero: " + midia.getGenero());
                            System.out.println("Ano: " + midia.getAnoLancamento());
                            System.out.println("Duracao total: " + midia.duracao() + " minutos");
                            System.out.println("-------------------------");
                        }
                    }

                } else if (opcao == 4) {

                    System.out.println();
                    System.out.println("===== BUSCAR MIDIA =====");

                    System.out.print("Digite o titulo da midia: ");
                    String titulo = entrada.nextLine();

                    Midia midia = sistema.buscarMidia(titulo);

                    if (midia != null) {

                        System.out.println("Midia encontrada!");

                        if (midia instanceof Filme) {
                            System.out.println("Tipo: Filme");
                        } else if (midia instanceof Serie) {
                            System.out.println("Tipo: Serie");
                        }

                        System.out.println("Titulo: " + midia.getTitulo());
                        System.out.println("Genero: " + midia.getGenero());
                        System.out.println("Ano: " + midia.getAnoLancamento());
                        System.out.println("Duracao total: " + midia.duracao() + " minutos");

                    } else {

                        System.out.println("Midia nao encontrada!");
                    }

                } else if (opcao == 5) {

                    try {

                        sistema.salvarArquivo();

                        System.out.println("Arquivo salvo com sucesso!");

                    } catch (IOException e) {

                        System.out.println("Erro ao salvar arquivo: " + e.getMessage());
                    }

                } else if (opcao == 6) {

                    try {

                        sistema.carregarArquivo();

                        System.out.println("Arquivo carregado com sucesso!");

                    } catch (IOException e) {

                        System.out.println("Erro ao carregar arquivo: " + e.getMessage());
                    }

                } else if (opcao == 0) {

                    System.out.println("Encerrando o sistema...");

                } else {

                    System.out.println("Opcao invalida!");
                }

            } catch (InputMismatchException e) {

                System.out.println("Erro: Digite apenas numeros nos campos numericos!");
                entrada.nextLine();

            } catch (IllegalArgumentException e) {

                System.out.println("Erro: " + e.getMessage());
            }
        }

        entrada.close();
    }
}
