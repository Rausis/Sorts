import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Main {

    // ============================================================
    // ================ FUNÇÃO PARA LER O CSV ======================
    // ============================================================
    /**
     * Lê um arquivo CSV que contém uma COLUNA de números.
     * Ignora a primeira linha caso seja um cabeçalho textual (ex: "Value").
     * Retorna os valores dentro de uma NewArrayList<Integer>.
     */
    public static NewArrayList<Integer> lerCSVParaLista(String caminhoArquivoCsv) {

        NewArrayList<Integer> listaNumeros = new NewArrayList<>();

        try (BufferedReader leitor = new BufferedReader(new FileReader(caminhoArquivoCsv))) {

            String linha = leitor.readLine(); // primeira linha pode ser cabeçalho

            // Se a primeira linha tiver letras, ignora (ex: "Value")
            if (linha != null && linha.matches(".*[a-zA-Z].*")) {
                linha = leitor.readLine(); // pula cabeçalho
            }

            // Lê linha por linha até acabar
            while (linha != null) {
                linha = linha.trim();

                if (!linha.isEmpty()) {
                    try {
                        listaNumeros.add(Integer.parseInt(linha));
                    } catch (NumberFormatException e) {
                        // ignora linhas inválidas
                    }
                }

                linha = leitor.readLine();
            }

        } catch (IOException e) {
            System.out.println("Erro ao ler arquivo " + caminhoArquivoCsv + ": " + e.getMessage());
        }

        return listaNumeros;
    }

    // ============================================================
    // ============ CONVERTE NewArrayList → int[] =================
    // ============================================================
    /**
     * Converte a NewArrayList personalizada em um vetor simples de int,
     * para ser usado pelos algoritmos de ordenação.
     */
    public static int[] converterParaArray(NewArrayList<Integer> lista) {

        int tamanho = lista.getSize();
        int[] vetorResultado = new int[tamanho];

        for (int i = 0; i < tamanho; i++) {
            vetorResultado[i] = lista.getElement(i);
        }

        return vetorResultado;
    }

    // ============================================================
    // ===================== COPIAR VETOR =========================
    // ============================================================
    /**
     * Cria uma cópia profunda de um vetor para evitar que um algoritmo
     * influencie os resultados do outro.
     */
    public static int[] copiarVetor(int[] vetorOriginal) {

        int[] vetorCopia = new int[vetorOriginal.length];

        for (int i = 0; i < vetorOriginal.length; i++) {
            vetorCopia[i] = vetorOriginal[i];
        }

        return vetorCopia;
    }

    // ============================================================
    // ========================= MAIN ==============================
    // ============================================================
    public static void main(String[] args) {

        // Instância da classe que contém os algoritmos
        Sorts sorts = new Sorts();

        // Arquivos exigidos pelo TDE
        String[] arquivosDeEntrada = {
                "aleatorio_100.csv", "aleatorio_1000.csv", "aleatorio_10000.csv",
                "crescente_100.csv", "crescente_1000.csv", "crescente_10000.csv",
                "decrescente_100.csv", "decrescente_1000.csv", "decrescente_10000.csv"
        };

        // Cabeçalho da tabela de resultados
        System.out.println("Arquivo\t\t\tBubble(ms)\tInsertion(ms)\tQuick(ms)");
        System.out.println("--------------------------------------------------------------------------");

        // Loop para processar cada arquivo
        for (String nomeArquivo : arquivosDeEntrada) {

            // 1) Lê o CSV
            NewArrayList<Integer> listaNumeros = lerCSVParaLista(nomeArquivo);

            // 2) Converte para vetor int padrão
            int[] vetorOriginal = converterParaArray(listaNumeros);

            if (vetorOriginal.length == 0) {
                System.out.println(nomeArquivo + "\tARQUIVO VAZIO/INVÁLIDO");
                continue;
            }

            // ============================================================
            // ======================= BUBBLE SORT =========================
            // ============================================================
            int[] vetorBubble = copiarVetor(vetorOriginal);
            long inicio = System.nanoTime();
            sorts.bubbleSort(vetorBubble);
            long fim = System.nanoTime();
            double tempoBubbleMs = (fim - inicio) / 1_000_000.0;

            // ============================================================
            // ===================== INSERTION SORT ========================
            // ============================================================
            int[] vetorInsertion = copiarVetor(vetorOriginal);
            inicio = System.nanoTime();
            sorts.insertionSort(vetorInsertion);
            fim = System.nanoTime();
            double tempoInsertionMs = (fim - inicio) / 1_000_000.0;

            // ============================================================
            // ======================= QUICK SORT ==========================
            // ============================================================
            int[] vetorQuick = copiarVetor(vetorOriginal);
            inicio = System.nanoTime();
            sorts.quickSort(vetorQuick);
            fim = System.nanoTime();
            double tempoQuickMs = (fim - inicio) / 1_000_000.0;

            // ============================================================
            // ================== IMPRIME RESULTADOS =======================
            // ============================================================
            System.out.println(
                    nomeArquivo + "\t" +
                            String.format("%.3f", tempoBubbleMs) + "\t\t" +
                            String.format("%.3f", tempoInsertionMs) + "\t\t" +
                            String.format("%.3f", tempoQuickMs)
            );
        }
    }
}
