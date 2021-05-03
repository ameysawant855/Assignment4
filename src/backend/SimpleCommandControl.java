package backend;


//this class plays a role in the implementation of the command pattern. We can consider this class as the invoker class.
public class SimpleCommandControl {

	Command_Quality quality;
	
	public SimpleCommandControl() {}
	
	public void setQualityLevelCommand(Command_Quality command) {
		quality = command;
	}
	
	public void giveCommand() {
		quality.execute();
	}
}
