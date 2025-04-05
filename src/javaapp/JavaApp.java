package javaapp;

import java.sql.*;
import java.util.Scanner;

public class JavaApp {
    static final String URL = "jdbc:mysql://localhost:3306/dbcrud";
    static final String USER = "root";
    static final String PASSWORD = "";

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int opcion;

        do {
            System.out.println("\n---- MENÚ ----");
            System.out.println("1. Registrar usuario");
            System.out.println("2. Consultar usuarios");
            System.out.println("3. Actualizar usuario");
            System.out.println("4. Eliminar usuario");
            System.out.println("5. Salir");
            System.out.print("Seleccione una opción: ");
            opcion = sc.nextInt();
            sc.nextLine(); // limpiar buffer

            switch (opcion) {
                case 1 -> registrarUsuario(sc);
                case 2 -> consultarUsuarios();
                case 3 -> actualizarUsuario(sc);
                case 4 -> eliminarUsuario(sc);
                case 5 -> System.out.println("Saliendo...");
                default -> System.out.println("Opción inválida");
            }
        } while (opcion != 5);

        sc.close();
    }

    public static void registrarUsuario(Scanner sc) {
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {
            System.out.print("Nombre: ");
            String nombre = sc.nextLine();

            System.out.print("Correo: ");
            String correo = sc.nextLine();

            System.out.print("Contraseña: ");
            String clave = sc.nextLine();

            String sql = "INSERT INTO usuarios (name, email, password) VALUES (?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, nombre);
            stmt.setString(2, correo);
            stmt.setString(3, clave);

            int filas = stmt.executeUpdate();
            if (filas > 0) System.out.println("Usuario registrado correctamente.");

        } catch (SQLException e) {
            System.out.println("Error al registrar: " + e.getMessage());
        }
    }

    public static void consultarUsuarios() {
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {
            String sql = "SELECT * FROM usuarios";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            System.out.println("\n--- Lista de usuarios ---");
            while (rs.next()) {
                System.out.println("ID: " + rs.getInt("id") +
                                   " | Nombre: " + rs.getString("name") +
                                   " | Email: " + rs.getString("email"));
            }

        } catch (SQLException e) {
            System.out.println("Error al consultar: " + e.getMessage());
        }
    }

    public static void actualizarUsuario(Scanner sc) {
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {
            System.out.print("ID del usuario a actualizar: ");
            int id = sc.nextInt();
            sc.nextLine();

            System.out.print("Nuevo nombre: ");
            String nombre = sc.nextLine();

            System.out.print("Nuevo correo: ");
            String correo = sc.nextLine();

            System.out.print("Nueva contraseña: ");
            String clave = sc.nextLine();

            String sql = "UPDATE usuarios SET name = ?, email = ?, password = ? WHERE id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, nombre);
            stmt.setString(2, correo);
            stmt.setString(3, clave);
            stmt.setInt(4, id);

            int filas = stmt.executeUpdate();
            if (filas > 0) System.out.println("Usuario actualizado correctamente.");
            else System.out.println("Usuario no encontrado.");

        } catch (SQLException e) {
            System.out.println("Error al actualizar: " + e.getMessage());
        }
    }

    public static void eliminarUsuario(Scanner sc) {
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {
            System.out.print("ID del usuario a eliminar: ");
            int id = sc.nextInt();
            sc.nextLine();

            String sql = "DELETE FROM usuarios WHERE id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);

            int filas = stmt.executeUpdate();
            if (filas > 0) System.out.println("Usuario eliminado correctamente.");
            else System.out.println("Usuario no encontrado.");

        } catch (SQLException e) {
            System.out.println("Error al eliminar: " + e.getMessage());
        }
    }
}