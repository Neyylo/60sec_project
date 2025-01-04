package fr.l2info.sixtysec.database.models;

public class Objet {
    private int idObjet;
    private String nom;
    private int consommable;

    public Objet() {}

    public Objet(int idObjet, String nom, int consommable) {
        this.idObjet = idObjet;
        this.nom = nom;
        this.consommable = consommable;
    }

    public int getIdObjet() {return idObjet;}
    public void setIdObjet(int idObjet) {this.idObjet = idObjet;}

    public String getNom() {return nom;}
    public void setNom(String nom) {this.nom = nom;}

    public int getConsommable() {return consommable;}
    public void setConsommable(int consommable) {this.consommable = consommable;}
}
