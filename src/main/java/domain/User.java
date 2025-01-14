package domain;

import java.security.MessageDigest;
import java.util.Base64;
import java.util.Date;

import domain.exception.AccessException;

public class User {
    private int id;
    private String login;
    private String passwordHash;
    private String actualToken;

    public User(int id, String login, String passwordHash, String actualToken) {
        this.id = id;
        this.login = login;
        this.passwordHash = passwordHash;
        this.actualToken = actualToken;
    }

    public int getID() {
        return this.id;
    }

    public String getLogin() {
        return this.login;
    }

    public String login(MessageDigest md, String password) throws AccessException {
        byte[] hash = md.digest(password.getBytes());

        String encodedHash = Base64.getEncoder().encodeToString(hash);
        if (encodedHash.equals(passwordHash)) {
            return getToken();
        } else {
            throw new AccessException("invalid credentials!");
        }
    }

    private String getToken() {
        if (!actualToken.isEmpty()) {
            return actualToken;
        }

        String keySource = this.login + this.id + new Date().getTime();
        byte [] tokenByte =  Base64.getEncoder().encode(keySource.getBytes());

        return new String(tokenByte);
    }
}