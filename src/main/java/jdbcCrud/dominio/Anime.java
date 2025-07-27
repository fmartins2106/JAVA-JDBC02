package jdbcCrud.dominio;

public class Anime {
    private final int id;
    private final String nome;

    private Anime(int id, String nome) {
        this.id = id;
        this.nome = nome;
        validacaoId(this.id);
        validacaoNome(this.nome);
    }

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public static void validacaoId(int id){
        if (id < 0){
            throw new IllegalArgumentException("ID não pode ser menor que zero.");
        }
    }

    public static void validacaoNome(String nome){
        if (nome == null || nome.isEmpty() || !nome.matches("^[\\p{L}0-9]+( [\\p{L}0-9]+)*$")){
            throw new IllegalArgumentException("Campo nome não pode ser vazio ou conter caracteres.");
        }
    }

    public static final class AnimeBuilder {
        private int id;
        private String nome;

        private AnimeBuilder() {
        }

        public static AnimeBuilder anAnime() {
            return new AnimeBuilder();
        }

        public AnimeBuilder id(int id) {
            this.id = id;
            return this;
        }

        public AnimeBuilder nome(String nome) {
            this.nome = nome;
            return this;
        }

        public Anime build() {
            return new Anime(id, nome);
        }
    }
}
