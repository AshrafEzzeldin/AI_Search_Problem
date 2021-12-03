package code;

import java.util.*;


public class Matrix extends General_Search {

	//num of rows, cols, carries, and number of states
	static int n, m, c, cnt_states;
	//giving id for each pad
	static int[][] padsIDs;
	// for getting random number
	static Random Rand;
	//the taken positions form generating a grid
	static boolean[][] Taken;
	// info about hostages
	static Hostage[] hostages;
	// locations of pills
	static Position[] pills;
	//location of Neo and telephone booth
	static Position Neo, Telephone;
	//location of pads and agents 
	static Position[] pads, agents;
	// the visited states for searching
	static HashSet<String> vis;

	

	//get random number between[l,r]
	static int rand(int l, int r) {
		return Rand.nextInt((r - l + 1)) + l;
	}

	//class position for location
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
	
	//class hostage to know the position and damage of hostage
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

	
	//get random position not taken before
	static Position GetRandomPosition() { 

		ArrayList<Position> Rem = new ArrayList<>();

		for (int i = 0; i < n; i++)
			for (int j = 0; j < m; j++)
				if (!Taken[i][j])
					Rem.add(new Position(i, j));

		Position pos = Rem.get(rand(0, Rem.size() - 1));

		Taken[pos.x][pos.y] = true;

		return pos;
	}

	//generate random grid
	static void genGrid() {
		Rand = new Random();

		n = rand(5, 15);
		m = rand(5, 15);
		c = rand(1, 4);
				
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
		
		// you can remove the 3 if you want no limit 
		int Pads = rand(0,Math.min(AllRemaning / 2,3));
		AllRemaning -= 2 * Pads;
		
		// you can remove the 20 if you want no limit 
		int Agents = rand(0, Math.min(AllRemaning ,20));

		pads = new Position[2 * Pads];
		padsIDs = new int[n][m];
		for (int i = 0; i < n; i++) {
			Arrays.fill(padsIDs[i], -1);
		}
		agents = new Position[Agents];

		for (int i = 0; i < Agents; i++)
			agents[i] = GetRandomPosition();

		for (int i = 0; i < Pads; i++) {
			pads[i] = GetRandomPosition();
			pads[i + Pads] = GetRandomPosition();
			padsIDs[pads[i].x][pads[i].y] = i;
			padsIDs[pads[(i + pads.length / 2)].x][pads[(i + pads.length / 2)].y] = (i + pads.length / 2);
		}
	}
	
	//parse string into INT
	static int num(String s) {
		return Integer.parseInt(s);
	}

	//parse the input string grid into arrays
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
		padsIDs = new int[n][m];
		for (int i = 0; i < n; i++) {
			Arrays.fill(padsIDs[i], -1);
		}

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

			pads[(i + pads.length / 2)] = new Position(num(padloc[i * 4 + 2]), num(padloc[i * 4 + 3]));
			padsIDs[pads[i].x][pads[i].y] = i;
			padsIDs[pads[(i + pads.length / 2)].x][pads[(i + pads.length / 2)].y] = (i + pads.length / 2);
		}
		hostages = new Hostage[hostTemp.length / 3];
		for (int i = 0; i < hostages.length; i++) {
			hostages[i] = new Hostage(new Position(num(hostTemp[i * 3]), num(hostTemp[i * 3 + 1])),
					num(hostTemp[i * 3 + 2]));
		}
	}

	// make array of hostages damage 
	static byte[] find_host_damage() {
		byte[] host_damage = new byte[hostages.length];
		for (int i = 0; i < hostages.length; i++) {
			host_damage[i] = (byte) hostages[i].damage;

		}
		return host_damage;
	}

	
	// solve method to search for grid
	public static String solve(String grid, String strategy, boolean visualize) {

		parse(grid);
		cnt_states = 0;
		byte[] host_damage = find_host_damage();

		Node node = new Node((byte) Neo.x, (byte) Neo.y, new byte[hostages.length], 0, (short) 0,
				new byte[agents.length], (byte) 0, new StringBuilder(), host_damage);

		memo = new HashMap<>();

		System.out.println();

		String sol = new Matrix().general_search(node, strategy);
		if (visualize) {
			visualize(sol);
		}
		return sol;

	}
	

	// just for testing gneGrid()
	public static String test(String strategy) {

		genGrid();
		System.out.println(n+" "+m+" "+c);
		System.out.println(pads.length);
		System.out.println(hostages.length);
		System.out.println(Arrays.deepToString(padsIDs));

		cnt_states = 0;
		byte[] host_damage = find_host_damage();

		Node node = new Node((byte) Neo.x, (byte) Neo.y, new byte[hostages.length], 0, (short) 0,
				new byte[agents.length], (byte) 0, new StringBuilder(), host_damage);

		memo = new HashMap<>();

		System.out.println();

		String sol = new Matrix().general_search(node, strategy);
		return sol;

	}
	
	//visualize the grid and the path to solution
	static void visualize(String solution) {
		String grid[][] = new String[n][m];
		String[] path = (solution.split(";"))[0].split(",");
		for (int i = 0; i < grid.length; i++) {
			Arrays.fill(grid[i], "");
		}
		int pad = 1;
		for (int k = 0; k < pads.length / 2; k++) {
			if (grid[pads[k].x][pads[k].y].length() == 0) {
				grid[pads[k].x][pads[k].y] = "Pad " + pad + ": ";
				grid[pads[k + pads.length / 2].x][pads[k + pads.length / 2].y] = "Pad" + (pad++) + ": ";
			}
		}
		grid[Telephone.x][Telephone.y] = "Telephone booth ";
		int i = Neo.x;
		int j = Neo.y;
		int cnt = 0;
		grid[i][j] = "Start " + (cnt++) + " ";
		for (Position p : pills) {
			grid[p.x][p.y] = "Pill ";
		}
		for (Position p : agents) {
			grid[p.x][p.y] = "Agent ";
		}
		for (Hostage H : hostages) {
			grid[H.position.x][H.position.y] = "Hostage " + H.damage + ": ";
		}

		for (String action : path) {
			switch (action) {
	
			case "up":
				grid[--i][j] +=  "("+(cnt++) + ") ";
				break;
	
			case "down":
				grid[++i][j] +=  "("+(cnt++) + ") ";
				break;
	
			case "right":
				grid[i][++j] += "("+(cnt++) + ") ";
				break;
	
			case "left":
				grid[i][--j] += "("+(cnt++) + ") ";
				break;
	
			case "carry":
				grid[i][j] = "Carried " + grid[i][j];
				break;
	
			case "drop":
				grid[i][j] += "Drop ";
				break;
	
			case "fly":
				int id = padsIDs[i][j];
				int to = (id + (pads.length / 2)) % pads.length;
				i = pads[to].x;
				j = pads[to].y;
				grid[i][j] += (cnt++) + " ";
				break;
				
			case "takePill":
				grid[i][j] += "take pill ";
				break;

			default:
				break;

			}
		}

		i = Neo.x;
		j = Neo.y;

		for (String action : path) {
			switch (action) {

			case "up":
				i--;
				break;
			
			case "down":
				i++;
				break;

			case "right":
				j++;
				break;

			case "left":
				j--;
				break;

			case "fly":
				int id = padsIDs[i][j];
				int to = (id + (pads.length / 2)) % pads.length;
				i = pads[to].x;
				j = pads[to].y;
				break;

			case "kill":
				for (int x : dx) {
					for (int y : dy) {
						if (valid(i + x, j + y) && !grid[i + x][j + y].contains("Killed") && (grid[i + x][j + y]
								.contains("Agent")
								|| (grid[i + x][j + y].contains("Hostage") && !grid[i + x][j + y].contains("Carry")))) {
							grid[i + x][j + y] = "Killed " + grid[i + x][j + y];
						}
					}
				}
				break;

			default:
				break;

			}
		}

		System.out.println(Arrays.deepToString(grid)
                .replace("],", "\n").replace(",", "\t---> \t")
                .replaceAll("[\\[\\]]", " ")+"\n");

	}

	
	//put the node as visited used in search
	static boolean vis(Node n) {
		if (n.time >= 400)
			return true;
		
		String visited=Node.hashState(n);
		if (!vis.contains(visited.toString())) {
			vis.add(visited.toString());
			return false;
		}
		return true;
	}

	//update the node after generating so the agents, hostages, ... are updated accordingly
	static Node updateNode(Node node) {
		if (node == null)
			return null;
		byte x = node.x;
		byte y = node.y;
		byte[] host = node.host.clone();
		int pill = node.pill;
		short time = node.time;
		byte[] ag = node.agents.clone();
		byte damageNeo = node.damageNeo;
		StringBuilder path = new StringBuilder(node.path.toString());
		byte[] host_damage = node.host_damage.clone();

		int deaths = 0;
		int agents_killed = 0;

		boolean gameover = Telephone.x == x && Telephone.y == y && damageNeo < 100;
		for (int i = 0; i < hostages.length; i++) {
			int damage = host_damage[i];
			if (damage >= 100) {
				if (host[i] == 1)
					host[i] = 5;
				if (host[i] == 0)
					host[i] = 2;
			}
			if (host[i] > 3 || host[i]==2)
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

		return new Node(x, y, host, pill, time, ag, damageNeo, path, deaths, agents_killed,
				deaths * 4000 + (agents_killed)*200, gameover, host_damage);

	}

	//build the output
	static String gameover(Node node) {
		return new StringBuilder(node.path.substring(0, node.path.length() - 1)) + ";" + node.deaths + ";" + node.agents_killed + ";"
				+ cnt_states;
	}

	//the method of the abstract class General_Search which doing search
	public String general_search(Node node, String strategy) {

		vis = new HashSet<String>();
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
			sol = GR.gr(node,(byte) 1);

		}
		if (strategy.equals("GR2")) {
			sol = GR.gr(node,(byte) 2);

		}
		if (strategy.equals("AS1")) {
			sol = AStar.AStar(node, (byte)1);
		}
		if (strategy.equals("AS2")) {
			sol = AStar.AStar(node, (byte)2);
		}

		return sol;
	}


	//check the validity of a location 
	static boolean valid(int x, int y) {
		return x >= 0 && x < n && y >= 0 && y < m;
	}
	
	// variables used in dp
	static int[] dx = new int[] { -1, 0, 1, 0 };
	static int[] dy = new int[] { 0, -1, 0, 1 };
	static HashMap<String, Integer> memo;

	// calculate number of deaths and kills for heuristic1
	static int dp(int x, int y, int mskStatus, int mskCarry, int time) {
		// base case;
		if (time >= 165)
			return 0;

		int carried = Integer.bitCount(mskCarry);
		mskStatus = updateMSK(mskStatus, mskCarry, time, pills.length);

		if (Integer.bitCount(mskStatus) == hostages.length && mskCarry == 0)
			return 0;
		String state = Hash(x, y, mskStatus, mskCarry, time);
		if (memo.containsKey(state))
			return memo.get(state);

		int cnt = 0;
		for (int i = 0; i < 4; i++) {
			if (valid(x + dx[i], y + dy[i])) {
				cnt = Math.max(cnt, dp(x + dx[i], y + dy[i], mskStatus, mskCarry, time + 1));
			}
		}

		for (int i = 0; i < hostages.length && carried < c; i++) {
			if (!isSet(i, mskStatus)
					&& (!isSet(i, mskCarry) && hostages[i].position.x == x && hostages[i].position.y == y)) {
				int temp = dp(x, y, mskStatus, mskCarry | (1 << i), time + 1);
				cnt = Math.max(cnt, temp);
			}

		}

		if (padsIDs[x][y] != -1) {
			int id = padsIDs[x][y];
			int to = (id + (pads.length / 2)) % pads.length;
			int new_x = pads[to].x;
			int new_y = pads[to].y;
			if (x == new_x && y == new_y)
				System.out.println("why");
			cnt = Math.max(cnt, dp(new_x, new_y, mskStatus, mskCarry, time + 1));

		}

		if (x == Telephone.x && y == Telephone.y) {
			mskCarry &= (~mskStatus);
			int temp = Integer.bitCount(mskCarry) + dp(x, y, mskStatus | mskCarry, 0, time + 1);
			cnt = Math.max(cnt, temp);
		}
		memo.put(state, cnt);
		return cnt;
	}

	//update the state of hostages in dp 
	static int updateMSK(int mskStatus, int mskCarry, int time, int takenPills) {
		for (int i = 0; i < hostages.length; i++) {
			int h = hostages[i].damage + time * 2 - takenPills * 22;
			if (h >= 100) {
				mskStatus |= (1 << i);
			}
		}
		return mskStatus;
	}

	//hashing the state of dp into string
	static String Hash(int x, int y, int mskStatus, int mskCarry, int time) {

		StringBuilder ret = new StringBuilder();

		ret.append(x);
		ret.append('|');
		ret.append(y);
		ret.append('|');
		ret.append(mskStatus);
		ret.append('|');
		ret.append(mskCarry);
		ret.append('|');
		ret.append(time);
		return ret.toString();
	}

	//check if bit i is 1 in mask msk 
	static boolean isSet(int i, int msk) {

		return ((1 << i) & msk) != 0;
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

		System.out.println(solve(grid0, "AS2", false));
		System.out.println(solve(grid1, "AS2", false));
		System.out.println(solve(grid2, "AS2", false));
		System.out.println(solve(grid3, "AS2", false));
		System.out.println(solve(grid4, "AS2", false));
		System.out.println(solve(grid5, "AS2", false));
		System.out.println(solve(grid6, "AS2", false));
		System.out.println(solve(grid7, "AS2", false));
		System.out.println(solve(grid8, "AS2", false));
		System.out.println(solve(grid9, "AS2", false));
		System.out.println(solve(grid10, "AS2", false));
		
//		System.out.println(test("UC"));
		
	}

}