package search;

public class BinarySearch {
    /*
     * pre: a != null
     *      for i 0..|a|: a[i] >= a[i + 1]
     * post: R = i: a[i - 1] > x, a[i] <= x
     */
    public static int search(int[] a, int x) {
        // Iterative
        return searchIterative(a, x);

        // Recursive
        //return searchRecursive(a, x, -1, a.length);
    }

    /*
     * pre: a != null
     *      for i 0..|a|: a[i] >= a[i + 1]
     * post: R = i: a[i - 1] > x, a[i] <= x
     */
    private static int searchIterative(int[] a, int x) {
        int l = -1;
        int r = a.length;
        // l - r >= 1
        // a[l] = +Inf, a[r] = -Inf
        // a[l] > x, a[r] <= x

        /*
         * I: a[l] > x, a[r] <= x
         */
        while (r - l > 1) {
            // r - l > 1
            // l' = l, r' = r
            int mid = (l + r) / 2;

            if (a[mid] > x) {
                // r - l > 1 && a[(l + r) / 2] > x
                l = mid;
                /*
                 * l' = (l + r) / 2
                 * a[(l + r) / 2] > x
                 * a[l'] > x
                 */
            } else {
                // r - l > 1 && a[(l + r) / 2] <= x
                r = mid;
                /*
                 * r' = (l + r) / 2
                 * a[(l + r) / 2] <= x
                 * a[r'] <= x
                 */
            }
            // a[l'] > x, a[r'] <= x
        }
        // l - r == 1
        // a[l] > x, a[r] <= x
        return r;
        // R = r
    }

    /*
     * pre: a != null,
     *      l < r,
     *      a[l] <= x,
     *      a[r]  > x
     * post: R = i: a[i - 1] > x, a[i] <= x
     */
    private static int searchRecursive(int[] a, int x, int l, int r) {
        // a[l] > x, a[r] <= x
        if (r - l <= 1) {
            // r - l = 1 && a[l] > x, a[r] <= x
            return r;
            // R = r
        }

        // r - l > 1
        int mid = (l + r) / 2;

        if (a[mid] > x) {
            // r - l > 1 && a[(l + r) / 2] > x
            return searchRecursive(a, x, mid, r);
            // R = searchRecursive(a, x, (l + r) / 2, r)
        } else {
            // r - l > 1 && a[(l + r) / 2] <= x
            return searchRecursive(a, x, l, mid);
            // R = searchRecursive(a, x, l, (l + r) / 2)
        }
    }

    /*
     * pre: |args| >= 1
     *      for i 1..|args|: a[i] >= a[i + 1]
     * post: R = i: a[i - 1] > x, a[i] <= x
     */
    public static void main(String[] args) {
        int x = Integer.parseInt(args[0]);
        // x = args[0]
        int[] a = new int[args.length - 1];
        // a = {0}, |a| = |args| - 1
        for (int i = 0; i < a.length; i++) {
            a[i] = Integer.parseInt(args[i + 1]);
        }
        // for i 0..|a|: a[i] = args[i + 1]
        System.out.println(search(a, x));
        // R = search(a, x)
    }
}
