package daccess;

import jssc.SerialPort;
import jssc.SerialPortEvent;
import jssc.SerialPortEventListener;
import jssc.SerialPortException;

public class Serial implements SerialPortEventListener {

	private SerialPort port;
	ActionMessageInterface handler;
	
	public Serial(String stringPort, ActionMessageInterface action)
	{
	    this.port = new SerialPort(stringPort);
	    handler = action;
	    try {
	    	port.openPort(); //Open serial port
	    	port.setParams(SerialPort.BAUDRATE_9600, 
	                             SerialPort.DATABITS_8,
	                             SerialPort.STOPBITS_1,
	                             SerialPort.PARITY_NONE);//Set params. Also you can set params by this string: serialPort.setParams(9600, 8, 1, 0);
	    	port.setFlowControlMode(SerialPort.FLOWCONTROL_RTSCTS_IN | SerialPort.FLOWCONTROL_RTSCTS_OUT);
	    	port.setEventsMask(SerialPort.MASK_RXCHAR);
	    	port.addEventListener(this);
	    	System.out.println("Bluetooth serial open");
	    }
	    catch (SerialPortException ex) {
	        System.out.println(ex);
	    }
	}
	
	public void send(String data)
	{
		System.out.println("Sending: " + data);
		try {
			port.writeBytes(data.getBytes());
			//port.purgePort(port.PURGE_TXCLEAR | port.PURGE_RXCLEAR);
		} catch (SerialPortException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void sendCommand(String cmd)
	{
		send("BGNMSG[" + cmd + "]ENDMSG\r\n");
	}

	public void serialEvent(SerialPortEvent event) {
		//System.out.println("Event:" + event.getEventValue());
		if(event.isRXCHAR()){//If data is available
            if(event.getEventValue() > 0){//Check bytes count in the input buffer
                //Read data, if 10 bytes available 
                try {
                    byte buffer[] = port.readBytes(1);
                    System.out.print(new String(buffer));
                    ProcessSerial(new String(buffer));
                }
                catch (SerialPortException ex) {
                    System.out.println("Serial read error: " + ex);
                }
            }
        }
        else if(event.isCTS()){//If CTS line has changed state
            if(event.getEventValue() == 1){//If line is ON
                System.out.println("CTS - ON");
            }
            else {
                System.out.println("CTS - OFF");
            }
        }
        else if(event.isDSR()){///If DSR line has changed state
            if(event.getEventValue() == 1){//If line is ON
                System.out.println("DSR - ON");
            }
            else {
                System.out.println("DSR - OFF");
            }
        }
	}
	
	String allData = "";
	boolean started = false;
	final String BGNM = "BGNMSG[";
	final String ENDM = "]ENDMSG";
	public void ProcessSerial(String data)
	{
		allData = allData + data;
		if (!started)
		{
			if (allData.contains(BGNM))
			{
				allData = allData.substring(allData.indexOf(BGNM) + BGNM.length());
				started = true;
			}
		}
		if (started)
		{
			int endIndex = allData.indexOf(ENDM);
			int bgnIndex = allData.indexOf(BGNM);
			if (endIndex != -1 && (bgnIndex == -1 || endIndex < bgnIndex))
			{
				String completedMsg = allData.substring(0,allData.indexOf(ENDM));
				allData = allData.substring(allData.indexOf(ENDM) + ENDM.length());
				started = false;
				handler.processAction(completedMsg);
				ProcessSerial("");
				return;
			} else if (allData.contains(BGNM))
			{
				started = false;
				ProcessSerial("");
				return;
			}
		}
	}
}
