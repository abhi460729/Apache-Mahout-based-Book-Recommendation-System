package com.recommendationengine;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.eval.RecommenderBuilder;
import org.apache.mahout.cf.taste.eval.RecommenderEvaluator;
import org.apache.mahout.cf.taste.impl.common.LongPrimitiveIterator;
import org.apache.mahout.cf.taste.impl.eval.AverageAbsoluteDifferenceRecommenderEvaluator;
import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.impl.neighborhood.NearestNUserNeighborhood;
import org.apache.mahout.cf.taste.impl.recommender.GenericUserBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.CityBlockSimilarity;
import org.apache.mahout.cf.taste.impl.similarity.EuclideanDistanceSimilarity;
import org.apache.mahout.cf.taste.impl.similarity.LogLikelihoodSimilarity;
import org.apache.mahout.cf.taste.impl.similarity.SpearmanCorrelationSimilarity;
import org.apache.mahout.cf.taste.impl.similarity.TanimotoCoefficientSimilarity;
import org.apache.mahout.cf.taste.impl.similarity.UncenteredCosineSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.model.Preference;
import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.recommender.Recommender;
import org.apache.mahout.cf.taste.recommender.UserBasedRecommender;
import org.apache.mahout.math.hadoop.similarity.cooccurrence.measures.PearsonCorrelationSimilarity;

public class RecommenderSystem {
	public static String dataSet="C:/Users/win10/eclipse-workspace/LikelyhoodSimilarity/src/main/java/com/recommendationengine/Products.csv";
	public static final int NEIGHBORHOOD_SIZE=5;
	public static DataModel model=null;
	public static CityBlockSimilarity similarity=null;
	public static UserNeighborhood neighborhood=null;
	public static UserBasedRecommender recommender=null;
	
	private static String[] books= {"To Kill a Mockingbird","Harry Potter and the Order of the Phoenix","Pride and Prejudice","Twilight","The Chronicles of Narnia","Romeo and Juliet","The Alchemist","Crime and Punishment","Dracula", "Game of Thrones"}; 
	
	private static String userNames[]= {"Abhishek","Raghu","Sunny","Suraj","Vikas","Robby","Ravinder","Varun","Sumit","Kushpinder","Aman","Nitin","Robin","Singh","Aditya","Rahul","Raman","Manu","Gaurav","Mani","Abhishek1","Raghu1","Sunny1","Suraj1","Vikas1","Robby1","Ravinder1","Varun1","Sumit1","Kushpinder1","Aman1","Nitin1","Robin1","Singh1","Aditya1","Rahul1","Raman1","Manu1","Gaurav1","Mani1"};
	
	
	
	public static void main(String[] args)throws IOException, TasteException{
		
	 model=new FileDataModel(new File(dataSet));
	 similarity = new CityBlockSimilarity(model);
	 neighborhood = new NearestNUserNeighborhood(NEIGHBORHOOD_SIZE, similarity, model);
	 recommender = new GenericUserBasedRecommender(model, neighborhood, similarity);
	 
	 List<RecommendedItem> recommendations = recommender.recommend(1, 5);
	 
	 System.out.println("Recommendations for customer " + userNames[0] + " are :");
	 
	 System.out.println("*************************************************************");
	 
	 /////////////////////////////////////////////////////////////////////////////
	 
	 /*System.out.println("Rated items:");
		for (Preference preference : model.getPreferencesFromUser(1)) {
			LongPrimitiveIterator itemISBN = model.getItemIDs();
			System.out.println("Item id: "
					+ itemISBN + " Value: " + preference.getValue());
		}

		System.out.println("\nRecommended items:");
		List<RecommendedItem> recommendations1 = recommender.recommend(1,5);
		for (RecommendedItem item : recommendations1) {
			int bookId = (int)item.getItemID();
			System.out.println("Item: " + books[bookId-1] + " | Item id: "
					+ bookId + " | Value: " + item.getValue());
		}*/
		////////////////////////////////////////////////////////////////////////////////
	 
	 
	 
	System.out.println("BookId\ttitle\t\testimated preference");
	 for (RecommendedItem recommendedItem : recommendations) {
		int bookId=(int)recommendedItem.getItemID();
		float estimatedPref = recommender.estimatePreference(1, bookId);
		System.out.println(bookId+" "+ books[bookId-1]+"\t"+estimatedPref);
	}
	 
	 
	 System.out.println("***************************************************");
	 long[] userIds=recommender.mostSimilarUserIDs(1, 5);
	 System.out.println("Most similar users for "+ userNames[0] +" are");
	 for (long id : userIds) {
		System.out.println(id+" "+userNames[(int)id-1]);
	}
	 
	 RecommenderEvaluator evaluator= new AverageAbsoluteDifferenceRecommenderEvaluator();
	 
	 RecommenderBuilder builder = new BookRecommender();
	 
	 double result=evaluator.evaluate(builder, null, model, 0.9, 1.0);
	 
	 System.out.println(result);
	 
	}
	
	

}
//Displaying five recommended books based on user's(User 1= Abhishek Verma) preference.
//Displaying users having similar taste of books as of User 1(=Abhishek Verma) 