package daccess;
import java.io.BufferedReader;
import java.io.InputStreamReader;


public class NFC {

	Manager t;
	public NFC(Manager t)
	{
		this.t = t;
		Thread th = new Thread() 
		{
		    public void run(){
		        loopRead();
		      }
		};
		th.start();
	}
	
	public void loopRead()
	{
		while (true)
		{
			startReading();
			if (read)
			{
				System.out.println("Card read: " + cardID);
				t.cardRead(cardID);
				readSleep(2000);
			}
			if (!connected)
			{
				System.out.println("NFC module connection error");
				readSleep(5000);
			}
		}
	}
	
	boolean connected;
	boolean read;
	String cardID;

	public void readSleep(int t)
	{
		try {
			Thread.sleep(t);
		} catch (InterruptedException e) {
		}
	}
	
	public void startReading()
	{
		connected = false;
		read = false;
		try {  
		      String line;  
		      Process p = Runtime.getRuntime().exec("/home/pi/libnfc/libnfc-1.7.0/examples/nfc-poll");  
		      BufferedReader input =  
		        new BufferedReader  
		          (new InputStreamReader(p.getInputStream()));  
		      while ((line = input.readLine()) != null) {  
		        processLine(line);  
		      }  
		      input.close();  
		    }  
		    catch (Exception err) {  
		      err.printStackTrace();  
		    }
	}
	
	private void processLine(String line)
	{
		line = line.replace(" ", "");
		if (line.startsWith("NFCdevicewillpollduring"))
			connected = true;
		if (line.startsWith("UID(NFCID"))
		{
			read = true;
			cardID = line.substring(line.indexOf(":") + 1);
		}
	}

}