package modelo;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author rxtsel
 */
public abstract class ObjetoGeografico {
    private String tipoCuerpoAgua;
    private String tipoAgua;
    Connection conexion;
    Statement comando;
    ResultSet registro;
    String sentencia;

    public String getTipoCuerpoAgua() {
        return tipoCuerpoAgua;
    }

    public void setTipoCuerpoAgua(String tipoCuerpoAgua) {
        this.tipoCuerpoAgua = tipoCuerpoAgua;
    }

    public String getTipoAgua() {
        return tipoAgua;
    }

    public void setTipoAgua(String tipoAgua) {
        this.tipoAgua = tipoAgua;
    }
    
    
    
    protected void conectar() {
        String sjdbc = "jdbc:sqlite";
        Path path = Paths.get("src/assets/DB.db");
        String url = sjdbc + ":\\" + path.toAbsolutePath();
        try {
            // Creamos la conexión
            conexion = DriverManager.getConnection(url);
            comando = conexion.createStatement();
            System.out.println("Connection to SQLite has been stablished.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    protected void desconectar() {
        try {
            conexion.close();
            comando.close();
            if (registro != null) {
                registro.close();
            }
        } catch (SQLException ex) {
            System.out.println(ex.toString());
        }
    }
}
