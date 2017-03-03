package tac.com.appandroidtac.model;

/**
 * Created by diego on 02/03/2017.
 */

public class Usuario {

    private Long id;
    private String nomeApelido;
    private String senha;

    public Usuario() {
    }

    public Usuario(Long id, String nomeApelido, String senha) {
        this.id = id;
        this.nomeApelido = nomeApelido;
        this.senha = senha;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomeApelido() {
        return nomeApelido;
    }

    public void setNomeApelido(String nomeApelido) {
        this.nomeApelido = nomeApelido;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Usuario usuario = (Usuario) o;

        if (id != null ? !id.equals(usuario.id) : usuario.id != null) return false;
        return nomeApelido != null ? nomeApelido.equals(usuario.nomeApelido) : usuario.nomeApelido == null;

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (nomeApelido != null ? nomeApelido.hashCode() : 0);
        return result;
    }
}
