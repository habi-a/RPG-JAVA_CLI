import java.io.Serializable;

public class Message implements Serializable {
    private WorldMulti _world;
    private String _input;
    
    public Message(WorldMulti world, String input) {
	_input = input;
	_world = world;
    }
    
    public String getInput() {
	return (_input);
    }
    
    public WorldMulti getWorld() {
	return (_world);
    }
}
