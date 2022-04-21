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
        mapa.gerarMapa();

        entidades = new Entidades(mapa);
        entidades.criarPopulacao();
        janelaSimulacao = new JanelaSimulacao(mapa, entidades);
    }

    public void executarSimulacao(int numPassos) {

        janelaSimulacao.executarAcao();
        for (int i = 0; i < numPassos; i++) {
            executarUmPasso();
            esperar(200);
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
