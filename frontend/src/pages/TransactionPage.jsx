import { useLoaderData } from "react-router-dom";
import Transactions from "../components/Transactions";

export async function transactionLoader({ request, params }) {
    const transactions = await getTransactions();
    return { transactions };
}

async function getTransactions() {
    const url = '/api/account/transactions';
    const response = await fetch(url, {
        headers: {
            Authorization: `Bearer ${localStorage.getItem('token')}`,
        }
    });
    if (response.status != 200) {
        throw Error('Failed to fetch the transactions');
    }
    return await response.json();
}

export function TransactionsPage() {
    const { transactions } = useLoaderData();

    return (
        <div className="mb-96">
            <h1 className="mb-8">Transactions</h1>
            <Transactions transactions={transactions} />
        </div>
    )
}