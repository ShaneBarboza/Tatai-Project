package utility;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class EquationList {

	private static Stack<String> _equations = new Stack<String>();
	private static Stack<Integer> _answers = new Stack<Integer>();
	private static List<String> _questionLog = new ArrayList<String>();
	private static List<Integer> _answerLog = new ArrayList<Integer>();
	private static List<Boolean> _userAnswerLog = new ArrayList<Boolean>();
	private static boolean _isCustom = false;


	/**
	 * Returns the next question in the queue
	 * @return A 2-member string array where the first member
	 * is the equation in string form, and the second member is the
	 * answer in string form (to be parsed as an int)
	 */
	public static String[] getQuestion() {
		String[] returnVal = {"",""};
		returnVal[0] = _equations.pop();
		returnVal[1] = Integer.toString(_answers.pop());
		
		return returnVal;
	}

	
	/**
	 * Gets the number of questions answered
	 * @return The number of questions answered so far
	 */
	public static int getNumberAnswered() {
		return _answerLog.size();
	}
	
	/**
	 * Checks if there are any more questions remaining
	 * @return true if no more questions to ask, false otherwise
	 */
	public static boolean noQuestions() {
		if (_equations.isEmpty()) {
			return true;
		}
		else {
			return false;
		}
	}
	
	/**
	 * Logs the answer and question passed into this method
	 * @param question the string form of the question
	 * @param answer the correct answer to the question
	 * @param correct whether or not the user was correct
	 */
	public static void logAnswer(String question, int answer, boolean correct) {
		_questionLog.add(question);
		_answerLog.add(answer);
		_userAnswerLog.add(correct);
	}
	
	/**
	 * Generates an atomic equation
	 * @param max the maximum value that the equation can equal
	 * @param allOperations Whether or not to enable multiplication and division
	 * @return a string array, where the first member is the equation,
	 * and the second member is the answer
	 */
	private static String[] generateAtomic(int max, boolean allOperations) {

		String eq = "";
		int ans = 0;

		int a;
		int b;
		int switchOps;

		// If allOperations is true, enables multiplication and division
		if (allOperations) {
			switchOps = 4;
		}
		else {
			switchOps = 2;
		}

		switch (new java.util.Random().nextInt(switchOps)) {
		// +
		case 0:
			a = new java.util.Random().nextInt(max - 1) + 1;
			b = new java.util.Random().nextInt(max - a) + 1;
			eq = a + " + " + b;
			ans = a + b;
			break;
			// -
		case 1:
			a = new java.util.Random().nextInt(max - 1) + 2;
			b = new java.util.Random().nextInt(a - 1) + 1;
			eq = a + " - " + b;
			ans = a - b;
			break;
			// x
		case 2:
			a = new java.util.Random().nextInt((int)(max / 2)) + 1;
			b = new java.util.Random().nextInt(max / a) + 1;
			eq = a + " X " + b;
			ans = a * b;
			break;
			// /
		case 3:
			a = new java.util.Random().nextInt((int)(max / 2)) + 1;
			b = new java.util.Random().nextInt(max / a) + 1;
			eq = (a*b) + " / " + b;
			ans = a;
			break;
		}

		String[] returnVal = {eq,Integer.toString(ans)};
		return returnVal;

	}

	
	/**
	 * Sets up the equationlist to play a custom list of questions
	 * @param list the 2d string array representing the custom list
	 */
	protected static void loadCustom(String[][] list) {
		// Clear to ensure question list & logs don't have any pre-existing members
		_equations.clear();
		_answers.clear();
		_userAnswerLog.clear();
		_answerLog.clear();
		_questionLog.clear();
		_isCustom = true;

		for (int i = 0; i < list.length; i++) {
			// Get the question
			String[] question = list[i];
			
			// Save this question and its answer
			_equations.push(question[0]);
			_answers.push(Integer.parseInt(question[1]));
		}
	}
	
	
	/**
	 * Generates a list of easy questions, no brackets, only addition / subtraction
	 * @param length the number of questions to generate
	 */
	public static void generateEasy(int length) {
		// Clear to ensure question list & logs don't have any pre-existing members
		_equations.clear();
		_answers.clear();
		_userAnswerLog.clear();
		_answerLog.clear();
		_questionLog.clear();
		_isCustom = false;

		for (int i = 0; i < length; i++) {
			// Generate an easy question
			String[] question = generateAtomic(99,false);
			
			// Save this question and its answer
			_equations.push(question[0]);
			_answers.push(Integer.parseInt(question[1]));
		}
		
	}

	/**
	 * Generates a list of mid-level questions, no brackets but includes multiplication 
	 * and division
	 * @param length the number of questions to generate
	 */
	public static void generateMid(int length) {
		// Clear to ensure question list & logs don't have any pre-existing members
		_equations.clear();
		_answers.clear();
		_userAnswerLog.clear();
		_answerLog.clear();
		_questionLog.clear();
		_isCustom = false;

		for (int i = 0; i < length; i++) {
			// Generate a mid-level question
			String[] question = generateAtomic(99,true);
			
			// Save this question and its answer
			_equations.push(question[0]);
			_answers.push(Integer.parseInt(question[1]));
		}
		
	}

	/**
	 * Generates a set of hard questions. "Hard" questions are those
	 * that have two parts, i.e are not atomic
	 * @param length the number of questions to generate
	 */
	public static void generateHard(int length) {


		// Clear to ensure question list & logs don't have any pre-existing members
		_equations.clear();
		_answers.clear();
		_userAnswerLog.clear();
		_answerLog.clear();
		_questionLog.clear();
		_isCustom = false;


		for (int i = 0; i < length; i++) {

			String[] a;
			int ans = 0;
			String eq = "";
			String[] b;

			// Each question is generated by combining 2 atomic equations,
			// either with +, -, or X
			switch (new java.util.Random().nextInt(3)) {
			// +
			case 0:
				a = generateAtomic(90,true);
				b = generateAtomic(99 - Integer.parseInt(a[1]),true);
				eq = "(" + a[0] + ")" + " + " + "(" + b[0] + ")";
				ans = Integer.parseInt(a[1]) + Integer.parseInt(b[1]);
				break;
				// -
			case 1:
				a = generateAtomic(98,true);
				if (Integer.parseInt(a[1]) <= 2) {
					int alt = new java.util.Random().nextInt(97) + 3;
					a[0] = Integer.toString(alt);
					a[1] = Integer.toString(alt);
				}
				b = generateAtomic(Integer.parseInt(a[1]) - 1,true);
				eq = "(" + a[0] + ")" + " - " + "(" + b[0] + ")";
				ans = Integer.parseInt(a[1]) - Integer.parseInt(b[1]);
				break;
				// x
			case 2:
				a = generateAtomic(49,true);
				b = generateAtomic(99 / Integer.parseInt(a[1]),true);
				eq = "(" + a[0] + ")" + " X " + "(" + b[0] + ")";
				ans = Integer.parseInt(a[1]) * Integer.parseInt(b[1]);
				break;	
			}				

			// Save this question and its answer
			_equations.push(eq);
			_answers.push(ans);


		}

	}
	
	
	/**
	 * Returns the question in string form asked at the specified index
	 * @param index the index of the question asked
	 * @return the string representing the question
	 */
	public static String getQuestionAt(int index) {
		return _questionLog.get(index);
	}

	
	/**
	 * Getter for the answer of the question at specified index
	 * @param index the index of the question asked
	 * @return the int answer for this question
	 */
	public static int getAnswerAt(int index) {
		return _answerLog.get(index);
	}
	
	
	/**
	 * Getter for if the user's answer was correct for a specific question
	 * @param index the index of the question asked
	 * @return true if the user was correct, false otherwise
	 */
	public static boolean getUserAnswerAt(int index) {
		return _userAnswerLog.get(index);
	}
	
	
	/**
	 * Gets the score of this session
	 * @return: a count of the number of questions answered correctly
	 */
	public static int getSessionScore() {
		int count = 0;
		for (boolean answer: _userAnswerLog) {
			if (answer) {
				count++;
			}
		}
		
		return count;
	}


	/**
	 * @return true if a custom list is being played, false otherwise
	 */
	public static boolean isCustom() {
		return _isCustom;
	}


}
