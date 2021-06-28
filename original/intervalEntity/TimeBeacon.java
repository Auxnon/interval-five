package intervalEntity;

import interval.Timeline;

public abstract class TimeBeacon {

	abstract public Timeline getTimeline();
	abstract public boolean active();
	abstract public boolean kairos();
}
