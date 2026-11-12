/**
 * Nó da árvore AVL (equivalente ao "struct No" da aula).
 * Além do valor e dos filhos, guarda a altura do nó.
 */
public class No {

    int valor;
    No esq;
    No dir;
    int altura;

    public No(int valor) {
        this.valor = valor;
    }
}
