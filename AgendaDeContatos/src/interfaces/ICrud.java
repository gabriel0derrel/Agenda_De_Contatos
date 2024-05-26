package interfaces;

import modelos.Contato;
import java.util.List;

public interface ICrud {
    void incluir(Contato objeto);
    void excluir(String nomeCompleto);
    void alterar(Contato objeto);
    void consultar(String nomeCompleto);
    List<Contato> listar();
}
