package com.nibodha.ordering.facebook.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.nibodha.ordering.exception.CannotAccessFacebookException;
import com.nibodha.ordering.model.OAuthAccessTokenParams;

@Service
public class FacebookServiceImpl implements FacebookService {

	private static final Logger LOG = Logger
			.getLogger(FacebookServiceImpl.class);

	@Override
	public String fetchAccessToken(OAuthAccessTokenParams params)
			throws CannotAccessFacebookException {
		String result = null;
		String url = params.getRequestURL() + "?grant_type="
				+ params.getGrantType() + "&client_id=" + params.getClientId()
				+ "&client_secret=" + params.getClientSecret()
				+ "&fb_exchange_token=" + params.getRefreshToken();
		LOG.info("URL: " + url);
		HttpClient client = HttpClientBuilder.create().build();
		HttpGet request = new HttpGet(url);
		try {
			StringBuffer resultBuffer = new StringBuffer();
			String line = "";
			HttpResponse response = client.execute(request);
			BufferedReader br = new BufferedReader(new InputStreamReader(
					response.getEntity().getContent()));
			while ((line = br.readLine()) != null) {
				resultBuffer.append(line);
			}
			LOG.info("Response obtained: " + resultBuffer);
			result = resultBuffer.substring(resultBuffer.indexOf("=") + 1,
					resultBuffer.indexOf("&expires"));
			LOG.info("ResultToken: " + result);
		} catch (IOException e) {
			throw new CannotAccessFacebookException(
					"Unable to contact facebook", e);
		}
		return result;
	}

	@Override
	public String getUserLikes(OAuthAccessTokenParams params)
			throws CannotAccessFacebookException {
		String result = null;
		String accessToken = fetchAccessToken(params);
		String url = "https://graph.facebook.com/me/likes?key=value&access_token="
				+ accessToken;
		HttpClient client = HttpClientBuilder.create().build();
		HttpGet request = new HttpGet(url);
		try {
			StringBuffer resultBuffer = new StringBuffer();
			String line = "";
			HttpResponse response = client.execute(request);
			BufferedReader br = new BufferedReader(new InputStreamReader(
					response.getEntity().getContent()));
			while ((line = br.readLine()) != null) {
				resultBuffer.append(line);
			}
			LOG.info("Response obtained: " + resultBuffer);
			result = resultBuffer.toString();
			LOG.info("Likes: " + result);
		} catch (IOException e) {
			e.printStackTrace();
			throw new CannotAccessFacebookException(
					"Unable to contact facebook");
		}
		return result;
	}
}
