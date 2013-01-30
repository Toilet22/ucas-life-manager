package com.calm.lifemanager;


import java.io.BufferedReader;  
import java.io.DataOutputStream;  
import java.io.File;  
import java.io.FileInputStream;  
import java.io.FileOutputStream;  
import java.io.IOException;  
import java.io.InputStream;  
import java.io.InputStreamReader;  
import java.io.OutputStream;  
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;  
import java.net.MalformedURLException;  
import java.net.URL;  
import java.net.URLEncoder;  
import java.util.ArrayList;  
import java.util.List;  
import java.util.Map;  
  
import org.apache.http.HttpEntity;  
import org.apache.http.HttpResponse;  
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;  
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CookieStore;
import org.apache.http.client.entity.UrlEncodedFormEntity;  
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;  
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.cookie.Cookie;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;  
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
  
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Environment;  
import android.util.Log;
import android.widget.Toast;

public class NetToolUtil {  
	private static final String TAG = "NetUtil";  
	
    private static final int TIMEOUT = 10000;// 10�� 
    
    // ����Cookie
    public static CookieStore cookieStore;
    public static String sessionValue;
    
    public static final String serverUrl = "http://daydayup-timemanager.rhcloud.com";
    
    public static final String timeTipUrl = serverUrl + "/tip/time";
    public static final String moodTipUrl = serverUrl + "/tip/mood";
    
    public static final String accountRegisterUrl = serverUrl + "/account/register";
    public static final String accountLoginUrl = serverUrl + "/account/login";
    public static final String accountLogoutUrl = serverUrl + "/account/logout";
    
    public static final String userProfilePullUrl = serverUrl + "/userdata/userprofile/pull";
    public static final String userProfilePushUrl = serverUrl + "/userdata/userprofile/push";
    
    public static final String userSettingsPullUrl = serverUrl + "/userdata/usersettings/pull";
    public static final String userSettingsPushUrl = serverUrl + "/userdata/usersettings/push";
    
    public static final String todolistPullUrl = serverUrl + "/userdata/todolist/pull";
    public static final String todolistPushUrl = serverUrl + "/userdata/todolist/push";
    
    public static final String todolistPushUrlJson = serverUrl + "/userdata/todolist/push/json";
    
    public static final String todolistPushUrlTestJson = serverUrl + "/test_json";
    
    public static final String wishlistPullUrl = serverUrl + "/userdata/wishlist/pull";
    public static final String wishlistPushUrl = serverUrl + "/userdata/wishlist/push";
    
    public static final String collectorPullUrl = serverUrl + "/userdata/collector/pull";
    public static final String collectorPushUrl = serverUrl + "/userdata/collector/push";
    
    public static final String recordPullUrl = serverUrl + "/userdata/record/pull";
    public static final String recordPushUrl = serverUrl + "/userdata/record/push";

    public static final String PUSH = "push";
    public static final String PULL = "pull"; 
    
    public static final int SUCCESS = 0;
    public static final int FAIL = 1;
    public static final int NOT_LOGIN = 2;
    public static final int ALREADY_LOGIN = 3;
    public static final int METHOD_ERROR = 4;
    
    public static final int USERNAME_EXISTED = 11;
    public static final int USER_PASSWORD_ERROR = 12;
    public static final int USERNAME_TOO_SHORT = 13;
    public static final int PASSWORD_TOO_SHROT = 14;
    
    public static final int SYNC_DATA = 20;
    public static final int SYNC_DATA_IS_NULL = 21;	
    public static final int SYNC_DATA_IS_LASTEST = 22;
    
    /** 
     * �����ı�,����JSON,XML�� 
     */  
    public static String sendTxt(String urlPath, String txt, String encoding)  
            throws Exception {
        byte[] sendData = txt.getBytes();
        URL url = new URL(urlPath);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();  
        conn.setRequestMethod("POST");  
        conn.setConnectTimeout(TIMEOUT);
        
        // ����Cookie
        if(sessionValue !=null && sessionValue.length() > 0) {
        	conn.setRequestProperty("Cookie", sessionValue);
        }
        
        // ���ͨ��post�ύ���ݣ�����������������������  
        conn.setDoOutput(true);  
        conn.setRequestProperty("Content-Type", "text/xml");
        conn.setRequestProperty("Charset", encoding);
        conn.setRequestProperty("Content-Length", String  
                .valueOf(sendData.length));  
        OutputStream outStream = conn.getOutputStream();  
        outStream.write(sendData);  
        outStream.flush();  
        outStream.close();  
        
        if (conn.getResponseCode() == 200) {  
            // ��÷�������Ӧ������  
            BufferedReader in = new BufferedReader(new InputStreamReader(conn  
                    .getInputStream(), encoding));  
            // ����  
            String retData = null;  
            String responseData = "";
            while ((retData = in.readLine()) != null) {  
                responseData += retData;
            }  
            in.close();  
            
            // ���cookie
            String cookie = conn.getHeaderField("set-cookie");
            if(cookie != null && cookie.length() > 0) {
            	sessionValue = cookie;
            }
            
            return responseData;  
        }  
        else {
       	 Log.i("Post Request", "Error Code: " + conn.getResponseCode() + conn.getResponseMessage());
       	 return "sendTxt error!";  
       }
    }
  
    /** 
     * �ϴ��ļ� 
     */  
    public static String sendFile(String urlPath, String filePath,  
            String newName) throws Exception {  
        String end = "\r\n";  
        String twoHyphens = "--";  
        String boundary = "*****";  
  
        URL url = new URL(urlPath);  
        HttpURLConnection con = (HttpURLConnection) url.openConnection();  
        /* ����Input��Output����ʹ��Cache */  
        con.setDoInput(true);  
        con.setDoOutput(true);  
        con.setUseCaches(false);  
        /* ���ô��͵�method=POST */  
        con.setRequestMethod("POST");  
        /* setRequestProperty */  
  
        con.setRequestProperty("Connection", "Keep-Alive");  
        con.setRequestProperty("Charset", "UTF-8");  
        con.setRequestProperty("Content-Type", "multipart/form-data;boundary="  
                + boundary);  
        /* ����DataOutputStream */  
        DataOutputStream ds = new DataOutputStream(con.getOutputStream());  
        ds.writeBytes(twoHyphens + boundary + end);  
        ds.writeBytes("Content-Disposition: form-data; "  
                + "name=\"file1\";filename=\"" + newName + "\"" + end);  
        ds.writeBytes(end);  
  
        /* ȡ���ļ���FileInputStream */  
        FileInputStream fStream = new FileInputStream(filePath);  
        /* ����ÿ��д��1024bytes */  
        int bufferSize = 1024;  
        byte[] buffer = new byte[bufferSize];  
  
        int length = -1;  
        /* ���ļ���ȡ������������ */  
        while ((length = fStream.read(buffer)) != -1) {  
            /* ������д��DataOutputStream�� */  
            ds.write(buffer, 0, length);  
        }  
        ds.writeBytes(end);  
        ds.writeBytes(twoHyphens + boundary + twoHyphens + end);  
  
        /* close streams */  
        fStream.close();  
        ds.flush();  
  
        /* ȡ��Response���� */  
        InputStream is = con.getInputStream();  
        int ch;  
        StringBuffer b = new StringBuffer();  
        while ((ch = is.read()) != -1) {  
            b.append((char) ch);  
        }  
        /* �ر�DataOutputStream */  
        ds.close();  
        return b.toString();  
    }  
  
    
  
    /** 
     * ������HTTPS��ȫģʽ���߲���cookie��ʱ��ʹ��HttpClient�᷽��ܶ� ʹ��HttpClient����Դ��Ŀ����������ύ���� 
     */  
    public static String sendHttpClientPost(String urlPath,  
            Map<String, String> params, String encoding) throws Exception {  
        // ��Ҫ�Ѳ����ŵ�NameValuePair
        List<NameValuePair> paramPairs = new ArrayList<NameValuePair>();  
        if (params != null && !params.isEmpty()) {  
            for (Map.Entry<String, String> entry : params.entrySet()) {  
                paramPairs.add(new BasicNameValuePair(entry.getKey(), entry  
                        .getValue()));  
            }  
        }  
        // ������������б��룬�õ�ʵ������  
        UrlEncodedFormEntity entitydata = new UrlEncodedFormEntity(paramPairs,  
                encoding);  
        // ����һ������·��  
        HttpPost post = new HttpPost(urlPath);  
        // ��������ʵ��  
        post.setEntity(entitydata);  
        // ���������  
        DefaultHttpClient client = new DefaultHttpClient();  
        // ִ��post����  
        HttpResponse response = client.execute(post);  
        // ��״̬���л�ȡ״̬�룬�ж���Ӧ���Ƿ����Ҫ��  
        if (response.getStatusLine().getStatusCode() == 200) {  
            HttpEntity entity = response.getEntity();  
            InputStream inputStream = entity.getContent();  
            InputStreamReader inputStreamReader = new InputStreamReader(  
                    inputStream, encoding);  
            BufferedReader reader = new BufferedReader(inputStreamReader);// ���ַ����õġ�  
            String s;  
            String responseData = "";  
            while (((s = reader.readLine()) != null)) {  
                responseData += s;  
            }  
            reader.close();// �ر�������  
            return responseData;  
        }  
        return "sendHttpClientPost error!";  
    }  
  
    /** 
     * ����URLֱ�Ӷ��ļ����ݣ�ǰ��������ļ����е��������ı��������ķ���ֵ�����ļ����е����� 
     */  
    public static String readTxtFile(String urlStr, String encoding)  
            throws Exception {  
        StringBuffer sb = new StringBuffer();  
        String line = null;  
        BufferedReader buffer = null;  
        try {  
            // ����һ��URL����  
            URL url = new URL(urlStr);  
            // ����һ��HTTP����  
            HttpURLConnection urlConn = (HttpURLConnection) url  
                    .openConnection();  
            // ʹ��IO����ȡ����  
            buffer = new BufferedReader(new InputStreamReader(urlConn  
                    .getInputStream(), encoding));  
            while ((line = buffer.readLine()) != null) {  
                sb.append(line);  
            }  
        } catch (Exception e) {  
            throw e;  
        } finally {  
            try {  
                buffer.close();  
            } catch (Exception e) {  
                e.printStackTrace();  
            }  
        }  
        return sb.toString();  
    }  
  
    /** 
     * �ú����������� -1�����������ļ����� 0�����������ļ��ɹ� 1�������ļ��Ѿ����� 
     */  
    public static int downloadFile(String urlStr, String path, String fileName)  
            throws Exception {  
        InputStream inputStream = null;  
        try {  
            inputStream = getInputStreamFromUrl(urlStr);  
            File resultFile = write2SDFromInput(path, fileName, inputStream);  
            if (resultFile == null) {  
                return -1;  
            }  
  
        } catch (Exception e) {  
            return -1;  
        } finally {  
            try {  
                inputStream.close();  
            } catch (Exception e) {  
                throw e;  
            }  
        }  
        return 0;  
    }  
  
    /** 
     * ����URL�õ������� 
     *  
     * @param urlStr 
     * @return 
     * @throws MalformedURLException 
     * @throws IOException 
     */  
    public static InputStream getInputStreamFromUrl(String urlStr)  
            throws MalformedURLException, IOException {  
        URL url = new URL(urlStr);  
        HttpURLConnection urlConn = (HttpURLConnection) url.openConnection();  
        InputStream inputStream = urlConn.getInputStream();  
        return inputStream;  
    }  
  
    /** 
     * ��һ��InputStream���������д�뵽SD���� 
     */  
    private static File write2SDFromInput(String directory, String fileName,  
            InputStream input) {  
        File file = null;  
        String SDPATH = Environment.getExternalStorageDirectory().toString();  
        FileOutputStream output = null;  
        File dir = new File(SDPATH + directory);  
        if (!dir.exists()) {  
            dir.mkdir();  
        }  
        try {  
            file = new File(dir + File.separator + fileName);  
            file.createNewFile();  
            output = new FileOutputStream(file);  
            byte buffer[] = new byte[1024];  
            while ((input.read(buffer)) != -1) {  
                output.write(buffer);  
            }  
            output.flush();  
        } catch (IOException e) {  
            e.printStackTrace();  
        } finally {  
            try {  
                output.close();  
            } catch (IOException e) {  
                e.printStackTrace();  
            }  
        }  
        return file;  
    }  
    
	// Http Get For String
	public static String HTTPGetCall(String WebMethodURL) throws IOException,
			MalformedURLException {
		StringBuilder response = new StringBuilder();
		// Prepare the URL and the connection
		URL u = new URL(WebMethodURL);
		HttpURLConnection conn = (HttpURLConnection) u.openConnection();
		if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
			// Get the Stream reader ready
			BufferedReader input = new BufferedReader(new InputStreamReader(
					conn.getInputStream()), 8192);
			// Loop through the return data and copy it over to the response
			// object to be processed
			String line = null;
			while ((line = input.readLine()) != null) {
				response.append(line);
			}
			input.close();
		}
		return response.toString();
	}
    
	//==================================Ư���ķָ���=============================================//
	
    /** 
     * JSON�������� 
     */  
    
    public static String sendJson(String url, JSONObject param) throws Exception {
        HttpPost request = new HttpPost(url);  
        // �ȷ�װһ�� JSON ����
        //JSONObject param = new JSONObject();  
        //param.put("name", "rarnu");  
        //param.put("password", "123456");  
        // �󶨵����� Entry  
        StringEntity se = new StringEntity(param.toString());   
        request.setEntity(se);  
        // ��������  
        HttpResponse httpResponse = new DefaultHttpClient().execute(request);  
        // �õ�Ӧ����ַ�������Ҳ��һ�� JSON ��ʽ���������  
        String retSrc = EntityUtils.toString(httpResponse.getEntity());
        // ���� JSON ����  
        JSONObject result = new JSONObject(retSrc);  
        String token = (String) result.get("token");
        return token;
    }
    
    
    //=============================������Java�ӿ��ṩ��http��get��postʵ��=============================//
    /** 
     * ͨ��get��ʽ�ύ������������ 
     */  
    public static String sendGetRequest(String urlPath,
            Map<String, String> params, String encoding) throws Exception {
  
        // ʹ��StringBuilder����
        StringBuilder sb = new StringBuilder(urlPath);  
        sb.append('?');
  
        // ����Map
        if(params != null && !params.isEmpty()) {
        	for (Map.Entry<String, String> entry : params.entrySet()) {
                sb.append(entry.getKey()).append('=').append(
                        URLEncoder.encode(entry.getValue(), encoding)).append('&');
            }
        }
        
        sb.deleteCharAt(sb.length() - 1);  
        
        // ������ 
        URL url = new URL(sb.toString());
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();  
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-Type", "text/xml");
        conn.setRequestProperty("Charset", encoding);  
        conn.setConnectTimeout(TIMEOUT);  
        
        // ����Cookie
        if(sessionValue !=null && sessionValue.length() > 0) {
        	conn.setRequestProperty("Cookie", sessionValue);
        }
        
        // ���������Ӧ����200�����ʾ�ɹ�  
        if (conn.getResponseCode() == 200) {  
            // ��÷�������Ӧ������  
            BufferedReader in = new BufferedReader(new InputStreamReader(conn  
                    .getInputStream(), encoding));  
            // ����  
            String retData = null;
            String responseData = "";  
            while ((retData = in.readLine()) != null) {  
                responseData += retData;  
            }
            in.close();  
            
            // ���cookie
            String cookie = conn.getHeaderField("set-cookie");
            if(cookie != null && cookie.length() > 0) {
            	sessionValue = cookie;
            }
            
            return responseData;  
        }  
        else {
        	Log.i("PostRequest",
					"Error Code: " + conn.getResponseCode()
							+ conn.getResponseMessage());
        	return "sendGetRequest error!";
        }
  
    }  
  
    /** 
     * ͨ��Post��ʽ�ύ������������,Ҳ������������JSON��XML�ļ�
     */
    public static String sendPostRequest(String urlPath,
            Map<String, String> params, String encoding) throws Exception {  
    	
        StringBuilder sb = new StringBuilder();
        // ���������Ϊ�� 
        if (params != null && !params.isEmpty()) {
            for (Map.Entry<String, String> entry : params.entrySet()) {
                // Post��ʽ�ύ�����Ļ�������ʡ�����������볤��  
                sb.append(entry.getKey()).append('=').append(
                        URLEncoder.encode(entry.getValue(), encoding)).append(
                        '&');
            }
            sb.deleteCharAt(sb.length() - 1);  
        }
        
        // �õ�ʵ��Ķ���������
        byte[] entitydata = sb.toString().getBytes();
        URL url = new URL(urlPath);  
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();  
        conn.setRequestMethod("POST");
        conn.setConnectTimeout(TIMEOUT);
        
        // ����Cookie
        if(sessionValue !=null && sessionValue.length() > 0) {
        	conn.setRequestProperty("Cookie", sessionValue);
        }
        
        // ���ͨ��post�ύ���ݣ�����������������������  
        conn.setDoOutput(true);
        // ���ò�ʹ�û���
        conn.setUseCaches(false);
        // ����ֻ�����������������ݳ��ȵ�ͷ�ֶ�  
        conn.setRequestProperty("Content-Type",
                "application/x-www-form-urlencoded");
        // conn.setRequestProperty("Content-Type", "text/xml");
        conn.setRequestProperty("Charset", encoding);  
        conn.setRequestProperty("Content-Length", String  
                .valueOf(entitydata.length));  
        OutputStream outStream = conn.getOutputStream();  
        // ��ʵ������д���������  
        outStream.write(entitydata);  
        // �ڴ��е�����ˢ��  
        outStream.flush();  
        outStream.close();
        
        // ���������Ӧ����200�����ʾ�ɹ�  
        if (conn.getResponseCode() == 200) {  
            // ��÷�������Ӧ������  
            BufferedReader in = new BufferedReader(new InputStreamReader(conn  
                    .getInputStream(), encoding));  
            // ����  
            String retData = null;  
            String responseData = "";  
            while ((retData = in.readLine()) != null) {  
                responseData += retData;
            }  
            in.close();  
            
            // ���cookie
            String cookie = conn.getHeaderField("set-cookie");
            if(cookie != null && cookie.length() > 0) {
            	sessionValue = cookie;
            }
            
            return responseData;  
        }  
        else {
			Log.i("PostRequest",
					"Error Code: " + conn.getResponseCode()
							+ conn.getResponseMessage());
			return "sendText error!";
        }
    }
    
    /** 
     * ͨ��Post��ʽ�ύJSON������������
     */  
    public static String sendPostRequestJson(String urlPath,
            JSONObject params, String encoding) throws Exception {
        
    	Log.i("PostJson","Data to Post: " + params.toString());
    	
        // �õ�ʵ��Ķ���������
    	//String jsonData = URLEncoder.encode(params.toString(),encoding);
    	//byte[] entitydata = jsonData.getBytes();
    	
    	//String jsonData = String.valueOf(params);
    	//byte[] entitydata = jsonData.getBytes();
    	
        byte[] entitydata = params.toString().getBytes();
    	
    	
        URL url = new URL(urlPath);
        HttpURLConnection conn = (HttpURLConnection)url.openConnection();
        conn.setRequestMethod("POST");
        conn.setConnectTimeout(TIMEOUT);
        
        // ����Cookie
        if(sessionValue !=null && sessionValue.length() > 0) {
        	conn.setRequestProperty("Cookie", sessionValue);
        }
        
        // ���ͨ��post�ύ���ݣ�����������������������  
        conn.setDoOutput(true);
        // ���ò�ʹ�û���
        conn.setUseCaches(false);
        
        // ����User-Agent: Fiddler
        //conn.setRequestProperty("ser-Agent", "Fiddler");
        
        // ����ֻ�����������������ݳ��ȵ�ͷ�ֶ�
        conn.setRequestProperty("Content-Type", "application/json");
        //conn.setRequestProperty("Accept", "application/json");
        conn.setRequestProperty("Charset", encoding);  
        conn.setRequestProperty("Content-Length", String.valueOf(entitydata.length));  
        OutputStream outStream = conn.getOutputStream();
        // ��ʵ������д���������  
        outStream.write(entitydata);  
        // �ڴ��е�����ˢ��  
        outStream.flush();  
        outStream.close();
        
        // ���������Ӧ����200�����ʾ�ɹ�  
        if (conn.getResponseCode() == 200) {
            // ��÷�������Ӧ������  
            BufferedReader in = new BufferedReader(new InputStreamReader(conn
                    .getInputStream(), encoding));  
            // ����  
            String retData = null;  
            String responseData = "";  
            while ((retData = in.readLine()) != null) {  
                responseData += retData;
            }  
            in.close();  
            
            // ���cookie
            String cookie = conn.getHeaderField("set-cookie");
            if(cookie != null && cookie.length() > 0) {
            	sessionValue = cookie;
            }
            
            return responseData;  
        }
        else {
        	 Log.i("PostJson", "Error Code: " + conn.getResponseCode() + conn.getResponseMessage());
        	 return "sendJson error!";  
        }
    }
    
    /** 
     * ͨ��Post��ʽ�ύString������������
     */
    public static String sendPostRequestJson(String urlPath,
            String params, String encoding) throws Exception {
        
    	Log.i("PostJson","Data to Post: " + params);
    	
        // �õ�ʵ��Ķ���������
    	
        byte[] entitydata = params.getBytes();
    	
    	
        URL url = new URL(urlPath);
        HttpURLConnection conn = (HttpURLConnection)url.openConnection();
        conn.setRequestMethod("POST");
        conn.setConnectTimeout(TIMEOUT);
        
        // ����Cookie
        if(sessionValue !=null && sessionValue.length() > 0) {
        	conn.setRequestProperty("Cookie", sessionValue);
        }
        
        // ���ͨ��post�ύ���ݣ�����������������������  
        conn.setDoOutput(true);  
        // ���ò�ʹ�û���
        conn.setUseCaches(false);
        
        // ����User-Agent: Fiddler
        //conn.setRequestProperty("ser-Agent", "Fiddler");
        
        // ����ֻ�����������������ݳ��ȵ�ͷ�ֶ�
        conn.setRequestProperty("Content-Type", "application/json");
        //conn.setRequestProperty("Accept", "application/json");
        conn.setRequestProperty("Charset", encoding);
        conn.setRequestProperty("Content-Length", String.valueOf(entitydata.length));
        OutputStream outStream = conn.getOutputStream();
        // ��ʵ������д���������  
        outStream.write(entitydata);  
        // �ڴ��е�����ˢ��  
        outStream.flush();  
        outStream.close();
        
        // ���������Ӧ����200�����ʾ�ɹ�  
        if (conn.getResponseCode() == 200) {
            // ��÷�������Ӧ������  
            BufferedReader in = new BufferedReader(new InputStreamReader(conn
                    .getInputStream(), encoding));  
            // ����  
            String retData = null;  
            String responseData = "";  
            while ((retData = in.readLine()) != null) {  
                responseData += retData;
            }  
            in.close();  
            
            // ���cookie
            String cookie = conn.getHeaderField("set-cookie");
            if(cookie != null && cookie.length() > 0) {
            	sessionValue = cookie;
            }
            
            return responseData;  
        }
        else {
        	 Log.i("PostJson", "Error Code: " + conn.getResponseCode() + conn.getResponseMessage());
        	 return "sendJson error!";  
        }
    }
    
    
    //=============================������Apache�ӿ��ṩ��http��get��postʵ��=============================//
	/** 
     * ���������Ƿ���� 
     */
    public static boolean isConnnected(Context context) {  
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);  
        if (null != connectivityManager) {  
            NetworkInfo networkInfo[] = connectivityManager.getAllNetworkInfo();  
  
            if (null != networkInfo) {  
                for (NetworkInfo info : networkInfo) {  
                    if (info.getState() == NetworkInfo.State.CONNECTED) {  
                        Log.i(TAG, "Network is OK");  
                        return true;  
                    }  
                }  
            }  
        }
        Toast.makeText(context, "Please Check Your Network!", Toast.LENGTH_SHORT).show();  
        return false;  
    }  
  
    /** 
     * �������״̬�£�ͨ��get��ʽ��server�˷������󣬲�������Ӧ���� 
     *  
     * @param strUrl ������ַ 
     * @param context ������ 
     * @return ��Ӧ���� 
     */  
    public static String getResponseForGet(String strUrl, Context context) {  
        if (isConnnected(context)) {  
            return getResponseForGet(strUrl);  
        }  
        return null;  
    }  
  
    /** 
     * ͨ��Get��ʽ�������󣬲�������Ӧ���� 
     *  
     * @param strUrl ������ַ 
     * @return ��Ӧ��JSON���� 
     */
    public static String getResponseForGet(String strUrl) {  
        HttpGet httpRequest = new HttpGet(strUrl);  
        return getRespose(httpRequest);  
    }  
  
    /** 
     * �������״̬�£�ͨ��post��ʽ��server�˷������󣬲�������Ӧ���� 
     *  
     * @param serverUrl ������ַ 
     * @param paramData JSON��ʽ�Ĳ�����Ϣ 
     * @param context ������ 
     * @return ��Ӧ���� 
     */  
    public static String getResponseForPostJson(String serverUrl, JSONObject paramData, Context context) {  
        if (isConnnected(context)) {  
            return getResponseForPostJson(serverUrl, paramData);
        }  
        return null;  
    }  
  
    /** 
     * ͨ��post��ʽ��������������󣬲�������Ӧ���� 
     *  
     * @param serverUrl ������ַ 
     * @param paramData JSON��ʽ������Ϣ 
     * @return ��Ӧ���� 
     */  
    public static String getResponseForPostJson(String serverUrl, JSONObject paramData) {
        if (null == serverUrl || "" == serverUrl) {
            return null;  
        }  
        
        HttpPost request = new HttpPost(serverUrl);
        request.setHeader("content-type", "application/json");
        try {
            //request.setEntity(new StringEntity(paramData.toString()));
        	
        	// ����HTTP POST�������������NameValuePair���� 
        	//List<NameValuePair> params = new ArrayList<NameValuePair>();  
        	//params.add(new BasicNameValuePair("jsondata", paramData.toString()));
        	//request.setEntity(new UrlEncodedFormEntity(params));
        	// ����HTTP POST�������������NameValuePair���� 
        	
        	StringEntity entity = new StringEntity(paramData.toString());
        	entity.setContentType("application/json");
        	entity.setContentEncoding("utf-8");
        	request.setEntity(entity);
        	
            return getRespose(request);
        } catch (UnsupportedEncodingException e1) {
            e1.printStackTrace();
        }  
        return null;  
    }  
  
    /** 
     * �������״̬�£�ͨ��post��ʽ��server�˷������󣬲�������Ӧ���� 
     *  
     * @param market_uri ������ַ 
     * @param nameValuePairs ������Ϣ 
     * @param context ������ 
     * @return ��Ӧ���� 
     */  
    public static String getResponseForPost(String market_uri, List<NameValuePair> nameValuePairs, Context context) {  
        if (isConnnected(context)) {  
            return getResponseForPost(market_uri, nameValuePairs);  
        }  
        return null;
    }  
  
    /** 
     * ͨ��post��ʽ��������������󣬲�������Ӧ���� 
     *  
     * @param strUrl ������ַ 
     * @param nameValuePairs ������Ϣ 
     * @return ��Ӧ���� 
     */  
    public static String getResponseForPost(String market_uri, List<NameValuePair> nameValuePairs) {  
        if (null == market_uri || "" == market_uri) {  
            return null;  
        }  
        HttpPost request = new HttpPost(market_uri);  
        try {  
            request.setEntity(new UrlEncodedFormEntity(nameValuePairs));
            return getRespose(request);  
        } catch (UnsupportedEncodingException e1) {  
            e1.printStackTrace();  
        }  
        return null;  
    }  
    
    /** 
     * ��Ӧ�ͻ������� 
     *  
     * @param request �ͻ�������get/post 
     * @return ��Ӧ���� 
     */  
    public static String getRespose(HttpUriRequest request) {
        try {
        	DefaultHttpClient defaultHttpClient = new DefaultHttpClient();
        	defaultHttpClient.setCookieStore(cookieStore);
        	
            HttpResponse httpResponse = defaultHttpClient.execute(request);
            int statusCode = httpResponse.getStatusLine().getStatusCode();
            if (HttpStatus.SC_OK == statusCode) {  
                String result = EntityUtils.toString(httpResponse.getEntity());
                Log.i(TAG, "results=" + result);
                return result;
            }  
        } catch (ClientProtocolException e) {  
            e.printStackTrace();  
        } catch (IOException e) {  
            e.printStackTrace();  
        } 
        return null;  
    }
    
    // ר������ͬ���ӿ�
    
    // ��ȡTime tips
    public static String getTimeTips(String serverUrl, Context context) {
    	return getResponseForGet(serverUrl,context);
    }
    
    // ��ȡMood tips
    public static String getMoodTips(String serverUrl, Context context) {
    	return getResponseForGet(serverUrl,context);
    }
    
    // �û�ע��
    public static String userRegister(String serverUrl, JSONObject paramData, Context context) throws Exception {
        return getResponseForPostJson(serverUrl, paramData, context);
    }
    
    // �û���¼
    public static String userLogin(String serverUrl, JSONObject paramData, Context context) throws Exception {
    	return getResponseForPostJson(serverUrl, paramData, context);
    }
    
    // �û��ǳ�
    public static String userLogout(String serverUrl, Context context) {
    	return getResponseForGet(serverUrl,context);
    }
    
    // �û�����ͬ��ͨ�ýӿ�
    public static JSONObject userDataSync(String url,String type, JSONObject param, Context context) {
    	if(type.equals(PUSH)){
    		
    	}
    	else if(type.equals(PULL)){
    		
    	}
    	else
    		;
    	return null;
    }
    
    //�û�����ͬ��ר�ýӿ�
    public static String todolistPull(String serverUrl, JSONObject paramData, Context context) {
    	return getResponseForPostJson(serverUrl, paramData, context);
    }
}
