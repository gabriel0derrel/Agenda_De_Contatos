package modelos;

import persinstencia.AgendaContatos;
import java.util.List;
import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        AgendaContatos agenda = new AgendaContatos();
        Scanner scanner = new Scanner(System.in);
        String opcao;

        do {
            System.out.println("Menu:");
            System.out.println("1. Adicionar Contato");
            System.out.println("2. Excluir Contato");
            System.out.println("3. Alterar Contato");
            System.out.println("4. Listar Contatos");
            System.out.println("5. Consultar Contato");
            System.out.println("0. Sair");
            System.out.print("Escolha uma opção: ");
            opcao = scanner.nextLine();

            switch (opcao) {
                case "1":
                    System.out.print("Nome: ");
                    String nome = scanner.nextLine();
                    System.out.print("DDI: ");
                    int ddi = scanner.nextInt();
                    System.out.print("DDD: ");
                    int ddd = scanner.nextInt();
                    System.out.print("Número: ");
                    int numero = scanner.nextInt();
                    scanner.nextLine();  // consumir o newline
                    Telefone telefone = new Telefone(ddi, ddd, numero);
                    System.out.print("Email: ");
                    String email = scanner.nextLine();
                    agenda.incluir(new Contato(nome, telefone, email));
                    break;
                case "2":
                    System.out.print("Nome do contato a excluir: ");
                    nome = scanner.nextLine();
                    agenda.excluir(nome);
                    break;
                case "3":
                    System.out.print("Novo Nome: ");
                    String novoNome = scanner.nextLine();
                    System.out.print("Novo DDI: ");
                    ddi = scanner.nextInt();
                    System.out.print("Novo DDD: ");
                    ddd = scanner.nextInt();
                    System.out.print("Novo Número: ");
                    numero = scanner.nextInt();
                    scanner.nextLine();  // consumir o newline
                    Telefone novoTelefone = new Telefone(ddi, ddd, numero);
                    System.out.print("Novo Email: ");
                    String novoEmail = scanner.nextLine();
                    agenda.alterar(new Contato(novoNome, novoTelefone, novoEmail));
                    break;
                case "4":
                    List<Contato> contatos = agenda.listar();
                    for (Contato contato : contatos) {
                        System.out.println(contato);
                    }
                    break;
                case "5":
                    System.out.print("Enter the name of the contact to view: ");
                    nome = scanner.nextLine().toUpperCase();
                    agenda.consultar(nome);
                    break;
                case "0":
                    System.out.println("Saindo...");
                    break;
                default:
                    System.out.println("Opção inválida!");
                    break;
            }
        } while (!opcao.equals("0"));

        scanner.close();
    }
}