package code;

import java.util.ArrayList;
import java.util.Arrays;
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
	static boolean vis[][][][][][];

	static int rand(int l, int r) {

		return Rand.nextInt((r - l + 1)) + l;
	}

	static class Position {
		int x, y;

		public Position(int a, int b) {
			x = a;
			y = b;
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
		c=num(split[1]);
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
			pads[i] = new Position(num(padloc[i * 2]), num(padloc[i * 2 + 1]));
			pads[(i + pads.length / 2)] = new Position(num(padloc[(i + pads.length / 2) * 2]),
					num(padloc[(i + pads.length / 2) * 2 + 1]));
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

		byte killed = 0, lifted = 0, drops = 0, hostAg = 0;
		for (byte h : n.host) {
			if (h == 1 || h == 5)
				lifted++;
			if (h > 3)
				killed++;
			if (h == 3 || h == 4)
				drops++;
			if (h == 2)
				hostAg++;
		}

		boolean f = vis[n.x][n.y][killed][lifted][drops][hostAg];
		if (!f) {
			vis[n.x][n.y][killed][lifted][drops][hostAg] = true;
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

		boolean gameover = true;
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

		vis = new boolean[Matrix.n][Matrix.m][Matrix.hostages.length + 1][Matrix.c + 1][Matrix.hostages.length
				+ 1][Matrix.hostages.length + 1];
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

		}
		if (strategy.equals("GR2")) {

		}
		if (strategy.equals("AS1")) {

		}
		if (strategy.equals("AS2")) {

		}

		return sol;
	}

	public static void main(String[] args) {
		genGrid();
		
		String grid0 = "5,5;2;4,3;2,1;2,0,0,4,0,3,0,1;3,1,3,2;4,4,3,3,3,3,4,4;4,0,17,1,2,54,0,0,46,4,1,22";
		String grid6 = "6,6;2;2,2;2,4;0,1,1,0,3,0,4,1,4,3,3,4,1,4,0,3;0,2;1,3,4,2,4,2,1,3;0,0,2,0,4,2,4,0,2,4,4,98,1,1,98";

		System.out.println(solve(grid6, "DF", false));
	}

}