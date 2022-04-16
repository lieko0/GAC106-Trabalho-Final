package simulacao;

import java.lang.management.ThreadInfo;
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

    public Entidades(Mapa mapa) {
        this.altura = mapa.getAltura();
        this.largura = mapa.getLargura();

        this.mapa = mapa;

        this.todasEntidades = new ArrayList<ItemDinamico>();
        this.populacao = new ArrayList<ItemDinamico>();
        this.frota = new ArrayList<ItemDinamico>();
        this.ruaGraph = new Graph(altura, largura, mapa.getTipoRua(),
                mapa.getTipoFaixaPedestre());
        this.calcadaGraph = new Graph(altura, largura, mapa.getTipoCalcada(),
                mapa.getTipoFaixaPedestre());
        this.ruaGraph.criarGrafo(mapa.getItens(), altura, largura);
        this.calcadaGraph.criarGrafo(mapa.getItens(), altura, largura);
    }

    public void criarPopulacao() {
        for (int i = 0; i < 6; i++) {
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

    private boolean verificaOcupado(ItemDinamico d) {
        return true;
    }

    private void adicionarPessoa(String path_imagem) {
        ItemMapa umItem = mapa.getCalcada().get((int) Math.floor(Math.random() * mapa.getCalcada().size()));
        Localizacao localizacao = umItem.getLocalizacaoAtual();
        System.out.print(" ~{" + localizacao + "}~ ");
        int tipo = mapa.getTipoCalcada();
        ItemDinamico p = new Pessoa(localizacao, tipo, path_imagem);

        Localizacao destino = mapa.getCalcada().get((int) Math.floor(rand.nextDouble() * mapa.getCalcada().size()))
                .getLocalizacaoAtual();
        System.out.print(" ^{" + destino + "}^ ");
        p.setCaminho(calcadaGraph.menosCaminhoL(p.getLocalizacaoAtual(), destino));
        System.out.print("\n |>");
        for (Localizacao a : p.getCaminho()) {
            System.out.print(" ->- " + a + " ");
        }
        todasEntidades.add(p);
        populacao.add(p);
        System.out.print(" >|\n");
    }

    private void adicionarVeiculo(String path_imagem) {
        ItemMapa umItem = mapa.getRuas().get((int) Math.floor(Math.random() * mapa.getRuas().size()));
        Localizacao localizacao = umItem.getLocalizacaoAtual();
        System.out.print(" ~{" + localizacao + "}~ ");
        int tipo = mapa.getTipoRua();
        ItemDinamico v = new Veiculo(localizacao, tipo, path_imagem);

        Localizacao destino = mapa.getRuas().get((int) Math.floor(rand.nextDouble() * mapa.getRuas().size()))
                .getLocalizacaoAtual();
        System.out.print(" ^{" + destino + "}^ ");
        v.setCaminho(ruaGraph.menosCaminhoL(v.getLocalizacaoAtual(), destino));
        System.out.print("\n |>");
        for (Localizacao a : v.getCaminho()) {
            System.out.print(" ->- " + a + " ");
        }
        todasEntidades.add(v);
        frota.add(v);
        System.out.print(" >|\n");
    }

    public List<ItemDinamico> getTodasEntidades() {
        return todasEntidades;
    }

    public void executar() {
        for (ItemDinamico d : this.getTodasEntidades()) {
            int index = todasEntidades.indexOf(d);
            if (d.getComCaminho()) {
                d.mover();
            } else {
                System.out.println(d.getClass().getName());
                if (d.getClass().getName() == "simulacao.Pessoa") {
                    Localizacao destino = mapa.getCalcada()
                            .get((int) Math.floor(rand.nextDouble() * mapa.getCalcada().size()))
                            .getLocalizacaoAtual();
                    System.out.print(" ^{" + destino + "}^ ");

                    d.setCaminho(calcadaGraph.menosCaminhoL(d.getLocalizacaoAtual(), destino));
                } else if (d.getClass().getName() == "simulacao.Veiculo") {
                    Localizacao destino = mapa.getRuas()
                            .get((int) Math.floor(rand.nextDouble() * mapa.getRuas().size()))
                            .getLocalizacaoAtual();
                    System.out.print(" ^{" + destino + "}^ ");

                    d.setCaminho(ruaGraph.menosCaminhoL(d.getLocalizacaoAtual(), destino));
                }
            }

            this.todasEntidades.set(index, d);
        }
    }

}
