package simulacao;

import java.util.ArrayList;

import java.util.List;

/**
 * Representa os grafos da simulacao.
 * 
 * @author TP3 - ARTHUR HAUCK DITTZ, MARCO ANTONIO MAGALHAES
 * 
 */
public class Graph {

    private int[][] adjacencyMatrix;
    private int tipo;
    private int tipoAlt;
    private static final int NO_PARENT = -1;
    private List<Integer> caminho;
    private int largura;

    // outofmemory
    private int nVertices;
    private List<Localizacao> loc;
    private int[] shortestDistances;
    private boolean[] added;
    private int[] parents;

    /**
     * Intancia Grafo
     * 
     */
    public Graph(int altura, int largura, int tipo, int tipoAlt) {
        adjacencyMatrix = new int[altura * largura][altura * largura];
        for (int i = 0; i < altura * largura; i++) {
            for (int j = 0; j < altura * largura; j++) {
                adjacencyMatrix[i][j] = 0;
            }
        }
        // this.altura = altura;
        this.largura = largura;
        this.tipo = tipo;
        this.tipoAlt = tipoAlt;
        this.caminho = new ArrayList<Integer>();

        // outofmemory
        this.nVertices = altura * largura;
        this.loc = new ArrayList<Localizacao>();

    }

    /**
     * Cria um grafo baseado nos tipos de parâmetro
     * 
     */
    public void criarGrafo(ItemMapa[][] itens, int altura, int largura) {
        for (int i = 0; i < largura; i++) {
            for (int j = 0; j < altura; j++) {
                if (itens[i][j].getTipo() == tipo || itens[i][j].getTipo() == tipoAlt) {
                    if (i + 1 < largura
                            && (itens[i + 1][j].getTipo() == tipo || itens[i + 1][j].getTipo() == tipoAlt)) {
                        adjacencyMatrix[largura * j + 1 + i][largura * j + i] = 1;
                        adjacencyMatrix[largura * j + i][largura * j + 1 + i] = 1;
                    }
                    if (j + 1 < altura
                            && (itens[i][j + 1].getTipo() == tipo || itens[i][j + 1].getTipo() == tipoAlt)) {
                        adjacencyMatrix[largura * (j + 1) + i][largura * j + i] = 1;
                        adjacencyMatrix[largura * j + i][largura * (j + 1) + i] = 1;
                    }
                }
            }
        }
    }

    /**
     * Retorna o menor caminho entre os dois vertices
     * 
     */
    private List<Integer> menosCaminho(int startVertex, int destino) {
        this.caminho.clear();
        dijkstra(adjacencyMatrix, startVertex, destino);
        if (this.caminho.size() > 0)
            this.caminho.remove(0);
        return caminho;
    }

    /**
     * Retorna o menor caminho entre as duas localizações
     * 
     */
    public List<Localizacao> menosCaminhoL(Localizacao origem, Localizacao destino) {
        loc.clear();
        // System.out.println("origem: " + origem + " destino: " + destino);
        for (Integer i : menosCaminho(largura * origem.getY() + origem.getX(), largura *
                destino.getY() + destino.getX())) {
            loc.add(new Localizacao(i % largura, i / largura));
        }
        return loc;
    }

    /**
     * Algoritimo de dijktra usado na obtenção do menor caminho
     * 
     */
    private void dijkstra(int[][] adjacencyMatrix,
            int startVertex, int destino) {

        // shortestDistances[i] will hold the
        // shortest distance from src to i
        shortestDistances = new int[nVertices];

        // added[i] will true if vertex i is
        // included / in shortest path tree
        // or shortest distance from src to
        // i is finalized
        added = new boolean[nVertices];

        // Initialize all distances as
        // INFINITE and added[] as false
        for (int vertexIndex = 0; vertexIndex < nVertices; vertexIndex++) {
            shortestDistances[vertexIndex] = Integer.MAX_VALUE;
            added[vertexIndex] = false;
        }

        // Distance of source vertex from
        // itself is always 0
        shortestDistances[startVertex] = 0;

        // Parent array to store shortest
        // path tree
        parents = new int[nVertices];

        // The starting vertex does not
        // have a parent
        parents[startVertex] = NO_PARENT;

        // Find shortest path for all
        // vertices
        for (int i = 0; i < nVertices; i++) {

            // Pick the minimum distance vertex
            // from the set of vertices not yet
            // processed. nearestVertex is
            // always equal to startNode in
            // first iteration.
            int nearestVertex = -1;
            int shortestDistance = Integer.MAX_VALUE;
            for (int vertexIndex = 0; vertexIndex < nVertices; vertexIndex++) {
                if (!added[vertexIndex] &&
                        shortestDistances[vertexIndex] <= shortestDistance) {
                    nearestVertex = vertexIndex;
                    shortestDistance = shortestDistances[vertexIndex];
                }
            }

            // Mark the picked vertex as
            // processed

            added[nearestVertex] = true;

            // Update dist value of the
            // adjacent vertices of the
            // picked vertex.
            for (int vertexIndex = 0; vertexIndex < nVertices; vertexIndex++) {
                int edgeDistance = adjacencyMatrix[nearestVertex][vertexIndex];

                if (edgeDistance > 0
                        && ((shortestDistance + edgeDistance) < shortestDistances[vertexIndex])) {
                    parents[vertexIndex] = nearestVertex;
                    shortestDistances[vertexIndex] = shortestDistance +
                            edgeDistance;
                }
            }
        }

        // printSolution(startVertex, shortestDistances, parents);
        if (destino != startVertex) {
            createPathRec(destino, parents);
        }

    }

    /**
     * Cria o caminho baseado nas interações realizadas no algoritmo de dijkstra
     * 
     */
    private void createPathRec(int destino,
            int[] parents) {

        // Base case : Source node has
        // been processed
        if (destino == NO_PARENT) {
            return;
        }
        createPathRec(parents[destino], parents);
        // System.out.println(destino);
        caminho.add(destino);
    }
    /*
     * private void createPathIt(int destino,
     * int[] parents) {
     * while (destino != NO_PARENT) {
     * caminho.add(destino);
     * destino = parents[destino];
     * }
     * Collections.reverse(caminho);
     * }
     */

}
