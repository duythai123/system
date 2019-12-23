package capstone_project.av_service.utils;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContextBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;

import com.google.common.io.ByteStreams;
import com.google.gson.Gson;

public class IntegrationUtil {
	private static final String AUTHORIZATION = "authorization";
    private static Gson gson = new Gson();

	

	public static ResponseEntity<?> callHttpPostWithFile(String apiUrl, File initialFile, String jwtToken)
			throws Exception {
		
		// Init method HttpPost
		HttpPost httpPost = new HttpPost(apiUrl);
		
		// Check cuckoo's authen code by json web token
		if (jwtToken != null) {
			httpPost.addHeader(AUTHORIZATION, jwtToken);
		}

		// convert object data to object json
		if (initialFile != null) {
			// add file into httpRequest
			HttpEntity httpEntity = MultipartEntityBuilder.create().addPart("file", new FileBody(initialFile)).build();
//			input.setContentType("application/json");
			httpPost.setEntity(httpEntity);
		}

		// init HttpClient to receive response request
		HttpClient httpClient = createHttpClient(null);

		HttpResponse httResponse = httpClient.execute(httpPost);
		
		// Check reponse request and status
		return checkResponse(httResponse);
	}
	

	public static ResponseEntity<?> callHttpGet(String apiUrl, Map<String, String> params, String jwtToken)
			throws Exception {

		// Init http Get method
		HttpGet httpGet = new HttpGet(apiUrl);

		if (jwtToken != null) {
			httpGet.addHeader(AUTHORIZATION, jwtToken);
		}
		URIBuilder uriBuilder = new URIBuilder(httpGet.getURI());

		// set parameters
		if (params != null && !params.isEmpty()) {
			for (String key : params.keySet()) {
				uriBuilder.addParameter(key, params.get(key));
			}
		}

		URI uri = uriBuilder.build();
		((HttpRequestBase) httpGet).setURI(uri);

		HttpClient httpClient = createHttpClient(null);

		HttpResponse httpResponse = httpClient.execute(httpGet);

		return checkResponse(httpResponse);
	}




	public static ResponseEntity<?> checkResponse(HttpResponse response) throws IOException {

		String _jsonRes;

		InputStream content = response.getEntity().getContent();
		//
		try {
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			ByteStreams.copy(content, out);
			// Convert content into json String
			_jsonRes = out.toString("UTF-8");
		} finally {
			content.close();
		}

		return new ResponseEntity<>(_jsonRes, HttpStatus.valueOf(response.getStatusLine().getStatusCode()));
	}

	public static CloseableHttpClient createHttpClient(CookieStore cookieStore) throws Exception {
		SSLContextBuilder builder = new SSLContextBuilder();
	    builder.loadTrustMaterial(null, new TrustSelfSignedStrategy());
	    SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
	            builder.build(), SSLConnectionSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
	    CloseableHttpClient httpclient = HttpClients.custom().setSSLSocketFactory(
	            sslsf).build();
	    if (cookieStore != null) {
	    	httpclient = HttpClients.custom().setSSLSocketFactory(
		            sslsf).setDefaultCookieStore(cookieStore).build();
		}
	    return httpclient;
	}
	
}