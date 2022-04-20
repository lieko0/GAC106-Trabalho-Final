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
public class Veiculo extends ItemDinamico {

    private List<Localizacao> caminho;
    private boolean carrengado;
    private boolean indoChamado;
    ItemDinamico passageiro;

    public Veiculo(Localizacao localizacao, int tipo, String path_imagem) {
        super(localizacao, tipo, path_imagem);
        this.caminho = new ArrayList<Localizacao>();
        this.carrengado = false;
    }

    public Veiculo(ItemDinamico itemDinamico) {
        super(itemDinamico.getLocalizacaoAtual(), itemDinamico.getTipo(), itemDinamico.getPath_imagem());
        this.caminho = new ArrayList<Localizacao>();
    }

    public boolean getCarregando() {
        return carrengado;
    }

    public void setCarregando(boolean carregando) {
        this.carrengado = carregando;
    }

    public boolean getIndoChamado() {
        return indoChamado;
    }

    public void setIndoChamado(boolean indoChamado) {
        this.indoChamado = indoChamado;
    }

    public void setPassageiro(ItemDinamico passageiro) {
        this.passageiro = passageiro;
    }

    public ItemDinamico getPassageiro() {
        return passageiro;
    }

    @Override
    public List<Localizacao> getCaminho() {
        return caminho;
    }

    @Override
    public void setCaminho(List<Localizacao> caminho) {

        if (caminho.size() == 0)
            this.setComCaminho(false);
        else {
            this.caminho = new ArrayList<Localizacao>(caminho);
            this.setComCaminho(true);
        }

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
            // System.out.print(" new|>");
            // for (Localizacao a : this.getCaminho()) {
            // System.out.print(" ->- " + a + " ");
            // }
            // System.out.print(" >|\n");
            caminho.remove(0);
            this.executarAcao();
        } else {
            this.setComCaminho(false);
        }

    }

}
