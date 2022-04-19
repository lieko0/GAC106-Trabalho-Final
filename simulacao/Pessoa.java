package simulacao;
//package simulacao;

//import java.awt.Image;
import java.util.ArrayList;
import java.util.List;

//import javax.swing.ImageIcon;

/**
 * Representa os veiculos da simulacao.
 * 
 * @author David J. Barnes and Michael Kolling and Luiz Merschmann
 */
public class Pessoa extends ItemDinamico {

    private List<Localizacao> caminho;
    private boolean carregado;

    public Pessoa(Localizacao localizacao, int tipo, String path_imagem) {
        super(localizacao, tipo, path_imagem);
        this.caminho = new ArrayList<Localizacao>();
        this.carregado = false;
    }

    public Pessoa(ItemDinamico itemDinamico) {
        super(itemDinamico.getLocalizacaoAtual(), itemDinamico.getTipo(), itemDinamico.getPath_imagem());
        this.caminho = new ArrayList<Localizacao>();
    }

    public boolean getCarregado() {
        return carregado;
    }

    public void setCarregado(boolean carregado) {
        this.carregado = carregado;
    }

    @Override
    public List<Localizacao> getCaminho() {
        return caminho;
    }

    @Override
    public void setCaminho(List<Localizacao> caminho) {
        this.caminho = new ArrayList<Localizacao>(caminho);
        if (caminho.size() > 0)
            this.setComCaminho(true);

    }

    @Override
    public void mover() {
        if (caminho.size() > 0) {
            this.setLocalizacaoDestino(caminho.get(0));
            // System.out.print("\n old|>");
            // for (Localizacao a : this.getCaminho()) {
            // System.out.print(" ->- " + a + " ");
            // }
            // System.out.print(" >|\n");
            caminho.remove(0);
            // System.out.print(" new|>");
            // for (Localizacao a : this.getCaminho()) {
            // System.out.print(" ->- " + a + " ");
            // }
            // System.out.print(" >|\n");
            this.executarAcao();
        } else {
            this.setComCaminho(false);
        }

    }

}
