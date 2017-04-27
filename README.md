# InformationRetrieval
Authors: Jesse Shellabarger and John Kirschenheiter

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
11. Challenge problem, can you give an indication of how relevant a document is to a given query, expressed in the likelhood or confidence in the relevance? Ensure that among others, you test your system with queries for which the confidence factor should be very low, such as "best front-loading washing machine" or "0-60 times of Tesla S".
