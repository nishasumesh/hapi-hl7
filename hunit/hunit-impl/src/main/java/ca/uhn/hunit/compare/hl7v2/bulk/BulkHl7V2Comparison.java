package ca.uhn.hunit.compare.hl7v2.bulk;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

import ca.uhn.hl7v2.HL7Exception;
import ca.uhn.hl7v2.model.Message;
import ca.uhn.hl7v2.parser.EncodingNotSupportedException;
import ca.uhn.hl7v2.parser.PipeParser;
import ca.uhn.hunit.compare.hl7v2.Hl7V2MessageCompare;
import ca.uhn.hunit.ex.UnexpectedTestFailureException;
import ca.uhn.hunit.util.Hl7FileUtil;

public class BulkHl7V2Comparison {

	private static final Logger ourLog = Logger.getLogger(BulkHl7V2Comparison.class.getName());
	
	private List<Message> myActualMessages;
	private List<Message> myExpectedMessages;
	private List<Hl7V2MessageCompare> myFailedComparisons = new ArrayList<Hl7V2MessageCompare>();
	private boolean myStopOnFirstFailure;
	private Set<String> myFieldsToIgnore = new HashSet<String>();
	private PipeParser myParser;

	public BulkHl7V2Comparison() {
		myParser = PipeParser.getInstanceWithNoValidation();
	}
	
	
	public void compare() throws UnexpectedTestFailureException {
		
		int actualIndex = 0;
		int expectedIndex = 0;
		
		while (actualIndex < myActualMessages.size() && expectedIndex < myExpectedMessages.size()) {
			
			ourLog.info("Comparing message " + (actualIndex + 1) + " / " + myActualMessages.size());
			
			Message actualMessage = myActualMessages.get(actualIndex);
			Message expectedMessage = myExpectedMessages.get(expectedIndex);
			
			Hl7V2MessageCompare comparison = new Hl7V2MessageCompare(myParser);
			comparison.setFieldsToIgnore(myFieldsToIgnore);
			comparison.compare(expectedMessage, actualMessage);
			
			if (!comparison.isSame()) {
				myFailedComparisons.add(comparison);
				
				if (myStopOnFirstFailure) {
					break;
				}
				
			}
			
			actualIndex++;
			expectedIndex++;
		}
		
	}
	
	
	/**
	 * @return the actualMessages
	 */
	public List<Message> getActualMessages() {
		return myActualMessages;
	}

	/**
	 * @return the expectedMessages
	 */
	public List<Message> getExpectedMessages() {
		return myExpectedMessages;
	}

	/**
	 * @return the failedComparisons
	 */
	public List<Hl7V2MessageCompare> getFailedComparisons() {
		return myFailedComparisons;
	}

	/**
	 * @return the stopOnFirstFailure
	 */
	public boolean isStopOnFirstFailure() {
		return myStopOnFirstFailure;
	}

	/**
	 * @param theActualMessages
	 *            the actualMessages to set
	 */
	public void setActualMessages(List<Message> theActualMessages) {
		myActualMessages = theActualMessages;
	}

	/**
	 * @param theExpectedMessages
	 *            the expectedMessages to set
	 */
	public void setExpectedMessages(List<Message> theExpectedMessages) {
		myExpectedMessages = theExpectedMessages;
	}

	/**
	 * @param theStopOnFirstFailure
	 *            the stopOnFirstFailure to set
	 */
	public void setStopOnFirstFailure(boolean theStopOnFirstFailure) {
		myStopOnFirstFailure = theStopOnFirstFailure;
	}

	/**
	 * @param theFieldToIgnore
	 *            the terserPathsToIgnore to set
	 */
	public void addFieldToIgnore(String theFieldToIgnore) {
		myFieldsToIgnore.add(theFieldToIgnore);
	}


	public String describeDifferences() {
		StringBuilder retVal = new StringBuilder();
		
		for (Hl7V2MessageCompare next : myFailedComparisons) {
			
			retVal.append(next.describeDifference());
			retVal.append("\n\n");
		}
		
		return retVal.toString();
	}
	
	
	public static void main(String[] theArgs) throws EncodingNotSupportedException, IOException, HL7Exception, UnexpectedTestFailureException {
		String expectedFile = "../../prj_Map_ADT_SIMS_EPR_ADT_UHN_RxTFC_Pojo/test/expected.txt";
		String actualFile = "../../prj_Map_ADT_SIMS_EPR_ADT_UHN_RxTFC_Pojo/test/actual.txt";
		
		BulkHl7V2Comparison tester = new BulkHl7V2Comparison();
		tester.setExpectedMessages(Hl7FileUtil.loadFileAndParseIntoMessages(expectedFile));
		tester.setActualMessages(Hl7FileUtil.loadFileAndParseIntoMessages(actualFile));
		tester.setStopOnFirstFailure(true);
		tester.addFieldToIgnore("MSH-2");
		tester.addFieldToIgnore("MSH-10");
		
		tester.compare();
		
		System.out.println();
		System.out.println();
		System.out.println();
		System.out.println(tester.describeDifferences());
		
	}

}