package br.com.unisinos.arvoreavl.arvore;

/**
 * Nó de uma árvore
 * @author Marcello Augusto Gava 
 * @author Mauricio Hartmann
 */
public class No {

    /** Valor */
    private int valor;
    /** Nó pai */
    private No noPai;
    /** Nó filho à esquerda */
    private No noEsquerda;
    /** Nó filho à direita */
    private No noDireita;
    /** Altura do nó */
    private int altura;

    /**
     * Método construtor
     *
     * @param valor Valor do nó
     * @param noPai No pai
     */
    public No(int valor, No noPai) {
        this.valor = valor;
        this.noPai = noPai;
        this.noEsquerda = this.noDireita = null;
        this.altura = 0;
    }

    public int getValor() {
        return valor;
    }

    public void setValor(int valor) {
        this.valor = valor;
    }

    public No getNoPai() {
        return noPai;
    }

    public void setNoPai(No noPai) {
        this.noPai = noPai;
    }

    public No getNoEsquerda() {
        return noEsquerda;
    }

    public void setNoEsquerda(No noEsquerda) {
        this.noEsquerda = noEsquerda;
    }

    public No getNoDireita() {
        return noDireita;
    }

    public void setNoDireita(No noDireita) {
        this.noDireita = noDireita;
    }

    public int getAltura() {
        return altura;
    }

    public void setAltura(int altura) {
        this.altura = altura;
    }

    public void calculaAltura() {

    }

    /**
     * Retorna se o nó não possui filhos
     *
     * @return Boolean
     */
    public boolean isFolha() {
        return noEsquerda == null && noDireita == null;
    }

    /**
     * Retorna se o nó possui filho a esquerda
     *
     * @return Boolean
     */
    public boolean isPossuiFilhoEsquerda() {
        return noEsquerda != null;
    }

    /**
     * Retorna se o nó possui filho a direita
     *
     * @return Boolean
     */
    public boolean isPossuiFilhoDireita() {
        return noDireita != null;
    }

    /**
     * Retorna se o nó é filho esquerdo
     * 
     * @return Boolean
     */
    public boolean isFilhoEsquerda() {
        return noPai.isPossuiFilhoEsquerda() && noPai.getNoEsquerda().getValor() == valor;
    }

    /**
     * Retorna se o nó é filho direito
     * 
     * @return Boolean
     */
    public boolean isFilhoDireita() {
        return noPai.isPossuiFilhoDireita() && noPai.getNoDireita().getValor() == valor;
    }

    /**
     * Retorna o nó em formato String
     *
     * @return String
     */
    @Override
    public String toString() {
        return String.valueOf(valor);
    }
}
