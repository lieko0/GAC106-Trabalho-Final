package simulacao;
//package simulacao;

//import java.util.ArrayList;
//import java.util.List;
//import java.util.Random;

/**
 * Responsavel pela simulacao.
 * 
 * @author David J. Barnes and Michael Kolling and Luiz Merschmann
 */
public class Simulacao {
    // private Veiculo veiculo;
    private JanelaSimulacao janelaSimulacao;
    private Mapa mapa;
    private Entidades entidades;

    public Simulacao() {
        // Random rand = new Random(345345684);
        mapa = new Mapa();

        // int largura = mapa.getLargura();
        // int altura = mapa.getAltura();
        // veiculo = new Veiculo(new Localizacao(rand.nextInt(largura),
        // rand.nextInt(altura)));// Cria um veiculo em uma
        // posicao aleatoria
        // veiculo.setLocalizacaoDestino(new Localizacao(rand.nextInt(largura),
        // rand.nextInt(altura)));// Define a posicao
        // destino
        // aleatoriamente
        // mapa.adicionarItem(veiculo);// Inicializando o mapa com o veículo
        mapa.gerarMapa();
        entidades = new Entidades(mapa);
        entidades.criarPopulacao();

        //
        //
        //
        // Teste de grafos para gerar caminhos nas calcadas
        //
        //
        //
        /*
         * Localizacao origem = mapa.getCalcada().get((int) Math.floor(Math.random() *
         * mapa.getCalcada().size()))
         * .getLocalizacaoAtual();
         * Localizacao destino = mapa.getCalcada().get((int)
         * Math.floor(rand.nextDouble() * mapa.getCalcada().size()))
         * .getLocalizacaoAtual();
         * 
         * System.out.println("Pedestre\nOrigem: " + origem + " " + (largura *
         * origem.getX() + origem.getY()));
         * System.out.println("Destino: " + destino + " " + (largura * destino.getX() +
         * destino.getY()));
         */

        /*
         * caminho = g.menosCaminho(largura * origem.getX() + origem.getY(), (largura *
         * destino.getX() + destino.getY()));
         * 
         * System.out.print("|> ");
         * for (Integer a : caminho) {
         * System.out.print(" ->- " + new Localizacao(a % largura, a / largura) + " " +
         * a);
         * }
         * System.out.print(" >|\n");
         */
        //
        //
        //
        // Teste de grafos para gerar caminhos nas calcadas
        //
        //
        //
        /*
         * origem = mapa.getRuas().get((int) Math.floor(Math.random() *
         * mapa.getRuas().size())).getLocalizacaoAtual();
         * destino = mapa.getRuas().get((int) Math.floor(rand.nextDouble() *
         * mapa.getRuas().size())).getLocalizacaoAtual();
         * 
         * System.out.println("\nVeiculo\nOrigem: " + origem + " " + (largura *
         * origem.getX() + origem.getY()));
         * System.out.println("Destino: " + destino + " " + (largura * destino.getX() +
         * destino.getY()));
         *//*
            * g = new Graph(altura, largura, mapa.getTipoRua(),
            * mapa.getTipoFaixaPedestre());
            * 
            * g.criarGrafo(mapa.getItens(), altura, largura);
            */
        /*
         * caminho = g.menosCaminho(largura * origem.getX() + origem.getY(), (largura *
         * destino.getX() + destino.getY()));
         * 
         * System.out.print("|> ");
         * for (Integer a : caminho) {
         * System.out.print(" ->- " + new Localizacao(a % largura, a / largura) + " " +
         * a);
         * }
         * System.out.print(" >|\n");
         */
        janelaSimulacao = new JanelaSimulacao(mapa, entidades);
    }

    public void executarSimulacao(int numPassos) {

        janelaSimulacao.executarAcao();
        for (int i = 0; i < numPassos; i++) {
            executarUmPasso();
            esperar(1200);
        }
    }

    private void executarUmPasso() {
        entidades.executar();
        janelaSimulacao.executarAcao();
    }

    private void esperar(int milisegundos) {
        try {
            Thread.sleep(milisegundos);
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }
    }

}
