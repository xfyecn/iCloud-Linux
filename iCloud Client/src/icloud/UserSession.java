package icloud;

import icloud.session.json.SessionBody;

import java.net.HttpCookie;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.apache.http.nio.entity.NStringEntity;

import apps.note.NoteSessionData;

import com.google.gson.Gson;
import com.mashape.unirest.http.Headers;
import com.mashape.unirest.http.HttpResponse;

public class UserSession {

	private final Credentials authTokens;
	private final UUID sessionKey;
	private final HashMap<SessionConfKeys, String> sessionConfig = new HashMap<SessionConfKeys, String>();

	private NoteSessionData nData = null;
	
	protected UserSession(String buildNum, Credentials authKeys,
			HttpResponse<String> authResponse, UUID sessionKey) {
		authTokens = authKeys;
		sessionConfig.put(SessionConfKeys.buildNumber, buildNum);
		this.sessionKey = sessionKey;
		parseBody(authResponse.getBody());
		makeCookies(authResponse.getHeaders());

	}

	private synchronized void makeCookies(Headers headers) {
		for (String str : headers.keySet()) {
			if (str.equalsIgnoreCase("set-cookie")) {
				List<String> cookies = headers.get(str);
				Set<HttpCookie> cookieSet = new HashSet<HttpCookie>();
				for (String cookieA : cookies) {
					for (HttpCookie cookieB : HttpCookie.parse(cookieA)) {
						cookieSet.add(cookieB);
					}
				}
				authTokens.updateTokens(cookieSet);
				// HttpCookie.parse(header);
				/*
				 * for(String cookie : cookies){ HttpCookie cookie2; int index2
				 * = 0; splitLoop: for(int index = 0; index < cookie.length();
				 * index++){ if(String.valueOf(cookie.charAt(index)) == ";"){
				 * String[] var = cookie.substring(0, index).split("="); //
				 * Split the cookie name and its value index2 = index; cookie2 =
				 * new HttpCookie(var[0], var[1]); break splitLoop; } } String[]
				 * cookiePeices = cookie.substring(index2, cookie.length() -
				 * 1).split(";"); for (String cookieChip : cookiePeices){
				 * if(cookieChip.contains("HttpOnly")){
				 * cookie2.setDomain(pattern); cookie2.setHttpOnly(httpOnly);
				 * cookie2.setMaxAge(expiry); cookie2.p } else
				 * if(cookieChip.contains("")){
				 * 
				 * } else if(cookieChip.contains("")){
				 * 
				 * } else if(cookieChip.contains("")){
				 * 
				 * } cookie2.setSecure(flag); } }
				 */
			}
		}
	}

	private synchronized void parseBody(String body) {
		SessionBody theData = new Gson().fromJson(body, SessionBody.class);
		this.sessionConfig.put(SessionConfKeys.dsinfo_appleId,
				theData.dsInfo.getAppleId());
		this.sessionConfig.put(SessionConfKeys.dsinfo_appleidAlias,
				theData.dsInfo.getAppleIdAlias());
		this.sessionConfig.put(SessionConfKeys.dsinfo_dsid,
				theData.dsInfo.getDsid());
		this.sessionConfig.put(SessionConfKeys.dsinfo_firstName,
				theData.dsInfo.getFirstName());
		this.sessionConfig.put(SessionConfKeys.dsinfo_fullName,
				theData.dsInfo.getFullName());
		this.sessionConfig.put(SessionConfKeys.dsinfo_languageCode,
				theData.dsInfo.getLanguageCode());
		this.sessionConfig.put(SessionConfKeys.dsinfo_lastName,
				theData.dsInfo.getLastName());
		this.sessionConfig.put(SessionConfKeys.dsinfo_locale,
				theData.dsInfo.getLocale());
		this.sessionConfig.put(SessionConfKeys.hasMinimumDeviceForPhotosWeb,
				theData.hasMinimumDeviceForPhotosWeb().toString());
		this.sessionConfig.put(SessionConfKeys.isExtendedLogin, theData
				.isExtendedLogin().toString());
		this.sessionConfig.put(SessionConfKeys.requestInfo_country,
				theData.requestInfo.getCountry());
		this.sessionConfig.put(SessionConfKeys.requestInfo_region,
				theData.requestInfo.getRegion());
		this.sessionConfig.put(SessionConfKeys.requestInfo_timeZone,
				theData.requestInfo.getTimeZone());
	}

	protected HashMap<SessionConfKeys, String> getSessionConfig() {
		return sessionConfig;
	}

	public String getUsername() {
		return authTokens.getUsername();
	}

	public String getPassword() {
		return authTokens.getPassword();
	}

	public boolean isExtendedLogin() {
		return authTokens.isExtendedLogin();
	}

	public Credentials getCredentials() {
		return authTokens;
	}

	public String getClientBuildNumber() {
		return sessionConfig.get(SessionConfKeys.session_clientBuildNumber)
				.toString();
	}

	public UUID getSessionID() {
		return sessionKey;
	}

	
	public synchronized SessionData getNoteSessionData() {
		if (nData == null){
			nData = new NoteSessionData(sessionConfig, authTokens, sessionKey);
		}
		return nData;
	}

	
}
