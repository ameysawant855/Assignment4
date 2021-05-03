package backend;

import java.io.Serializable;


//this factory class contributes to the factory method pattern.
//It is the factory class which creates large and small room objects according to the parameters passed to it. 
public class RoomTypeFactory implements Serializable{
	
	private static final long serialVersionUID = -5901570440883086069L;

	public RoomTypeInterface createRoomType(String roomtype) {
		if (roomtype == null || roomtype.isEmpty())
            return null;
        if ("small_room".equalsIgnoreCase(roomtype)) {
            return new SmallerRoom();
        }
        else if ("large_room".equalsIgnoreCase(roomtype)) {
            return new LargerRoom();
        }
        return null;
    }

}
