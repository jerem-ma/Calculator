package fr.alpha.calculator;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import fr.alpha.calculator.OperationManager;

	public class TestOperationManager{

		/* Tests for addCharacter method */
		@Test
		public void testAddCharacterEmptyOperation(){

			final OperationManager testOperationManager = new OperationManager();
			
			assertTrue(testOperationManager.addCharacter('0'), 
					"addCharacter must return true !");

			final OperationManager expectedOperationManager = new OperationManager();
			expectedOperationManager.setOperation("0");

			assertEquals(expectedOperationManager, testOperationManager, 
					"operation must be equal to \"0\" !");

		}

		@Test
		public void testAddCharacterFilledOperation(){
			
			final OperationManager testOperationManager = new OperationManager();
			testOperationManager.setOperation("3+73-5");

			assertTrue(testOperationManager.addCharacter('+'),
					"addCharacter must return true !");

			final OperationManager expectedOperationManager = new OperationManager();
			testOperationManager.setOperation("3+73-5+");

			assertEquals(expectedOperationManager, testOperationManager,
					"operaton must be equal to \"3+73-5+\" !");

		}

		@Test
		public void testAddInvalidCharacter(){
			final OperationManager testOperationManager = new OperationManager();
			testOperationManager.setOperation("3+73-");
			
			assertFalse(testOperationManager.addCharacter('+'),
					"addcharacter must return false !");

			final OperationManager expectedOperationManager = new OperationManager();
			expectedOperationManager.setOperation("3+73-");

			assertEquals(expectedOperationManager, testOperationManager,
					"operation must be equal to \"3.73-\" !");
		}

		@Test
		public void testClearEmptyOperation(){
			final OperationManager testOperationManager = new OperationManager();
			testOperationManager.clearOperation();

			final OperationManager expectedOperationManager = new OperationManager();
			expectedOperationManager.setOperation("");

			assertEquals(expectedOperationManager, testOperationManager,
					"operation must be equal to an empty string !");
		}

		@Test
		public void testClearFilledOperation(){
			final OpearationManager testOperationManager = new OperationManager();
			testOperationManager.setOperation("5-3");
			testOperationManager.clearOperation();

			final OperationManager expectedOperationManager = new OperationManager();
			expectedOperationManager.setOperation("");

			assertEquals(expectedOperationManager, testOperationManager,
					"operation must be equal to an empty string !");
		}
	}
