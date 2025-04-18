package class165;

// 可持久化并查集模版题，C++版
// 测试链接 : https://www.luogu.com.cn/problem/P3402
// 如下实现是C++的版本，C++版本和java版本逻辑完全一样
// 提交如下代码，可以通过所有测试用例

//#include <bits/stdc++.h>
//
//using namespace std;
//
//const int MAXM = 200001;
//const int MAXT = 8000001;
//int n, m;
//int rootfa[MAXM];
//int rootsiz[MAXM];
//int ls[MAXT];
//int rs[MAXT];
//int val[MAXT];
//int cnt = 0;
//
//int buildfa(int l, int r) {
//    int rt = ++cnt;
//    if (l == r) {
//        val[rt] = l;
//    } else {
//        int mid = (l + r) / 2;
//        ls[rt] = buildfa(l, mid);
//        rs[rt] = buildfa(mid + 1, r);
//    }
//    return rt;
//}
//
//int buildsiz(int l, int r) {
//    int rt = ++cnt;
//    if (l == r) {
//        val[rt] = 1;
//    } else {
//        int mid = (l + r) / 2;
//        ls[rt] = buildsiz(l, mid);
//        rs[rt] = buildsiz(mid + 1, r);
//    }
//    return rt;
//}
//
//int update(int jobi, int jobv, int l, int r, int i) {
//    int rt = ++cnt;
//    ls[rt] = ls[i];
//    rs[rt] = rs[i];
//    if (l == r) {
//        val[rt] = jobv;
//    } else {
//        int mid = (l + r) / 2;
//        if (jobi <= mid) {
//            ls[rt] = update(jobi, jobv, l, mid, ls[rt]);
//        } else {
//            rs[rt] = update(jobi, jobv, mid + 1, r, rs[rt]);
//        }
//    }
//    return rt;
//}
//
//int query(int jobi, int l, int r, int i) {
//    if (l == r) {
//        return val[i];
//    }
//    int mid = (l + r) / 2;
//    if (jobi <= mid) {
//        return query(jobi, l, mid, ls[i]);
//    } else {
//        return query(jobi, mid + 1, r, rs[i]);
//    }
//}
//
//int find(int x, int v) {
//    int fa = query(x, 1, n, rootfa[v]);
//    while(x != fa) {
//        x = fa;
//        fa = query(x, 1, n, rootfa[v]);
//    }
//    return x;
//}
//
//void merge(int x, int y, int v) {
//    int fx = find(x, v);
//    int fy = find(y, v);
//    if (fx != fy) {
//        int xsiz = query(fx, 1, n, rootsiz[v]);
//        int ysiz = query(fy, 1, n, rootsiz[v]);
//        if (xsiz >= ysiz) {
//            rootfa[v] = update(fy, fx, 1, n, rootfa[v]);
//            rootsiz[v] = update(fx, xsiz + ysiz, 1, n, rootsiz[v]);
//        } else {
//            rootfa[v] = update(fx, fy, 1, n, rootfa[v]);
//            rootsiz[v] = update(fy, xsiz + ysiz, 1, n, rootsiz[v]);
//        }
//    }
//}
//
//int main() {
//    ios::sync_with_stdio(false);
//    cin.tie(nullptr);
//    cin >> n >> m;
//    rootfa[0] = buildfa(1, n);
//    rootsiz[0] = buildsiz(1, n);
//    for (int v = 1, op, x, y; v <= m; v++) {
//        cin >> op;
//        rootfa[v] = rootfa[v - 1];
//        rootsiz[v] = rootsiz[v - 1];
//        if (op == 1) {
//            cin >> x >> y;
//            merge(x, y, v);
//        } else if (op == 2) {
//            cin >> x;
//            rootfa[v] = rootfa[x];
//            rootsiz[v] = rootsiz[x];
//        } else {
//            cin >> x >> y;
//            if (find(x, v) == find(y, v)) {
//                cout << 1 << "\n";
//            } else {
//                cout << 0 << "\n";
//            }
//        }
//    }
//    return 0;
//}