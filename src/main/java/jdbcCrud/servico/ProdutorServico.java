package jdbcCrud.servico;

import jdbcCrud.dominio.Produtor;
import jdbcCrud.repositorio.RepositorioProdutor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class ProdutorServico {
    private static final Scanner SCANNER = new Scanner(System.in);
    private static final Logger log = LogManager.getLogger(ProdutorServico.class);

    public static void menu(int opcao){
        switch (opcao){
            case 1 -> pesquisaPorNome();
            case 2 -> delete();
            case 3 -> salvar();
            case 4 -> atualizarDados();
            default -> log.warn("Digite uma opção válida.");
        }
    }

    private static void pesquisaPorNome(){
        log.info("Digite o nome ou deixe vazio para listar todos:");
        String nome = SCANNER.nextLine().trim();
        List<Produtor> produtores = RepositorioProdutor.pesquisaPorNome(nome);
        produtores.forEach(produtor -> System.out.printf("[%d] - %s\n",produtor.getId_produtor(),produtor.getNome()));
    }

    private static void delete(){
        pesquisaPorNome();
        log.info("Digite o ID do produto que você quer deletar:");
        int id = Integer.parseInt(SCANNER.nextLine().trim());
        log.warn("Você tem certeza que deseja excluir o cadastro #ID ? (Digite S ou N):'{}'",id);
        String escolha = SCANNER.nextLine().trim().toLowerCase();
        if (escolha.equalsIgnoreCase("s")) {
            RepositorioProdutor.delete(id);
        }
    }

    private static void salvar(){
        log.info("Digite o nome do produtor que você deseja cadastrar:");
        String nome = SCANNER.nextLine().trim();
        Produtor produtor = Produtor.ProducerBuilder.aProducer().nome(nome).build();
        RepositorioProdutor.save(produtor);
    }

    private static void atualizarDados(){
        log.info("Digite o ID do produtor que você deseja atualizar os dados:");
        Optional<Produtor> produtorOptional = RepositorioProdutor.pesquisaPeloId(Integer.parseInt(SCANNER.nextLine().trim()));
        if (produtorOptional.isEmpty()){
            log.warn("Produtor não encontrado.");
            return;
        }
        Produtor produtor = produtorOptional.get();
        log.info("Produtor encontrado:"+produtor);
        log.info("Digite o novo nome ou digite enter para manter o mesmo:");
        String nome = SCANNER.nextLine().trim();
        nome = nome.isEmpty() ? produtor.getNome() : nome;
        Produtor produtorAtualizado = Produtor.ProducerBuilder
                .aProducer()
                .id_produtor(produtor.getId_produtor())
                .nome(nome)
                .build();
        RepositorioProdutor.atualizacao(produtorAtualizado);
    }
}
