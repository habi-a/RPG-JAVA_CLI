import java.util.List;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
	Menu menu = new Menu();
	Multiplayer multiplayer = new Multiplayer();
	WinState winState = new WinState();
	LoseState loseState = new LoseState();
	GameClient gameClient = new GameClient();
	List<Ennemy> ennemies = new ArrayList<Ennemy>();
	Ennemy ennemy1 = new Ennemy("Ennemy1", 4, 7, 'x', new Sword("Sword", Util.damageSword), 500, new HealthBar("HealthBar", 300, 300));
	Ennemy ennemy2 = new Ennemy("Ennemy2", 4, 32, 'x', new Sword("Sword", Util.damageSword), 500, new HealthBar("HealthBar", 300, 300));
	ennemies.add(ennemy1);
	ennemies.add(ennemy2);
	GameArcade gameArcade = new GameArcade(new World("./Map.txt", 20, 40, "Player1", 17, 20, 'i', new HealthBar("HealthBar", 500, 500), ennemies));
	GameServer gameServer = new GameServer(new WorldMulti("./Map.txt", 20, 40, "Player1", 17, 19, 'i', new HealthBar("HealthBar", 500, 500), 
							 "Player2", 17, 21, 'j', new HealthBar("HealthBar", 500, 500), WorldMulti.cloneEnnemies(ennemies)));
	StateType currentState = StateType.MENU;
	List<State> states = new ArrayList<State>();
	states.add(menu);
	states.add(gameArcade);
	states.add(multiplayer);
	states.add(gameServer);
	states.add(gameClient);
	states.add(winState);
	states.add(loseState);
	Util.clearScreen();
	
	while (currentState != StateType.QUIT)
	    currentState = states.get(currentState.toInt()).run();
    }
}
