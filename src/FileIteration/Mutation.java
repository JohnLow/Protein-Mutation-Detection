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
    
    ExonData exon = new ExonData();
    GeneData gene = new GeneData();
    
    
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
       
    public int[] getExonStart(String isomerAccession) throws FileNotFoundException, IOException{
        
       int[] exonStart = null;
       String filePath = "C:\\Users\\ipurusho.ASURITE\\Desktop\\Mutation files\\test_TCGA.txt";
       BufferedReader TSVFile = new BufferedReader(new FileReader(filePath));  
       String str;
       
       int i = 0;
       
       while((str = TSVFile.readLine())!= null){
          String[] row = str.split("[\t]");
          if(row[1].equals(isomerAccession)){
           
              
              String[] tempStart = row[9].split("(?:^|,)(\\\"(?:[^\\\"]+|\\\"\\\")*\\\"|[^,]*)");
              for (int j = 0; j<tempStart.length; j++){
                  
                  exonStart[j] = Integer.parseInt(tempStart[j]);
                  
              }
          }
           i++;
       }
       
   
       return exonStart; 
    }
       
    public int[] getExonEnd(String isomerAccession) throws FileNotFoundException, IOException{
        
       int[] exonEnd = null;
       String filePath = "C:\\Users\\ipurusho.ASURITE\\Desktop\\Mutation files\\test_TCGA.txt";
       BufferedReader TSVFile = new BufferedReader(new FileReader(filePath));  
       String str;
       
       int i = 0;
       
       while((str = TSVFile.readLine())!= null){
          String[] row = str.split("[\t]");
          if(row[1].equals(isomerAccession)){
           
              
              String[] tempStart = row[10].split("(?:^|,)(\\\"(?:[^\\\"]+|\\\"\\\")*\\\"|[^,]*)");
              for (int j = 0; j<tempStart.length; j++){
                  
                  exonEnd[j] = Integer.parseInt(tempStart[j]);
                  
              }
          }
           i++;
       }
       
   
       return exonEnd; 
    }

    public int[] exonIndex(String str) throws FileNotFoundException, IOException{
        
        int[] exonNumber = null;
        int mutationIndex = Integer.parseInt(gene.getGeneData(str)[1]);
        int numberOfExons = this.getExonStart(str).length;
        
        for(int i = 0; i<numberOfExons; i++){
            
            if(mutationIndex >= this.getExonStart(str)[i] && mutationIndex <= this.getExonEnd(str)[i]){
                
                exonNumber[0] = i+1; //exon number (correspoinding to GenBank entry)
                exonNumber[1] = this.getExonStart(str)[i] - mutationIndex; //relative position in that specific exon
                
                
                
            }
            
            
        }
        
        
        
        return exonNumber;
    }
    
    
    public char[] analyzeGene(String isomerAccession, String str) throws BioException, FileNotFoundException, IOException{
        
        
        this.getStartPosition(isomerAccession);
        String nucleicSeq = exon.getExons(isomerAccession);
       
        int startIndex = this.getStartPosition(isomerAccession);
        int mutationIndex = Integer.parseInt(gene.getGeneData(str)[1]);
        int mutationLocation = 4; //mutationIndex - startIndex;
        
        
        
        
        char[] iterateSeq = nucleicSeq.toCharArray();
        System.out.println(iterateSeq);
        char[] mutatedSeq = gene.getGeneData(str)[3].toCharArray();
        iterateSeq[mutationLocation] = mutatedSeq[0];
        
        
        System.out.println(mutatedSeq[0]);
        System.out.println(iterateSeq);
        
        return iterateSeq;
        
    }
    
    
    
    
    
    
    
    
    
}
