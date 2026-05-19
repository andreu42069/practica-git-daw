package com.projecte.menus;

import com.projecte.entitats.Actor;
import com.projecte.entitats.Director;
import com.projecte.entitats.ListItem;
import com.projecte.entitats.Pelicula;
import com.projecte.entitats.Usuari;
import com.projecte.main.GestorLlistes;
import java.util.ArrayList;

public class MenuLlistats {

    public static void executarMenu(Usuari usuari) {
        GestorFitxerUsuaris.actualitzarLlistatsLocals(usuari);
        GestorLlistes.actualitzarLlistatsLocal();
        validarUsuariLlistesPersonals(usuari);
        System.out.println("Benvingut de nou " + usuari.getNom() + " " + usuari.getCognoms());
        String opcion;
        do {
            mostrarMenu();
            opcion = UI.retornarString("Introdueix una opció: ");
            switch (opcion) {
                case "1":
                    veureLlistat(usuari);
                    break;
                case "2":
                    afegirElementLlistat(usuari);
                    break;
                case "0":
                    System.out.println("Tancant sessió...");
                    break;
                default:
                    System.out.println("ERROR: Valor introduit (" + opcion + ") no reconegut.");
                    break;
            }
        } while (!opcion.equals("0"));
    }

    private static void mostrarMenu() {
        System.out.println("\n--- MENÚ PRINCIPAL ---");
        System.out.println("1.- Veure llistat");
        System.out.println("2.- Afegir elements a un llistat");
        System.out.println("0.- Tancar sessió");
    }

    private static void veureLlistat(Usuari usuari) {
        System.out.println("\n--- ELIGEIX L'OPERACIÓ A FER ---");
        System.out.println("1.- Veure llistat de películes personal");
        System.out.println("2.- Veure llistat de directors personal");
        System.out.println("3.- Veure llistat de actors personal");
        System.out.println("4.- Veure llistat de películes global");
        System.out.println("5.- Veure llistat de directors global");
        System.out.println("6.- Veure llistat de actors global");
        System.out.println("0.- Cancelar operació");
        String opcion = UI.retornarString("Introdueix què llistat dessitges veure: ");
        ArrayList<ListItem> llista = null;
        switch (opcion) {
            case "1":
                llista = usuari.getLlistaPelis();
                break;
            case "2":
                llista = usuari.getLlistaDire();
                break;
            case "3":
                llista = usuari.getLlistaActor();
                break;
            case "4":
                llista = GestorLlistes.getLlistaPelis();
                break;
            case "5":
                llista = GestorLlistes.getLlistaDire();
                break;
            case "6":
                llista = GestorLlistes.getLlistaActor();
                break;
            case "0":
                System.out.println("Se cancelarà l'operació");
                break;
            default:
                System.out.println("ERROR: Valor introduit (" + opcion + ") no reconegut. Es cancelarà l'operació");
                return;
        }

        if (llista != null && llista.size() > 0) {
            mostrarLlista(llista);
            do {
                opcion = UI.retornarString("Vols veure detalls de un element en específic (s/n)?: ");

            } while (!opcion.equalsIgnoreCase("s") && !opcion.equalsIgnoreCase("n"));

            if (opcion.equalsIgnoreCase("s")) {
                retornarElementoEnLista(llista).mostrarDetalles();
            }
        } else {
            System.out.println("Llistat vuit.");
        }
    }

    private static void afegirElementLlistat(Usuari usuari) {
        System.out.println("\n--- ELIGEIX L'OPERACIÓ A FER ---");
        System.out.println("1.- Afegir element a películes personal");
        System.out.println("2.- Afegir element a directors personal");
        System.out.println("3.- Afegir element a actors personal");
        System.out.println("4.- Afegir element a películes global");
        System.out.println("5.- Afegir element a directors global");
        System.out.println("6.- Afegir element a actors global");
        System.out.println("0.- Cancelar operació");
        String opcion = UI.retornarString("Introdueix què llistat dessitges editar: ");
        switch (opcion) {
            case "1":
                try {
                    mostrarLlista(GestorLlistes.getLlistaPelis());
                    usuari.afegirPeli(retornarElementoEnLista(GestorLlistes.getLlistaPelis()));
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
                break;
            case "2":
                try {
                    mostrarLlista(GestorLlistes.getLlistaDire());
                    usuari.afegirDire(retornarElementoEnLista(GestorLlistes.getLlistaDire()));
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
                break;
            case "3":
                try {
                    mostrarLlista(GestorLlistes.getLlistaActor());
                    usuari.afegirAct(retornarElementoEnLista(GestorLlistes.getLlistaActor()));
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
                break;
            case "4":
                try {
                    GestorLlistes.afegirPeli(new Pelicula(UI.retornarString("Escribe el nombre de la película: "), idMasAlta(usuari.getLlistaPelis())));
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
                break;
            case "5":
                try {
                    GestorLlistes.afegirDire(new Director(UI.retornarString("Escribe el nombre del director: "), idMasAlta(usuari.getLlistaDire())));
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
                break;
            case "6":
                try {
                    GestorLlistes.afegirAct(new Actor(UI.retornarString("Escribe el nombre del actor: "), idMasAlta(usuari.getLlistaActor())));
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
                break;
            case "0":
                System.out.println("Se cancelarà l'operació");
                break;
            default:
                System.out.println("ERROR: Valor introduit (" + opcion + ") no reconegut. Es cancelarà l'operació");
                break;
        }
        GestorFitxerUsuaris.actualitzarLlistatsFitxers(usuari);
        GestorLlistes.actualitzarLlistatsFitxer();
        GestorFitxerUsuaris.actualitzarLlistatsLocals(usuari);
        GestorLlistes.actualitzarLlistatsLocal();
    }

    private static <T> void mostrarLlista(ArrayList<T> llista) {
        int contador = 0;
        for (T element : llista) {
            System.out.println(++contador + ") " + element);
        }
    }

    private static <T> T retornarElementoEnLista(ArrayList<T> llista) {
        if (llista.size() <= 0) {
            System.out.println("ERROR: No se puede elegir un elemento de una lista vacía, deteniendo acción.");
        }
        int opcion;
        do {
            opcion = UI.retornarInt("Introdueix un objecte a elegir: ");
            if (opcion < 1 || opcion > llista.size()) {
                System.out.println("ERROR: Elige un elemento de entre 1 y " + llista.size());
            }
        } while (opcion < 1 || opcion > llista.size());
        return llista.get(opcion - 1);
    }

    static void validarUsuariLlistesPersonals(Usuari usuari) {
        for (ListItem item : usuari.getLlistaPelis()) {
            if (usuari.buscarElementenLlista(item.getId(), GestorLlistes.getLlistaPelis()) == null) {
                usuari.getLlistaPelis().remove(item);
            }
        }
        for (ListItem item : usuari.getLlistaActor()) {
            if (usuari.buscarElementenLlista(item.getId(), GestorLlistes.getLlistaActor()) == null) {
                usuari.getLlistaActor().remove(item);
            }
        }
        for (ListItem item : usuari.getLlistaDire()) {
            if (usuari.buscarElementenLlista(item.getId(), GestorLlistes.getLlistaDire()) == null) {
                usuari.getLlistaDire().remove(item);
            }
        }
    }

    private static int idMasAlta(ArrayList<ListItem> llista) {
        int idMax = 0;
        for (ListItem item : llista) {
            if (item.getId() > idMax) {
                idMax = item.getId();
            }
        }
        return idMax + 1;
    }
}
