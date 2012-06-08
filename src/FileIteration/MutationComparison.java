/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package FileIteration;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import org.biojava.bio.BioException;

/**
 *
 * @author ipurusho
 */
public class MutationComparison {
        
            
   public static void iterateTCGA() throws FileNotFoundException, IOException, BioException{
       
     GeneData name = new GeneData();
     String filePath = "C:\\Users\\ipurusho.ASURITE\\Desktop\\Mutation files\\refGene_test.txt";
     BufferedReader TSVFile = new BufferedReader(new FileReader(filePath));  
     String str;
     
     
       
       while((str = TSVFile.readLine())!= null){
       
      System.out.println(name.getLargest(name.getAccession(name.getName(str))));
       
       }
   }
        
   
    public static void main(String[] args) throws FileNotFoundException, IOException, BioException  {
   
        
        iterateTCGA();
        
   
   }
        
 
}
