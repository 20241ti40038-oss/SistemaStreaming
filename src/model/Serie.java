
package model;

public class Serie extends Midia {

    private int numeroTemporadas;
    private int numeroEpisodios;
    private int duracaoEpisodio;

    public Serie(String titulo, String genero, int anoLancamento,
                 int numeroTemporadas, int numeroEpisodios,
                 int duracaoEpisodio) {

        super(titulo, genero, anoLancamento);

        validarSerie(numeroTemporadas, numeroEpisodios, duracaoEpisodio);

        this.numeroTemporadas = numeroTemporadas;
        this.numeroEpisodios = numeroEpisodios;
        this.duracaoEpisodio = duracaoEpisodio;
    }

    private void validarSerie(int numeroTemporadas,
                             int numeroEpisodios,
                             int duracaoEpisodio) {

        if (numeroTemporadas < 1) {
            throw new IllegalArgumentException(
                "A serie deve ter no minimo 1 temporadas!"
            );
        }

        if (numeroEpisodios < 1) {
            throw new IllegalArgumentException(
                "Cada temporada deve ter pelo menos 1 episodio!"
            );
        }

        if (numeroEpisodios <= 5) {

            if (duracaoEpisodio < 45 || duracaoEpisodio > 50) {
                throw new IllegalArgumentException(
                    "A duracao deve ser entre 45 e 50 minutos!"
                );
            }

        } else {

            if (duracaoEpisodio < 30) {
                throw new IllegalArgumentException(
                    "A duracao deve ser de no minimo 30 minutos!"
                );
            }
        }
    }

    public int getNumeroTemporadas() {
        return numeroTemporadas;
    }

    public void setNumeroTemporadas(int numeroTemporadas) {
        validarSerie(numeroTemporadas, this.numeroEpisodios,
                     this.duracaoEpisodio);
        this.numeroTemporadas = numeroTemporadas;
    }

    public int getNumeroEpisodios() {
        return numeroEpisodios;
    }

    public void setNumeroEpisodios(int numeroEpisodios) {
        validarSerie(this.numeroTemporadas, numeroEpisodios,
                     this.duracaoEpisodio);
        this.numeroEpisodios = numeroEpisodios;
    }

    public int getDuracaoEpisodio() {
        return duracaoEpisodio;
    }

    public void setDuracaoEpisodio(int duracaoEpisodio) {
        validarSerie(this.numeroTemporadas, this.numeroEpisodios,
                     duracaoEpisodio);
        this.duracaoEpisodio = duracaoEpisodio;
    }

    @Override
    public int duracao() {
        return numeroTemporadas * numeroEpisodios * duracaoEpisodio;
    }
}
