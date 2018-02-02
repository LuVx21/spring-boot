package org.luvx.algorithm;

import java.util.Arrays;

public class Sorts {
	public static void main(String[] args) {
		int[] arr = { 43, 99, 38, 76, 65, 27, 17 };
		// int[] arr = { 45, 32, 98, 74, 17, 53, 44, 61, 22 };
		System.out.println("排序前 " + Arrays.toString(arr));
		// sort(arr);
		// sort1(arr);
		// sort2(arr);
		// sort3(arr);
		// sort4(arr, 0, arr.length - 1);
		sort5(arr);
		System.out.println("排序后为17, 27, 38, 43, 65, 76, 99]");
		System.out.println("排序后" + Arrays.toString(arr));
	}

	// 简单选择排序
	public static void sort5(int[] array) {
		for (int i = 0; i < array.length - 1; i++) {
			int k = i;
			for (int j = i + 1; j < array.length; j++) {
				if (array[k] > array[j])
					k = j;
			}
			if (k != i) {
				int temp = array[i];
				array[i] = array[k];
				array[k] = temp;
			}
		}
	}

	// 快速排序
	public static void sort4(int[] array, int left, int right) {
		if (right <= left)
			return;// 只有0或1个记录,不需排序
		int pivot = partition2(array, left, right);
		sort4(array, left, pivot - 1);
		sort4(array, pivot + 1, right);
	}

	// 分割策略2
	public static int partition2(int[] array, int start, int end) {
		int pivot = array[start];
		int left = start, right = end;
		while (left <= right) {
			while (left <= right && array[left] <= pivot)
				left++;
			while (left <= right && array[right] > pivot)
				right--;
			if (left < right) {
				int temp = array[right];
				array[right] = array[left];
				array[left] = temp;
				left++;
				right--;
			}
		}
		int temp1 = array[start];
		array[start] = array[right];
		array[right] = temp1;
		return right;
	}

	// 分割策略1
	public static int partition1(int[] array, int left, int right) {
		int pivot = array[left];
		while (left < right) {
			while (left < right && array[right] > pivot)
				right--;
			array[left] = array[right];
			while (left < right && array[left] <= pivot)
				left++;
			array[right] = array[left];
		}
		array[left] = pivot;
		return left;
	}

	// 冒泡排序
	public static void sort3(int[] array) {
		boolean flag = false;
		for (int i = 0; i < array.length; i++) {
			for (int j = 1; j < array.length - i; j++) {
				if (array[j] < array[j - 1]) {
					flag = true;
					int temp = array[j];
					array[j] = array[j - 1];
					array[j - 1] = temp;
				}
			}
			if (flag == false)
				return;
		}
	}

	// 希尔排序
	public static void sort2(int[] array) {
		int len = array.length / 2;
		while (len >= 1)// 分组,每组元素为len个
		{
			for (int k = 0; k < len; k++)// 组内进行排序
			{
				for (int i = k + len; i < array.length; i += len) {
					int temp = array[i];
					int j = i - len;// 前一个
					while (j >= k && array[j] > temp) {
						array[j + len] = array[j];
						j -= len;
					}
					array[j + len] = temp;
				} // 当len = 1 时 可看做直接插入排序
			}
			len = len / 2;// 每组元素为原来的一半
		}
	}

	// 折半插入排序
	public static void sort1(int[] array) {
		for (int i = 0; i < array.length; i++) {
			int temp = array[i];
			int left = 0;
			int right = i - 1;
			while (left <= right) {
				int middle = (left + right) / 2;
				if (array[middle] > temp)
					right = middle - 1;
				else
					left = middle + 1;
			}
			for (int j = i - 1; j >= left; j--)
				array[j + 1] = array[j];
			array[left] = temp;
		}
	}

	// 直接插入排序
	public static void sort(int[] array) {
		for (int i = 1; i < array.length; i++) {
			int temp = array[i];// 将array[i]插入前面以排序好的
			int j = i - 1;
			while ((j >= 0) && (array[j] > temp)) {
				array[j + 1] = array[j];// 比array[i]大的 依次向后移动
				j--;
			}
			array[j + 1] = temp;
		}
	}
}
