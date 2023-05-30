/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package prac2;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import prac2.algoritmos.ED;
import prac2.algoritmos.Ev;
import prac2.algoritmos.Evolutivo;
//import prac2.algoritmos.*;

/**
 *
 * @author jesus
 */
public class Configurador {
    private ArrayList<String> archivos;
    private ArrayList<String> algoritmos;
    private ArrayList<Long> semillas;
    private Integer dimension, maxIndividuos, tamPoblacion;
    private double probCruce, probMutacion, alfa, probCambio;
    private int numDecimales, k, numPadres, kPeores, numElites, kIndObj,numVariables;
    
    public Configurador(String ruta){
        algoritmos = new ArrayList<>();
        archivos = new ArrayList<>();
        semillas = new ArrayList<>();
        String linea;
        FileReader f =null;
        try{
            f = new FileReader(ruta);
            BufferedReader b = new BufferedReader(f);
            while( (linea=b.readLine()) != null){
                String[] split = linea.split("=");
                switch(split[0]){
                    case "Archivos":
                        String[] v= split[1].split(" ");
                        for (int i = 0; i < v.length; i++) 
                            archivos.add(v[i]);
                        break;
                    case "Semillas":
                        String[] vsemillas= split[1].split(" ");
                        for (int i = 0; i < vsemillas.length; i++) 
                            semillas.add(Long.parseLong(vsemillas[i]));
                        break;
                    case "Algoritmos":
                        String[] valgoritmos= split[1].split(" ");
                        for (int i = 0; i < valgoritmos.length; i++) 
                            algoritmos.add(valgoritmos[i]);
                        break;
                    case "Dimension":
                        dimension = Integer.parseInt(split[1]);
                        break;
                    case "MaxIndividuos":
                        maxIndividuos = Integer.parseInt(split[1]);
                        break;
                    case "TamPoblacion":
                        tamPoblacion = Integer.parseInt(split[1]);
                        break;
                    case "K":
                        k = Integer.parseInt(split[1]);
                        break;
                    case "ProbCruce":
                        probCruce = (double)Integer.parseInt(split[1]) / 100;
                        break;
                    case "ProbMutacion":
                        probMutacion = (double)Integer.parseInt(split[1]) / 100;
                        break;
                    case "ProbCambio":
                        probCambio = (double)Integer.parseInt(split[1]) / 100;
                        break;
                    case "Alfa":
                        alfa = (double)Integer.parseInt(split[1]) / 100;
                        break;
                    case "NumDecimales":
                        numDecimales = Integer.parseInt(split[1]);
                        break;
                    case "NumPadres":
                        numPadres = Integer.parseInt(split[1]);
                        break;
                    case "KPeores":
                        kPeores = Integer.parseInt(split[1]);
                        break;
                    case "NumElites":
                        numElites = Integer.parseInt(split[1]);
                        break;
                    case "KIndObj":
                        kIndObj = Integer.parseInt(split[1]);
                        break;
                    case "NumVariables":
                        numVariables = Integer.parseInt(split[1]);
                        break;
                }
            }
            
        }catch(IOException e){
            System.out.println(e);
        }
    }
    
    public ArrayList<Evolutivo> obtenerBusquedas(){
        ArrayList<Evolutivo> busquedas = new ArrayList<>();
        for (int i = 0; i < algoritmos.size(); i++) {
            switch (algoritmos.get(i)) {
                case "EvM":
                    busquedas.add(new Ev(this, true));
                    break;
                case "EvBLX":
                    busquedas.add(new Ev(this, false));
                    break;
                case "ED":
                    busquedas.add(new ED(this));
                    break;
            }
        }
        return busquedas;
    }
    
    public ArrayList<String> getArchivos() {
        return archivos;
    }

    public ArrayList<String> getAlgoritmos() {
        return algoritmos;
    }

    public ArrayList<Long> getSemillas() {
        return semillas;
    }

    public Integer getDimension() {
        return dimension;
    }

    public Integer getMaxIndividuos() {
        return maxIndividuos;
    }

    public double getProbCruce() {
        return probCruce;
    }

    public double getProbMutacion() {
        return probMutacion;
    }

    public int getNumDecimales() {
        return numDecimales;
    }    

    public Integer getTamPoblacion() {
        return tamPoblacion;
    }

    public int getK() {
        return k;
    }

    public double getAlfa() {
        return alfa;
    }

    public int getNumPadres() {
        return numPadres;
    }

    public int getkPeores() {
        return kPeores;
    }

    public int getNumElites() {
        return numElites;
    }

    public int getkIndObj() {
        return kIndObj;
    }

    public double getProbCambio() {
        return probCambio;
    }

    public int getNumVariables() {
        return numVariables;
    }

    public void cambiarDimension() {
        int aux=dimension;
        dimension = numVariables;
        numVariables=aux;
    }
    
    
    
}
