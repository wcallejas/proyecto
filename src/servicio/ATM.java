package servicio;

import modelo.Cuenta;
import modelo.Cliente;
import modelo.Transaccion;
import modelo.Retirada;
import modelo.Deposito;
import modelo.MiniDeclaracion;

/**
 * ╔══════════════════════════════════════════════════╗
 * ║  CLASE: ATM (Cajero Automático)                  ║
 * ║  PAQUETE: servicio                               ║
 * ║  PASO 10 del desarrollo                          ║
 * ╠══════════════════════════════════════════════════╣
 * ║  CONCEPTO: ASOCIACIÓN con Cliente                ║
 * ║  Coordina todas las operaciones bancarias        ║
 * ╚══════════════════════════════════════════════════╝
 *
 * Clase central que gestiona la lógica del cajero automático.
 *
 * RELACIONES:
 *   ATM ──── Cliente (ASOCIACIÓN: el cajero atiende a un cliente,
 *                     pero ambos existen de forma independiente)
 *   ATM usa  Banco   (para validar credenciales y acceder a cuentas)
 *   ATM usa  Cuenta  (cuenta seleccionada en la sesión actual)
 */
public class ATM {

    // ── ATRIBUTOS ──────────────────────────────────────────────────────────
    private int    pin;          // PIN del sistema para validar acceso
    private String ubicacion;    // dónde está físicamente el cajero
    private String nombreBanco;  // banco propietario del ATM

    /**
     * ASOCIACIÓN: El ATM trabaja con un cliente durante una sesión.
     * Cuando termina la sesión, clienteActual = null.
     * El Cliente puede existir sin el ATM (no es su dueño).
     */
    private Cliente clienteActual; // cliente autenticado en la sesión actual
    private Banco   banco;         // banco que respalda las operaciones
    private Cuenta  cuentaActual;  // cuenta seleccionada para operar

    // ── CONSTRUCTOR ────────────────────────────────────────────────────────
    /**
     * Crea el ATM configurado para un banco específico.
     *
     * @param pin         PIN de validación del sistema
     * @param ubicacion   localización física del cajero
     * @param nombreBanco banco propietario
     * @param banco       referencia al objeto Banco (ASOCIACIÓN)
     */
    public ATM(int pin, String ubicacion, String nombreBanco, Banco banco) {
        this.pin         = pin;
        this.ubicacion   = ubicacion;
        this.nombreBanco = nombreBanco;
        this.banco       = banco;
    }

    // ── MÉTODOS DE AUTENTICACIÓN ───────────────────────────────────────────

    /**
     * PASO 1 — Simula insertar la tarjeta buscando al cliente por ID.
     * Si el cliente existe en el banco, lo carga en la sesión.
     *
     * @param  idCliente identificador del titular de la tarjeta
     * @return true si el cliente está registrado
     */
    public boolean insertarTarjeta(int idCliente) {
        clienteActual = banco.buscarCliente(idCliente);
        if (clienteActual != null) {
            System.out.println("✅ Tarjeta aceptada.");
            System.out.println("   Bienvenido/a, " + clienteActual.getNombre() + ".");
            return true;
        }
        System.out.println("❌ Tarjeta no reconocida. Verifique sus datos.");
        return false;
    }

    /**
     * PASO 2 — Valida el PIN ingresado por el usuario.
     *
     * @param  pinIngresado PIN que el usuario escribió
     * @return true si el PIN es correcto
     */
    public boolean validarPin(int pinIngresado) {
        if (this.pin == pinIngresado) {
            System.out.println("✅ PIN correcto. Acceso concedido.");
            return true;
        }
        System.out.println("❌ PIN incorrecto. Intente nuevamente.");
        return false;
    }

    /**
     * PASO 3 — Selecciona la cuenta a utilizar durante la sesión.
     * Busca la cuenta en el banco por su número.
     *
     * @param  numeroCuenta número de la cuenta a activar
     * @return true si la cuenta fue encontrada
     */
    public boolean seleccionarCuenta(String numeroCuenta) {
        cuentaActual = banco.buscarCuenta(numeroCuenta);
        if (cuentaActual != null) {
            System.out.println("✅ Cuenta seleccionada: " + numeroCuenta);
            System.out.printf( "   Tipo: %s%n",
                               cuentaActual.getClass().getSimpleName());
            return true;
        }
        System.out.println("❌ Cuenta no encontrada en " + nombreBanco);
        return false;
    }

    // ── OPERACIONES DEL CAJERO ─────────────────────────────────────────────

    /**
     * Muestra el saldo disponible de la cuenta activa.
     */
    public void consultarSaldo() {
        if (!cuentaDisponible()) return;
        System.out.println();
        System.out.println("┌──────────────────────────────────┐");
        System.out.println("│         CONSULTA DE SALDO        │");
        System.out.println("├──────────────────────────────────┤");
        System.out.printf( "│  Cuenta:  %-22s│%n", cuentaActual.getNumeroCuenta());
        System.out.printf( "│  Saldo:   $%-21.2f│%n", cuentaActual.getSaldo());
        System.out.println("└──────────────────────────────────┘");
    }

    /**
     * Procesa la retirada de dinero.
     * 1. Intenta retirar del saldo de la cuenta
     * 2. Si es exitoso, crea un objeto Retirada (hereda de Transaccion)
     * 3. Registra la Retirada en el historial de la cuenta (Agregación)
     * 4. Imprime el comprobante
     *
     * @param  monto cantidad a retirar
     * @return true si la operación fue exitosa
     */
    public boolean retirarDinero(int monto) {
        if (!cuentaDisponible()) return false;

        if (monto <= 0) {
            System.out.println("⚠️  El monto debe ser mayor a cero.");
            return false;
        }

        // Intentar el retiro (Cuenta.retirar() lo valida internamente)
        boolean exito = cuentaActual.retirar(monto);

        if (exito) {
            // Crear objeto Retirada (HERENCIA: Retirada extends Transaccion)
            Retirada retirada = new Retirada(monto, cuentaActual.getNumeroCuenta());
            // Registrar en el historial (AGREGACIÓN: cuenta tiene transacciones)
            cuentaActual.agregarTransaccion(retirada);
            // POLIMORFISMO: llama al imprimirComprobante() de Retirada
            retirada.imprimirComprobante();
            System.out.printf("   Nuevo saldo: $%.2f%n", cuentaActual.getSaldo());
        } else {
            System.out.println("❌ Operación fallida. Fondos insuficientes.");
        }
        return exito;
    }

    /**
     * Procesa el depósito de dinero.
     * 1. Deposita en la cuenta
     * 2. Crea un objeto Deposito (hereda de Transaccion)
     * 3. Registra en el historial
     * 4. Imprime el comprobante
     *
     * @param monto cantidad a depositar
     */
    public void depositarDinero(int monto) {
        if (!cuentaDisponible()) return;

        if (monto <= 0) {
            System.out.println("⚠️  El monto debe ser mayor a cero.");
            return;
        }

        cuentaActual.depositar(monto);
        // Crear objeto Deposito (HERENCIA: Deposito extends Transaccion)
        Deposito deposito = new Deposito(monto, cuentaActual.getNumeroCuenta());
        cuentaActual.agregarTransaccion(deposito);
        deposito.imprimirComprobante();
        System.out.printf("   Nuevo saldo: $%.2f%n", cuentaActual.getSaldo());
    }

    /**
     * Muestra la mini declaración (historial de movimientos).
     * Crea un objeto MiniDeclaracion (hereda de Transaccion).
     */
    public void verMiniDeclaracion() {
        if (!cuentaDisponible()) return;

        // Crear objeto MiniDeclaracion (HERENCIA: MiniDeclaracion extends Transaccion)
        MiniDeclaracion mini = new MiniDeclaracion(cuentaActual.getNumeroCuenta());
        cuentaActual.agregarTransaccion(mini);
        // Imprime el historial de transacciones
        mini.imprimirMiniDeclaracion(cuentaActual.getTransacciones());
    }

    /**
     * Cierra la sesión actual del cliente.
     * Limpia las referencias: clienteActual y cuentaActual vuelven a null.
     */
    public void cerrarSesion() {
        System.out.println();
        System.out.println("┌──────────────────────────────────┐");
        System.out.println("│    SESIÓN CERRADA CORRECTAMENTE  │");
        System.out.println("│   Retire su tarjeta. ¡Hasta pronto! │");
        System.out.println("└──────────────────────────────────┘");
        clienteActual = null;  // limpia la asociación con el cliente
        cuentaActual  = null;  // limpia la cuenta seleccionada
    }

    // ── MÉTODO AUXILIAR ────────────────────────────────────────────────────
    /**
     * Verifica que haya una cuenta seleccionada antes de operar.
     * @return true si hay cuenta activa
     */
    private boolean cuentaDisponible() {
        if (cuentaActual == null) {
            System.out.println("⚠️  No hay ninguna cuenta seleccionada.");
            return false;
        }
        return true;
    }

    // ── GETTERS Y SETTERS ──────────────────────────────────────────────────
    public int     getPin()           { return pin; }
    public String  getUbicacion()     { return ubicacion; }
    public String  getNombreBanco()   { return nombreBanco; }
    public Cliente getClienteActual() { return clienteActual; }
    public Cuenta  getCuentaActual()  { return cuentaActual; }
    public Banco   getBanco()         { return banco; }

    public void setPin(int pin)               { this.pin         = pin; }
    public void setUbicacion(String u)        { this.ubicacion   = u; }
    public void setNombreBanco(String n)      { this.nombreBanco = n; }
    public void setBanco(Banco b)             { this.banco       = b; }
}
