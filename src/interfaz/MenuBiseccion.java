package interfaz;

import javax.swing.*;

import javax.swing.plaf.ColorUIResource;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

import mundo.*;

import java.awt.*;
import java.awt.event.*;

public class MenuBiseccion extends JFrame {
    /*--------------
    * atributos
    *----------------------*/

    // @TODO se utiliza para almaceenar el modelo de la tabla
    private static DefaultTableModel modelo = new DefaultTableModel();

    // @TODO controla lo que se ba observar en pantalla, es de tipo byte porque no
    // se almacenaran valores muy grandes
    byte controlador = 0;

    private JPanel jPanel1, jPanel2, jPanel3, jPanel4, jPanel5, jPanel6, jPanel7, jPanel8;

    private JTextField pantalla, rangoA, rangoB, error, pantallaFuncion, pantallaSalidaIntervalo;

    private JLabel rangoALabel, rangoBLabel, erroJLabel, nombrePanelIngresoDatos, metodo, funcioningresada,
            intevalo;

    private static JLabel resultado;

    private JTextArea descripcion;

    private JButton evaluar;

    private JScrollPane jScrollTabla;

    private static JTable tabaResultados;

    private final static JButton matriez_BOTONES[][] = new JButton[7][7];

    private final static String NUMEROSOPERADORES[][] = {
            { "1", "2", "3", "CE", ".", "sin", "⬇" },
            { "4", "5", "6", "+", "^", "*", "cos" },
            { "7", "8", "9", "-", "√", "log", "tan" },
            { "x", "0", "÷", "(", ")", "ln", "e" }
    };

    private String cadena;

    /*-------------------------
     * Relaciones 
     *-----------------*/
    private MetodoBiseccion funcionIngresada;

    private MetodoBiseccion metodoBiseccion;

    /*-----------------------
     * Metodos
     *----------------------*/
    /**
     * metodo contructor de la clase Menu Biseccion
     */

   
    public MenuBiseccion() {
        /*---------
         *configuraciones basicas de el menu 
         *-----------*/
        setTitle("Método de solución de funciones");
        this.setSize(1000, 550);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        /*----------
         * intanciacion y configuracines  del panel 1 el cual contiene todo lo que vamos a llevar a cabo
         *------------*/
        jPanel1 = new JPanel();
        jPanel1.setBackground(new ColorUIResource(255, 255, 255));
        jPanel1.setLayout(null);
        jPanel1.setForeground(new ColorUIResource(0, 0, 0));
        this.getContentPane().add(jPanel1);

        /*-------------------------------------
         * intanciacion del panel dos, contendra todo lo necesario para
         * ingresar los datos
         *-------------------*/
        jPanel2 = new JPanel();
        jPanel2.setForeground(new ColorUIResource(255, 255, 255));
        jPanel2.setSize(500, 550);
        jPanel2.setBorder(new RoundedBorder(10));
        jPanel2.setLayout(null);
        jPanel1.add(jPanel2);
        panelIngredardatos();
        panelElegirRango();
        panelPantalla();

        /*-------------------------
         * panel 3 se observara las tablas de las bisecciones realizadas
        *---------------------------*/
        jPanel3 = new JPanel();
        jPanel3.setForeground(new ColorUIResource(238, 238, 238));
        jPanel3.setLayout(null);
        jPanel3.setBounds(502, 2, 496, 530);
        jPanel1.add(jPanel3);
        panelTabla();
        tablaResultados();

        ordenar();

        this.setVisible(true);
    }

    /**
     * contruye el panel donde se encuentrarn los botones
     * 
     */

    private void panelIngredardatos()

    {
        Font formaatoTexto = new Font("Arial", Font.ITALIC, 15);

        jPanel4 = new JPanel();
        jPanel4.setBackground(new ColorUIResource(238, 238, 238));
        jPanel4.setForeground(Color.WHITE);
        jPanel4.setLayout(null);
        jPanel4.setBounds(10, 250, 495, 240);
        jPanel4.setBorder(new RoundedBorder(20));
        jPanel2.add(jPanel4);

        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 4; j++) {
                matriez_BOTONES[j][i] = new JButton(NUMEROSOPERADORES[j][i]);
                matriez_BOTONES[j][i].setFont(formaatoTexto);
                matriez_BOTONES[j][i].setBounds(20 + (i * 65), 20 + (j * 55), 60, 50);
                matriez_BOTONES[j][i].setBackground(Color.blue);
                matriez_BOTONES[j][i].setForeground(Color.white);
                matriez_BOTONES[j][i].setBorder(new RoundedBorder(2));
                matriez_BOTONES[j][i].addActionListener(new ActionListener() {

                    @Override
                    public void actionPerformed(ActionEvent e) {

                        for (int i = 0; i < 7; i++) {
                            for (int j = 0; j < 4; j++) {

                                if (e.getSource() == matriez_BOTONES[j][i] && controlador == 0) {

                                    // se agrega todas acciones de los botones y se toma desciones
                                    // en caso de botones especiales
                                    // se tiene control de donde se va escrtibir

                                    pantalla.setText(pantalla.getText() + matriez_BOTONES[j][i].getText());

                                    cadena = pantalla.getText();
                                    //System.out.println(cadena);
                                    if (matriez_BOTONES[j][i].getText() == "CE") {

                                        if (cadena.length() >= 3) {
                                            cadena = cadena.substring(0, cadena.length() - 3);
                                            pantalla.setText(cadena);

                                        } else {
                                            pantalla.setText("");

                                        }
                                    }
                                    if (cadena.length() <= 1 && matriez_BOTONES[j][i].getText() == ".") {
                                        pantalla.setText("0.");
                                    }

                                }

                            }
                        }
                    }
                });
                jPanel4.add(matriez_BOTONES[j][i]);
            }
        }

    }

    /**
     * contruye el panel donde se puede elegir los rangos
     * y se de clara la tolerancia o margen de eror que se puede
     * aceptar
     */
    private void panelElegirRango() {
        Font formaatoTexto = new Font("Arial", Font.ITALIC, 18);
        jPanel5 = new JPanel();
        jPanel5.setBackground(new ColorUIResource(238, 238, 238));
        jPanel5.setForeground(Color.white);
        jPanel5.setLayout(null);
        jPanel5.setBounds(10, 85, 495, 180);
        jPanel5.setBorder(new RoundedBorder(10));
        jPanel2.add(jPanel5);

        nombrePanelIngresoDatos = new JLabel("elige tu rango y porcentaje de error");
        nombrePanelIngresoDatos.setBackground(new ColorUIResource(255, 255, 255));
        nombrePanelIngresoDatos.setBounds(10, 0, 400, 50);
        nombrePanelIngresoDatos.setFont(formaatoTexto);
        jPanel5.add(nombrePanelIngresoDatos);
        
        /*--------------
         *creacion de el jlabel donde se imprimer la infomacin para guiar a el usuario
         * y creacion del espacion donde el usuario ingresara los datos  
         *-----------*/
        rangoA = new JTextField();
        rangoA.setBackground(Color.white);
        rangoA.setBounds(50, 60, 50, 30);
        rangoA.setFont(formaatoTexto);
        // rangoA.setEditable(false);
        rangoA.setHorizontalAlignment(JTextField.RIGHT);
        jPanel5.add(rangoA);

        rangoALabel = new JLabel("a=");
        rangoALabel.setBackground(new ColorUIResource(238, 238, 238));
        rangoALabel.setBounds(15, 50, 50, 50);
        rangoALabel.setFont(formaatoTexto);
        jPanel5.add(rangoALabel);

        /*--------------
         *creacion de el jlabel donde se imprimer la infomacin para guiar a el usuario
         * y creacion del espacion donde el usuario ingresara los datos  todo esto
         * para el rango b
         *-----------*/
        rangoB = new JTextField();
        rangoB.setBackground(Color.white);
        rangoB.setBounds(170, 60, 50, 30);
        rangoB.setFont(formaatoTexto);
        // rangoB.setEditable(false);
        rangoB.setHorizontalAlignment(JTextField.RIGHT);
        jPanel5.add(rangoB);

        rangoBLabel = new JLabel(",  b=");
        rangoBLabel.setBackground(new ColorUIResource(238, 238, 238));
        rangoBLabel.setBounds(120, 50, 50, 50);
        rangoBLabel.setFont(formaatoTexto);
        jPanel5.add(rangoBLabel);

        /*------
         * En el caso del error
         *-------*/
        erroJLabel = new JLabel("% de error aceptado ");
        erroJLabel.setBackground(new ColorUIResource(238, 238, 238));
        erroJLabel.setBounds(10, 100, 190, 50);
        erroJLabel.setFont(formaatoTexto);
        jPanel5.add(erroJLabel);

        error = new JTextField();
        error.setBackground(Color.white);
        error.setBounds(210, 110, 50, 30);
        error.setFont(formaatoTexto);
        // error.setEditable(false);
        error.setHorizontalAlignment(JTextField.RIGHT);
        jPanel5.add(error);

        /*---------------
         * para el boton 
         *---------------*/
        evaluar = new JButton("evaluar");
        // evaluar.setOpaque(true);
        evaluar.setBackground(Color.BLUE);
        evaluar.setForeground(Color.WHITE);
        evaluar.setBounds(290, 80, 100, 30);
        evaluar.setFont(formaatoTexto);
        evaluar.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == evaluar) {
                  
                    limpiarTabla();
                    ordenar();
                    
                 
                    funcionIngresada = new MetodoBiseccion();
            

                    if (!pantalla.getText().equals("") && funcionIngresada.contieneX(pantalla.getText())) {
                     
                        /*
                         * mostrar resultados
                         * se muestra la funcion por pantalla
                         */
                        try {
                            
                            Double errorParceado=Double.parseDouble(error.getText());
                            
                            Double rangoAPacerdado=Double.parseDouble(rangoA.getText());

                            Double rangoBParcedado=Double.parseDouble(rangoB.getText());
                
                            metodoBiseccion=new MetodoBiseccion(pantalla.getText(), rangoAPacerdado, rangoBParcedado, errorParceado);
                            
                            pantallaFuncion=new JTextField(pantalla.getText());
                            pantallaFuncion.setBackground(new ColorUIResource(238, 238, 238));
                            pantallaFuncion.setBounds(35, 120, 400, 30);
                            pantallaFuncion.setBorder(null);
                            jPanel8.add(pantallaFuncion);

                            pantallaSalidaIntervalo= new JTextField("["+rangoA.getText()+","+rangoB.getText()+"]");
                            pantallaSalidaIntervalo.setBounds(110, 161, 338, 30);
                            pantallaSalidaIntervalo.setBackground(new ColorUIResource(238, 238, 238));
                            pantallaSalidaIntervalo.setBorder(null);
                            jPanel8.add(pantallaSalidaIntervalo);
        


                            
                        } catch (NumberFormatException k) {
                            JOptionPane.showMessageDialog(null, "Verifique que el intervalo o tolerancia sea correcto.", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                            
                    }
                    else {
                        JOptionPane.showMessageDialog(null, "Se requiere que ingrese una función con al menos una variable x.", "No ingreso función", JOptionPane.ERROR_MESSAGE);
                    }

                }
            }
        });

        jPanel5.add(evaluar);

    }

    /**
     * se contruye la pantalla donde se visualisara
     * la funcion que se ha ingresado
     */
    private void panelPantalla() {
        Font formaatoTexto = new Font("Arial", Font.ITALIC, 40);
        jPanel6 = new JPanel();
        jPanel6.setBackground(new ColorUIResource(238, 238, 238));
        jPanel6.setForeground(new ColorUIResource(0, 0, 0));
        jPanel6.setBounds(10, 1, 480, 95);
        jPanel6.setLayout(null);
        jPanel2.add(jPanel6);

        pantalla = new JTextField();
        pantalla.setBackground(Color.white);
        pantalla.setForeground(new ColorUIResource(0, 0, 0));
        pantalla.setBounds(5, 10, 480, 60);
        pantalla.setFont(formaatoTexto);
        pantalla.setEditable(false);
        pantalla.setHorizontalAlignment(JTextField.RIGHT);

        jPanel6.add(pantalla);

    }

    /**
     * 
     */

    public void panelTabla() {

        jPanel7 = new JPanel();
        // jPanel7.setBackground(new ColorUIResource(200,238,238));
        jPanel7.setBounds(5, 10, 470, 250);
        jPanel7.setLayout(null);
        jPanel3.add(jPanel7);

        metodo = new JLabel("Método de bisección");
        metodo.setBounds(0, 10, 180, 30);
        jPanel7.add(metodo);

        jScrollTabla = new JScrollPane();
        jScrollTabla.setBackground(new ColorUIResource(238, 238, 238));
        jScrollTabla.setBounds(0, 50, 450, 200);

        jPanel7.add(jScrollTabla);

        tabaResultados = new JTable();
        tabaResultados.setBackground(new ColorUIResource(238, 238, 238));
        tabaResultados.setModel(new DefaultTableModel(
                new Object[][] {
                    
                },
                new String[] {
                        "paso", "Xa", "f(Xa)", "Xb", "f(Xb)", "Xr (Raiz)", "f(Xr)",
                }) {
            boolean[] canEdit = new boolean[] {
                    false, false, false, false, true, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit[columnIndex];
            }
        });

        jScrollTabla.setViewportView(tabaResultados);

        

    }

    /**
     * 
     */
    public void tablaResultados()

    {
        jPanel8 = new JPanel();
        jPanel8 = new JPanel();
        jPanel8.setBounds(5, 250, 470, 250);
        jPanel8.setLayout(null);
        jPanel3.add(jPanel8);

        descripcion = new JTextArea("1. se deber garantizar que el en el intervalo" +
                "\n  escogido ([Xa,Xb])halla almenos una raiz ( f(Xa)f(Xb) < 0) \n\n2.se reduce el intervalo sucesivamente hasta hacerlo tan\n  pequeño como exija la precisión que hayamos \n  decidido emplear ");
        descripcion.setBackground(new ColorUIResource(238, 238, 238));
        descripcion.setBounds(0, 10, 400, 100);
        jPanel8.add(descripcion);

      
        funcioningresada = new JLabel("f(x)=");
        funcioningresada.setBounds(0, 120, 80, 30);
        jPanel8.add(funcioningresada);

        intevalo = new JLabel("En el invervalo: ");
        intevalo.setBounds(0, 160, 200, 30);
        jPanel8.add(intevalo);

        resultado = new JLabel("Raiz: ");
        resultado.setBounds(0, 210, 200, 30);
        jPanel8.add(resultado);
    }

    private void ordenar() {

        TableColumnModel ModeloColumna = tabaResultados.getColumnModel();
        tabaResultados.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        ModeloColumna.getColumn(0).setPreferredWidth(50);
        ModeloColumna.getColumn(1).setPreferredWidth(100);
        ModeloColumna.getColumn(2).setPreferredWidth(100);
        ModeloColumna.getColumn(3).setPreferredWidth(100);
        ModeloColumna.getColumn(4).setPreferredWidth(100);
        ModeloColumna.getColumn(5).setPreferredWidth(100);
        ModeloColumna.getColumn(6).setPreferredWidth(100);

    }

    /**
     * nos permite eliminar todo el contendio de la tabla cada ves que ejecutemos
     * o precionesmos el boton evaluar
     */
   
    public void limpiarTabla() {
        tabaResultados.setModel(new DefaultTableModel(new Object[][]{},
                new String[]{"paso", "Xa", "f(Xa)", "Xb", "f(Xb)", "Xr (Raiz)", "f(Xr)"}) {
                });
    }

    public static void escribirResultados(double Xr) {
        

        resultado.setText(resultado.getText()+Xr);
        
    }
    
    /*
     * Geter necesarios, porque se aplico encamsulamiento
     */

    /**
     * retorna el boton evaluar
     * 
     * @return evaluar JButton
     */


    public static void setTabaResultados( Object[] datosFilas) {
        modelo = (DefaultTableModel) tabaResultados.getModel();
        modelo.addRow(datosFilas);
  
        tabaResultados.setModel(modelo);
    }

    
}
