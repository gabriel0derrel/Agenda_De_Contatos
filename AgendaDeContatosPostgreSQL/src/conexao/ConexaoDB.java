/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package conexao;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author misuka
 */
public class ConexaoDB {
    private static Connection conexao = null;
    private ConexaoDB(){}
    public static Connection getConexao() throws Exception{
        try {
            if(conexao == null){
                String driver = "org.postgresql.Driver";
                String url = "jdbc:postgresql://localhost:5432/ContatosDB";
                String user = "postgres";
                String password = "aluno";
                Class.forName(driver);
                conexao = DriverManager.getConnection(url, user, password);
            }
        } catch (ClassNotFoundException erro) {
            throw new Exception("Erro na ConexaoDB - Drive:" + erro.getMessage());
        } 
        catch (SQLException erro){
            throw new Exception("Erro na ConexaoDB - Banco de dados: " + erro.getMessage());
        }
        return conexao;
    }
}
