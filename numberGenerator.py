import random

# Number of elements
def generate_tc(file_name, number, sorted):
    random_numbers = [random.randint(1, 1000) for _ in range(number)]

    if sorted == "sorted":
        random_numbers.sort()
    elif sorted == "reverse":
        random_numbers.sort(reverse=True)

    with open(file_name, "w") as file:
        for random_number in random_numbers:
            file.write(str(random_number) + "\n")

generate_tc("data200random.txt", 200, "random")
generate_tc("data200sorted.txt", 200, "sorted")
generate_tc("data200reverse.txt", 200, "reverse")
generate_tc("data2000random.txt", 2000, "random")
generate_tc("data2000sorted.txt", 2000, "sorted")
generate_tc("data2000reverse.txt", 2000, "reverse")
generate_tc("data20000random.txt", 20000, "random")
generate_tc("data20000sorted.txt", 20000, "sorted")
generate_tc("data20000reverse.txt", 20000, "reverse")

