package jdbcCrud.servico;

import jdbcCrud.dominio.Anime;
import jdbcCrud.dominio.Produtor;
import jdbcCrud.repositorio.RepositorioAnime;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class AnimeServico {
    private static final Scanner SCANNER = new Scanner(System.in);
    private static final Logger log = LogManager.getLogger(AnimeServico.class);

    public static void menu(int opcao){
        switch (opcao){
            case 1 -> pesquisaPeloNome();
            case 2 -> deletarCadastro();
            case 3 -> salvarNovoCadastro();
            case 4 -> update();
            default -> log.warn("Erro, digite uma opção válida.");
        }
    }

    public static void pesquisaPeloNome(){
        log.info("Digite o nome do anime que gostaria de pesquisar ou enter para mostrar todos:");
        String nome = SCANNER.nextLine().trim();
        List<Anime> animes = RepositorioAnime.pesquisaPorNome(nome);
        animes.forEach(anime -> System.out.printf("[%d] - %s \n",anime.getId(),anime.getNome()));
    }

    public static void deletarCadastro(){
        pesquisaPeloNome();
        log.info("Digite o número do #ID que gostaria de remover:");
        int id = Integer.parseInt(SCANNER.nextLine().trim());
        log.warn("Tem certeza que deseja excluir o cadastro #ID: '{}' ? (S ou N)",id);
        String escolha = SCANNER.nextLine().toLowerCase().trim();
        if (escolha.equalsIgnoreCase("s")){
            RepositorioAnime.deletar(id);
        }
    }

    public static void salvarNovoCadastro(){
        log.info("Digite o nome do novo anime a ser cadastrado:");
        String nome = SCANNER.nextLine().trim();
        Anime.validacaoNome(nome);
        log.info("Digite a quantidade de episódeos:");
        int episodeos = Integer.parseInt(SCANNER.nextLine().trim());
        Anime.validacaoEpisodeos(episodeos);
        Anime anime = Anime.AnimeBuilder
                .anAnime()
                .nome(nome)
                .episodeos(episodeos)
                .build();
        RepositorioAnime.salvar(anime);
    }

    public static void update(){
        log.info("Digite o #ID do anime a ser atualizado:");
        Optional<Anime> optionalAnime = RepositorioAnime.pesquisaPorID(Integer.parseInt(SCANNER.nextLine().trim()));
        if (optionalAnime.isEmpty()){
            log.warn("#ID não encontrado. Verifique.");
            return;
        }
        Anime animeEncontrado = optionalAnime.get();
        log.info("Digite o novo nome ou tecle enter para manter o mesmo:");
        String novoNome = SCANNER.nextLine().trim();
        novoNome = novoNome.isEmpty() ? animeEncontrado.getNome() : novoNome;
        log.info("Digite a quantidade de animes ou tecle enter para manter o mesmo:");
        int novosEpisodeios = Integer.parseInt(SCANNER.nextLine().trim());
        Anime anime = Anime.AnimeBuilder.anAnime()
                .id(animeEncontrado.getId())
                .nome(novoNome)
                .episodeos(novosEpisodeios)
                .build();
        RepositorioAnime.atualizarDados(anime);
    }

}
