package code;

public class Node implements Comparable<Node> {

	byte x, y, damageNeo, agents[]; // pos of neo
	byte[] host; // 0 means alive, 1 means carried, 2 means agent, 3 means dropped alive ,
					// 4 dropped killed, 5 carried killed ,6 means killed after being agent
	int pill; // 1 taken, 0 not_taken
	short time;
	StringBuilder path = new StringBuilder();

	public Node(byte x, byte y, byte[] host, int pill, short time, byte[] agents, byte damage, StringBuilder path) {
		this.x = x;
		this.y = y;
		this.host = host;
		this.pill = pill;
		this.time = time;
		this.agents = agents;
		this.damageNeo = damage;
		this.path = path;
	}

	@Override
	public int compareTo(Node n) {
		int deathsme = 0, killsme = 0;
		for (int h : n.host) {
			if (h > 3)
				deathsme++;
			if (h == 6)
				killsme++;
		}
		
		int deaths = 0, kills = 0;
		for (int h : this.host) {
			if (h > 3)
				deaths++;
			if (h == 6)
				kills++;
		}
		
		if(kills==killsme) {
			return deaths-deathsme;
		}
		return kills-killsme; 
	}

}