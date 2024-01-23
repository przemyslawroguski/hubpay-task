Create a simulated digital wallet API using Java technologies/
frameworks.
A digital wallet for the purpose of this exercise holds a balance for
a customer and can be debited and credited like a bank account.
Acceptance Criteria:
1. Customer can add funds:
⁃ API that accept an amount and credits the customers
wallet
⁃ The maximum amount the user can send is £10,000
⁃ The minimum amount the user can send is £10
2. Customer can withdraw funds:
⁃ API that accept an amount and debits the amount in the
customers wallet
⁃ The maximum amount the user can withdraw is £5,000
per transaction
⁃ Customers wallet should not go into the negative in any
scenario and must protect against concurrent requests/
double submits etc.

3. Customer can view transactions
⁃ API that provides a paginated list of transaction (credits
and debits)
Assumptions/Notes
• Security is not mandatory unless provided by the framework
and within the limited time constraints.
• An in memory or container based relational data store of
choices can be used. E.g. PostgresQL, H2 or MySQL.
• Customer Wallet creation/signup is out of scope. For this
exercise, setup data can be initialised via any means
possible. E.g. database migration, post start-up code or via
another API endpoint.
• Frameworks such as Spring Boot or similar are

recommended to expedite setup and reduce boilerplate
code.