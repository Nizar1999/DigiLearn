<p align="center">
  <img src="https://github.com/Nizar1999/DigiLearn/blob/master/screenshots/Banner.png" width = 55%; height=55% />
</p>

![Languages](https://img.shields.io/badge/-Java-%23A1E88E?style=for-the-badge) 

# Features

- Users can sign-up to DigiLearn by creating an account to which their course progress is linked and maintained. 

- Accounts and passwords are stored locally and encrypted.

- A course creator that allows for extremely easy creation of custom courses.

- Text and image based MCQ questions for quizzes.

- Ability to create questions for different difficulties and adjusting difficulties for quizzes.

- Dynamic GUI that changes with new course additions.

- Easy addition of new courses.

- Progress tracking across different courses.

# Check Wiki for details on usage and implementation.

# Usage

![pic1]("https://ibb.co/n3VhmB1")
Upon looking at the above screenshot, one can notice that all the courses are presented as folders by their names and that there exists a Metadata text file containing the names of all courses. Each course folder also contains the corresponding chapters as folders themselves.

https://ibb.co/pngDLNC
Upon closer inspection of the Metadata text file in the course folder, we can see that it contains the chapter names by order.

Inside the chapter folders, each chapter contains a bunch of text files holding data that is read by DigiLearn to present the course.

https://ibb.co/j5LZ19y
The resources folder contains the images of the courses, and a folder by the name of Accounts which contains all the accounts created and their corresponding data inside them.

https://ibb.co/0jq3PY2
Inside each user folder there exists two text files, the first (one named the same as the username) contains the encrypted password of the user. The other contains the progress of the user in each course, course1/course2/course3 where courses 1 and 2 have 0 chapters completed, but course 3 has 4 chapters completed.
