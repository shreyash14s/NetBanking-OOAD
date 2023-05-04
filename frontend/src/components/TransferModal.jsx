import { useEffect, useState } from "react";
import { Form } from "react-router-dom";

export default function TransferModal({ open, setOpen }) {
    const [ amount, setAmount ] = useState(0);
    const [ accountNumber, setAccountNumber ] = useState('');
    const [ loading, setLoading ] = useState(false);
    const [ error, setError ] = useState('');

    useEffect(() => {
        setError('');
    }, []);

    const doTransfer = async () => {
        setLoading(true);
        try {
            const url = '/api/transactions/create';
            const response = await fetch(url, {
                method: 'POST',
                headers: {
                    Authorization: `Bearer ${localStorage.getItem('token')}`,
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify({
                    toAccountNumber: accountNumber,
                    transactionAmount: amount,
                    transactionType: 'transfer',
                }),
            });
            if (response.status != 200) {
                const errMsg = await response.json();
                setError(errMsg.message);
                throw Error('Failed to deposit');
            }
            const res = await response.json();
            console.log(res)
            setOpen(false);
        } finally {
            setLoading(false);
        }
    };

    return (
        <>
            <input type="checkbox" id="deposit-modal" className="modal-toggle" checked={open} readOnly />
            <div className="modal">
                <div className="modal-box">
                    <h3 className="font-bold text-lg">Transfer!</h3>
                    <p className="py-4">Enter the account and amount to transfer:</p>
                    <p className="text-red-500">{error}</p>
                    <div className="flex flex-col items-center justify-center">
                        <div className="form-control max-w-xs">
                            <label className="label">
                                <span className="label-text">Account number</span>
                            </label>
                            <input
                                placeholder="Account number"
                                className={"input input-bordered max-w-xs" + (loading ? ' input-disabled' : '')}
                                name="amount"
                                value={accountNumber}
                                onChange={(e) => setAccountNumber(e.target.value)}
                            />
                        </div>
                        <div className="form-control max-w-xs">
                            <label className="label">
                                <span className="label-text">Transfer amount</span>
                            </label>
                            <input
                                type="number"
                                placeholder="Amount"
                                className={"input input-bordered max-w-xs" + (loading ? ' input-disabled' : '')}
                                name="amount"
                                value={amount}
                                onChange={(e) => setAmount(e.target.value)}
                            />
                        </div>
                    </div>
                    <div className="modal-action">
                        {/* <label htmlFor="deposit-modal" className="btn">Ok</label> */}
                        <button className="btn" onClick={doTransfer}>Ok</button>
                        <button className="btn" onClick={() => setOpen(false)}>cancel</button>
                    </div>
                </div>
            </div>
        </>
    )
}