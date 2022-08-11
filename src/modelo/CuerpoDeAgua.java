package modelo;

import java.util.Scanner;

public class CuerpoDeAgua {
    String nombre;
    int codigo;
    String municipio;
    float irca;

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

    public CuerpoDeAgua(String nombre, int codigo, String municipio, String tipoCuerpoAgua, String tipoAgua, float irca) {
        this.nombre = nombre;
        this.codigo = codigo;
        this.municipio = municipio;
        this.irca = irca;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
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
}