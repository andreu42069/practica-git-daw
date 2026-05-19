package com.projecte.entitats;

import java.io.File;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;

public class Usuari implements Serializable {

    private ArrayList<ListItem> peliculesPersonal;
    private ArrayList<ListItem> actorsPersonal;
    private ArrayList<ListItem> directorsPersonal;

    private final int id;
    private String nom;
    private String cognoms;
    private String correu;
    private String poblacio;
    private LocalDate dataNaixement;
    private String contrasenya;
    private Rol rol;
    private File carpetaPersonal;

    public Usuari(int id, String nom, String cognoms, String correu, String poblacio, LocalDate dataNaixement, String contrasenya, Rol rol) {
        this.id = id;
        this.nom = nom;
        this.cognoms = cognoms;
        this.correu = correu;
        this.poblacio = poblacio;
        this.dataNaixement = dataNaixement;
        this.contrasenya = contrasenya;
        this.rol = rol;
        this.actorsPersonal = new ArrayList<>();
        this.directorsPersonal = new ArrayList<>();
        this.peliculesPersonal = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getCognoms() {
        return cognoms;
    }

    public void setCognoms(String cognoms) {
        this.cognoms = cognoms;
    }

    public String getCorreu() {
        return correu;
    }

    public void setCorreu(String correu) {
        this.correu = correu;
    }

    public String getPoblacio() {
        return poblacio;
    }

    public void setPoblacio(String poblacio) {
        this.poblacio = poblacio;
    }

    public LocalDate getDataNaixement() {
        return dataNaixement;
    }

    public void setDataNaixement(LocalDate dataNaixement) {
        this.dataNaixement = dataNaixement;
    }

    public String getContrasenya() {
        return contrasenya;
    }

    public void setContrasenya(String contrasenya) {
        this.contrasenya = contrasenya;
    }

    public Rol getRol() {
        return rol;
    }

    public void setRol(String rol) {
        try {
            this.rol = Rol.valueOf(rol.toUpperCase());
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    public ArrayList<ListItem> getLlistaPelis() {
        return peliculesPersonal;
    }

    public ArrayList<ListItem> getLlistaActor() {
        return actorsPersonal;
    }

    public ArrayList<ListItem> getLlistaDire() {
        return directorsPersonal;
    }

    public void afegirPeli(ListItem p) {
        peliculesPersonal.add(p);
    }

    public void afegirDire(ListItem d) {
        directorsPersonal.add(d);
    }

    public void afegirAct(ListItem a) {
        actorsPersonal.add(a);
    }

    public void setLlistaPelis(ArrayList<ListItem> novaLlista) {
        peliculesPersonal = novaLlista;
    }

    public void setLlistaActor(ArrayList<ListItem> novaLlista) {
        actorsPersonal = novaLlista;
    }

    public void setLlistaDire(ArrayList<ListItem> novaLlista) {
        directorsPersonal = novaLlista;
    }

    public File getCarpetaPersonal() {
        return carpetaPersonal;
    }

    public void setCarpetaPersonal(File carpetaPersonal) {
        this.carpetaPersonal = carpetaPersonal;
    }

    public ListItem buscarElementenLlista(int id, ArrayList<ListItem> llista) {
        for (ListItem item : llista) {
            if (item.getId() == id) {
                return item;
            }
        }
        return null;
    }
}
