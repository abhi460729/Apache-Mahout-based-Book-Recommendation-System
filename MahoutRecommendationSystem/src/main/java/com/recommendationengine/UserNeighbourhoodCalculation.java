package com.recommendationengine;

import java.io.File;
import java.io.IOException;
import java.util.Collection;

import org.apache.mahout.cf.taste.common.Refreshable;
import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.impl.neighborhood.ThresholdUserNeighborhood;
import org.apache.mahout.cf.taste.impl.similarity.EuclideanDistanceSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood;

public class UserNeighbourhoodCalculation {
	public static String dataSet="C:/Users/win10/eclipse-workspace/LikelyhoodSimilarity/src/main/java/com/recommendationengine/Products.csv";
	public static void main(String[] args)throws IOException, TasteException {
		
		//dataSet columns description User Id, Product Id, Rating (5 being highest and 1 being lowest) 
        
        DataModel model= new FileDataModel(new File(dataSet));
        
        EuclideanDistanceSimilarity similarity = new EuclideanDistanceSimilarity(model);
        
        UserNeighborhood neighborhood= new ThresholdUserNeighborhood(0.5, similarity, model);
        
        long neighbors[]=neighborhood.getUserNeighborhood(1);
        
        System.out.println("Neighbours for User 1 are:");
        
        for (long l : neighbors) {
			System.out.println("User "+l);
		}
        
	}
}
