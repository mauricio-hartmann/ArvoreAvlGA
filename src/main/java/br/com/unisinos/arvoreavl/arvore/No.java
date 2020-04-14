package br.com.unisinos.arvoreavl.arvore;

/**
 *
 * @author Marcelo & Mauricio
 */
public class No {

    private int valor;
    private No noEsquerda;
    private No noDireita;
    private No noPai;
    private int balanceamento;

    //construtores
    public No(int valor) {
        this.noDireita = null;
        this.noEsquerda = null;
        this.noPai = null;
        this.balanceamento = 0;
        this.valor = valor;
    }

    public int getValor() {
        return valor;
    }

    public No getNoEsquerda() {
        return noEsquerda;
    }

    public No getNoDireita() {
        return noDireita;
    }
    
    public No getNoPai() {
        return noPai;
    }
    
    public int getBalanceamento() {
        return balanceamento;
    }
    
    public void setValor(Integer valor) {
        this.valor = valor;
    }
    
    public void setNoEsquerda(No noEsquerda) {
        this.noEsquerda = noEsquerda;
    }

    public void setNoDireita(No noDireita) {
        this.noDireita = noDireita;
    }
    
    public void setNoPai(No noPai){
        this.noPai = noPai;
    }
    
    public void setBalanceamento(int balanceamento){
        this.balanceamento = balanceamento;
    }
    
//    @Override
//    public String toString() {
//        return "No [valor=" + valor + "]";
//    }    
}
