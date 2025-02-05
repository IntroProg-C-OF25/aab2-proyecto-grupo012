/**
 *
 * @author Jorge Fernández & Francis Tapia
 */
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class SistemaAdmisionesUTPL {

    static ArrayList<Postulante> postulantes = new ArrayList<>();
    static Scanner tcl = new Scanner(System.in);
    static Random random = new Random();
    static final int CUPOS_QUIMICA = 80;
    static final int CUPOS_MEDICINA = 80;
    static final int PUNTAJE_MIN_QUIMICA = 80;
    static final int PUNTAJE_MIN_FISIO = 90;
    static final int PUNTAJE_MIN_MEDICINA = 85;

    public static void main(String[] args) {
        boolean salir = false;
        while (!salir) {
            mostrarMenu();
            int opcion = leerEntero();
            switch (opcion) {
                case 1 ->
                    registrarPostulante();
                case 2 ->
                    rendirExamen();
                case 3 ->
                    mostrarResultados();
                case 4 -> {
                    salir = true;
                    System.out.println("Saliendo del sistema...");
                }
                default ->
                    System.out.println("Opcion no valida. Intente nuevamente.");
            }
        }
    }

    public static void mostrarMenu() {
        System.out.println("\nSistema de Gestion de Admisiones UTPL");
        System.out.println("1. Registrar postulante");
        System.out.println("2. Rendir examen de admision");
        System.out.println("3. Mostrar resultados");
        System.out.println("4. Salir");
        System.out.print("Seleccione una opcion: ");
    }

    public static void registrarPostulante() {
        System.out.println("\n--- Registro de Postulante ---");
        System.out.print("Ingrese el nombre del postulante: ");
        String nombre = tcl.nextLine();
        System.out.println("Carreras disponibles: Quimica, Fisiorehabilitacion, Medicina");
        System.out.print("Ingrese la carrera a la que desea postular: ");
        String carrera = tcl.nextLine();
        if (Arrays.asList("Quimica", "Fisiorehabilitacion", "Medicina").contains(carrera)) {
            postulantes.add(new Postulante(nombre, carrera));
            System.out.println("Postulante registrado exitosamente.");
        } else {
            System.out.println("Carrera no valida. Intente nuevamente.");
        }
    }

    public static void rendirExamen() {
        System.out.println("\n--- Rendir Examen ---");
        System.out.print("Ingrese el nombre del postulante: ");
        String nombre = tcl.nextLine();

        for (Postulante p : postulantes) {
            if (p.nombre.equalsIgnoreCase(nombre)) {
                if (p.puntaje > 0) {
                    System.out.println("El postulante ya rindio el examen.");
                } else {
                    int puntaje = random.nextInt(101);
                    p.puntaje = puntaje;
                    System.out.println("Puntaje generado aleatoriamente: " + puntaje);

                    if (p.carrera.equals("Medicina")) {
                        p.puntosAdicionales += preguntar("¿Fue abanderado? (s/n): ", 5);
                        p.puntosAdicionales += preguntar("¿Tiene bachillerato afin? (s/n): ", 2);
                        p.puntosAdicionales += preguntar("¿Capacidad especial menor al 35%? (s/n): ", 1);
                    }
                    System.out.println("Puntaje registrado exitosamente.");
                }
                return;
            }
        }
        System.out.println("Postulante no encontrado. Verifique el nombre.");
    }

    public static void mostrarResultados() {
        System.out.println("\n--- Resultados de Admisión ---");
        List<Postulante> admitidosQuimica = filtrarPostulantes("Quimica", PUNTAJE_MIN_QUIMICA, CUPOS_QUIMICA);
        List<Postulante> admitidosFisio = filtrarPostulantes("Fisiorehabilitacion", PUNTAJE_MIN_FISIO, Integer.MAX_VALUE);
        List<Postulante> admitidosMedicina = filtrarPostulantes("Medicina", PUNTAJE_MIN_MEDICINA, CUPOS_MEDICINA);

        imprimirAdmitidos("Quimica", admitidosQuimica);
        imprimirAdmitidos("Fisiorehabilitacion", admitidosFisio);
        imprimirAdmitidos("Medicina", admitidosMedicina);
    }

    public static List<Postulante> filtrarPostulantes(String carrera, int puntajeMinimo, int cupo) {
        List<Postulante> filtrados = new ArrayList<>();
        for (Postulante p : postulantes) {
            if (p.carrera.equals(carrera) && p.puntaje >= puntajeMinimo) {
                filtrados.add(p);
            }
        }
        filtrados.sort(Comparator.comparingInt((Postulante p) -> p.puntaje + p.puntosAdicionales).reversed());
        return filtrados.subList(0, Math.min(filtrados.size(), cupo));
    }

    public static void imprimirAdmitidos(String carrera, List<Postulante> admitidos) {
        System.out.println("\nAdmitidos en " + carrera + ":");
        if (admitidos.isEmpty()) {
            System.out.println("No hay admitidos.");
        } else {
            for (Postulante p : admitidos) {
                System.out.println(p.nombre + " - Puntaje: " + p.puntaje + " (Adicional: " + p.puntosAdicionales + ")");
            }
        }
    }

    public static int leerEntero() {
        while (true) {
            try {
                return Integer.parseInt(tcl.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.print("Entrada invalida. Intente de nuevo: ");
            }
        }
    }

    public static int preguntar(String mensaje, int puntos) {
        System.out.print(mensaje);
        return tcl.nextLine().equalsIgnoreCase("s") ? puntos : 0;
    }
}

class Postulante {

    String nombre, carrera;
    int puntaje = 0, puntosAdicionales = 0;

    Postulante(String nombre, String carrera) {
        this.nombre = nombre;
        this.carrera = carrera;
    }
}
