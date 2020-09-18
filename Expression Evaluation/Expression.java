package app;

import java.io.*;
import java.util.*;
import java.util.regex.*;

import structures.Stack;

public class Expression {

	public static String delims = " \t*+-/()[]";

	/**
	 * Populates the vars list with simple variables, and arrays lists with arrays
	 * in the expression. For every variable (simple or array), a SINGLE instance is
	 * created and stored, even if it appears more than once in the expression. At
	 * this time, values for all variables and all array items are set to zero -
	 * they will be loaded from a file in the loadVariableValues method.
	 * 
	 * @param expr   The expression
	 * @param vars   The variables array list - already created by the caller
	 * @param arrays The arrays array list - already created by the caller
	 */
	public static void makeVariableLists(String expr, ArrayList<Variable> vars, ArrayList<Array> arrays) {

		boolean alreadyInVars = false;
		boolean alreadyInArrays = false;
		int index1 = 0;
		int index2 = 0;
		String onlyVariablesLeft = "";
		String removeNumAndPar = "0123456789 ()]";

		for (int i = 0; i < expr.length(); i++) {
			if (!removeNumAndPar.contains(expr.substring(i, i + 1))) {
				onlyVariablesLeft = onlyVariablesLeft + expr.substring(i, i + 1);
			}
		}
		System.out.println(onlyVariablesLeft);

		if (onlyVariablesLeft.length() == 1) {
			vars.add(new Variable(onlyVariablesLeft));
		} else {
			while ((index1 <= onlyVariablesLeft.length() - 1) && (index2 <= onlyVariablesLeft.length() - 1)) {
				if (onlyVariablesLeft.charAt(index2) != '+' && onlyVariablesLeft.charAt(index2) != '-'
						&& onlyVariablesLeft.charAt(index2) != '*' && onlyVariablesLeft.charAt(index2) != '/'
						&& onlyVariablesLeft.charAt(index2) != '[') {
					index2++;
				} else if (onlyVariablesLeft.charAt(index2) == '[') {

					for (int j = 0; j <= arrays.size() - 1; j++) {
						if ((arrays.get(j).name).equals(onlyVariablesLeft.substring(index1, index2))) {
							alreadyInArrays = true;
						}
					}
					if (!alreadyInArrays) {
						arrays.add(new Array(onlyVariablesLeft.substring(index1, index2)));
						index1 = index2 + 1;
						index2++;
					} else {
						alreadyInArrays = false;
						index1 = index2 + 1;
						index2++;
					}

				} else {

					for (int j = 0; j <= vars.size() - 1; j++) {
						if ((vars.get(j).name).equals(onlyVariablesLeft.substring(index1, index2))) {
							alreadyInVars = true;
						}
					}
					if (!alreadyInVars) {
						if (!onlyVariablesLeft.substring(index1, index2).equals("")) {
							vars.add(new Variable(onlyVariablesLeft.substring(index1, index2)));
						}
						index1 = index2 + 1;
						index2++;
					} else {
						alreadyInVars = false;
						index1 = index2 + 1;
						index2++;
					}

				}

			}

			System.out.println("FRONT INDEX: " + index1);
			System.out.println("SECOND INDEX: " + index2);
			if (index2 >= onlyVariablesLeft.length() - 1 && index1 <= onlyVariablesLeft.length() - 1) {
				if (index2 >= onlyVariablesLeft.length() - 1) {
					index2 = index2 - 1;
				}
				System.out.println("FRONT INDEX NOW : " + index1);
				System.out.println("SECOND INDEX NOW: " + index2);
				if (index1 == index2) {
					alreadyInArrays = false;

					for (int j = 0; j <= vars.size() - 1; j++) {
						if ((vars.get(j).name).equals(onlyVariablesLeft.substring(index1))) {
							alreadyInVars = true;
						}
					}
					if (!alreadyInVars) {
						vars.add(new Variable("" + onlyVariablesLeft.substring(index1)));
					}
				} else {
					alreadyInVars = false;
					for (int j = 0; j <= vars.size() - 1; j++) {
						if ((vars.get(j).name).equals(onlyVariablesLeft.substring(index1, index2))) {
							alreadyInVars = true;
						}
					}
					if (!alreadyInVars) {
						vars.add(new Variable(onlyVariablesLeft.substring(index1)));
					}
				}
			}

			System.out.println("SIMPLE VARIABLES: " + vars);
			System.out.println("ARRAY VARIABLES: " + arrays);

		}
	}

	/**
	 * Loads values for variables and arrays in the expression
	 * 
	 * @param sc Scanner for values input
	 * @throws IOException If there is a problem with the input
	 * @param vars   The variables array list, previously populated by
	 *               makeVariableLists
	 * @param arrays The arrays array list - previously populated by
	 *               makeVariableLists
	 */
	public static void loadVariableValues(Scanner sc, ArrayList<Variable> vars, ArrayList<Array> arrays)
			throws IOException {
		while (sc.hasNextLine()) {
			StringTokenizer st = new StringTokenizer(sc.nextLine().trim());
			int numTokens = st.countTokens();
			String tok = st.nextToken();
			Variable var = new Variable(tok);
			Array arr = new Array(tok);
			int vari = vars.indexOf(var);
			int arri = arrays.indexOf(arr);
			if (vari == -1 && arri == -1) {
				continue;
			}
			int num = Integer.parseInt(st.nextToken());
			if (numTokens == 2) { // scalar symbol
				vars.get(vari).value = num;
			} else { // array symbol
				arr = arrays.get(arri);
				arr.values = new int[num];
				// following are (index,val) pairs
				while (st.hasMoreTokens()) {
					tok = st.nextToken();
					StringTokenizer stt = new StringTokenizer(tok, " (,)");
					int index = Integer.parseInt(stt.nextToken());
					int val = Integer.parseInt(stt.nextToken());
					arr.values[index] = val;
				}
			}
		}
	}

	private static String[] getSubExpr(String expr) {
		for (int i = 0; i < expr.length(); i++) {
			if (expr.charAt(i) == '(') {
				Stack<Character> check = new Stack<Character>();
				check.push('(');
				int frontIndex = i;
				int endIndex = 0;
				for (int j = i + 1; j < expr.length(); j++) {
					if (expr.charAt(j) == '(') {
						check.push('(');
					} else if (expr.charAt(j) == ')') {
						check.pop();
					}
					if (check.isEmpty()) {
						endIndex = j;
						break;
					}
				}
				String[] arr = { "" + frontIndex, "" + endIndex,
						expr.substring(frontIndex + 1, endIndex) };
				return arr;
			}
		}
		return null;
	}

	private static boolean containsSubExpr(String expr) {
		for (int i = 0; i < expr.length(); i++) {
			if (expr.charAt(i) == '(') {
				return true;
			}
		}
		return false;
	}

	private static boolean containsSubArr(String expr) {
		for (int i = 0; i < expr.length(); i++) {
			if (expr.charAt(i) == '[') {
				return true;
			}
		}
		return false;
	}
	
	private static boolean hasVariables(String expr) {
		for (int i = 0; i < expr.length(); i++) {
			if (Character.isLetter(expr.charAt(i))) {
				return true;
			}
		}
		return false;
	}

	private static int[] getSubArray(String expr, ArrayList<Variable> vars, ArrayList<Array> arrays) {
		int index = 0;
		for (int i = 0; i < expr.length(); i++) {
			if (expr.charAt(i) == '[') {
				String nameOfArray = "";
				int indexOfName = 0;
				for (int j = i - 1; j >= 0 && Character.isLetter(expr.charAt(j)); j--) {
					nameOfArray += expr.charAt(j);
					indexOfName = j;
				}
				char[] ex = new char[nameOfArray.length()];
				for (int k = 0; k < nameOfArray.length(); k++) {
					ex[k] = nameOfArray.charAt(k);
				}
				nameOfArray = "";
				for (int k = ex.length - 1; k >= 0; k--) {
					nameOfArray += ex[k];
				}
				Stack<Character> check = new Stack<Character>();
				int frontIndex = i;
				int endIndex = 0;
				check.push('[');
				for (int j = i + 1; j < expr.length(); j++) {
					if (expr.charAt(j) == '[') {
						check.push('[');
					} else if (expr.charAt(j) == ']') {
						check.pop();
					}

					if (check.isEmpty()) {
						endIndex = j;
						break;
					}
				}
				index = (int) evaluate(expr.substring(frontIndex + 1, endIndex), vars, arrays);
				int value = 0;
				for (int j = 0; j < arrays.size(); j++) {
					if (arrays.get(j).name.equals(nameOfArray)) {
						value = arrays.get(j).values[index];
					}
				}
				frontIndex = indexOfName;
				int[] array = { frontIndex, endIndex, value };
				return array;
			}
		}
		return null;
	}


	private static int[] getVariable(String expr, ArrayList<Variable> vars) {
		for (int i = 0; i < expr.length(); i++) {
			if (Character.isLetter(expr.charAt(i))) {
				int frontIndex = i;
				int endIndex = 0;
				String name = "";
				int j;
				for (j = i; j < expr.length(); j++) {
					if (Character.isLetter(expr.charAt(j))) {
						name += expr.charAt(j);
					} else {
						break;
					}
				}
				endIndex = j - 1;
				int value = 0;
				for (j = 0; j < vars.size(); j++) {
					if (vars.get(j).name.equals(name)) {
						value = vars.get(j).value;
					}
				}
				int[] arr = { frontIndex, endIndex, value };
				return arr;
			}
		}
		return null;
	}

	private static String removeSpaces(String expr) {
		char[] arr = new char[expr.length()];
		for (int i = 0; i < expr.length(); i++) {
			arr[i] = expr.charAt(i);
		}
		String result = "";
		for (int i = 0; i < arr.length; i++) {
			if (arr[i] != ' ') {
				result += arr[i];
			}
		}
		return result;
	}
	
	private static float calculate(String expr) {
		Stack<Float> expression = new Stack<Float>();
		Stack<Character> expressionOperator = new Stack<Character>();
		for (int i = 0; i < expr.length(); i++) {
			if (Character.isDigit(expr.charAt(i))) {
				String ex = "";
				if ((i - 1) >= 0 && expr.charAt(i - 1) == '-') {
					if ((i - 2) >= 0) {
						if (!Character.isDigit(expr.charAt(i - 2))) {
							ex += '-';
						}
					} else {
						ex += '-';
					}
				}
				while (i < expr.length()) {
					if (Character.isDigit(expr.charAt(i))) {
						ex += expr.charAt(i);
					} else if (expr.charAt(i) == '.') {
						ex += expr.charAt(i);
					} else {
						i--;
						break;
					}
					i++;
				}
				expression.push(Float.valueOf(ex));
			} else if (expr.charAt(i) == '+' || expr.charAt(i) == '-') {
				if ((i - 1) >= 0 && Character.isDigit(expr.charAt(i - 1))) {
					expressionOperator.push(expr.charAt(i));
				}
			} else {
				boolean multiply = false;
				if (expr.charAt(i) == '*') {
					multiply = true;
				}
				String ex = "";
				i++;
				if (expr.charAt(i) == '-') {
					ex += '-';
					i++;
				}
				while (i < expr.length()) {
					if (Character.isDigit(expr.charAt(i))) {
						ex += expr.charAt(i);
					} else if (expr.charAt(i) == '.') {
						ex += expr.charAt(i);
					} else {
						i--;
						break;
					}
					i++;
				}
				if (multiply) {
					expression.push(expression.pop() * Float.valueOf(ex));
				} else {
					expression.push(expression.pop() / Float.valueOf(ex));
				}
			}
		}

		Stack<Float> expressionRev = new Stack<Float>();
		while (!expression.isEmpty()) {
			expressionRev.push(expression.pop());
		}
		Stack<Character> expressionOperatorRev = new Stack<Character>();
		while (!expressionOperator.isEmpty()) {
			expressionOperatorRev.push(expressionOperator.pop());
		}
		while (!expressionOperatorRev.isEmpty()) {
			Character type = expressionOperatorRev.pop();
			if (type == '+') {
				expressionRev.push(expressionRev.pop() + expressionRev.pop());
			} else {
				expressionRev.push(expressionRev.pop() - expressionRev.pop());
			}
		}
		return expressionRev.pop();
	}

	/**
	 * Evaluates the expression.
	 * 
	 * @param vars   The variables array list, with values for all variables in the
	 *               expression
	 * @param arrays The arrays array list, with values for all array items
	 * @return Result of evaluation
	 */
	public static float evaluate(String expr, ArrayList<Variable> vars, ArrayList<Array> arrays) {
		Float res;
		expr = removeSpaces(expr);
		while (containsSubArr(expr)) {
			int[] subArrExpr = getSubArray(expr, vars, arrays);
			expr = expr.substring(0, subArrExpr[0]) + "" + subArrExpr[2] + expr.substring(subArrExpr[1] + 1);
		}
		while (containsSubExpr(expr)) {
			String[] subExprArr = getSubExpr(expr);
			expr = expr.substring(0, Integer.parseInt(subExprArr[0])) + evaluate(subExprArr[2], vars, arrays) + expr.substring(Integer.parseInt(subExprArr[1]) + 1);
		}
		while (hasVariables(expr)) {
			int[] subVariable = getVariable(expr, vars);
			expr = expr.substring(0, subVariable[0]) + subVariable[2] + expr.substring(subVariable[1] + 1);
		}
		res = calculate(expr);

		return res;
	}
}
 
