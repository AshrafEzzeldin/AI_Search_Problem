package code;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

public class BFS {

	static String bfs(Node node) {

		Queue<Node> q = new LinkedList<Node>();
		q.add(node);

//		Matrix.vis = new boolean[Matrix.n][Matrix.m][Matrix.hostages.length + 1][Matrix.c + 1][Matrix.hostages.length
//				+ 1][Matrix.hostages.length + 1];
		while (!q.isEmpty()) {
			Node n = q.poll();
//			System.out.println(Arrays.toString(n.host));
			Matrix.cnt_states++;
//			boolean gameover = Matrix.nextStep(n);
//			System.out.println(Arrays.toString(n.host));
//			System.out.println(gameover + " s");
//			System.out.println(n.path.toString());

			if (n.gameover)
				return Matrix.gameover(n);
//			if (valid(n.x, n.y) && !vis[n.x][n.y][killed][lifted][drops][hostAg]) {

//			System.out.println(n.x + " " + n.y + " " + Matrix.Telephone.x + " " + Matrix.Telephone.y);

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

		return "No Solution";

	}

}
