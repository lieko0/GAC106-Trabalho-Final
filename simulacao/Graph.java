package simulacao;

import java.util.ArrayList;
import java.util.List;

public class Graph {

    int[][] adjacencyMatrix;
    int tipo;
    int tipoAlt;
    private static final int NO_PARENT = 4;
    private List<Integer> caminho;

    public Graph(int altura, int largura, int tipo, int tipoAlt) {
        adjacencyMatrix = new int[altura * largura][altura * largura];
        for (int i = 0; i < altura * largura; i++) {
            for (int j = 0; j < largura; j++) {
                adjacencyMatrix[i][j] = 0;
            }
        }
        this.tipo = tipo;
        this.tipoAlt = tipoAlt;
        this.caminho = new ArrayList<Integer>();
    }

    public void criarGrafo(ItemMapa[][] itens, int altura, int largura) {
        for (int i = 0; i < altura; i++) {
            for (int j = 0; j < largura; j++) {
                if (itens[i][j].getTipo() == tipo || itens[i][j].getTipo() == tipoAlt) {
                    if (i + 1 < altura && (itens[i + 1][j].getTipo() == tipo || itens[i + 1][j].getTipo() == tipoAlt)) {
                        adjacencyMatrix[largura * i + j][largura * (i + 1) + j] = 1;
                        adjacencyMatrix[largura * (i + 1) + j][largura * i + j] = 1;
                    }
                    if (j + 1 < largura
                            && (itens[i][j + 1].getTipo() == tipo || itens[i][j + 1].getTipo() == tipoAlt)) {
                        adjacencyMatrix[largura * i + j][largura * i + j + 1] = 1;
                        adjacencyMatrix[largura * i + j + 1][largura * i + j] = 1;
                    }
                }
            }
        }
    }

    public List<Integer> menosCaminho(int startVertex, int destino) {
        dijkstra(adjacencyMatrix, startVertex, destino);
        return caminho;
    }

    private void dijkstra(int[][] adjacencyMatrix,
            int startVertex, int destino) {
        int nVertices = adjacencyMatrix[0].length;

        // shortestDistances[i] will hold the
        // shortest distance from src to i
        int[] shortestDistances = new int[nVertices];

        // added[i] will true if vertex i is
        // included / in shortest path tree
        // or shortest distance from src to
        // i is finalized
        boolean[] added = new boolean[nVertices];

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
        int[] parents = new int[nVertices];

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
        createPath(destino, parents);
    }

    private void createPath(int destino,
            int[] parents) {

        // Base case : Source node has
        // been processed
        if (destino == NO_PARENT) {
            return;
        }
        createPath(parents[destino], parents);
        caminho.add(destino);
    }
}
