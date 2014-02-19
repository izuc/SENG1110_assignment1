/*	
 * 	Author: Lance Baker
 *	Student No: 3128034
 *	Date: 28-03-2011
 *	Description: 
 * 	The program calculates income tax and investment growth 
 * 	based on the yearly income/salary of an individual.
 *
 */

import java.text.NumberFormat;
import java.text.DecimalFormat;
import java.util.*;

public class Invest {
	
	// The following constants are used for input prompts, and error messages relating to input given.
	private static final String INPUT_NAME_MSG = "Please enter your full name: ";
	private static final String INPUT_NAME_ERR = "Error: Please enter your first and last name (separated by a space)";
	private static final String INPUT_INCOME_MSG = "Please enter your annual income: ";
	private static final String INPUT_RESIDENT_MSG = "Are you currently a resident [Yes or No]: ";
	private static final String INPUT_INVEST_MSG = "Would you like to invest? [Yes or No] ";
	private static final String INPUT_LIVING_EXPENDITURE_MSG = "The amount (per week) used on living expenditures: ";
	private static final String INPUT_LIVING_EXPENDITURE_WARNING = "Warning: The amount given is less than your earnings.\n You will need to enter a new amount, otherwise the program will be terminated.";
	private static final String INPUT_LIVING_EXPENDITURE_REENTER_MSG = "Would you like to enter a new living expenditure amount? [Yes or No] ";
	private static final String INPUT_INVESTMENT_VALUE_MSG = "Please enter the investment amount per week: ";
	private static final String INPUT_INVESTMENT_VALUE_ERR = "Error: The investment amount must not exceed %.2f \nIn being the weekly net salary minus the expenditure.\n";
	private static final String INPUT_INTEREST_RATE_MSG = "Please enter the interest rate percentage (between 1-100): ";
	private static final String INPUT_INTEREST_RATE_ERR = "Error: The interest rate must be between 1 and 100.";
	private static final String INPUT_INVESTMENT_LENGTH_MSG = "Please enter the investment length (# of weeks): ";
	private static final String INPUT_BOOLEAN_YES = "Yes";
	private static final String INPUT_BOOLEAN_NO = "No";
	private static final String INPUT_BOOLEAN_ERR = "Error: Must input either Yes or No";
	private static final String ERROR_POSITIVE_VALUE_REQUIRED = "Error: The %s must be a positive number (and greater than zero).\n";
	
	// The following constants are used for displaying the program output.
	private static final NumberFormat currency = NumberFormat.getCurrencyInstance();
	private static final DecimalFormat decimalFormat = new DecimalFormat("#0.00");
	private static final String MSG_NAME = "Name: ";
	private static final String MSG_NET_SALARY = "Net Salary";
	private static final String MSG_PER_WEEK = "Per Week: ";
	private static final String MSG_PER_YEAR = "Per Year: ";
	private static final String MSG_TAX_PAID = "Tax Paid";
	private static final String MSG_MEDICARE_LEVY = "Medicare Levy";
	private static final String MSG_INTEREST = "Interest";
	private static final String MSG_INVESTMENT = "Investment";
	private static final String MSG_INVESTMENT_LENGTH = "Investment Length";
	private static final String MSG_INVESTMENT_WEEKS = "Weeks";
	private static final String MSG_INVESTMENT_BALANCE = "Balance";
	private static final String MSG_AMOUNT = "amount";
	private static final String MSG_SEPARATOR = "--------------------";
	private static final String INTEREST_OUTPUT_TITLE_FORMAT = "%s %12s \n";
	private static final String INTEREST_OUTPUT_BODY_FORMAT = "%02d %15s \n";
	
	// The following constants are used for the resident tax calculations.
	private static final int RESIDENT_TAXABLE_INCOME1 = 6000;
	private static final int RESIDENT_TAXABLE_INCOME2 = 37000;
	private static final double RESIDENT_TAXABLE_INCOME2_RATE = 0.15;
	private static final int RESIDENT_TAXABLE_INCOME3 = 80000;
	private static final int RESIDENT_TAXABLE_INCOME3_TAX = 4650;
	private static final double RESIDENT_TAXABLE_INCOME3_RATE = 0.30;
	private static final int RESIDENT_TAXABLE_INCOME4 = 180000;
	private static final int RESIDENT_TAXABLE_INCOME4_TAX = 17550;
	private static final double RESIDENT_TAXABLE_INCOME4_RATE = 0.37;
	private static final int RESIDENT_TAXABLE_INCOME5_TAX = 54550;
	private static final double RESIDENT_TAXABLE_INCOME5_RATE = 0.45;
	private static final int MEDICARE_LEVY = 20000;
	private static final double MEDICARE_LEVY_RATE = 0.015;
	
	// The following constants are used for the nonresident tax calculations.
	private static final int NONRESIDENT_TAXABLE_INCOME1 = 37000;
	private static final double NONRESIDENT_TAXABLE_INCOME1_RATE = 0.29;
	private static final int NONRESIDENT_TAXABLE_INCOME2 = 80000;
	private static final int NONRESIDENT_TAXABLE_INCOME2_TAX = 10730;
	private static final double NONRESIDENT_TAXABLE_INCOME2_RATE = 0.30;
	private static final int NONRESIDENT_TAXABLE_INCOME3 = 180000;
	private static final int NONRESIDENT_TAXABLE_INCOME3_TAX = 23630;
	private static final double NONRESIDENT_TAXABLE_INCOME3_RATE = 0.37;
	private static final int NONRESIDENT_TAXABLE_INCOME4_TAX = 60630;
	private static final double NONRESIDENT_TAXABLE_INCOME4_RATE = 0.45;
	
	// The following constants are miscellaneous.
	private static final int WEEKS_PER_YEAR = 52;
	private static final String EMPTY_STRING = "";
	private static final String SPACE = " ";	
	
	// The main method used in the program.
	public static void main(String[] args) {
		try {
			Scanner console = new Scanner(System.in);
			
			// Declares variables that will be used throughout the program.
			String name = EMPTY_STRING;
			String input = EMPTY_STRING;
			double income = 0.0;
			double tax = 0.0;
			double medicare = 0.0;
			double netSalary = 0.0;
			double netSalaryPerWeek = 0.0;
			double livingExpenditure = 0.0;
			boolean resident = false;
			
			System.out.println(SPACE);
			
			do {
				System.out.println(SPACE);
				// Prompts input for the full name. 
				System.out.print(INPUT_NAME_MSG);
				// Grabs the entire contents of the inputted line. Placing it in the name variable.
				name = console.nextLine(); 
				// If the name doesn't contain a separated space (meaning it is just one name) then
				// it will display an error message stating to give more than one name.
				if (!(name.indexOf(SPACE) > 0)) {
					System.out.println(INPUT_NAME_ERR);
				}
				// It will iterate through the same process until there is more than just one name given.
			} while(!(name.indexOf(SPACE) > 0));
			
			do {
				System.out.println(SPACE);
				// Prompts input for the user's income.
				System.out.print(INPUT_INCOME_MSG);
				// Fetches a double from the input, and places it in the income variable.
				// If anything else is given it will through an exception, thus terminating the program.
				income = console.nextDouble();
				// If the income is either zero, or a negative number, then it will display an error message
				// stating to input only a positive figure.
				if (income <= 0) {
					System.out.printf(ERROR_POSITIVE_VALUE_REQUIRED, MSG_AMOUNT);
				}
				// It will iterate until a income greater than zero is given.
			} while(income <= 0);	
			
			do {
				System.out.println(SPACE);
				// Prompts to input either Yes or No to determine whether the user is a resident.
				System.out.print(INPUT_RESIDENT_MSG);
				input = console.next(); // Fetches the String inputted, and places it in the input variable.
				// If the input is equal to either Yes or No, then it is accepted as valid data.
				// Otherwise it will display an error message.
				if (input.equalsIgnoreCase(INPUT_BOOLEAN_YES) || input.equalsIgnoreCase(INPUT_BOOLEAN_NO)) {
					// The method equalsIgnoreCase returns a boolean response, in which will be stored in the 
					// resident variable for further use.
					resident = input.equalsIgnoreCase(INPUT_BOOLEAN_YES);
				} else {
					// Displays error message stating to only input Yes or No.
					System.out.println(INPUT_BOOLEAN_ERR);
				}
			// It will iterate until a valid input is given.
			} while (!(input.equalsIgnoreCase(INPUT_BOOLEAN_YES) || input.equalsIgnoreCase(INPUT_BOOLEAN_NO)));
			
			// If the user is a resident, then it will complete the income calculations based on the tax rates for residents.
			if (resident) {
				// If the income is less than or equal to $6,000
				if (income <= RESIDENT_TAXABLE_INCOME1) {
					tax = 0; // Then the tax would be nil.
				// Otherwise, if the income is less than or equal to $37,000
				} else if (income <= RESIDENT_TAXABLE_INCOME2) {
					// Then the tax would be 15c for each $1 over $6,000.
					tax = ((income - RESIDENT_TAXABLE_INCOME1) * RESIDENT_TAXABLE_INCOME2_RATE);
				// Otherwise, if the income is less than or equal to $80,000
				} else if (income <= RESIDENT_TAXABLE_INCOME3) {
					// Then the tax would be $4,650 plus 30c for each $1 over $37,000.
					tax = (RESIDENT_TAXABLE_INCOME3_TAX + 
							((income - RESIDENT_TAXABLE_INCOME2) * RESIDENT_TAXABLE_INCOME3_RATE));
				// Otherwise, if the income is less than or equal to $180,000
				} else if (income <= RESIDENT_TAXABLE_INCOME4) {
					// Then the tax would be $17,550 plus 37c for each $1 over $80,000
					tax = (RESIDENT_TAXABLE_INCOME4_TAX + 
							((income - RESIDENT_TAXABLE_INCOME3) * RESIDENT_TAXABLE_INCOME4_RATE));
							
				// Otherwise, the income would be over $180,000
				} else {
					// Therefore, the tax would be $54,550 plus 45c for each $1 over $180,000
					tax = (RESIDENT_TAXABLE_INCOME5_TAX + 
							((income - RESIDENT_TAXABLE_INCOME4) * RESIDENT_TAXABLE_INCOME5_RATE));
				}
				// If the income is greater than or equal to the medicare levy amount (being $20,000) then 
				// the additional tax is calculated at 1.5% of the taxable income.
				medicare = ((income >= MEDICARE_LEVY) ? (income * MEDICARE_LEVY_RATE) : 0);
				
			// Otherwise it will do the income calculations based on the tax rates for nonresidents.
			} else {
				// If the income is less than or equal to $37,000
				if (income <= NONRESIDENT_TAXABLE_INCOME1) {
					// Then the tax would be 29c for each $1.
					tax = (income * NONRESIDENT_TAXABLE_INCOME1_RATE);
				// Otherwise, if the income is less than or equal to $80,000
				} else if (income <= NONRESIDENT_TAXABLE_INCOME2) {
					// Then the tax would be $10,730 plus 30c for each $1 over $37,000.
					tax = (NONRESIDENT_TAXABLE_INCOME2_TAX + 
							((income - NONRESIDENT_TAXABLE_INCOME1) * NONRESIDENT_TAXABLE_INCOME2_RATE));
				// Otherwise, if the income is less than or equal to $180,000
				} else if (income <= NONRESIDENT_TAXABLE_INCOME3) {
					// Then the tax would be $23,630 plus 37c for each $1 over $80,000
					tax = (NONRESIDENT_TAXABLE_INCOME3_TAX + 
							((income - NONRESIDENT_TAXABLE_INCOME2) * NONRESIDENT_TAXABLE_INCOME3_RATE));
				// Otherwise, the income would be over $180,000
				} else {
					// Therefore, the tax would be $60,630 plus 45c for each $1 over $180,000
					tax = (NONRESIDENT_TAXABLE_INCOME4_TAX + 
							((income - NONRESIDENT_TAXABLE_INCOME3) * NONRESIDENT_TAXABLE_INCOME4_RATE));
				}
			}
			// The net salary is calculated by subtracting the tax 
			// (and medicare levy if applicable) from the taxable income.
			netSalary = ((income - tax) - medicare);
			// The net salary devided by the number of weeks in a year.
			// It will need to be rounded to two decimal places in order to do comparisons accurately.
			netSalaryPerWeek = Double.parseDouble(decimalFormat.format((netSalary /WEEKS_PER_YEAR))); 
			
			// Outputs the results from the tax calculations.
			System.out.println(SPACE);
			System.out.println(MSG_NAME + name);
			System.out.println(SPACE);
			System.out.println(MSG_NET_SALARY);
			// Displays the weekly and yearly net salary formatted to two decimal places.
			System.out.println(MSG_PER_WEEK + currency.format(netSalaryPerWeek));
			System.out.println(MSG_PER_YEAR + currency.format(netSalary));
			System.out.println(SPACE);
			System.out.println(MSG_TAX_PAID);
			// Displays the weekly and yearly tax formatted to two decimal places.
			System.out.println(MSG_PER_WEEK + currency.format((tax /WEEKS_PER_YEAR)));
			System.out.println(MSG_PER_YEAR + currency.format(tax));
			System.out.println(SPACE);
			System.out.println(MSG_MEDICARE_LEVY + SPACE + MSG_PER_YEAR + currency.format(medicare));
			
			do {
				System.out.println(SPACE);
				// Prompts input for the weekly living expenditure amount.
				System.out.print(INPUT_LIVING_EXPENDITURE_MSG);
				// Fetches the double value from the input, placing it in the livingExpenditure variable.
				livingExpenditure = console.nextDouble();
				// If the given value is less than or equal to zero
				// It will display an error message.
				if (livingExpenditure <= 0) {
					System.out.printf(ERROR_POSITIVE_VALUE_REQUIRED, MSG_AMOUNT);
				// Otherwise, if the given value is greater than the net salary per week
				} else if (livingExpenditure > netSalaryPerWeek) {
					// It will display a warning stating that the living expenditure has exceeded the income.
					System.out.println(INPUT_LIVING_EXPENDITURE_WARNING);				
					do {
						System.out.println(SPACE);
						// It will then prompt asking whether the user desires to reenter the living expenditure amount.
						System.out.print(INPUT_LIVING_EXPENDITURE_REENTER_MSG);
						input = console.next(); // Grabs the (Yes or No) response from the user, placing it in the temporary input variable.
						// If the input value is equal to Yes or No, then it is considered as valid.
						if (input.equalsIgnoreCase(INPUT_BOOLEAN_YES) || input.equalsIgnoreCase(INPUT_BOOLEAN_NO)) {
							// If the user entered No
							if (input.equalsIgnoreCase(INPUT_BOOLEAN_NO)) {
								// Then it will throw a exception which will be caught in the try catch surrounding the main method.
								// It will then end the program.
								throw new Exception();
							}
						} else {
							// Otherwise it will display a error message stating it was invalid input
							System.out.println(INPUT_BOOLEAN_ERR);
						}
					// It will iterate until valid data (Yes or No) is given.
					} while (!(input.equalsIgnoreCase(INPUT_BOOLEAN_YES) || input.equalsIgnoreCase(INPUT_BOOLEAN_NO)));
				}
			// It will iterate until the living expenditure amount given is between 1 and less or equal to the net salary per week.
			} while((livingExpenditure <= 0) || (livingExpenditure > netSalaryPerWeek));
			
			// If the living expenditure is not equal to the weekly net salary, 
			// then it will ask the user whether they would like to invest.
			// Otherwise it will just end the program.
			if (livingExpenditure != netSalaryPerWeek) {
				do {
					System.out.println(SPACE);
					// Prompts the user asking whether they wish to invest.
					System.out.print(INPUT_INVEST_MSG);
					input = console.next(); // Grabs the reponse, placing it in the temporary input variable.
					// If the input is equal to Yes or No then it is considered as valid input
					if (input.equalsIgnoreCase(INPUT_BOOLEAN_YES) || input.equalsIgnoreCase(INPUT_BOOLEAN_NO)) {
						// If the user entered Yes
						if (input.equalsIgnoreCase(INPUT_BOOLEAN_YES)) {
							// Declares some variables which will be used throughout the following.
							double investment = 0.0;
							double interest = 0.0;
							int investmentLength = 0;
							// The remaining funds will need to be rounded to two decimal places in order to do comparisons accurately.
							double remainingFunds = Double.parseDouble(decimalFormat.format((netSalaryPerWeek - livingExpenditure)));
							do {
								System.out.println(SPACE);
								// Prompts the user to enter an investment value.
								System.out.print(INPUT_INVESTMENT_VALUE_MSG);
								// Fetches the double value given, placing it in the investment variable.
								investment = console.nextDouble();
								// If the value given is less than or equal to zero then it will display an error message.
								if (investment <= 0) {
									System.out.printf(ERROR_POSITIVE_VALUE_REQUIRED, MSG_INVESTMENT);
								// Otherwise if the investment is greater than the weekly net salary minus the living expenditure,
								// then it will display an error message stating that it has been exceeded.
								} else if (investment > remainingFunds) {
									System.out.printf(INPUT_INVESTMENT_VALUE_ERR, (netSalaryPerWeek - livingExpenditure));
								}
							// Iterates until the user enters a figure greater than 1, and less than or equal to 
							// the net salary per week minus the living expenditure.
							} while ((investment <= 0) || (investment > remainingFunds));
							
							do {
								System.out.println(SPACE);
								// Prompts the user to enter a interest rate.
								System.out.print(INPUT_INTEREST_RATE_MSG);
								// Fetches the value entered, placing it in the interest variable.
								interest = console.nextDouble(); 
								// If the value given is outside of the range 1 to 100, then it will display an error message
								// stating to only input a value within the said range.
								if (!(interest >= 1 && interest <= 100)) {
									System.out.println(INPUT_INTEREST_RATE_ERR);
								}
							// Iterates until the value given is within the correct range.
							} while(!(interest >= 1 && interest <= 100));
							
							do {
								System.out.println(SPACE);
								// Prompts the user to enter the investment length.
								System.out.print(INPUT_INVESTMENT_LENGTH_MSG);
								 // Fetches the value entered, placing it in the investmentLength variable.
								investmentLength = console.nextInt();
								// If the value is less than or equal to zero, it will display an error message
								// stating that a positive number is required.
								if (investmentLength <= 0) {
									System.out.printf(ERROR_POSITIVE_VALUE_REQUIRED, MSG_INVESTMENT_LENGTH);
								}
							// Iterates until a positive value is given.
							} while (investmentLength <= 0);
							
							double total = 0.0; // Declares the total variable which will be used in the following calculations.
							System.out.println(SPACE);
							// Displays the formatted output headings.
							System.out.println(MSG_INVESTMENT);
							System.out.printf(INTEREST_OUTPUT_TITLE_FORMAT, MSG_INVESTMENT_WEEKS, MSG_INVESTMENT_BALANCE);
							System.out.println(MSG_SEPARATOR);
							// The following will iterate for each week for the investmentLength.
							for (int week = 1; week <= investmentLength; week++) {
								// If the week modula by four is equal to zero (meaning that it has equaled to 4, 8, 12, 16 ...etc)
								// then it will display total output for that monthly group of weeks. 
								
								// In the event that the investment length is not evenly divisible by four, then it will still perform 
								// the following once the weeks variable is equal to the investment length and the division by four has a remainder.
								if (((week % 4) == 0) || ((week == investmentLength) && ((week % 4) != 0))) {
									// The following weeks variable will be for majority equal to four, but in the circumstance
									// that an unevenly divisable investment length was given, then on the last iteration it will be the remainder.
									int weeks = (((week % 4) == 0) ? 4 : (week % 4));
									// The total is calculated by adding the previous running total with the investment and multiplying it by the weeks variable.
									// This is then additionally multiplied by the interest rate (which is converted to a monthly rate) and divided by 100, 
									// with 1 added to get it in the correct format. In the circumstance that the weeks do not equal to four, meaning it is the last
									// week on the uneven investment length, then it won't have the interest rate applied and instead be set to 1 (being the amount the 
									// total is multiplied, not the interest percentage).
									total = (((total + investment * weeks)) * ((weeks == 4) ? (((interest / 13)/ 100) + 1) : 1));
									// Displays the weeks and the total in the columnar format.
									System.out.printf(INTEREST_OUTPUT_BODY_FORMAT, week, currency.format(total));
								}
							}
						}
					// Otherwise invalid input was given.
					} else {
						// It will then display an error message.
						System.out.println(INPUT_BOOLEAN_ERR);
					}
				// Iterates until the user enters valid data (either Yes or No).
				} while (!(input.equalsIgnoreCase(INPUT_BOOLEAN_YES) || input.equalsIgnoreCase(INPUT_BOOLEAN_NO)));
			}
		} catch (Exception ex) {}
	}
}