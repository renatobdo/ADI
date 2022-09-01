package semana6;

public class Bacon {
	static Bacon fryBacon(int slices) throws InterruptedException
    {
        System.out.println("putting "+slices +" slices of bacon in the pan");
        System.out.println("cooking first side of bacon...");
        Thread.sleep(3000);
        for (int slice = 0; slice < slices; slice++)
        {
            System.out.println("flipping a slice of bacon");
        }
        System.out.println("cooking the second side of bacon...");
        Thread.sleep(3000);
        System.out.println("Put bacon on plate");

        return new Bacon();
    }
}
