/*
 * File: Yahtzee.java
 * ------------------
 * This program will eventually play the Yahtzee game.
 */

import acm.io.*;
import acm.program.*;
import acm.util.*;

public class Yahtzee extends GraphicsProgram implements YahtzeeConstants {
	public void run() {
		setupPlayers();
		initDisplay();
		playGame();
	}
	
	/**
	 * Prompts the user for information about the number of players, then sets up the
	 * players array and number of players.
	 */
	private void setupPlayers() {
		nPlayers = chooseNumberOfPlayers();	
		
		/* Set up the players array by reading names for each player. */
		playerNames = new String[nPlayers];
		for (int i = 0; i < nPlayers; i++) {
			/* IODialog is a class that allows us to prompt the user for information as a
			 * series of dialog boxes.  We will use this here to read player names.
			 */
			IODialog dialog = getDialog();
			playerNames[i] = dialog.readLine("Enter name for player " + (i + 1));
		}
	}
	
	/**
	 * Prompts the user for a number of players in this game, reprompting until the user
	 * enters a valid number.
	 * 
	 * @return The number of players in this game.
	 */
	private int chooseNumberOfPlayers() {
		/* See setupPlayers() for more details on how IODialog works. */
		IODialog dialog = getDialog();
		
		while (true) {
			/* Prompt the user for a number of players. */
			int result = dialog.readInt("Enter number of players");
			
			/* If the result is valid, return it. */
			if (result > 0 && result <= MAX_PLAYERS)
				return result;
			
			dialog.println("Please enter a valid number of players.");
		}
	}
	
	/**
	 * Sets up the YahtzeeDisplay associated with this game.
	 */
	private void initDisplay() {
		display = new YahtzeeDisplay(getGCanvas(), playerNames);
	}

	/**
	 * Actually plays a game of Yahtzee.  This is where you should begin writing your
	 * implementation.
	 */
	private void playGame() {
		/* You fill this in! */
		int[] total=new int[nPlayers];
		int[][] arr = new int[N_CATEGORIES][nPlayers];
		int upper_score=0,upper_bonus=0,lower_score=0;
		for(int k=0;k<3;k++)
		{
	
			for(int player=0;player<nPlayers;player++)
			 {
				int[] dice = new int[N_DICE];
				display.waitForPlayerToClickRoll(player); 
				for(int i=0;i<N_DICE;i++)
					dice[i]=rgen.nextInt(1,6);
				display.displayDice(dice);
				display.waitForPlayerToSelectDice();
				for(int i=0;i<N_DICE;i++)
					{
					 if(display.isDieSelected(i))
						 dice[i]=rgen.nextInt(1,6);
					}
				display.displayDice(dice);
				display.waitForPlayerToSelectDice();
				for(int i=0;i<N_DICE;i++)
				{
				 if(display.isDieSelected(i))
					 dice[i]=rgen.nextInt(1,6);
				}
			    display.displayDice(dice);
			int category = display.waitForPlayerToSelectCategory();
			int score=0;
	
			if(category==0||category==1||category==2||category==3||category==4||category==5||category==14)
			{
				for(int i=0;i<N_DICE;i++)
				{
					if(category==14)
						score=score+dice[i];
					else if(dice[i]==category+1)
						score=score+dice[i];
				}
			}
			else if((category==8 && YahtzeeMagicStub.checkCategory(dice, category))||(category==9 && YahtzeeMagicStub.checkCategory(dice, category))||(category==13) && YahtzeeMagicStub.checkCategory(dice, category))
			{
				if(category==13)
					score=50;
				else
				{
					for(int i=0;i<N_DICE;i++)
							score=score+dice[i];
				}		
			}
			else if(category==10 && YahtzeeMagicStub.checkCategory(dice, FULL_HOUSE))
				score=25;
			else if(category==11 && YahtzeeMagicStub.checkCategory(dice, category))
				score=30;
			else if(category==12 && YahtzeeMagicStub.checkCategory(dice, category))
				score=40;
			total[player]=total[player]+score;
			arr[category][player]=score;
			display.updateScorecard(category,player,score);
			display.updateScorecard(16,player,total[player]);
			 }
	   }
		for(int j=0;j<nPlayers;j++)
		{
			for(int i=0;i<6;i++)
				upper_score+=arr[i][j];
			for(int i=8;i<15;i++)
				lower_score+=arr[i][j];
			if(upper_score>=63)
				upper_bonus=35;
			display.updateScorecard(6,j,upper_score);
			display.updateScorecard(7,j,upper_bonus);
			display.updateScorecard(15,j,lower_score);
			upper_score=upper_bonus=lower_score=0;
		}
	}
		
	/* Private instance variables */
	private int nPlayers;
	private String[] playerNames;
	private YahtzeeDisplay display;
	private RandomGenerator rgen = RandomGenerator.getInstance();
}
