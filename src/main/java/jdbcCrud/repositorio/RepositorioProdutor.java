package jdbcCrud.repositorio;

import jdbcCrud.connection.ConnectionFactory;
import jdbcCrud.dominio.Produtor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

public class RepositorioProdutor {

    private static final Logger log = LogManager.getLogger(RepositorioProdutor.class);

    public static List<Produtor> pesquisaPorNome(String nome){
        log.info("Pesquisa pelo nome do produto '{}'",nome);
        String sql = "SELECT * FROM anime_store.producer WHERE nome LIKE ?;";
        List<Produtor> produtores = new ArrayList<>();
        try (Connection connection = ConnectionFactory.getConnection();
            PreparedStatement ps = pesquisaNomePreparedStatement(connection,nome,sql);
            ResultSet rs = ps.executeQuery()){
            while (rs.next()){
                Produtor produtor = Produtor.ProducerBuilder
                        .aProducer()
                        .id_produtor(rs.getInt("id"))
                        .nome(rs.getString("nome"))
                        .build();
                produtores.add(produtor);
            }
        }catch (SQLException e){
            log.error("Erro na pesquisa.",e);
            e.printStackTrace();
        }
        return produtores;
    }

    public static PreparedStatement pesquisaNomePreparedStatement(Connection connection, String nome, String sql) throws SQLException{
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setString(1,String.format("%%%s%%",nome));
        return ps;
    }


}
