package daccess;

public class BTChip implements ActionMessageInterface {
	Serial sr;
	ActionMessageInterface handler;

	public BTChip(ActionMessageInterface action)
	{
		sr = new Serial("/dev/ttyACM0", this);
		handler = action;
	}
	
	public void sendCommand(String cmd)
	{
		sr.sendCommand(cmd);
	}
	
	@Override
	public void processAction(String action)
	{
		handler.processAction(action);
	}
}