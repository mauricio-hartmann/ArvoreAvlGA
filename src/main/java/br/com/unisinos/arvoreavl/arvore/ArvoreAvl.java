package br.com.unisinos.arvoreavl.arvore;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe que representa uma árvore AVL e suas operações
 * @author Marcello Augusto Gava 
 * @author Mauricio Hartmann
 */
public class ArvoreAvl {

    /** Patter para adicionar valores ao builder de ordem de percurso */
    private static final String PATTERN_ORDER_STRING = "%s ";

    /** Nó raíz da árvore */
    private No raiz;
    /** Lista de nós percorridos durante a busca de um nó */
    private final List<No> listaNosPercorridosBusca;
    /** Builder responsável por montar a string de ordem de percurso */
    private final StringBuilder orderBuilder;

    /**
     * Método construtor
     */
    public ArvoreAvl() {
        this.listaNosPercorridosBusca = new ArrayList();
        this.orderBuilder = new StringBuilder();
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
        // Se a raíz não possui valor, insere na raíz
        if (raiz == null) {
            raiz = new No(valor, null);
            return;
        }
        // Se o valor for menor que o valor do nó de comparação, vai para esquerda
        if (valor < noComparacao.getValor()) {
            // Caso o nó já possua um filho a esquerda, caminha para dentro desse filho
            if (noComparacao.getNoEsquerda() != null) {
                inserir(noComparacao.getNoEsquerda(), valor);
            } else {
                // Insere como filho a esquerda
                noComparacao.setNoEsquerda(new No(valor, noComparacao));
            }
        } else if (valor > noComparacao.getValor()) {
            // Caso o nó já possua um filho a esquerda, caminha para dentro desse filho
            if (noComparacao.getNoDireita() != null) {
                inserir(noComparacao.getNoDireita(), valor);
            } else {
                noComparacao.setNoDireita(new No(valor, noComparacao));
            }
        }
        // Ajsuta o balanceamento da árvore e a altura do nó
        ajustaBalanceamento(noComparacao);
        noComparacao.setAltura(NoUtils.getAlturaNo(noComparacao));
    }

    /**
     * Remove um nó da árvore
     *
     * @param valor Valor do nó
     * @return No
     */
    public No excluir(int valor) {
        // Verifica se o nó existe antes de fazer a exclusão
        No noExcluir = busca(valor);
        // Caso o nó não exista retorna null
        if (noExcluir == null) {
            return noExcluir;
        }
        // Exclui o nó, rebalanceia a árvore e retorna o nó
        noExcluir = excluir(noExcluir);
        ajustaBalanceamento(noExcluir.getNoPai());
        return noExcluir;
    }

    /**
     * Exclui um nó da árvore
     *
     * @param no Nó
     * @return No
     */
    private No excluir(No no) {
        // Se o nó é uma folha, remove a vinculação com o nó pai
        if (no.isFolha()) {
            // Remove da esquerda ou direita, de acordo com a posição do nó
            if (no.isFilhoEsquerda()) {
                no.getNoPai().setNoEsquerda(null);
            } else {
                no.getNoPai().setNoDireita(null);
            }
        } else if (no.getNoEsquerda() == null || no.getNoDireita() == null) {
            // Caso o nó possua 1 filho a esquerda ou a direita, recupera o filho
            No noFilhoExcluido = no.isPossuiFilhoDireita() ? no.getNoDireita() : no.getNoEsquerda();
            // Substitui a posição do nó pelo filho no nó pai
            if (no.isFilhoEsquerda()) {
                no.getNoPai().setNoEsquerda(noFilhoExcluido);
            } else {
                no.getNoPai().setNoDireita(noFilhoExcluido);
            }
        } else {
            // Recupera o nó esquerdo
            No noTemp = no.getNoEsquerda();
            // Desce a árvore sempre pelo lado direito para encontrar o substituto
            while (noTemp.getNoDireita() != null) {
                noTemp = noTemp.getNoDireita();
            }
            no.setValor(noTemp.getValor());
            no = excluir(noTemp);
        }
        no.getNoPai().setAltura(NoUtils.getAlturaNo(no.getNoPai()));
        return no;
    }

    /**
     * Ajusta o balanceamento da árvore recursivamente
     *
     * @param no Nó a ser ajustado
     */
    private void ajustaBalanceamento(No no) {
        // Calcula o fator de balancemento da árvore (-1, 0, 1, ...)
        int fatorBalanceamento = NoUtils.getFatorbalanceamento(no);
        No noPai = no.getNoPai();
        // Se o fator de balanceamento for menor que -1, rotaciona a esquerda
        if (fatorBalanceamento < -1) {
            // Realiza rotação simples ou dupla, de acordo com a necessidade
            if (NoUtils.getAlturaNo(no.getNoEsquerda().getNoEsquerda())
                    >= NoUtils.getAlturaNo(no.getNoEsquerda().getNoDireita())) {
                rotacaoSimplesDireita(no);
            } else {
                rotacaoDuplaEsquerda(no);
            }
        } else if (fatorBalanceamento > 1) {
            // Realiza rotação simples ou dupla, de acordo com a necessidade
            if (NoUtils.getAlturaNo(no.getNoDireita().getNoDireita())
                    >= NoUtils.getAlturaNo(no.getNoDireita().getNoEsquerda())) {
                rotacaoSimplesEsquerda(no);
            } else {
                rotacaoDuplaDireita(no);
            }
        }
        // Se o nó possui pai, faz o rebalanceamento no pai
        if (noPai != null) {
            ajustaBalanceamento(noPai);
        }
        // Ajusta a altura do nó
        no.setAltura(NoUtils.getAlturaNo(no));
    }

    /**
     * Realiza a rotação simples a direita
     *
     * @param noRotacionado Nó pivo da rotação
     */
    private void rotacaoSimplesDireita(No noRotacionado) {
        // Recupera o nó pai, o nó esquerdo e o nó a direita do nó esquerdo
        No noPai = noRotacionado.getNoPai();
        No noEsquerdo = noRotacionado.getNoEsquerda();
        No filhoDireitoDoFilhoEsquerdo = noEsquerdo.getNoDireita();
        noRotacionado.setNoEsquerda(filhoDireitoDoFilhoEsquerdo);
        noEsquerdo.setNoDireita(noRotacionado);
        // Se for a raíz, atualiza a raíz
        if (noPai == null) {
            this.raiz = noEsquerdo;
            noEsquerdo.setNoPai(null);
            return;
        }
        // Substitui o nó esquerdo caso seja o nó rotacionado
        if (noPai.getNoEsquerda() == noRotacionado) {
            noPai.setNoEsquerda(noEsquerdo);
        } else {
            noPai.setNoDireita(noEsquerdo);
        }
        // Ajusta a altura dos nós
        noRotacionado.setAltura(NoUtils.getAlturaNo(noRotacionado));
        noEsquerdo.setAltura(NoUtils.getAlturaNo(noEsquerdo));
    }

    /**
     * Realiza a rotação simples a direita
     *
     * @param noRotacionado Nó pivo da rotação
     */
    private void rotacaoSimplesEsquerda(No noRotacionado) {
        // Recupera o nó pai, o nó direito e o nó a esquerda do nó direito
        No noPai = noRotacionado.getNoPai();
        No noDireito = noRotacionado.getNoDireita();
        No filhoEsquerdoDoFilhoDireito = noDireito.getNoEsquerda();
        noRotacionado.setNoDireita(filhoEsquerdoDoFilhoDireito);
        noDireito.setNoEsquerda(noRotacionado);
        // Se for a raíz, atualiza a raíz
        if (noPai == null) {
            this.raiz = noDireito;
            noDireito.setNoPai(null);
            return;
        }
        // Substitui o nó esquerdo caso seja o nó rotacionado
        if (noPai.getNoEsquerda() == noRotacionado) {
            noPai.setNoEsquerda(noDireito);
        } else {
            noPai.setNoDireita(noDireito);
        }
        // Ajusta a altura dos nós
        noRotacionado.setAltura(NoUtils.getAlturaNo(noRotacionado));
        noDireito.setAltura(NoUtils.getAlturaNo(noDireito));
    }

    /**
     * Realiza a rotação dupla a esquerda
     *
     * @param no Nó
     */
    private void rotacaoDuplaEsquerda(No no) {
        rotacaoSimplesEsquerda(no.getNoEsquerda());
        rotacaoSimplesDireita(no);
    }

    /**
     * Realiza a rotação dupla a direita
     *
     * @param no Nó
     */
    private void rotacaoDuplaDireita(No no) {
        rotacaoSimplesDireita(no.getNoDireita());
        rotacaoSimplesEsquerda(no);
    }

    /**
     * Imprime os nós da árvore em pré-ordem
     */
    public void printPreOrdem() {
        montaStringPreOrdem(raiz);
        System.out.println(formataStringOrder());
    }

    /**
     * Imprime os nós da árvore em pré-ordem
     *
     * @param no Nó
     */
    private void montaStringPreOrdem(No no) {
        if (no != null) {
            addNoToOrderBuilder(no);
            montaStringPreOrdem(no.getNoEsquerda());
            montaStringPreOrdem(no.getNoDireita());
        }
    }

    /**
     * Imprime os nós da árvore em ordem
     */
    public void printEmOrdem() {
        montaStringEmOrdem(raiz);
        System.out.println(formataStringOrder());
    }

    /**
     * Imprime os nós da árvore em ordem
     *
     * @param no Nó
     */
    private void montaStringEmOrdem(No no) {
        if (no != null) {
            montaStringEmOrdem(no.getNoEsquerda());
            addNoToOrderBuilder(no);
            montaStringEmOrdem(no.getNoDireita());
        }
    }

    /**
     * Imprime os nós da árvore em ordem
     */
    public void printPosOrdem() {
        montaStringPosOrdem(raiz);
        System.out.println(formataStringOrder());
    }

    /**
     * Imprime os nós da árvore em ordem
     *
     * @param no Nó
     */
    private void montaStringPosOrdem(No no) {
        if (no != null) {
            montaStringPosOrdem(no.getNoEsquerda());
            montaStringPosOrdem(no.getNoDireita());
            addNoToOrderBuilder(no);
        }
    }

    /**
     * Adiciona um nó ao builder responsável por construir a string de ordem de
     * percurso
     *
     * @param no Nó
     */
    private void addNoToOrderBuilder(No no) {
        orderBuilder.append(String.format(PATTERN_ORDER_STRING, no));
    }

    /**
     * Formata a string de saída das ordens de percurso
     *
     * @return
     */
    private String formataStringOrder() {
        // Cria uma string removendo os espaços no final e substituindo os espaços
        // entre os caracteres por ", "
        String orderResultado = orderBuilder.toString().replaceFirst("\\s++$", "")
                .replaceAll(" ", ", ");
        // Limpa o builder
        orderBuilder.delete(0, orderBuilder.toString().length());
        return orderResultado;
    }

    /**
     * Imprime a árvore
     */
    public void printArvore() {
        int alturaArvore = NoUtils.getAlturaNo(raiz) + 1;
        for (int i = 1; i <= alturaArvore; i++) {
            printNivel(raiz, i);
            System.out.println();
        }
    }

    /**
     * Impríme os níveis da árvore
     *
     * @param no Nó
     * @param nivel Nível
     */
    private void printNivel(No no, int nivel) {
        // Se o nó for nulo, interrompe a execução
        if (no == null) {
            return;
        }
        // Imprime os níveis da árvore
        if (nivel == 1) {
            System.out.print(String.format("|%s| ", no));
        } else if (nivel > 1) {
            printNivel(no.getNoEsquerda(), nivel - 1);
            printNivel(no.getNoDireita(), nivel - 1);
        }
    }

}
