package modelo;

/**
 *
 * @author rxtsel
 */
public abstract class ObjetoGeografico {
    private String tipoCuerpoAgua;
    private String tipoAgua;

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
}
