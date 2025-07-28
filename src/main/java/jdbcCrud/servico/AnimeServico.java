package jdbcCrud.servico;

import jdbcCrud.dominio.Anime;
import jdbcCrud.repositorio.RepositorioAnime;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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
        animes.forEach(anime -> {
            System.out.printf("#[%d] - %s  - %d", anime.getId(), anime.getNome(), anime.getEpisodeos());
        });
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
//________________________________________________________________________________________________
    public static void pesquisaPorNome(){
        log.info("Digite o nome do anime para efetuar a pesquisa ou digite:");
        String nome = SCANNER.nextLine().trim();
        List<Anime> animes = RepositorioAnime.pesquisaPorNome(nome);
        animes.forEach(anime -> {
            System.out.printf("#[%d] - %s  - %d", anime.getId(), anime.getNome(), anime.getEpisodeos());
        });
    }

    public static void deletarDados(){
        log.info("Digite o #ID do anime a ser excluido do sistema:");
        int idSelecionado = Integer.parseInt(SCANNER.nextLine().trim());
        log.warn("Realmente gostaria de excluir o cadastro #ID:'{}' (s/n)?",idSelecionado);
        String confirmacao = SCANNER.nextLine().trim().toLowerCase();
        if (confirmacao.equalsIgnoreCase("s")){
            RepositorioAnime.deletar(idSelecionado);
        }
    }

    public static void salvarDadosNoBancoDados(){
        log.info("Digite o nome do anime a ser cadastrado:");
        String nome = SCANNER.nextLine().trim();
        Anime.validacaoNome(nome);
        log.info("Digite a quantidade de episodeios:");
        int episodeos = Integer.parseInt(SCANNER.nextLine().trim());
        Anime.validacaoEpisodeos(episodeos);
        Anime anime = Anime.AnimeBuilder.anAnime()
                .nome(nome)
                .episodeos(episodeos)
                .build();
        RepositorioAnime.salvar(anime);
    }

    public static void atualizarDadosAnime(){
        log.info("Digite o id do anime:");
        Optional<Anime> optionalAnime = RepositorioAnime.pesquisaPorID(Integer.parseInt(SCANNER.nextLine().trim()));
        if (optionalAnime.isEmpty()){
            return;
        }
        Anime animeEncontrado = optionalAnime.get();
        log.info("Digite o novo nome do anime:");
        String novoNome = SCANNER.nextLine().trim();
        novoNome = novoNome.isEmpty() ? animeEncontrado.getNome() : novoNome;
        log.info("Digite a quantidade de episodeos:");
        int novosEpisodeos = Integer.parseInt(SCANNER.nextLine().trim());
        Anime anime = Anime.AnimeBuilder.anAnime()
                .id(animeEncontrado.getId())
                .nome(novoNome)
                .episodeos(novosEpisodeos)
                .build();
        RepositorioAnime.atualizarDados(anime);
    }
//    ______________________________________________________________________________________________________

    public static void pesquisaNome(){
        log.info("Digite o nome do anime ou enter para listar todos:");
        String nome = SCANNER.nextLine().trim();
        List<Anime> animes = RepositorioAnime.pesquisaPorNome(nome);
        if (nome.isEmpty()){
            animes.forEach(anime -> log.info("[{}] - {}  - {}\n", anime.getId(), anime.getNome(), anime.getEpisodeos()));
        }
    }

    public static void deletarDados2(){
        log.info("Digite o #ID do anime a ser deletado:");
        int id = Integer.parseInt(SCANNER.nextLine().trim());
        log.warn("Realmente deseja excluir os dados do ID '{}' ?(S|N)",id);
        String escolha = SCANNER.nextLine().trim().toLowerCase().trim().trim();
        if (escolha.equalsIgnoreCase("s")){
            RepositorioAnime.deletar(id);
        }
    }

    public static void salvarNovoCadastro2(){
        log.info("Digite o nome do anime:");
        String nomeAnime = SCANNER.nextLine().trim();
        Anime.validacaoNome(nomeAnime);
        log.info("Digite a quantidade de episodeos:");
        int eposodeos = Integer.parseInt(SCANNER.nextLine().trim());
        Anime.validacaoEpisodeos(eposodeos);
        Anime anime = Anime.AnimeBuilder.anAnime()
                .nome(nomeAnime)
                .episodeos(eposodeos)
                .build();
        RepositorioAnime.salvar(anime);
    }

    public static void atualizarDadosAnime2(){
        log.info("Digite o #ID do anime a ser atualizado:");
        Optional<Anime> optionalIdAnime = RepositorioAnime.pesquisaPorID(Integer.parseInt(SCANNER.nextLine().trim()));
        if (optionalIdAnime.isEmpty()){
            log.info("#ID inválido.Verifique.");
            return;
        }
        Anime animeEncontrado = optionalIdAnime.get();
        log.info("Digite o novo nome do anime ou enter para manter:");
        String novoNome = SCANNER.nextLine().trim();
        novoNome = novoNome.isEmpty() ? animeEncontrado.getNome() : novoNome;
        log.info("Digite a quantidade de episodeos:");
        int episodeos = Integer.parseInt(SCANNER.nextLine().trim());
        Anime.validacaoEpisodeos(episodeos);
        Anime anime = Anime.AnimeBuilder.anAnime()
                .nome(novoNome)
                .episodeos(episodeos)
                .build();
        RepositorioAnime.atualizarDados(anime);
    }

















}
