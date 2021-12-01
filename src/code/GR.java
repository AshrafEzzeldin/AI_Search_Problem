package code;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;

public class GR {
    //	static String rem1 = "down,up,left,fly,fly,down,down,right,takePill,left,right,left,up,right,up,down,left,left,kill,left,kill,left,takePill,right,left,down,down,";
//	static String rem = "down,up,left,fly,fly,down,down,right,takePill,";
    static String gr(Node node, int type) {

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

            Node action = Actions.carry(n);
            if (action != null) {
                int[] hu = new int[2];
                if (type == 1)
                    hu = heuristic1(action);
                else
                    hu = heuristic2(action);

                action.deaths = 0;
                action.agents_killed = hu[1];
                q.add(action);
            }
//			}
            action = Actions.drop(n);
            if (action != null) {
                int[] hu = new int[2];
                if (type == 1)
                    hu = heuristic1(action);
                else
                    hu = heuristic2(action);

                action.deaths = 0;
                action.agents_killed = hu[1];
                q.add(action);
            }

//				System.out.println("drop");

//			if (valid(n.x, n.y) && !vis[n.x][n.y][killed][lifted][drops][hostAg]) {

            action = Actions.kill(n);
            if (action != null) {
                int[] hu = new int[2];
                if (type == 1)
                    hu = heuristic1(action);
                else
                    hu = heuristic2(action);

                action.deaths = 0;
                action.agents_killed = hu[1];
                q.add(action);
            }

//			}

            action = Actions.up(n);
            if (action != null && !Matrix.vis(action)) {
                int[] hu = new int[2];
                if (type == 1)
                    hu = heuristic1(action);
                else
                    hu = heuristic2(action);

                action.deaths = 0;
                action.agents_killed = hu[1];
                q.add(action);
            }

            action = Actions.down(n);
            if (action != null && !Matrix.vis(action)) {
                int[] hu = new int[2];
                if (type == 1)
                    hu = heuristic1(action);
                else
                    hu = heuristic2(action);

                action.deaths = 0;
                action.agents_killed = hu[1];
                q.add(action);
            }
            action = Actions.left(n);
            if (action != null && !Matrix.vis(action)) {
                int[] hu = new int[2];
                if (type == 1)
                    hu = heuristic1(action);
                else
                    hu = heuristic2(action);

                action.deaths = 0;
                action.agents_killed = hu[1];
                q.add(action);
            }

            action = Actions.right(n);
            if (action != null && !Matrix.vis(action)) {
                int[] hu = new int[2];
                if (type == 1)
                    hu = heuristic1(action);
                else
                    hu = heuristic2(action);

                action.deaths = 0;
                action.agents_killed = hu[1];
                q.add(action);
            }

            action = Actions.fly(n);
            if (action != null && !Matrix.vis(action)) {
                int[] hu = new int[2];
                if (type == 1)
                    hu = heuristic1(action);
                else
                    hu = heuristic2(action);

                action.deaths = 0;
                action.agents_killed = hu[1];
                q.add(action);
            }

            action = Actions.takePill(n);
            if (action != null) {
                int[] hu = new int[2];
                if (type == 1)
                    hu = heuristic1(action);
                else
                    hu = heuristic2(action);

                action.deaths = 0;
                action.agents_killed = hu[1];
                q.add(action);
            }

        }

        return "No Solution";

    }

    static int[] heuristic3(Node n) {
        int kills = 0;
        for (int h : n.host) {
            if (h == 2)
                kills++;
        }
        return new int[]{0, kills};
    }

    static int[] heuristic1(Node n) {
        int kills = 0;
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
        return new int[]{deaths, kills};
    }

    static int[] heuristic2(Node n) {
        // TODO Auto-generated method stub
        int kills = 0;
        int deaths = 0;
        for (int i = 0; i < n.host.length; i++) {
            if (n.host[i] == 2)
                kills++;
            if (n.host_damage[i] == 98 || n.host_damage[i] == 99)
                deaths++;
        }
        return new int[]{deaths, kills + deaths};
    }
}

// grid flatened cell i, j i*m+j
// matrix , time save hostage at cell i,j

// new to every other cell and from booth

//	static int[] Dijkstra(int x, int y, int neo_damage) { // x,y are source of the cell;
//		int n = Matrix.n, m = Matrix.m;
//		int[][] dist = new int[n][m];
//		PriorityQueue<DijNode> pq = new PriorityQueue<GR.DijNode>();
//		int max_kills = Math.max(0, (99 - neo_damage) / 2);
//		DijNode node = new DijNode(x, y, 0, 0);
//		pq.add(node);
//
//		while (!pq.isEmpty()) {
//			DijNode cur = pq.poll();
//			if (dist[cur.x][cur.y]) {
//				continue;
//			}
//
//			for (int i = 0; i < 4; i++) {
//				int xloc = cur.x + dx[i];
//				int yloc = cur.y + dy[i];
//				if (valid(xloc, yloc) && dist[xloc][yloc] > cur.cost + 1) {
//					pq.add(new DijNode(xloc, yloc, cur.cnt_kills, cur.cost + 1));
//				}
//			}
//		}
//	}
//
//	static boolean valid(int x, int y) {
//		return x >= 0 && x < Matrix.cnt_states && y >= 0 && y < Matrix.m;
//	}
//
//	static int[] dx = new int[] { -1, 0, 1, 0 };
//	static int[] dy = new int[] { 0, -1, 0, 1 };
//
//	static class DijNode {
//		int cnt_kills, x, y, cost;
//
//		public DijNode(int x, int y, int cnt, int cost) {
//			this.cnt_kills = cnt;
//			this.cost = cost;
//			this.x = x;
//			this.y = y;
//		}
//	}
//}
