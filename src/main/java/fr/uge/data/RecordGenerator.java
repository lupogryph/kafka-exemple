package fr.uge.data;

import com.github.javafaker.Faker;
import  java.util.Random;

public class RecordGenerator {
    private String nom;
    private String prenom;
    private int idProduit;
    private float prix;

    private static final int MARGEPRIX = 10;
    private static final int BASEPRIX = 30;
    private static final int MINPRIX = 10;
    public RecordGenerator() {
        fakerData();
    }

    public void fakerData() {
        Faker faker = new Faker();
        nom = faker.name().lastName();
        prenom = faker.name().firstName();
        Random idp  = new Random();
        idProduit = 5 + idp.nextInt(10) ;
        Random prx  = new Random();
        // + ou - 10% de variation sur le prix
        float marge = prx.nextInt(2*MARGEPRIX)-MARGEPRIX;
        prix = MINPRIX * (1+ (marge/100));
    }

    public String getNom() {
        return nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public int getIdProduit() {
        return idProduit;
    }

    public float getPrix() {
        return prix;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }
}