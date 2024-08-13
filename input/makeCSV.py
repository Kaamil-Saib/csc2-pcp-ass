import csv

# Prompt the user for input
rows = int(input("Enter the number of rows: "))
cols = int(input("Enter the number of columns: "))
fill_value = input("Enter the number to fill all values with: ")

# Create the grid
grid = [[fill_value for _ in range(cols)] for _ in range(rows)]

# Define the filename based on the input
filename = f"{rows}_by_{cols}_all_{fill_value}.csv"

# Write the grid to a CSV file
with open(filename, 'w', newline='') as file:
    writer = csv.writer(file)
    # Write the header
    writer.writerow([rows, cols])
    # Write the grid
    writer.writerows(grid)

print(f"CSV file '{filename}' created successfully.")


