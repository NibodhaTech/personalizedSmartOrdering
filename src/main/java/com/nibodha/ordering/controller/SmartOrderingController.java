package com.nibodha.ordering.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nibodha.ordering.model.Product;
import com.nibodha.ordering.model.ProductContext;
import com.nibodha.ordering.model.ProductContextWrapper;
import com.nibodha.ordering.model.ProductWrapper;
import com.nibodha.ordering.service.ISmartOrderingService;
import com.nibodha.ordering.util.SmartOrderingConstants;

/**
 * vr-pms: vacation rental property management systems
 * 
 * @author Nibodha [Mar 10, 2015:12:50:49 PM]
 * 
 */
@Controller
@RequestMapping(value = "/vr-pms")
public class SmartOrderingController {
	
	@Autowired
	private ISmartOrderingService smartOrderingService;
	
	private ProductWrapper productWrapper;

	@RequestMapping(value = "/smartorder", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	@ResponseBody
	public ProductContextWrapper sortProductUsingType(
			@RequestBody ProductWrapper productWrapper) {
		return sortProduct("", productWrapper);
	}

	@RequestMapping(value = "/smartorder/{type}", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	@ResponseBody
	public ProductContextWrapper sortProduct(
			@PathVariable("type") String sortOption,
			@RequestBody ProductWrapper productWrapper) {
		ProductContextWrapper productContextWrapper = null;
		List<Product> products = productWrapper.getProducts();
		if (products != null && !products.isEmpty()) {
			productContextWrapper = new ProductContextWrapper();
			List<ProductContext> productContexts = new ArrayList<ProductContext>();
			int genericAmenitiesCount = 0;
			if (!sortOption.equalsIgnoreCase(SmartOrderingConstants.WALKSCORE))
				genericAmenitiesCount = getGenericAmenitiesCount(products);
			for (Product product : products) {
				productContexts.add(buildProductContext(product,
						genericAmenitiesCount, sortOption));
			}
			Collections.sort(productContexts);
			productContextWrapper.setProducts(productContexts);
		}
		return productContextWrapper;
	}

	/**
	 * Personalized Search
	 * @param productWrapper
	 * @return
	 */
	@RequestMapping(value = "/personalizedorder", method = RequestMethod.POST, consumes = "application/json")
	@ResponseBody
	public String personalizesSorting(
			@RequestBody ProductWrapper productWrapper) {
		this.productWrapper = productWrapper;
		Set<String> amenitySet = new HashSet<>(100);
		for(Product product :productWrapper.getProducts()){
			amenitySet.addAll(product.getAmenities());
		}
		for(String amenity: amenitySet){
			System.out.println(amenity);
		}
		System.out.println("executing personalizesSorting()...");
		return "true";
	}

	private int getGenericAmenitiesCount(List<Product> products) {
		Set<String> amenities = new HashSet<String>();
		for (Product product : products) {
			amenities.addAll(product.getAmenities());
		}
		return amenities.size();
	}

	private ProductContext buildProductContext(Product product,
			int genericAmenitiesCount, String sortOption) {
		Double score = 0.0;
		if (sortOption.equalsIgnoreCase(SmartOrderingConstants.AMENITY))
			score = getAmenityScore(product, genericAmenitiesCount);
		else if (sortOption.equalsIgnoreCase(SmartOrderingConstants.WALKSCORE))
			score = getWalkScore(product);
		else
			score = getAmenityScore(product, genericAmenitiesCount)
					+ getWalkScore(product);
		ProductContext productContext = new ProductContext();
		productContext.setProductId(product.getProductId());
		productContext.setProductName(product.getProductName());
		productContext.setScore(score);
		return productContext;
	}

	private Double getWalkScore(Product product) {
		return smartOrderingService.getWalkScore(product);
	}

	private Double getAmenityScore(Product product, int genericAmenitiesCount) {
		return smartOrderingService.getAmenityScore(product,
				genericAmenitiesCount);
	}
}
