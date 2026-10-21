/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.holamundo_avanzada;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.PreparedStatement;

/**
 *
 * @author caria
 */
public class Holamundo_avanzada {

     public static void main(String[] args) throws SQLException {
        System.out.println(".-.-.-Ejecutando operaciones en tabla Alumnos-.-.-.");
        //obtener la conexion
        Connection cn = ConexionDB.getConexion();
        if (cn == null) {
            System.out.println("no se puede establecer la conexión");
            return;
        }
        //Declarar los objetos de SQL para nuestra base
        PreparedStatement psInsertar = null;
        PreparedStatement psSeleccionar = null;
        PreparedStatement psActualizar=null;
        PreparedStatement psEliminar=null;
        ResultSet rs = null;
        try {//insertar alumno
            System.out.println("\n 1. Intentando insertar nuevo alumno...");
            String psInsert = "INSERT INTO Alumnos(nombre, correo) VALUES(?,?)";
            psInsertar = cn.prepareStatement(psInsert);
            //asignaremos valores a nuestros parametros
            psInsertar.setString(1, "Ana Iris");
            psInsertar.setString(2, "ana.iris@uth.hn");
            // ejecutar la inserción
            int filasAfectadas = psInsertar.executeUpdate();
            if (filasAfectadas > 0) {
                System.out.println("!Alumno insertado con exito");
            }
            //Parte Bseleccionar y visualizar datos en pantalla
            System.out.println("\n2. Consulta de lista de Alumnos en la base de datos");
            String sqlSelect = "SELECT id, nombre, correo FROM Alumnos";
            psSeleccionar = cn.prepareStatement(sqlSelect);
            rs = psSeleccionar.executeQuery();
            System.out.println("\n-.-.-.-.-.-.-.-.-.-.-.-.-.-.-.-.-.-.-");
            System.out.println("   ID      | Nombre       |Correo      ");
            System.out.println("\n-.-.-.-.-.-.-.-.-.-.-.-.-.-.-.-.-.-.-");
            while (rs.next()) {
                int id = rs.getInt("id");
                String nombre = rs.getString("nombre");
                String correo = rs.getString("correo");
                //Imprimimos la tabla virtual
                System.out.printf("%-3d | %-20s | %s\n", id, nombre, correo);
            }
            System.out.println("\n-.-.-.-.-.-.-.-.-.-.-.-.-.-.-.-.-.-.-");
            if (rs != null) {
                rs.close();
            }
            //parte: Update
            System.out.println("\n3. actualizando el correo del Alumno...");
            String sqlUpdate = "UPDATE Alumnos SET correo = ? WHERE nombre = ?";
            psActualizar = cn.prepareStatement(sqlUpdate);
            psActualizar.setString(1, "marimari.jose@uth.edu.hn");
            psActualizar.setString(2, "Maria Jose");
            // ejecutar la inserción
            int filasActualizadas = psActualizar.executeUpdate();
            if (filasActualizadas > 0) {
                System.out.println("Alumnos actualizados con exito:" + filasActualizadas);
            } else {
                System.out.println("no se encontro el alumno para actualizar.");
            }
            //parte:Delete
            System.out.println("\n4. Eliminando al Alumno...");
            String sqlDelete = "DELETE FROM Alumnos WHERE nombre = ?";
            psEliminar = cn.prepareStatement(sqlDelete);
            psEliminar.setString(1, "Jose Maria");
            int filasEliminadas = psEliminar.executeUpdate();
            if (filasEliminadas > 0) {
                System.out.println("Alumnos eliminados con exito: " + filasEliminadas);
            } else {
                System.out.println("no se encontro el alumno a eliminar.");
            }
            System.out.println("\n5. Consulta final de los Alumnos");
            psSeleccionar = cn.prepareStatement(sqlSelect);
            rs = psSeleccionar.executeQuery();
            System.out.println("\n-.-.-.-.-.-.-.-.-.-.-.-.-.-.-.-.-.-.-");
            System.out.println("   ID    | Nombre       |Correo        ");
            System.out.println("\n-.-.-.-.-.-.-.-.-.-.-.-.-.-.-.-.-.-.-");
            while (rs.next()) {
                int id = rs.getInt("id");
                String nombre = rs.getString("nombre");
                String correo = rs.getString("correo");
                System.out.printf("%-3d | %-20s | %s\n", id, nombre, correo);
            }
            System.out.println("\n-.-.-.-.-.-.-.-.-.-.-.-.-.-.-.-.-.-.-");
        } catch (SQLException e) {
            System.err.println("ocurrio un error con la base de datos: " + e.getMessage());
        } finally {
            //cerrar la conexion de manera obligatoria
            if (rs != null) {
                rs.close();
            }
            if (psInsertar != null) psInsertar.close();
            if (psSeleccionar != null) psSeleccionar.close();
            if (psActualizar !=null) psActualizar.close();
            if (psEliminar !=null) psEliminar.close();
            ConexionDB.cerrarConexion();
        }
    }
}