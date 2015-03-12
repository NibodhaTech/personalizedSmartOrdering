package com.nibodha.ordering.facebook.service;

import com.nibodha.ordering.exception.CannotAccessFacebookException;
import com.nibodha.ordering.model.OAuthAccessTokenParams;

public interface FacebookService {	

	String fetchAccessToken(OAuthAccessTokenParams params) throws CannotAccessFacebookException;

	String getUserLikes(OAuthAccessTokenParams params) throws CannotAccessFacebookException;
	
}
