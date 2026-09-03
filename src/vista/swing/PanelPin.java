package vista.swing;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * ╔══════════════════════════════════════════════════════╗
 * ║  CLASE: PanelPin                                     ║
 * ║  PAQUETE: vista.swing                                ║
 * ╠══════════════════════════════════════════════════════╣
 * ║  PANTALLA 2: Ingreso del PIN                         ║
 * ║  Teclado numérico propio + indicador de intentos     ║
 * ╚══════════════════════════════════════════════════════╝
 */
public class PanelPin extends JPanel {

    // ── CONSTANTES ─────────────────────────────────────────────────────────
    private static final int MAX_INTENTOS = 3;
    private static final int LONGITUD_PIN = 4;

    // ── REFERENCIAS ────────────────────────────────────────────────────────
    private ATMSwingApp app;

    // ── ESTADO ─────────────────────────────────────────────────────────────
    private StringBuilder pinIngresado = new StringBuilder();
    private int intentosRestantes      = MAX_INTENTOS;

    // ── COMPONENTES ────────────────────────────────────────────────────────
    private JLabel    lblPuntos;       // muestra ● por cada dígito ingresado
    private JLabel    lblIntentos;     // muestra intentos restantes
    private JLabel    lblMensaje;      // mensaje de error o estado
    private JLabel    lblCliente;      // nombre del cliente autenticado


    // ── CONSTRUCTOR ────────────────────────────────────────────────────────
    public PanelPin(ATMSwingApp app) {
        this.app = app;
        setBackground(EstiloATM.FONDO_OSCURO);
        setLayout(new BorderLayout());
        construirUI();
    }

    // ── CONSTRUCCIÓN DE LA INTERFAZ ────────────────────────────────────────
    private void construirUI() {

        // Cabecera
        add(crearCabecera(), BorderLayout.NORTH);

        // Zona central con el teclado
        JPanel centro = new JPanel(new GridBagLayout());
        centro.setBackground(EstiloATM.FONDO_OSCURO);

        JPanel card = EstiloATM.panelCard();
        card.setLayout(new BorderLayout(0, 20));
        card.setBorder(BorderFactory.createEmptyBorder(36, 44, 36, 44));
        card.setPreferredSize(new Dimension(420, 520));

        // ── Sección superior: cliente + display PIN ─────────────────────────
        JPanel topSection = new JPanel(new GridLayout(5, 1, 0, 8));
        topSection.setOpaque(false);

        JLabel lblTitulo = EstiloATM.labelAmbar("INGRESE SU PIN", EstiloATM.FUENTE_MONO_TITULO);

        lblCliente = EstiloATM.labelGris("Cliente: —");
        lblCliente.setHorizontalAlignment(SwingConstants.CENTER);

        // Display de dígitos (círculos ●)
        lblPuntos = new JLabel("", SwingConstants.CENTER);
        lblPuntos.setFont(new Font("Monospaced", Font.BOLD, 32));
        lblPuntos.setForeground(EstiloATM.AMBAR);
        lblPuntos.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(EstiloATM.BORDE_COLOR, 1),
            BorderFactory.createEmptyBorder(12, 20, 12, 20)
        ));
        lblPuntos.setBackground(new Color(12, 18, 34));
        lblPuntos.setOpaque(true);
        lblPuntos.setPreferredSize(new Dimension(0, 60));

        lblMensaje = new JLabel(" ", SwingConstants.CENTER);
        lblMensaje.setFont(EstiloATM.FUENTE_MONO_CHICA);
        lblMensaje.setForeground(EstiloATM.ROJO_ERROR);

        lblIntentos = new JLabel("Intentos restantes: " + MAX_INTENTOS,
                                 SwingConstants.CENTER);
        lblIntentos.setFont(EstiloATM.FUENTE_MONO_CHICA);
        lblIntentos.setForeground(EstiloATM.TEXTO_GRIS);

        topSection.add(lblTitulo);
        topSection.add(lblCliente);
        topSection.add(lblPuntos);
        topSection.add(lblMensaje);
        topSection.add(lblIntentos);

        // ── Teclado numérico ────────────────────────────────────────────────
        JPanel teclado = crearTeclado();

        card.add(topSection, BorderLayout.NORTH);
        card.add(teclado,    BorderLayout.CENTER);

        centro.add(card);
        add(centro, BorderLayout.CENTER);
    }

    private JPanel crearCabecera() {
        JPanel p = new JPanel(new BorderLayout());
        p.setBackground(EstiloATM.FONDO_CARD);
        p.setBorder(BorderFactory.createEmptyBorder(14, 28, 14, 28));

        JButton btnVolver = new JButton("◄ Volver");
        btnVolver.setFont(EstiloATM.FUENTE_MONO_CHICA);
        btnVolver.setForeground(EstiloATM.TEXTO_GRIS);
        btnVolver.setBackground(EstiloATM.FONDO_CARD);
        btnVolver.setBorderPainted(false);
        btnVolver.setFocusPainted(false);
        btnVolver.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnVolver.addActionListener(e -> {
            resetear();
            app.mostrarPanel(ATMSwingApp.PANEL_BIENVENIDA);
        });

        JLabel lblNombre = EstiloATM.labelAmbar("  ◈  BANCOJAVA S.A.",
                                                 EstiloATM.FUENTE_MONO_TITULO);

        p.add(btnVolver, BorderLayout.WEST);
        p.add(lblNombre, BorderLayout.CENTER);

        JPanel linea = new JPanel();
        linea.setBackground(EstiloATM.AMBAR);
        linea.setPreferredSize(new Dimension(0, 2));
        p.add(linea, BorderLayout.SOUTH);

        return p;
    }

    /**
     * Construye el teclado numérico 3×4 + acciones (Borrar / Confirmar).
     * Distribución estándar de cajeros ATM:
     *   1  2  3
     *   4  5  6
     *   7  8  9
     *   ←  0  ✓
     */
    private JPanel crearTeclado() {
        JPanel p = new JPanel(new GridLayout(4, 3, 10, 10));
        p.setOpaque(false);
        p.setBorder(BorderFactory.createEmptyBorder(16, 0, 0, 0));

        String[] teclas = { "1","2","3", "4","5","6", "7","8","9", "←","0","✓" };

        for (String t : teclas) {
            JButton btn = EstiloATM.botonTeclado(t);

            if (t.equals("←")) {
                btn.setForeground(EstiloATM.ROJO_ERROR);
                btn.addActionListener(e -> borrarUltimo());
            } else if (t.equals("✓")) {
                btn.setForeground(EstiloATM.VERDE_EXITO);
                btn.addActionListener(e -> confirmarPin());
            } else {
                final String digito = t;
                btn.addActionListener(e -> agregarDigito(digito));
            }

            p.add(btn);
        }

        return p;
    }

    // ── LÓGICA DEL PIN ─────────────────────────────────────────────────────

    private void agregarDigito(String d) {
        if (pinIngresado.length() < LONGITUD_PIN) {
            pinIngresado.append(d);
            actualizarDisplay();
        }
    }

    private void borrarUltimo() {
        if (pinIngresado.length() > 0) {
            pinIngresado.deleteCharAt(pinIngresado.length() - 1);
            actualizarDisplay();
            lblMensaje.setText(" ");
        }
    }

    private void confirmarPin() {
        if (pinIngresado.length() < LONGITUD_PIN) {
            lblMensaje.setText("Ingrese los " + LONGITUD_PIN + " dígitos del PIN.");
            return;
        }

        int pin = Integer.parseInt(pinIngresado.toString());

        if (app.getAtm().validarPin(pin)) {
            // PIN correcto → ir a selección de cuenta
            resetear();
            app.mostrarPanel(ATMSwingApp.PANEL_CUENTAS);
        } else {
            intentosRestantes--;
            pinIngresado.setLength(0);
            actualizarDisplay();

            if (intentosRestantes <= 0) {
                JOptionPane.showMessageDialog(
                    this,
                    "<html><center><b>Tarjeta bloqueada.</b><br>" +
                    "Ha superado el máximo de intentos.<br>" +
                    "Contacte a su banco para desbloquearla.</center></html>",
                    "🔒  Seguridad ATM",
                    JOptionPane.ERROR_MESSAGE
                );
                resetear();
                app.mostrarPanel(ATMSwingApp.PANEL_BIENVENIDA);
            } else {
                lblMensaje.setText("PIN incorrecto. Verifique e intente de nuevo.");
                lblIntentos.setText("Intentos restantes: " + intentosRestantes);
                lblIntentos.setForeground(
                    intentosRestantes == 1 ? EstiloATM.ROJO_ERROR : EstiloATM.TEXTO_GRIS
                );
            }
        }
    }

    private void actualizarDisplay() {
        // Mostrar ● por cada dígito ingresado, vacío por los restantes
        StringBuilder display = new StringBuilder();
        for (int i = 0; i < LONGITUD_PIN; i++) {
            display.append(i < pinIngresado.length() ? "● " : "○ ");
        }
        lblPuntos.setText(display.toString().trim());
    }

    // ── RESET ──────────────────────────────────────────────────────────────
    public void resetear() {
        pinIngresado.setLength(0);
        intentosRestantes = MAX_INTENTOS;
        actualizarDisplay();
        lblMensaje.setText(" ");
        lblIntentos.setText("Intentos restantes: " + MAX_INTENTOS);
        lblIntentos.setForeground(EstiloATM.TEXTO_GRIS);
    }

    /** Actualiza el nombre del cliente en pantalla al entrar */
    public void actualizarCliente() {
        if (app.getAtm().getClienteActual() != null) {
            lblCliente.setText("Cliente: " + app.getAtm().getClienteActual().getNombre());
        }
    }
}
