package modelo;

import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;

public class CuerpoDeAgua extends ObjetoGeografico {

    private String nombre;
    private int id;
    String municipio;
    float irca;

    public CuerpoDeAgua(String nombre, int id, String municipio, String tipoCuerpoAgua, String tipoAgua, float irca) {
        this.nombre = nombre;
        this.id = id;
        this.municipio = municipio;
        this.irca = irca;
    }

    public CuerpoDeAgua() {
        super();
    }
    
    public String nivel() {
        if (irca >= 0 && irca <= 5) {
            return "SIN RIESGO";
        } else if (irca > 5 && irca <= 14) {
            return "BAJO";
        } else if (irca > 14 && irca <= 35) {
            return "MEDIO";
        } else if (irca > 35 && irca <= 80) {
            return "ALTO";
        } else if (irca > 80 && irca <= 100) {
            return "INVIABLE SANITARIAMENTE";
        }

        return "NA";
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMunicipio() {
        return municipio;
    }

    public void setMunicipio(String municipio) {
        this.municipio = municipio;
    }

    public float getIrca() {
        return irca;
    }

    public void setIrca(float irca) {
        this.irca = irca;
    }

    /*init*/
    public String consultarCuerposDeAgua() {
        String variableRetorno = "";
        String query = "SELECT * from cuerposDeAgua Order by id asc";
        try {
            conectar();
            registro = comando.executeQuery(query);
            ResultSetMetaData rsmd = registro.getMetaData();
            int columnsNumber = rsmd.getColumnCount();
            while (registro.next()) {
                for (int i = 1; i <= columnsNumber; i++) {
                    String columnValue = registro.getString(i);
                    variableRetorno = variableRetorno + rsmd.getColumnName(i) + ":" + " " + columnValue + "\n";
                }
            }
            desconectar();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return variableRetorno;
    }

    public String[] consultarCuerpoDeAgua(int cod) {
        String[] variableRetorno = null;
        String query = "SELECT * from cuerposDeAgua where id = " + cod + ";";
        try {
            conectar();
            registro = comando.executeQuery(query);
            ResultSetMetaData rsmd = registro.getMetaData();
            int columnsNumber = rsmd.getColumnCount();
            variableRetorno = new String[columnsNumber];
            while (registro.next()) {
                for (int i = 1; i <= columnsNumber; i++) {
                    String columnValue = registro.getString(i);
                    variableRetorno[i - 1] = columnValue;
                }
            }
            desconectar();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return variableRetorno;
    }

    public ArrayList consultarCuerpoDeAguaArray() {
        ArrayList<CuerpoDeAgua> variableRetorno = new ArrayList<>();
        String query = "SELECT * from cuerposDeAgua Order by id asc;";
        try {
            conectar();
            registro = comando.executeQuery(query);
            while (registro.next()) {
                CuerpoDeAgua temp = new CuerpoDeAgua();
                temp.setId(Integer.parseInt(registro.getString("id")));
                temp.setNombre(registro.getString("nombre"));
                temp.setMunicipio(registro.getString("municipio"));
                temp.setTipoAgua(registro.getString("tipoCuerpoAgua"));
                temp.setTipoAgua(registro.getString("tipoAgua"));
                temp.setIrca(Float.parseFloat(registro.getString("irca")));
                variableRetorno.add(temp);
            }
            desconectar();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return variableRetorno;
    }

    public boolean registrarCuerpoDeAgua(int cod, String name, String mun, String tipCue, String tipAg, float irca) {
        int resultado = 0;
        try {
            conectar();
            sentencia = "insert into cuerposDeAgua (id, nombre, municipio, tipoCuerpoAgua,tipoAgua, irca) ";
            sentencia += "values (" + cod + ",'" + name + "','" + mun + "', '" + tipCue + "','" + tipAg + "'," + irca + ");";
            resultado = comando.executeUpdate(sentencia);
            desconectar();
            System.out.println("Cuerpo de Agua creado");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return resultado != 0;
    }

    public boolean modificarCuerpoDeAgua(int oldCod, int cod, String name, String mun, String tipCue, String tipAg, float irca) {
        int resultado = 0;
        try {
            conectar();
            sentencia = "update cuerposDeAgua set  id = " + cod + ",nombre = '" + name + "', municipio = '" + mun + "', tipoCuerpoAgua = '" + tipCue + "', tipoAgua = '" + tipAg + "', irca = " + irca + "  where id = " + oldCod + ";";
            resultado = comando.executeUpdate(sentencia);
            desconectar();
        } catch (SQLException ex) {
            System.out.println(ex.toString());
        }
        return resultado != 0;
    }

    public boolean eliminarCuerpoDeAgua(int cod) {
        int resultado = 0;
        try {
            conectar();
            sentencia = "DELETE FROM cuerposDeAgua WHERE id = " + cod + ";";
            resultado = comando.executeUpdate(sentencia);
            desconectar();
            System.out.println("Cuerpo de Agua Eliminado");
        } catch (SQLException ex) {
            System.out.println(ex.toString());
        }
        return resultado != 0;
    }
    /*end*/

}
