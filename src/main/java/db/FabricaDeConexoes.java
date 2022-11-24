package db;

import java.sql.Connection;
import java.sql.DriverManager;

class FabricaDeConexoes {

    private static FabricaDeConexoes instancia;

    private FabricaDeConexoes(){}

    public static FabricaDeConexoes obterInstancia(){
        if(instancia == null){
            instancia = new FabricaDeConexoes();
        }
        return instancia;
    }

    public Connection obterConexao(){
        try{
            String stringDeConexao = "jdbc:sqlite:tarefa.db";
            Connection c = DriverManager.getConnection(stringDeConexao);
            return c;
        }catch (Exception e){
            e.printStackTrace();
        }
        throw  new RuntimeException("Erro ao criar a conexão com o banco de dados.");
    }

}
