package backend;

//this class plays a part in the Command pattern. 
public class QualityLevelSetCommand implements Command_Quality{

	QualityLevelImpl quality_level;

	  //It represents setting the command for setting the daily rate for a room quality level	
	  public QualityLevelSetCommand(QualityLevelImpl quality_level){
	      this.quality_level = quality_level;
	   }
	
	@Override
	public void execute() {
		// TODO Auto-generated method stub
		quality_level.set();
	}

}
