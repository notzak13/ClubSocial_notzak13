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
        this.fondos = (pTipo == Tipo.VIP) ? FONDOS_INICIALES_VIP : FONDOS_INICIALES_REGULARES;
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

    public void aumentarFondos(double pFondos) throws Exception {
        double maximo = (this.tipoSubscripcion == Tipo.VIP) ? MONTO_MAXIMO_VIP : MONTO_MAXIMO_REGULARES;
        if (this.fondos + pFondos > maximo) {
            throw new Exception("Error: No se pueden exceder los fondos máximos permitidos ($" + maximo + ").");
        }
        this.fondos += pFondos;
    }

    public void registrarConsumo(String pNombre, String pConcepto, double pValor) throws Exception {
        if (!pNombre.equals(this.nombre) && !existeAutorizado(pNombre)) {
            throw new Exception("Error: El consumidor no es el socio ni un autorizado.");
        }
        if (pValor > this.fondos) {
            throw new Exception("Error: Fondos insuficientes para este consumo.");
        }
        this.facturas.add(new Factura(pNombre, pConcepto, pValor));
    }

}

    public void agregarAutorizado(String pNombreAutorizado) throws Exception {
        if (pNombreAutorizado.equals(this.nombre)) {
            throw new Exception("Error: El socio no puede ser su propio autorizado.");
        }
        if (this.fondos <= 0) {
            throw new Exception("Error: Socio sin fondos para agregar autorizados.");
        }
        if (existeAutorizado(pNombreAutorizado)) {
            throw new Exception("Error: El autorizado ya existe.");
        }
        this.autorizados.add(pNombreAutorizado);
    }

    public void eliminarAutorizado(String pNombreAutorizado) throws Exception {
        if (tieneFacturaAsociada(pNombreAutorizado)) {
            throw new Exception("Error: " + pNombreAutorizado + " tiene facturas pendientes.");
        }
        if (!this.autorizados.remove(pNombreAutorizado)) {
            throw new Exception("Error: El autorizado no existe.");
        }
    }

    public void pagarFactura(int pIndiceFactura) throws Exception {
        if (pIndiceFactura < 0 || pIndiceFactura >= this.facturas.size()) {
            throw new Exception("Error: Indice de factura inválido.");
        }
        Factura f = this.facturas.get(pIndiceFactura);
        if (f.darValor() > this.fondos) {
            throw new Exception("Error: Fondos insuficientes para pagar la factura.");
        }
        this.fondos -= f.darValor();
        this.facturas.remove(pIndiceFactura);
    }
}