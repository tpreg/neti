# Offer Calculator Application

## Introduction

This application calculates the offer price based on a given customer and a list of parts, then creates an offer summary and saves it to a
file.

## Prerequisites

- Java 17 is required to run this application.
- Ensure that the necessary input files are available in the correct format.

## Input Data

The application requires a single comma-separated input file containing:

- **First Line**: The customer's name and type. The type can be one of the following:
    - `NON_REGISTERED`
    - `LOYAL_CUSTOMER`
    - `VIP`

- **Subsequent Lines**: Details of the parts, with each line containing:
    - **Part Name**: The name of the part.
    - **Part Price**: The price of the part in HUF.

The input file should be accessible, and its filename should be passed as an argument to the application.

## How to Build and Run

1. **Compile and Run**:
    - Open a command prompt or terminal.
    - Navigate to the root directory of the application.
    - Compile the code with:
      ```bash
      javac Main.java
      ```
    - Run the program using the command below, using `data.csv` as an example file name:
      ```bash
      java Main data.csv
      ```

2. **Expected Operation**:
    - The application reads the specified file, calculates the offer price, and creates an offer summary.
    - The result is saved to `offer.txt`.

## Output Data

After running the program, the following output file is generated:

- **offer.txt**: Contains the customer's name and the final offer price in a summarized format.

## Troubleshooting

If any issues arise:

- Verify that the input files are correctly formatted.
- Ensure that Java is properly installed.
- Check console error messages for more details about the problem.

## Contact

For questions or support, please contact the application's developer or support team.
