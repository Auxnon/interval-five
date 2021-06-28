package intervalHistory;

public class Opinion {
	float like; //-1 dislike, 0 neutral, 1 like;
	float temperance; //0 reserved, 1 open    -> low and dislike:  fear. high and dislike: rage. low and like: friendly. high and like: adore.
	//or how drawn person is to context
	float bound; //0 to 1, how dependant person is, higher value overrides opinions
	//high and fear: submissive. high and rage: disdain. high and friendly: sheepish. high and adore: addicted.
}
