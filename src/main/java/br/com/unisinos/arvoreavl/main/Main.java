package br.com.unisinos.arvoreavl.main;

import br.com.unisinos.arvoreavl.arvore.ArvoreAvl;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        ArvoreAvl arvore = new ArvoreAvl();
        Scanner scanner = new Scanner(System.in);
        String opcaoMenu = new String();
        do {
            System.out.println(getMenu());
            opcaoMenu = scanner.nextLine();
            switch (opcaoMenu) {
                case "c":
                    System.out.println("Selecionou consulta");
                    break;
                case "i":
                    inserir(arvore);
                    break;
                case "e":
                    System.out.println("Selecionou exclusão");
                    break;
                default:
                    System.out.println("Opção inválida");
                    break;
            }
        } while (!opcaoMenu.equals("s"));
    }

    private static String getMenu() {
        StringBuilder menuBuilder = new StringBuilder();
        menuBuilder.append("Selecione uma opção:\n");
        menuBuilder.append("c - Consulta:\n");
        menuBuilder.append("i - Incluir:\n");
        menuBuilder.append("e - Excluir:\n");
        menuBuilder.append("s - Sair:\n");
        return menuBuilder.toString();
    }
            
    private static void inserir(ArvoreAvl arvore) {
        System.out.println("Informe um valor numérico");
        Scanner scanner = new Scanner(System.in);
        int valor = scanner.nextInt();
        arvore.inserir(valor);
    }

}
