package simulacao;

/**
 * Responsavel pela simulacao.
 * 
 * @author TP3 - ARTHUR HAUCK DITTZ, MARCO ANTONIO MAGALHAES
 * @author David J. Barnes and Michael Kolling and Luiz Merschmann
 */
public class Simulacao {
    private JanelaSimulacao janelaSimulacao;
    private Mapa mapa;
    private Entidades entidades;

    public Simulacao() {
        mapa = new Mapa();
        mapa.gerarMapa();

        entidades = new Entidades(mapa);
        entidades.criarPopulacao(3, 3);
        janelaSimulacao = new JanelaSimulacao(mapa, entidades);
    }

    public void executarSimulacao(int numPassos) {

        janelaSimulacao.executarAcao();
        for (int i = 0; i < numPassos; i++) {
            executarUmPasso();
            esperar(600);
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
