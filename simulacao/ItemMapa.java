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

    public ItemMapa(Localizacao localizacao, int tipo, String path_imagem) {
        this.localizacaoAtual = localizacao;
        this.tipo = tipo;
        // System.out.println(path_imagem);
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

    public void setLocalizacaoAtual(Localizacao localizacaoAtual) {
        this.localizacaoAtual = localizacaoAtual;
    }

}
