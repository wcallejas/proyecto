package modelo;

/**
 * ╔══════════════════════════════════════════════════╗
 * ║  CLASE: Deposito                                 ║
 * ║  PAQUETE: modelo                                 ║
 * ║  PASO 7 del desarrollo                           ║
 * ╠══════════════════════════════════════════════════╣
 * ║  CONCEPTO: HERENCIA  →  extends Transaccion      ║
 * ╚══════════════════════════════════════════════════╝
 *
 * Representa la operación de DEPOSITAR dinero en el cajero.
 * Segunda clase hija de Transaccion.
 */
public class Deposito extends Transaccion {

    // ── ATRIBUTOS PROPIOS ──────────────────────────────────────────────────
    private int    importe;       // monto a depositar
    private String numeroCuenta;  // cuenta receptora del dinero

    // ── CONSTRUCTOR VACÍO ──────────────────────────────────────────────────
    public Deposito() {
        super();
    }

    // ── CONSTRUCTOR CON PARÁMETROS ─────────────────────────────────────────
    /**
     * @param importe      cantidad de dinero a depositar
     * @param numeroCuenta cuenta que recibirá el dinero
     */
    public Deposito(int importe, String numeroCuenta) {
        super("DEPOSITO", importe);
        this.importe      = importe;
        this.numeroCuenta = numeroCuenta;
    }

    // ── MÉTODO SOBREESCRITO ────────────────────────────────────────────────
    /**
     * Comprobante específico de depósito.
     * SOBREESCRIBE imprimirComprobante() de Transaccion.
     */
    @Override
    public void imprimirComprobante() {
        System.out.println();
        System.out.println("┌──────────────────────────────────┐");
        System.out.println("│      COMPROBANTE DE DEPÓSITO     │");
        System.out.println("├──────────────────────────────────┤");
        System.out.printf( "│  Cuenta:   %-21s│%n", numeroCuenta);
        System.out.printf( "│  Depósito: $%-20d│%n", importe);
        System.out.printf( "│  Fecha:    %-21s│%n", getFecha().toString().substring(0,16));
        System.out.println("├──────────────────────────────────┤");
        System.out.println("│  ✅ DEPÓSITO REALIZADO CON ÉXITO │");
        System.out.println("└──────────────────────────────────┘");
    }

    // ── GETTERS Y SETTERS ──────────────────────────────────────────────────
    public int    getImporte()      { return importe; }
    public String getNumeroCuenta() { return numeroCuenta; }

    public void setImporte(int i)         { this.importe      = i; }
    public void setNumeroCuenta(String n) { this.numeroCuenta = n; }

    // ── toString ───────────────────────────────────────────────────────────
    @Override
    public String toString() {
        return String.format("DEPOSITO          | $%-8d | %s",
               importe, getFecha().toString().substring(0, 16));
    }
}
