package mundo;

import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import interfaz.*;
import jme.Expresion;
import jme.excepciones.ExpresionException;


public class MetodoBiseccion {
    /*---------------
     * Atributos
     *-------------*/
    private double rangoA;

    private double rangoB;
    
    private double eror;

    private String funcion;


    private int contador=0;

    private double Xr;

    private String definicion = "";

  


    /*--------------------------------------------------------
     * Relaciones 
     *--------------------------------------------------*/

   
    private Expresion expresion;
    /*---------------------
     * Metodos 
     *---------------------*/
    
     public MetodoBiseccion()
     {
       
     }

    /**
     * 
     * @param fff
     * @param a
     * @param b
     * @param t
     */

   public  MetodoBiseccion(String funcion, double rangoA, double rangoB, double eror) 
    {
        
        this.rangoA=rangoA;
        this.rangoB=rangoB;

        this.eror=eror;
     
  
        this.definicion=funcion;
        
        
        double Xr = Double.NaN;
        double errorCalculado=100;

        double fa=eval(this.rangoA);


        if (!Double.isNaN(fa)) {
            double fb=eval(this.rangoB);
            

            if ((fa * fb) < 0) {
                do {
                    Xr = (rangoA + rangoB) / 2;   
                    contador++;    
                    //System.out.println(Xr);

                    fa= eval(rangoA);
                    fb=eval(rangoB);
                    double fXr=eval(Xr);
                    
                    

                    if (fa * eval(Xr) < 0) {
                        /*
                         * Xr= es el actual 
                         * si se cumple esta condicion el rangoB es el pasado
                         * esto para cuando se halla realizado mas de un paso 
                         */
                        if (contador>1) {
                            errorCalculado=Math.abs((Xr-rangoB)/Xr)*100;
                        }
                        
                        
                        //System.out.println(errorCalculado);
                        rangoB=Xr;
                        
                    } else {
                        if (contador>1) {
                            errorCalculado=Math.abs((Xr-rangoA)/Xr)*100;
                        }
                       
                        //System.out.println(errorCalculado);
                        rangoA = Xr;
                        
                    }
                    
                    Menu.setTabaResultados(new Object[]{contador, redondearDecimales(rangoA, 6) , redondearDecimales(fa, 6), redondearDecimales(rangoB, 6),redondearDecimales(fb, 6),redondearDecimales(Xr, 6),redondearDecimales(fXr, 6),redondearDecimales(errorCalculado, 6)});
                   
            
                    
                } while (errorCalculado >=eror);
            }//termina if

            if (Double.isNaN(Xr)) {
                JOptionPane.showMessageDialog(null, "Intervalos no factibles.", "Error de intervalos", JOptionPane.WARNING_MESSAGE);
            } else {
               //MenuBiseccion.escribirResultados(Xr);
        
            }
        }
    }
    

    /**
     * este metodo verifica si hay una x en la exprecion ingresada por pantalla 
     * 
     * @param cantidad es la exprescion y verifica si hay un elemento 
     * @return retorna falso o verdadero segun si hay o no x en la expresion ingresada 
     * 
     */
    public boolean contieneX(String cantidad) {
        for (int i = 0; i < cantidad.length(); i++) {
            if (cantidad.substring(i, i + 1).equals("x"))
            { 
                return true;  
            }
                         
        }
        return false;
    }

    /**
     * metodo evalua si hay errores en en el ingreso DOUBLE.NaN es un metodo 
     * para retornar una constante de double para no generar errores 
     * @param x es el punto de x 
     * @return retorna en el caso de que no halla errores la funcion evaluada 
     */
    public double eval(double x)
    {
     
        try {
            expresion = new Expresion(this.definicion); 
          
            expresion.setVariable("x", x);
            return Double.parseDouble(String.valueOf(expresion.evaluar()));
        } catch (ExpresionException ex) {
            JOptionPane.showMessageDialog(null, "Error en la función.", "Error", JOptionPane.ERROR_MESSAGE);
            return Double.NaN; 
        }   
    }

    
    public static double redondearDecimales(double valorInicial, int numeroDecimales) {

        double parteEntera, resultado;
        resultado = valorInicial;
        parteEntera = Math.floor(resultado);
        resultado=(resultado-parteEntera)*Math.pow(10, numeroDecimales);
        resultado=Math.round(resultado);
        resultado=(resultado/Math.pow(10, numeroDecimales))+parteEntera;
        return resultado;
    }
    
    public double getXr() {
        return Xr;
    }

}
