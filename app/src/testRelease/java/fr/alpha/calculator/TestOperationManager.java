package fr.alpha.calculator;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import fr.alpha.calculator.OperationManager;

	public class TestOperationManager{

		private final String[] mathSigns = new String[] {"+", "-", "*", "/"};

		/* Tests for addCharacter method */
		@Test
		public void testAddCharacterEmptyOperation(){

			final OperationManager testOperationManager = new OperationManager(mathSigns);
			
			assertTrue(testOperationManager.addCharacter('0'), 
					"addCharacter must return true !");

			final OperationManager expectedOperationManager = new OperationManager(mathSigns);
			expectedOperationManager.setOperation("0");

			assertEquals(expectedOperationManager, testOperationManager, 
					"operation must be equal to \"0\" !");

		}

		@Test
		public void testAddCharacterFilledOperation(){
			
			final OperationManager testOperationManager = new OperationManager(mathSigns);
			testOperationManager.setOperation("3+73-5");

			assertTrue(testOperationManager.addCharacter('+'),
					"addCharacter must return true !");

			final OperationManager expectedOperationManager = new OperationManager(mathSigns);
			testOperationManager.setOperation("3+73-5+");

			assertEquals(expectedOperationManager, testOperationManager,
					"operaton must be equal to \"3+73-5+\" !");

		}

		@Test
		public void testAddInvalidCharacter(){
			final OperationManager testOperationManager = new OperationManager(mathSigns);
			testOperationManager.setOperation("3+73-");
			
			assertFalse(testOperationManager.addCharacter('+'),
					"addcharacter must return false !");

			final OperationManager expectedOperationManager = new OperationManager(mathSigns);
			expectedOperationManager.setOperation("3+73-");

			assertEquals(expectedOperationManager, testOperationManager,
					"operation must be equal to \"3.73-\" !");
		}

		@Test
		public void testClearEmptyOperation(){
			final OperationManager testOperationManager = new OperationManager(mathSigns);
			testOperationManager.clearOperation();

			final OperationManager expectedOperationManager = new OperationManager(mathSigns);
			expectedOperationManager.setOperation("");

			assertEquals(expectedOperationManager, testOperationManager,
					"operation must be equal to an empty string !");
		}

		@Test
		public void testClearFilledOperation(){
			final OperationManager testOperationManager = new OperationManager(mathSigns);
			testOperationManager.setOperation("5-3");
			testOperationManager.clearOperation();

			final OperationManager expectedOperationManager = new OperationManager(mathSigns);
			expectedOperationManager.setOperation("");

			assertEquals(expectedOperationManager, testOperationManager,
					"operation must be equal to an empty string !");
		}

		@Test
		public void testComputes(){
			final OperationManager testOperationManager = new OperationManager(mathSigns);
			testOperationManager.setOperation("5-3");
			
			assertEquals(2.0, testOperationManager.computes(),
					"computes must return 2.0 !");

			testOperationManager.setOperation("4*3");
			assertEquals(12.0, testOperationManager.computes(),
					"computes must return 12.0 !");

			testOperationManager.setOperation("10/3");
			assertEquals(10/3, testOperationManager.computes(),
					"computes must return the result of 10/3");

			testOperationManager.setOperation("5/0");
			assertThrows(ArithmeticException.class, new Executable(){

				@Override
				public void execute(){
					testOperationManager.computes();
				}

			}, "computes must throw an ArithmeticException !");

			testOperationManager.setOperation("0/5");
			assertEquals(0.0, testOperationManager.computes(),
					"computes must return 0.0 !");

			testOperationManager.setOperation("5*0");
			assertEquals(0.0, testOperationManager.computes(),
					"computes must return 0.0 !");
		}

		@Test
		public void testRemoveCharacter(){
			final OperationManager testOperationManager = new OperationManager(mathSigns);
			testOperationManager.setOperation("4*23");

			assertTrue(testOperationManager.removeCharacter(),
					"removeCharacter must return true !");

			final OperationManager expectedOperationManager = new OperationManager(mathSigns);
			expectedOperationManager.setOperation("4*2");

			assertEquals(expectedOperationManager, testOperationManager,
					"operation must be equal to \"4*2\"");


			testOperationManager.setOperation("5");
			assertTrue(testOperationManager.removeCharacter(),
					"removeCharacter must return true !");

			expectedOperationManager.setOperation("0");

			assertEquals(expectedOperationManager, testOperationManager,
					"operation must be equal to 0 !");


			testOperationManager.setOperation("");
			assertFalse(testOperationManager.removeCharacter(),
					"removeCharacter must return false !");

			expectedOperationManager.setOperation("0");

			assertEquals(expectedOperationManager, testOperationManager,
					"operation must be equal to 0 !");
		}
	}
