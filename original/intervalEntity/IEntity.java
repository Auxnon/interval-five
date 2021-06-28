package intervalEntity;

import org.lwjgl.util.vector.Matrix4f;

import interval.Timeline;
import interval.Vec;

public interface IEntity {

	float x = 0;
	float y=0;
	float z=0;
	double r = 0;
	float height = 0;
	void hear(Entity s, String message);
	int getPIN();
	void Destroy();
	Container getContained();
	void render();
	void activate();
	void setTime(long t, int en);
	void logic();
	boolean active();
	boolean isDestroyed();
	void considerTime();
	void time(Timeline t);
	void annual();
	void presetTime(long tick, int ento);
	Matrix4f getMat();
	void setVec(Vec v);
	Vec getVec();
	float getHalfSize();
	void contactTop(Entity ei);

}
