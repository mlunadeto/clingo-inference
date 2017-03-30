package it.eng.util;


import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.ContentBody;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreProtocolPNames;
import org.apache.http.util.EntityUtils;

import it.eng.hybrid.util.ProxyPassport;

public class RestUtil {



	private static  RestUtil RESTUTILINSTANCE;

	private RestUtil() {
	}


	public static RestUtil getInstance(){
		if(RESTUTILINSTANCE==null){
			RESTUTILINSTANCE=new RestUtil();
		}
		return RESTUTILINSTANCE;
	}



	private int timeOut=180000;	
	private HttpURLConnection conn;
	private String acceptType=null;
	private String assignedToken=null;
	private String currentUsername=null;
	private static Log log = LogFactory.getLog(RestUtil.class);

	public final boolean resetClient()
	{
		timeOut=180000;	
		conn=null;
		acceptType=null;
		assignedToken=null;
		currentUsername=null;
		return true;
	}

	private boolean validateToken(String validate)
	{
		if(validate!=null)
			return true;
		return false;
	}


	public final boolean login(String url,String username,String password)
	{
		assignedToken=this.getRest(url, "username="+username+"&password="+password);
		if(validateToken(assignedToken))
		{
			currentUsername=username;
			return true;
		}
		return false;	
	}

	public final boolean logout(String url)
	{
		this.getRest(url,null);	
		assignedToken=null;
		currentUsername=null;	
		return true;
	}							

	public final String authPost(URL url,String body) 
	{
		if(currentUsername==null||assignedToken==null)
			return null;
		URL nurl;
		try 
		{
			nurl = new URL(url.toString()+"username="+currentUsername+"&token="+assignedToken);
			return postRest(nurl,body);
		} 
		catch (MalformedURLException e) 
		{
			e.printStackTrace();
			return null;
		}					 					 
	}

	public final String authGet(String address,String params) 
	{
		if(currentUsername==null||assignedToken==null)
			return null;
		return this.getRest(address, "username="+currentUsername+"&token="+assignedToken);
	}

	public final String authDelete(String address) 
	{	
		if(currentUsername==null||assignedToken==null)
			return null;
		return this.delete(address+"?username="+currentUsername+"&token="+assignedToken);
	}

	public void setAcceptType(String type)
	{
		this.acceptType=type;
	}

	public final String postRest(URL url,String body) 
	{
		String output="";
		StringBuffer res=new StringBuffer();
		if(url==null) return null;
		try 
		{					
			if(!url.toString().contains("localhost")&&!url.toString().contains("127.0.0.1")&&ProxyPassport.getIstance()!=null) 
				conn = (HttpURLConnection)url.openConnection(ProxyPassport.getIstance());
			else 
				conn = (HttpURLConnection)url.openConnection();		
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");					
			conn.setReadTimeout(timeOut);
			if(this.acceptType!=null)
				conn.setRequestProperty("Accept", this.acceptType);
			else
				conn.setRequestProperty("Content-Type", "application/json");
			OutputStream os = conn.getOutputStream();
			os.write(body.getBytes());
			os.flush();
			if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) 
				log.error("Post Rest Error (RestUtil) HTTP error code : "	+ conn.getResponseCode());
			BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));			
			while ((output = br.readLine()) != null) 
				res.append(output);
			br.close();
			return res.toString();
		}
		catch (Exception e) 
		{
			log.error("Post Rest Error (RestUtile.getMessage())"+e.getMessage());
			return null;
		}
		finally
		{
			conn.disconnect();					  
		}
	}	

	public final String getRest(String address,String params) 
	{
		String req=address;		
		if(address==null) return null;
		try
		{				
			if(params!=null)
				req=req+"?"+params;
			URL url=new URL(req);
			if(!address.contains("localhost")&&!address.contains("127.0.0.1")&&ProxyPassport.getIstance()!=null) 
				conn = (HttpURLConnection)url.openConnection(ProxyPassport.getIstance());
			else 
				conn = (HttpURLConnection)url.openConnection();	

			conn.setDoOutput(true);
			conn.setRequestMethod("GET");
			conn.setReadTimeout(timeOut);
			if(this.acceptType!=null)
				conn.setRequestProperty("Accept", this.acceptType);
			else
				conn.setRequestProperty("Content-Type", "application/json");				
			BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();
			while ((inputLine = in.readLine()) != null) 
			{
				response.append(inputLine);
			}
			in.close();
			return response.toString();
		}
		catch(Exception e)
		{
			log.error("Get Rest Error (RestUtile.getMessage())"+e.getMessage());
			log.error("Get Request>"+ req);
			return null;
		}
		finally
		{
			conn.disconnect();					  
		}

	}

	public final boolean headRest(String address) 
	{
		String req=address;		
		if(address==null) return false;
		try
		{				

			URL url=new URL(req);
			if(!address.contains("localhost")&&!address.contains("127.0.0.1")&&ProxyPassport.getIstance()!=null) 
				conn = (HttpURLConnection)url.openConnection(ProxyPassport.getIstance());
			else 
				conn = (HttpURLConnection)url.openConnection();	

			conn.setRequestMethod("HEAD");
			conn.setReadTimeout(timeOut);
			return (conn.getResponseCode() == HttpURLConnection.HTTP_OK);
		}
		catch(Exception e)
		{
			log.error("Get Rest Error (RestUtile.getMessage())"+e.getMessage());
			log.error("Get Request>"+ req);
			return false;
		}
		finally
		{
			conn.disconnect();					  
		}

	}


	public final boolean setTimeOut(int timeOut)
	{
		this.timeOut=timeOut;
		return true;		  
	}
	public int getTimeOut()
	{
		return this.timeOut;		  		  
	}

	public final String delete(String address) 
	{		
		String req=address;
		if(address==null) return null;
		try
		{
			URL url=new URL(req);
			if(!address.contains("localhost")&&!address.contains("127.0.0.1")&&ProxyPassport.getIstance()!=null) 
				conn = (HttpURLConnection)url.openConnection(ProxyPassport.getIstance());
			else 
				conn = (HttpURLConnection)url.openConnection();						
			conn.setDoOutput(true);
			conn.setRequestMethod("DELETE");
			conn.setReadTimeout(timeOut);
			BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();
			while ((inputLine = in.readLine()) != null) 
			{
				response.append(inputLine);
			}
			in.close();
			return response.toString();
		}
		catch(Exception e)
		{
			log.error("Get Rest Error (RestUtile.getMessage())"+e.getMessage());
			log.error("Get Request>"+ req);
			return null;
		}	
		finally
		{
			conn.disconnect();					  
		}
	}



	@SuppressWarnings("deprecation")
	public boolean postFile(String url,String path) {
		try{
			HttpClient httpclient = new DefaultHttpClient();
			httpclient.getParams().setParameter(CoreProtocolPNames.PROTOCOL_VERSION, HttpVersion.HTTP_1_1);

			HttpPost httppost = new HttpPost(url);
			File file = new File(path);

			MultipartEntity mpEntity = new MultipartEntity();
			ContentBody cbFile = new FileBody(file);
			mpEntity.addPart("userfile", cbFile);


			httppost.setEntity(mpEntity);
			log.info("executing request " + httppost.getRequestLine());
			HttpResponse response = httpclient.execute(httppost);
			HttpEntity resEntity = response.getEntity();

			log.info(response.getStatusLine());
			if (resEntity != null) {
				log.info(EntityUtils.toString(resEntity));
			}
			if (resEntity != null) {
				resEntity.consumeContent();
			}

			httpclient.getConnectionManager().shutdown();
			return true;
		}
		catch(Exception e){log.error(e.getMessage());return false;}
	}


}
