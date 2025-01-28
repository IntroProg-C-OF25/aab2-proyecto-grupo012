/**
 *
 * @author Jorge Fernández & Francis Tapia
 */
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
public class SistemaAdmisionesUTPL {
    static ArrayList<String[]> postulantes;
    static Scanner tcl;
    public static void main(String[] args) {
        postulantes = new ArrayList<>();
        Scanner tcl = new Scanner(System.in);
        generarPostulantesAleatorios();
        boolean salir = false;

        while (!salir) {
            mostrarMenu();
            int opcion = tcl.nextInt();

            switch (opcion) {
                case 1:
                    registrarPostulante();
                    break;
                case 2:
                    rendirExamen();
                    break;
                case 3:
                    mostrarResultados();
                    break;
                case 4:
                    salir = true;
                    System.out.println("Saliendo del sistema...");
                    break;
                default:
                    System.out.println("Opción no válida. Intente nuevamente.");
            }
        }
    }
    public static void generarPostulantesAleatorios() {
        Random random = new Random();
        String[] carreras = {"Química", "Fisiorehabilitación", "Medicina"};

        for (int i = 0; i < 20; i++) {
            String nombre = generarNombreAleatorio(random);
            String carrera = carreras[random.nextInt(carreras.length)];
            postulantes.add(new String[]{nombre, carrera, "0"});
        }
    }
    public static String generarNombreAleatorio(Random random) {
        String letras = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        int longitudNombre = random.nextInt(5) + 5;
        StringBuilder nombre = new StringBuilder();

        for (int i = 0; i < longitudNombre; i++) {
            char letra = letras.charAt(random.nextInt(letras.length()));
            nombre.append(letra);
        }
        return nombre.toString();
    }
    public static void mostrarMenu() {
        System.out.println("\nSistema de Gestión de Admisiones UTPL");
        System.out.println("1. Registrar postulante");
        System.out.println("2. Rendir examen de admisión");
        System.out.println("3. Mostrar resultados");
        System.out.println("4. Salir");
        System.out.print("Seleccione una opción: ");
    }
    public static void registrarPostulante() {
        System.out.println("\n--- Registro de Postulante ---");
        System.out.print("Ingrese el nombre del postulante: ");
        String nombre = tcl.next();
        System.out.println("Carreras disponibles: Química, Fisiorehabilitación, Medicina");
        System.out.print("Ingrese la carrera a la que desea postular: ");
        String carrera = tcl.next();

        if (carrera.equalsIgnoreCase("Química") || carrera.equalsIgnoreCase("Fisiorehabilitación") || carrera.equalsIgnoreCase("Medicina")) {
            postulantes.add(new String[]{nombre, carrera, "0"});
            System.out.println("Postulante registrado exitosamente.");
        } else {
            System.out.println("Carrera no válida. Intente nuevamente.");
        }
    }
    public static void rendirExamen() {
        System.out.println("\n--- Rendir Examen ---");
        System.out.print("Ingrese el nombre del postulante: ");
        String nombre = tcl.nextLine();
        boolean encontrado = false;
        for (String[] p : postulantes) {
            if (p[0].equalsIgnoreCase(nombre)) {
                encontrado = true;
                if (!p[2].equals("0")) {
                    System.out.println("El postulante ya rindió el examen.");
                } else {
                    System.out.print("Ingrese el puntaje obtenido (0-100): ");
                    double puntaje = tcl.nextDouble();

                    if (puntaje >= 0 && puntaje <= 100) {
                        p[2] = String.valueOf(puntaje);
                        System.out.println("Puntaje registrado exitosamente.");
                    } else {
                        System.out.println("Puntaje no válido. Debe estar entre 0 y 100.");
                    }
                }
                break;
            }
        }
        if (!encontrado) {
            System.out.println("Postulante no encontrado. Verifique el nombre.");
        }
    }
    public static void mostrarResultados() {
        System.out.println("\n--- Resultados de Admisión ---");
        if (postulantes.isEmpty()) {
            System.out.println("No hay postulantes registrados.");
        } else {
            for (String[] p : postulantes) {
                System.out.println("Nombre: " + p[0] + ", Carrera: " + p[1] + ", Puntaje: " + p[2]);
            }
        }
    }
}
