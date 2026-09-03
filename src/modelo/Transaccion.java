package modelo;

import java.util.Date;

/**
 * ╔══════════════════════════════════════════════════╗
 * ║  CLASE: Transaccion                              ║
 * ║  PAQUETE: modelo                                 ║
 * ║  PASO 5 del desarrollo                           ║
 * ╠══════════════════════════════════════════════════╣
 * ║  CONCEPTO: Clase PADRE (Superclase)              ║
 * ║  Retirada, Deposito y MiniDeclaracion la heredan ║
 * ╚══════════════════════════════════════════════════╝
 *
 * Clase base que representa cualquier operación realizada en el cajero.
 * RELACIÓN: Cliente ──── Transaccion (1 a muchos: un cliente hace muchas)
 *           Cuenta  ◇─── Transaccion (agregación: una cuenta tiene muchas)
 *           Transaccion ◁── Retirada        (herencia)
 *           Transaccion ◁── Deposito        (herencia)
 *           Transaccion ◁── MiniDeclaracion (herencia)
 */
public class Transaccion {

    // ── ATRIBUTOS ──────────────────────────────────────────────────────────
    private Date   fecha;    // fecha y hora exacta de la transacción
    private String tipo;     // "RETIRADA", "DEPOSITO", "MINI_DECLARACION"
    private int    importe;  // monto de la operación (0 para MiniDeclaracion)

    // ── CONSTRUCTOR VACÍO ──────────────────────────────────────────────────
    public Transaccion() {
        this.fecha = new Date(); // captura la fecha/hora actual
    }

    // ── CONSTRUCTOR CON PARÁMETROS ─────────────────────────────────────────
    /**
     * @param tipo     tipo de operación
     * @param importe  monto involucrado
     */
    public Transaccion(String tipo, int importe) {
        this.fecha   = new Date(); // se registra automáticamente
        this.tipo    = tipo;
        this.importe = importe;
    }

    // ── MÉTODO DE NEGOCIO ──────────────────────────────────────────────────
    /**
     * Imprime comprobante genérico de la transacción.
     * Las clases hijas SOBREESCRIBEN este método con su propio formato.
     * Concepto: POLIMORFISMO → cada hija imprime su propio comprobante.
     */
    public void imprimirComprobante() {
        System.out.println("┌─────────────────────────────────┐");
        System.out.println("│        COMPROBANTE ATM          │");
        System.out.println("├─────────────────────────────────┤");
        System.out.printf( "│  Tipo:    %-22s│%n", tipo);
        System.out.printf( "│  Importe: $%-21d│%n", importe);
        System.out.printf( "│  Fecha:   %-22s│%n", fecha.toString().substring(0,16));
        System.out.println("└─────────────────────────────────┘");
    }

    // ── GETTERS ────────────────────────────────────────────────────────────
    public Date   getFecha()   { return fecha; }
    public String getTipo()    { return tipo; }
    public int    getImporte() { return importe; }

    // ── SETTERS ────────────────────────────────────────────────────────────
    public void setFecha(Date fecha)    { this.fecha   = fecha; }
    public void setTipo(String tipo)    { this.tipo    = tipo; }
    public void setImporte(int importe) { this.importe = importe; }

    // ── toString ───────────────────────────────────────────────────────────
    @Override
    public String toString() {
        return String.format("%-18s | $%-8d | %s",
               tipo, importe, fecha.toString().substring(0, 16));
    }
}
