package code;

import java.util.Arrays;
import java.util.PriorityQueue;

public class AStar {
	static String rem1 = "down,up,left,fly,fly,down,down,right,takePill,left,right,left,up,right,up,down,left,left,kill,left,kill,left,takePill,right,left,down,down,";
	static String rem = "left,fly,right,carry,left,fly,right,down,drop,down,";
	
	static String AStar(Node node,int type) {
		
		PriorityQueue<Node> q = new PriorityQueue();
//		Queue<Node> q = new LinkedList<Node>();
		q.add(node);

//		Matrix.vis = new boolean[Matrix.n][Matrix.m][Matrix.hostages.length + 1][Matrix.c + 1][Matrix.hostages.length + 1][Matrix.hostages.length + 1];
		while (!q.isEmpty()) {
//			System.out.println(Matrix.vis.size());
			Node n = q.poll();
//			if (n.path.toString().equals(rem)) {
////				System.out.println(n.path.toString());
//				System.out.println(Arrays.toString(n.host_damage));
//				System.out.println(Arrays.toString(n.host));
//			}
			//			System.out.println(n.host);
			Matrix.cnt_states++;
//			boolean gameover = Matrix.nextStep(n);
			if (n.gameover)
				return Matrix.gameover(n);


			Node action = Matrix.updateNode(Actions.carry(n));
			if (action != null&& !Matrix.vis(action)) {
				int[] hu = new int[2];
				if (type == 1)
					hu = GR.heuristic1(action);
				else
					hu = GR.heuristic2(action);

				action.agents_killed += hu[1];
				q.add(action);
			}
//			}
			action = Matrix.updateNode(Actions.drop(n));
			if (action != null&& !Matrix.vis(action)) {
				int[] hu = new int[2];
				if (type == 1)
					hu = GR.heuristic1(action);
				else
					hu = GR.heuristic2(action);

				action.agents_killed += hu[1];
				q.add(action);
			}
//			if (valid(n.x, n.y) && !vis[n.x][n.y][killed][lifted][drops][hostAg]) {

			action = Matrix.updateNode(Actions.kill(n));
			if (action != null&& !Matrix.vis(action)) {
				int[] hu = new int[2];
				if (type == 1)
					hu = GR.heuristic1(action);
				else
					hu = GR.heuristic2(action);

				action.agents_killed += hu[1];
				q.add(action);
			}
//			}

			action = Matrix.updateNode(Actions.up(n));
			if (action != null&& !Matrix.vis(action)) {
				int[] hu = new int[2];
				if (type == 1)
					hu = GR.heuristic1(action);
				else
					hu = GR.heuristic2(action);

				action.agents_killed += hu[1];
				q.add(action);
			}
			action = Matrix.updateNode(Actions.down(n));
			if (action != null&& !Matrix.vis(action)) {
				int[] hu = new int[2];
				if (type == 1)
					hu = GR.heuristic1(action);
				else
					hu = GR.heuristic2(action);

				action.agents_killed += hu[1];
				q.add(action);
			}
			action = Matrix.updateNode(Actions.left(n));
			if (action != null&& !Matrix.vis(action)) {
				int[] hu = new int[2];
				if (type == 1)
					hu = GR.heuristic1(action);
				else
					hu = GR.heuristic2(action);

				action.agents_killed += hu[1];
				q.add(action);
			}
			action = Matrix.updateNode(Actions.right(n));
			if (action != null&& !Matrix.vis(action)) {
				int[] hu = new int[2];
				if (type == 1)
					hu = GR.heuristic1(action);
				else
					hu = GR.heuristic2(action);

				action.agents_killed += hu[1];
				q.add(action);
			}

			action = Matrix.updateNode(Actions.fly(n));
			if (action != null&& !Matrix.vis(action)) {
				int[] hu = new int[2];
				if (type == 1)
					hu = GR.heuristic1(action);
				else
					hu = GR.heuristic2(action);

				action.agents_killed += hu[1];
				q.add(action);
			}
			
			action = Matrix.updateNode(Actions.takePill(n));
			if (action != null&& !Matrix.vis(action)) {
				int[] hu = new int[2];
				if (type == 1)
					hu = GR.heuristic1(action);
				else
					hu = GR.heuristic2(action);

				action.agents_killed += hu[1];
				q.add(action);
			}

		}

		return "No Solution";

	}

	
}
