package modelo;

/**
 * ╔══════════════════════════════════════════════════╗
 * ║  CLASE: Retirada                                 ║
 * ║  PAQUETE: modelo                                 ║
 * ║  PASO 6 del desarrollo                           ║
 * ╠══════════════════════════════════════════════════╣
 * ║  CONCEPTO: HERENCIA  →  extends Transaccion      ║
 * ║  Hereda: fecha, tipo, importe + métodos          ║
 * ╚══════════════════════════════════════════════════╝
 *
 * Representa la operación de RETIRAR dinero del cajero.
 * Hereda de Transaccion y agrega: numeroCuenta, importe propio.
 */
public class Retirada extends Transaccion {

    // ── ATRIBUTOS PROPIOS ──────────────────────────────────────────────────
    private int    importe;        // monto a retirar en esta operación
    private String numeroCuenta;   // cuenta de la que se retira el dinero

    // ── CONSTRUCTOR VACÍO ──────────────────────────────────────────────────
    public Retirada() {
        super(); // llama al constructor de Transaccion
    }

    // ── CONSTRUCTOR CON PARÁMETROS ─────────────────────────────────────────
    /**
     * Crea una retirada indicando monto y cuenta origen.
     * Llama a super() enviando al padre el tipo y monto.
     *
     * @param importe      cantidad de dinero a retirar
     * @param numeroCuenta cuenta de la que se extrae el dinero
     */
    public Retirada(int importe, String numeroCuenta) {
        super("RETIRADA", importe); // inicializa tipo e importe en Transaccion
        this.importe      = importe;
        this.numeroCuenta = numeroCuenta;
    }

    // ── MÉTODO SOBREESCRITO ────────────────────────────────────────────────
    /**
     * Comprobante específico de retirada.
     * SOBREESCRIBE imprimirComprobante() de Transaccion.
     * POLIMORFISMO: cada transacción imprime su propio formato.
     */
    @Override
    public void imprimirComprobante() {
        System.out.println();
        System.out.println("┌──────────────────────────────────┐");
        System.out.println("│      COMPROBANTE DE RETIRADA     │");
        System.out.println("├──────────────────────────────────┤");
        System.out.printf( "│  Cuenta:  %-22s│%n", numeroCuenta);
        System.out.printf( "│  Monto:   $%-21d│%n", importe);
        System.out.printf( "│  Fecha:   %-22s│%n", getFecha().toString().substring(0,16));
        System.out.println("├──────────────────────────────────┤");
        System.out.println("│   ¡GRACIAS POR USAR EL ATM!     │");
        System.out.println("└──────────────────────────────────┘");
    }

    // ── GETTERS Y SETTERS ──────────────────────────────────────────────────
    public int    getImporte()      { return importe; }
    public String getNumeroCuenta() { return numeroCuenta; }

    public void setImporte(int i)          { this.importe      = i; }
    public void setNumeroCuenta(String n)  { this.numeroCuenta = n; }

    // ── toString ───────────────────────────────────────────────────────────
    @Override
    public String toString() {
        return String.format("RETIRADA         | $%-8d | %s",
               importe, getFecha().toString().substring(0, 16));
    }
}
