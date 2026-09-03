package modelo;

/**
 * ╔══════════════════════════════════════════════════╗
 * ║  CLASE: Cliente                                  ║
 * ║  PAQUETE: modelo                                 ║
 * ║  PASO 1 del desarrollo                           ║
 * ╠══════════════════════════════════════════════════╣
 * ║  CONCEPTO: Encapsulamiento                       ║
 * ║  Todos los atributos son privados (private).     ║
 * ║  Solo se acceden mediante getters y setters.     ║
 * ╚══════════════════════════════════════════════════╝
 *
 * Representa a la persona que usa el cajero automático.
 * RELACIÓN: ATM ──── Cliente (Asociación)
 *           Cliente ──── Transaccion (1 a muchos)
 */
public class Cliente {

    // ── ATRIBUTOS (privados → encapsulamiento) ─────────────────────────────
    private String nombre;     // nombre completo del cliente
    private int    telefono;   // número de contacto
    private String email;      // correo electrónico
    private int    id;         // identificador único del cliente

    // ── CONSTRUCTOR VACÍO ──────────────────────────────────────────────────
    /**
     * Constructor sin parámetros.
     * Necesario para crear un objeto sin datos iniciales.
     * Uso: Cliente c = new Cliente();
     */
    public Cliente() {}

    // ── CONSTRUCTOR CON PARÁMETROS ─────────────────────────────────────────
    /**
     * Constructor completo: inicializa todos los atributos de una vez.
     * Uso: Cliente c = new Cliente("Carlos López", 3001234567, "c@email.com", 1001);
     *
     * @param nombre    nombre completo
     * @param telefono  número de teléfono
     * @param email     correo electrónico
     * @param id        identificador único
     */
    public Cliente(String nombre, int telefono, String email, int id) {
        this.nombre   = nombre;
        this.telefono = telefono;
        this.email    = email;
        this.id       = id;
    }

    // ── GETTERS (leer atributos) ───────────────────────────────────────────

    /** @return nombre completo del cliente */
    public String getNombre()   { return nombre; }

    /** @return número de teléfono */
    public int    getTelefono() { return telefono; }

    /** @return correo electrónico */
    public String getEmail()    { return email; }

    /** @return identificador único */
    public int    getId()       { return id; }

    // ── SETTERS (modificar atributos de forma controlada) ─────────────────

    public void setNombre(String nombre)     { this.nombre   = nombre; }
    public void setTelefono(int telefono)    { this.telefono = telefono; }
    public void setEmail(String email)       { this.email    = email; }
    public void setId(int id)                { this.id       = id; }

    // ── MÉTODO toString ────────────────────────────────────────────────────
    /**
     * Representación en texto del objeto.
     * Se invoca automáticamente con System.out.println(cliente).
     */
    @Override
    public String toString() {
        return "Cliente{nombre='" + nombre  + "'" +
               ", id="           + id       +
               ", email='"       + email    + "'" +
               ", telefono="     + telefono + "}";
    }
}
