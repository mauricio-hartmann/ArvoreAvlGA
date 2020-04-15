package br.com.unisinos.arvoreavl.arvore;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe que representa uma árvore AVL e suas operações
 */
public class ArvoreAvl implements ArvoreAvlConstantes {

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
            verificaFatorBalanceamento(noComparacao);
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

    /**
     * Verifica o fator de balancemanto de um nó
     *
     * @param no Nó a verificar
     */
    private void verificaFatorBalanceamento(No no) {
        // Define o fator de balanceamento do nó
        setBalanceamentoNo(no);
        int balanceamento = no.getFatorBalanceamento();
        // Recupera os nós filhos da esquerda e direita
        No noEsquerda = no.getNoEsquerda();
        No noDireita = no.getNoDireita();
        if (balanceamento == BALACEAMENTO_MINUS_DOIS) {
            if (getAlturaNo(noEsquerda.getNoEsquerda()) >= getAlturaNo(noEsquerda.getNoDireita())) {
//                no = rotacaoDireita(no);
            } else {
//                no = duplaRotacaoEsquerdaDireita(no);
            }
        } else if (balanceamento == BALACEAMENTO_DOIS) {
            if (getAlturaNo(noDireita.getNoDireita()) >= getAlturaNo(noDireita.getNoEsquerda())) {
//                no = rotacaoEsquerda(no);
            } else {
//                no = duplaRotacaoDireitaEsquerda(no);
            }
        }
        if (no.getNoPai() != null) {
            verificaFatorBalanceamento(no.getNoPai());
        } else {
            raiz = no;
        }
    }

    /**
     * Define o fator de balanceamento de um nó
     *
     * @param no Nó
     */
    private void setBalanceamentoNo(No no) {
        no.setFatorBalanceamento(getAlturaNo(no.getNoDireita()) - getAlturaNo(no.getNoEsquerda()));
    }

    /**
     * Retorna a altura de um nó
     *
     * @param no Nó
     * @return int
     */
    private int getAlturaNo(No no) {
        // Se o nó estiver vazio (árvore vazia)
        if (no == null) {
            return ALTURA_ARVORE_VAZIA;
        } else if (no.getNoEsquerda() == null && no.getNoDireita() == null) {
            // Se o nó não possui filhos
            return ALTURA_NO_SEM_FILHOS;
        } else if (no.getNoEsquerda() == null) {
            // Se não possui nó à esquerda, retorna a altura do nó à direita
            return ALTURA_BASE + getAlturaNo(no.getNoDireita());
        } else if (no.getNoDireita() == null) {
            // Se não possui nó à direita, retorna a altura do nó à esquerda
            return ALTURA_BASE + getAlturaNo(no.getNoEsquerda());
        } else {
            //retorna 1 + o maior número da comparação entre a altura da esquerda e direita.
            return ALTURA_BASE + Math.max(getAlturaNo(no.getNoEsquerda()),
                    getAlturaNo(no.getNoDireita()));
        }
    }

}
