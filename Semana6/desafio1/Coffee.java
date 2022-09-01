package semana6;

public class Coffee {
	static Coffee pourCoffee() {
		System.out.println("Pouring(fazendo) coffee");
		return new Coffee();
	}
}
