package simulacao;
//package simulacao;

/**
 * Representa um mapa com todos os itens que participam da simulacao
 * 
 * @author David J. Barnes and Michael Kolling and Luiz Merschmann
 */
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Mapa {
    private ItemMapa[][] itens;
    private int largura;
    private int altura;
    private static Random rand = new Random();

    private static final int LARGURA_PADRAO = 10;
    private static final int ALTURA_PADRAO = 10;
    private List<ItemMapa> ruas;
    private List<ItemMapa> calcada;
    private List<ItemMapa> faixa;

    private int tipoRua = 0;
    private int tipoCalcada = 1;
    private int tipoFaixaPedestre = 2;

    /**
     * Cria mapa para alocar itens da simulacao.
     * 
     * @param largura: largura da área de simulacao.
     * @param altura:  altura da área de simulação.
     */
    public Mapa(int largura, int altura) {
        this.largura = largura;
        this.altura = altura;
        itens = new ItemMapa[largura][altura];
        ruas = new ArrayList<ItemMapa>();
        calcada = new ArrayList<ItemMapa>();
        faixa = new ArrayList<ItemMapa>();
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

    public int getTipoCalcada() {
        return tipoCalcada;
    }

    public int getTipoFaixaPedestre() {
        return tipoFaixaPedestre;
    }

    public int getTipoRua() {
        return tipoRua;
    }

    public void gerarMapa() {
        // Configurações da geração de rua
        int quantidadeRuas = (int) (altura * largura * 0.3); // Numero de blocos do tipo rua
        int espacamento = 10; // Tendência de tamanho das ruas (>=1) quando maior, menos denso será o mapa
        int borda = 1; // Espaço minimo entre qualquer bloco de rua e a borda (preferencialmetne >=1)
        // int tipoRua = 0; // ID do bloco de rua
        String imagemRua = "Imagens/rua.jpg"; // Imagem do bloco de rua
        gerarRuas(quantidadeRuas, espacamento, borda, imagemRua, tipoRua);

        // Configurações da geração de calcadas (sempre gerada 1 bloco ao redor das
        // ruas)
        // int tipoCalcada = 1; // ID do bloco de calcada
        String imagemCalcada = "Imagens/calcada.jpg"; // Imagem do bloco de calcada
        gerarCalcada(imagemCalcada, tipoCalcada);

        // Configurações da geração de calcadas (sempre gerada em qualquer cruzamento de
        // ruas)
        // Caso não tenha cruzamentos uma faixa será criada aleatoriamente
        // int tipoFaixaPedestre = 2; // ID do bloco de faixa de pedestre
        String imagemFaixaPedestre = "Imagens/faixa.jpg"; // Imagem do bloco de faixa (horizontal)
        String imagemFaixaPedestreFlip = "Imagens/faixa_alt.jpg"; // Imagem do bloco de faixa (vertical)
        gerarFaixaPedestre(tipoRua, tipoCalcada, imagemFaixaPedestre,
                imagemFaixaPedestreFlip, tipoFaixaPedestre);

        // Configurações da geração do resto do mapa (área que não for preenchida pelos
        // outros blocos)
        int tipoPreenchimento = 3; // ID do bloco de faixa de pedestre
        String imagemPreenchimento = "Imagens/preenchimento.jpg"; // Imagem do bloco de preenchimento
        preencheEspaco(imagemPreenchimento, tipoPreenchimento);

    }

    private void gerarRuas(int max, int espacamento, int borda, String imagemCaminho, int tipo) {
        ItemMapa umItem;
        int dir;
        // System.out.println(max + " - tudo: " + altura + "," + largura + " = " +
        // largura * altura);
        int alt = 0;
        int lar = 0;
        int cont1 = 1, cont, loop, loop1 = 0;
        alt = rand.nextInt(altura - 2 * borda) + borda;
        lar = rand.nextInt(largura - 2 * borda) + borda;
        umItem = new ItemMapa(new Localizacao(lar, alt), 0,
                imagemCaminho);
        adicionarItem(umItem);
        ruas.add(umItem);
        // System.out.println("1: " + lar + "," + alt + " ---- " + cont1);

        while (cont1 < max && loop1 < largura * altura * 10) {
            cont = 0;
            umItem = ruas.get((int) Math.floor(Math.random() * ruas.size()));
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
                                cont++;
                                cont1++;
                                // System.out.println(cont1 + ": " + cont + ": " + lar + "," + alt);
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
                                cont++;
                                cont1++;
                                // System.out.println(cont1 + ": " + cont + ": " + alt + "," + lar);
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
                                cont++;
                                cont1++;
                                // System.out.println(cont1 + ": " + cont + ": " + alt + "," + lar);
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
                                cont++;
                                cont1++;
                                // System.out.println(cont1 + ": " + cont + ": " + alt + "," + lar);
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

    private void gerarFaixaPedestre(int tipoRua, int tipoCalcada, String imagemCaminho, String imagemCaminhoAlt,
            int tipo) {
        int x, y;
        List<ItemMapa> cruzamento;
        for (ItemMapa rua : ruas) {
            x = rua.getLocalizacaoAtual().getX();
            y = rua.getLocalizacaoAtual().getY();
            cruzamento = new ArrayList<ItemMapa>();

            if (y < altura - 1) {
                if (getItem(x, y + 1) != null && getItem(x, y + 1).getTipo() == tipoRua) {
                    cruzamento.add(getItem(x, y + 1));
                }
            }
            if (y > 0) {
                if (getItem(x, y - 1) != null && getItem(x, y - 1).getTipo() == tipoRua) {
                    cruzamento.add(getItem(x, y - 1));
                }
            }
            if (x < largura - 1) {
                if (getItem(x + 1, y) != null && getItem(x + 1, y).getTipo() == tipoRua) {
                    cruzamento.add(getItem(x + 1, y));
                }
            }
            if (x > 0) {
                if (getItem(x - 1, y) != null && getItem(x - 1, y).getTipo() == tipoRua) {
                    cruzamento.add(getItem(x - 1, y));
                }
            }

            if (cruzamento.size() > 2) {
                // umItem = cruzamento.get((int) Math.floor(Math.random() * cruzamento.size()));

                for (ItemMapa umItem : cruzamento) {

                    x = umItem.getLocalizacaoAtual().getX();
                    y = umItem.getLocalizacaoAtual().getY();
                    // removerItem(umItem);
                    // umItem = new ItemMapa(new Localizacao(x, y), tipoRua, "Imagens/inicio.jpg");
                    // adicionarItem(umItem);

                    // System.out.println("tentando : " + ": " + x + "," + y);

                    if (verificaVizinho(tipo, x, y) == 0 && verificaVizinho(tipoRua, x, y) < 3) {
                        if (y != altura - 1 && y != 0 && x != largura - 1 && x != 0) {
                            if (getItem(x + 1, y) != null && getItem(x + 1, y).getTipo() == tipoCalcada
                                    && getItem(x - 1, y) != null && getItem(x - 1, y).getTipo() == tipoCalcada) {
                                // System.out.println("sucesso x : " + ": " + x + "," + y);
                                removerItem(umItem);
                                umItem = new ItemMapa(new Localizacao(x, y), tipo, imagemCaminho);
                                adicionarItem(umItem);
                                faixa.add(umItem);
                                calcada.add(umItem);
                            }
                            if (getItem(x, y + 1) != null && getItem(x, y + 1).getTipo() == tipoCalcada
                                    && getItem(x, y - 1) != null && getItem(x, y - 1).getTipo() == tipoCalcada) {
                                // System.out.println("sucesso y : " + ": " + x + "," + y);
                                removerItem(umItem);
                                umItem = new ItemMapa(new Localizacao(x, y), tipo, imagemCaminhoAlt);
                                adicionarItem(umItem);
                                faixa.add(umItem);
                                calcada.add(umItem);
                            }
                        }

                    }

                }

            }
        }

        if (faixa.isEmpty() == true) { // exceção

            ItemMapa umItem = ruas.get((int) Math.floor(Math.random() * ruas.size()));
            x = umItem.getLocalizacaoAtual().getX();
            y = umItem.getLocalizacaoAtual().getY();
            if (y != altura - 1 && y != 0 && x != largura - 1 && x != 0) {
                if (getItem(x + 1, y) != null && getItem(x + 1, y).getTipo() == tipoCalcada && getItem(x - 1, y) != null
                        && getItem(x - 1, y).getTipo() == tipoCalcada) {
                    // System.out.println("sucesso x : " + ": " + x + "," + y);
                    removerItem(umItem);
                    ruas.remove(umItem);
                    umItem = new ItemMapa(new Localizacao(x, y), tipo, imagemCaminho);
                    adicionarItem(umItem);
                    ruas.add(umItem);
                    faixa.add(umItem);
                    calcada.add(umItem);
                }
                if (getItem(x, y + 1) != null && getItem(x, y + 1).getTipo() == tipoCalcada
                        && getItem(x, y - 1) != null && getItem(x, y - 1).getTipo() == tipoCalcada) {
                    // System.out.println("sucesso y : " + ": " + x + "," + y);
                    removerItem(umItem);
                    ruas.remove(umItem);
                    umItem = new ItemMapa(new Localizacao(x, y), tipo, imagemCaminhoAlt);
                    ruas.add(umItem);
                    adicionarItem(umItem);
                    faixa.add(umItem);
                    calcada.add(umItem);
                }
            }
        }
    }

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

    private boolean verifica2Prox(int tipo, int x, int y, int dirX, int dirY) {
        ItemMapa p;
        if (dirY == 1 || dirY == -1) {
            if (y < altura - 1) {
                p = getItem(x, y + 1);
                if (p != null && p.getTipo() == tipo) {
                    // System.out.println("aqui 1");
                    return false;
                } else if (y < altura - 2) {
                    p = getItem(x, y + 2);
                    if (p != null && p.getTipo() == tipo) {
                        // System.out.println("aqui 2");
                        return false;
                    }
                }
            }
            if (y > 0) {
                p = getItem(x, y - 1);
                if (p != null && p.getTipo() == tipo) {
                    // System.out.println("aqui 3");
                    return false;
                } else if (y > 1) {
                    p = getItem(x, y - 2);
                    if (p != null && p.getTipo() == tipo) {
                        // System.out.println("aqui 4");
                        return false;
                    }
                }
            }

        }
        if (dirX == 1 || dirX == -1) {
            if (x < largura - 1) {
                p = getItem(x + 1, y);
                if (p != null && p.getTipo() == tipo) {
                    // System.out.println("aqui 5");
                    return false;
                } else if (x < largura - 2) {
                    p = getItem(x + 2, y);
                    if (p != null && p.getTipo() == tipo) {
                        // System.out.println("aqui 6");
                        return false;
                    }
                }
            }
            if (x > 0) {
                p = getItem(x - 1, y);
                if (p != null && p.getTipo() == tipo) {
                    // System.out.println("aqui 7");
                    return false;
                } else if (x > 1) {
                    p = getItem(x - 2, y);
                    if (p != null && p.getTipo() == tipo) {
                        // System.out.println("aqui 8");
                        return false;
                    }
                }
            }
        }
        // System.out.println("aqui 9");
        return true;
    }

    public void adicionarItem(ItemMapa v) {
        itens[v.getLocalizacaoAtual().getX()][v.getLocalizacaoAtual().getY()] = v;
    }

    public void removerItem(ItemMapa v) {
        itens[v.getLocalizacaoAtual().getX()][v.getLocalizacaoAtual().getY()] = null;
    }

    public void atualizarMapa(ItemMapa v) {
        removerItem(v);
        adicionarItem(v);
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
