package com.recommendationengine;

import java.io.File;
import java.io.IOException;

import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.impl.similarity.LogLikelihoodSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;

public class LikelyhoodSimilariy 
{
	public static String dataSet="C:/Users/win10/eclipse-workspace/LikelyhoodSimilarity/src/main/java/com/recommendationengine/Products.csv";
    public static void main(String[] args) throws IOException, TasteException
    {
        //dataSet columns description User Id, Product Id, Rating (5 being highest and 1 being lowest) 
        
        DataModel model= new FileDataModel(new File(dataSet));
        
        LogLikelihoodSimilarity similarity = new LogLikelihoodSimilarity(model);
        
        System.out.println("Similarity between User 1 and User 3 is " + similarity.userSimilarity(1, 3));
    }
}
