package Club;

import java.util.ArrayList;

public class Socio {
    public enum Tipo { VIP, REGULAR }

    public final static double FONDOS_INICIALES_REGULARES = 50;
    public final static double FONDOS_INICIALES_VIP = 100;
    public final static double MONTO_MAXIMO_REGULARES = 1000;
    public final static double MONTO_MAXIMO_VIP = 5000;

    private String cedula;
    private String nombre;
    private double fondos;
    private Tipo tipoSubscripcion;
    private ArrayList<Factura> facturas;
    private ArrayList<String> autorizados;

    public Socio(String pCedula, String pNombre, Tipo pTipo) {
        this.cedula = pCedula;
        this.nombre = pNombre;
        this.tipoSubscripcion = pTipo;

        if (this.tipoSubscripcion == Tipo.VIP) {
            this.fondos = FONDOS_INICIALES_VIP;
        } else {
            this.fondos = FONDOS_INICIALES_REGULARES;
        }

        this.facturas = new ArrayList<>();
        this.autorizados = new ArrayList<>();
    }

    public String darNombre() { return this.nombre; }
    public String darCedula() { return this.cedula; }
    public double darFondos() { return this.fondos; }
    public Tipo darTipo() { return this.tipoSubscripcion; }
    public ArrayList<Factura> darFacturas() { return this.facturas; }
    public ArrayList<String> darAutorizados() { return this.autorizados; }

    public boolean existeAutorizado(String pNombre) {
        return this.autorizados.contains(pNombre);
    }

    public boolean tieneFacturaAsociada(String pNombre) {
        for (Factura f : this.facturas) {
            if (f.darNombre().equals(pNombre)) return true;
        }
        return false;
    }

    public void aumentarFondos(double pFondos) {
        double maximo = (this.tipoSubscripcion == Tipo.VIP) ? MONTO_MAXIMO_VIP : MONTO_MAXIMO_REGULARES;
        if (this.fondos + pFondos <= maximo) {
            this.fondos += pFondos;
        } else {
            System.out.println("Con este monto se excederían los fondos máximos");
        }
    }

    public void registrarConsumo(String pNombre, String pConcepto, double pValor) {
        if (pValor <= this.fondos) {
            this.facturas.add(new Factura(pNombre, pConcepto, pValor));
        } else {
            System.out.println("El socio no posee fondos suficientes para este consumo");
        }
    }

    public void agregarAutorizado(String pNombreAutorizado) {
        if (pNombreAutorizado.equals(this.nombre)) {
            System.out.println("No puede agregar el socio como autorizado.");
        } else if (this.fondos > 0 && !existeAutorizado(pNombreAutorizado)) {
            this.autorizados.add(pNombreAutorizado);
        } else if (this.fondos <= 0) {
            System.out.println("El socio no tiene fondos para financiar un nuevo autorizado.");
        } else if (existeAutorizado(pNombreAutorizado)) {
            System.out.println("El autorizado ya existe.");
        }
    }

    public void eliminarAutorizado(String pNombreAutorizado) {
        if (tieneFacturaAsociada(pNombreAutorizado)) {
            System.out.println(pNombreAutorizado + " tiene una factura sin pagar.");
        } else {
            boolean eliminado = this.autorizados.remove(pNombreAutorizado);
            if (!eliminado) {
                System.out.println("El autorizado no existe.");
            }
        }
    }

    public void pagarFactura(int pIndiceFactura) {
        if (pIndiceFactura >= 0 && pIndiceFactura < this.facturas.size()) {
            Factura f = this.facturas.get(pIndiceFactura);
            if (f.darValor() <= this.fondos) {
                this.fondos -= f.darValor();
                this.facturas.remove(pIndiceFactura);
            } else {
                System.out.println("El socio no posee fondos suficientes para pagar esta factura");
            }
        }
    }
}