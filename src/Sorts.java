public class Sorts {

    // ============================================================
    // ====================== BUBBLE SORT ==========================
    // ============================================================
    /**
     * Ordena o vetor movendo os maiores valores para o final
     * em sucessivas "passadas".
     */
    public void bubbleSort(int[] vetor) {

        int tamanho = vetor.length;

        // Cada passada garante que o maior elemento "bolha" para o final
        for (int passada = 0; passada < tamanho - 1; passada++) {

            // Percorre até o ponto onde os últimos já estão ordenados
            for (int indice = 0; indice < tamanho - passada - 1; indice++) {

                // Se estiver fora de ordem, troca
                if (vetor[indice] > vetor[indice + 1]) {
                    int temporario = vetor[indice];
                    vetor[indice] = vetor[indice + 1];
                    vetor[indice + 1] = temporario;
                }
            }
        }
    }

    // ============================================================
    // ==================== INSERTION SORT ========================
    // ============================================================
    /**
     * Ordena o vetor como se estivesse organizando cartas na mão.
     * A parte inicial cresce ordenada, e cada elemento novo
     * é inserido na posição correta.
     */
    public void insertionSort(int[] vetor) {

        int tamanho = vetor.length;

        for (int atual = 1; atual < tamanho; atual++) {

            int valorSendoInserido = vetor[atual];
            int posicao = atual - 1;

            // Enquanto valores anteriores forem maiores, mova-os para a frente
            while (posicao >= 0 && vetor[posicao] > valorSendoInserido) {
                vetor[posicao + 1] = vetor[posicao];
                posicao--;
            }

            // Coloca o valor na posição correta
            vetor[posicao + 1] = valorSendoInserido;
        }
    }

    // ============================================================
    // ======================= QUICK SORT ==========================
    // ============================================================

    /**
     * Função pública que o usuário chama.
     * Apenas inicia a recursão passando os limites.
     */
    public void quickSort(int[] vetor) {
        quickSortRecursivo(vetor, 0, vetor.length - 1);
    }

    /**
     * QuickSort recursivo: divide o vetor em duas partes
     * ao redor do pivô, e ordena cada lado separadamente.
     */
    private void quickSortRecursivo(int[] vetor, int inicio, int fim) {

        // Só continua se o intervalo tiver pelo menos 2 elementos
        if (inicio < fim) {

            // Posição final correta do pivô
            int indiceDoPivo = particionar(vetor, inicio, fim);

            // Ordena o lado esquerdo do pivô
            quickSortRecursivo(vetor, inicio, indiceDoPivo - 1);

            // Ordena o lado direito do pivô
            quickSortRecursivo(vetor, indiceDoPivo + 1, fim);
        }
    }

    /**
     * Particiona o vetor escolhendo o último elemento como pivô.
     * Todos os valores menores ou iguais ao pivô ficam à esquerda.
     * Valores maiores ficam à direita.
     * Retorna a posição final do pivô.
     */
    private int particionar(int[] vetor, int inicio, int fim) {

        int pivo = vetor[fim]; // Pivô é o último elemento do intervalo
        int indiceMenores = inicio - 1; // Marca onde terminam os menores

        // Varre todos os elementos antes do pivô
        for (int indiceAtual = inicio; indiceAtual < fim; indiceAtual++) {

            // Se encontrou valor <= pivô, avança a fronteira dos menores
            if (vetor[indiceAtual] <= pivo) {
                indiceMenores++;

                // Troca o elemento atual com a posição correta dos menores
                int temp = vetor[indiceMenores];
                vetor[indiceMenores] = vetor[indiceAtual];
                vetor[indiceAtual] = temp;
            }
        }

        // Coloca o pivô logo após os menores
        int temp = vetor[indiceMenores + 1];
        vetor[indiceMenores + 1] = vetor[fim];
        vetor[fim] = temp;

        // Retorna a posição final onde o pivô ficou
        return indiceMenores + 1;
    }
}
