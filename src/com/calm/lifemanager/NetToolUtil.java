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
	
    private static final int TIMEOUT = 10000;// 10秒 
    
    // 开启Cookie
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
     * 传送文本,例如JSON,XML等 
     */  
    public static String sendTxt(String urlPath, String txt, String encoding)  
            throws Exception {
        byte[] sendData = txt.getBytes();
        URL url = new URL(urlPath);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();  
        conn.setRequestMethod("POST");  
        conn.setConnectTimeout(TIMEOUT);
        
        // 设置Cookie
        if(sessionValue !=null && sessionValue.length() > 0) {
        	conn.setRequestProperty("Cookie", sessionValue);
        }
        
        // 如果通过post提交数据，必须设置允许对外输出数据  
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
            // 获得服务器响应的数据  
            BufferedReader in = new BufferedReader(new InputStreamReader(conn  
                    .getInputStream(), encoding));  
            // 数据  
            String retData = null;  
            String responseData = "";
            while ((retData = in.readLine()) != null) {  
                responseData += retData;
            }  
            in.close();  
            
            // 获得cookie
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
     * 上传文件 
     */  
    public static String sendFile(String urlPath, String filePath,  
            String newName) throws Exception {  
        String end = "\r\n";  
        String twoHyphens = "--";  
        String boundary = "*****";  
  
        URL url = new URL(urlPath);  
        HttpURLConnection con = (HttpURLConnection) url.openConnection();  
        /* 允许Input、Output，不使用Cache */  
        con.setDoInput(true);  
        con.setDoOutput(true);  
        con.setUseCaches(false);  
        /* 设置传送的method=POST */  
        con.setRequestMethod("POST");  
        /* setRequestProperty */  
  
        con.setRequestProperty("Connection", "Keep-Alive");  
        con.setRequestProperty("Charset", "UTF-8");  
        con.setRequestProperty("Content-Type", "multipart/form-data;boundary="  
                + boundary);  
        /* 设置DataOutputStream */  
        DataOutputStream ds = new DataOutputStream(con.getOutputStream());  
        ds.writeBytes(twoHyphens + boundary + end);  
        ds.writeBytes("Content-Disposition: form-data; "  
                + "name=\"file1\";filename=\"" + newName + "\"" + end);  
        ds.writeBytes(end);  
  
        /* 取得文件的FileInputStream */  
        FileInputStream fStream = new FileInputStream(filePath);  
        /* 设置每次写入1024bytes */  
        int bufferSize = 1024;  
        byte[] buffer = new byte[bufferSize];  
  
        int length = -1;  
        /* 从文件读取数据至缓冲区 */  
        while ((length = fStream.read(buffer)) != -1) {  
            /* 将资料写入DataOutputStream中 */  
            ds.write(buffer, 0, length);  
        }  
        ds.writeBytes(end);  
        ds.writeBytes(twoHyphens + boundary + twoHyphens + end);  
  
        /* close streams */  
        fStream.close();  
        ds.flush();  
  
        /* 取得Response内容 */  
        InputStream is = con.getInputStream();  
        int ch;  
        StringBuffer b = new StringBuffer();  
        while ((ch = is.read()) != -1) {  
            b.append((char) ch);  
        }  
        /* 关闭DataOutputStream */  
        ds.close();  
        return b.toString();  
    }  
  
    
  
    /** 
     * 在遇上HTTPS安全模式或者操作cookie的时候使用HttpClient会方便很多 使用HttpClient（开源项目）向服务器提交参数 
     */  
    public static String sendHttpClientPost(String urlPath,  
            Map<String, String> params, String encoding) throws Exception {  
        // 需要把参数放到NameValuePair
        List<NameValuePair> paramPairs = new ArrayList<NameValuePair>();  
        if (params != null && !params.isEmpty()) {  
            for (Map.Entry<String, String> entry : params.entrySet()) {  
                paramPairs.add(new BasicNameValuePair(entry.getKey(), entry  
                        .getValue()));  
            }  
        }  
        // 对请求参数进行编码，得到实体数据  
        UrlEncodedFormEntity entitydata = new UrlEncodedFormEntity(paramPairs,  
                encoding);  
        // 构造一个请求路径  
        HttpPost post = new HttpPost(urlPath);  
        // 设置请求实体  
        post.setEntity(entitydata);  
        // 浏览器对象  
        DefaultHttpClient client = new DefaultHttpClient();  
        // 执行post请求  
        HttpResponse response = client.execute(post);  
        // 从状态行中获取状态码，判断响应码是否符合要求  
        if (response.getStatusLine().getStatusCode() == 200) {  
            HttpEntity entity = response.getEntity();  
            InputStream inputStream = entity.getContent();  
            InputStreamReader inputStreamReader = new InputStreamReader(  
                    inputStream, encoding);  
            BufferedReader reader = new BufferedReader(inputStreamReader);// 读字符串用的。  
            String s;  
            String responseData = "";  
            while (((s = reader.readLine()) != null)) {  
                responseData += s;  
            }  
            reader.close();// 关闭输入流  
            return responseData;  
        }  
        return "sendHttpClientPost error!";  
    }  
  
    /** 
     * 根据URL直接读文件内容，前提是这个文件当中的内容是文本，函数的返回值就是文件当中的内容 
     */  
    public static String readTxtFile(String urlStr, String encoding)  
            throws Exception {  
        StringBuffer sb = new StringBuffer();  
        String line = null;  
        BufferedReader buffer = null;  
        try {  
            // 创建一个URL对象  
            URL url = new URL(urlStr);  
            // 创建一个HTTP连接  
            HttpURLConnection urlConn = (HttpURLConnection) url  
                    .openConnection();  
            // 使用IO流读取数据  
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
     * 该函数返回整形 -1：代表下载文件出错 0：代表下载文件成功 1：代表文件已经存在 
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
     * 根据URL得到输入流 
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
     * 将一个InputStream里面的数据写入到SD卡中 
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
    
	//==================================漂亮的分割线=============================================//
	
    /** 
     * JSON交互处理 
     */  
    
    public static String sendJson(String url, JSONObject param) throws Exception {
        HttpPost request = new HttpPost(url);  
        // 先封装一个 JSON 对象
        //JSONObject param = new JSONObject();  
        //param.put("name", "rarnu");  
        //param.put("password", "123456");  
        // 绑定到请求 Entry  
        StringEntity se = new StringEntity(param.toString());   
        request.setEntity(se);  
        // 发送请求  
        HttpResponse httpResponse = new DefaultHttpClient().execute(request);  
        // 得到应答的字符串，这也是一个 JSON 格式保存的数据  
        String retSrc = EntityUtils.toString(httpResponse.getEntity());
        // 生成 JSON 对象  
        JSONObject result = new JSONObject(retSrc);  
        String token = (String) result.get("token");
        return token;
    }
    
    
    //=============================以下是Java接口提供的http的get和post实现=============================//
    /** 
     * 通过get方式提交参数给服务器 
     */  
    public static String sendGetRequest(String urlPath,
            Map<String, String> params, String encoding) throws Exception {
  
        // 使用StringBuilder对象
        StringBuilder sb = new StringBuilder(urlPath);  
        sb.append('?');
  
        // 迭代Map
        if(params != null && !params.isEmpty()) {
        	for (Map.Entry<String, String> entry : params.entrySet()) {
                sb.append(entry.getKey()).append('=').append(
                        URLEncoder.encode(entry.getValue(), encoding)).append('&');
            }
        }
        
        sb.deleteCharAt(sb.length() - 1);  
        
        // 打开链接 
        URL url = new URL(sb.toString());
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();  
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-Type", "text/xml");
        conn.setRequestProperty("Charset", encoding);  
        conn.setConnectTimeout(TIMEOUT);  
        
        // 设置Cookie
        if(sessionValue !=null && sessionValue.length() > 0) {
        	conn.setRequestProperty("Cookie", sessionValue);
        }
        
        // 如果请求响应码是200，则表示成功  
        if (conn.getResponseCode() == 200) {  
            // 获得服务器响应的数据  
            BufferedReader in = new BufferedReader(new InputStreamReader(conn  
                    .getInputStream(), encoding));  
            // 数据  
            String retData = null;
            String responseData = "";  
            while ((retData = in.readLine()) != null) {  
                responseData += retData;  
            }
            in.close();  
            
            // 获得cookie
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
     * 通过Post方式提交参数给服务器,也可以用来传送JSON或XML文件
     */
    public static String sendPostRequest(String urlPath,
            Map<String, String> params, String encoding) throws Exception {  
    	
        StringBuilder sb = new StringBuilder();
        // 如果参数不为空 
        if (params != null && !params.isEmpty()) {
            for (Map.Entry<String, String> entry : params.entrySet()) {
                // Post方式提交参数的话，不能省略内容类型与长度  
                sb.append(entry.getKey()).append('=').append(
                        URLEncoder.encode(entry.getValue(), encoding)).append(
                        '&');
            }
            sb.deleteCharAt(sb.length() - 1);  
        }
        
        // 得到实体的二进制数据
        byte[] entitydata = sb.toString().getBytes();
        URL url = new URL(urlPath);  
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();  
        conn.setRequestMethod("POST");
        conn.setConnectTimeout(TIMEOUT);
        
        // 设置Cookie
        if(sessionValue !=null && sessionValue.length() > 0) {
        	conn.setRequestProperty("Cookie", sessionValue);
        }
        
        // 如果通过post提交数据，必须设置允许对外输出数据  
        conn.setDoOutput(true);
        // 设置不使用缓存
        conn.setUseCaches(false);
        // 这里只设置内容类型与内容长度的头字段  
        conn.setRequestProperty("Content-Type",
                "application/x-www-form-urlencoded");
        // conn.setRequestProperty("Content-Type", "text/xml");
        conn.setRequestProperty("Charset", encoding);  
        conn.setRequestProperty("Content-Length", String  
                .valueOf(entitydata.length));  
        OutputStream outStream = conn.getOutputStream();  
        // 把实体数据写入是输出流  
        outStream.write(entitydata);  
        // 内存中的数据刷入  
        outStream.flush();  
        outStream.close();
        
        // 如果请求响应码是200，则表示成功  
        if (conn.getResponseCode() == 200) {  
            // 获得服务器响应的数据  
            BufferedReader in = new BufferedReader(new InputStreamReader(conn  
                    .getInputStream(), encoding));  
            // 数据  
            String retData = null;  
            String responseData = "";  
            while ((retData = in.readLine()) != null) {  
                responseData += retData;
            }  
            in.close();  
            
            // 获得cookie
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
     * 通过Post方式提交JSON参数给服务器
     */  
    public static String sendPostRequestJson(String urlPath,
            JSONObject params, String encoding) throws Exception {
        
    	Log.i("PostJson","Data to Post: " + params.toString());
    	
        // 得到实体的二进制数据
    	//String jsonData = URLEncoder.encode(params.toString(),encoding);
    	//byte[] entitydata = jsonData.getBytes();
    	
    	//String jsonData = String.valueOf(params);
    	//byte[] entitydata = jsonData.getBytes();
    	
        byte[] entitydata = params.toString().getBytes();
    	
    	
        URL url = new URL(urlPath);
        HttpURLConnection conn = (HttpURLConnection)url.openConnection();
        conn.setRequestMethod("POST");
        conn.setConnectTimeout(TIMEOUT);
        
        // 设置Cookie
        if(sessionValue !=null && sessionValue.length() > 0) {
        	conn.setRequestProperty("Cookie", sessionValue);
        }
        
        // 如果通过post提交数据，必须设置允许对外输出数据  
        conn.setDoOutput(true);
        // 设置不使用缓存
        conn.setUseCaches(false);
        
        // 设置User-Agent: Fiddler
        //conn.setRequestProperty("ser-Agent", "Fiddler");
        
        // 这里只设置内容类型与内容长度的头字段
        conn.setRequestProperty("Content-Type", "application/json");
        //conn.setRequestProperty("Accept", "application/json");
        conn.setRequestProperty("Charset", encoding);  
        conn.setRequestProperty("Content-Length", String.valueOf(entitydata.length));  
        OutputStream outStream = conn.getOutputStream();
        // 把实体数据写入是输出流  
        outStream.write(entitydata);  
        // 内存中的数据刷入  
        outStream.flush();  
        outStream.close();
        
        // 如果请求响应码是200，则表示成功  
        if (conn.getResponseCode() == 200) {
            // 获得服务器响应的数据  
            BufferedReader in = new BufferedReader(new InputStreamReader(conn
                    .getInputStream(), encoding));  
            // 数据  
            String retData = null;  
            String responseData = "";  
            while ((retData = in.readLine()) != null) {  
                responseData += retData;
            }  
            in.close();  
            
            // 获得cookie
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
     * 通过Post方式提交String参数给服务器
     */
    public static String sendPostRequestJson(String urlPath,
            String params, String encoding) throws Exception {
        
    	Log.i("PostJson","Data to Post: " + params);
    	
        // 得到实体的二进制数据
    	
        byte[] entitydata = params.getBytes();
    	
    	
        URL url = new URL(urlPath);
        HttpURLConnection conn = (HttpURLConnection)url.openConnection();
        conn.setRequestMethod("POST");
        conn.setConnectTimeout(TIMEOUT);
        
        // 设置Cookie
        if(sessionValue !=null && sessionValue.length() > 0) {
        	conn.setRequestProperty("Cookie", sessionValue);
        }
        
        // 如果通过post提交数据，必须设置允许对外输出数据  
        conn.setDoOutput(true);  
        // 设置不使用缓存
        conn.setUseCaches(false);
        
        // 设置User-Agent: Fiddler
        //conn.setRequestProperty("ser-Agent", "Fiddler");
        
        // 这里只设置内容类型与内容长度的头字段
        conn.setRequestProperty("Content-Type", "application/json");
        //conn.setRequestProperty("Accept", "application/json");
        conn.setRequestProperty("Charset", encoding);
        conn.setRequestProperty("Content-Length", String.valueOf(entitydata.length));
        OutputStream outStream = conn.getOutputStream();
        // 把实体数据写入是输出流  
        outStream.write(entitydata);  
        // 内存中的数据刷入  
        outStream.flush();  
        outStream.close();
        
        // 如果请求响应码是200，则表示成功  
        if (conn.getResponseCode() == 200) {
            // 获得服务器响应的数据  
            BufferedReader in = new BufferedReader(new InputStreamReader(conn
                    .getInputStream(), encoding));  
            // 数据  
            String retData = null;  
            String responseData = "";  
            while ((retData = in.readLine()) != null) {  
                responseData += retData;
            }  
            in.close();  
            
            // 获得cookie
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
    
    
    //=============================以下是Apache接口提供的http的get和post实现=============================//
	/** 
     * 网络连接是否可用 
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
     * 网络可用状态下，通过get方式向server端发送请求，并返回响应数据 
     *  
     * @param strUrl 请求网址 
     * @param context 上下文 
     * @return 响应数据 
     */  
    public static String getResponseForGet(String strUrl, Context context) {  
        if (isConnnected(context)) {  
            return getResponseForGet(strUrl);  
        }  
        return null;  
    }  
  
    /** 
     * 通过Get方式处理请求，并返回相应数据 
     *  
     * @param strUrl 请求网址 
     * @return 响应的JSON数据 
     */
    public static String getResponseForGet(String strUrl) {  
        HttpGet httpRequest = new HttpGet(strUrl);  
        return getRespose(httpRequest);  
    }  
  
    /** 
     * 网络可用状态下，通过post方式向server端发送请求，并返回响应数据 
     *  
     * @param serverUrl 请求网址 
     * @param paramData JSON格式的参数信息 
     * @param context 上下文 
     * @return 响应数据 
     */  
    public static String getResponseForPostJson(String serverUrl, JSONObject paramData, Context context) {  
        if (isConnnected(context)) {  
            return getResponseForPostJson(serverUrl, paramData);
        }  
        return null;  
    }  
  
    /** 
     * 通过post方式向服务器发送请求，并返回响应数据 
     *  
     * @param serverUrl 请求网址 
     * @param paramData JSON格式参数信息 
     * @return 响应数据 
     */  
    public static String getResponseForPostJson(String serverUrl, JSONObject paramData) {
        if (null == serverUrl || "" == serverUrl) {
            return null;  
        }  
        
        HttpPost request = new HttpPost(serverUrl);
        request.setHeader("content-type", "application/json");
        try {
            //request.setEntity(new StringEntity(paramData.toString()));
        	
        	// 设置HTTP POST请求参数必须用NameValuePair对象 
        	//List<NameValuePair> params = new ArrayList<NameValuePair>();  
        	//params.add(new BasicNameValuePair("jsondata", paramData.toString()));
        	//request.setEntity(new UrlEncodedFormEntity(params));
        	// 设置HTTP POST请求参数必须用NameValuePair对象 
        	
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
     * 网络可用状态下，通过post方式向server端发送请求，并返回响应数据 
     *  
     * @param market_uri 请求网址 
     * @param nameValuePairs 参数信息 
     * @param context 上下文 
     * @return 响应数据 
     */  
    public static String getResponseForPost(String market_uri, List<NameValuePair> nameValuePairs, Context context) {  
        if (isConnnected(context)) {  
            return getResponseForPost(market_uri, nameValuePairs);  
        }  
        return null;
    }  
  
    /** 
     * 通过post方式向服务器发送请求，并返回响应数据 
     *  
     * @param strUrl 请求网址 
     * @param nameValuePairs 参数信息 
     * @return 响应数据 
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
     * 响应客户端请求 
     *  
     * @param request 客户端请求get/post 
     * @return 响应数据 
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
    
    // 专用数据同步接口
    
    // 获取Time tips
    public static String getTimeTips(String serverUrl, Context context) {
    	return getResponseForGet(serverUrl,context);
    }
    
    // 获取Mood tips
    public static String getMoodTips(String serverUrl, Context context) {
    	return getResponseForGet(serverUrl,context);
    }
    
    // 用户注册
    public static String userRegister(String serverUrl, JSONObject paramData, Context context) throws Exception {
        return getResponseForPostJson(serverUrl, paramData, context);
    }
    
    // 用户登录
    public static String userLogin(String serverUrl, JSONObject paramData, Context context) throws Exception {
    	return getResponseForPostJson(serverUrl, paramData, context);
    }
    
    // 用户登出
    public static String userLogout(String serverUrl, Context context) {
    	return getResponseForGet(serverUrl,context);
    }
    
    // 用户数据同步通用接口
    public static JSONObject userDataSync(String url,String type, JSONObject param, Context context) {
    	if(type.equals(PUSH)){
    		
    	}
    	else if(type.equals(PULL)){
    		
    	}
    	else
    		;
    	return null;
    }
    
    //用户数据同步专用接口
    public static String todolistPull(String serverUrl, JSONObject paramData, Context context) {
    	return getResponseForPostJson(serverUrl, paramData, context);
    }
}
