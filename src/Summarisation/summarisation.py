from transformers import pipeline
from datetime import datetime
from threading import Thread
import time, math, json, requests


# Generate a summary of conversation.
def get_summary(conversation):
	# If the length of the conversation is too short, don't bother summarising.
	if len(conversation) < 100:
		return conversation

	# Otherwise summarise the conversation using the Transformers pipeline function and the SAMSUM dataset.
	else:
		summariser = pipeline("summarization", model="linydub/bart-large-samsum")

		# Summary is initially in the form of a list with a nested dictionary.
		summary = summariser(conversation)

		# Get value of key, value pair and return it.
		return summary[0]['summary_text']

# Method that returns current date.
def get_datetime():
	dt = datetime.today().strftime('%d %b %Y')
	return dt

# Return sentiment description using average sentiment value.
def sentiment_analysis(sentiment_value):

	''' 
	If there is no conversation on the day, there won't exist a value.
	In this case, default to 2 (Neutral).
	'''
	if math.isnan(float(sentiment_value)):
		sentiment_value = 2

	# If there is an average value, round it to the nearest whole value.
	else:
		sentiment_value = round(float(sentiment_value))

	# Depending on the value, return the corresponding description.
	if sentiment_value == 1:
		return "Negative"
	elif sentiment_value == 2:
		return "Neutral"
	elif sentiment_value == 3:
		return "Positive"
	elif sentiment_value == 4:
		return "Very Positive"

# Send POST request for specific Chat UID.
def post_request(chat_uid, data):
	requests.post(f'http://ptsv2.com/t/sentiscape_summary{chat_uid}', data = data)
	requests.post(f'http://ptsv2.com/t/sentiscape_summary{chat_uid}/post', data = data)

# Return timespan.
def timespan(start_time, end_time):
	times = start_time + " - " + end_time
	return times

# Get all data from server, summarise data, store in JSON and send to server.
def server():

	date = get_datetime()


	while True:
		# Get the conversation data from the server.
		getter = requests.get('http://ptsv2.com/t/sentiscape_conversation/d/latest/json').text
		# Seperate the JSON data into different strings.
		json_data = json.loads(getter)
		d = json.loads(json_data['Body'])
		conversation = d['conversation']
		sentiment_value = d['sentimentValue']
		chat_uid = d['chatUID']
		start_time = d['startTime']
		end_time = d['endTime']

		# Set display for start time and end time in SummaryActivity.java.
		times = timespan(start_time, end_time)

		# Calculate sentiment analysis description.
		sentiment = sentiment_analysis(sentiment_value)

		# Calculate summary. 
		summary = get_summary(conversation)

		# Store all data in dictionary.
		data = {'date':date, 'summary':summary, 'sentiment':sentiment, 'times':times}

		# Get chat room ID for both users in conversation.
		chat_uid1 = chat_uid[:len(chat_uid)//2] 
		chat_uid2 = chat_uid[len(chat_uid)//2:] 
		chat_uid_alt = chat_uid2 + chat_uid1

		# POST request to server for both users.
		post_request(chat_uid, data)
		post_request(chat_uid_alt, data)

		print("Summary sent to Sentiscape")
		time.sleep(10)

def main():
	server()

if __name__ == '__main__':
	main()