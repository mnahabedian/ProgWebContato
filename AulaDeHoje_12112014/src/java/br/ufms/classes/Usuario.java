/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufms.classes;

/**
 *
 * @author Hercules Sandim
 */
public class Usuario {
    private Integer id;
    private String login;
    private String password;
    private String name;
    private String tipo;

    public Usuario(Integer id, String login, String password, String name, String tipo) {
        this.id=id;
        this.login = login;
        this.password = password;
        this.name = name;
        this.tipo = tipo;
    }
    
    public Integer getId() {
        return id;
    }

    
    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public String getTipo() {
        return tipo;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    
    public void setLogin(String login) {
        this.login = login;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }    
}
