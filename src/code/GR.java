package code;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;

public class GR {
	static String gr(Node node, byte type) {

		PriorityQueue<Node> q = new PriorityQueue();
		q.add(node);

		while (!q.isEmpty()) {
			Node n = q.poll();
			Matrix.cnt_states++;

			if (n.gameover)
				return Matrix.gameover(n);

			Node action = Actions.carry(n);
			action = dealAction(action, type, true);
			if (action != null)
				q.add(action);

			action = Actions.drop(n);
			action = dealAction(action, type, true);
			if (action != null)
				q.add(action);

			action = Actions.kill(n);
			action = dealAction(action, type, true);
			if (action != null)
				q.add(action);

			action = Actions.up(n);
			action = dealAction(action, type, false);
			if (action != null)
				q.add(action);

			action = Actions.down(n);
			action = dealAction(action, type, false);
			if (action != null)
				q.add(action);

			action = Actions.left(n);
			action = dealAction(action, type, false);
			if (action != null)
				q.add(action);

			action = Actions.right(n);
			action = dealAction(action, type, false);
			if (action != null)
				q.add(action);

			action = Actions.fly(n);
			action = dealAction(action, type, false);
			if (action != null)
				q.add(action);

			action = Actions.takePill(n);
			action = dealAction(action, type, true);
			if (action != null)
				q.add(action);
		}

		return "No Solution";

	}

	// apply the heuristic of this action and add it into the priority queue
	static Node dealAction(Node action, byte type, boolean visited) {
		if (action != null && (visited || !Matrix.vis(action))) {
			int hu = 0;
			if (type == 1)
				hu = heuristic1(action);
			else
				hu = heuristic2(action);

			action.cost = hu;
			return action;
		}
		return null;
	}

	// heuristic1 gets the number of hostages which neo can't save
	// but if he takes all available pills and without agents
	static int heuristic1(Node n) {
		int deaths = 0;
		int mskstatus = 0;
		int mskcarry = 0;
		for (int i = 0; i < n.host.length; i++) {
			if (n.host[i] > 1)
				mskstatus |= (1 << i);
			if (n.host[i] == 1 || n.host[i] == 5)
				mskcarry |= (1 << i);
		}
		deaths = n.host.length - Matrix.dp(n.x, n.y, mskstatus, mskcarry, 0);
		int c = deaths * 10 ;

//		if (c == 0 && !n.gameover)
//			c = 1;
		return c;
	}

	// heuristic2 the number of hostages which are dead and who have damage greater
	// than 97
	static int heuristic2(Node n) {
		// TODO Auto-generated method stub
		int kills = 0;
		int deaths = 0;
		for (int i = 0; i < n.host.length; i++) {
			if (n.host[i] == 2)
				kills++;
//			if (n.host_damage[i] == 98 || n.host_damage[i] == 99)
//				deaths++;
		}
		int c = deaths * 10 + kills + deaths ;

//		if (c == 0 && !n.gameover)
//			c = 1;
		return c;

	}
}
