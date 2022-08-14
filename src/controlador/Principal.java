package controlador;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import modelo.CuerpoDeAgua;
import vista.Interfaz;

/**
 *
 * @author rxtsel
 */
public class Principal {

    static Interfaz obInterfaz = new Interfaz();
    static ArrayList<CuerpoDeAgua> objLista = new ArrayList<>();
    static CuerpoDeAgua CDA = new CuerpoDeAgua();


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
                posicion = cuerpoDeAgua.getId();
            }
        }
        obInterfaz.SALIDAS_TXT_AREA.append("" + contador + "\n");
        if (listaNombres == "") {
            obInterfaz.SALIDAS_TXT_AREA.append("NA" + "\n");
        } else {
            obInterfaz.SALIDAS_TXT_AREA.append(listaNombres + "\n");
        }
        obInterfaz.SALIDAS_TXT_AREA.append(objLista.get(posicion).getNombre() + " " + objLista.get(posicion).getId() + "\n");

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
        obInterfaz.SEARCH_TEXT.setText("");
        obInterfaz.BTN_INGRESAR.setEnabled(true);
        obInterfaz.BTN_EDITAR.setEnabled(false);
        obInterfaz.BTN_ELIMINAR.setEnabled(false);
        obInterfaz.BTN_CONSULTAR.setEnabled(true);
    }

    /*validar que el id no exista en la db*/
    static boolean isValidId(int num) {
        String[] results = CDA.consultarCuerpoDeAgua(num);
        return results[0] == null;
    }

    public static void main(String[] args) {

        /*Mostrar interface*/
        obInterfaz.setVisible(true);
        obInterfaz.BTN_PROCESAR.setEnabled(false);
        obInterfaz.ENTRADAS_TXT_AREA.setEnabled(false);
        obInterfaz.SALIDAS_TXT_AREA.setEnabled(false);
        obInterfaz.BTN_EDITAR.setEnabled(false);
        obInterfaz.BTN_ELIMINAR.setEnabled(false);

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
                        obInterfaz.SALIDAS_TXT_AREA.setText("");
                        /*ingresar todos los datos de los campos al texarea de entradas*/
                        obInterfaz.ENTRADAS_TXT_AREA.append(obInterfaz.NOMBRE_TEXT.getText() + " " + obInterfaz.ID_TEXT.getText() + " " + obInterfaz.MUNICIPIO_TEXT.getText() + " " + obInterfaz.TIPO_CUERPO_AGUA_TEXT.getText() + " " + obInterfaz.TIPO_AGUA_TEXT.getText() + " " + obInterfaz.IRCA_TEXT.getText() + "\n");
                        obInterfaz.BTN_PROCESAR.setEnabled(true);

                        if (isValidId(Integer.parseInt(obInterfaz.ID_TEXT.getText()))) {

                            /*Añadir campos a db*/
                            int isValid = JOptionPane.showConfirmDialog(null, "¿Desea guardar estos campos en la base de datos?", "INFORMATION", JOptionPane.YES_NO_OPTION);
                            if (isValid == JOptionPane.YES_OPTION) {
                                CDA.registrarCuerpoDeAgua(Integer.parseInt(values[0]), values[1], values[2], values[3], values[4], Float.parseFloat(values[5]));
                                JOptionPane.showMessageDialog(null, "Datos agregados correctamente.", "Succesfull", JOptionPane.INFORMATION_MESSAGE);
                            }

                            /*Ingresar todos los datos a la lista*/
                            CuerpoDeAgua objCuerpoDeAgua = new CuerpoDeAgua(obInterfaz.NOMBRE_TEXT.getText(), Integer.parseInt(obInterfaz.ID_TEXT.getText()), obInterfaz.MUNICIPIO_TEXT.getText(), obInterfaz.TIPO_CUERPO_AGUA_TEXT.getText(), obInterfaz.TIPO_AGUA_TEXT.getText(), Float.parseFloat(obInterfaz.IRCA_TEXT.getText()));
                            objLista.add(objCuerpoDeAgua);

                            limpiar();
                            obInterfaz.BTN_PROCESAR.setEnabled(true);
                        } else {
                            JOptionPane.showMessageDialog(null, "ID existe en la base de datos.", "WARNING", JOptionPane.WARNING_MESSAGE);
                        }

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

        /*btn buscar*/
        ActionListener clicBuscar = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!obInterfaz.SEARCH_TEXT.getText().equals("")) {
                    if (obInterfaz.SEARCH_TEXT.getText().matches("[0-9]*")) {
                        if (isValidId(Integer.parseInt(obInterfaz.SEARCH_TEXT.getText()))) {
                            JOptionPane.showMessageDialog(null, "No se encuentran registros para " + obInterfaz.SEARCH_TEXT.getText(), "Invalid data", JOptionPane.WARNING_MESSAGE);
                            obInterfaz.SEARCH_TEXT.setText("");
                        } else {
                            /*Array que contiene datos de bd segun id (btn  search)*/
                            String[] result = CDA.consultarCuerpoDeAgua(Integer.parseInt(obInterfaz.SEARCH_TEXT.getText()));
                            obInterfaz.ID_TEXT.setText(result[0]);
                            obInterfaz.NOMBRE_TEXT.setText(result[1]);
                            obInterfaz.MUNICIPIO_TEXT.setText(result[2]);
                            obInterfaz.TIPO_CUERPO_AGUA_TEXT.setText(result[3]);
                            obInterfaz.TIPO_AGUA_TEXT.setText(result[4]);
                            obInterfaz.IRCA_TEXT.setText(result[5]);
                            obInterfaz.BTN_EDITAR.setEnabled(true);
                            obInterfaz.BTN_ELIMINAR.setEnabled(true);
                            CuerpoDeAgua objCuerpoDeAgua = new CuerpoDeAgua(obInterfaz.NOMBRE_TEXT.getText(), Integer.parseInt(obInterfaz.ID_TEXT.getText()), obInterfaz.MUNICIPIO_TEXT.getText(), obInterfaz.TIPO_CUERPO_AGUA_TEXT.getText(), obInterfaz.TIPO_AGUA_TEXT.getText(), Float.parseFloat(obInterfaz.IRCA_TEXT.getText()));
                            objLista.add(objCuerpoDeAgua);
                        }

                    } else {
                        JOptionPane.showMessageDialog(null, "No se permiten letras", "Invalid data", JOptionPane.ERROR_MESSAGE);
                    }

                } else {
                    JOptionPane.showMessageDialog(null, "Debe ingresar un ID.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        };
        obInterfaz.BTN_SEARCH.addActionListener(clicBuscar);

        /*btn editar*/
        ActionListener clicEditar = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                
                int isValid = JOptionPane.showConfirmDialog(null, "¿Está seguro que desea guardar cambios en este registro?", "WARNING", JOptionPane.YES_NO_OPTION);
                if (isValid == JOptionPane.YES_OPTION) {
                    CDA.modificarCuerpoDeAgua(Integer.parseInt(obInterfaz.ID_TEXT.getText()), Integer.parseInt(obInterfaz.ID_TEXT.getText()), obInterfaz.NOMBRE_TEXT.getText(), obInterfaz.MUNICIPIO_TEXT.getText(), obInterfaz.TIPO_CUERPO_AGUA_TEXT.getText(), obInterfaz.TIPO_AGUA_TEXT.getText(), Float.parseFloat(obInterfaz.IRCA_TEXT.getText()));
                    obInterfaz.SEARCH_TEXT.setText("");
                    limpiar();
                    JOptionPane.showMessageDialog(null, "Cambios guardados correctamente.", "Succesfull", JOptionPane.INFORMATION_MESSAGE);
                    obInterfaz.BTN_INGRESAR.setEnabled(true);
                }
            }
        };
        obInterfaz.BTN_EDITAR.addActionListener(clicEditar);

        /*btn consultar*/
        ActionListener clicConsultar = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (CDA.consultarCuerpoDeAguaArray().size() > 0) {
                    obInterfaz.SALIDAS_TXT_AREA.setEnabled(true);
                    obInterfaz.SALIDAS_TXT_AREA.append(CDA.consultarCuerposDeAgua());
                    obInterfaz.BTN_CONSULTAR.setEnabled(false);
                } else {
                    JOptionPane.showMessageDialog(null, "No hay registros en la base de datos.", "No data", JOptionPane.INFORMATION_MESSAGE);
                }

            }
        };
        obInterfaz.BTN_CONSULTAR.addActionListener(clicConsultar);

        /*btn eliminar*/
        ActionListener clicEliminar = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int isValid = JOptionPane.showConfirmDialog(null, "¿Está seguro que desea eliminar este registro de la base de datos?", "WARNING", JOptionPane.YES_NO_OPTION);
                if (isValid == JOptionPane.YES_OPTION) {
                    CDA.eliminarCuerpoDeAgua(Integer.parseInt(obInterfaz.ID_TEXT.getText()));
                    JOptionPane.showMessageDialog(null, "Datos eliminados correctamente.", "Succesfull", JOptionPane.WARNING_MESSAGE);
                    limpiar();
                    obInterfaz.ENTRADAS_TXT_AREA.setText("");
                    obInterfaz.SALIDAS_TXT_AREA.setText("");
                }
            }
        };
        obInterfaz.BTN_ELIMINAR.addActionListener(clicEliminar);

    }

}
