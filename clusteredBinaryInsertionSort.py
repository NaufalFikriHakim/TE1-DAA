from random import randrange
import time
import tracemalloc


def clustered_binary_insertion_sort(data):
    position_pointer = 0
    for i in range(1, len(data)):
        current_pointer = i
        key = data[current_pointer]
        if key >= data[position_pointer]:
            place = binary_loc_finder(
                data, position_pointer + 1, current_pointer - 1, key
            )
        else:
            place = binary_loc_finder(data, 0, position_pointer - 1, key)
        position_pointer = place
        data = place_inserter(data, place, current_pointer)
    return data


def binary_loc_finder(data, start, end, key):
    if start == end:
        if data[start] > key:
            loc = start
            return loc
        else:
            loc = start + 1
            return loc
    if start > end:
        loc = start
        return loc
    else:
        middle = (start + end) // 2
        if data[middle] < key:
            return binary_loc_finder(data, middle + 1, end, key)
        elif data[middle] > key:
            return binary_loc_finder(data, start, middle - 1, key)
        else:
            return middle


def place_inserter(data, start, end):
    temp = data[end]
    for k in range(end, start, -1):
        data[k] = data[k - 1]
        k = k - 1
    data[start] = temp
    return data


# Quicksort with Lomuto Partitioning
def quicksort(arr, start, stop):
    if start < stop:
        pivotindex = partitionrand(arr, start, stop)
        quicksort(arr, start, pivotindex - 1)
        quicksort(arr, pivotindex + 1, stop)

def partitionrand(arr, start, stop):
    randpivot = randrange(start, stop)
    arr[start], arr[randpivot] = arr[randpivot], arr[start]
    return partition(arr, start, stop)


def partition(arr, start, stop):
    pivot = start

    i = start + 1

    for j in range(start + 1, stop + 1):
        if arr[j] <= arr[pivot]:
            arr[i], arr[j] = arr[j], arr[i]
            i = i + 1
    arr[pivot], arr[i - 1] = arr[i - 1], arr[pivot]
    pivot = i - 1
    return pivot

def calculate(file_path, sort_func, file_desc):
    data = []

    with open(file_path, "r") as file:
        for line in file:
            data.append(int(line.strip()))

    tracemalloc.start()

    if sort_func == "QS":
        start_time = time.time()
        quicksort(data, 0, len(data) - 1)
        end_time = time.time()
    elif sort_func == "CBIS":
        start_time = time.time()
        clustered_binary_insertion_sort(data)
        end_time = time.time()

    _, peak = tracemalloc.get_traced_memory()
    tracemalloc.stop()

    elapsed_time = end_time - start_time
    print(f"Elapsed time using {sort_func} for {file_desc} is: {elapsed_time*1000} ms")
    
    print(f"memory usage: {peak / 10**6} MB")
    

def is_sorted(data):
    for i in range(len(data) - 1):
        if data[i] > data[i + 1]:
            return False
    return True

def compare_sorts(file_name, file_desc):
    calculate(file_name, "CBIS", file_desc)
    calculate(file_name, "QS", file_desc)
    print("--------------------")

compare_sorts("data200random.txt", "small random data")
compare_sorts("data200sorted.txt", "small sorted data")
compare_sorts("data200reverse.txt", "small reverse data")
compare_sorts("data2000random.txt", "medium random data")
compare_sorts("data2000sorted.txt", "medium sorted data")
compare_sorts("data2000reverse.txt", "medium reverse data")
compare_sorts("data20000random.txt", "large random data")
compare_sorts("data20000sorted.txt", "large sorted data")
compare_sorts("data20000reverse.txt", "large reverse data")