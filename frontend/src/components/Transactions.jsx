export default function Transactions({ transactions }) {
    if (transactions == null) {
        return <p>Loading...</p>;
    }
    return (
        <table className="table">
            <thead>
                <tr>
                    {/* <th>ID</th> */}
                    <th>Type</th>
                    <th>Amount</th>
                    <th>Source</th>
                    <th>Destination</th>
                    <th>Description</th>
                    <th>Status</th>
                </tr>
            </thead>
            <tbody>
                {transactions.map((transaction) => (
                    <tr key={transaction.transactionId}>
                        {/* <td>{transaction.transactionId}</td> */}
                        <td>{transaction.type}</td>
                        <td>{transaction.amount}</td>
                        <td>{transaction.sourceAccountNumber || "-"}</td>
                        <td>{transaction.destinationAccountNumber || "-"}</td>
                        <td>{transaction.description}</td>
                        <td>{transaction.status}</td>
                    </tr>
                ))}
                {transactions.length == 0 && (
                    <tr>
                        <td colSpan="6">No transactions</td>
                    </tr>
                )}
            </tbody>
        </table>
    )
}
