# Play a game of Tic Tac Toe in a slack channel

![TIC TAC TOE](/src/main/resources/image.png?raw=true "Sample Board")

### Supported commands
- `/ttt start [userName] [boardSize]` - Challenge a user to play a new game with default board size 3 X 3
- `/ttt status` - Get the current status of the game played in the channel.
- `/ttt move [row] [column]` - Play your next move. The row and column index starts with 1.
- `/ttt end` - End the current game
- `/ttt help` - Help


### Configure
Please setup values for following tokens in your environment - 
- SLACK_TTT_TOKEN - Verification token for this Slack command
- SLACK_API_TOKEN - Slack API token

For the scope of this POC these values were stored in AWS environment.

### Build
``` mvn clean install
```

### Deploy

Once the war file is created for this application it can be deployed on any application server. For the scope of this POC this was deployed on AWS Elastic Beanstalk. 