
public class ArvoreAVL {

    public static No novoNo(int valor) {
        No no = new No(valor);
        no.esq = null;
        no.dir = null;
        no.altura = 1;
        return no;
    }

    public static int obterAltura(No n) {
        if (n == null) {
            return 0; // Um nó inexistente tem altura 0
        }
        return n.altura;
    }

    public static int max(int a, int b) {
        return (a > b) ? a : b;
    }

    public static int obterFatorEquilibrio(No n) {
        if (n == null) {
            return 0;
        }
        return obterAltura(n.esq) - obterAltura(n.dir);
    }

    public static int obterFB(No n) {
        return obterFatorEquilibrio(n);
    }

    public static No rotacionarDireita(No y) {
        No x = y.esq;
        No T2 = x.dir;

        // Executa a rotação
        x.dir = y;
        y.esq = T2;

        // Atualiza as alturas (a ordem importa: o que desceu primeiro)
        y.altura = max(obterAltura(y.esq), obterAltura(y.dir)) + 1;
        x.altura = max(obterAltura(x.esq), obterAltura(x.dir)) + 1;

        // Retorna a nova raiz
        return x;
    }

    public static No rotacionarEsquerda(No x) {
        No y = x.dir;
        No T2 = y.esq;

        // Executa a rotação
        y.esq = x;
        x.dir = T2;

        // Atualiza as alturas (sempre do nó que ficou mais baixo para o mais alto)
        x.altura = max(obterAltura(x.esq), obterAltura(x.dir)) + 1;
        y.altura = max(obterAltura(y.esq), obterAltura(y.dir)) + 1;

        // Retorna a nova raiz da subárvore
        return y;
    }

    public static No inserir(No no, int valor) {
        // 1. Inserção BST padrão
        if (no == null) return novoNo(valor);

        if (valor < no.valor)
            no.esq = inserir(no.esq, valor);
        else if (valor > no.valor)
            no.dir = inserir(no.dir, valor);
        else
            return no; // Valores iguais não permitidos

        // 2. Atualiza altura do nó atual
        no.altura = 1 + max(obterAltura(no.esq), obterAltura(no.dir));

        // 3. Verifica o equilíbrio
        int fb = obterFB(no);

        // Caso LL (Simples Direita)
        if (fb > 1 && valor < no.esq.valor)
            return rotacionarDireita(no);

        // Caso RR (Simples Esquerda)
        if (fb < -1 && valor > no.dir.valor)
            return rotacionarEsquerda(no);

        // Caso LR (Dupla Direita)
        if (fb > 1 && valor > no.esq.valor) {
            no.esq = rotacionarEsquerda(no.esq);
            return rotacionarDireita(no);
        }

        // Caso RL (Dupla Esquerda)
        if (fb < -1 && valor < no.dir.valor) {
            no.dir = rotacionarDireita(no.dir);
            return rotacionarEsquerda(no);
        }

        return no;
    }

    public static No remover(No raiz, int chave) {
        // PASSO 1: Remoção padrão de BST
        if (raiz == null) return raiz;

        if (chave < raiz.valor)
            raiz.esq = remover(raiz.esq, chave);
        else if (chave > raiz.valor)
            raiz.dir = remover(raiz.dir, chave);
        else {
            // Nó com um filho ou nenhum
            if ((raiz.esq == null) || (raiz.dir == null)) {
                No temp = (raiz.esq != null) ? raiz.esq : raiz.dir;

                if (temp == null) { // Caso sem filhos
                    raiz = null;
                } else {            // Caso com um filho: o filho ocupa o lugar do nó
                    raiz = temp;
                }
                // Em Java não há free(): o Garbage Collector libera o nó removido
            } else {
                // Nó com dois filhos: pega o sucessor (menor da subárvore direita)
                No temp = raiz.dir;
                while (temp.esq != null) temp = temp.esq;

                raiz.valor = temp.valor;                       // Copia o valor do sucessor
                raiz.dir = remover(raiz.dir, temp.valor);      // Deleta o sucessor
            }
        }

        // Se a árvore tinha apenas um nó, retorna
        if (raiz == null) return raiz;

        // PASSO 2: Atualizar altura do nó atual
        raiz.altura = 1 + max(obterAltura(raiz.esq), obterAltura(raiz.dir));

        // PASSO 3: Verificar e corrigir o equilíbrio
        int fb = obterFB(raiz);

        // Caso Left Left (LL)
        if (fb > 1 && obterFB(raiz.esq) >= 0)
            return rotacionarDireita(raiz);

        // Caso Left Right (LR)
        if (fb > 1 && obterFB(raiz.esq) < 0) {
            raiz.esq = rotacionarEsquerda(raiz.esq);
            return rotacionarDireita(raiz);
        }

        // Caso Right Right (RR)
        if (fb < -1 && obterFB(raiz.dir) <= 0)
            return rotacionarEsquerda(raiz);

        // Caso Right Left (RL)
        if (fb < -1 && obterFB(raiz.dir) > 0) {
            raiz.dir = rotacionarDireita(raiz.dir);
            return rotacionarEsquerda(raiz);
        }

        return raiz;
    }

    public static void imprimirArvore(No raiz, int espaco) {
        // Caso base
        if (raiz == null)
            return;

        // Aumenta a distância entre os níveis
        espaco += 10;

        // Primeiro, imprime o filho da DIREITA (fica no topo do terminal)
        imprimirArvore(raiz.dir, espaco);

        // Imprime o nó atual após os espaços necessários
        System.out.print("\n");
        for (int i = 10; i < espaco; i++)
            System.out.print(" ");

        // Mostra o valor, a altura e o Fator de Equilíbrio (FB) para conferência
        int fb = obterFB(raiz);
        System.out.printf("[%d](H:%d)(fb:%d)%n", raiz.valor, raiz.altura, fb);

        // Por fim, imprime o filho da ESQUERDA (fica na parte de baixo)
        imprimirArvore(raiz.esq, espaco);
    }
}
