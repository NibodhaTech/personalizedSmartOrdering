/**
 * 
 */
package com.nibodha.ordering.model;

import java.util.Arrays;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @author Nibodha [Mar 9, 2015:5:25:19 PM]
 * 
 */
@Document(collection = "preference_categories")
public class PreferenceCategory {

	@Id
	private int id;

	@Indexed
	private String categoryType;

	private String[] catagotyNames;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCategoryType() {
		return categoryType;
	}

	public void setCategoryType(String categoryType) {
		this.categoryType = categoryType;
	}

	public String[] getCatagotyNames() {
		return catagotyNames;
	}

	public void setCatagotyNames(String[] catagotyNames) {
		this.catagotyNames = catagotyNames;
	}

	/**
	 * @param categoryType
	 * @param catagotyNames
	 */
	public PreferenceCategory(int id, String categoryType, String[] catagotyNames) {
		super();
		this.id = id;
		this.categoryType = categoryType;
		this.catagotyNames = catagotyNames;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "PreferenceCategory [id=" + id + ", categoryType="
				+ categoryType + ", catagotyNames="
				+ Arrays.toString(catagotyNames) + "]";
	}

}
