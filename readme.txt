README:
======

Short Project # 08: Cuckoo Hashing algorithm Implementation and Comaprison with Java Hashtable, HashMap & HashSet.


Authors :
------
1) Shariq Ali SXA190016
2) Bhushan Vasisht BSV180000


How to compile and run the code:
-------------------------------
The file CuckooHashing.java should be placed inside the folder named as 'sxa190016' which is the package name.
Run the below commands sequentially to execute the program

1) The command prompt path should be in "sxa190016" directory
2) javac CuckooHashing.java
3) java CuckooHashing
	
Note: Only Integers are valid as input values.

Methods in Code:
-------------------
The following methods are written for CuckooHashing Method:

getHashPrime(x)       - Generates a unique hash value based on the approximate number of elements to be stored

isHashPrime(x)        - Check if the generated hash number is prime or not

CuckooHashing(x, l)   - Constructor which initializes the private member variables

getHash1(x)           - Get the hashcode for array 1

getHash2(x)           - Get the hashcode for array 2

printStorage()        - Helper method to print the location at which each element is stored

add(x)                - Add an element to the Cuckoo Hash tables

remove(x)             - Remove an element from the hashtables

contains(x) 	      - Check if the element if present in the hashtables

getKeysFromFile(x, f) - Helper method to read random keys from a text file

generateKeys(x)       - Helper method to generate the keys automatically

printTimes(x)         - Helper method to print the elapsed time for each operation

buildReport(f,c,t,m,s)- Helper method to build the report in a user-friendly format

main()  	      - main function to test the CuckooHashing class


The main function:
-------------------
When you run the main function, it will
1. Initialize the timer
2. Set the number of test cases to be tested. Here we are testing 10 cases from 1M to 10M elements
3. Initialize 2D long arrays for storing the operation time for CuckooHashing, Hashtable, HashMap, HashSet
4. Load Factors of 0.5f, 0.75f, 0.9f are used for testing the performance of these hashing methods
5. Do for each load factor value
6. Do for each test-case
7. Uncomment the below line to read random values from a text file
8. Generate keys for each test case
9. Initialize the Hashtable for testing the speed of it's operations
10. Test the add operation
11. Test the contains operation
12. Test the remove operation
13. Initialize the HashMap for testing the speed of it's operations and test for all three operations.
14. Initialize the HashSet for testing the speed of it's operations and test for all three operations.
15. Initialize the CuckooHashing algorithm for testing the speed of it's operations.
16. Build report to visualize the respective performance of CuckooHashing, HashTable, HashMap & HashSet


Report:
-------------------
Note: 
# All the values are in milli-seconds
# M - million operations

                      Operation : Add() & LoadFactor : 0.5
                 n=1M  n=2M  n=3M  n=4M  n=5M  n=6M  n=7M  n=8M  n=9M n=10M
  CuckooHashing    30    41    72    67    98   170   314   257   150   203
      Hashtable   166   294   114   202   178   361   379   807   512   737
        HashMap   124   161   119   228   551   354   866   786   664   754
        HashSet    67   102   192   272   274   422   679   661   535   511

                      Operation : Contains() & LoadFactor : 0.5
                 n=1M  n=2M  n=3M  n=4M  n=5M  n=6M  n=7M  n=8M  n=9M n=10M
  CuckooHashing    23    30    19    28    32    41    93    71    81    72
      Hashtable    59   107    52    36    51    84    89   228   240   224
        HashMap    22    71    26    47    79    92   216   263   119   210
        HashSet    38    62    44    53    38    92   313   208   254   346

                      Operation : Remove() & LoadFactor : 0.5
                 n=1M  n=2M  n=3M  n=4M  n=5M  n=6M  n=7M  n=8M  n=9M n=10M
  CuckooHashing    14    17    14    17    24    25    56    52    50    43
      Hashtable    81    82   143    60    61   209   246   369   173   291
        HashMap    34   105    88    35   108   113   326   287   286   274
        HashSet    58    94    47    72    83   207   353   240   147   167

                      Operation : Add() & LoadFactor : 0.75
                 n=1M  n=2M  n=3M  n=4M  n=5M  n=6M  n=7M  n=8M  n=9M n=10M
  CuckooHashing    35    67   147   192   154   201   149   241   210   253
      Hashtable    89   180   228   356   272   216   416   788   427   564
        HashMap    68    77   166   355   259   290   425   674   311   477
        HashSet    77   210   134   236   325   183   352   359   373   359

                      Operation : Contains() & LoadFactor : 0.75
                 n=1M  n=2M  n=3M  n=4M  n=5M  n=6M  n=7M  n=8M  n=9M n=10M
  CuckooHashing    41    26    26    33    32    46    42    57    64    89
      Hashtable   101    44   102    77    42   238    74   177   242   230
        HashMap    33    73    90    92    51    72    57   152   290   281
        HashSet     9    44   148   125    73    52   292   228   312   144

                      Operation : Remove() & LoadFactor : 0.75
                 n=1M  n=2M  n=3M  n=4M  n=5M  n=6M  n=7M  n=8M  n=9M n=10M
  CuckooHashing    41    23    12    21    32    41    37    45    48    76
      Hashtable    38    65   143    90   150    86    81   208   128   148
        HashMap    17    29    92    96    71    88   306   260   212   168
        HashSet     8    40   109    60   119    92   142   248   168   227

                      Operation : Add() & LoadFactor : 0.9
                 n=1M  n=2M  n=3M  n=4M  n=5M  n=6M  n=7M  n=8M  n=9M n=10M
  CuckooHashing    55    45    67    96   502   192   156   291   331   316
      Hashtable    39   106   140   144   278   635   264   522   520   534
        HashMap    33   121   208   147   267   303   408   647   298   461
        HashSet    34    81   132   235   199   233   169   626   513   481

                      Operation : Contains() & LoadFactor : 0.9
                 n=1M  n=2M  n=3M  n=4M  n=5M  n=6M  n=7M  n=8M  n=9M n=10M
  CuckooHashing    12    12    25    24    36    37    44    56    72    64
      Hashtable     6    31    38    29    63   130   310   125    79   170
        HashMap     9   115    33    31    46   230    73    97   352   261
        HashSet    14    21    47    49   110    63    74   173   148   240

                      Operation : Remove() & LoadFactor : 0.9
                 n=1M  n=2M  n=3M  n=4M  n=5M  n=6M  n=7M  n=8M  n=9M n=10M
  CuckooHashing     8    10    22    23    40    33    40    47    57    56
      Hashtable    50    36    71   126    74   131   163   171   139   349
        HashMap    10    30    45    39   179   110   152   230   207   191
        HashSet    20    24   103    39    99    75   274   280   206   218


Summary:
-------------------
CuckooHashing is much faster than Hashtable, HashMap & HashSet, especially when the number of operations are in millions and the Load Factor is very high. Please check the Report.docx file for detailed comparison.