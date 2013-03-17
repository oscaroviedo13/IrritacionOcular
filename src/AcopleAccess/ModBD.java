/*
 * modBDAccess.java
 *
 * Created on 6 de febrero de 2007, 8:42
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package AcopleAccess;


public class ModBD {
    
    private java.sql.Connection db;
    private java.sql.Statement stat;
    private java.sql.ResultSet res;
    private java.sql.PreparedStatement prepStat;
    
    /*Formato de la fecha*/
    private java.text.SimpleDateFormat fecha_actual;
    private java.text.SimpleDateFormat hora_actual;
    private java.util.Date hora_sistema;
    
    /* Variable que permite verificar la estabilidad de la conexion
     * revisando si existe el controlador correspondiente (driver)
     */
    private boolean driver_encontrado;
    
    /** Creates a new instance of modBDAccess */
    public ModBD(){
        try{
            fecha_actual = new java.text.SimpleDateFormat("dd/MM/yyyy");
            hora_actual = new java.text.SimpleDateFormat("HH:mm:ss");
            Class.forName("sun.jdbc.odbc.JdbcOdbcDriver").newInstance();
            driver_encontrado = true;
            
        }
        catch(java.lang.ClassNotFoundException exception_driver){
            hora_sistema = new java.util.Date();
            System.out.println("\n-" + fecha_actual.format(hora_sistema) + " - " + hora_actual.format(hora_sistema) + "/ Problemas con el Driver!!1: " + exception_driver.getMessage() + ": " + exception_driver.getLocalizedMessage() + "\n");
            driver_encontrado = false;
        }
        catch(java.lang.InstantiationException exception_driver){
            hora_sistema = new java.util.Date();
            System.out.println("\n-" + fecha_actual.format(hora_sistema) + " - " + hora_actual.format(hora_sistema) + "/ Problemas con el Driver!!2: " + exception_driver.getMessage() + ": " + exception_driver.getLocalizedMessage() + "\n");
            driver_encontrado = false;
        }
        catch(java.lang.IllegalAccessException exception_driver){
            hora_sistema = new java.util.Date();
            System.out.println("\n-" + fecha_actual.format(hora_sistema) + " - " + hora_actual.format(hora_sistema) + "/ Problemas con el Driver!!3: " + exception_driver.getMessage() + ": " + exception_driver.getLocalizedMessage() + "\n");
            driver_encontrado = false;
        }
        catch(Exception exception_driver){
            hora_sistema = new java.util.Date();
            System.out.println("\n-" + fecha_actual.format(hora_sistema) + " - " + hora_actual.format(hora_sistema) + "/ Problemas con el Driver!!4: " + exception_driver.getMessage() + ": " + exception_driver.getLocalizedMessage() + "\n");
            driver_encontrado = false;
        }
    }
    
    public int set_conexionBD(){
       /* la conexion a la base de datos sero clasificada por medio de 
          la variable driver_conexion, de acuerdo a tres posibles estados, que son:
        * 0 = la conexion no se realizo
        * 1 = la conexion se realizo exitosamente
        * 2 = la conexion fue establecida anteriormente por otro llamado
        */
       
        
            if(driver_encontrado){
                
//                    String server = "jdbc:odbc:";
//                    String user = "osarmiento";
//                    String pwd = "oscaroviedo135";
//                    String dbName = "SADET";
//                    int driver_conexion = 0;
                    String server = "jdbc:odbc:";
                    String user = "jbecerra";
                    String pwd = "village1";
                        String dbName = "BIOBANCOMIGRA";
                    int driver_conexion = 0;

                    try{                  

                        db = java.sql.DriverManager.getConnection(server+dbName,user,pwd);//, usuario, contrasena
                        db.setAutoCommit(false);
                        driver_conexion = 1;
                    }
                    catch(java.lang.NullPointerException nullExcep){
                        hora_sistema = new java.util.Date();
                        System.out.println("Error:\n-" + nullExcep.getMessage() + "\n");
                        driver_conexion = 0;
                    }
                    catch(java.sql.SQLException exception_setConexionBD){
                        System.out.println("Error: Base de datos desconocida: " + exception_setConexionBD.getMessage() + "\n");
                        driver_conexion = 0;  
                    }
                    return driver_conexion;
                
            }
            else return 0;
        
        
    }
    
    public String consulta(String consulta){
       String data = "";

        try{
            stat = db.createStatement();
            //stat.executeUpdate("START TRANSACTION;");
            res = stat.executeQuery(consulta);

            while (res.next()){
                java.sql.ResultSetMetaData aux = res.getMetaData();
                //el ondice comienza en 1, no en 0
                for(int i=1; i <= aux.getColumnCount(); i++)
                    data += res.getString(i) + "/";
            }
            res.close();
            stat.close();

        }
        catch(java.lang.NullPointerException nullExcep){
            try{
                hora_sistema = new java.util.Date();
                System.out.println("\n-" + fecha_actual.format(hora_sistema) + " - " + hora_actual.format(hora_sistema) + "- Error:\n-" + nullExcep.getMessage() + "\n");
                return null;
            }catch(java.lang.OutOfMemoryError memoryError){
                hora_sistema = new java.util.Date();
                System.out.println("\n-" + fecha_actual.format(hora_sistema) + " - " + hora_actual.format(hora_sistema) + "-Error:\n-" +
                        "Se sobrepaso la memoria heap de la VM \n- " + memoryError.getLocalizedMessage());
                return null;
            }
        }
        catch(java.sql.SQLException exception_consulta){
            try{
                hora_sistema = new java.util.Date();
                System.out.println("\n-" + fecha_actual.format(hora_sistema) + " - " + hora_actual.format(hora_sistema) + "- (Consulta normal) Error en la consulta:\n-" + exception_consulta.getMessage()
                            + "\n-Generado por la sentencia: " + consulta + "\n" +
                            "-ErrorCode: " + exception_consulta.getErrorCode() + " / SQLState: " + exception_consulta.getSQLState() + "\n");

                //.getSQLState() "2006".equals(sqlState)
                //int sqlState = exception_consulta.getErrorCode();

                /*The two SQL states that are 'retry-able' are 08S01
                for a communications error, and 40001 for deadlock.*/
                //if(sqlState == 2006 || sqlState == 2003)
                    //readBat();

                return null;
            }
            catch(java.lang.OutOfMemoryError memoryError){
                hora_sistema = new java.util.Date();
                System.out.println("\n-" + fecha_actual.format(hora_sistema) + " - " + hora_actual.format(hora_sistema) + "-Error exception_consulta:\n-" +
                        "Se sobrepaso la memoria heap de la VM \n- " + memoryError.getLocalizedMessage());
                return null;
            }
        }
        return data;
    }
    
    public java.util.ArrayList consulta_ext(String consulta){
        java.util.ArrayList ArrayList_data = new java.util.ArrayList();
        String data = "", cad_prueba = "";

        try{
            stat = db.createStatement();
            //stat.executeUpdate("START TRANSACTION;");
            res = stat.executeQuery(consulta);

            while (res.next()){
                java.sql.ResultSetMetaData aux = res.getMetaData();
                /*el ondice comienza en 1, no en 0*/
                for(int i=1; i <= aux.getColumnCount(); i++){
                    cad_prueba = res.getString(i);
                    data += cad_prueba + "/";
                }
                ArrayList_data.add(data);
                data = "";
            }
            res.close();
            stat.close();

        }
        catch(java.lang.NullPointerException nullExcep){
            try{
                hora_sistema = new java.util.Date();
                System.out.println("\n-" + fecha_actual.format(hora_sistema) + " - " + hora_actual.format(hora_sistema) + "- Error:\n-" + nullExcep.getMessage() + "\n");
                return null;
            }catch(java.lang.OutOfMemoryError memoryError){
                hora_sistema = new java.util.Date();
                System.out.println("\n-" + fecha_actual.format(hora_sistema) + " - " + hora_actual.format(hora_sistema) + "-Error:\n-" +
                        "Se sobrepaso la memoria heap de la VM \n- " + memoryError.getLocalizedMessage());
                return null;
            }
        }
        catch(java.sql.SQLException exception_consulta){
            try{
                hora_sistema = new java.util.Date();
                System.out.println("\n-" + fecha_actual.format(hora_sistema) + " - " + hora_actual.format(hora_sistema) + "/ (Consulta especial) Error en la consulta:\n-" + exception_consulta.getMessage()
                            + "\n-Generado por la sentencia: " + consulta + "\n" +
                            "-ErrorCode: " + exception_consulta.getErrorCode() + " / SQLState: " + exception_consulta.getSQLState() + "\n");

                return null;
            }
            catch(java.lang.OutOfMemoryError memoryError){
                hora_sistema = new java.util.Date();
                System.out.println("\n-" + fecha_actual.format(hora_sistema) + " - " + hora_actual.format(hora_sistema) + "-Error exception_consulta_ext:\n-" +
                        "Se sobrepaso la memoria heap de la VM \n- " + memoryError.getLocalizedMessage());
                return null;
            }
        }
        return ArrayList_data;
    }
    
    public String insertar(String insertar){
        String msg_retorno = "0";
        try{
            
                
            prepStat = (java.sql.PreparedStatement) db.prepareStatement(insertar);
            msg_retorno = (String.valueOf(prepStat.executeUpdate()));
            guardarCambios(Integer.parseInt(msg_retorno));
            prepStat.clearBatch();
            prepStat.clearParameters();
            prepStat.close();
            prepStat = null;
        }
        catch(java.lang.NullPointerException nullExcep){
            try{
                hora_sistema = new java.util.Date();
                System.out.println("\n-" + fecha_actual.format(hora_sistema) + " - " + hora_actual.format(hora_sistema) + "- Error:\n-" + nullExcep.getMessage() + "\n");
                return "0";
            }catch(java.lang.OutOfMemoryError memoryError){
                hora_sistema = new java.util.Date();
                System.out.println("\n-" + fecha_actual.format(hora_sistema) + " - " + hora_actual.format(hora_sistema) + "-Error:\n-" +
                        "Se sobrepaso la memoria heap de la VM \n- " + memoryError.getLocalizedMessage());
                return "0";
            }
        }
        catch(java.sql.SQLException excepcion_insertar){
            try{
                hora_sistema = new java.util.Date();
                System.out.println("\n-" + fecha_actual.format(hora_sistema) + " - " + hora_actual.format(hora_sistema) + "- Error al ingresar 3:\n-" + excepcion_insertar.getMessage()
                                                            + "\n-Generado por la sentencia: " + insertar + "\n" +
                                                            "-ErrorCode: " + excepcion_insertar.getErrorCode() + " / SQLState: " + excepcion_insertar.getSQLState() + "\n");
                hora_sistema = null;
                prepStat = null;
                
                return "0";
            }
            catch(java.lang.OutOfMemoryError memoryError){
                hora_sistema = new java.util.Date();
                System.out.println("\n-" + fecha_actual.format(hora_sistema) + " - " + hora_actual.format(hora_sistema) + "-Error:\n-" +
                        "Se sobrepaso la memoria heap de la VM \n- " + memoryError.getLocalizedMessage());
                return "0";
            }
        }
        return msg_retorno;
    }
     
    public String insertarBloque(String[] insertar){
        String msg_retorno = "0";
        try{

            for(int i=0; i<insertar.length; i++){   
                //Complementos.SaveLogClass.save_regEvntLog(insertar[i].trim().toString());
                prepStat = (java.sql.PreparedStatement) db.prepareStatement(insertar[i].trim().toString());
                msg_retorno = (String.valueOf(prepStat.executeUpdate()));

                if(Integer.parseInt(msg_retorno) == 0){
                    guardarCambios(Integer.parseInt(msg_retorno));           
                    break;
                }
            }

            guardarCambios(Integer.parseInt(msg_retorno));
            prepStat.close();

        }
        catch(java.lang.NullPointerException nullExcep){
            guardarCambios(0); 
            try{
                hora_sistema = new java.util.Date();
                System.out.println("\n-" + fecha_actual.format(hora_sistema) + " - " + hora_actual.format(hora_sistema) + "- Error:\n-" + nullExcep.getMessage() + "\n");
                return "0";
            }catch(java.lang.OutOfMemoryError memoryError){
                hora_sistema = new java.util.Date();
                System.out.println("\n-" + fecha_actual.format(hora_sistema) + " - " + hora_actual.format(hora_sistema) + "-Error:\n-" +
                        "Se sobrepaso la memoria heap de la VM \n- " + memoryError.getLocalizedMessage());
                return "0";
            }
        }   
        catch(java.sql.SQLException excepcion_insertar){
            try{
                guardarCambios(0); 
                hora_sistema = new java.util.Date();
                System.out.println("\n-" + fecha_actual.format(hora_sistema) + " - " + hora_actual.format(hora_sistema) + "- Error al ingresar 4:\n-" + excepcion_insertar.getMessage()
                            + "\n-Generado por la sentencia: " + insertar[0] + "\n" +
                            "-ErrorCode: " + excepcion_insertar.getErrorCode() + " / SQLState: " + excepcion_insertar.getSQLState() + "\n");
         
                return "0";
            }
            catch(java.lang.OutOfMemoryError memoryError){
                hora_sistema = new java.util.Date();
                System.out.println("\n-" + fecha_actual.format(hora_sistema) + " - " + hora_actual.format(hora_sistema) + "-Error:\n-" +
                        "Se sobrepaso la memoria heap de la VM \n- " + memoryError.getLocalizedMessage());
                return "0";
            }
        }
        return msg_retorno;
    }
    
    public String modificar(String modificar){
        String msg_retorno = null;
        try{
            prepStat = (java.sql.PreparedStatement) db.prepareStatement(modificar);
            msg_retorno = (String.valueOf(prepStat.executeUpdate()));
            guardarCambios(Integer.parseInt(msg_retorno));
            prepStat.close();

        }catch(java.lang.NullPointerException nullExcep){
            try{
                hora_sistema = new java.util.Date();
                System.out.println("\n-" + fecha_actual.format(hora_sistema) + " - " + hora_actual.format(hora_sistema) + "- Error:\n-" + nullExcep.getMessage() + "\n");
                return "0";
            }catch(java.lang.OutOfMemoryError memoryError){
                hora_sistema = new java.util.Date();
                System.out.println("\n-" + fecha_actual.format(hora_sistema) + " - " + hora_actual.format(hora_sistema) + "-Error:\n-" +
                        "Se sobrepaso la memoria heap de la VM \n- " + memoryError.getLocalizedMessage());
                return "0";
            }
        }catch(java.sql.SQLException excepcion_modificar){
            try{
                hora_sistema = new java.util.Date();
                System.out.println("\n-" + fecha_actual.format(hora_sistema) + " - " + hora_actual.format(hora_sistema) + "- Error al modificar:\n-" + excepcion_modificar.getMessage()
                            + "\n-Generado por la sentencia: " + modificar + "\n" +
                            "-ErrorCode: " + excepcion_modificar.getErrorCode() + " / SQLState: " + excepcion_modificar.getSQLState() + "\n");

                return "0";
            }
            catch(java.lang.OutOfMemoryError memoryError){
                hora_sistema = new java.util.Date();                
                System.out.println("\n-" + fecha_actual.format(hora_sistema) + " - " + hora_actual.format(hora_sistema) + "-Error:\n-" +
                        "Se sobrepaso la memoria heap de la VM \n- " + memoryError.getLocalizedMessage());
                return "0";
            }
        }
        return msg_retorno;
}
    
    public String eliminar(String eliminar){
        String msg_retorno = "0";
         try{
            prepStat = (java.sql.PreparedStatement) db.prepareStatement(eliminar);
            msg_retorno = (String.valueOf(prepStat.executeUpdate()));
            guardarCambios(Integer.parseInt(msg_retorno));
            prepStat.close();
        }catch(java.lang.NullPointerException nullExcep){
            try{
                hora_sistema = new java.util.Date();
                System.out.println("\n-" + fecha_actual.format(hora_sistema) + " - " + hora_actual.format(hora_sistema) + "- Error:\n-" + nullExcep.getMessage() + "\n");
                return "0";
            }catch(java.lang.OutOfMemoryError memoryError){
                hora_sistema = new java.util.Date();
                System.out.println("\n-" + fecha_actual.format(hora_sistema) + " - " + hora_actual.format(hora_sistema) + "-Error:\n-" +
                        "Se sobrepaso la memoria heap de la VM \n- " + memoryError.getLocalizedMessage());
                return "0";
            }
        }catch(java.sql.SQLException excepcion_eliminar){
            try{
                hora_sistema = new java.util.Date();
                System.out.println("\n-" + fecha_actual.format(hora_sistema) + " - " + hora_actual.format(hora_sistema) + "- Error al eliminar:\n-" + excepcion_eliminar.getMessage()
                            + "\n-Generado por la sentencia: " + eliminar + "\n" +
                            "-ErrorCode: " + excepcion_eliminar.getErrorCode() + " / SQLState: " + excepcion_eliminar.getSQLState() + "\n");

                return "0";
            }
            catch(java.lang.OutOfMemoryError memoryError){
                hora_sistema = new java.util.Date();
                System.out.println("\n-" + fecha_actual.format(hora_sistema) + " - " + hora_actual.format(hora_sistema) + "-Error:\n-" +
                        "Se sobrepaso la memoria heap de la VM \n- " + memoryError.getLocalizedMessage());
                return "0";
            }
        }
        return msg_retorno;
    }
    
    public void guardarCambios(int msg_retorno){
       try{
            if(msg_retorno > 0)
                    db.commit();
            else{
                try{
                    db.rollback();
                }
                catch(java.sql.SQLException excepcion_rollback){
                    try{
                        int sqlState = excepcion_rollback.getErrorCode();
                        hora_sistema = new java.util.Date();
                        System.out.println("- " + fecha_actual.format(hora_sistema) + " - " + hora_actual.format(hora_sistema) + "- Error al deshacer cambios:\n-" + excepcion_rollback.getMessage() + "\n");
                    }
                    catch(java.lang.OutOfMemoryError memoryError){
                        hora_sistema = new java.util.Date();
                        System.out.println("\n-" + fecha_actual.format(hora_sistema) + " - " + hora_actual.format(hora_sistema) + "-Error excepcion_rollback:\n-" +
                                "Se sobrepaso la memoria heap de la VM \n- " + memoryError.getLocalizedMessage());
                    }
                }
              } 
        }catch(java.lang.NullPointerException nullExcep){
            hora_sistema = new java.util.Date();
            System.out.println("\n-" + fecha_actual.format(hora_sistema) + " - " + hora_actual.format(hora_sistema) + "- Error:\n-" + nullExcep.getMessage() + "\n");
        }catch(java.sql.SQLException excepcion_commit){
            hora_sistema = new java.util.Date();
            System.out.println("\n-" + fecha_actual.format(hora_sistema) + " - " + hora_actual.format(hora_sistema) + "- Error al guardar: " + excepcion_commit.getMessage() + "\n");

        }
    }
    
    public boolean end_conexionBD(int tipo_conexion){
        /* la conexion a la base de datos sero clasificada por medio de 
          la variable tipo_conexion, de acuerdo a tres posibles estados, que son:
        * 0 = la conexion no se realizo
        * 1 = la conexion se realizo exitosamente
        * 2 = la conexion fue establecida anteriormente por otro llamado
        */
        if(tipo_conexion == 1){
            try{
                
                db = null;
                if(db != null)
                    if(!db.isClosed())
                        db.close();
                
                
                return true;
                
            }catch(java.lang.NullPointerException nullExcep){
                hora_sistema = new java.util.Date();
                System.out.println("\n-" + fecha_actual.format(hora_sistema) + " - " + hora_actual.format(hora_sistema) + "- Error:\n-" + nullExcep.getMessage() + "\n");
                db = null;
                return false;
            }
            
            catch(java.sql.SQLException exception_four){
               
                hora_sistema = new java.util.Date();
                System.out.println("\n-" + fecha_actual.format(hora_sistema) + " - " + hora_actual.format(hora_sistema) + "- Error al finalizar conexion con BD\n " + db + "\n" + exception_four.getMessage() + "\n");
                
               
                
                db = null;
                return false;
            }
        }
        else return false;
    }
    
    public boolean readDataAcp(){
        try{
            String io = (new java.io.File("./configDataAcople.acp")).getAbsolutePath();
            //System.out.println("2 obtenido? " + io);
            boolean opt = false;
            if(io !=null){
                java.io.BufferedReader lector = new java.io.BufferedReader(new java.io.FileReader(io));
                
                
                String[] Data=lector.readLine().toString().split("<");
                
                String[] subData2 = Data[2].split(";");
                opt = Boolean.valueOf(subData2[0]);
                subData2 = null;
                lector.close();
                lector = null;
                
                
            }
            return opt;
        }
        catch(java.io.FileNotFoundException e){
            System.out.println("FileNotFoundException (readDataAcp[modBDAcces]): " + e.getMessage() );
            return false;
        }
        catch(java.io.IOException e){
            System.out.println("IOException (readDataAcp[modBDAcces]): " + e.getMessage() );
            return false;
        }
    }
    
}