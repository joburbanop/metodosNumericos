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
    private MenuBiseccion menu;

   
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
        menu= new MenuBiseccion();
  
        this.definicion=funcion;
        
        
        double Xr = Double.NaN;


        double fa=eval(this.rangoA);


        if (!Double.isNaN(fa)) {
            double fb=eval(this.rangoB);
            

            if ((fa * fb) < 0) {
                do {
                    Xr = (rangoA + rangoB) / 2;   
                    contador++;                                    
                    fa=eval(rangoA);
                    fb=eval(rangoB);
                    double fXr=eval(Xr);
                    
                    menu.setTabaResultados(new Object[]{contador, rangoA, fa, rangoB, fb, Xr,fXr});
            
                    if (fa * eval(Xr) < 0) {
                        rangoB= Xr;
                    } else {
                        rangoA = Xr;
                    }
                } while (Math.abs(eval(Xr)) > eror);
            }//termina if

            if (Double.isNaN(Xr)) {
                JOptionPane.showMessageDialog(null, "Intervalos no factibles.", "Error de intervalos", JOptionPane.WARNING_MESSAGE);
            } else {
                menu.escribirResultados(Xr);
        
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
                return true;                
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

    
    
    public double getXr() {
        return Xr;
    }

}
