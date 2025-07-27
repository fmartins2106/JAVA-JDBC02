package jdbcCrud.dominio;

public class Anime {
    private final int id;
    private final String nome;
    private final int episodeos;

    private Anime(int id, String nome, int episodeos) {
        this.id = id;
        this.nome = nome;
        this.episodeos = episodeos;
        validacaoId(this.id);
        validacaoNome(this.nome);
        validacaoEpisodeos(this.episodeos);
    }

    public int getId() {
        return id;
    }


    public String getNome() {
        return nome;
    }

    public int getEpisodeos() {
        return episodeos;
    }

    public static void validacaoId(int id) {
        if (id < 0) {
            throw new IllegalArgumentException("ID não pode ser menor que zero.");
        }
    }

    public static void validacaoNome(String nome) {
        if (nome == null || nome.isEmpty() || !nome.matches("^[\\p{L}0-9]+( [\\p{L}0-9]+)*$")) {
            throw new IllegalArgumentException("Campo nome não pode ser vazio ou conter caracteres.");
        }
    }

    public static void validacaoEpisodeos(int episodeos) {
        if (episodeos < 0) {
            throw new IllegalArgumentException("Campo episodeos não pode receber valor menor que zero.");
        }
    }

    public static final class AnimeBuilder {
        private int id;
        private String nome;
        private int episodeos;

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

        public AnimeBuilder episodeos(int episodeos) {
            this.episodeos = episodeos;
            return this;
        }

        public Anime build() {
            return new Anime(id, nome, episodeos);
        }
    }
}
