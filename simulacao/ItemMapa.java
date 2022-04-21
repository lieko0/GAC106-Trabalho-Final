package simulacao;
//package simulacao;

import java.awt.Image;
import javax.swing.ImageIcon;

/**
 * Representa os da simulacao.
 * 
 * @author David J. Barnes and Michael Kolling and Luiz Merschmann
 */
public class ItemMapa {
    private Localizacao localizacaoAtual;
    private Image imagem;
    private int tipo;
    String path_imagem;

    public ItemMapa(Localizacao localizacao, int tipo, String path_imagem) {
        this.localizacaoAtual = localizacao;
        this.tipo = tipo;
        this.path_imagem = path_imagem;
        // // System.out.println(path_imagem);
        imagem = new ImageIcon(getClass().getResource(path_imagem)).getImage();// Imagens/veiculo.jpg
    }

    public Localizacao getLocalizacaoAtual() {
        return localizacaoAtual;
    }

    public int getTipo() {
        return tipo;
    }

    public Image getImagem() {
        return imagem;
    }

    public String getPath_imagem() {
        return path_imagem;
    }

    public void setPath_imagem(String path_imagem) {
        this.path_imagem = path_imagem;
        imagem = new ImageIcon(getClass().getResource(path_imagem)).getImage();
    }

    public void setLocalizacaoAtual(Localizacao localizacaoAtual) {
        this.localizacaoAtual = localizacaoAtual;
    }

}
