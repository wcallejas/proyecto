package modelo;

import java.util.List;

/**
 * ╔══════════════════════════════════════════════════╗
 * ║  CLASE: MiniDeclaracion                          ║
 * ║  PAQUETE: modelo                                 ║
 * ║  PASO 8 del desarrollo                           ║
 * ╠══════════════════════════════════════════════════╣
 * ║  CONCEPTO: HERENCIA  →  extends Transaccion      ║
 * ║  Tercera clase hija de Transaccion               ║
 * ╚══════════════════════════════════════════════════╝
 *
 * Genera un mini extracto con el historial de movimientos de la cuenta.
 * También es una Transaccion (se registra en el historial).
 */
public class MiniDeclaracion extends Transaccion {

    // ── ATRIBUTO PROPIO ────────────────────────────────────────────────────
    private String accountNumber; // número de cuenta a consultar

    // ── CONSTRUCTOR VACÍO ──────────────────────────────────────────────────
    public MiniDeclaracion() {
        super();
    }

    // ── CONSTRUCTOR CON PARÁMETROS ─────────────────────────────────────────
    /**
     * @param accountNumber número de cuenta del que se extrae el historial
     */
    public MiniDeclaracion(String accountNumber) {
        super("MINI_DECLARACION", 0); // importe = 0 (es consulta, no mueve dinero)
        this.accountNumber = accountNumber;
    }

    // ── MÉTODO PRINCIPAL ───────────────────────────────────────────────────
    /**
     * Imprime el historial de transacciones de la cuenta.
     * Muestra como máximo las últimas 5 operaciones.
     *
     * Recibe la lista desde Cuenta (relación de agregación).
     * No tiene acceso directo a Cuenta — recibe los datos como parámetro.
     *
     * @param transacciones lista de transacciones de la cuenta
     */
    public void imprimirMiniDeclaracion(List<Transaccion> transacciones) {
        System.out.println();
        System.out.println("┌──────────────────────────────────────────┐");
        System.out.println("│           MINI DECLARACIÓN               │");
        System.out.printf( "│  Cuenta: %-32s│%n", accountNumber);
        System.out.printf( "│  Fecha:  %-32s│%n", getFecha().toString().substring(0,16));
        System.out.println("├──────────────────────────────────────────┤");
        System.out.println("│  ÚLTIMAS TRANSACCIONES:                  │");
        System.out.println("├──────────────────────────────────────────┤");

        if (transacciones.isEmpty()) {
            System.out.println("│  No hay transacciones registradas.       │");
        } else {
            // Mostrar las últimas 5 (o menos si hay pocas)
            int inicio = Math.max(0, transacciones.size() - 5);
            for (int i = inicio; i < transacciones.size(); i++) {
                Transaccion t = transacciones.get(i);
                // Solo mostrar Retiradas y Depositos (no la MiniDeclaracion misma)
                if (!(t instanceof MiniDeclaracion)) {
                    System.out.printf("│  %-40s│%n", t.toString());
                }
            }
        }

        System.out.println("└──────────────────────────────────────────┘");
    }

    // ── GETTER Y SETTER ────────────────────────────────────────────────────
    public String getAccountNumber()        { return accountNumber; }
    public void   setAccountNumber(String a){ this.accountNumber = a; }

    // ── toString ───────────────────────────────────────────────────────────
    @Override
    public String toString() {
        return String.format("MINI_DECLARACION  | $%-8d | %s",
               0, getFecha().toString().substring(0, 16));
    }
}
