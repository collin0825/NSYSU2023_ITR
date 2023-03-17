//todo: M114020022 & 高雲揚
//todo: write code in the chop(int p, int q) to calculate what happens when a tree branch is chopped. 
//todo: modify union(int p, int q) to maintain add nodes to the tree. 
//DO NOT EDIT other functions NOR add global variables.

public class HW1 {
 
	// ChopTrees is modified from QuickUnionUF https://algs4.cs.princeton.edu/code/edu/princeton/cs/algs4/QuickUnionUF.java.html
	// QuickUnionUF JavaDoc https://algs4.cs.princeton.edu/code/javadoc/edu/princeton/cs/algs4/QuickUnionUF.html
	public static class ChopTrees {
		private int[] parent;   // parent[i] = parent of i
		private int count;      // number of components
		private int N;
		private boolean collapsed;

		/**
		* Initializes an empty union-find data structure with
		* {@code n} elements {@code 0} through {@code n-1}.
		* Initially, each element is in its own set.
		*
		* @param  n the number of elements
		* @throws IllegalArgumentException if {@code n < 0}
		*/
		public ChopTrees(int n) {
			count = n;
			N = n;
			collapsed = false;
			parent = new int[n];
			for (int i = 0; i < n; i++) {
				parent[i] = 0;
			}
		}

		/**
		* Returns the number of sets.
		*
		* @return the number of sets (between {@code 1} and {@code n})
		*/
		public int count() {
			return count;
		}

		/**
		* Returns the canonical element of the set containing element {@code p}.
		*
		* @param  p an element
		* @return the canonical element of the set containing {@code p}
		* @throws IllegalArgumentException unless {@code 0 <= p < n}
		*/
		public int find(int p) {
			validate(p);
			while (parent[p] != 0 && parent[p] != -1)
				p = parent[p];
			return p;
		}

		/**
		* Returns true if the two elements are in the same set.
		* 
		* @param  p one element
		* @param  q the other element
		* @return {@code true} if {@code p} and {@code q} are in the same set;
		*         {@code false} otherwise
		* @throws IllegalArgumentException unless
		*         both {@code 0 <= p < n} and {@code 0 <= q < n}
		* @deprecated Replace with two calls to {@link #find(int)}.
		*/
		@Deprecated
		public boolean connected(int p, int q) {
			return find(p) == find(q);
		}

		// validate that p is a valid index
		private void validate(int p) {
			int n = parent.length;
			if (p < 0 || p >= n) {
				System.out.println("index " + p + " is not between 0 and " + (n-1));
			}
		}

		/**
		* Adds a branch to the tree, see assignment for details
		*
		* @param  p one element
		* @param  q the other element
		* @throws IllegalArgumentException unless
		*         both {@code 0 <= p < n} and {@code 0 <= q < n}
		*/
		public boolean union(int p, int q) {
			// 如果已倒塌則回傳false
			if(collapsed == true){
				return false;
			}
			
			// 如果q原本不存在則回傳false
			if(parent[q] == -1){
				return false;
			}

			int parentP = parent[p];
			int parentQ = parent[q];

			// 如果欲union的兩點已連結則回傳true
			if (parentP == q || parentQ == p){
				return true;
			}
			// 進行連接
			parent[p] = q;
			count--;
			return true;
		}

		// For chopping tree branches, see assignment for details
		public int chop(int p, int q){
			//建立一個紀錄node高度的陣列
			int[] heightArr = new int[N];
			for(int i = 0; i< heightArr.length; i++){
				heightArr[i] = 0;
			}

			// 計算node高度
			for(int i = 0; i < parent.length; i++){
				if(parent[i] == 0 || parent[i] == -1) 
					heightArr[i] = 0;
				else {
					int temp = i;
					while(temp != 0 && temp != -1) {
						heightArr[i]++;
						temp = parent[temp];
					}
				}
			}

			// 找出最大高度
			int maxDepth = 0;
			for(int i = 0; i < heightArr.length; i++){
				if(heightArr[i] > maxDepth){
					maxDepth = heightArr[i];
				}
			}	
			// =====開始砍樹======

			//判斷是否砍不存在的分支且還未collapse，是則回傳0
			if(parent[p] != q && parent[q] != p && collapsed==false){
				return 0;
			}
			int amount = 0;
			//進行砍樹

			//若p為上面的點
			if(heightArr[p] > heightArr[q] && collapsed==false){
				amount = 1;  //p先切掉，先進行計算
				for(int i = 0; i < N; i++) {
					if(find(i) == find(p) && heightArr[i] > heightArr[p]) {
						// 檢測是否為同一分支
						int j = i;
						while(j != p){
							j = parent[j];
							if(j == 0){
								break;
							}
						}
						if(j == p){
							heightArr[i] = -1;
							amount++; //若i跟p在同一棵樹，且i的高度>被切掉的p，則i也會被切掉
						}
					}
				}
				parent[p] = -1;
				//找看看最maxdepth是否存在，存在的話樹不會倒。
				for(int i=0; i<N; i++) {
					if(heightArr[i] == -1){
						parent[i] = -1;
					}
					if(heightArr[i] == maxDepth && i != p){
						collapsed=false;
						break;
					}else{
						collapsed=true;
					}
				}
			}else if (heightArr[q] > heightArr[p] && collapsed==false){//若q為上面的點
				amount = 1;  //q先切掉，先進行計算
				for(int i=0; i<N; i++) {
					if(find(i) == find(q) && heightArr[i] > heightArr[q]) {
						// 檢測是否為同一分支
						int j = i;
						while(j != q){
							j = parent[j];
							if(j == 0){
								break;
							}
						}
						if(j == q){
							heightArr[i] = -1;
							amount++;//若i跟q在同一棵樹，且i的高度>被切掉的p，則i也會被切掉
						}
					}
				}
				parent[q] = -1;
				//找看看最maxdepth是否存在，存在的話樹不會倒。
				for(int i=0; i<N; i++) {
					if(heightArr[i] == -1){
						parent[i] = -1;
					}
					if(heightArr[i] == maxDepth && i != q){
						collapsed=false;
						break;
					}else{
						collapsed=true;
					}
				}
			}

			//進行倒塌判斷 
			if(collapsed){
				return -1;
			}else{
				return amount;
			}
		}
	}

	public static void main(String[] args) {
		ChopTrees ct = new ChopTrees(25);
		ct.union(1, 2);
		ct.union(3, 4);
		ct.union(1, 3);
		ct.union(7, 2);
		ct.union(7, 3);
		ct.union(1, 6);
		ct.union(10, 11);
		ct.union(15, 12);
		ct.union(2, 17);
		ct.union(3, 15);
		ct.union(4, 11);
		ct.union(1, 3);
		ct.union(6, 8);
		ct.union(8, 19);
		ct.union(11, 17);
		ct.union(12, 15);
		ct.union(11, 18);
		ct.union(5, 14);

		System.out.println("After Chop 8, 6 => " + ct.chop(8, 6)); // expected output: After Chop 8, 6 => 1
		System.out.println("After Chop 11, 18 => " + ct.chop(11, 18));// expected output: After Chop 11, 18 => 3
		System.out.println("After Chop 1, 3 => " + ct.chop(1, 3));//  expected output: After Chop 1, 3 => 1

		System.out.println("Union of 20, 9 => " + ct.union(20,9)); // expected output: Union of 20, 9 => true

		System.out.println("After Chop 15, 3 => " + ct.chop(15, 3));//  expected output: After Chop 15, 3 => -1
		System.out.println("After Chop 14, 9 => " + ct.chop(14, 9));//  expected output: After Chop 14, 9 => -1

		System.out.println("Union of 20, 9 => " + ct.union(20,9)); // expected output: Union of 20, 9 => false
		
	}
}