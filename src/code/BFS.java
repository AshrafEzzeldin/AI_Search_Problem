package code;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

public class BFS {

	static String bfs(Node node) {

		Queue<Node> q = new LinkedList<Node>();
		q.add(node);

		while (!q.isEmpty()) {
			Node n = q.poll();
			Matrix.cnt_states++;

			if (n.gameover)
				return Matrix.gameover(n);

			Node takePill = Actions.takePill(n);
			if (takePill != null) {
				q.add(takePill);
			}

			Node carry = Actions.carry(n);
			if (carry != null) {
				q.add(carry);
			}

			Node drop = Actions.drop(n);
			if (drop != null) {
				q.add(drop);
			}

			Node kill = Actions.kill(n);
			if (kill != null) {
				q.add(kill);
			}

			Node up = Actions.up(n);
			if (up != null && !Matrix.vis(up))
				q.add(up);

			Node down = Actions.down(n);
			if (down != null && !Matrix.vis(down))
				q.add(down);

			Node left = Actions.left(n);
			if (left != null && !Matrix.vis(left))
				q.add(left);

			Node right = Actions.right(n);
			if (right != null && !Matrix.vis(right))
				q.add(right);

			Node fly = Actions.fly(n);
			if (fly != null && !Matrix.vis(fly))
				q.add(fly);

		}

		return "No Solution";

	}

}
