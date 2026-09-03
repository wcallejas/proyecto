package modelo;

import java.util.ArrayList;
import java.util.List;

/**
 * ╔══════════════════════════════════════════════════╗
 * ║  CLASE: Cuenta                                   ║
 * ║  PAQUETE: modelo                                 ║
 * ║  PASO 2 del desarrollo                           ║
 * ╠══════════════════════════════════════════════════╣
 * ║  CONCEPTO: Clase PADRE de Ahorro y Comprobaciones║
 * ║  AGREGACIÓN con Transaccion (tiene muchas)       ║
 * ╚══════════════════════════════════════════════════╝
 *
 * Representa una cuenta bancaria genérica.
 * Es la SUPERCLASE de Ahorro y Comprobaciones.
 *
 * RELACIONES:
 *   Cuenta ◁── Ahorro           (herencia)
 *   Cuenta ◁── Comprobaciones   (herencia)
 *   Cuenta ◇─── Transaccion     (agregación: 1 cuenta → muchas transacciones)
 *   Banco  ◇─── Cuenta          (agregación: 1 banco  → muchas cuentas)
 */
public class Cuenta {

    // ── ATRIBUTOS ──────────────────────────────────────────────────────────
    private String numeroCuenta;  // identificador único  ej: "AHO-001-2024"
    private String nombreBanco;   // banco al que pertenece la cuenta
    private double saldo;         // dinero disponible actualmente

    /**
     * AGREGACIÓN: una cuenta puede tener MUCHAS transacciones.
     * La lista existe dentro de la cuenta (la "contiene"),
     * pero las Transacciones pueden existir de forma independiente.
     */
    private List<Transaccion> transacciones;

    // ── CONSTRUCTOR VACÍO ──────────────────────────────────────────────────
    public Cuenta() {
        this.transacciones = new ArrayList<>();
    }

    // ── CONSTRUCTOR CON PARÁMETROS ─────────────────────────────────────────
    /**
     * @param numeroCuenta  identificador de la cuenta
     * @param nombreBanco   banco al que pertenece
     * @param saldo         saldo inicial
     */
    public Cuenta(String numeroCuenta, String nombreBanco, double saldo) {
        this.numeroCuenta  = numeroCuenta;
        this.nombreBanco   = nombreBanco;
        this.saldo         = saldo;
        this.transacciones = new ArrayList<>();
    }

    // ── MÉTODOS DE NEGOCIO ─────────────────────────────────────────────────

    /**
     * Agrega una transacción al historial.
     * Demuestra la AGREGACIÓN: la cuenta registra sus propias operaciones.
     *
     * @param t transacción a registrar
     */
    public void agregarTransaccion(Transaccion t) {
        transacciones.add(t);
    }

    /**
     * Verifica si hay fondos suficientes para operar.
     *
     * @param monto cantidad a verificar
     * @return true si el saldo cubre el monto
     */
    public boolean tieneSaldoSuficiente(double monto) {
        return saldo >= monto;
    }

    /**
     * Deposita dinero sumando al saldo.
     * Solo acepta montos positivos.
     *
     * @param monto cantidad a depositar
     */
    public void depositar(double monto) {
        if (monto > 0) {
            this.saldo += monto;
        }
    }

    /**
     * Retira dinero restando del saldo.
     * Las clases hijas pueden SOBREESCRIBIR este método
     * para cambiar las reglas (ej: Comprobaciones permite sobregiro).
     *
     * @param  monto cantidad a retirar
     * @return true si el retiro fue exitoso
     */
    public boolean retirar(double monto) {
        if (tieneSaldoSuficiente(monto)) {
            this.saldo -= monto;
            return true;
        }
        return false;
    }

    // ── GETTERS ────────────────────────────────────────────────────────────
    public String            getNumeroCuenta()    { return numeroCuenta; }
    public String            getNombreBanco()     { return nombreBanco; }
    public double            getSaldo()           { return saldo; }
    public List<Transaccion> getTransacciones()   { return transacciones; }

    // ── SETTERS ────────────────────────────────────────────────────────────
    public void setNumeroCuenta(String n)  { this.numeroCuenta  = n; }
    public void setNombreBanco(String n)   { this.nombreBanco   = n; }
    public void setSaldo(double s)         { this.saldo         = s; }

    // ── toString ───────────────────────────────────────────────────────────
    @Override
    public String toString() {
        return "Cuenta{numero='" + numeroCuenta +
               "', banco='"      + nombreBanco  +
               "', saldo="       + saldo        + "}";
    }
}
