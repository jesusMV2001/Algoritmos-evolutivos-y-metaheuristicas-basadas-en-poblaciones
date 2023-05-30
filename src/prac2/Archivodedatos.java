/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package prac2;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author jesus
 */
public class Archivodedatos {
    ArrayList<double[]> muestra;
    
    
    public Archivodedatos(String ruta){
        muestra = new ArrayList<>();
        String linea;
        FileReader f =null;
        try{
            f = new FileReader(ruta);
            BufferedReader b = new BufferedReader(f);
            b.readLine();
            while( (linea=b.readLine()) != null){
                String[] split = linea.split(",");
                double valores[] = new double[5];
                
                for (int i = 0; i < split.length; i++) {
                    if(i!=1){
                        if(i>1) valores[i-1] = Double.parseDouble( split[i] );
                        else valores[i] = Double.parseDouble( split[i] );
                    }
                }
                muestra.add(valores);
            }
            
        }catch(IOException e){
            System.out.println(e);
        }
    }

    public ArrayList<double[]> getMuestra() {
        return muestra;
    }

            
    
}
