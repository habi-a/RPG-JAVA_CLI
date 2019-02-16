import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Ennemy extends Fightable implements Tangible, Serializable
{
    private int _scoreReward;
    private Weapon _weapon;
    private char _symbol;

    public Ennemy() {}
    
    public Ennemy(String name, int x, int y, char symbol, Weapon weapon,
		  int scoreReward, HealthBar healthBar)
    {
	super(x, y, name, healthBar);
	_scoreReward = scoreReward;
	_weapon = weapon;
        _symbol = symbol;
    }

    public Weapon getWeapon() {
	return _weapon;
    }

    public void setWeapon(Weapon weapon) {
	_weapon = weapon;
    }

    public int getScoreReward() {
	return (_scoreReward);
    }

    public void setScoreReward(int scoreReward) {
	_scoreReward = scoreReward;
    }

    public char getSymbol() {
        return (_symbol);
    }

    public void setSymbol(char symbol) {
        _symbol = symbol;
    }

    @Override
    public char showAs() {
        return (_symbol);
    }

    public Ennemy clone() {
	Ennemy copy = new Ennemy();

	copy.setName(_name);
	copy.setCoord(_x, _y);
	copy.setWeapon(_weapon);
	copy.setSymbol(_symbol);
	copy.setScoreReward(_scoreReward);
	copy.setHealthBar(_healthBar.clone());
	return copy;
    }
}
