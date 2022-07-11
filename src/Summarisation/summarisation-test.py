import unittest
import summarisation
from datetime import datetime

class SummarisationTest(unittest.TestCase):

	# Test that the date displays in the right format.
	def test_get_datetime(self):
		self.assertEqual(summarisation.get_datetime(), datetime.today().strftime('%d %b %Y'))
		self.assertNotEqual(summarisation.get_datetime(), datetime.today().strftime('%d %m %Y'))

	# Test that different values for sentiment analysis return the correct description.
	def test_sentiment_analysis(self):
		self.assertEqual(summarisation.sentiment_analysis('1.6'), "Neutral")
		self.assertEqual(summarisation.sentiment_analysis('2.9'), "Positive")
		self.assertEqual(summarisation.sentiment_analysis('1.26'), "Negative")
		self.assertEqual(summarisation.sentiment_analysis('3.776'), "Very Positive")
		self.assertNotEqual(summarisation.sentiment_analysis('1.3'), "Neutral")
		self.assertNotEqual(summarisation.sentiment_analysis('1.9'), "Positive")
		self.assertNotEqual(summarisation.sentiment_analysis('3.56'), "Negative")
		self.assertNotEqual(summarisation.sentiment_analysis('4'), "Neutral")

	# Test that the timespan() method works as expected.
	def test_timespan(self):
		self.assertEqual(summarisation.timespan('15.40', '19.10'), '15.40 - 19.10')
		self.assertNotEqual(summarisation.timespan('15.40', '19.10'), '15.40-19.10')

	# Test that the get_summary() method returns the summary as a string.
	def test_get_summary(self):
		test_conversation = "Amanda: I baked cookies. Do you want some? Jerry: Sure! Amanda: I'll bring you tomorrow :-)"
		self.assertEqual(type(summarisation.get_summary(test_conversation)), str)
		self.assertNotEqual(type(summarisation.get_summary(test_conversation)), list)

if __name__ == '__main__':
	unittest.main()