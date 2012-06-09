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
public class Mutation {
    
    public int getStartPosition(String isomerAccession) throws FileNotFoundException, IOException{
        
       int start = 0;
       String filePath = "C:\\Users\\ipurusho.ASURITE\\Desktop\\Mutation files\\test_TCGA.txt";
       BufferedReader TSVFile = new BufferedReader(new FileReader(filePath));  
       String str;
       
       int i = 0;
       while((str = TSVFile.readLine())!= null){
          String[] row = str.split("[\t]");
          if(row[1].equals(isomerAccession)){
           
              
              start = Integer.parseInt(row[4]);
          }
           i++;
       }
       
   
       return start; 
    }
    

    
    public char[] analyzeGene(String isomerAccession, String str) throws BioException, FileNotFoundException, IOException{
        
        ExonData exon = new ExonData();
        GeneData gene = new GeneData();
        this.getStartPosition(isomerAccession);
        String nucleicSeq = exon.getExons(isomerAccession);
       
        int startIndex = this.getStartPosition(isomerAccession);
        int mutationIndex = Integer.parseInt(gene.getGeneData(str)[1]);
        int mutationLocation = mutationIndex - startIndex;
        
        char[] iterateSeq = nucleicSeq.toCharArray();
        char[] mutatedSeq = gene.getGeneData(str)[3].toCharArray();
        iterateSeq[mutationLocation+1] = mutatedSeq[0];
        
        
        System.out.println(iterateSeq);
        
        return iterateSeq;
        
    }
    
    
    
    
    
    
    
    
    
}
