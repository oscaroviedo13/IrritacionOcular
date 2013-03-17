/*
 * Conexion_BD.java
 *
 * Created on 6 de febrero de 2007, 12:26
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package AcopleAccess;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ConexionBD {
    
    private SimpleDateFormat fecha_actual;
    private Date hora_sistema;
    
    private String Username;
    private String[][] arrayAlarm = null;

    public ConexionBD(){
        fecha_actual = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        hora_sistema = new Date();
    }
    
    public int updateGenerico(String sql){
        ModBD mod_interfaz = new ModBD();
        int tipo_conexion = 0;
        
        if((tipo_conexion = mod_interfaz.set_conexionBD()) != 0){
            String msg_error = null;
            int filas_actualizadas = 0;

            if(sql == null || sql.equals("")) return 0;
            //////System.out.println(sql);
            
            try{
                msg_error = mod_interfaz.modificar(sql);
                filas_actualizadas = Integer.parseInt(msg_error);
                
                mod_interfaz.end_conexionBD(tipo_conexion);
                mod_interfaz = null;
                return filas_actualizadas;
                
            }catch(NumberFormatException formato_msg){
                hora_sistema = new Date();
                //Eventos.LogEvento.GuardarEventoLog("\n - " + fecha_actual.format(hora_sistema) + "\n-Error al tranformar el dato " + msg_error + "/ " + formato_msg.getMessage()  + "\n");
                mod_interfaz.end_conexionBD(tipo_conexion);
                mod_interfaz = null;
                return 0;
            }
        }
        else{
            mod_interfaz = null;
            return 0;
        }
    }
    
    public int deleteGenerico(String sql){
        ModBD mod_interfaz = new ModBD();
        int tipo_conexion = 0;
        
        if((tipo_conexion = mod_interfaz.set_conexionBD()) != 0){
            String msg_error = null;
            int filas_eliminadas = 0;

            if(sql == null || sql.equals("")) return 0;
            //////System.out.println(sql);
            
            try{
                msg_error = mod_interfaz.eliminar(sql);
                filas_eliminadas = Integer.parseInt(msg_error);
                
                mod_interfaz.end_conexionBD(tipo_conexion);
                mod_interfaz = null;
                return filas_eliminadas;
                
            }catch(NumberFormatException formato_msg){
                hora_sistema = new Date();
                //Eventos.LogEvento.GuardarEventoLog("\n - " + fecha_actual.format(hora_sistema) + "\n-Error al tranformar el dato " + msg_error + "/ " + formato_msg.getMessage()  + "\n");
                mod_interfaz.end_conexionBD(tipo_conexion);
                mod_interfaz = null;
                return 0;
            }
        }
        else{
            mod_interfaz = null;
            return 0;
        }
    }
    
    public int insertGenerico(String sql){
        ModBD mod_interfaz = new ModBD();
        int tipo_conexion = 0;
        
        if((tipo_conexion = mod_interfaz.set_conexionBD()) != 0){
            String msg_error = null;
            int filas_eliminadas = 0;

            if(sql == null || sql.equals("")) return 0;
            //////System.out.println(sql);
            
            try{
                msg_error = mod_interfaz.insertar(sql);
                filas_eliminadas = Integer.parseInt(msg_error);
                
                mod_interfaz.end_conexionBD(tipo_conexion);
                mod_interfaz = null;
                return filas_eliminadas;
                
            }catch(NumberFormatException formato_msg){
                hora_sistema = new Date();
                //Eventos.LogEvento.GuardarEventoLog("\n - " + fecha_actual.format(hora_sistema) + "\n-Error al tranformar el dato " + msg_error + "/ " + formato_msg.getMessage()  + "\n");
                mod_interfaz.end_conexionBD(tipo_conexion);
                mod_interfaz = null;
                return 0;
            }
        }
        else{
            mod_interfaz = null;
            return 0;
        }
    }
    
    public int insertGenerico(String[] sql){
        ModBD mod_interfaz = new ModBD();
        int tipo_conexion = 0;
        
        if((tipo_conexion = mod_interfaz.set_conexionBD()) != 0){
            String msg_error = null;
            int filas_eliminadas = 0;

            if(sql == null) return 0;
            
           
            try{
                msg_error = mod_interfaz.insertarBloque(sql);
                filas_eliminadas = Integer.parseInt(msg_error);
                
                mod_interfaz.end_conexionBD(tipo_conexion);
                mod_interfaz = null;
                return filas_eliminadas;
                
            }catch(NumberFormatException formato_msg){
                hora_sistema = new Date();
                //Eventos.LogEvento.GuardarEventoLog("\n - " + fecha_actual.format(hora_sistema) + "\n-Error al tranformar el dato " + msg_error + "/ " + formato_msg.getMessage()  + "\n");
                mod_interfaz.end_conexionBD(tipo_conexion);
                mod_interfaz = null;
                return 0;
            }
        }
        else{
            mod_interfaz = null;
            return 0;
        }
    }
    
   
    public String[] consultaGenerico(String sql){
        ModBD mod_interfaz = new ModBD();
        int tipo_conexion = 0;
        
        if((tipo_conexion = mod_interfaz.set_conexionBD()) != 0){

            String[] aux = mod_interfaz.consulta(sql).split("/");
            mod_interfaz.end_conexionBD(tipo_conexion);
            mod_interfaz = null;
            return aux;
        }
        else{
            mod_interfaz = null;
            return null;
        }
    }
    
    public String[][] consultaGenerica(String sql){
        ModBD mod_interfaz = new ModBD();
        int tipo_conexion = 0;
        
        if(sql != null){
            if((tipo_conexion = mod_interfaz.set_conexionBD()) != 0){
                ArrayList ArrayList_data = null;

                ArrayList_data = mod_interfaz.consulta_ext(sql);
                if(ArrayList_data != null && !ArrayList_data.isEmpty()){
                    
                    String [][]data = new String[ArrayList_data.size()][];
                    for(int i=0; i<data.length; i++){
                        data[i] = ((String)ArrayList_data.get(i)).split("/");
                    }

                    mod_interfaz.end_conexionBD(tipo_conexion);
                    mod_interfaz = null;
                    return data;
                }
                else{
                    mod_interfaz.end_conexionBD(tipo_conexion);
                    mod_interfaz = null;
                    return null;
                }
            }
            else{
                mod_interfaz = null;
                return null;
            }
        }
        else return null;
    }
    
}