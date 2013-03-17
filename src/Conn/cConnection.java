package Conn;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import sun.rmi.runtime.Log;

public class cConnection { 
 
    /**
     * Parametros de conexion.
     */
      /*Atributos*/
    private String url = "jdbc:odbc:"; 
 
    private String driver = "SADET"; 
 
    private String usr = "dbuser"; 
 
    private String pswd = "123"; 
    
    private Connection con; 
 
    /*Constructor, carga puente JDBC-ODBC*/ 
 
    public cConnection() 
    { 
       cargarDriver(); 
    } 
 
    /** 
    * Carga el driver de la conexión a la base de datos 
    */ 
    private void cargarDriver() 
    { 
        try
        { 
            //Instancía de una nueva clase para el puente 
            //sun.jdbc.odbc.JdbcOdbcDriver 
            //El puente sirve entre la aplicación y el driver.     
            Class.forName( "sun.jdbc.odbc.JdbcOdbcDriver" ); 
        } 
        catch(ClassNotFoundException e){
                
            System.out.println("Error al crear el puente JDBC-ODBC");  
        } 
    } 
 
    /** 
    *Obtiene una conexión con el nombre del driver especificado 
    *@param driverName Nombre del driver de la base de datos 
    *@return 
    */
    public Connection crearConexion() 
    { 
        url = url + driver; 
        System.out.println("Estableciendo conexión con " + url); 
        try
        { 
            //Obtiene la conexión 
            con = DriverManager.getConnection( url,usr,pswd); 
        } 
        catch(SQLException sqle) 
        { 
            System.out.println("No se pudo establecer la conexión"); 
            return null; 
        } 
 
        System.out.println("Conexión establecida con:t " + url); 
 
        return con; 
    } 
 
    /* Cerrar la conexión.*/
 
    public boolean closeConecction() 
    { 
        try 
        { 
            con.close(); 
        } 
        catch(SQLException sqle) 
        { 
            System.out.println("No se cerro la conexión"); 
            return false; 
        } 
 
        System.out.println("Conexión cerrada con éxito "); 
        return true; 
    } 
}