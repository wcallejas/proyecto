package servicio;

import modelo.Cuenta;
import modelo.Cliente;
import java.util.ArrayList;
import java.util.List;

/**
 * ╔══════════════════════════════════════════════════╗
 * ║  CLASE: Banco                                    ║
 * ║  PAQUETE: servicio                               ║
 * ║  PASO 9 del desarrollo                           ║
 * ╠══════════════════════════════════════════════════╣
 * ║  CONCEPTO: AGREGACIÓN (tiene muchas Cuentas)     ║
 * ║  Banco ◇──── Cuenta   (1 banco → muchas cuentas) ║
 * ║  Banco ◇──── Cliente  (1 banco → muchos clientes)║
 * ╚══════════════════════════════════════════════════╝
 *
 * Gestiona las cuentas y clientes registrados en la entidad bancaria.
 * Proporciona métodos de búsqueda que el ATM utiliza para operar.
 */
public class Banco {

    // ── ATRIBUTOS ──────────────────────────────────────────────────────────
    private String nombreBanco;  // nombre de la entidad bancaria
    private String ubicacion;    // ciudad o sede principal

    /**
     * AGREGACIÓN: el banco contiene múltiples cuentas y clientes.
     * Si el banco "desaparece", los datos podrían transferirse a otra entidad.
     * Esto distingue la Agregación de la Composición (donde no podrían existir solos).
     */
    private List<Cuenta>  cuentas;   // todas las cuentas del banco
    private List<Cliente> clientes;  // todos los clientes del banco

    // ── CONSTRUCTOR VACÍO ──────────────────────────────────────────────────
    public Banco() {
        this.cuentas  = new ArrayList<>();
        this.clientes = new ArrayList<>();
    }

    // ── CONSTRUCTOR CON PARÁMETROS ─────────────────────────────────────────
    /**
     * @param nombreBanco nombre de la entidad
     * @param ubicacion   ciudad o dirección
     */
    public Banco(String nombreBanco, String ubicacion) {
        this.nombreBanco = nombreBanco;
        this.ubicacion   = ubicacion;
        this.cuentas     = new ArrayList<>();
        this.clientes    = new ArrayList<>();
    }

    // ── MÉTODOS DE GESTIÓN ─────────────────────────────────────────────────

    /**
     * Registra una cuenta en el banco.
     * Demuestra la AGREGACIÓN: el banco "agrega" cuentas.
     *
     * @param cuenta cuenta a registrar
     */
    public void agregarCuenta(Cuenta cuenta) {
        cuentas.add(cuenta);
        System.out.println("  ✅ Cuenta " + cuenta.getNumeroCuenta() + " registrada en " + nombreBanco);
    }

    /**
     * Registra un cliente en el banco.
     *
     * @param cliente cliente a registrar
     */
    public void agregarCliente(Cliente cliente) {
        clientes.add(cliente);
        System.out.println("  ✅ Cliente " + cliente.getNombre() + " registrado en " + nombreBanco);
    }

    /**
     * Busca una cuenta por su número identificador.
     * Recorre la lista y compara cadenas de texto.
     *
     * @param  numeroCuenta identificador a buscar
     * @return la Cuenta si se encuentra, null si no existe
     */
    public Cuenta buscarCuenta(String numeroCuenta) {
        for (Cuenta c : cuentas) {
            if (c.getNumeroCuenta().equals(numeroCuenta)) {
                return c;
            }
        }
        return null; // no encontrada
    }

    /**
     * Busca un cliente por su ID único.
     *
     * @param  id identificador a buscar
     * @return el Cliente si existe, null si no está registrado
     */
    public Cliente buscarCliente(int id) {
        for (Cliente c : clientes) {
            if (c.getId() == id) {
                return c;
            }
        }
        return null; // no encontrado
    }

    /**
     * Imprime todas las cuentas del banco con su saldo.
     */
    public void listarCuentas() {
        System.out.println("\n📋 Cuentas registradas en " + nombreBanco + ":");
        System.out.println("   ─────────────────────────────────────────");
        for (Cuenta c : cuentas) {
            System.out.printf("   → %-16s │ Saldo: $%,.2f%n",
                              c.getNumeroCuenta(), c.getSaldo());
        }
        System.out.println("   ─────────────────────────────────────────");
        System.out.println("   Total de cuentas: " + cuentas.size());
    }

    /**
     * Imprime todos los clientes del banco.
     */
    public void listarClientes() {
        System.out.println("\n👥 Clientes de " + nombreBanco + ":");
        for (Cliente c : clientes) {
            System.out.printf("   → [%d] %s — %s%n",
                              c.getId(), c.getNombre(), c.getEmail());
        }
    }

    // ── GETTERS Y SETTERS ──────────────────────────────────────────────────
    public String        getNombreBanco()  { return nombreBanco; }
    public String        getUbicacion()    { return ubicacion; }
    public List<Cuenta>  getCuentas()      { return cuentas; }
    public List<Cliente> getClientes()     { return clientes; }

    public void setNombreBanco(String n)   { this.nombreBanco = n; }
    public void setUbicacion(String u)     { this.ubicacion   = u; }

    // ── toString ───────────────────────────────────────────────────────────
    @Override
    public String toString() {
        return "Banco{'" + nombreBanco + "', ubicacion='" + ubicacion +
               "', cuentas=" + cuentas.size() +
               ", clientes=" + clientes.size() + "}";
    }
}
