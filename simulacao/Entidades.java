package simulacao;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Representa todas as entidades dinâmicas da simulacao.
 * 
 * @author TP3 - ARTHUR HAUCK DITTZ, MARCO ANTONIO MAGALHAES
 * 
 */
public class Entidades {
    private Mapa mapa;
    private List<ItemDinamico> todasEntidades;
    private List<Pessoa> populacao;
    private List<Veiculo> frota;

    private int largura;
    private int altura;
    private static Random rand = new Random();
    private Graph ruaGraph;
    private Graph calcadaGraph;

    private List<Chamado> chamados;
    private boolean chamadoAberto;

    /**
     * Instanciando Entidades
     * 
     * @param mapa : mapa da simulação
     * 
     */
    public Entidades(Mapa mapa) {
        this.altura = mapa.getAltura();
        this.largura = mapa.getLargura();

        this.mapa = mapa;

        this.todasEntidades = new ArrayList<ItemDinamico>();
        this.populacao = new ArrayList<Pessoa>();
        this.frota = new ArrayList<Veiculo>();

        this.chamados = new ArrayList<Chamado>();
        chamadoAberto = false;
        System.out.println("Gerando grafos ...");
        this.ruaGraph = new Graph(altura, largura, mapa.getTipoRua(),
                mapa.getTipoFaixaPedestre());
        this.calcadaGraph = new Graph(altura, largura, mapa.getTipoCalcada(),
                mapa.getTipoFaixaPedestre());
        this.ruaGraph.criarGrafo(mapa.getItens(), altura, largura);
        this.calcadaGraph.criarGrafo(mapa.getItens(), altura, largura);
    }

    /**
     * Cria pessoas e motos
     * 
     * @param numPessoas : número de pessoas criadas
     * @param numMotos   : número de motos criadas
     * 
     */
    public void criarPopulacao(int numPessoas, int numMotos) {
        System.out.println("Gerando entidades ...");
        for (int i = 0; i < numPessoas; i++) {
            if (i % 3 == 0) {
                this.adicionarPessoa("Imagens/pessoaNS_1.png", "Imagens/pessoaLO_1.png", "Imagens/pessoaChamado_1.png");
            } else if (i % 3 == 1) {
                this.adicionarPessoa("Imagens/pessoaNS_2.png", "Imagens/pessoaLO_2.png", "Imagens/pessoaChamado_2.png");
            } else {
                this.adicionarPessoa("Imagens/pessoaNS_3.png", "Imagens/pessoaLO_3.png", "Imagens/pessoaChamado_3.png");
            }

        }
        for (int i = 0; i < numMotos; i++) {

            this.adicionarVeiculo("Imagens/motoN.png", "Imagens/motoS.png", "Imagens/motoL.png", "Imagens/motoO.png",
                    "Imagens/motoN_passageiro.png", "Imagens/motoS_passageiro.png", "Imagens/motoL_passageiro.png",
                    "Imagens/motoO_passageiro.png");

        }

    }

    /**
     * Adiciona pessoas
     * 
     */
    private void adicionarPessoa(String path_imagemNS, String path_imagemLO, String path_imagemChamado) {

        ItemMapa umItem = mapa.getCalcada().get((int) Math.floor(Math.random() *
                mapa.getCalcada().size()));

        Localizacao localizacao = umItem.getLocalizacaoAtual();

        int tipo = mapa.getTipoCalcada();
        Pessoa p = new Pessoa(localizacao, tipo, path_imagemNS, path_imagemLO, path_imagemChamado);

        Localizacao destino = mapa.getCalcada().get((int) Math.floor(rand.nextDouble() * mapa.getCalcada().size()))
                .getLocalizacaoAtual();

        p.setCaminho(calcadaGraph.menosCaminhoL(p.getLocalizacaoAtual(), destino));

        todasEntidades.add(p);
        populacao.add(p);

    }

    /**
     * Adiciona veiculos
     * 
     */
    private void adicionarVeiculo(String path_imagemN, String path_imagemS, String path_imagemL,
            String path_imagemO, String path_imagemN_carregando, String path_imagemS_carregando,
            String path_imagemL_carregando,
            String path_imagemO_carregando) {
        ItemMapa umItem = mapa.getRuas().get((int) Math.floor(Math.random() * mapa.getRuas().size()));
        Localizacao localizacao = umItem.getLocalizacaoAtual();

        int tipo = mapa.getTipoRua();
        Veiculo v = new Veiculo(localizacao, tipo, path_imagemN, path_imagemS, path_imagemL, path_imagemO,
                path_imagemN_carregando, path_imagemS_carregando, path_imagemL_carregando, path_imagemO_carregando);

        Localizacao destino = mapa.getRuas().get((int) Math.floor(rand.nextDouble() * mapa.getRuas().size()))
                .getLocalizacaoAtual();

        v.setCaminho(ruaGraph.menosCaminhoL(v.getLocalizacaoAtual(), destino));

        todasEntidades.add(v);
        frota.add(v);

    }

    public List<ItemDinamico> getTodasEntidades() {
        return todasEntidades;
    }

    public List<Veiculo> getFrota() {
        return frota;
    }

    public List<Pessoa> getPopulacao() {
        return populacao;
    }

    /**
     * Executa ação das entidades. Primeiramente de todas as pessoas (primeirametne
     * as com chamados ativos e depois das demais). Após isso executa a ação das
     * motos (primeiramente das com chamados ativos e depois das demais). As
     * operações se resume em gerar caminhos e executar os passos de movimentação e
     * de criação e execução de chamados. E por fim
     * executa as operações dos semaforos.
     * 
     */
    public void executar() {

        for (Pessoa p : this.getPopulacao()) {
            int indexE = todasEntidades.indexOf(p);
            int indexP = populacao.indexOf(p);
            if (!p.getComCaminho() && p.getChamadoAtivo() != true) {

                int choice = rand.nextInt(2);
                Localizacao destino = mapa.getCalcada()
                        .get((int) Math.floor(rand.nextDouble() * mapa.getCalcada().size()))
                        .getLocalizacaoAtual();
                if (choice == 0 && mapa.getItem(p.getLocalizacaoAtual().getX(), p.getLocalizacaoAtual().getY())
                        .getTipo() != mapa.getTipoFaixaPedestre() && mapa.getItem(destino.getX(), destino.getY())
                                .getTipo() != mapa.getTipoFaixaPedestre()) {
                    this.chamados.add(new Chamado(indexP, indexE, p.getLocalizacaoAtual(), destino));
                    chamadoAberto = true;
                    p.setChamadoAtivo(true);

                } else {
                    p.setCaminho(calcadaGraph.menosCaminhoL(p.getLocalizacaoAtual(), destino));
                }

            }
            boolean impedimento = false;
            if (p.getCaminho().size() > 0) {
                Localizacao proxLoc = p.getCaminho().get(0);

                for (ItemDinamico mot : frota) {
                    if (mot.getLocalizacaoAtual().getX() == proxLoc.getX()
                            && mot.getLocalizacaoAtual().getY() == proxLoc.getY()) {
                        impedimento = true;
                    }
                }
                for (Semaforo sema : mapa.getSemaforo()) {
                    if (sema.getLocalizacaoAtual().getX() == proxLoc.getX()
                            && sema.getLocalizacaoAtual().getY() == proxLoc.getY()) {
                        if (sema.getEstado() == true)
                            impedimento = true;
                    }
                }

            }
            if (impedimento == false) {
                p.mover();
                this.todasEntidades.set(indexE, p);
                this.populacao.set(indexP, p);
            }

        }

        for (Veiculo v : this.getFrota()) {

            int indexE = todasEntidades.indexOf(v);
            int indexF = frota.indexOf(v);
            if (v.getComChamado() && !v.getIndoChamado() && !v.getCarregando()) {

                v.setCaminho(ruaGraph.menosCaminhoL(v.getLocalizacaoAtual(), mapa.ruaProx(v.getChamado().getInicio())));
                v.setIndoChamado(true);

            } else if (v.getComChamado() && v.getIndoChamado()) {
                if (v.getLocalizacaoAtual().equals(mapa.ruaProx(v.getChamado().getInicio()))) {

                    v.setCarregando(true);
                    Pessoa pp = populacao.get(v.getChamado().getIndexPessoaPop());
                    pp.setCarregado(true);
                    this.todasEntidades.set(v.getChamado().getIndexPessoaTot(), pp);
                    this.populacao.set(v.getChamado().getIndexPessoaPop(), pp);
                    v.setCaminho(
                            ruaGraph.menosCaminhoL(v.getLocalizacaoAtual(), mapa.ruaProx(v.getChamado().getDestino())));
                    v.setIndoChamado(false);

                }

            } else if (v.getComChamado() && !v.getIndoChamado() && v.getCarregando()) {
                if (v.getLocalizacaoAtual().equals(mapa.ruaProx(v.getChamado().getDestino()))) {

                    v.setCarregando(false);
                    v.setComChamado(false);

                    Pessoa pp = populacao.get(v.getChamado().getIndexPessoaPop());
                    pp.setLocalizacaoAtual(v.getChamado().getDestino());
                    pp.setCarregado(false);
                    pp.setChamadoAtivo(false);
                    this.todasEntidades.set(v.getChamado().getIndexPessoaTot(), pp);
                    this.populacao.set(v.getChamado().getIndexPessoaPop(), pp);
                    v.setChamado(null);
                }

            } else if (!v.getComCaminho()) {

                if (chamadoAberto && mapa.getItem(v.getLocalizacaoAtual().getX(), v.getLocalizacaoAtual().getY())
                        .getTipo() != mapa.getTipoFaixaPedestre()) {
                    v.setChamado(chamados.get(0));
                    v.setComChamado(true);
                    chamados.remove(0);
                    if (chamados.size() == 0)
                        chamadoAberto = false;

                } else {
                    Localizacao destino = mapa.getRuas()
                            .get((int) Math.floor(rand.nextDouble() * mapa.getRuas().size()))
                            .getLocalizacaoAtual();

                    v.setCaminho(ruaGraph.menosCaminhoL(v.getLocalizacaoAtual(), destino));
                }

            }
            boolean impedimento = false;
            if (v.getCaminho().size() > 0) {
                Localizacao proxLoc = v.getCaminho().get(0);

                for (ItemDinamico pess : populacao) {
                    if (pess.getLocalizacaoAtual().getX() == proxLoc.getX()
                            && pess.getLocalizacaoAtual().getY() == proxLoc.getY()) {

                        impedimento = true;
                    }
                }
                for (Semaforo sema : mapa.getSemaforo()) {
                    if (sema.getLocalizacaoAtual().getX() == proxLoc.getX()
                            && sema.getLocalizacaoAtual().getY() == proxLoc.getY()) {
                        if (sema.getEstado() == false)
                            impedimento = true;

                    }
                }
                for (ItemMapa cruz : mapa.getCruzamento()) {
                    if (cruz.getLocalizacaoAtual().getX() == v.getLocalizacaoAtual().getX()
                            && cruz.getLocalizacaoAtual().getY() == v.getLocalizacaoAtual().getY())
                        impedimento = false;
                }
            }
            if (impedimento == false) {
                v.mover();
                this.todasEntidades.set(indexE, v);
                this.frota.set(indexF, v);
            }

        }
        for (Semaforo sem : mapa.getSemaforo()) {
            sem.run();
        }

    }

}
