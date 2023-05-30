/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package prac2.algoritmos;

import java.util.Random;
import prac2.Configurador;
import prac2.funciones.Evaluador;

/**
 *
 * @author Xjesu
 */
public class Individuo implements Comparable<Individuo>{
    
    private Configurador config;
    private double genotipo[], fitness;
    private boolean evaluado,elite;
    private int d,generacion,indice;
    
    public Individuo(){
        genotipo = null;
    }
        
    public Individuo(double[] valores, Evaluador exe, Configurador config, int _generacion, int _indice){
        indice = _indice;
        evaluado = true;
        elite = false;
        generacion = _generacion;
        this.config = config;
        d = config.getDimension();
        genotipo = new double[d];
        System.arraycopy(valores, 0, genotipo, 0, d);
        fitness = exe.evaluar(genotipo);
    }
    
    public Individuo(Configurador config, Evaluador exe, Random aleatorio, int _indice){
        indice = _indice;
        evaluado = true;
        elite = false;
        generacion = 0;
        this.config = config;
        evaluado = true;
        d = config.getDimension();
        genotipo = new double[d];
        
        // Rellenamos el individuo con valores aleatorio dada una función de evaluación y una semilla
        for(int i=0; i<d; i++)
            genotipo[i] = aleatorio.nextDouble(exe.getRango_min(), exe.getRango_max());
        fitness = exe.evaluar(genotipo);
    }       

    public int getIndice() {
        return indice;
    }
    
    public Individuo setIndice(int indice){
        this.indice = indice;
        return this;
    }
    
    public double getFitness() {
        return fitness;
    }
    
    public double getAtPos(int pos){
        return genotipo[pos];
    }
    
    public void setAtPos(double valor, int pos){
        genotipo[pos] = valor;
    }
    
    public void calcularFitness(Evaluador exe){
        fitness = exe.evaluar(genotipo);
    }

    @Override
    public int compareTo(Individuo o) {
        if(o.getFitness() > this.getFitness())
            return -1;
        if(o.getFitness() == this.getFitness())
            return 0;
        return 1;
    }

    public double[] getGenotipo() {
        return genotipo;
    }    

    public int getGeneracion() {
        return generacion;
    }
}
