# InformationRetrieval
Authors: Jesse Shellabarger and John Kirschenheiter

## How to use
Run the code as a command-line application. The application is hard-coded to use its internal document directory, so you don't have to worry about that. The user will be repeatedly prompted for search terms in the terminal. Any number of terms can be used to search. Press enter to execute the search. Enter `exit` to close the application.

## Overview

The purpose of this assignment is to explore basic information retrieval techniques. It is the first of a two part assignment. The second part will focus on NLP techniques.

## Requirements

For this, the information retrieval portion of the assignment, implement the following algorithms and techniques:
1. Curation of documents. Strip off any necessary information. Be careful as anchor tags have useful information. Perhaps, you wish to store anchor tag information in a separate file or data structure. Please notice that information in the info-boxes has some very precise information. You only have to curate documents once. You also want to look at the document sources, to appreciate some of the meta-information that you may wish to harvest. The IR software will work with the curated documents, as well as any other documents you produced as part of the curation process.
2. Since the documents are well edited, you do not need to worry about spell checking documents.
3. We won't worry about stemming for this assignment.
4. Notice that in Wikipedia, you can have a look at which other Wikipedia pages point to a give page. (On the left side-bar, under Tools, you can ask "What links here.") This will be useful information.
5. Use information from the title tags.
6. Use information from anchor tags.
7. Implement the BM25 scoring function
8. Implement skip-bigrams on words.
9. Implement the ability to enter a simple query. There is no need to develop a GUI.
10. As a response to a query, return relevant document names.
11. Challenge problem, can you give an indication of how relevant a document is to a given query, expressed in the likelihood or confidence in the relevance? Ensure that among others, you test your system with queries for which the confidence factor should be very low, such as "best front-loading washing machine" or "0-60 times of Tesla S".

## Curation
To aid in our ability to search through the documents, we did a little bit of curation. First, we used an online tool (https://www.phpjunkyard.com/tools/html-to-text.php) to convert the html from the wikipedia page into plain text. We then removed some of the text pertaining only to Wikipedia and not to the article. We stripped the title of the page of any text relating to wikipedia so that our header search would be more accurate. Additionally, we converted the entire article to lowercase to make the search case insensitive. We also removed any of the lines that seemed to have excessive white space in there start. These lines were a result of the online html converter we used, and mostly referenced unuseful data. Finally, we did our best to remove the references section of the article. This proved difficult, as the syntax wasn’t standardized. In the end, we simply removed the last section of the page in an attempt solve the problem. While this solution isn’t perfect, it worked well enough to give use good results. 

##Retrieval Method
We started off by implementing the BM25 indexing algorithm. We started here because we believed it would end up being the most relevant part of our information retrieval system. After completing BM25 we found that it did not perform as well as we had hoped. Document length had a larger effect on the results than we would have liked. It was so extreme that when searching for “Washington” the algorithm would suggest the article on Fillmore because it was much shorter and also mentioned Washington frequently.

To fix this we added a component to search the titles of the articles for terms in the search query. We made this component contribute much more to the final score of the document than the BM25 search, so that a search for “Washington” would be sure to return the page on Washington.

At this stage we were unhappy with our inability to search for phrases effectively. Searching “Civil War President” would look at only at the frequencies of the individual words and not consider the phrase as a whole. To remedy this, we implemented another search methods using Skip Bigrams. This search looks for phrases in the search query within the documents. To aid in successfully finding these phrases in the documents, a single word is allowed to be inserted into the query phrase. We weighted this search more than the BM25 index, but less than our header search.

## Results
The final results were satisfactory. The search operates quite quickly, and the results are all relevant to the searches given (with the exception of nonsense searches). This is with the exception of searches where five relevant documents do not exist. Because we always display five results, the search of “Civil War President” will return more than just the article on Lincoln.

We decided to output the top five search results since, for many searches, more than one president fulfills the search terms. At the same time, we didn’t want to overwhelm the reader with the scores of every article. Our results were so reliable that we got the exact same matches for many of the test searches given to us in the assignment. That being said, our program did somewhat diverge when very common words were searched such as “I”, “the”, and “an”. This is probably a result of the BM25 being weighted differently from the test program. 
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                       We currently r