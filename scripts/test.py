import os

def get_jar_files_with_sizes(folder_path):
    jar_files = []
    for root, _, files in os.walk(folder_path):
        for file in files:
            if file.endswith(".jar"):
                file_path = os.path.join(root, file)
                file_size_kb = os.path.getsize(file_path) / 1024  # Convert to KB
                jar_files.append((file, file_size_kb))
    return jar_files

if __name__ == "__main__":
    folder_path = input("Enter the folder path: ")
    jar_files_with_sizes = get_jar_files_with_sizes(folder_path)
    
    if jar_files_with_sizes:
        print("List of JAR files and their sizes:")
        for file_name, file_size_kb in jar_files_with_sizes:
            print(f"{file_name} - {file_size_kb:.2f} KB")
    else:
        print("No JAR files found in the specified folder.")
