package simulacao;

//package simulacao;
import java.awt.*;
import javax.swing.*;

/**
 * Fornece a visualizacao da simulacao
 * 
 * @author TP3 - ARTHUR HAUCK DITTZ, MARCO ANTONIO MAGALHAES
 * @author David J. Barnes and Michael Kolling and Luiz Merschmann
 * 
 */
public class JanelaSimulacao extends JFrame {
    private Mapa mapa;
    private Entidades entidades;
    private VisaoMapa visaoMapa;

    public JanelaSimulacao(Mapa mapa, Entidades entidades) {
        this.mapa = mapa;
        this.entidades = entidades;
        visaoMapa = new VisaoMapa(mapa.getLargura(), mapa.getAltura());
        getContentPane().add(visaoMapa);
        setTitle("Simulator");
        int tam = 1500;
        float ratioAlt = tam * ((float) mapa.getAltura() / ((float) mapa.getLargura() + (float) mapa.getAltura()));
        float ratioLar = tam * ((float) mapa.getLargura() / ((float) mapa.getLargura() + (float) mapa.getAltura()));
        setSize((int) ratioLar,
                (int) ratioAlt);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    /**
     * Mostra o estado atual do mapa.
     * 
     */
    public void executarAcao() {

        visaoMapa.preparePaint();
        Localizacao localizacao;
        for (int i = 0; i < mapa.getLargura(); i++) {
            for (int j = 0; j < mapa.getAltura(); j++) {
                if (mapa.getItem(i, j) != null) {// Se existir algum objeto na
                                                 // posicao (i,j)
                    ItemMapa chao = mapa.getItem(i, j);
                    localizacao = chao.getLocalizacaoAtual();
                    visaoMapa.desenharImagem(localizacao.getY(), localizacao.getX(), chao.getImagem());

                }

            }
        }
        for (ItemDinamico d : entidades.getTodasEntidades()) {
            if (d.getVisivel())
                visaoMapa.desenharImagem(d.getLocalizacaoAtual().getY(), d.getLocalizacaoAtual().getX(), d.getImagem());
        }
        for (ItemMapa s : mapa.getSemaforo()) {
            visaoMapa.desenharImagem(s.getLocalizacaoAtual().getY(), s.getLocalizacaoAtual().getX(), s.getImagem());
        }
        visaoMapa.repaint();
    }

    /**
     * Fornece uma visualizacao grafica do mapa. Esta eh
     * uma classe interna que define os componentes da GUI.
     * Ela contém alguns detalhes mais avancados sobre GUI
     * que voce pode ignorar para realizacao do seu trabalho.
     * 
     */
    private class VisaoMapa extends JPanel {

        private final int VIEW_SCALING_FACTOR = 6;

        private int larguraMapa, alturaMapa;
        private int xScale, yScale;
        private Dimension tamanho;
        private Graphics g;
        private Image imagemMapa;

        /**
         * Cria um novo componente VisaoMapa.
         * 
         */
        public VisaoMapa(int largura, int altura) {
            larguraMapa = largura;
            alturaMapa = altura;
            setBackground(Color.white);
            tamanho = new Dimension(0, 0);
        }

        /**
         * Informa para o gerenciador GUI o tamanho.
         * 
         */
        public Dimension getPreferredSize() {
            return new Dimension(larguraMapa * VIEW_SCALING_FACTOR,
                    alturaMapa * VIEW_SCALING_FACTOR);
        }

        /**
         * Prepara para um novo ciclo de exibicao. Uma vez que o componente
         * pode ser redimensionado, calcula o "fator de escala" novamente.
         * 
         */
        public void preparePaint() {
            if (!tamanho.equals(getSize())) { // se o tamanho mudou...
                tamanho = getSize();
                imagemMapa = visaoMapa.createImage(tamanho.width, tamanho.height);
                g = imagemMapa.getGraphics();

                xScale = tamanho.width / larguraMapa;
                if (xScale < 1) {
                    xScale = VIEW_SCALING_FACTOR;
                }
                yScale = tamanho.height / alturaMapa;
                if (yScale < 1) {
                    yScale = VIEW_SCALING_FACTOR;
                }
            }
            g.setColor(Color.BLACK);
            g.fillRect(0, 0, tamanho.width, tamanho.height);
            g.setColor(Color.WHITE);
            for (int i = 0, x = 0; x < tamanho.width; i++, x = i * xScale) {
                g.drawLine(x, 0, x, tamanho.height - 1);
            }
            for (int i = 0, y = 0; y < tamanho.height; i++, y = i * yScale) {
                g.drawLine(0, y, tamanho.width - 1, y);
            }
        }

        /**
         * Desenha a imagem para um determinado item.
         * 
         */
        public void desenharImagem(int y, int x, Image image) {
            g.drawImage(image, x * xScale + 1, y * yScale + 1,
                    xScale - 1, yScale - 1, this);

            // ********* debug grafo **********
            // g.drawString("(x" + x + ",y" + y + ")v" + (y * larguraMapa + x), x *
            // xScale, y * yScale + 35);
        }

        /**
         * O componente VisaoMapa precisa ser reexibido. Copia a
         * imagem interna para a tela.
         * 
         */
        public void paintComponent(Graphics g) {
            if (imagemMapa != null) {
                g.drawImage(imagemMapa, 0, 0, null);
            }
        }
    }

}
