package br.com.unisinos.arvoreavl.arvore;

import java.util.ArrayList;
import java.util.List;

public class ArvoreAvl {

    /** Nós raíz da árvore */
    private No raiz;
    /** Lista de nós percorridos durante a busca de um nó */
    private final List<No> listaNosPercorridosBusca;

    /**
     * Método construtor
     */
    public ArvoreAvl() {
        this.listaNosPercorridosBusca = new ArrayList();
    }

    /**
     * Retorna a lista de nós percorridos durante a busca
     *
     * @return {@code List<No>}
     */
    public List<No> getListaNosPercorridosBusca() {
        return listaNosPercorridosBusca;
    }

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

    /**
     * Busca um nó na árvore
     *
     * @param valor Valor do nó
     * @return No
     */
    public No busca(int valor) {
        // Percorre os nós da árvore, começando pela raíz
        No noAtual = raiz;
        while (noAtual != null) {
            listaNosPercorridosBusca.add(noAtual);
            // Se o valor do nó pesquisado for igual ao valor procurado
            if (noAtual.getValor() == valor) {
                break;
            }
            // Define o próximo nó da pesquisa de acordo com o valor
            noAtual = noAtual.getValor() < valor ? noAtual.getNoDireita()
                    : noAtual.getNoEsquerda();
        }
        return noAtual;
    }

    //Parte de inserir
    public void inserir(int valor) {
        if (busca(valor) != null) {
            return;
        }
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
