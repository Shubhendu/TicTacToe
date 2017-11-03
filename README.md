# Tic Tac Toe application for a slack channel

![TIC TAC TOE](/src/main/resources/image.png?raw=true "Sample Board")

### Supported commands
- `/ttt start [userName] [boardSize]` - Challenge a user to play a new game with default board size 3 X 3
- `/ttt status` - Get the current status of the game played in the channel.
- `/ttt move [row] [column]` - Play your next move. The row and column index starts with 1.
- `/ttt end` - End the current game
- `/ttt help` - Help


### Configuration
Please setup values for following tokens in your environment - 
- SLACK_TTT_TOKEN - Verification token for this Slack command
- SLACK_API_TOKEN - Slack API token

For the scope of this POC these values were stored in AWS environment.

### Build
We are using maven to build this application.
` mvn clean install`

### Deployment
- Local environment
``` 
	1- Copy the .war file to $CATALINA_HOME/webapps/
	2- Start the server by $CATALINA_HOME/bin/startup.sh
	Note: $CATALINA_HOME: is an environment variable whcih points to the directory where you have installed Tomcat. 
	For example, /Users/abc/Documents/software/apache-tomcat-8.5.23. 
```
- Cloud
This application is currently deployed on AWS Elastic Beanstalk. 
[Instructions can be found here](http://docs.aws.amazon.com/gettingstarted/latest/deploy/deploying-with-elastic-beanstalk.html)


### Scope for improvements
- Use caching service like Redis cache instead of in-memory map to store channel to game details.
- Allow users to play with their own emojis.
- Deploy with AWS auto scaling features turned on(for high load).
- More tests.