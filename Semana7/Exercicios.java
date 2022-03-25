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
//**************//
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
  
// ***********************************************************************************//

  

// ********************************************** A Java program to demonstrate working of synchronized ************************//
//** da pra realizar sincronização no método (Object level lock), sincronização no bloco (Object level lock) e sincronização a nível de classe(Class level lock)  ****// 
//** Nesse exemplo iremos sincronizar o bloco ******************/
	
import java.io.*;
import java.util.*;

//*********** A Class used to send a message class Sender *****************//
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

//***************** Class for send a message using Threads *********************************//
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
    //************************************************ TESTE aqui removendo o sincronized e executando novamente e veja o resultado **********************//
		synchronized(sender)
		{
			// synchronizing the send object
			sender.send(msg);
		}
	}
}

// ************************** Driver class class SyncDemo *********************************************//
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
//***************************** SAÍDA SEM Sinchronized ****************************************//
Sending	 Bye 
Sending	 Hi 

 Bye Sent

 Hi Sent

//************************ MAIS EXEMPLOS DE APLICAÇÃO DE SINCRONIZAÇÃO ENTRE THREADS **********************//

//** Nesse exemplo iremos sincronizar o método ******************/
// ************************** Ecommerce *******************************************//
package Semana7;

public class Ecommerce{
    public static void main(String args[]){
        SharedProductResource sharedResource = new SharedProductResource();
        BuyPen buyPen = new BuyPen(sharedResource);
        BuyBook buyBook = new BuyBook(sharedResource);

        Thread student1 = new Thread(buyBook, "student1");
        Thread student2 = new Thread(buyBook, "student2");
        Thread student3 = new Thread(buyPen, "student3");
        student1.start();
        student2.start();
        student3.start();
    }
}

//*******************
package Semana7;

import java.util.HashMap;
import java.util.Map;

class SharedProductResource{
	private Map<String, Integer> products = new HashMap<String, Integer>();
	public SharedProductResource(){
		products.put("PEN", 10);
		products.put("BOOK", 1);
		products.put("CYCLE", 2);
		products.put("CAMERA", 5);
		products.put("COAT", 1);

		
	}
	// ************************** Testes aqui de sincronização ********************************//
	public  String buyProduct(String key){
        //Esse if está na seção crítica...
		if (products.containsKey(key)){
			Integer quantity = products.get(key);
			if(!quantity.equals(0)){
				products.put(key,(quantity-1));
				return "Processing Successful. "+key+" is out for delivery to "+Thread.currentThread().getName();
				
			}
		}
		return "Oops Product go out of stock. Sorry "+ Thread.currentThread().getName();
	}
        
}
//*******************************
package Semana7;

//classe compraDeLivros
class BuyBook implements Runnable{
    SharedProductResource sharedResource = null;
    public BuyBook(SharedProductResource sharedResource){
        this.sharedResource = sharedResource;
    }
    public void run(){
        System.out.println("Buy book-> "+sharedResource.buyProduct("BOOK"));
    }
}
//******************************
package Semana7;
//Classe compraDeCanetas
class BuyPen implements Runnable{
    SharedProductResource sharedResource = null;
    public BuyPen(SharedProductResource sharedResource){
        this.sharedResource = sharedResource;
    }
    public void run(){
        System.out.println("Buy pen-> "+sharedResource.buyProduct("PEN"));
    }
}

// *************Resultado sem synchronized ********************************************//
Buy book-> Processing Successful. BOOK is out for delivery to student1
Buy book-> Processing Successful. BOOK is out for delivery to student2
Buy pen-> Processing Successful. PEN is out for delivery to student3


// **************Resultado com synchronized *********************************************//

Buy book-> Oops Product go out of stock. Sorry student1
Buy book-> Processing Successful. BOOK is out for delivery to student2
Buy pen-> Processing Successful. PEN is out for delivery to student3


//************* Sistema de votação *************************//

class votingCounterRunnable implements Runnable{
    private int counter = 0;
    public int getCounter(){
        return counter;
    }
    public void setCounter(int counter){
        this.counter = counter;
    }
    public void run(){
        System.out.println(Thread.currentThread().getName()+
            " antes de incrementar o contador - "+getCounter());
            //seção crítica
        setCounter(getCounter()+1);
        System.out.println(Thread.currentThread().getName()+ 
        " Depois de incrementar o contador - "+getCounter());
    }
}
//*************************
public class ReadModifyWriteMain{
    public static void main(String args[]){
        votingCounterRunnable counterRunnable = new votingCounterRunnable();
        Thread state1 = new Thread(counterRunnable, "State1");
        Thread state2 = new Thread(counterRunnable, "State2");
        Thread state3 = new Thread(counterRunnable, "State3");
        Thread state4 = new Thread(counterRunnable, "State4");
        Thread state5 = new Thread(counterRunnable, "State5");
        state1.start();
        state2.start();
        state3.start();
        state4.start();
        state5.start();

    }
}
//************** Saída sem synchronized **************************
State3 antes de incrementar o contador - 0
State5 antes de incrementar o contador - 0
State2 antes de incrementar o contador - 0
State1 antes de incrementar o contador - 0
State4 antes de incrementar o contador - 0
State1 Depois de incrementar o contador - 4
State2 Depois de incrementar o contador - 3
State5 Depois de incrementar o contador - 2
State3 Depois de incrementar o contador - 1
State4 Depois de incrementar o contador - 5

//************************com synchronized *****************************
State1 antes de incrementar o contador - 0
State2 antes de incrementar o contador - 0
State1 Depois de incrementar o contador - 1
State2 Depois de incrementar o contador - 2
State4 antes de incrementar o contador - 1
State4 Depois de incrementar o contador - 3
State5 antes de incrementar o contador - 2
State3 antes de incrementar o contador - 2
State5 Depois de incrementar o contador - 4
State3 Depois de incrementar o contador - 5
	
//***********************************

class MyRunnable1 implements Runnable{
    public void run(){
        for (int i = 0; i < 5; i++){
            System.out.println(Thread.currentThread().getName()+ " Number "+i);
        }
    }
}
//************
package Semana7;

public class ThreadInterleavingMain{
    public static void main(String args[]){
        Thread thread1 = new Thread(new MyRunnable1());
        Thread thread2 = new Thread(new MyRunnable1());
        thread1.start();
        thread2.start();
    }
}
//*****************Saída sem sincronia ****************************
Thread-1 Number 0
Thread-0 Number 0
Thread-1 Number 1
Thread-0 Number 1
Thread-1 Number 2
Thread-0 Number 2
Thread-1 Number 3
Thread-0 Number 3
Thread-1 Number 4
Thread-0 Number 4
//********************Com sincronia veja código-fonte mais abaixo MyRunnable2 e ThreadInterleavingMain2**************************
Thread 1 Number 0
Thread 1 Number 1
Thread 1 Number 2
Thread 1 Number 3
Thread 1 Number 4
Thread 2 Number 0
Thread 2 Number 1
Thread 2 Number 2
Thread 2 Number 3
Thread 2 Number 4
//********Com sincronia
class MyRunnable2 implements Runnable{
    public void run(){
        MyRunnable2.print();
    }
    public static synchronized void print(){
        for (int i = 0; i < 5; i++){
            System.out.println(Thread.currentThread().getName()+ " Number "+i);
        }
    }
}
//************************
public class ThreadInterleavingMain2{
        public static void main(String args[]){
            Thread thread1 = new Thread(new MyRunnable(), "Thread 1");
            Thread thread2 = new Thread(new MyRunnable(), "Thread 2");
            thread1.start();
            thread2.start();
        }
    }





