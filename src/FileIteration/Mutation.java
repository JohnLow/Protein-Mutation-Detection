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
                  
                  exonStart[j] = Integer.parseInt(tempStart[j])+1;
                  
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
                  
                  exonEnd[j] = Integer.parseInt(tempStart[j])+1;
                  
              }
          }
           i++;
       }
       
   
       return exonEnd; 
    }

    public int[] exonIndex(String str, String isomerAccession) throws FileNotFoundException, IOException{ //Method to find where the mutation exists relative to the method
        
        int[] exonNumber = new int[2];
        
     
        int mutationIndex = Integer.parseInt(gene.getGeneData(str)[1])+1 ; //location of mutation (absolute)
        int numberOfExons = this.getExonStart(isomerAccession).length;
        
        
        
        for(int i = 0; i<numberOfExons; i++){
            
            if(mutationIndex >= this.getExonStart(isomerAccession)[i] && mutationIndex <= this.getExonEnd(isomerAccession)[i]){
                
                exonNumber[0] = i+1; //exon number (correspoinding to GenBank entry)
                exonNumber[1] =  mutationIndex - this.getExonStart(isomerAccession)[i] ; //relative position in that specific exon
                
     //     System.out.println(exonNumber[0] + " " + exonNumber[1] + " " + gene.getGeneData(str)[2]);
               
            }
             
           
        }
    return exonNumber;
    }
    
    
    
    
    
    public char[] convertOriginalToArray(String str, String isomerAccession) throws BioException, FileNotFoundException, IOException{  
        
        
       
        String nucleicSeq = exon.getExons(isomerAccession); //original nucleotide sequence of exons

        char[] iterateSeq = nucleicSeq.toCharArray();
   
     //  System.out.println(isomerAccession);
     //  for (int i = 0; i<iterateSeq.length; i++){
    //  System.out.println(iterateSeq[i]);
     //  }
         
       
        return iterateSeq; //returns a char array with mutation
        
    }
    
    
    
    public char[] convertMutatedToArray(String str, String isomerAccession) throws BioException, FileNotFoundException, IOException{ 
        
        
        String nucleicSeq = exon.getExons(isomerAccession);
        char[] iterateSeq = nucleicSeq.toCharArray();
        
        iterateSeq[exon.absoluteMutationPosition(str, isomerAccession)-1] = gene.getGeneData(str)[3].toCharArray()[0];
    //    for(int i = 0; i<iterateSeq.length; i++){
         //   System.out.println(iterateSeq[exon.absoluteMutationPosition(str, isomerAccession)]);
        //    if(gene.getGeneData(str)[2].equals(iterateSeq[exon.absoluteMutationPosition(str, isomerAccession)])){
      //      iterateSeq[exon.absoluteMutationPosition(str, isomerAccession)] = gene.getGeneData(str)[3].toCharArray()[0];
       //     System.out.println(iterateSeq[exon.absoluteMutationPosition(str, isomerAccession)]);
       //     System.out.println(isomerAccession);
        //    System.out.print(iterateSeq);
        //    }
       //     }
        
        return iterateSeq;
        
    }
    
    
    
    
    
}
