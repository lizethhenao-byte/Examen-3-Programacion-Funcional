import java.io.BufferedReader;
import java.io.FileReader;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class Main {

    static class Registro {
        String ciudad;
        LocalDate fecha;
        double temperatura;

        public Registro(String ciudad, LocalDate fecha, double temperatura) {
            this.ciudad = ciudad;
            this.fecha = fecha;
            this.temperatura = temperatura;
        }
    }

    public static void main(String[] args) {
        String ruta = "../data/temperaturas.csv";
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        List<Registro> registros = cargarCSV(ruta, formato);

        Scanner sc = new Scanner(System.in);

        System.out.println("Ingrese fecha inicial (dd/MM/yyyy): ");
        LocalDate fechaIni = LocalDate.parse(sc.nextLine(), formato);

        System.out.println("Ingrese fecha final (dd/MM/yyyy): ");
        LocalDate fechaFin = LocalDate.parse(sc.nextLine(), formato);

        // ================================================================
        //   filter() → filtrar por rango   |   map() → identidad
        //   sorted() → ordenar por ciudad  |   collect() → agrupar
        // ================================================================
        Map<String, Double> promedios = registros.stream()
            .filter(r -> !r.fecha.isBefore(fechaIni) && !r.fecha.isAfter(fechaFin))    // filter()
            .sorted(Comparator.comparing(r -> r.ciudad))                               // sorted()
            .collect(Collectors.groupingBy(
                    r -> r.ciudad,
                    Collectors.averagingDouble(r -> r.temperatura)                     // collect()
            ));

        System.out.println("\nPROMEDIOS EN EL RANGO:");
        promedios.forEach((ciudad, prom) -> {
            int barras = (int) prom;
            System.out.printf("%-12s | %s (%.1f°C)%n", ciudad, "█".repeat(barras), prom);
        });

        // ================================================================
        //  Consulta para saber la ciudad más y menos calurosa en una fecha
        // ================================================================
        System.out.println("\nIngrese fecha a consultar (dd/MM/yyyy): ");
        LocalDate fechaConsulta = LocalDate.parse(sc.nextLine(), formato);

        List<Registro> enFecha = registros.stream()
            .filter(r -> r.fecha.equals(fechaConsulta))       // filter()
            .sorted(Comparator.comparing(r -> r.temperatura)) // sorted()
            .collect(Collectors.toList());                    // collect()

        if (enFecha.isEmpty()) {
            System.out.println("No hay datos para esa fecha.");
            return;
        }

        Registro min = enFecha.get(0);
        Registro max = enFecha.get(enFecha.size() - 1);

        System.out.println("\nResultados para " + fechaConsulta.format(formato));
        System.out.println("Ciudad más calurosa: " + max.ciudad + " (" + max.temperatura + "°C)");
        System.out.println("Ciudad menos calurosa: " + min.ciudad + " (" + min.temperatura + "°C)");
    }

    // ================================================================
    //   Método para cargar el CSV con programación funcional
    // ================================================================
    public static List<Registro> cargarCSV(String ruta, DateTimeFormatter formato) {
        List<Registro> lista = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(ruta))) {
            br.lines()
              .skip(1) // saltar encabezado
              .map(linea -> linea.split(","))                                                                 // map()
              .forEach(parts -> lista.add(
                      new Registro(
                              parts[0],
                              LocalDate.parse(parts[1], formato),
                              Double.parseDouble(parts[2])
                      )
              ));
        } catch (Exception e) {
            System.out.println("Error al leer el CSV: " + e.getMessage());
        }

        return lista;
    }
}
