package vista.swing;

import servicio.ATM;
import javax.swing.*;
import java.awt.*;

/**
 * ╔══════════════════════════════════════════════════════╗
 * ║  CLASE: ATMSwingApp                                  ║
 * ║  PAQUETE: vista.swing                                ║
 * ╠══════════════════════════════════════════════════════╣
 * ║  PROPÓSITO: JFrame principal — Controlador de        ║
 * ║  navegación con CardLayout                           ║
 * ╠══════════════════════════════════════════════════════╣
 * ║  CONCEPTO SWING:                                     ║
 * ║  CardLayout = gestor de layout que permite apilar    ║
 * ║  múltiples paneles y mostrar solo uno a la vez,      ║
 * ║  como un mazo de cartas. Perfecto para flujos ATM.   ║
 * ╚══════════════════════════════════════════════════════╝
 *
 * FLUJO DE NAVEGACIÓN:
 *   BIENVENIDA → PIN → CUENTAS → MENÚ
 *                                  ├── SALDO
 *                                  ├── RETIRO
 *                                  ├── DEPOSITO
 *                                  └── MINI_DECL.
 */
public class ATMSwingApp extends JFrame {

    // ── CONSTANTES DE NAVEGACIÓN ───────────────────────────────────────────
    // Claves únicas para identificar cada panel en el CardLayout
    public static final String PANEL_BIENVENIDA = "BIENVENIDA";
    public static final String PANEL_PIN        = "PIN";
    public static final String PANEL_CUENTAS    = "CUENTAS";
    public static final String PANEL_MENU       = "MENU";
    public static final String PANEL_SALDO      = "SALDO";
    public static final String PANEL_RETIRO     = "RETIRO";
    public static final String PANEL_DEPOSITO   = "DEPOSITO";
    public static final String PANEL_MINI       = "MINI";

    // ── REFERENCIA AL CAJERO (lógica de negocio) ───────────────────────────
    private ATM atm;

    // ── GESTOR DE LAYOUT ───────────────────────────────────────────────────
    /**
     * CardLayout: permite mostrar un panel a la vez.
     * Para cambiar de pantalla: cardLayout.show(contenedor, "CLAVE")
     */
    private CardLayout cardLayout;
    private JPanel     contenedor;  // panel raíz que contiene todos los paneles

    // ── REFERENCIAS A LOS PANELES ──────────────────────────────────────────
    // Se guardan referencias para poder actualizar su contenido dinámicamente
    private PanelBienvenida panelBienvenida;
    private PanelPin        panelPin;
    private PanelCuentas    panelCuentas;
    private PanelMenu       panelMenu;
    private PanelOperacion  panelSaldo;
    private PanelOperacion  panelRetiro;
    private PanelOperacion  panelDeposito;
    private PanelOperacion  panelMini;


    // ── CONSTRUCTOR ────────────────────────────────────────────────────────
    /**
     * @param atm instancia del cajero configurado desde Main
     */
    public ATMSwingApp(ATM atm) {
        this.atm = atm;
        configurarVentana();
        inicializarPaneles();
        mostrarPanel(PANEL_BIENVENIDA);
    }

    // ── CONFIGURACIÓN DE LA VENTANA ────────────────────────────────────────
    private void configurarVentana() {
        setTitle("🏧  Sistema ATM — BancoJava S.A.");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(860, 680);
        setMinimumSize(new Dimension(760, 580));
        setLocationRelativeTo(null); // centra en pantalla
        setResizable(true);

        // Ícono de la ventana (usa un carácter unicode como imagen)
        // En un proyecto real: setIconImage(ImageIO.read(...));

        // Fondo del frame
        getContentPane().setBackground(EstiloATM.FONDO_OSCURO);
    }

    // ── INICIALIZACIÓN DE PANELES ──────────────────────────────────────────
    /**
     * Crea todos los paneles y los registra en el CardLayout.
     * Cada panel recibe 'this' para poder llamar a mostrarPanel().
     */
    private void inicializarPaneles() {
        cardLayout = new CardLayout();
        contenedor = new JPanel(cardLayout);
        contenedor.setBackground(EstiloATM.FONDO_OSCURO);

        // Crear cada pantalla
        panelBienvenida = new PanelBienvenida(this);
        panelPin        = new PanelPin(this);
        panelCuentas    = new PanelCuentas(this);
        panelMenu       = new PanelMenu(this);
        panelSaldo      = new PanelOperacion(this, PanelOperacion.SALDO);
        panelRetiro     = new PanelOperacion(this, PanelOperacion.RETIRO);
        panelDeposito   = new PanelOperacion(this, PanelOperacion.DEPOSITO);
        panelMini       = new PanelOperacion(this, PanelOperacion.MINI);

        // Registrar paneles con su clave de navegación
        contenedor.add(panelBienvenida, PANEL_BIENVENIDA);
        contenedor.add(panelPin,        PANEL_PIN);
        contenedor.add(panelCuentas,    PANEL_CUENTAS);
        contenedor.add(panelMenu,       PANEL_MENU);
        contenedor.add(panelSaldo,      PANEL_SALDO);
        contenedor.add(panelRetiro,     PANEL_RETIRO);
        contenedor.add(panelDeposito,   PANEL_DEPOSITO);
        contenedor.add(panelMini,       PANEL_MINI);

        add(contenedor);
    }

    // ── NAVEGACIÓN ─────────────────────────────────────────────────────────
    /**
     * Cambia la pantalla activa.
     * Este método centraliza toda la navegación y permite ejecutar
     * lógica de actualización antes de mostrar cada panel.
     *
     * @param nombrePanel constante PANEL_* que identifica la pantalla
     */
    public void mostrarPanel(String nombrePanel) {

        // Actualizar datos dinámicos al entrar a ciertos paneles
        switch (nombrePanel) {
            case PANEL_BIENVENIDA -> panelBienvenida.resetear();
            case PANEL_PIN        -> panelPin.actualizarCliente();
            case PANEL_CUENTAS    -> panelCuentas.cargarCuentas();
            case PANEL_MENU       -> panelMenu.actualizar();
            case PANEL_SALDO,
                 PANEL_RETIRO,
                 PANEL_DEPOSITO,
                 PANEL_MINI       -> actualizarPanelOperacion(nombrePanel);
        }

        // Cambiar de pantalla con CardLayout
        cardLayout.show(contenedor, nombrePanel);
    }

    private void actualizarPanelOperacion(String nombre) {
        PanelOperacion panel = switch (nombre) {
            case PANEL_SALDO    -> panelSaldo;
            case PANEL_RETIRO   -> panelRetiro;
            case PANEL_DEPOSITO -> panelDeposito;
            case PANEL_MINI     -> panelMini;
            default             -> null;
        };
        if (panel != null) panel.actualizar();
    }

    // ── GETTERS ────────────────────────────────────────────────────────────
    /** Expone el ATM para que los paneles puedan llamar a sus métodos */
    public ATM      getAtm()       { return atm; }
    public PanelMenu getPanelMenu() { return panelMenu; }
}
