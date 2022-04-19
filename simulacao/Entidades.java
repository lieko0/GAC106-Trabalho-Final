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

        this.ruaGraph = new Graph(altura, largura, mapa.getTipoRua(),
                mapa.getTipoFaixaPedestre());
        this.calcadaGraph = new Graph(altura, largura, mapa.getTipoCalcada(),
                mapa.getTipoFaixaPedestre());
        this.ruaGraph.criarGrafo(mapa.getItens(), altura, largura);
        this.calcadaGraph.criarGrafo(mapa.getItens(), altura, largura);
    }

    public void criarPopulacao() {
        for (int i = 0; i < 10; i++) {
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
        ItemMapa umItem = mapa.getCalcada().get((int) Math.floor(Math.random() * mapa.getCalcada().size()));
        Localizacao localizacao = umItem.getLocalizacaoAtual();
        // // System.out.print(" ~{" + localizacao + "}~ ");
        int tipo = mapa.getTipoCalcada();
        ItemDinamico p = new Pessoa(localizacao, tipo, path_imagem);

        Localizacao destino = mapa.getCalcada().get((int) Math.floor(rand.nextDouble() * mapa.getCalcada().size()))
                .getLocalizacaoAtual();
        // // System.out.print(" ^{" + destino + "}^ ");
        p.setCaminho(calcadaGraph.menosCaminhoL(p.getLocalizacaoAtual(), destino));
        // // System.out.print("\n |>");
        for (Localizacao a : p.getCaminho()) {
            // // System.out.print(" ->- " + a + " ");
        }
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
        for (Localizacao a : v.getCaminho()) {
            // // System.out.print(" ->- " + a + " ");
        }
        todasEntidades.add(v);
        frota.add(v);
        // // System.out.print(" >|\n");
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
                // // System.out.println(d.getClass().getName());
                if (d.getClass().getName() == "simulacao.Pessoa") {
                    int choice = rand.nextInt(2);
                    Localizacao destino = mapa.getCalcada()
                            .get((int) Math.floor(rand.nextDouble() * mapa.getCalcada().size()))
                            .getLocalizacaoAtual();
                    if (choice == 0) {
                        // // System.out.print(" ^{" + destino + "}^ ");

                        d.setCaminho(calcadaGraph.menosCaminhoL(d.getLocalizacaoAtual(), destino));
                    } else {
                        // chamadoAberto = true;
                        // chamados.add(new Chamado(d, destino));
                    }

                } else if (d.getClass().getName() == "simulacao.Veiculo") {
                    if (chamadoAberto) {
                        // Veiculo veic = new Veiculo(d);
                        // Chamado ch = chamados.get(0);
                        // chamados.remove(0);
                        // if (chamados.size() == 0)
                        // chamadoAberto = false;
                        // veic.setCaminho(
                        // ruaGraph.menosCaminhoL(d.getLocalizacaoAtual(),
                        // ch.getCliente().getLocalizacaoAtual()));

                    } else {
                        Localizacao destino = mapa.getRuas()
                                .get((int) Math.floor(rand.nextDouble() * mapa.getRuas().size()))
                                .getLocalizacaoAtual();
                        // // System.out.print(" ^{" + destino + "}^ ");

                        d.setCaminho(ruaGraph.menosCaminhoL(d.getLocalizacaoAtual(), destino));
                    }

                }
            }

            this.todasEntidades.set(index, d);
        }
    }

}
