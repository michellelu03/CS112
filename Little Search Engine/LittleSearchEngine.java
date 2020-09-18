package lse;

import java.io.*;
import java.util.*;

/**
 * This class builds an index of keywords. Each keyword maps to a set of pages in
 * which it occurs, with frequency of occurrence in each page.
 *
 */
public class LittleSearchEngine {
	/*
	// Driver:
	public static void main(String[] args) throws FileNotFoundException {
        // TODO Auto-generated method stub
        LittleSearchEngine lse = new LittleSearchEngine();
        lse.makeIndex("docs.txt", "noisewords.txt"); //"docs.txt" "theHeavenlyTestCase.txt"
        

        
//       lse.loadKeywordsFromDocument("avesta.txt");
//       lse.loadKeywordsFromDocument("genesis1.txt");
//       lse.loadKeywordsFromDocument("gita.txt");
//       lse.loadKeywordsFromDocument("guruGranthSahib.txt");
//       lse.loadKeywordsFromDocument("quran.txt");
//       lse.loadKeywordsFromDocument("shemot.txt");
//       
//       System.out.println("TEST CASES FROM HELL: ");
//       //System.out.println("\n1: " + lse.top5search("Israel", "Holy"));
//       System.out.println("\n2: " + lse.top5search("God", "Israel"));
//       System.out.println("\n3: " + lse.top5search("God", "created"));
//       //System.out.println("\n4: " + lse.top5search("Persia", "rain"));
//       //System.out.println("\n5: " + lse.top5search("Persia", "knife"));
//       System.out.println("\n6: " + lse.top5search("light", "knife"));
//       System.out.println("\n7: " + lse.top5search("love", "war"));
//       //System.out.println("\n8: " + lse.top5search("knife", "noon"));
//       //System.out.println("\n9: " + lse.top5search("moon", "noon"));
//       System.out.println("\n10: " + lse.top5search("river", "harvest"));
//       //System.out.println("\n11: " + lse.top5search("have", "want"));
//       //System.out.println("\n12: " + lse.top5search("ain't", "don't"));
//       //System.out.println("13: " + lse.top5search("have", "haven't"));
//       
//        
//        System.out.println("1: " + lse.top5search("third", "round"));
//        System.out.println("2: " + lse.top5search("house", "life"));
//        System.out.println("3: " + lse.top5search("anything", "life"));
//        System.out.println("4: " + lse.top5search("stupid", "days"));
//        System.out.println("5: " + lse.top5search("Earth", "concerning"));
//        System.out.println("6: " + lse.top5search("bad", "food"));
//        System.out.println("7: " + lse.top5search("curious", "deep"));
//        System.out.println("8: " + lse.top5search("back", "smallest"));
//        System.out.println("9: " + lse.top5search("pop", "kitty"));
//        System.out.println("10: " + lse.top5search("freeze", "JOHN"));
//        
//        
//        // getKeyword
//        System.out.println("getKeyword");
//        System.out.println("================================");
//        String s[] = {"!distance.", "equi-distant", "Rabbit", "Between", "we're",
//                "World...", "World?!", "What,ever", "bruh:bruh.;;;;;"};
//       
//        for (int i = 0; i < s.length; i++) {
//            System.out.println(lse.getKeyword(s[i]));
//        }
//        System.out.println("================================");
//        System.out.println("insertLastOccurrence");
//        System.out.println("================================");
//        // insertLastOccurrence
//        int data[] = {12, 8, 7, 5, 3, 2, 6};
//        ArrayList<Occurrence> occs = new ArrayList<Occurrence>();
//        for (int i = 0; i < data.length; i++) {
//            occs.add(new Occurrence("Doc", data[i]));
//        }
//        ArrayList<Integer> a = lse.insertLastOccurrence(occs);
//        System.out.print("Sequence: ");
//        for (int i = 0; i < a.size(); i ++) {
//            System.out.print(a.get(i) + " ");
//        }
//        System.out.println();
//        System.out.print("Result: ");
//        for (int i = 0; i < occs.size(); i++) {
//            System.out.print(occs.get(i).frequency + " ");
//        }
//        System.out.println();
//        System.out.println("================================");
//        System.out.println("top5search");
//        System.out.println("================================");
//        ArrayList<String> top = lse.top5search("deep", "world");
//        System.out.println(top);
//       
//       
//       
//        System.out.println();
//        System.out.println("================================");
//        System.out.println("More Testing");
//        System.out.println("================================");
//       
//        //getKeyword
//        String[] words = {"Word", "night,", "question??", "Could", "test-case"};
//        String[] wordsAns = {"word", "night", "question", null, null};
//        for(int i = 0; i < words.length; i++){
//            if(lse.getKeyword(words[i]) == null){
//                if(lse.getKeyword(words[i]) == wordsAns[i]){
//                    System.out.println(lse.getKeyword(words[i]) + " is right");
//                }
//                else{
//                    System.out.println(lse.getKeyword(words[i]) + " is WRONG");
//                }
//            }
//           
//            else if(lse.getKeyword(words[i]).equals(wordsAns[i])){
//                System.out.println(lse.getKeyword(words[i]) + " is right");
//            }
//            else{
//                System.out.println(lse.getKeyword(words[i]) + " is WRONG");
//            }
//        }
//       
//        //insertLastOccurrence
//        System.out.println();
//       
//        ArrayList<Occurrence> occLst1 = new ArrayList<Occurrence>();
//        occLst1.add(new Occurrence("doc1.txt", 20));
//        occLst1.add(new Occurrence("doc2.txt", 15));
//        occLst1.add(new Occurrence("doc3.txt", 14));
//        occLst1.add(new Occurrence("doc4.txt", 12));
//        occLst1.add(new Occurrence("doc5.txt", 12));
//        occLst1.add(new Occurrence("doc6.txt", 10));
//        occLst1.add(new Occurrence("doc7.txt", 8));
//        occLst1.add(new Occurrence("doc8.txt", 20));
//        ArrayList<Integer> mids1 = lse.insertLastOccurrence(occLst1);
//       
//        ArrayList<Integer> occLst1Ans = new ArrayList<Integer>();
//        occLst1Ans.add(3);
//        occLst1Ans.add(1);
//        occLst1Ans.add(0);
//       
//        for(int i = 0; i < mids1.size(); i++){
//            if(mids1.get(i) == occLst1Ans.get(i)){
//                System.out.println(mids1.get(i) + " is right");
//            }
//            else{
//                System.out.println(mids1.get(i) + " is WRONG");
//            }
//        }
//       
//        //ins2
// 
//        occLst1 = new ArrayList<Occurrence>();
//        occLst1.add(new Occurrence("doc1.txt", 20));
//        occLst1.add(new Occurrence("doc2.txt", 15));
//        occLst1.add(new Occurrence("doc3.txt", 14));
//        occLst1.add(new Occurrence("doc4.txt", 12));
//        occLst1.add(new Occurrence("doc5.txt", 12));
//        occLst1.add(new Occurrence("doc6.txt", 10));
//        occLst1.add(new Occurrence("doc7.txt", 8));
//        occLst1.add(new Occurrence("doc8.txt", 1));
//        mids1 = lse.insertLastOccurrence(occLst1);
//       
//        occLst1Ans = new ArrayList<Integer>();
//        occLst1Ans.add(3);
//        occLst1Ans.add(5);
//        occLst1Ans.add(6);
//       
//        for(int i = 0; i < mids1.size(); i++){
//            if(mids1.get(i) == occLst1Ans.get(i)){
//                System.out.println(mids1.get(i) + " is right");
//            }
//            else{
//                System.out.println(mids1.get(i) + " is WRONG");
//            }
//        }
//       
//       
//        //ins3
//       
//        occLst1 = new ArrayList<Occurrence>();
//        occLst1.add(new Occurrence("doc1.txt", 20));
//        occLst1.add(new Occurrence("doc2.txt", 15));
//        occLst1.add(new Occurrence("doc3.txt", 14));
//        occLst1.add(new Occurrence("doc4.txt", 12));
//        occLst1.add(new Occurrence("doc5.txt", 12));
//        occLst1.add(new Occurrence("doc6.txt", 10));
//        occLst1.add(new Occurrence("doc7.txt", 8));
//        occLst1.add(new Occurrence("doc8.txt", 13));
//        mids1 = lse.insertLastOccurrence(occLst1);
//       
//        occLst1Ans = new ArrayList<Integer>();
//        occLst1Ans.add(3);
//        occLst1Ans.add(1);
//        occLst1Ans.add(2);
//       
//        for(int i = 0; i < mids1.size(); i++){
//            if(mids1.get(i) == occLst1Ans.get(i)){
//                System.out.println(mids1.get(i) + " is right");
//            }
//            else{
//                System.out.println(mids1.get(i) + " is WRONG");
//            }
//        }
//       
//        //ins4
//        occLst1 = new ArrayList<Occurrence>();
//        occLst1.add(new Occurrence("doc1.txt", 20));
//        occLst1.add(new Occurrence("doc2.txt", 15));
//        occLst1.add(new Occurrence("doc3.txt", 14));
//        occLst1.add(new Occurrence("doc4.txt", 12));
//        occLst1.add(new Occurrence("doc5.txt", 12));
//        occLst1.add(new Occurrence("doc6.txt", 10));
//        occLst1.add(new Occurrence("doc7.txt", 8));
//        occLst1.add(new Occurrence("doc8.txt", 10));
//        mids1 = lse.insertLastOccurrence(occLst1);
//       
//        occLst1Ans = new ArrayList<Integer>();
//        occLst1Ans.add(3);
//        occLst1Ans.add(5);
//       
//        for(int i = 0; i < mids1.size(); i++){
//            if(mids1.get(i) == occLst1Ans.get(i)){
//                System.out.println(mids1.get(i) + " is right");
//            }
//            else{
//                System.out.println(mids1.get(i) + " is WRONG");
//            }
//        }

//        System.out.println();
//        ///load keywords
//        if(lse.loadKeywordsFromDocument("pohlx.txt").size() == 61){
//            System.out.println("pohlx.txt is right: " + lse.loadKeywordsFromDocument("pohlx.txt").size());
//        }
//        else{
//            System.out.println("pohlx.txt is WRONG: " + lse.loadKeywordsFromDocument("pohlx.txt").size());
//        }
//       
//        if(lse.loadKeywordsFromDocument("Tyger.txt").size() == 53){
//            System.out.println("Tyger.txt is right: " + lse.loadKeywordsFromDocument("Tyger.txt").size());
//        }
//        else{
//            System.out.println("Tyger.txt is WRONG: " + lse.loadKeywordsFromDocument("Tyger.txt").size());
//        }
//       
//        if(lse.loadKeywordsFromDocument("jude.txt").size() == 25){
//            System.out.println("jude.txt is right: " + lse.loadKeywordsFromDocument("jude.txt").size());
//        }
//        else{
//            System.out.println("jude.txt is WRONG: " + lse.loadKeywordsFromDocument("jude.txt").size());
//        }
//       
//       
//        System.out.println();
//        //merge keys
//        lse.keywordsIndex = new HashMap<String,ArrayList<Occurrence>>();
//        lse.makeIndex("2.txt", "noisewords.txt");
//       
//        if(lse.keywordsIndex.size() == 1059){
//            System.out.println("keywordsMerge is right: " + lse.keywordsIndex.size());
//        }
//        else{
//            System.out.println("keywordsMerge is WRONG: " + lse.keywordsIndex.size());
//        }
//       
//       
//        System.out.println();
//        //top5
//        lse.keywordsIndex = new HashMap<String,ArrayList<Occurrence>>();
//        lse.makeIndex("t3.txt", "noisewords.txt");
//       
//        ArrayList<String> results = new ArrayList<String>();
//        results.add("t2.txt"); results.add("t1.txt");
//       
//        for(int i =0; i < lse.top5search("bruhl", "bruh").size(); i++){
//            if(lse.top5search("bruhl", "bruh").get(i).equals(results.get(i))){
//                System.out.println("works for bruh and bruhl");
//            }
//            else{
//                System.out.println("DOES NOT WORK for bruh and bruhl");
//            }
//        }
//        System.out.println();
//       
//        if(lse.top5search("riggity", "rowscomin") == null){
//            System.out.println("riggity rows comin!");
//        }
//        else{
//            for(String doc : lse.top5search("riggity", "rowscomin")){
//                System.out.println(doc);
//            }
//        }
//        System.out.println();
//       
//       
//        results = new ArrayList<String>();
//        results.add("t2.txt"); results.add("t1.txt"); results.add("t5.txt");  results.add("t4.txt");
//        for(int i =0; i < lse.top5search("yo", "nothere").size(); i++){
//            if(lse.top5search("yo", "nothere").get(i).equals(results.get(i))){
//                System.out.println("works for yo and nothere");
//            }
//            else{
//                System.out.println("DOES NOT WORK for yo and yo nothere: ");
//                System.out.println("should be: " + results.get(i));
//                System.out.println("but is: " + lse.top5search("yo", "nothere").get(i));
//            }
//        }
//       
       
    }
	*/

	/**
	 * This is a hash table of all keywords. The key is the actual keyword, and the associated value is
	 * an array list of all occurrences of the keyword in documents. The array list is maintained in 
	 * DESCENDING order of frequencies.
	 */
	HashMap<String,ArrayList<Occurrence>> keywordsIndex;
	
	/**
	 * The hash set of all noise words.
	 */
	HashSet<String> noiseWords;
	
	/**
	 * Creates the keyWordsIndex and noiseWords hash tables.
	 */
	public LittleSearchEngine() {
		keywordsIndex = new HashMap<String,ArrayList<Occurrence>>(1000,2.0f);
		noiseWords = new HashSet<String>(100,2.0f);
	}
	
	/**
	 * Scans a document, and loads all keywords found into a hash table of keyword occurrences
	 * in the document. Uses the getKeyWord method to separate keywords from other words.
	 * 
	 * @param docFile Name of the document file to be scanned and loaded
	 * @return Hash table of keywords in the given document, each associated with an Occurrence object
	 * @throws FileNotFoundException If the document file is not found on disk
	 */
	public HashMap<String,Occurrence> loadKeywordsFromDocument(String docFile) 
	throws FileNotFoundException {
		HashMap<String, Occurrence> keyWords = new HashMap<String, Occurrence>();
		
		Scanner scan = new Scanner(new File(docFile));
		while(scan.hasNext()){ 
			String word = scan.next(); 
			word = getKeyword(word);
			if(word != null) { 
				if(keyWords.containsKey(word)) {
					keyWords.get(word).frequency++;
				}else {
					Occurrence o = new Occurrence(docFile, 1);
					keyWords.put(word, o);
				}
			}
		}
		scan.close();
		return keyWords;
	}
	
	
	/**
	 * Merges the keywords for a single document into the master keywordsIndex
	 * hash table. For each keyword, its Occurrence in the current document
	 * must be inserted in the correct place (according to descending order of
	 * frequency) in the same keyword's Occurrence list in the master hash table. 
	 * This is done by calling the insertLastOccurrence method.
	 * 
	 * @param kws Keywords hash table for a document
	 */
	public void mergeKeywords(HashMap<String,Occurrence> kws) {
		for(String k : kws.keySet()) {
			if(keywordsIndex.keySet().contains(k)) {
				keywordsIndex.get(k).add(kws.get(k));
				insertLastOccurrence(keywordsIndex.get(k));
			}else {
				ArrayList<Occurrence> o = new ArrayList<Occurrence>();
				o.add(kws.get(k));
				keywordsIndex.put(k, o);
			}
		}
	}
		
	/**
	 * Given a word, returns it as a keyword if it passes the keyword test,
	 * otherwise returns null. A keyword is any word that, after being stripped of any
	 * trailing punctuation(s), consists only of alphabetic letters, and is not
	 * a noise word. All words are treated in a case-INsensitive manner.
	 * 
	 * Punctuation characters are the following: '.', ',', '?', ':', ';' and '!'
	 * NO OTHER CHARACTER SHOULD COUNT AS PUNCTUATION
	 * 
	 * If a word has multiple trailing punctuation characters, they must all be stripped
	 * So "word!!" will become "word", and "word?!?!" will also become "word"
	 * 
	 * See assignment description for examples
	 * 
	 * @param word Candidate word
	 * @return Keyword (word without trailing punctuation, LOWER CASE)
	 */
	public String getKeyword(String word) {		
		if(word != null) {
			word = word.toLowerCase();
			String onlyLetters = "abcdefghijklmnopqrstuvwxyz";
			String punctuation = ".,?:;!";
			//REMOVES TRAILING PUNCTUATION
			int count = 0;

			for(int i = word.length()-1; i >= 0; i--) {
				if(!onlyLetters.contains(""+ word.charAt(i)) && punctuation.contains(""+word.charAt(i))) {
					count++;
				}
			}
			word = word.substring(0, word.length()-count);
			
			//CHECKS FOR IF THERE ARE SYMBOLS IN WORD
			boolean notAllLetters = false;
			for(int j = 0; j < word.length(); j++) {
				char a = word.charAt(j);
				if(!onlyLetters.contains(a+"")) {
					notAllLetters = true;
				}
			}
			//System.out.println(noiseWords.size());
			if(notAllLetters || noiseWords.contains(word)) {
				return null;
			}else {
				return word;
			}
		
		}
		
		return null;
	}
	
	
	
	
	/**
	 * Inserts the last occurrence in the parameter list in the correct position in the
	 * list, based on ordering occurrences on descending frequencies. The elements
	 * 0..n-2 in the list are already in the correct order. Insertion is done by
	 * first finding the correct spot using binary search, then inserting at that spot.
	 * 
	 * @param occs List of Occurrences
	 * @return Sequence of mid point indexes in the input list checked by the binary search process,
	 *         null if the size of the input list is 1. This returned array list is only used to test
	 *         your code - it is not used elsewhere in the program.
	 */

	public ArrayList<Integer> insertLastOccurrence(ArrayList<Occurrence> occs) {
		ArrayList<Integer> midpts = new ArrayList<Integer>();
		int m = 0;
		boolean added = false;
		if(occs.size() > 1) {
			Occurrence o = occs.get(occs.size()-1);
			int insert = o.frequency;
			int l = 0, r = occs.size()-2;
			occs.remove(occs.size()-1);
			while(l <= r) {
				m = (l + (r)) / 2;
				//System.out.println("left: " + l + "\nright: " + r + "\nmid: "+ m);
				midpts.add(m);
				if(occs.get(m).frequency == insert) {
					occs.add(m, o);	
					added = true;
					break;
				}
				if(occs.get(m).frequency < insert) {
					r = m - 1;
				}else {
					l = m + 1;
				}
			}
			if(!added) {
				occs.add(m,o);
			}
			return midpts;
		}	
		return null;
	}
	
	
	/**
	 * This method indexes all keywords found in all the input documents. When this
	 * method is done, the keywordsIndex hash table will be filled with all keywords,
	 * each of which is associated with an array list of Occurrence objects, arranged
	 * in decreasing frequencies of occurrence.
	 * 
	 * @param docsFile Name of file that has a list of all the document file names, one name per line
	 * @param noiseWordsFile Name of file that has a list of noise words, one noise word per line
	 * @throws FileNotFoundException If there is a problem locating any of the input files on disk
	 */
	public void makeIndex(String docsFile, String noiseWordsFile) 
	throws FileNotFoundException {
		// load noise words to hash table
		Scanner sc = new Scanner(new File(noiseWordsFile));
		while (sc.hasNext()) {
			String word = sc.next();
			noiseWords.add(word);
		}
		
		// index all keywords
		sc = new Scanner(new File(docsFile));
		while (sc.hasNext()) {
			String docFile = sc.next();
			HashMap<String,Occurrence> kws = loadKeywordsFromDocument(docFile);
			mergeKeywords(kws);
		}
		sc.close();
	}
	
	/**
	 * Search result for "kw1 or kw2". A document is in the result set if kw1 or kw2 occurs in that
	 * document. Result set is arranged in descending order of document frequencies. 
	 * 
	 * Note that a matching document will only appear once in the result. 
	 * 
	 * Ties in frequency values are broken in favor of the first keyword. 
	 * That is, if kw1 is in doc1 with frequency f1, and kw2 is in doc2 also with the same 
	 * frequency f1, then doc1 will take precedence over doc2 in the result. 
	 * 
	 * The result set is limited to 5 entries. If there are no matches at all, result is null.
	 * 
	 * See assignment description for examples
	 * 
	 * @param kw1 First keyword
	 * @param kw1 Second keyword
	 * @return List of documents in which either kw1 or kw2 occurs, arranged in descending order of
	 *         frequencies. The result size is limited to 5 documents. If there are no matches, 
	 *         returns null or empty array list.
	 */
	public ArrayList<String> top5search(String kw1, String kw2) {
		ArrayList<String> result = new ArrayList<String>(); //will be returned
		ArrayList<Occurrence> occur1 = new ArrayList<Occurrence>(); //holds all the occurrences of kw1
		ArrayList<Occurrence> occur2 = new ArrayList<Occurrence>(); //holds all the occurrences of kw2
		ArrayList<Occurrence> combined = new ArrayList<Occurrence>(); //holds all the occurrences of kw1 & kw2
		
		ArrayList<String> docs1 = new ArrayList<String>();
		ArrayList<String> docs2 = new ArrayList<String>();
		
		
		if(kw1 == null && kw2 == null) {	//if no keywords, return null
			return result;
		}
		if(keywordsIndex.containsKey(kw1.toLowerCase())) {
			for(Occurrence occ : keywordsIndex.get(kw1.toLowerCase())) {
				occur1.add(occ);
				docs1.add(occ.document);
			}
		}
		if(keywordsIndex.containsKey(kw2.toLowerCase())) {
			for(Occurrence occ : keywordsIndex.get(kw2.toLowerCase())) {
				occur2.add(occ);
				docs2.add(occ.document);
			}
		}
		//System.out.println("=======================================");
		//System.out.println("Occurrence 1:  " + occur1);
		//System.out.println("Occurrence 2: " +  occur2);
		
		combined.addAll(occur1);	//adds all of occurrences of the two keywords 
		combined.addAll(occur2);	//^
		//System.out.println("Combined ArrayList: " + combined);
		
		if(!occur1.isEmpty() || !occur2.isEmpty()) {	//both keywords found, sort 
			for(int i = 0; i < combined.size()-1; i++) {
				for(int j = i+1; j <= combined.size()-1; j++) {
					if(combined.get(j).frequency > combined.get(i).frequency) {
						Occurrence o = combined.get(i);
						combined.set(i, combined.get(j));
						combined.set(j, o);
					}
					//System.out.println("\tAfter loop" + i + "," + j +  "\t" + combined);
				}
			}
			//System.out.println("After 1st for loop: " + combined);
		
			for(int i = 0; i < combined.size()-1; i++) {								//deletes repeats
				for(int j = i+1; j <= combined.size()-1; j++) {
					if(combined.get(i).document.equals(combined.get(j).document)) {
						if(combined.get(i).frequency >= combined.get(j).frequency) {
							combined.remove(j);
						}else {
							combined.remove(i);
						}	
					}
				}
			}
			//System.out.println("After 2nd for loop: " + combined);
		
			for(int i = 0; i < combined.size()-1; i++) {
				for(int j = i+1; j <= combined.size()-1; j++) {
					if(combined.get(i).frequency == combined.get(j).frequency) {
						if(docs1.contains(combined.get(j).document) && docs1.contains(combined.get(i).document)) {
							//System.out.println("check frequency \nj: " + docs1.indexOf(combined.get(j).document));
							//System.out.println("i: " + docs1.indexOf(combined.get(i).document));
							if(docs1.indexOf(combined.get(j).document) < docs1.indexOf(combined.get(i).document)) {
								Occurrence o = combined.get(i);
								combined.set(i, combined.get(j));
								combined.set(j, o);
							}
						}
						if(docs1.contains(combined.get(j).document) && !docs1.contains(combined.get(i).document)){
							Occurrence o = combined.get(i);
							combined.set(i, combined.get(j));
							combined.set(j, o);
						}
					}
				}
			}
			//System.out.println("After 3rd for loop: " + combined);
			
			while(combined.size() > 5) {
				combined.remove(combined.size()-1);
			}
			
			for(Occurrence o : combined) {
				result.add(o.document);
			}
			
			return result;
			
		}
		
		return null;
	
	}
}
