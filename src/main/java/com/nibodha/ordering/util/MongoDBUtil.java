/**
 * 
 */
package com.nibodha.ordering.util;

import static org.springframework.data.mongodb.core.FindAndModifyOptions.options;
import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Update;

import com.nibodha.ordering.SpringMongoConfig;
import com.nibodha.ordering.model.Counter;
import com.nibodha.ordering.model.PreferenceCategory;

/**
 * 
 * @author Nibodha [Mar 9, 2015:5:06:34 PM]
 * 
 */
public class MongoDBUtil {

	@Autowired
	private static MongoOperations mongo;
	static {
		mongo = (MongoOperations) new AnnotationConfigApplicationContext(
				SpringMongoConfig.class).getBean("mongoTemplate");
	}

	private static int getNextSequence(String collectionName) {
		Counter counter = mongo.findAndModify(
				query(where("_id").is(collectionName)),
				new Update().inc("seq", 1), options().returnNew(true),
				Counter.class);
		if (counter == null) {
			return 1;
		}
		return counter.getSeq();
	}

	public static void insertSinglePreference(PreferenceCategory category) {
		//category.setId(getNextSequence("preference_categories"));
		mongo.save(category);
	}

	public static void insertListPreference(List<PreferenceCategory> collection) {
		Iterator<PreferenceCategory> iterator = collection.iterator();
		while (iterator.hasNext()) {
			PreferenceCategory preferenceCategory = (PreferenceCategory) iterator
					.next();
			//preferenceCategory.setId(getNextSequence("preference_categories"));
			mongo.save(preferenceCategory);
		}
	}

	public static List<PreferenceCategory> getAllCategory() {
		return mongo.findAll(PreferenceCategory.class);
	}
}
