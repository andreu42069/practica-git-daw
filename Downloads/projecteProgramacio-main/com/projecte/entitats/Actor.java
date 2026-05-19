package com.projecte.entitats;

import java.io.Serializable;
import java.util.ArrayList;

public class Actor implements Serializable, ListItem {
    private int id;
    private String nom;
    private ArrayList<Pelicula> peliculesActuatEn;

    public Actor(String nom, ArrayList<Pelicula> peliculesActuatEn, int id) {
        this.id = id;
        this.nom = nom;
        this.peliculesActuatEn = peliculesActuatEn;
    }

    public Actor(String nom, int id) {
        this.id = id;
        this.nom = nom;
        this.peliculesActuatEn = new ArrayList<>();
    }

    public void mostrarDetalles() {
        System.out.println("Actor " + nom + ":");
        System.out.println("- Pelis en les que ha actuat: " + nomsAct());
    }

    private String nomsAct() {
        if (peliculesActuatEn == null || peliculesActuatEn.isEmpty()) {
            return "Cap pel·lícula assignada";
        }
        String cadena = "\n";
        for (Pelicula p : peliculesActuatEn) {
            cadena += ("· " + p.getNom() + "\n");
        }
        return cadena;
    }

    public String getNom() {
        return nom;
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return nom;
    }
}