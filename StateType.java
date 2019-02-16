public enum StateType {
    MENU(0),
    ARCADE(1),
    MULTIPLAYER(2),
    GAME_SERVER(3),
    GAME_CLIENT(4),
    WIN_STATE(5),
    LOSE_STATE(6),
    QUIT(7);

    private final int _stateTypeCode;

    private StateType(int stateTypeCode) {
	_stateTypeCode = stateTypeCode;
    }
    
    public int toInt() {
	return (_stateTypeCode);
    }
}
