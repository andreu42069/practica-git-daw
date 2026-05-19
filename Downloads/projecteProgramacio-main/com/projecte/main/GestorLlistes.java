package com.projecte.main;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import com.projecte.entitats.Pelicula;
import com.projecte.entitats.Actor;
import com.projecte.entitats.Director;
import com.projecte.entitats.ListItem;

public class GestorLlistes {
    private static final File fitxerLlistaPelis = new File("com\\projecte\\fitxers\\llistesGlobals\\pelicula.llista");
    private static final File fitxerLlistaDire = new File("com\\projecte\\fitxers\\llistesGlobals\\director.llista");
    private static final File fitxerLlistaAct = new File("com\\projecte\\fitxers\\llistesGlobals\\actor.llista");

    private static ArrayList<ListItem> llistaPelisGeneral = new ArrayList<>();
    private static ArrayList<ListItem> llistaDireGeneral = new ArrayList<>();
    private static ArrayList<ListItem> llistaActorGeneral = new ArrayList<>();

    public static ArrayList<ListItem> getLlistaPelis() {
        return llistaPelisGeneral;
    }

    public static ArrayList<ListItem> getLlistaDire() {
        return llistaDireGeneral;
    }

    public static ArrayList<ListItem> getLlistaActor() {
        return llistaActorGeneral;
    }

    public static void afegirElement(String tipus, Object element) {
        try {
            switch (tipus.toUpperCase()) {
                case "PELICULA":
                    llistaPelisGeneral.add((Pelicula)element);
                    break;
            
                case "DIRECTOR":
                    llistaDireGeneral.add((Director)element);
                    break;

                case "ACTOR":
                    llistaActorGeneral.add((Actor)element);
                    break;
            }
        } catch (Exception e) {
            System.err.println("Error al afegir l'element: " + e.getMessage());
        }
    }

    public static void afegirPeli(Pelicula p) {
        llistaPelisGeneral.add(p);
    }

    public static void afegirDire(Director d) {
        llistaDireGeneral.add(d);
    }

    public static void afegirAct(Actor a) {
        llistaActorGeneral.add(a);
    }

    public static void actualitzarLlistatsLocal() {
        comprobarLlistatsFitxers();

        ArrayList<ListItem> novaLlistaActors = llegirLlistaEnFile(fitxerLlistaAct);
        ArrayList<ListItem> novaLlistaDirectors = llegirLlistaEnFile(fitxerLlistaDire);
        ArrayList<ListItem> novaLlistaPelicules = llegirLlistaEnFile(fitxerLlistaPelis);

        GestorLlistes.setLlistaActor(novaLlistaActors);
        GestorLlistes.setLlistaDire(novaLlistaDirectors);
        GestorLlistes.setLlistaPelis(novaLlistaPelicules);
    }

    private static ArrayList<ListItem> llegirLlistaEnFile(File file) {
        if (!file.exists() || file.length() == 0) {
            return crearLlistaVuidaEnFile(file);
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            return (ArrayList<ListItem>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("ADVERTÈNCIA: El Fitxer " + file.getPath() + " no s'ha pogut llegir. Es crearà un llistat buit.");
            return crearLlistaVuidaEnFile(file);
        }
    }

    private static ArrayList<ListItem> crearLlistaVuidaEnFile(File file) {
        ArrayList<ListItem> emptyList = new ArrayList<>();
        try {
            if (file.getParentFile() != null && !file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {
                oos.writeObject(emptyList);
            }
        } catch (IOException e) {
            throw new RuntimeException("ERROR: No s'ha pogut crear l'arxiu de llista " + file.getPath() + ": " + e.getMessage());
        }
        return emptyList;
    }

    public static void setLlistaPelis(ArrayList<ListItem> novaLlista) {
        llistaPelisGeneral = novaLlista;
    }

    public static void setLlistaActor(ArrayList<ListItem> novaLlista) {
        llistaActorGeneral = novaLlista;
    }

    public static void setLlistaDire(ArrayList<ListItem> novaLlista) {
        llistaDireGeneral = novaLlista;
    }

    public static void actualitzarLlistatsFitxer() {
        try (
            ObjectOutputStream oosActors = new ObjectOutputStream(new FileOutputStream(fitxerLlistaAct));
            ObjectOutputStream oosDirectors = new ObjectOutputStream(new FileOutputStream(fitxerLlistaDire));
            ObjectOutputStream oosPelicules = new ObjectOutputStream(new FileOutputStream(fitxerLlistaPelis));
        ) {
            oosActors.writeObject(llistaActorGeneral);
            oosDirectors.writeObject(llistaDireGeneral);
            oosPelicules.writeObject(llistaPelisGeneral);
        } catch (IOException e) {
            throw new RuntimeException("ERROR: No s'ha pogut escriure en un fitxer de la carpeta " + fitxerLlistaAct.getParent() + ": " + e.getMessage());
        }
    }

    private static void comprobarLlistatsFitxers() {
        File directoris = fitxerLlistaPelis.getParentFile();
        if (directoris == null || !directoris.exists()) {
            directoris.mkdirs();
        }
        if (!fitxerLlistaPelis.exists()) {
            crearLlistaVuidaEnFile(fitxerLlistaPelis);
        }

        if (!fitxerLlistaDire.exists()) {
            crearLlistaVuidaEnFile(fitxerLlistaDire);
        }

        if (!fitxerLlistaAct.exists()) {
            crearLlistaVuidaEnFile(fitxerLlistaAct);
        }
    }
}