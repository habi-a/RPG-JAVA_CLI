import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

public class World
{
    private String _filename;
    private int _nbColumn;
    private int _nbRow;
    private Player _player;
    private List<Ennemy> _ennemies;
    private Cell[][] _world;
    private CellFactory _cellFactory;

    public World(int nbRow, int nbColumn, String filename) {
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
    
    public World(String filename, int nbRow, int nbColumn, String playerName, int xPlayer,
		 int yPlayer, char symbolPlayer, HealthBar healthBar, List<Ennemy> ennemies)
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

	for (int row = 0; scanner.hasNextLine() && row < nbRow; row++) {
	    char[] chars = scanner.nextLine().toCharArray();
	    for (int column = 0; column < nbColumn && column < chars.length; column++)
		_world[row][column] = _cellFactory.getCell(chars[column]);
	}
    }

    public void setPlayer(Player player) {
	_player = player;
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
	    if (this.playerIsHere(player.getX() - 1, _player.getY()))
		return true;
	    if (this.playerIsHere(player.getX() + 1, _player.getY()))
		return true;
	    if (this.playerIsHere(player.getX(), _player.getY() - 1))
		return true;
	    if (this.playerIsHere(player.getX(), _player.getY() + 1))
		return true;
	}
	
	else if (player instanceof Player) {
	    Player tmp = (Player)player;
	    if (tmp.getWeapons().size() == 0) {
		System.out.println("\033[31mPlayer don't have weapon\033[0m");
		return false;
	    }
	    if (this.ennemyIsHere(player.getX() - 1, _player.getY()) != null)
		return true;
	    if (this.ennemyIsHere(player.getX() + 1, _player.getY()) != null)
		return true;
	    if (this.ennemyIsHere(player.getX(), _player.getY() - 1) != null)
		return true;
	    if (this.ennemyIsHere(player.getX(), _player.getY() + 1) != null)
		return true;
	}
	return false;
    }

    public void attack(Fightable player) {
	List<Fightable> fightables = new ArrayList<Fightable>();

	if (player instanceof Ennemy) {
	    if (this.playerIsHere(player.getX() - 1, player.getY()))
		fightables.add(_player);
	    else if (this.playerIsHere(player.getX() + 1, player.getY()))
                fightables.add(_player);
	    else if (this.playerIsHere(player.getX(), player.getY() - 1))
                fightables.add(_player);
	    else if (this.playerIsHere(player.getX(), player.getY() + 1))
                fightables.add(_player);
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
	Weapon wToAdd = null;
	Key toAdd = null;
	Reaction reaction = null;
	boolean ok = false;
	
	if (_world[_player.getX()][_player.getY()].getTangible() instanceof Weapon) {
	    wToAdd = (Weapon)_world[_player.getX()][_player.getY()].getTangible();
	    if (_player.addWeapon(wToAdd))
		ok = true;
	}
	
	else if (_world[_player.getX()][_player.getY()].getTangible() instanceof Key) {
	    toAdd = (Key)_world[_player.getX()][_player.getY()].getTangible();
	    _player.addKey(toAdd);
	    ok = true;
	}

	else if (_world[_player.getX()][_player.getY()].getTangible() instanceof Reaction) {
	    MiniGameRunner runner = new MiniGameRunner(_player.getKeys());
	    reaction = (Reaction)_world[_player.getX()][_player.getY()].getTangible();
	    if ((toAdd = runner.playGame(reaction.getMiniGame())) != null) {
		_player.addKey(toAdd);
		ok = true;
	    }
	}

	if (ok) {
	    _world[_player.getX()][_player.getY()] = null;
	    _world[_player.getX()][_player.getY()] = new Cell(new EmptyTangible());
	}
    }

    public void moveEnnemies() {
	for (Ennemy x : _ennemies) {
	    if (!x.isDead()) {
		if ((Math.abs(x.getX() - _player.getX()) == 1 && x.getY() == _player.getY())
		    || (Math.abs(x.getY() - _player.getY()) == 1 && x.getX() == _player.getX())) {
		    this.attack(x);
		    break;
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
    }

    public boolean fightableIsHere(int row, int column) {
	return (this.playerIsHere(row, column) || this.ennemyIsHere(row, column) != null);
    }
    
    public boolean playerIsHere(int row, int column) {
	return (_player.getX() == row && _player.getY() == column);
    }

    public Ennemy ennemyIsHere(int row, int column) {
	for (Ennemy x : _ennemies)
	    if (x.getX() == row && x.getY() == column && !x.isDead())
		return x;
	return null;
    }
    
    public boolean playerCanGo(int row, int column) {
	return (_world[row][column].isWalkable() && this.ennemyIsHere(row, column) == null && !this.playerIsHere(row, column));
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
	return (_player.getKeys().size() >= 4);
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
		    System.out.print("\033[36m" + _player.showAs() + "\033[0m");
		else if (this.ennemyIsHere(i, j) != null)
		    System.out.print("\033[31m" + this.ennemyIsHere(i, j).showAs() + "\033[0m");
		else
                    System.out.print(_world[i][j].showAs());
                j++;
            }
            System.out.println("");
            i++;
        }
    }

    public List<Ennemy> cloneEnnemies() {
	List<Ennemy> copy = new ArrayList<Ennemy>();

	for (Ennemy x : _ennemies)
	    copy.add(x.clone());
	return copy;
    }

    public World clone() {
	World copy = new World(_nbRow, _nbColumn, _filename);

	copy.setEnnemies(this.cloneEnnemies());
	copy.setPlayer(_player.clone());
	return copy;
    }
}
