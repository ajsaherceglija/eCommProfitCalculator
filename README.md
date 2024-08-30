# eComm Profit Calculator

## Overview
The eComm Profit Calculator project aims to calculate profit margins for orders in an eComm web application. By processing orders from a CSV file, this application provides valuable insights into total revenue, total profit, and profit margins based on shirt sizes. It also implements a payment processing system using the Strategy pattern, allowing for different payment methods and their associated fees.

## Project Structure
This project consists of several components that work together to analyze order data and generate financial reports.

### Features
- Calculate total revenue from orders.
- Calculate total profit after deducting costs.
- Analyze profit margins based on shirt sizes.
- Implement payment processing using the Strategy pattern to handle different payment methods.
- Use of multithreading to enhance performance and efficiency.

## Order Data Format
The application processes orders from a CSV file with the following format:
```full_name,shirt_size,with_design,with_hoodie,payment```

- `full_name`: Customer's name
- `shirt_size`: Size of the shirt (e.g., S, M, L, XL)
- `with_design`: Boolean value indicating if a design is requested (`true` or `false`)
- `with_hoodie`: Boolean value indicating if a hoodie is requested (`true` or `false`)
- `payment`: Payment method used for the order (e.g., wallet, bankcard, visa, mastercard)

## Pricing Structure
- **Selling Price per Shirt**: 40 BAM
- **Cost of Shirt**: 14 BAM
- **Hoodie Cost**: +3 BAM if attached
- **Design Cost**: +2 BAM if applied

### Payment Processing Fees
- Wallet: No additional cost
- Bankcard: 5% of transaction
- Visa: 2% of transaction
- Mastercard: 3% of transaction
- Other: 10% of transaction
