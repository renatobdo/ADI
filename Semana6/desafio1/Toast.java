package semana6;

public class Toast {
	static Toast ToastBread(int slices) throws InterruptedException {
		for (int slice = 0; slice < slices; slice++) {
			System.out.println("Putting a slice of bread in the toaster");
		}
		System.out.println("Start toasting...");
		Thread.sleep(3000);
		System.out.println("Remove toast from toaster");

		return new Toast();
	}
}
