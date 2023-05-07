import pandas as pd

# Load the sleep data CSV file into a pandas dataframe
sleep_data = pd.read_csv('your_sleep_data.csv') # You can use OURA ring data here

# Filter out any rows that do not have sleep data
sleep_data = sleep_data[sleep_data['is_main_sleep'] == True]

# Extract the relevant columns for sleep analysis
sleep_data = sleep_data[['date', 'bedtime_start', 'bedtime_end', 'rem', 'deep', 'light', 'awake']]

# Convert the bedtime and wake up times from strings to datetime objects
sleep_data['bedtime_start'] = pd.to_datetime(sleep_data['bedtime_start'])
sleep_data['bedtime_end'] = pd.to_datetime(sleep_data['bedtime_end'])

# Calculate the duration of sleep for each night
sleep_data['duration'] = (sleep_data['bedtime_end'] - sleep_data['bedtime_start']).dt.total_seconds() / 3600

# Print out the sleep data
print(sleep_data)
