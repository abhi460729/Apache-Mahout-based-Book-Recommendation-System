package com.recommendationengine;

import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.eval.RecommenderBuilder;
import org.apache.mahout.cf.taste.impl.neighborhood.NearestNUserNeighborhood;
import org.apache.mahout.cf.taste.impl.recommender.GenericUserBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.EuclideanDistanceSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood;
import org.apache.mahout.cf.taste.recommender.Recommender;
import org.apache.mahout.cf.taste.recommender.UserBasedRecommender;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;

public class BookRecommender implements RecommenderBuilder{

	public Recommender buildRecommender(DataModel dataModel) throws TasteException {
		 UserSimilarity similarity= new EuclideanDistanceSimilarity(dataModel);
		 UserNeighborhood neighborhood=new NearestNUserNeighborhood(5, similarity, dataModel);
		 UserBasedRecommender recommender = new GenericUserBasedRecommender(dataModel, neighborhood, similarity);
		return recommender;
	}

}
