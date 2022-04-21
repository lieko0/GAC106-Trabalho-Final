package simulacao;

/**
 * TP 03: Tema do trabalho prático
 * Veículos em circulação na área demarcada: motos.
 * Item a ser transportado: pessoas.
 * Dificultadores para movimentação dos veículos: pessoas, semáforo.
 * Modalidade de transporte: considerar que o veículo de transporte irá coletar
 * um único item num ponto de origem e deixá-lo num ponto de destino.
 * 
 * 
 * @author TP3 - ARTHUR HAUCK DITTZ, MARCO ANTONIO MAGALHAES
 * @author Luiz Merschmann
 */
public class Principal {

    public static void main(String[] args) {
        Simulacao sim = new Simulacao();
        sim.executarSimulacao(10000);
    }
}
