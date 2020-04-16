package br.com.unisinos.arvoreavl.arvore;

/**
 * Classe com funções úteis para os nós
 */
public class NoUtils {

    /** Altura de uma árvore vazia */
    private static final int ALTURA_ARVORE_VAZIA = -1;
    /** Altura de um nó que não possui nós filhos */
    private static final int ALTURA_NO_SEM_FILHOS = 0;
    /** Altura base de um nó com filhos */
    private static final int ALTURA_BASE = 1;

    /**
     * Retorna a altura de um nó
     *
     * @param no Nó
     * @return int
     */
    public static int getAlturaNo(No no) {
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

    /**
     * Retorna o fator de balancemanto de um nó
     *
     * @param no Nó
     * @return int
     */
    public static int getFatorbalanceamento(No no) {
        return getAlturaNo(no.getNoDireita()) - getAlturaNo(no.getNoEsquerda());
    }

    /**
     * Realiza a rotação simples a esquerda
     *
     * @param noRotacionado Nó
     * @return No
     */
    public static No rotacaoSimplesEsquerda(No noRotacionado) {
        // Recupera o nó da direita e o coloca no lugar do nó atual,alterando
        // a raíz dessa parte da árvore
        No noDireito = noRotacionado.getNoDireita();
        noDireito.setNoPai(noRotacionado.getNoPai());
        // Coloca o nó da esquerda do raíz atual como nó direito do nó rotacionado
        noRotacionado.setNoDireita(noDireito.getNoEsquerda());
        // Coloca o nó rotacionado como filho a esquerda da nova raíz
        noDireito.setNoEsquerda(noRotacionado);
        noRotacionado.setNoPai(noDireito);
        // Se a noza raíz possui nó pai
        if (noDireito.getNoPai() != null) {
            // Atualiza a vinculação no nó pai
            if (noDireito.getNoPai().getNoDireita() == noRotacionado) {
                noDireito.getNoPai().setNoDireita(noDireito);
            } else if (noDireito.getNoPai().getNoEsquerda() == noRotacionado) {
                noDireito.getNoPai().setNoEsquerda(noDireito);
            }
        }
        // Atualiza a altura dos nós
        noRotacionado.setAltura(NoUtils.getAlturaNo(noRotacionado));
        noDireito.setAltura(NoUtils.getAlturaNo(noDireito));
        return noDireito;
    }

    /**
     * Realiza a rotação simples a direita
     *
     * @param noRotacionado Nó
     * @return No
     */
    public static No rotacaoSimplesDireita(No noRotacionado) {
        // Recupera o nó da esquerda e o coloca no lugar do nó atual,alterando
        // a raíz dessa parte da árvore
        No noEsquerdo = noRotacionado.getNoEsquerda();
        noEsquerdo.setNoPai(noRotacionado.getNoPai());
        // Coloca o nó da direita do raíz atual como nó esquerdo do nó rotacionado
        noRotacionado.setNoEsquerda(noEsquerdo.getNoDireita());
        // Coloca o nó rotacionado como filho a direita da nova raíz
        noEsquerdo.setNoDireita(noRotacionado);
        noRotacionado.setNoPai(noEsquerdo);
        // Se a noza raíz possui nó pai
        if (noEsquerdo.getNoPai() != null) {
            // Atualiza a vinculação no nó pai
            if (noEsquerdo.getNoPai().getNoDireita() == noRotacionado) {
                noEsquerdo.getNoPai().setNoDireita(noEsquerdo);
            } else if (noEsquerdo.getNoPai().getNoEsquerda() == noRotacionado) {
                noEsquerdo.getNoPai().setNoEsquerda(noEsquerdo);
            }
        }
        // Atualiza a altura dos nós
        noRotacionado.setAltura(NoUtils.getAlturaNo(noRotacionado));
        noEsquerdo.setAltura(NoUtils.getAlturaNo(noEsquerdo));
        return noEsquerdo;
    }

    /**
     * Realiza a rotação dupla a esquerda
     *
     * @param noRotacionado Nó
     * @return Nó
     */
    public static No rotacaoDuplaEsquerda(No noRotacionado) {
        noRotacionado.setNoEsquerda(rotacaoSimplesEsquerda(noRotacionado.getNoEsquerda()));
        return rotacaoSimplesDireita(noRotacionado);
    }

    /**
     * Realiza a rotação dupla a direita
     *
     * @param noRotacionado Nó
     * @return No
     */
    public static No rotacaoDuplaDireita(No noRotacionado) {
        noRotacionado.setNoDireita(rotacaoSimplesDireita(noRotacionado.getNoDireita()));
        return rotacaoSimplesEsquerda(noRotacionado);
    }

}
