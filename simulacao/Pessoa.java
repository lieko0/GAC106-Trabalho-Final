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
    private boolean chamadoAtivo;
    String path_imagemNS;
    String path_imagemLO;
    String path_imagemChamado;

    public Pessoa(Localizacao localizacao, int tipo, String path_imagemNS, String path_imagemLO,
            String path_imagemChamado) {
        super(localizacao, tipo, path_imagemNS);
        this.path_imagemNS = path_imagemNS;
        this.path_imagemLO = path_imagemLO;
        this.caminho = new ArrayList<Localizacao>();
        this.carregado = false;
        this.chamadoAtivo = false;
        this.path_imagemChamado = path_imagemChamado;
    }

    public Pessoa(ItemDinamico itemDinamico, String path_imagemNS, String path_imagemLO,
            String path_imagemChamado) {
        super(itemDinamico.getLocalizacaoAtual(), itemDinamico.getTipo(), itemDinamico.getPath_imagem());
        this.caminho = new ArrayList<Localizacao>();
        this.path_imagemNS = path_imagemNS;
        this.path_imagemLO = path_imagemLO;
        this.carregado = false;
        this.chamadoAtivo = false;
        this.path_imagemChamado = path_imagemChamado;
    }

    public void setChamadoAtivo(boolean chamadoAtivo) {
        this.chamadoAtivo = chamadoAtivo;
    }

    public boolean getChamadoAtivo() {
        return chamadoAtivo;
    }

    public boolean getCarregado() {
        return carregado;
    }

    public void setCarregado(boolean carregado) {
        this.carregado = carregado;
        if (this.carregado)
            this.setVisivel(false);
        else
            this.setVisivel(true);

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

            if (this.getLocalizacaoAtual().getX() != this.getLocalizacaoDestino().getX()) {
                this.setPath_imagem(this.path_imagemLO);

            } else if (this.getLocalizacaoAtual().getY() != this.getLocalizacaoDestino().getY()) {

                this.setPath_imagem(this.path_imagemNS);

            }

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
            if (chamadoAtivo == true) {
                this.setPath_imagem(this.path_imagemChamado);
            }
        }

    }

}
