package code;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

public class DFS {

	static int time = -1;

	static String dfs(Node n) {
		
//		System.out.println(Arrays.toString(n.host));
		Matrix.cnt_states++;
		boolean gameover = Matrix.nextStep(n);

		if (gameover)
			return Matrix.gameover(n) ;

		if (time == n.time)
			return "No Solution";

		String ret = "No Solution";

		Node takePill = Actions.takePill(n);
		if (takePill != null) {
//			System.out.println("take pill");
			ret = dfs(takePill);
			if (!ret.equals("No Solution"))
				return ret;

		}

		Node carry = Actions.carry(n);
		if (carry != null) {
//			System.out.println("carry");
			ret = dfs(carry);
			if (!ret.equals("No Solution"))
				return ret;
		}
		
		Node drop = Actions.drop(n);
		if (drop != null) {
			ret = dfs(drop);
			if (!ret.equals("No Solution"))
				return ret;
//			System.out.println("drop");
		}

		
		Node up = Actions.up(n);
		if (up != null && !Matrix.vis(up))
			ret = dfs(up);
		if (!ret.equals("No Solution"))
			return ret;

		Node down = Actions.down(n);
		if (down != null && !Matrix.vis(down))
			ret = dfs(down);
		if (!ret.equals("No Solution"))
			return ret;

		Node left = Actions.left(n);
		if (left != null && !Matrix.vis(left))
			ret = dfs(left);
		if (!ret.equals("No Solution"))
			return ret;

		Node right = Actions.right(n);
		if (right != null && !Matrix.vis(right))
			ret = dfs(right);
		if (!ret.equals("No Solution"))
			return ret;


		Node fly = Actions.fly(n);
		if (fly != null && !Matrix.vis(fly))
			ret = dfs(fly);
		if (!ret.equals("No Solution"))
			return ret;
		
		Node kill = Actions.kill(n);
		if (kill != null) {
//			System.out.println("kill");
			ret = dfs(kill);
			if (!ret.equals("No Solution"))
				return ret;
			
		}
		
		return ret;

	}

}
