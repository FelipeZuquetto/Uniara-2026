
public class Main {

    public static void main(String[] args) {
        No raiz = null;

        int[] valores = {50, 17, 72, 12, 23, 54, 76, 9, 14, 19, 67};
        int n = valores.length;

        for (int i = 0; i < n; i++) {
            System.out.printf("%nInserindo: %d...", valores[i]);
            raiz = ArvoreAVL.inserir(raiz, valores[i]);
        }

        System.out.print("\n\n--- ESTRUTURA DA ARVORE AVL (DEITADA) ---\n");
        ArvoreAVL.imprimirArvore(raiz, 0);
        System.out.print("\n-----------------------------------------\n");

        int[] remover = {9, 14, 12};
        for (int v : remover) {
            System.out.printf("%nRemovendo: %d...", v);
            raiz = ArvoreAVL.remover(raiz, v);
        }

        System.out.print("\n\n--- ARVORE APOS AS REMOCOES ---\n");
        ArvoreAVL.imprimirArvore(raiz, 0);
        System.out.print("\n-----------------------------------------\n");
    }
}
