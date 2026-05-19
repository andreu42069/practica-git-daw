package com.projecte.entitats;

import java.io.Serializable;
import java.util.ArrayList;

public class Director implements Serializable, ListItem {
    private int id;
    private String nom;
    private ArrayList<Pelicula> obres;

    public Director(String nom, ArrayList<Pelicula> obres, int id) {
        this.id = id;
        this.nom = nom;
        this.obres = obres;
    }

    public Director(String nom, int id) {
        this.id = id;
        this.nom = nom;
        this.obres = new ArrayList<>();
    }

    public void mostrarDetalles() {
        System.out.println("Director " + nom + ":");
        System.out.println("- Pelis en les que ha treballat: " + nomsObres());
    }

    private String nomsObres() {
        if (obres == null || obres.isEmpty()) {
            return "Cap pel·lícula assignada";
        }
        String cadena = "\n";
        for (Pelicula p : obres) {
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