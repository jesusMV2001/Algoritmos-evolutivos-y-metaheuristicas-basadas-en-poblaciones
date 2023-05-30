/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package prac2.algoritmos;

import java.util.ArrayList;
import java.util.Random;
import prac2.Configurador;
import prac2.TablaDatos;
import prac2.funciones.Evaluador;

/**
 *
 * @author Xjesu
 */
public class ED extends Evolutivo{

    Configurador config;
    private ArrayList<Individuo> poblacion;
    private ArrayList<ArrayList<Individuo>> soluciones; //Poblacion - Individuo - Genotipo
    
    public ED(Configurador config){
        this.config = config;
        poblacion = new ArrayList<>();
        soluciones = new ArrayList<>();
    }
    
    @Override
    public double[] algoritmoEvolutivo(Evaluador exe, long semilla, TablaDatos datos) {
        int maxIndividuos = config.getMaxIndividuos();
        Random aleatorio = new Random(semilla);
        ArrayList<Individuo> pobGenerada = new ArrayList<>();
        
        for(int i=0; i<config.getTamPoblacion(); i++){
            poblacion.add(new Individuo(config, exe, aleatorio,i));
            pobGenerada.add(poblacion.get(i));
        }
        
        int evaluados = config.getTamPoblacion();
        int generacion = 1;        
        while(evaluados < maxIndividuos){
            soluciones.add(pobGenerada);
            pobGenerada = new ArrayList<>();
            ArrayList<Individuo> creados = new ArrayList<>();
            for (Individuo padre : poblacion) {
                //OPERADOR TERNARIO
                int ale1 = aleatorio.nextInt(0, poblacion.size());
                int ale2;
                do{
                    ale2 = aleatorio.nextInt(0, poblacion.size());
                }while(ale1 == ale2);
                //TORNEO PARA LA ELECCIÓN DEL OBJETIVO
                Individuo objetivo = torneo(aleatorio, config.getkIndObj());
                
                //SE HACE LA RECOMBINACION
                double[] valores = recombinacion(aleatorio, padre, objetivo, ale1, ale2);
                
                //SE COMPRUEBA QUIEN ES MEJOR EL PADRE O EL HIJO, SE QUEDA EL MEJOR
                Individuo hijo = new Individuo(valores, exe, config, generacion, padre.getIndice());
                if(hijo.getFitness() < padre.getFitness()){
                    creados.add(hijo);
                    pobGenerada.add(hijo);
                }
                else creados.add(padre);
                
                evaluados++;
            }
            
            generacion++;
            reemplaza(creados);
        }
        poblacion.sort((o1, o2) -> o1.compareTo(o2));
        return poblacion.get(0).getGenotipo();
    }
    
    private void reemplaza(ArrayList<Individuo> nuevaPob){
        poblacion.clear();
        for (Individuo ind : nuevaPob) {
            poblacion.add(ind);
        }
    }
    
    private boolean repetido(Individuo aInsertar, ArrayList<Individuo> padres){
        for (Individuo padre : padres) {
            if(aInsertar.getIndice()==padre.getIndice()) return true;
        }
        return false;
    }
    
    private Individuo torneo(Random aleatorio, int k){
        ArrayList<Individuo> generados = new ArrayList<>();
        generados.add(poblacion.get(aleatorio.nextInt(0, poblacion.size())));
        Individuo mejor = generados.get(0);
        
        for(int i=1; i<k; i++){
            Individuo nuevo;
            do{
                nuevo = poblacion.get(aleatorio.nextInt(0, poblacion.size()));
            }while(repetido(nuevo, generados));
            if(nuevo.getFitness() < mejor.getFitness()) mejor = nuevo;
        }
        
        return mejor;
    }
    
    private double[] recombinacion(Random aleatorio, Individuo padre, Individuo objetivo, int ale1, int ale2){
        double[] valores = new double[config.getDimension()];
        double FactMutacion = aleatorio.nextDouble();
        for (int i = 0; i < config.getDimension(); i++) {
            if (aleatorio.nextDouble() > config.getProbCambio()) {
                valores[i] = objetivo.getAtPos(i);
            } else {
                valores[i] = padre.getAtPos(i) + FactMutacion
                        * (poblacion.get(ale1).getAtPos(i) - poblacion.get(ale2).getAtPos(i));
            }

        }
                
        return valores;
    }
    
    @Override
    public void limpiaAlgoritmo(){
        poblacion.clear();
        soluciones.clear();
    }
    
    @Override
    public ArrayList<ArrayList<Individuo>> getSoluciones(){
        return soluciones;
    }

    @Override
    public ArrayList<Individuo> getElites() {
        return null;
    }
}
