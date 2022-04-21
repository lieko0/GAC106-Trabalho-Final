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
    private boolean comChamado;
    private Chamado chamado;
    ItemDinamico passageiro;
    String path_imagemS;
    String path_imagemL;
    String path_imagemO;
    String path_imagemN;
    String path_imagemS_carregando;
    String path_imagemL_carregando;
    String path_imagemO_carregando;
    String path_imagemN_carregando;

    public Veiculo(Localizacao localizacao, int tipo, String path_imagemN, String path_imagemS, String path_imagemL,
            String path_imagemO, String path_imagemN_carregando, String path_imagemS_carregando,
            String path_imagemL_carregando,
            String path_imagemO_carregando) {
        super(localizacao, tipo, path_imagemN);
        this.path_imagemS = path_imagemS;
        this.path_imagemL = path_imagemL;
        this.path_imagemO = path_imagemO;
        this.path_imagemN = path_imagemN;
        this.path_imagemS_carregando = path_imagemS_carregando;
        this.path_imagemL_carregando = path_imagemL_carregando;
        this.path_imagemO_carregando = path_imagemO_carregando;
        this.path_imagemN_carregando = path_imagemN_carregando;
        this.caminho = new ArrayList<Localizacao>();
        this.carrengado = false;
        this.indoChamado = false;
        this.comChamado = false;
    }

    public Veiculo(ItemDinamico itemDinamico) {
        super(itemDinamico.getLocalizacaoAtual(), itemDinamico.getTipo(), itemDinamico.getPath_imagem());
        this.caminho = new ArrayList<Localizacao>();
    }

    public Chamado getChamado() {
        return chamado;
    }

    public void setComChamado(boolean comChamado) {
        this.comChamado = comChamado;
    }

    public boolean getComChamado() {
        return comChamado;
    }

    public void setChamado(Chamado chamado) {
        this.chamado = chamado;
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
                    if (this.carrengado)
                        this.setPath_imagem(this.path_imagemO_carregando);
                    else
                        this.setPath_imagem(this.path_imagemO);

                } else {
                    if (this.carrengado)
                        this.setPath_imagem(this.path_imagemL_carregando);
                    else
                        this.setPath_imagem(this.path_imagemL);
                }
            } else if (this.getLocalizacaoAtual().getY() != this.getLocalizacaoDestino().getY()) {
                if (this.getLocalizacaoAtual().getY() > this.getLocalizacaoDestino().getY()) {
                    if (this.carrengado)
                        this.setPath_imagem(this.path_imagemN_carregando);
                    else
                        this.setPath_imagem(this.path_imagemN);
                } else {
                    if (this.carrengado)
                        this.setPath_imagem(this.path_imagemS_carregando);
                    else
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
