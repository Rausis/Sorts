import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;


public class Main {


    public static NewArrayList<Integer> lerCSVParaLista(String caminhoArquivoCsv) {
        NewArrayList<Integer> numeros = new NewArrayList<>();

        try (BufferedReader leitor = new BufferedReader(new FileReader(caminhoArquivoCsv))) {
            String linhaAtual = leitor.readLine(); // pode ser cabeçalho

            // Se a primeira linha tiver letras (ex: "Value"), pula como cabeçalho
            if (linhaAtual != null && linhaAtual.matches(".*[a-zA-Z].*")) {
                linhaAtual = leitor.readLine();
            }

            // Lê uma linha por vez; cada linha deve conter um único inteiro
            while (linhaAtual != null) {
                linhaAtual = linhaAtual.trim();
                if (!linhaAtual.isEmpty()) {
                    try {
                        numeros.add(Integer.parseInt(linhaAtual));
                    } catch (NumberFormatException e) {
                        // Se houver uma linha inválida, ignoramos silenciosamente
                        // (poderia logar/contar erros se necessário)
                    }
                }
                linhaAtual = leitor.readLine();
            }

        } catch (IOException e) {
            System.out.println("Erro ao ler o arquivo: " + caminhoArquivoCsv + " -> " + e.getMessage());
        }

        return numeros;
    }

    /**
     * Converte sua NewArrayList<Integer> em um vetor primitivo int[].
     * Evita o uso de java.util.* (Arrays, List, etc.).
     */
    public static int[] converterParaArray(NewArrayList<Integer> lista) {
        int tamanho = lista.getSize();
        int[] resultado = new int[tamanho];
        for (int i = 0; i < tamanho; i++) {
            resultado[i] = lista.getElement(i);
        }
        return resultado;
    }

    /**
     * Cria uma cópia profunda de um vetor int[] (sem usar Arrays.copyOf).
     */
    public static int[] copiarVetor(int[] origem) {
        int[] destino = new int[origem.length];
        for (int i = 0; i < origem.length; i++) {
            destino[i] = origem[i];
        }
        return destino;
    }

    public static void main(String[] args) {
        Sorts sorts = new Sorts();

        // Ajuste os caminhos se necessário. Cada arquivo contém UMA coluna com números.
        String[] caminhosArquivos = {
                "aleatorio_100.csv", "aleatorio_1000.csv", "aleatorio_10000.csv",
                "crescente_100.csv", "crescente_1000.csv", "crescente_10000.csv",
                "decrescente_100.csv", "decrescente_1000.csv", "decrescente_10000.csv"
        };

        System.out.println("Arquivo\t\t\tBubbleSort(ms)\tInsertionSort(ms)\tQuickSort(ms)");
        System.out.println("--------------------------------------------------------------------------");

        for (int indiceArquivo = 0; indiceArquivo < caminhosArquivos.length; indiceArquivo++) {
            String caminhoAtual = caminhosArquivos[indiceArquivo];

            // 1) Lê o CSV em NewArrayList<Integer>
            NewArrayList<Integer> numerosLista = lerCSVParaLista(caminhoAtual);

            // 2) Converte para int[] uma única vez
            int[] dadosOriginais = converterParaArray(numerosLista);
            if (dadosOriginais.length == 0) {
                System.out.println(caminhoAtual + "\t" + "ARQUIVO VAZIO/INVÁLIDO");
                continue;
            }

            // 3) Faz cópias independentes e mede tempos com nanoTime

            // Bubble Sort
            int[] dadosBubble = copiarVetor(dadosOriginais);
            long inicioNs = System.nanoTime();
            sorts.bubbleSort(dadosBubble);
            long fimNs = System.nanoTime();
            double tempoBubbleMs = (fimNs - inicioNs) / 1_000_000.0;

            // Insertion Sort
            int[] dadosInsertion = copiarVetor(dadosOriginais);
            inicioNs = System.nanoTime();
            sorts.insertionSort(dadosInsertion);
            fimNs = System.nanoTime();
            double tempoInsertionMs = (fimNs - inicioNs) / 1_000_000.0;

            // Quick Sort
            int[] dadosQuick = copiarVetor(dadosOriginais);
            inicioNs = System.nanoTime();
            quickSort(dadosQuick, 0, dadosQuick.length - 1);
            fimNs = System.nanoTime();
            double tempoQuickMs = (fimNs - inicioNs) / 1_000_000.0;

            // 4) Exibe linha da tabela com os três tempos em ms
            System.out.println(
                    caminhoAtual + "\t" +
                            String.format("%.3f", tempoBubbleMs) + "\t\t" +
                            String.format("%.3f", tempoInsertionMs) + "\t\t\t" +
                            String.format("%.3f", tempoQuickMs)
            );
        }
    }


    /** Ordena o vetor v no intervalo [ini..fim] usando QuickSort. */
    public static void quickSort(int[] v, int ini, int fim) {
        if (ini < fim) {
            int indicePivo = particionar(v, ini, fim);
            quickSort(v, ini, indicePivo - 1);   // parte esquerda
            quickSort(v, indicePivo + 1, fim);   // parte direita
        }
    }

    /**
     * Particiona o vetor escolhendo o último elemento como pivô.
     * Todos <= pivô ficam à esquerda; > pivô à direita.
     * Retorna a posição final do pivô.
     */
    private static int particionar(int[] v, int ini, int fim) {
        int pivo = v[fim];
        int i = ini - 1; // i marca a "fronteira" dos elementos <= pivô
        for (int j = ini; j < fim; j++) {
            if (v[j] <= pivo) {
                i++;
                int temp = v[i]; v[i] = v[j]; v[j] = temp; // troca v[i] com v[j]
            }
        }
        // Coloca o pivô na posição correta (i+1)
        int temp = v[i + 1]; v[i + 1] = v[fim]; v[fim] = temp;
        return i + 1;
    }
}
