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
import java.util.Optional;
import java.util.TreeMap;

public class RepositorioProdutor {

    private static final Logger log = LogManager.getLogger(RepositorioProdutor.class);

    public static List<Produtor> pesquisaPorNome(String nome) { // Método público e estático que retorna uma lista de Produtor com base no nome informado
        log.info("Pesquisa pelo nome do produtor '{}'", nome); // Registra no log uma mensagem informando o nome que será pesquisado
        String sql = "SELECT * FROM anime_store.produtor WHERE nome LIKE ?"; // Query SQL com LIKE (busca por nome parcial usando parâmetro)
        List<Produtor> produtores = new ArrayList<>(); // Cria uma lista para armazenar os produtores encontrados
        try (
                Connection connection = ConnectionFactory.getConnection(); // Obtém uma conexão com o banco de dados
                PreparedStatement ps = pesquisaNomePreparedStatement(connection, sql, nome); // Prepara a query com o nome como parâmetro
                ResultSet rs = ps.executeQuery() // Executa a query e obtém o resultado (linha por linha) no ResultSet
        ) {
            while (rs.next()) { // Percorre cada linha retornada pela query
                Produtor produtor = Produtor.ProducerBuilder // Usa um builder para criar um objeto Produtor
                        .aProducer() // Inicia a construção do Produtor
                        .id_produtor(rs.getInt("id")) // Seta o ID a partir da coluna "id" do banco
                        .nome(rs.getString("nome")) // Seta o nome a partir da coluna "nome"
                        .build(); // Finaliza a criação do objeto Produtor
                produtores.add(produtor); // Adiciona o produtor à lista de retorno
            }
        } catch (SQLException e) { // Captura qualquer exceção SQL que possa ocorrer
            log.error("Erro na pesquisa do nome.", e); // Registra o erro no log com detalhes
            e.printStackTrace(); // Imprime o erro no console (útil durante o desenvolvimento)
        }

        return produtores; // Retorna a lista de produtores encontrados
    }

    public static PreparedStatement pesquisaNomePreparedStatement(Connection connection, String sql, String nome) throws SQLException {
        PreparedStatement ps = connection.prepareStatement(sql); // Prepara o statement SQL usando a conexão fornecida
        ps.setString(1, String.format("%%%s%%", nome)); // Substitui o parâmetro da query com um valor LIKE (%nome%) para busca parcial
        return ps; // Retorna o PreparedStatement configurado
    }


    public static void delete(int id) {
        String sql = "DELETE FROM anime_stores.produtor WHERE id = ?;";
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement ps = preparedStatementDelete(connection, sql, id)) {
            int produtorEncontrado = ps.executeUpdate();
            if (produtorEncontrado > 0) {
                log.info("Produtor #ID: '{}' deletado do bando de dados.", id);
                return;
            }
            log.warn("Produtor #ID: '{}' não encontrado.", id);
        } catch (SQLException e) {
            log.error("Erro ao deletar produto nṹmero cadastro:'{}'", id);
            e.printStackTrace();
        }
    }

    public static PreparedStatement preparedStatementDelete(Connection connection, String sql, int id) throws SQLException {
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setInt(1, id);
        return ps;
    }

    public static void save(Produtor produtor) {
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement ps = preparedStatementSalvar2(connection, produtor)) {
            int nomeInserido = ps.executeUpdate();
            if (nomeInserido == 0) {
                log.warn("Nenhuma linha foi inseriada para o produtor '{}'", produtor.getNome());
                return;
            }
            log.info("Produtor com nome '{}' cadastrado com sucesso.", produtor.getNome());
        } catch (SQLException e) {
            log.error("Erro ao salvar nome de produtor '{}'", produtor.getId_produtor(), e);
            e.printStackTrace();
        }
    }

    public static PreparedStatement preparedStatementSalvar2(Connection connection, Produtor produtor) throws SQLException {
        String sql = "INSERT INTO anime_store.produtor (name) values (?);";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setString(1, produtor.getNome());
        return ps;
    }

    public static Optional<Produtor> pesquisaPeloId(int id) {
        log.info("Pesquisa pelo #ID: '{}'", id);
        String sql = "SELECT * FROM anime_store.produtor WHERE id_produtor = ?;";
        try (Connection connection = ConnectionFactory.getConnection();
            PreparedStatement ps = preparedStatementPesquisaPeloID(connection,sql,id);
            ResultSet rs = ps.executeQuery()) {
            if (!rs.next()) return Optional.empty();
            return Optional.of(Produtor.ProducerBuilder
                    .aProducer()
                    .id_produtor(rs.getInt("id"))
                    .nome(rs.getString("nome"))
                    .build());
        }catch (SQLException e){
            log.error("Erro na pesquisa pelo #ID '{}'",id,e);
            e.printStackTrace();
        }
        return Optional.empty();
    }

    public static PreparedStatement preparedStatementPesquisaPeloID(Connection connection, String sql, int id) throws SQLException {
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setInt(1, id);
        return ps;
    }

    public static void atualizacao(Produtor produtor){
        log.info("Atualização dos dados do produtor: '{}'",produtor.getNome());
        try (Connection connection = ConnectionFactory.getConnection();
            PreparedStatement ps = preparedStatementAtualizacao(connection,produtor)){
            int linhasAfetadas = ps.executeUpdate();
            if (linhasAfetadas > 0){
                log.info("Dados do produtor #ID: '{}' alterados com sucesso.",produtor.getId_produtor());
                return;
            }
            log.warn("Erro ao atualizar os dados produtor #ID: '{}'",produtor.getId_produtor());
        }catch (SQLException e){
            log.error("Erro de atualização de dados do produtor '{}' no bando de dados.",produtor.getId_produtor(),e);
            e.printStackTrace();
        }
    }
    
    public static PreparedStatement preparedStatementAtualizacao(Connection connection, Produtor produtor) throws SQLException{
        String sql = "UPDATE anime_store.produtor  SET nome = ? WHERE id = ?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setString(1,produtor.getNome());
        ps.setInt(2,produtor.getId_produtor());
        return ps;
    }


//    ___________________________________________________________________________________________________________________________-
//    public static List<Produtor> pesquisaPorNome2(String nome){
//        log.info("Pesquisa pelo nome do produtor '{}'",nome);
//        List<Produtor> produtors = new ArrayList<>();
//        try (Connection connection = ConnectionFactory.getConnection();
//            PreparedStatement ps = preparedStatementPesquisaPeloNome2(connection,nome);
//            ResultSet rs = ps.executeQuery()){
//            while (rs.next()){
//                Produtor produtor = Produtor.ProducerBuilder.aProducer()
//                        .id_produtor(rs.getInt("id"))
//                        .nome(rs.getString("nome"))
//                        .build();
//                produtors.add(produtor);
//            }
//        }catch (SQLException e){
//            log.error("Erro na pesqui do nome produtor '{}' no bando de dados.",nome,e);
//            e.printStackTrace();
//        }
//        return produtors;
//    }
//
//    public static PreparedStatement preparedStatementPesquisaPeloNome2(Connection connection,String nome)throws SQLException{
//        String sql = "SELECT * FROM anime_store.produtor WHERE nome LIKE ?;";
//        PreparedStatement ps = connection.prepareStatement(sql);
//        ps.setString(1,String.format("%%%s%%",nome));
//        return ps;
//    }
//
//    public static void deletar2(int id){
//        try (Connection connection = ConnectionFactory.getConnection();
//            PreparedStatement ps = preparedStatementDeletar2(connection,id)){
//            int novaLinha = ps.executeUpdate();
//            if (novaLinha > 0){
//                log.info("produto removido com sucesso.");
//                return;
//            }
//            log.warn("Erro ao deletar cadastro produtor com #ID '{}'",id);
//        }catch (SQLException e){
//            log.error("Erro ao deletar ID '{}'",id,e);
//            e.printStackTrace();
//        }
//    }
//
//    public static PreparedStatement preparedStatementDeletar2(Connection connection,int id) throws SQLException{
//        String sql = "DELETE FROM anime_store.produtor WHERE id = ?;";
//        PreparedStatement ps = connection.prepareStatement(sql);
//        ps.setInt(1,id);
//        return ps;
//    }
//
//    public static void salvar2(Produtor produtor){
//        try (Connection connection = ConnectionFactory.getConnection();
//            PreparedStatement ps = preparedStatementSalvar3(connection,produtor)){
//            int novaLinha = ps.executeUpdate();
//            if (novaLinha == 0){
//                log.warn("Atenção. Produtor com #ID:'{}' não foi cadastrado.",produtor.getId_produtor());
//                return;
//            }
//            log.info("Produtor #ID: '{}' cadastrado com sucesso.",produtor.getId_produtor());
//        }catch (SQLException e){
//            log.error("Erro ao cadastrar #ID: '{}' no banco de dados.",produtor.getId_produtor(),e);
//            e.printStackTrace();
//        }
//    }
//
//    public static PreparedStatement preparedStatementSalvar3(Connection connection, Produtor produtor) throws SQLException{
//        String sql = "INSERT INTO anime_store.produtor(nome) VALUES(?);";
//        PreparedStatement ps = connection.prepareStatement(sql);
//        ps.setString(1,produtor.getNome());
//        return ps;
//    }
//
//
//    public static Optional<Produtor> pesquisaId2(int id){
//        try (Connection connection = ConnectionFactory.getConnection();
//            PreparedStatement ps = preparedStatementPesquisaId2(connection,id);
//            ResultSet rs = ps.executeQuery()){
//            if (rs.next()) return Optional.empty();
//            return Optional.of(Produtor.ProducerBuilder
//                    .aProducer()
//                    .id_produtor(rs.getInt("id"))
//                    .build());
//        }catch (SQLException e){
//            log.error("Erro na pesquisa do ID '{}'",id,e);
//                    e.printStackTrace();
//        }
//        return Optional.empty();
//    }
//
//    public static PreparedStatement preparedStatementPesquisaId2(Connection connection, int id) throws SQLException{
//        String sql = "SELECT * FROM anime_store.produtor WHERE id = ?;";
//        PreparedStatement ps = connection.prepareStatement(sql);
//        ps.setInt(1,id);
//        return ps;
//    }
//
//
//    public static void atualizarDados(Produtor produtor){
//        try (Connection connection = ConnectionFactory.getConnection();
//            PreparedStatement ps = preparedStatementAtualizarCadastro(connection,produtor)){
//            int proximaLinha = ps.executeUpdate();
//            if (proximaLinha > 0){
//                log.info("Dados produtor '{}' atualizado com sucesso.",produtor.getNome());
//                return;
//            }
//            log.warn("Erro ao atualizadar dados do produtor '{}'",produtor.getNome());
//        }catch (SQLException e){
//            log.error("Erro ao atualizar dados produtor '{}' no banco de dados.",produtor.getNome(),e);
//            e.printStackTrace();
//        }
//    }
//
//    public static PreparedStatement preparedStatementAtualizarCadastro(Connection connection,Produtor produtor) throws SQLException{
//        String sql = "UPDATE anime_store.produtor  SET nome = ? WHERE id = ?;";
//        PreparedStatement ps = connection.prepareStatement(sql);
//        ps.setInt(1,produtor.getId_produtor());
//        ps.setString(2,produtor.getNome());
//        return ps;
//    }
//
//
//
//
//

}
