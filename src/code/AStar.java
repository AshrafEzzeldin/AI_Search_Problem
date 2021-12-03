package code;

import java.util.PriorityQueue;

public class AStar {

	static String AStar(Node node, byte type) {

		PriorityQueue<Node> q = new PriorityQueue<Node>();
		q.add(node);

		while (!q.isEmpty()) {
			Node n = q.poll();
			Matrix.cnt_states++;
			if (n.gameover)
				return Matrix.gameover(n);

			Node action = Matrix.updateNode(Actions.carry(n));
			action = dealAction(action, type, true);
			if (action != null)
				q.add(action);

			action = Matrix.updateNode(Actions.drop(n));
			action = dealAction(action, type, true);
			if (action != null)
				q.add(action);

			action = Matrix.updateNode(Actions.left(n));
			action = dealAction(action, type, false);
			if (action != null)
				q.add(action);

			action = Matrix.updateNode(Actions.right(n));
			action = dealAction(action, type, false);
			if (action != null)
				q.add(action);

			action = Matrix.updateNode(Actions.up(n));
			action = dealAction(action, type, false);
			if (action != null)
				q.add(action);

			action = Matrix.updateNode(Actions.down(n));
			action = dealAction(action, type, false);
			if (action != null)
				q.add(action);
			
			action = Matrix.updateNode(Actions.kill(n));
			action = dealAction(action, type, true);
			if (action != null)
				q.add(action);

			action = Matrix.updateNode(Actions.takePill(n));
			action = dealAction(action, type, true);
			if (action != null)
				q.add(action);
			
			action = Matrix.updateNode(Actions.fly(n));
			action = dealAction(action, type, false);
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
				hu = GR.heuristic1(action);
			else
				hu = GR.heuristic2(action);

			action.cost += hu;
			return action;
		}
		return null;
	}

}
