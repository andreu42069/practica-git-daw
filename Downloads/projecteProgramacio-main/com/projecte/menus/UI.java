package com.projecte.menus;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class UI {
   /*
   Aquesta classe serveix com a una biblioteca
   de mètodes comuns a gastar entre objectes que
   usen la interficie de usuari (Terminal)
   */
   private static Scanner sc = new Scanner(System.in);

   public static int retornarInt(String missatge) {
      do {
         String entrada = "";
         try {
            entrada = retornarString(missatge);
            int numero = Integer.parseInt(entrada);
            return numero;
         } catch (NumberFormatException e) {
            System.out.println("ERROR: El valor introducido (" + entrada + ") no es un número");
         }
      } while (true);
   }

   public static String retornarString(String missatge) {
      System.out.print(missatge);
      return sc.nextLine();
   }

   public static LocalDate retornarData(String missatge) {
      while (true) {
         try {
            String dataText = retornarString(missatge);
            LocalDate data = LocalDate.parse(dataText, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            return data;
         } catch (DateTimeParseException e) {
            System.out.println("ERROR: el text introduit no és un format de data vàlid.");
         }
      }
   }
}