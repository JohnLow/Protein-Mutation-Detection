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
import org.biojava.bio.seq.DNATools;
import org.biojava.bio.symbol.SymbolList;
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
       
           String[] newString = row[9].split("[\"]");
              
              String[] tempStart = newString[0].split("[\\,]");
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
              
              String[] tempStart = newString[0].split("[\\,]");
              exonEnd = new int[tempStart.length];
              for (int j = 0; j<tempStart.length; j++){
                  
                  exonEnd[j] = Integer.parseInt(tempStart[j])+1;
                  
              }
          }
           i++;
       }
       
   
       return exonEnd; 
    }
    
public String forwardOrReverse(String isomerAccession) throws FileNotFoundException, IOException{ //gets range for exon: END (obviously)
        
       String direction = null;
       String filePath = "C:\\Users\\ipurusho.ASURITE\\Desktop\\Mutation files\\test_TCGA.txt";
       BufferedReader TSVFile = new BufferedReader(new FileReader(filePath));
       String str;
       
       int i = 0;
       
       while((str = TSVFile.readLine())!= null){
          String[] row = str.split("[\t]");
        if(row[1].equals(isomerAccession)){
                
            direction = row[3];
        }
       }
       
   
       return direction;
    }



    public int[] exonIndex(String str, String isomerAccession) throws FileNotFoundException, IOException{ //Method to find where the mutation exists relative to the method
        
        int[] exonNumber = new int[2];
        
     
        int mutationIndex = Integer.parseInt(gene.getGeneData(str)[1])+1 ; //location of mutation (absolute)
        int numberOfExons = this.getExonStart(isomerAccession).length;
        
        
        
        for(int i = 0; i<numberOfExons; i++){
            
            if(mutationIndex >= this.getExonStart(isomerAccession)[i] && mutationIndex <= this.getExonEnd(isomerAccession)[i]){
               
             if(this.forwardOrReverse(isomerAccession).equals("+")){
                exonNumber[0] = i+1; //exon number (correspoinding to GenBank entry)
                exonNumber[1] =  mutationIndex - this.getExonStart(isomerAccession)[i]; //relative position in that specific exon
             }
      
              if(this.forwardOrReverse(isomerAccession).equals("-")){
                exonNumber[0] = numberOfExons -(i); //exon number (correspoinding to GenBank entry)
               exonNumber[1] = this.getExonEnd(isomerAccession)[i]-mutationIndex;//relative position in that specific exon
               }
            }
            
            
        }
        
    //    System.out.println(exonNumber[0] + " " + exonNumber[1] + " " + gene.getGeneData(str)[2]);
     //  System.out.println(exonNumber[1]);
    return exonNumber;
    }
    
    
    
    
    
    public String convertOriginalToArray(String str, String isomerAccession) throws BioException, FileNotFoundException, IOException{  
        
        
       Mutation mutation = new Mutation();
        String nucleicSeq = null;
         if(mutation.forwardOrReverse(isomerAccession).equals("+")){
        nucleicSeq = exon.getCDS(isomerAccession); //original nucleotide sequence of exons
         }
         SymbolList sl;
         RichSequence rs = null;
       GenbankRichSequenceDB grsdb = new GenbankRichSequenceDB();
       rs = grsdb.getRichSequence(isomerAccession);
       sl = rs.getInternalSymbolList(); 
       
       
         if(mutation.forwardOrReverse(isomerAccession).equals("-")){
             
           nucleicSeq = exon.getCDS(isomerAccession);
           
      //   int start = rs.length()-exon.getEndIndexes(rs,exon.getCDSRange(rs))[0]-1;
        // int end = rs.length() - exon.getStartIndexes(rs, exon.getCDSRange(rs))[0]-1;
        //  nucleicSeq =   sl.subStr(start,end);
         
         }
       // char[] iterateSeq = nucleicSeq.toCharArray();
   
     //  System.out.println(isomerAccession);
     //  for (int i = 0; i<iterateSeq.length; i++){
    //  System.out.println(iterateSeq[i]);
     //  }
         
       
        return nucleicSeq; //returns a char array with mutation
        
    }
   
    
    
    public String convertMutatedToArray(String str, String isomerAccession) throws BioException, FileNotFoundException, IOException{ 
        
       Mutation mutation = new Mutation();
       String nucleicSeq = exon.getExons(isomerAccession);
       RichSequence rs = null;
       GenbankRichSequenceDB grsdb = new GenbankRichSequenceDB();
       rs = grsdb.getRichSequence(isomerAccession);
       String finalMutant ="";
      
        if(mutation.forwardOrReverse(isomerAccession).equals("+")){
         int index = exon.absoluteMutationPosition(str, isomerAccession)-1;
         if(index == -1){
             index = 0;
         }
         
         char[] iterateSeq = nucleicSeq.toCharArray();
         
         iterateSeq[index] = gene.getGeneData(str)[3].toCharArray()[0];
         for(int i = exon.getStartIndexes(rs, exon.getCDSRange(rs))[0]-1; i <= exon.getEndIndexes(rs,exon.getCDSRange(rs))[0]-1 ;i++ ){
         finalMutant = finalMutant + iterateSeq[i];
         }
       }
       
       
       
       
       if(mutation.forwardOrReverse(isomerAccession).equals("-")){
           SymbolList symL = DNATools.createDNA(nucleicSeq);
           symL = DNATools.reverseComplement(symL);
           nucleicSeq = symL.seqString();
           
           int index = exon.getEndIndexes(rs, exon.getExonRange(rs))[exon.getEndIndexes(rs, exon.getExonRange(rs)).length-1] - exon.absoluteMutationPosition(str, isomerAccession);
           
           char[] iterateSeq = nucleicSeq.toCharArray();
           
           
           char mutationNuc = gene.getGeneData(str)[3].toCharArray()[0];
           
           if(mutationNuc == 'g'){
               mutationNuc = 'c';
           }
            if(mutationNuc == 't'){
               mutationNuc = 'a';
           }
            if(mutationNuc == 'a'){
               mutationNuc = 't';
           }
            if(mutationNuc == 'c'){
               mutationNuc = 'g';
           }
           
           
           iterateSeq[index] = mutationNuc; //gene.getGeneData(str)[3].toCharArray()[0];
           
     //    int start = rs.length()-exon.getEndIndexes(rs,exon.getCDSRange(rs))[0]-1;
       //  int end = rs.length() - exon.getStartIndexes(rs, exon.getCDSRange(rs))[0]-1;
           
           for (int i = 0; i<=iterateSeq.length-1;i++) {
               
               finalMutant = finalMutant + iterateSeq[i];
               
           }
          symL = DNATools.createDNA(finalMutant);
           symL = DNATools.reverseComplement(symL);
           finalMutant = symL.seqString();
           
           char[] iterateSeq1 = finalMutant.toCharArray();
           int start1 = exon.getStartIndexes(rs, exon.getCDSRange(rs))[0]-1;
           int end2 = exon.getEndIndexes(rs,exon.getCDSRange(rs))[0]-1;
           finalMutant = "";
           for(int i = exon.getStartIndexes(rs, exon.getCDSRange(rs))[0]-1; i <= exon.getEndIndexes(rs,exon.getCDSRange(rs))[0]-1 ;i++ ){
            finalMutant = finalMutant + iterateSeq1[i];
            }
     /*     
       for(int i = start ; i <= end  ;i++ ){
          
           finalMutant = finalMutant + iterateSeq[i];
           
          symL = DNATools.createDNA(finalMutant);
           symL = DNATools.reverseComplement(symL);
           finalMutant = symL.seqString();
          }
           
       */    
       }
        
      
   
        
        
        return finalMutant;
        
    }
    
    
    
    
    
}
