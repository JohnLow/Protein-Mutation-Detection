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
import org.biojavax.bio.db.ncbi.GenbankRichSequenceDB;
import org.biojavax.bio.seq.RichSequence;

/**
 *
 * @author ipurusho
 */
public class Mutation {
    
    ExonData exon = new ExonData();
    GeneData gene = new GeneData();
    
    
    /* I dont believe this is applicable anymore
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
    * */
    
       
    public int[] getExonStart(String isomerAccession) throws FileNotFoundException, IOException{  //gets range for exon: START
        
       
       String filePath = "C:\\Users\\ipurusho.ASURITE\\Desktop\\Mutation files\\test_TCGA.txt";
       BufferedReader TSVFile = new BufferedReader(new FileReader(filePath));  
       String str;
       int[] exonStart = null;
       int i = 0;
       
       while((str = TSVFile.readLine())!= null){
          String[] row = str.split("[\t]");
          if(row[1].equals(isomerAccession)){
       
           String[] newString =   row[9].split("[\"]");
              
              String[] tempStart = newString[1].split("[\\,]");
              exonStart = new int[tempStart.length];
              for (int j = 0; j<tempStart.length; j++){
                  
                  exonStart[j] = Integer.parseInt(tempStart[j]);
                  
             }
          }
          
           i++;
       }
       
   
       return exonStart; 
    }
       
    public int[] getExonEnd(String isomerAccession) throws FileNotFoundException, IOException{ //gets range for exon: END (obviously)
        
       int[] exonEnd = null;
       String filePath = "C:\\Users\\ipurusho.ASURITE\\Desktop\\Mutation files\\test_TCGA.txt";
       BufferedReader TSVFile = new BufferedReader(new FileReader(filePath));  
       String str;
       
       int i = 0;
       
       while((str = TSVFile.readLine())!= null){
          String[] row = str.split("[\t]");
          if(row[1].equals(isomerAccession)){
           String[] newString =   row[10].split("[\"]");
              
              String[] tempStart = newString[1].split("[\\,]");
              exonEnd = new int[tempStart.length];
              for (int j = 0; j<tempStart.length; j++){
                  
                  exonEnd[j] = Integer.parseInt(tempStart[j]);
                  
              }
          }
           i++;
       }
       
   
       return exonEnd; 
    }

    public int[] exonIndex(String str, String isomerAccession) throws FileNotFoundException, IOException{ //Method to find where the mutation exists relative to the method
        
        int[] exonNumber = new int[2];
        
     
        int mutationIndex = Integer.parseInt(gene.getGeneData(str)[1]); //location of mutation (absolute)
        int numberOfExons = this.getExonStart(isomerAccession).length;
        
        
        
        for(int i = 0; i<numberOfExons; i++){
            
            if(mutationIndex >= this.getExonStart(isomerAccession)[i] && mutationIndex <= this.getExonEnd(isomerAccession)[i]){
                
                exonNumber[0] = i+1; //exon number (correspoinding to GenBank entry)
                exonNumber[1] =  mutationIndex - this.getExonStart(isomerAccession)[i] ; //relative position in that specific exon
                
          //    System.out.println(exonNumber[0] + " " + exonNumber[1]);
               
            }
             
           
        }
    return exonNumber;
    }
    
    
    
    
    
    public char[] analyzeGene(String isomerAccession, String str) throws BioException, FileNotFoundException, IOException{  //REWORK THIS METHOD!!!!!!11
        
        
       // this.getStartPosition(isomerAccession);
        String nucleicSeq = exon.getExons(isomerAccession); //original nucleotide sequence of exons
       
     //   int startIndex = this.getStartPosition(isomerAccession);
        //int mutationIndex = Integer.parseInt(gene.getGeneData(str)[1]);
         RichSequence rs = null;
        GenbankRichSequenceDB grsdb = new GenbankRichSequenceDB();
        rs = grsdb.getRichSequence(isomerAccession);
         
        
        
        
        
        char[] iterateSeq = nucleicSeq.toCharArray();
        System.out.println(iterateSeq);
        char[] mutatedSeq = gene.getGeneData(str)[3].toCharArray();
        iterateSeq[exon.absoluteMutationPosition(str,isomerAccession)] = mutatedSeq[0];
        
        
        System.out.println(mutatedSeq[0]);
        System.out.println(iterateSeq);
        
        return iterateSeq; //returns a char array with mutation
        
    }
    
    
    
    
    
    
    
    
    
}
