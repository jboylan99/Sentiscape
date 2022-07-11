# School of Computing &mdash; Year 4 Project Proposal Form

## SECTION A

|                     |                   |
|---------------------|-------------------|
|Project Title:       | Sentiscape        |
|Student 1 Name:      | Jason Boylan      |
|Student 1 ID:        | 18342986          |
|Student 2 Name:      | Kelan Smyth       |
|Student 2 ID:        | 18342973          |
|Project Supervisor:  | Hyowon Lee        |

## SECTION B

### Introduction

Sentiscape is an innovative messaging application that will offer a different approach than other messaging apps currently available. Current messaging apps such as Facebook Messenger, WhatsApp and iMessage all use the basically same user interaction strategy that have been around since multi-touch smartphones started featuring the text messaging function, although there have been minor incremental improvements over the years. With Sentiscape, we want to re-think how text-messaging interaction could/should be done and subsequently design novel user-interfaces by bringing in up-to-date computational technologies at the back-end of the app.

The main issues we considered innovating on from current apps are the difficulty in accessing old conversations, the difficulty in conveying the user's affective side (as opposed to the informational side) of the conversation in texting, and the difficulty in providing more accurate predictions/suggestions of
terms taking into account the context the user is in. 

Our aim is to to resolve these issues by exploring and researching what readily-available or experimental computational technologies can do and demonstrating
this by developing a fully-functioning prototype text messaging app.


### Outline

Sentiscape will be an Android app that will function as a next-generation messaging application. The primary function of a messaging app is to allow users to send messages and other media to friends, family, colleagues. This app will be able to accomplish the basic needs of any messaging platform and it will introduce new features not seen in other apps currently available.

It will allow users to categorise their conversations with friends which will make it easier to look for past conversations. They will be able to sort the conversations by day and view the conversation on a sidebar where different shapes will distiguish emoticons, single line replies and multi-line replies. Natural language processing will be used to generate a summary of the conversation which will be displayed alongside the day-by-day breakdown.

It will also track the mood of the conversation which will allow for different predictive text, as well as softer and brighter backgrounds, depending on the mood. This will be done using sentiment analysis to analyse the mood of the conversation. If the most prominent emotion being displayed is sad, there will be more muted colours and suggested text will lean towards that sort of vocabulary.

And additional function of sentiment analysis is that we can use it for user profiles. Our idea is to have an emoticon represent a user's profile in the chat, and if they send a message using 'happy' vocabulary, their user emoticon will have a happy face beside the message they've sent.


### Background

The idea behind Sentiscape derived from our experience using messaging applications over the years. Many of the popular messaging platforms haven't seen a lot of innovation in recent years and all follow a similar convention of text bubbles for each message alongside a small profile image of the user that sent it. The likes of Facebook Messenger, Viber, WhatsApp, iMessage and Android Messenger have received many quality of life improvement updates but rarely do we see any major changes to the functionality of the application.

That is where we came up with the idea for Sentiscape, building upon traditional messaging applications to create a more functional and reactive way of communication through text. Sentiscape will use sentiment analysis to analyse users messages to determine the mood of the conversation and responsively change the backgound and predictive text. It will also allow users to categorise their conversations in a visually engaging manner.


### Achievements

This project will target a very large userbase - basically everyone who uses a phone and needs to get in contact with other people. This will make it easy to user test in the future.

Sentiscape will provide a variety of functions to its users. Firstly, it will accomplish all the goals a messaging application should - those being sending messages back and forth, sharing photos, and group chat support. 

Then it will provide additional functionality that will be the key selling point of the application. It will make use of sentiment analysis to understand the primary sentiment in the conversation which will make for more accurate predictive text. It will also use this analysis to provide more muted / amplified colours on-screen.

Another function that the project will provide is the ability to categorise conversations. This will make it easier to access previous conversations from a long time ago without having to endlessly scroll upwards. Users can easily recall what was discussed that day as a summary will also be provided.


### Justification

- Why? Sentiscape will allow users to be more expresive when communicating over text with responsive mood changing and improved prdeictive text.

- When & Where? Sentiscape being an Android app can be accessed by users at any time of day or night anywhere in the world to communicate with other users.

- How? Sentiscape's sentiment analysis will allow the application to react reponsively to users mood and adapt predictive text and application background.


### Programming language(s)

Java (Development of app)  
Python (Sentiment analysis)  
MySQL (Data Storage)


### Programming tools / Tech stack

Android Studio (Integrated Development Enviornment)  
Spring (Backend Development)  
IntelliJ (Integrated Development Enviornment)  
Natural Language Toolkit (Natural Language Processing Plugin)


### Hardware

An Android phone will be required in order to demonstrate the app running.


### Learning Challenges

One of the main challenges of this project will be the implementation of sentiment analysis which will be a completely new endeavour for us as we have never explored it before. Android Studio will also be a new challenge as it will be our first time creating a mobile application for a project.


### Breakdown of work

For the project, we will break the workload into 3 distinct segments. Firstly, we will build the fundamental building blocks of the app, and make sure it works as a basic messaging service. This will be split like so:

The frontend will be designed by one person which will involve creating a clean user interface and a streamlined and intuitive way to categorise conversations.

The backend will involve creating the building blocks for the messaging service. This will involve networking for one-to-one conversations between users as well as group chats.

Once that is done, we will begin working on sentiment analysis which will be broken up into the predictive text section and the mood colour section. We will split the sentiment analysis work between us as it is new technology to both of us and we can break up the workload evenly.


#### Student 1 - Jason Boylan

I will work on building the application using Android Studio. I will focus on the front-end and work on creating a clean user interface. I will also design a method to divide and categorise conversations. This will be done by categorising the conversation by day, where the user can look for a specific date and find the conversation they're looking for.

Furthermore, natural language processing will allow conversations had on these days to feature a summary alongside them. This will be done with Python using the NLTK library.

I will also work on the display of the data so users can visualise the conversations from a sentimental point of view.


#### Student 2 - Kelan Smyth

I will work on the Spring back-end of the application and the networking sections allowing users to connect to each other. I will focus on the functionality and features of the application ensuring the users can send and receive messages using Sentiscape.

I will also be working on the sentiment analysis section of the project to incorporate the dynamic mood changes of the application. This will involve user profiles reacting to the mood detected by sentiment analysis as well as the background of the chat.

This tool will also be useful for group chats were the group admin has the ability to remove a user if they regularly send negative messages (this may indicate trolling).

I will also be working on the database using SQL. This will store information on the users.