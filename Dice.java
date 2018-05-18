public class Dice {
	public int roll() {
		int one = (int)(Math.random()*6) + 1;
		int two = (int)(Math.random()*6) + 1;
		return one + two;
	}
}
