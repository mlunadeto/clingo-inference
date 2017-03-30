package it.eng.util;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

public class ReaderUtil {
	

	private static  ReaderUtil READERUTILINSTANCE;

	private ReaderUtil() {
		
		
	}


	public static ReaderUtil getInstance(){
		if(READERUTILINSTANCE==null){
			READERUTILINSTANCE=new ReaderUtil();
		}
		return READERUTILINSTANCE;
	}
	
	
	
	/**
	 * 
	 * @param path2Observations2
	 * @param listOfStrings
	 * @param append
	 */
	public void printOnObservation(String path2Observations2, List<String> listOfStrings, boolean append) {
		try{
			BufferedWriter out = new BufferedWriter(new FileWriter(path2Observations2, append));
			Iterator<String> it=listOfStrings.iterator();
			while (it.hasNext()) {
				out.write(it.next() + "\n");
				out.newLine();
			}
			out.close();
		} catch (IOException e) {
			// do something
		}

	}


	public void printOnObservation(String path2Observations2, String strings, boolean append) {
		try{
			BufferedWriter out = new BufferedWriter(new FileWriter(path2Observations2, append));
			out.write(strings + "\n");
			out.newLine();
			out.close();
		} catch (IOException e) {
			// do something
		}

	}




	public void cleanObservations(String path2Observations2){
		FileOutputStream writer;
		try {
			writer = new FileOutputStream(path2Observations2);
			writer.write(("").getBytes());
			writer.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}


	
	
	
	


}
