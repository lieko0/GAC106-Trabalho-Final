package simulacao;

import java.awt.Image;
import java.util.List;

import javax.swing.ImageIcon;

/**
 * Representa os itens dinâmicos da simulacao.
 * 
 * @author TP3 - ARTHUR HAUCK DITTZ, MARCO ANTONIO MAGALHAES
 * @author David J. Barnes and Michael Kolling and Luiz Merschmann
 * 
 */
public abstract class ItemDinamico {
    private Localizacao localizacaoAtual;
    private Localizacao localizacaoDestino;
    private Image imagem;
    private String path_imagem;
    private int tipo;
    private boolean comCaminho;
    private boolean visivel;

    /**
     * Instancia um item dinâmico
     * 
     */
    public ItemDinamico(Localizacao localizacao, int tipo, String path_imagem) {
        this.localizacaoAtual = localizacao;
        this.tipo = tipo;
        this.path_imagem = path_imagem;
        imagem = new ImageIcon(getClass().getResource(path_imagem)).getImage();// Imagens/veiculo.jpg
        this.visivel = true;
        this.comCaminho = false;
    }

    public int getTipo() {
        return tipo;
    }

    public String getPath_imagem() {
        return path_imagem;
    }

    public void setPath_imagem(String path_imagem) {
        this.path_imagem = path_imagem;
        imagem = new ImageIcon(getClass().getResource(path_imagem)).getImage();
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

    public boolean getVisivel() {
        return visivel;
    }

    public void setComCaminho(boolean comCaminho) {
        this.comCaminho = comCaminho;
    }

    public void setVisivel(boolean visivel) {
        this.visivel = visivel;
    }

    public void setLocalizacaoAtual(Localizacao localizacaoAtual) {
        this.localizacaoAtual = localizacaoAtual;
    }

    public void setLocalizacaoDestino(Localizacao localizacaoDestino) {
        this.localizacaoDestino = localizacaoDestino;
    }

    /**
     * Executa a ação de movimento
     * 
     */
    public void executarAcao() {
        Localizacao destino = getLocalizacaoDestino();
        if (destino != null) {
            setLocalizacaoAtual(getLocalizacaoDestino());
        }
    }

    /**
     * Executa a ação de movimento. Altera as imagens baseadas na orientação de
     * movimento
     * 
     */
    public abstract void mover();

    public abstract List<Localizacao> getCaminho();

    public abstract void setCaminho(List<Localizacao> caminho);

}
