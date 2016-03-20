package solitaire;

import java.io.IOException;
import java.util.Scanner;
import java.util.Random;
import java.util.NoSuchElementException;

/**
 * This class implements a simplified version of Bruce Schneier's Solitaire Encryption algorithm.
 *
 * @author RU NB CS112
 */
public class Solitaire {

	/**
	 * Circular linked list that is the deck of cards for encryption
	 */
	CardNode deckRear;

	/**
	 * Makes a shuffled deck of cards for encryption. The deck is stored in a circular
	 * linked list, whose last node is pointed to by the field deckRear
	 */
	public void makeDeck() {
		// start with an array of 1..28 for easy shuffling
		int[] cardValues = new int[28];
		// assign values from 1 to 28
		for (int i=0; i < cardValues.length; i++) {
			cardValues[i] = i+1;
		}

		// shuffle the cards
		Random randgen = new Random();
		for (int i = 0; i < cardValues.length; i++) {
			int other = randgen.nextInt(28);
			int temp = cardValues[i];
			cardValues[i] = cardValues[other];
			cardValues[other] = temp;
		}

		// create a circular linked list from this deck and make deckRear point to its last node
		CardNode cn = new CardNode();
		cn.cardValue = cardValues[0];
		cn.next = cn;
		deckRear = cn;
		for (int i=1; i < cardValues.length; i++) {
			cn = new CardNode();
			cn.cardValue = cardValues[i];
			cn.next = deckRear.next;
			deckRear.next = cn;
			deckRear = cn;
		}
	}

	/**
	 * Makes a circular linked list deck out of values read from scanner.
	 */
	public void makeDeck(Scanner scanner)
			throws IOException {
		CardNode cn = null;
		if (scanner.hasNextInt()) {
			cn = new CardNode();
			cn.cardValue = scanner.nextInt();
			cn.next = cn;
			deckRear = cn;
		}
		while (scanner.hasNextInt()) {
			cn = new CardNode();
			cn.cardValue = scanner.nextInt();
			cn.next = deckRear.next;
			deckRear.next = cn;
			deckRear = cn;
		}
	}

	/**
	 * Implements Step 1 - Joker A - on the deck.
	 */
	void jokerA() {
		CardNode ptr = this.deckRear.next;
		CardNode prev = this.deckRear;
		CardNode front = this.deckRear.next;
		//System.out.println(ptr.cardValue + "  " +  ptr.next.cardValue + "  " + prev.cardValue);


		for(; ptr.cardValue != 27; ptr = ptr.next, prev = prev.next){
		}
		

		if(ptr.next == this.deckRear){
			CardNode temp = this.deckRear;
			prev.next = temp;
			temp.next = ptr;
			this.deckRear = ptr;
			ptr.next = front;

		}
		else if(ptr == this.deckRear){
			CardNode temp = front.next;
			this.deckRear = front;
			prev.next = front;
			front.next = ptr;
			ptr.next = temp;
		}
		else{
			CardNode temp = ptr.next;
			ptr.next = temp.next;
			temp.next = ptr;
			prev.next = temp;
		}

	}

	/**
	 * Implements Step 2 - Joker B - on the deck.
	 */
	void jokerB() {
		CardNode ptr = this.deckRear.next;
		CardNode prev = this.deckRear;
		CardNode front = this.deckRear.next;

		for(; ptr.cardValue != 28; ptr = ptr.next, prev = prev.next){
		}

		if(ptr == this.deckRear){

			CardNode temp = front.next;
			CardNode tempnext = temp.next;
			this.deckRear = front;
			prev.next = front;
			front.next = temp;
			temp.next = ptr;
			ptr.next = tempnext;

		}else if (ptr.next == this.deckRear){
			CardNode temp = ptr.next;
			CardNode frontnext = front.next;
			prev.next = ptr.next;
			temp.next = front;
			this.deckRear = front;
			front.next = ptr;
			ptr.next = frontnext;

		}
		else {CardNode temp = ptr.next;

		prev.next = temp;
		ptr.next = temp.next.next;
		temp.next.next = ptr;
		}

	}

	/**
	 * Implements Step 3 - Triple Cut - on the deck.
	 */
	void tripleCut() {
		CardNode joke1 = this.deckRear.next;
		CardNode end = this.deckRear;
		CardNode front = this.deckRear.next;
		CardNode joke2;
		CardNode prev = this.deckRear;
		CardNode afterJoke2;
		CardNode newEnd;

		if((this.deckRear.cardValue == 27 || this.deckRear.cardValue==28) && (this.deckRear.next.cardValue ==27 ||this.deckRear.next.cardValue ==28)){

		}
		else if (joke1.cardValue == 28 || joke1.cardValue == 27){

			for(joke2 = joke1.next; joke2.cardValue != 28 && joke2.cardValue != 27; joke2 = joke2.next){
			}
			this.deckRear = joke2;
			end.next = joke1;

		}
		else if(end.cardValue==28 || end.cardValue == 27){
			for(newEnd = end.next; newEnd.next.cardValue != 28 && newEnd.next.cardValue != 27; newEnd = newEnd.next){
			}
			this.deckRear = newEnd;
			end.next = front;
		}
		else {
			for(; joke1.cardValue != 28 && joke1.cardValue != 27; joke1 = joke1.next, prev = prev.next){
			}

			for(joke2 = joke1.next; joke2.cardValue != 28 && joke2.cardValue != 27; joke2 = joke2.next){
			}
			this.deckRear = prev;
			afterJoke2 = joke2.next;
			end.next = joke1;
			prev.next = afterJoke2;
			joke2.next = front;

		}  
	}

	/**
	 * Implements Step 4 - Count Cut - on the deck.
	 */
	void countCut() {  
		CardNode ptr = this.deckRear.next;
		CardNode end = this.deckRear;
		CardNode front = this.deckRear.next;
		CardNode secondEnd;
		int count = this.deckRear.cardValue;
		if(count ==27 || count == 28){
			
		}
		else {
		for(int i = 1; i <count; i++,ptr=ptr.next){
			
		}
		for(secondEnd = ptr; secondEnd.next !=this.deckRear; secondEnd=secondEnd.next){
			
		}
		CardNode diffEnd = ptr.next;
		secondEnd.next = front;
		end.next = diffEnd;
		ptr.next =end;
		
		
		
		}
	}

	/**
	 * Gets a key. Calls the four steps - Joker A, Joker B, Triple Cut, Count Cut, then
	 * counts down based on the value of the first card and extracts the next card value
	 * as key. But if that value is 27 or 28, repeats the whole process (Joker A through Count Cut)
	 * on the latest (current) deck, until a value less than or equal to 26 is found, which is then returned.
	 *
	 * @return Key between 1 and 26
	 */
	int getKey() {
		jokerA();
		//printList(this.deckRear);
		jokerB();
		//printList(this.deckRear);
		tripleCut();
		//printList(this.deckRear);
		countCut();
		//printList(this.deckRear);
		CardNode ptr = this.deckRear;
		int count = this.deckRear.next.cardValue;
		if(count == 28){
			count = 27;
		}
		for(int i = 1; i <=count; i++,ptr=ptr.next){
			
		}
		while (ptr.next.cardValue == 28 || ptr.next.cardValue == 27){
			printList(this.deckRear);

			jokerA();
			//printList(this.deckRear);
			jokerB();
			//printList(this.deckRear);

			tripleCut();
			//printList(this.deckRear);

			countCut();
			//printList(this.deckRear);

			
			ptr = this.deckRear.next;
			count = this.deckRear.next.cardValue;
			if(count == 28){
				count = 27;
				
			}
			for(int i = 2; i <=count; i++,ptr=ptr.next){
				
			}
		}
		int key = ptr.next.cardValue; 
		
		//System.out.println(key);
		
		return key;
	}

	/**
	 * Utility method that prints a circular linked list, given its rear pointer
	 *
	 * @param rear Rear pointer
	 */
	private static void printList(CardNode rear) {
		if (rear == null) {
			return;
		}
		System.out.print(rear.next.cardValue);
		CardNode ptr = rear.next;
		do {
			ptr = ptr.next;
			System.out.print("," + ptr.cardValue);
		} while (ptr != rear);
		System.out.println("\n");
	}

	/**
	 * Encrypts a message, ignores all characters except upper case letters
	 *
	 * @param message Message to be encrypted
	 * @return Encrypted message, a sequence of upper case letters only
	 */
	public String encrypt(String message) {
		
		
		String newMessage = message.toUpperCase();
		int count =0;
		for(int i =0; i<newMessage.length(); i++){
			
			 if(Character.isLetter(newMessage.charAt(i)) == true ){
				count++;
			} 
			
		}
		System.out.println(count);
				String FINAL = "";
		
		int encrypt;
		
		for(int i=0; i<newMessage.length(); i++ ){
			 if(Character.isLetter(newMessage.charAt(i)) == true ){
				 int word = newMessage.charAt(i)-'A'+1;
					int key = getKey();
					if((word + key) >26){
						encrypt = word +key - 26;
					}else {
						encrypt= word+ key;
					}
					 FINAL = FINAL + (char)(encrypt-1+'A'); 
					count++;
				
			   
		    
		}
		}
		

		String output1 = new String(FINAL);
		
		
		
		
		return output1;
		
	}
	

	

	/**
	 * Decrypts a message, which consists of upper case letters only
	 *
	 * @param message Message to be decrypted
	 * @return Decrypted message, a sequence of upper case letters only
	 */
	public String decrypt(String message) {
		
		String newMessage = message.toUpperCase();
		int count =0;
		for(int i =0; i<newMessage.length(); i++){
			
			 if(Character.isLetter(newMessage.charAt(i)) == true ){
				count++;
			} 
			
		}
		System.out.println(count);
				String FINAL = "";
		
		int encrypt;
		
		for(int i=0; i<newMessage.length(); i++ ){
			 if(Character.isLetter(newMessage.charAt(i)) == true ){
				 int word = newMessage.charAt(i)-'A'+1;
					int key = getKey();
					if((word - key) <0){
						encrypt = word - key + 26;
					}else {
						encrypt= word-  key;
					}
					 FINAL = FINAL + (char)(encrypt-1+'A'); 
					count++;
				
			   
		    
		}
		}
		

		String output1 = new String(FINAL);
		
		
		
		
		return output1;
	}
}
