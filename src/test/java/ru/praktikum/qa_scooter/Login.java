package ru.praktikum.qa_scooter;

// Класс для логина курьером
public class Login {

    private String login;
    private String password;

    public Login(String login, String password) {
        this.login = login;
        this.password = password;
    }

    // Геттеры и сеттеры
    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
