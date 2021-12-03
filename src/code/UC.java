package code;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;

public class UC {

	static String uc(Node node) {

		PriorityQueue<Node> q = new PriorityQueue();
		q.add(node);

		while (!q.isEmpty()) {
			Node n = q.poll();

			Matrix.cnt_states++;
			if (n.gameover)
				return Matrix.gameover(n);

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

			Node action = Actions.up(n);
			if (action != null && !Matrix.vis(action))
				q.add(action);

			action = Actions.down(n);
			if (action != null && !Matrix.vis(action))
				q.add(action);

			action = Actions.right(n);
			if (action != null && !Matrix.vis(action))
				q.add(action);

			action = Actions.left(n);
			if (action != null && !Matrix.vis(action))
				q.add(action);
			action = Actions.fly(n);
			if (action != null && !Matrix.vis(action))
				q.add(action);

			action = Actions.takePill(n);
			if (action != null) {
				q.add(action);
			}
		}

		return "No Solution";

	}

}
