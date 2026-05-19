package com.projecte.menus;

import com.projecte.entitats.Usuari;
import com.projecte.entitats.Rol;
import java.time.LocalDate;
import java.util.ArrayList;

public class MenuInici {

    public static void executarMenu() {
        String opcion;

        do {
            System.out.println("\n--- MENU INICI ---");
            System.out.println("1.- Registrarse");
            System.out.println("2.- Iniciar Sessió");
            System.out.println("0.- Tancar el programa");
            opcion = UI.retornarString("Tria una opcio: ");

            switch (opcion) {
                case "1":
                    registrarUsuari();
                    break;

                case "2":
                    iniciarSessio();
                    break;

                default:
                    System.out.println("ERROR: La opció introduida (" + opcion + ") no és reconeguda.");
                    break;
            }

        } while (!opcion.equals("0"));
        System.out.println("Es finalitzarà el programa.");
    }

    private static void registrarUsuari() {
        String cont;
        String cont2;
        int id = 1;

        System.out.println("\n--- REGISTRE ---");
        String nom = UI.retornarString("Introdueix el teu nom: ");
        String cognom = UI.retornarString("Introdueix els teus cognoms: ");
        String email;
        do {
            email = UI.retornarString("Introdueix el correu electronic: ");
            if (!email.contains("@")) {
                System.out.println("ERROR: El email intoducido (" + email + ") no contiene @");
            }
        } while (!email.contains("@"));

        do {
            cont = UI.retornarString("Introdueix la teua contrasenya: ");
            cont2 = UI.retornarString("Confirma la teua contrasenya: ");
            if (!cont.equals(cont2)) {
                System.out.println("ERROR: Les contrasenyes introduides no son iguals, torna a introduir-les.");
            }
        } while (!cont.equals(cont2));

        String poblacio = UI.retornarString("Introdueix la teua poblacio: ");
        LocalDate data = UI.retornarData("Introdueix la teua data de naiximent (dd/MM/yyyy): ");

        id = GestorFitxerUsuaris.contarUsuaris();

        Usuari user = new Usuari(id, nom, cognom, email, poblacio, data, cont2, Rol.NORMAL);

        GestorFitxerUsuaris.anyadirUsuari(user);

        System.out.println("Usuario creado correctamenete");
    }

    private static void iniciarSessio() {
        System.out.println("\n--- LOGIN ---");
        String correu = UI.retornarString("Introdueix el teu correu electronic: ");
        String contrasenya = UI.retornarString("Introedueix la teua contrasenya: ");

        ArrayList<Usuari> usuaris = GestorFitxerUsuaris.getUsuaris();

        for (Usuari usuari : usuaris) {
            if (usuari.getCorreu().equals(correu) && usuari.getContrasenya().equals(contrasenya)) {
                MenuLlistats.executarMenu(usuari);
                return;
            }
        }
    }
}
