package daccess;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class MQTT implements MqttCallback{

	HashMap<String,User> list;	
	Manager manager;
	
	class User
	{
		String uID;
		String user;
		long last;
		boolean around;
		int badge;
		
		public User(String user, String id, long last, boolean around, int badge) {
			this.user = user;
			uID = id;
			this.last = last;
			this.around = around;
			this.badge = badge;
		}
	}
	
	
	public MQTT(Manager mn){
		manager = mn;
		list = new HashMap<String,User>();
		list.put("22222222222222222222222222222222", new User("Team" , "22222222222222222222222222222222" , 0, false, 9999));
		list.put("44444444444444444444444444444444", new User("Baja" , "44444444444444444444444444444444" , 0, false, 9999));
		list.put("66666666666666666666666666666666", new User("Ahmodi" , "66666666666666666666666666666666" , 0, false, 792363));
		list.put("99999999999999999999999999999999", new User("Hammodi" , "99999999999999999999999999999999" , 0, false, 758837));
		list.put("00021588888888888888888888888888", new User("Ahmed iPhone" , "00021588888888888888888888888888" , 0, false, 9999));
		list.put("77777777777777777777777777777777", new User("Bolbol" , "77777777777777777777777777777777" , 0, false, 756585));
		//list.put("55555555555555555555555555555555", new User("SmartYousef" , "55555555555555555555555555555555" , 0, false));

		try 
		{
		
			MqttClient client = new MqttClient("tcp://localhost:1883", MqttClient.generateClientId());

			client.setCallback(this);

			MqttConnectOptions options = new MqttConnectOptions();

			client.connect(options);
			client.subscribe("/beacons");
			System.out.println("MQTT is " + (client.isConnected() ? "Connected" : "Not connected"));
		} 
		catch (MqttException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		new Thread() {
		public void run() {
			while (true)
			{
				long now = System.currentTimeMillis();
				for(Map.Entry<String, User> entry : list.entrySet()) {
//				    String key = entry.getKey();
				    User value = entry.getValue();
				    if (value.around)
				    {
				    	if (now - value.last > 10000)
					    {
					    	System.out.println("See you later " + value.user + " @ " + LocalDateTime.now());
					    	value.around = false;
					    }
				    }
				}
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		}.start();	
	}
	

	public void connectionLost(Throwable throwable) {
	    System.out.println("Connection to MQTT broker lost!");
	  }
	 
	  public void messageArrived(String s, MqttMessage mqttMessage) throws Exception {

		  final String msg = new String(mqttMessage.getPayload());
		  
		  new Thread(new Runnable() {
				
			@Override
			public void run() {
			try
			{
				// TODO Auto-generated method stub

			JSONParser parser = new JSONParser();
			Object obj = null;
			try {
				obj = parser.parse(msg);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			JSONObject json = (JSONObject) obj;
			//System.out.println(json.toString());
			String rawData = (String) json.get("raw_beacons_data");
			//System.out.println(rawData);
//			System.out.println(rawData.substring(28, 28 + 32));
			
			checkBeacon(rawData.substring(28, 28 + 32));
			} catch (Exception e)
			{}
			}
		}).start();
	  }
	  
	  public void checkBeacon(String bc)
	  {
		  User u = list.get(bc);
		  if (u != null)
		  {
			  if (u.around)
			  {
				  u.last = System.currentTimeMillis();
			  } else 
			  {
				  u.around = true;
				  u.last = System.currentTimeMillis();
				  System.out.println("Access granted! Welcome " + u.user + " @ " + LocalDateTime.now());
				  manager.approveLogin(u.badge);
			  }
		  }
	  }
	 
	  public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {
		  
	  }
	  
	  public void beaconDetected()
	  {
		  
	  }
}