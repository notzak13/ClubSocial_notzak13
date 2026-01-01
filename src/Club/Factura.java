package Club;

public class Factura {
    private String concepto;
    private double valor;
    private String nombre;

    public Factura(String pNombre, String pConcepto, double pValor) {
        this.nombre = pNombre;
        this.concepto = pConcepto;
        this.valor = pValor;
    }

    public String darConcepto() { return this.concepto; }
    public double darValor() { return this.valor; }
    public String darNombre() { return this.nombre; }

    @Override
    public String toString() {
        return this.concepto + " $" + this.valor + " (" + this.nombre + ")";
    }
}