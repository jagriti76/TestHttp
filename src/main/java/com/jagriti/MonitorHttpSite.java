package com.jagriti;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpException;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.AuthenticationException;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.config.AuthSchemes;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.config.RequestConfig.Builder;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.cookie.Cookie;
import org.apache.http.entity.BasicHttpEntity;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.apache.http.message.BasicHeader;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Timer;
import java.util.TimerTask;

public class MonitorHttpSite {

	private static int statuscode;
	private static CloseableHttpClient client = null;
	private static HttpClientBuilder builder = HttpClients.custom();
	private static CredentialsProvider provider = new BasicCredentialsProvider();
	private static String urlString;
	private static boolean failOnHttpStatus = true;
	private static int followRedirects = 0;
	private static boolean redirected;
	private static String postString;
	private static String authentication;
	private static boolean followRedirectOnPost;
	private static final int R_FOLLOW = 3;
	private static String output;
	private static String cookiesToSend;
	private static Cookie[] cookies;
	private static String agentIdentifier;
	private static String virtualHost;
	private static String agentHeader;
	private static String stringToVerify;
	private static int timeInterval = 30;

	public static void main(String args[]) {
		String configurationFile = args[0];

		Properties prop = new Properties();
		InputStream input;
		try {
			input = new FileInputStream(configurationFile);
			prop.load(input);
			urlString = prop.getProperty("urlString");
			authentication = prop.getProperty("authentication");
			cookiesToSend = prop.getProperty("cookiesToSend");
			postString = prop.getProperty("postString");
			agentIdentifier = prop.getProperty("agentIdentifier");
			stringToVerify = prop.getProperty("stringToVerify");

		} catch (FileNotFoundException e1) {
			System.out.println("Unable to read property file from ::: " + configurationFile);
			e1.printStackTrace();
		} catch (IOException e) {
			System.out.println("Exception occurred while reading property file ::: " + e);
			e.printStackTrace();
		}

		TimerTask task = new TimerTask() {
			@Override
			public void run() {
				try {
					String output = checkHttp(urlString);
					if (output.equals(stringToVerify)) {
						System.out.println("Site is Up!!!! ::: ");
					}
				} catch (HttpException e) {
					e.printStackTrace();
					System.out.println("HttpException occured ::: " + e);
				} catch (IOException e) {
					e.printStackTrace();
					System.out.println("IOException occured ::: " + e);
				} catch (Exception e) {
					e.printStackTrace();
					System.out.println("Exception occured ::: " + e);
				}

			}
		};

		Timer timer = new Timer();
		long delay = 0;
		long intevalPeriod = timeInterval * 60 * 1000;

		// schedules the task to be run in an interval
		timer.scheduleAtFixedRate(task, delay, intevalPeriod);
	}

	public static String buildUrl(boolean useSsl, String hostname, int port, String string_to_send) {
		String protocol = getProtocol(useSsl);

		return protocol + "://" + hostname + ":" + port + string_to_send;
	}

	private static String getProtocol(boolean useSsl) {
		String protocol = "http";
		if (useSsl) {
			protocol = "https";
		}
		return protocol;
	}

	public static String checkHttp(String urlString) throws HttpException,
			IOException {
		URL url = new URL(urlString);
		configureClientBuilder();
		HttpUriRequest request = null;
		try {
			request = configureRequest(urlString);
			configureAuthentication(url.getHost(), url.getPort(), request, authentication, urlString);

			builder.setDefaultCredentialsProvider(provider);

			client = builder.build();

			HttpResponse response = client.execute(request);

			System.out.println(request.getMethod()
					+ " Call reply Recieved from Host: "	+ request.getURI().getHost() + " at Port: " + request.getURI().getPort());

			statuscode = response.getStatusLine().getStatusCode();
			checkStatusCode(statuscode);

			if ((statuscode == HttpStatus.SC_MOVED_TEMPORARILY)
					|| (statuscode == HttpStatus.SC_MOVED_PERMANENTLY)
					|| (statuscode == HttpStatus.SC_SEE_OTHER)
					|| (statuscode == HttpStatus.SC_TEMPORARY_REDIRECT)) {
				redirected = true;
				if (followRedirectOnPost || followRedirects == R_FOLLOW) {
					Header header = response.getFirstHeader("location");
					if (header == null) {
						System.out.println("Invalid redirect while following " 	+ urlString);
						return "";
					}
					followRedirect(url.getHost(), url.getPort(), urlString,
							header, request);
				}
			} else {
				output = readBodyAsStream(response.getEntity());
			}
		} finally {
			if (request != null) {
				request.abort();
			}
		}
		return output;
	}

	public static String readBodyAsStream(HttpEntity get) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(get.getContent()));
		StringBuilder resultBuffer = new StringBuilder();
		String line = null;
		while ((line = reader.readLine()) != null) {
			resultBuffer.append(line);
		}
		reader.close();
		return resultBuffer.toString();
	}

	public static String checkHttp(boolean useSsl, String hostname, int port, String string_to_send) throws HttpException, IOException {
		String url = buildUrl(useSsl, hostname, port, string_to_send);
		return checkHttp(url);
	}

	private static void configureClientBuilder() {
		configureCookiesAndTimeouts();
		addCookiesToSend();
	}

	private static void checkStatusCode(int statuscode) throws HttpException {
		if (failOnHttpStatus && statuscode >= 400 && statuscode < 600 || (statuscode == 502)) {
			if (statuscode == HttpStatus.SC_UNAUTHORIZED) {
				throw new AuthenticationException();
			}
			throw new HttpException("Invalid HTTP status code: " + statuscode);
		}
	}

	private static void configureAuthentication(String hostname, int port,
			HttpUriRequest request, String authentication, String urlString) {
		if (!isEmpty(authentication)) {
			String[] usr = authentication.split(":");
			String userName = (usr.length >= 1) ? usr[0] : "";
			String password = (usr.length >= 2) ? usr[1] : "";
			provider.setCredentials(AuthScope.ANY,
					new UsernamePasswordCredentials(userName, password));

		}
	}

	private static void followRedirect(String hostname, int oldPort,
			String urlString, Header header, HttpUriRequest request)
			throws HttpException, IOException {
		String newuri = header.getValue();
		if ((newuri == null) || (newuri.equals(""))) {
			newuri = "/";
		}
		// -- we actually have to figure out if we're being
		// sent to a different host
		// -- release the connection, we're now trying a
		// different get, it retains the wrong info

		// -- now we have to parse the URI to see if we get
		// a new host, port, and protocol
		StringBuffer reDirectLog = new StringBuffer("Redirecting: " + hostname
				+ ":" + oldPort + " ----> ");
		URL url = null;
		try {
			url = new URL(newuri);
		} catch (MalformedURLException e) {
			url = new URL(urlString);
			url = new URL(url, newuri);
		}

		String protocol = url.getProtocol();
		boolean useSsl = "https".equals(protocol);
		int port = getPort(url.getPort(), useSsl);

		if (!url.getHost().equals("")) {
			hostname = url.getHost();
		}
		newuri = url.getPath();
		if ("".equals(newuri)) {
			newuri = "/";
		}

		String query = url.getQuery();
		if (query != null) {
			newuri += "?" + query;
		}
		reDirectLog.append(hostname + ":" + port);
		System.out.println(reDirectLog);

		checkHttp(useSsl, hostname, port, newuri);
	}

	private static int getPort(int portFromUrl, boolean useSsl) {
		if (portFromUrl > 0) {
			return portFromUrl;
		}
		if (useSsl) {
			return 443;
		}
		return 80;
	}

	private static boolean isEmpty(String string) {
		return string == null || string.isEmpty();
	}

	private static void configureCookiesAndTimeouts() {
		RequestConfig.Builder requestConfigBuilder = RequestConfig.custom();
		requestConfigBuilder.setCookieSpec(CookieSpecs.DEFAULT)
				.setConnectTimeout(60000).setSocketTimeout(60000);
		requestConfigBuilder.setRedirectsEnabled(false);
		setClientProxyParams(requestConfigBuilder);
		builder.setDefaultRequestConfig(requestConfigBuilder.build());
	}

	private static void setClientProxyParams(Builder requestConfigBuilder) {
		List<String> authPrefs = new ArrayList<String>();
		authPrefs.add(AuthSchemes.BASIC);
		requestConfigBuilder.setProxyPreferredAuthSchemes(authPrefs);
	}

	private static void addCookiesToSend() {
		// -- now, we add in our cookies
		Cookie[] newcookies = null;
		cookiesToSend = null;
		boolean areCookiesToAdd = false;
		if (!isEmpty(cookiesToSend)) {
			String[] cook = cookiesToSend.split(";");
			newcookies = new Cookie[cook.length];
			for (int i = 0; i < cook.length; i++) {
				String[] c = cook[i].split(":");
				BasicClientCookie cookie = new BasicClientCookie(c[1], c[2]);
				cookie.setDomain(c[0]);
				cookie.setPath(c[3]);
				cookie.setExpiryDate(null);
				cookie.setSecure(false);
				newcookies[i] = cookie;
			}
			areCookiesToAdd = true;
		}

		if (cookies != null) {
			areCookiesToAdd = true;
		}

		if (areCookiesToAdd) {
			BasicCookieStore store = new BasicCookieStore();
			if (cookies != null) {
				store.addCookies(cookies);
			}
			if (newcookies != null) {
				store.addCookies(newcookies);
			}
			builder.setDefaultCookieStore(store);
		}
	}

	private static HttpUriRequest configureRequest(String urlStr)
			throws IOException {
		HttpUriRequest request = buildRequest(urlStr);
		if (isEmpty(agentIdentifier)) {
			request.setHeader("User-Agent",	"Test");
		} else {
			request.setHeader("User-Agent", agentIdentifier);
		}
		if (!isEmpty(virtualHost)) {
			request.addHeader("Host", virtualHost);
		} else {
			URL url = new URL(urlStr);
			String host = url.getHost();
			request.addHeader("Host", host);
		}
		addHeaders(request);
		return request;
	}

	private static void addHeaders(HttpUriRequest request) {
		if (!isEmpty(agentHeader)) {
			List<HeaderItem> items = parse(agentHeader);

			for (HeaderItem item : items) {
				String name = item.getName();
				request.removeHeaders(name);
				request.addHeader(name, item.getValue());

			}
		}

	}

	public static List<HeaderItem> parse(String header) {
		ArrayList<HeaderItem> result = new ArrayList<HeaderItem>();

		String[] h = header.split("\n");
		for (int i = 0; i < h.length; i++) {
			String line = h[i];
			int divider = line.indexOf(':');
			String name = line.substring(0, divider);
			String value = line.substring(divider + 1);

			result.add(new HeaderItem(name.trim(), value.trim()));

		}
		return result;
	}

	private static HttpUriRequest buildRequest(String urlStr)
			throws UnsupportedEncodingException {
		HttpUriRequest request = null;
		if (redirected) {
			request = new HttpGet(urlStr);
		} else {
			if (isEmpty(postString)) {
				request = new HttpGet(urlStr);
			} else {
				HttpPost post = new HttpPost(urlStr);
				byte[] bytes = postString.getBytes("US-ASCII");
				InputStream content = new ByteArrayInputStream(bytes);
				BasicHttpEntity httpentity = new BasicHttpEntity();
				httpentity.setContent(content);
				httpentity.setContentLength(bytes.length);
				httpentity.setContentType(new BasicHeader("Content-Type",
						"application/x-www-form-urlencoded"));
				post.setEntity(httpentity);
				request = post;
			}
		}
		return request;
	}

}
