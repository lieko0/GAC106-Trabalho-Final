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
    String path_imagemS;
    String path_imagemL;
    String path_imagemO;
    String path_imagemN;

    public Veiculo(Localizacao localizacao, int tipo, String path_imagemN, String path_imagemS, String path_imagemL,
            String path_imagemO) {
        super(localizacao, tipo, path_imagemN);
        this.path_imagemS = path_imagemS;
        this.path_imagemL = path_imagemL;
        this.path_imagemO = path_imagemO;
        this.path_imagemN = path_imagemN;

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
            // System.out.print("\n|>" + this.getLocalizacaoAtual() + " " +
            // this.getLocalizacaoDestino());
            if (this.getLocalizacaoAtual().getX() != this.getLocalizacaoDestino().getX()) {
                if (this.getLocalizacaoAtual().getX() > this.getLocalizacaoDestino().getX()) {
                    // System.out.print(this.getPath_imagem());
                    this.setPath_imagem(this.path_imagemO);
                    // System.out.print(this.getPath_imagem());
                } else {
                    this.setPath_imagem(this.path_imagemL);
                }
            } else if (this.getLocalizacaoAtual().getY() != this.getLocalizacaoDestino().getY()) {
                if (this.getLocalizacaoAtual().getY() > this.getLocalizacaoDestino().getY()) {
                    this.setPath_imagem(this.path_imagemN);
                } else {
                    this.setPath_imagem(this.path_imagemS);
                }
            }
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
