
package model;

public class Filme extends Midia {

    private int duracaoMinutos;

    public Filme(String titulo, String genero, int anoLancamento, int duracaoMinutos) {
        super(titulo, genero, anoLancamento);

        if (duracaoMinutos < 50) {
            throw new IllegalArgumentException("O filme deve ter no minimo 50 minutos!");
        }

        this.duracaoMinutos = duracaoMinutos;
    }

    @Override
    public int duracao() {
        return duracaoMinutos;
    }

    public int getDuracaoMinutos() {
    return duracaoMinutos;
}

public void setDuracaoMinutos(int duracaoMinutos) {
    if (duracaoMinutos < 50) {
        throw new IllegalArgumentException("O filme deve ter no minimo 50 minutos!");
    }

    this.duracaoMinutos = duracaoMinutos;
}




}
