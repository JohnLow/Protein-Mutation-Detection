/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package FileIteration;

import java.util.Iterator;
import org.biojava.bio.BioException;
import org.biojava.bio.seq.Feature;
import org.biojava.bio.seq.FeatureFilter;
import org.biojava.bio.seq.FeatureHolder;
import org.biojava.bio.symbol.SymbolList;
import org.biojavax.RichAnnotation;
import org.biojavax.RichObjectFactory;
import org.biojavax.bio.db.ncbi.GenbankRichSequenceDB;
import org.biojavax.bio.seq.RichFeature;
import org.biojavax.bio.seq.RichSequence;
import org.biojavax.ontology.ComparableTerm;

/**
 *
 * @author ipurusho
 */
public class ExonData {
    
    
    
    private FeatureFilter ff;
    private FeatureHolder fh;
    private RichFeature rf;
    private RichAnnotation ra;
    
    private String featureLocation;
   private String[] geneRange = null;
   private int[] geneIndexes = null;
  
     public  int getEntrySize(RichSequence rs){
              
               int entrySize = 0;
             //Filter the sequence on CDS features
             
             ff = new FeatureFilter.ByType("exon");
             fh = rs.filter(ff);
                
                //Iterate through the CDS features
                for (Iterator <Feature> is = fh.features(); is.hasNext();){
                    
                        rf = (RichFeature)is.next();
                        //Get the annotation of the feature
                        ra = (RichAnnotation)rf.getAnnotation();
                        featureLocation = rf.getLocation().toString(); //Get the location of the feature
                        entrySize = entrySize+1;
                        }
                 
                 return entrySize;
                 
    }
     
     
    public String[] getExonRange(RichSequence rs){
    
     geneRange = new String[this.getEntrySize(rs)];
     ff = new FeatureFilter.ByType("exon");
             fh = rs.filter(ff);
                int i = 0;
                //Iterate through the CDS features
                for (Iterator <Feature> is = fh.features(); is.hasNext();){
                    
                        rf = (RichFeature)is.next();
                        //Get the annotation of the feature
                        ra = (RichAnnotation)rf.getAnnotation();
                        featureLocation = rf.getLocation().toString();
                        geneRange[i] = featureLocation;
                        i++;
                
                }
    return geneRange;
     
   }
    
      public int[] getStartIndexes(RichSequence rs){
        
        
        getExonRange(rs);
        geneIndexes = new int[this.getEntrySize(rs)]; 
        
        String delimeter ="[\\.\\.\\>\\<]";//"[\\>\\<\\.\\.]";
        String[] useableIndex = new String[3];
        
       
       try{
        for (int i = 0; i < this.getEntrySize(rs); i++){
          
            String[] parsedString = new String[15];
            parsedString = geneRange[i].split(delimeter);
            int k = 0;
            for (int j = 0; j < parsedString.length ; j++){
                
                if(parsedString[j].equals("")){}
                else{
                    useableIndex[k] = parsedString[j];
                    k++;
                 }
                  
            }
            geneIndexes[i] = Integer.parseInt(useableIndex[0]);
            
            
        }
      }catch(Exception e){
          System.out.println("Gene Locations are not in proper format");
      }
      
        return geneIndexes;
    }
    
    
    
    public int[] getEndIndexes(RichSequence rs){
        
        
        getExonRange(rs);
        geneIndexes = new int[this.getEntrySize(rs)]; 
        
        String delimeter ="[\\.\\.\\>\\<]";//"[\\>\\<\\.\\.]";
        String[] useableIndex = new String[2];
        
        
       try{
        for (int i = 0; i < this.getEntrySize(rs); i++){
          
            String[] parsedString = geneRange[i].split(delimeter);
            int k = 0;
            for (int j = 0; j < parsedString.length ; j++){
                
                if(parsedString[j].equals("")){}
                else{
                    useableIndex[k] = parsedString[j];
                    k++;
                 }
                  
            }
            geneIndexes[i] = Integer.parseInt(useableIndex[1]);
            
            
        }
      }catch(Exception e){
          
          System.out.println("Gene Locations are not in proper format");
      }
        
        return geneIndexes;
        
        
    }
    
 
  public String getExons(String isomerAccession) throws BioException{
        
        String nucSeq ="";
        String tempExonSeq = null;
        SymbolList sl;
        RichSequence rs = null;
        GenbankRichSequenceDB grsdb = new GenbankRichSequenceDB();
        
        rs = grsdb.getRichSequence(isomerAccession);
        sl = rs.getInternalSymbolList(); 
        this.getExonRange(rs);
        for (int i =0; i < this.getEntrySize(rs); i++){
             
              tempExonSeq = sl.subStr(getStartIndexes(rs)[i], getEndIndexes(rs)[i]);
              nucSeq = nucSeq+ tempExonSeq;
              i++;
       }
       
        
        return nucSeq;
    }
    
    
    
    
}
