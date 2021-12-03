package code;

import java.util.Arrays;

import code.Matrix.Hostage;
import code.Matrix.Position;

public class Actions {

	static Node left(Node node) {

		byte x = node.x;
		byte y = node.y;
		byte[] host = node.host.clone();
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
					&& (host[i] == 2 || (node.host_damage[i] >= 98 && host[i] == 0)))
				f = false;
		}

		if (f) {
			byte[] host_damage = update_hostageDamage(node.host_damage, (byte) 2);
			Node ret = new Node(x, (byte) (y - 1), host, pill, (short) (time + 1), ag.clone(), node.damageNeo,
					path.append("left,"), host_damage);
			return Matrix.updateNode(ret);
		}
		return null;
	}

	static Node right(Node node) {

		byte x = node.x;
		byte y = node.y;
		byte[] host = node.host.clone();
		int pill = node.pill;
		short time = node.time;
		byte[] ag = node.agents;
		StringBuilder path = new StringBuilder(node.path.toString());

		boolean f = (y + 1) < Matrix.m;
		for (int i = 0; i < Matrix.agents.length; i++) {
			if (Matrix.agents[i].x == x && Matrix.agents[i].y == y + 1 && ag[i] == 0)
				f = false;
		}
		for (int i = 0; i < host.length; i++) {
			if (Matrix.hostages[i].position.x == x && Matrix.hostages[i].position.y == y + 1
					&& (host[i] == 2 || (node.host_damage[i] >= 98 && host[i] == 0)))
				f = false;
		}
		if (f) {
			byte[] host_damage = update_hostageDamage(node.host_damage, (byte) 2);
			Node ret = new Node(x, (byte) (y + 1), host, pill, (short) (time + 1), ag.clone(), node.damageNeo,
					path.append("right,"), host_damage);
			return Matrix.updateNode(ret);
		}
		return null;
	}

	static Node down(Node node) {

		byte x = node.x;
		byte y = node.y;
		byte[] host = node.host.clone();
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
					&& (host[i] == 2 || (node.host_damage[i] >= 98 && host[i] == 0)))
				f = false;
		}

		if (f) {
			byte[] host_damage = update_hostageDamage(node.host_damage, (byte) 2);
			Node ret = new Node((byte) (x + 1), y, host, pill, (short) (time + 1), ag.clone(), node.damageNeo,
					path.append("down,"), host_damage);
			return Matrix.updateNode(ret);
		}
		return null;

	}

	static Node up(Node node) {

		byte x = node.x;
		byte y = node.y;
		byte[] host = node.host.clone();
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
					&& (host[i] == 2 || (node.host_damage[i] >= 98 && host[i] == 0)))
				f = false;
		}

		if (f) {
			byte[] host_damage = update_hostageDamage(node.host_damage, (byte) 2);
			Node ret = new Node((byte) (x - 1), y, host, pill, (short) (time + 1), ag.clone(), node.damageNeo,
					path.append("up,"), host_damage);
			return Matrix.updateNode(ret);
		}
		return null;

	}

	static Node drop(Node node) {

		byte x = node.x;
		byte y = node.y;
		byte[] host = node.host.clone();
		int pill = node.pill;
		short time = node.time;
		byte[] ag = node.agents;
		int numOfPills = Integer.bitCount(pill);
		StringBuilder path = new StringBuilder(node.path.toString());

		boolean f = false;
		byte[] new_host = host.clone();
		if (x == Matrix.Telephone.x && y == Matrix.Telephone.y) {
			for (int i = 0; i < host.length; i++) {
				int damage = node.host_damage[i];
				if (damage >= 100) {
					if (host[i] == 1)
						host[i] = 5;
				}
				if (host[i] == 1) {
					f = true;
					new_host[i] = 3;
				}
				if (host[i] == 5) {
					f = true;
					new_host[i] = 4;
				}
			}
		}

		if (f) {
			byte[] host_damage = update_hostageDamage(node.host_damage, (byte) 2);
			Node ret = new Node(x, y, new_host, pill, (short) (time + 1), ag.clone(), node.damageNeo,
					path.append("drop,"), host_damage);
			return Matrix.updateNode(ret);
		}
		return null;


	}

	static Node carry(Node node) {

		byte x = node.x;
		byte y = node.y;
		byte[] host = node.host.clone();
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
				byte[] host_damage = update_hostageDamage(node.host_damage, (byte) 2);
				Node ret = new Node(x, y, new_host, pill, (short) (time + 1), ag.clone(), node.damageNeo,
						path.append("carry,"), host_damage);
				return Matrix.updateNode(ret);

			}
		}

		return null;

	}

	static Node takePill(Node node) {

		byte x = node.x;
		byte y = node.y;
		byte[] host = node.host.clone();
		int pill = node.pill;
		short time = node.time;
		byte[] ag = node.agents;
		StringBuilder path = new StringBuilder(node.path.toString());
		byte[] host_damage = node.host_damage;

		for (int i = 0; i < Matrix.pills.length; i++) {
			if (Matrix.pills[i].x == x && Matrix.pills[i].y == y && ((pill >> i) & 1) == 0) {
				pill |= (1 << i);
				host_damage = update_hostageDamage(host_damage, (byte) -20);
				Node ret = new Node(x, y, host, pill, (short) (time + 1), ag.clone(),
						(byte) Math.max(0, node.damageNeo - 20), path.append("takePill,"), host_damage);
				return Matrix.updateNode(ret);
			}
		}
		return null;

	}

	static Node fly(Node node) {

		byte x = node.x;
		byte y = node.y;
		byte[] host = node.host.clone();
		int pill = node.pill;
		short time = node.time;
		byte[] ag = node.agents;
		StringBuilder path = new StringBuilder(node.path.toString());

		for (int i = 0; i < Matrix.pads.length; i++) {
			if (Matrix.pads[i].x == x && Matrix.pads[i].y == y) {
				x = (byte) Matrix.pads[(i + Matrix.pads.length / 2) % Matrix.pads.length].x;
				y = (byte) Matrix.pads[(i + Matrix.pads.length / 2) % Matrix.pads.length].y;
				byte[] host_damage = update_hostageDamage(node.host_damage, (byte) 2);
				Node ret = new Node(x, y, host, pill, (short) (time + 1), ag.clone(), node.damageNeo, path.append("fly,"),
						host_damage);
				return Matrix.updateNode(ret);
			}
		}

		return null;
	}

	static Node kill(Node node) { // number of kills to know neo's damage.
		byte x = node.x;
		byte y = node.y;
		byte[] host = node.host.clone();
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
			int damage = node.host_damage[i];
			if (neighbour(x, y, h.position) && host[i] == 2) {
				new_host[i] = 6;
				cnt++;
			}
			if (h.position.x == x && h.position.y == y && host[i] == 0 && damage >= 98)
				illegal = true;
		}

		for (int i = 0; i < Matrix.agents.length; i++) {
			if (neighbour(x, y, Matrix.agents[i]) && ag[i] == 0) {
				new_ag[i] = 1;
				cnt++;
			}
		}
		if (node.damageNeo + 20 >= 100 || illegal)
			cnt = 0;
		
		if (cnt!=0) {
			byte[] host_damage = update_hostageDamage(node.host_damage, (byte) 2);
			Node ret = new Node(x, y, new_host, pill, (short) (time + 1), new_ag, (byte) (node.damageNeo + 20),
					path.append("kill,"), host_damage);
			return Matrix.updateNode(ret);
		}
		return null;
	}

	static boolean neighbour(int x, int y, Position p) {
		if ((y == p.y && (x == p.x - 1 || x == p.x + 1)) || (x == p.x && (y == p.y - 1 || y == p.y + 1)))
			return true;
		return false;
	}

	static byte[] update_hostageDamage(byte[] hd, byte val) {
		byte[] ret = new byte[hd.length];
		for (int i = 0; i < ret.length; i++) {
			if (hd[i] < 100)
				ret[i] = (byte) Math.max(0, hd[i] + val);
		}
		return ret;
	}

}
