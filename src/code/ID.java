package code;

import java.util.HashSet;

public class ID {


	static String ID(Node n) {
		DFS.time=0;
		while (DFS.time<1000) {
			DFS.time++;
			Matrix.vis = new HashSet<String>();
			String sol = DFS.dfs(n);
			if (!sol.equals("No Solution")) {
				return sol;
			}
		}
		return "No Solution";
	}


}
