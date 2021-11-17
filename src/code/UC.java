package code;

import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;

public class UC {

	static String uc(Node node) {

		PriorityQueue<Node> q = new PriorityQueue();
		q.add(node);

		Matrix.vis = new boolean[Matrix.n][Matrix.m][Matrix.hostages.length + 1][Matrix.c + 1][Matrix.hostages.length + 1][Matrix.hostages.length + 1];
		while (!q.isEmpty()) {

			Node n = q.poll();
//			System.out.println(n.host);
			Matrix.cnt_states++;
			boolean gameover = Matrix.nextStep(n);
			if (gameover)
				return Matrix.gameover(n) + " /" + n.time;


			Node takePill = Actions.takePill(n);
			if (takePill != null) {
//				System.out.println("take pill");
				q.add(takePill);
//				return "asd";

			}

			Node carry = Actions.carry(n);
			if (carry != null) {
//				System.out.println("carry");
				q.add(carry);
			}
//			}
			Node drop = Actions.drop(n);
			if (drop != null) {
				q.add(drop);

//				System.out.println("drop");
			}
//			if (valid(n.x, n.y) && !vis[n.x][n.y][killed][lifted][drops][hostAg]) {

			Node kill = Actions.kill(n);
			if (kill != null) {
//				System.out.println("kill");
				q.add(kill);
			}
//			}

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

		return "NOSOL";

	}
	
}
