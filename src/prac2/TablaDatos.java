/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package prac2;

import prac2.funciones.*;
import java.io.*;
import java.util.ArrayList;
import prac2.algoritmos.Individuo;

/**
 *
 * @author Xjesu
 */
public class TablaDatos {
    private Configurador config = null;
    private int numAlgt = 0, filas = 0, cols = 0;
    private ArrayList<double [][]> datos = null;
    private FileWriter fichero = null;
    private PrintWriter pw = null;

    public TablaDatos(Configurador config, int numFunciones) {
        this.config = config;
        numAlgt = config.getAlgoritmos().size();
        filas = config.getSemillas().size();
        cols = numFunciones * 2;
        datos = new ArrayList<>();
        for (int i = 0; i < numAlgt; i++)             
            datos.add(new double[filas][cols]);
        
        
    }
    
    public void insertarDato(double valor, int ejec, int fila, int col){
        datos.get(ejec)[fila][col] = valor;
    }
    
    public void escribeInfoLog(ArrayList<ArrayList<Individuo>> soluciones,ArrayList<Individuo> elites , int numAlg, int numEjec, Evaluador exe){
        try
        {            
            
            String semilla = String.valueOf(config.getSemillas().get(numEjec));
            //Generación del fichero con todo el log de la ejecución
                fichero = new FileWriter("logs/"+config.getAlgoritmos().get(numAlg)+"_"+
                       config.getSemillas().get(numEjec)+"_"+exe.getNombre()+"_log.txt");
            pw = new PrintWriter(fichero);

            pw.println("Algoritmo " + config.getAlgoritmos().get(numAlg));
            pw.println("Semilla " + config.getSemillas().get(numEjec));
            pw.println("Funcion " + exe.getNombre());
            for (int i = 0; i < soluciones.size(); i++) {
                pw.println();
                
                pw.print("----------- Generacion " + (i + 1) + " -----------");
                if(!config.getAlgoritmos().get(numAlg).equals("ED")){
                    pw.print("\nElite: [" );
                    for(int k=0; k<config.getDimension(); k++)
                        pw.print(elites.get(i).getAtPos(k) + ", ");
                    pw.print("] Evaluacion: " + exe.evaluar(elites.get(i).getGenotipo())
                    + ", Generacion: " + elites.get(i).getGeneracion()
                    );
                }
                for(int j=0; j<soluciones.get(i).size(); j++){
                    pw.print("\nIndividuo " + soluciones.get(i).get(j).getIndice() + ": [");
                    for(int k=0; k<config.getDimension(); k++)
                        pw.print(soluciones.get(i).get(j).getAtPos(k) + ", ");
                    pw.print("] Evaluacion: " + exe.evaluar(soluciones.get(i).get(j).getGenotipo())
                    + ", Generacion: " + soluciones.get(i).get(j).getGeneracion()
                    );
                }
                pw.println();
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
           try {
           if (null != fichero)
              fichero.close();
           } catch (Exception e2) {
              e2.printStackTrace();
           }
        }
    }
    
    public void generarFichero(){
        try
        {
            // Generacion del fichero con los datos en formato CSV
            fichero = new FileWriter("salida.txt");
            pw = new PrintWriter(fichero);

            for (int i = 0; i < numAlgt; i++) {
                pw.print(config.getAlgoritmos().get(i) + ","); // Nombre tabla
                for (int j = 0; j < cols / 2; j++) pw.print("Solucion,Tiempo,"); // Cabecera tabla
                //Datos
                pw.println();
                for (int j = 0; j < filas; j++){
                    pw.print("Ejecucion_" + (j+1) + ",");
                    for (int k = 0; k < cols; k++) {
                        pw.print(datos.get(i)[j][k] + ",");
                    }
                    pw.println();
                }
                pw.println();
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
           try {
           if (null != fichero)
              fichero.close();
           } catch (Exception e2) {
              e2.printStackTrace();
           }
        }
    }    
}
