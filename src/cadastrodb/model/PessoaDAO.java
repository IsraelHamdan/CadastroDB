package cadastrodb.model;

import java.util.List;

public interface PessoaDAO<T> {
    T getPessoa(int id);
    List<T> getPessoas();
    void incluir(T pessoa);
    void alerar(T pessoa);
    void excluir(int id);
}
