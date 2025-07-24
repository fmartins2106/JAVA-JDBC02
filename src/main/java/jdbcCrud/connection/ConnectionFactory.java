package jdbcCrud.connection;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {
    private static final Logger log = LogManager.getLogger(ConnectionFactory.class);

    public static Connection getConnection() {
        String url = "jdbc:postgresql://localhost:5434/java_jdbc2";  // URL de conexão com o banco (host, porta, nome do BD)
        String username = "fmartins";                                // Usuário do banco de dados
        String password = "masterkey";                               // Senha do banco de dados
        try {
            Connection connection = DriverManager.getConnection(url, username, password); // Tenta abrir a conexão
            log.info("Conexão com banco de dados efetuada com sucesso.");                  // Loga sucesso na conexão
            return connection;                                                            // Retorna a conexão criada
        } catch (SQLException e) {
            log.error("Erro de conexão", e);                                              // Loga erro com detalhes da exceção
            throw new RuntimeException("Erro ao conectar com o banco de dados.", e);      // Lança exceção para avisar do problema
        }
    }

}
