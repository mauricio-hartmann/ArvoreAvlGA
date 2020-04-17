package br.com.unisinos.arvoreavl.arvore;

/**
 * Classe com funções úteis para os nós
 * @author Marcello Augusto Gava 
 * @author Mauricio Hartmann
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

}
