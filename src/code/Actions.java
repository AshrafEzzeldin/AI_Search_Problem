package code;

import java.util.Arrays;

import code.Matrix.Hostage;
import code.Matrix.Position;

public class Actions {

	static Node left(Node node) {

		byte x = node.x;
		byte y = node.y;
		byte[] host = node.host;
		int pill = node.pill;
		short time = node.time;
		byte[] ag = node.agents;
		StringBuilder path = new StringBuilder(node.path.toString());

		boolean f = y - 1 >= 0;
		for (int i = 0; i < Matrix.agents.length; i++) {
			if (Matrix.agents[i].x == x && Matrix.agents[i].y == y - 1 && ag[i] == 0)
				f = false;
		}
		for (int i = 0; i < host.length; i++) {
			if (Matrix.hostages[i].position.x == x && Matrix.hostages[i].position.y == y - 1
					&& (host[i] == 2 || Matrix.hostages[i].damage >= 98))
				f = false;
		}
		return f ? new Node(x, (byte) (y - 1), host.clone(), pill, (short) (time + 1), ag.clone(), node.damageNeo,
				path.append("left,")) : null;
	}

	static Node right(Node node) {

		byte x = node.x;
		byte y = node.y;
		byte[] host = node.host;
		int pill = node.pill;
		short time = node.time;
		byte[] ag = node.agents;
		StringBuilder path = new StringBuilder(node.path.toString());

		boolean f = y + 1 < Matrix.m;
		for (int i = 0; i < Matrix.agents.length; i++) {
			if (Matrix.agents[i].x == x && Matrix.agents[i].y == y + 1 && ag[i] == 0)
				f = false;
		}
		for (int i = 0; i < host.length; i++) {
			if (Matrix.hostages[i].position.x == x && Matrix.hostages[i].position.y == y + 1
					&& (host[i] == 2 || Matrix.hostages[i].damage >= 98))
				f = false;
		}
		return f ? new Node(x, (byte) (y + 1), host.clone(), pill, (short) (time + 1), ag.clone(), node.damageNeo,
				path.append("right,")) : null;
	}

	static Node down(Node node) {

		byte x = node.x;
		byte y = node.y;
		byte[] host = node.host;
		int pill = node.pill;
		short time = node.time;
		byte[] ag = node.agents;
		StringBuilder path = new StringBuilder(node.path.toString());

		boolean f = x + 1 < Matrix.n;
		for (int i = 0; i < Matrix.agents.length; i++) {
			if (Matrix.agents[i].x == x + 1 && Matrix.agents[i].y == y && ag[i] == 0)
				f = false;
		}
		for (int i = 0; i < host.length; i++) {
			if (Matrix.hostages[i].position.x == x + 1 && Matrix.hostages[i].position.y == y
					&& (host[i] == 2 || Matrix.hostages[i].damage >= 98))
				f = false;
		}
		return f ? new Node((byte) (x + 1), y, host.clone(), pill, (short) (time + 1), ag.clone(), node.damageNeo,
				path.append("down,")) : null;
	}

	static Node up(Node node) {

		byte x = node.x;
		byte y = node.y;
		byte[] host = node.host;
		int pill = node.pill;
		short time = node.time;
		byte[] ag = node.agents;
		StringBuilder path = new StringBuilder(node.path.toString());

		boolean f = x - 1 >= 0;
		for (int i = 0; i < Matrix.agents.length; i++) {
			if (Matrix.agents[i].x == x - 1 && Matrix.agents[i].y == y && ag[i] == 0)
				f = false;
		}
		for (int i = 0; i < host.length; i++) {
			if (Matrix.hostages[i].position.x == x - 1 && Matrix.hostages[i].position.y == y
					&& (host[i] == 2 || Matrix.hostages[i].damage >= 98))
				f = false;
		}
		return f ? new Node((byte) (x - 1), y, host.clone(), pill, (short) (time + 1), ag.clone(), node.damageNeo,
				path.append("up,")) : null;
	}

	static Node drop(Node node) {

		byte x = node.x;
		byte y = node.y;
		byte[] host = node.host;
		int pill = node.pill;
		short time = node.time;
		byte[] ag = node.agents;
		int numOfPills = Integer.bitCount(pill);
		StringBuilder path = new StringBuilder(node.path.toString());

		byte cnt = 0;
		byte[] new_host = host.clone();
		if (x == Matrix.Telephone.x && y == Matrix.Telephone.y) {
			for (int i = 0; i < host.length; i++) {
				int damage = Matrix.hostages[i].damage + 2 * (time) - numOfPills * 22;
				if (damage >= 100) {
					if (host[i] == 1)
						host[i] = 5;
				}
				if (host[i] == 1) {
					cnt++;
					new_host[i] = 3;
				}
				if (host[i] == 5) {
					cnt++;
					new_host[i] = 4;
				}
			}
		}

		return cnt == 0 ? null
				: new Node(x, y, new_host, pill, (short) (time + 1), ag.clone(), node.damageNeo, path.append("drop,"));

	}

	static Node carry(Node node) {

		byte x = node.x;
		byte y = node.y;
		byte[] host = node.host;
		int pill = node.pill;
		short time = node.time;
		byte[] ag = node.agents;
		StringBuilder path = new StringBuilder(node.path.toString());

		int cnt_carried_host = 0;
		for (byte temp : host)
			if (temp == 1 || temp == 5)
				cnt_carried_host++;
		byte[] new_host = host.clone();
		for (int i = 0; i < Matrix.hostages.length; i++) {
			Hostage h = Matrix.hostages[i];
			if (h.position.x == x && h.position.y == y && host[i] == 0 && cnt_carried_host < Matrix.c) {
				new_host[i] = 1;
				return new Node(x, y, new_host, pill, (short) (time + 1), ag.clone(), node.damageNeo,
						path.append("carry,"));

			}
		}

		return null;

	}

	static Node takePill(Node node) {

		byte x = node.x;
		byte y = node.y;
		byte[] host = node.host;
		int pill = node.pill;
		short time = node.time;
		byte[] ag = node.agents;
		StringBuilder path = new StringBuilder(node.path.toString());

		for (int i = 0; i < Matrix.pills.length; i++) {
			if (Matrix.pills[i].x == x && Matrix.pills[i].y == y && ((pill >> i) & 1) == 0) {
				pill |= (1 << i);
				return new Node(x, y, host.clone(), pill, (short) (time + 1), ag.clone(),
						(byte) Math.max(0, node.damageNeo - 20), path.append("takePill,"));
			}
		}
		return null;

	}

	static Node fly(Node node) {

		byte x = node.x;
		byte y = node.y;
		byte[] host = node.host;
		int pill = node.pill;
		short time = node.time;
		byte[] ag = node.agents;
		StringBuilder path = new StringBuilder(node.path.toString());

		for (int i = 0; i < Matrix.pads.length; i++) {
			if (Matrix.pads[i].x == x && Matrix.pads[i].y == y) {
				x = (byte) Matrix.pads[(i + Matrix.pads.length / 2) % Matrix.pads.length].x;
				y = (byte) Matrix.pads[(i + Matrix.pads.length / 2) % Matrix.pads.length].y;
				return new Node(x, y, host.clone(), pill, (short) (time + 1), ag.clone(), node.damageNeo,
						path.append("fly,"));
			}
		}

		return null;
	}

	static Node kill(Node node) { // number of kills to know neo's damage.
		byte x = node.x;
		byte y = node.y;
		byte[] host = node.host;
		int pill = node.pill;
		short time = node.time;
		byte[] ag = node.agents;
		StringBuilder path = new StringBuilder(node.path.toString());
		int numOfPills = Integer.bitCount(pill);

		byte[] new_host = host.clone();
		byte[] new_ag = ag.clone();

		int cnt = 0;
		boolean illegal = false;
		for (int i = 0; i < Matrix.hostages.length; i++) {
			Hostage h = Matrix.hostages[i];
			int damage = h.damage + 2 * time - numOfPills * 22;
			if (neighbour(x, y, h.position) && host[i] == 2) {
				new_host[i] = 6;
				cnt++;
			}
			if (h.position.x == x && h.position.y == y && (host[i] == 0 && damage >= 98) && host[i] == 0)
				illegal = true;
		}

		for (int i = 0; i < Matrix.agents.length; i++) {
			if (neighbour(x, y, Matrix.agents[i]) && ag[i] == 0) {
				new_ag[i] = 1;
				cnt++;
			}
		}
		if (node.damageNeo >= 100)
			cnt = 0;
		if (illegal)
			cnt = 0;

//		System.out.println(cnt + " " + Arrays.toString(host));
		return cnt == 0 ? null
				: new Node(x, y, new_host, pill, (short) (time + 1), new_ag, (byte) (node.damageNeo + 20),
						path.append("kill,"));
	}

	static boolean neighbour(int x, int y, Position p) {
		if ((y == p.y && (x == p.x - 1 || x == p.x + 1)) || (x == p.x && (y == p.y - 1 || y == p.y + 1)))
			return true;
		return false;
	}

}
