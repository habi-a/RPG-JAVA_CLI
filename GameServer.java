import java.util.List;
import java.lang.NumberFormatException;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class GameServer implements State {
    private int _port;
    private ServerSocket _serverSocket;
    private Socket _socketP2;
    private ObjectInputStream _in;
    private ObjectOutputStream _out;
    private boolean _gameIsOver;
    private WorldMulti _copyWorld;
    private InputType _inputType;
    private String _inputPlayer;
    private InputType _inputType2;
    private String _inputPlayer2;
    private WorldMulti _world;

    public GameServer(WorldMulti world) {
	_port = 0;
	_gameIsOver = false;
	_serverSocket = null;
	_socketP2 = null;
	_inputType = InputType.SHOW_SCOREBAR;
	_inputType2 = InputType.SHOW_SCOREBAR;
	_inputPlayer = null;
	_inputPlayer2 = null;
	_in = null;
	_out = null;
	_world = world;
	_copyWorld = _world.clone();
    }

    public void connect() {
	System.out.println("Waiting for player...");
	try {
	    _serverSocket = new ServerSocket(_port);
	    _socketP2 = _serverSocket.accept();
	} catch (IOException e) {
	    e.printStackTrace();
	}
	System.out.println("Player2 founded!");
    }

    public void sendWorld() {
	try {
	    _out = new ObjectOutputStream(_socketP2.getOutputStream());
	    _out.writeObject(new Message(_world, null));
	    _out.flush();
	} catch (IOException e) {
	    e.printStackTrace();
	}
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

    public String readInput2() {
	try {
	    _in = new ObjectInputStream(_socketP2.getInputStream());
	    Message inputP2 = (Message)_in.readObject();
	    _inputPlayer2 = inputP2.getInput();
	} catch (Exception e) {
	    e.printStackTrace();
	}
	return (_inputPlayer2);
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

    public InputType interpretInput(String inputPlayer, InputType inputType) {
	if (inputPlayer == null) {
	    inputType = InputType.QUIT;
	    return (inputType);
	}
	switch (inputPlayer) {
	case "Z":
	    inputType = InputType.UP;
	    return (inputType);
	case "S":
	    inputType = InputType.DOWN;
	    return (inputType);
	case "Q":
	    inputType = InputType.LEFT;
	    return (inputType);
	case "D":
	    inputType = InputType.RIGHT;
	    return (inputType);
	case "T":
	    inputType = InputType.TAKE;
	    return (inputType);
	case "U":
	    inputType = InputType.USE_WEAPON;
	    return (inputType);
	case "Y":
	    inputType = InputType.SWITCH_WEAPON;
	    return (inputType);
	case "O":
	    inputType = InputType.DROP_WEAPON;
	    return (inputType);
	case "I":
	    inputType = InputType.SHOW_SCOREBAR;
	    return (inputType);
	default:
	    break;
	}
	return (inputType);
    }

    public void action(InputType inputType) {
	if (inputType.getInputTypeCode() <= 3) {
	    if (this.canMovePlayer())
		this.movePlayer();
	    else
		System.out.println("Player can't move here!");
	}
	else if (inputType.equals(InputType.TAKE)) {
	    if (this.playerCanTake())
		this.playerTake();
	    else
		System.out.println("Player can't take this object");
	}
	else if (inputType.equals(InputType.USE_WEAPON)) {
	    if (this.playerCanAttack())
		this.playerAttack();
	    else
		System.out.println("Player can't attack");
	}
	else if (inputType.equals(InputType.DROP_WEAPON)) {
	    if (this.playerCanDropWeapon())
		this.playerDropWeapon();
	    else
		System.out.println("Player can't drop Weapon");
	}
	else if (inputType.equals(InputType.SWITCH_WEAPON))
	    this.playerSwitchWeapon();
	else if (inputType.equals(InputType.SHOW_SCOREBAR))
	    System.out.println(_world.getPlayer().getName() + "'s score: " + _world.getPlayer().getScore());
	else if (inputType.equals(InputType.QUIT))
	    _gameIsOver = true;
    }

    public void action2(InputType inputType) {
	if (inputType.getInputTypeCode() <= 3) {
	    if (this.canMovePlayer2())
		this.movePlayer2();
	    else
		System.out.println("Player 2 can't move here!");
	}
	else if (inputType.equals(InputType.TAKE)) {
	    if (this.player2CanTake())
		this.player2Take();
	    else
		System.out.println("Player 2 can't take this object");
	}
	else if (inputType.equals(InputType.USE_WEAPON)) {
	    if (this.player2CanAttack())
		this.player2Attack();
	    else
		System.out.println("Player 2 can't attack");
	}
	else if (inputType.equals(InputType.DROP_WEAPON)) {
	    if (this.player2CanDropWeapon())
		this.player2DropWeapon();
	    else
		System.out.println("Player 2 can't drop Weapon");
	}
	else if (inputType.equals(InputType.SWITCH_WEAPON))
	    this.player2SwitchWeapon();
	else if (inputType.equals(InputType.SHOW_SCOREBAR))
	    System.out.println(_world.getPlayer2().getName() + "'s score: " + _world.getPlayer2().getScore());
	else if (inputType.equals(InputType.QUIT))
	    _gameIsOver = true;
    }

    public boolean playerCanDropWeapon() {
	return (_world.playerCanDropWeapon());
    }

    public void playerDropWeapon() {
	_world.playerDropWeapon();
    }

    public void playerSwitchWeapon() {
	_world.playerSwitchWeapon();
    }

    public boolean playerCanAttack() {
	return (_world.canAttack(_world.getPlayer()));
    }

    public void playerAttack() {
	_world.attack(_world.getPlayer());
    }

    public boolean playerCanTake() {
	return (_world.playerCanTake());
    }

    public void playerTake() {
	_world.playerTake();
    }

    public boolean canMovePlayer() {
	switch (_inputType) {
	case UP:
	    return (_world.canMovePlayerUp());
	case DOWN:
	    return (_world.canMovePlayerDown());
	case LEFT:
	    return (_world.canMovePlayerLeft());
	case RIGHT:
	    return (_world.canMovePlayerRight());
	default:
	    break;
	}
	return false;
    }

    public void movePlayer() {
	switch (_inputType) {
	case UP:
	    _world.movePlayerUp();
	    break;
	case DOWN:
	    _world.movePlayerDown();
	    break;
	case LEFT:
	    _world.movePlayerLeft();
	    break;
	case RIGHT:
	    _world.movePlayerRight();
	    break;
	default:
	    break;
	}
    }

    public boolean player2CanDropWeapon() {
	return (_world.player2CanDropWeapon());
    }

    public void player2DropWeapon() {
	_world.player2DropWeapon();
    }

    public void player2SwitchWeapon() {
	_world.player2SwitchWeapon();
    }

    public boolean player2CanAttack() {
	return (_world.canAttack(_world.getPlayer2()));
    }

    public void player2Attack() {
	_world.attack(_world.getPlayer2());
    }

    public boolean player2CanTake() {
	return (_world.player2CanTake());
    }

    public void player2Take() {
	_world.player2Take();
    }

    public boolean canMovePlayer2() {
	switch (_inputType2) {
	case UP:
	    return (_world.canMovePlayer2Up());
	case DOWN:
	    return (_world.canMovePlayer2Down());
	case LEFT:
	    return (_world.canMovePlayer2Left());
	case RIGHT:
	    return (_world.canMovePlayer2Right());
	default:
	    break;
	}
	return false;
    }

    public void movePlayer2() {
	switch (_inputType2) {
	case UP:
	    _world.movePlayer2Up();
	    break;
	case DOWN:
	    _world.movePlayer2Down();
	    break;
	case LEFT:
	    _world.movePlayer2Left();
	    break;
	case RIGHT:
	    _world.movePlayer2Right();
	    break;
	default:
	    break;
	}
    }

    public void moveEnnemies() {
	_world.moveEnnemies();
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
	if (_world.getPlayer().getCurrentWeapon() != null) {
	    System.out.println(_world.getPlayer().getName() + "'s current weapon: " +
			       _world.getPlayer().getCurrentWeapon().getName());
	}
	else {
	    System.out.println(_world.getPlayer().getName() + "'s current weapon: ");
	}
	System.out.println(_world.getPlayer().getName() + "'s weapons: " +
			   _world.getPlayer().getWeapons().toString());
	System.out.println(_world.getPlayer().getName() + "'s keys: " +
			   _world.getPlayer().getKeys().toString());
    }

    public void displayMap() {
	_world.print();
    }

    public void resetGame() {
	_world = null;
	_gameIsOver = false;
	_inputType = InputType.SHOW_SCOREBAR;
	_inputPlayer = null;
	_world = _copyWorld.clone();
    }

    public boolean gameOver() {
	if (_world.getPlayer().isDead())
	    _gameIsOver = true;
	else if (_world.playerWon() || _world.player2Won())
	    _gameIsOver = true;
	return (_gameIsOver);
    }

    @Override
    public StateType run() {
	boolean won = false;

	if (!this.readPort())
	    return StateType.QUIT;

	this.connect();
	this.sendWorld();
	System.out.println("\033[33mIt's your turn\033[0m");
	this.displayMap();
	this.displayInfos();
	while (!this.gameOver()) {
	    this.readInput();
	    Util.clearScreen();
	    _inputType = this.interpretInput(_inputPlayer, _inputType);
	    this.action(_inputType);
	    System.out.println("\033[33mWaiting for player 2\033[0m");
	    this.sendWorld();
	    this.displayMap();
	    this.displayInfos();
	    this.readInput2();
	    Util.clearScreen();
	    _inputType2 = this.interpretInput(_inputPlayer2, _inputType2);
	    this.action2(_inputType2);
	    this.moveEnnemies();
	    System.out.println("\033[33mIt's your turn\033[0m");
	    this.sendWorld();
	    this.displayMap();
	    this.displayInfos();
	    if (_world.playerWon())
		won = true;
	}
	Util.clearScreen();
	this.resetGame();

	try {
	    _socketP2.close();
	} catch (IOException e) {
	    e.printStackTrace();
	}

	if (won)
	    return StateType.WIN_STATE;
	return StateType.LOSE_STATE;
    }
}
