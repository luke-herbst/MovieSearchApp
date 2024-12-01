# Movie Search App

This Android app allows users to search for movies using the OMDb API. It features Firebase authentication, a movie search screen, and functionality to display movie details like title, poster, year of release, genre, rating, runtime, and IMDB page links. The app also saves the user's search history to Firestore and stores a random poster image from each search in a private GitHub repository.

## Functionality

The following **required** functionality is completed:
* [x] User can sign up and log in using Firebase Authentication.
* [x] User can search for movies using the OMDb API.
* [x] Displays movie details: title, poster image, year of release, rating, runtime, genre, and IMDB link.
* [x] The IMDB page link opens via implicit intent.
* [x] Share button to share movie title and IMDB URL.
* [x] User's search queries are saved to Firestore.
* [x] A random poster image from each search is saved to a private GitHub repository.
* [x] Feedback button on the toolbar that composes an email to the developer.

## Video Walkthrough

Here's a walkthrough of implemented user stories:

<img src="https://github.com/user-attachments/assets/8cf7e7ae-f6fc-4fe6-8616-0a9c900796dc" width=250/>



## Notes

Describe any challenges encountered while building the app:
* Integrating Firebase Authentication for login and sign-up.
* Handling network requests and parsing JSON data with Retrofit.
* Managing data persistence using Firestore for search history.
* Implementing the feature to save movie poster images to GitHub.

## License

Copyright [2024] [Lucas Herbst]

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and
limitations under the License.
