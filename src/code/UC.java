package code;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;

public class UC {

	
	static String uc(Node node) {

		PriorityQueue<Node> q = new PriorityQueue();
//		Queue<Node> q = new LinkedList<Node>();
		q.add(node);

//		Matrix.vis = new boolean[Matrix.n][Matrix.m][Matrix.hostages.length + 1][Matrix.c + 1][Matrix.hostages.length + 1][Matrix.hostages.length + 1];
		while (!q.isEmpty()) {
//			System.out.println(Matrix.vis.size());
			Node n = q.poll();
			
			Matrix.cnt_states++;
//			boolean gameover = Matrix.nextStep(n);
			if (n.gameover)
				return Matrix.gameover(n);

			Node carry = Actions.carry(n);
			if (carry != null) {
//				System.out.println("carry");
				q.add(Matrix.updateNode(carry));
			}
//			}
			Node drop = Actions.drop(n);
			if (drop != null) {
				q.add(Matrix.updateNode(drop));

//				System.out.println("drop");
			}
//			if (valid(n.x, n.y) && !vis[n.x][n.y][killed][lifted][drops][hostAg]) {

			Node kill = Actions.kill(n);
			if (kill != null) {
//				System.out.println("kill");
				q.add(Matrix.updateNode(kill));
			}
//			}

			Node action = Matrix.updateNode(Actions.up(n));
			if (action != null && !Matrix.vis(action))
				q.add(action);

			action = Matrix.updateNode(Actions.down(n));
			if (action != null && !Matrix.vis(action))
				q.add(action);

			action = Matrix.updateNode(Actions.left(n));
			if (action != null && !Matrix.vis(action))
				q.add(action);

			action = Matrix.updateNode(Actions.right(n));
			if (action != null && !Matrix.vis(action))
				q.add(action);

			action = Matrix.updateNode(Actions.fly(n));
			if (action != null && !Matrix.vis(action))
				q.add(action);

			action = Matrix.updateNode(Actions.takePill(n));
			if (action != null) {
//				System.out.println("take pill");
				q.add(action);
//				return "asd";

			}
		}

		return "No Solution";

	}

}
