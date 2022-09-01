package semana6;

public class Egg {
	static Egg fryEggs(int howMany) throws InterruptedException {
		System.out.println("Warming the egg pan...");
		Thread.sleep(3000);
		System.out.println("cracking "+howMany+" eggs");
		System.out.println("cooking the eggs ...");
		Thread.sleep(3000);
		System.out.println("Put eggs on plate");

		return new Egg();
	}
}
