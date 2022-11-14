package interfaz;

import javax.swing.*;

import javax.swing.plaf.ColorUIResource;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

import mundo.*;

import java.awt.*;
import java.awt.event.*;

public class Menu extends JFrame {
    /*--------------
    * atributos
    *----------------------*/

    // @TODO se utiliza para almaceenar el modelo de la tabla
    private static DefaultTableModel modelo = new DefaultTableModel();

    // @TODO controla lo que se ba observar en pantalla, es de tipo byte porque no
    // se almacenaran valores muy grandes
    byte controlador = 1;

    private JPanel jPanel1, jPanel2, jPanel3, jPanel4, jPanel5, jPanel6, jPanel7, jPanel8,jPanel9, jPanel10, jPanel11, jPanel12,jPanel13;

    private JTextField pantalla, rangoA, rangoB, error, pantallaFuncion, pantallaSalidaIntervalo;

    private JLabel metodo, metodo2, metodo3,funcioningresada,
            intevalo,nombrePanelIngresoDatos,rangoALabel,rangoBLabel,erroJLabel;

    private static JLabel resultado;

    private JTextArea descripcion, descripcionSecante,descripcionNewton;

    private JButton evaluar;

    private JScrollPane jScrollTabla, jScrollTabla2;

    private static JTable tabaResultados, tabla2;

    private JButton  metodoSecante,metodoNewton,metodosBiseccion;

    private final static JButton matriez_BOTONES[][] = new JButton[7][7];

    private final static String NUMEROSOPERADORES[][] = {
            { "1", "2", "3", "CE", ".", "sin", "⬇" },
            { "4", "5", "6", "+", "^", "*", "cos" },
            { "7", "8", "9", "-", "√", "log", "tan" },
            { "x", "0", "÷", "(", ")", "ln", "_e" }
    };

    private String cadena;

    private JTabbedPane menu;

    /*-------------------------
     * Relaciones 
     *-----------------*/
    private MetodoBiseccion funcionIngresada;

    private MetodoBiseccion metodoBiseccion;

    private newtonRaphson newton;
    
    private MetodoSecante secante;


    /*-----------------------
     * Metodos
     *----------------------*/
    /**
     * metodo contructor de la clase Menu Biseccion
     */   
    public Menu() {
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
        jPanel3.setBounds(502, 2, 492, 508);
        /*
         * jpanel 9 pertenese a el metodo de la secante
         */
        jPanel9 = new JPanel();
        jPanel9.setForeground(new ColorUIResource(238, 238, 238));
        jPanel9.setLayout(null);
        jPanel9.setBounds(502, 2, 492, 508);
         /*
          * jpanel10 pertense a el metodo de newton
          */
        jPanel10 = new JPanel();
        jPanel10.setForeground(new ColorUIResource(238, 238, 238));
        jPanel10.setLayout(null);
        jPanel10.setBounds(502, 2, 492, 508);
        /*secrea el panel que nos permite hacer pestañas
         * este panel es de tipo JTabbedPane y  se agrega cada metodo
         */
        menu=new JTabbedPane();
        menu.setBounds(502, 0, 492,508);
        menu.addTab("Biseccion", jPanel3);
        menu.addTab("Secante", jPanel9);
        menu.addTab("Newton-R", jPanel10);
        jPanel1.add(menu);
        panelTabla();
        tablaResultados();

        ordenar();
        panelSecante();
        

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
                                
                                if (e.getSource() == matriez_BOTONES[j][i]) {

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
        nombrePanelIngresoDatos = new JLabel("Bienvenido, escoje el metodo ha desarrollar");
        nombrePanelIngresoDatos.setBackground(new ColorUIResource(255, 255, 255));
        nombrePanelIngresoDatos.setBounds(10, 0, 400, 50);
        nombrePanelIngresoDatos.setFont(formaatoTexto);
        jPanel5.add(nombrePanelIngresoDatos);

        /*--------------
         *creacion de el jlabel donde se imprimer la infomacin para guiar a el usuario
         * y creacion del espacion donde el usuario ingresara los datos  
         *-----------*/
        rangoA = new JTextField();
        rangoA.setBounds(50, 60, 50, 30);
        rangoA.setFont(formaatoTexto);
        rangoA.setVisible(false);
        //rangoA.setEditable(false);
        rangoA.setHorizontalAlignment(JTextField.RIGHT);
        jPanel5.add(rangoA);

        rangoALabel = new JLabel("------");
        rangoALabel.setBackground(new ColorUIResource(238, 238, 238));
        rangoALabel.setBounds(15, 50, 50, 50);
        rangoALabel.setFont(formaatoTexto);
        jPanel5.add(rangoALabel);
        
        /*--------------
         *creacion de el jlabel donde se imprimer la infomacjPanel5.add(rangoB);in para guiar a el usuario
         * y creacion del espacion donde el usuario ingresara los datos  todo esto
         * para el rango b
         *-----------*/
        rangoB = new JTextField();
        rangoB.setBackground(Color.white);
        rangoB.setBounds(170, 60, 50, 30);
        rangoB.setFont(formaatoTexto);
        //rangoB.setEditable(false);
        rangoB.setHorizontalAlignment(JTextField.RIGHT);
        rangoB.setVisible(false);
        jPanel5.add(rangoB);
        
        rangoBLabel = new JLabel("");
        rangoBLabel.setBackground(new ColorUIResource(238, 238, 238));
        rangoBLabel.setBounds(120, 50, 50, 50);
        //rangoBLabel.setFont(formaatoTexto);
        jPanel5.add(rangoBLabel);
        
        /*------
         * En el caso del error
         *-------*/
        erroJLabel = new JLabel("-----------------------");
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
        error.setVisible(false);
        jPanel5.add(error);


        /*seleccionador de metodo */
        metodosBiseccion=new JButton("Biseccion");
        metodosBiseccion.setBounds(400, 45, 100, 20); 
        metodosBiseccion.setForeground(Color.BLACK);
        metodosBiseccion.setBackground(new ColorUIResource(255, 255, 255));
        metodosBiseccion.setBorderPainted(false);
        
        metodosBiseccion.addActionListener( new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                controlador=1;
                nombrePanelIngresoDatos.setText("Ingresa: los valores a y b");
                rangoALabel.setText("a:");
                rangoBLabel.setText(",      b:");
                rangoA.setVisible(true);
                rangoB.setVisible(true);
                jPanel5.add(rangoB);
                jPanel5.add(rangoA);
                erroJLabel.setText("Error en porcentaje:");
                error.setVisible(true);
                jPanel5.add(error);

                
                menu.setSelectedIndex(0);
                    
                    
            }

        });
        jPanel5.add(metodosBiseccion);

        metodoSecante=new JButton("Secante");
        metodoSecante.setBounds(400, 70, 100, 20);
        metodoSecante.setForeground(Color.BLACK);
        metodoSecante.setBackground(new ColorUIResource(255, 255, 255));
        metodoSecante.setBorderPainted(false);   
        metodoSecante.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                controlador=2;
                nombrePanelIngresoDatos.setText("Ingresa: X1,X0 y porcentaje de error");
                rangoALabel.setText("Xi: ");
                rangoBLabel.setText(",    X0: ");
                rangoA.setVisible(true);
                rangoB.setVisible(true);
                jPanel5.add(rangoB);
                jPanel5.add(rangoA);
                erroJLabel.setText("Error en porcentaje:");
                error.setVisible(true);
                jPanel5.add(error);

                menu.setSelectedIndex(1);

               
            }

        });  
        jPanel5.add(metodoSecante);

        metodoNewton=new JButton("Newto-R");
        metodoNewton.setBounds(400, 95, 100, 20);    
        metodoNewton.setForeground(Color.BLACK);
        metodoNewton.setBackground(new ColorUIResource(255, 255, 255));
        metodoNewton.setBorderPainted(false);
        metodoNewton.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                    controlador=3;
                    descripcion.setText("En análisis numérico, el método de Newton-Raphson es un"+
                    "\n"+"algoritmo para encontrar aproximaciones de los ceros o" +
                    "\n"+"raíces de una función real."+ 
                    "\n"+"También puede ser usado para encontrar el máximo o mínimo"+ 
                    "\n"+"de una función, encontrando los ceros de su primera derivada."+
                    "\n"+"\n"+"Para el calculo de la raiz por este metodo se necesita:"+
                    "\n"+"1. necesitamos la funcion a la cual le vamos a encontrar su raiz"+
                    "\n"+"2. el punto inicial de la funcion, este puede ser cualquier"+ 
                    "\n"+"numero que pertenezca a el dominio de la funcion"+
                    "\n"+"3. la derivada de la funcion");
                    descripcion.setLocation(10, 10);
                    descripcion.setSize(400,200);
                    descripcion.setFont(new Font("Arial", Font.ITALIC, 12));
                    jPanel10.add(descripcion);

                    nombrePanelIngresoDatos.setText("Ingresa: la funcion y el punto inicial");
                    rangoALabel.setText("pi: ");
                    rangoA.setVisible(true);
                    jPanel5.add(rangoA);
                    erroJLabel.setText("Error en porcentaje:");
                    error.setVisible(true);
                    jPanel5.add(error);
                    
                    
                    
                    jPanel12 = new JPanel();
                    jPanel12 = new JPanel();
                    jPanel12.setBounds(10, 250, 470, 250);
                    //jPanel12.setBackground(Color.BLACK);
                    jPanel12.setLayout(null);
                    jPanel10.add(jPanel12);

                    metodo3 = new JLabel("derivada:");
                    metodo3.setBounds(0, 50, 100, 30);
                    jPanel12.add(metodo3);

                    funcioningresada = new JLabel("f(x)=");
                    funcioningresada.setBounds(0, 10, 50, 30);
                    jPanel12.add(funcioningresada);

                    intevalo = new JLabel("error: ");
                    intevalo.setBounds(0, 90, 200, 30);
                    jPanel12.add(intevalo);

                    resultado = new JLabel("Raiz: ");
                    resultado.setBounds(0, 130, 200, 30);
                    jPanel12.add(resultado);

                    menu.setSelectedIndex(2);
            }

        });
        jPanel5.add(metodoNewton);
        
        /*---------------
         * para el boton 
         *---------------*/
        evaluar = new JButton("evaluar");
        // evaluar.setOpaque(true);
        evaluar.setBackground(Color.BLUE);
        evaluar.setForeground(Color.WHITE);
        evaluar.setBounds(290, 80, 100, 30);
        evaluar.setFont(formaatoTexto);
        funcionIngresada = new MetodoBiseccion();
       

        evaluar.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == evaluar) {
                  
                    limpiarTabla();
                    ordenar();
                    limpiarTablaSecante();
                    ordenarSecante();
                    
                    

                    if (!pantalla.getText().equals("") && funcionIngresada.contieneX(pantalla.getText())) {
                        
                        /*
                         * mostrar resultados
                         * se muestra la funcion por pantalla
                         */
                        try {
                            
                            Double errorParceado=Double.parseDouble(error.getText());
                            
                            Double rangoAPacerdado=Double.parseDouble(rangoA.getText());
                            
                            if (controlador==3) {
                                rangoB.setText("0");
                            }
                            Double rangoBParcedado=Double.parseDouble(rangoB.getText());
                            
                            switch (controlador) {
                                
                                case 1:
                                    metodoBiseccion=new MetodoBiseccion(pantalla.getText(), rangoAPacerdado, rangoBParcedado, errorParceado);
                                    break;
                                case 2:
                                    secante=new MetodoSecante(pantalla.getText(), rangoAPacerdado,rangoBParcedado , errorParceado);
                                    break;
                                case 3:
                                    //System.out.println("se llama newton");
                                    newton =new newtonRaphson(pantalla.getText(),rangoAPacerdado,errorParceado);
                                    resultadosNewton();
                                    break;
                            
                                default:
                                    break;
                            }
                
                                                        
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
     * se crea el panel donde estara el metodo biseccion
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
                        "paso", "Xa", "f(Xa)", "Xb", "f(Xb)", "Xr (Raiz)", "f(Xr)","error %"
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
     * panel inferior donde se visualizan los resultados y esta la descripcion del metodo
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

    /*
     * reordena la tabla
     */
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
                new String[]{"paso", "Xa", "f(Xa)", "Xb", "f(Xb)", "Xr (Raiz)", "f(Xr)","error %"}) {
                });
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
        resultado.setText(resultado.getText()+"");
                
    }

    public static void escribirResultados(double Xr) {
        

        resultado.setText(resultado.getText()+Xr);
        
    }
       
    public void panelSecante(){
        jPanel11 = new JPanel();
        // jPanel7.setBackground(new ColorUIResource(200,238,238));
        jPanel11.setBounds(5, 10, 470, 250);
        jPanel11.setLayout(null);
        jPanel9.add(jPanel11);

        metodo2 = new JLabel("Método secante");
        metodo2.setBounds(0, 10, 180, 30);
        jPanel11.add(metodo2);

        jScrollTabla2 = new JScrollPane();
        jScrollTabla2.setBackground(new ColorUIResource(238, 238, 238));
        jScrollTabla2.setBounds(0, 50, 450, 200);

        jPanel11.add(jScrollTabla2);
        tabla2 = new JTable();
        tabla2.setBackground(new ColorUIResource(238, 238, 238));
        tabla2.setModel(new DefaultTableModel(
                new Object[][] {
                    
                },
                new String[] {
                        "X(i-1)", "Xi", "x(i+1)(Raiz)", "f(Xi)",  "f(Xi-1)","error %"
                }) {
            boolean[] canEdit = new boolean[] {
                    false, false, false, false, true, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit[columnIndex];
            }
        });

        jScrollTabla2.setViewportView(tabla2);
        limpiarTablaSecante();
        ordenarSecante();
        tablaResultadosSecante();

    }
    /**
     * 
     */

    private void ordenarSecante() {

        TableColumnModel ModeloColumna = tabla2.getColumnModel();
        tabla2.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        ModeloColumna.getColumn(0).setPreferredWidth(100);
        ModeloColumna.getColumn(1).setPreferredWidth(100);
        ModeloColumna.getColumn(2).setPreferredWidth(120);
        ModeloColumna.getColumn(3).setPreferredWidth(200);
        ModeloColumna.getColumn(4).setPreferredWidth(200);
        ModeloColumna.getColumn(5).setPreferredWidth(150);

    }

    /**
     * 
     */
    public void limpiarTablaSecante() {
        tabla2.setModel(new DefaultTableModel(new Object[][]{},
                new String[]{"X(i-1)", "Xi", "x(i+1)(Raiz)", "f(Xi)",  "f(Xi-1)","error %"}) {
                });
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
        resultado.setText(resultado.getText()+"");
                
    }

    public void tablaResultadosSecante()

    {
        jPanel12 = new JPanel();
        jPanel12 = new JPanel();
        jPanel12.setBounds(5, 250, 470, 250);
        jPanel12.setLayout(null);
        jPanel9.add(jPanel12);

        descripcionSecante = new JTextArea("El método de la secante parte de dos puntos."+"\n"+"se estima la secante es decir la recta que"+"\n" +"corta en dos puntos a la curva."+"\n"+"\n"+"una diferencia fundamental a el metodo de biseccion"+"\n"+"es que no se nesecita garantizar el cambio de signo.");
        descripcionSecante.setBackground(new ColorUIResource(238, 238, 238));
        descripcionSecante.setBounds(0, 10, 400, 100);
        jPanel12.add(descripcionSecante);

      
        funcioningresada = new JLabel("f(x)=");
        funcioningresada.setBounds(0, 120, 80, 30);
        jPanel12.add(funcioningresada);

        intevalo = new JLabel("En el invervalo: ");
        intevalo.setBounds(0, 160, 200, 30);
        jPanel12.add(intevalo);

        resultado = new JLabel("Raiz: ");
        resultado.setBounds(0, 210, 200, 30);
        jPanel12.add(resultado);
    }

    public void resultadosNewton(){
        pantallaFuncion=new JTextField(pantalla.getText());
        pantallaFuncion.setBackground(new ColorUIResource(238, 238, 238));
        pantallaFuncion.setBounds(32, 10, 300, 30);
        pantallaFuncion.setBorder(null);
        jPanel12.add(pantallaFuncion);

        pantallaSalidaIntervalo= new JTextField(newton.getDerivada());
        pantallaSalidaIntervalo.setBounds(72, 50, 338, 30);
        pantallaSalidaIntervalo.setBackground(new ColorUIResource(238, 238, 238));
        pantallaSalidaIntervalo.setBorder(null);
        jPanel12.add(pantallaSalidaIntervalo);

        JTextField errorPantalla=new JTextField(error.getText());
        errorPantalla.setBounds(42, 90, 200, 30);
        errorPantalla.setBackground(new ColorUIResource(238, 238, 238));
        errorPantalla.setBorder(null);
        jPanel12.add(errorPantalla);

        resultado.setText("Raiz= "+newton.getXi());

    
    }
    
    /*
     * Geter necesarios, porque se aplico encamsulamiento
     */

    /**
     * nos permite modificar la tabla y agragar las filas necesarias ha esta
     * @param datosFilas
     */


    public static void setTabaResultados( Object[] datosFilas) {
        modelo = (DefaultTableModel) tabaResultados.getModel();
        modelo.addRow(datosFilas);
  
        tabaResultados.setModel(modelo);
    }
    public static void setTabaResultadosSecante( Object[] datosFilas) {
        modelo = (DefaultTableModel) tabla2.getModel();
        modelo.addRow(datosFilas);
  
        tabla2.setModel(modelo);
    }

    
}
