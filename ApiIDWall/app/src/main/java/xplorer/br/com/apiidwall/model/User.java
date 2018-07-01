package xplorer.br.com.apiidwall.model;

public class User {

    private String id, token;

    public User(String id, String token) {
        this.id = id;
        this.token = token;
    }

    public String getId() {
        return id;
    }

    public String getToken() {
        return token;
    }
}
