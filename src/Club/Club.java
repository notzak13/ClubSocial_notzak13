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

    public void afiliarSocio(String pCedula, String pNombre, Tipo pTipo) throws Exception {
        if (buscarSocio(pCedula) != null) {
            throw new Exception("Error: Ya existe un socio con esa cédula.");
        }
        if (pTipo == Tipo.VIP && contarSociosVIP() >= MAXIMO_VIP) {
            throw new Exception("Error: No se permiten más socios VIP (Máximo " + MAXIMO_VIP + ").");
        }
        this.socios.add(new Socio(pCedula, pNombre, pTipo));
    }

    public int contarSociosVIP() {
        int conteo = 0;
        for (Socio s : this.socios) {
            if (s.darTipo() == Tipo.VIP) conteo++;
        }
        return conteo;
    }

    public double totalConsumosSocio(String pCedula) throws Exception {
        Socio s = buscarSocio(pCedula);
        if (s == null) throw new Exception("Error: No existe el socio.");

        double total = 0;
        for (Factura f : s.darFacturas()) {
            total += f.darValor();
        }
        return total;
    }

    public void sePuedeEliminarSocio(String pCedula) throws Exception {
        Socio s = buscarSocio(pCedula);
        if (s == null) throw new Exception("Error: No existe un socio con la cédula recibida.");
        if (s.darTipo() == Tipo.VIP) throw new Exception("Error: No se pueden eliminar socios VIP.");
        if (!s.darFacturas().isEmpty()) throw new Exception("Error: El socio tiene facturas pendientes.");
        if (s.darAutorizados().size() > 1) throw new Exception("Error: El socio tiene más de un autorizado.");
    }

    public void eliminarSocio(String pCedula) throws Exception {
        sePuedeEliminarSocio(pCedula);
        Socio s = buscarSocio(pCedula);
        this.socios.remove(s);
    }

    public void registrarConsumo(String pCedula, String pNombre, String pConcepto, double pValor) throws Exception {
        Socio s = buscarSocio(pCedula);
        if (s == null) throw new Exception("Error: Socio no encontrado.");
        s.registrarConsumo(pNombre, pConcepto, pValor);
    }

    public void pagarFacturaSocio(String pCedula, int pIndice) throws Exception {
        Socio s = buscarSocio(pCedula);
        if (s == null) throw new Exception("Error: Socio no encontrado.");
        s.pagarFactura(pIndice);
    }

    public void aumentarFondosSocio(String pCedula, double pValor) throws Exception {
        Socio s = buscarSocio(pCedula);
        if (s == null) throw new Exception("Error: Socio no encontrado.");
        s.aumentarFondos(pValor);
    }

    public void agregarAutorizadoSocio(String pCedula, String pNombre) throws Exception {
        Socio s = buscarSocio(pCedula);
        if (s == null) throw new Exception("Error: Socio no encontrado.");
        s.agregarAutorizado(pNombre);
    }
}