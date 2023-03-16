//todo: M114020060 陳侑萱, M114020052 王渙鈞
//todo: write code in the sort(int[] a) function in InclassSort.java to sort the input array in the way shown in the expected output. 
//DO NOT EDIT other functions NOR add global variables. 

public class InclassSort {
	
	// todo: write code in this function
	// 用for迴圈，每次執行找array裡面的最大值跟最小值，重複執行找最大跟最小進行排序
	public int[] sort(int[] a) {
		int N = a.length;
		for(int i = 0; i < a.length; i = i + 2){
            int max = i;
			int min = i+1;
			//找最大值
			for(int j = i+1; j < N; j++){
				if(a[j] > a[max]){
                    max = j;
				}		
            }
			int swap = a[max];
		    a[max] = a[i];
			a[i] = swap;
			//判斷有沒有超出邊界
			if(i < a.length-1){
				//找最小值
				for(int j = i+1; j < N; j++){
					if(a[j] < a[min]){
						min = j;
					}
				}
				int swap1 = a[min];
				a[min] = a[i+1];
				a[i+1] = swap1;
			}
		}
		return a;
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
