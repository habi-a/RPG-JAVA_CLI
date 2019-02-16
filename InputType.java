public enum InputType {
    UP(0),
    DOWN(1),
    LEFT(2),
    RIGHT(3),
    TAKE(4),
    USE_WEAPON(5),
    SWITCH_WEAPON(6),
    DROP_WEAPON(7),
    SHOW_SCOREBAR(8),
    QUIT(9);

    private final int _inputTypeCode;

    private InputType(int inputTypeCode) {
	_inputTypeCode = inputTypeCode;
    }

    public int getInputTypeCode() {
	return (_inputTypeCode);
    }
}
