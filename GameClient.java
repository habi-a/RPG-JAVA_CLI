import java.util.List;
import java.net.Socket;
import java.lang.ClassNotFoundException;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class GameClient implements State {
    private boolean _printScore;
    private int _port;
    private WorldMulti _world;
    private String _ipServer;
    private String _inputPlayer;
    private Socket _socket;
    private ObjectInputStream _in;
    private ObjectOutputStream _out;

    public GameClient() {
	_port = 0;
	_printScore = false;
	_ipServer = null;
    }

    public boolean readAddress() {
	String inputPlayer = null;
	BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

	try {
	    System.out.println("Enter Server IP Address: ");
	    inputPlayer = reader.readLine();
	} catch (IOException e) {
	    e.printStackTrace();
	}

	if (inputPlayer == null)
	    return false;

	_ipServer = inputPlayer;
	return true;
    }

    public boolean readPort() {
	String inputPlayer = null;
	BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

	try {
	    System.out.println("Enter Server Port: ");
	    inputPlayer = reader.readLine();
	} catch (IOException e) {
	    e.printStackTrace();
	}

	if (inputPlayer == null)
	    return false;

	try {
	    _port = Integer.parseInt(inputPlayer);
	} catch (NumberFormatException e) {
	    return false;
	}
	return true;
    }

    public void connect() {
	System.out.println("Attempting to connect to server " + _ipServer + ", port: " + _port);
	try {
	    _socket = new Socket(_ipServer, _port);
	    System.out.println("Connection succesfull to server");
	} catch (IOException e) {
	    e.printStackTrace();
	}
    }

    public String readInput() {
	try {
	    BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
	    _inputPlayer = reader.readLine();
	    if (_inputPlayer == null)
		return null;
	    _inputPlayer = _inputPlayer.toUpperCase();

	} catch (IOException e) {
	    e.printStackTrace();
	}
	return (_inputPlayer);
    }

    public void sendInput() {
	try {
	    _out = new ObjectOutputStream(_socket.getOutputStream());
	    _out.writeObject(new Message(null, _inputPlayer));
	    _out.flush();
	} catch (IOException e) {
	    e.printStackTrace();
	}
    }

    public WorldMulti getWorld() {
	try {
	    _in = new ObjectInputStream(_socket.getInputStream());
	    Message message = (Message) _in.readObject();
	    _world = message.getWorld();
	} catch (Exception e) {
	    e.printStackTrace();
	}

	try {
	    if (_inputPlayer.equals("I")) {
		_printScore = true;
		_inputPlayer = null;
	    }
	} catch (Exception e) {}
	return (_world);
    }

    public void displayInfos() {
	List<Ennemy> tmp = _world.getEnnemies();

	System.out.println(_world.getPlayer().getName() + ": " +
			   _world.getPlayer().getHealthBar().getCurrentPoints() + "/" +
			   _world.getPlayer().getHealthBar().getMaxPoint() + "hp");
	System.out.println(_world.getPlayer2().getName() + ": " +
			   _world.getPlayer2().getHealthBar().getCurrentPoints() + "/" +
			   _world.getPlayer2().getHealthBar().getMaxPoint() + "hp");
	for (Ennemy x : tmp) {
	    System.out.println(x.getName() + ": " +
			       x.getHealthBar().getCurrentPoints() + "/" +
			       x.getHealthBar().getMaxPoint() + "hp");
	}
	System.out.println("");
	if (_world.getPlayer2().getCurrentWeapon() != null) {
	    System.out.println(_world.getPlayer2().getName() + "'s current weapon: " +
			       _world.getPlayer2().getCurrentWeapon().getName());
	}
	else {
	    System.out.println(_world.getPlayer2().getName() + "'s current weapon: ");
	}
	System.out.println(_world.getPlayer2().getName() + "'s weapons: " +
			   _world.getPlayer2().getWeapons().toString());
	System.out.println(_world.getPlayer2().getName() + "'s keys: " +
			   _world.getPlayer2().getKeys().toString());
    }

    public void displayMap() {
	if (_printScore) {
	    System.out.println(_world.getPlayer2().getName() + "'s score: \033[36m" + _world.getPlayer2().getScore() + "\033[0m");
	    _printScore = false;
	}
	_world.print();
    }

    public boolean gameOver() {
	if (_world.getPlayer().isDead())
	    return true;
	else if (_world.playerWon() || _world.player2Won())
	    return true;
	return false;
    }

    public boolean playerWon() {
	return (_world.player2Won());
    }

    @Override
    public StateType run() {
	Util.clearScreen();
	if (!this.readAddress())
	    return StateType.QUIT;
	if (!this.readPort())
	    return StateType.QUIT;
	this.connect();
	this.getWorld();

	while (!_socket.isClosed() && !this.gameOver()) {
	    Util.clearScreen();
	    System.out.println("\033[33mWaiting for Player1 to play...\033[0m");
	    this.displayMap();
	    this.displayInfos();
	    this.getWorld();
	    Util.clearScreen();
	    System.out.println("\033[33mIt's your turn\033[0m");
	    this.displayMap();
	    this.displayInfos();
	    this.readInput();
	    this.sendInput();
	    this.getWorld();
	}

	if (this.playerWon())
	    return StateType.WIN_STATE;
	return StateType.LOSE_STATE;
    }
}
