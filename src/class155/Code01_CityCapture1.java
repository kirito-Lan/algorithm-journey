package class155;

// 城池攻占，java版
// 输入保证，如果城市a管辖城市b，必有a < b
// 测试链接 : https://www.luogu.com.cn/problem/P3261
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例

import java.io.IOException;
import java.io.PrintWriter;

public class Code01_CityCapture1 {

	public static int MAXN = 300001;

	public static int n, m;

	public static long[] defend = new long[MAXN];

	public static int[] belong = new int[MAXN];

	public static int[] op = new int[MAXN];

	public static long[] gain = new long[MAXN];

	public static int[] deep = new int[MAXN];

	public static int[] top = new int[MAXN];

	public static int[] sacrifice = new int[MAXN];

	public static long[] attack = new long[MAXN];

	public static int[] born = new int[MAXN];

	public static int[] die = new int[MAXN];

	public static int[] left = new int[MAXN];

	public static int[] right = new int[MAXN];

	public static int[] dist = new int[MAXN];

	public static long[] mul = new long[MAXN];

	public static long[] add = new long[MAXN];

	public static void prepare() {
		dist[0] = -1;
		for (int i = 1; i <= m; i++) {
			left[i] = right[i] = dist[i] = 0;
			mul[i] = 1;
			add[i] = 0;
		}
		for (int i = 1; i <= n; i++) {
			sacrifice[i] = top[i] = 0;
		}
	}

	public static void down(int i) {
		if (mul[i] != 1 || add[i] != 0) {
			int l = left[i];
			int r = right[i];
			if (l != 0) {
				attack[l] = attack[l] * mul[i] + add[i];
				mul[l] *= mul[i];
				add[l] = add[l] * mul[i] + add[i];
			}
			if (r != 0) {
				attack[r] = attack[r] * mul[i] + add[i];
				mul[r] *= mul[i];
				add[r] = add[r] * mul[i] + add[i];
			}
			mul[i] = 1;
			add[i] = 0;
		}
	}

	public static int merge(int i, int j) {
		if (i == 0 || j == 0) {
			return i + j;
		}
		int tmp;
		if (attack[i] > attack[j]) {
			tmp = i;
			i = j;
			j = tmp;
		}
		down(i);
		right[i] = merge(right[i], j);
		if (dist[left[i]] < dist[right[i]]) {
			tmp = left[i];
			left[i] = right[i];
			right[i] = tmp;
		}
		dist[i] = dist[right[i]] + 1;
		return i;
	}

	public static int pop(int i) {
		down(i);
		int ans = merge(left[i], right[i]);
		left[i] = right[i] = dist[i] = 0;
		return ans;
	}

	public static void upgrade(int i, int t, long v) {
		if (t == 0) {
			add[i] += v;
			attack[i] += v;
		} else {
			mul[i] *= v;
			add[i] *= v;
			attack[i] *= v;
		}
	}

	public static void compute() {
		deep[1] = 1;
		for (int i = 2; i <= n; i++) {
			deep[i] = deep[belong[i]] + 1;
		}
		for (int i = 1; i <= m; i++) {
			if (top[born[i]] == 0) {
				top[born[i]] = i;
			} else {
				top[born[i]] = merge(top[born[i]], i);
			}
		}
		for (int i = n; i >= 1; i--) {
			while (top[i] != 0 && attack[top[i]] < defend[i]) {
				die[top[i]] = i;
				sacrifice[i]++;
				top[i] = pop(top[i]);
			}
			if (top[i] != 0) {
				upgrade(top[i], op[i], gain[i]);
				if (top[belong[i]] == 0) {
					top[belong[i]] = top[i];
				} else {
					top[belong[i]] = merge(top[belong[i]], top[i]);
				}
			}
		}
	}

	public static void main(String[] args) {
		ReaderWriter io = new ReaderWriter();
		n = io.nextInt();
		m = io.nextInt();
		prepare();
		for (int i = 1; i <= n; i++) {
			defend[i] = io.nextLong();
		}
		for (int i = 2; i <= n; i++) {
			belong[i] = io.nextInt();
			op[i] = io.nextInt();
			gain[i] = io.nextLong();
		}
		for (int i = 1; i <= m; i++) {
			attack[i] = io.nextLong();
			born[i] = io.nextInt();
		}
		compute();
		for (int i = 1; i <= n; i++) {
			io.println(sacrifice[i]);
		}
		for (int i = 1; i <= m; i++) {
			io.println(deep[born[i]] - deep[die[i]]);
		}
		io.flush();
		io.close();
	}

	// 读写工具类
	public static class ReaderWriter extends PrintWriter {
		byte[] buf = new byte[1 << 16];
		int bId = 0, bSize = 0;
		boolean eof = false;

		public ReaderWriter() {
			super(System.out);
		}

		private byte getByte() {
			if (bId >= bSize) {
				try {
					bSize = System.in.read(buf);
				} catch (IOException e) {
					e.printStackTrace();
				}
				if (bSize == -1)
					eof = true;
				bId = 0;
			}
			return buf[bId++];
		}

		byte c;

		public boolean hasNext() {
			if (eof)
				return false;
			while ((c = getByte()) <= 32 && !eof)
				;
			if (eof)
				return false;
			bId--;
			return true;
		}

		public String next() {
			if (!hasNext())
				return null;
			byte c = getByte();
			while (c <= 32)
				c = getByte();
			StringBuilder sb = new StringBuilder();
			while (c > 32) {
				sb.append((char) c);
				c = getByte();
			}
			return sb.toString();
		}

		public int nextInt() {
			if (!hasNext())
				return Integer.MIN_VALUE;
			int sign = 1;
			byte c = getByte();
			while (c <= 32)
				c = getByte();
			if (c == '-') {
				sign = -1;
				c = getByte();
			}
			int val = 0;
			while (c >= '0' && c <= '9') {
				val = val * 10 + (c - '0');
				c = getByte();
			}
			bId--;
			return val * sign;
		}

		public long nextLong() {
			if (!hasNext())
				return Long.MIN_VALUE;
			int sign = 1;
			byte c = getByte();
			while (c <= 32)
				c = getByte();
			if (c == '-') {
				sign = -1;
				c = getByte();
			}
			long val = 0;
			while (c >= '0' && c <= '9') {
				val = val * 10 + (c - '0');
				c = getByte();
			}
			bId--;
			return val * sign;
		}

		public double nextDouble() {
			if (!hasNext())
				return Double.NaN;
			int sign = 1;
			byte c = getByte();
			while (c <= 32)
				c = getByte();
			if (c == '-') {
				sign = -1;
				c = getByte();
			}
			double val = 0;
			while (c >= '0' && c <= '9') {
				val = val * 10 + (c - '0');
				c = getByte();
			}
			if (c == '.') {
				double mul = 1;
				c = getByte();
				while (c >= '0' && c <= '9') {
					mul *= 0.1;
					val += (c - '0') * mul;
					c = getByte();
				}
			}
			bId--;
			return val * sign;
		}
	}

}
