package modelo;

/**
 * ╔══════════════════════════════════════════════════╗
 * ║  CLASE: Comprobaciones (Cuenta Corriente)        ║
 * ║  PAQUETE: modelo                                 ║
 * ║  PASO 4 del desarrollo                           ║
 * ╠══════════════════════════════════════════════════╣
 * ║  CONCEPTO: HERENCIA  →  extends Cuenta           ║
 * ║  POLIMORFISMO: sobreescribe retirar()            ║
 * ╚══════════════════════════════════════════════════╝
 *
 * Una cuenta corriente (de comprobaciones) permite operar
 * aunque el saldo sea negativo, hasta cierto límite de sobregiro.
 *
 * DIFERENCIA con Ahorro:
 *   - Ahorro: no permite saldo negativo
 *   - Comprobaciones: permite saldo negativo hasta limiteSobregiro
 */
public class Comprobaciones extends Cuenta {

    // ── ATRIBUTO PROPIO ────────────────────────────────────────────────────
    /** Monto máximo que puede ir en negativo. Ej: 500.0 → puede llegar a -500 */
    private double limiteSobregiro;

    // ── CONSTRUCTOR VACÍO ──────────────────────────────────────────────────
    public Comprobaciones() {
        super();
    }

    // ── CONSTRUCTOR CON PARÁMETROS ─────────────────────────────────────────
    /**
     * @param numeroCuenta   identificador de la cuenta
     * @param nombreBanco    banco al que pertenece
     * @param saldo          saldo inicial
     * @param limiteSobregiro monto máximo de crédito adicional permitido
     */
    public Comprobaciones(String numeroCuenta, String nombreBanco,
                          double saldo, double limiteSobregiro) {
        super(numeroCuenta, nombreBanco, saldo);
        this.limiteSobregiro = limiteSobregiro;
    }

    // ── MÉTODO SOBREESCRITO (POLIMORFISMO) ─────────────────────────────────
    /**
     * SOBREESCRIBE el método retirar() de Cuenta.
     *
     * Regla nueva: se puede retirar aunque el saldo sea menor al monto,
     * siempre que (saldo + limiteSobregiro) cubra el retiro.
     *
     * Ejemplo: saldo = $100, limiteSobregiro = $500
     *   → puede retirar hasta $600 total
     *
     * @param  monto cantidad a retirar
     * @return true si el retiro fue exitoso
     */
    @Override
    public boolean retirar(double monto) {
        if (getSaldo() + limiteSobregiro >= monto) {
            setSaldo(getSaldo() - monto); // puede quedar negativo
            return true;
        }
        System.out.println("❌ Supera el límite de sobregiro de $" + limiteSobregiro);
        return false;
    }

    /**
     * Muestra el estado actual de la cuenta corriente.
     */
    public void mostrarResumen() {
        System.out.println("┌────────────────────────────────────┐");
        System.out.println("│      CUENTA CORRIENTE              │");
        System.out.println("├────────────────────────────────────┤");
        System.out.printf( "│  Número:    %-23s│%n", getNumeroCuenta());
        System.out.printf( "│  Banco:     %-23s│%n", getNombreBanco());
        System.out.printf( "│  Saldo:     $%-22.2f│%n", getSaldo());
        System.out.printf( "│  Sobregiro: $%-22.2f│%n", limiteSobregiro);
        System.out.printf( "│  Disponible:$%-22.2f│%n", getSaldo() + limiteSobregiro);
        System.out.println("└────────────────────────────────────┘");
    }

    // ── GETTERS Y SETTERS ──────────────────────────────────────────────────
    public double getLimiteSobregiro()        { return limiteSobregiro; }
    public void   setLimiteSobregiro(double l){ this.limiteSobregiro = l; }

    // ── toString ───────────────────────────────────────────────────────────
    @Override
    public String toString() {
        return "CuentaComprobaciones{numero='" + getNumeroCuenta() +
               "', saldo="                     + getSaldo()        +
               ", limiteSobregiro="            + limiteSobregiro   + "}";
    }
}
