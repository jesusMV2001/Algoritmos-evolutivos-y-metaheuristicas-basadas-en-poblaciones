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
public class Ev extends Evolutivo{

    Configurador config;
    private boolean esMedia = true;
    private ArrayList<Individuo> mejoresInd;
    private ArrayList<Individuo> poblacion, elites;
    private ArrayList<ArrayList<Individuo>> soluciones; //Poblacion - Individuo - Genotipo
    
    public Ev(Configurador config, boolean esMedia){
        this.config = config;
        this.esMedia = esMedia;
        poblacion = new ArrayList<>();
        elites = new ArrayList<>();
        soluciones = new ArrayList<>();
        mejoresInd = new ArrayList<>();
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
        poblacion.sort((o1, o2) -> o1.compareTo(o2));
        
        int evaluados = config.getTamPoblacion();
        int generacion = 1;        
        while(evaluados < maxIndividuos){
            soluciones.add(pobGenerada);
            mejoresInd.add(poblacion.get(0));
            pobGenerada = new ArrayList<>();
            //Se añaden los elites
            for(int i=0;i <config.getNumElites();i++)
                elites.add(poblacion.get(i));
            
            ArrayList<Individuo> siguienteGeneracion = new ArrayList<>();
            for(int i=0; i<poblacion.size(); i++){
                // Ejecutamos el operador de selección 
                ArrayList<Individuo> padres = new ArrayList<>();
                padres.add(torneo(aleatorio, config.getK()));
                for(int j=1;j<config.getNumPadres();j++){
                    Individuo padre;
                    do{
                        padre = torneo(aleatorio, config.getK());
                    }while(repetido(padre, padres));
                    padres.add(padre);
                }
                
                // Cruzamos los dos padres si se da la probabilidad
                Individuo ind;
                boolean cruzado = false;
                if(aleatorio.nextDouble() <= config.getProbCruce()){
                    if(esMedia) ind = operadorCruceMedia(padres, exe, aleatorio,generacion,i);   //CRUCE MEDIA
                    else ind = operadorCruceAlfa(padres, exe, aleatorio,generacion,i);  // CRUCE BLX-ALFA
                    evaluados++;
                    cruzado = true;
                }else{ // Si no se da la probabilidad escogemos el primer padre
                    ind = padres.get(0);//new Individuo(padres.get(0).getGenotipo(), exe, config, generacion, i);
                }
                
                // Mutamos el individuo (devuelve true si ha sido mutado)
                boolean mutado = false;
                if(operadorMutacion(ind, exe, aleatorio)){
                    ind.calcularFitness(exe);
                    evaluados++;
                    mutado = true;
                }
                
                siguienteGeneracion.add(ind);
                if(cruzado || mutado) pobGenerada.add(ind);
            }
            
            // Ordenamos el array para escoger los k peores
            siguienteGeneracion.sort((o1, o2) -> o1.compareTo(o2));
            for(int i=0;i<config.getNumElites();i++){
                if(!estaElite(siguienteGeneracion,elites.get(i))){
                    int index = aleatorio.nextInt(siguienteGeneracion.size()-config.getkPeores()-1, siguienteGeneracion.size());
                    //Se añade el elite
                    siguienteGeneracion.set(index, elites.get(i).setIndice(index));
                    siguienteGeneracion.sort((o1, o2) -> o1.compareTo(o2));
                }
            } 
            
            reemplaza(siguienteGeneracion);
            poblacion.sort((o1, o2) -> o1.compareTo(o2));
            elites.clear();
            generacion++;
        }      
        
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
    
    private Individuo operadorCruceMedia(ArrayList<Individuo> padres, Evaluador exe, Random aleatorio, int generacion, int indice){
        
        double[] valores = new double[config.getDimension()];
        for(int i=0; i<config.getDimension(); i++){
            double sum=0;
            for (Individuo padre : padres) {
                sum += padre.getAtPos(i);
            }
            valores[i] = sum / (double) padres.size();
        }
        
        return new Individuo(valores, exe, config,generacion,indice);
    }
    
    private Individuo operadorCruceAlfa(ArrayList<Individuo> padres, Evaluador exe, Random aleatorio, int generacion, int indice){
        
        double[] valores = new double[config.getDimension()];
        
        for(int i=0; i<config.getDimension(); i++){
            double min = padres.get(0).getAtPos(i);
            double max = padres.get(0).getAtPos(i);
            for (Individuo padre : padres) {
                if(padre.getAtPos(i) < min) min = padre.getAtPos(i);
                if(padre.getAtPos(i) > max) max = padre.getAtPos(i);
            }
            valores[i] = aleatorio.nextDouble(min - config.getNumPadres()*config.getAlfa(), max + config.getNumPadres()*config.getAlfa());
        }
        
        return new Individuo(valores, exe, config,generacion,indice);
    }
    
    private boolean operadorMutacion(Individuo ind, Evaluador exe, Random aleatorio){
        boolean mutado = false;
        for(int i=0; i<config.getDimension(); i++){
            if(aleatorio.nextDouble() <= config.getProbMutacion()){
                ind.setAtPos(aleatorio.nextDouble(exe.getRango_min(), exe.getRango_max()), i);
                mutado = true;
            }
        }
        return mutado;
    }

    private boolean estaElite(ArrayList<Individuo> siguienteGeneracion, Individuo elite) {
        for (Individuo individuo : siguienteGeneracion) 
                if(individuo.compareTo(elite)==0)
                    return true;
        
        return false;
    }
    
    @Override
    public void limpiaAlgoritmo(){
        poblacion.clear();
        elites.clear();
        soluciones.clear();
        mejoresInd.clear();
    }

    @Override
    public ArrayList<ArrayList<Individuo>> getSoluciones() {
        return soluciones;
    }
    
    @Override
    public ArrayList<Individuo> getElites() {
        return mejoresInd;
    }
}
