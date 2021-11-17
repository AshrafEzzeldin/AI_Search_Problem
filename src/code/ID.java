package code;

public class ID {


	static String ID(Node n) {
		DFS.time=0;
		while (true) {
			DFS.time++;
			Matrix.vis = new boolean[Matrix.n][Matrix.m][Matrix.hostages.length + 1][Matrix.c + 1][Matrix.hostages.length
					+ 1][Matrix.hostages.length + 1];
			String sol = DFS.dfs(n);
			if (!sol.equals("NOSOL")) {
				return sol;
			}
		}
	}


}
