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

/**
 *
 * @author ipurusho
 */
public class GeneData {
 
    
    public String getName(String str1){  //retrieve gene name from TCGA data
        
        
        String regex = "[\t]";
        String[] parsedLine = str1.split(regex);
        String geneName = parsedLine[0];
        return geneName;
    }
    
    
     
    public  ArrayList <String>  getAccession(String geneName) throws FileNotFoundException, IOException{  //retrieve list of accession numbers for isomers in reference file
        
       
       ArrayList <String> accessionNumber = new ArrayList<String>();
       String filePath = "C:\\Users\\ipurusho.ASURITE\\Desktop\\Mutation files\\test_TCGA.txt";
       BufferedReader TSVFile = new BufferedReader(new FileReader(filePath));  
       String str;
       
       
       
       int i = 0;
       while((str = TSVFile.readLine())!= null){
          String[] row = str.split("[\t]");
          if(row[12].equals(geneName)){
           
              accessionNumber.add(row[1]);
          
          }
           i++;
       }
        return accessionNumber; 
    }

    public String getLargest(){  //compares all isomers and returns the accession of the largest
        
        String largestIsomer = null;
        
        
        
        
        return largestIsomer;
        
        
    }




}
