package simulacao;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Entidades {
    Mapa mapa;
    private List<ItemDinamico> todasEntidades;
    private List<ItemDinamico> populacao;
    private List<ItemDinamico> frota;
    private int largura;
    private int altura;
    private static Random rand = new Random();
    private Graph ruaGraph;
    private Graph calcadaGraph;

    // private List<Chamado> chamados;
    boolean chamadoAberto;

    public Entidades(Mapa mapa) {
        this.altura = mapa.getAltura();
        this.largura = mapa.getLargura();

        this.mapa = mapa;

        this.todasEntidades = new ArrayList<ItemDinamico>();
        this.populacao = new ArrayList<ItemDinamico>();
        this.frota = new ArrayList<ItemDinamico>();

        // this.chamados = new ArrayList<Chamado>();
        chamadoAberto = false;
        System.out.println("Gerando grafos ...");
        this.ruaGraph = new Graph(altura, largura, mapa.getTipoRua(),
                mapa.getTipoFaixaPedestre());
        this.calcadaGraph = new Graph(altura, largura, mapa.getTipoCalcada(),
                mapa.getTipoFaixaPedestre());
        this.ruaGraph.criarGrafo(mapa.getItens(), altura, largura);
        this.calcadaGraph.criarGrafo(mapa.getItens(), altura, largura);
    }

    public void criarPopulacao() {
        System.out.println("Gerando entidades ...");
        for (int i = 0; i < 3; i++) {
            if (i % 3 == 0) {
                this.adicionarPessoa("Imagens/pessoa.png");
            } else if (i % 3 == 1) {
                this.adicionarPessoa("Imagens/pessoa1.png");
            } else {
                this.adicionarPessoa("Imagens/pessoa2.png");
            }

            if (i % 3 == 0) {
                this.adicionarVeiculo("Imagens/veiculo.png");
            } else if (i % 3 == 1) {
                this.adicionarVeiculo("Imagens/veiculo1.png");
            } else {
                this.adicionarVeiculo("Imagens/veiculo2.png");
            }

        }

    }
    /*
     * private boolean verificaOcupado(ItemDinamico d) {
     * return true;
     * }
     */

    private void adicionarPessoa(String path_imagem) {

        ItemMapa umItem = mapa.getCalcada().get((int) Math.floor(Math.random() *
                mapa.getCalcada().size()));
        // ItemMapa umItem = mapa.getFaixa().get((int) Math.floor(Math.random() *
        // mapa.getFaixa().size()));
        Localizacao localizacao = umItem.getLocalizacaoAtual();
        // // System.out.print(" ~{" + localizacao + "}~ ");
        int tipo = mapa.getTipoCalcada();
        ItemDinamico p = new Pessoa(localizacao, tipo, path_imagem);

        Localizacao destino = mapa.getCalcada().get((int) Math.floor(rand.nextDouble() * mapa.getCalcada().size()))
                .getLocalizacaoAtual();
        // // System.out.print(" ^{" + destino + "}^ ");

        p.setCaminho(calcadaGraph.menosCaminhoL(p.getLocalizacaoAtual(), destino));

        // // System.out.print("\n |>");
        // for (Localizacao a : p.getCaminho()) {
        // // System.out.print(" ->- " + a + " ");
        // }
        todasEntidades.add(p);
        populacao.add(p);
        // // System.out.print(" >|\n");
    }

    private void adicionarVeiculo(String path_imagem) {
        ItemMapa umItem = mapa.getRuas().get((int) Math.floor(Math.random() * mapa.getRuas().size()));
        Localizacao localizacao = umItem.getLocalizacaoAtual();
        // // System.out.print(" ~{" + localizacao + "}~ ");
        int tipo = mapa.getTipoRua();
        ItemDinamico v = new Veiculo(localizacao, tipo, path_imagem);

        Localizacao destino = mapa.getRuas().get((int) Math.floor(rand.nextDouble() * mapa.getRuas().size()))
                .getLocalizacaoAtual();
        // // System.out.print(" ^{" + destino + "}^ ");
        v.setCaminho(ruaGraph.menosCaminhoL(v.getLocalizacaoAtual(), destino));
        // // System.out.print("\n |>");
        // for (Localizacao a : v.getCaminho()) {
        // // System.out.print(" ->- " + a + " ");
        // }
        todasEntidades.add(v);
        frota.add(v);
        // // System.out.print(" >|\n");
    }

    public List<ItemDinamico> getTodasEntidades() {
        return todasEntidades;
    }

    public List<ItemDinamico> getFrota() {
        return frota;
    }

    public List<ItemDinamico> getPopulacao() {
        return populacao;
    }

    public void executar() {

        for (ItemDinamico p : this.getPopulacao()) {
            int indexE = todasEntidades.indexOf(p);
            int indexP = populacao.indexOf(p);
            if (!p.getComCaminho()) {

                // int choice = rand.nextInt(2);
                Localizacao destino = mapa.getCalcada()
                        .get((int) Math.floor(rand.nextDouble() * mapa.getCalcada().size()))
                        .getLocalizacaoAtual();

                // // System.out.print(" ^{" + destino + "}^ ");
                p.setCaminho(calcadaGraph.menosCaminhoL(p.getLocalizacaoAtual(), destino));

            }

            p.mover();
            // System.out.print(" ^{" + p.getLocalizacaoAtual() + "}^ ");
            this.todasEntidades.set(indexE, p);
            this.populacao.set(indexP, p);
        }

        for (ItemDinamico v : this.getFrota()) {
            // System.out.print(" ^{" + v.getComCaminho() + "}^ ");
            int indexE = todasEntidades.indexOf(v);
            int indexF = frota.indexOf(v);
            if (!v.getComCaminho()) {

                if (chamadoAberto) {

                } else {
                    Localizacao destino = mapa.getRuas()
                            .get((int) Math.floor(rand.nextDouble() * mapa.getRuas().size()))
                            .getLocalizacaoAtual();
                    // // System.out.print(" ^{" + destino + "}^ ");

                    v.setCaminho(ruaGraph.menosCaminhoL(v.getLocalizacaoAtual(), destino));
                }

            }
            boolean pessoaNaFaixa = false;
            if (v.getCaminho().size() > 0) {
                Localizacao proxLoc = v.getCaminho().get(0);
                // System.out.print(" #{" + v.getLocalizacaoAtual() + "}");
                // System.out.print(" -> {" + proxLoc + "}# ");

                for (ItemDinamico pess : populacao) {
                    if (pess.getLocalizacaoAtual().getX() == proxLoc.getX()
                            && pess.getLocalizacaoAtual().getY() == proxLoc.getY()) {
                        System.out.print(" col+{" + proxLoc + "}+ ");
                        pessoaNaFaixa = true;
                    }
                }

            }
            if (pessoaNaFaixa == false) {
                // System.out.print(" ~{" + pessoaNaFaixa + "}~ \n");
                v.mover();
                this.todasEntidades.set(indexE, v);
                this.frota.set(indexF, v);
            }

        }

    }

}
