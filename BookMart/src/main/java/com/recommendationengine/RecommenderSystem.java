package com.recommendationengine;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.eval.RecommenderBuilder;
import org.apache.mahout.cf.taste.eval.RecommenderEvaluator;
import org.apache.mahout.cf.taste.impl.eval.AverageAbsoluteDifferenceRecommenderEvaluator;
import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.impl.neighborhood.NearestNUserNeighborhood;
import org.apache.mahout.cf.taste.impl.recommender.GenericUserBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.EuclideanDistanceSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.recommender.Recommender;
import org.apache.mahout.cf.taste.recommender.UserBasedRecommender;
import bookmartapplication.main.entities.Book;
import bookmartapplication.main.entities.User;

public class RecommenderSystem {
	public static String dataSet="C:/Users/win10/eclipse-workspace/BookMart/src/main/java/com/recommendationengine/Products.csv";
	//public static String dataSet="/home/ubuntu/Products.csv";
	public static final int NEIGHBORHOOD_SIZE=5;
	public static DataModel model=null;
	public static EuclideanDistanceSimilarity similarity=null;
	public static UserNeighborhood neighborhood=null;
	public static UserBasedRecommender recommender=null;
	
	public static List<String> recommendBooks(List<Book> books,int id) throws IOException, TasteException{
		List<String> resultBook= new ArrayList<String>();
		String bookNames[]=books.stream().map(book->book.getName()).toArray(size -> new String[size]);;
		
		 model=new FileDataModel(new File(dataSet));
		 similarity = new EuclideanDistanceSimilarity(model);
		 neighborhood = new NearestNUserNeighborhood(NEIGHBORHOOD_SIZE, similarity, model);
		 recommender = new GenericUserBasedRecommender(model, neighborhood, similarity);
		 
		 List<RecommendedItem> recommendations = recommender.recommend(id, 5);
		 
		 System.out.println("BookId\ttitle\t\testimated preference");
		 // Displaying five recommended books based on user's(User 1= Abhishek Verma) preference.
		 for (RecommendedItem recommendedItem : recommendations) {
			int bookId=(int)recommendedItem.getItemID();
			float estimatedPref = recommender.estimatePreference(id, bookId);
			System.out.println(bookId+" "+ bookNames[bookId-1]+"\t"+estimatedPref);
			resultBook.add(bookNames[bookId-1]);
		}
		 
		return resultBook;
		}
	
	public static List<String> recommendUsers(List<User> users,int id) throws IOException, TasteException{
		List<String> resultUser = new ArrayList<String>();
		String userNames[]=users.stream().map(user->user.getName()).toArray(size -> new String[size]);
		
		 model=new FileDataModel(new File(dataSet));
		 similarity = new EuclideanDistanceSimilarity(model);
		 neighborhood = new NearestNUserNeighborhood(NEIGHBORHOOD_SIZE, similarity, model);
		 recommender = new GenericUserBasedRecommender(model, neighborhood, similarity);
		 

		 
		 //Displaying users having similar taste of books as of User 1(=Abhishek Verma) 
		 long[] userIds=recommender.mostSimilarUserIDs(id, 5);
		 //System.out.println("Most similar users for "+ userNames[id-1] +" are");
		 for (long ids : userIds) {
			System.out.println(ids+" "+userNames[(int)ids-1]);
			resultUser.add(userNames[(int)ids-1]);
		}
		 
		return resultUser;
		}
	
	public static void main(String[] args)throws IOException, TasteException{
		
	}
	

}
