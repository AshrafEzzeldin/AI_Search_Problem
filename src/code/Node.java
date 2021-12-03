package code;

public class Node implements Comparable<Node> {

	// location of Neo and his damage and if agent is alive or killed
	byte x, y, damageNeo, agents[];
	// 0 means alive, 1 means carried, 2 means agent, 3 means dropped alive ,
	// 4 dropped dead, 5 carried dead ,6 means killed after being agent
	byte[] host;
	// the damage of hostages
	byte[] host_damage;
	// mask for pills, 1 taken, 0 not_taken
	int pill;
	// calculate the depth
	short time;
	// number of deaths, kills and the cost for (UC greedy and AStart)
	int deaths, agents_killed, cost;
	// the path till the goal
	StringBuilder path = new StringBuilder();
	// check if we reach a goal
	boolean gameover;

	// constructor without deaths, agents_killed, cost and gameover
	public Node(byte x, byte y, byte[] host, int pill, short time, byte[] agents, byte damage, StringBuilder path,
			byte[] hd) {
		this.x = x;
		this.y = y;
		this.host = host;
		this.pill = pill;
		this.time = time;
		this.agents = agents;
		this.damageNeo = damage;
		this.path = path;
		this.host_damage = hd;
	}

	// constructor with deaths, agents_killed, cost and gameover
	public Node(byte x, byte y, byte[] host, int pill, short time, byte[] agents, byte damage, StringBuilder path,
			int d, int k, int c, boolean g, byte[] hd) {
		this.x = x;
		this.y = y;
		this.host = host;
		this.pill = pill;
		this.time = time;
		this.agents = agents;
		this.damageNeo = damage;
		this.path = path;
		this.deaths = d;
		this.agents_killed = k;
		this.cost = c;
		this.gameover = g;
		this.host_damage = hd;
	}

	// hashing the state for visited hashset (searching)
	public static String hashState(Node n) {
		StringBuilder visited = new StringBuilder();
		visited.append("" + n.x + "|" + n.y + "|" + n.damageNeo + "|");

		for (byte a : n.agents) {
			visited.append(a + "|");
		}

		for (byte h : n.host) {
			visited.append(h + "|");
		}
		visited.append("" + n.pill + "|");

		return visited.toString();
	}

	// comparing for the cost
	public int compareTo(Node n) {

		return cost - n.cost;
	}

}
