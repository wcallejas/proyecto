package main;

import modelo.Ahorro;
import modelo.Cliente;
import modelo.Comprobaciones;
import servicio.ATM;
import servicio.Banco;
import vista.swing.ATMSwingApp;
import vista.swing.EstiloATM;

import javax.swing.*;

/**
 * ╔══════════════════════════════════════════════════════╗
 * ║  CLASE: MainSwing                                    ║
 * ║  PAQUETE: main                                       ║
 * ╠══════════════════════════════════════════════════════╣
 * ║  PUNTO DE ENTRADA de la versión Swing del ATM        ║
 * ║  Crea los objetos del modelo y lanza la ventana      ║
 * ╠══════════════════════════════════════════════════════╣
 * ║  CONCEPTO SWING:                                     ║
 * ║  SwingUtilities.invokeLater() garantiza que la UI    ║
 * ║  se construya en el hilo de Swing (EDT), que es el   ║
 * ║  único hilo autorizado para modificar componentes.   ║
 * ╚══════════════════════════════════════════════════════╝
 *
 * ─────────────────────────────────────────────────────────
 * CREDENCIALES DE PRUEBA:
 *   Número de cliente : 1001 (Carlos López)
 *                       1002 (María García)
 *   PIN del cajero    : 1234
 *   Cuenta Ahorro     : AHO-001-2024
 *   Cuenta Corriente  : COR-002-2024
 * ─────────────────────────────────────────────────────────
 */
public class MainSwing {

    public static void main(String[] args) {

        // ── PASO 1: Aplicar tema visual antes de construir la ventana ────────
        EstiloATM.aplicarLookAndFeel();

        // ── PASO 2: Crear el mundo de objetos (mismo que en la versión consola)
        Banco banco = new Banco("BancoJava S.A.", "Bogotá, Colombia");

        // Clientes
        Cliente cliente1 = new Cliente("Carlos López",  320123456, "carlos@email.com", 1001);
        Cliente cliente2 = new Cliente("María García",  311987654, "maria@email.com",  1002);
        banco.agregarCliente(cliente1);
        banco.agregarCliente(cliente2);

        // Cuentas (Herencia: Ahorro y Comprobaciones extienden Cuenta)
        Ahorro cuentaAhorro = new Ahorro(
            "AHO-001-2024", "BancoJava S.A.", 5000.00, 3.5, 5);

        Comprobaciones cuentaCorriente = new Comprobaciones(
            "COR-002-2024", "BancoJava S.A.", 2500.00, 500.00);

        banco.agregarCuenta(cuentaAhorro);
        banco.agregarCuenta(cuentaCorriente);

        // ATM (Asociación con Banco)
        ATM cajero = new ATM(1234, "Bogotá — Centro", "BancoJava S.A.", banco);

        // ── PASO 3: Lanzar la ventana Swing en el Event Dispatch Thread (EDT) ─
        //
        // ¿Por qué invokeLater?
        // Swing no es thread-safe. Todos los cambios en la interfaz
        // deben hacerse desde el hilo EDT para evitar condiciones de carrera.
        // invokeLater() encola la tarea para ejecutarse en ese hilo.

        SwingUtilities.invokeLater(() -> {
            ATMSwingApp ventana = new ATMSwingApp(cajero);
            ventana.setVisible(true);
        });
    }
}
