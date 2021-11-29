package code;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

public class Matrix extends General_Search {

	static int n, m, c, cnt_states;
	static char[][] g;
	static Random Rand;
	static boolean[][] Taken;
	static Hostage[] hostages;
	static Position[] pills;
	static Position Neo, Telephone;
	static Position[] pads, agents;
	static HashSet<String> vis;

//	static boolean vis[][][][][][];

	static int rand(int l, int r) {

		return Rand.nextInt((r - l + 1)) + l;
	}

	static class Position {
		int x, y;

		public Position(int a, int b) {
			x = a;
			y = b;
		}

		@Override
		public String toString() {
			// TODO Auto-generated method stub
			return "(" + x + " " + y + ")";
		}
	}

	static class Hostage {
		Position position;
		int damage;

		public Hostage(int x, int y, int d) {
			position = new Position(x, y);
			damage = d;
		}

		public Hostage(Position p, int d) {
			position = p;
			damage = d;
		}
	}

	static Position GetRandomPosition() { // Editable

		ArrayList<Position> Rem = new ArrayList<>();

		for (int i = 0; i < n; i++)
			for (int j = 0; j < m; j++)
				if (!Taken[i][j])
					Rem.add(new Position(i, j));

		Position pos = Rem.get(rand(0, Rem.size() - 1));
		String grid2 = "5,5;2;3,2;0,1;4,1;0,3;1,2,4,2,4,2,1,2,0,4,3,0,3,0,0,4;1,1,77,3,4,34";

		Taken[pos.x][pos.y] = true;

		return pos;
	}

	static void genGrid() {
		Rand = new Random();

		n = rand(5, 15);
		m = rand(5, 15);
//		n = 5;
//		m = 5;
		c = rand(1, 4);

		g = new char[n][m];
		Taken = new boolean[n][m];

		Neo = GetRandomPosition();
		Telephone = GetRandomPosition();

		int Hostages = rand(3, 10);
		hostages = new Hostage[Hostages];

		for (int i = 0; i < Hostages; i++) {
			hostages[i] = new Hostage(GetRandomPosition(), rand(1, 99));
		}

		int Pills = rand(0, Hostages);
		pills = new Position[Pills];
		for (int i = 0; i < Pills; i++)
			pills[i] = GetRandomPosition();

		int AllRemaning = (n * m) - Hostages - Pills - 2;

		int Pads = rand(0, AllRemaning / 2);
		AllRemaning -= 2 * Pads;
		int Agents = rand(0, AllRemaning);

		pads = new Position[2 * Pads];
		agents = new Position[Agents];

		for (int i = 0; i < Agents; i++)
			agents[i] = GetRandomPosition();

		for (int i = 0; i < 2 * Pads; i += 2) {
			pads[i] = GetRandomPosition();
			pads[i + 1] = GetRandomPosition();
		}
	}

	static int num(String s) {
		return Integer.parseInt(s);
	}

	static void parse(String grid) {
		String split[] = grid.split(";");
		String size[] = split[0].split(",");
		String neoloc[] = split[2].split(",");
		String telloc[] = split[3].split(",");
		String agentloc[] = split[4].split(",");
		String pillloc[] = split[5].split(",");
		String padloc[] = split[6].split(",");
		String hostTemp[] = split[7].split(",");
		c = num(split[1]);
		n = num(size[0]);
		m = num(size[1]);
		Neo = new Position(num(neoloc[0]), num(neoloc[1]));
		Telephone = new Position(num(telloc[0]), num(telloc[1]));

		agents = new Position[agentloc.length / 2];
		for (int i = 0; i < agents.length; i++) {
			agents[i] = new Position(num(agentloc[i * 2]), num(agentloc[i * 2 + 1]));
		}
		pills = new Position[pillloc.length / 2];
		for (int i = 0; i < pills.length; i++) {
			pills[i] = new Position(num(pillloc[i * 2]), num(pillloc[i * 2 + 1]));
		}
		pads = new Position[padloc.length / 2];
		for (int i = 0; i < pads.length / 2; i++) {
			pads[i] = new Position(num(padloc[i * 4]), num(padloc[i * 4 + 1]));
//			pads[(i + pads.length / 2)] = new Position(num(padloc[(i + pads.length / 2) * 2]),
//					num(padloc[(i + pads.length / 2) * 2 + 1]));

			pads[(i + pads.length / 2)] = new Position(num(padloc[i * 4 + 2]), num(padloc[i * 4 + 3]));
		}
		hostages = new Hostage[hostTemp.length / 3];
		for (int i = 0; i < hostages.length; i++) {
			hostages[i] = new Hostage(new Position(num(hostTemp[i * 3]), num(hostTemp[i * 3 + 1])),
					num(hostTemp[i * 3 + 2]));
		}
	}

	public static String solve(String grid, String strategy, boolean visualize) {

		// parsing the input to gen init state (node)

		parse(grid);
		cnt_states = 0;
		Node node = new Node((byte) Neo.x, (byte) Neo.y, new byte[hostages.length], 0, (short) 0,
				new byte[agents.length], (byte) 0, new StringBuilder());

		String sol = new Matrix().general_search(node, strategy);

		return sol;

	}

	static boolean vis(Node n) {

		StringBuilder visited = new StringBuilder();
		visited.append("" + n.x + n.y + n.damageNeo);

//		byte killed = 0, lifted = 0, drops = 0, hostAg = 0;

		for (byte a : n.agents) {
			visited.append(a);
		}

		for (byte h : n.host) {
			visited.append(h);
		}
		visited.append("" + n.pill);

//				
//			if (h == 1 || h == 5)
//				lifted++;
//			if (h > 3)
//				killed++;
//			if (h == 3 || h == 4)
//				drops++;
//			if (h == 2)
//				hostAg++;
//	}

		if (!vis.contains(visited.toString())) {
			vis.add(visited.toString());
			return false;
		}
		return true;
	}

	static boolean nextStep(Node node) {
		// host next step +2

		byte x = node.x;
		byte y = node.y;
		byte[] host = node.host;
		int pill = node.pill;
		short time = node.time;
		byte[] ag = node.agents;
		byte damageNeo = node.damageNeo;
		int numOfPills = Integer.bitCount(pill);

		boolean gameover = Telephone.x == x && Telephone.y == y && damageNeo < 100;
		for (int i = 0; i < hostages.length; i++) {
			int damage = hostages[i].damage + 2 * time - numOfPills * 22;
			if (damage >= 100) {
				if (host[i] == 1)
					host[i] = 5;
				if (host[i] == 0)
					host[i] = 2;
			}
			if (host[i] < 3 || host[i] == 5) {
				gameover = false;
			}
		}

//		gameover |= damageNeo - 20 * numOfPills >= 100;

		return gameover;

	}

	static Node updateNode(Node node) {
		// host next step +2

		byte x = node.x;
		byte y = node.y;
		byte[] host = node.host;
		int pill = node.pill;
		short time = node.time;
		byte[] ag = node.agents;
		byte damageNeo = node.damageNeo;
		int numOfPills = Integer.bitCount(pill);
		StringBuilder path = new StringBuilder(node.path.toString());

		int deaths = 0;
		int agents_killed = 0;

		boolean gameover = Telephone.x == x && Telephone.y == y && damageNeo < 100;
		for (int i = 0; i < hostages.length; i++) {
			int damage = hostages[i].damage + 2 * time - numOfPills * 22;
			if (damage >= 100) {
				if (host[i] == 1)
					host[i] = 5;
				if (host[i] == 0)
					host[i] = 2;
			}
			if (host[i] > 3)
				deaths++;
			if (host[i] == 6)
				agents_killed++;
			if (host[i] < 3 || host[i] == 5) {
				gameover = false;
			}
		}

		for (byte temp : ag) {
			if (temp == 1)
				agents_killed++;
		}

//		gameover |= damageNeo - 20 * numOfPills >= 100;

		return new Node(x, y, host, pill, time, ag, damageNeo, path, deaths, agents_killed, gameover);

	}

	static String gameover(Node node) {
		int deaths = 0, kills = 0;
		for (int h : node.host) {
			if (h > 3)
				deaths++;
			if (h == 6)
				kills++;
		}
		for (int a : node.agents)
			if (a == 1)
				kills++;
		return new StringBuilder(node.path.substring(0, node.path.length() - 1)) + ";" + deaths + ";" + kills + ";"
				+ cnt_states;
	}

	@Override
	public String general_search(Node node, String strategy) {

		vis = new HashSet<String>();
//		vis = new boolean[Matrix.n][Matrix.m][Matrix.hostages.length + 1][Matrix.c + 1][Matrix.hostages.length
//				+ 1][Matrix.hostages.length + 1];
		String sol = "";
		if (strategy.equals("BF")) {
			sol = BFS.bfs(node);
		}
		if (strategy.equals("DF")) {
			sol = DFS.dfs(node);
		}
		if (strategy.equals("ID")) {
			sol = ID.ID(node);
		}
		if (strategy.equals("UC")) {
			sol = UC.uc(node);

		}
		if (strategy.equals("GR1")) {
			sol = GR.gr(node,1);

		}
		if (strategy.equals("GR2")) {
			sol = GR.gr(node,2);

		}
		if (strategy.equals("AS1")) {
			sol = AStar.AStar(node,1);
		}
		if (strategy.equals("AS2")) {
			sol = AStar.AStar(node,2);

		}

		return sol;
	}

	public static void main(String[] args) {
		genGrid();

		String grid0 = "5,5;2;3,4;1,2;0,3,1,4;2,3;4,4,0,2,0,2,4,4;2,2,91,2,4,62";
		String grid1 = "5,5;1;1,4;1,0;0,4;0,0,2,2;3,4,4,2,4,2,3,4;0,2,32,0,1,38";
		String grid2 = "5,5;2;3,2;0,1;4,1;0,3;1,2,4,2,4,2,1,2,0,4,3,0,3,0,0,4;1,1,77,3,4,34";
		String grid3 = "5,5;1;0,4;4,4;0,3,1,4,2,1,3,0,4,1;4,0;2,4,3,4,3,4,2,4;0,2,98,1,2,98,2,2,98,3,2,98,4,2,98,2,0,1";
		String grid4 = "5,5;1;0,4;4,4;0,3,1,4,2,1,3,0,4,1;4,0;2,4,3,4,3,4,2,4;0,2,98,1,2,98,2,2,98,3,2,98,4,2,98,2,0,98,1,0,98";
		String grid5 = "5,5;2;0,4;3,4;3,1,1,1;2,3;3,0,0,1,0,1,3,0;4,2,54,4,0,85,1,0,43";
		String grid6 = "5,5;2;3,0;4,3;2,1,2,2,3,1,0,0,1,1,4,2,3,3,1,3,0,1;2,4,3,2,3,4,0,4;4,4,4,0,4,0,4,4;1,4,57,2,0,46";
		String grid7 = "5,5;3;1,3;4,0;0,1,3,2,4,3,2,4,0,4;3,4,3,0,4,2;1,4,1,2,1,2,1,4,0,3,1,0,1,0,0,3;4,4,45,3,3,12,0,2,88";
		String grid8 = "5,5;2;4,3;2,1;2,0,0,4,0,3,0,1;3,1,3,2;4,4,3,3,3,3,4,4;4,0,17,1,2,54,0,0,46,4,1,22";
		String grid9 = "5,5;2;0,4;1,4;0,1,1,1,2,1,3,1,3,3,3,4;1,0,2,4;0,3,4,3,4,3,0,3;0,0,30,3,0,80,4,4,80";
		String grid10 = "5,5;4;1,1;4,1;2,4,0,4,3,2,3,0,4,2,0,1,1,3,2,1;4,0,4,4,1,0;2,0,0,2,0,2,2,0;0,0,62,4,3,45,3,3,39,2,3,40";

		System.out.println(solve(grid4, "D", false));
	}

}