/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package FileIteration;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 *
 * @author ipurusho
 */
public class MutationComparison {
        
            
   public static void iterateTCGA() throws FileNotFoundException, IOException{
       
     GeneData name = new GeneData();
     String filePath = "C:\\Users\\ipurusho.ASURITE\\Desktop\\Mutation files\\test_TCGA.txt";
     BufferedReader TSVFile = new BufferedReader(new FileReader(filePath));  
     String str;
     
     
       
       while((str = TSVFile.readLine())!= null){
       
           System.out.println(name.getName(str));
       
       }
   }
        
   
    public static void main(String[] args) throws FileNotFoundException, IOException  {
   
      
        iterateTCGA();
        
   
   }
        
 
}
