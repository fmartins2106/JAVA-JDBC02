package jdbcCrud.connection;

import jdbcCrud.dominio.Produtor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class ConnectionFactory {

    private static final Logger log = LogManager.getLogger(ConnectionFactory.class);

    public static Connection getConnection(){
       String url = "jdbc:postgresql://localhost:5434/java_jdbc2";
       String username = "fmartins";
       String password = "masterkey";
       try {
           Connection connection = DriverManager.getConnection(url,username,password);
           log.info("Conecção com bando de dados realizada com sucesso.");
           return connection;
       }catch (SQLException e){
           log.error("Erro de conecação com bando de dados.",e);
           throw new RuntimeException("Erro de conecção",e);
       }
   }





}
