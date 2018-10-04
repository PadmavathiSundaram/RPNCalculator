RPN Mode CLI Calculator

Setup:
-------------
mvn clean install 

Execute Test
-----
mvn test

Run with tests:
----------
mvn clean install exec:java


Overview:
-----------

1. The calculator has a stack that can contain real numbers.
2. The calculator waits for user input and expects to receive strings containing
whitespace separated lists of numbers and operators.
3.  Numbers are pushed on to the stack. Operators operate on numbers that are on
the stack.
4. Available operators are +, -, *, /, sqrt, undo, clear
5.Operators pop their parameters off the stack, and push their results back onto
the stack.
6. The ‘clear’ operator removes all items from the stack.
7. The ‘undo’ operator undoes the previous operation. “undo undo” will undo the
previo us two operations.
8. sqrt performs a square root on the top item from the stack
9. The ‘+’, ‘-’, ‘*’, ‘/’ operators perform addition, subtraction, multiplication and
division respectively on the top two items from the stack.
10. After processing an input string, the calculator displays the current contents of
the stack as a space-separated list.
11. Numbers should be stored on the stack to at least 15 decimal places of
precision, but displayed to 10 decimal places (or less if it causes no loss of
precision).
12. All numbers should be formatted as plain decimal strings (ie. no engineering
formatting).
13. If an operator cannot find a sufficient number of parameters on the stack, a
warning is displayed:
operator <operator> (position: <pos>): insufficient parameters
14. After displaying the warning, all further processing of the string terminates and
the current state of the stack is displayed.



Examples
---------


Example 1
-----

5 2

stack: 5 2

Example 2
----
2 sqrt

stack: 1.4142135623

clear 9 sqrt

stack: 3

Example 3
----
5 2 -

stack: 3

3 -

stack: 0

clear

stack:

Example 4
------
5 4 3 2

stack: 5 4 3 2

undo undo *

stack: 20

5 *

stack: 100

undo

stack: 20 5

Example 5
------

7 12 2 /

stack: 7 6

*
stack: 42

4 /

stack: 10.5

Example 6
---
1 2 3 4 5

stack: 1 2 3 4 5
*
stack: 1 2 3 20

clear 3 4 -

stack: - 1

Example 7
---
1 2 3 4 5
stack: 1 2 3 4 5
* * * *
stack: 120

Example 8
---

1 2 3 * 5 + * * 6 5

operator * (position: 15): insucient parameters

stack: 11

(the 6 and 5 were not pushed on to the stack
due to the previous error)