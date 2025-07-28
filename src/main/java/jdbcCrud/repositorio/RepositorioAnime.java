package jdbcCrud.repositorio;

import jdbcCrud.connection.ConnectionFactory;
import jdbcCrud.dominio.Anime;
import jdbcCrud.dominio.Produtor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.plaf.PanelUI;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class RepositorioAnime {


    private static final Logger log = LogManager.getLogger(RepositorioAnime.class);

    public static List<Anime> pesquisaPorNome(String nome) {
        List<Anime> animes = new ArrayList<>();
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement ps = preparedStatementPesquisaNome(connection, nome);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Anime anime = Anime.AnimeBuilder.anAnime()
                        .id(rs.getInt("id"))
                        .nome(rs.getString("nome"))
                        .episodeos(rs.getInt("episodeos"))
                        .build();
                animes.add(anime);
            }
        } catch (SQLException e) {
            log.error("Erro de conecção ao pesquisar no banco de dados pelo nome '{}'", nome, e);
            e.printStackTrace();
        }
        return animes;
    }

    public static PreparedStatement preparedStatementPesquisaNome(Connection connection, String nome) throws SQLException {
        String sql = "SELECT * FROM anime_store.anime WHERE nome = ?;";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setString(1, String.format("%%%s%%", nome));
        return ps;
    }


    public static void deletar(int id) {
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement ps = preparedStatementDeletarAnime(connection, id)) {
            int proximaLinha = ps.executeUpdate();
            if (proximaLinha > 0) {
                log.info("Cadastro Anime #ID '{}' deletado com sucesso do sistema.", id);
                return;
            }
            log.warn("Anime #ID'{}' ainda consta no sistema. Verificar.", id);
        } catch (SQLException e) {
            log.error("Erro ao deletar anime #ID:'{}' do bando de dados.", id, e);
            e.printStackTrace();
        }
    }

    public static PreparedStatement preparedStatementDeletarAnime(Connection connection, int id) throws SQLException {
        String sql = "DELETE FROM anime_store.anime WHERE id = ?;";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setInt(1, id);
        return ps;
    }

    public static void salvar(Anime anime) {
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement ps = preparedStatementSalvarAnime(connection, anime)) {
            int proximaLinha = ps.executeUpdate();
            if (proximaLinha == 0) {
                log.warn("Anime nome '{}' não teve cadastro concluido. Verifique.", anime.getNome());
                return;
            }
            log.info("Anime nome '{}' cadastrado com sucesso.", anime.getNome());
        } catch (SQLException e) {
            log.error("Erro ao cadastrar no bando de dados anime '{}'", anime.getNome(), e);
            e.printStackTrace();
        }
    }

    public static PreparedStatement preparedStatementSalvarAnime(Connection connection, Anime anime) throws SQLException {
        String sql = "INSERT INTO anime_store.anime(nome,episodeos) VALUES(?,?);";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setString(1, anime.getNome());
        ps.setInt(2,anime.getEpisodeos());
        return ps;
    }

    public static Optional<Anime> pesquisaPorID(int id) {
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement ps = preparedStatementPesquisaID(connection, id);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) return Optional.empty();
            return Optional.of(Anime.AnimeBuilder.anAnime()
                    .id(rs.getInt("id"))
                    .nome(rs.getString("nome"))
                    .episodeos(rs.getInt("episodeos"))
                    .build());
        } catch (SQLException e) {
            log.error("Erro na pesquisa do #ID '{}' no banco de dados.", id, e);
            e.printStackTrace();
        }
        return Optional.empty();
    }

    public static PreparedStatement preparedStatementPesquisaID(Connection connection, int id) throws SQLException {
        String sql = "SELECT * FROM anime_store.anime WHERE id = ?;";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setInt(1, id);
        return ps;
    }

    public static void atualizarDados(Anime anime) {
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement ps = preparedStatementAlterarDados(connection, anime)) {
            int linhaModificada = ps.executeUpdate();
            if (linhaModificada > 0) {
                log.info("Dados alterados do cadastro do anime '{}'",anime.getNome());
                return;
            }
            log.warn("Falha na operação de alteração do cadastro do anime '{}'",anime.getNome());
        }catch (SQLException e){
            log.error("Erro ao atualizar cadastro anime '{}'. Verifique !",anime.getNome(),e);
            e.printStackTrace();
        }
    }

    public static PreparedStatement preparedStatementAlterarDados(Connection connection, Anime anime) throws SQLException {
        String sql = "UPDATE FROM anime_store.anime SET nome = ?, episodeos = ? WHERE id = ?;";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setString(1,anime.getNome());
        ps.setInt(2,anime.getEpisodeos());
        ps.setInt(3,anime.getId());
        return ps;
    }

//    ---------------------------------------------------------------------------------------------------------------

//    public static List<Anime> pesquisaPorNome3(String nome){
//        log.info("Pesquisa pelo nome de anome '{]'",nome);
//        List<Anime> animes = new ArrayList<>();
//        try (Connection connection = ConnectionFactory.getConnection();
//        PreparedStatement ps = preparedStatementPesquisaNome2(connection,nome);
//        ResultSet rs = ps.executeQuery()){
//            while (rs.next()){
//                Anime anime = Anime.AnimeBuilder.anAnime()
//                        .id(rs.getInt("id"))
//                        .nome(rs.getString("nome"))
//                        .build();
//                animes.add(anime);
//            }
//        }catch (SQLException e){
//            log.error("Erro ao pesquisar no bando de dados pelo nome do anime '{}'",nome,e);
//            e.printStackTrace();
//        }
//        return animes;
//    }
//
//    public static PreparedStatement preparedStatementPesquisaNome2(Connection connection, String nome) throws SQLException{
//        String sql = "SELECT * FROM anime_store.anime WHERE nome LIKE ?;";
//        PreparedStatement ps = connection.prepareStatement(sql);
//        ps.setString(1,String.format("nome"));
//        return ps;
//    }
//
//    public static void deletar2(int id){
//        try (Connection connection = ConnectionFactory.getConnection();
//        PreparedStatement ps = preparedStatementDeletar2(connection,id)){
//            int linhaAlterada = ps.executeUpdate();
//            if (linhaAlterada > 0){
//                log.info("Cadastro #ID '{}' excluido do sistema com sucesso.",id);
//                return;
//            }
//            log.warn("Não foi possível concluir operação de exclusão do cadastro #ID '{}'",id);
//        }catch (SQLException e){
//            log.error("Erro na operação de exclusão do cadastro de anime #ID '{}'",id,e);
//            e.printStackTrace();
//        }
//    }
//
//    public static PreparedStatement preparedStatementDeletar2(Connection connection, int id) throws SQLException{
//        String sql = "DELETE FROM anime_store.anime WHERE id = ?;";
//        PreparedStatement ps = connection.prepareStatement(sql);
//        ps.setInt(1,id);
//        return ps;
//    }
//
//    public static void salvar3(Anime anime){
//        try (Connection connection = ConnectionFactory.getConnection();
//        PreparedStatement ps = preparedStatementSalvar2(connection,anime)){
//            int addNovaLinha = ps.executeUpdate();
//            if (addNovaLinha == 0){
//                log.warn("Cadastro anime '{}' não efetuado.",anime.getNome());
//                return;
//            }
//            log.info("Cadastro anime '{}' efetuado com sucesso.",anime.getNome());
//        }catch (SQLException e){
//            log.error("Erro ao cadastrar anime '{}' no banco de dados.",anime.getNome(),e);
//            e.printStackTrace();
//        }
//    }
//
//    public static PreparedStatement preparedStatementSalvar2(Connection connection, Anime anime) throws SQLException {
//        String sql = "INSERT INTO anime_store.anime(nome,episodeos) VALUES(?,?);";
//        PreparedStatement ps = connection.prepareStatement(sql);
//        ps.setString(1,anime.getNome());
//        ps.setInt(2,anime.getEpisodeos());
//        return ps;
//    }
//
//    public static Optional<Anime> pesquisaPorId2(int id){
//        try (Connection connection = ConnectionFactory.getConnection();
//        PreparedStatement ps = preparedStatementPesquisaID2(connection,id);
//        ResultSet rs = ps.executeQuery()){
//            if (rs.next()) return Optional.empty();
//            return Optional.of(Anime.AnimeBuilder.anAnime()
//                            .id(rs.getInt("id"))
//                            .nome(rs.getString("nome"))
//                            .episodeos(rs.getInt("episodeios"))
//                            .build());
//        }catch (SQLException e){
//            log.error("Erro na pesquisa do bando de dados pelo anime #ID '{}'",id,e);
//            e.printStackTrace();
//        }
//        return Optional.empty();
//    }
//
//    public static PreparedStatement preparedStatementPesquisaID2(Connection connection, int id) throws SQLException {
//        String sql = "SELECT * FROM anime_store.anime WHERE nome = ?;";
//        PreparedStatement ps = connection.prepareStatement(sql);
//        ps.setInt(1,id);
//        return ps;
//    }
//
//    public static void atualizandoDados2(Anime anime){
//        try (Connection connection = ConnectionFactory.getConnection();
//        PreparedStatement ps = preparedStatementAtualizandoDados(connection,anime)){
//            int proximaLinha = ps.executeUpdate();
//            if (proximaLinha > 0){
//                log.info("Cadastro anime #ID '{}' atualizado com sucesso.",anime.getId());
//                return;
//            }
//            log.warn("Não foi possivél comcluir atualização do cadastro #ID '{}'.",anime.getId());
//
//        }catch (SQLException e){
//            log.error("Erro ao atualizar dados do anime #ID '{}' no banco de dados.",anime.getNome(),e);
//            e.printStackTrace();
//        }
//    }
//
//    public static PreparedStatement preparedStatementAtualizandoDados(Connection connection, Anime anime) throws SQLException {
//        String sql = "UPDATE FROM anime_store.anime SET nome = ?, episodeos = ? WHERE id = ?;";
//        PreparedStatement ps = connection.prepareStatement(sql);
//        ps.setString(1,anime.getNome());
//        ps.setInt(2,anime.getEpisodeos());
//        ps.setInt(3,anime.getId());
//        return ps;
//    }
//
//






}
