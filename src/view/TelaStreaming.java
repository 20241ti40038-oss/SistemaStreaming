
package view;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.imageio.ImageIO;

import controller.StreamingController;
import model.Midia;
import model.Filme;
import model.Serie;

public class TelaStreaming {

    static Color fundo = new Color(37, 52, 71);
    static Color painel = new Color(52, 70, 92);
    static Color azul = new Color(183, 216, 237);
    static Color lilas = new Color(207, 201, 229);
    static Color rosa = new Color(241, 201, 220);
    static Color branco = new Color(234, 234, 242);

    static Font fonteTitulo = new Font("SansSerif", Font.BOLD, 40);
    static Font fonteTexto = new Font("SansSerif", Font.PLAIN, 15);
    static Font fonteBotao = new Font("SansSerif", Font.BOLD, 14);

    static StreamingController sistema = new StreamingController();

    static JPanel catalogo;
    static JFrame janela;

    static Map<String, ImageIcon> capas = new HashMap<>();

    static String chaveTMDB = System.getenv("TMDB_API_KEY");

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> criarTela());
    }

    public static void criarTela() {

        janela = new JFrame("SistemaStreaming");
        janela.setSize(1100, 760);
        janela.setMinimumSize(new Dimension(850, 600));
        janela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        janela.setLocationRelativeTo(null);

        JPanel principal = new JPanel(new BorderLayout(15, 15));
        principal.setBackground(fundo);
        principal.setBorder(
            BorderFactory.createEmptyBorder(0, 20, 20, 20)
        );

        Color[] coresRGB = {azul, lilas, rosa, lilas};
        final int[] indice = {0};

        JPanel cabecalho = new JPanel(new BorderLayout()) {

            protected void paintComponent(Graphics g) {
                super.paintComponent(g);

                Graphics2D g2 = (Graphics2D) g;

                GradientPaint degrade = new GradientPaint(
                    0, 0, coresRGB[indice[0]],
                    getWidth(), 0,
                    coresRGB[(indice[0] + 1) % coresRGB.length]
                );

                g2.setPaint(degrade);
                g2.fillRect(0, 0, getWidth(), getHeight());
            }
        };

        cabecalho.setPreferredSize(new Dimension(1000, 130));
        cabecalho.setBorder(
            BorderFactory.createEmptyBorder(20, 30, 20, 30)
        );

        JLabel titulo = new JLabel("STREAMING");
        titulo.setFont(fonteTitulo);
        titulo.setForeground(fundo);

        JLabel subtitulo = new JLabel(
            "Seu universo de filmes e series"
        );
        subtitulo.setFont(
            new Font("SansSerif", Font.PLAIN, 17)
        );
        subtitulo.setForeground(fundo);

        JPanel textos = new JPanel(new GridLayout(2, 1));
        textos.setOpaque(false);
        textos.add(titulo);
        textos.add(subtitulo);

        cabecalho.add(textos, BorderLayout.CENTER);

        Timer animacao = new Timer(1200, e -> {
            indice[0] = (indice[0] + 1) % coresRGB.length;
            cabecalho.repaint();
        });

        animacao.start();

        JPanel centro = new JPanel(new BorderLayout(10, 10));
        centro.setBackground(fundo);

        JLabel tituloCatalogo = new JLabel("MEU CATALOGO");
        tituloCatalogo.setForeground(branco);
        tituloCatalogo.setFont(
            new Font("SansSerif", Font.BOLD, 23)
        );

        catalogo = new JPanel(new FlowLayout(
            FlowLayout.LEFT, 18, 18
        ));
        catalogo.setBackground(painel);

        JScrollPane rolagem = new JScrollPane(catalogo);
        rolagem.getVerticalScrollBar().setUnitIncrement(18);
        rolagem.setBorder(
            BorderFactory.createLineBorder(lilas, 2)
        );

        centro.add(tituloCatalogo, BorderLayout.NORTH);
        centro.add(rolagem, BorderLayout.CENTER);

        JPanel inferior = new JPanel(new BorderLayout(10, 10));
        inferior.setBackground(fundo);

        JLabel tituloAcoes = new JLabel("GERENCIAR CATALOGO");
        tituloAcoes.setForeground(branco);
        tituloAcoes.setFont(
            new Font("SansSerif", Font.BOLD, 21)
        );

        JPanel botoes = new JPanel(new GridLayout(2, 3, 12, 12));
        botoes.setBackground(fundo);

        JButton filme = criarBotao("Cadastrar filme", azul);
        JButton serie = criarBotao("Cadastrar serie", lilas);
        JButton listar = criarBotao("Listar midias", rosa);
        JButton buscar = criarBotao("Buscar midia", lilas);
        JButton salvar = criarBotao("Salvar arquivo", azul);
        JButton carregar = criarBotao("Carregar arquivo", rosa);

        botoes.add(filme);
        botoes.add(serie);
        botoes.add(listar);
        botoes.add(buscar);
        botoes.add(salvar);
        botoes.add(carregar);

        inferior.add(tituloAcoes, BorderLayout.NORTH);
        inferior.add(botoes, BorderLayout.CENTER);

        principal.add(cabecalho, BorderLayout.NORTH);
        principal.add(centro, BorderLayout.CENTER);
        principal.add(inferior, BorderLayout.SOUTH);

        janela.add(principal);

        filme.addActionListener(e -> cadastrarFilme());
        serie.addActionListener(e -> cadastrarSerie());
        listar.addActionListener(e -> listarMidias());
        buscar.addActionListener(e -> buscarMidia());
        salvar.addActionListener(e -> salvarArquivo());
        carregar.addActionListener(e -> carregarArquivo());

        listarMidias();

        janela.setVisible(true);
    }

    public static JButton criarBotao(String texto, Color cor) {

        JButton botao = new JButton(texto);
        botao.setFont(fonteBotao);
        botao.setBackground(cor);
        botao.setForeground(fundo);
        botao.setFocusPainted(false);
        botao.setOpaque(true);
        botao.setBorder(
            BorderFactory.createEmptyBorder(14, 10, 14, 10)
        );

        return botao;
    }

    public static JTextField criarCampo() {

        JTextField campo = new JTextField();

        campo.setBackground(painel);
        campo.setForeground(branco);
        campo.setCaretColor(branco);
        campo.setFont(fonteTexto);

        campo.setBorder(
            BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(lilas),
                BorderFactory.createEmptyBorder(8, 10, 8, 10)
            )
        );

        return campo;
    }

    public static JLabel criarRotulo(String texto) {

        JLabel rotulo = new JLabel(texto);
        rotulo.setForeground(azul);
        rotulo.setFont(
            new Font("SansSerif", Font.BOLD, 14)
        );

        return rotulo;
    }

    public static void cadastrarFilme() {

        abrirCadastro(false);
    }

    public static void cadastrarSerie() {

        abrirCadastro(true);
    }

    public static void abrirCadastro(boolean ehSerie) {

        String tipo = ehSerie ? "serie" : "filme";

        JDialog dialogo = new JDialog(
            janela, "Cadastrar " + tipo, true
        );

        dialogo.setSize(490, ehSerie ? 670 : 530);
        dialogo.setLocationRelativeTo(janela);
        dialogo.setResizable(false);

        JPanel principal = new JPanel(new BorderLayout(15, 15));
        principal.setBackground(fundo);
        principal.setBorder(
            BorderFactory.createEmptyBorder(22, 28, 22, 28)
        );

        JLabel titulo = new JLabel("Cadastrar " + tipo);
        titulo.setFont(
            new Font("SansSerif", Font.BOLD, 27)
        );
        titulo.setForeground(azul);

        JPanel campos = new JPanel();
        campos.setLayout(new BoxLayout(
            campos, BoxLayout.Y_AXIS
        ));
        campos.setBackground(fundo);

        JTextField campoTitulo = criarCampo();
        JTextField campoGenero = criarCampo();
        JTextField campoAno = criarCampo();
        JTextField campoDuracao = criarCampo();

        JTextField campoTemporadas = criarCampo();
        JTextField campoEpisodios = criarCampo();

        adicionarCampo(campos, "Titulo", campoTitulo);
        adicionarCampo(campos, "Genero", campoGenero);
        adicionarCampo(campos, "Ano de lancamento", campoAno);

        if (ehSerie) {
            adicionarCampo(
                campos, "Numero de temporadas", campoTemporadas
            );
            adicionarCampo(
                campos, "Numero de episodios", campoEpisodios
            );
            adicionarCampo(
                campos, "Duracao de cada episodio", campoDuracao
            );
        } else {
            adicionarCampo(
                campos, "Duracao em minutos", campoDuracao
            );
        }

        JPanel botoes = new JPanel(new GridLayout(1, 2, 12, 0));
        botoes.setOpaque(false);

        JButton cancelar = criarBotao("Cancelar", lilas);
        JButton cadastrar = criarBotao("Cadastrar", azul);

        botoes.add(cancelar);
        botoes.add(cadastrar);

        cancelar.addActionListener(e -> dialogo.dispose());

        cadastrar.addActionListener(e -> {

            try {

                String nome = campoTitulo.getText().trim();
                String genero = campoGenero.getText().trim();

                if (nome.isEmpty() || genero.isEmpty()) {
                    throw new IllegalArgumentException(
                        "Preencha o titulo e o genero."
                    );
                }

                int ano = Integer.parseInt(
                    campoAno.getText().trim()
                );

                int duracao = Integer.parseInt(
                    campoDuracao.getText().trim()
                );

                if (ehSerie) {

                    int temporadas = Integer.parseInt(
                        campoTemporadas.getText().trim()
                    );

                    int episodios = Integer.parseInt(
                        campoEpisodios.getText().trim()
                    );

                    Serie serie = new Serie(
                        nome, genero, ano,
                        temporadas, episodios, duracao
                    );

                    sistema.cadastrarMidia(serie);

                } else {

                    Filme filme = new Filme(
                        nome, genero, ano, duracao
                    );

                    sistema.cadastrarMidia(filme);
                }

                dialogo.dispose();
                listarMidias();

                JOptionPane.showMessageDialog(
                    janela, "Cadastro realizado com sucesso!"
                );

            } catch (NumberFormatException erro) {

                JOptionPane.showMessageDialog(
                    dialogo, "Preencha os numeros corretamente."
                );

            } catch (IllegalArgumentException erro) {

                JOptionPane.showMessageDialog(
                    dialogo, "Erro: " + erro.getMessage()
                );
            }
        });

        principal.add(titulo, BorderLayout.NORTH);
        principal.add(campos, BorderLayout.CENTER);
        principal.add(botoes, BorderLayout.SOUTH);

        dialogo.add(principal);
        dialogo.setVisible(true);
    }

    public static void adicionarCampo(
        JPanel painelCampos, String texto, JTextField campo
    ) {

        JLabel rotulo = criarRotulo(texto);

        rotulo.setAlignmentX(Component.LEFT_ALIGNMENT);
        campo.setAlignmentX(Component.LEFT_ALIGNMENT);
        campo.setMaximumSize(new Dimension(1000, 38));

        painelCampos.add(rotulo);
        painelCampos.add(Box.createVerticalStrut(5));
        painelCampos.add(campo);
        painelCampos.add(Box.createVerticalStrut(12));
    }

    public static void listarMidias() {

        catalogo.removeAll();

        if (sistema.listarMidias().isEmpty()) {

            JLabel vazio = new JLabel(
                "Nenhum filme ou serie cadastrado."
            );

            vazio.setForeground(branco);
            vazio.setFont(fonteTexto);
            catalogo.add(vazio);

        } else {

            for (Midia midia : sistema.listarMidias()) {
                catalogo.add(criarCartao(midia));
            }
        }

        catalogo.revalidate();
        catalogo.repaint();
    }

    public static JPanel criarCartao(Midia midia) {

        JPanel cartao = new JPanel(new BorderLayout(5, 8));
        cartao.setBackground(fundo);
        cartao.setPreferredSize(new Dimension(185, 325));
        cartao.setBorder(
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        );

        JLabel capa = new JLabel(
            "SEM CAPA", SwingConstants.CENTER
        );

        capa.setOpaque(true);
        capa.setBackground(lilas);
        capa.setForeground(fundo);
        capa.setFont(
            new Font("SansSerif", Font.BOLD, 17)
        );
        capa.setPreferredSize(new Dimension(165, 230));

        String chave = chaveDaMidia(midia);

        if (capas.containsKey(chave)) {

            ImageIcon imagem = capas.get(chave);

            if (imagem != null) {
                capa.setIcon(imagem);
                capa.setText("");
            }

        } else {

            capas.put(chave, null);
            buscarCapa(midia, capa);
        }

        String tipo = midia instanceof Filme ? "FILME" : "SERIE";

        JLabel titulo = new JLabel(
            "<html><center>" +
            escaparHtml(midia.getTitulo()) +
            "</center></html>",
            SwingConstants.CENTER
        );

        titulo.setForeground(branco);
        titulo.setFont(
            new Font("SansSerif", Font.BOLD, 14)
        );

        JLabel detalhes = new JLabel(
            tipo + " | " + midia.getAnoLancamento(),
            SwingConstants.CENTER
        );

        detalhes.setForeground(azul);
        detalhes.setFont(
            new Font("SansSerif", Font.PLAIN, 12)
        );

        JPanel informacoes = new JPanel(new GridLayout(2, 1));
        informacoes.setOpaque(false);
        informacoes.add(titulo);
        informacoes.add(detalhes);

        cartao.add(capa, BorderLayout.CENTER);
        cartao.add(informacoes, BorderLayout.SOUTH);

        cartao.setToolTipText(
            midia.getGenero() + " | " +
            midia.duracao() + " minutos"
        );

        return cartao;
    }

    public static String escaparHtml(String texto) {

        return texto.replace("&", "&amp;")
                    .replace("<", "&lt;")
                    .replace(">", "&gt;");
    }

    public static String chaveDaMidia(Midia midia) {

        String tipo = midia instanceof Filme ? "filme" : "serie";

        return tipo + ":" +
            midia.getTitulo().toLowerCase().trim() + ":" +
            midia.getAnoLancamento();
    }

    public static void buscarCapa(Midia midia, JLabel capa) {

        if (chaveTMDB == null || chaveTMDB.trim().isEmpty()) {
            return;
        }

        String chave = chaveDaMidia(midia);

        capa.setText("BUSCANDO...");

        SwingWorker<ImageIcon, Void> tarefa =
            new SwingWorker<ImageIcon, Void>() {

            protected ImageIcon doInBackground() throws Exception {

                String tipo = midia instanceof Filme
                    ? "movie" : "tv";

                String endereco =
                    "https://api.themoviedb.org/3/search/" +
                    tipo + "?query=" +
                    URLEncoder.encode(
                        midia.getTitulo(), "UTF-8"
                    ) +
                    "&language=pt-BR" +
                    "&include_adult=false" +
                    "&api_key=" +
                    URLEncoder.encode(chaveTMDB, "UTF-8");

                if (midia instanceof Filme) {
                    endereco += "&year=" +
                        midia.getAnoLancamento();
                } else {
                    endereco += "&first_air_date_year=" +
                        midia.getAnoLancamento();
                }

                HttpURLConnection conexao =
                    (HttpURLConnection)
                    new URL(endereco).openConnection();

                conexao.setConnectTimeout(5000);
                conexao.setReadTimeout(5000);

                StringBuilder resposta = new StringBuilder();

                try (BufferedReader leitor =
                    new BufferedReader(
                        new InputStreamReader(
                            conexao.getInputStream(),
                            StandardCharsets.UTF_8
                        )
                    )) {

                    String linha;

                    while ((linha = leitor.readLine()) != null) {
                        resposta.append(linha);
                    }
                }

                Pattern padrao = Pattern.compile(
                    "\"poster_path\"\\s*:\\s*\"([^\"]+)\""
                );

                Matcher resultado = padrao.matcher(
                    resposta.toString()
                );

                if (!resultado.find()) {
                    return null;
                }

                String caminho = resultado.group(1);

                URL urlImagem = new URL(
                    "https://image.tmdb.org/t/p/w342" + caminho
                );

                URLConnection conexaoImagem =
                    urlImagem.openConnection();

                conexaoImagem.setConnectTimeout(5000);
                conexaoImagem.setReadTimeout(5000);

                BufferedImage imagem;

                try (InputStream entrada =
                    conexaoImagem.getInputStream()) {

                    imagem = ImageIO.read(entrada);
                }

                if (imagem == null) {
                    return null;
                }

                Image redimensionada = imagem.getScaledInstance(
                    165, 230, Image.SCALE_SMOOTH
                );

                return new ImageIcon(redimensionada);
            }

            protected void done() {

                try {

                    ImageIcon imagem = get();

                    capas.put(chave, imagem);

                    if (imagem != null) {
                        capa.setIcon(imagem);
                        capa.setText("");
                    } else {
                        capa.setText("SEM CAPA");
                    }

                } catch (Exception erro) {

                    capas.put(chave, null);
                    capa.setText("SEM CAPA");
                }
            }
        };

        tarefa.execute();
    }

    public static void buscarMidia() {

        String titulo = JOptionPane.showInputDialog(
            janela, "Digite o titulo da midia:"
        );

        if (titulo == null) return;

        Midia encontrada = sistema.buscarMidia(titulo);

        if (encontrada == null) {

            JOptionPane.showMessageDialog(
                janela, "Midia nao encontrada."
            );

            return;
        }

        catalogo.removeAll();
        catalogo.add(criarCartao(encontrada));
        catalogo.revalidate();
        catalogo.repaint();
    }

    public static void salvarArquivo() {

        try {

            sistema.salvarArquivo();

            JOptionPane.showMessageDialog(
                janela, "Arquivo salvo com sucesso!"
            );

        } catch (IOException erro) {

            JOptionPane.showMessageDialog(
                janela, "Erro ao salvar: " + erro.getMessage()
            );
        }
    }

    public static void carregarArquivo() {

        try {

            sistema.carregarArquivo();

            capas.clear();
            listarMidias();

            JOptionPane.showMessageDialog(
                janela, "Arquivo carregado com sucesso!"
            );

        } catch (IOException erro) {

            JOptionPane.showMessageDialog(
                janela, "Erro ao carregar: " + erro.getMessage()
            );

        } catch (IllegalArgumentException erro) {

            JOptionPane.showMessageDialog(
                janela, "Erro nos dados: " + erro.getMessage()
            );
        }
    }
}
