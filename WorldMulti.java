import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

public class WorldMulti implements Serializable
{
    private String _filename;
    private int _nbColumn;
    private int _nbRow;
    private Player _player;
    private Player _player2;
    private List<Ennemy> _ennemies;
    private Cell[][] _world;
    private CellFactory _cellFactory;

    public WorldMulti(int nbRow, int nbColumn, String filename) {
	_nbRow = nbRow;
	_nbColumn = nbColumn;
	_filename = filename;
	_world = new Cell[nbRow][nbColumn];
        _cellFactory = new CellFactory();

	Scanner scanner = null;
	File file = new File(_filename);

	try {
	    scanner = new Scanner(file);
	} catch (FileNotFoundException e) {
	    e.printStackTrace();
	}

	for (int row = 0; scanner.hasNextLine() && row < nbRow; row++) {
	    char[] chars = scanner.nextLine().toCharArray();
	    for (int column = 0; column < nbColumn && column < chars.length; column++)
		_world[row][column] = _cellFactory.getCell(chars[column]);
	}
    }

    public WorldMulti(String filename, int nbRow, int nbColumn,
		      String playerName, int xPlayer, int yPlayer, char symbolPlayer, HealthBar healthBar,
		      String playerName2, int xPlayer2, int yPlayer2, char symbolPlayer2, HealthBar healthBar2,
		      List<Ennemy> ennemies)
    {
	Scanner scanner = null;
	_filename = filename;
	File file = new File(_filename);

	try {
	    scanner = new Scanner(file);
	} catch (FileNotFoundException e) {
	    e.printStackTrace();
	}

	_nbRow = nbRow;
	_nbColumn = nbColumn;
	_ennemies = ennemies;
	_cellFactory = new CellFactory();
	_world = new Cell[nbRow][nbColumn];
	_player = new Player(playerName, xPlayer, yPlayer, symbolPlayer, healthBar);
	_player2 = new Player(playerName2, xPlayer2, yPlayer2, symbolPlayer2, healthBar2);

	for (int row = 0; scanner.hasNextLine() && row < nbRow; row++) {
	    char[] chars = scanner.nextLine().toCharArray();
	    for (int column = 0; column < nbColumn && column < chars.length; column++)
		_world[row][column] = _cellFactory.getCell(chars[column]);
	}
    }

    public void setPlayer(Player player) {
	_player = player;
    }

    public void setPlayer2(Player player) {
	_player2 = player;
    }

    public void setCellFactory(CellFactory cellFactory) {
	_cellFactory = cellFactory;
    }

    public void setEnnemies(List<Ennemy> ennemies) {
	_ennemies = ennemies;
    }

    public Player getPlayer() {
	return (_player);
    }

    public Player getPlayer2() {
	return (_player2);
    }

    public List<Ennemy> getEnnemies() {
	return (_ennemies);
    }

    public void playerSwitchWeapon() {
	if (_player.getWeapons().size() <= 1)
	    return;
	_player.setWeaponNumber(_player.getWeaponNumber() + 1);
	if (_player.getWeaponNumber() >= _player.getWeapons().size())
	    _player.setWeaponNumber(0);
    }

    public boolean playerCanDropWeapon() {
	return (_world[_player.getX()][_player.getY()].isEmpty() && _player.getWeapons().size() > 0);
    }

    public void playerDropWeapon() {
	_world[_player.getX()][_player.getY()] = null;
	_world[_player.getX()][_player.getY()] = _cellFactory.getCell(_player.getCurrentWeapon().showAs());
	_player.dropCurrentWeapon();
    }

    public boolean canAttack(Fightable player) {
	if (player instanceof Ennemy) {
	    Ennemy tmp = (Ennemy)player;
	    if (tmp.getWeapon() == null)
		return false;
	    if (this.aPlayerIsHere(player.getX() - 1, _player.getY()))
		return true;
	    if (this.aPlayerIsHere(player.getX() + 1, _player.getY()))
		return true;
	    if (this.aPlayerIsHere(player.getX(), _player.getY() - 1))
		return true;
	    if (this.aPlayerIsHere(player.getX(), _player.getY() + 1))
		return true;
	}

	else if (player instanceof Player) {
	    Player tmp = (Player)player;
	    if (tmp.getWeapons().size() == 0) {
		System.out.println("Player don't have weapon");
		return false;
	    }
	    if (this.ennemyIsHere(player.getX() - 1, player.getY()) != null)
		return true;
	    if (this.ennemyIsHere(player.getX() + 1, player.getY()) != null)
		return true;
	    if (this.ennemyIsHere(player.getX(), player.getY() - 1) != null)
		return true;
	    if (this.ennemyIsHere(player.getX(), player.getY() + 1) != null)
		return true;
	}
	return false;
    }

    public void player2SwitchWeapon() {
	if (_player2.getWeapons().size() <= 1)
	    return;
	_player2.setWeaponNumber(_player2.getWeaponNumber() + 1);
	if (_player2.getWeaponNumber() >= _player2.getWeapons().size())
	    _player2.setWeaponNumber(0);
    }

    public boolean player2CanDropWeapon() {
	return (_world[_player2.getX()][_player2.getY()].isEmpty() && _player2.getWeapons().size() > 0);
    }

    public void player2DropWeapon() {
	_world[_player2.getX()][_player2.getY()] = null;
	_world[_player2.getX()][_player2.getY()] = _cellFactory.getCell(_player2.getCurrentWeapon().showAs());
	_player2.dropCurrentWeapon();
    }

    public void attack(Fightable player) {
	List<Fightable> fightables = new ArrayList<Fightable>();

	if (player instanceof Ennemy) {
	    if (this.aPlayerIsHere(player.getX() - 1, player.getY()))
		fightables.add(this.whatPlayerIsHere(player.getX() - 1, player.getY()));
	    else if (this.aPlayerIsHere(player.getX() + 1, player.getY()))
		fightables.add(this.whatPlayerIsHere(player.getX() + 1, player.getY()));
	    else if (this.aPlayerIsHere(player.getX(), player.getY() - 1))
		fightables.add(this.whatPlayerIsHere(player.getX(), player.getY() - 1));
	    else if (this.aPlayerIsHere(player.getX(), player.getY() + 1))
		fightables.add(this.whatPlayerIsHere(player.getX(), player.getY() + 1));
	}

	else if (player instanceof Player) {
	    if (this.ennemyIsHere(player.getX() - 1, player.getY()) != null)
		fightables.add(this.ennemyIsHere(player.getX() - 1, player.getY()));
	    if (this.ennemyIsHere(player.getX() + 1, player.getY()) != null)
		fightables.add(this.ennemyIsHere(player.getX() + 1, player.getY()));
	    if (this.ennemyIsHere(player.getX(), player.getY() - 1) != null)
		fightables.add(this.ennemyIsHere(player.getX(), player.getY() - 1));
	    if (this.ennemyIsHere(player.getX(), player.getY() + 1) != null)
		fightables.add(this.ennemyIsHere(player.getX(), player.getY() + 1));
	}

	for (Fightable x : fightables) {
	    if (player instanceof Player) {
		Ennemy tmpE = (Ennemy) x;
		Player tmpP = (Player) player;
		player.attack(x, tmpP.getCurrentWeapon().getDamagePoints());
		if (x.isDead())
		    tmpP.addScore(tmpE.getScoreReward());
	    }
	    else if (player instanceof Ennemy) {
		Ennemy tmpE = (Ennemy) player;
		player.attack(x, tmpE.getWeapon().getDamagePoints());
	    }
	}
    }

    public boolean playerCanTake() {
	return (_world[_player.getX()][_player.getY()].isTakable());
    }

    public void playerTake() {
	boolean ok = false;
	Weapon wToAdd = null;
	Key toAdd = null;
	Reaction reaction = null;

	if (_world[_player.getX()][_player.getY()].getTangible() instanceof Weapon) {
	    wToAdd = (Weapon)_world[_player.getX()][_player.getY()].getTangible();
	    _player.addWeapon(wToAdd);
	    ok = true;
	}

	else if (_world[_player.getX()][_player.getY()].getTangible() instanceof Key) {
	    toAdd = (Key)_world[_player.getX()][_player.getY()].getTangible();
	    _player.addKey(toAdd);
	}

	else if (_world[_player.getX()][_player.getY()].getTangible() instanceof Reaction) {
	    MiniGameRunner runner = new MiniGameRunner(_player.getKeys());
	    reaction = (Reaction)_world[_player.getX()][_player.getY()].getTangible();
	    if ((toAdd = runner.playGame(reaction.getMiniGame())) != null)
		_player.addKey(toAdd);
	}

	if (ok) {
	    _world[_player.getX()][_player.getY()] = null;
	    _world[_player.getX()][_player.getY()] = new Cell(new EmptyTangible());
	}
    }

    public boolean player2CanTake() {
	return (_world[_player2.getX()][_player2.getY()].isTakable());
    }

    public void player2Take() {
        boolean ok = false;
	Weapon wToAdd = null;
	Key toAdd = null;
	Reaction reaction = null;

	if (_world[_player2.getX()][_player2.getY()].getTangible() instanceof Weapon) {
	    wToAdd = (Weapon)_world[_player2.getX()][_player2.getY()].getTangible();
	    _player2.addWeapon(wToAdd);
        ok = true;
	}

	else if (_world[_player2.getX()][_player2.getY()].getTangible() instanceof Key) {
	    toAdd = (Key)_world[_player2.getX()][_player2.getY()].getTangible();
	    _player2.addKey(toAdd);
	}

	else if (_world[_player2.getX()][_player2.getY()].getTangible() instanceof Reaction) {
	    MiniGameRunner runner = new MiniGameRunner(_player2.getKeys());
	    reaction = (Reaction)_world[_player2.getX()][_player2.getY()].getTangible();
	    if ((toAdd = runner.playGame(reaction.getMiniGame())) != null)
		_player2.addKey(toAdd);
	}

    if (ok) {
	    _world[_player2.getX()][_player2.getY()] = null;
	    _world[_player2.getX()][_player2.getY()] = new Cell(new EmptyTangible());
	}
    }

    public void moveEnnemies() {
	for (int i = 0; i < _ennemies.size(); i++) {
	    if (i % 2 == 0)
		this.moveEnnemy1(_ennemies.get(i));
	    else
		this.moveEnnemy2(_ennemies.get(i));
	}
    }

    public void moveEnnemy1(Ennemy x) {
	if (!x.isDead()) {
	    if ((Math.abs(x.getX() - _player.getX()) == 1 && x.getY() == _player.getY())
		|| (Math.abs(x.getY() - _player.getY()) == 1 && x.getX() == _player.getX())) {
		this.attack(x);
		return;
	    }

	    if (x.getX() < _player.getX()) {
		if (this.playerCanGo(x.getX() + 1, x.getY()))
		    x.setCoord(x.getX() + 1, x.getY());
	    }
	    else if (x.getX() > _player.getX()) {
		if (this.playerCanGo(x.getX() - 1, x.getY()))
		    x.setCoord(x.getX() - 1, x.getY());
	    }

	    if (x.getY() > _player.getY()) {
		if (this.playerCanGo(x.getX(), x.getY() - 1))
		    x.setCoord(x.getX(), x.getY() - 1);
	    }
	    else if (x.getY() < _player.getY()) {
		if (this.playerCanGo(x.getX(), x.getY() + 1))
		    x.setCoord(x.getX(), x.getY() + 1);
	    }
	}
    }

    public void moveEnnemy2(Ennemy x) {
	if (!x.isDead()) {
	    if ((Math.abs(x.getX() - _player2.getX()) == 1 && x.getY() == _player2.getY())
		|| (Math.abs(x.getY() - _player2.getY()) == 1 && x.getX() == _player2.getX())) {
		this.attack(x);
		return;
	    }

	    if (x.getX() < _player2.getX()) {
		if (this.playerCanGo(x.getX() + 1, x.getY()))
		    x.setCoord(x.getX() + 1, x.getY());
	    }
	    else if (x.getX() > _player2.getX()) {
		if (this.playerCanGo(x.getX() - 1, x.getY()))
		    x.setCoord(x.getX() - 1, x.getY());
	    }

	    if (x.getY() > _player2.getY()) {
		if (this.playerCanGo(x.getX(), x.getY() - 1))
		    x.setCoord(x.getX(), x.getY() - 1);
	    }
	    else if (x.getY() < _player2.getY()) {
		if (this.playerCanGo(x.getX(), x.getY() + 1))
		    x.setCoord(x.getX(), x.getY() + 1);
	    }
	}
    }

    public Player whatPlayerIsHere(int row, int column) {
	if (this.playerIsHere(row, column))
	    return _player;
	else if (this.player2IsHere(row, column))
	    return _player2;
	return null;
    }

    public boolean fightableIsHere(int row, int column) {
	return (this.aPlayerIsHere(row, column) || this.ennemyIsHere(row, column) != null);
    }

    public boolean aPlayerIsHere(int row, int column) {
	return (this.playerIsHere(row, column) || this.player2IsHere(row, column));
    }

    public boolean playerIsHere(int row, int column) {
	return (_player.getX() == row && _player.getY() == column);
    }

    public boolean player2IsHere(int row, int column) {
	return (_player2.getX() == row && _player2.getY() == column);
    }

    public Ennemy ennemyIsHere(int row, int column) {
	for (Ennemy x : _ennemies)
	    if (x.getX() == row && x.getY() == column && !x.isDead())
		return x;
	return null;
    }

    public boolean playerCanGo(int row, int column) {
	return (_world[row][column].isWalkable() && this.ennemyIsHere(row, column) == null && !this.aPlayerIsHere(row, column));
    }

    public boolean canMovePlayerUp() {
        return (this.playerCanGo(_player.getX() - 1, _player.getY()));
    }

    public boolean canMovePlayerDown() {
        return (this.playerCanGo(_player.getX() + 1, _player.getY()));
    }

    public boolean canMovePlayerLeft() {
        return (this.playerCanGo(_player.getX(), _player.getY() - 1));
    }

    public boolean canMovePlayerRight() {
	return (this.playerCanGo(_player.getX(), _player.getY() + 1));
    }

    public void movePlayerUp() {
	_player.setCoord(_player.getX() - 1, _player.getY());
    }

    public void movePlayerDown() {
	_player.setCoord(_player.getX() + 1, _player.getY());
    }

    public void movePlayerLeft() {
	_player.setCoord(_player.getX(), _player.getY() - 1);
    }

    public void movePlayerRight() {
	_player.setCoord(_player.getX(), _player.getY() + 1);
    }

    public boolean playerWon() {
	return (_player.getKeys().size() >= 4 || _player2.isDead());
    }

    public boolean canMovePlayer2Up() {
        return (this.playerCanGo(_player2.getX() - 1, _player2.getY()));
    }

    public boolean canMovePlayer2Down() {
        return (this.playerCanGo(_player2.getX() + 1, _player2.getY()));
    }

    public boolean canMovePlayer2Left() {
        return (this.playerCanGo(_player2.getX(), _player2.getY() - 1));
    }

    public boolean canMovePlayer2Right() {
	return (this.playerCanGo(_player2.getX(), _player2.getY() + 1));
    }

    public void movePlayer2Up() {
	_player2.setCoord(_player2.getX() - 1, _player2.getY());
    }

    public void movePlayer2Down() {
	_player2.setCoord(_player2.getX() + 1, _player2.getY());
    }

    public void movePlayer2Left() {
	_player2.setCoord(_player2.getX(), _player2.getY() - 1);
    }

    public void movePlayer2Right() {
	_player2.setCoord(_player2.getX(), _player2.getY() + 1);
    }

    public boolean player2Won() {
	return (_player2.getKeys().size() >= 4 || _player.isDead());
    }

    public void print()
    {
        int i;
        int j;

        i = 0;
        while (i < _world.length) {
            j = 0;
            while (j < _world[i].length) {
		if (this.playerIsHere(i, j))
		    System.out.print(_player.showAs());
		else if (this.player2IsHere(i, j))
		    System.out.print(_player2.showAs());
		else if (this.ennemyIsHere(i, j) != null)
		    System.out.print(this.ennemyIsHere(i, j).showAs());
		else
                    System.out.print(_world[i][j].showAs());
                j++;
            }
            System.out.println("");
            i++;
        }
    }

    public static List<Ennemy> cloneEnnemies(List<Ennemy> ennemies) {
	List<Ennemy> copy = new ArrayList<Ennemy>();

	for (Ennemy x : ennemies)
	    copy.add(x.clone());
	return copy;
    }

    public WorldMulti clone() {
	WorldMulti copy = new WorldMulti(_nbRow, _nbColumn, _filename);

	copy.setEnnemies(WorldMulti.cloneEnnemies(_ennemies));
	copy.setPlayer(_player.clone());
	copy.setPlayer2(_player2.clone());
	return copy;
    }
}
