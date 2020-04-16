package br.com.unisinos.arvoreavl.arvore;

/**
 * Nó de uma árvore
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
     */
    public No(int valor) {
        this.valor = valor;
        this.noPai = this.noEsquerda = this.noDireita = null;
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
     * Retorna se o nó possui filhos
     * 
     * @return Boolean
     */
    public boolean isPossuiFilhos() {
        return noEsquerda != null || noDireita != null;
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
     * Retorna o nó em formato String
     * 
     * @return String
     */
    @Override
    public String toString() {
        return String.valueOf(valor);
    }
}
