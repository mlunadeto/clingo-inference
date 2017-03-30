package it.eng.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

public final class PathLoader {


	private static String OS = null;
	private static String getOsName()
	{
		if(OS == null) { OS = System.getProperty("os.name"); }
		return OS;
	}
	public static boolean isWindows()
	{
		return getOsName().startsWith("Windows");
	}

	public static boolean isUnix(){
		return !isWindows();
	}


	public static String loadPath(String fileName){
		
		ClassLoader classLoader=Thread.currentThread().getContextClassLoader();
		
		java.net.URL url=null;
		if(isWindows()){
			//url=classLoader.getResource("practionistFileFolder\\"+fileName);
			url=classLoader.getResource("aspFolder\\"+fileName);
			
		
		}else  {
			//url=classLoader.getResource("practionistFileFolder/"+fileName);
			url=classLoader.getResource("aspFolder\\"+fileName);
		}
		String a=null;
		try{		
			//a=	isWindows()?url.getPath().substring(1).replace("/", "\\\\"):url.getPath().substring(1);	
			a=url.getPath().substring(1);
			a=URLDecoder.decode(a, "UTF-8");
		}catch(NullPointerException e){
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return a;

	}

	
	public static String loadPathConfigFile(String fileName){
		ClassLoader classLoader=Thread.currentThread().getContextClassLoader();
		java.net.URL url=null;
	
			url=classLoader.getResource(fileName);
	
		String a=null;
		try{		
			//a=	isWindows()?url.getPath().substring(1).replace("/", "\\\\"):url.getPath().substring(1);	
			a=url.getPath().substring(1);
		}catch(NullPointerException e){
			e.printStackTrace();
		}
		return a;

	}

	

}
