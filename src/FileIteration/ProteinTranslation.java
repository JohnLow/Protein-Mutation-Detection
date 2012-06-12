/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package FileIteration;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import org.biojava.bio.BioException;
import org.biojava3.core.sequence.DNASequence;
import org.biojava3.core.sequence.ProteinSequence;
import org.biojava3.core.sequence.RNASequence;
import org.biojava3.core.sequence.transcription.TranscriptionEngine;

/**
 *
 * @author ipurusho
 */
public class ProteinTranslation {
    
   
    
    public ArrayList <String> translatedOriginal(String str, String isomerAccession) throws BioException, FileNotFoundException, IOException{
        
        TranscriptionEngine.Builder b = new TranscriptionEngine.Builder();
        b.table(1).initMet(true).trimStop(true);
        TranscriptionEngine engine = b.build();
        
    Mutation mutation = new Mutation();
   // int proteinLength = mutation.convertOriginalToArray(str, isomerAccession).length;   
    ArrayList <String> proteinArray = new ArrayList<String>();
   
    String[] codon = mutation.convertOriginalToArray(str, isomerAccession).split("(?<=\\G...)");
    
   for(int i = 0; i < codon.length; i++){
    
  //  String temp = Character.toString(mutation.convertOriginalToArray(str, isomerAccession)[i]);
   // temp = temp + Character.toString(mutation.convertOriginalToArray(str, isomerAccession)[i+1]);
  //  temp = temp +  Character.toString(mutation.convertOriginalToArray(str, isomerAccession)[i+2]);
    
    
    DNASequence dna = new DNASequence(codon[i]);
    RNASequence rna = dna.getRNASequence(engine);
    ProteinSequence protein = rna.getProteinSequence(engine);
    proteinArray.add(protein.toString());
  }
    
 return proteinArray;

    
    }
    
    
      
    public ArrayList <String> translatedMutated(String str, String isomerAccession) throws BioException, FileNotFoundException, IOException{
        TranscriptionEngine.Builder b = new TranscriptionEngine.Builder();
        b.table(1).initMet(true).trimStop(true);
        TranscriptionEngine engine = b.build();
  
    Mutation mutation = new Mutation();
    
    
     ArrayList <String> proteinArray = new ArrayList<String>();
     String[] codon = mutation.convertMutatedToArray(str, isomerAccession).split("(?<=\\G...)");
   
    for(int i = 0; i < codon.length; i++){
    
   // String temp = Character.toString(mutation.convertMutatedToArray(str, isomerAccession)[i]);
   // temp = temp + Character.toString(mutation.convertMutatedToArray(str, isomerAccession)[i+1]);
   // temp = temp +  Character.toString(mutation.convertMutatedToArray(str, isomerAccession)[i+2]);
    
    DNASequence dna = new DNASequence(codon[i]);
    RNASequence rna = dna.getRNASequence(engine);
    ProteinSequence protein = rna.getProteinSequence(engine);
    proteinArray.add(protein.toString());
    
    }
    
    return proteinArray;

    }
    
    public String[] proteinComparison(String str, String isomerAccession) throws BioException, FileNotFoundException, IOException{
        
        String[] originalAndMutated = new String[3];
       ArrayList <String> translatedOriginal = this.translatedOriginal(str, isomerAccession);
        ArrayList <String> translatedMutated = this.translatedMutated(str, isomerAccession);
        
       
        
       Iterator iterator1 = translatedOriginal.iterator();
        Iterator iterator2 = translatedMutated.iterator();
        
        int i = 0;
        while(iterator1.hasNext()){
          while(iterator2.hasNext()){
              
              if(iterator1.next().toString().equals(iterator2.next().toString()) == false){
                   
                  originalAndMutated[0] = iterator1.next().toString();
                  originalAndMutated[1] = iterator2.next().toString();
                  originalAndMutated[2] = Integer.toString(i);
                  
              }i++;
              
              
          }  
            
            
        }
        
        
        return originalAndMutated;
    }
    
    
}
