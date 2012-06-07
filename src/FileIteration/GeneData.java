/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package FileIteration;

/**
 *
 * @author ipurusho
 */
public class GeneData {
 
    public String getName(String str1){  //retrieve gene name from TCGA data
        
        
        String regex = "[\t]";
                 
        String[] parsedLine = str1.split(regex);
        
        
       // parsedLine[0] = geneName;
        
        String geneName = parsedLine[0];
        
        
        return geneName;
    }
    
    
    
    public String[] getAccession(){  //retrieve list of accession numbers for isomers in reference file
        
        String[] accessionNumber = null;
    
    
        return accessionNumber; 
    }

    public String getLargest(){  //compares all isomers and returns the accession of the largest
        
        String largestIsomer = null;
        
        
        
        
        return largestIsomer;
        
        
    }




}
