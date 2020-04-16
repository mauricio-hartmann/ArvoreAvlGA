package br.com.unisinos.arvoreavl.main;

import br.com.unisinos.arvoreavl.arvore.ArvoreAvl;
import br.com.unisinos.arvoreavl.arvore.No;
import java.util.Scanner;

public class Main {

    /** Objeto responsável por ler os inputs de dados */
    private static final Scanner scanner = new Scanner(System.in);

    /** Opção do menu - Busca */
    private static final String OPCAO_MENU_BUSCA = "b";
    /** Opção do menu - Inserção */
    private static final String OPCAO_MENU_INCLUIR = "i";
    /** Opção do menu - Exclusão */
    private static final String OPCAO_MENU_EXCLUIR = "e";
    /** Opção do menu - Imprimir em ordem */
    private static final String OPCAO_MENU_PRINT_ORDEM = "1";
    /** Opção do menu - Imprimir em pré-ordem */
    private static final String OPCAO_MENU_PRINT_PRE_ORDEM = "2";
    /** Opção do menu - Imprimir em pós-ordem */
    private static final String OPCAO_MENU_PRINT_POS_ORDEM = "3";
    /** Opção do menu - Sair */
    private static final String OPCAO_MENU_SAIR = "s";

    /**
     * Menu impresso na tela
     */
    private static String menu;

    /**
     * Método principal
     *
     * @param args Argumentos de execução
     */
    public static void main(String[] args) {
        ArvoreAvl arvore = new ArvoreAvl();
        boolean exibeMenu = true;
        String opcaoMenu;
        // Exibe o menu em loop até que o comando de saída seja recebido
        while (exibeMenu) {
            printMenu();
            opcaoMenu = scanner.next();
            switch (opcaoMenu) {
                case OPCAO_MENU_BUSCA:
                    busca(arvore);
                    break;
                case OPCAO_MENU_INCLUIR:
                    inserir(arvore);
                    break;
                case OPCAO_MENU_EXCLUIR:
                    excluir(arvore);
                    break;
                case OPCAO_MENU_PRINT_ORDEM:
                    // Verifica se a árvore está vazia
                    if (arvore.isArvoreVazia()) {
                        printArvoreVazia();
                        break;
                    }
                    arvore.printEmOrdem();
                    break;
                case OPCAO_MENU_PRINT_PRE_ORDEM:
                    // Verifica se a árvore está vazia
                    if (arvore.isArvoreVazia()) {
                        printArvoreVazia();
                        break;
                    }
                    arvore.printPreOrdem();
                    break;
                case OPCAO_MENU_PRINT_POS_ORDEM:
                    // Verifica se a árvore está vazia
                    if (arvore.isArvoreVazia()) {
                        printArvoreVazia();
                        break;
                    }
                    arvore.printPosOrdem();
                    break;
                case OPCAO_MENU_SAIR:
                    exibeMenu = false;
                    break;
                default:
                    System.out.println("Opção inválida!");
                    break;
            }
            System.out.println();
        }
    }

    /**
     * Imprime o menu na tela
     */
    private static void printMenu() {
        // Cria o menu caso ainda não tenha sido criado
        if (menu == null) {
            StringBuilder menuBuilder = new StringBuilder();
            menuBuilder.append("Selecione uma opção:\n");
            menuBuilder.append(String.format("%s - Busca:\n", OPCAO_MENU_BUSCA));
            menuBuilder.append(String.format("%s - Incluir:\n", OPCAO_MENU_INCLUIR));
            menuBuilder.append(String.format("%s - Excluir:\n", OPCAO_MENU_EXCLUIR));
            menuBuilder.append(String.format("%s - Imprimir em ordem:\n", OPCAO_MENU_PRINT_ORDEM));
            menuBuilder.append(String.format("%s - Imprimir pré-ordem:\n", OPCAO_MENU_PRINT_PRE_ORDEM));
            menuBuilder.append(String.format("%s - Imprimir pós-ordem:\n", OPCAO_MENU_PRINT_POS_ORDEM));
            menuBuilder.append(String.format("%s - Sair:\n", OPCAO_MENU_SAIR));
            menu = menuBuilder.toString();
        }
        System.out.print(menu);
    }

    /**
     * Busca um nó na árvore através do valor
     *
     * @param arvore Arvore
     */
    private static void busca(ArvoreAvl arvore) {
        // Exibe aviso caso a árvore esteja vazia
        if (arvore.isArvoreVazia()) {
            printArvoreVazia();
            return;
        }
        // busca o nó
        System.out.println("Informe o valor para busca: ");
        int valor = scanner.nextInt();
        No noRetornado = arvore.busca(valor);
        // Exibe o aviso caso tenha encontrado ou não o nó
        if (noRetornado == null) {
            System.out.println("Valor não encontrado!");
        } else {
            System.out.println(String.format("Nó %s encontrado!", noRetornado));
        }
        // Exibe o caminho percorrido
        System.out.println(String.format("Caminho percorrido: %s",
                arvore.getListaNosPercorridosBusca()));
    }

    /**
     * Insere um valor na árvore
     *
     * @param arvore Árvore
     */
    private static void inserir(ArvoreAvl arvore) {
        System.out.println("Informe um valor numérico:");
        int valor = scanner.nextInt();
        // Verifica se a árvore já possui o valor antes de inserir
        if (arvore.busca(valor) != null) {
            System.out.println("Valor já existe na árvore!");
        } else {
            arvore.inserir(valor);
            System.out.println("Valor inserido com sucesso!");
        }
    }

    /**
     * Exclui um valor da árvore
     *
     * @param arvore Árvore
     */
    private static void excluir(ArvoreAvl arvore) {
        // Exibe aviso caso a árvore esteja vazia
        if (arvore.isArvoreVazia()) {
            printArvoreVazia();
            return;
        }
        No noRemovido;
        System.out.println("Informe um valor numérico:");
        int valor = scanner.nextInt();
        noRemovido = arvore.excluir(valor);
        // Exibe o aviso caso tenha excluído ou não o nó
        if (noRemovido != null) {
            System.out.println("Nó removido com sucesso!");
        } else {
            System.out.println("Valor não existe na árvore!");
        }
    }

    /**
     * Imprime o aviso de arvore vazia
     */
    private static void printArvoreVazia() {
        System.out.println("A árvore está vazia!");
    }

}
