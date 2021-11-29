package code;

import java.util.PriorityQueue;

public class AStar {

	static String AStar(Node node,int type) {

		PriorityQueue<Node> q = new PriorityQueue();
//		Queue<Node> q = new LinkedList<Node>();
		q.add(node);

//		Matrix.vis = new boolean[Matrix.n][Matrix.m][Matrix.hostages.length + 1][Matrix.c + 1][Matrix.hostages.length + 1][Matrix.hostages.length + 1];
		while (!q.isEmpty()) {
//			System.out.println(Matrix.vis.size());
			Node n = q.poll();
//			System.out.println(n.host);
			Matrix.cnt_states++;
//			boolean gameover = Matrix.nextStep(n);
			if (n.gameover)
				return Matrix.gameover(n);


			Node carry = Actions.carry(n);
			if (carry != null) {
//				System.out.println("carry");
				int []hu=new int[2];
				if(type==1)
					hu=GR.heuristic1(carry);
				else
					hu=GR.heuristic2(carry);
					
				Node temp= Matrix.updateNode(carry);
				temp.agents_killed+=hu[1];
				q.add(Matrix.updateNode(temp));
			}
//			}
			Node drop = Actions.drop(n);
			if (drop != null) {
				int []hu=new int[2];
				if(type==1)
					hu=GR.heuristic1(drop);
				else
					hu=GR.heuristic2(drop);
				Node temp= Matrix.updateNode(drop);
				temp.agents_killed+=hu[1];
				q.add(Matrix.updateNode(temp));

//				System.out.println("drop");
			}
//			if (valid(n.x, n.y) && !vis[n.x][n.y][killed][lifted][drops][hostAg]) {

			Node kill = Actions.kill(n);
			if (kill != null) {
				int []hu=new int[2];
				if(type==1)
					hu=GR.heuristic1(kill);
				else
					hu=GR.heuristic2(kill);

				Node temp= Matrix.updateNode(kill);
				temp.agents_killed+=hu[1];
				q.add(Matrix.updateNode(temp));
			}
//			}

			Node up = Actions.up(n);
			if (up != null && !Matrix.vis(up)) {
				int []hu=new int[2];
				if(type==1)
					hu=GR.heuristic1(up);
				else
					hu=GR.heuristic2(up);
				Node temp= Matrix.updateNode(up);
				temp.agents_killed+=hu[1];
				q.add(Matrix.updateNode(temp));
			}

			Node down = Actions.down(n);
			if (down != null && !Matrix.vis(down)) {
				int []hu=new int[2];
				if(type==1)
					hu=GR.heuristic1(down);
				else
					hu=GR.heuristic2(down);
				Node temp= Matrix.updateNode(down);
				temp.agents_killed+=hu[1];
				q.add(Matrix.updateNode(temp));
			}
			Node left = Actions.left(n);
			if (left != null && !Matrix.vis(left)) {
				int []hu=new int[2];
				if(type==1)
					hu=GR.heuristic1(left);
				else
					hu=GR.heuristic2(left);
				Node temp= Matrix.updateNode(left);
				temp.agents_killed+=hu[1];
				q.add(Matrix.updateNode(temp));
			}

			Node right = Actions.right(n);
			if (right != null && !Matrix.vis(right)) {
				int []hu=new int[2];
				if(type==1)
					hu=GR.heuristic1(right);
				else
					hu=GR.heuristic2(right);
				Node temp= Matrix.updateNode(right);
				temp.agents_killed+=hu[1];
				q.add(Matrix.updateNode(temp));
			}

			Node fly = Actions.fly(n);
			if (fly != null && !Matrix.vis(Matrix.updateNode(fly))) {
				int []hu=new int[2];
				if(type==1)
					hu=GR.heuristic1(fly);
				else
					hu=GR.heuristic2(fly);
				Node temp= Matrix.updateNode(fly);
				temp.agents_killed+=hu[1];
				q.add(Matrix.updateNode(temp));
			}

			Node takePill = Actions.takePill(n);
			if (takePill != null) {
				int []hu=new int[2];
				if(type==1)
					hu=GR.heuristic1(takePill);
				else
					hu=GR.heuristic2(takePill);

				Node temp= Matrix.updateNode(takePill);
				temp.agents_killed+=hu[1];
				q.add(Matrix.updateNode(temp));	
			}
		}

		return "No Solution";

	}

	
}
