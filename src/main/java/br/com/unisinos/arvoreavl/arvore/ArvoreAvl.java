package br.com.unisinos.arvoreavl.arvore;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe que representa uma árvore AVL e suas operações
 */
public class ArvoreAvl {

    /** Valor de balanceamento -2 */
    private static final int BALACEAMENTO_MINUS_DOIS = -2;
    /** Valor de balanceamento 2 */
    private static final int BALACEAMENTO_DOIS = 2;

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
     * Retorna se a árvore está vazia
     *
     * @return Boolean
     */
    public boolean isArvoreVazia() {
        return raiz == null;
    }

    /**
     * Busca um nó na árvore
     *
     * @param valor Valor do nó
     * @return No
     */
    public No busca(int valor) {
        // Limpa a lista de nós percorridos
        listaNosPercorridosBusca.clear();
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
        if (isArvoreVazia()) {
            this.raiz = new No(valor);
        } else {
            // Se o valor for menor que o valor do nó de comparação
            if (valor < noComparacao.getValor()) {
                inserirNoAEsquerda(noComparacao, valor);
            } else {
                inserirNoADireita(noComparacao, valor);
            }
            ajustaBalanceamento(noComparacao);
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
        if (noComparacao.isPossuiFilhoEsquerda()) {
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
        if (noComparacao.isPossuiFilhoDireita()) {
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
     * Ajusta o balanceamento da árvore recursivamente até a raíz
     *
     * @param no Nó a ser ajustado
     */
    private void ajustaBalanceamento(No no) {
        // Define o fator de balanceamento do nó
        int fatorBalanceamento = NoUtils.getFatorbalanceamento(no);
        // Recupera os nós filhos da esquerda e direita
        No noEsquerda = no.getNoEsquerda();
        No noDireita = no.getNoDireita();
        // Verificar o fator de balanceamento e realiza as rotações
        if (fatorBalanceamento == BALACEAMENTO_MINUS_DOIS) {
            // Se a altura da esquerda foi maior ou igual a altura da direita
            if (NoUtils.getAlturaNo(noEsquerda.getNoEsquerda())
                    >= NoUtils.getAlturaNo(noEsquerda.getNoDireita())) {
                no = NoUtils.rotacaoSimplesDireita(no);
            } else {
                no = NoUtils.rotacaoDuplaEsquerda(no);
            }
        } else if (fatorBalanceamento == BALACEAMENTO_DOIS) {
            // Se a altura da direita for maior ou igual a altura da esquerda
            if (NoUtils.getAlturaNo(noDireita.getNoDireita())
                    >= NoUtils.getAlturaNo(noDireita.getNoEsquerda())) {
                no = NoUtils.rotacaoSimplesEsquerda(no);
            } else {
                no = NoUtils.rotacaoDuplaDireita(no);
            }
        }
        // Se possui um nó pai, ajusta o balanceamento do nó pai recursivamente até a raíz
        if (no.getNoPai() != null) {
            ajustaBalanceamento(no.getNoPai());
        } else {
            raiz = no;
        }
        no.setAltura(NoUtils.getAlturaNo(no));
    }

    /**
     * Remove um nó da árvore
     *
     * @param valor Valor do nó
     * @return No
     */
    public No excluir(int valor) {
        return excluir(raiz, valor);
    }

    private No excluir(No no, int valor) {
        No noExcluido = null;
        // Se o nó não existe, retorna null
        if (no == null) {
            return noExcluido;
        }
        // Entra no nó especifico de acordo com o valor
        if (no.getValor() > valor) {
            noExcluido = excluir(no.getNoEsquerda(), valor);
        } else if (no.getValor() < valor) {
            noExcluido = excluir(no.getNoDireita(), valor);
        } else if (no.getValor() == valor) {
            // Se o nó não possui filhos a esquerda ou a direita, significando que possui apenas 1 filho
            if (!no.isPossuiFilhoEsquerda() || !no.isPossuiFilhoDireita()) {
                noExcluido = no;
                // Se o nó não possui nó pai, é a raiz. Apaga a raiz
                if (no.getNoPai() == null) {
                    this.raiz = null;
                    return noExcluido;
                }
            } else {
                noExcluido = getNoSubstituto(no);
            }
            No noTemp = noExcluido.isPossuiFilhoEsquerda() ? noExcluido.getNoEsquerda()
                    : noExcluido.getNoDireita();
            // Se possui um nó temporário para troca de dados
            if (noTemp != null) {
                noTemp.setNoPai(noExcluido.getNoPai());
            }
            // Se o nó a ser excluído possui nó pai
            if (noExcluido.getNoPai() == null) {
                this.raiz = noTemp;
            } else {
                if (noExcluido == noExcluido.getNoPai().getNoEsquerda()) {
                    noExcluido.getNoPai().setNoEsquerda(noTemp);
                } else {
                    noExcluido.getNoPai().setNoDireita(noTemp);
                }
                ajustaBalanceamento(noExcluido.getNoPai());
            }
        }
        return noExcluido;
    }

    /**
     * Recupera o nó que irá substituir o nó excluído
     * 
     * @param no Nó
     * @return No
     */
    public No getNoSubstituto(No no) {
        // Se o nó possui filho a direita
        if (no.isPossuiFilhoDireita()) {
            // Vai para o nó direito então tenta recuperar o nó mais a esquerda desse nó
            No noTemp = no.getNoDireita();
            while (noTemp.isPossuiFilhoEsquerda()) {
                noTemp = noTemp.getNoEsquerda();
            }
            return noTemp;
        } else {
            // Retorna o nó pai
            No noPai = no.getNoPai();
            while (noPai != null && no == noPai.getNoDireita()) {
                no = noPai;
                noPai = no.getNoPai();
            }
            return noPai;
        }
    }

}
