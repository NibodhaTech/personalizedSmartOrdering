package com.nibodha.ordering;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.nibodha.ordering.model.PreferenceCategory;
import com.nibodha.ordering.util.MongoDBUtil;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = PersonalizedSmartOrderingApplication.class)
public class PersonalizedSmartOrderingApplicationTests {

	@Test
	public void contextLoads() {

		PreferenceCategory enertainment = new PreferenceCategory(1,
				"Entertainment", new String[] { "movie", "TV Shows", "Music",
						"Actor/director", "Radio station", "Musician/Band",
						"Artist", "Entertainer", "RMA163", "RMP90" });

		PreferenceCategory education = new PreferenceCategory(2, "Education",
				new String[] { "Internet/software", "Education website",
						"Computers/internet website", "Computers/technology",
						"Book", "RMP90", "RMA123"});

		PreferenceCategory social = new PreferenceCategory(3, "Social",
				new String[] { "Media/news/publishing", "Book", "Author",
						"Politician", "RMA123" });

		PreferenceCategory health = new PreferenceCategory(4, "health",
				new String[] { "Health/wellness website",
						"Recreation/sports website", "PHY24", "REC2", "RMA41",
						"RST5" });

		PreferenceCategory lifestyle = new PreferenceCategory(5, "lifestyle",
				new String[] { "Product/service", "Interest", "Athlete",
						"Artist", "Games/toys", "ACC37", "FAC2", "REC2",
						"RST151" });

		PreferenceCategory food = new PreferenceCategory(6, "food",
				new String[] { "Local business", "RMA59", "RMA68", "RMA88",
						"RMA167" });

		final List<PreferenceCategory> categories = new ArrayList<>(10);
		categories.add(lifestyle);
		categories.add(health);
		categories.add(education);
		categories.add(enertainment);
		categories.add(food);
		categories.add(social);

		MongoDBUtil.insertListPreference(categories);
		System.out.println("done");

		List<PreferenceCategory> preferenceCategories = MongoDBUtil
				.getAllCategory();
		for (PreferenceCategory preferenceCategory : preferenceCategories) {
			System.out.println(preferenceCategory);
		}
	}

}
