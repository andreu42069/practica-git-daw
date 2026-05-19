package com.projecte.menus;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.time.LocalDate;
import java.util.ArrayList;

import com.projecte.entitats.ListItem;
import com.projecte.entitats.Rol;
import com.projecte.entitats.Usuari;

public class GestorFitxerUsuaris {
   private static final File FITXER = new File("com\\projecte\\fitxers\\usuaris.txt");
   private static final File CARPETES_USUARIS = new File("com\\projecte\\fitxers\\usuaris\\");

   public static int contarUsuaris() {
      comprobarSiFileExisteix();
      ArrayList<Usuari> usuaris = getUsuaris();
      return usuaris.size();
   }

   public static void anyadirUsuari(Usuari user) {
      comprobarSiFileExisteix();
      ArrayList<Usuari> usuaris = getUsuaris();
      usuaris.add(user);

      File usuari = construirCarpetaPersonal(user);
      user.setCarpetaPersonal(usuari);

      escriureUsuaris(usuaris);

      usuari.mkdirs();
      File actors = new File(usuari.getPath() + "\\actor.llista");
      File directors = new File(usuari.getPath() + "\\director.llista");
      File pelicules = new File(usuari.getPath() + "\\pelicula.llista");
      
      try (
         ObjectOutputStream oosActors = new ObjectOutputStream(new FileOutputStream(actors));
         ObjectOutputStream oosDirectors = new ObjectOutputStream(new FileOutputStream(directors));
         ObjectOutputStream oosPelicules = new ObjectOutputStream(new FileOutputStream(pelicules));
      ) {
         actors.createNewFile();
         oosActors.writeObject(new ArrayList<ListItem>());
         directors.createNewFile();
         oosDirectors.writeObject(new ArrayList<ListItem>());
         pelicules.createNewFile();
         oosPelicules.writeObject(new ArrayList<ListItem>());
      } catch (IOException e) {
         throw new RuntimeException("ERROR: No s'ha pogut escriure en el directori " + usuari.getPath() + ": " + e.getMessage());
      }
   }

   public static ArrayList<Usuari> getUsuaris() {
      comprobarSiFileExisteix();
      ArrayList<Usuari> usuaris = new ArrayList<>();
      
      if (FITXER.length() == 0) {
         return usuaris;
      }
      
      try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FITXER))) {
         usuaris = (ArrayList<Usuari>) ois.readObject();
      } catch (ClassNotFoundException | IOException e) {
         throw new RuntimeException("ERROR: No s'ha pogut llegir el fitxer " + FITXER.getPath() + ": " + e.getMessage());
      }
      return usuaris;
   }

   private static void escriureUsuaris(ArrayList<Usuari> usuaris) {
      try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FITXER))) {
         oos.writeObject(usuaris);
      } catch (IOException e) {
         throw new RuntimeException("ERROR: No s'ha pogut escriure en el fitxer " + FITXER.getPath() + ": " + e.getMessage());
      }
   }

   private static void comprobarSiFileExisteix() {
      try {
         File directoris = FITXER.getParentFile();

         if (directoris == null || !directoris.exists()) {
            directoris.mkdirs();
         }

         if (!FITXER.exists()) {
            FITXER.createNewFile();
            crearUsuariAdminPerDefecte();
         }
      } catch (IOException e) {
         throw new RuntimeException("ERROR: No s'ha pogut crear el fitxer " + FITXER.getPath() + ": " + e.getMessage());
      }
   }

   private static void crearUsuariAdminPerDefecte() {
      Usuari adminUser = new Usuari(0, "admin", "", "admin@admin", "", null, "admin", Rol.ADMIN);
      ArrayList<Usuari> usuaris = new ArrayList<>();
      usuaris.add(adminUser);
      escriureUsuaris(usuaris);
   }

   public static void actualitzarLlistatsLocals(Usuari usuari) {
      File carpetaPersonal = usuari.getCarpetaPersonal();
      if (carpetaPersonal == null) {
         carpetaPersonal = construirCarpetaPersonal(usuari);
         usuari.setCarpetaPersonal(carpetaPersonal);
      }
      if (!carpetaPersonal.exists()) {
         carpetaPersonal.mkdirs();
      }

      File fileActors = new File(carpetaPersonal, "actor.llista");
      File fileDirectors = new File(carpetaPersonal, "director.llista");
      File filePelis = new File(carpetaPersonal, "pelicula.llista");

      usuari.setLlistaActor(llegirLlistaEnFile(fileActors));
      usuari.setLlistaDire(llegirLlistaEnFile(fileDirectors));
      usuari.setLlistaPelis(llegirLlistaEnFile(filePelis));
   }

   private static File construirCarpetaPersonal(Usuari usuari) {
      String nomCarpetaUsuaria = String.format("%04d", usuari.getId());
      int correuPuntTrim = usuari.getCorreu().indexOf('@');
      if (correuPuntTrim == -1) {
         nomCarpetaUsuaria += usuari.getCorreu();
      } else {
         nomCarpetaUsuaria += usuari.getCorreu().substring(0, correuPuntTrim);
      }
      return new File(CARPETES_USUARIS, nomCarpetaUsuaria);
   }

   private static ArrayList<ListItem> llegirLlistaEnFile(File file) {
      if (!file.exists() || file.length() == 0) {
         return crearLlistaVuidaEnFile(file);
      }

      try (
         ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))
      ) {
         return (ArrayList<ListItem>) ois.readObject();
      } catch (IOException | ClassNotFoundException e) {
         System.err.println("ADVERTÈNCIA: El fitxer " + file.getPath() + " no s'ha pogut llegir. Creant llistat buit.");
         return crearLlistaVuidaEnFile(file);
      }
   }

   private static ArrayList<ListItem> crearLlistaVuidaEnFile(File file) {
      ArrayList<ListItem> vuida = new ArrayList<>();
      try {
         if (file.getParentFile() != null && !file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
         }
         try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {
            oos.writeObject(vuida);
         }
      } catch (IOException e) {
         throw new RuntimeException("ERROR: No s'ha pogut crear el fitxer de llista " + file.getPath() + ": " + e.getMessage());
      }
      return vuida;
   }

   public static void actualitzarLlistatsFitxers(Usuari usuari) {
      try (
         ObjectOutputStream oosActors = new ObjectOutputStream(new FileOutputStream(usuari.getCarpetaPersonal().getPath() + "\\actor.llista"));
         ObjectOutputStream oosDirectors = new ObjectOutputStream(new FileOutputStream(usuari.getCarpetaPersonal().getPath() + "\\director.llista"));
         ObjectOutputStream oosPelicules = new ObjectOutputStream(new FileOutputStream(usuari.getCarpetaPersonal().getPath() + "\\pelicula.llista"));
      ) {
         oosActors.writeObject(usuari.getLlistaActor());
         oosDirectors.writeObject(usuari.getLlistaDire());
         oosPelicules.writeObject(usuari.getLlistaPelis());
      } catch (IOException e) {
         throw new RuntimeException("ERROR: No s'ha pogut escriure en un fitxer del directori " + usuari.getCarpetaPersonal().getPath() + ": " + e.getMessage());
      }
   }
}
