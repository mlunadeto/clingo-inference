package it.eng.clingo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.List;



public class ClingoExecutor {




	public ClingoExecutor(String path2Clingo, String commands, String[] path2Axioms) {
		super();
		this.setPath2Clingo(path2Clingo);
		this.setCommands(commands);
		this.setPath2Axioms(path2Axioms);
	}


	private String path2Clingo;
	private String commands;
	private String[]path2Axioms;

	private String outputDataString = null;
	private String errorDataString = null;





	/**
	 * fa girare clingo e ritorna il suo output
	 * @param path2Clingo
	 * @param commands
	 * @param path2axioms
	 * @return
	 * @throws IOException 
	 */
	public  void runClingo(){


		StringBuilder stringBuilder= new StringBuilder();

		stringBuilder.append(this.path2Clingo+" ");
		for(int i=0;i<this.path2Axioms.length;i++){
			stringBuilder.append(path2Axioms[i]+" ");

		}
		stringBuilder.append(this.commands+" ");
		String script=stringBuilder.toString();

		Process process=null;
		List<String> outputData=null;
		List<String> errorData=null;
		try {
			process = Runtime.getRuntime().exec(script);

			
			StreamFlusher stdoutFlusher = new StreamFlusher( process.getInputStream());
			stdoutFlusher.start();


			StreamFlusher erroutFlusher = new StreamFlusher( process.getErrorStream() );
			erroutFlusher.start();



			int reval = 0;
			try {
				reval = process.waitFor();
			}
			catch ( InterruptedException e ) {
				e.printStackTrace();
			}
		

			outputData = stdoutFlusher.getOutput();
			errorData = erroutFlusher.getOutput();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		StringBuilder builderOutput=new StringBuilder();
		for(String output: outputData){
			builderOutput.append(output);
		}
		setOutputDataString(builderOutput.toString());

		StringBuilder builderError=new StringBuilder();
		for(String error: errorData){
			builderError.append(error);
		}
		setErrorDataString(builderError.toString());

	}
	
	
	
	
	


	/**
	 * preleva il risultato dallo stream e lo inserisce in una stringa di testo
	 * @param process
	 * @return
	 */
	private String parseResult(Process process){
		String result=null;

		InputStream inputStream=process.getInputStream();
		InputStreamReader inputStreamReader=new InputStreamReader(inputStream);
		BufferedReader bufferedReaderOutput=new BufferedReader(inputStreamReader);
		BufferedReader bufferedReaderError=new BufferedReader(new InputStreamReader(process.getErrorStream()));

		StringBuilder builder=new StringBuilder();
		String line;
		try {
			while((line=bufferedReaderOutput.readLine())!=null){
				builder.append(line);

			}

			if(builder.length()==0){
				while((line=bufferedReaderError.readLine())!=null){
					builder.append(line);
				}
			}


			bufferedReaderOutput.close();
			inputStreamReader.close();
			inputStream.close();
			bufferedReaderError.close();

			result=builder.toString();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;	
	}


	public String getPath2Clingo() {
		return path2Clingo;
	}


	public void setPath2Clingo(String path2Clingo) {
		this.path2Clingo = path2Clingo;
	}


	public String getCommands() {
		return commands;
	}


	public void setCommands(String commands) {
		this.commands = commands;
	}


	public String[] getPath2Axioms() {
		return path2Axioms;
	}


	public void setPath2Axioms(String[] path2Axioms) {
		this.path2Axioms = path2Axioms;
	}

	public String getOutputDataString() {
		return outputDataString;
	}


	public void setOutputDataString(String outputDataString) {
		this.outputDataString = outputDataString;
	}

	public String getErrorDataString() {
		return errorDataString;
	}


	public void setErrorDataString(String errorDataString) {
		this.errorDataString = errorDataString;
	}

	class StreamFlusher
	extends Thread
	{
		private InputStream is;
		private List<String> output = new LinkedList<String>();

		StreamFlusher( InputStream is )
		{
			this.is = is;
		}

		public void run()
		{
			try {
				BufferedReader br = new BufferedReader( new InputStreamReader( is ) );
				String line = null;
				while ( ( line = br.readLine() ) != null ) {
					output.add(line);
				}
				br.close();
				is.close();
			}
			catch ( IOException ioe ) {
				ioe.printStackTrace();
			}
		}


		public List<String> getOutput()
		{
			try {
				this.join();
			}
			catch ( InterruptedException e ) {
				e.printStackTrace();
			}
			return output;
		}


	}



}
