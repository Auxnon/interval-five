package intervalGui;

import interval.MainFrame;

public abstract class Guiscroll extends Gui {


	float lowestX,highestX,lowestY;
	public abstract void initer();


	@Override
	public void sub(){
		smy = my;
		rmy = 0;
		smx = mx;
		rmx = 0;
		float L=lowestY-4;
		//if (scroll) {
		if (lowestY < 4) {
			double dd = (64 - (L));
			double dm = 60 - (dd * (1 - (my / 64.0)));
			if (dm < 0) {
				if(dm-lowestY<-4){
					dm=L;
				}
				smy = my + (float) dm;
				rmy = (float) dm;
			}
		}


		float Left=MainFrame.screenLeft();
		float Right=MainFrame.screenRight();
		L=lowestX;

		if (lowestX < Left) {
			double dd = (Right - (L));
			double dm = Right - (4+ dd * (1 - (my / 64.0)));
			if (dm < Right) {
				if(dm-lowestY<Right){
					dm=L;
				}
				smx = mx + (float) dm;
				rmx = (float) dm;
			}
		}
		/*else if (highestX > Right) {
			double dd = (Right - (L));
			double dm = Right - (4+ dd * (1 - (my / 64.0)));
			if (dm < Right) {
				if(dm-lowestY<Right){
					dm=L;
				}
				smx = mx + (float) dm;
				rmx = (float) dm;
			}
		}*/

	}

	public void sub2(){
		lowestX = 99;
		lowestY = 99;
		highestX = -99;

		for (GI G:getG()) {
			if (G.visible){
				if( G.y < lowestY){
					lowestY = G.y;
				}
				if( G.x < lowestX){
					lowestX = G.x;
				}
				if( G.x+G.sx > highestX){
					highestX = G.x+G.sx;
				}
			}
		}
	}
}
