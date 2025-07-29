package jdbcCrud;

import com.sun.source.tree.NewArrayTree;
import jdbcCrud.servico.AnimeServico;
import jdbcCrud.servico.ProdutorServico;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Scanner;

public class teste {
    private static final Logger log = LogManager.getLogger(teste.class);
    private static final Scanner SCANNER = new Scanner(System.in);
    public static void main(String[] args) {
        int opcao = 0;
        while (true){
            try {
                menu();
                opcao = Integer.parseInt(SCANNER.nextLine().trim());
                switch (opcao){
                    case 0:
                        return;
                    case 1:
                        menuProdutor();
                        opcao = Integer.parseInt(SCANNER.nextLine().trim());
                        ProdutorServico.menu(opcao);
                        break;
                    case 2:
                        animeMenu();
                        opcao = Integer.parseInt(SCANNER.nextLine().trim());
                        AnimeServico.menu(opcao);
                        break;
                    default:
                        log.warn("Erro, digite uma opção válida.");
                }
            }catch (NumberFormatException e){
                log.error("Erro, Digite um valor válido para opção.");
            }
        }
    }


    private static void menuProdutor(){
        System.out.println("Digite o número da operação:");
        System.out.println("1 - Pesquisa pelo produtor.");
        System.out.println("2 - Deletar cadastro produtor.");
        System.out.println("3 - Salvar dados produtor.");
        System.out.println("4 - Atualizar dados produtor.");
        System.out.println("9 - Voltar.");
    }

    public static void menu(){
        System.out.println("Digite o número da operação:");
        System.out.println("1 - Produtor.");
        System.out.println("2 - Anime.");
        System.out.println("0 - Sair..");
    }

    public static void animeMenu(){
        System.out.println("Digite o número da operação:");
        System.out.println("1- Pesquisa pelo anime.");
        System.out.println("2- Excluir dados anime.");
        System.out.println("3- Cadastro anime.");
        System.out.println("4- Atualizar dados anime.");
        System.out.println("9- Go back.");
    }

}
