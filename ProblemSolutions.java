/******************************************************************
 *
 *        BEN FLOWERS / COMP 272/400C 002
 *
 *   This java file contains the problem solutions for the methods selectionSort,
 *   mergeSortDivisibleByKFirst, asteroidsDestroyed, and numRescueCanoes methods.
 *
 ********************************************************************/

import java.util.Arrays;

public class ProblemSolutions {

    /**
     * Method SelectionSort
     * <p>
     * This method performs a selection sort. This file will be spot checked,
     * so ENSURE you are performing a Selection Sort!
     * <p>
     * This is an in-place sorting operation that has two function signatures. This
     * allows the second parameter to be optional, and if not provided, defaults to an
     * ascending sort. If the second parameter is provided and is false, a descending
     * sort is performed.
     * <p>
     * @param values        - int[] array to be sorted.
    // * @param ascending     - if true, method performs an ascending sort, else descending.
     * There are two method signatures allowing this parameter
     * to not be passed and defaulting to 'true (or ascending sort).
     */

    public void selectionSort(int[] values) {
        selectionSort(values, true);
    }

    public static void selectionSort(int[] values, boolean ascending) {
        int n = values.length;

        for (int i = 0; i < n - 1; i++) {
            int idx = i;

            for (int j = i + 1; j < n; j++) {
                if (ascending) {
                    if (values[j] < values[idx]) {
                        idx = j;
                    }
                } else {
                    if (values[j] > values[idx]) {
                        idx = j;
                    }
                }
            }

            // swap the found min/max element with the first element
            int temp = values[idx];
            values[idx] = values[i];
            values[i] = temp;
        }
    } // eynd method selectionSort

    /**
     * Method mergeSortDivisibleByKFirst
     * <p>
     * This method will perform a merge sort algorithm. However, all numbers
     * that are divisible by the argument 'k', are returned first in the sorted
     * list.
     *
     * @param values - input array to sort per definition above
     * @param k      - value k, such that all numbers divisible by this value are first
     */

    public void mergeSortDivisibleByKFirst(int[] values, int k) {
        if (k == 0) return;
        if (values.length <= 1) return;

        mergeSortDivisibleByKFirst(values, k, 0, values.length - 1);
    }

    private void mergeSortDivisibleByKFirst(int[] values, int k, int left, int right) {
        if (left >= right)
            return;

        int mid = left + (right - left) / 2;
        mergeSortDivisibleByKFirst(values, k, left, mid);
        mergeSortDivisibleByKFirst(values, k, mid + 1, right);
        mergeDivisbleByKFirst(values, k, left, mid, right);
    }

    private void mergeDivisbleByKFirst(int arr[], int k, int left, int mid, int right) {
        // create a temporary array to hold the merged result
        int[] temp = new int[right - left + 1];

        // create separate lists for divisible and non-divisible elements
        int[] leftDivisible = new int[mid - left + 1];
        int[] leftNonDivisible = new int[mid - left + 1];
        int[] rightDivisible = new int[right - mid];
        int[] rightNonDivisible = new int[right - mid];

        int leftDivCount = 0, leftNonDivCount = 0;
        int rightDivCount = 0, rightNonDivCount = 0;

        // separate elements into divisible and non-divisible arrays
        for (int i = left; i <= mid; i++) {
            if (arr[i] % k == 0) {
                leftDivisible[leftDivCount++] = arr[i];
            } else {
                leftNonDivisible[leftNonDivCount++] = arr[i];
            }
        }

        for (int i = mid + 1; i <= right; i++) {
            if (arr[i] % k == 0) {
                rightDivisible[rightDivCount++] = arr[i];
            } else {
                rightNonDivisible[rightNonDivCount++] = arr[i];
            }
        }

        // merge the two divisible arrays
        int i = 0, j = 0, index = 0;
        while (i < leftDivCount && j < rightDivCount) {
            temp[index++] = leftDivisible[i++];  // Maintain original order for divisible elements
        }
        while (i < leftDivCount) {
            temp[index++] = leftDivisible[i++];
        }
        while (j < rightDivCount) {
            temp[index++] = rightDivisible[j++];
        }

        // merge the two non-divisible arrays in ascending order
        i = 0;
        j = 0;
        while (i < leftNonDivCount && j < rightNonDivCount) {
            if (leftNonDivisible[i] <= rightNonDivisible[j]) {
                temp[index++] = leftNonDivisible[i++];
            } else {
                temp[index++] = rightNonDivisible[j++];
            }
        }
        while (i < leftNonDivCount) {
            temp[index++] = leftNonDivisible[i++];
        }
        while (j < rightNonDivCount) {
            temp[index++] = rightNonDivisible[j++];
        }

        // copy back to original array
        for (int t = 0; t < temp.length; t++) {
            arr[left + t] = temp[t];
        }
    }

    /**
     * Method asteroidsDestroyed
     *
     * You are given an integer 'mass', which represents the original mass of a planet.
     * You are further given an integer array 'asteroids', where asteroids[i] is the mass
     * of the ith asteroid.
     *
     * Return true if possible for all asteroids to be destroyed. Otherwise, return false.
     *
     * @param mass          - integer value representing the mass of the planet
     * @param asteroids     - integer array of the mass of asteroids
     * @return              - return true if all asteroids destroyed, else false.
     */

    public static boolean asteroidsDestroyed(int mass, int[] asteroids) {
        if (asteroids == null || asteroids.length == 0) {
            return true;
        }

        Arrays.sort(asteroids);
        long currentMass = mass;

        for (int asteroid : asteroids) {
            if (currentMass < asteroid) {
                return false;
            }
            currentMass += asteroid;
        }

        return true;
    }

    /**
     * Method numRescueSleds
     *
     * You are given an array people where people[i] is the weight of the ith person,
     * and an infinite number of rescue sleds where each sled can carry a maximum weight
     * of limit. Each sled carries at most two people at the same time.
     *
     * @param people    - an array of weights for people that need to go in a sled
     * @param limit     - the weight limit per sled
     * @return          - the minimum number of rescue sleds required to hold all people
     */

    public static int numRescueSleds(int[] people, int limit) {
        if (people == null || people.length == 0) {
            return 0;
        }

        Arrays.sort(people);
        int sleds = 0;
        int left = 0;
        int right = people.length - 1;

        while (left <= right) {
            if (left == right) {
                sleds++;
                break;
            }

            if (people[left] + people[right] <= limit) {
                left++;
            }

            right--;
            sleds++;
        }

        return sleds;
    }

} // End class ProblemSolutions
