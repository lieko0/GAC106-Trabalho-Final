package simulacao;

public class Chamado {
    private int indexPessoaPop;
    private int indexPessoaTot;
    private Localizacao inicio;
    private Localizacao destino;

    public Chamado(int indexPessoaPop, int indexPessoaTot, Localizacao inicio, Localizacao destino) {
        this.indexPessoaPop = indexPessoaPop;
        this.indexPessoaTot = indexPessoaTot;
        this.inicio = inicio;
        this.destino = destino;
    }

    public int getIndexPessoaPop() {
        return indexPessoaPop;
    }

    public int getIndexPessoaTot() {
        return indexPessoaTot;
    }

    public Localizacao getInicio() {
        return inicio;
    }

    public Localizacao getDestino() {
        return destino;
    }

}
