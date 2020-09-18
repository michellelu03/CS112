package structures;

import java.util.*;

/**
 * This class implements an HTML DOM Tree. Each node of the tree is a TagNode, with fields for
 * tag/text, first child and sibling.
 * 
 */
public class Tree {
	
	/**
	 * Root node
	 */
	TagNode root=null;
	
	/**
	 * Scanner used to read input HTML file when building the tree
	 */
	Scanner sc;
	
	/**
	 * Initializes this tree object with scanner for input HTML file
	 * 
	 * @param sc Scanner for input HTML file
	 */
	public Tree(Scanner sc) {
		this.sc = sc;
		root = null;
	}
	
	/**
	 * Builds the DOM tree from input HTML file, through scanner passed
	 * in to the constructor and stored in the sc field of this object. 
	 * 
	 * The root of the tree that is built is referenced by the root field of this object.
	 */
	
	
	public void build() {
		
		if(sc == null) {
			return;
		}
		
		Stack<TagNode> givenTags = new Stack<TagNode>();
		TagNode temp = null;
		
		String strLine = sc.nextLine();
		
		root = new TagNode("html", null, null); 
		givenTags.push(root);
		
		while(sc.hasNextLine()) {
			strLine = sc.nextLine();
			boolean lineIsATag = false;
			
			if(strLine.charAt(0) == '<') {
				if(strLine.charAt(1) == '/') {
					givenTags.pop();
					continue;
				}else {
					lineIsATag = true;
				}
			}
		
			temp = new TagNode(strLine.replace("<", "").replace(">", ""), null, null);
			TagNode firstChild = givenTags.peek().firstChild;
			
			if(firstChild == null) {
				givenTags.peek().firstChild = temp;
			}else {
				TagNode pointer = givenTags.peek().firstChild;
					while(pointer.sibling != null) {
						pointer = pointer.sibling;
					}
				pointer.sibling = temp;
			}
		
			if(lineIsATag) {
				givenTags.push(temp);
			}
			
		}
		
	}
	
	
	/**
	 * Replaces all occurrences of an old tag in the DOM tree with a new tag
	 * 
	 * @param oldTag Old tag
	 * @param newTag Replacement tag
	 */
	public void replaceTag(String oldTag, String newTag) {
		
		replaceTag(oldTag, newTag, root.firstChild);
	}
	
	private void replaceTag(String oldTag, String newTag, TagNode n) {
		if(oldTag.contentEquals(n.tag)) {
			n.tag = newTag;
		}
		if(n.sibling != null) {
			replaceTag(oldTag, newTag, n.sibling);
		}
		if(n.firstChild != null) {
			replaceTag(oldTag, newTag, n.firstChild);
		}
		
	}
	
	/**
	 * Boldfaces every column of the given row of the table in the DOM tree. The boldface (b)
	 * tag appears directly under the td tag of every column of this row.
	 * 
	 * @param row Row to bold, first row is numbered 1 (not 0).
	 */
	
	
	public void boldRow(int row) {
		boldRow(row, root, root.firstChild, 0);
	}
	
	private void boldRow(int row, TagNode parent, TagNode child, int currentRowNum) {
		if(child == null) {
			return;
		}else if(child.tag.equals("tr")) {
			currentRowNum++;
		}
		
		if(child.firstChild == null && currentRowNum == row) {
				TagNode newChild = new TagNode("b", child, null);
				parent.firstChild = newChild;
		}
		
		
		boldRow(row, child, child.firstChild, currentRowNum);
		boldRow(row, child, child.sibling, currentRowNum);		
	}
	

	
	
	
	/**
	 * Remove all occurrences of a tag from the DOM tree. If the tag is p, em, or b, all occurrences of the tag
	 * are removed. If the tag is ol or ul, then All occurrences of such a tag are removed from the tree, and, 
	 * in addition, all the li tags immediately under the removed tag are converted to p tags. 
	 * 
	 * @param tag Tag to be removed, can be p, em, b, ol, or ul
	 */
	
	private TagNode getLastSibling(TagNode child) {
		while (child.sibling != null) {
			child = child.sibling;
		}
		return child;
	}

	private void removeTag(TagNode child) {
		if(child == null) {
			return;
		} else if (child.tag.equals("li")) {
			child.tag = "p";
		}
		removeTag(child.sibling);
	}

	
	private boolean hasTag(String tag, TagNode child) {
		if (child == null) {
			return false;
		}
		else if (child.tag.equals(tag)) {
			return true;
		}
		return (hasTag(tag, child.firstChild) || hasTag(tag, child.sibling));

	}
	private void addLastSibling(TagNode child, TagNode sibling) {
		child = getLastSibling(child);
		child.sibling = sibling;
	}
	public void removeTag(String tag) {
		if(root != null) {
			while(hasTag(tag, root)) {
				removeTag(tag, root, root.firstChild);
			}
		}
	}

	private void removeTag(String tag, TagNode parent, TagNode child) {
		if (child != null && parent != null) {
		if(child.tag.equals(tag)) {
			if(tag.equals("ul") || tag.equals("ol")) {
				removeTag(child.firstChild);
			}
			if(parent.firstChild == child) {
				parent.firstChild = child.firstChild;
				addLastSibling(child.firstChild, child.sibling);
			}else if(parent.sibling == child){
				addLastSibling(child.firstChild, child.sibling);
				parent.sibling = child.firstChild;
			}
		}
		
		parent = child;
		removeTag(tag, parent, child.sibling);
		removeTag(tag, parent, child.firstChild);
		}
	}
	
	/**
	 * Adds a tag around all occurrences of a word in the DOM tree.
	 * 
	 * @param word Word around which tag is to be added
	 * @param tag Tag to be added
	 */

	public void addTag(String word, String tag) {
		if(root != null && !word.equals("") && !tag.equals("")) {
			addTag(word, tag, root.firstChild);
		}
	}
	
	private void addTag(String word, String tag, TagNode curr) {
		if(curr != null) {
			if(curr.tag.toLowerCase().contains(word.toLowerCase())) {
				if(curr.tag.equalsIgnoreCase(word)) {
					curr.tag = tag;
					curr.firstChild = new TagNode(word, curr.firstChild, null);
				}else if(curr.tag.toLowerCase().contains(word.toLowerCase())) {
					String punctuation = "";
					TagNode sib = curr.sibling;
					int startOf = curr.tag.toLowerCase().indexOf(word.toLowerCase());
					int endOf = curr.tag.toLowerCase().indexOf(word.toLowerCase()) + word.length();
					String before = curr.tag.substring(0, startOf);
					String after = curr.tag.substring(endOf);
					String s = curr.tag.substring(curr.tag.toLowerCase().indexOf(word.toLowerCase()), curr.tag.toLowerCase().indexOf(word.toLowerCase()) + word.length());
					
					if(after.length() != 0) {
						if(after.length() > 1) {
							if(isPunct(after.charAt(0)) && !isPunct(after.charAt(1))) {
								punctuation = "" + after.charAt(0);
								after = after.substring(1);
							}
						}
					}
					if (after.length() == 0 || (after.length() >= 1 && (after.charAt(0) == ' ' || this.isPunct(after.charAt(0))))){
							if(after.equals("!") || after.equals("?") || after.equals(".") || after.equals(",")){
								s = s + after;
								after = "";
							}
							
							TagNode newChild = new TagNode(s + punctuation, null, null); 
							curr.tag = before;
							curr.sibling = new TagNode(tag, newChild, null);
							
							if(after.length() > 0) {
								if(sib == null) {
									curr.sibling.sibling = new TagNode(after, null, null);
								}else {
									curr.sibling.sibling = new TagNode(after, null, sib);
								}
							}else if(sib != null) {
								curr.sibling.sibling = sib;
							}
						}
					}
					

				
				addTag(word, tag, curr.sibling.sibling);
				
			}else {
				addTag(word, tag, curr.sibling);
				addTag(word, tag, curr.firstChild);
			}
		}
	}

	private boolean isPunct(char a) {
		if(a == '?'){
			return true; 
		}
		if(a == '!') {
			return true;
		}
		if(a == ',') {
			return true;
		}
		if(a == '.') {
			return true;
		}
		return false;
	}

	/**
	 * Gets the HTML represented by this DOM tree. The returned string includes
	 * new lines, so that when it is printed, it will be identical to the
	 * input file from which the DOM tree was built.
	 * 
	 * @return HTML string, including new lines. 
	 */
	public String getHTML() {
		StringBuilder sb = new StringBuilder();
		getHTML(root, sb);
		return sb.toString();
	}
	
	private void getHTML(TagNode root, StringBuilder sb) {
		for (TagNode ptr=root; ptr != null;ptr=ptr.sibling) {
			if (ptr.firstChild == null) {
				sb.append(ptr.tag);
				sb.append("\n");
			} else {
				sb.append("<");
				sb.append(ptr.tag);
				sb.append(">\n");
				getHTML(ptr.firstChild, sb);
				sb.append("</");
				sb.append(ptr.tag);
				sb.append(">\n");	
			}
		}
	}
	
	/**
	 * Prints the DOM tree. 
	 *
	 */
	public void print() {
		print(root, 1);
	}
	
	private void print(TagNode root, int level) {
		for (TagNode ptr=root; ptr != null;ptr=ptr.sibling) {
			for (int i=0; i < level-1; i++) {
				System.out.print("      ");
			};
			if (root != this.root) {
				System.out.print("|----");
			} else {
				System.out.print("     ");
			}
			System.out.println(ptr.tag);
			if (ptr.firstChild != null) {
				print(ptr.firstChild, level+1);
			}
		}
	}
}
