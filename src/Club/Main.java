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
            System.out.println("1. Afiliar socio");
            System.out.println("2. Agregar autorizado");
            System.out.println("3. Pagar factura");
            System.out.println("4. Registrar consumo");
            System.out.println("5. Aumentar fondos");
            System.out.println("6. Total consumos socio");
            System.out.println("7. Eliminar socio");
            System.out.println("8. Salir");
            System.out.print("Seleccione: ");

            try {
                op = Integer.parseInt(sc.nextLine());
                switch (op) {
                    case 1:
                        System.out.print("Cédula: "); String c1 = sc.nextLine();
                        System.out.print("Nombre: "); String n1 = sc.nextLine();
                        System.out.print("Tipo (1.VIP, 2.Regular): "); String t1 = sc.nextLine();
                        Tipo tipo = t1.equals("1") ? Tipo.VIP : Tipo.REGULAR;
                        club.afiliarSocio(c1, n1, tipo);
                        System.out.println("Afiliación exitosa.");
                        break;
                    case 2:
                        System.out.print("Cédula Socio: "); String c2 = sc.nextLine();
                        System.out.print("Nombre Autorizado: "); String n2 = sc.nextLine();
                        club.agregarAutorizadoSocio(c2, n2);
                        System.out.println("Autorizado agregado.");
                        break;
                    case 3:
                        System.out.print("Cédula: "); String c3 = sc.nextLine();
                        Socio s = club.buscarSocio(c3);
                        if (s != null) {
                            ArrayList<Factura> facts = s.darFacturas();
                            if (facts.isEmpty()) throw new Exception("No hay facturas pendientes.");
                            for (int i = 0; i < facts.size(); i++) System.out.println(i + ". " + facts.get(i));
                            System.out.print("Índice a pagar: ");
                            int idx = Integer.parseInt(sc.nextLine());
                            club.pagarFacturaSocio(c3, idx);
                            System.out.println("Pago exitoso.");
                        } else throw new Exception("Socio no encontrado.");
                        break;
                    case 4:
                        System.out.print("Cédula: "); String c4 = sc.nextLine();
                        System.out.print("Consumidor: "); String n4 = sc.nextLine();
                        System.out.print("Concepto: "); String con = sc.nextLine();
                        System.out.print("Valor: "); double v4 = Double.parseDouble(sc.nextLine());
                        club.registrarConsumo(c4, n4, con, v4);
                        System.out.println("Consumo registrado.");
                        break;
                    case 5:
                        System.out.print("Cédula: "); String c5 = sc.nextLine();
                        System.out.print("Monto: "); double v5 = Double.parseDouble(sc.nextLine());
                        club.aumentarFondosSocio(c5, v5);
                        System.out.println("Fondos actualizados.");
                        break;
                    case 6:
                        System.out.print("Cédula: "); String c6 = sc.nextLine();
                        System.out.println("Total: $" + club.totalConsumosSocio(c6));
                        break;
                    case 7:
                        System.out.print("Cédula: "); String c7 = sc.nextLine();
                        club.eliminarSocio(c7);
                        System.out.println("Socio eliminado.");
                        break;
                }
            } catch (NumberFormatException e) {
                System.out.println("Error: Ingrese un número válido.");
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        } while (op != 8);
        sc.close();
    }
}