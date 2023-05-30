/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package prac2.funciones;

import prac2.Configurador;
import prac2.Archivodedatos;

/**
 *
 * @author admin
 */
public class Potencia extends Evaluador{
    final private Configurador config;
    private boolean mape;
    final private Archivodedatos archivo;
    final private String nombre;
    final private double rango_min = -1;
    final private double rango_max = 1;

    public Potencia(Configurador config, Archivodedatos ar, boolean mape) {
        this.config = config;
        this.archivo = ar; 
        this.mape = mape;
        if(mape)nombre = "Potencia_MAPE";
        else nombre = "Potencia_RMSE";
    }
    
    
    @Override
    public double evaluar(double[] vector){
        MAPE m = new MAPE();
        RMSE r = new RMSE();
        double pm[] = new double[archivo.getMuestra().size()];
        double pr[] = new double[archivo.getMuestra().size()];
        
        
        for (int i = 0; i < archivo.getMuestra().size(); i++) {
            double DNI = archivo.getMuestra().get(i)[0], Ta = archivo.getMuestra().get(i)[1],
                   Ws = archivo.getMuestra().get(i)[2], SMR = archivo.getMuestra().get(i)[3];
            
            pm[i] = DNI * (vector[0] + vector[1]*DNI + vector[2]*Ta + vector[3]*Ws + vector[4]*SMR);
            pr[i] = archivo.getMuestra().get(i)[4];
        }
        
        if(mape)
            return m.compute(pr, pm);
        
        return r.compute(pr, pm);
    }

    @Override
    public double getRango_min() {
        return rango_min;
    }

    @Override
    public double getRango_max() {
        return rango_max;
    }    
    
    @Override
    public double[] getSolReal(){
        return null;
    }

    @Override
    public String getNombre() {
        return nombre;
    }

    public boolean isMape() {
        return mape;
    }
    
    
}
