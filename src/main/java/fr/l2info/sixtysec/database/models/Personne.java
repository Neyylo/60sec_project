package fr.l2info.sixtysec.database.models;

public class Personne {
    private int idPersonne;
    private String nom;
    private String prenom;
    private int sanite;
    private int JoursFaim;
    private int JoursSoif;
    private int A_ETAT_SANTE;


    public Personne() {}

    public Personne(int idPersonne, String nom, String prenom, int sanite,int joursFaim, int joursSoif, int A_ETAT_SANTE) {
        this.idPersonne = idPersonne;
        this.nom = nom;
        this.prenom = prenom;
        this.sanite = sanite;
        this.JoursFaim = joursFaim;
        this.JoursSoif = joursSoif;
        this.A_ETAT_SANTE = A_ETAT_SANTE;
    }

    public int getIdPersonne() {return idPersonne;}
    public void setIdPersonne(int idPersonne) {this.idPersonne = idPersonne;}

    public String getNom() {return nom;}
    public void setNom(String nom) {this.nom = nom;}

    public String getPrenom() {return prenom;}
    public void setPrenom(String prenom) {this.prenom = prenom;}

    public int getSanite() {return sanite;}
    public void setSanite(int sanite) {this.sanite = sanite;}

    public int getJoursFaim() {return JoursFaim;}
    public void setJoursFaim(int joursFaim) {JoursFaim = joursFaim;}

    public int getJoursSoif() {return JoursSoif;}
    public void setJoursSoif(int joursSoif) {JoursSoif = joursSoif;}

    public int getA_ETAT_SANTE() {return A_ETAT_SANTE;}
    public void setA_ETAT_SANTE(int a_ETAT_SANTE) {A_ETAT_SANTE = a_ETAT_SANTE;}
}

