package code;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

public class DFS {

	static int time = -1;

	static String dfs(Node n) {

		Matrix.cnt_states++;

		if (n.gameover)
			return Matrix.gameover(n);

		if (time == n.time)
			return "No Solution";

		String ret = "No Solution";

		Node action = Actions.takePill(n);
		if (action != null) {
			ret = dfs(Matrix.updateNode(action));
			if (!ret.equals("No Solution"))
				return ret;
		}

		action = Actions.carry(n);
		if (action != null) {
			ret = dfs(Matrix.updateNode(action));
			if (!ret.equals("No Solution"))
				return ret;
		}

		action = Actions.drop(n);
		if (action != null) {
			ret = dfs(Matrix.updateNode(action));
			if (!ret.equals("No Solution"))
				return ret;
		}

		action = Actions.up(n);
		if (action != null && !Matrix.vis(action))
			ret = dfs(Matrix.updateNode(action));
		if (!ret.equals("No Solution"))
			return ret;

		action = Actions.down(n);
		if (action != null && !Matrix.vis(action))
			ret = dfs(Matrix.updateNode(action));
		if (!ret.equals("No Solution"))
			return ret;

		action = Actions.left(n);
		if (action != null && !Matrix.vis(action))
			ret = dfs(Matrix.updateNode(action));
		if (!ret.equals("No Solution"))
			return ret;

		action = Actions.right(n);
		if (action != null && !Matrix.vis(action))
			ret = dfs(Matrix.updateNode(action));
		if (!ret.equals("No Solution"))
			return ret;

		action = Actions.fly(n);
		if (action != null && !Matrix.vis(action))
			ret = dfs(Matrix.updateNode(action));
		if (!ret.equals("No Solution"))
			return ret;

		action = Actions.kill(n);
		if (action != null) {
			ret = dfs(Matrix.updateNode(action));
			if (!ret.equals("No Solution"))
				return ret;
		}

		return ret;

	}

}
