package modelo;

/**
 * ╔══════════════════════════════════════════════════╗
 * ║  CLASE: Ahorro                                   ║
 * ║  PAQUETE: modelo                                 ║
 * ║  PASO 3 del desarrollo                           ║
 * ╠══════════════════════════════════════════════════╣
 * ║  CONCEPTO: HERENCIA  →  extends Cuenta           ║
 * ║  Ahorro "es-una" Cuenta con características      ║
 * ║  adicionales: tasa de interés y límite de retiros║
 * ╚══════════════════════════════════════════════════╝
 *
 * Hereda de Cuenta todos sus atributos y métodos:
 *   ✓ numeroCuenta, nombreBanco, saldo
 *   ✓ agregarTransaccion(), depositar(), retirar()
 *   ✓ getters y setters de Cuenta
 *
 * Agrega sus propios atributos:
 *   ✓ tasaInteres, limiteRetirosMes
 */
public class Ahorro extends Cuenta {

    // ── ATRIBUTOS PROPIOS DE AHORRO ────────────────────────────────────────
    private double tasaInteres;       // porcentaje de interés anual (ej: 3.5)
    private int    limiteRetirosMes;  // máximo de retiros permitidos por mes

    // ── CONSTRUCTOR VACÍO ──────────────────────────────────────────────────
    /**
     * super() → llama al constructor vacío de Cuenta (clase padre).
     * Siempre debe ser la primera instrucción en el constructor de la hija.
     */
    public Ahorro() {
        super(); // invoca Cuenta()
    }

    // ── CONSTRUCTOR CON PARÁMETROS ─────────────────────────────────────────
    /**
     * Inicializa los atributos del padre y los propios de Ahorro.
     *
     * @param numeroCuenta     identificador de la cuenta
     * @param nombreBanco      banco al que pertenece
     * @param saldo            saldo inicial
     * @param tasaInteres      tasa de interés anual en porcentaje
     * @param limiteRetirosMes máximo de retiros por mes
     */
    public Ahorro(String numeroCuenta, String nombreBanco, double saldo,
                  double tasaInteres, int limiteRetirosMes) {
        // super(...) inicializa los atributos de la clase padre Cuenta
        super(numeroCuenta, nombreBanco, saldo);
        this.tasaInteres      = tasaInteres;
        this.limiteRetirosMes = limiteRetirosMes;
    }

    // ── MÉTODOS PROPIOS DE AHORRO ──────────────────────────────────────────

    /**
     * Calcula los intereses anuales generados por el saldo actual.
     * Usa getSaldo() del padre (encapsulamiento: no accede a saldo directamente).
     *
     * @return monto de intereses generados
     */
    public double calcularIntereses() {
        return getSaldo() * (tasaInteres / 100.0);
    }

    /**
     * Muestra un resumen de la cuenta de ahorro.
     */
    public void mostrarResumen() {
        System.out.println("┌────────────────────────────────────┐");
        System.out.println("│        CUENTA DE AHORRO            │");
        System.out.println("├────────────────────────────────────┤");
        System.out.printf( "│  Número:   %-24s│%n", getNumeroCuenta());
        System.out.printf( "│  Banco:    %-24s│%n", getNombreBanco());
        System.out.printf( "│  Saldo:    $%-23.2f│%n", getSaldo());
        System.out.printf( "│  Interés:  %.1f%% anual%s│%n", tasaInteres, "              ");
        System.out.printf( "│  Intereses generados: $%-10.2f│%n", calcularIntereses());
        System.out.println("└────────────────────────────────────┘");
    }

    // ── GETTERS ────────────────────────────────────────────────────────────
    public double getTasaInteres()      { return tasaInteres; }
    public int    getLimiteRetirosMes() { return limiteRetirosMes; }

    // ── SETTERS ────────────────────────────────────────────────────────────
    public void setTasaInteres(double t)      { this.tasaInteres      = t; }
    public void setLimiteRetirosMes(int l)    { this.limiteRetirosMes = l; }

    // ── toString ───────────────────────────────────────────────────────────
    @Override
    public String toString() {
        return "CuentaAhorro{numero='" + getNumeroCuenta() +
               "', saldo="             + getSaldo()        +
               ", tasa="               + tasaInteres       + "%" +
               ", limiteRetiros="      + limiteRetirosMes  + "/mes}";
    }
}
