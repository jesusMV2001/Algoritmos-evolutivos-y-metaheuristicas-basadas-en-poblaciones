/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package prac2.algoritmos;

import java.util.ArrayList;
import prac2.TablaDatos;
import prac2.funciones.Evaluador;

/**
 *
 * @author Xjesu
 */
public abstract class Evolutivo {
    public Evolutivo(){
    }
    
    public abstract double[] algoritmoEvolutivo(Evaluador exe, long semilla, TablaDatos datos);
    public abstract void limpiaAlgoritmo();
    public abstract ArrayList<ArrayList<Individuo>> getSoluciones();
    public abstract ArrayList<Individuo> getElites();
}
