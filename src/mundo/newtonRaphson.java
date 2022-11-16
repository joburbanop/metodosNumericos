package mundo;

import javax.swing.JOptionPane;

import org.lsmp.djep.djep.DJep;
import org.nfunk.jep.Node;
import org.nfunk.jep.ParseException;

import jme.Expresion;
import jme.excepciones.ExpresionException;

public class newtonRaphson {
    Expresion expresion;
    private String definicion = "";
    private String derivada;
    private double xmas1;
    private double xi;
    private double fxi;
    private double fderivada;
    private double errorInterno=100;


    public newtonRaphson(String funcion, double puntoinicial, double error) {

        
        
        xi = puntoinicial;
    
        derivada = Derivar(funcion);

        

        if (!Double.isNaN(puntoinicial)) {

            do {
                
                definicion = funcion;
                fxi=eval(xi);
                //System.out.println("valor del punto xi en la funcion "+fxi);

                definicion = derivada;
                fderivada=eval(xi);
                //System.out.println("el valor de xi evaluado en la derivada "+fderivada);

                xmas1=xi-(fxi/fderivada);

                errorInterno=((xmas1-xi)/(xmas1))*100;
                
                xi=xmas1;

                //System.out.println("raiz="+xi);
                

            } while (errorInterno>error);

        } else {
            JOptionPane.showMessageDialog(null, "Verifiqué la función o que el punto inicial sea correcto.", "Error",
                    JOptionPane.ERROR_MESSAGE);
        }

    }

    public String Derivar(String funcion) {

        try {

            //Djep la clase encargada de la derivacion
            DJep derivacionDJep;

            //es el nodo de la funcion
            Node nodeFuncion;

            //nodo de la derivada
            Node nodeDerivada;

            derivacionDJep = new DJep();           
             
            //se necesitan agregar las funciones estandares
            derivacionDJep.addStandardFunctions();
            
            //se agregan las constantes estandares
            derivacionDJep.addStandardConstants();

            // se añaden los numeros complejos
            derivacionDJep.addComplex();

             //prtmitir variables que no se han declarado
            derivacionDJep.setAllowUndeclared(true);
            //permite diferenteas accionaciones
            derivacionDJep.setAllowAssignment(true);
            //activando las reglas de multiplicacion 
            derivacionDJep.setImplicitMul(true);
            //reglas estandares de diferenciaon
            derivacionDJep.addStandardDiffRules();


            nodeFuncion = (Node) derivacionDJep.parse(funcion);

            //se deriva respecto a x 
            Node diff = derivacionDJep.differentiate(nodeFuncion, "x");

            //se simplifica 
            nodeDerivada = derivacionDJep.simplify(diff);

            //se envia 
            return derivacionDJep.toString(nodeDerivada);

        } catch (ParseException e) {

            System.out.println("No se puede derivar");
            return "";
        }

    }

    public double eval(double x) {

        try {
            expresion = new Expresion(this.definicion);

            expresion.setVariable("x", x);
            return Double.parseDouble(String.valueOf(expresion.evaluar()));
        } catch (ExpresionException ex) {
            JOptionPane.showMessageDialog(null, "Error en la función.", "Error", JOptionPane.ERROR_MESSAGE);
            return Double.NaN;
        }
    }

    public String getDerivada() {
        return derivada;
    }

    public double getXi() {
        return xi;
    }
    
}
