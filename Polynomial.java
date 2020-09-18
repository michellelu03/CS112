package poly;

import java.io.IOException;
import java.util.Scanner;

/**
 * This class implements evaluate, add and multiply for polynomials.
 * 
 * @author runb-cs112
 *
 */
public class Polynomial {
	
	/**
	 * Reads a polynomial from an input stream (file or keyboard). The storage format
	 * of the polynomial is:
	 * <pre>
	 *     <coeff> <degree>
	 *     <coeff> <degree>
	 *     ...
	 *     <coeff> <degree>
	 * </pre>
	 * with the guarantee that degrees will be in descending order. For example:
	 * <pre>
	 *      4 5
	 *     -2 3
	 *      2 1
	 *      3 0
	 * </pre>
	 * which represents the polynomial:
	 * <pre>
	 *      4*x^5 - 2*x^3 + 2*x + 3 
	 * </pre>
	 * 
	 * @param sc Scanner from which a polynomial is to be read
	 * @throws IOException If there is any input error in reading the polynomial
	 * @return The polynomial linked list (front node) constructed from coefficients and
	 *         degrees read from scanner
	 */
	public static Node read(Scanner sc) 
	throws IOException {
		Node poly = null;
		while (sc.hasNextLine()) {
			Scanner scLine = new Scanner(sc.nextLine());
			poly = new Node(scLine.nextFloat(), scLine.nextInt(), poly);
			scLine.close();
		}
		return poly;
	}
	
	/**
	 * Returns the sum of two polynomials - DOES NOT change either of the input polynomials.
	 * The returned polynomial MUST have all new nodes. In other words, none of the nodes
	 * of the input polynomials can be in the result.
	 * 
	 * @param poly1 First input polynomial (front of polynomial linked list)
	 * @param poly2 Second input polynomial (front of polynomial linked list
	 * @return A new polynomial which is the sum of the input polynomials - the returned node
	 *         is the front of the result polynomial
	 */
	public static Node add(Node poly1, Node poly2) {
		Node sum = null;
		Node revSum = null;
		//checks if either polynomial is 0; if so, return null(both), if only one is null, other is returned
		if(poly1 == null && poly2 == null) {
			return null;
		}else if(poly1 != null && poly2 == null) {
			return poly1;
		}else if (poly1 == null && poly2 != null) {
			return poly2;
		}else {
			
			int highestDegree1 = 0;
			int highestDegree2 = 0;
			int highestDegreeBoth = 0;
			
			//finds highest degree in polynomial 1
			for(Node p1 = poly1; p1 != null; p1 = p1.next) {
				if(p1.term.degree > highestDegree1) {
					highestDegree1 = p1.term.degree;
				}
			}
			//finds highest degree in polynomial 2
			for(Node p2 = poly2; p2 != null; p2 = p2.next) {
				if(p2.term.degree > highestDegree2) {
					highestDegree2 = p2.term.degree;
				}
			}
			//finds highest degree term out of both polynomials
			if(highestDegree1 >= highestDegree2) {
				highestDegreeBoth = highestDegree1;
			}else {
				highestDegreeBoth = highestDegree2;
			}
				
			//Adds coefficients of terms with same degree start at degree = 0 and finish with highest degree (found above)
			//For each degree, new Node is created with the added coefficient value and degree i --> reset addedCoefficient and move one degree up
			float addedCoeff = 0;
			for(int i = 0; i <= highestDegreeBoth; i++) {

				for(Node n1 = poly1; n1 != null; n1 = n1.next) {
					if(n1.term.degree == i) {
						addedCoeff += n1.term.coeff;
					}
				}
				for(Node n2 = poly2; n2 != null; n2 = n2.next) {
					if(n2.term.degree == i) {
						addedCoeff += n2.term.coeff;
					}
				}
				sum = new Node(addedCoeff, i, sum);
				addedCoeff = 0;
			}
			
		}
		//creates new node that does not include terms with coefficient = 0
		//reverse the order of the node, so it will print in decreasing order (by degree)
		for(Node s = sum; s != null; s = s.next) {
			if(s.term.coeff != 0) {
				revSum = new Node(s.term.coeff, s.term.degree, revSum);
			}
		}
		return revSum;
	}
	
	/**
	 * Returns the product of two polynomials - DOES NOT change either of the input polynomials.
	 * The returned polynomial MUST have all new nodes. In other words, none of the nodes
	 * of the input polynomials can be in the result.
	 * 
	 * @param poly1 First input polynomial (front of polynomial linked list)
	 * @param poly2 Second input polynomial (front of polynomial linked list)
	 * @return A new polynomial which is the product of the input polynomials - the returned node
	 *         is the front of the result polynomial
	 */
	public static Node multiply(Node poly1, Node poly2) {
		Node product = null;
		Node simplifiedProduct = null;
		Node finalProduct = null;
		int newDegree = 0;
		float newCoeff = 0;
		
		//checks if both polynomials have terms before multiplying, if not: return null
		if(poly1 != null && poly2 != null) {
			//Multiplies each term with each other and puts the new terms in a new polynomial (FOIL)
			for(Node p1 = poly1; p1 != null; p1 = p1.next) {
				for(Node p2 = poly2; p2 != null; p2 = p2.next) {
					newDegree = p1.term.degree + p2.term.degree;
					newCoeff = (p1.term.coeff)*(p2.term.coeff);
					
					product = new Node(newCoeff, newDegree, product);
					
				}
			}
			
			//finds the highest degree in the product
			int highestDegree = product.term.degree;
			for(Node p = product; p != null; p = p.next) {
				if(p.term.degree > highestDegree) {
					highestDegree = p.term.degree;
				}
			}
			
			//Adds coefficients of terms with same degree start at degree = 0 and finish with highest degree (found above)
			//For each degree, new Node is created with the added coefficient value and degree i --> reset addedCoefficient and move one degree up
			float addedCoeff = 0;
			for(int i = 0; i <= highestDegree; i++) {
				for(Node n = product; n != null; n = n.next) {
					if(n.term.degree == i) {
						addedCoeff += n.term.coeff;
					}
				}
				simplifiedProduct = new Node(addedCoeff, i, simplifiedProduct);
				addedCoeff = 0;
			}
			//creates new node that does not include terms with coefficient = 0
			//reverse the order of the node, so it will print in decreasing order (by degree)
			for(Node p = simplifiedProduct; p != null; p = p.next) {
				if(p.term.coeff != 0) {
					finalProduct = new Node(p.term.coeff, p.term.degree, finalProduct);
				}
				
			}
		}
		
		return finalProduct;
	}
	
		
	/**
	 * Evaluates a polynomial at a given value.
	 * 
	 * @param poly Polynomial (front of linked list) to be evaluated
	 * @param x Value at which evaluation is to be done
	 * @return Value of polynomial p at x
	 */
	public static float evaluate(Node poly, float x) {

		float total = 0; //Declares a float that will hold the evaluated value of the polynomial
		
		if(poly != null) { //Checks if polynomial is empty. If it is, return 0. If it is not, proceed to evaluate.
			for (Node n = poly; n != null; n = n.next) { //Will allow the method to able to evaluate each node(term) in the polynomial
			
				total += n.term.coeff*(Math.pow(x, n.term.degree)); //Math.pow returns x^n, n being any integer. This value is then multiplied by the coefficient.
																	//The value of the term is then added to total.
			}	
		}
		
		return total; //Returns the evaluation of the polynomial at inputed x.
	}
	
	/**
	 * Returns string representation of a polynomial
	 * 
	 * @param poly Polynomial (front of linked list)
	 * @return String representation, in descending order of degrees
	 */
	public static String toString(Node poly) {
		if (poly == null) {
			return "0";
		} 
		
		String retval = poly.term.toString();
		for (Node current = poly.next ; current != null ;
		current = current.next) {
			retval = current.term.toString() + " + " + retval;
		}
		return retval;
	}	
}
