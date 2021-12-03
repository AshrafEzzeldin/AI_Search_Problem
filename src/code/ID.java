package code;

import java.util.HashSet;

public class ID {

	
	// max depth is 400 as after that all hostages should be at booth or killed  
	static String ID(Node n) {
		DFS.time=0;
		while (DFS.time<=400) {
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