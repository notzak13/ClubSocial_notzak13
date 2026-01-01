package Club;

import java.util.Scanner;
import java.util.ArrayList;
import Club.Socio.Tipo;

public class Main {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        Club club = new Club();
        int op = 0;

        do {
            System.out.println("\n--- MENU CLUB SOCIAL ---");
            System.out.println("1. Afiliar un socio al club.");
            System.out.println("2. Registrar una persona autorizada por un socio.");
            System.out.println("3. Pagar una factura.");
            System.out.println("4. Registrar un consumo en la cuenta de un socio.");
            System.out.println("5. Aumentar fondos de la cuenta de un socio.");
            System.out.println("6. Ver Total Consumos de un Socio.");
            System.out.println("7. Validar Eliminación de Socio.");
            System.out.println("8. Salir");
            System.out.print("Ingrese una opcion: ");

            try {
                String input = sc.nextLine();
                op = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Opción inválida, ingrese un número.");
                continue;
            }

            switch (op) {
                case 1: {
                    System.out.print("Ingrese Cédula: ");
                    String cedula = sc.nextLine();
                    System.out.print("Ingrese Nombre: ");
                    String nombre = sc.nextLine();
                    System.out.println("Seleccione Tipo (1. VIP, 2. REGULAR): ");
                    String tipoStr = sc.nextLine();
                    Tipo tipo = (tipoStr.equals("1") || tipoStr.equalsIgnoreCase("VIP")) ? Tipo.VIP : Tipo.REGULAR;
                    club.afiliarSocio(cedula, nombre, tipo);
                } break;

                case 2: {
                    System.out.print("Ingrese Cédula del Socio: ");
                    String cedula = sc.nextLine();
                    System.out.print("Ingrese Nombre del Autorizado: ");
                    String autorizado = sc.nextLine();
                    club.agregarAutorizadoSocio(cedula, autorizado);
                } break;

                case 3: {
                    System.out.print("Ingrese Cédula del Socio: ");
                    String cedula = sc.nextLine();
                    Socio s = club.buscarSocio(cedula);
                    if (s != null) {
                        ArrayList<Factura> facturas = s.darFacturas();
                        if (facturas.isEmpty()) {
                            System.out.println("El socio no tiene facturas pendientes.");
                        } else {
                            for (int i = 0; i < facturas.size(); i++) {
                                System.out.println(i + ". " + facturas.get(i).toString());
                            }
                            System.out.print("Ingrese el índice de factura: ");
                            try {
                                int indice = Integer.parseInt(sc.nextLine());
                                club.pagarFacturaSocio(cedula, indice);
                            } catch (NumberFormatException e) {
                                System.out.println("Índice inválido.");
                            }
                        }
                    } else {
                        System.out.println("Socio no encontrado.");
                    }
                } break;

                case 4: {
                    System.out.print("Cédula: "); String id = sc.nextLine();
                    System.out.print("Nombre de quien consume: "); String nom = sc.nextLine();
                    System.out.print("Concepto: "); String con = sc.nextLine();
                    System.out.print("Valor: ");
                    try {
                        double v = Double.parseDouble(sc.nextLine());
                        club.registrarConsumo(id, nom, con, v);
                    } catch (Exception e) { System.out.println("Monto inválido."); }
                } break;

                case 5: {
                    System.out.print("Cédula: "); String id = sc.nextLine();
                    System.out.print("Monto: ");
                    try {
                        double m = Double.parseDouble(sc.nextLine());
                        club.aumentarFondosSocio(id, m);
                    } catch (Exception e) { System.out.println("Monto inválido."); }
                } break;

                case 6: {
                    System.out.print("Cédula: "); String id = sc.nextLine();
                    double t = club.totalConsumosSocio(id);
                    if (t != -1) System.out.println("Total consumos: $" + t);
                } break;

                case 7: {
                    System.out.print("Cédula: "); String id = sc.nextLine();
                    if (club.sePuedeEliminarSocio(id)) System.out.println("SÍ se puede eliminar.");
                    else System.out.println("NO se puede eliminar.");
                } break;
            }
        } while (op != 8);
        sc.close();
    }
}