public class Util {
    public final static int damageSword = 150;
    public final static int damageKatana = 300;

    public static void clearScreen() {
        System.out.print("\033[H\033[2J");
	System.out.flush();
    }
}
