/**
 * 
 */
package com.nibodha.ordering.service;

import com.nibodha.ordering.model.Product;

/**
 * 
 * @author Nibodha [Mar 10, 2015:12:52:06 PM]
 * 
 */
public interface ISmartOrderingService {
	Double getWalkScore(Product product);

	Double getAmenityScore(Product product, int genericAmenitiesCount);
}
