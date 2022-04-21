package simulacao;

/**
 * Representa os chamados de viagens, em que uma pessoa é levada por uma moto.
 * 
 * @author TP3 - ARTHUR HAUCK DITTZ, MARCO ANTONIO MAGALHAES
 * 
 */
public class Chamado {
    private int indexPessoaPop;
    private int indexPessoaTot;
    private Localizacao inicio;
    private Localizacao destino;

    /**
     * Instanciando um chamado
     * 
     * @param indexPessoaPop : index da pessoa que fez o chamado (referente ao vetor
     *                       de populacao)
     * @param indexPessoaTot : index da pessoa que fez o chamado (referente ao vetor
     *                       de todasEntidades)
     * @param inicio         : localizacao inicial da pessoa que fez o chamado
     * @param destino        : localização de destino da pessoa que fez o chamado
     */
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
