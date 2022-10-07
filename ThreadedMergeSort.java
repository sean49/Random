class ThreadedMergeSort extends Thread {
    private int[] arr;

    public static String arr_to_str(int[] arr) {
        String arr_str = "";

        for (int num : arr)
            arr_str += num + " ";
        
        return arr_str;
    }

    public ThreadedMergeSort(int[] arr) {
        this.arr = arr;
    }

    public void run() {
        System.out.println("Thread " + Thread.currentThread().getId() + " started");
        
        int length = arr.length;

        if (length < 2) {
            System.out.println("Thread " + Thread.currentThread().getId() + " finished: " + arr_to_str(arr));
            return;
        }

        int mid = length / 2;

        int[] left = new int[mid];
        int[] right = new int[length - mid];

        for (int i = 0; i < mid; i++) {
            left[i] = arr[i];
        }

        for (int i = mid; i < length; i++) {
            right[i - mid] = arr[i];
        }

        ThreadedMergeSort t1 = new ThreadedMergeSort(left);
        ThreadedMergeSort t2 = new ThreadedMergeSort(right);
        t1.start();
        t2.start();
        try {
            t1.join();            
            t2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        merge(arr, left, right);
        System.out.println("Thread " + Thread.currentThread().getId() + " finished: " + arr_to_str(arr));
    }

    public static void merge(int[] arr, int[] left, int[] right) {
        int llength = left.length;
        int rlength = right.length;

        int i = 0, j = 0, k = 0;

        while (i < llength && j < rlength) {
            if (left[i] <= right[j]) {
                arr[k++] = left[i++];

            } else {
                arr[k++] = right[j++];
            }
        }

        while (i < llength) {
            arr[k++] = left[i++];
        }
        while (j < rlength) {
            arr[k++] = right[j++];
        }
    }

}

class Driver {

    public static void main(String[] args) throws InterruptedException {
        int[] arr = new int[] { 1, 2, 64, 45, 7, 3, 67, 8, 3, 34 };

        System.out.println(ThreadedMergeSort.arr_to_str(arr));
        
        ThreadedMergeSort tms = new ThreadedMergeSort(arr);
        tms.start();
        tms.join();
        
        System.out.println(ThreadedMergeSort.arr_to_str(arr));

    }
}
