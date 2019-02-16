import java.util.List;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class GameArcade implements State {
    private boolean _gameIsOver;
    private World _copyWorld;
    private InputType _inputType;
    private String _inputPlayer;
    private World _world;

    public GameArcade(World world) {
	_gameIsOver = false;
	_inputType = InputType.SHOW_SCOREBAR;
	_inputPlayer = null;
	_world = world;
	_copyWorld = _world.clone();
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

    public InputType interpretInput() {
	if (_inputPlayer == null) {
	    _inputType = InputType.QUIT;
	    return (_inputType);
	}

	switch (_inputPlayer) {
	case "Z":
	    _inputType = InputType.UP;
	    return (_inputType);

	case "S":
	    _inputType = InputType.DOWN;
	    return (_inputType);

	case "Q":
	    _inputType = InputType.LEFT;
	    return (_inputType);

	case "D":
	    _inputType = InputType.RIGHT;
	    return (_inputType);

	case "T":
	    _inputType = InputType.TAKE;
	    return (_inputType);

	case "U":
	    _inputType = InputType.USE_WEAPON;
	    return (_inputType);

	case "Y":
	    _inputType = InputType.SWITCH_WEAPON;
	    return (_inputType);

	case "O":
	    _inputType = InputType.DROP_WEAPON;
	    return (_inputType);

	case "I":
	    _inputType = InputType.SHOW_SCOREBAR;
	    return (_inputType);

	default:
	    break;
	}
	return (_inputType);
    }

    public void action() {
	if (_inputType.getInputTypeCode() <= 3) {
	    if (this.canMovePlayer())
		this.movePlayer();
	    else
		System.out.println("\033[31mPlayer can't move here!\033[0m");
	}

	else if (_inputType.equals(InputType.TAKE)) {
	    if (this.playerCanTake())
		this.playerTake();
	    else
		System.out.println("\033[31mPlayer can't take this object\033[0m");
	}

	else if (_inputType.equals(InputType.USE_WEAPON)) {
	    if (this.playerCanAttack())
		this.playerAttack();
	    else
		System.out.println("\033[31mPlayer can't attack\033[0m");
	}

	else if (_inputType.equals(InputType.DROP_WEAPON)) {
	    if (this.playerCanDropWeapon())
		this.playerDropWeapon();
	    else
		System.out.println("\033[31mPlayer can't drop Weapon\033[0m");
	}

	else if (_inputType.equals(InputType.SWITCH_WEAPON))
	    this.playerSwitchWeapon();

	else if (_inputType.equals(InputType.SHOW_SCOREBAR))
	    System.out.println(_world.getPlayer().getName() + "'s score: \033[36m" + _world.getPlayer().getScore() + "\033[0m");

	else if (_inputType.equals(InputType.QUIT))
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

    public void moveEnnemies() {
	_world.moveEnnemies();
    }

    public void displayInfos() {
	List<Ennemy> tmp = _world.getEnnemies();

        System.out.println("\033[36m" + _world.getPlayer().getName() + "\033[0m: " +
			   _world.getPlayer().getHealthBar().getCurrentPoints() + "/" +
			   _world.getPlayer().getHealthBar().getMaxPoint() + "hp");

	for (Ennemy x : tmp) {
	    System.out.println("\033[31m" + x.getName() + "\033[0m: " +
			       x.getHealthBar().getCurrentPoints() + "/" +
			       x.getHealthBar().getMaxPoint() + "hp");
	}

	System.out.println("");
	if (_world.getPlayer().getCurrentWeapon() != null) {
	    System.out.println("\033[36m" + _world.getPlayer().getName() + "\033[0m's current weapon: " +
			       _world.getPlayer().getCurrentWeapon().getName());
	}
	else {
	    System.out.println("\033[36m" + _world.getPlayer().getName() + "\033[0m's current weapon: ");
	}
	System.out.println("\033[36m" + _world.getPlayer().getName() + "\033[0m's weapons: " +
			   _world.getPlayer().getWeapons().toString());
	System.out.println("\033[36m" + _world.getPlayer().getName() + "\033[0m's keys: " +
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
	if (_world.playerWon())
	    _gameIsOver = true;
	return (_gameIsOver);
    }

    @Override
    public StateType run() {
	boolean won = false;

	this.displayMap();
	this.displayInfos();
	while (!gameOver()) {
	    this.readInput();
	    this.interpretInput();
	    Util.clearScreen();
	    this.action();
	    this.moveEnnemies();
	    this.displayMap();
	    this.displayInfos();
	    if (_world.playerWon())
		won = true;
	}
	Util.clearScreen();
	this.resetGame();

	if (won)
	    return StateType.WIN_STATE;
	return StateType.LOSE_STATE;
    }
}
