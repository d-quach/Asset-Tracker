The goal here was to summarize concepts that I have learned in school. This program is complete with a GUI, a connection to a MySQL database, and some features using the JavaMail API.
GUI programming isn't my strongest point but I learned some things along the way. It might have been better to use the card layout instead of making multiple JFrames.
Using one actionPerformed method would have been better instead of attaching one to each button.
There still needs to be an edit function where an asset can be updated.
If the text in any cell of the asset table gets too large I need to add a way to scroll through the content.
A future improvement would be to allow the sorting of each column in the table.
Another one would be to send a URL for the user to update their password instead of mailing them one.

Using this program on your local machine needs:
1) For you to clone this project.
2) Update the config.xml file from the "res" folder to have your email and password in the "env-entry-value" tags.
3) Your email account would have to approve the use of less-secure applications.
4) Update the variables in the MySQLDriver.java file to match the url, user, and password for connecting to your database.
5) Run the asset_tracker_db_schema.sql file located in "res" folder in MySQL to create a few tables.
6) Export the project as a runnable jar file for use.

Here are some pictures of the program.


![Register Account Window](https://i.imgur.com/pbYlHkT.png)

![Log In Page](https://i.imgur.com/og6O2fe.png)

![Forgotten Password](https://i.imgur.com/iXHrSFs.png)

![The Asset Page](https://i.imgur.com/NcNgNfC.png)

![Deleting Assets](https://i.imgur.com/sp8aqbP.png)
