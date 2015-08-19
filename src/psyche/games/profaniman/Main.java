package psyche.games.profaniman;


import java.util.Scanner;
import static psyche.games.profaniman.Utilities.*;

public class Main 
{
	public static final int MAX_WRONG_TRIES = 5;
	
	private static String[] words = 
			{"bell end" , "cock muncher" , "ass belonker" , "gregs baby" ,
				"vinegar strokes" , "turtles head" , "fudge packer" , 
					"japs eye", "big floppy donkey dick" , "brown trout", "knackers", "ball sack"};
	
	private static char[] tokens, guess;
	private static int guessCount = 0;
	private static String activeWord;
	private static Scanner input = new Scanner(System.in);
	private static boolean[] correctGuesses;
	private static int correctGuessCounter = 0;
	private static int guessesToWin = 0;
	private static int incorrectGuesses = 0;
	private static String[] answer, sucGuesses, unsucGuesses;
	private static boolean gameOver, win, lose;
	
	
	
	public static void main(String[] args)
	{
		System.out.println("Working......");
		initialise();
		initBoolArr();		
		initAnswer();
		
		printStringArray(answer);
		guess();
	}
	
	public static void initialise()
	{
		activeWord = randomWordSelect(words);
		System.out.printf("%s\n", activeWord);
		guessesToWin = countUniqueChars(activeWord) - 1;
		System.out.println(guessesToWin);
		gameOver = win = lose = false;
		tokens = stringSplitToChars(activeWord);
		answer = new String[tokens.length];
		unsucGuesses = new String[MAX_WRONG_TRIES];
		sucGuesses = new String[guessesToWin];
		correctGuesses = new boolean[tokens.length];
		
		guess = new char[tokens.length];
	}
	
	public static void initBoolArr()
	{
		for(int i = 0; i < correctGuesses.length; i++)
		{
			if(tokens[i] == 32)
			{
				correctGuesses[i] = true;
			}
			else
				correctGuesses[i] = false;
		}
	}
	
	public static void guess()
	{
		do
		{
			try{
			input.nextLine().getChars(0, 1, guess, guessCount);	
			if(guess[guessCount] >= '0' && guess[guessCount] <= '9')
			{
				System.out.println("Thats a number you spaz!");
				continue;
			}
			}catch(StringIndexOutOfBoundsException e)
			{
				System.out.println("Enter a letter!");
			}
			checkGuess(guessCount);
			guessCount++;
		}
		while(!gameIsOver());
		
		if(win)
			System.out.println("You Win");
		else if(lose)
		{
			System.out.println("You Suck");
			System.out.printf("The profanity was: " + "%s\n", activeWord );
		}
			
	}

	public static void initAnswer()
	{
		for(int i = 0; i < tokens.length; i++)
		{
			if(correctGuesses[i])
			{
				answer[i] = "/ ";
			}
				
			else
				answer[i] = "_ ";
		}
		
	}
	
	public static void addToAnswer(String s)
	{
		
		for(int i = 0; i < tokens.length; i++)
		{
			if(correctGuesses[i] && answer[i] == "_ ")
			{
				answer[i] = (s + " ");
			}
		}
		printStringArray(answer);
	}
	
	public static void checkGuess(int counter)
	{
		char c = guess[counter];
		if(c >= 'A' && c <= 'Z')
		{
			c += 32;
		}
		String s = String.valueOf(c);
		
		boolean dupCorrGuess = false;
		boolean dupUncorrGuess = false;
		
		if(sucGuesses[0] != null)
		{
			dupCorrGuess = checkPrevGuesses(sucGuesses, s);
		}
		if(unsucGuesses[0] != null)
		{
			dupUncorrGuess = checkPrevGuesses(unsucGuesses, s);
		}
		if(activeWord.contains(s))
		{
			if(dupCorrGuess)
			{
				guessCount--;
				System.out.println("Duplicate Guess You Knob!");
			}
			else
			{
				sucGuesses[correctGuessCounter] = (s + " ");
				correctGuessCounter++;
				amendBoolArray(s);
				addToAnswer(s);
			}
		}
		else
		{
			if(dupUncorrGuess)
			{
				guessCount--;
				System.out.println("Its Still Wrong, Retard");
			}
			else
			{
				unsucGuesses[incorrectGuesses] = (s + " ");
				incorrectGuesses++;
				System.out.printf("Guesses remaining: " + "%d\n", (MAX_WRONG_TRIES - incorrectGuesses));
			}
		}
		
		
		System.out.print("Successful Letters: ");
		printStringArray(sucGuesses);
		System.out.print("Unsuccessful Letters: ");
		printStringArray(unsucGuesses);
	}
	
	public static boolean checkPrevGuesses(String[] s ,String curGuess)
	{
		boolean prevGuess = false;
		int counter = nElementsNotNull(s) - 1;
		for(int i = counter; i >= 0; i--)
		{
			if(s[i].contains(curGuess))
				prevGuess = true;
		}
		return prevGuess;
	}
		
	public static void amendBoolArray(String letter)
	{
		for(int i = 0; i < correctGuesses.length; i++)
		{
			String s = String.valueOf(tokens[i]);
			if(s.equals(letter))
			{
				correctGuesses[i] = true;
			}
		}
	}
	
	public static boolean gameIsOver()
	{
		int guessesRemaining = MAX_WRONG_TRIES - incorrectGuesses;
		if(correctGuessCounter == guessesToWin)
		{
			win = true;
			gameOver = true;
		}
		if(guessesRemaining == 0)
		{
			lose = true;
			gameOver = true;
		}
		return gameOver;
	}
	
	
	
	
}
