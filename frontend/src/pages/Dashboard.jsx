import { useContext, useEffect, useState } from "react";
import { UserContext } from "../context/UserContext";
import { Link, useNavigate } from "react-router-dom";
import './Dashboard.css';
import Transactions from "../components/Transactions";
import DepositModal from "../components/DepositModal";
import TransferModal from "../components/TransferModal";

// export async function userLoader({ request, params }) {
//   // const taskId = params.taskId;
//     const token = localStorage.getItem('token');
//     const task = await getUserDetails(token);
//     return { task };
// }

export function Dashboard() {
    const { user } = useContext(UserContext);
    const navigate = useNavigate();
    const [ transactions, setTransactions ] = useState(null);
    const [ openDespositModal, setOpenDepositModal ] = useState(false);
    const [ openTransferModal, setOpenTransferModal ] = useState(false);

    useEffect(() => {
        if (!user) {
            console.log('No user, redirecting...');
            // localStorage.removeItem('token');
            navigate('/login-redirect');
        }
    }, [user, navigate]);

    return (
        <div className="max-w-full full-width">
            <h1>Dashboard</h1>
            <div className="grid grid-cols-2 mt-8 mb-72">
                <div className="col-span-1 pr-8 py-4 flex items-center justify-center">
                    <div className="flex flex-col gap-4 w-max text-left">
                        <p>Hello there!</p>
                        <h1 className="text-3xl">Balance: {user?.accountBalance}</h1>
                        <h2 className="text-xl mb-4">Account Number: {user?.accountNumber}</h2>
                        <Link to="/transactions" className="btn">Show Transactions</Link>
                    </div>
                </div>
                <div className="col-span-1 border-l-2">
                    <div className="flex flex-col gap-2 justify-center items-center py-4">
                        <button className="btn" onClick={() => setOpenDepositModal(true)}>Deposit</button>
                        <button className="btn" onClick={() => setOpenTransferModal(true)}>Transfer</button>
                    </div>
                </div>
            </div>
            <DepositModal open={openDespositModal} setOpen={setOpenDepositModal} />
            <TransferModal open={openTransferModal} setOpen={setOpenTransferModal} />
        </div>
    );
}
