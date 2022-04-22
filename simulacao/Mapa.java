package simulacao;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Representa um mapa com os itens que compõe o cenário
 * 
 * @author TP3 - ARTHUR HAUCK DITTZ, MARCO ANTONIO MAGALHAES
 * @author David J. Barnes and Michael Kolling and Luiz Merschmann
 */
public class Mapa {
    private ItemMapa[][] itens;
    private int largura;
    private int altura;
    private static Random rand = new Random();

    private static final int LARGURA_PADRAO = 20;
    private static final int ALTURA_PADRAO = 20;
    private List<ItemMapa> ruas;
    private List<ItemMapa> calcada;
    private List<ItemMapa> faixa;
    private List<ItemMapa> cruzamento;
    private List<Semaforo> semaforos;

    private int tipoRua = 0;
    private int tipoCalcada = 1;
    private int tipoFaixaPedestre = 2;

    /**
     * Cria mapa para alocar itens da simulacao.
     * 
     * @param largura : largura da área de simulacao.
     * @param altura  : altura da área de simulação.
     */
    public Mapa(int largura, int altura) {
        this.largura = largura;
        this.altura = altura;
        this.itens = new ItemMapa[largura][altura];
        this.ruas = new ArrayList<ItemMapa>();
        this.calcada = new ArrayList<ItemMapa>();
        this.faixa = new ArrayList<ItemMapa>();
        this.cruzamento = new ArrayList<ItemMapa>();
        this.semaforos = new ArrayList<Semaforo>();
    }

    /**
     * Cria mapa com tamanho padrao.
     */
    public Mapa() {
        this(LARGURA_PADRAO, ALTURA_PADRAO);
    }

    public ItemMapa[][] getItens() {
        return itens;
    }

    public List<ItemMapa> getRuas() {
        return ruas;
    }

    public List<ItemMapa> getCalcada() {
        return calcada;
    }

    public List<ItemMapa> getFaixa() {
        return faixa;
    }

    public List<ItemMapa> getCruzamento() {
        return cruzamento;
    }

    public int getTipoCalcada() {
        return tipoCalcada;
    }

    public int getTipoFaixaPedestre() {
        return tipoFaixaPedestre;
    }

    public int getTipoRua() {
        return tipoRua;
    }

    public List<Semaforo> getSemaforo() {
        return semaforos;
    }

    /**
     * Gerador de mapa.
     */
    public void gerarMapa() {
        System.out.println("Começando a geração de mapa ...");

        System.out.println("Gerando ruas ...");
        int quantidadeRuas = (int) (altura * largura * 0.3);
        int espacamento = 10;
        int borda = 1;
        String imagemRua = "Imagens/rua.jpg";

        gerarRuas(quantidadeRuas, espacamento, borda, imagemRua, tipoRua);

        System.out.println("Gerando calcadas ...");
        String imagemCalcada = "Imagens/calcada.jpg";

        gerarCalcada(imagemCalcada, tipoCalcada);

        System.out.println("Gerando faixas de pedestre ...");
        String imagemFaixaPedestre = "Imagens/faixa.jpg";
        String imagemFaixaPedestreFlip = "Imagens/faixa_alt.jpg";

        gerarFaixaPedestre(tipoRua, tipoCalcada, imagemFaixaPedestre,
                imagemFaixaPedestreFlip, tipoFaixaPedestre);

        System.out.println("Gerando semaforos ...");
        int tempo = 5;
        String imagemSemaforoGreen = "Imagens/semaforoG.png";
        String imagemSemaforoRed = "Imagens/semaforoR.png";

        this.gerarSemaforo(imagemSemaforoGreen, imagemSemaforoRed, tempo);

        System.out.println("Preenchendo espaço vazio do mapa ...");
        int tipoPreenchimento = 3;
        String imagemPreenchimento = "Imagens/preenchimento.png";

        preencheEspaco(imagemPreenchimento, tipoPreenchimento);

    }

    /**
     * Gerador de ruas.
     * 
     * @param max           : número máximo de ruas
     * @param espacamento   : Tendência de tamanho das ruas (>=1) quando maior,
     *                      menos
     *                      esquinas o mapa terá
     * @param borda         : Espaço minimo entre qualquer bloco de rua e a borda (
     *                      >=1)
     * @param imagemCaminho : imagem do bloco de rua
     * @param tipo          : ID do bloco de rua
     */
    private void gerarRuas(int max, int espacamento, int borda, String imagemCaminho, int tipo) {
        ItemMapa umItem;
        int dir;
        // // System.out.println(max + " - tudo: " + altura + "," + largura + " = " +
        // largura * altura);
        int alt = 0;
        int lar = 0;
        int cont1 = 1, /* cont, */ loop, loop1 = 0;
        alt = rand.nextInt(altura - 2 * borda) + borda;
        lar = rand.nextInt(largura - 2 * borda) + borda;
        umItem = new ItemMapa(new Localizacao(lar, alt), 0,
                imagemCaminho);
        adicionarItem(umItem);
        ruas.add(umItem);
        // // System.out.println("1: " + lar + "," + alt + " ---- " + cont1);

        while (cont1 < max && loop1 < largura * altura * 10) {
            // cont = 0;
            umItem = ruas.get((int) Math.floor(rand.nextDouble() * ruas.size()));
            lar = umItem.getLocalizacaoAtual().getX();
            alt = umItem.getLocalizacaoAtual().getY();
            loop1++;
            loop = 0;

            while (cont1 < max && loop < largura * altura) {
                dir = rand.nextInt(4);
                if (dir == 0) {
                    for (int u = 0; u < espacamento && cont1 < max; u++) {
                        if (alt > 0 + borda) {
                            alt -= 1;
                            if (getItem(lar, alt) == null && verifica2Prox(0, lar, alt, -1, 0)) {
                                umItem = new ItemMapa(new Localizacao(lar, alt), tipo, imagemCaminho);
                                adicionarItem(umItem);
                                ruas.add(umItem);
                                // cont++;
                                cont1++;
                                // // System.out.println(cont1 + ": " + cont + ": " + lar + "," + alt);
                            } else {
                                alt += 1;
                            }
                        }
                    }
                } else if (dir == 1) {
                    for (int u = 0; u < espacamento && cont1 < max; u++) {
                        if (lar > 0 + borda) {
                            lar -= 1;
                            if (getItem(lar, alt) == null && verifica2Prox(0, lar, alt, 0, -1)) {
                                umItem = new ItemMapa(new Localizacao(lar, alt), tipo, imagemCaminho);
                                adicionarItem(umItem);
                                ruas.add(umItem);
                                // cont++;
                                cont1++;
                                // // System.out.println(cont1 + ": " + cont + ": " + alt + "," + lar);
                            } else {
                                lar += 1;
                            }
                        }
                    }
                } else if (dir == 2) {
                    for (int u = 0; u < espacamento && cont1 < max; u++) {
                        if (alt < altura - 1 - borda) {
                            alt += 1;
                            if (getItem(lar, alt) == null && verifica2Prox(0, lar, alt, +1, 0)) {
                                umItem = new ItemMapa(new Localizacao(lar, alt), tipo, imagemCaminho);
                                adicionarItem(umItem);
                                ruas.add(umItem);
                                // cont++;
                                cont1++;
                                // // System.out.println(cont1 + ": " + cont + ": " + alt + "," + lar);
                            } else {
                                alt -= 1;
                            }
                        }
                    }
                } else {
                    for (int u = 0; u < espacamento && cont1 < max; u++) {
                        if (lar < largura - 1 - borda) {
                            lar += 1;
                            if (getItem(lar, alt) == null && verifica2Prox(0, lar, alt, 0, -1)) {
                                umItem = new ItemMapa(new Localizacao(lar, alt), tipo, imagemCaminho);
                                adicionarItem(umItem);
                                ruas.add(umItem);
                                // cont++;
                                cont1++;
                                // // System.out.println(cont1 + ": " + cont + ": " + alt + "," + lar);
                            } else {
                                lar -= 1;
                            }
                        }
                    }
                }
                loop++;
            }
        }
    }

    /**
     * Gerador de calcadas.
     * 
     * @param imagemCaminho : imagem do bloco de calcada
     * @param tipo          : ID do bloco de calcada
     */
    private void gerarCalcada(String imagemCaminho, int tipo) {
        ItemMapa umItem;
        int x, y;

        for (ItemMapa rua : ruas) {
            x = rua.getLocalizacaoAtual().getX();
            y = rua.getLocalizacaoAtual().getY();

            if (y < altura - 1) {
                if (getItem(x, y + 1) == null) {
                    umItem = new ItemMapa(new Localizacao(x, y + 1), tipo, imagemCaminho);
                    adicionarItem(umItem);
                    calcada.add(umItem);
                }
                if (x < largura - 1) {
                    if (getItem(x + 1, y + 1) == null) {
                        umItem = new ItemMapa(new Localizacao(x + 1, y + 1), tipo, imagemCaminho);
                        adicionarItem(umItem);
                        calcada.add(umItem);
                    }
                }
                if (x > 0) {
                    if (getItem(x - 1, y + 1) == null) {
                        umItem = new ItemMapa(new Localizacao(x - 1, y + 1), tipo, imagemCaminho);
                        adicionarItem(umItem);
                        calcada.add(umItem);
                    }
                }
            }
            if (y > 0) {
                if (getItem(x, y - 1) == null) {
                    umItem = new ItemMapa(new Localizacao(x, y - 1), tipo, imagemCaminho);
                    adicionarItem(umItem);
                    calcada.add(umItem);
                }
                if (x < largura - 1) {
                    if (getItem(x + 1, y - 1) == null) {
                        umItem = new ItemMapa(new Localizacao(x + 1, y - 1), tipo, imagemCaminho);
                        adicionarItem(umItem);
                        calcada.add(umItem);
                    }
                }
                if (x > 0) {
                    if (getItem(x - 1, y - 1) == null) {
                        umItem = new ItemMapa(new Localizacao(x - 1, y - 1), tipo, imagemCaminho);
                        adicionarItem(umItem);
                        calcada.add(umItem);
                    }
                }
            }
            if (x < largura - 1) {
                if (getItem(x + 1, y) == null) {
                    umItem = new ItemMapa(new Localizacao(x + 1, y), tipo, imagemCaminho);
                    adicionarItem(umItem);
                    calcada.add(umItem);
                }
            }
            if (x > 0) {
                if (getItem(x - 1, y) == null) {
                    umItem = new ItemMapa(new Localizacao(x - 1, y), tipo, imagemCaminho);
                    adicionarItem(umItem);
                    calcada.add(umItem);
                }
            }
        }
    }

    /**
     * Gerador de faixas de pedestre.
     * 
     * @param tipoRua          : ID do bloco de rua
     * @param tipoCalcada      : ID do bloco de calcada
     * @param imagemCaminho    : imagem do bloco de faixas de pedestre
     * @param imagemCaminhoAlt : imagem do bloco de faixas de pedestre em outra
     *                         direção
     * @param tipo             : ID do bloco de faixas de pedestre
     */
    private void gerarFaixaPedestre(int tipoRua, int tipoCalcada, String imagemCaminho, String imagemCaminhoAlt,
            int tipo) {
        int x, y;
        List<ItemMapa> umCruzamento;
        for (ItemMapa rua : ruas) {
            x = rua.getLocalizacaoAtual().getX();
            y = rua.getLocalizacaoAtual().getY();
            umCruzamento = new ArrayList<ItemMapa>();

            if (y < altura - 1) {
                if (getItem(x, y + 1) != null && getItem(x, y + 1).getTipo() == tipoRua) {
                    umCruzamento.add(getItem(x, y + 1));
                }
            }
            if (y > 0) {
                if (getItem(x, y - 1) != null && getItem(x, y - 1).getTipo() == tipoRua) {
                    umCruzamento.add(getItem(x, y - 1));
                }
            }
            if (x < largura - 1) {
                if (getItem(x + 1, y) != null && getItem(x + 1, y).getTipo() == tipoRua) {
                    umCruzamento.add(getItem(x + 1, y));
                }
            }
            if (x > 0) {
                if (getItem(x - 1, y) != null && getItem(x - 1, y).getTipo() == tipoRua) {
                    umCruzamento.add(getItem(x - 1, y));
                }
            }

            if (umCruzamento.size() > 2) {

                int cruzamentoValido = 0;
                for (ItemMapa umItem : umCruzamento) {

                    x = umItem.getLocalizacaoAtual().getX();
                    y = umItem.getLocalizacaoAtual().getY();

                    if (verificaVizinho(tipo, x, y) == 0 && verificaVizinho(tipoRua, x, y) < 3) {
                        if (y != altura - 1 && y != 0 && x != largura - 1 && x != 0) {
                            if (getItem(x + 1, y) != null && getItem(x + 1, y).getTipo() == tipoCalcada
                                    && getItem(x - 1, y) != null && getItem(x - 1, y).getTipo() == tipoCalcada) {
                                removerItem(umItem);
                                umItem = new ItemMapa(new Localizacao(x, y), tipo, imagemCaminhoAlt);
                                adicionarItem(umItem);
                                faixa.add(umItem);
                                calcada.add(umItem);
                                cruzamentoValido++;
                            }
                            if (getItem(x, y + 1) != null && getItem(x, y + 1).getTipo() == tipoCalcada
                                    && getItem(x, y - 1) != null && getItem(x, y - 1).getTipo() == tipoCalcada) {
                                removerItem(umItem);
                                umItem = new ItemMapa(new Localizacao(x, y), tipo, imagemCaminho);
                                adicionarItem(umItem);
                                faixa.add(umItem);
                                calcada.add(umItem);
                                cruzamentoValido++;
                            }
                        }

                    }

                }
                if (cruzamentoValido > 1)
                    this.cruzamento.add(this.getItem(x + 1, y));
            }
        }

        if (faixa.isEmpty() == true) { // exceção
            boolean peloMenosUm = false;
            ItemMapa umItem;
            int loop = 0;
            while (!peloMenosUm && loop < altura * largura) {
                umItem = ruas.get((int) Math.floor(rand.nextDouble() * ruas.size()));
                x = umItem.getLocalizacaoAtual().getX();
                y = umItem.getLocalizacaoAtual().getY();
                if (y != altura - 1 && y != 0 && x != largura - 1 && x != 0) {
                    if (getItem(x + 1, y) != null && getItem(x + 1, y).getTipo() == tipoCalcada
                            && getItem(x - 1, y) != null
                            && getItem(x - 1, y).getTipo() == tipoCalcada) {

                        removerItem(umItem);
                        ruas.remove(umItem);
                        umItem = new ItemMapa(new Localizacao(x, y), tipo, imagemCaminhoAlt);
                        adicionarItem(umItem);
                        ruas.add(umItem);
                        faixa.add(umItem);
                        calcada.add(umItem);
                        peloMenosUm = true;
                    }
                    if (getItem(x, y + 1) != null && getItem(x, y + 1).getTipo() == tipoCalcada
                            && getItem(x, y - 1) != null && getItem(x, y - 1).getTipo() == tipoCalcada) {
                        removerItem(umItem);
                        ruas.remove(umItem);
                        umItem = new ItemMapa(new Localizacao(x, y), tipo, imagemCaminho);
                        ruas.add(umItem);
                        adicionarItem(umItem);
                        faixa.add(umItem);
                        calcada.add(umItem);
                        peloMenosUm = true;
                    }
                }
                loop++;
            }

        }
    }

    /**
     * Gerador de semaforos.
     * 
     * @param path_imagemG : Imagem do semaforo verde
     * @param path_imagemR : Imagem do semaforo vermelho
     * @param tempo        : tempo (em execuções) entre as mudanças de estado do
     *                     semaforo
     */
    private void gerarSemaforo(String path_imagemG, String path_imagemR, int tempo) {
        ItemMapa cru;
        boolean repetido;
        int xCruzam, yCruzam;
        for (int i = 0; i < this.getCruzamento().size(); i++) {
            cru = this.getCruzamento().get(i);
            xCruzam = cru.getLocalizacaoAtual().getX();
            yCruzam = cru.getLocalizacaoAtual().getY();

            if ((xCruzam + 1) < largura
                    && this.getItem(xCruzam + 1, yCruzam)
                            .getTipo() == this.getTipoFaixaPedestre()) {
                if ((yCruzam - 1 > 0)
                        && this.getItem(xCruzam, yCruzam - 1)
                                .getTipo() == this.getTipoFaixaPedestre()) {

                    repetido = false;
                    for (ItemMapa sem : semaforos) {
                        if (sem.getLocalizacaoAtual().getX() == (xCruzam + 1)
                                && sem.getLocalizacaoAtual().getY() == (yCruzam)) {
                            repetido = true;
                        }
                        if (sem.getLocalizacaoAtual().getX() == (xCruzam)
                                && sem.getLocalizacaoAtual().getY() == (yCruzam - 1)) {
                            repetido = true;
                        }
                    }
                    if (repetido == false) {
                        semaforos.add(
                                new Semaforo(
                                        new Localizacao(xCruzam + 1,
                                                yCruzam),
                                        cru.getTipo(), path_imagemG, path_imagemR, tempo,
                                        true));
                        semaforos.add(
                                new Semaforo(
                                        new Localizacao(xCruzam,
                                                yCruzam - 1),
                                        cru.getTipo(), path_imagemG, path_imagemR, tempo,
                                        false));
                    }

                } else if ((yCruzam + 1 < altura)
                        && this.getItem(xCruzam, yCruzam + 1)
                                .getTipo() == this.getTipoFaixaPedestre()) {

                    repetido = false;
                    for (ItemMapa sem : semaforos) {
                        if (sem.getLocalizacaoAtual().getX() == (xCruzam + 1)
                                && sem.getLocalizacaoAtual().getY() == (yCruzam)) {
                            repetido = true;
                        }
                        if (sem.getLocalizacaoAtual().getX() == (xCruzam)
                                && sem.getLocalizacaoAtual().getY() == (yCruzam + 1)) {
                            repetido = true;
                        }
                    }
                    if (repetido == false) {
                        semaforos.add(
                                new Semaforo(
                                        new Localizacao(xCruzam + 1,
                                                yCruzam),
                                        cru.getTipo(), path_imagemG, path_imagemR, tempo,
                                        true));
                        semaforos.add(
                                new Semaforo(
                                        new Localizacao(xCruzam,
                                                yCruzam + 1),
                                        cru.getTipo(), path_imagemG, path_imagemR, tempo,
                                        false));
                    }
                }

            } else if ((xCruzam - 1 > 0)
                    && this.getItem(xCruzam - 1, yCruzam)
                            .getTipo() == this.getTipoFaixaPedestre()) {
                if ((yCruzam - 1 > 0)
                        && this.getItem(xCruzam, yCruzam - 1)
                                .getTipo() == this.getTipoFaixaPedestre()) {
                    repetido = false;
                    for (ItemMapa sem : semaforos) {
                        if (sem.getLocalizacaoAtual().getX() == (xCruzam - 1)
                                && sem.getLocalizacaoAtual().getY() == (yCruzam)) {
                            repetido = true;
                        }
                        if (sem.getLocalizacaoAtual().getX() == (xCruzam)
                                && sem.getLocalizacaoAtual().getY() == (yCruzam - 1)) {
                            repetido = true;
                        }
                    }
                    if (repetido == false) {
                        semaforos.add(
                                new Semaforo(
                                        new Localizacao(xCruzam - 1,
                                                yCruzam),
                                        cru.getTipo(), path_imagemG, path_imagemR, tempo,
                                        true));
                        semaforos.add(
                                new Semaforo(
                                        new Localizacao(xCruzam,
                                                yCruzam - 1),
                                        cru.getTipo(), path_imagemG, path_imagemR, tempo,
                                        false));
                    }

                } else if ((yCruzam + 1 > altura)
                        && this.getItem(xCruzam, yCruzam + 1)
                                .getTipo() == this.getTipoFaixaPedestre()) {

                    repetido = false;
                    for (ItemMapa sem : semaforos) {
                        if (sem.getLocalizacaoAtual().getX() == (xCruzam - 1)
                                && sem.getLocalizacaoAtual().getY() == (yCruzam)) {
                            repetido = true;
                        }
                        if (sem.getLocalizacaoAtual().getX() == (xCruzam)
                                && sem.getLocalizacaoAtual().getY() == (yCruzam + 1)) {
                            repetido = true;
                        }
                    }
                    if (repetido == false) {
                        semaforos.add(
                                new Semaforo(
                                        new Localizacao(xCruzam - 1,
                                                yCruzam),
                                        cru.getTipo(), path_imagemG, path_imagemR, tempo,
                                        true));
                        semaforos.add(
                                new Semaforo(
                                        new Localizacao(xCruzam,
                                                yCruzam + 1),
                                        cru.getTipo(), path_imagemG, path_imagemR, tempo,
                                        false));
                    }
                }
            }
        }
    }

    /**
     * Peencher espaços que sobraram de calcadas.
     * 
     * @param imagemCaminho : imagem do bloco de preenchimento
     * @param tipo          : ID do bloco de preenchimento
     */
    private void preencheEspaco(String imagemCaminho, int tipo) {
        ItemMapa umItem;
        for (int i = 0; i < largura; i++) {
            for (int j = 0; j < altura; j++) {
                if (getItem(i, j) == null) {
                    umItem = new ItemMapa(new Localizacao(i, j), tipo, imagemCaminho);
                    adicionarItem(umItem);
                }
            }
        }
    }

    /**
     * Verificas quantos vizinhos do mesmo tipo do bloco tem ao redor.
     * 
     * @param tipo : ID do bloco a ser procurado
     * @param x    : coordenada x do bloco
     * @param y    : coordenada y do bloco
     * @return int número de vizinho de mesmo tipo
     */
    private int verificaVizinho(int tipo, int x, int y) {
        int retorno = 0;
        if (y < altura - 1) {
            if (getItem(x, y + 1) != null && getItem(x, y + 1).getTipo() == tipo) {
                retorno++;
            }
        }
        if (y > 0) {
            if (getItem(x, y - 1) != null && getItem(x, y - 1).getTipo() == tipo) {
                retorno++;
            }
        }
        if (x < largura - 1) {
            if (getItem(x + 1, y) != null && getItem(x + 1, y).getTipo() == tipo) {
                retorno++;
            }
        }
        if (x > 0) {
            if (getItem(x - 1, y) != null && getItem(x - 1, y).getTipo() == tipo) {
                retorno++;
            }
        }

        return retorno;
    }

    /**
     * Verificas se tem vizinhos em blocos adjacentes (usado na geração de ruas)
     * 
     * @param tipo : ID do bloco a ser procurado
     * @param x    : coordenada x do bloco
     * @param y    : coordenada y do bloco
     * @param x    : coordenada x do bloco
     * @param y    : coordenada y do bloco
     * @return boolean número de vizinho de mesmo tipo
     */
    private boolean verifica2Prox(int tipo, int x, int y, int dirX, int dirY) {
        ItemMapa p;
        if (dirY == 1 || dirY == -1) {
            if (y < altura - 1) {
                p = getItem(x, y + 1);
                if (p != null && p.getTipo() == tipo) {
                    return false;
                } else if (y < altura - 2) {
                    p = getItem(x, y + 2);
                    if (p != null && p.getTipo() == tipo) {
                        return false;
                    }
                }
            }
            if (y > 0) {
                p = getItem(x, y - 1);
                if (p != null && p.getTipo() == tipo) {
                    return false;
                } else if (y > 1) {
                    p = getItem(x, y - 2);
                    if (p != null && p.getTipo() == tipo) {
                        return false;
                    }
                }
            }

        }
        if (dirX == 1 || dirX == -1) {
            if (x < largura - 1) {
                p = getItem(x + 1, y);
                if (p != null && p.getTipo() == tipo) {
                    return false;
                } else if (x < largura - 2) {
                    p = getItem(x + 2, y);
                    if (p != null && p.getTipo() == tipo) {
                        return false;
                    }
                }
            }
            if (x > 0) {
                p = getItem(x - 1, y);
                if (p != null && p.getTipo() == tipo) {
                    return false;
                } else if (x > 1) {
                    p = getItem(x - 2, y);
                    if (p != null && p.getTipo() == tipo) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    /**
     * Retorna bloco de rua proxima a um bolco de calcada
     * 
     * @param calcada : Localizacao do bloco de calcada
     * @return Localizacao do bloco de rua
     */
    public Localizacao ruaProx(Localizacao calcada) {
        int x = calcada.getX();
        int y = calcada.getY();
        if (x + 1 < largura && (this.getItem(x + 1, y)
                .getTipo() == tipoRua
                || this.getItem(x + 1, y)
                        .getTipo() == tipoFaixaPedestre)) {
            return getItem(x + 1, y)
                    .getLocalizacaoAtual();
        } else if (y + 1 < altura && (this.getItem(x, y + 1)
                .getTipo() == tipoRua
                || this.getItem(x, y + 1)
                        .getTipo() == tipoFaixaPedestre)) {
            return getItem(x, y + 1)
                    .getLocalizacaoAtual();
        } else if (x - 1 > 0 && (this.getItem(x - 1, y)
                .getTipo() == tipoRua
                || this.getItem(x - 1, y)
                        .getTipo() == tipoFaixaPedestre)) {
            return getItem(x - 1, y)
                    .getLocalizacaoAtual();
        } else if (y - 1 > 0 && (this.getItem(x, y - 1)
                .getTipo() == tipoRua
                || this.getItem(x, y - 1)
                        .getTipo() == tipoFaixaPedestre)) {
            return getItem(x, y - 1)
                    .getLocalizacaoAtual();
        } else if (x + 1 < largura && y + 1 < altura && (this.getItem(x + 1, y + 1)
                .getTipo() == tipoRua
                || this.getItem(x + 1, y + 1)
                        .getTipo() == tipoFaixaPedestre)) {
            return getItem(x + 1, y + 1)
                    .getLocalizacaoAtual();
        } else if (x - 1 > 0 && y + 1 < altura && (this.getItem(x - 1, y + 1)
                .getTipo() == tipoRua
                || this.getItem(x - 1, y + 1)
                        .getTipo() == tipoFaixaPedestre)) {
            return getItem(x - 1, y + 1)
                    .getLocalizacaoAtual();
        } else if (x - 1 > 0 && y - 1 > 0 && (this.getItem(x - 1, y - 1)
                .getTipo() == tipoRua
                || this.getItem(x - 1, y - 1)
                        .getTipo() == tipoFaixaPedestre)) {
            return getItem(x - 1, y - 1)
                    .getLocalizacaoAtual();
        } else if (x + 1 < largura && y - 1 > 0 && (this.getItem(x + 1, y - 1)
                .getTipo() == tipoRua && y - 1 > 0 || this.getItem(x + 1, y - 1)
                        .getTipo() == tipoFaixaPedestre)) {
            return getItem(x + 1, y - 1)
                    .getLocalizacaoAtual();
        } else {
            return null;
        }

    }

    public void adicionarItem(ItemMapa v) {
        itens[v.getLocalizacaoAtual().getX()][v.getLocalizacaoAtual().getY()] = v;
    }

    public void removerItem(ItemMapa v) {
        itens[v.getLocalizacaoAtual().getX()][v.getLocalizacaoAtual().getY()] = null;
    }

    public ItemMapa getItem(int x, int y) {
        return itens[x][y];
    }

    public int getLargura() {
        return largura;
    }

    public int getAltura() {
        return altura;
    }

}
