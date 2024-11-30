# Offer Calculator Application

## Introduction

This application calculates the offer price based on a given customer and a list of parts, then creates an offer summary and saves it to a
file.

## Prerequisites

- Java 17 is required to run this application.
- Ensure that the necessary input files are available in the correct format.

## Input Data

The application requires a single comma-separated input file (`CSV`) containing:

- **First Line**: The customer's name and type. The type can be one of the following:
    - `NON_REGISTERED`
    - `LOYAL_CUSTOMER`
    - `VIP`

- **Subsequent Lines**: Details of the parts, with each line containing:
    - **Part Name**: The name of the part.
    - **Part Price**: The price of the part in HUF.

The input file should be accessible, and its filename should be passed as an argument to the application.

## How to Build and Run

### Build

- Open a command prompt or terminal.
- Navigate to the root directory of the application.
- Compile the code with:
    ```bash
    javac Main.java
    ```

### Run

- Run the program using the command below, using `data.csv` as an example file name:
    ```bash
    java Main data.csv
    ```

### Expected Operation

- The application processes the input file to compute total offer price contingent on a customer type, includes detailed pricing for each
  part.
- Generates a detailed `offer.pdf` capturing the customer's name, summarized total price, and per-part breakdown.

## Output Data

After running the program, the following output file is generated:

- **offer.pdf**: A detailed summary including:
    - Customer's name.
    - Total offer price.
    - Pricing details per part, accounting for customer-based markups.

## Troubleshooting

If any issues arise:

- Verify that the input files are correctly formatted.
- Ensure that Java is properly installed.
- Check console error messages for more details about the problem.

## Contact

For questions or support, please contact the application's developer or support team.
