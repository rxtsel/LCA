package vistas;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import modelo.CuerpoDeAgua;

/**
 *
 * @author rxtsel
 */
public class Principal {

    static Interfaz obInterfaz = new Interfaz();
    static ArrayList<CuerpoDeAgua> objLista = new ArrayList<CuerpoDeAgua>();

    /*Metodo para la accion del boton procesar*/
    static void procesar() {
        for (CuerpoDeAgua cuerpoDeAgua : objLista) {
            obInterfaz.SALIDAS_TXT_AREA.append(cuerpoDeAgua.nivel() + "\n");
        }
        int contador = 0;
        String listaNombres = "";
        double minIRCA = 100;
        int posicion = 0;
        //Inicio del for
        for (CuerpoDeAgua cuerpoDeAgua : objLista) {
            Float nivelRiesgo = cuerpoDeAgua.getIrca();
            //Validamos cuerpos de de Agua nivel es Medio o Inferior
            if (nivelRiesgo <= 35) {
                //Actuliza variable contador si cuerpos de Agua nivel es Medio o Inferior
                contador = contador + 1;
                if (cuerpoDeAgua.nivel() == "MEDIO") {
                    //Solo guadamos los nombre de Cuerpos de Agua nivel MEDIO
                    listaNombres = listaNombres + cuerpoDeAgua.getNombre() + " ";
                }
            }
            //Validamos IRCA mas Baja encontrada
            if (cuerpoDeAgua.getIrca() < minIRCA) {
                minIRCA = cuerpoDeAgua.getIrca();
                posicion = cuerpoDeAgua.getCodigo();
            }
        }
        obInterfaz.SALIDAS_TXT_AREA.append("" + contador + "\n");
        if (listaNombres == "") {
            obInterfaz.SALIDAS_TXT_AREA.append("NA" + "\n");
        } else {
            obInterfaz.SALIDAS_TXT_AREA.append(listaNombres + "\n");
        }
        obInterfaz.SALIDAS_TXT_AREA.append(objLista.get(posicion).getNombre() + " " + objLista.get(posicion).getCodigo() + "\n");

    }

    /*obtener todos los campos*/
    static String[] getCampos() {
        String[] values = new String[6];
        values[0] = obInterfaz.ID_TEXT.getText();
        values[1] = obInterfaz.NOMBRE_TEXT.getText();
        values[2] = obInterfaz.MUNICIPIO_TEXT.getText();
        values[3] = obInterfaz.TIPO_CUERPO_AGUA_TEXT.getText();
        values[4] = obInterfaz.TIPO_AGUA_TEXT.getText();
        values[5] = obInterfaz.IRCA_TEXT.getText();

        return values;
    }

    /*validar todos campos*/
    static boolean validarCampos() {

        boolean FLAG = true;

        String[] values = getCampos();

        for (int i = 0; i < values.length; i++) {
            if (values[i].equals("")) {
                return FLAG = false;
            }
        }

        return FLAG;
    }

    /*validar btn procesar*/
    static boolean beforeBtnProcess() {

        boolean FLAG = false;

        if (objLista.size() >= 0) {
            return FLAG = true;
        }

        return FLAG;
    }

    /*limpiar*/
    static void limpiar() {
        obInterfaz.ID_TEXT.setText("");
        obInterfaz.NOMBRE_TEXT.setText("");
        obInterfaz.MUNICIPIO_TEXT.setText("");
        obInterfaz.TIPO_CUERPO_AGUA_TEXT.setText("");
        obInterfaz.TIPO_AGUA_TEXT.setText("");
        obInterfaz.IRCA_TEXT.setText("");
    }

    public static void main(String[] args) {

        /*Mostrar interface*/
        obInterfaz.setVisible(true);
        obInterfaz.BTN_PROCESAR.setEnabled(false);
        obInterfaz.ENTRADAS_TXT_AREA.setEnabled(false);
        obInterfaz.SALIDAS_TXT_AREA.setEnabled(false);

        /*Posicionar interfaz siempre en el centro*/
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) ((dimension.getWidth() - obInterfaz.getWidth()) / 2);
        int y = (int) ((dimension.getHeight() - obInterfaz.getHeight()) / 2);
        obInterfaz.setLocation(x, y);

        /*Btn ingresar*/
        ActionListener clicIngresar = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String[] values = getCampos();

                if (validarCampos()) {
                    if (values[0].matches("[0-9]*") && values[5].matches("[0-9.,0-9]*")) {
                        obInterfaz.ENTRADAS_TXT_AREA.setEnabled(true);

                        /*ingresar todos los datos de los campos al texarea de entradas*/
                        obInterfaz.ENTRADAS_TXT_AREA.append(obInterfaz.NOMBRE_TEXT.getText() + " " + obInterfaz.ID_TEXT.getText() + " " + obInterfaz.MUNICIPIO_TEXT.getText() + " " + obInterfaz.TIPO_CUERPO_AGUA_TEXT.getText() + " " + obInterfaz.TIPO_AGUA_TEXT.getText() + " " + obInterfaz.IRCA_TEXT.getText() + "\n");
                        /*Ingresar todos los datos a la lista*/
                        CuerpoDeAgua objCuerpoDeAgua = new CuerpoDeAgua(obInterfaz.NOMBRE_TEXT.getText(), Integer.parseInt(obInterfaz.ID_TEXT.getText()), obInterfaz.MUNICIPIO_TEXT.getText(), obInterfaz.TIPO_CUERPO_AGUA_TEXT.getText(), obInterfaz.TIPO_AGUA_TEXT.getText(), Float.parseFloat(obInterfaz.IRCA_TEXT.getText()));
                        objLista.add(objCuerpoDeAgua);

                        limpiar();
                        obInterfaz.BTN_PROCESAR.setEnabled(true);
                    } else {
                        JOptionPane.showMessageDialog(null, "ID o IRCA deben ser numéricos.", "Invalid Data Types", JOptionPane.WARNING_MESSAGE);
                    }

                } else {

                    JOptionPane.showMessageDialog(null, "Todos los campos son obligatorios.", "Error", JOptionPane.ERROR_MESSAGE);
                }

            }
        };
        obInterfaz.BTN_INGRESAR.addActionListener(clicIngresar);

        /*Btn procesar*/
        ActionListener clicProcesar = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (beforeBtnProcess()) {
                    obInterfaz.SALIDAS_TXT_AREA.setEnabled(true);
                    procesar();
                    objLista.clear();
                } else {
                    JOptionPane.showMessageDialog(null, "Vuelve a llenar los campos.", "Error", JOptionPane.WARNING_MESSAGE);
                }

            }
        };
        obInterfaz.BTN_PROCESAR.addActionListener(clicProcesar);

        /*btn limpiar*/
        ActionListener clicLimpiar = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                limpiar();
                obInterfaz.ENTRADAS_TXT_AREA.setText("");
                obInterfaz.SALIDAS_TXT_AREA.setText("");
                objLista.clear();
                obInterfaz.BTN_PROCESAR.setEnabled(false);
                obInterfaz.ENTRADAS_TXT_AREA.setEnabled(false);
                obInterfaz.SALIDAS_TXT_AREA.setEnabled(false);
            }
        };
        obInterfaz.BTN_LIMPIAR.addActionListener(clicLimpiar);

    }

}
