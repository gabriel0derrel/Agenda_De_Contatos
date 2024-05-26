package persinstencia;

import interfaces.ICrud;
import java.io.*;
import java.util.*;
import modelos.Contato;

public class AgendaContatos implements ICrud{

    private static final String ARQUIVO_CONTATOS = "contatos.txt";
    private List<Contato> contatos;

    public AgendaContatos() {
        contatos = new ArrayList<>();
        carregarContatos();
    }

    public void incluir(Contato contato) {
        contatos.add(contato);
        salvarContatos();
    }

    public void excluir(String nome) {
        contatos.removeIf(contato -> contato.getNome().equalsIgnoreCase(contato.getNome()));
        salvarContatos();
    }

    public void alterar(Contato novoContato) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Nome do contato a alterar: ");
        String nome = scanner.nextLine();
        excluir(nome);
        incluir(novoContato);
    }

    public List<Contato> listar() {
        contatos.sort(Comparator.comparing(contato -> contato.getNome()));
        return contatos;
    }

    private void carregarContatos() {
        try (BufferedReader br = new BufferedReader(new FileReader(ARQUIVO_CONTATOS))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                contatos.add(Contato.fromString(linha));
            }
        } catch (IOException e) {
            System.out.println("Erro ao carregar contatos: " + e.getMessage());
        }
    }

    private void salvarContatos() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(ARQUIVO_CONTATOS))) {
            for (Contato contato : contatos) {
                bw.write(contato.toString());
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Erro ao salvar contatos: " + e.getMessage());
        }
    }

    public void consultar(String nome) {
        try (BufferedReader reader = new BufferedReader(new FileReader(ARQUIVO_CONTATOS))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                if (linha.startsWith(nome + ";")) {
                    System.out.println("Contact: " + linha);
                    return;
                }
            }
        } catch (IOException e) {
            System.out.println("An error occurred while reading the contact list.");
        }
        System.out.println("Contact not found.");
    }
}