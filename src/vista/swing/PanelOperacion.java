package vista.swing;

import modelo.*;
import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.text.SimpleDateFormat;

/**
 * ╔══════════════════════════════════════════════════════╗
 * ║  CLASE: PanelOperacion                               ║
 * ║  PAQUETE: vista.swing                                ║
 * ╠══════════════════════════════════════════════════════╣
 * ║  PANTALLAS 5-8: Saldo, Retiro, Depósito,             ║
 * ║                  Mini Declaración                    ║
 * ║  Panel reutilizable que cambia según el tipo         ║
 * ╚══════════════════════════════════════════════════════╝
 */
public class PanelOperacion extends JPanel {

    // ── TIPOS DE OPERACIÓN ─────────────────────────────────────────────────
    public static final String SALDO   = "SALDO";
    public static final String RETIRO  = "RETIRO";
    public static final String DEPOSITO = "DEPOSITO";
    public static final String MINI    = "MINI";

    // ── REFERENCIAS ────────────────────────────────────────────────────────
    private ATMSwingApp app;
    private String      tipo;

    // ── COMPONENTES DINÁMICOS ──────────────────────────────────────────────
    private JPanel  panelContenido;  // zona central que se reemplaza

    public PanelOperacion(ATMSwingApp app, String tipo) {
        this.app  = app;
        this.tipo = tipo;
        setBackground(EstiloATM.FONDO_OSCURO);
        setLayout(new BorderLayout());
        construirEstructura();
    }

    private void construirEstructura() {
        add(crearCabecera(), BorderLayout.NORTH);

        panelContenido = new JPanel(new GridBagLayout());
        panelContenido.setBackground(EstiloATM.FONDO_OSCURO);
        add(panelContenido, BorderLayout.CENTER);

        add(crearPie(), BorderLayout.SOUTH);
    }

    /** Refresca la pantalla con los datos actuales */
    public void actualizar() {
        panelContenido.removeAll();

        JPanel card = switch (tipo) {
            case SALDO    -> construirSaldo();
            case RETIRO   -> construirFormulario(
                "RETIRAR DINERO", "▼", new Color(255,100,80),
                "Monto a retirar:", false);
            case DEPOSITO -> construirFormulario(
                "DEPOSITAR DINERO", "▲", EstiloATM.VERDE_EXITO,
                "Monto a depositar:", true);
            case MINI     -> construirMiniDeclaracion();
            default       -> new JPanel();
        };

        panelContenido.add(card);
        panelContenido.revalidate();
        panelContenido.repaint();
    }

    // ── PANTALLA: CONSULTA DE SALDO ────────────────────────────────────────
    private JPanel construirSaldo() {
        JPanel card = EstiloATM.panelCard();
        card.setLayout(new GridLayout(5, 1, 0, 16));
        card.setBorder(BorderFactory.createEmptyBorder(44, 60, 44, 60));
        card.setPreferredSize(new Dimension(500, 340));

        JLabel lblTitulo = EstiloATM.labelAmbar("◎  CONSULTA DE SALDO",
                                                  EstiloATM.FUENTE_MONO_TITULO);

        JSeparator sep = EstiloATM.separador();

        Cuenta c = app.getAtm().getCuentaActual();

        // Número de cuenta
        JPanel fila1 = crearFilaDato("Número de cuenta:", c.getNumeroCuenta());

        // Tipo de cuenta
        JPanel fila2 = crearFilaDato("Tipo de cuenta:",
                                      c.getClass().getSimpleName());

        // Saldo principal con fuente grande
        JPanel filaSaldo = new JPanel(new BorderLayout());
        filaSaldo.setOpaque(false);
        JLabel lblTitSaldo = EstiloATM.labelGris("Saldo disponible:");
        JLabel lblSaldo = new JLabel(String.format("$  %,.2f", c.getSaldo()),
                                      SwingConstants.RIGHT);
        lblSaldo.setFont(EstiloATM.FUENTE_SALDO);
        lblSaldo.setForeground(EstiloATM.VERDE_EXITO);
        filaSaldo.add(lblTitSaldo, BorderLayout.WEST);
        filaSaldo.add(lblSaldo, BorderLayout.EAST);

        card.add(lblTitulo);
        card.add(sep);
        card.add(fila1);
        card.add(fila2);
        card.add(filaSaldo);

        return card;
    }

    // ── PANTALLA: FORMULARIO (RETIRO / DEPÓSITO) ───────────────────────────
    private JPanel construirFormulario(String titulo, String icono, Color color,
                                        String labelMonto, boolean esDeposito) {
        JPanel card = EstiloATM.panelCard();
        card.setLayout(new BorderLayout(0, 20));
        card.setBorder(BorderFactory.createEmptyBorder(36, 50, 36, 50));
        card.setPreferredSize(new Dimension(500, 400));

        // Título
        JLabel lblTitulo = new JLabel(icono + "  " + titulo);
        lblTitulo.setFont(EstiloATM.FUENTE_MONO_TITULO);
        lblTitulo.setForeground(color);

        // Saldo actual
        Cuenta c = app.getAtm().getCuentaActual();
        JLabel lblSaldoActual = new JLabel(
            "Saldo actual:  " + String.format("$%,.2f", c.getSaldo()));
        lblSaldoActual.setFont(EstiloATM.FUENTE_MONO_NORMAL);
        lblSaldoActual.setForeground(EstiloATM.TEXTO_GRIS);

        JSeparator sep = EstiloATM.separador();

        // Campo de monto
        JPanel formulario = new JPanel(new GridLayout(3, 1, 0, 14));
        formulario.setOpaque(false);

        JLabel lblMonto = EstiloATM.labelGris(labelMonto);
        JTextField campoMonto = EstiloATM.campoTexto(12);
        campoMonto.setFont(EstiloATM.FUENTE_MONO_TITULO);
        campoMonto.setHorizontalAlignment(JTextField.CENTER);
        campoMonto.setToolTipText("Ingrese el monto en números enteros");

        // Montos rápidos
        JPanel montosRapidos = crearMontosRapidos(campoMonto);

        formulario.add(lblMonto);
        formulario.add(campoMonto);
        formulario.add(montosRapidos);

        // Botón confirmar
        JButton btnConfirmar = EstiloATM.botonPrimario(
            esDeposito ? "▲  DEPOSITAR" : "▼  RETIRAR");
        btnConfirmar.setBackground(color);
        btnConfirmar.setForeground(EstiloATM.FONDO_OSCURO);
        btnConfirmar.setPreferredSize(new Dimension(220, 48));

        JLabel lblResultado = new JLabel(" ", SwingConstants.CENTER);
        lblResultado.setFont(EstiloATM.FUENTE_MONO_NORMAL);

        btnConfirmar.addActionListener(e -> {
            String texto = campoMonto.getText().trim();
            try {
                int monto = Integer.parseInt(texto);
                if (esDeposito) {
                    app.getAtm().depositarDinero(monto);
                    lblResultado.setText("✅  Depósito de $" + monto + " realizado.");
                    lblResultado.setForeground(EstiloATM.VERDE_EXITO);
                } else {
                    boolean ok = app.getAtm().retirarDinero(monto);
                    if (ok) {
                        lblResultado.setText("✅  Retiro de $" + monto + " completado.");
                        lblResultado.setForeground(EstiloATM.VERDE_EXITO);
                    } else {
                        lblResultado.setText("❌  Saldo insuficiente para esta operación.");
                        lblResultado.setForeground(EstiloATM.ROJO_ERROR);
                    }
                }
                campoMonto.setText("");
                // Actualizar el menú con el nuevo saldo
                app.getPanelMenu().actualizar();
            } catch (NumberFormatException ex) {
                lblResultado.setText("⚠  Ingrese un monto válido (solo números).");
                lblResultado.setForeground(EstiloATM.AMBAR);
            }
        });

        campoMonto.addActionListener(e -> btnConfirmar.doClick());

        JPanel botones = new JPanel(new FlowLayout(FlowLayout.CENTER));
        botones.setOpaque(false);
        botones.add(btnConfirmar);

        card.add(lblTitulo,      BorderLayout.NORTH);
        card.add(lblSaldoActual, BorderLayout.CENTER);

        JPanel formCompleto = new JPanel(new BorderLayout(0, 12));
        formCompleto.setOpaque(false);
        formCompleto.add(sep,          BorderLayout.NORTH);
        formCompleto.add(formulario,   BorderLayout.CENTER);
        formCompleto.add(botones,      BorderLayout.SOUTH);

        card.add(formCompleto, BorderLayout.SOUTH);

        // Agregar resultado fuera del card en un panel externo
        JPanel wrapper = new JPanel(new BorderLayout(0, 10));
        wrapper.setOpaque(false);
        wrapper.add(card,         BorderLayout.CENTER);
        wrapper.add(lblResultado, BorderLayout.SOUTH);
        return wrapper;
    }

    /** Botones de montos predeterminados (50, 100, 200, 500) */
    private JPanel crearMontosRapidos(JTextField campo) {
        JPanel p = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0));
        p.setOpaque(false);

        JLabel lbl = EstiloATM.labelGris("Rápido: ");
        p.add(lbl);

        int[] montos = {50, 100, 200, 500};
        for (int m : montos) {
            JButton btn = new JButton("$" + m);
            btn.setFont(EstiloATM.FUENTE_MONO_CHICA);
            btn.setForeground(EstiloATM.AMBAR);
            btn.setBackground(new Color(25, 35, 60));
            btn.setBorder(BorderFactory.createLineBorder(EstiloATM.BORDE_COLOR));
            btn.setFocusPainted(false);
            btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
            btn.addActionListener(e -> campo.setText(String.valueOf(m)));
            p.add(btn);
        }
        return p;
    }

    // ── PANTALLA: MINI DECLARACIÓN ─────────────────────────────────────────
    private JPanel construirMiniDeclaracion() {
        JPanel card = EstiloATM.panelCard();
        card.setLayout(new BorderLayout(0, 16));
        card.setBorder(BorderFactory.createEmptyBorder(28, 36, 28, 36));
        card.setPreferredSize(new Dimension(620, 440));

        // Título
        JLabel lblTitulo = new JLabel("☰  MINI DECLARACIÓN");
        lblTitulo.setFont(EstiloATM.FUENTE_MONO_TITULO);
        lblTitulo.setForeground(new Color(80, 160, 255));

        Cuenta c = app.getAtm().getCuentaActual();
        JLabel lblCuenta = EstiloATM.labelGris(
            "Cuenta: " + c.getNumeroCuenta() + "   |   Saldo: $" +
            String.format("%,.2f", c.getSaldo()));

        // Generar la mini declaración
        MiniDeclaracion mini = new MiniDeclaracion(c.getNumeroCuenta());
        c.agregarTransaccion(mini);

        // ── Tabla de transacciones ─────────────────────────────────────────
        String[] columnas = {"TIPO", "IMPORTE", "FECHA"};
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");

        java.util.List<Transaccion> todas = c.getTransacciones();
        int inicio = Math.max(0, todas.size() - 8);

        Object[][] datos = new Object[todas.size() - inicio][3];
        for (int i = inicio; i < todas.size(); i++) {
            Transaccion t = todas.get(i);
            if (!(t instanceof MiniDeclaracion)) {
                datos[i - inicio][0] = t.getTipo();
                datos[i - inicio][1] = String.format("$%,d", t.getImporte());
                datos[i - inicio][2] = sdf.format(t.getFecha());
            } else {
                datos[i - inicio][0] = "CONSULTA MINI";
                datos[i - inicio][1] = "-";
                datos[i - inicio][2] = sdf.format(t.getFecha());
            }
        }

        JTable tabla = new JTable(datos, columnas) {
            @Override public boolean isCellEditable(int r, int c2) { return false; }
        };

        // Estilo de la tabla
        tabla.setBackground(new Color(12, 18, 36));
        tabla.setForeground(EstiloATM.TEXTO_BLANCO);
        tabla.setFont(EstiloATM.FUENTE_MONO_CHICA);
        tabla.setRowHeight(32);
        tabla.setGridColor(EstiloATM.SEPARADOR);
        tabla.setSelectionBackground(new Color(35, 55, 100));
        tabla.setSelectionForeground(EstiloATM.AMBAR);
        tabla.setShowHorizontalLines(true);
        tabla.setShowVerticalLines(false);
        tabla.setIntercellSpacing(new Dimension(0, 1));

        // Header
        JTableHeader header = tabla.getTableHeader();
        header.setBackground(EstiloATM.FONDO_CARD);
        header.setForeground(EstiloATM.AMBAR);
        header.setFont(new Font("Monospaced", Font.BOLD, 12));
        header.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, EstiloATM.AMBAR));

        // Colorear filas alternadas
        tabla.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable t, Object v,
                    boolean sel, boolean foc, int row, int col) {
                super.getTableCellRendererComponent(t, v, sel, foc, row, col);
                setOpaque(true);
                if (sel) {
                    setBackground(new Color(35, 55, 100));
                    setForeground(EstiloATM.AMBAR);
                } else {
                    setBackground(row % 2 == 0 ? new Color(12, 18, 36) : new Color(16, 24, 46));
                    // Colorear depósitos en verde y retiros en rojo
                    Object tipo = t.getValueAt(row, 0);
                    if ("DEPOSITO".equals(tipo)) setForeground(EstiloATM.VERDE_EXITO);
                    else if ("RETIRADA".equals(tipo)) setForeground(new Color(255, 120, 100));
                    else setForeground(EstiloATM.TEXTO_GRIS);
                }
                setBorder(BorderFactory.createEmptyBorder(0, 12, 0, 12));
                return this;
            }
        });

        JScrollPane scroll = new JScrollPane(tabla);
        scroll.setBackground(new Color(12, 18, 36));
        scroll.getViewport().setBackground(new Color(12, 18, 36));
        scroll.setBorder(BorderFactory.createLineBorder(EstiloATM.BORDE_COLOR));

        if (datos.length == 0) {
            JLabel lblVacio = new JLabel("Sin transacciones registradas.",
                                         SwingConstants.CENTER);
            lblVacio.setFont(EstiloATM.FUENTE_MONO_NORMAL);
            lblVacio.setForeground(EstiloATM.TEXTO_GRIS);
            card.add(lblTitulo, BorderLayout.NORTH);
            card.add(lblVacio,  BorderLayout.CENTER);
        } else {
            card.add(lblTitulo, BorderLayout.NORTH);
            card.add(lblCuenta, BorderLayout.CENTER);
            card.add(scroll,    BorderLayout.SOUTH);
        }

        return card;
    }

    // ── HELPER: fila de dato (etiqueta : valor) ────────────────────────────
    private JPanel crearFilaDato(String etiqueta, String valor) {
        JPanel fila = new JPanel(new BorderLayout());
        fila.setOpaque(false);
        JLabel lblEtiq = EstiloATM.labelGris(etiqueta);
        JLabel lblVal  = new JLabel(valor, SwingConstants.RIGHT);
        lblVal.setFont(EstiloATM.FUENTE_MONO_NORMAL);
        lblVal.setForeground(EstiloATM.TEXTO_BLANCO);
        fila.add(lblEtiq, BorderLayout.WEST);
        fila.add(lblVal,  BorderLayout.EAST);
        return fila;
    }

    // ── CABECERA Y PIE ─────────────────────────────────────────────────────
    private JPanel crearCabecera() {
        JPanel p = new JPanel(new BorderLayout());
        p.setBackground(EstiloATM.FONDO_CARD);
        p.setBorder(BorderFactory.createEmptyBorder(14, 28, 14, 28));

        JButton btnVolver = new JButton("◄  Volver al menú");
        btnVolver.setFont(EstiloATM.FUENTE_MONO_CHICA);
        btnVolver.setForeground(EstiloATM.TEXTO_GRIS);
        btnVolver.setBackground(EstiloATM.FONDO_CARD);
        btnVolver.setBorderPainted(false);
        btnVolver.setFocusPainted(false);
        btnVolver.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnVolver.addActionListener(e -> {
            app.getPanelMenu().actualizar();
            app.mostrarPanel(ATMSwingApp.PANEL_MENU);
        });

        JLabel lblLogo = EstiloATM.labelAmbar("  ◈  BANCOJAVA S.A.",
                                               EstiloATM.FUENTE_MONO_TITULO);

        String tituloHeader = switch (tipo) {
            case SALDO    -> "Consulta de saldo";
            case RETIRO   -> "Retiro de efectivo";
            case DEPOSITO -> "Depósito de dinero";
            case MINI     -> "Mini declaración";
            default -> "";
        };
        JLabel lblPaso = EstiloATM.labelGris(tituloHeader);

        p.add(btnVolver, BorderLayout.WEST);
        p.add(lblLogo,   BorderLayout.CENTER);
        p.add(lblPaso,   BorderLayout.EAST);

        JPanel linea = new JPanel();
        linea.setBackground(EstiloATM.AMBAR);
        linea.setPreferredSize(new Dimension(0, 2));
        p.add(linea, BorderLayout.SOUTH);

        return p;
    }

    private JPanel crearPie() {
        JPanel p = new JPanel(new FlowLayout(FlowLayout.CENTER));
        p.setBackground(EstiloATM.FONDO_CARD);
        p.setBorder(BorderFactory.createMatteBorder(1,0,0,0, EstiloATM.SEPARADOR));
        JLabel lbl = EstiloATM.labelGris("🔒  Todas sus operaciones están protegidas");
        p.add(lbl);
        return p;
    }
}
