package simulacao;

public class Chamado {
    public ItemDinamico cliente;
    public Localizacao destino;

    public Chamado(ItemDinamico cliente, Localizacao destino) {
        this.cliente = cliente;
        this.destino = destino;
    }

    public Localizacao getDestino() {
        return destino;
    }

    public ItemDinamico getCliente() {
        return cliente;
    }
}
