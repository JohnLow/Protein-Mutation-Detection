/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package FileIteration;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import org.biojava.bio.BioException;
import org.biojavax.bio.db.ncbi.GenbankRichSequenceDB;
import org.biojavax.bio.seq.RichSequence;

/**
 *
 * @author ipurusho
 */
public class GeneData {
 
    
    public String[] getGeneData(String str1){  //retrieve gene name from TCGA data
        
        
        String[] mutationIndex = new String[4];
        String regex = "[\t]";
        String[] parsedLine = str1.split(regex);
        
        mutationIndex[0] = parsedLine[0];  //gene name 
        mutationIndex[1] = parsedLine[5];  //gene Mutation Location
        mutationIndex[2] = parsedLine[10]; //Reference nucleotide
        mutationIndex[3] = parsedLine[12]; //Mutated nucleotide
        
       
        
        return mutationIndex;
    }
    
    
     
    public  ArrayList <String>  getAccession(String[] geneName) throws FileNotFoundException, IOException{  //retrieve list of accession numbers for isomers in reference file
        
       
       ArrayList <String> accessionNumber = new ArrayList<String>();
       String filePath = "C:\\Users\\ipurusho.ASURITE\\Desktop\\Mutation files\\test_TCGA.txt";
       BufferedReader TSVFile = new BufferedReader(new FileReader(filePath));  
       String str;
       
       
       
       int i = 0;
       while((str = TSVFile.readLine())!= null){
          String[] row = str.split("[\t]");
          if(row[12].equals(geneName[0])){
           
              accessionNumber.add(row[1]);
          
          }
           i++;
       }
        return accessionNumber; 
    }

    public String getLargest(ArrayList <String> accessionList) {  //compares all isomers and returns the accession of the largest
        
        String largestIsomer = null;
        Iterator iterator = accessionList.iterator();
        RichSequence rs = null;
        GenbankRichSequenceDB grsdb = new GenbankRichSequenceDB();
        
        
        try{
        int compareLengths[] = new int[accessionList.size()];
      
        int i = 0;
        while(iterator.hasNext()){
            
            
             boolean loop = true;
        
        while(loop){
            
        try{
        rs = grsdb.getRichSequence(iterator.next().toString());
        loop = false;
        }
        catch(BioException e){
            
        }
        }
             
             compareLengths[i] = rs.length();
             i++;
        }
        int maxValue = compareLengths[0];
        for(int j = 0; j < compareLengths.length;j++){
            if(compareLengths[j] >= maxValue){
               maxValue = compareLengths[j];
               largestIsomer = accessionList.get(j);
            }else if (compareLengths[j]==maxValue){
                
                
            }
            
        }
        }
        catch(ArrayIndexOutOfBoundsException e){
            largestIsomer = null;
            System.out.println("not available");
        }
       
        return largestIsomer;
        
        
    }




}
