package Club;

import java.util.ArrayList;
import Club.Socio.Tipo;

public class Club {
    public final static int MAXIMO_VIP = 3;
    private ArrayList<Socio> socios;

    public Club() {
        this.socios = new ArrayList<>();
    }

    public Socio buscarSocio(String pCedulaSocio) {
        for (Socio s : this.socios) {
            if (s.darCedula().equals(pCedulaSocio)) return s;
        }
        return null;
    }

    public void afiliarSocio(String pCedula, String pNombre, Tipo pTipo) {
        Socio s = buscarSocio(pCedula);
        if (s != null) {
            System.out.println("El socio ya existe");
        } else if (pTipo == Tipo.VIP && contarSociosVIP() >= MAXIMO_VIP) {
            System.out.println("El club en el momento no acepta más socios VIP");
        } else {
            this.socios.add(new Socio(pCedula, pNombre, pTipo));
        }
    }

    public int contarSociosVIP() {
        int conteo = 0;
        for (Socio s : this.socios) {
            if (s.darTipo() == Tipo.VIP) conteo++;
        }
        return conteo;
    }

    public double totalConsumosSocio(String pCedula) {
        Socio s = buscarSocio(pCedula);
        if (s == null) {
            System.out.println("No existe socio con la cédula dada");
            return -1;
        }
        double total = 0;
        for (Factura f : s.darFacturas()) {
            total += f.darValor();
        }
        return total;
    }

    public boolean sePuedeEliminarSocio(String pCedula) {
        Socio s = buscarSocio(pCedula);
        if (s == null) return false;
        if (s.darTipo() == Tipo.VIP) return false;
        if (!s.darFacturas().isEmpty()) return false;
        if (s.darAutorizados().size() > 1) return false;

        return true;
    }

    public void eliminarSocio(String pCedula) {
        if (sePuedeEliminarSocio(pCedula)) {
            Socio s = buscarSocio(pCedula);
            this.socios.remove(s);
            System.out.println("Socio eliminado exitosamente.");
        } else {
            System.out.println("No se cumplen las condiciones para eliminar al socio.");
        }
    }

    public void registrarConsumo(String pCedula, String pNombre, String pConcepto, double pValor) {
        Socio s = buscarSocio(pCedula);
        if (s != null) s.registrarConsumo(pNombre, pConcepto, pValor);
    }

    public void pagarFacturaSocio(String pCedula, int pIndice) {
        Socio s = buscarSocio(pCedula);
        if (s != null) s.pagarFactura(pIndice);
    }

    public void aumentarFondosSocio(String pCedula, double pValor) {
        Socio s = buscarSocio(pCedula);
        if (s != null) s.aumentarFondos(pValor);
    }

    public void agregarAutorizadoSocio(String pCedula, String pNombre) {
        Socio s = buscarSocio(pCedula);
        if (s != null) s.agregarAutorizado(pNombre);
    }

    public void eliminarAutorizadoSocio(String pCedulaSocio, String pNombreAutorizado) {
        Socio s = buscarSocio(pCedulaSocio);
        if (s != null) s.eliminarAutorizado(pNombreAutorizado);
    }
}