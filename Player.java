import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Player extends Fightable implements Tangible, Serializable
{
    private int _score;
    private int _currentWeapon;
    private List<Weapon> _weapons;
    private List<Key> _keys;
    private char _symbol;

    public Player() {}
    
    public Player(String name, int x, int y, char symbol, HealthBar healthBar)
    {
	super(x, y, name, healthBar);
	_score = 0;
	_currentWeapon = 0;
        _symbol = symbol;
	_weapons = new ArrayList<Weapon>();
	_keys = new ArrayList<Key>();
    }

    public int getWeaponNumber() {
	return (_currentWeapon);
    }

    public void setWeaponNumber(int currentWeapon) {
	_currentWeapon = currentWeapon;
    }

    public boolean addWeapon(Weapon weapon) {
	for (Weapon x : _weapons)
	    if (x.getName() == weapon.getName())
		return false;
	_weapons.add(weapon);
	return true;	    
    }

    public void removeWeapon(Weapon weapon) {
	_weapons.remove(weapon);
    }

    public void dropCurrentWeapon() {
	this.removeWeapon(getCurrentWeapon());
	_currentWeapon = 0;
    }

    public List<Weapon> getWeapons() {
	return (_weapons);
    }

    public Weapon getCurrentWeapon() {
	if (_weapons.size() == 0)
	    return null;
	else
	    return (_weapons.get(_currentWeapon));
    }

    public void setKeys(List<Key> keys) {
	_keys = keys;
    }

    public void setWeapons(List<Weapon> weapons) {
	_weapons = weapons;
    }
    
    public int getScore() {
	return (_score);
    }

    public void addScore(int score) {
	_score += score;
    }

    public void setScore(int score) {
	_score = score;
    }

    public void addKey(Key key) {
	for (Key x : _keys)
	    if (x.getName().equals(key.getName()))
		return;
	_keys.add(key);
    }

    public List<Key> getKeys() {
	return (_keys);
    }

    public char getSymbol() {
        return (_symbol);
    }

    public void setSymbol(char symbol) {
        _symbol = symbol;
    }

    @Override
    public char showAs()
    {
        return (_symbol);
    }

    public Player clone() {
	Player copy = new Player();

	copy.setName(_name);
	copy.setCoord(_x, _y);
	copy.setSymbol(_symbol);
	copy.setScore(_score);
	copy.setWeaponNumber(_currentWeapon);
	copy.setKeys(new ArrayList<Key>(_keys));
	copy.setWeapons(new ArrayList<Weapon>(_weapons));
	copy.setHealthBar(_healthBar.clone());
	return copy;
    }
}
