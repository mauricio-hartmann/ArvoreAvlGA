package br.com.unisinos.arvoreavl.arvore;

public class ArvoreAvl {

    private No raiz;

    //verifica altura da arvore, necessario para o balanceamento
    private int altura(No raiz) {
        if (raiz == null) {
            return -1;
        }

        if (raiz.getNoEsquerda() == null && raiz.getNoDireita() == null) {
            return 0;

        } else if (raiz.getNoEsquerda() == null) {
            return 1 + altura(raiz.getNoDireita());

        } else if (raiz.getNoDireita() == null) {
            return 1 + altura(raiz.getNoEsquerda());

        } else {
            //retorna 1 + o maior numero da comparação.
            return 1 + Math.max(altura(raiz.getNoEsquerda()), altura(raiz.getNoDireita()));
        }
    }
    //fim altura

    //balanco de ArvoreAVL = altura(arvore a esq) - altura(arvore a dir)
    private void setBalanceamento(No no) {
        no.setFatorBalanceamento(altura(no.getNoDireita()) - altura(no.getNoEsquerda()));
    }

    //Parte de inserir
    public void inserir(int valor) {
        inserir(this.raiz, valor);
    }

    public void inserir(No no, int valor) {
        //Se for o primeiro vai criar uma raiz.
        if (this.raiz == null) {
            this.raiz = new No(valor);
        } else {
            if (valor < no.getValor()) {
                inserirNoAEsquerda(no, valor);
            } else {
                inserirNoADireita(no, valor);
            }
        }
    }
    //Fim parte inserir

    private void inserirNoAEsquerda(No no, int valor) {
        //Se tiver elemento no no esquedo, entra nesse nó para continuar a inserção
        if (no.getNoEsquerda() != null) {
            inserir(no.getNoEsquerda(), valor);
        } else {
            //Se nó esquerdo estiver vazio, insere o novo no aqui 
            no.setNoEsquerda(new No(valor));
        }
    }

    private void inserirNoADireita(No no, int valor) {
        //Se tiver elemento no no direito, entra nesse nó para continuar a inserção
        if (no.getNoDireita() != null) {
            inserir(no.getNoDireita(), valor);
        } else {
            //Se nodo direito vazio insere o novo no aqui 
            no.setNoDireita(new No(valor));
        }
    }

}
