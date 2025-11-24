import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) throws IOException {

        Scanner sc = new Scanner(System.in);
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        // 1. Cargar CSV
        List<Registro> datos = Files.lines(Paths.get("data/temperaturas.csv"))
                .skip(1) // saltar encabezado
                .map(linea -> linea.split(","))
                .map(arr -> new Registro(
                        arr[0],
                        LocalDate.parse(arr[1], formato),
                        Double.parseDouble(arr[2])
                ))
                .collect(Collectors.toList());

        System.out.println("=== EXAMEN 3 - PROGRAMACIÓN FUNCIONAL ===");

        // 2. Selección de rango
        System.out.print("Ingrese fecha inicial (dd/MM/yyyy): ");
        LocalDate f1 = LocalDate.parse(sc.nextLine(), formato);

        System.out.print("Ingrese fecha final (dd/MM/yyyy): ");
        LocalDate f2 = LocalDate.parse(sc.nextLine(), formato);

        // Filtrar rango
        List<Registro> rango = datos.stream()
                .filter(r -> !r.fecha.isBefore(f1) && !r.fecha.isAfter(f2))
                .collect(Collectors.toList());

        // Agrupar por ciudad y hacer promedios
        Map<String, Double> promedios = rango.stream()
                .collect(Collectors.groupingBy(
                        r -> r.ciudad,
                        Collectors.averagingDouble(r -> r.temp)
                ));

        System.out.println("\nPromedio de temperaturas por ciudad:");
        promedios.forEach((c, t) -> System.out.println(c + ": " + t));

        // (La gráfica de barras se representa textual)
        System.out.println("\nGráfica de barras (texto):\n");
        promedios.forEach((c, t) -> {
            int barras = (int) Math.round(t);
            System.out.println(c + " | " + "█".repeat(barras));
        });

        // 3. Ciudad más y menos calurosa para una fecha específica
        System.out.print("\nIngrese una fecha para comparar (dd/MM/yyyy): ");
        LocalDate fBuscar = LocalDate.parse(sc.nextLine(), formato);

        List<Registro> dia = datos.stream()
                .filter(r -> r.fecha.equals(fBuscar))
                .collect(Collectors.toList());

        if (dia.isEmpty()) {
            System.out.println("No hay datos para esa fecha.");
        } else {
            Registro max = dia.stream().max(Comparator.comparingDouble(r -> r.temp)).get();
            Registro min = dia.stream().min(Comparator.comparingDouble(r -> r.temp)).get();

            System.out.println("\nCiudad más calurosa ese día: " + max.ciudad + " (" + max.temp + "°C)");
            System.out.println("Ciudad menos calurosa ese día: " + min.ciudad + " (" + min.temp + "°C)");
        }

    }

    static class Registro {
        String ciudad;
        LocalDate fecha;
        double temp;

        public Registro(String c, LocalDate f, double t) {
            this.ciudad = c;
            this.fecha = f;
            this.temp = t;
        }
    }
}
