//***************** Java code for thread creation by extending the Thread class *******************************//

class MultithreadingDemo extends Thread {
	public void run()
	{
		try {
			// Displaying the thread that is running
			System.out.println(
				"Thread " + Thread.currentThread().getId()
				+ " is running");
		}
		catch (Exception e) {
			// Throwing an exception
			System.out.println("Exception is caught");
		}
	}
}

// Main Class
public class Multithread {
	public static void main(String[] args)
	{
		int n = 8; // Number of threads
		for (int i = 0; i < n; i++) {
			MultithreadingDemo object
				= new MultithreadingDemo();
			object.start();
		}
	}
}
//**************SAÍDA *************************************//
Thread 12 is running
Thread 19 is running
Thread 18 is running
Thread 17 is running
Thread 16 is running
Thread 15 is running
Thread 14 is running
Thread 13 is running


//****************** Java code for thread creation by implementing the Runnable Interface ********************************//

class MultithreadingDemo implements Runnable {
	public void run()
	{
		try {
			// Displaying the thread that is running
			System.out.println(
				"Thread " + Thread.currentThread().getId()
				+ " is running");
		}
		catch (Exception e) {
			// Throwing an exception
			System.out.println("Exception is caught");
		}
	}
}

// Main Class
class Multithread {
	public static void main(String[] args)
	{
		int n = 8; // Number of threads
		for (int i = 0; i < n; i++) {
			Thread object
				= new Thread(new MultithreadingDemo());
			object.start();
		}
	}
}
//**************SAÍDA *************************************//
Thread 13 is running
Thread 11 is running
Thread 12 is running
Thread 15 is running
Thread 14 is running
Thread 18 is running
Thread 17 is running
Thread 16 is running


//********************** Java program to demonstrate thread states  ***************************************//
    1 - New
    2 - Runnable
    3 - Blocked
    4 - Waiting
    5 - Timed Waiting
    6 - Terminated
//**********************************************************************************************************//
class thread implements Runnable {
	public void run()
	{
		// moving thread2 to timed waiting state
		try {
			Thread.sleep(1500);
		}
		catch (InterruptedException e) {
			e.printStackTrace();
		}

		System.out.println(
			"State of thread1 while it called join() method on thread2 -"
			+ Test.thread1.getState());
		try {
			Thread.sleep(200);
		}
		catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
//**************
public class Test implements Runnable {
	public static Thread thread1;
	public static Test obj;

	public static void main(String[] args)
	{
		obj = new Test();
		thread1 = new Thread(obj);

		// thread1 created and is currently in the NEW
		// state.
		System.out.println(
			"State of thread1 after creating it - "
			+ thread1.getState());
		thread1.start();

		// thread1 moved to Runnable state
		System.out.println(
			"State of thread1 after calling .start() method on it - "
			+ thread1.getState());
	}

	public void run()
	{
		thread myThread = new thread();
		Thread thread2 = new Thread(myThread);

		// thread1 created and is currently in the NEW
		// state.
		System.out.println(
			"State of thread2 after creating it - "
			+ thread2.getState());
		thread2.start();

		// thread2 moved to Runnable state
		System.out.println(
			"State of thread2 after calling .start() method on it - "
			+ thread2.getState());

		// moving thread1 to timed waiting state
		try {
			// moving thread1 to timed waiting state
			Thread.sleep(200);
		}
		catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println(
			"State of thread2 after calling .sleep() method on it - "
			+ thread2.getState());

		try {
			// waiting for thread2 to die
			thread2.join();
		}
		catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println(
			"State of thread2 when it has finished it's execution - "
			+ thread2.getState());
	}
}

// ************************ SAÍDA ***********************************//
State of thread1 after creating it - NEW
State of thread1 after calling .start() method on it - RUNNABLE
State of thread2 after creating it - NEW
State of thread2 after calling .start() method on it - RUNNABLE
State of thread2 after calling .sleep() method on it - TIMED_WAITING
State of thread1 while it called join() method on thread2 -WAITING
State of thread2 when it has finished it's execution - TERMINATED
//  
// ***********************************************************************************

  

// ********************************************** A Java program to demonstrate working of synchronized ************************//
// 
import java.io.*;
import java.util.*;

// A Class used to send a message
class Sender
{
	public void send(String msg)
	{
		System.out.println("Sending\t" + msg );
		try
		{
			Thread.sleep(1000);
		}
		catch (Exception e)
		{
			System.out.println("Thread interrupted.");
		}
		System.out.println("\n" + msg + "Sent");
	}
}

// Class for send a message using Threads
class ThreadedSend extends Thread
{
	private String msg;
	Sender sender;

	// Receives a message object and a string
	// message to be sent
	ThreadedSend(String m, Sender obj)
	{
		msg = m;
		sender = obj;
	}

	public void run()
	{
		// Only one thread can send a message
		// at a time.
    //************************************************ TESTE aqui removendo o sincronized e executando novamente e veja o resultado **********************
		synchronized(sender)
		{
			// synchronizing the send object
			sender.send(msg);
		}
	}
}

// Driver class
class SyncDemo
{
	public static void main(String args[])
	{
		Sender send = new Sender();
		ThreadedSend S1 =
			new ThreadedSend( " Hi " , send );
		ThreadedSend S2 =
			new ThreadedSend( " Bye " , send );

		// Start two threads of ThreadedSend type
		S1.start();
		S2.start();

		// wait for threads to end
		try
		{
			S1.join();
			S2.join();
		}
		catch(Exception e)
		{
			System.out.println("Interrupted");
		}
	}
}

//*************************** SAÍDA COM Sinchronized ***************************************//
Sending     Hi 

 Hi Sent
Sending     Bye 

 Bye Sent
***************************** SAÍDA SEM Sinchronized ****************************************//
Sending	 Bye 
Sending	 Hi 

 Bye Sent

 Hi Sent
