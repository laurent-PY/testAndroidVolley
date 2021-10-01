package com.example.victor.models;

public class Users {

    private int id;
    private String action;
    private String nom;
    private String prenom;
    private String mail;
    private String password;
    private String confirm;

    public Users() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAction() { return action; }

    public void setAction(String action) { this.action = action; }

    public String getConfirm() { return confirm; }

    public void setConfirm(String confirm) { this.confirm = confirm; }

    @Override
    public String toString() {
        return "Users{" +
                "id=" + id +
                ", action='" + action + '\'' +
                ", nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", mail='" + mail + '\'' +
                ", password='" + password + '\'' +
                ", confirm='" + confirm + '\'' +
                '}';
    }
}
