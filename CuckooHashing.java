package sxa190016;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Scanner;

/**
 * @author sxa190016
 * @author bsv180000
 * @version 1.0 Cuckoo Hashing: Short project 8
 * 				Generic Hashing algorithm which is assured to 
 * 				give a worst-case time complexity of O(1),
 * 				implemented using 2 arrays & 1 java Hashtable
 * 				using a prime number hash function.
 */
public class CuckooHashing {
	
	/**
	 * The maximum number of elements that can be stored in the two arrays
	 */
	@SuppressWarnings("unused")
	private int n;
	
	/**
	 * Stores the maximum fill factor of the 2 arrays
	 */
	@SuppressWarnings("unused")
	private double load_factor;
	
	/**
	 * The number of replacements after which a cycle is detected
	 */
	private double threshold;
	
	/**
	 * Hash array 1
	 */
	private Integer [] arr1;
	
	/**
	 * Hash array 2
	 */
	private Integer [] arr2;
	
	/**
	 * Secondary Hashtable to store elements in case of a cycle
	 */
	private Hashtable<Integer, String> secondaryTable;
	
	/**
	 * Hash value for array 1
	 */
	private int hash1;
	
	/**
	 * Hash value for array 2
	 */
	private int hash2;

	/**
	 * Generates a unique hash value based on the approximate number of elements to be stored
	 * 
	 * @param n		Approximate number of elements to be stored using this hash value
	 * @return		Generated hash value
	 */
	public static int getHashPrime(int n) {
		n = n%2==0 ? n+1 : n;
		while(!isHashPrime(n))
		{
			n += 2;
		}
		return n;
	}
	
	/**
	 * Check if the generated hash number is prime or not
	 * 
	 * @param number	Hash number to checked
	 * @return			True if the hash is prime else false
	 */
	private static boolean isHashPrime(int number) 
	{
		for(int i=2; i*i < number; i++)
		{
			if(number%i == 0)
			{
				return false;
			}
		}
		return true;
	}

	/**
	 * Constructor which initializes the private member variables
	 * 
	 * @param n				The maximum number of elements to be stored
	 * @param load_factor	The maximum fill factor of the arrays
	 */
	public CuckooHashing(int n, double load_factor)
	{
		this.n = n;
		this.load_factor = load_factor;
		this.threshold = Math.log10(n);
		int totalStorage = (int) Math.ceil(n/load_factor);
		this.hash1 = CuckooHashing.getHashPrime(totalStorage/2);
		this.hash2 = totalStorage-hash1;
		this.arr1 = new Integer[hash1];
		this.arr2 = new Integer[hash2];
		this.secondaryTable = new Hashtable<Integer, String>();		
	}

	/**
	 * Get the hashcode for array 1
	 * 
	 * @param num	The element for which hashcode is to be calculated
	 * @return		The hashcode for num
	 */
	public int getHash1(int num)
	{
		return num % this.hash1;
	}

	/**
	 * Get the hashcode for array 2
	 * 
	 * @param num	The element for which hashcode is to be calculated
	 * @return		The hashcode for num
	 */
	public int getHash2(int num)
	{
		return num % this.hash2;		
	}

	/**
	 * Helper method to print the location at which each element is stored
	 */
	public void printStorage()
	{
		for(int i=0; i<this.arr1.length; i++)
		{
			System.out.println(String.format("%03d", i)+"   "+String.format("%5s", this.arr1[i])+"   "+String.format("%5s", this.arr2[i]));
		}
		System.out.println(this.secondaryTable);
		System.out.println();
	}

	/**
	 * Add an element to the Cuckoo Hash tables
	 * 
	 * @param num	The element to be stored in the hash tables
	 * @return		True if the operation is successful else false
	 */
	public boolean add(int num)
	{
		/**
		 * The previous hash table from which the element is replaced 
		 */
		int prev_table = 0;
		
		/**
		 * The current element to be stored at it's correct location
		 */
		Integer toPlace = num;
		
		/**
		 * The number of replaced already occurred
		 */
		int replacements = 0;

		//While the element is not placed and the replacements have not exceeded threshold value
		while(toPlace!=null && replacements<=this.threshold)
		{
			/**
			 * temporary variable used for temporary storage
			 */
			Integer temp;
			
			//If replaced from table1 place in table 2
			if(prev_table == 1)
			{
				prev_table = 2;
				int hash = this.getHash2(toPlace);
				temp = this.arr2[hash];
				this.arr2[hash] = toPlace;
			}
			//Else place in table 1
			else
			{
				prev_table = 1;
				int hash = this.getHash1(toPlace);
				temp = this.arr1[hash];
				this.arr1[hash] = toPlace;
			}
			toPlace = temp;
			replacements++;
		}
		
		//If threshold exceeded place in secondary hashtable
		if(toPlace!=null)
		{
			this.secondaryTable.put(toPlace, "");
		}

		return true;
	}

	/**
	 * Remove an element from the hashtables
	 * 
	 * @param num	The element to be removed from the hashtables
	 * @return		True is successfully removed else false
	 */
	public boolean remove(int num)
	{
		/**
		 * variable to store the result of the operation
		 */
		boolean result  = false;
		
		/**
		 * value of hashcode 1 for num
		 */
		int hash1 = this.getHash1(num);
		
		/**
		 * Current element in the correct location in hashtable 1
		 */
		Integer val1 = this.arr1[hash1];
		
		//If the element is present in hashtable 1
		if(val1!=null && val1 == num)
		{
			this.arr1[hash1] = null;
			result = true;
		}
		else
		{
			/**
			 * value of hashcode 2 for num
			 */
			int hash2 = this.getHash2(num);
			
			/**
			 * Current element in the correct location in hashtable 2
			 */
			Integer val2  = this.arr2[hash2];
			
			//If the element is present in hashtable 2
			if(val2!=null && val2 == num)
			{
				this.arr2[hash2] = null;
				result =  true;
			}
			else
			{
				//If the element is present in secondary hashtable
				if(this.secondaryTable.remove(num)!=null)
				{
					result  = true;
				}
			}			
		}		
		return result;
	}

	/**
	 * Check if the element if present in the hashtables
	 * 
	 * @param num	Element to be checked if present
	 * @return		True if found else false
	 */
	public boolean contains(int num)
	{		
		/**
		 * Element in hashtable 1
		 */
		Integer arr1Val = this.arr1[this.getHash1(num)];
		
		/**
		 * Element in hashtable 2
		 */
		Integer arr2Val  = this.arr2[this.getHash2(num)];
		
		//Return true is element present in hashtable 1, hashtable 2 or secondary hashtable
		return (arr1Val!=null && arr1Val==num) || (arr2Val!=null && arr2Val==num) || this.secondaryTable.containsKey(num);
	}
	
	/**
	 * Helper method to read random keys from a text file
	 * 
	 * @param n			Number of keys to be read from the file
	 * @param filePath	The complete path of the file in the system
	 * @return			An array of keys
	 * @throws FileNotFoundException	If file not found
	 */
	public static int[] getKeysFromFile(int n, String filePath) throws FileNotFoundException
	{
		/**
		 * File at the given path
		 */
		File arr = new File(filePath);
		
		/**
		 * Scanner to read the values
		 */
		Scanner in= new Scanner(arr);
		
		/**
		 * Array to store the keys
		 */
		int [] keys = new int [n];
		int i = 0;
		try
		{
			while(in.hasNext() && i<n)
			{
				keys[i++] = in.nextInt();
			}
		}
		finally
		{
			in.close();
		}		
		return keys;
	}
	
	/**
	 * Helper method to generate the keys automatically
	 * 
	 * @param n		The number of keys to be generated
	 * @return		An array of keys
	 */
	public static int[] generateKeys(int n) 
	{
		/**
		 * Array to store the keys
		 */
		int [] keys = new int [n];
		int i = 0;
		while(i<n)
		{
			keys[i] = i;
			i++;
		}
		return keys;
	}
	
	/**
	 * Helper method to print the elapsed time for each operation
	 * 
	 * @param arr	2D long array containing the elapsed times
	 */
	public static void printTimes(long [][] arr)
	{
		for(long[] x : arr)
		{
			for(long y : x)
			{
				System.out.print(y+" ");
			}
			System.out.println();
		}
	}
	
	/**
	 * Helper method to build the report in a user-friendly format
	 * 
	 * @param loadFactor	The fill-factor of the hashtables
	 * @param chTimes		2D long array containing the elapsed times for operations using CuckooHashing
	 * @param htTimes		2D long array containing the elapsed times for java Hashtable
	 * @param hmTimes		2D long array containing the elapsed times for java HashMap
	 * @param hsTimes		2D long array containing the elapsed times for java HashSet
	 */
	public static void buildReport(float loadFactor, long [][] chTimes, long [][] htTimes, long [][] hmTimes, long [][] hsTimes)
	{
		System.out.println();
		System.out.println("                      Operation : Add() & LoadFactor : "+loadFactor);	
		System.out.print(String.format("%15s", " "));	
		for(int n=1; n<=10; n++)
		{
			System.out.print(String.format("%6s", "n="+n+"M"));
		}
		System.out.println();
		System.out.print(String.format("%15s", "CuckooHashing"));
		for(int n=1; n<=10; n++)
		{
			System.out.print(String.format("%6s", chTimes[n-1][0]));
		}
		System.out.println();
		System.out.print(String.format("%15s", "Hashtable"));
		for(int n=1; n<=10; n++)
		{
			System.out.print(String.format("%6s", htTimes[n-1][0]));
		}
		System.out.println();
		System.out.print(String.format("%15s", "HashMap"));
		for(int n=1; n<=10; n++)
		{
			System.out.print(String.format("%6s", hmTimes[n-1][0]));
		}
		System.out.println();
		System.out.print(String.format("%15s", "HashSet"));
		for(int n=1; n<=10; n++)
		{
			System.out.print(String.format("%6s", hsTimes[n-1][0]));
		}
		System.out.println();	
		System.out.println();	
		
		System.out.println("                      Operation : Contains() & LoadFactor : "+loadFactor);	
		System.out.print(String.format("%15s", " "));	
		for(int n=1; n<=10; n++)
		{
			System.out.print(String.format("%6s", "n="+n+"M"));
		}
		System.out.println();
		System.out.print(String.format("%15s", "CuckooHashing"));
		for(int n=1; n<=10; n++)
		{
			System.out.print(String.format("%6s", chTimes[n-1][1]));
		}
		System.out.println();
		System.out.print(String.format("%15s", "Hashtable"));
		for(int n=1; n<=10; n++)
		{
			System.out.print(String.format("%6s", htTimes[n-1][1]));
		}
		System.out.println();
		System.out.print(String.format("%15s", "HashMap"));
		for(int n=1; n<=10; n++)
		{
			System.out.print(String.format("%6s", hmTimes[n-1][1]));
		}
		System.out.println();
		System.out.print(String.format("%15s", "HashSet"));
		for(int n=1; n<=10; n++)
		{
			System.out.print(String.format("%6s", hsTimes[n-1][1]));
		}
		System.out.println();	
		System.out.println();	
		
		System.out.println("                      Operation : Remove() & LoadFactor : "+loadFactor);	
		System.out.print(String.format("%15s", " "));	
		for(int n=1; n<=10; n++)
		{
			System.out.print(String.format("%6s", "n="+n+"M"));
		}
		System.out.println();
		System.out.print(String.format("%15s", "CuckooHashing"));
		for(int n=1; n<=10; n++)
		{
			System.out.print(String.format("%6s", chTimes[n-1][2]));
		}
		System.out.println();
		System.out.print(String.format("%15s", "Hashtable"));
		for(int n=1; n<=10; n++)
		{
			System.out.print(String.format("%6s", htTimes[n-1][2]));
		}
		System.out.println();
		System.out.print(String.format("%15s", "HashMap"));
		for(int n=1; n<=10; n++)
		{
			System.out.print(String.format("%6s", hmTimes[n-1][2]));
		}
		System.out.println();
		System.out.print(String.format("%15s", "HashSet"));
		for(int n=1; n<=10; n++)
		{
			System.out.print(String.format("%6s", hsTimes[n-1][2]));
		}
		System.out.println();	
	}

	/**
	 * main function to test the CuckooHashing class
	 * 
	 * @param args		Arguments to be passed to the main function
	 * @throws FileNotFoundException 
	 */
	public static void main(String[] args) throws FileNotFoundException {

		/**
		 * Initialize the timer
		 */
		Timer timer = new Timer();
		
		/**
		 * Set the number of test cases to be tested. Here we are testing 10 cases from 1M to 10M elements
		 */
		int totalTestFiles = 10;
		
		/*
		 * Initialize 2D long arrays for storing the operation time for CuckooHashing
		 */
		long [][] chTimes = new long[totalTestFiles][3];
		
		/*
		 * Initialize 2D long arrays for storing the operation time for Hashtable
		 */
		long [][] htTimes = new long[totalTestFiles][3];
		
		/*
		 * Initialize 2D long arrays for storing the operation time for HashMap
		 */
		long [][] hmTimes = new long[totalTestFiles][3];
		
		/*
		 * Initialize 2D long arrays for storing the operation time for HashSet
		 */
		long [][] hsTimes = new long[totalTestFiles][3];
		
		/*
		 * Load Factors of 0.5f, 0.75f, 0.9f are used for testing the performance of these hashing methods
		 */
		float [] loadFactorsArr = {0.5f, 0.75f, 0.9f};
		
		//Do for each load factor value
		for(float loadFactor : loadFactorsArr)
		{
			//Do for each test-case
			for(int n=1; n<=totalTestFiles; n++)
			{
				/*
				 * Uncomment the below line to read random values from a text file
				 */
//				int [] keys = getKeysFromFile(n*1000000, "C:\\Users\\shari\\eclipse-workspace\\SP8-CH\\src\\test\\10M.txt");
				
				/**
				 * Generate keys for each test case
				 */
				int [] keys = generateKeys(n*1000000);
				
				/*
				 * Initialize the Hashtable for testing the speed of it's operations
				 */
				Hashtable<Integer, String> ht = new Hashtable<Integer, String>(n*1000000, loadFactor); 
				
				//Test the add operation
				timer.start();
				for(int num : keys)
				{
				ht.put(num, "");
				}
				timer.end();
				htTimes[n-1][0] = timer.elapsedTime;
				
				//Test the contains operation
				timer.start();
				for(int num : keys)
				{
				if(!ht.containsKey(num))
				{
				System.out.println(num+" not found!");
				}
				}
				timer.end();
				htTimes[n-1][1] = timer.elapsedTime;
				
				//Test the remove operation
				timer.start();
				for(int num : keys)
				{
				if(ht.remove(num)==null)
				{
				System.out.println(num+" not removed!");
				}
				}
				timer.end();
				htTimes[n-1][2] = timer.elapsedTime;
				
				/**
				 * Initialize the HashMap for testing the speed of it's operations
				 */
				HashMap<Integer, String> hm = new HashMap<Integer, String>(n*1000000, loadFactor); 

				//Test the add operation
				timer.start();
				for(int num : keys)
				{
					hm.put(num, "");
				}
				timer.end();
				hmTimes[n-1][0] = timer.elapsedTime;

				//Test the contains operation
				timer.start();
				for(int num : keys)
				{
					if(!hm.containsKey(num))
					{
						System.out.println(num+" not found!");
					}
				}
				timer.end();
				hmTimes[n-1][1] = timer.elapsedTime;

				//Test the remove operation
				timer.start();
				for(int num : keys)
				{
					if(hm.remove(num)==null)
					{
						System.out.println(num+" not removed!");
					}
				}
				timer.end();
				hmTimes[n-1][2] = timer.elapsedTime;

				/**
				 * Initialize the HashSet for testing the speed of it's operations
				 */
				HashSet<Integer> hs = new HashSet<Integer>(n*1000000, loadFactor); 

				//Test the add operation
				timer.start();
				for(int num : keys)
				{
					hs.add(num);
				}
				timer.end();
				hsTimes[n-1][0] = timer.elapsedTime;

				//Test the contains operation
				timer.start();
				for(int num : keys)
				{
					if(!hs.contains(num))
					{
						System.out.println(num+" not found!");
					}
				}
				timer.end();
				hsTimes[n-1][1] = timer.elapsedTime;

				//Test the remove operation
				timer.start();
				for(int num : keys)
				{
					if(!hs.remove(num))
					{
						System.out.println(num+" not removed!");
					}
				}
				timer.end();
				hsTimes[n-1][2] = timer.elapsedTime;
				
				/**
				 * Initialize the CuckooHashing algorithm for testing the speed of it's operations
				 */
				CuckooHashing ch = new CuckooHashing(n*1000000, loadFactor);

				//Test the add operation
				timer.start();
				for(int num : keys)
				{
				ch.add(num);
				}
				timer.end();
				chTimes[n-1][0] = timer.elapsedTime;
				
				//Test the contains operation
				timer.start();
				for(int num : keys)
				{
				if(!ch.contains(num))
				{
				System.out.println(num+" not found!");
				}
				}
				timer.end();
				chTimes[n-1][1] = timer.elapsedTime;
				
				//Test the remove operation
				timer.start();
				for(int num : keys)
				{
				if(!ch.remove(num))
				{
				System.out.println(num+" not removed!");
				}
				}
				timer.end();
				chTimes[n-1][2] = timer.elapsedTime;
			}
			
			//Build report to visualize the respective performance of CuckooHashing, HashTable, HashMap & HashSet
			buildReport(loadFactor, chTimes, htTimes, hmTimes,  hsTimes);
		}		
	}

	/**
	 * 
	 * @author idsa
	 * @version	1.0 : class copied from idsa for using timer functionality
	 *
	 */
	public static class Timer {
		long startTime, endTime, elapsedTime, memAvailable, memUsed;
		boolean ready;

		public Timer() {
			startTime = System.currentTimeMillis();
			ready = false;
		}

		public void start() {
			startTime = System.currentTimeMillis();
			ready = false;
		}

		public Timer end() {
			endTime = System.currentTimeMillis();
			elapsedTime = endTime-startTime;
			memAvailable = Runtime.getRuntime().totalMemory();
			memUsed = memAvailable - Runtime.getRuntime().freeMemory();
			ready = true;
			return this;
		}

		public long duration() { if(!ready) { end(); }  return elapsedTime; }

		public long memory()   { if(!ready) { end(); }  return memUsed; }

		public String toString() {
			if(!ready) { end(); }
			return "Time: " + elapsedTime + " msec.\n" + "Memory: " + (memUsed/1048576) + " MB / " + (memAvailable/1048576) + " MB.";
		}
	}
}
