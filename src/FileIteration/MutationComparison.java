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
import org.biojavax.bio.seq.RichSequence;

/**
 *
 * @author ipurusho
 */
public class MutationComparison {
        
            
   public static void iterateTCGA() throws FileNotFoundException, IOException, BioException{
       
     GeneData name = new GeneData();
     ExonData exon = new ExonData();
     Mutation mutation = new Mutation();
     RichSequence rs = null; 
     String filePath = "C:\\Users\\ipurusho.ASURITE\\Desktop\\Mutation files\\refGene_test.txt";
     BufferedReader TSVFile = new BufferedReader(new FileReader(filePath));  
     String str;
     
     
       
      while((str = TSVFile.readLine())!= null){
    // System.out.println(name.getLargest(name.getAccession(name.getGeneData(str))));
   //  System.out.println(exon.getExons(name.getLargest(name.getAccession(name.getGeneData(str)))));
     // mutation.analyzeGene(name.getLargest(name.getAccession(name.getGeneData(str))),str,rs);
          
         // mutation.exonIndex(str, name.getLargest(name.getAccession(name.getGeneData(str))));
        exon.absoluteMutationPosition(str, name.getLargest(name.getAccession(name.getGeneData(str))));
       }
   }
        
   
    public static void main(String[] args) throws FileNotFoundException, IOException, BioException  {
   
        
        iterateTCGA();
        
   
   }
        
 
}
