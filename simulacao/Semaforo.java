package simulacao;

/**
 * Representa os semaforos da simulacao.
 * 
 * @author TP3 - ARTHUR HAUCK DITTZ, MARCO ANTONIO MAGALHAES
 * @author David J. Barnes and Michael Kolling and Luiz Merschmann
 * 
 */
public class Semaforo extends ItemMapa {
    private boolean estado;
    private String path_imagemG;
    private String path_imagemR;
    private int tempo;
    private int runTempo;

    public Semaforo(Localizacao localizacao, int tipo, String path_imagemG, String path_imagemR, int tempo,
            boolean inicio) {

        super(localizacao, tipo, path_imagemG);
        this.estado = true;
        this.path_imagemR = path_imagemR;
        this.path_imagemG = path_imagemG;
        this.tempo = tempo;
        this.runTempo = tempo;
        if (!inicio)
            this.mudaEstado();
    }

    public void mudaEstado() {
        estado = !estado;
        if (estado)
            this.setPath_imagem(path_imagemG);
        else
            this.setPath_imagem(path_imagemR);
    }

    public boolean getEstado() {
        return this.estado;
    }

    public void run() {
        if (this.runTempo > 0) {
            this.runTempo--;
        } else {
            this.runTempo = this.tempo;
            this.mudaEstado();
        }
    }

}
