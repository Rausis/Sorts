public class Sorts {

    // Bubble Sort
    public void bubbleSort(int[] numeros) {
        int tamanho = numeros.length;
        for (int i = 0; i < tamanho - 1; i++) {
            for (int j = 0; j < tamanho - i - 1; j++) {
                if (numeros[j] > numeros[j + 1]) {
                    int temporario = numeros[j];
                    numeros[j] = numeros[j + 1];
                    numeros[j + 1] = temporario;
                }
            }
        }
    }

    // Selection Sort
    public void selectionSort(int[] numeros) {
        int tamanho = numeros.length;
        for (int i = 0; i < tamanho - 1; i++) {
            int indiceMenor = i;
            for (int j = i + 1; j < tamanho; j++) {
                if (numeros[j] < numeros[indiceMenor]) {
                    indiceMenor = j;
                }
            }

            // Troca o menor elemento com o primeiro da parte não ordenada
            int temporario = numeros[indiceMenor];
            numeros[indiceMenor] = numeros[i];
            numeros[i] = temporario;
        }
    }

    // Insertion Sort
    public void insertionSort(int[] numeros) {
        int tamanho = numeros.length;
        for (int i = 1; i < tamanho; i++) {
            int valorAtual = numeros[i];
            int posicao = i - 1;

            while (posicao >= 0 && numeros[posicao] > valorAtual) {
                numeros[posicao + 1] = numeros[posicao];
                posicao--;
            }
            numeros[posicao + 1] = valorAtual;
        }
    }
}
