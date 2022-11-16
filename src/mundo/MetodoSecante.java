package mundo;

import javax.swing.JOptionPane;

import interfaz.Menu;
import jme.Expresion;
import jme.excepciones.ExpresionException;

public class MetodoSecante {

    private Expresion expresion;
    
    private String definicion = "";
    private double xi;
    private double x0;
    private double errorInterno=100;
    private double xmas1;
    private double fxi;
    private double fx0;
    
    public MetodoSecante(String funcion, double xi, double x0,double error) 
    {
        this.xi = xi;
        this.x0 = x0;
        this.xmas1= Double.NaN;
        this.definicion=funcion;
        
        
        if (this.xi!=0 && this.x0!=0 ) 
        {
            do {
                fxi=eval(this.xi);
                fx0=eval(this.x0);
                
                //System.out.println("xi "+this.xi+" f(xi) "+fxi+" \n"+" x0"+ this.x0+ " fx0 "+fx0);

                xmas1= this.xi - (fxi * (this.x0 - (this.xi)) / (fx0 - (fxi)));
                
                //System.out.println("x+i "+xmas1);
                
                Menu.setTabaResultadosSecante(new Object[]{redondearDecimales(this.x0, 6) , redondearDecimales(this.xi, 6), redondearDecimales(xmas1, 6),fxi,fx0,redondearDecimales(errorInterno, 6)});
                
                errorInterno=Math.abs(((xmas1-this.xi)/(xmas1))*100);

                this.x0 = this.xi;
                this.xi = xmas1; 
                
               
                
                    
                
                
                
                
                
            } while (errorInterno > error);
                   
        }else {
            JOptionPane.showMessageDialog(null, "sus datos de entrada.", "Error", JOptionPane.ERROR_MESSAGE);
        }
        
    }

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
}
