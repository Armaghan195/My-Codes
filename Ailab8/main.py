import time
from bubble_sort import bubble_sort
from selection_sort import selection_sort
from insertion_sort import insertion_sort
from heap_sort import heap_sort
from merge_sort import merge_sort
from quick_sort import quick_sort

def analyze_and_sort(arr):
    print(f"\nAnalyzing array of size {len(arr)}...")
    
    if len(arr) <= 1:
        print("Array size is 0 or 1. Already sorted.")
        return arr

    
    is_sorted = True
    is_reverse_sorted = True
    for i in range(len(arr) - 1):
        if arr[i] > arr[i + 1]:
            is_sorted = False
        if arr[i] < arr[i + 1]:
            is_reverse_sorted = False
            
    if is_sorted:
        print("Conclusion: Array is already sorted. No sorting needed.")
        return arr


    if len(arr) < 50:
        print("Conclusion: Small array detected. Using Insertion Sort (efficient for small datasets).")
        return insertion_sort(arr)
        
    
    if is_reverse_sorted:
        print("Conclusion: Array is reverse sorted. Using Merge Sort to guarantee O(N log N) efficiently.")
        return merge_sort(arr)
        
    
    unique_elements = len(set(arr))
    if unique_elements < len(arr) / 4:
        print(f"Conclusion: Many duplicates detected ({unique_elements} unique out of {len(arr)}). Using Quick Sort (3-way partition handles duplicates efficiently).")
        return quick_sort(arr)

    
    print("Conclusion: Generic large dataset. Using Merge Sort for stable O(N log N) performance.")
    return merge_sort(arr)

if __name__ == "__main__":
    
    test_cases = {
        "Small Array": [12, 4, 5, 2, 8, 1, 9, 3, 7, 6],
        "Already Sorted": list(range(100)),
        "Reverse Sorted": list(range(100, 0, -1)),
        "Many Duplicates": [5, 2, 5, 2, 5, 5, 2, 2, 5, 5, 2, 5] * 10,
        "Generic Random (Large)": [ (i * 13) % 71 for i in range(200) ]
    }

    for name, test_arr in test_cases.items():
        print(f"\n--- Test Case: {name} ---")
        start_time = time.time()
        sorted_arr = analyze_and_sort(test_arr.copy())
        end_time = time.time()
        print(f"Sorted correctly? {sorted_arr == sorted(test_arr)}")
        print(f"Time taken: {end_time - start_time:.6f} seconds")
