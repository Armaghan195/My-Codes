import heapq

def heap_sort(arr):
    heap = arr.copy()
    heapq.heapify(heap)
    sorted_list = []
    while heap:
        sorted_list.append(heapq.heappop(heap))
    return sorted_list
