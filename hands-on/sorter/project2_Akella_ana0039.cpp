/* project2_Akella_ana0039 
* AUTHOR: Aneesh Akella  
* imulates 2 strategies for a duel to the death and displays results. G++ to compile and au test server used to run
*/ 
#include <iostream>
#include <cstdlib>
#include <cassert>
#include <time.h>
#include <iomanip>
using namespace std;
int aaronWins = 0;
int bobWins = 0;
int charlieWins = 0;
/* 
* Input: A_alive indicates Aaron is alive true for alive, false for dead
* B_alive indicates Bob is alive
* C_alive indicates Charlie is alive* Return: true if at least two are alive otherwise return false
*/ 
bool at_least_two_alive(bool aaronAlive, bool bobAlive, bool charlieAlive);
/* * Call by reference* Strategy 1: Everyone shoots to kill the highest accuracy player alive
* Input: B_alive indicates Bob is alive or dead*  C_alive indicates Aaron is alive or dead
* Return: Change B_alive into false if Bob is killed
*    Change C_alive into false if Charlie is killed
*/ 
void Aaron_shoots1(bool& bobAlive, bool& charlieAlive);
/* 
* Call by reference
* Input: A_alive indicates Aaron is alive or dead
*  C_alive indicates Charlie is alive or dead
* Return: Change A_alive into false if Aaron is killed
*    Change C_alive into false if Charlie is killed
*/ 
void Bob_shoots(bool& aaronAlive, bool& charlieAlive);
/* 
* Call by reference* Input: A_alive indicates Aaron is alive or dead
*  B_alive indicates Bob is alive or dead* Return: Change A_alive into false if Aaron is killed
*    Change B_alive into false if Bob is killed*/ 
void Charlie_shoots(bool& aaronAlive, bool& charlieAlive);
/* 
* Call by reference
* Strategy 2: Aaron intentionally misses if both are alive
* Input: B_alive indicates Bob is alive or dead
*  C_alive indicates Aaron is alive or dead
* Return: Change B_alive into false if Bob is killed
*    Change C_alive into false if Charlie is killed
*/ 
void Aaron_shoots2(bool& bobAlive, bool& charlieAlive);
void Press_any_key(void);
void duelStrategy1(void);
void duelStrategy2(void);
//TEST PROTOTYPES
void test_at_least_two_alive(void);
void test_Aaron_shoots1(void);
void test_Bob_shoots(void);
void test_Charlie_shoots(void);
void test_Aaron_shoots2(void);

int main() {//Initializes Random number generator's seed and calls test functions
	cout.setf(ios::fixed | ios::showpoint);
	const int TOTALDUELS = 10000;
	srand((unsigned int)time(0)); 
	cout << "*** Welcome to Aneesh's Duel Simulator ***\n";
	//Run tests
	test_at_least_two_alive();
	Press_any_key();
	test_Aaron_shoots1();
	Press_any_key();
	test_Bob_shoots();
	Press_any_key();
	test_Charlie_shoots();
	Press_any_key();
	test_Aaron_shoots2();
	Press_any_key();

	//Strat 1
	cout << "Ready to test strategy 1 (run 10000 times):\n";
	Press_any_key();
	cout << endl;
	int duelCount = 0;
	while (duelCount < TOTALDUELS) {
		duelStrategy1();
		duelCount++;
	}
	double aaronPercentage = (double)aaronWins / (double)TOTALDUELS;
	double bobPercentage = (double)bobWins / (double)TOTALDUELS;
	double charliePercentage = (double)charlieWins / (double)TOTALDUELS;
	int wins1 = aaronWins; 
	cout << "Aaron won " << aaronWins << "/" << TOTALDUELS << " duels or "
		<< setprecision(2) << aaronPercentage * 100 << "%\n";
	cout << "Bob won " << bobWins << "/" << TOTALDUELS << " duels or "
		<< setprecision(2) << bobPercentage * 100 << "%\n";
	cout << "Charlie won " << charlieWins << "/" << TOTALDUELS << " duels or "
		<< setprecision(2) << charliePercentage * 100 << "%\n\n";

	//Strat 2
	cout << "Ready to test strategy 2 (run 10000 times):\n";
	Press_any_key();
	cout << endl;
	//reset 
	duelCount = 0;
	aaronWins = 0;
	bobWins = 0;
	charlieWins = 0;
	while (duelCount < TOTALDUELS) {
		duelStrategy2();
		duelCount++;
	}
	aaronPercentage = (double)aaronWins / (double)TOTALDUELS;
	bobPercentage = (double)bobWins / (double)TOTALDUELS;
	charliePercentage = (double)charlieWins / (double)TOTALDUELS;
	cout << "Aaron won " << aaronWins << "/" << TOTALDUELS << " duels or "
		<< setprecision(2) << aaronPercentage * 100 << "%\n";
	cout << "Bob won " << bobWins << "/" << TOTALDUELS << " duels or "
		<< setprecision(2) << bobPercentage * 100 << "%\n";
	cout << "Charlie won " << charlieWins << "/" << TOTALDUELS << " duels or "
		<< setprecision(2) << charliePercentage * 100 << "%\n\n";
		//Comparing
	if (aaronWins > wins1) {
		cout << "Strategy 2 is better than strategy 1\n";
	}
	else if (aaronWins < wins1) {
		cout << "Strategy 1 is better than strategy 2\n";
	}
	else {
		cout << "The strategies have the same outcomes\n";
	}
}
//checks if at east 2 remain.
bool at_least_two_alive(bool aaronAlive, bool bobAlive, bool charlieAlive) {
	if(aaronAlive) {
		if (bobAlive || charlieAlive) {
			return true;
		}
		return false;
	}
	else if (bobAlive) {
		if (charlieAlive) {
			return true;
		}
		return false;
	}
	else {
		return false;
	}
}
//TESTING 1
void test_at_least_two_alive(void) {
	cout << "Unit Testing 1: Function - at_least_two_alive()\n";
	cout << "\tCase 1: Aaron alive, Bob alive, Charlie alive\n";
	assert(true == at_least_two_alive(true, true, true));
	cout << "\tCase passed...\n";
	cout << "\tCase 2: Aaron dead, Bob alive, Charlie alive\n";
	assert(true == at_least_two_alive(false, true, true));
	cout << "\tCase passed...\n";
	cout << "\tCase 3: Aaron alive, Bob dead, Charlie alive\n";
	assert(true == at_least_two_alive(true, false, true));
	cout << "\tCase passed...\n";
	cout << "\tCase 4: Aaron alive, Bob alive, Charlie dead\n";
	assert(true == at_least_two_alive(true, true, false));
	cout << "\tCase passed...\n";
	cout << "\tCase 5: Aaron dead, Bob dead, Charlie alive\n";
	assert(false == at_least_two_alive(false, false, true));
	cout << "\tCase passed...\n";
	cout << "\tCase 6: Aaron dead, Bob alive, Charlie dead\n";
	assert(false == at_least_two_alive(false, true, false));
	cout << "\tCase passed...\n";
	cout << "\tCase 7: Aaron alive, Bob dead, Charlie dead\n";
	assert(false == at_least_two_alive(true, false, false));
	cout << "\tCase passed...\n";
	cout << "\tCase 8: Aaron dead, Bob dead, Charlie dead\n";
	assert(false == at_least_two_alive(false, false, false));
	cout << "\tCase passed...\n";
}
/*
* no intentional miss
*/
 void Aaron_shoots1(bool& bobAlive, bool& charlieAlive) { 
	 const int AARON_AIM = 33; 
	 int accuracy;
	 accuracy = rand() % 100;
	 if (accuracy <= 33) {
		 if (charlieAlive) {
			 charlieAlive = false;
		 }
		 else {
			 bobAlive = false;
		 }
	 }
 } 
 /*
 *Testing 2
 */
 void test_Aaron_shoots1(void) {
	 cout << "Unit Test 2: Function Aaron_shoots1(Bob_alive, Charlie_alive)\n";
	 bool bobAlive;
	 bool charlieAlive;
	 cout << "\tCase 1: Bob alive, Charlie alive\n";
	 cout << "\t\tAaron is shooting at Charlie\n";
	 bobAlive = true;
	 charlieAlive = true;
	 Aaron_shoots1(bobAlive, charlieAlive);
	 assert(true == bobAlive);
	 cout << "\tCase 2: Bob dead, Charlie alive\n";
	 cout << "\t\tAaron is shooting at Charlie\n";
	 bobAlive = false;
	 charlieAlive = true;
	 Aaron_shoots1(bobAlive, charlieAlive);
	 assert(false == bobAlive);
	 cout << "\tCase 3: Bob alive, Charlie dead\n";
	 cout << "\t\tAaron is shooting at Bob\n";
	 bobAlive = true;
	 charlieAlive = false;
	 Aaron_shoots1(bobAlive, charlieAlive);
	 assert(false == charlieAlive);
 }
void Bob_shoots(bool& aaronAlive, bool& charlieAlive) {
	const int BOB_AIM = 50; 
	int accuracy;
	accuracy = rand() % 100;
	if (accuracy <= 50) {
		if (charlieAlive) {
			charlieAlive = false;
		}
		else {
			aaronAlive = false;
		}
	}
} 
/*
 *Testing 3
 */
void test_Bob_shoots(void) {
	cout << "Unit Testing 3: Function Bob_shoots(Aaron_alive, Charlie_alive)\n";
	bool aaronAlive;
	bool charlieAlive;

	cout << "\tCase 1: Aaron alive, Charlie alive\n";
	cout << "\t\tBob is shooting at Charlie\n";
	aaronAlive = true;
	charlieAlive = true;
	Bob_shoots(aaronAlive, charlieAlive);
	assert(true == aaronAlive);
	cout << "\tCase 2: Aaron dead, Charlie alive\n";
	cout << "\t\tBob is shooting at Charlie\n";
	aaronAlive = false;
	charlieAlive = true;
	Bob_shoots(aaronAlive, charlieAlive);
	assert(false == aaronAlive);
	cout << "\tCase 3: Aaron alive, Charlie dead\n";
	cout << "\t\tBob is shooting at Charlie\n";
	aaronAlive = true;
	charlieAlive = false;
	Bob_shoots(aaronAlive, charlieAlive);
	assert(false == charlieAlive);
} 
void Charlie_shoots(bool& aaronAlive, bool& bobAlive) {
	if (bobAlive) {
		bobAlive = false;
	}
	else {
		aaronAlive = false;
	}
}
/*
 *Testing 4
 */
void test_Charlie_shoots(void) {
	cout << "Unit Testing 4: Function Charlie_shoots(Aaron_alive, Bob_alive)\n";
	bool aaronAlive;
	bool bobAlive;
	cout << "\tCase 1: Aaron alive, Bob alive\n";
	cout << "\t\tCharlie is shooting at Bob\n";
	aaronAlive = true;
	bobAlive = true;
	Charlie_shoots(aaronAlive, bobAlive);
	assert(true == aaronAlive);
	assert(false == bobAlive);
	cout << "\t\tBob is dead.\n";
	cout << "\tCase 2: Aaron dead, Bob alive\n";
	cout << "\t\tCharlie is shooting at Bob\n";
	aaronAlive = false;
	bobAlive = true;
	Charlie_shoots(aaronAlive, bobAlive);
	assert(false == aaronAlive);
	assert(false == bobAlive);
	cout << "\t\tBob is dead.\n";
	cout << "\tCase 3: Aaron alive, Bob dead\n";
	cout << "\t\tCharlie is shooting at Aaron\n";
	aaronAlive = true;
	bobAlive = false;
	Charlie_shoots(aaronAlive, bobAlive);
	assert(false == bobAlive);
	assert(false == aaronAlive);
	cout << "\t\tAaron is dead.\n";
}
/*
* with miss
*/
void Aaron_shoots2(bool& bobAlive, bool& charlieAlive)
{
	const int AARON_PROBABILITY = 33; 
	int shootingResult;
	if (!bobAlive || !charlieAlive) {
		shootingResult = rand() % 100;
		if (shootingResult <= 33) {
			if (charlieAlive) {
				charlieAlive = false;
			}
			else {
				bobAlive = false;
			}
		}
	}
} 
/*
 *Testing 5
 */
void test_Aaron_shoots2(void) {
	cout << "Unit Testing 5: Function Aaron_shoots2(Bob_alive, Charlie_alive)\n";
	bool bobAlive;
	bool charlieAlive;

	cout << "\tCase 1: Bob alive, Charlie alive\n";
	cout << "\t\tAaron intentionally misses his first shot\n";
	bobAlive = true;
	charlieAlive = true;
	Aaron_shoots2(bobAlive, charlieAlive);
	assert(true == bobAlive);
	assert(true == charlieAlive);
	cout << "\t\tBoth Bob and Charlie are alive\n";

	cout << "\tCase 2: Bob dead, Charlie alive\n";
	cout << "\t\tAaron is shooting at Charlie\n";
	bobAlive = false;
	charlieAlive = true;
	Aaron_shoots2(bobAlive, charlieAlive);
	assert(false == bobAlive);

	cout << "\tCase 3: Bob alive, Charlie dead\n";
	cout << "\t\tAaron is shooting at Bob\n";
	bobAlive = true;
	charlieAlive = false;
	Aaron_shoots2(bobAlive, charlieAlive);
	assert(false == charlieAlive);
} 
void Press_any_key(void) { 
	cout << "Press any key to continue...";
	cin.ignore().get(); 
}

void duelStrategy1(void) {//put strat 1 and 2 in methods to clean up main
	bool aaronAlive = true;
	bool bobAlive = true;
	bool charlieAlive = true;
	while (at_least_two_alive(aaronAlive, bobAlive, charlieAlive)) {
		if (aaronAlive) {
			Aaron_shoots1(bobAlive, charlieAlive);
		}
		if (bobAlive) {
			Bob_shoots(aaronAlive, charlieAlive);
		}
		if (charlieAlive) {
			Charlie_shoots(aaronAlive, bobAlive);
		}
	}
	if (aaronAlive) {
		aaronWins++;
	}
	else if (bobAlive) {
		bobWins++;
	}
	else {
		charlieWins++;
	}
}
void duelStrategy2(void)
{//strat 2
	bool aaronAlive = true;
	bool bobAlive = true;
	bool charlieAlive = true;
	while (at_least_two_alive(aaronAlive, bobAlive, charlieAlive)) {
		if (aaronAlive) {
			Aaron_shoots2(bobAlive, charlieAlive);
		}
		if (bobAlive) {
			Bob_shoots(aaronAlive, charlieAlive);
		}
		if (charlieAlive) {
			Charlie_shoots(aaronAlive, bobAlive);
		}
	}
	if (aaronAlive) {
		aaronWins++;
	}
	else if (bobAlive) {
		bobWins++;
	}
	else {
		charlieWins++;
	}
}
