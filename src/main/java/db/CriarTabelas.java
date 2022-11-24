package db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;

public class CriarTabelas {

    private final static String CRIAR_TABELA_TAREFAS = """
            create table if not exists tarefas(
                id integer primary key autoincrement
               ,descricao  varchar(500)
               ,concluido  boolean
               ,prioridade integer
            )
            """;

    public static void criarTabelas(){
        List<String> lista = new ArrayList<>();
        lista.add(CRIAR_TABELA_TAREFAS);
        for (String comando : lista){
            try(Connection c = FabricaDeConexoes.obterInstancia().obterConexao();
                PreparedStatement p = c.prepareStatement(comando)) {
                p.execute();
            }catch (Exception e){
                e.printStackTrace();
            }
        }

    }

}