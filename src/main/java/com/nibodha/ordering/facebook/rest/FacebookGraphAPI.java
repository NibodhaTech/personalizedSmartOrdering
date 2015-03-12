package com.nibodha.ordering.facebook.rest;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.http.HttpStatus;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import com.nibodha.ordering.exception.CannotAccessFacebookException;
import com.nibodha.ordering.facebook.service.FacebookService;
import com.nibodha.ordering.facebook.service.FacebookServiceImpl;
import com.nibodha.ordering.model.OAuthAccessTokenParams;

@Path("/user")
public class FacebookGraphAPI {

	//@Value("${FACEBOOK_APP_ID}")
	private String appId="1561590967417058";

	//@Value("${FACEBOOK_APP_SECRET}")
	private String appSecret="93e1b1bcbd240e08f50c4c174e4c28c7";

	//@Value("${FACEBOOK_ACCESS_TOKEN_URL}")
	private String accessTokenURL="https://graph.facebook.com/oauth/access_token";
	
	@Autowired
	private FacebookService facebookService;

	private static final Logger LOG = Logger.getLogger(FacebookGraphAPI.class);

	@POST
	@Path("/long-lived-token")
	@Consumes(MediaType.TEXT_PLAIN)
	public Response generateLongLiveToken(String accessToken) throws CannotAccessFacebookException {
		LOG.info("AccessToken recieved");
		LOG.info("AccessToken: " + accessToken);
		FacebookService facebookService = new FacebookServiceImpl();
		OAuthAccessTokenParams params = new OAuthAccessTokenParams();
		params.setClientId(appId);
		params.setClientSecret(appSecret);
		params.setGrantType("fb_exchange_token");
		params.setRefreshToken(accessToken);
		params.setRequestURL(accessTokenURL);
		String longLiveToken = facebookService.fetchAccessToken(params);
		LOG.info("LongLiveToken: " + longLiveToken);
		writeToken(longLiveToken);
		return Response.ok("Long live token successfully generated").build();
	}

	@GET
	@Path("/likes")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getUserLikes() throws CannotAccessFacebookException {
		final String longLiveToken = readToken();
		if (StringUtils.isEmpty(longLiveToken)) {
			return Response.status(HttpStatus.SC_UNAUTHORIZED).build();
		}
		OAuthAccessTokenParams params = new OAuthAccessTokenParams();
		params.setClientId(appId);
		params.setClientSecret(appSecret);
		params.setGrantType("fb_exchange_token");
		params.setRequestURL(accessTokenURL);
		params.setRefreshToken(longLiveToken);
		String likes = facebookService.getUserLikes(params);
		LOG.info("Likes: " + likes);
		return Response.ok(likes).build();
	}

	private String getTokenFilePath() {
		String tokenFIlePath = new File("").getAbsolutePath()
				+ "/src/main/resources/facebook_longlive_token";
		if (!new File(tokenFIlePath).exists()) {
			try {
				new File(tokenFIlePath).createNewFile();
			} catch (IOException e) {
				LOG.error("Token file not exist. Getting error while creating so."
						+ e.getMessage());
			}
		}
		return tokenFIlePath;
	}

	private void writeToken(String token) {
		BufferedWriter bw = null;
		try {
			bw = new BufferedWriter(new FileWriter(getTokenFilePath()));
			bw.write(token);
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				bw.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private String readToken() {
		StringBuilder token = new StringBuilder();
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(getTokenFilePath()));
			String sCurrentLine;
			while ((sCurrentLine = br.readLine()) != null) {
				token.append(sCurrentLine);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				br.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return token.toString();
	}
}
