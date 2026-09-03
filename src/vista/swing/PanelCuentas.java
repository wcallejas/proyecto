package vista.swing;

import modelo.Ahorro;
import modelo.Comprobaciones;
import modelo.Cuenta;
import servicio.Banco;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * ╔══════════════════════════════════════════════════════╗
 * ║  CLASE: PanelCuentas                                 ║
 * ║  PAQUETE: vista.swing                                ║
 * ╠══════════════════════════════════════════════════════╣
 * ║  PANTALLA 3: Selección de cuenta                     ║
 * ║  Muestra todas las cuentas del banco como tarjetas   ║
 * ╚══════════════════════════════════════════════════════╝
 */
public class PanelCuentas extends JPanel {

    private ATMSwingApp app;
    private JPanel      panelCuentas; // área donde se colocan las tarjetas
    private JLabel      lblCliente;

    public PanelCuentas(ATMSwingApp app) {
        this.app = app;
        setBackground(EstiloATM.FONDO_OSCURO);
        setLayout(new BorderLayout(0, 0));
        construirUI();
    }

    private void construirUI() {
        add(crearCabecera(), BorderLayout.NORTH);

        JPanel centro = new JPanel(new BorderLayout(0, 16));
        centro.setBackground(EstiloATM.FONDO_OSCURO);
        centro.setBorder(BorderFactory.createEmptyBorder(30, 60, 30, 60));

        // Título de sección
        JPanel titSection = new JPanel(new GridLayout(2, 1, 0, 6));
        titSection.setOpaque(false);

        JLabel lblTitulo = EstiloATM.labelAmbar(
            "SELECCIONE UNA CUENTA", EstiloATM.FUENTE_MONO_TITULO);

        lblCliente = EstiloATM.labelGris("Titular: —");

        titSection.add(lblTitulo);
        titSection.add(lblCliente);

        // Panel donde se agregarán las tarjetas de cuenta dinámicamente
        panelCuentas = new JPanel();
        panelCuentas.setOpaque(false);
        panelCuentas.setLayout(new BoxLayout(panelCuentas, BoxLayout.Y_AXIS));

        JScrollPane scroll = new JScrollPane(panelCuentas);
        scroll.setOpaque(false);
        scroll.getViewport().setOpaque(false);
        scroll.setBorder(null);

        centro.add(titSection, BorderLayout.NORTH);
        centro.add(scroll,     BorderLayout.CENTER);

        add(centro, BorderLayout.CENTER);
    }

    private JPanel crearCabecera() {
        JPanel p = new JPanel(new BorderLayout());
        p.setBackground(EstiloATM.FONDO_CARD);
        p.setBorder(BorderFactory.createEmptyBorder(14, 28, 14, 28));

        JLabel lblNombre = EstiloATM.labelAmbar("  ◈  BANCOJAVA S.A.",
                                                 EstiloATM.FUENTE_MONO_TITULO);
        JLabel lblPaso   = EstiloATM.labelGris("Paso 3 de 3 — Selección de cuenta");

        p.add(lblNombre, BorderLayout.WEST);
        p.add(lblPaso,   BorderLayout.EAST);

        JPanel linea = new JPanel();
        linea.setBackground(EstiloATM.AMBAR);
        linea.setPreferredSize(new Dimension(0, 2));
        p.add(linea, BorderLayout.SOUTH);

        return p;
    }

    /**
     * Recarga la lista de cuentas desde el banco.
     * Se llama cada vez que se muestra esta pantalla.
     */
    public void cargarCuentas() {
        panelCuentas.removeAll();

        // Actualizar nombre del cliente
        if (app.getAtm().getClienteActual() != null) {
            lblCliente.setText("Titular: " + app.getAtm().getClienteActual().getNombre());
        }

        // Obtener cuentas del banco y crear una tarjeta por cada una
        List<Cuenta> cuentas = app.getAtm().getBanco().getCuentas();

        for (Cuenta cuenta : cuentas) {
            panelCuentas.add(crearTarjetaCuenta(cuenta));
            panelCuentas.add(Box.createRigidArea(new Dimension(0, 16)));
        }

        panelCuentas.revalidate();
        panelCuentas.repaint();
    }

    /**
     * Crea una tarjeta visual para una cuenta bancaria.
     * Muestra: tipo de cuenta, número, saldo e información adicional.
     *
     * @param cuenta la cuenta a representar visualmente
     * @return panel con la tarjeta de cuenta
     */
    private JPanel crearTarjetaCuenta(Cuenta cuenta) {

        // Determinar tipo y color de acento
        boolean esAhorro = cuenta instanceof Ahorro;
        Color colorTipo  = esAhorro ? EstiloATM.AMBAR : new Color(80, 180, 255);
        String tipoTexto = esAhorro ? "CUENTA DE AHORRO" : "CUENTA CORRIENTE";
        String icono     = esAhorro ? "⬡" : "◈";

        // Panel tarjeta personalizado con borde de color
        JPanel tarjeta = new JPanel(new BorderLayout(14, 0)) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                                    RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(EstiloATM.FONDO_CARD);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 14, 14);
                g2.setColor(colorTipo);
                g2.setStroke(new BasicStroke(1.5f));
                g2.drawRoundRect(0, 0, getWidth()-1, getHeight()-1, 14, 14);
                // Barra lateral izquierda de color
                g2.fillRoundRect(0, 0, 6, getHeight(), 4, 4);
                g2.dispose();
            }
        };
        tarjeta.setOpaque(false);
        tarjeta.setBorder(BorderFactory.createEmptyBorder(18, 24, 18, 24));
        tarjeta.setMaximumSize(new Dimension(Integer.MAX_VALUE, 120));
        tarjeta.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // ── Columna izquierda: tipo e ícono ──────────────────────────────
        JPanel colIzq = new JPanel(new GridLayout(3, 1, 0, 4));
        colIzq.setOpaque(false);

        JLabel lblIcono = new JLabel(icono + "  " + tipoTexto);
        lblIcono.setFont(new Font("Monospaced", Font.BOLD, 13));
        lblIcono.setForeground(colorTipo);

        JLabel lblNumero = new JLabel(cuenta.getNumeroCuenta());
        lblNumero.setFont(EstiloATM.FUENTE_MONO_NORMAL);
        lblNumero.setForeground(EstiloATM.TEXTO_BLANCO);

        JLabel lblBanco = EstiloATM.labelGris(cuenta.getNombreBanco());

        colIzq.add(lblIcono);
        colIzq.add(lblNumero);
        colIzq.add(lblBanco);

        // ── Columna derecha: saldo + botón ──────────────────────────────
        JPanel colDer = new JPanel(new GridLayout(3, 1, 0, 4));
        colDer.setOpaque(false);

        JLabel lblSaldoTit = EstiloATM.labelGris("Saldo disponible");
        lblSaldoTit.setHorizontalAlignment(SwingConstants.RIGHT);

        JLabel lblSaldo = new JLabel(
            String.format("$%,.2f", cuenta.getSaldo()), SwingConstants.RIGHT);
        lblSaldo.setFont(new Font("Monospaced", Font.BOLD, 22));
        lblSaldo.setForeground(EstiloATM.VERDE_EXITO);

        // Información adicional según tipo de cuenta
        String infoExtra = "";
        if (esAhorro) {
            Ahorro a = (Ahorro) cuenta;
            infoExtra = String.format("Interés: %.1f%%/año", a.getTasaInteres());
        } else {
            Comprobaciones c = (Comprobaciones) cuenta;
            infoExtra = String.format("Sobregiro: $%,.0f", c.getLimiteSobregiro());
        }
        JLabel lblExtra = EstiloATM.labelGris(infoExtra);
        lblExtra.setHorizontalAlignment(SwingConstants.RIGHT);

        colDer.add(lblSaldoTit);
        colDer.add(lblSaldo);
        colDer.add(lblExtra);

        tarjeta.add(colIzq, BorderLayout.CENTER);
        tarjeta.add(colDer, BorderLayout.EAST);

        // ── Evento: click en la tarjeta para seleccionar la cuenta ────────
        tarjeta.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                seleccionarCuenta(cuenta.getNumeroCuenta());
            }
            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
                tarjeta.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(colorTipo, 2),
                    BorderFactory.createEmptyBorder(16, 22, 16, 22)
                ));
            }
            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
                tarjeta.setBorder(BorderFactory.createEmptyBorder(18, 24, 18, 24));
            }
        });

        return tarjeta;
    }

    private void seleccionarCuenta(String numeroCuenta) {
        if (app.getAtm().seleccionarCuenta(numeroCuenta)) {
            app.mostrarPanel(ATMSwingApp.PANEL_MENU);
        }
    }
}
