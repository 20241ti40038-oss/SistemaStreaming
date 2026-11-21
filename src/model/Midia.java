package model;

public abstract class Midia {

    private String titulo;
    private String genero;
    private int anoLancamento;

    public Midia(String titulo, String genero, int anoLancamento) {

        if (titulo == null || titulo.trim().isEmpty()) {
            throw new IllegalArgumentException("O titulo nao pode estar vazio!");
        }

        if (genero == null || genero.trim().isEmpty()) {
            throw new IllegalArgumentException("O genero nao pode estar vazio!");
        }

        if (anoLancamento < 2000) {
            throw new IllegalArgumentException("O ano de lancamento deve ser 2000 ou posterior!");
        }

        this.titulo = titulo;
        this.genero = genero;
        this.anoLancamento = anoLancamento;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        if (titulo == null || titulo.trim().isEmpty()) {
            throw new IllegalArgumentException("O titulo nao pode estar vazio!");
        }

        this.titulo = titulo;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        if (genero == null || genero.trim().isEmpty()) {
            throw new IllegalArgumentException("O genero nao pode estar vazio!");
        }

        this.genero = genero;
    }

    public int getAnoLancamento() {
        return anoLancamento;
    }

    public void setAnoLancamento(int anoLancamento) {
        if (anoLancamento < 2000) {
            throw new IllegalArgumentException("O ano de lancamento deve ser 2000 ou posterior!");
        }

        this.anoLancamento = anoLancamento;
    }

    public abstract int duracao();
}
