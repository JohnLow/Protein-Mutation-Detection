/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package FileIteration;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Iterator;
import org.biojava.bio.BioException;
import org.biojava.bio.seq.Feature;
import org.biojava.bio.seq.FeatureFilter;
import org.biojava.bio.seq.FeatureHolder;
import org.biojava.bio.symbol.SymbolList;
import org.biojavax.Note;
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
    
    
       
    public String[] getCDSRange(RichSequence rs){
    
     geneRange = new String[1];
     ff = new FeatureFilter.ByType("CDS");
             fh = rs.filter(ff);
                int i = 0;
                //Iterate through the CDS features
                for (Iterator <Feature> is = fh.features(); is.hasNext();){
                    
                        rf = (RichFeature)is.next();
                        //Get the annotation of the feature
                        ra = (RichAnnotation)rf.getAnnotation();
                        featureLocation = rf.getLocation().toString();
                        geneRange[0]=featureLocation;                       
                        i++;
                
                }
    return geneRange;
     
   }
    
    /* ==============================================This has been modified!================================================
      public int[] getStartIndexes(RichSequence rs){
        
        getExonRange(rs);
        geneIndexes = new int[this.getEntrySize(rs)]; 
        
        String delimeter ="[\\.\\.\\>\\<]";//"[\\>\\<\\.\\.]";
        String[] useableIndex = new String[3];
        
       
       try{
        for (int i = 0; i < this.getEntrySize(rs); i++){
          
            String[] parsedString = new String[15];
            parsedString = geneRange[i].split(delimeter);//geneRange[i].split(delimeter);
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
    */
    public int[] getStartIndexes(RichSequence rs, String[] exonInput ){
        
        
        
        geneIndexes = new int[this.getEntrySize(rs)]; 
        
        String delimeter ="[\\.\\.\\>\\<]";//"[\\>\\<\\.\\.]";
        String[] useableIndex = new String[3];
        
       
       try{
        for (int i = 0; i < this.getEntrySize(rs); i++){
          
            String[] parsedString = new String[15];
            parsedString = exonInput[i].split(delimeter);//geneRange[i].split(delimeter);
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
       //   System.out.println("Gene Locations are not in proper format");
      }
      
        return geneIndexes;
    }
    
    
    public int[] getEndIndexes(RichSequence rs,String[] exonInput){
        
        
        getExonRange(rs);
        geneIndexes = new int[this.getEntrySize(rs)]; 
        
        String delimeter ="[\\.\\.\\>\\<]";//"[\\>\\<\\.\\.]";
        String[] useableIndex = new String[2];
        
        
       try{
        for (int i = 0; i < this.getEntrySize(rs); i++){
          
            String[] parsedString = exonInput[i].split(delimeter);
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
          
        //  System.out.println("Gene Locations are not in proper format");
      }
        
        return geneIndexes;
        
        
    }
    
 
  public String getExons(String isomerAccession){
        
        String nucSeq ="";
        String tempExonSeq = null;
        SymbolList sl = null;
        RichSequence rs = null;
        GenbankRichSequenceDB grsdb = new GenbankRichSequenceDB();
        
        boolean loop = true;
        
        while(loop){
            
        try{
        rs = grsdb.getRichSequence(isomerAccession);
        sl = rs.getInternalSymbolList(); 
        loop = false;
        }
        catch(BioException e){
            
        }
        }
        
        this.getExonRange(rs);
        for (int i =0; i < this.getEntrySize(rs); i++){
             if(getStartIndexes(rs,this.getCDSRange(rs))[0] == 0 &&  getEndIndexes(rs,this.getCDSRange(rs))[0] == 0){
                 nucSeq = null;
             }if(this.getExonRange(rs).length ==0){
                 nucSeq = null;
             }
             else{
              tempExonSeq = sl.subStr(getStartIndexes(rs,this.getExonRange(rs))[i], getEndIndexes(rs,this.getExonRange(rs))[i]);
              nucSeq = nucSeq+ tempExonSeq;
             }
       }
       
        
        return nucSeq;
    }
    
    public String getCDS(String isomerAccession) {
        String nucSeq ="";
        String tempExonSeq = null;
        SymbolList sl = null;
        RichSequence rs = null;
        GenbankRichSequenceDB grsdb = new GenbankRichSequenceDB();
        
         boolean loop = true;
        
        while(loop){
            
        try{
        rs = grsdb.getRichSequence(isomerAccession);
        sl = rs.getInternalSymbolList(); 
        loop = false;
        }
        catch(BioException e){
            
        }
        }
     //   for (int i =0; i < this.getEntrySize(rs); i++){
        
//         if( getStartIndexes(rs,this.getCDSRange(rs))[0] == 0  &&  getEndIndexes(rs,this.getCDSRange(rs))[0] == 0 ){
  //   nucSeq = null;
  if(this.getExonRange(rs).length ==0){
                 nucSeq = null;
             }
  else if(getCDSRange(rs)[0]==(null)){
                 nucSeq = null;
             }
else{
             
              tempExonSeq = sl.subStr(getStartIndexes(rs,this.getCDSRange(rs))[0], getEndIndexes(rs,this.getCDSRange(rs))[0]);
              nucSeq = tempExonSeq;
         }    
     
        if(nucSeq == null){
           System.out.println("Not Avaliable");
      }
        return nucSeq;
    }
           public int absoluteMutationPosition(String str, String isomerAccession) throws FileNotFoundException, IOException, BioException{ //works as expected. Certain Strains have country annotation.
                
             //Filter the sequence on CDS features
             Mutation mutation = new Mutation();
             int mutationPosition = 0;          
             
             RichSequence rs = null;
             GenbankRichSequenceDB grsdb = new GenbankRichSequenceDB();
             
             
         
         boolean loop = true;
         
                 while(loop){
            
        try{
        rs = grsdb.getRichSequence(isomerAccession);
      loop = false;
        }
        catch(BioException e){
            
        }
        }
             
             int exon1 = mutation.exonIndex(str, isomerAccession)[0];
             if(exon1 == 0){
                 exon1 = 1;
                 
             }
            
            int[] yo = this.getStartIndexes(rs, this.getExonRange(rs));
             
                //Iterate through the CDS features
            //    for (Iterator <Feature> is = fh.features(); is.hasNext();){
                     //   ComparableTerm exonNumber = RichObjectFactory.getDefaultOntology().getOrCreateTerm("number");
                  //      rf = (RichFeature)is.next();
                        //Get the annotation of the feature
                  //    ra = (RichAnnotation)rf.getAnnotation();
                       
                        //Iterate through the notes in the annotation
                 //       for (Iterator <Note> it = ra.getNoteSet().iterator(); it.hasNext();){
                   //         Note note = it.next();
                            
                            //Check each note to see if it matches one of the required ComparableTerms
                  //          if(note.getTerm().equals(exonNumber)){
                 //         if(note.getValue().toString().equals(exon)){
                      //  try{ 
            
            
           
                          if(mutation.forwardOrReverse(isomerAccession).equals("+")){    
                              int absolutePos = this.getStartIndexes(rs, this.getExonRange(rs))[exon1-1];
                             mutationPosition =  absolutePos + mutation.exonIndex(str,isomerAccession)[1]-1;
                             
                          }
                          
                          if(mutation.forwardOrReverse(isomerAccession).equals("-")){ 
                               int absolutePos = this.getStartIndexes(rs, this.getExonRange(rs))[exon1-1];
                               mutationPosition =  absolutePos + mutation.exonIndex(str,isomerAccession)[1];
                          }
                    //    }
                       // catch(ArrayIndexOutOfBoundsException e){
                     //       System.out.println("Not Available");
                      //  }
                     //        System.out.println(mutationPosition);
                            // System.out.println(this.getExonRange(rs)[mutation.exonIndex(str, isomerAccession)[0]-1]);
                           
                    //      System.out.println(exon1 + " " + mutationPosition);
                          //   i++;
                        //    }
                          //  }
                                
                     //   }
          
            // }
  return mutationPosition;
          
 }

    
}
