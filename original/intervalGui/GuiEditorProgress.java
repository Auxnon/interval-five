package intervalGui;

import interval.Changer;
import interval.FileManager;
import interval.IFont;
import interval.Message;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class GuiEditorProgress extends Guiscroll {

	public class ProgressPart {
		String details;
		int minutes;
		String[] path;
		int weight;
		int tot;

		public ProgressPart(String p[], int w, int m, String d) {
			path = p;
			weight = w;
			minutes = m;
			details = d;
			tot=weight*minutes;
		}

		public String getTag() {
			int hour = minutes / 60;
			String min = "" + (minutes % 60);
			if (min.length() == 1)
				min = "0" + min;
			return  details + " " + hour + ":" + min+"  "+weight;
		}
	}

	ArrayList<ProgressPart> categories;
	int type;
	GIdrop whole;
	
	int total;

	public void command(String s,GI g){
		if (s.contains("ACTION")) {
			Message.addPass(s);
		} else if (s.equals("Level")) {

		} else if (s == "<- Back") {
			Changer.menuUp();
		}
	}
	public void remake(){
		whole.drops.clear();
		whole.weighted=0;
		expanse();
	}

	public void expanse() {
		BufferedImage txt = IFont.newIm(Color.lightGray.getRGB(), IFont.im, -1);
		HashMap<String, GIdrop> split = new HashMap<String, GIdrop>();

		for (ProgressPart p : categories) {
			Color imo = IFont.getC(p.tot,total);

			GIdrop parent = null;
			for (int u = 0; u < p.path.length; u++) {

				GIdrop gg = split.get(p.path[u]);
				if (gg == null) {
					GIdrop gd = new GIdrop(p.path[u], 0, 6, type, txt, imo);

					/*for (int k = 0; k <= u; k++) {
					}*/

					split.put(p.path[u], gd);
					if (u == 0)
						whole.add(gd);

					if (parent != null)
						parent.add(gd);
						

					gg = gd;
				}
				parent = gg;
			}

			//Message.m(this, p.getTag()+"  "+imo);
			parent.add(new GI(p.getTag(), 0, 0, type, txt, imo));
			parent.weighted+=p.tot;
		}
		whole.dropFigure(total);
	}


	public void initer() {
		type = IFont.BLACK;
		parse();
		menuPriority = true;
		GI back = new GI("<- Back", 0, 6).centerLeft();
		back.sticky = true;
		add(back);
		whole = new GIdrop("Progress", 10, 60, type);
		expanse();
		add(whole);
	}

	public void parse() {

		BufferedReader br = FileManager.getProgressReader();
		boolean addHeader = false;
		categories = new ArrayList<ProgressPart>();
		ArrayList<String> data = new ArrayList<String>();

		if (br != null) {
			String s = "";
			try {
				int c = 0;
				while ((s = br.readLine()) != null) {
					data.add(s);
					boolean com = s.startsWith("//");
					if (!com) {
						if (c == 0) {
							addHeader = true;
						}
						if (s.contains(":")) {
							String[] st = s.split(":");
							String p[] = st[0].split("/");
							int weight = Integer.parseInt(st[1]);
							int minutes = Integer.parseInt(st[2]);
							total+=weight*minutes;
							String details = st[3];
							categories.add(new ProgressPart(p, weight, minutes,
									details));
						}
					}
					c++;
				}
				br.close();

				if (addHeader) {
					String header = "//format is as follows:   type/subType:contribution-weight:minutes-taken:details";
					BufferedWriter bw = FileManager.getProgressWriter();
					bw.write(header + "\n");
					for (String tt : data)
						bw.write(tt + "\n");

					bw.close();
				}

			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
