package br.com.unisinos.arvoreavl.arvore;

import java.util.ArrayList;
import java.util.List;

public class ArvoreAvl {

    /** Nó raíz da árvore */
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

    /**
     * Insere um nó na árvore
     *
     * @param valor Valor do nó
     */
    public void inserir(int valor) {
        // Se já existe um nó com esse valor
        if (busca(valor) != null) {
            return;
        }
        inserir(this.raiz, valor);
    }

    /**
     * Insere um novo nó na árvore
     *
     * @param noComparacao Nó para comparação de valor
     * @param valor Valor do nó
     */
    private void inserir(No noComparacao, int valor) {
        //Se for o primeiro vai criar uma raiz
        if (this.raiz == null) {
            this.raiz = new No(valor);
        } else {
            // Se o valor for menor que o valor do nó de comparação
            if (valor < noComparacao.getValor()) {
                inserirNoAEsquerda(noComparacao, valor);
            } else {
                inserirNoADireita(noComparacao, valor);
            }
        }
    }

    /**
     * Insere um novo nó à esquerda do nó de comparação
     *
     * @param noComparacao Nó de comparação
     * @param valor Valor
     */
    private void inserirNoAEsquerda(No noComparacao, int valor) {
        //Se tiver elemento no nó esquerdo, entra nesse nó para continuar a inserção
        if (noComparacao.getNoEsquerda() != null) {
            inserir(noComparacao.getNoEsquerda(), valor);
        } else {
            //Se nó esquerdo estiver vazio, insere o novo nó
            noComparacao.setNoEsquerda(criaNovoNo(noComparacao, valor));
        }
    }

    /**
     * Insere um novo nó à direita do nó de comparação
     *
     * @param noComparacao Nó de comparação
     * @param valor Valor
     */
    private void inserirNoADireita(No noComparacao, int valor) {
        //Se tiver elemento no nó direito, entra nesse nó para continuar a inserção
        if (noComparacao.getNoDireita() != null) {
            inserir(noComparacao.getNoDireita(), valor);
        } else {
            //Se nodo direito vazio insere o novo nó
            noComparacao.setNoDireita(criaNovoNo(noComparacao, valor));
        }
    }

    /**
     * Cria um novo nó
     * 
     * @param noPai Nó pai
     * @param valor Valor do nó
     * @return No
     */
    private No criaNovoNo(No noPai, int valor) {
        No no = new No(valor);
        no.setNoPai(noPai);
        return no;
    }

}
