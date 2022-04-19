package simulacao;
//package simulacao;

import java.awt.Image;
import java.util.List;

import javax.swing.ImageIcon;

/**
 * Representa os veiculos da simulacao.
 * 
 * @author David J. Barnes and Michael Kolling and Luiz Merschmann
 */
public abstract class ItemDinamico {
    private Localizacao localizacaoAtual;
    private Localizacao localizacaoDestino;
    private Image imagem;
    private String path_imagem;
    private int tipo;
    private boolean comCaminho;
    private boolean moveu;

    public ItemDinamico(Localizacao localizacao, int tipo, String path_imagem) {
        this.localizacaoAtual = localizacao;
        this.tipo = tipo;
        this.path_imagem = path_imagem;
        imagem = new ImageIcon(getClass().getResource(path_imagem)).getImage();// Imagens/veiculo.jpg
        this.moveu = false;
    }

    public int getTipo() {
        return tipo;
    }

    public String getPath_imagem() {
        return path_imagem;
    }

    public Localizacao getLocalizacaoAtual() {
        return localizacaoAtual;
    }

    public Localizacao getLocalizacaoDestino() {
        return localizacaoDestino;
    }

    public Image getImagem() {
        return imagem;
    }

    public boolean getComCaminho() {
        return comCaminho;
    }

    public boolean getMoveu() {
        return moveu;
    }

    public void setComCaminho(boolean comCaminho) {
        this.comCaminho = comCaminho;
    }

    public void setMoveu(boolean moveu) {
        this.moveu = moveu;
    }

    public void setLocalizacaoAtual(Localizacao localizacaoAtual) {
        // System.out.print(" {" + localizacaoAtual + "} ");
        this.localizacaoAtual = localizacaoAtual;
    }

    public void setLocalizacaoDestino(Localizacao localizacaoDestino) {
        this.localizacaoDestino = localizacaoDestino;
    }

    public void executarAcao() {
        Localizacao destino = getLocalizacaoDestino();
        if (destino != null) {
            Localizacao proximaLocalizacao = getLocalizacaoAtual().proximaLocalizacao(localizacaoDestino);
            setLocalizacaoAtual(proximaLocalizacao);
            // System.out.print(" {" + proximaLocalizacao + "} ");
        }
    }

    public abstract void mover();

    public abstract List<Localizacao> getCaminho();

    public abstract void setCaminho(List<Localizacao> caminho);

    @Override
    public ItemDinamico clone() throws CloneNotSupportedException {
        return (ItemDinamico) super.clone();
    }
}
