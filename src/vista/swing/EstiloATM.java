package vista.swing;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;

/**
 * ╔══════════════════════════════════════════════════════╗
 * ║  CLASE: EstiloATM                                    ║
 * ║  PAQUETE: vista.swing                                ║
 * ╠══════════════════════════════════════════════════════╣
 * ║  PROPÓSITO: Sistema de diseño (Design System)        ║
 * ║  Centraliza colores, fuentes y métodos de UI         ║
 * ║  para mantener coherencia visual en todos los paneles║
 * ╚══════════════════════════════════════════════════════╝
 *
 * Principio de diseño: estética de terminal bancaria real.
 * Tema oscuro profundo + ámbar dorado como color de acento.
 */
public class EstiloATM {

    // ── PALETA DE COLORES ──────────────────────────────────────────────────

    /** Fondo principal: azul muy oscuro (casi negro) */
    public static final Color FONDO_OSCURO      = new Color(8, 12, 24);

    /** Fondo de paneles internos: ligeramente más claro */
    public static final Color FONDO_PANEL       = new Color(14, 20, 38);

    /** Fondo de tarjetas y secciones elevadas */
    public static final Color FONDO_CARD        = new Color(20, 28, 52);

    /** Borde sutil de paneles */
    public static final Color BORDE_COLOR       = new Color(35, 50, 80);

    /** ámbar dorado — color de acento principal (texto resaltado, bordes activos) */
    public static final Color AMBAR             = new Color(255, 185, 0);

    /** ámbar oscuro para hover de botones */
    public static final Color AMBAR_OSCURO      = new Color(200, 140, 0);

    /** Verde éxito */
    public static final Color VERDE_EXITO       = new Color(0, 210, 120);

    /** Rojo error */
    public static final Color ROJO_ERROR        = new Color(255, 75, 75);

    /** Texto principal: blanco */
    public static final Color TEXTO_BLANCO      = new Color(230, 235, 245);

    /** Texto secundario: gris azulado */
    public static final Color TEXTO_GRIS        = new Color(120, 140, 175);

    /** Color de la pantalla interna del cajero */
    public static final Color PANTALLA_FONDO    = new Color(10, 15, 30);

    /** Separador sutil */
    public static final Color SEPARADOR         = new Color(28, 40, 70);


    // ── TIPOGRAFÍA ─────────────────────────────────────────────────────────

    /** Fuente monoespaciada: el look de terminal bancaria */
    public static final Font FUENTE_MONO_GRANDE  = new Font("Monospaced", Font.BOLD,  22);
    public static final Font FUENTE_MONO_TITULO  = new Font("Monospaced", Font.BOLD,  18);
    public static final Font FUENTE_MONO_NORMAL  = new Font("Monospaced", Font.PLAIN, 14);
    public static final Font FUENTE_MONO_CHICA   = new Font("Monospaced", Font.PLAIN, 12);
    public static final Font FUENTE_DISPLAY      = new Font("Monospaced", Font.BOLD,  28);
    public static final Font FUENTE_SALDO        = new Font("Monospaced", Font.BOLD,  36);
    public static final Font FUENTE_BOTON        = new Font("Monospaced", Font.BOLD,  15);
    public static final Font FUENTE_TECLADO      = new Font("Monospaced", Font.BOLD,  20);


    // ── FÁBRICA DE COMPONENTES ─────────────────────────────────────────────

    /**
     * Crea un JLabel con estilo de texto principal (blanco).
     */
    public static JLabel labelBlanco(String texto, Font fuente) {
        JLabel lbl = new JLabel(texto);
        lbl.setForeground(TEXTO_BLANCO);
        lbl.setFont(fuente);
        return lbl;
    }

    /**
     * Crea un JLabel con el color ámbar (énfasis).
     */
    public static JLabel labelAmbar(String texto, Font fuente) {
        JLabel lbl = new JLabel(texto, SwingConstants.CENTER);
        lbl.setForeground(AMBAR);
        lbl.setFont(fuente);
        return lbl;
    }

    /**
     * Crea un JLabel con texto gris (información secundaria).
     */
    public static JLabel labelGris(String texto) {
        JLabel lbl = new JLabel(texto);
        lbl.setForeground(TEXTO_GRIS);
        lbl.setFont(FUENTE_MONO_CHICA);
        return lbl;
    }

    /**
     * Crea un botón principal con estilo ámbar.
     * Animación: oscurece al pasar el mouse (hover).
     *
     * @param texto  etiqueta del botón
     * @return botón estilizado
     */
    public static JButton botonPrimario(String texto) {
        JButton btn = new JButton(texto) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                                    RenderingHints.VALUE_ANTIALIAS_ON);
                Color fondo = getModel().isPressed()  ? AMBAR_OSCURO :
                              getModel().isRollover() ? AMBAR.brighter() : AMBAR;
                g2.setColor(fondo);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);
                g2.dispose();
                super.paintComponent(g);
            }
        };
        btn.setFont(FUENTE_BOTON);
        btn.setForeground(FONDO_OSCURO);
        btn.setBackground(AMBAR);
        btn.setOpaque(false);
        btn.setContentAreaFilled(false);
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setPreferredSize(new Dimension(200, 44));
        return btn;
    }

    /**
     * Crea un botón secundario con borde ámbar y fondo transparente.
     */
    public static JButton botonSecundario(String texto) {
        JButton btn = new JButton(texto) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                                    RenderingHints.VALUE_ANTIALIAS_ON);
                if (getModel().isRollover()) {
                    g2.setColor(new Color(255, 185, 0, 30));
                    g2.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);
                }
                g2.setColor(AMBAR);
                g2.setStroke(new BasicStroke(1.5f));
                g2.drawRoundRect(1, 1, getWidth()-2, getHeight()-2, 10, 10);
                g2.dispose();
                super.paintComponent(g);
            }
        };
        btn.setFont(FUENTE_BOTON);
        btn.setForeground(AMBAR);
        btn.setOpaque(false);
        btn.setContentAreaFilled(false);
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setPreferredSize(new Dimension(200, 44));
        return btn;
    }

    /**
     * Crea un botón para el teclado numérico del PIN.
     * Diseño: cuadrado oscuro con número ámbar.
     *
     * @param label texto del botón (dígito o acción)
     * @return botón de teclado estilizado
     */
    public static JButton botonTeclado(String label) {
        JButton btn = new JButton(label) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                                    RenderingHints.VALUE_ANTIALIAS_ON);
                Color fondo = getModel().isPressed()  ? new Color(40, 55, 90) :
                              getModel().isRollover() ? new Color(30, 45, 75) :
                                                        new Color(20, 32, 58);
                g2.setColor(fondo);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 8, 8);
                g2.setColor(new Color(40, 60, 100));
                g2.setStroke(new BasicStroke(1f));
                g2.drawRoundRect(0, 0, getWidth()-1, getHeight()-1, 8, 8);
                g2.dispose();
                super.paintComponent(g);
            }
        };
        btn.setFont(FUENTE_TECLADO);
        btn.setForeground(label.matches("[0-9]") ? AMBAR : TEXTO_GRIS);
        btn.setOpaque(false);
        btn.setContentAreaFilled(false);
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setPreferredSize(new Dimension(72, 60));
        return btn;
    }

    /**
     * Crea un JPanel con el fondo principal oscuro.
     */
    public static JPanel panelOscuro() {
        JPanel p = new JPanel();
        p.setBackground(FONDO_OSCURO);
        return p;
    }

    /**
     * Crea un JPanel con fondo de tarjeta elevada.
     * Tiene borde redondeado visual (se pinta manualmente).
     */
    public static JPanel panelCard() {
        JPanel p = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                                    RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(FONDO_CARD);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 16, 16);
                g2.setColor(BORDE_COLOR);
                g2.setStroke(new BasicStroke(1f));
                g2.drawRoundRect(0, 0, getWidth()-1, getHeight()-1, 16, 16);
                g2.dispose();
            }
        };
        p.setOpaque(false);
        return p;
    }

    /**
     * Crea un JTextField estilizado (fondo oscuro, texto ámbar, borde sutil).
     */
    public static JTextField campoTexto(int columnas) {
        JTextField tf = new JTextField(columnas);
        tf.setBackground(new Color(15, 22, 42));
        tf.setForeground(AMBAR);
        tf.setCaretColor(AMBAR);
        tf.setFont(FUENTE_MONO_NORMAL);
        tf.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(BORDE_COLOR, 1),
            BorderFactory.createEmptyBorder(8, 12, 8, 12)
        ));
        return tf;
    }

    /**
     * Crea un JPasswordField estilizado.
     */
    public static JPasswordField campoPassword(int columnas) {
        JPasswordField pf = new JPasswordField(columnas);
        pf.setBackground(new Color(15, 22, 42));
        pf.setForeground(AMBAR);
        pf.setCaretColor(AMBAR);
        pf.setFont(FUENTE_MONO_NORMAL);
        pf.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(BORDE_COLOR, 1),
            BorderFactory.createEmptyBorder(8, 12, 8, 12)
        ));
        return pf;
    }

    /**
     * Crea un separador horizontal de color sutil.
     */
    public static JSeparator separador() {
        JSeparator sep = new JSeparator();
        sep.setForeground(SEPARADOR);
        sep.setBackground(FONDO_OSCURO);
        return sep;
    }

    /**
     * Aplica el Look and Feel "Nimbus" si está disponible.
     * Mejora la apariencia general de los componentes Swing.
     */
    public static void aplicarLookAndFeel() {
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception e) {
            // Si Nimbus no está disponible, usa el L&F por defecto
        }
        // Aplica colores globales a los componentes Swing
        UIManager.put("Panel.background",          FONDO_OSCURO);
        UIManager.put("OptionPane.background",     FONDO_PANEL);
        UIManager.put("OptionPane.messageForeground", TEXTO_BLANCO);
    }
}
