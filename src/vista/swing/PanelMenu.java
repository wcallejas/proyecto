package vista.swing;

import modelo.Cuenta;
import javax.swing.*;
import java.awt.*;

/**
 * ╔══════════════════════════════════════════════════════╗
 * ║  CLASE: PanelMenu                                    ║
 * ║  PAQUETE: vista.swing                                ║
 * ╠══════════════════════════════════════════════════════╣
 * ║  PANTALLA 4: Menú principal de operaciones           ║
 * ║  4 botones grandes + saldo visible + opción salir    ║
 * ╚══════════════════════════════════════════════════════╝
 */
public class PanelMenu extends JPanel {

    private ATMSwingApp app;

    // Componentes actualizables
    private JLabel lblCliente;
    private JLabel lblCuenta;
    private JLabel lblSaldo;
    private JLabel lblTipoCuenta;

    public PanelMenu(ATMSwingApp app) {
        this.app = app;
        setBackground(EstiloATM.FONDO_OSCURO);
        setLayout(new BorderLayout(0, 0));
        construirUI();
    }

    private void construirUI() {
        add(crearCabecera(), BorderLayout.NORTH);
        add(crearContenido(), BorderLayout.CENTER);
        add(crearPie(), BorderLayout.SOUTH);
    }

    private JPanel crearCabecera() {
        JPanel p = new JPanel(new BorderLayout());
        p.setBackground(EstiloATM.FONDO_CARD);
        p.setBorder(BorderFactory.createEmptyBorder(14, 28, 14, 28));

        JLabel lblLogo = EstiloATM.labelAmbar("  ◈  BANCOJAVA S.A.",
                                               EstiloATM.FUENTE_MONO_TITULO);
        lblCuenta = EstiloATM.labelGris("Cuenta: —");

        p.add(lblLogo,   BorderLayout.WEST);
        p.add(lblCuenta, BorderLayout.EAST);

        JPanel linea = new JPanel();
        linea.setBackground(EstiloATM.AMBAR);
        linea.setPreferredSize(new Dimension(0, 2));
        p.add(linea, BorderLayout.SOUTH);

        return p;
    }

    private JPanel crearContenido() {
        JPanel p = new JPanel(new BorderLayout(0, 24));
        p.setBackground(EstiloATM.FONDO_OSCURO);
        p.setBorder(BorderFactory.createEmptyBorder(28, 50, 20, 50));

        // ── Saldo prominente ─────────────────────────────────────────────
        JPanel panelSaldo = crearPanelSaldo();

        // ── Grid de 4 operaciones ────────────────────────────────────────
        JPanel grid = crearGridOperaciones();

        p.add(panelSaldo, BorderLayout.NORTH);
        p.add(grid,       BorderLayout.CENTER);

        return p;
    }

    private JPanel crearPanelSaldo() {
        JPanel card = EstiloATM.panelCard();
        card.setLayout(new GridLayout(1, 3, 0, 0));
        card.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));
        card.setPreferredSize(new Dimension(0, 100));

        // Columna: Cliente
        JPanel colCliente = new JPanel(new GridLayout(2, 1, 0, 4));
        colCliente.setOpaque(false);
        JLabel tit1 = EstiloATM.labelGris("TITULAR");
        lblCliente = new JLabel("—");
        lblCliente.setFont(EstiloATM.FUENTE_MONO_NORMAL);
        lblCliente.setForeground(EstiloATM.TEXTO_BLANCO);
        colCliente.add(tit1);
        colCliente.add(lblCliente);

        // Columna: Tipo de cuenta
        JPanel colTipo = new JPanel(new GridLayout(2, 1, 0, 4));
        colTipo.setOpaque(false);
        JLabel tit2 = EstiloATM.labelGris("TIPO DE CUENTA");
        tit2.setHorizontalAlignment(SwingConstants.CENTER);
        lblTipoCuenta = new JLabel("—", SwingConstants.CENTER);
        lblTipoCuenta.setFont(EstiloATM.FUENTE_MONO_NORMAL);
        lblTipoCuenta.setForeground(EstiloATM.AMBAR);
        colTipo.add(tit2);
        colTipo.add(lblTipoCuenta);

        // Columna: Saldo
        JPanel colSaldo = new JPanel(new GridLayout(2, 1, 0, 4));
        colSaldo.setOpaque(false);
        JLabel tit3 = EstiloATM.labelGris("SALDO DISPONIBLE");
        tit3.setHorizontalAlignment(SwingConstants.RIGHT);
        lblSaldo = new JLabel("$0.00", SwingConstants.RIGHT);
        lblSaldo.setFont(EstiloATM.FUENTE_SALDO);
        lblSaldo.setForeground(EstiloATM.VERDE_EXITO);
        colSaldo.add(tit3);
        colSaldo.add(lblSaldo);

        card.add(colCliente);
        card.add(colTipo);
        card.add(colSaldo);

        return card;
    }

    /**
     * Crea el grid 2×2 con los 4 botones de operación.
     * Cada botón tiene ícono, título y descripción breve.
     */
    private JPanel crearGridOperaciones() {
        JPanel grid = new JPanel(new GridLayout(2, 2, 16, 16));
        grid.setOpaque(false);

        grid.add(crearBotonOperacion(
            "◎", "CONSULTAR SALDO",
            "Ver el saldo actual de la cuenta",
            EstiloATM.AMBAR,
            () -> irA(ATMSwingApp.PANEL_SALDO)
        ));

        grid.add(crearBotonOperacion(
            "▼", "RETIRAR DINERO",
            "Retirar efectivo de la cuenta",
            new Color(255, 100, 80),
            () -> irA(ATMSwingApp.PANEL_RETIRO)
        ));

        grid.add(crearBotonOperacion(
            "▲", "DEPOSITAR DINERO",
            "Ingresar dinero en la cuenta",
            EstiloATM.VERDE_EXITO,
            () -> irA(ATMSwingApp.PANEL_DEPOSITO)
        ));

        grid.add(crearBotonOperacion(
            "☰", "MINI DECLARACIÓN",
            "Ver los últimos movimientos",
            new Color(80, 160, 255),
            () -> irA(ATMSwingApp.PANEL_MINI)
        ));

        return grid;
    }

    /**
     * Crea una tarjeta-botón de operación.
     * Componente reutilizable con ícono, título y descripción.
     */
    private JPanel crearBotonOperacion(String icono, String titulo,
                                        String descripcion, Color color,
                                        Runnable accion) {
        JPanel card = new JPanel(new BorderLayout(0, 10)) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                                    RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(EstiloATM.FONDO_CARD);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 14, 14);
                g2.setColor(color.darker());
                g2.setStroke(new BasicStroke(1f));
                g2.drawRoundRect(0, 0, getWidth()-1, getHeight()-1, 14, 14);
                g2.dispose();
            }
        };
        card.setOpaque(false);
        card.setBorder(BorderFactory.createEmptyBorder(22, 24, 22, 24));
        card.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Ícono grande
        JLabel lblIcono = new JLabel(icono, SwingConstants.CENTER);
        lblIcono.setFont(new Font("Monospaced", Font.BOLD, 30));
        lblIcono.setForeground(color);

        // Textos
        JPanel textos = new JPanel(new GridLayout(2, 1, 0, 4));
        textos.setOpaque(false);

        JLabel lblTitulo = new JLabel(titulo, SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Monospaced", Font.BOLD, 14));
        lblTitulo.setForeground(EstiloATM.TEXTO_BLANCO);

        JLabel lblDesc = new JLabel(descripcion, SwingConstants.CENTER);
        lblDesc.setFont(EstiloATM.FUENTE_MONO_CHICA);
        lblDesc.setForeground(EstiloATM.TEXTO_GRIS);

        textos.add(lblTitulo);
        textos.add(lblDesc);

        card.add(lblIcono, BorderLayout.CENTER);
        card.add(textos,   BorderLayout.SOUTH);

        // Hover
        card.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
                card.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(color, 2),
                    BorderFactory.createEmptyBorder(20, 22, 20, 22)
                ));
                lblIcono.setForeground(color.brighter());
                card.repaint();
            }
            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
                card.setBorder(BorderFactory.createEmptyBorder(22, 24, 22, 24));
                lblIcono.setForeground(color);
                card.repaint();
            }
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                accion.run();
            }
        });

        return card;
    }

    private JPanel crearPie() {
        JPanel p = new JPanel(new FlowLayout(FlowLayout.RIGHT, 24, 12));
        p.setBackground(EstiloATM.FONDO_CARD);
        p.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, EstiloATM.SEPARADOR));

        JButton btnCambiarCuenta = EstiloATM.botonSecundario("◄ Cambiar cuenta");
        btnCambiarCuenta.setPreferredSize(new Dimension(180, 38));
        btnCambiarCuenta.addActionListener(e -> app.mostrarPanel(ATMSwingApp.PANEL_CUENTAS));

        JButton btnSalir = EstiloATM.botonPrimario("✕  SALIR / FINALIZAR");
        btnSalir.setPreferredSize(new Dimension(200, 38));
        btnSalir.addActionListener(e -> {
            int resp = JOptionPane.showConfirmDialog(
                this,
                "¿Desea finalizar la sesión y retirar su tarjeta?",
                "Cerrar sesión",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE
            );
            if (resp == JOptionPane.YES_OPTION) {
                app.getAtm().cerrarSesion();
                app.mostrarPanel(ATMSwingApp.PANEL_BIENVENIDA);
            }
        });

        p.add(btnCambiarCuenta);
        p.add(btnSalir);

        return p;
    }

    private void irA(String panel) {
        app.mostrarPanel(panel);
    }

    /** Actualiza los datos del cliente y cuenta al entrar a este panel */
    public void actualizar() {
        if (app.getAtm().getClienteActual() != null) {
            lblCliente.setText(app.getAtm().getClienteActual().getNombre());
        }
        if (app.getAtm().getCuentaActual() != null) {
            Cuenta c = app.getAtm().getCuentaActual();
            lblCuenta.setText("Cuenta: " + c.getNumeroCuenta());
            lblTipoCuenta.setText(c.getClass().getSimpleName().toUpperCase());
            lblSaldo.setText(String.format("$%,.2f", c.getSaldo()));
        }
    }
}
