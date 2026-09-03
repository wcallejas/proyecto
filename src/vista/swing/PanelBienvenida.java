package vista.swing;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * ╔══════════════════════════════════════════════════════╗
 * ║  CLASE: PanelBienvenida                              ║
 * ║  PAQUETE: vista.swing                                ║
 * ╠══════════════════════════════════════════════════════╣
 * ║  PANTALLA 1: Bienvenida e inserción de tarjeta       ║
 * ║  El usuario ingresa su número de cliente             ║
 * ╚══════════════════════════════════════════════════════╝
 */
public class PanelBienvenida extends JPanel {

    // ── REFERENCIAS ────────────────────────────────────────────────────────
    private ATMSwingApp      app;       // referencia al frame principal
    private JTextField       campoId;   // campo para el ID del cliente
    private JLabel           lblReloj;  // reloj en tiempo real
    private Timer            timerReloj; // actualiza el reloj cada segundo

    // ── CONSTRUCTOR ────────────────────────────────────────────────────────
    public PanelBienvenida(ATMSwingApp app) {
        this.app = app;
        setBackground(EstiloATM.FONDO_OSCURO);
        setLayout(new BorderLayout());
        construirUI();
        iniciarReloj();
    }

    // ── CONSTRUCCIÓN DE LA INTERFAZ ────────────────────────────────────────
    private void construirUI() {

        // ── CABECERA: logo del banco ────────────────────────────────────────
        JPanel cabecera = crearCabecera();
        add(cabecera, BorderLayout.NORTH);

        // ── CENTRO: pantalla principal del cajero ──────────────────────────
        JPanel centro = crearPantallaCentral();
        add(centro, BorderLayout.CENTER);

        // ── PIE: información del banco ─────────────────────────────────────
        JPanel pie = crearPie();
        add(pie, BorderLayout.SOUTH);
    }

    private JPanel crearCabecera() {
        JPanel p = new JPanel(new BorderLayout());
        p.setBackground(EstiloATM.FONDO_CARD);
        p.setBorder(BorderFactory.createEmptyBorder(14, 28, 14, 28));

        // Logo / nombre del banco
        JLabel lblLogo = new JLabel("  ◈  BANCOJAVA S.A.");
        lblLogo.setFont(new Font("Monospaced", Font.BOLD, 20));
        lblLogo.setForeground(EstiloATM.AMBAR);

        // Reloj
        lblReloj = new JLabel("00:00:00");
        lblReloj.setFont(EstiloATM.FUENTE_MONO_NORMAL);
        lblReloj.setForeground(EstiloATM.TEXTO_GRIS);

        p.add(lblLogo, BorderLayout.WEST);
        p.add(lblReloj, BorderLayout.EAST);

        // Línea inferior de acento
        JPanel linea = new JPanel();
        linea.setBackground(EstiloATM.AMBAR);
        linea.setPreferredSize(new Dimension(0, 2));
        p.add(linea, BorderLayout.SOUTH);

        return p;
    }

    private JPanel crearPantallaCentral() {
        JPanel contenedor = new JPanel(new GridBagLayout());
        contenedor.setBackground(EstiloATM.FONDO_OSCURO);
        contenedor.setBorder(BorderFactory.createEmptyBorder(30, 60, 30, 60));

        // Panel principal (tarjeta elevada)
        JPanel card = EstiloATM.panelCard();
        card.setLayout(new BorderLayout(0, 24));
        card.setBorder(BorderFactory.createEmptyBorder(40, 50, 40, 50));
        card.setPreferredSize(new Dimension(520, 460));

        // ── Icono ATM ──────────────────────────────────────────────────────
        JLabel lblIcono = new JLabel("🏧", SwingConstants.CENTER);
        lblIcono.setFont(new Font("Serif", Font.PLAIN, 64));
        lblIcono.setBorder(BorderFactory.createEmptyBorder(0, 0, 8, 0));

        // ── Título ─────────────────────────────────────────────────────────
        JPanel titulos = new JPanel(new GridLayout(2, 1, 0, 6));
        titulos.setOpaque(false);

        JLabel lblTitulo = EstiloATM.labelAmbar("CAJERO AUTOMÁTICO", EstiloATM.FUENTE_DISPLAY);
        lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);

        JLabel lblSubtitulo = EstiloATM.labelGris("Sistema ATM  ─  BancoJava S.A.");
        lblSubtitulo.setHorizontalAlignment(SwingConstants.CENTER);

        titulos.add(lblTitulo);
        titulos.add(lblSubtitulo);

        // ── Formulario de inserción de tarjeta ─────────────────────────────
        JPanel formPanel = new JPanel(new GridLayout(3, 1, 0, 12));
        formPanel.setOpaque(false);
        formPanel.setBorder(BorderFactory.createEmptyBorder(12, 0, 0, 0));

        JLabel lblInstruccion = new JLabel("  Inserte su tarjeta  (N° de cliente)");
        lblInstruccion.setFont(EstiloATM.FUENTE_MONO_CHICA);
        lblInstruccion.setForeground(EstiloATM.TEXTO_GRIS);

        campoId = EstiloATM.campoTexto(20);
        campoId.setHorizontalAlignment(JTextField.CENTER);
        campoId.setFont(EstiloATM.FUENTE_MONO_TITULO);
        campoId.setToolTipText("Ingrese su número de cliente (ej: 1001)");

        // Al presionar ENTER en el campo, avanza
        campoId.addActionListener(e -> procesarInsercion());

        JButton btnInsertar = EstiloATM.botonPrimario("►  INSERTAR TARJETA");
        btnInsertar.addActionListener(e -> procesarInsercion());

        formPanel.add(lblInstruccion);
        formPanel.add(campoId);
        formPanel.add(btnInsertar);

        // ── Hint de credenciales de prueba ─────────────────────────────────
        JLabel lblHint = new JLabel(
            "<html><center><font color='#4A6080'>Credenciales demo: cliente <b>1001</b> | PIN <b>1234</b></font></center></html>");
        lblHint.setHorizontalAlignment(SwingConstants.CENTER);
        lblHint.setFont(EstiloATM.FUENTE_MONO_CHICA);

        // ── Ensamblaje del card ─────────────────────────────────────────────
        card.add(lblIcono,  BorderLayout.NORTH);
        card.add(titulos,   BorderLayout.CENTER);
        card.add(formPanel, BorderLayout.SOUTH);

        JPanel wrapper = new JPanel(new BorderLayout(0, 14));
        wrapper.setOpaque(false);
        wrapper.add(card,    BorderLayout.CENTER);
        wrapper.add(lblHint, BorderLayout.SOUTH);

        contenedor.add(wrapper);
        return contenedor;
    }

    private JPanel crearPie() {
        JPanel p = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 10));
        p.setBackground(EstiloATM.FONDO_CARD);
        p.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, EstiloATM.SEPARADOR));

        agregarInfoPie(p, "◈",  "24 horas / 7 días");
        agregarInfoPie(p, "🔒", "Conexión segura");
        agregarInfoPie(p, "📍", "Bogotá — Centro");

        return p;
    }

    private void agregarInfoPie(JPanel parent, String icono, String texto) {
        JLabel lbl = new JLabel(icono + "  " + texto);
        lbl.setFont(EstiloATM.FUENTE_MONO_CHICA);
        lbl.setForeground(EstiloATM.TEXTO_GRIS);
        parent.add(lbl);
    }

    // ── LÓGICA ─────────────────────────────────────────────────────────────

    private void procesarInsercion() {
        String texto = campoId.getText().trim();
        if (texto.isEmpty()) {
            mostrarError("Por favor ingrese su número de cliente.");
            return;
        }
        try {
            int idCliente = Integer.parseInt(texto);
            if (app.getAtm().insertarTarjeta(idCliente)) {
                campoId.setText("");
                app.mostrarPanel(ATMSwingApp.PANEL_PIN);
            } else {
                mostrarError("<html><center>Número de cliente no reconocido.<br>" +
                             "Verifique su tarjeta e intente de nuevo.</center></html>");
                campoId.setText("");
                campoId.requestFocus();
            }
        } catch (NumberFormatException ex) {
            mostrarError("El número de cliente debe contener solo dígitos.");
            campoId.setText("");
            campoId.requestFocus();
        }
    }

    private void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(
            this, mensaje, "⚠  Tarjeta Rechazada",
            JOptionPane.WARNING_MESSAGE
        );
    }

    // ── RELOJ ──────────────────────────────────────────────────────────────
    private void iniciarReloj() {
        timerReloj = new Timer(1000, e -> {
            String hora = LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("HH:mm:ss  EEE dd/MM/yyyy"));
            lblReloj.setText(hora);
        });
        timerReloj.start();
    }

    /** Limpia el campo al volver a esta pantalla */
    public void resetear() {
        campoId.setText("");
        campoId.requestFocus();
    }
}
