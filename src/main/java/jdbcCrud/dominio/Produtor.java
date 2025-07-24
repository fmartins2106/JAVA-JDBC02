package jdbcCrud.dominio;

public class Produtor {
    public final Integer id_produtor;
    public final String nome;

    private Produtor(Integer id_produtor, String nome) {
        validacaoIDProdutor(id_produtor);
        validacaoNomeProdutor(nome);
        this.id_produtor = id_produtor;
        this.nome = nome;
    }

    public static void validacaoIDProdutor(Integer id_produtor){
        if (id_produtor <= 0 ){
            throw new IllegalArgumentException("Erro. ID produtor não pode ser menor que zero.");
        }
    }

    public static void validacaoNomeProdutor(String nome){
        if (nome == null || nome.isEmpty() || !nome.matches("^[\\p{L}0-9]+( [\\p{L}0-9]+)*$")){
            throw new IllegalArgumentException("Nome do produtor não pode conter caracteres. Tente novamente.");
        }
    }

    public static final class ProducerBuilder {
        private Integer id_produtor;
        private String nome;

        private ProducerBuilder() {
        }

        public static ProducerBuilder aProducer() {
            return new ProducerBuilder();
        }

        public ProducerBuilder id_produtor(Integer id_produtor) {
            this.id_produtor = id_produtor;
            return this;
        }

        public ProducerBuilder nome(String nome) {
            this.nome = nome;
            return this;
        }

        public Produtor build() {
            Produtor.validacaoIDProdutor(this.id_produtor);
            Produtor.validacaoNomeProdutor(this.nome);
            return new Produtor(id_produtor,nome);
        }
    }
}
