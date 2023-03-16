//todo: M114020022 & 高雲揚, M114020039 & 張家晧
//todo: write code in the sort(int[] a) function in InclassSort.java to sort the input array in the way shown in the expected output. 
//DO NOT EDIT other functions NOR add global variables. 

public class InclassSort {
	
	// todo: write code in this function
	public int[] sort(int[] a) {
		int N = a.length;
		// 先sort由小到大(進行selection sort)
		for (int i = 0; i < N; i++){
			int min = i;
			for (int j = i+1; j < N; j++){
				if(a[j] < a[min]) {
					min = j;
				}
			}
			int swap = a[min];
			a[min] = a[i];
			a[i] = swap;
		}
		
		// 進行題目的sort
		int[] b = new int[N];
		// 從小開始算
		int indexi = 0;
		// 從大開始算
		int indexj = N-1;
		for(int i = 0; i<N; i++){
			if(i%2==0){
				b[i] = a[indexj];
				indexj--;
			} else{
				b[i] = a[indexi];
				indexi++;
			}
		}
		return b;
	}
	
	public static void printSorted(int[] sorted) {
		int i;
    	String output = "";
    	for (i=0; i<sorted.length; i++) {
    		output += sorted[i] + ",";
    	}
    	if (output != ""){
    		output = output.substring(0, output.length() - 1);
    	}
    	System.out.println(output);
	}

	public static void main(String[] args) {
		InclassSort ics = new InclassSort();
		printSorted(ics.sort(new int[]{1,3,6})); // expected output: 6,1,3 
		printSorted(ics.sort(new int[]{1,2,3,4,5,6})); // expected output: 6,1,5,2,4,3
		printSorted(ics.sort(new int[]{7,6,3,5,1,2,9})); // expected output: 9,1,7,2,6,3,5
		printSorted(ics.sort(new int[]{100,1,10,8,6,2,5,10,1})); // expected output: 100,1,10,1,10,2,8,5,6
	}
}
