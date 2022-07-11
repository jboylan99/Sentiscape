# Blog

We decided to create a blog to write in addition to working on our project. The aim is to provide weekly updates where we can review what we achieved, what we need to focus on, and the discussions we've had with our supervisor.

**08/12/2021**

<p><b>Jason: </b>I began working on the account system that we'd be using for our application's users. I needed to have an Account class for creating accounts, a Login class for logging in, and a SQL database to store usernames and passwords.</p>

<p><b>Kelan: </b>In parallel to Jason's log in system I also worked on a seperate method for users to access the application using a one time password 
that would be send via sms to the user. I also began work designing the splash screen and main menu design.</p>

<p>The purpose of simultaniously working on two log in systems was to determine which method would best suit our application.</p>


**19/01/22**

<p>We began meeting weekly with our supervisor where we discussed what we'd accomplished so far and what we aimed to have achieved by the next meeting. We also created slides to help our supervisor visualise our aims and objectives for the app. We took minutes of the meeting as well.</p>

<p><b>Jason: </b>At this point I had the login system fully operational, and my plan for next week was to have created tests for it and add some additional functionality (such as a "Remember Me" option). I also plan on integrating the login system into Kelan's splash screen and main screen.</p>

<p><b>Kelan: </b>The OTP (One Time Password) log in system was completed by know and I had integrated it with the splash screen and main menu I had 
designed. My next taask was to begin work on the networking components of the application to allow users to send messages.</p>

<p>After some consideration and comparison of the two log in systems we decided to go with Jason's password system so integrating it into the existing 
splash screen and main menu would be an additional task.</p>


**26/01/22**

<p><b>Jason: </b>I met with our supervisor again this week and discussed our progress. I discussed the sketches we'd created to help envision what our app will look like. I also confirmed that we'd successfully integrated my login system with Kelan's main screen. I also created a "Remember Me" option for the login sreen and the password is now hashed before being stored on the MySQLite database.</p>

<p>My plan for the next week is to develop a network for the system. Myself and Kelan have both developed a simple network GUI for a project in a past module so we will compare both and adapt and implement the most appropriate one.</p>

<p><b>Kelan: </b>I was unable to attend this weeks meeting with our supervisor due to a bereavement, I emailed Hyowon ahead of time to let him know. 
We integrated the password log in system into the existing application and I was making good progress on the network.</p>

<p>I also drew up some sketches of the messaging screen UI to present to our supervisor and discuss what the best design for the screen should be. 
The network systems will be ready next week to compare and we will integrate the system that we deem to be most appropriate</p>


**02/02/22**

<p><b>Jason: </b>This week, Kelan and I compared our network systems and decided his was more appropriate to use as it was more fleshed out to work with groups, whereas mine only functioned in a one-on-one environment. While Kelan worked on the network, I focused on other elements of the app.</p>

<p>First of all, I updated the logo and recreated it as an SVG. This is now a part of the splash screen when the user first opens the app. Next, I implemented a light / dark mode functionality into the app. This features on the Settings fragment. Also featured now on the Settings fragment is a logout button that takes the user back to the login screen.</p>

<p>I discussed this with my supervisor at our weekly meeting. I suggested that my plan for next week is to create a search system that the user can use to search for other users to chat to and to focus on creating an original layout for the app.</p>


**09/02/2022**

<p>(There was no meeting this week)</p>

<p><b>Jason: </b>This week, we ran into issues with our network and had to spend time working on fixing it. During this process, we moved to using Firebase instead of SQLite. This took some time for both of us to figure out.</p>


**16/02/2022**

<p><b>Jason: </b>Now that the network is up and running, we began to divide the work a little more. I started working on the sentiment analysis and natural language processing aspect of the application. I would be using Python for this. First of all, I followed a basic [tutorial](https://www.datacamp.com/community/tutorials/simplifying-sentiment-analysis-python) that used a dataset of movie reviews to help me gain a basic understanding of the process.</p>

<p>After gaining some knowledge of this, I had to choose a dataset of my own to start working on. I was focusing on the conversation summarisation aspect of the app at this stage, so I was looking for an appropriate dataset. It came down to two datasets: the [first one](http://faculty.nps.edu/cmartell/NPSChat.htm) was the NPSChat Corpus, which contained over 10,000 posts from message boards in 2006. The [second one](https://paperswithcode.com/dataset/samsum-corpus) was the SAMSum Corpus, a 2019 dataset with over 16,000 conversations between two people. We chose the second one because it was more recent, contained more data and because the conversations were between only two people, similar to our application.</p>

<p>I began using different libraries to start understanding the data, such as Pandas, PyTorch, NLTK and Transformers.</p>


**23/02/2022**

<p><b>Jason: </b>I spent the week continuing on work for the natural language processing element, trying to integrate it into the app. Next week, I plan on adding UI sketches for the Conversation Summary screen and further develop the machine learning aspect.</p>


**02/03/2022**

<p><b>Jason: </b>This week I worked on completing the ethics approval form. After this, I focused on preparing the app for the expo demo, as well as write a summary for the project and a voiceover to narrate over the demo.</p>


**10/03/2022**
<p><b>Jason: </b>This week I began working on the Summary screens. I created the SummaryActivity and SummaryListActivity and started implementing them. We also met with our supervisor this week and he gave us some suggestions for help with our presentation.</p>


**24/03/2022**
<p><b>Jason: </b>This week I continued my work on the summary screen. Also making use of JSON file for conversations and using it for summarisation. We met with our supervisor and discussed keeping references for decisions we've made in regards to the project.</p>


**30/03/2022**
<p><b>Jason: </b>This week I continued my work on the summarisation feature. I'm using Chaquopy to try and integrate it directly into AndroidStudio. Also focused on repo readmes. Work has begun on user manual and technical guide in shared Google Docs.</p>


**06/04/2022**
<p><b>Jason: </b>This week I worked on creating a server to send POST and GET requests to. I wanted to send POST requests containing conversations to the server. The Python script then sends a GET request and gets the conversation, summarises it and sends a POST request containing the summary. The application then sends a GET request to get the summarised conversation.</p>


**06/04/2022**
<p><b>Jason: </b>This week I worked on creating a server to send POST and GET requests to. I wanted to send POST requests containing conversations to the server. The Python script then sends a GET request and gets the conversation, summarises it and sends a POST request containing the summary. The application then sends a GET request to get the summarised conversation.</p>


**13/04/2022**
<p><b>Jason: </b>This week I updated colours for message bubbles, made significant display changes to summary activity and integrated Summarisation and Firebase.</p>


**20/04/2022**
<p><b>Jason: </b>This week, finishing touches were added to the project, testing completed and documentation being finalised.</p>